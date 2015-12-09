package us.mcsw.core.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import us.mcsw.minerad.MineRad;

public class ChatUtil {

	public static String prefix = EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.GREEN + MineRad.MOD_NAME
			+ EnumChatFormatting.DARK_GRAY + "] " + EnumChatFormatting.WHITE;

	public static void sendChatTo(EntityPlayer p, String msg) {
		p.addChatMessage(new ChatComponentText(prefix + msg));
	}

	public static void sendTranslatedTo(EntityPlayer p, String msg, Object... translate) {
		p.addChatMessage(new ChatComponentText(prefix).appendSibling(new ChatComponentTranslation(msg, translate)));
	}

}
