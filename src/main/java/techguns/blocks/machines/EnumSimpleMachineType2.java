package techguns.blocks.machines;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.api.machines.IMachineType;
import techguns.tileentities.GrinderTileEnt;
import techguns.tileentities.UpgradeBenchTileEnt;

public enum EnumSimpleMachineType2 implements IStringSerializable, IMachineType<EnumSimpleMachineType2> {
	GRINDER(0, GrinderTileEnt.class,true, BlockRenderType.MODEL),
	ARMOR_BENCH(1, UpgradeBenchTileEnt.class, false, BlockRenderType.MODEL);
	
	private int id;
	private String name;
	private Class<? extends TileEntity> tile;
	private boolean isFullCube;
	private BlockRenderType renderType;
	private BlockRenderLayer renderLayer;
	
	private EnumSimpleMachineType2(int id, Class<? extends TileEntity> tile, boolean isFullCube, BlockRenderType renderType) {
		this(id,tile,isFullCube,renderType, BlockRenderLayer.SOLID);
	}
	
	private EnumSimpleMachineType2(int id, Class<? extends TileEntity> tile, boolean isFullCube, BlockRenderType renderType, BlockRenderLayer layer) {
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

	@Override
	public boolean hasCustomModelLocation() {
		return this==GRINDER;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setCustomModelLocation(Item itemblock, int meta, ResourceLocation registryName, BlockState state) {
		ResourceLocation loc = new ResourceLocation(Techguns.MODID, registryName.getResourcePath()+"_"+this.name().toLowerCase()+"_inv");
		ModelLoader.setCustomModelResourceLocation(itemblock, meta, new ModelResourceLocation(loc, "inventory"));
	}

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		if(this==EnumSimpleMachineType2.ARMOR_BENCH) {
			 return UpgradeBenchTileEnt.BLOCK_BB;
		}
		return IMachineType.super.getBoundingBox(state, source, pos);
	}
	
	
}