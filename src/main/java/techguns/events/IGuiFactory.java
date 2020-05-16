package techguns.events;

import net.minecraft.entity.player.PlayerInventory;

public interface IGuiFactory<T> {
	Object createElement(PlayerInventory player, T tile);
}
