package us.mcsw.core.util;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import us.mcsw.minerad.MineRad;

public class LogUtil {

	public static void log(Level lvl, Object obj) {
		FMLLog.log(MineRad.MOD_NAME, lvl, obj.toString());
	}
	
	public static void debug(Object obj) {
		log(Level.DEBUG, obj);
	}
	
	public static void error(Object obj) {
		log(Level.ERROR, obj);
	}
	
	public static void fatal(Object obj) {
		log(Level.FATAL, obj);
	}
	
	public static void info(Object obj) {
		log(Level.INFO, obj);
	}
	
	public static void trace(Object obj) {
		log(Level.TRACE, obj);
	}
	
	public static void warn(Object obj) {
		log(Level.WARN, obj);
	}

}
