package com.pWn3d1337.techguns.capabilities;

import com.pWn3d1337.techguns.entities.npcs.ITGSpawnerNPC;
import com.pWn3d1337.techguns.tileentites.TGSpawnerTileEnt;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TGSpawnerNPCData {
	
	protected BlockPos spawnerPos;
	
	public boolean hasSpawner() {
		return spawnerPos!=null;
	}

	public BlockPos getSpawnerPos() {
		return spawnerPos;
	}
	
	public void setSpawnerPos(BlockPos spawnerPos) {
		this.spawnerPos = spawnerPos;
	}

	public static TGSpawnerNPCData get(ITGSpawnerNPC npc){
		return (TGSpawnerNPCData) npc.getCapability(TGSpawnerNPCDataCapProvider.TG_GENERICNPC_DATA);
	}
	
	public void tryRelink(World w, ITGSpawnerNPC npc) {
		if(this.hasSpawner()) {
			TileEntity tile = w.getTileEntity(this.getSpawnerPos());
			if(tile!=null && tile instanceof TGSpawnerTileEnt) {
				((TGSpawnerTileEnt)tile).relinkNPC(npc);
			}
		}
	}
	
	public void writeAdditional(final CompoundNBT tags) {
		if(spawnerPos!=null) {
			tags.putBoolean("hasSpawner", true);
			tags.putInt("spawnerX", spawnerPos.getX());
			tags.putInt("spawnerY", spawnerPos.getY());
			tags.putInt("spawnerZ", spawnerPos.getZ());
		}
	}
	public void readAdditional(final CompoundNBT tags) {
		if(tags.getBoolean("hasSpawner")) {
			int x = tags.getInt("spawnerX");
			int y = tags.getInt("spawnerY");
			int z = tags.getInt("spawnerZ");
			this.spawnerPos = new BlockPos(x, y, z);
		}
	}
}
