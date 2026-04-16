package com.pWn3d1337.techguns.api.damagesystem;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TextFormatting;

public enum DamageType {
	PHYSICAL, //Melee hits
	PROJECTILE, //Arrows, bullets,..
	FIRE, //fire, flamethrower
	EXPLOSION, // Explosions like TNT, Rockets
	ENERGY, // Energy Weapons, Lasers, Magic
	POISON, //gas, slimy stuff
	UNRESISTABLE, //Things that should never get reduced with armor value: starve, falldmg, suffocate...
	ICE, //anything cold related
	LIGHTNING, //electricity, shock, lightning magic
	RADIATION, //
	DARK; // dark energy, black holes, dark magic

	@Override
	public String toString() {
		switch(this){
			
			case PROJECTILE:
				return Color.fromTextFormatting(TextFormatting.GRAY)+I18n.format("techguns.TGDamageType.PROJECTILE", new Object[0]);
			case FIRE:
				return Color.fromTextFormatting(TextFormatting.RED)+I18n.format("techguns.TGDamageType.FIRE",new Object[0]);
			case EXPLOSION:
				return Color.fromTextFormatting(TextFormatting.DARK_RED)+I18n.format("techguns.TGDamageType.EXPLOSION",new Object[0]);
			case ENERGY:
				return Color.fromTextFormatting(TextFormatting.DARK_AQUA)+I18n.format("techguns.TGDamageType.ENERGY",new Object[0]);
			case POISON:
				return Color.fromTextFormatting(TextFormatting.DARK_GREEN)+I18n.format("techguns.TGDamageType.POISON",new Object[0]);
			case UNRESISTABLE:
				return Color.fromTextFormatting(TextFormatting.BLACK)+I18n.format("techguns.TGDamageType.UNRESISTABLE",new Object[0]);
			case ICE:
				return Color.fromTextFormatting(TextFormatting.AQUA)+I18n.format("techguns.TGDamageType.ICE",new Object[0]);
			case LIGHTNING:
				return Color.fromTextFormatting(TextFormatting.YELLOW)+I18n.format("techguns.TGDamageType.LIGHTNING",new Object[0]);
			case RADIATION:
				return Color.fromTextFormatting(TextFormatting.GREEN)+I18n.format("techguns.TGDamageType.RADIATION",new Object[0]);
			case DARK:
				return Color.fromTextFormatting(TextFormatting.BLACK)+I18n.format("techguns.TGDamageType.DARK",new Object[0]);
			case PHYSICAL:
			default:
				return Color.fromTextFormatting(TextFormatting.DARK_GRAY)+I18n.format("techguns.TGDamageType.PHYSICAL",new Object[0]);
		}
	}

}
