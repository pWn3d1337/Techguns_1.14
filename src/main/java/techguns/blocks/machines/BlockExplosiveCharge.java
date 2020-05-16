package techguns.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.api.machines.IMachineType;
import techguns.tileentities.ExplosiveChargeTileEnt;
import techguns.util.BlockUtils;

public class BlockExplosiveCharge<T extends Enum<T> & IStringSerializable & IMachineType> extends BasicMachine<T> {

	public static final PropertyDirection FACING = DirectionalBlock.FACING; //PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public static final PropertyBool ARMED = PropertyBool.create("armed");
	
	public BlockExplosiveCharge(String name, Class<T> clazz) {
		super(name, clazz);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(FACING).add(MACHINE_TYPE).add(ARMED).build();
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(ARMED, false));
	}
	
	protected static final AxisAlignedBB[] boundingBoxes = {
		//D,U,N,S,W,E
		new AxisAlignedBB(2/16d, 0, 2/16d, 14/16d, 5/16d, 14/16d),
		new AxisAlignedBB(2/16d, 11/16d, 2/16d, 14/16d, 1, 14/16d),
		
		new AxisAlignedBB(2/16d, 2/16d, 0, 14/16d, 14/16d, 5/16d),
		new AxisAlignedBB(2/16d, 2/16d, 11/16d, 14/16d, 14/16d, 1),
		
		new AxisAlignedBB(0, 2/16d, 2/16d, 5/16d, 14/16d, 14/16d),
		new AxisAlignedBB(11/16d, 2/16d, 2/16d, 1, 14/16d, 14/16d)
	};

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		Direction facing = state.getValue(FACING);
		return boundingBoxes[facing.getIndex()];
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(FACING).getIndex() << 1 | state.getValue(MACHINE_TYPE).getIndex();
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
	    .withProperty(FACING, Direction.getFront(meta >> 1))
	    .withProperty(MACHINE_TYPE, clazz.getEnumConstants()[meta & 1]);
    }
	
	@Override
	public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile!=null && tile instanceof ExplosiveChargeTileEnt) {
			ExplosiveChargeTileEnt charge = (ExplosiveChargeTileEnt) tile;
			return state.withProperty(ARMED,charge.isArmed());
		}
		return state;
	}

	@Override
	public boolean isFullCube(BlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}
	
	/**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer)
    {
        return this.getStateFromMeta(meta).withProperty(FACING, facing.getOpposite());
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
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		
		if (worldIn.isAirBlock(pos.offset(state.getValue(FACING)))){
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public BlockState withRotation(BlockState state, Rotation rot) {
		Direction facing = state.getValue(FACING);
		switch(rot) {
		case CLOCKWISE_180:
			return state.withProperty(FACING, facing.getOpposite());
		case CLOCKWISE_90:
			return state.withProperty(FACING, facing.rotateY());
		case COUNTERCLOCKWISE_90:
			return state.withProperty(FACING, facing.rotateYCCW());
		case NONE:
		default:
			return state;
		}
	}

	@Override
	public BlockState withMirror(BlockState state, Mirror mirrorIn) {
		Direction facing = state.getValue(FACING);
		return state.withProperty(FACING,mirrorIn.mirror(facing));
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, Direction axis) {
		if (axis == Direction.DOWN || axis== Direction.UP) {
			BlockState state = world.getBlockState(pos);
			BlockState statenew = state.withProperty(FACING, state.getValue(FACING).rotateY());
			world.setBlockState(pos, statenew, 3);
			return true;
		} 
		return false;
	}
}
