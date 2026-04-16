package com.pWn3d1337.techguns.api.guns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IGenericGun {

	public boolean isShootWithLeftClick();

	public boolean isSemiAuto();

	@OnlyIn(Dist.CLIENT)
	public boolean isZooming();

	public void shootGunPrimary(ItemStack stack, World world, PlayerEntity player, boolean zooming, Hand hand, Entity target);

	public int getAmmoLeft(ItemStack stack);
	
	public GunHandType getGunHandType();

	public boolean isHoldZoom();

	public float getZoomMult();

	public default boolean canCharge() {return false;};

	public ResourceLocation getCurrentTexture(ItemStack stack);
}
