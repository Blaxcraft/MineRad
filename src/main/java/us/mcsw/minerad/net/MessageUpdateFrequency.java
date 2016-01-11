package us.mcsw.minerad.net;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.minerad.tiles.TileRadioTowerBase;

public class MessageUpdateFrequency implements IMessage {

	public int x, y, z, freq;

	public MessageUpdateFrequency() {
	}

	public MessageUpdateFrequency(int x, int y, int z, int freq) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.freq = freq;
	}

	public static class Handler implements IMessageHandler<MessageUpdateFrequency, IMessage> {

		@Override
		public IMessage onMessage(MessageUpdateFrequency message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
			if (te instanceof TileRadioTowerBase) {
				TileRadioTowerBase tile = (TileRadioTowerBase) te;
				int bef = tile.freq;
				tile.freq = message.freq;
				tile.markDirty();
			}
			return null;
		}

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = ByteBufUtils.readVarInt(buf, 5);
		y = ByteBufUtils.readVarInt(buf, 5);
		z = ByteBufUtils.readVarInt(buf, 5);
		freq = ByteBufUtils.readVarShort(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, x, 5);
		ByteBufUtils.writeVarInt(buf, y, 5);
		ByteBufUtils.writeVarInt(buf, z, 5);
		ByteBufUtils.writeVarShort(buf, freq);
	}

}
