package techguns.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumCamoNetType;
import techguns.world.BlockRotator;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class MBlockCamoNetTop extends MBlock {

	protected static BlockState WOODLAND = TGBlocks.CAMONET_TOP.getDefaultState();
	protected static BlockState DESERT = TGBlocks.CAMONET_TOP.getDefaultState().withProperty(TGBlocks.CAMONET_TOP.TYPE, EnumCamoNetType.DESERT);
	protected static BlockState SNOW = TGBlocks.CAMONET_TOP.getDefaultState().withProperty(TGBlocks.CAMONET_TOP.TYPE, EnumCamoNetType.SNOW);;
	
	public MBlockCamoNetTop() {
		super(WOODLAND);
	}

	@Override
	public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome) {
		if(pos.getY()>=1) {
			BlockState targetState = BlockRotator.getRotatedHorizontal(getCamoTypeState(biome), rotation);
			w.setBlockState(pos, targetState,getPlacementFlags());
			if(this.hasTileEntity) {
				this.tileEntityPostPlacementAction(w, targetState, pos, rotation);
			}
		}
	}

	public BlockState getCamoTypeState(BiomeColorType camo) {
		switch(camo) {
		case DESERT:
			return DESERT;
		case SNOW:
			return SNOW;
		case WOODLAND:
		default:
			return WOODLAND;
		}
	}
	
}
