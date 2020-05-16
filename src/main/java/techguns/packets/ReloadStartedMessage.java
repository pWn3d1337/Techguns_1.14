package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.client.ShooterValues;

public class ReloadStartedMessage implements IMessage {

	protected int entityID;
	protected int time;
	protected byte attackType;
	protected boolean offHand;
	
	
    public ReloadStartedMessage() 
    { 
     // need this constructor
    }
    
    public ReloadStartedMessage(LivingEntity shooter, Hand hand, int firetime, int attackType)
    { 
    	entityID = shooter.getEntityId();
    	time = firetime;
    	this.attackType=(byte)attackType;
    	this.offHand = hand== Hand.OFF_HAND;
    }
	
	@Override
	public void fromBytes(ByteBuf buf) {
		entityID=buf.readInt();
		time=buf.readInt();
		attackType=buf.readByte();
		this.offHand=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(time);
		buf.writeByte(attackType);
		buf.writeBoolean(offHand);
	}

	public static class Handler implements IMessageHandler<ReloadStartedMessage, IMessage> {
		@Override
		public IMessage onMessage(ReloadStartedMessage message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(ReloadStartedMessage message, MessageContext ctx) {
						
			PlayerEntity ply = TGPackets.getPlayerFromContext(ctx);
			
			LivingEntity shooter = (LivingEntity) ply.world.getEntityByID(message.entityID);
			
			if (shooter !=null){
				if (shooter!=Minecraft.getMinecraft().player){

					ShooterValues.setReloadtime(shooter, message.offHand, System.currentTimeMillis()+message.time, message.time, message.attackType);
					//System.out.println("MESSAGE, gun fired:"+shooter.getClass().toString()+" "+message.time);
				}
			}	
		}
	}
	
}