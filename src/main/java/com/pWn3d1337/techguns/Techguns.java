package com.pWn3d1337.techguns;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import java.util.logging.Logger;

@Mod("techguns")
public class Techguns
{
	//Mod integration
	public boolean FTBLIB_ENABLED=false;
	public boolean CHISEL_ENABLED=false;
    public static final String MODID = "techguns";
	public static Techguns instance;
	public static Logger logger_client = Logger.getLogger("TechgunsClient");
	public static Logger logger_server = Logger.getLogger("TechgunsServer");
	public static Logger logger_both = Logger.getLogger("Techguns");

	public Techguns() {

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TGConfig.COMMON_SPEC, "techguns-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TGConfig.CLIENT_SPEC, "techguns-client.toml");
		MinecraftForge.EVENT_BUS.register(this);
	}


	private void setup(final FMLCommonSetupEvent event) {
		{
			if(ModList.get().isLoaded("ftblib")) {
				FTBLIB_ENABLED=true;
			}
			if(ModList.get().isLoaded("chisel")) {
				CHISEL_ENABLED=true;
			}
		}
	}
	private void doClientStuff(final FMLClientSetupEvent event) {

	}
}



