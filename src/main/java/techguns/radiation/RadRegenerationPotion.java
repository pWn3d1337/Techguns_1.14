package techguns.radiation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import techguns.capabilities.TGExtendedPlayer;
import techguns.gui.player.TGPlayerInventoryGui;

public class RadRegenerationPotion extends Effect {

	public RadRegenerationPotion() {
		super(false, 0xffa2000);
	}

	@Override
	public void renderInventoryEffect(int x, int y, EffectInstance effect, Minecraft mc) {
		super.renderInventoryEffect(x, y, effect, mc);

		if(mc.currentScreen!=null) {
			mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
			mc.currentScreen.drawTexturedModalRect(x+8, y+8, 32, 168, 16, 16);
			mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
		}
	}

	@Override
	public void renderHUDEffect(int x, int y, EffectInstance effect, Minecraft mc, float alpha) {
		super.renderHUDEffect(x, y, effect, mc, alpha);
		
		mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
		
		mc.ingameGUI.drawTexturedModalRect(x+4, y+4, 32, 168, 16, 16);
		mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
	}
	
	@Override
	public void performEffect(LivingEntity elb, int amplifier) {
		
		int amount = (amplifier+1);
		
		if(elb instanceof PlayerEntity) {
			TGExtendedPlayer props = TGExtendedPlayer.get((PlayerEntity) elb);
			props.addRadiation(-amount);
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 20 == 0;
	}
}
