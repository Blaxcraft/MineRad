package us.mcsw.core.util;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.resources.I18n;

public class LangUtil {
	
	public static String translate(String trans, Object... format) {
		return String.format(LanguageRegistry.instance().getStringLocalization(trans), format);
	}

}
