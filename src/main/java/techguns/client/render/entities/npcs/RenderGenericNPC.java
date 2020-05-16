package techguns.client.render.entities.npcs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import techguns.entities.npcs.GenericNPC;

public class RenderGenericNPC<T extends GenericNPC> extends BipedRenderer<T> {

	public RenderGenericNPC(EntityRendererManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
		super(renderManagerIn, modelBipedIn, shadowSize);
	}

}
