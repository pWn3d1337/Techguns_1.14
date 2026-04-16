package com.pWn3d1337.techguns.client.render;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.renderer.LightTexture;

public class TGRenderHelper {

    private static int lastSrcBlend = GL11.GL_ONE;
    private static int lastDstBlend = GL11.GL_ZERO;
    private static boolean depthMaskState = true;

    public enum TGRenderType {
        ALPHA, ADDITIVE, SOLID, ALPHA_SHADED, NO_Z_TEST
    }

    public static final int FULL_BRIGHT = LightTexture.packLight(15, 15);

    // Fake FX lighting (1.16.5 no longer lets you modify lightmap directly)
    public static int enableFXLighting() {
        return FULL_BRIGHT;
    }

    public static void disableFXLighting() {
        // No-op in 1.16.5 — light is handled per vertex
    }

    public static int enableFluidGlow(int luminosity) {
        int light = LightTexture.packLight(luminosity, luminosity);
        return light;
    }

    public static void disableFluidGlow() {
        // Again, cannot restore like old system — per-vertex
    }

    public static void enableBlendMode(TGRenderType type) {

        if (type != TGRenderType.SOLID) {
            RenderSystem.enableBlend();
        }

        if (type == TGRenderType.ALPHA) {
            saveBlendFunc();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        } else if (type == TGRenderType.ADDITIVE || type == TGRenderType.NO_Z_TEST) {
            saveBlendFunc();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        }

        if (type == TGRenderType.NO_Z_TEST) {
            depthMaskState = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
            RenderSystem.depthMask(false);
            RenderSystem.disableDepthTest();
        }

        // Return fullbright but can't auto-apply like old code
    }

    public static void disableBlendMode(TGRenderType type) {

        if (type != TGRenderType.SOLID) {
            RenderSystem.disableBlend();
        }

        if (type == TGRenderType.ALPHA ||
                type == TGRenderType.ADDITIVE ||
                type == TGRenderType.NO_Z_TEST) {

            RenderSystem.blendFunc(lastSrcBlend, lastDstBlend);
        }

        if (type == TGRenderType.NO_Z_TEST) {
            RenderSystem.depthMask(depthMaskState);
            RenderSystem.enableDepthTest();
        }
    }

    private static void saveBlendFunc() {
        lastSrcBlend = GL11.glGetInteger(GL11.GL_BLEND_SRC);
        lastDstBlend = GL11.glGetInteger(GL11.GL_BLEND_DST);
    }
}
