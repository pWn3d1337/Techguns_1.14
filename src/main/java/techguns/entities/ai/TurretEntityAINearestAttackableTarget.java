package techguns.entities.ai;

import com.google.common.base.Predicate;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.util.math.AxisAlignedBB;

public class TurretEntityAINearestAttackableTarget<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

	protected double yOffset;
	
	public TurretEntityAINearestAttackableTarget(CreatureEntity creature, Class<T> classTarget, int chance,
                                                 boolean checkSight, boolean onlyNearby, Predicate<? super T> targetSelector, double yOffset) {
		super(creature, classTarget, chance, checkSight, onlyNearby, targetSelector);
		this.yOffset=yOffset;
	}

	@Override
	protected AxisAlignedBB getTargetableArea(double targetDistance) {
		return this.taskOwner.getEntityBoundingBox().grow(targetDistance, yOffset, targetDistance);
	}
	
	
	
}