package techguns.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.blocks.ItemBlockOreDrill;

public class BlockOreDrill extends MultiBlockMachine<EnumOreDrillType> {

	public BlockOreDrill(String name) {
		super(name, EnumOreDrillType.class);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(BlockState blockState, IBlockAccess blockAccess, BlockPos pos,
                                        Direction side) {

        BlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if(this.getClass().isInstance(block)) {
        	if(blockState.getValue(MACHINE_TYPE)==EnumOreDrillType.SCAFFOLD && blockState.getValue(FORMED)) {
        		return iblockstate!=blockState;
        	}
        }
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);	
	}

	@Override
	public BlockItem createItemBlock() {
		ItemBlockOreDrill itemblock =  new ItemBlockOreDrill(this);
		this.itemblock=itemblock;
		return itemblock;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
		return BlockFaceShape.SOLID;
	}
	
}
