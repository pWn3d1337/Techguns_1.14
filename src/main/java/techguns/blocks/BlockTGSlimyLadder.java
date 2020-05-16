package techguns.blocks;

import net.minecraft.block.*;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import techguns.util.BlockUtils;

public class BlockTGSlimyLadder extends LadderBlock implements IGenericBlock {

	protected BlockItem itemblock;
	
	public BlockTGSlimyLadder(String name) {
		super();
		this.setSoundType(SoundType.SLIME);
		this.init(this, name, true);
		this.setDefaultState(this.getDefaultState().withProperty(FACING, Direction.SOUTH));
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState())));
	}
	
	@Override
	public int damageDropped(BlockState state) {
		return this.getMetaFromState(getDefaultState());
	}

	@Override
	public BlockItem createItemBlock() {
		this.itemblock= new GenericItemBlockMeta(this);
		return itemblock;
	}

	@Override
	public void registerBlock(Register<Block> event) {
		event.getRegistry().register(this);
	}

	@Override
	public void registerItemBlockModels() {
		 for (Direction enumfacing : Direction.Plane.HORIZONTAL) {
			 BlockState state = getDefaultState().withProperty(FACING, enumfacing);
             ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state),  new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
         }
	}

    protected boolean canAttachTo(World p_193392_1_, BlockPos p_193392_2_, Direction p_193392_3_)
    {
        BlockState iblockstate = p_193392_1_.getBlockState(p_193392_2_);
        boolean flag = isExceptBlockForAttachWithPiston(iblockstate.getBlock());
        return !flag && iblockstate.getBlockFaceShape(p_193392_1_, p_193392_2_, p_193392_3_) == BlockFaceShape.SOLID && !iblockstate.canProvidePower();
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (!super.canPlaceBlockAt(worldIn, pos))
			return false;
		for (Direction enumfacing : Direction.Plane.HORIZONTAL) {
			if (this.canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing)) {
				return true;
			}
		}
		return false;
	}

	
}
