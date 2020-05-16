package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketNotifyAmbientEffectChange implements IMessage {
	int entityId;
	Hand hand;
	
	public PacketNotifyAmbientEffectChange() {}
	
	public PacketNotifyAmbientEffectChange(LivingEntity entity, Hand hand) {
		super();
		this.entityId = entity.getEntityId();
		this.hand = hand;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		boolean offhand = buf.readBoolean();
		if(offhand) {
			this.hand= Hand.OFF_HAND;
		} else {
			this.hand= Hand.MAIN_HAND;
		}
		this.entityId=buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(hand== Hand.OFF_HAND);
		buf.writeInt(entityId);
	}
	
}
