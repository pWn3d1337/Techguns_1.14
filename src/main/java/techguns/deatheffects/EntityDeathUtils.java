package techguns.deatheffects;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.MuleEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import techguns.entities.npcs.AlienBug;
import techguns.entities.npcs.ArmySoldier;
import techguns.entities.npcs.Bandit;
import techguns.entities.npcs.Commando;
import techguns.entities.npcs.CyberDemon;
import techguns.entities.npcs.DictatorDave;
import techguns.entities.npcs.Ghastling;
import techguns.entities.npcs.Outcast;
import techguns.entities.npcs.PsychoSteve;
import techguns.entities.npcs.SkeletonSoldier;
import techguns.entities.npcs.StormTrooper;
import techguns.entities.npcs.SuperMutantBasic;
import techguns.entities.npcs.SuperMutantElite;
import techguns.entities.npcs.SuperMutantHeavy;
import techguns.entities.npcs.ZombieFarmer;
import techguns.entities.npcs.ZombieMiner;
import techguns.entities.npcs.ZombiePigmanSoldier;
import techguns.entities.npcs.ZombieSoldier;

/**
 * Server and client side, needed by server to know to send out packets
 *
 */
public class EntityDeathUtils {
	
	public static HashMap<DeathType, HashSet<Class<? extends LivingEntity>>> entityDeathTypes;

	public static HashSet<Class<? extends LivingEntity>> goreMap = new HashSet<>();
	static {
		entityDeathTypes = new HashMap<>();
		//Gore
		
		goreMap.add(PlayerEntity.class);
		goreMap.add(ZombieEntity.class);
		goreMap.add(SkeletonEntity.class);
		goreMap.add(EndermanEntity.class);
		goreMap.add(CreeperEntity.class);
		goreMap.add(CowEntity.class);
		goreMap.add(SheepEntity.class);
		goreMap.add(PigEntity.class);
		goreMap.add(ChickenEntity.class);
		goreMap.add(ZombiePigmanEntity.class);
		goreMap.add(ZombieSoldier.class);
		goreMap.add(ArmySoldier.class);
		goreMap.add(CyberDemon.class);
		goreMap.add(ZombiePigmanSoldier.class);
		goreMap.add(SpiderEntity.class);
		goreMap.add(CaveSpiderEntity.class);
		goreMap.add(WitchEntity.class);
		goreMap.add(SlimeEntity.class);
		goreMap.add(ZombieFarmer.class);
		goreMap.add(ZombieMiner.class);
		goreMap.add(Bandit.class);
		goreMap.add(ZombieSoldier.class);
		goreMap.add(HorseEntity.class);
		goreMap.add(MooshroomEntity.class);
		goreMap.add(WolfEntity.class);
		goreMap.add(SquidEntity.class);
		//goreMap.add(EntityGhast.class);
		goreMap.add(VillagerEntity.class);
		goreMap.add(PsychoSteve.class);
		goreMap.add(DictatorDave.class);
		goreMap.add(SkeletonSoldier.class);
		goreMap.add(AlienBug.class);
		goreMap.add(StormTrooper.class);
		goreMap.add(SuperMutantElite.class);
		goreMap.add(SuperMutantHeavy.class);
		goreMap.add(SuperMutantBasic.class);
		goreMap.add(Outcast.class);
		goreMap.add(Commando.class);
		goreMap.add(Ghastling.class);
		
		goreMap.add(LlamaEntity.class);
		goreMap.add(EvokerEntity.class);
		goreMap.add(HuskEntity.class);
		goreMap.add(PolarBearEntity.class);
		goreMap.add(MagmaCubeEntity.class);
		goreMap.add(ParrotEntity.class);
		goreMap.add(RabbitEntity.class);
		goreMap.add(StrayEntity.class);
		goreMap.add(SilverfishEntity.class);
		goreMap.add(VindicatorEntity.class);
		goreMap.add(VexEntity.class);
		goreMap.add(ShulkerEntity.class);
		goreMap.add(WitherSkeletonEntity.class);
		goreMap.add(GhastEntity.class);
		goreMap.add(ZombieVillagerEntity.class);
		goreMap.add(HorseEntity.class);
		goreMap.add(DonkeyEntity.class);
		goreMap.add(MuleEntity.class);
		
		
		entityDeathTypes.put(DeathType.GORE, goreMap);
	}
	
	/**
	 * Add an entity to the gore death type list
	 * @param clazz
	 */
	public static void addEntityToDeathEffectList(Class<? extends LivingEntity> clazz) {
		entityDeathTypes.get(DeathType.GORE).add(clazz);
	}
	
	public static boolean hasSpecialDeathAnim(LivingEntity entityLiving, DeathType deathtype) {

		//TEST CODE:
		if (deathtype == DeathType.BIO || deathtype == DeathType.LASER) return true;
		
		//GenericGore
		if (entityDeathTypes.get(DeathType.GORE).contains(entityLiving.getClass())){
			return true;
		}
		
		return false;
		

	}
	
    public enum DeathType {
    	DEFAULT(0), GORE(1), BIO(2), LASER(3), DISMEMBER(4);
    	
    	int value;
    	
    	private DeathType(int value) {
    		this.value = value;
    	}
    	
    	public int getValue() {
    		return value;
    	}
    }
}
