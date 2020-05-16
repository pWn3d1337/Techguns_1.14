package techguns.radiation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import techguns.gui.player.TGPlayerInventoryGui;

public class RadResistancePotion extends Effect {

	public RadResistancePotion() {
		super(false, 0xff99ac2d);
	}

	@Override
	public void renderInventoryEffect(int x, int y, EffectInstance effect, Minecraft mc) {
		super.renderInventoryEffect(x, y, effect, mc);

		if(mc.currentScreen!=null) {
			mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
			mc.currentScreen.drawTexturedModalRect(x+8, y+8, 16, 168, 16, 16);
			mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
		}
	}

	@Override
	public void renderHUDEffect(int x, int y, EffectInstance effect, Minecraft mc, float alpha) {
		super.renderHUDEffect(x, y, effect, mc, alpha);
		
		mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
		
		mc.ingameGUI.drawTexturedModalRect(x+4, y+4, 16, 168, 16, 16);
		mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
	}
	
}
