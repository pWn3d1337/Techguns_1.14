package com.pWn3d1337.techguns.debug;

import com.pWn3d1337.techguns.TGConfig;
import com.pWn3d1337.techguns.Techguns;

import com.pWn3d1337.techguns.client.particle.TGFX;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

import org.lwjgl.glfw.GLFW;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Techguns.MODID)
public class Keybinds {

	public static float X=0;
	public static float Y=0;
	public static float Z=0;
	
	public static final float D = 0.01f;
	
    public static KeyBinding plusX;
    public static KeyBinding minusX;
    public static KeyBinding plusY;
    public static KeyBinding minusY;
    public static KeyBinding plusZ;
    public static KeyBinding minusZ;
    public static KeyBinding showXYZ;
    public static KeyBinding resetXYZ;
    
    public static KeyBinding reloadFX;
    
    private static final String CATEGORY = "key.categories."+Techguns.MODID;
    
    public static void init() {
    	
        plusX = new KeyBinding("key.+x", GLFW.GLFW_KEY_KP_7, CATEGORY);

        minusX = new KeyBinding("key.-x", GLFW.GLFW_KEY_KP_8, CATEGORY);
        
        plusY = new KeyBinding("key.+y", GLFW.GLFW_KEY_KP_4, CATEGORY);
        minusY = new KeyBinding("key.-y", GLFW.GLFW_KEY_KP_5, CATEGORY);
        
        plusZ = new KeyBinding("key.+z", GLFW.GLFW_KEY_KP_1, CATEGORY);
        minusZ = new KeyBinding("key.-z", GLFW.GLFW_KEY_KP_2, CATEGORY);
        
        showXYZ = new KeyBinding("key.show", GLFW.GLFW_KEY_KP_0, CATEGORY);
        
        resetXYZ = new KeyBinding("key.resetXYZ", GLFW.GLFW_KEY_COMMA, CATEGORY);
        
        reloadFX = new KeyBinding("key.reloadFX", GLFW.GLFW_KEY_KP_ENTER, CATEGORY);
        
        ClientRegistry.registerKeyBinding(plusX);
        ClientRegistry.registerKeyBinding(plusY);
        ClientRegistry.registerKeyBinding(plusZ);
        ClientRegistry.registerKeyBinding(minusX);
        ClientRegistry.registerKeyBinding(minusY);
        ClientRegistry.registerKeyBinding(minusZ);
        ClientRegistry.registerKeyBinding(showXYZ);
        ClientRegistry.registerKeyBinding(resetXYZ);
        ClientRegistry.registerKeyBinding(reloadFX);
    }
    
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
    	if(!TGConfig.debug.get()) return;
    	
        if(Keybinds.plusX.isPressed()){
        	Keybinds.X+=D;
			Minecraft.getInstance().player.sendMessage(new StringTextComponent("X:"+ X), UUID.randomUUID());
        }
        if(Keybinds.minusX.isPressed()){
        	Keybinds.X-=D;
        	Minecraft.getInstance().player.sendMessage(new StringTextComponent("X:"+Keybinds.X), UUID.randomUUID());
        }
        if(Keybinds.plusY.isPressed()){
        	Keybinds.Y+=D;
			Minecraft.getInstance().player.sendMessage(new StringTextComponent("Y:"+Keybinds.Y), UUID.randomUUID());
        }
        if(Keybinds.minusY.isPressed()){
        	Keybinds.Y-=D;
        	Minecraft.getInstance().player.sendMessage(new StringTextComponent("Y:"+Keybinds.Y), UUID.randomUUID());
        }
        if(Keybinds.plusZ.isPressed()){
        	Keybinds.Z+=D;
			Minecraft.getInstance().player.sendMessage(new StringTextComponent("Z:"+Keybinds.Z), UUID.randomUUID());
        }
        if(Keybinds.minusZ.isPressed()){
        	Keybinds.Z-=D;
        	Minecraft.getInstance().player.sendMessage(new StringTextComponent("Z:"+Keybinds.Z), UUID.randomUUID());
        }
        if(Keybinds.showXYZ.isPressed()){
        	Minecraft.getInstance().player.sendMessage(new StringTextComponent("XYZ: "+Keybinds.X+", "+Keybinds.Y+", "+Keybinds.Z), UUID.randomUUID());
        }
        if(Keybinds.reloadFX.isPressed()) {
        	Minecraft.getInstance().player.sendMessage(new StringTextComponent("Reload FX..."), UUID.randomUUID());
        	TGFX.loadFXList();
        	Minecraft.getInstance().player.sendMessage(new StringTextComponent("Reload FX... Done!"), UUID.randomUUID());
        }
        if(Keybinds.resetXYZ.isPressed()) {
        	Keybinds.Z=0f;
        	Keybinds.Y=0f;
        	Keybinds.X=0f;
			Minecraft.getInstance().player.sendMessage(new StringTextComponent("Reset XYZ"), UUID.randomUUID());
        }
    }
}
