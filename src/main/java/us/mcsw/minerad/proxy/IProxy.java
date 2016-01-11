package us.mcsw.minerad.proxy;

import java.awt.Color;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.world.World;
import us.mcsw.minerad.entity.EntityFinalBoss;

public interface IProxy {

	public abstract void init(FMLInitializationEvent event);

	public abstract void preInit(FMLPreInitializationEvent event);

	public abstract void postInit(FMLPostInitializationEvent event);

	public abstract void generateShieldParticles(World w, double cx, double cy, double cz, double radius, int amount,
			Color colour);

	public abstract void generateBossParticles(EntityFinalBoss boss);

}
