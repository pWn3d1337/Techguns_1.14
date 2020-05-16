package techguns.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import techguns.blocks.machines.MultiBlockMachine;

public abstract class MultiBlockMachineTileEntSlave extends TileEntity {

	protected int masterX;
	protected int masterY;
	protected int masterZ;
	protected boolean hasMaster;
	
	/**
	 * 0 - unformed, other values depend on tile entity type
	 */
	protected byte type=0;
	
	private MultiBlockMachineTileEntMaster masterTileEnt=null;
    
	protected MultiBlockMachineTileEntMaster getMaster() {
		if(hasMaster) {
			if(masterTileEnt==null) {
				TileEntity tile = this.world.getTileEntity(new BlockPos(this.masterX, this.masterY, this.masterZ));
				if(tile!=null && tile instanceof MultiBlockMachineTileEntMaster) {
					this.masterTileEnt=(MultiBlockMachineTileEntMaster) tile;
					return this.masterTileEnt;
				}
			} else {
				return masterTileEnt;
			}
		}
		return null;
	}

	public void unform() {
		this.hasMaster=false;
		this.masterTileEnt=null;
		this.setType((byte) 0);
	}
	
	public void setType(byte type) {
		this.type = type;
	}
	
	public void form(BlockPos masterPos, byte type) {
		this.hasMaster=true;
		this.masterX=masterPos.getX();
		this.masterY=masterPos.getY();
		this.masterZ=masterPos.getZ();
		this.type=type;
	}
	
	public int getSlaveType() {
		return this.type;
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT tags = new CompoundNBT();
		this.writeClientDataToNBT(tags);
		return new SUpdateTileEntityPacket(pos, 1, tags);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
		this.readClientDataFromNBT(packet.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tags= super.getUpdateTag();
		this.writeClientDataToNBT(tags);
		return tags;
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		super.handleUpdateTag(tag);
		this.readClientDataFromNBT(tag);
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
		this.writeClientDataToNBT(compound);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(CompoundNBT compound) {
		this.readClientDataFromNBT(compound);
		super.readFromNBT(compound);
	}
	
	/**
	 * write all data the client needs too, to nbt.
	 * @param compound
	 * @return
	 */
	public void writeClientDataToNBT(CompoundNBT tags) {
		tags.setBoolean("hasMaster", this.hasMaster);
		if(this.hasMaster) {
			tags.setInteger("masterX", this.masterX);
			tags.setInteger("masterY", this.masterY);
			tags.setInteger("masterZ", this.masterZ);
			tags.setByte("type", this.type);
		}
	}
	
	/**
	 * read all data the client needs from nbt
	 * @param compound
	 */
	public void readClientDataFromNBT(CompoundNBT tags) {
		this.hasMaster=tags.getBoolean("hasMaster");
		if(this.hasMaster) {
			this.masterX=tags.getInteger("masterX");
			this.masterY=tags.getInteger("masterY");
			this.masterZ=tags.getInteger("masterZ");
			this.type=tags.getByte("type");
		} else {
			this.type=0;
		}
	}

	public boolean hasMaster() {
		return hasMaster;
	}
	
	public void onBlockBreak() {
		if(!this.world.isRemote && this.hasMaster) {
			this.getMaster().onMultiBlockBreak();
		}
	}
	
	public BlockPos getMasterPos() {
		return new BlockPos(masterX,masterY,masterZ);
	}
	
	/**
	 * called serverside when this tileent should send out updated to client
	 */
	public void needUpdate(){
		if(!this.world.isRemote) {
			
			ChunkPos cp = this.world.getChunkFromBlockCoords(getPos()).getPos();
			PlayerChunkMapEntry entry = ((ServerWorld)this.world).getPlayerChunkMap().getEntry(cp.x, cp.z);
			if (entry!=null) {
				entry.sendPacket(this.getUpdatePacket());
			}
		}
		this.markDirty();
	}

	public abstract MultiBlockMachine getMachineBlockType();
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
		return  (oldState.getBlock()!=newState.getBlock()) || (oldState.getValue(getMachineBlockType().MACHINE_TYPE) != newState.getValue(getMachineBlockType().MACHINE_TYPE));
		//System.out.println("Refresh?: "+oldState+ " --> "+newState+ " :"+ret);
		//return ret;
	}	

	public AxisAlignedBB getFormedCollisionBoundingBox() {
		return Block.FULL_BLOCK_AABB;
	}
}
