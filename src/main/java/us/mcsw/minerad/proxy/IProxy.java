package us.mcsw.minerad.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

	public abstract void init(FMLInitializationEvent event);
	
	public abstract void preInit(FMLPreInitializationEvent event);
	
	public abstract void postInit(FMLPostInitializationEvent event);
	
}
