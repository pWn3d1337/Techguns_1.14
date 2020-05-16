package techguns.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import techguns.api.capabilities.ITGExtendedPlayer;

public class TGExtendedPlayerStorage implements IStorage<ITGExtendedPlayer> {

	@Override
	public NBTBase writeNBT(Capability<ITGExtendedPlayer> capability, ITGExtendedPlayer instance, Direction side) {
		final CompoundNBT tags = new CompoundNBT();
		instance.saveToNBT(tags);
		return tags;
	}

	@Override
	public void readNBT(Capability<ITGExtendedPlayer> capability, ITGExtendedPlayer instance, Direction side, NBTBase nbt) {
		final CompoundNBT tags = (CompoundNBT) nbt;
		instance.loadFromNBT(tags);
	}

}
