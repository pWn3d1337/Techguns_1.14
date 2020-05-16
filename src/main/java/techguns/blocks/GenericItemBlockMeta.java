package techguns.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.util.TextUtil;

public class GenericItemBlockMeta extends BlockItem {

	public GenericItemBlockMeta(Block block) {
		super(block);
		this.setRegistryName(block.getRegistryName());
		this.setUnlocalizedName(block.getUnlocalizedName());
		setCreativeTab(Techguns.tabTechgun);
		
		this.setHasSubtypes(true);
		//this.setMaxDamage(0);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack)+"."+stack.getItemDamage();
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		CompoundNBT tags = stack.getTagCompound();
		if(tags!=null && tags.hasKey("TileEntityData")) {
			tooltip.add(TextUtil.trans(Techguns.MODID+".block.hasTileEntityData"));
		}
	}

}
