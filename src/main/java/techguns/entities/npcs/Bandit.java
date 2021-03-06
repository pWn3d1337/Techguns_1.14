package techguns.entities.npcs;

import java.util.Random;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGuns;
import techguns.Techguns;

public class Bandit extends GenericNPC {

	public static final ResourceLocation LOOT = new ResourceLocation(Techguns.MODID, "entities/Bandit");
	
	public Bandit(World world) {
		super(world);
		setTGArmorStats(5.0f, 0f);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {

		// Armors
		
		this.setItemStackToSlot(EquipmentSlotType.CHEST,new ItemStack(TGArmors.t1_scout_Chestplate));
		this.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(TGArmors.t1_scout_Leggings));
		this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(TGArmors.t1_scout_Boots));
		
		double chance = 0.5;
		if (Math.random() <= chance) {
			this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(TGArmors.t1_scout_Helmet));
		}			 

		// Weapons
		Random r = new Random();
		Item weapon = null;
		switch (r.nextInt(6)) {
		case 0:
			weapon = TGuns.pistol;
			break;
		case 1:
			weapon = TGuns.ak47;
			break;
		case 2:
			weapon = TGuns.sawedoff;
			break;
		case 3:
			weapon = TGuns.thompson;
			break;
		case 4:
			weapon = TGuns.revolver;
			break;
		default:
			weapon = TGuns.boltaction;
			break;
		}
		if (weapon != null) this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(weapon));
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		return LOOT;
	}
}