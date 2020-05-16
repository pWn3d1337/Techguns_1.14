package techguns.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.util.BlockUtils;

public class BlockTGMetalPanel extends GenericBlock{

	public static final PropertyEnum<TGMetalPanelType> BLOCK_TYPE = PropertyEnum.create("type",TGMetalPanelType.class);
	
	protected GenericItemBlockMeta itemblock;
	
	public BlockTGMetalPanel(String name) {
		super(name, Material.IRON);
	}

	@Override
	public BlockItem createItemBlock() {
		GenericItemBlockMeta itemblock =  new GenericItemBlockMeta(this);
		this.itemblock=itemblock;
		return itemblock;
	}
	
	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(BLOCK_TYPE).ordinal();
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
	    .withProperty(BLOCK_TYPE, TGMetalPanelType.class.getEnumConstants()[meta]);
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< TGMetalPanelType.class.getEnumConstants().length;i++) {
			BlockState state = getDefaultState().withProperty(BLOCK_TYPE, TGMetalPanelType.class.getEnumConstants()[i]);
			ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
		}
	}
	

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (TGMetalPanelType t : TGMetalPanelType.class.getEnumConstants()) {
			if (t.isEnabled()) {
				items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(BLOCK_TYPE, t))));
			}
		}
	}	
	
	
	
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { BLOCK_TYPE });
	}
}
