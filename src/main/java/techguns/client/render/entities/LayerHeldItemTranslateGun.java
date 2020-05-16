package techguns.client.render.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import techguns.api.npc.INPCTechgunsShooter;

public class LayerHeldItemTranslateGun extends HeldItemLayer {

	public LayerHeldItemTranslateGun(LivingRenderer<?> livingEntityRendererIn) {
		super(livingEntityRendererIn);
	}
	
	@Override
	public void doRenderLayer(LivingEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        boolean flag = entitylivingbaseIn.getPrimaryHand() == HandSide.RIGHT;
        ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
        ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();

        if (!itemstack.isEmpty() || !itemstack1.isEmpty())
        {
            GlStateManager.pushMatrix();

            if (this.livingEntityRenderer.getMainModel().isChild)
            {
                float f = 0.5F;
                GlStateManager.translate(0.0F, 0.75F, 0.0F);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
            }

            this.renderHeldItemTranslateGun(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT);
            this.renderHeldItemTranslateGun(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT);
            GlStateManager.popMatrix();
        }
    }

    protected void renderHeldItemTranslateGun(LivingEntity ent, ItemStack stack, ItemCameraTransforms.TransformType transformType, HandSide handSide)
    {
        if (!stack.isEmpty())
        {
            GlStateManager.pushMatrix();
           
          
            
            if (ent.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            // Forge: moved this call down, fixes incorrect offset while sneaking.
            this.translateToHand(handSide);
            
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == HandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            
            this.setEntityTranslation(ent,flag);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(ent, stack, transformType, flag);
            
            GlStateManager.popMatrix();
        }
    }
    
    protected void setEntityTranslation(LivingEntity ent, boolean lefthand) {
    	if (ent instanceof INPCTechgunsShooter) {
    		INPCTechgunsShooter shooter = (INPCTechgunsShooter) ent;
    		
    		GlStateManager.translate(lefthand ? -shooter.getWeaponPosX() : shooter.getWeaponPosX(), shooter.getWeaponPosY(), shooter.getWeaponPosZ());   		
    	}
    }
}
