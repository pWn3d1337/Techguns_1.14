package techguns.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderArmorItem extends RenderItemBase {

	protected ModelBiped modelBiped;
	
	public RenderArmorItem(ModelBiped model, ResourceLocation texture, EquipmentSlotType armorSlot) {
		super(null, texture);
		this.modelBiped=model;
		
		modelBiped.bipedHead.showModel = armorSlot == EquipmentSlotType.HEAD;
		modelBiped.bipedHeadwear.showModel = armorSlot == EquipmentSlotType.HEAD;

		modelBiped.bipedBody.showModel = armorSlot == EquipmentSlotType.CHEST || armorSlot == EquipmentSlotType.LEGS;
		modelBiped.bipedRightArm.showModel = armorSlot == EquipmentSlotType.CHEST;
		modelBiped.bipedLeftArm.showModel = armorSlot == EquipmentSlotType.CHEST;
		modelBiped.bipedRightLeg.showModel = armorSlot == EquipmentSlotType.LEGS || armorSlot == EquipmentSlotType.FEET;
		modelBiped.bipedLeftLeg.showModel = armorSlot == EquipmentSlotType.LEGS || armorSlot == EquipmentSlotType.FEET;
		
		this.scale_thirdp = 1.0f;
		this.scale_ego = 1.0f;
		this.scale_gui = 1.5f;
		this.scale_itemframe = 1.5f;
		
		if (armorSlot == EquipmentSlotType.HEAD) {
			this.scale_ground = 1.25f;
		} else {
			this.scale_ground = 1.5f;
		}
		
		//this.translateBase[1]=0;
		switch(armorSlot) {
		case HEAD:
			this.translateBase[1]=-0.54f;
			break;
		case CHEST:
			this.translateBase[1]= -0.9f;
			break;
		case LEGS:
			this.translateBase[1]= -1.29f;
			break;
		case FEET:
			this.translateBase[1]= -1.42f;
			break;
		default:
			break;
		}
		
		this.translateType[4][2] = 0.07f; //Item Frame, Z
		
		this.translateType[0][1] = 0.1f;
		this.translateType[1][1] = 0.04f;
		
	}

	@Override
	public void renderItem(TransformType transform, ItemStack stack, LivingEntity elb, boolean leftHanded) {
		
			GlStateManager.pushMatrix();

			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

			this.applyTranslation(transform);

			if (TransformType.FIRST_PERSON_LEFT_HAND == transform || TransformType.FIRST_PERSON_RIGHT_HAND == transform) {

			} else if (TransformType.THIRD_PERSON_LEFT_HAND == transform || TransformType.THIRD_PERSON_RIGHT_HAND == transform) {

			} else if (TransformType.GUI == transform) {
				GlStateManager.rotate(180.0f, 0, 1f, 0);
				//GlStateManager.rotate(20.0f, 1f, 0, 0);

			} else if (TransformType.GROUND == transform) {

			} else if (TransformType.FIXED == transform) {
				//GlStateManager.rotate(-90.0f, 0, 1.0f, 0);
			}

			this.setBaseScale(elb,transform);
			this.setBaseRotation(transform);
			this.applyBaseTranslation();

			modelBiped.render(elb, 0, 0, 0, 0, 0, SCALE);

			GlStateManager.popMatrix();

	}
	
}
