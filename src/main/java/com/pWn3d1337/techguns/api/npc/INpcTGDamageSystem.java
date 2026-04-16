package com.pWn3d1337.techguns.api.npc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;

import com.pWn3d1337.techguns.api.damagesystem.TGDamageSource;

public interface INpcTGDamageSystem {
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc);
	public float getPenetrationResistance(TGDamageSource dmgsrc);

	/**
	 * @param elb - should be considered "this", passed as param to allow default implementation
	 * @param src
	 * @return
	 */
	public default float getToughnessAfterPentration(LivingEntity elb, TGDamageSource src) {
		float toughness = (float)elb.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
		float pen = Math.max(src.armorPenetration-this.getPenetrationResistance(src),0f)*4f; //per part for players, so *4 for total
		return Math.max(toughness-pen,0f);
	}
}

