package techguns.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.*;
import net.minecraft.block.StairsBlock.EnumHalf;
import net.minecraft.block.StairsBlock.EnumShape;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.util.BlockUtils;

public class BlockTGStairs extends GenericBlock {
	
	protected GenericItemBlockMeta itemblock;
	
	public static final PropertyDirection FACING = HorizontalBlock.FACING;
    public static final PropertyEnum<StairsBlock.EnumHalf> HALF = PropertyEnum.<StairsBlock.EnumHalf>create("half", StairsBlock.EnumHalf.class);
    public static final PropertyEnum<StairsBlock.EnumShape> SHAPE = PropertyEnum.<StairsBlock.EnumShape>create("shape", StairsBlock.EnumShape.class);
    public static final PropertyBool TYPE2 = PropertyBool.create("type2");
    /**
     * B: .. T: xx
     * B: .. T: xx
     */
    protected static final AxisAlignedBB AABB_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
    /**
     * B: .. T: x.
     * B: .. T: x.
     */
    protected static final AxisAlignedBB AABB_QTR_TOP_WEST = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D);
    /**
     * B: .. T: .x
     * B: .. T: .x
     */
    protected static final AxisAlignedBB AABB_QTR_TOP_EAST = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
    /**
     * B: .. T: xx
     * B: .. T: ..
     */
    protected static final AxisAlignedBB AABB_QTR_TOP_NORTH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
    /**
     * B: .. T: ..
     * B: .. T: xx
     */
    protected static final AxisAlignedBB AABB_QTR_TOP_SOUTH = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
    /**
     * B: .. T: x.
     * B: .. T: ..
     */
    protected static final AxisAlignedBB AABB_OCT_TOP_NW = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 0.5D);
    /**
     * B: .. T: .x
     * B: .. T: ..
     */
    protected static final AxisAlignedBB AABB_OCT_TOP_NE = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
    /**
     * B: .. T: ..
     * B: .. T: x.
     */
    protected static final AxisAlignedBB AABB_OCT_TOP_SW = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 0.5D, 1.0D, 1.0D);
    /**
     * B: .. T: ..
     * B: .. T: .x
     */
    protected static final AxisAlignedBB AABB_OCT_TOP_SE = new AxisAlignedBB(0.5D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
    /**
     * B: xx T: ..
     * B: xx T: ..
     */
    protected static final AxisAlignedBB AABB_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    /**
     * B: x. T: ..
     * B: x. T: ..
     */
    protected static final AxisAlignedBB AABB_QTR_BOT_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D);
    /**
     * B: .x T: ..
     * B: .x T: ..
     */
    protected static final AxisAlignedBB AABB_QTR_BOT_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    /**
     * B: xx T: ..
     * B: .. T: ..
     */
    protected static final AxisAlignedBB AABB_QTR_BOT_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    /**
     * B: .. T: ..
     * B: xx T: ..
     */
    protected static final AxisAlignedBB AABB_QTR_BOT_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
    /**
     * B: x. T: ..
     * B: .. T: ..
     */
    protected static final AxisAlignedBB AABB_OCT_BOT_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 0.5D);
    /**
     * B: .x T: ..
     * B: .. T: ..
     */
    protected static final AxisAlignedBB AABB_OCT_BOT_NE = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    /**
     * B: .. T: ..
     * B: x. T: ..
     */
    protected static final AxisAlignedBB AABB_OCT_BOT_SW = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 0.5D, 0.5D, 1.0D);
    /**
     * B: .. T: ..
     * B: .x T: ..
     */
    protected static final AxisAlignedBB AABB_OCT_BOT_SE = new AxisAlignedBB(0.5D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);

	public BlockTGStairs(String name, Material mat, SoundType sound) {
		super(name, mat);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, Direction.EAST).withProperty(HALF, StairsBlock.EnumHalf.BOTTOM).withProperty(SHAPE, StairsBlock.EnumShape.STRAIGHT).withProperty(TYPE2, false));
	    this.setSoundType(sound); 
	}
	
    @Override
	public int damageDropped(BlockState state) {
		return this.getMetaFromState(this.getDefaultState().withProperty(TYPE2, state.getValue(TYPE2)));
	}

	public void addCollisionBoxToList(BlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        if (!p_185477_7_)
        {
            state = this.getActualState(state, worldIn, pos);
        }

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }

    private static List<AxisAlignedBB> getCollisionBoxList(BlockState bstate)
    {
        List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
        boolean flag = bstate.getValue(HALF) == StairsBlock.EnumHalf.TOP;
        list.add(flag ? AABB_SLAB_TOP : AABB_SLAB_BOTTOM);
        StairsBlock.EnumShape blockstairs$enumshape = (StairsBlock.EnumShape)bstate.getValue(SHAPE);

        if (blockstairs$enumshape == StairsBlock.EnumShape.STRAIGHT || blockstairs$enumshape == StairsBlock.EnumShape.INNER_LEFT || blockstairs$enumshape == StairsBlock.EnumShape.INNER_RIGHT)
        {
            list.add(getCollQuarterBlock(bstate));
        }

        if (blockstairs$enumshape != StairsBlock.EnumShape.STRAIGHT)
        {
            list.add(getCollEighthBlock(bstate));
        }

        return list;
    }

    /**
     * Returns a bounding box representing a quarter of a block (two eight-size cubes back to back).
     * Used in all stair shapes except OUTER.
     */
    private static AxisAlignedBB getCollQuarterBlock(BlockState bstate)
    {
        boolean flag = bstate.getValue(HALF) == StairsBlock.EnumHalf.TOP;

        switch ((Direction)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return flag ? AABB_QTR_BOT_NORTH : AABB_QTR_TOP_NORTH;
            case SOUTH:
                return flag ? AABB_QTR_BOT_SOUTH : AABB_QTR_TOP_SOUTH;
            case WEST:
                return flag ? AABB_QTR_BOT_WEST : AABB_QTR_TOP_WEST;
            case EAST:
                return flag ? AABB_QTR_BOT_EAST : AABB_QTR_TOP_EAST;
        }
    }

    /**
     * Returns a bounding box representing an eighth of a block (a block whose three dimensions are halved).
     * Used in all stair shapes except STRAIGHT (gets added alone in the case of OUTER; alone with a quarter block in
     * case of INSIDE).
     */
    private static AxisAlignedBB getCollEighthBlock(BlockState bstate)
    {
        Direction enumfacing = (Direction)bstate.getValue(FACING);
        Direction enumfacing1;

        switch ((StairsBlock.EnumShape)bstate.getValue(SHAPE))
        {
            case OUTER_LEFT:
            default:
                enumfacing1 = enumfacing;
                break;
            case OUTER_RIGHT:
                enumfacing1 = enumfacing.rotateY();
                break;
            case INNER_RIGHT:
                enumfacing1 = enumfacing.getOpposite();
                break;
            case INNER_LEFT:
                enumfacing1 = enumfacing.rotateYCCW();
        }

        boolean flag = bstate.getValue(HALF) == StairsBlock.EnumHalf.TOP;

        switch (enumfacing1)
        {
            case NORTH:
            default:
                return flag ? AABB_OCT_BOT_NW : AABB_OCT_TOP_NW;
            case SOUTH:
                return flag ? AABB_OCT_BOT_SE : AABB_OCT_TOP_SE;
            case WEST:
                return flag ? AABB_OCT_BOT_SW : AABB_OCT_TOP_SW;
            case EAST:
                return flag ? AABB_OCT_BOT_NE : AABB_OCT_TOP_NE;
        }
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, BlockState p_193383_2_, BlockPos p_193383_3_, Direction p_193383_4_)
    {
        p_193383_2_ = this.getActualState(p_193383_2_, p_193383_1_, p_193383_3_);

        if (p_193383_4_.getAxis() == Direction.Axis.Y)
        {
            return p_193383_4_ == Direction.UP == (p_193383_2_.getValue(HALF) == StairsBlock.EnumHalf.TOP) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
        }
        else
        {
            StairsBlock.EnumShape blockstairs$enumshape = (StairsBlock.EnumShape)p_193383_2_.getValue(SHAPE);

            if (blockstairs$enumshape != StairsBlock.EnumShape.OUTER_LEFT && blockstairs$enumshape != StairsBlock.EnumShape.OUTER_RIGHT)
            {
                Direction enumfacing = (Direction)p_193383_2_.getValue(FACING);

                switch (blockstairs$enumshape)
                {
                    case INNER_RIGHT:
                        return enumfacing != p_193383_4_ && enumfacing != p_193383_4_.rotateYCCW() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
                    case INNER_LEFT:
                        return enumfacing != p_193383_4_ && enumfacing != p_193383_4_.rotateY() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
                    case STRAIGHT:
                        return enumfacing == p_193383_4_ ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
                    default:
                        return BlockFaceShape.UNDEFINED;
                }
            }
            else
            {
                return BlockFaceShape.UNDEFINED;
            }
        }
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(BlockState state)
    {
        return false;
    }

    public boolean isFullCube(BlockState state)
    {
        return false;
    }

    

    /**
     * Determines if the block is solid enough on the top side to support other blocks, like redstone components.
     */
    public boolean isTopSolid(BlockState state)
    {
        return state.getValue(HALF) == StairsBlock.EnumHalf.TOP;
    }

    

    @Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState())));
		items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(TYPE2, true))));
	}

	/**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer)
    {
        BlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        iblockstate = iblockstate.withProperty(FACING, placer.getHorizontalFacing()).withProperty(SHAPE, StairsBlock.EnumShape.STRAIGHT);
        return facing != Direction.DOWN && (facing == Direction.UP || (double)hitY <= 0.5D) ? iblockstate.withProperty(HALF, StairsBlock.EnumHalf.BOTTOM) : iblockstate.withProperty(HALF, StairsBlock.EnumHalf.TOP);
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit.
     */
    @Nullable
    public RayTraceResult collisionRayTrace(BlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.<RayTraceResult>newArrayList();

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, worldIn, pos)))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;

        for (RayTraceResult raytraceresult : list)
        {
            if (raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);

                if (d0 > d1)
                {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public BlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE2, (meta&8)>0).withProperty(HALF, EnumHalf.values()[(meta&4)>>2]).withProperty(FACING, Direction.getHorizontal(meta&3));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(BlockState state)
    {
    	return ((state.getValue(TYPE2)?1:0)<<3) + (state.getValue(HALF).ordinal() << 2)+ state.getValue(FACING).getHorizontalIndex(); 
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(SHAPE, getStairsShape(state, worldIn, pos));
    }

    private static StairsBlock.EnumShape getStairsShape(BlockState p_185706_0_, IBlockAccess p_185706_1_, BlockPos p_185706_2_)
    {
        Direction enumfacing = (Direction)p_185706_0_.getValue(FACING);
        BlockState iblockstate = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing));

        if (isBlockStairs(iblockstate) && p_185706_0_.getValue(HALF) == iblockstate.getValue(HALF))
        {
            Direction enumfacing1 = (Direction)iblockstate.getValue(FACING);

            if (enumfacing1.getAxis() != ((Direction)p_185706_0_.getValue(FACING)).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing1.getOpposite()))
            {
                if (enumfacing1 == enumfacing.rotateYCCW())
                {
                    return StairsBlock.EnumShape.OUTER_LEFT;
                }

                return StairsBlock.EnumShape.OUTER_RIGHT;
            }
        }

        BlockState iblockstate1 = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing.getOpposite()));

        if (isBlockStairs(iblockstate1) && p_185706_0_.getValue(HALF) == iblockstate1.getValue(HALF))
        {
            Direction enumfacing2 = (Direction)iblockstate1.getValue(FACING);

            if (enumfacing2.getAxis() != ((Direction)p_185706_0_.getValue(FACING)).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing2))
            {
                if (enumfacing2 == enumfacing.rotateYCCW())
                {
                    return StairsBlock.EnumShape.INNER_LEFT;
                }

                return StairsBlock.EnumShape.INNER_RIGHT;
            }
        }

        return StairsBlock.EnumShape.STRAIGHT;
    }

    private static boolean isDifferentStairs(BlockState p_185704_0_, IBlockAccess p_185704_1_, BlockPos p_185704_2_, Direction p_185704_3_)
    {
        BlockState iblockstate = p_185704_1_.getBlockState(p_185704_2_.offset(p_185704_3_));
        return !isBlockStairs(iblockstate) || iblockstate.getValue(FACING) != p_185704_0_.getValue(FACING) || iblockstate.getValue(HALF) != p_185704_0_.getValue(HALF);
    }

    public static boolean isBlockStairs(BlockState state)
    {
        return state.getBlock() instanceof StairsBlock || state.getBlock() instanceof BlockTGStairs;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public BlockState withRotation(BlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings("incomplete-switch")
    public BlockState withMirror(BlockState state, Mirror mirrorIn)
    {
        Direction enumfacing = (Direction)state.getValue(FACING);
        StairsBlock.EnumShape blockstairs$enumshape = (StairsBlock.EnumShape)state.getValue(SHAPE);

        switch (mirrorIn)
        {
            case LEFT_RIGHT:

                if (enumfacing.getAxis() == Direction.Axis.Z)
                {
                    switch (blockstairs$enumshape)
                    {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, StairsBlock.EnumShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, StairsBlock.EnumShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, StairsBlock.EnumShape.INNER_LEFT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, StairsBlock.EnumShape.INNER_RIGHT);
                        default:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }

                break;
            case FRONT_BACK:

                if (enumfacing.getAxis() == Direction.Axis.X)
                {
                    switch (blockstairs$enumshape)
                    {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, StairsBlock.EnumShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, StairsBlock.EnumShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, StairsBlock.EnumShape.INNER_RIGHT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, StairsBlock.EnumShape.INNER_LEFT);
                        case STRAIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }
        }

        return super.withMirror(state, mirrorIn);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, HALF, SHAPE, TYPE2});
    }

    @Override
    public boolean doesSideBlockRendering(BlockState state, IBlockAccess world, BlockPos pos, Direction face)
    {
        if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
            return super.doesSideBlockRendering(state, world, pos, face);

        if ( state.isOpaqueCube() )
            return true;

        state = this.getActualState(state, world, pos);

        EnumHalf half = state.getValue(HALF);
        Direction side = state.getValue(FACING);
        EnumShape shape = state.getValue(SHAPE);
        if (face == Direction.UP) return half == EnumHalf.TOP;
        if (face == Direction.DOWN) return half == EnumHalf.BOTTOM;
        if (shape == EnumShape.OUTER_LEFT || shape == EnumShape.OUTER_RIGHT) return false;
        if (face == side) return true;
        if (shape == EnumShape.INNER_LEFT && face.rotateY() == side) return true;
        if (shape == EnumShape.INNER_RIGHT && face.rotateYCCW() == side) return true;
        return false;
    }
    
    @Override
	public BlockItem createItemBlock() {
    	this.itemblock =  new GenericItemBlockMeta(this);
		return itemblock;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		BlockState state = getDefaultState();
		BlockState state2 = getDefaultState().withProperty(TYPE2, true);
		ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
		ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state2), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state2)));
	}
    
}
