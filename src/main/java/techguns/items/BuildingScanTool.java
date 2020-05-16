package techguns.items;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import techguns.util.BlockPosInd;
import techguns.util.MBlock;

public class BuildingScanTool extends GenericItem {
	
	public BuildingScanTool(String name) {
		super(name);
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand,
                                      Direction facing, float hitX, float hitY, float hitZ) {
		
		ItemStack item = player.getHeldItem(hand);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if (!item.hasTagCompound()) {
			item.setTagCompound(new CompoundNBT());
		}
		if (item.getTagCompound().hasKey("x1") && item.getTagCompound().hasKey("y1") && item.getTagCompound().hasKey("z1")) {
			
			if (world.isRemote) player.sendMessage(new StringTextComponent("Position2 set ("+x+"/"+y+"/"+z+")."));
			int x1 = item.getTagCompound().getInteger("x1");
			int y1 = item.getTagCompound().getInteger("y1");
			int z1 = item.getTagCompound().getInteger("z1");
			int sizeX = Math.abs(x1-x)+1;
			int sizeY = Math.abs(y1-y)+1;
			int sizeZ = Math.abs(z1-z)+1;
			
			if (world.isRemote) player.sendMessage(new StringTextComponent("Size: ("+sizeX+"/"+sizeY+"/"+sizeZ+")."));
			
			if (!world.isRemote) {
				scanStructure(world, Math.min(x, x1), Math.min(y, y1), Math.min(z, z1), sizeX, sizeY, sizeZ);
			}

			item.getTagCompound().removeTag("x1");
			item.getTagCompound().removeTag("y1");
			item.getTagCompound().removeTag("z1");
			
		}else {
			item.getTagCompound().setInteger("x1",x);
			item.getTagCompound().setInteger("y1",y);
			item.getTagCompound().setInteger("z1",z);
			if (world.isRemote) player.sendMessage(new StringTextComponent("Position1 set ("+x+"/"+y+"/"+z+")."));
		}
		
		return ActionResultType.SUCCESS;
    }
	
	private void scanStructure(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
		StringBuilder sbDefBlocks = new StringBuilder();
		//StringBuilder sbSetBlocks = new StringBuilder();
		StringBuilder sbIndices = new StringBuilder();
		ArrayList<MBlock> blockList = new ArrayList<MBlock>();
		ArrayList<BlockPosInd> blockPosList = new ArrayList<BlockPosInd>();
		
		//sbDefBlocks.append("ArrayList<MBlock> blockList = new ArrayList<MBlock>();\n");
		MutableBlockPos p = new MutableBlockPos();
		
		for (int ix=0;ix<sizeX;ix++){
			for (int iy=0;iy<sizeY;iy++){
				for (int iz=0;iz<sizeZ;iz++){
					
					int coordX=x+ix;
					int coordY=y+iy;
					int coordZ=z+iz;
					
					BlockState bs = world.getBlockState(p.setPos(coordX, coordY, coordZ));
					Block b = bs.getBlock(); //world.getBlock(coordX, coordY, coordZ);
					//int meta = world.getBlockMetadata(coordX, coordY, coordZ);
					
					
					if ((bs != Blocks.AIR.getDefaultState()) && (bs != Blocks.DIRT.getDefaultState()) && (bs!= Blocks.GRASS.getDefaultState()) ){
						MBlock mblock = new MBlock(bs);
						if (!blockList.contains(mblock)) {
							blockList.add(mblock);
							//sbDefBlocks.append("MBlock block_"+blockList.indexOf(mblock)+" = new MBlock(Block.getBlockFromName(\""+mblock.block.getUnlocalizedName()+"\"), "+mblock.meta+");\n");
							//sbDefBlocks.append("blockList.add(new MBlock(Block.getBlockFromName(\""+mblock.block.getUnlocalizedName()+"\"), "+mblock.meta+"));\n");
							sbDefBlocks.append("blockList.add(new MBlock(\""+mblock.block.getRegistryName()+"\", "+mblock.meta+"));\n");
						}	
						//String name = "block_"+blockList.indexOf(mblock);
						//System.out.println("Block("+coordX+","+coordY+","+coordZ+"):"+b.getUnlocalizedName()+":"+meta);
						//sbSetBlocks.append("BlockUtils.setBlockRotated(world, "+name+", posX, posY, posZ, "+ix+", "+iy+", "+iz+", cntX, cntY, cntZ, rotation);\n");					
						blockPosList.add(new BlockPosInd(ix, iy, iz, blockList.indexOf(mblock)));
					}
					
				}
			}
		}
		
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(new File("structure_scan.txt")));
			pw.println(sbDefBlocks.toString());
			pw.println("---");
			pw.println();
			pw.println(blockPosList.size());
			for (int i = 0; i < blockPosList.size(); i++) {
				BlockPosInd bp = blockPosList.get(i);
				pw.println(bp.toString());
			}
			
			pw.flush();
			pw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
}
	