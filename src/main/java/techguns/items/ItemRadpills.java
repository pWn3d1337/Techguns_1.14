package techguns.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import techguns.TGRadiationSystem;
import techguns.util.TextUtil;

public class ItemRadpills extends GenericItemConsumable {
	public ItemRadpills(String name) {
		super(name, 32);
	}
	
	public ItemRadpills(String name, boolean addToItemList) {
		super(name, 32, addToItemList);
	}

	@Override
	protected void onConsumed(ItemStack stack, World worldIn, PlayerEntity player) {
		player.addPotionEffect(new EffectInstance(TGRadiationSystem.radregen_effect, 500, 1, false, false));
		player.addPotionEffect(new EffectInstance(TGRadiationSystem.radresistance_effect, 3600, 2, false, false));
		
		if(!worldIn.isRemote) {
			ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
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
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(ChatFormatting.BLUE+TextUtil.trans("techguns.radresistance")+" "+TextUtil.trans("potion.potency.2") +" (180s)");
		tooltip.add(ChatFormatting.BLUE+TextUtil.trans("techguns.radregeneration")+" "+TextUtil.trans("potion.potency.1") +" (-50)");
	}
}
