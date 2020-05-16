package techguns.gui.widgets;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.FurnaceTileEntity;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class SlotFurnaceFuelTG extends SlotMachineInput {

	public SlotFurnaceFuelTG(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return FurnaceTileEntity.getItemBurnTime(item)>0;
	}

}
