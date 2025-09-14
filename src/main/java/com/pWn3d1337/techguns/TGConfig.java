package com.pWn3d1337.techguns;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class TGConfig {

	// COMMON CONFIG VALUES
	public static ForgeConfigSpec.BooleanValue debug;
	public static ForgeConfigSpec.BooleanValue addCopperIngots;
	public static ForgeConfigSpec.BooleanValue addLeadNuggets;
	public static ForgeConfigSpec.BooleanValue addCopperNuggets;
	public static ForgeConfigSpec.BooleanValue addBronzeIngots;
	public static ForgeConfigSpec.BooleanValue addTinIngots;
	public static ForgeConfigSpec.BooleanValue addLeadIngots;
	public static ForgeConfigSpec.BooleanValue addSteelIngots;
	public static ForgeConfigSpec.BooleanValue addSteelNuggets;
	public static ForgeConfigSpec.BooleanValue keepLavaRecipesWhenFuelIsPresent;
	public static ForgeConfigSpec.BooleanValue disableAutofeeder;
	public static ForgeConfigSpec.BooleanValue machinesNeedNoPower;
	public static ForgeConfigSpec.BooleanValue doOreGenCopper;
	public static ForgeConfigSpec.BooleanValue doOreGenTin;
	public static ForgeConfigSpec.BooleanValue doOreGenLead;
	public static ForgeConfigSpec.BooleanValue doOreGenUranium;
	public static ForgeConfigSpec.BooleanValue doOreGenTitanium;
	public static ForgeConfigSpec.BooleanValue doWorldspawn;
	public static ForgeConfigSpec.BooleanValue limitUnsafeModeToOP;
	public static ForgeConfigSpec.BooleanValue WIP_disableRadiationSystem;
	public static ForgeConfigSpec.BooleanValue spawnOreClusterStructures;

	public static ForgeConfigSpec.IntValue distanceSpawnLevel0;
	public static ForgeConfigSpec.IntValue distanceSpawnLevel1;
	public static ForgeConfigSpec.IntValue distanceSpawnLevel2;

	public static ForgeConfigSpec.IntValue spawnWeightZombieSoldier;
	public static ForgeConfigSpec.IntValue spawnWeightZombieFarmer;
	public static ForgeConfigSpec.IntValue spawnWeightZombieMiner;
	public static ForgeConfigSpec.IntValue spawnWeightZombiePigmanSoldier;
	public static ForgeConfigSpec.IntValue spawnWeightCyberDemon;
	public static ForgeConfigSpec.IntValue spawnWeightSkeletonSoldier;
	public static ForgeConfigSpec.IntValue spawnWeightBandit;
	public static ForgeConfigSpec.IntValue spawnWeightPsychoSteve;
	public static ForgeConfigSpec.IntValue spawnWeightTGOverworld;
	public static ForgeConfigSpec.IntValue spawnWeightTGNether;

	public static ForgeConfigSpec.IntValue spawnWeightTGStructureSmall;
	public static ForgeConfigSpec.IntValue spawnWeightTGStructureMedium;
	public static ForgeConfigSpec.IntValue spawnWeightTGStructureBig;

	public static ForgeConfigSpec.IntValue cl_sortPassesPerTick;
	public static ForgeConfigSpec.IntValue upgrade_xp_cost;
	public static ForgeConfigSpec.IntValue mininglevel_coal;
	public static ForgeConfigSpec.IntValue mininglevel_common_metal;
	public static ForgeConfigSpec.IntValue mininglevel_rare_metal;
	public static ForgeConfigSpec.IntValue mininglevel_shiny_metal;
	public static ForgeConfigSpec.IntValue mininglevel_uranium;
	public static ForgeConfigSpec.IntValue mininglevel_common_gem;
	public static ForgeConfigSpec.IntValue mininglevel_shiny_gem;
	public static ForgeConfigSpec.IntValue mininglevel_nether_crystal;
	public static ForgeConfigSpec.IntValue mininglevel_oil;

	public static ForgeConfigSpec.DoubleValue damagePvP;
	public static ForgeConfigSpec.DoubleValue damageTurretToPlayer;
	public static ForgeConfigSpec.DoubleValue damageFactorNPC;

	public static ForgeConfigSpec.DoubleValue oremult_coal;
	public static ForgeConfigSpec.DoubleValue oremult_common_metal;
	public static ForgeConfigSpec.DoubleValue oremult_rare_metal;
	public static ForgeConfigSpec.DoubleValue oremult_shiny_metal;
	public static ForgeConfigSpec.DoubleValue oremult_uranium;
	public static ForgeConfigSpec.DoubleValue oremult_common_gem;
	public static ForgeConfigSpec.DoubleValue oremult_shiny_gem;
	public static ForgeConfigSpec.DoubleValue oremult_nether_crystal;
	public static ForgeConfigSpec.DoubleValue oremult_oil;

	public static ForgeConfigSpec.DoubleValue powermult_coal;
	public static ForgeConfigSpec.DoubleValue powermult_common_metal;
	public static ForgeConfigSpec.DoubleValue powermult_rare_metal;
	public static ForgeConfigSpec.DoubleValue powermult_shiny_metal;
	public static ForgeConfigSpec.DoubleValue powermult_uranium;
	public static ForgeConfigSpec.DoubleValue powermult_common_gem;
	public static ForgeConfigSpec.DoubleValue powermult_shiny_gem;
	public static ForgeConfigSpec.DoubleValue powermult_nether_crystal;
	public static ForgeConfigSpec.DoubleValue powermult_oil;

	public static ForgeConfigSpec.DoubleValue oreDrillMultiplierOres;
	public static ForgeConfigSpec.DoubleValue oreDrillMultiplierPower;
	public static ForgeConfigSpec.IntValue oreDrillMultiplierFuel;
	public static ForgeConfigSpec.IntValue oreDrillFuelValueFuel;

	public static ForgeConfigSpec.DoubleValue explosiveChargeMaxBlockHardness;
	public static ForgeConfigSpec.DoubleValue explosiveChargeAdvancedMaxBlockHardness;

	public static ForgeConfigSpec.ConfigValue<String[]> fluidListOil;
	public static ForgeConfigSpec.ConfigValue<String[]> fluidListOilWorldspawn;
	public static ForgeConfigSpec.ConfigValue<String[]> fluidListFuel;
	public static ForgeConfigSpec.ConfigValue<String[]> biomeBlacklist;

	// CLIENT CONFIG VALUES
	public static ForgeConfigSpec.BooleanValue cl_lockSpeedFov;
	public static ForgeConfigSpec.DoubleValue cl_fixedSprintFov;
	public static ForgeConfigSpec.BooleanValue cl_enableDeathFX;
	public static ForgeConfigSpec.BooleanValue cl_enableDeathFX_Gore;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final ForgeConfigSpec CLIENT_SPEC;

	static {
		ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
		ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();

		setupCommon(commonBuilder);
		setupClient(clientBuilder);

		COMMON_SPEC = commonBuilder.build();
		CLIENT_SPEC = clientBuilder.build();
	}

	private static void setupCommon(ForgeConfigSpec.Builder builder) {
		builder.push("General");
		debug = builder.define("debug", false);
		limitUnsafeModeToOP = builder.define("limitUnsafeModeToOP", false);
		disableAutofeeder = builder.define("disableAutofeeder", false);
		machinesNeedNoPower = builder.define("machinesNeedNoPower", false);
		keepLavaRecipesWhenFuelIsPresent = builder.define("keepLavaRecipesWhenFuelIsPresent", false);
		upgrade_xp_cost = builder.defineInRange("upgradeXPCost", 20, 0, 10000);
		builder.pop();

		builder.push("Items");
		addCopperIngots = builder.define("addCopperIngots", true);
		addCopperNuggets = builder.define("addCopperNuggets", true);
		addTinIngots = builder.define("addTinIngots", true);
		addBronzeIngots = builder.define("addBronzeIngots", true);
		addLeadIngots = builder.define("addLeadIngots", true);
		addLeadNuggets = builder.define("addLeadNuggets", true);
		addSteelIngots = builder.define("addSteelIngots", true);
		addSteelNuggets = builder.define("addSteelNuggets", true);
		builder.pop();

		builder.push("Worldgen");
		doOreGenCopper = builder.define("doOreGenCopper", true);
		doOreGenTin = builder.define("doOreGenTin", true);
		doOreGenLead = builder.define("doOreGenLead", true);
		doOreGenTitanium = builder.define("doOreGenTitanium", true);
		doOreGenUranium = builder.define("doOreGenUranium", true);
		doWorldspawn = builder.define("doWorldspawn", true);
		spawnOreClusterStructures = builder.define("spawnOreClusterStructures", true);
		spawnWeightTGStructureSmall = builder.defineInRange("spawnWeightTGStructureSmall", 16, 1, 100000);
		spawnWeightTGStructureMedium = builder.defineInRange("spawnWeightTGStructureMedium", 32, 1, 100000);
		spawnWeightTGStructureBig = builder.defineInRange("spawnWeightTGStructureBig", 64, 1, 100000);
		biomeBlacklist = builder.define("biomeBlacklist", new String[]{});
		builder.pop();

		builder.push("NPCSpawn");
		distanceSpawnLevel0 = builder.defineInRange("distanceSpawnLevel0", 500, 0, Integer.MAX_VALUE);
		distanceSpawnLevel1 = builder.defineInRange("distanceSpawnLevel1", 1000, 0, Integer.MAX_VALUE);
		distanceSpawnLevel2 = builder.defineInRange("distanceSpawnLevel2", 2500, 0, Integer.MAX_VALUE);

		spawnWeightTGOverworld = builder.defineInRange("spawnWeightTGOverworld", 600, 0, 10000);
		spawnWeightTGNether = builder.defineInRange("spawnWeightTGNether", 300, 0, 10000);

		spawnWeightZombieSoldier = builder.defineInRange("spawnWeightZombieSoldier", 100, 0, 10000);
		spawnWeightZombieFarmer = builder.defineInRange("spawnWeightZombieFarmer", 200, 0, 10000);
		spawnWeightZombieMiner = builder.defineInRange("spawnWeightZombieMiner", 200, 0, 10000);
		spawnWeightZombiePigmanSoldier = builder.defineInRange("spawnWeightZombiePigmanSoldier", 100, 0, 10000);
		spawnWeightCyberDemon = builder.defineInRange("spawnWeightCyberDemon", 30, 0, 10000);
		spawnWeightSkeletonSoldier = builder.defineInRange("spawnWeightSkeletonSoldier", 100, 0, 10000);
		spawnWeightBandit = builder.defineInRange("spawnWeightBandit", 50, 0, 10000);
		spawnWeightPsychoSteve = builder.defineInRange("spawnWeightPsychoSteve", 3, 0, 10000);
		builder.pop();

		builder.push("DamageFactors");
		damagePvP = builder.defineInRange("damagePvP", 0.5, 0.0, 100.0);
		damageTurretToPlayer = builder.defineInRange("damageTurretToPlayer", 0.5, 0.0, 100.0);
		damageFactorNPC = builder.defineInRange("damageFactorNPC", 1.0, 0.0, 100.0);
		builder.pop();

		builder.push("OreDrills");
		oreDrillMultiplierOres = builder.defineInRange("oreDrillMultiplierOres", 1.0, 0.001, 1000.0);
		oreDrillMultiplierPower = builder.defineInRange("oreDrillMultiplierPower", 1.0, 0.0, 1000.0);
		oreDrillMultiplierFuel = builder.defineInRange("oreDrillMultiplierFuel", 1000, 1, 100000);
		oreDrillFuelValueFuel = builder.defineInRange("oreDrillFuelValueFuel", 100, 1, 100000);

		mininglevel_coal = builder.defineInRange("mininglevel_coal", 0, 0, 10);
		mininglevel_common_metal = builder.defineInRange("mininglevel_common_metal", 0, 0, 10);
		mininglevel_rare_metal = builder.defineInRange("mininglevel_rare_metal", 1, 0, 10);
		mininglevel_shiny_metal = builder.defineInRange("mininglevel_shiny_metal", 2, 0, 10);
		mininglevel_uranium = builder.defineInRange("mininglevel_uranium", 3, 0, 10);
		mininglevel_common_gem = builder.defineInRange("mininglevel_common_gem", 1, 0, 10);
		mininglevel_shiny_gem = builder.defineInRange("mininglevel_shiny_gem", 3, 0, 10);
		mininglevel_nether_crystal = builder.defineInRange("mininglevel_nether_crystal", 2, 0, 10);
		mininglevel_oil = builder.defineInRange("mininglevel_oil", 2, 0, 10);

		oremult_coal = builder.defineInRange("oremult_coal", 10.0, 0.0001, 1000.0);
		oremult_common_metal = builder.defineInRange("oremult_common_metal", 5.0, 0.0001, 1000.0);
		oremult_rare_metal = builder.defineInRange("oremult_rare_metal", 2.5, 0.0001, 1000.0);
		oremult_shiny_metal = builder.defineInRange("oremult_shiny_metal", 1.0, 0.0001, 1000.0);
		oremult_uranium = builder.defineInRange("oremult_uranium", 0.5, 0.0001, 1000.0);
		oremult_common_gem = builder.defineInRange("oremult_common_gem", 5.0, 0.0001, 1000.0);
		oremult_shiny_gem = builder.defineInRange("oremult_shiny_gem", 0.2, 0.0001, 1000.0);
		oremult_nether_crystal = builder.defineInRange("oremult_nether_crystal", 4.0, 0.0001, 1000.0);
		oremult_oil = builder.defineInRange("oremult_oil", 4.0, 0.0001, 1000.0);

		powermult_coal = builder.defineInRange("powermult_coal", 0.1, 0.0001, 1000.0);
		powermult_common_metal = builder.defineInRange("powermult_common_metal", 0.2, 0.0001, 1000.0);
		powermult_rare_metal = builder.defineInRange("powermult_rare_metal", 0.4, 0.0001, 1000.0);
		powermult_shiny_metal = builder.defineInRange("powermult_shiny_metal", 1.0, 0.0001, 1000.0);
		powermult_uranium = builder.defineInRange("powermult_uranium", 1.0, 0.0001, 1000.0);
		powermult_common_gem = builder.defineInRange("powermult_common_gem", 0.2, 0.0001, 1000.0);
		powermult_shiny_gem = builder.defineInRange("powermult_shiny_gem", 1.0, 0.0001, 1000.0);
		powermult_nether_crystal = builder.defineInRange("powermult_nether_crystal", 0.5, 0.0001, 1000.0);
		powermult_oil = builder.defineInRange("powermult_oil", 1.0, 0.0001, 1000.0);
		builder.pop();

		builder.push("Explosives");
		explosiveChargeMaxBlockHardness = builder.defineInRange("explosiveChargeMaxBlockHardness", 30.0, 0.0, Float.MAX_VALUE);
		explosiveChargeAdvancedMaxBlockHardness = builder.defineInRange("explosiveChargeAdvancedMaxBlockHardness", 100.0, 0.0, Float.MAX_VALUE);
		builder.pop();

		builder.push("Fluids");
		fluidListFuel = builder.define("fluidListFuel", new String[]{
				"fuel", "refined_fuel", "biofuel", "biodiesel", "diesel",
				"gasoline", "fluiddiesel", "fluidnitrodiesel", "fliudnitrofuel",
				"refined_biofuel", "fire_water", "rocket_fuel"
		});
		fluidListOil = builder.define("fluidListOil", new String[]{
				"oil", "tree_oil", "crude_oil", "fluidoil", "seed_oil"
		});
		fluidListOilWorldspawn = builder.define("fluidListOilWorldspawn", new String[]{
				"oil", "crude_oil"
		});
		builder.pop();

		builder.push("Experimental");
		WIP_disableRadiationSystem = builder.define("WIP_disableRadiationSystem", true);
		builder.pop();
	}

	private static void setupClient(ForgeConfigSpec.Builder builder) {
		builder.push("Client");
		cl_enableDeathFX = builder.define("cl_enableDeathFX", true);
		cl_enableDeathFX_Gore = builder.define("cl_enableDeathFX_Gore", true);
		cl_lockSpeedFov = builder.define("cl_lockSpeedFov", true);
		cl_fixedSprintFov = builder.defineInRange("cl_fixedSprintFov", 1.15, 1.0, 10.0);
		cl_sortPassesPerTick = builder.defineInRange("cl_sortPassesPerTick", 10, 0, 20);
		builder.pop();
	}

	public static void register() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
	}
}
