package us.mcsw.minerad.init;

import cpw.mods.fml.common.registry.GameRegistry;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.tiles.TileAutoDropper;
import us.mcsw.minerad.tiles.TileEnergyStorage;
import us.mcsw.minerad.tiles.TileFilter;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;
import us.mcsw.minerad.tiles.TileGhoulLight;
import us.mcsw.minerad.tiles.TileMagnet;
import us.mcsw.minerad.tiles.TileMicrowave;
import us.mcsw.minerad.tiles.TilePipe;
import us.mcsw.minerad.tiles.TileRadEmitter;
import us.mcsw.minerad.tiles.TileRadioAntenna;
import us.mcsw.minerad.tiles.TileRadioTowerBase;
import us.mcsw.minerad.tiles.TileRadioactiveGenerator;
import us.mcsw.minerad.tiles.TileShieldGenerator;
import us.mcsw.minerad.tiles.TileInfuser;
import us.mcsw.minerad.tiles.TileWaterDistiller;

public class ModTileEntities {

	public static void init() {
		GameRegistry.registerTileEntity(TileFusionReactor.class, MineRad.MODID + ".multiblock.fusionReactor");
		GameRegistry.registerTileEntity(TileFissionReactor.class, MineRad.MODID + ".multiblock.fissionReactor");

		GameRegistry.registerTileEntity(TileMagnet.class, MineRad.MODID + ".magnet");
		GameRegistry.registerTileEntity(TileMicrowave.class, MineRad.MODID + ".microwave");
		GameRegistry.registerTileEntity(TileFilter.class, MineRad.MODID + ".filter");
		GameRegistry.registerTileEntity(TileAutoDropper.class, MineRad.MODID + ".autodropper");

		GameRegistry.registerTileEntity(TileInfuser.class, MineRad.MODID + ".machine.uraniumInfuser");
		GameRegistry.registerTileEntity(TileRadioTowerBase.class, MineRad.MODID + ".machine.radioTowerBase");
		GameRegistry.registerTileEntity(TileRadioAntenna.class, MineRad.MODID + ".radioTower");
		GameRegistry.registerTileEntity(TileRadioactiveGenerator.class, MineRad.MODID + ".generator.radioactive");
		GameRegistry.registerTileEntity(TileShieldGenerator.class, MineRad.MODID + ".machine.shieldGenerator");
		GameRegistry.registerTileEntity(TileWaterDistiller.class, MineRad.MODID + ".machine.waterDistiller");

		GameRegistry.registerTileEntity(TilePipe.class, MineRad.MODID + ".pipe");
		GameRegistry.registerTileEntity(TileEnergyStorage.class, MineRad.MODID + ".energyStorage");

		GameRegistry.registerTileEntity(TileRadEmitter.class, MineRad.MODID + ".radEmitter");
		
		GameRegistry.registerTileEntity(TileGhoulLight.class, MineRad.MODID + ".ghoullight");
	}

}
