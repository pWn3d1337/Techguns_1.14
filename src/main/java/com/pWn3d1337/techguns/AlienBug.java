package com.pWn3d1337.techguns;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.Capability;

import com.pWn3d1337.techguns.TGSounds;
import com.pWn3d1337.techguns.api.npc.INpcTGDamageSystem;
import com.pWn3d1337.techguns.capabilities.TGSpawnerNPCData;
import com.pWn3d1337.techguns.entities.npcs.ITGSpawnerNPC;
import com.pWn3d1337.techguns.TGPackets;
import com.pWn3d1337.techguns.api.npc.factions.ITGNpcTeam;
import com.pWn3d1337.techguns.api.npc.factions.TGNpcFaction;
import com.pWn3d1337.techguns.client.audio.TGSoundCategory;
import com.pWn3d1337.techguns.api.damagesystem.TGDamageSource;
import com.pWn3d1337.techguns.packets.PacketPlaySound;

public class AlienBug extends SpiderEntity implements ITGNpcTeam, INpcTGDamageSystem, ITGSpawnerNPC {

	 protected int attackTimer;
	
	 protected static final ResourceLocation LOOT = null; //TODO //new ResourceLocation(Techguns.MODID, "entities/alienbug");
	 
	 protected boolean tryLink=true;

    public AlienBug(final EntityType<? extends AlienBug> type, final World worldIn) {
        super(type, worldIn);
    }
	@Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LOOT;
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {
        return MobEntity.registerAttributes()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D);
    }
	
//	public AlienBug(World w) {
//		super(w);
//		this.setSize(1.1F, 1.2F);
//	}

	 /**
     * Get this Entity's EnumCreatureAttribute
     */
	@Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }
	
	 @Override
	protected SoundEvent getAmbientSound() {
		return TGSounds.ALIENBUG_IDLE;
	}


	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return TGSounds.ALIENBUG_HURT;
	}

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(TGSounds.ALIENBUG_STEP, 0.15F, 1.0F);
    }

	@Override
	protected SoundEvent getDeathSound() {
		return TGSounds.ALIENBUG_DEATH;
	}


	@Override
	protected boolean isValidLightLevel() {
		return true;
	}


	@SideOnly(Side.CLIENT)
   public int getAttackTimer()
   {
       return this.attackTimer;
   }
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.attackTimer > 0)
       {
           --this.attackTimer;
       }
	}

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2,  new AlienBug.AIAlienBugTarget(this, PlayerEntity.class));
        this.goalSelector.addGoal(3,  new AlienBug.AIAlienBugTarget(this, IronGolemEntity.class));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new AlienBug.AISpiderAttack(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this,0.8));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }
	static class AIAlienBugTarget<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		public AIAlienBugTarget(AlienBug bug, Class<T> classTarget) {
			super(bug, classTarget, true);
		}

		@Override
		public void startExecuting() {
			super.startExecuting();
            this.playSound(TGSounds.ALIENBUG_AGGRO, 1.0F,
                    1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}

	}

	static class AISpiderAttack extends MeleeAttackGoal {
		public AISpiderAttack(SpiderEntity spider) {
			super(spider, 1.0D, true);
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			float f = this.attacker.getBrightness();

			if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
				this.attacker.setAttackTarget((LivingEntity) null);
				return false;
			} else {
				return super.shouldContinueExecuting();
			}
		}

		protected double getAttackReachSqr(LivingEntity attackTarget) {
			return (double) (4.0F + attackTarget.getWidth());
		}
	}
	 
	@Override
	public boolean attackEntityAsMob(Entity ent) {
	   boolean b =super.attackEntityAsMob(ent);
       this.attackTimer = 10;

       if(!this.world.isRemote) {
    	   TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.ALIENBUG_BITE, this, 2.0f, 1.0f,false,false, TGSoundCategory.HOSTILE), TGPackets.targetPointAroundEnt(this, 24.0f));
       }

       this.world.setEntityState(this, (byte)4);
          
       return b;
	}

	@Override
	public TGNpcFaction getTGFaction() {
		return TGNpcFaction.HOSTILE;
	}

	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 4) {
			this.attackTimer = 10;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	@Override
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		switch( dmgsrc.damageType){
			case ENERGY:
			case EXPLOSION:
			case ICE:
			case LIGHTNING:
				return 5.0f;
			case PHYSICAL:
				return 10.0f;
			case POISON:
				return 20.0f;
			case PROJECTILE:
				return 10.0f;
			case RADIATION:
				return 20.0f;
			case FIRE:
			case UNRESISTABLE:
			default:
				return 0;
		}
	}

	@Override
	public float getPenetrationResistance(TGDamageSource dmgsrc) {
		return 0.0f;
	}

	@Override
	public boolean getTryLink() {
		return this.tryLink;
	}

	@Override
	public void setTryLink(boolean value) {
		this.tryLink=value;
	}

	@Override
	public TGSpawnerNPCData getCapability(Capability<TGSpawnerNPCData> tgGenericnpcData) {
		return this.getCapability(tgGenericnpcData, null);
	}
	
	@Override
	protected void despawnEntity() {
		super.despawnEntity();
		this.despawnEntitySpawner(world, dead);
	}

	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		this.onDeathSpawner(world, dead);
	}
}