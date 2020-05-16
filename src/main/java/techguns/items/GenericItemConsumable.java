package techguns.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class GenericItemConsumable extends GenericItem {

	 /** Number of ticks to run while 'EnumAction'ing until result. */
    public final int itemUseDuration;
    protected boolean ignoreHunger=true;
    
	public GenericItemConsumable(String name, int useDuration) {
		super(name);
		this.itemUseDuration = useDuration;
	}

	public GenericItemConsumable(String name, int useDuaration, boolean addToItemList) {
		super(name, addToItemList);
		 this.itemUseDuration = useDuaration;
	}


    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        if (entityLiving instanceof PlayerEntity)
        {
            PlayerEntity entityplayer = (PlayerEntity)entityLiving;
            //entityplayer.getFoodStats().addStats(this, stack);
            worldIn.playSound((PlayerEntity)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, this.getConsumedSound(), SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onConsumed(stack, worldIn, entityplayer);
            entityplayer.addStat(Stats.getObjectUseStats(this));

            if (entityplayer instanceof ServerPlayerEntity)
            {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)entityplayer, stack);
            }
        }

        stack.shrink(1);
        return stack;
    }

    protected SoundEvent getConsumedSound()
    {
    	return SoundEvents.ENTITY_PLAYER_BURP;
    }    
    
    protected void onConsumed(ItemStack stack, World worldIn, PlayerEntity player)
    {

    }


    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return this.itemUseDuration;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAction getItemUseAction(ItemStack stack)
    {
        return UseAction.EAT;
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(this.ignoreHunger))
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
        }
        else
        {
            return new ActionResult<ItemStack>(ActionResultType.FAIL, itemstack);
        }
    }
	
}
