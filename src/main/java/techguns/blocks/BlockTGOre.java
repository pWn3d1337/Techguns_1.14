package techguns.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.util.BlockUtils;

public class BlockTGOre extends GenericBlock {

	public static PropertyEnum<EnumOreType> ORE_TYPE = PropertyEnum.create("type",EnumOreType.class);
	protected GenericItemBlockMeta itemblock;
	
	public BlockTGOre(String name) {
		super(name, Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ORE_TYPE, EnumOreType.ORE_COPPER));
	}
	
	public ItemStack getStackFor(EnumOreType type) {
		return new ItemStack(this,1,this.getMetaFromState(this.getDefaultState().withProperty(ORE_TYPE, type)));
	}
	
	@Override
	public BlockItem createItemBlock() {
		GenericItemBlockMeta itemblock =  new GenericItemBlockMeta(this);
		this.itemblock=itemblock;
		return itemblock;
	}
	
	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(ORE_TYPE).ordinal();
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
	    .withProperty(ORE_TYPE, EnumOreType.class.getEnumConstants()[meta]);
    }
	
	@Override
	public int damageDropped(BlockState state) {
		return this.getMetaFromState(getDefaultState().withProperty(ORE_TYPE, state.getValue(ORE_TYPE)));
	}
	
	@Override
	public int getLightValue(BlockState state) {
		EnumOreType type = state.getValue(ORE_TYPE);
		return type.getLightlevel();
	}

	@Override
	public float getBlockHardness(BlockState blockState, World worldIn, BlockPos pos) {
		EnumOreType type = blockState.getValue(ORE_TYPE);
		return type.getHardness();
	}

	@Override
	public String getHarvestTool(BlockState state) {
		return "pickaxe";
	}

	@Override
	public int getHarvestLevel(BlockState state) {
		EnumOreType type = state.getValue(ORE_TYPE);
		return type.getMininglevel();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< EnumOreType.class.getEnumConstants().length;i++) {
			BlockState state = getDefaultState().withProperty(ORE_TYPE, EnumOreType.class.getEnumConstants()[i]);
			ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
		}
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (EnumOreType t : EnumOreType.class.getEnumConstants()) {
			if (t.isEnabled()) {
				items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(ORE_TYPE, t))));
			}
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { ORE_TYPE });
	}
}
