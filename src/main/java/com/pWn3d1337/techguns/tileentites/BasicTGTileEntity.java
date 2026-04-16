package com.pWn3d1337.techguns.tileentites;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;

public class BasicTGTileEntity extends TileEntity {

	/**
	 * Has this tileent rotation saved in the tile?
	 */
	protected boolean hasRotation;
	//How the tile is rotated
	public byte rotation=0;

	public BasicTGTileEntity(TileEntityType<?> tileEntityType, boolean hasRotation) {
		super(tileEntityType);
		this.hasRotation = hasRotation;
	}
	
	protected int getUseRangeSquared() {
		return 64;
	}

	public boolean isUseableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
					(double) this.pos.getZ() + 0.5D) <= getUseRangeSquared();
		}
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT tags = new CompoundNBT();
		this.writeClientDataToNBT(tags);
		return new SUpdateTileEntityPacket(pos, 1, tags);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
		this.readClientDataFromNBT(packet.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tags= super.getUpdateTag();
		this.writeClientDataToNBT(tags);
		return tags;
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		super.handleUpdateTag(state, tag);
		this.readClientDataFromNBT(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		this.writeClientDataToNBT(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		this.readClientDataFromNBT(compound);
		super.read(state, compound);
	}
	
	/**
	 * write all data the client needs too, to nbt.
	 * @param compound
	 * @return
	 */
	public void writeClientDataToNBT(CompoundNBT tags) {
		if (this.hasRotation) {
			tags.putByte("rotation", this.rotation);
		}
	}
	
	/**
	 * read all data the client needs from nbt
	 * @param compound
	 */
	public void readClientDataFromNBT(CompoundNBT tags) {
		if(this.hasRotation) {
			this.rotation =(byte) (tags.getByte("rotation") % 4);
		}
	}
	
	public boolean hasRotation() {
		return hasRotation;
	}
	
	public void rotateTile() {
		if(!this.world.isRemote && this.hasRotation) {
			this.rotation = (byte) (this.rotation+1 %4);
			this.needUpdate();
		}
	}
	
	public void rotateTile(Direction sideHit) {
		this.rotateTile();
	}
	
	public boolean canBeWrenchRotated() {
		return true;
	}
	
	public boolean canBeWrenchDismantled() {
		return true;
	}
	
	/**
	 * called serverside when this tileent should send out updated to client
	 */
	public void needUpdate() {
		if (!this.world.isRemote) {
			ChunkPos cp = new ChunkPos(this.pos);
			((ServerWorld) this.world).getChunkProvider().chunkManager
					.getTrackingPlayers(cp, false)
					.forEach(player -> player.connection.sendPacket(this.getUpdatePacket()));
		}
		this.markDirty();
	}
	
}
