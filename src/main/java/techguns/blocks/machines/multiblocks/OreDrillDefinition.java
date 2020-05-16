package techguns.blocks.machines.multiblocks;

import java.util.ArrayList;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.BlockOreCluster;
import techguns.blocks.machines.EnumOreDrillType;
import techguns.blocks.machines.MultiBlockMachine;
import techguns.packets.PacketMultiBlockFormInvalidBlockMessage;
import techguns.tileentities.MultiBlockMachineTileEntMaster;
import techguns.tileentities.OreDrillTileEntMaster;

public class OreDrillDefinition extends MultiBlockMachineSchematic {

	protected static final Direction[][] DIRECTIONS_AROUND_AXIS = { /*X*/{Direction.DOWN, Direction.NORTH, Direction.UP, Direction.SOUTH},
																	 /*Y*/{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST},
																	 /*Z*/{Direction.DOWN, Direction.EAST, Direction.UP, Direction.WEST}
	};

	public static final int MAX_RADIUS=3;
	public static final int MAX_SIZE = 16;
	
	public OreDrillDefinition() {
		super(OreDrillTileEntMaster.class);
	}

	@Override
	public boolean checkForm(World w, PlayerEntity player, BlockPos masterPos, Direction direction) {
		int dir = getDirection(w, player, masterPos);
		if( dir <0) {
			sendErrorMSG(w, masterPos, player, PacketMultiBlockFormInvalidBlockMessage.MSG_TYPE_ORE_DRILL_CONTROLLER_ALONE);
			return false;
		}
		
		Direction drill_direction = Direction.getFront(dir);
				
		int[] length = this.checkLength(w, masterPos, drill_direction);
		
		//System.out.println("Length: engines:"+length[0]+", rods:"+length[1]);
		
		int engines = length[0];
		int rods = length[1];
		
		if(engines>=0&& rods>0 && (rods+engines<=MAX_SIZE)) {
		
			if(engines==0 && rods==1) {
				return true; 
			}
			
			//check distribution of engines and rods
			int rad = this.checkWidth(w, masterPos, drill_direction);
			
			//Have radius, check all blocks
			
			
			if (this.checkDrillSize(rods, engines, rad)) {
				//check engine block
				if(allBlocksMatch(w, player, getEngineBlocks(w, masterPos, drill_direction, rad, engines), TGBlocks.ORE_DRILL_BLOCK.getDefaultState().withProperty(TGBlocks.ORE_DRILL_BLOCK.MACHINE_TYPE, EnumOreDrillType.ENGINE).withProperty(TGBlocks.ORE_DRILL_BLOCK.FORMED, false), false)) {
					//engines OK, check outer frame
					if(allBlocksMatch(w, player, getFrameBlocks(w, masterPos, drill_direction, rad+1, engines+rods, true, true), TGBlocks.ORE_DRILL_BLOCK.getDefaultState().withProperty(TGBlocks.ORE_DRILL_BLOCK.MACHINE_TYPE, EnumOreDrillType.FRAME).withProperty(TGBlocks.ORE_DRILL_BLOCK.FORMED, false), false)) {
						
						if(allBlocksMatch(w, player, getFrameBlocks(w, masterPos, drill_direction, rad+1, engines+rods, false, true), TGBlocks.ORE_DRILL_BLOCK.getDefaultState().withProperty(TGBlocks.ORE_DRILL_BLOCK.MACHINE_TYPE, EnumOreDrillType.SCAFFOLD).withProperty(TGBlocks.ORE_DRILL_BLOCK.FORMED, false), false)) {
							
							if(allBlocksMatch(w, player, getAirBlocks(w, masterPos.offset(drill_direction, engines), drill_direction, rad, rods-1), Blocks.AIR.getDefaultState(), false)) {
								return true;
							} else {
								//Air Block Error
								sendErrorMSG(w, masterPos, player, PacketMultiBlockFormInvalidBlockMessage.MSG_TYPE_ORE_DRILL_AIR_ERROR);
							}
							
						} else {
							//Scaffold Error
							sendErrorMSG(w, masterPos, player, PacketMultiBlockFormInvalidBlockMessage.MSG_TYPE_ORE_DRILL_SCAFFOLD_ERROR);
						}
						
					} else {
						//Frame Error
						sendErrorMSG(w, masterPos, player, PacketMultiBlockFormInvalidBlockMessage.MSG_TYPE_ORE_DRILL_FRAME_ERROR);
					}	
					
				} else {
					//Engine Error
					sendErrorMSG(w, masterPos, player, PacketMultiBlockFormInvalidBlockMessage.MSG_TYPE_ORE_DRILL_ENGINE_ERROR);
				}
			} else {
				sendErrorMSG(w, masterPos, player, PacketMultiBlockFormInvalidBlockMessage.MSG_TYPE_ROD_SIZE);
			}
			return false;
			
			
		} else {
			
			if(engines <0) {
				sendErrorPing(w, masterPos.offset(drill_direction, -engines+1), player, 0, false);
			} else if (rods < 0) {
				sendErrorPing(w, masterPos.offset(drill_direction, (-rods)+Math.abs(engines)+1), player, 0, false);
			}
			return false;
		}
	}

