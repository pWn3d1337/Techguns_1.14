package com.pWn3d1337.techguns.util;

import com.pWn3d1337.techguns.Techguns;

import net.minecraft.client.resources.I18n;

public class TextUtil {
	public static String trans(String key){
		return I18n.format(key, new Object[0]);
	}
	/**
	 * Trans with prefixing MODID.
	 */
	public static String transTG(String key){
		return I18n.format(Techguns.MODID+"."+key, new Object[0]);
	}
}
