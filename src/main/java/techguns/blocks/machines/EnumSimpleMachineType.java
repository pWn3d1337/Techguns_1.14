package techguns.blocks.machines;

import net.minecraft.block.BlockRenderType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import techguns.api.machines.IMachineType;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.RepairBenchTileEnt;
import techguns.tileentities.BlastFurnaceTileEnt;

public enum EnumSimpleMachineType implements IStringSerializable, IMachineType<EnumSimpleMachineType> {
	CAMO_BENCH(0, CamoBenchTileEnt.class,true, BlockRenderType.MODEL),
	REPAIR_BENCH(1, RepairBenchTileEnt.class,true, BlockRenderType.MODEL),
	CHARGING_STATION(2, ChargingStationTileEnt.class, false, BlockRenderType.MODEL),
	BLAST_FURNACE(3, BlastFurnaceTileEnt.class, true, BlockRenderType.MODEL);
	
	private int id;
	private String name;
	private Class<? extends TileEntity> tile;
	private boolean isFullCube;
	private BlockRenderType renderType;
	private BlockRenderLayer renderLayer;
	
	private EnumSimpleMachineType(int id, Class<? extends TileEntity> tile, boolean isFullCube, BlockRenderType renderType) {
		this(id,tile,isFullCube,renderType, BlockRenderLayer.SOLID);
	}
	
	
	private EnumSimpleMachineType(int id, Class<? extends TileEntity> tile, boolean isFullCube, BlockRenderType renderType, BlockRenderLayer layer) {
		this.id=id;
		this.name=this.name().toLowerCase();
		this.tile = tile;
		this.isFullCube=isFullCube;
		this.renderType=renderType;
		this.renderLayer=layer;
	}
	
	public int getIndex() {
		return id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getMaxMachineIndex() {
		return this.values().length;
	}

	@Override
	public TileEntity getTile() {
		try {
			return this.tile.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Class<? extends TileEntity> getTileClass() {
		return this.tile;
	}

	@Override
	public boolean isFullCube() {
		return isFullCube;
	}

	@Override
	public BlockRenderType getRenderType() {
		return renderType;
	}

	@Override
	public BlockRenderLayer getBlockRenderLayer() {
		return this.renderLayer;
	}

	@Override
	public boolean debugOnly() {
		return false;
	}
	
}
