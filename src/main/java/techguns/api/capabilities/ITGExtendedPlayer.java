package techguns.api.capabilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;

/**
 * Should only be used on EntityPlayer
 * 
 */
public interface ITGExtendedPlayer extends ITGShooterValues {

	public PlayerEntity getEntity();
	
	public int getFireDelay(Hand hand);
	public void setFireDelay(Hand hand, int delay);
	public IInventory getTGInventory();
	
	public void saveToNBT(final CompoundNBT tags);
	public void loadFromNBT(final CompoundNBT tags);
}
