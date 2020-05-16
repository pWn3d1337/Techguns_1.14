package techguns.tileentities;

import static techguns.gui.ButtonConstants.BUTTON_ID_SECURITY;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import techguns.Techguns;
import techguns.capabilities.TGExtendedPlayer;
import techguns.gui.player.TGPlayerInventory;
import techguns.items.armors.ICamoChangeable;
import techguns.tileentities.operation.CamoBenchRecipes;
import techguns.tileentities.operation.CamoBenchRecipes.CamoBenchRecipe;

public class CamoBenchTileEnt extends BasicOwnedTileEnt {

	public CamoBenchTileEnt() {
		super(1, false);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(Techguns.MODID+".container.camobench", new Object[0]);
	}
	
	/**
	 * Returns current ItemStack if it has changeable camo or null
	 * @return
	 */
	public ItemStack getItem(){
		if(!this.inventory.getStackInSlot(0).isEmpty()){
			if(this.inventory.getStackInSlot(0).getItem() instanceof ICamoChangeable){
				return this.inventory.getStackInSlot(0);
			} else if (this.inventory.getStackInSlot(0).getItem() instanceof BlockItem) {
				Block b = ((BlockItem) this.inventory.getStackInSlot(0).getItem()).getBlock();
        		CamoBenchRecipe r = CamoBenchRecipes.getRecipeFor(b);
        		if(r!=null) {
        			return this.inventory.getStackInSlot(0);
        		}
			}
		}
		return ItemStack.EMPTY;
	}

	
	@Override
	public void buttonClicked(int id, PlayerEntity ply, String data) {
		if(this.isUseableByPlayer(ply)){
			
			if(id<=BUTTON_ID_SECURITY) {
				super.buttonClicked(id, ply, data);
			} else {
			
				int odd_even = BUTTON_ID_SECURITY%2;
				
				if (id<BUTTON_ID_SECURITY+3){
					ItemStack item = this.getItem();
					if(item!=null && item.getItem() instanceof ICamoChangeable){
						ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
						camoitem.switchCamo(item, id==BUTTON_ID_SECURITY+2);
						this.needUpdate();
					} else if (item.getItem() instanceof BlockItem) {
						Block b = ((BlockItem)item.getItem()).getBlock();
						CamoBenchRecipe r = CamoBenchRecipes.getRecipeFor(b);
						if(b!=null) {
							r.switchCamo(item, id==BUTTON_ID_SECURITY+2);
							this.needUpdate();
						}
					}
				} else if (id<BUTTON_ID_SECURITY+11) {
					
					int slotid = 3-(((int) (Math.ceil((id-(BUTTON_ID_SECURITY+2))*0.5)))-1);
					
					ItemStack item = ply.inventory.armorInventory.get(slotid);//this.content[slotid];
					if(!item.isEmpty() && item.getItem() instanceof ICamoChangeable){
						boolean back = id%2 == odd_even;
						
						ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
						camoitem.switchCamo(item, back);
						//this.needUpdate();
						((ServerPlayerEntity) ply).connection.sendPacket(new SSetSlotPacket(ply.openContainer.windowId, 37+(3-slotid), item));
					}
					
				} else if (id<BUTTON_ID_SECURITY+13) {
					boolean back = id %2==odd_even;
					TGExtendedPlayer props = TGExtendedPlayer.get(ply);
					if (props!=null){
						ItemStack item = props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_BACK);
						if(item!=null && item.getItem() instanceof ICamoChangeable) {
							ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
							camoitem.switchCamo(item, back);
							//TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply, props, false), (EntityPlayerMP) ply);
						}				
					}
				} else if (id<BUTTON_ID_SECURITY+15) {
					boolean back = id %2==odd_even;
					TGExtendedPlayer props = TGExtendedPlayer.get(ply);
					if (props!=null){
						ItemStack item = props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_FACE);
						if(item!=null && item.getItem() instanceof ICamoChangeable) {
							ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
							camoitem.switchCamo(item, back);
							//TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply, props, false), (EntityPlayerMP) ply);
						}				
					}
				}
			}
		}
	}

	
}
