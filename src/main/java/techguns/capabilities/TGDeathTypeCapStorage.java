package techguns.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TGDeathTypeCapStorage implements IStorage<TGDeathTypeCap> {

	@Override
	public NBTBase writeNBT(Capability<TGDeathTypeCap> capability, TGDeathTypeCap instance, Direction side) {
		final CompoundNBT tags = new CompoundNBT();
		return tags;
	}

	@Override
	public void readNBT(Capability<TGDeathTypeCap> capability, TGDeathTypeCap instance, Direction side, NBTBase nbt) {
	}
	
}
