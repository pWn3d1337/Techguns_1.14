package techguns.items.additionalslots;

import java.util.List;
import java.util.UUID;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import techguns.api.radiation.TGRadiation;
import techguns.api.tginventory.TGSlotType;

public class ItemGasMask extends ItemTGSpecialSlot {

	public static final UUID UUID_RAD_RESIST_FACE = UUID.fromString("1B428AAE-2ECA-42C6-A14F-0A8B3F507276");
	
	protected float radresist=0f;
	
	public ItemGasMask(String unlocalizedName,  int camoCount,int dur) {
		super(unlocalizedName, TGSlotType.FACESLOT, camoCount,dur);
	}
	
	public ItemGasMask setRadresist(float radresist) {
		this.radresist=radresist;
		return this;
	}
	
	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event) {
		EffectInstance poison=event.player.getActivePotionEffect(Effects.POISON);
		EffectInstance wither=event.player.getActivePotionEffect(Effects.WITHER);
		
		if (poison !=null && item.getItemDamage()<item.getMaxDamage()){
			event.player.removePotionEffect(Effects.POISON);
			item.setItemDamage(item.getItemDamage()+1);
		}
		if (wither !=null && item.getItemDamage()<item.getMaxDamage()){
			event.player.removePotionEffect(Effects.WITHER);
			item.setItemDamage(item.getItemDamage()+1);
		}
		
		if(radresist!=0f) {
			IAttributeInstance attributeRadresistance = event.player.getAttributeMap().getAttributeInstance(TGRadiation.RADIATION_RESISTANCE);
			attributeRadresistance.applyModifier(new AttributeModifier(UUID_RAD_RESIST_FACE, "techguns.radresist.faceslot", radresist, 0));
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(this.radresist>0) {
			tooltip.add(ChatFormatting.BLUE+"RAD Resistance: "+this.radresist);
		}
	}
	
	
}
