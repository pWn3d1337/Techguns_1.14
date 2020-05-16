package techguns.recipes;

import com.google.gson.JsonObject;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import techguns.Techguns;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.items.guns.ammo.AmmoType;

public class MiningToolUpgradeHeadRecipeFactory implements IRecipeFactory {

	public static final String MINING_TOOL_UPGRADE_RECIPE = "miningtool_upgrade";
		
	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
		return new MiningToolUpgradeRecipe(new ResourceLocation(Techguns.MODID, MINING_TOOL_UPGRADE_RECIPE), recipe.getIngredients(), recipe.getRecipeOutput());
	}

	public static class MiningToolUpgradeRecipe extends ShapelessOreRecipe {

		public MiningToolUpgradeRecipe(ResourceLocation group, NonNullList<Ingredient> input, ItemStack result) {
			super(group, input, result);
		}

		@Override
		public ItemStack getCraftingResult(CraftingInventory var1) {
			int slot=0;
			
			int slot_other=0;
			
			for (int i=0;i<var1.getSizeInventory();i++){
				//System.out.println("Slot "+i+" :"+var1.getStackInSlot(i));
				if (!var1.getStackInSlot(i).isEmpty()){
					if (var1.getStackInSlot(i).getItem()instanceof GenericGunMeleeCharge){
						//System.out.println("Copy from Slot:"+i);
						slot=i;
					} else {
						slot_other=i;
					}
				}
			}
			ItemStack gun = var1.getStackInSlot(slot);
			ItemStack head = var1.getStackInSlot(slot_other);
			
			CompoundNBT tags = ((ItemStack)var1.getStackInSlot(slot)).getTagCompound();
			CompoundNBT newTags=null;
			if(tags!=null){
				newTags = (CompoundNBT) tags.copy();
				
				GenericGunMeleeCharge g = (GenericGunMeleeCharge) gun.getItem();
				AmmoType type = g.getAmmoType();
				
				newTags.setInteger("miningHead", g.getMiningHeadLevelForHead(head));
			}
			ItemStack out = super.getCraftingResult(var1);
			if(newTags!=null){
				out.setTagCompound(newTags);
			}
			
			return out;
		}
		
		
		
		
	}

}
