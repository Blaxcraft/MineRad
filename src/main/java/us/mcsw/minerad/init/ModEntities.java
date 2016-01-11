package us.mcsw.minerad.init;

import java.awt.Color;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.entity.EntityArcThrowerProjectile;
import us.mcsw.minerad.entity.EntityFeralGhoul;
import us.mcsw.minerad.entity.EntityFinalBoss;
import us.mcsw.minerad.entity.EntityHomingBoss;
import us.mcsw.minerad.entity.EntitySpawningProjectile;
import us.mcsw.minerad.entity.EntityStationaryMarker;
import us.mcsw.minerad.entity.EntityThrownMarker;

public class ModEntities {

	public static void init() {
		EntityRegistry.registerModEntity(EntityThrownMarker.class, "thrownMarker", 0, MineRad.ins, 80, 4, true);
		EntityRegistry.registerModEntity(EntityStationaryMarker.class, "stationaryMarker", 1, MineRad.ins, 80, 5,
				false);
		EntityRegistry.registerModEntity(EntityArcThrowerProjectile.class, "arcThrowerProjectile", 2, MineRad.ins, 50,
				2, true);
		EntityRegistry.registerModEntity(EntityHomingBoss.class, "homingBoss", 3, MineRad.ins, 50, 2, true);
		EntityRegistry.registerModEntity(EntitySpawningProjectile.class, "spawningProjectile", 4, MineRad.ins, 50, 2,
				true);

		EntityRegistry.registerGlobalEntityID(EntityFeralGhoul.class, "feralGhoul", ConfigMR.FERAL_GHOUL_ID,
				new Color(230, 230, 200).getRGB(), new Color(150, 150, 125).getRGB());
		EntityRegistry.addSpawn(EntityFeralGhoul.class, 6, 1, 4, EnumCreatureType.monster, MineRad.wasteland);

		EntityRegistry.registerGlobalEntityID(EntityFinalBoss.class, "finalBoss", ConfigMR.BOSS_ID);
	}

}
