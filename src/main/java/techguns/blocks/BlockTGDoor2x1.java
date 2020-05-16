package techguns.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGSounds;
import techguns.items.ItemTGDoor2x1;

public class BlockTGDoor2x1 extends DoorBlock implements IGenericBlock {

	ItemTGDoor2x1 placer;
	
	public BlockTGDoor2x1(String name, ItemTGDoor2x1 placer) {
		super(Material.IRON);
		this.init(this, name, true);
		this.setSoundType(SoundType.METAL);
		this.placer=placer;
		this.placer.setBlock(this);
	}
	
    /**
     * Called when the block is right clicked by a player.
     */
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ)
    {
       
        BlockPos blockpos = state.getValue(HALF) == DoorBlock.EnumDoorHalf.LOWER ? pos : pos.down();
        BlockState temp = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);
        BlockState iblockstate = temp.getActualState(worldIn, blockpos);
        
        if (iblockstate.getBlock() != this)
        {
            return false;
        }
        else
        {
        	
            state = iblockstate.cycleProperty(OPEN);
        	
        	//check if double door
        	Direction doorfacing = iblockstate.getValue(FACING);
        	Direction offsetfacing;

        	if(iblockstate.getValue(HINGE)==EnumHingePosition.LEFT) {
        		offsetfacing = doorfacing.rotateY();
        	} else {
        		offsetfacing = doorfacing.rotateYCCW();
        	}

        	BlockPos offsetPos = blockpos.offset(offsetfacing);
        	BlockState offsetState = worldIn.getBlockState(offsetPos);
        	if(offsetState.getBlock()==iblockstate.getBlock() && offsetState.getValue(OPEN)==iblockstate.getValue(OPEN)) {
                worldIn.setBlockState(offsetPos, offsetState.withProperty(OPEN, state.getValue(OPEN)), 10);
                worldIn.markBlockRangeForRenderUpdate(offsetPos, pos.offset(offsetfacing));
        	}
        	
            worldIn.setBlockState(blockpos, state, 10);
            worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
            //worldIn.playEvent(playerIn, ((Boolean)state.getValue(OPEN)).booleanValue() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), TGSounds.BUNKER_DOOR_OPEN, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            return true;
        }
        
    }
	
    @Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
	}

	/**
     * Gets the localized name of this block. Used for the statistics page.
     */
	@Override
    public String getLocalizedName()
    {
        return I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    public PushReaction getMobilityFlag(BlockState state)
    {
        return PushReaction.BLOCK;
    }
	
    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == DoorBlock.EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state)
    {
        return new ItemStack(this.getItem());
    }

    private Item getItem()
    {
        return this.placer;
    }

	@Override
	public BlockItem createItemBlock() {
		return new GenericItemBlock(this);
	}

	@Override
	public void registerBlock(Register<Block> event) {
		event.getRegistry().register(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {

	}
    
    
}
