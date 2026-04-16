package com.pWn3d1337.techguns.api.render;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

/**
 * <3 <3 <3  1.7.10  <3 <3 <3
 */
public interface IItemRenderer {

	public void renderItem(@Nonnull ItemCameraTransforms.TransformType transform, @Nonnull ItemStack stack, @Nullable LivingEntity elb, boolean leftHanded);
	
}
