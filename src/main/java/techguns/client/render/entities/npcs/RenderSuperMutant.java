package techguns.client.render.entities.npcs;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.npcs.ModelSuperMutant;
import techguns.client.render.entities.LayerHeldItemTranslateGun;
import techguns.entities.npcs.SuperMutantBasic;

public class RenderSuperMutant extends RenderGenericNPC<SuperMutantBasic> {
	
	private static final ResourceLocation[] textures = {
			new ResourceLocation(Techguns.MODID, "textures/entity/supermutant_texture_1.png"),
			new ResourceLocation(Techguns.MODID, "textures/entity/supermutant_texture_2.png"),
			new ResourceLocation(Techguns.MODID, "textures/entity/supermutant_texture_3.png")
	};

	
	public RenderSuperMutant(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new ModelSuperMutant(), 0.5f);
		
		this.layerRenderers.remove(this.layerRenderers.size()-1);
		this.addLayer(new LayerHeldItemTranslateGun(this));
		
		BipedArmorLayer layerbipedarmor = new BipedArmorLayer(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelSuperMutant();
                this.modelArmor = new ModelSuperMutant();
            }
        };
        this.addLayer(layerbipedarmor);
	}
	
	@Override
	public void doRender(SuperMutantBasic entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y+entity.getModelHeightOffset(), z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(SuperMutantBasic entity) {
		return textures[entity.gettype()];
	}

}
