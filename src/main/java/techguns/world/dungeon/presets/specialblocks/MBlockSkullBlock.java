package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.util.MBlock;

public class MBlockSkullBlock extends MBlock {

	public MBlockSkullBlock(MBlock other) {
		super(other);
		this.hasTileEntity=true;
	}

	@Override
	public void tileEntityPostPlacementAction(World w, BlockState state, BlockPos p, int rotation) {
		TileEntity tile = w.getTileEntity(p);
		if(tile!=null && tile instanceof SkullTileEntity) {
			SkullTileEntity skull = (SkullTileEntity) tile;
			
			//TODO
			skull.setSkullRotation(rotation);
		}
	}

	
}
