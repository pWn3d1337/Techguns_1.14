package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import techguns.Techguns;
import techguns.entities.npcs.ArmySoldier;
import techguns.entities.npcs.ZombieSoldier;
import techguns.tileentities.TGSpawnerTileEnt;

public class BlockTGSpawner extends GenericBlockMetaEnum<EnumMonsterSpawnerType> {
	
	public BlockTGSpawner(String name) {
		super(name, Material.ROCK, EnumMonsterSpawnerType.class);
		this.setBlockUnbreakable();
	}

	protected static final AxisAlignedBB BB = new AxisAlignedBB(2/16d, 0, 2/16d, 14/16d, 2/16d, 14/16d);
	
	
	
	@Override
	public void registerBlock(Register<Block> event) {
		super.registerBlock(event);
		GameRegistry.registerTileEntity(TGSpawnerTileEnt.class, Techguns.MODID+":"+"TGSpawner");
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, BlockState state) {
		return new TGSpawnerTileEnt();
	}

	@Override
	public boolean isFullBlock(BlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		return BB;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state,
			int fortune) {
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer,
                                ItemStack stack) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile!=null && tile instanceof TGSpawnerTileEnt) {
			TGSpawnerTileEnt spawner = (TGSpawnerTileEnt) tile;
			
			switch(state.getValue(TYPE)) {
			case HOLE:
				spawner.addMobType(ZombieSoldier.class, 1);
				break;
			case SOLDIER_SPAWN:
				spawner.addMobType(ArmySoldier.class, 1);
				break;
			default:
				break;
			
			}
		}
	}

	/*@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile!=null && tile instanceof TGSpawnerTileEnt) {
				TGSpawnerTileEnt spawner = (TGSpawnerTileEnt) tile;
				
				spawner.debug();
				
			}
		}
		return true;
	}*/

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
		return BlockFaceShape.UNDEFINED;
	}

}
