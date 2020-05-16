package techguns.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MBlockVanillaSpawner extends MBlock {

	ResourceLocation entityId;
	
	public MBlockVanillaSpawner(Class<? extends Entity> clazz) {
		super(Blocks.MOB_SPAWNER, 0);
		this.hasTileEntity=true;
		this.entityId = EntityList.getKey(clazz);
	}

	@Override
	public void tileEntityPostPlacementAction(World w, BlockState state, BlockPos p, int rotation) {
		TileEntity tile = w.getTileEntity(p);
		if(tile!=null && tile instanceof MobSpawnerTileEntity) {
			MobSpawnerTileEntity spawner = (MobSpawnerTileEntity) tile;
			spawner.getSpawnerBaseLogic().setEntityId(entityId);
		}
	}
	
}
