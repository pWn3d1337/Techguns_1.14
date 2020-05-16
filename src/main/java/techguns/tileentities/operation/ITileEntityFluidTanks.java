package techguns.tileentities.operation;

import net.minecraft.nbt.CompoundNBT;

public interface ITileEntityFluidTanks {
	public void saveTanksToNBT(CompoundNBT tags);
	public void loadTanksFromNBT(CompoundNBT tags);
}
