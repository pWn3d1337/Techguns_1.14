package techguns.recipes;

import com.google.gson.JsonObject;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import techguns.Techguns;
import techguns.items.guns.GenericGun;

public class ShapedOreRecipeCopyNBTFactory implements IRecipeFactory {

	public static final String COPY_NBT_RECIPE = "copy_nbt";
	
	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);
		ShapedPrimer p = new ShapedPrimer();
		p.height=recipe.getRecipeHeight();
		p.width=recipe.getRecipeWidth();
		p.input=recipe.getIngredients();
		p.mirrored= JSONUtils.getBoolean(json, "mirrored", true);
		ShapedOreRecipeCopyNBT r = new ShapedOreRecipeCopyNBT(new ResourceLocation(Techguns.MODID, COPY_NBT_RECIPE), recipe.getRecipeOutput(), p);
		return r;
	}

	public static class ShapedOreRecipeCopyNBT extends ShapedOreRecipe {

		public ShapedOreRecipeCopyNBT(ResourceLocation group, ItemStack result, ShapedPrimer primer) {
			super(group, result, primer);
		}

		@Override
		public ItemStack getCraftingResult(CraftingInventory var1) {
			int slot=0;
			
			for (int i=0;i<var1.getSizeInventory();i++){
				//System.out.println("Slot "+i+" :"+var1.getStackInSlot(i));
				if (!var1.getStackInSlot(i).isEmpty()){
					if (var1.getStackInSlot(i).getItem() instanceof GenericGun){
						//System.out.println("Copy from Slot:"+i);
						slot=i;
					} 
				}
			}
			ItemStack gun = var1.getStackInSlot(slot);
			
			CompoundNBT tags = ((ItemStack)var1.getStackInSlot(slot)).getTagCompound();
			CompoundNBT newTags=null;
			if(tags!=null){
				newTags = (CompoundNBT) tags.copy();
			}
			ItemStack out = super.getCraftingResult(var1);
			if(newTags!=null){
				out.setTagCompound(newTags);
			}
			return out;
		}
		
		
	}
}
