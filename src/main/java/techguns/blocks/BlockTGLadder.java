package techguns.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.items.armors.ICamoChangeable;
import techguns.util.BlockUtils;

public class BlockTGLadder<T extends Enum<T> & IStringSerializable> extends GenericBlock implements ICamoChangeable {

	public static final PropertyDirection FACING = HorizontalBlock.FACING;
	
	protected static final double LADDER_SIZE=0.125D;
    protected static final AxisAlignedBB LADDER_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, LADDER_SIZE, 1.0D, 1.0D);
    protected static final AxisAlignedBB LADDER_WEST_AABB = new AxisAlignedBB(1.0D-LADDER_SIZE, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB LADDER_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, LADDER_SIZE);
    protected static final AxisAlignedBB LADDER_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 1.0D-LADDER_SIZE, 1.0D, 1.0D, 1.0D);
	
    protected BlockStateContainer blockStateOverride;
	
	public PropertyEnum<T> TYPE;
	protected Class<T> clazz;
	protected GenericItemBlockMeta itemblock;
    
	public BlockTGLadder(String name, Class<T> clazz) {
		super(name, Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.clazz=clazz;
		this.TYPE = PropertyEnum.create("type", clazz);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(TYPE).add(FACING).build();
		this.setDefaultState(this.getBlockState().getBaseState());
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
		case NORTH:
			return LADDER_NORTH_AABB;
		case SOUTH:
			return LADDER_SOUTH_AABB;
		case WEST:
			return LADDER_WEST_AABB;
		case EAST:
		default:
			return LADDER_EAST_AABB;
		}
	}

	@Override
	public BlockStateContainer getBlockState() {
		return this.blockStateOverride;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for
	 * render
	 */
	@Override
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state) {
		return false;
	}
	
	    /**
	     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	     * IBlockstate
	     */
		@Override
	    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer)
	    {
			BlockState basetype = this.getStateFromMeta(meta);
	        if (facing.getAxis().isHorizontal() /*&& this.canAttachTo(worldIn, pos.offset(facing.getOpposite()), facing)*/)
	        {
	            return basetype.withProperty(FACING, facing);
	        }
	        else
	        {
	        	if(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockTGLadder) {
	        		BlockState statedown = worldIn.getBlockState(pos.down());
	        		return basetype.withProperty(FACING, statedown.getValue(FACING));
	        	} else if (worldIn.getBlockState(pos.up()).getBlock() instanceof BlockTGLadder) {
	        		BlockState stateup = worldIn.getBlockState(pos.up());
	        		return basetype.withProperty(FACING, stateup.getValue(FACING));
	        	}
	        	
	        	return basetype.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	        }
	    }
		
		@Override
		public int damageDropped(BlockState state) {
			return this.getMetaFromState(this.getDefaultState().withProperty(TYPE, state.getValue(TYPE)));
		}

		@Override
		public int getMetaFromState(BlockState state) {
			return state.getValue(FACING).getHorizontalIndex() << 2 | state.getValue(TYPE).ordinal();
		}

		@Override
		public BlockState getStateFromMeta(int meta) {
			return this.getDefaultState()
		    .withProperty(FACING, Direction.getHorizontal(meta >> 2))
		    .withProperty(TYPE, clazz.getEnumConstants()[meta & 0b11]);
	    }

		@Override
		public boolean isLadder(BlockState state, IBlockAccess world, BlockPos pos, LivingEntity entity) {
			return true;
		}

		@Override
	    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, BlockState p_193383_2_, BlockPos p_193383_3_, Direction p_193383_4_)
	    {
	        return BlockFaceShape.UNDEFINED;
	    }

		@Override
		public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
			for (T t : clazz.getEnumConstants()) {
				items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(TYPE, t))));
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
		public BlockItem createItemBlock() {
			this.itemblock =  new GenericItemBlockMeta(this);
			return itemblock;
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerItemBlockModels() {
			for(int i = 0; i< clazz.getEnumConstants().length;i++) {
				BlockState state = getDefaultState().withProperty(TYPE, clazz.getEnumConstants()[i]);
				ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
			}
		}
		
		@Override
		public BlockState withMirror(BlockState state, Mirror mirrorIn) {
			Direction facing = state.getValue(FACING);
			return state.withProperty(FACING,mirrorIn.mirror(facing));
		}

		@Override
		public int getCamoCount() {
			return clazz.getEnumConstants().length-1; //actually maxcamo
		}

		@Override
		public int getCurrentCamoIndex(ItemStack item) {
			int meta = item.getMetadata();
			BlockState state = this.getStateFromMeta(meta);
			return state.getValue(TYPE).ordinal();
		}

		@Override
		public String getCurrentCamoName(ItemStack item) {
			return Techguns.MODID+"."+this.getRegistryName().getResourcePath()+".camoname."+getCurrentCamoIndex(item);
		}

		@Override
		public int switchCamo(ItemStack item, boolean back) {
			BlockState state = this.getStateFromMeta(item.getMetadata());
			
			int type = state.getValue(TYPE).ordinal();
			
			if(back) {
				type--;
				if(type<0) {
					type=clazz.getEnumConstants().length-1;
				}
			} else {
				type++;
				if(type>=clazz.getEnumConstants().length) {
					type=0;
				}
			}
			int newmeta = this.getMetaFromState(state.withProperty(TYPE, clazz.getEnumConstants()[type]));
			item.setItemDamage(newmeta);
			
			return newmeta;
		}

		@Override
		public int getFirstItemCamoDamageValue() {
			return this.getMetaFromState(getDefaultState().withProperty(TYPE, clazz.getEnumConstants()[0]));
		}
		
		
		
}
