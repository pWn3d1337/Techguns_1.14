package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.util.MBlock;

public class RandomStateMBlock extends MBlock {

	protected transient int maxMeta;
	
	public RandomStateMBlock(MBlock other, int maxmeta) {
		super(other);
		this.maxMeta=maxmeta;
		this.hasTileEntity=true;
	}

	@Override
	public void tileEntityPostPlacementAction(World w, BlockState state, BlockPos p, int rotation) {
		int s = w.rand.nextInt(maxMeta);
		BlockState stateNew = block.getStateFromMeta(s);
		w.setBlockState(p, stateNew, 2);
	}
	
}
