package techguns.client.particle;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import techguns.capabilities.TGExtendedPlayerClient;
import techguns.capabilities.TGShooterValues;

public class TGParticleSystemItemAttached extends TGParticleSystem {

	protected Hand hand;
	
	protected LivingEntity elb;
	protected PlayerEntity ply;
	
	public TGParticleSystemItemAttached(Entity entity, Hand hand, TGParticleSystemType type) {
		super(entity, type);
		this.hand=hand;
		if(this.entity instanceof PlayerEntity) {
			this.ply = (PlayerEntity) this.entity;
			this.elb=null;
		} else {
			this.elb= (LivingEntity) this.entity;
			this.ply=null;
		}
		this.itemAttached=true;
		float s = 1.0f; // + (Keybinds.X*10f);
		this.posX= type.offset.x; //* s + Keybinds.X;
		this.posY= type.offset.y; //* s + Keybinds.Y;
		this.posZ= type.offset.z; // * s + Keybinds.Z;
		this.scale=s;
	}

	@Override
	protected void addEffect(ITGParticle s) {
		List<ITGParticle> list = new ArrayList<>();
		s.setItemAttached();
		list.add(s);
		if(this.ply!=null) {
			TGExtendedPlayerClient props = TGExtendedPlayerClient.get(this.ply);
			props.addEffectHand(hand, list);
		} else if (this.elb!=null) {
			TGShooterValues props = TGShooterValues.get(this.elb);
			props.addEffectHand(hand, list);
		}
	}
	
	
}
