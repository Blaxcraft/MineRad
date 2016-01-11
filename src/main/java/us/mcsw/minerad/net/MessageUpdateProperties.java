package us.mcsw.minerad.net;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IExtendedEntityProperties;

public class MessageUpdateProperties implements IMessage {

	NBTTagCompound data;
	String id;
	int entId;
	
	public MessageUpdateProperties() {
		data = new NBTTagCompound();
	}

	public MessageUpdateProperties(IExtendedEntityProperties props, String propsId, int entId) {
		data = new NBTTagCompound();
		props.saveNBTData(data);
		this.id = propsId;
		this.entId = entId;
	}

	public static class Handler implements IMessageHandler<MessageUpdateProperties, IMessage> {
		@Override
		public IMessage onMessage(MessageUpdateProperties message, MessageContext ctx) {
			EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
			Entity e = pl.worldObj.getEntityByID(message.entId);
			IExtendedEntityProperties props = e.getExtendedProperties(message.id);
			props.loadNBTData(message.data);
			return null;
		}

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		data = ByteBufUtils.readTag(buf);
		entId = ByteBufUtils.readVarShort(buf);
		id = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, data);
		ByteBufUtils.writeVarShort(buf, entId);
		ByteBufUtils.writeUTF8String(buf, id);
	}

}
