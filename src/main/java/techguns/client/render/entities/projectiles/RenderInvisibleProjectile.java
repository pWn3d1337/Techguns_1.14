package techguns.client.render.entities.projectiles;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import techguns.entities.projectiles.GenericProjectile;

/**
 * Renders (or actually don't render) an invisible projectile
 *
 * @param <T>
 */
public class RenderInvisibleProjectile <T extends GenericProjectile> extends EntityRenderer<T> {

	public RenderInvisibleProjectile(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return null;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		//DO NOTHING
	}

}