	public boolean checkDrillSize(int rods, int engines, int radius) {
		//System.out.println("Radius:"+radius+" Rods:"+rods+ "Engines: "+engines);
		return rods>=(radius*2+1) && rods >= engines && engines > radius;		
	}
	
	public static Direction getSide1(Direction facing) {
		switch(facing.getAxis()) {
		case X:
			return facing.DOWN;
		case Y:
			return facing.NORTH;
		case Z:
			return facing.DOWN;
		default:
			return facing.DOWN; //WOn't happen
		}
	}
	
	public static Direction getSide2(Direction facing) {
		switch(facing.getAxis()) {
		case X:
			return facing.NORTH;
		case Y:
			return facing.EAST;
		case Z:
			return facing.EAST;
		default:
			return facing.DOWN; //WOn't happen
		}
	}
	
	protected ArrayList<BlockPos> getEngineBlocks(World w, BlockPos masterPos, Direction direction, int radius, int len){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>((radius*2+1)*(radius*2+1)*len);
		
		for(int r1=-radius;r1<=radius; r1++) for (int r2=-radius;r2<=radius;r2++) for (int h=0; h<len; h++){
			BlockPos p = masterPos.offset(direction, h+1).offset(getSide1(direction), r1).offset(getSide2(direction), r2);
			positions.add(p);
		}
		return positions;
	}
	
	protected ArrayList<BlockPos> getAirBlocks(World w, BlockPos startPos, Direction direction, int radius, int len){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		
		for(int r1=-radius;r1<=radius; r1++) for (int r2=-radius;r2<=radius;r2++) for (int h=0; h<len; h++){
			
			if(!(r1==0 && r2==0)) {
				BlockPos p = startPos.offset(direction, h+1).offset(getSide1(direction), r1).offset(getSide2(direction), r2);
				positions.add(p);
			}
		}
		return positions;
	}
	
	protected ArrayList<BlockPos> getFrameBlocks(World w, BlockPos masterPos, Direction direction, int radius, int len, boolean outer, boolean lastRow){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		
		for(int r1=-radius;r1<=radius; r1++) for (int r2=-radius;r2<=radius;r2++) for (int h=0; h<len; h++){
			
			if(outer && isFramePos(r1,r2, h, radius, len)) {	
				BlockPos p = masterPos.offset(direction, h+1).offset(getSide1(direction), r1).offset(getSide2(direction), r2);
				positions.add(p);
			} else if (!outer && isScaffoldPos(r1,r2,h,radius, len)) {
				if(!lastRow || h!=len-1) {
					BlockPos p = masterPos.offset(direction, h+1).offset(getSide1(direction), r1).offset(getSide2(direction), r2);
					positions.add(p);
				}
			}
		}
		return positions;
	}
	
	protected ArrayList<BlockPos> getFinalScaffoldRow(World w, BlockPos masterPos, Direction direction, int radius, int len){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>((radius*2+1)*(radius*2+1)-1);
		for(int r1=-radius;r1<=radius; r1++) for (int r2=-radius;r2<=radius;r2++) {
			if (!(r1==0 && r2==0)) {
				BlockPos p = masterPos.offset(direction, len).offset(getSide1(direction), r1).offset(getSide2(direction), r2);
				positions.add(p);
			}
		}
		return positions;
	}
	
	protected boolean isFramePos(int r1, int r2, int h, int radius, int len) {
		int minc=0;
		if(Math.abs(r1)==radius) minc++;
		if(Math.abs(r2)==radius) minc++;
		if(h==0||h==len-1) minc++;
		return minc>=2;
	}
	
	protected boolean isScaffoldPos(int r1, int r2, int h, int radius, int len) {
		if(h==0) return false;
		int minc=0;
		if(Math.abs(r1)==radius) minc++;
		if(Math.abs(r2)==radius) minc++;
		if(h==len-1) {
			minc++;
			return minc==1 && !(r1==0 && r2==0);
		}
		return minc==1;
	}
	
