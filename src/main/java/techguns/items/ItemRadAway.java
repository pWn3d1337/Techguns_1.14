package techguns.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.UseAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.TGRadiationSystem;
import techguns.util.TextUtil;

public class ItemRadAway extends GenericItemConsumable {

	public ItemRadAway(String name) {
		super(name, 32);
	}
	
	public ItemRadAway(String name, boolean addToItemList) {
		super(name, 32, addToItemList);
	}

	@Override
	protected void onConsumed(ItemStack stack, World worldIn, PlayerEntity player) {
		player.addPotionEffect(new EffectInstance(TGRadiationSystem.radregen_effect, 400, 14, false, false));
		
		if(!worldIn.isRemote) {
			ItemStack bottle = TGItems.newStack(TGItems.INFUSION_BAG, 1);
			if(!player.addItemStackToInventory(bottle)) {
				 worldIn.spawnEntity(new ItemEntity(worldIn, player.posX, player.posY, player.posZ, bottle));
			}
		}
	}

	@Override
	protected SoundEvent getConsumedSound() {
		return SoundEvents.ENTITY_PLAYER_BREATH;
	}

	@Override
	public UseAction getItemUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(ChatFormatting.BLUE+TextUtil.trans("techguns.radregeneration")+" "+TextUtil.trans("potion.potency.9") +" (-300)");
	}
	
}
