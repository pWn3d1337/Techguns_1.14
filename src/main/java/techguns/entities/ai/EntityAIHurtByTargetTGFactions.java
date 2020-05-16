package techguns.entities.ai;

import java.util.UUID;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import techguns.api.npc.factions.ITGNpcTeam;
import techguns.entities.npcs.NPCTurret;
import techguns.factions.TGNpcFactions;

public class EntityAIHurtByTargetTGFactions extends HurtByTargetGoal {

	public EntityAIHurtByTargetTGFactions(CreatureEntity ent, boolean callsForHelp) {
		super(ent, callsForHelp);
	}

	@Override
	protected boolean isSuitableTarget(LivingEntity elb, boolean b) {
		
		if (this.taskOwner instanceof NPCTurret && elb instanceof PlayerEntity){
			NPCTurret turret = (NPCTurret) this.taskOwner;
			if (turret.mountedTileEnt != null){
				UUID owner = turret.mountedTileEnt.getOwner();
				if (owner!=null){
					return TGNpcFactions.isHostile(owner, ((PlayerEntity)elb).getGameProfile().getId());
				}
			}
		} 
		if (this.taskOwner instanceof ITGNpcTeam && elb instanceof ITGNpcTeam){
			ITGNpcTeam owner = (ITGNpcTeam) this.taskOwner;
			ITGNpcTeam target = (ITGNpcTeam) elb;
			
			return TGNpcFactions.isHostile(owner.getTGFaction(), target.getTGFaction());
			
		}
		
		return super.isSuitableTarget(elb, b);
	}
	
}