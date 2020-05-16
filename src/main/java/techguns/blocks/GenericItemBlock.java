package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import techguns.Techguns;

public class GenericItemBlock extends BlockItem {

	public GenericItemBlock(Block block) {
		super(block);
		this.setRegistryName(block.getRegistryName());
		this.setUnlocalizedName(block.getUnlocalizedName());
		setCreativeTab(Techguns.tabTechgun);
	}

}
