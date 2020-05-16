package techguns.gui.widgets;

import javax.annotation.Nullable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotArmor extends Slot {
	private EquipmentSlotType armortype;
	private PlayerEntity ply;
	
	public SlotArmor(IInventory inv, int slot, int x,
                     int y, EquipmentSlotType armorType, PlayerEntity ply) {
		super(inv, slot, x, y);
		this.armortype=armorType;
		this.ply=ply;
	}
	
	public SlotArmor(IInventory inv, int slot, int x,
                     int y, int armorType, PlayerEntity ply) {
		super(inv, slot, x, y);
		this.armortype=getArmorTypeFromInt(armorType);
		this.ply=ply;
	}

	protected static EquipmentSlotType getArmorTypeFromInt(int slot) {
		switch(slot) {
		case 0:
			return EquipmentSlotType.HEAD;
		case 1:
			return EquipmentSlotType.CHEST;
		case 2:
			return EquipmentSlotType.LEGS;
		default:
			return EquipmentSlotType.FEET;
		}
	}
	
    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
     * in the case of armor slots)
     */
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace
     * fuel.
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem().isValidArmor(stack, armortype, ply);
    }
    
    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    @Override
    public boolean canTakeStack(PlayerEntity playerIn)
    {
        ItemStack itemstack = this.getStack();
        return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
    }
    
    @Nullable
    @SideOnly(Side.CLIENT)
    public String getSlotTexture()
    {
        return ArmorItem.EMPTY_SLOT_NAMES[armortype.getIndex()];
    }
    
}