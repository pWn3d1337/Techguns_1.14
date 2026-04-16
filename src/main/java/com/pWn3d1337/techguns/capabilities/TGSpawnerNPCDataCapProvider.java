package com.pWn3d1337.techguns.capabilities;

import com.pWn3d1337.techguns.Techguns;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class TGSpawnerNPCDataCapProvider implements ICapabilitySerializable<INBT> {

	@CapabilityInject(TGSpawnerNPCData.class)
	public static final Capability<TGSpawnerNPCData> TG_GENERICNPC_DATA = null;

	public static final ResourceLocation ID = new ResourceLocation(Techguns.MODID, "genericnpcdata");

	private final TGSpawnerNPCData instance;
	private final LazyOptional<TGSpawnerNPCData> optional;

	public TGSpawnerNPCDataCapProvider(TGSpawnerNPCData caps) {
		this.instance = caps;
		this.optional = LazyOptional.of(() -> this.instance);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
		return capability == TG_GENERICNPC_DATA ? optional.cast() : LazyOptional.empty();
	}

	@Override
	public INBT serializeNBT() {
		return TG_GENERICNPC_DATA.getStorage().writeNBT(TG_GENERICNPC_DATA, this.instance, null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		TG_GENERICNPC_DATA.getStorage().readNBT(TG_GENERICNPC_DATA, this.instance, null, nbt);
	}
}

