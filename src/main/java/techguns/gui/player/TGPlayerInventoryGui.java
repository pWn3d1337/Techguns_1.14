package techguns.gui.player;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import micdoodle8.mods.galacticraft.api.client.tabs.TabRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGRadiationSystem;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.damagesystem.DamageSystem;
import techguns.gui.TGBaseGui;
import techguns.gui.player.tabs.TGPlayerTab;
import techguns.items.armors.GenericArmor;
import techguns.util.TextUtil;

public class TGPlayerInventoryGui extends TGBaseGui {


		public static ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/tgplayerinventory.png");
		private final TGPlayerInventory inventory;
		private float sizex;
		private float sizey;
		private TGExtendedPlayer props;
		
		private static final int togglebuttons_xpos= -8;
		/*public GuiTGPlayerInventory(EntityPlayer player, InventoryPlayer inv, TGPlayerInventory tgplayerinv) {
			super(new TGPlayerContainer(player, inv, tgplayerinv));
			this.inventory=tgplayerinv;
			this.props = TechgunsExtendedPlayerProperties.get(player);
		}*/

		public TGPlayerInventoryGui(Container inventorySlotsIn, PlayerEntity player) {
			super(new TGPlayerInventoryContainer(player));
			this.props = TGExtendedPlayer.get(player);
			this.inventory=props.tg_inventory;
			this.tex=texture;
		}

		
		private List<String> getTooltipForToggleButton(int index){
			List<String> tooltip = new ArrayList<String>();
			
			boolean state=false;
			if (index==0){
				state = props.showTGHudElements;
				tooltip.add(trans("tgguitooltip.togglehud"));
				tooltip.add(trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off")));
				tooltip.add(trans("tgguitooltip.togglehud.tooltip1"));
				tooltip.add(trans("tgguitooltip.togglehud.tooltip2"));
			} else if (index==1){
				state = props.enableNightVision;
				tooltip.add(trans("tgguitooltip.togglenv"));
				tooltip.add(trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off")));
				tooltip.add(trans("tgguitooltip.togglenv.tooltip1"));
				tooltip.add(trans("tgguitooltip.togglenv.tooltip2"));
			} else if (index==2){
				state=props.enableSafemode;
				tooltip.add(trans("tgguitooltip.togglesafe"));
				tooltip.add(trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off")));
				tooltip.add(trans("tgguitooltip.togglesafe.tooltip1"));
				tooltip.add(trans("tgguitooltip.togglesafe.tooltip2"));
			} else if (index==3){
				state=props.enableStepAssist;
				tooltip.add(trans("tgguitooltip.togglestep"));
				tooltip.add(trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off")));
				tooltip.add(trans("tgguitooltip.togglestep.tooltip1"));
				tooltip.add(trans("tgguitooltip.togglestep.tooltip2"));
			} else if (index==4){
				state=props.enableJetpack;
				tooltip.add(trans("tgguitooltip.togglejetpack"));
				tooltip.add(trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off")));
				tooltip.add(trans("tgguitooltip.togglejetpack.tooltip1"));
				tooltip.add(trans("tgguitooltip.togglejetpack.tooltip2"));
			}
			
			return tooltip;
		}
		
		@SideOnly(Side.CLIENT)
		private List<String> getTooltipTotalArmorValues(){
			
			List<String> tooltip = new ArrayList<String>(); //NonNullList.<String>withSize(11, "");
			
			tooltip.add(ChatFormatting.UNDERLINE+trans("tgguitooltip.totalarmorvalues"));
	
			PlayerEntity ply = Minecraft.getMinecraft().player;
			tooltip.add(ChatFormatting.DARK_GRAY+trans("TGDamageType.PHYSICAL")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.PHYSICAL), DamageType.PHYSICAL));
			tooltip.add(ChatFormatting.GRAY+trans("TGDamageType.PROJECTILE")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.PROJECTILE), DamageType.PROJECTILE));
			
			tooltip.add(ChatFormatting.DARK_RED+trans("TGDamageType.EXPLOSION")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.EXPLOSION), DamageType.EXPLOSION));
			
