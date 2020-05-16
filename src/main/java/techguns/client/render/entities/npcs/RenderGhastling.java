package techguns.client.render.entities.npcs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import techguns.client.models.npcs.ModelGhastling;
import techguns.entities.npcs.Ghastling;

public class RenderGhastling extends MobRenderer<Ghastling> {

	private static final ResourceLocation GHAST_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast.png");
	private static final ResourceLocation GHAST_SHOOTING_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");

	
	public RenderGhastling(EntityRendererManager rendermanagerIn) {
		super(rendermanagerIn, new ModelGhastling(), 0.25f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Ghastling entity) {
		 return entity.isAttacking() ? GHAST_SHOOTING_TEXTURES : GHAST_TEXTURES;
	}
}
