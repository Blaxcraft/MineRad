package us.mcsw.minerad.init;

import cpw.mods.fml.common.registry.GameRegistry;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;
import us.mcsw.minerad.tiles.TileMagnet;
import us.mcsw.minerad.tiles.TileMicrowave;
import us.mcsw.minerad.tiles.TilePipe;
import us.mcsw.minerad.tiles.TileRadioTowerAntenna;
import us.mcsw.minerad.tiles.TileRadioTowerBase;
import us.mcsw.minerad.tiles.TileTestRF;
import us.mcsw.minerad.tiles.TileUraniumInfuser;

public class ModTileEntities {

	public static void init() {
		GameRegistry.registerTileEntity(TileFusionReactor.class, MineRad.MODID + ".multiblock.fusionReactor");
		GameRegistry.registerTileEntity(TileFissionReactor.class, MineRad.MODID + ".multiblock.fissionReactor");

		GameRegistry.registerTileEntity(TileMagnet.class, MineRad.MODID + ".magnet");
		GameRegistry.registerTileEntity(TileMicrowave.class, MineRad.MODID + ".microwave");

		GameRegistry.registerTileEntity(TileUraniumInfuser.class, MineRad.MODID + ".machine.uraniumInfuser");
		GameRegistry.registerTileEntity(TileRadioTowerBase.class, MineRad.MODID + ".machine.radioTowerBase");
		GameRegistry.registerTileEntity(TileRadioTowerAntenna.class, MineRad.MODID + ".radioTower");

		GameRegistry.registerTileEntity(TilePipe.class, MineRad.MODID + ".pipe");

		GameRegistry.registerTileEntity(TileTestRF.class, MineRad.MODID + ".testrf");
	}

}
