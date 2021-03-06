package techguns.api.tginventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import techguns.items.armors.TGArmorBonus;

public interface ITGSpecialSlot {
	/**
	 * The slot type this item belongs
	 * @param item
	 * @return
	 */
	public TGSlotType getSlot(ItemStack item);

	/**
	 * Called each player tick, when worn on back/face/hand slot
	 * @param item
	 * @param player
	 */
	public default void onPlayerTick(ItemStack item, PlayerTickEvent player) {};

	/**
	 * @param type The bonus type
	 * @param stack
	 * @param consume if power should be consumed (the bonus is actively used)
	 * @param player
	 * @return
	 */
	public default float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, PlayerEntity player) {
		return 0f;
	}
}
