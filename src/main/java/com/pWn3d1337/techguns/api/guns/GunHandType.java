package com.pWn3d1337.techguns.api.guns;

import com.pWn3d1337.techguns.util.TextUtil;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.TextFormatting;

public enum GunHandType {
	TWO_HANDED,
	ONE_HANDED,
	ONE_POINT_FIVE_HANDED;

	@Override
	public String toString() {
		String s="";
		switch(this) {
		case ONE_HANDED:
			s+= Color.fromTextFormatting(TextFormatting.GREEN);
			break;
		case ONE_POINT_FIVE_HANDED:
			s+=Color.fromTextFormatting(TextFormatting.YELLOW);
			break;
		case TWO_HANDED:
			s+=Color.fromTextFormatting(TextFormatting.GOLD);
			break;
		default:
			break;
		}
		return s+ TextUtil.trans("techguns.gunhandtype."+super.toString().toLowerCase());
	}
}