			tooltip.add(ChatFormatting.DARK_AQUA+trans("TGDamageType.ENERGY")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.ENERGY), DamageType.ENERGY));
			
			tooltip.add(ChatFormatting.RED+trans("TGDamageType.FIRE")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.FIRE), DamageType.FIRE));
			tooltip.add(ChatFormatting.AQUA+trans("TGDamageType.ICE")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.ICE), DamageType.ICE));
			tooltip.add(ChatFormatting.YELLOW+trans("TGDamageType.LIGHTNING")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.LIGHTNING), DamageType.LIGHTNING));
			
			tooltip.add(ChatFormatting.DARK_GRAY+trans("TGDamageType.DARK")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.DARK), DamageType.DARK));
			
			
			tooltip.add(ChatFormatting.DARK_GREEN+trans("TGDamageType.POISON")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.POISON), DamageType.POISON));
			
			tooltip.add(ChatFormatting.GREEN+trans("TGDamageType.RADIATION")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.RADIATION), DamageType.RADIATION));
			
			
			return tooltip;
		}
		
		@SideOnly(Side.CLIENT)
		private List<String> getTooltipRadiationBar(){
			List<String> tooltip = new ArrayList<String>();
			
			TGExtendedPlayer props = TGExtendedPlayer.get(Minecraft.getMinecraft().player);
			
			int displayedRad =0;
			if(props!=null){
				displayedRad = props.radlevel; //(int) (props.radlevel * 0.001);			
			}
			
			tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.UNDERLINE+trans("tgguitooltip.currentrad"));
			tooltip.add(displayedRad+"/"+"1000");
			
			if(displayedRad<TGRadiationSystem.MINOR_POISONING) {
				tooltip.add(trans("tgguitooltip.radeffects")+": "+ChatFormatting.GREEN+trans("tgguitooltip.radeffects.none"));
			} else {
				tooltip.add(trans("tgguitooltip.radeffects")+":");
				
				if(displayedRad<TGRadiationSystem.SEVERE_POISONING) {
					tooltip.add(" "+TextUtil.trans("effect.hunger"));
				} else {
					tooltip.add(" "+TextUtil.trans("effect.hunger")+" "+TextUtil.trans("potion.potency.1"));
					tooltip.add(" "+TextUtil.trans("effect.digSlowDown")+" "+TextUtil.trans("potion.potency.1"));
					tooltip.add(" "+TextUtil.trans("effect.weakness")+" "+TextUtil.trans("potion.potency.1"));
					
					if(displayedRad>=(TGRadiationSystem.LETHAL_POISONING)) {
						tooltip.add(" "+TextUtil.trans("effect.confusion")+" "+TextUtil.trans("potion.potency.1"));
						tooltip.add(" "+TextUtil.trans("effect.moveSlowdown")+" "+TextUtil.trans("potion.potency.1"));
						tooltip.add(" "+trans("gun.tooltip.damage"));
					}
				}
				
			}
			return tooltip;
		}
		
		@Override
		protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
			 int mx = mouseX - (this.width-this.xSize)/2;
		     int my = mouseY - (this.height-this.ySize)/2;
		        
			 for(int i=0;i<5;i++){
		        if (this.isInRect(mx, my, togglebuttons_xpos, 7+i*11, 11, 11)){
		        	this.drawHoveringText(getTooltipForToggleButton(i), mx, my);
		        }
	        }
			 
			 //Total Armor Tooltip
			 if(this.isInRect(mx, my, 98, 7, 14, 14)){
				 this.drawHoveringText(this.getTooltipTotalArmorValues(), mx, my);
			 }
			 
			 //Radiation Bar Tooltip
			 if(this.isInRect(mx, my, 115, 7, 54, 6)){
				 this.drawHoveringText(this.getTooltipRadiationBar(), mx, my);
			 }
		}


		@Override
		protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY) {
			super.drawGuiContainerBackgroundLayer(partialTickTime, mouseX, mouseY);

			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			
			TGExtendedPlayer props = TGExtendedPlayer.get(Minecraft.getMinecraft().player);
			
			int displayedRad =0;
			if(props!=null){
				displayedRad = props.radlevel; //(int) (props.radlevel * 0.001);			
			}
			
			if(displayedRad>0) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				int width = (int) Math.ceil(displayedRad/1000.0d*52d);
				this.drawTexturedModalRect(k + 116, l + 8, 177, 0, width, 4);
			}
			
	        InventoryScreen.drawEntityOnScreen(k + 51, l + 75, 30, (float)(k + 51) - this.sizex, (float)(l + 75 - 50) - this.sizey, this.mc.player);
		}

		@Override
		public void drawScreen(int i1, int i2, float f1) {
			super.drawScreen(i1, i2, f1);
			this.sizex=(float)i1;
			this.sizey=(float)i2;
		}



		@Override
		public void initGui() {
			super.initGui();
			int index=0;
			
			/*if (!Techguns.isTConstructLoaded){
				this.buttonList.add(new TGGuiTabButton(index, this.guiLeft-26, this.guiTop+5, true, new ItemStack(Blocks.crafting_table,1),0));
			    this.buttonList.add(new TGGuiTabButton(index, this.guiLeft-26, this.guiTop+5+26, false,TGItems.newStack(TGItems.bullets9mm,1),TGConfig.GUI_ID_tgplayerInventory));
			} else {
				((TechgunsTConstructIntegrationClient)Techguns.pluginTConstructIntegration).addTabs(this.guiLeft, this.guiTop, this.buttonList);

			}*/
			//this.buttonList.add(new TGGuiTabButton(index, this.guiLeft+5, this.guiTop-26, true, new ItemStack(Blocks.CRAFTING_TABLE,1),-1));
		    //this.buttonList.add(new TGGuiTabButton(index, this.guiLeft+5+28, this.guiTop-26, false,TGItems.newStack(TGItems.PISTOL_ROUNDS,1),0));	
			
			//Galacticraft API for Tabs
			TabRegistry.updateTabValues(this.guiLeft, this.guiTop, TGPlayerTab.class);
			TabRegistry.addTabsToList(this.buttonList);
			
		    for(int x =0; x<5;x++){
		    	this.buttonList.add(new TGGuiToggleButton(++index, this.guiLeft+togglebuttons_xpos/*+173*/, this.guiTop+7+(x*11), x));
		    }
		}

		
		
}
