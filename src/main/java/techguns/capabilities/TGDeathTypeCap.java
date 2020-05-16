package techguns.capabilities;

import net.minecraft.entity.LivingEntity;
import techguns.deatheffects.EntityDeathUtils.DeathType;

public class TGDeathTypeCap {

	protected LivingEntity ent;
	protected DeathType deathType = DeathType.DEFAULT; 
	
	public TGDeathTypeCap(LivingEntity ent) {
		this.ent = ent;
	}

	public LivingEntity getEnt() {
		return ent;
	}

	public DeathType getDeathType() {
		return deathType;
	}

	public void setDeathType(DeathType deathType) {
		this.deathType = deathType;
	}

	public static TGDeathTypeCap get(LivingEntity elb){
		return (TGDeathTypeCap) elb.getCapability(TGDeathTypeCapProvider.TG_DEATHTYPE_CAP, null);
	}
	
}
