package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.util.MBlock;

public class MBlockItemFrame extends MBlock {

	ItemStack itemToDisplay = ItemStack.EMPTY;
	
	Direction face = Direction.SOUTH;
	
	public MBlockItemFrame(ItemStack itemToDisplay, Direction facing) {
		super(Blocks.AIR.getDefaultState());
		this.hasTileEntity=true;
		this.layer=1;
		this.itemToDisplay = itemToDisplay;
		this.face = facing;
	}

	@Override
	protected int getPlacementFlags() {
		return 3;
	}

	@Override
	public void tileEntityPostPlacementAction(World w, BlockState state, BlockPos p, int rotation) {
		
		if(!w.isRemote) {
			ItemFrameEntity frame = new ItemFrameEntity(w, p, rotate(face,rotation));
			w.spawnEntity(frame);
	
			if(!itemToDisplay.isEmpty()) {
				frame.setDisplayedItem(itemToDisplay.copy());
			}
		}
	}

	private Direction rotate (Direction facing, int rotations) {
		for(int i=0;i<rotations;i++) {
			facing = facing.rotateYCCW();
		}
		return facing;
	}
}
