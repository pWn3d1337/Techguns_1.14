package com.pWn3d1337.techguns;

import com.pWn3d1337.techguns.packets.PacketShootGun;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor.TargetPoint;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Class for dealing with packets
*
*/
public class TGPackets {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel network = NetworkRegistry.newSimpleChannel(
			new ResourceLocation("techguns", "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	private static int packetId = 0;

	private static int nextId() {
		return packetId++;
	}

	public static ServerPlayerEntity getPlayerFromContext(Supplier<NetworkEvent.Context> ctx) {
		return ctx.get().getSender(); // null if packet came from client
	}

	public static TargetPoint targetPointAroundBlockPos(ServerWorld world, BlockPos pos, double distance) {
		return new TargetPoint(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, distance, world.getDimensionKey());
	}

	public static TargetPoint targetPointAroundEnt(Entity ent, double distance) {
		return new TargetPoint(ent.getPosX(), ent.getPosY(), ent.getPosZ(), distance, ent.world.getDimensionKey());
	}

	public static TargetPoint targetPointAroundEnt(TileEntity ent, double distance){
		return new TargetPoint(ent.getPos().getX(), ent.getPos().getY(), ent.getPos().getZ(), distance, ent.getWorld().getDimensionKey());
	}

	public static void init() {

		network.registerMessage(nextId(), PacketShootGun.class,
				PacketShootGun::encode,
				PacketShootGun::decode,
				PacketShootGun::handle,
				Optional.of(NetworkDirection.PLAY_TO_SERVER));

		/*network.registerMessage(PacketShootGun.Handler.class, PacketShootGun.class, packetid++, Side.SERVER);
		network.registerMessage(PacketShootGunTarget.Handler.class, PacketShootGunTarget.class, packetid++, Side.SERVER);
		network.registerMessage(GunFiredMessage.Handler.class, GunFiredMessage.class, packetid++, Side.CLIENT);
		network.registerMessage(ReloadStartedMessage.Handler.class, ReloadStartedMessage.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketPlaySound.Handler.class, PacketPlaySound.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketSpawnParticle.Handler.class, PacketSpawnParticle.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketSpawnParticleOnEntity.Handler.class, PacketSpawnParticleOnEntity.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketSwapWeapon.Handler.class, PacketSwapWeapon.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketEntityDeathType.Handler.class, PacketEntityDeathType.class,  packetid++, Side.CLIENT);
		network.registerMessage(PacketOpenPlayerGUI.Handler.class, PacketOpenPlayerGUI.class,  packetid++, Side.SERVER);
		network.registerMessage(PacketTGKeybindPress.Handler.class, PacketTGKeybindPress.class, packetid++, Side.SERVER);
		network.registerMessage(PacketTGExtendedPlayerSync.Handler.class, PacketTGExtendedPlayerSync.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketTGPlayerFieldSync.Handler.class, PacketTGPlayerFieldSync.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketRequestTGPlayerSync.Handler.class, PacketRequestTGPlayerSync.class, packetid++, Side.SERVER);
		network.registerMessage(PacketGuiButtonClick.Handler.class, PacketGuiButtonClick.class, packetid++, Side.SERVER);
		network.registerMessage(PacketUpdateTileEntTanks.Handler.class, PacketUpdateTileEntTanks.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketRequestTileEntitySync.Handler.class, PacketRequestTileEntitySync.class, packetid++, Side.SERVER);
		network.registerMessage(PacketMultiBlockFormInvalidBlockMessage.Handler.class, PacketMultiBlockFormInvalidBlockMessage.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketShowKeybindConfirmMessage.Handler.class, PacketShowKeybindConfirmMessage.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketDoorStateChange.Handler.class, PacketDoorStateChange.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketNotifyAmbientEffectHandler.class, PacketNotifyAmbientEffectChange.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketGunImpactFX.Handler.class, PacketGunImpactFX.class, packetid++, Side.CLIENT);*/

	}
}