	@Override
	public boolean form(World w, PlayerEntity player, BlockPos masterPos, Direction direction) {
		TileEntity tile = w.getTileEntity(masterPos);
		if(tile!=null && tile instanceof OreDrillTileEntMaster) {
		
			OreDrillTileEntMaster master = (OreDrillTileEntMaster) tile;
			
			int dir = getDirection(w, player, masterPos);
			if( dir <0) return false;
			
			Direction drill_direction = Direction.getFront(dir);
					
			int[] length = this.checkLength(w, masterPos, drill_direction);
			
			//System.out.println("Length: engines:"+length[0]+", rods:"+length[1]);
			
			int engines = length[0];
			int rods = length[1];
			
			if(engines>=0&& rods>0) {
			
				if(engines==0 && rods==1) {
					master.formOreDrill(direction,engines, rods, 0, drill_direction);
					linkSlave(w, player, masterPos.offset(drill_direction, 1), 0, masterPos);
					return true; 
				}
				
				//check radius
				int rad = this.checkWidth(w, masterPos, drill_direction);
				
				master.formOreDrill(direction,engines, rods, rad, drill_direction);
				
				for(int i=engines;i<engines+rods+1;i++) {
					linkSlave(w, player, masterPos.offset(drill_direction, i), 0, masterPos);
					//sendErrorPing(w, masterPos.offset(drill_direction, i), player, 0, false);
				}
				
				this.getEngineBlocks(w, masterPos, drill_direction, rad, engines).forEach(p -> linkSlave(w, player, p, 1, masterPos));
				
				this.getFrameBlocks(w, masterPos, drill_direction, rad+1, engines+rods, true, true).forEach(p -> linkSlave(w, player, p, 2, masterPos));
				this.getFrameBlocks(w, masterPos, drill_direction, rad+1, engines+rods, false, false).forEach(p -> linkSlave(w, player, p, 3, masterPos));
				this.getFinalScaffoldRow(w, masterPos, drill_direction, rad, engines+rods).forEach(p -> {linkSlave(w, player, p, 4, masterPos);
				/*sendErrorPing(w, p, player, 0, false);*/});
				
			}
		}
		return false;
	}

	@Override
	protected void updateBlockStateForm(World w, BlockPos pos, int type) {
		if(type==4) {
			w.setBlockState(pos, w.getBlockState(pos).withProperty(MultiBlockMachine.FORMED, true).withProperty(TGBlocks.ORE_DRILL_BLOCK.MACHINE_TYPE, EnumOreDrillType.SCAFFOLD_HIDDEN), 3);
		} else {
			super.updateBlockStateForm(w, pos, type);
		}
	}

	@Override
	protected void updateBlockStateUnform(World w, BlockPos pos, BlockState bs, int type) {
		if (bs.getValue(TGBlocks.ORE_DRILL_BLOCK.MACHINE_TYPE)==EnumOreDrillType.SCAFFOLD_HIDDEN) {
			w.setBlockState(pos, w.getBlockState(pos).withProperty(MultiBlockMachine.FORMED, false).withProperty(TGBlocks.ORE_DRILL_BLOCK.MACHINE_TYPE, EnumOreDrillType.SCAFFOLD), 3);
		} else {
			super.updateBlockStateUnform(w, pos, bs, type);
		}
	}

	@Override
	public void unform(World w, MultiBlockMachineTileEntMaster master) {
		if(master instanceof OreDrillTileEntMaster) {
			OreDrillTileEntMaster masterTile = (OreDrillTileEntMaster) master;
					
			Direction drill_direction = masterTile.getDrill_direction();
					
			int engines = masterTile.getEngines();
			int rods = masterTile.getRods();
			
			BlockPos masterPos = masterTile.getPos();
			
			if(engines==0 && rods==1) {
				unlinkSlave(w, masterPos.offset(drill_direction, 1));
				master.unform();
				return; 
			}
			
			//check radius
			int rad = masterTile.getRadius();
			
			for(int i=engines;i<engines+rods+1;i++) {
				unlinkSlave(w, masterPos.offset(drill_direction, i));
				//sendErrorPing(w, masterPos.offset(drill_direction, i), player, 0, false);
			}
			
			this.getEngineBlocks(w, masterPos, drill_direction, rad, engines).forEach(p -> unlinkSlave(w, p));
			
			this.getFrameBlocks(w, masterPos, drill_direction, rad+1, engines+rods, true, true).forEach(p -> unlinkSlave(w, p));
			this.getFrameBlocks(w, masterPos, drill_direction, rad+1, engines+rods, false, false).forEach(p -> unlinkSlave(w, p));
			this.getFinalScaffoldRow(w, masterPos, drill_direction, rad, engines+rods).forEach(p -> unlinkSlave(w, p));
			
			master.unform();
		}
	}

