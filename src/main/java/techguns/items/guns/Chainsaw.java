package techguns.items.guns;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.TGSounds;

public class Chainsaw extends GenericGunMeleeCharge {

	public Chainsaw(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
			float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
		
		this.setMiningHeads(TGItems.CHAINSAWBLADES_OBSIDIAN, TGItems.CHAINSAWBLADES_CARBON);
	}

	
	
	@Override
	public float getExtraDigSpeed(ItemStack stack) {
		int headlevel = this.getMiningHeadLevel(stack);
		return 3.0f*headlevel;
	}



	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
		ItemStack stack = player.getHeldItem(handIn);
		this.shootGunPrimary(stack, worldIn, player, false, handIn, null);
		return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);
	}

	@Override
	protected SoundEvent getSwingSound() {
		return TGSounds.CHAINSAW_LOOP;
	}
	
	@Override
	protected SoundEvent getBlockBreakSound() {
		return TGSounds.CHAINSAW_HIT;
	}
	
	@Override
	protected void playSweepSoundEffect(PlayerEntity player) {
		player.world.playSound((PlayerEntity) null, player.posX, player.posY, player.posZ, TGSounds.CHAINSAW_HIT,
				player.getSoundCategory(), melee_sound_volume, 1.0F);
	}

	
}
