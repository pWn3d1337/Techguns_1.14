package techguns.gui.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import techguns.tileentities.ExplosiveChargeTileEnt;

public class ExplosiveChargeContainer<T extends ExplosiveChargeTileEnt> extends OwnedTileContainer {
	protected T tile;

	public ExplosiveChargeContainer(PlayerInventory player, T tile){
		super(player, tile);
		this.tile=tile;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int slotid) {
		return null;
	};
	
	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return tile.isUseableByPlayer(player);
	}

}
