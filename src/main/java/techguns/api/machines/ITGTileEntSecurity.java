package techguns.api.machines;

import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;

public interface ITGTileEntSecurity {
	public void setOwner(PlayerEntity ply);
	public boolean isOwnedByPlayer(PlayerEntity ply);
	public UUID getOwner();
	
	/**
	 * 0 - everyone
	 * 1 - friends if FTButilities installed, otherwise like 0
	 * 2 - owner only
	 * @return
	 */
	public byte getSecurity();
}

