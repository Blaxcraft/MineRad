package us.mcsw.minerad.init;

import cpw.mods.fml.common.registry.EntityRegistry;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.entity.EntityStationaryMarker;
import us.mcsw.minerad.entity.EntityThrownMarker;

public class ModEntities {
	
	public static void init() {
		EntityRegistry.registerModEntity(EntityThrownMarker.class, "thrownMarker", 0, MineRad.ins, 80, 4, true);
		EntityRegistry.registerModEntity(EntityStationaryMarker.class, "stationaryMarker", 1, MineRad.ins, 80, 5, false);
	}

}
