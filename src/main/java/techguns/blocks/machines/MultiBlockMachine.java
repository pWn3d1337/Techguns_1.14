package techguns.blocks.machines;

import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.api.machines.IMachineType;
import techguns.tileentities.MultiBlockMachineTileEntMaster;
import techguns.tileentities.MultiBlockMachineTileEntSlave;
import techguns.util.BlockUtils;

public class MultiBlockMachine<T extends Enum<T> & IStringSerializable & IMachineType> extends BasicMachine<T> {
	public static final PropertyBool FORMED = PropertyBool.create("formed");
	
	public static final PropertyDirection MULTIBLOCK_DIRECTION = HorizontalBlock.FACING;
	
	public MultiBlockMachine(String name, Class<T> clazz) {
		super(name, clazz);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(MACHINE_TYPE).add(FORMED).add(MULTIBLOCK_DIRECTION).build();
		this.setDefaultState( this.getBlockState().getBaseState().withProperty(FORMED, false).withProperty(MULTIBLOCK_DIRECTION, Direction.SOUTH));
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return (state.getValue(FORMED)?8:0) + state.getValue(MACHINE_TYPE).getIndex();
	}
	
	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
	    .withProperty(FORMED, meta>=8)
	    .withProperty(MACHINE_TYPE, clazz.getEnumConstants()[meta>=8?meta-8:meta]);
	}
	
	@Override
	public BlockState getActualState(BlockState state, IBlockAccess world, BlockPos pos) {
		BlockState s = world.getBlockState(pos);
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof MultiBlockMachineTileEntMaster) {
			MultiBlockMachineTileEntMaster mastertile = (MultiBlockMachineTileEntMaster) tile;
			if(mastertile.isFormed()) {
				Direction dir = mastertile.getMultiblockDirection();
				s = s.withProperty(MULTIBLOCK_DIRECTION, dir);
				return s;
			}
		}
		return s.withProperty(MULTIBLOCK_DIRECTION, Direction.SOUTH);
	}

	/**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer)
    {
        return this.getStateFromMeta(meta).withProperty(FORMED,false);
    }

	/*@Override
	public boolean hasCustomBreakingProgress(IBlockState state) {
		return state.getValue(FORMED);
	}*/

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		T t = state.getValue(MACHINE_TYPE);
		return t.getRenderType();
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos,
                                  PlayerEntity player) {
		if(state.getValue(MACHINE_TYPE).hideInCreative()) {
			return ItemStack.EMPTY;
		}
		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	public boolean isFullBlock(BlockState state) {
		return this.isFullCube(state);
	}

	@Override
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(BlockState state) {
		
		/**
		 * Required to check this, because vanilla block constructor calls this method too early.
		 */
		Block b= state.getBlock();
		if(b.getRegistryName()==null) {
			return false;
		}
		return this.isFullCube(state);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		if(blockState.getValue(FORMED)) {
			
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof MultiBlockMachineTileEntSlave) {
				return ((MultiBlockMachineTileEntSlave) tile).getFormedCollisionBoundingBox();
			}
			
		}
		
		return FULL_BLOCK_AABB;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		return this.getCollisionBoundingBox(state, source, pos);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< clazz.getEnumConstants().length;i++) {
			BlockState state = getDefaultState().withProperty(MACHINE_TYPE, clazz.getEnumConstants()[i]);
			ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
		}
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, Direction axis) {
		//NO ROTATING ON MULTIBLOCK MACHINES
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
		boolean formed = state.getValue(FORMED);
		if(formed) {
			return BlockFaceShape.UNDEFINED;
		}
		return super.getBlockFaceShape(worldIn, state, pos, face);
	}
	
}
