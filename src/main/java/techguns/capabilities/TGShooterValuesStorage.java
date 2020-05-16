package techguns.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import techguns.api.capabilities.ITGShooterValues;

public class TGShooterValuesStorage implements IStorage<ITGShooterValues> {

	@Override
	public NBTBase writeNBT(Capability<ITGShooterValues> capability, ITGShooterValues instance, Direction side) {
		final CompoundNBT tags = new CompoundNBT();
		return tags;
	}

	@Override
	public void readNBT(Capability<ITGShooterValues> capability, ITGShooterValues instance, Direction side, NBTBase nbt) {
	}

}
