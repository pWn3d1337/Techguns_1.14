package techguns.tileentities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import techguns.TGConfig;

public abstract class BasicPoweredTileEnt extends BasicRedstoneTileEnt {
	
	protected EnergyStoragePlus energy;
	
	public BasicPoweredTileEnt(int inventorySize, boolean hasRotation, int maximumPower) {
		super(inventorySize, hasRotation);
		energy = new EnergyStoragePlus(maximumPower);
	}
	
	public EnergyStoragePlus getEnergyStorage() {
		return energy;
	}

	/**
	 * Drain amount power from internal storage, return if enough power left
	 * @param amount
	 * @return
	 */
	protected boolean consumePower(int amount){
		if ( TGConfig.machinesNeedNoPower){
			return true;
		}
		if (this.energy.extractEnergy(amount, true)>=amount){
			return this.energy.extractEnergy(amount, false)>=amount;
		} else {
			return false;
		}
	}
	
	@Override
	public void readClientDataFromNBT(CompoundNBT tags) {
		super.readClientDataFromNBT(tags);
		this.energy.setEnergyStored(tags.getInteger("powerStored"));
	}

	@Override
	public void writeClientDataToNBT(CompoundNBT tags) {
		super.writeClientDataToNBT(tags);
		tags.setInteger("powerStored", this.energy.getEnergyStored());
	}

	@Override
	public boolean hasCapability(Capability<?> capability, Direction facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, Direction facing) {
		return capability == CapabilityEnergy.ENERGY ? (T)energy : super.getCapability(capability, facing);
	}
}
