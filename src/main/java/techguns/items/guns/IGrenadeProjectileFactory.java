package techguns.items.guns;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GrenadeProjectile;

public interface IGrenadeProjectileFactory<T extends GrenadeProjectile> {

	 public T createProjectile(World world, LivingEntity p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, float radius, double gravity,
                               float charge, int bounces);
	
	 public T createBounceProjectile(T proj, double bounceX, double bounceY, double bounceZ);
	 
	 public DamageType getDamageType();
	
}
