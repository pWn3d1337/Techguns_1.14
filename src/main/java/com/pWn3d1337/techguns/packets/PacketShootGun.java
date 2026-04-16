package com.pWn3d1337.techguns.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

import net.minecraftforge.fml.network.NetworkEvent;

import com.pWn3d1337.techguns.api.guns.IGenericGun;

import java.util.function.Supplier;

/**
 * Tells the server that the player who sent this message wants to shoot with his current gun
 */
public class PacketShootGun {
	private boolean isZooming;
	private boolean offHand;

	public PacketShootGun() {
	}

	public PacketShootGun(boolean isZooming, Hand hand) {
		this.isZooming = isZooming;
		this.offHand = hand == Hand.OFF_HAND;
	}

	public static void encode(PacketShootGun msg, PacketBuffer buf) {
		buf.writeBoolean(msg.isZooming);
		buf.writeBoolean(msg.offHand);
	}

	public static PacketShootGun decode(PacketBuffer buf) {
		return new PacketShootGun(buf.readBoolean(), buf.readBoolean() ? Hand.OFF_HAND : Hand.MAIN_HAND);
	}

	public static void handle(PacketShootGun msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			if (player != null) {
				Hand hand = msg.getHand();
				ItemStack stack = player.getHeldItem(hand);
				if (!stack.isEmpty() && stack.getItem() instanceof IGenericGun) {
					((IGenericGun) stack.getItem()).shootGunPrimary(stack, player.world, player, msg.isZooming, hand, null);
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}

	public Hand getHand() {
		return offHand ? Hand.OFF_HAND : Hand.MAIN_HAND;
	}
}
