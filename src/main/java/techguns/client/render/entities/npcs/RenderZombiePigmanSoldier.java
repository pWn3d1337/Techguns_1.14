package techguns.client.render.entities.npcs;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;
import techguns.client.models.npcs.ModelGenericNPC;
import techguns.entities.npcs.ZombiePigmanSoldier;

public class RenderZombiePigmanSoldier extends RenderGenericNPC<ZombiePigmanSoldier> {

	
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");

	
	public RenderZombiePigmanSoldier(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new ModelGenericNPC(), 0.5f);
		BipedArmorLayer layerbipedarmor = new BipedArmorLayer(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelGenericNPC(0.5F, true);
                this.modelArmor = new ModelGenericNPC(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
	}

	
	
	   @Override
	protected ResourceLocation getEntityTexture(ZombiePigmanSoldier entity) {
		return TEXTURE;
	}
	
}
