package techguns.blocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.blocks.machines.BasicMachine;

public class GenericItemBlockMetaMachineBlock extends GenericItemBlockMeta {

	public GenericItemBlockMetaMachineBlock(Block block) {
		super(block);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, PlayerEntity player, World world, BlockPos pos, Direction side,
                                float hitX, float hitY, float hitZ, BlockState newState) {
		if (!world.setBlockState(pos, newState, 11)) return false;

        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == this.block)
        {
            setTileEntityNBT(world, player, pos, stack);
            if(this.block instanceof BasicMachine) {
            	((BasicMachine)this.block).onBlockPlacedByExtended(world, pos, state, player, stack, side);
            } else {
            	this.block.onBlockPlacedBy(world, pos, state, player, stack);
            }

            if (player instanceof ServerPlayerEntity)
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, pos, stack);
        }

        return true;
	}

	
}
