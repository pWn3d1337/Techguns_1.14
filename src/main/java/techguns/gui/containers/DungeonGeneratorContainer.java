package techguns.gui.containers;

import net.minecraft.entity.player.PlayerInventory;
import techguns.tileentities.DungeonGeneratorTileEnt;

public class DungeonGeneratorContainer extends OwnedTileContainer {

	DungeonGeneratorTileEnt tile;
	
	public DungeonGeneratorContainer(PlayerInventory player, DungeonGeneratorTileEnt ent) {
		super(player, ent);
		this.tile=ent;
	}

}
