package us.mcsw.minerad.net;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.util.RadUtil;

public class MessageUpdateEmitters implements IMessage {

	NBTTagList sources = null;

	public MessageUpdateEmitters() {
	}

	public MessageUpdateEmitters(NBTTagList sources) {
		this.sources = sources;
	}

	public static class Handler implements IMessageHandler<MessageUpdateEmitters, IMessage> {
		@Override
		public IMessage onMessage(MessageUpdateEmitters message, MessageContext ctx) {
			World world = MineRad.proxy.getWorldFromContext(ctx);
			RadUtil.forWorld(world).setSourcesList(message.sources);
			return null;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound read = ByteBufUtils.readTag(buf);
		sources = read.getTagList("Sources", 10);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound write = new NBTTagCompound();
		write.setTag("Sources", sources);
		ByteBufUtils.writeTag(buf, write);
	}

}
