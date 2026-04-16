package com.pWn3d1337.techguns.tileentites;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.pWn3d1337.techguns.capabilities.TGSpawnerNPCData;
import com.pWn3d1337.techguns.entities.npcs.ITGSpawnerNPC;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.*;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;


public class TGSpawnerTileEnt extends BasicTGTileEntity implements ITickable {

	protected Random rand = new Random();
	protected int delay=200;
	protected int spawndelay = 200;
	protected int mobsLeft=5;
	protected int maxActive=3;
	
	protected int spawnHeightOffset=0;
	
	//protected static final int retrydelay = 40;
	protected double spawnrange=2d;
	
	protected ArrayList<WeightedSpawnerEntity> mobtypes = new ArrayList<>();
	
	protected LinkedList<ITGSpawnerNPC> activeMobs = new LinkedList<>();
	
	protected ItemStack weaponOverride = ItemStack.EMPTY;

	public TGSpawnerTileEnt(TileEntityType<?> tileEntityType) {
		super(tileEntityType, false);
	}

	public <T extends MobEntity & ITGSpawnerNPC> void addMobType(Class<T> clazz, int weight) {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putString("id", clazz.getCanonicalName());
		WeightedSpawnerEntity ent = new WeightedSpawnerEntity(weight, nbt);
		this.mobtypes.add(ent);
	}

	public void despawnedEntity(ITGSpawnerNPC ent) {
		this.activeMobs.remove(ent);
	}
	
	public void killedEntity(ITGSpawnerNPC ent) {
		if(this.activeMobs.remove(ent)) {
			this.mobsLeft--;
			this.markDirty();
		}
	}
	
	public void relinkNPC(ITGSpawnerNPC ent) {
		if(!this.activeMobs.contains(ent)) {
			this.activeMobs.add(ent);
		}
	}
	
	@Override
	public boolean canBeWrenchRotated() {
		return false;
	}

	@Override
	public boolean canBeWrenchDismantled() {
		return false;
	}
	
	public void setParams(int mobsleft, int maxactive, int spawndelay, int spawnrange) {
		this.mobsLeft=mobsleft;
		this.maxActive=maxactive;
		this.spawndelay=spawndelay;
		this.delay=spawndelay;
		this.spawnrange=spawnrange;
	}
	
	public TGSpawnerTileEnt setWeaponOverride(ItemStack weapon) {
		this.weaponOverride = weapon;
		return this;
	}
	
	public void setSpawnHeightOffset(int offset) {
		this.spawnHeightOffset=offset;
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putByte("mobsLeft", (byte) this.mobsLeft);
		compound.putShort("delay", (short) this.delay);
		compound.putByte("maxActive", (byte) this.maxActive);
		compound.putShort("spawnDelay", (short) this.spawndelay);
		compound.putByte("spawnRange", (byte)this.spawnrange);
		compound.putShort("spawnHeightOffset", (short) this.spawnHeightOffset);
		
        ListNBT nbttaglist = new ListNBT();
        for(WeightedSpawnerEntity type : this.mobtypes) {
			nbttaglist.add(type.toCompoundTag());
        }
        compound.put("mobtypes", nbttaglist);
		
        if(!weaponOverride.isEmpty()) {
        	CompoundNBT weapon = this.weaponOverride.write(new CompoundNBT());
        	compound.put("weapon", weapon);
        }
        
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		this.mobsLeft = compound.getByte("mobsLeft");
		this.delay = compound.getShort("delay");
		this.spawndelay = compound.getShort("spawnDelay");
		if(spawndelay<1) {
			spawndelay=200;
		}
		this.maxActive = compound.getShort("maxActive");
		if(maxActive<1) {
			maxActive=1;
		}
		this.spawnrange=compound.getByte("spawnRange");
		this.spawnHeightOffset = compound.getShort("spawnHeightOffset");
		
		if (compound.contains("mobtypes", 9)) {
			ListNBT mobTypesList = compound.getList("mobtypes", Constants.NBT.TAG_COMPOUND);
			this.mobtypes.clear();
			mobTypesList.forEach(nbt -> this.mobtypes.add(new WeightedSpawnerEntity((CompoundNBT) nbt)));
		}

		if (compound.contains("weapon", Constants.NBT.TAG_COMPOUND)) {
			this.weaponOverride = ItemStack.read(compound.getCompound("weapon"));
		}

		super.read(state, compound);
	}

	@Override
	public void writeClientDataToNBT(CompoundNBT tags) {
		super.writeClientDataToNBT(tags);
	}

	@Override
	public void readClientDataFromNBT(CompoundNBT tags) {
		super.readClientDataFromNBT(tags);
	}

	protected boolean hasMobTypes() {
		return this.mobtypes.size()>0;
	}

	@Override
	public void tick() {
		if (this.world == null || this.world.isRemote) return;
		this.delay--;

		if (this.delay <= 0 && this.activeMobs.size() < Math.min(this.maxActive, this.mobsLeft) && !this.mobtypes.isEmpty()) {
			if (this.world.getDifficulty() != Difficulty.PEACEFUL) {
				WeightedSpawnerEntity entData = WeightedRandom.getRandomItem(this.rand, this.mobtypes);

				BlockPos pos = this.getPos();
				double x = pos.getX() + (rand.nextDouble() - rand.nextDouble()) * this.spawnrange + 0.5D;
				double y = pos.getY() + 1 + this.spawnHeightOffset;
				double z = pos.getZ() + (rand.nextDouble() - rand.nextDouble()) * this.spawnrange + 0.5D;

				ServerWorld serverWorld = (ServerWorld) this.world;
				Entity entity = EntityType.loadEntityAndExecute(entData.getNbt(), serverWorld, e -> {
					e.setPosition(x, y, z);
					return e;
				});

				if (entity instanceof ITGSpawnerNPC && entity instanceof MobEntity) {
					MobEntity mobEntity = (MobEntity) entity;

					if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(mobEntity, serverWorld, (float) x, (float) y, (float) z, null, SpawnReason.SPAWNER)) {
						mobEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(new BlockPos((IPosition) entity)), SpawnReason.SPAWNER, null, null);
						serverWorld.summonEntity(entity);
						this.activeMobs.add((ITGSpawnerNPC) entity);
						this.delay = this.spawndelay;
						this.mobsLeft--;
					}
				}
			}
		}
	}

	/*
	public void debug() {
		System.out.println("Left:"+this.mobsLeft);
		System.out.println("Active:"+this.activeMobs.size());
		System.out.println("MaxActive:"+this.maxActive);
		System.out.println("Delay:"+this.delay+"/"+this.spawndelay);
		System.out.println("Types:"+this.mobtypes.size());
	}*/
	
}
