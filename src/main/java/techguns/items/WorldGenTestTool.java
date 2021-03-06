package techguns.items;

import java.util.Random;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.util.BlockUtils;
import techguns.world.dungeon.Dungeon;
import techguns.world.dungeon.presets.IDungeonPreset;
import techguns.world.structures.MilitaryCamp;
import techguns.world.structures.NetherDungeonEntrance;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class WorldGenTestTool extends GenericItem{

	static String[] modes = new String[]{"MilitaryBase", "Barracks", "Smooth", "Flatten", "Remove Junk",/* "Sphere", "Cylinder"*/};
	
	public WorldGenTestTool(String name) {
		this(name,true);
	}
	
	public WorldGenTestTool(String name, boolean addItemToList) {
		super(name, addItemToList);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack item = player.getHeldItem(hand);
		if (!item.hasTagCompound()) {
			item.setTagCompound(new CompoundNBT());
		}
		if (player.isSneaking()) {
			int mode = 1;
			if (item.getTagCompound().hasKey("mode")) {
				mode = (item.getTagCompound().getInteger("mode")+1) % modes.length;
			}
			item.getTagCompound().setInteger("mode", mode);
			if (world.isRemote) player.sendStatusMessage(new StringTextComponent("WorldGen tool mode set to \""+modes[mode]+"\"."), false);
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand,
                                      Direction facing, float hitX, float hitY, float hitZ) {
	
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		ItemStack item = player.getHeldItem(hand);
		if (!item.hasTagCompound()) {
			item.setTagCompound(new CompoundNBT());
		}
		if (player.isSneaking()) {
			int mode = 1;
			if (item.getTagCompound().hasKey("mode")) {
				mode = (item.getTagCompound().getInteger("mode")+1) % modes.length;
			}
			item.getTagCompound().setInteger("mode", mode);
			if (world.isRemote) player.sendStatusMessage(new StringTextComponent("WorldGen tool mode set to \""+modes[mode]+"\"."), false);
		}else {
			if (item.getTagCompound().hasKey("x1") && item.getTagCompound().hasKey("y1") && item.getTagCompound().hasKey("z1")) {
				
				if (world.isRemote) player.sendStatusMessage(new StringTextComponent("Position2 set ("+x+"/"+y+"/"+z+")."), false);
				int x1 = item.getTagCompound().getInteger("x1");
				int y1 = item.getTagCompound().getInteger("y1");
				int z1 = item.getTagCompound().getInteger("z1");
				int sizeX = Math.abs(x1-x)+1;
				int sizeY = Math.abs(y1-y)+1;
				int sizeZ = Math.abs(z1-z)+1;
				
				if (world.isRemote) player.sendStatusMessage(new StringTextComponent("Size: ("+sizeX+"/"+sizeY+"/"+sizeZ+")."), false);
				
				if (!world.isRemote) {
					int mode = 0;
					if (item.getTagCompound().hasKey("mode")) {
						mode = item.getTagCompound().getInteger("mode");
					}
					doOperation(world, Math.min(x, x1), Math.min(y, y1), Math.min(z, z1), sizeX, sizeY, sizeZ, mode);

				}
	
				item.getTagCompound().removeTag("x1");
				item.getTagCompound().removeTag("y1");
				item.getTagCompound().removeTag("z1");
				
			}else {
				item.getTagCompound().setInteger("x1",x);
				item.getTagCompound().setInteger("y1",y);
				item.getTagCompound().setInteger("z1",z);
				if (world.isRemote) player.sendStatusMessage(new StringTextComponent("Position1 set ("+x+"/"+y+"/"+z+")."), false);
			}
		}
		return ActionResultType.SUCCESS;
    }
	
	
	private void doOperation(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, int mode) {
		
		Random rnd = new Random();
		switch (mode) {
		case 0: //structure
			MilitaryCamp camp = new MilitaryCamp(3,rnd);
		    camp.init(x, y, z, sizeX, sizeZ);
		    camp.setBlocks(world, rnd);
			break;
		case 1: //dungeon
			//Helipad b = new Helipad(7,7,7,10,-1,10);
			//Tanks b = new Tanks(4,5,4,10,-1,10);
			//Barracks b = new Barracks(7,7,7,-1,-1,-1);
			//Bunker b = new Bunker(3,3,5,15,3,15, rnd.nextInt(4));
			//FactoryHouseSmall b = new FactoryHouseSmall(8, 8, 8, 8, 8, 8);
			//SmallTrainstation b = new SmallTrainstation(8, 8, 8, 8, 8, 8);
			
			//AircraftCarrier b = new AircraftCarrier(54,24,21,54,24,21);
				
			//SmallMine b = new SmallMine();
			//new AlienBugNest(x ,y+32, z,sizeX, 0, sizeZ, rnd).setBlocks(world);
			
			//TechFortressStructure b = new TechFortressStructure();
			//CastleStructure b = new CastleStructure();
			
			//CityStructure c = new CityStructure();
			//Biome bi = world.getBiome(new BlockPos(x,y,z));
			//c.spawnStructureWorldgen(world, x>>4, z>>4, sizeX, sizeY, sizeZ, rnd, bi);
			
			//OreClusterMeteorBasis b = new OreClusterMeteorBasis();
			
			//PoliceStation b = new PoliceStation();
			
			//GasStation b = new GasStation();
			//SurvivorHideout b = new SurvivorHideout();
			//b.setBlocks(world, x, y, z, sizeX, 32, sizeZ, rnd.nextInt(4), BiomeColorType.DESERT, rnd);
			
			
			IDungeonPreset preset = IDungeonPreset.PRESET_NETHER;
			Dungeon	dungeon = new Dungeon(preset);
			
			int height = 24 + world.rand.nextInt(25);
			
			int yOffset = preset.getSizeY() + (height % preset.getSizeY()) - height;
			
			dungeon.generate(world, x, y+yOffset ,z, sizeX, height, sizeZ);
			
			NetherDungeonEntrance e = new NetherDungeonEntrance();
			
			int px = x+(dungeon.startPos.getX()*preset.getSizeXZ());
			int py = y+(dungeon.startPos.getY()*preset.getSizeY()) + yOffset;
			int pz = z+(dungeon.startPos.getZ()*preset.getSizeXZ());
			
			e.setBlocks(world, px+1 - preset.getSizeXZ()/2, py, pz+1 - preset.getSizeXZ()/2, e.sizeX, e.sizeY, e.sizeZ, BlockUtils.facingToRotation(dungeon.startRotation), BiomeColorType.WOODLAND, rnd);
			
			//bugnest
			//AlienBugNestStructure b = new AlienBugNestStructure();
			//b.spawnStructureWorldgen(world, x>>4, z>>4, 4, 0, 4, rnd, Biomes.PLAINS);
			
			break;
		case 2: //Smooth
			BlockUtils.apply2DHeightmapFilter(world, x, z, sizeX, sizeZ, BlockUtils.FILTER_GAUSSIAN_5x5);
			break;
		case 3: //Flatten
			BlockUtils.flattenArea(world, x, z, sizeX, sizeZ, 2);
			break;
		case 4: //remove junk
			BlockUtils.removeJunkInArea(world, x, z, sizeX, sizeZ);
			break;	
		}

	}


}
