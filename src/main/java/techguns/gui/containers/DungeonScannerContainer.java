package techguns.gui.containers;

import net.minecraft.entity.player.PlayerInventory;
import techguns.tileentities.DungeonScannerTileEnt;

public class DungeonScannerContainer extends OwnedTileContainer{

	DungeonScannerTileEnt tile;
	
	public DungeonScannerContainer(PlayerInventory player, DungeonScannerTileEnt ent) {
		super(player, ent);
		this.tile=ent;
	}

}
