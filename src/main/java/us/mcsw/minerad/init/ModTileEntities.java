package us.mcsw.minerad.init;

import cpw.mods.fml.common.registry.GameRegistry;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;
import us.mcsw.minerad.tiles.TileTestRF;
import us.mcsw.minerad.tiles.TileUraniumInfuser;

public class ModTileEntities {

	public static void init() {
		GameRegistry.registerTileEntity(TileFusionReactor.class, MineRad.MODID + ".multiblock.fusionReactor");
		GameRegistry.registerTileEntity(TileFissionReactor.class, MineRad.MODID + ".multiblock.fissionReactor");
		
		GameRegistry.registerTileEntity(TileUraniumInfuser.class, MineRad.MODID + ".machine.uraniumInfuser");
		
		GameRegistry.registerTileEntity(TileTestRF.class, MineRad.MODID + ".testrf");
	}

}
