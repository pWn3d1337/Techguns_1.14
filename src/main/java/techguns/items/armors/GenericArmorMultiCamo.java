package techguns.items.armors;

import java.util.List;
import java.util.Random;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.util.TextUtil;

public class GenericArmorMultiCamo extends GenericArmor implements ICamoChangeable{
		protected static Random rnd=new Random();
		protected String[] textureNames;
		protected String camoNameSuffix="";
		
		
		public GenericArmorMultiCamo(String unlocalizedName, TGArmorMaterial material, String[] textureNames, EquipmentSlotType type) {
			this(Techguns.MODID, unlocalizedName, material, textureNames, type);
		}
		
		public GenericArmorMultiCamo(String modid, String unlocalizedName, TGArmorMaterial material, String[] textureNames, EquipmentSlotType type) {
		    super(modid,unlocalizedName,material, textureNames[0],type);
		   this.textureNames=textureNames;
		}

		public GenericArmorMultiCamo setCamoNameSuffix(String s){
			this.camoNameSuffix=s+".";
			return this;
		}
		
		/*@Override
		public String getArmorTexture(ItemStack stack, Entity entity, int slot,
				String type) {
			NBTTagCompound tags = stack.getTagCompound();
			byte camoID=0;
			if (tags!=null){
				if (tags.hasKey("camo")){
					camoID=tags.getByte("camo");
				}
			}
			
			if (this.hasDoubleTexture()) {
				return Techguns.MODID + ":textures/armor/" + this.textureNames[camoID] + "_" + (this.armorType == EntityEquipmentSlot.LEGS ? "2" : "1") + ".png";
			} else { //USE SINGLE TEXTURE
				return Techguns.MODID + ":textures/armor/" + this.textureNames[camoID] + ".png";
			}
		}*/



		@Override
		public void addInformation(ItemStack item, World worldIn, List<String> list, ITooltipFlag flagIn) {
			
			/*NBTTagCompound tags = item.getTagCompound();
			byte camoID=0;
			if (tags!=null && tags.hasKey("camo")){
				camoID=tags.getByte("camo");
			}*/
			super.addInformation(item, worldIn, list, flagIn);
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){	
				list.add(TextUtil.trans(Techguns.MODID+".tooltip.currentcamo")+": "+getCurrentCamoName(item));
			}
		}

		@Override
		public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn) {

			if (player.isSneaking()){
				
				ItemStack item = player.getHeldItem(handIn);
				
				//int camoID=switchCamo(item);
				//this.setTextureName(this.textureNames[camoID]);
				//System.out.println("Switched Camo to:"+camoID);
				
				this.switchCamo(item);
				if (world.isRemote){
					player.sendMessage(new StringTextComponent(TextUtil.trans("techguns.message.camoswitch")+" "+getCurrentCamoName(item)));
				}
		
				return new ActionResult<ItemStack>(ActionResultType.SUCCESS, item);
			} else {
				return super.onItemRightClick(world, player, handIn);
			}
		}

		@Override
		public int getCamoCount() {
			if (textureNames!=null){
				return textureNames.length;
			}
			return 0;
		}

		@Override
		public String getCurrentCamoName(ItemStack item) {
			CompoundNBT tags = item.getTagCompound();
			byte camoID=-1;
			if (tags!=null && tags.hasKey("camo")){
				camoID=tags.getByte("camo");
			}
			if(camoID>=0){
				return TextUtil.trans(this.modid+".item."+textureNames[0]+"."+camoNameSuffix+"camoname."+camoID);
			} else {
				return TextUtil.trans(Techguns.MODID+".item.invalidcamo");
			}
		}
		
		public static int getRandomCamoIndexFor(GenericArmorMultiCamo type){
			int count = type.getCamoCount();
			if( count <=0){
				return 0;
			}
			return rnd.nextInt(count);
		}
		
		public static ItemStack getNewWithCamo(Item item, int camo){
			ItemStack armor = new ItemStack(item);
			CompoundNBT tags = armor.getTagCompound();
			if(tags==null){
				tags=new CompoundNBT();
				armor.setTagCompound(tags);
			}
			tags.setByte("camo", (byte)camo);		
			return armor;
		}
		
		@Override
		public void onCreated(ItemStack stack, World world, PlayerEntity player) {
			super.onCreated(stack, world, player);
			CompoundNBT tags = stack.getTagCompound();
			if(tags==null){
				tags=new CompoundNBT();
				stack.setTagCompound(tags);
			}
			tags.setByte("camo", (byte)0);
		}

	
		protected static int getArmorLayer(EquipmentSlotType slot){
			return EquipmentSlotType.LEGS==slot ? 2:1;
		}
		
	    @Override
	    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	    {
	    	GenericArmorMultiCamo armor = (GenericArmorMultiCamo) stack.getItem();
	    	 
	    	int i = armor.getCurrentCamoIndex(stack);
	    	if (i>=0 && i<this.textureNames.length){
		        return this.modid+":textures/models/armor/"+this.textureNames[i]+(this.hasDoubleTexture()?("_layer_"+getArmorLayer(slot)):"")+".png";
	    	} else {
	    		return null;
	    	}

	    }
		
		
}
