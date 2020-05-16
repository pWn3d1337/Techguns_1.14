package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.tileentities.operation.ITileEntityFluidTanks;

public class PacketUpdateTileEntTanks implements IMessage {

	int tileX;
	int tileY;
	int tileZ;
	CompoundNBT tankTags;
	
	public PacketUpdateTileEntTanks(ITileEntityFluidTanks tile, BlockPos pos) {
		this.tankTags = new CompoundNBT();
		tile.saveTanksToNBT(tankTags);
		this.tileX= pos.getX();
		this.tileY=pos.getY();
		this.tileZ=pos.getZ();
	}
	
	public PacketUpdateTileEntTanks() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.tileX=buf.readInt();
		this.tileY=buf.readInt();
		this.tileZ=buf.readInt();
		tankTags = ByteBufUtils.readTag(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(tileX);
		buf.writeInt(tileY);
		buf.writeInt(tileZ);
		ByteBufUtils.writeTag(buf, tankTags);
	}
	
	
	public static class Handler extends HandlerTemplate<PacketUpdateTileEntTanks> {
		@Override
		protected void handle(PacketUpdateTileEntTanks m, MessageContext ctx) {
			if (m.tankTags!=null) {
				BlockPos p = new BlockPos(m.tileX, m.tileY, m.tileZ);
				TileEntity tile = TGPackets.getPlayerFromContext(ctx).world.getTileEntity(p);
				if (tile != null && tile instanceof ITileEntityFluidTanks) {
					((ITileEntityFluidTanks)tile).loadTanksFromNBT(m.tankTags);
				}
			}
		}
	}
}