	@Override
	public boolean canFormFromSide(Direction side) {
		return true;
	}

	protected int getDirection(World w, PlayerEntity ply, BlockPos startPos) {
		int dir=-1;
		for (Direction f: Direction.VALUES) {
			if(checkBlock(w, startPos.offset(f))) {
				if(dir==-1) {
					dir=f.ordinal();
				} else {
					sendErrorPing(w, startPos.offset(f, 1), ply, 0, false);
					return -1;
				}
			}
		}
		return dir;
	}
	
	protected int checkWidth(World w, BlockPos p, Direction dir) {
		BlockPos enginePos = p.offset(dir, 1);
		int rad=0;
		for (Direction facing : DIRECTIONS_AROUND_AXIS[dir.getAxis().ordinal()]) {
			int result = countEngines(w, enginePos, facing);
			if(result>rad) {
				rad=result;
			}
		}
		return rad;
	}

	protected int countEngines(World w, BlockPos p, Direction dir) {
		int count=0;
		while(isDrillType(w,p.offset(dir, count+1), EnumOreDrillType.ENGINE) && count < MAX_RADIUS) {
			count++;
		}
		return count;
	}
	
	
	/**
	 * Return the length of the drills and rods as int[] with 2 entries, negative values = errors
	 * [engines,rods]
	 * negative rods -> failure after |rods| rod blocks 
	 */
	protected int[] checkLength(World w, BlockPos p, Direction dir) {
		int engines=0;
		int rods=0;
		if (isDrillType(w, p.offset(dir, 1), EnumOreDrillType.ROD)) {
			//Check for mini drill with Controller + rod
			rods++;
			if(isOreCluster(w, p.offset(dir, 2))) {
				return new int[] {0,1};
			} else {
				return new int[] {-1,-1};
			}
			
		} else if (isDrillType(w, p.offset(dir, 1), EnumOreDrillType.ENGINE)) {
			engines++;
			while(isDrillType(w, p.offset(dir, engines+1), EnumOreDrillType.ENGINE) && engines<MAX_SIZE) {
				engines++;
			}
			if (isDrillType(w, p.offset(dir, engines+rods+1), EnumOreDrillType.ROD)) {
				rods++;
				while(isDrillType(w, p.offset(dir, engines+rods+1), EnumOreDrillType.ROD ) && rods<MAX_SIZE) {
					rods++;
				}				
				if(isOreCluster(w, p.offset(dir, engines+rods+1))) {
					return new int[] {engines, rods};
				} else {
					return new int[] {engines, -rods};
				}
				
			} else {
				return new int[] {-engines,-1};
			}
		} else {
			return new int[] {-1,-1};
		}
		
	}
	
	public static boolean isOreCluster(World w, BlockPos p) {
		BlockState bs = w.getBlockState(p);
		if(bs.getBlock() instanceof BlockOreCluster) {
			return true;
		}
		return false;
	}
	
	protected boolean isDrillType(World w, BlockPos p,EnumOreDrillType type) {
		BlockState bs = w.getBlockState(p);
		if(bs.getBlock()==TGBlocks.ORE_DRILL_BLOCK) {
			EnumOreDrillType t = bs.getValue(TGBlocks.ORE_DRILL_BLOCK.MACHINE_TYPE);
			boolean formed = bs.getValue(TGBlocks.ORE_DRILL_BLOCK.FORMED);
			if( !formed && t == type) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean checkBlock(World w, BlockPos p) {
		BlockState bs = w.getBlockState(p);
		if(bs.getBlock()==TGBlocks.ORE_DRILL_BLOCK) {
			EnumOreDrillType type = bs.getValue(TGBlocks.ORE_DRILL_BLOCK.MACHINE_TYPE);
			boolean formed = bs.getValue(TGBlocks.ORE_DRILL_BLOCK.FORMED);
			if( !formed && (type == EnumOreDrillType.ROD || type == EnumOreDrillType.ENGINE)) {
				return true;
			}
		}
		return false;
	}
}
