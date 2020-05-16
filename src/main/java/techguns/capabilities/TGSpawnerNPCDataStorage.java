package techguns.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TGSpawnerNPCDataStorage implements IStorage<TGSpawnerNPCData>{

	@Override
	public NBTBase writeNBT(Capability<TGSpawnerNPCData> capability, TGSpawnerNPCData instance, Direction side) {
		final CompoundNBT tags = new CompoundNBT();
		instance.saveToNBT(tags);
		return tags;
	}

	@Override
	public void readNBT(Capability<TGSpawnerNPCData> capability, TGSpawnerNPCData instance, Direction side,
			NBTBase nbt) {
		final CompoundNBT tags = (CompoundNBT) nbt;
		instance.loadFromNBT(tags);
	}

}
