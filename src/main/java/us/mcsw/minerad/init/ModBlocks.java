package us.mcsw.minerad.init;

import cpw.mods.fml.common.registry.GameRegistry;
import us.mcsw.minerad.blocks.BlockFissionReactor;
import us.mcsw.minerad.blocks.BlockFusionReactor;
import us.mcsw.minerad.blocks.BlockGroundZero;
import us.mcsw.minerad.blocks.BlockNuclearArsenal;
import us.mcsw.minerad.blocks.BlockPlutonium;
import us.mcsw.minerad.blocks.BlockPureNeptunium;
import us.mcsw.minerad.blocks.BlockPureUranium;
import us.mcsw.minerad.blocks.BlockTest;
import us.mcsw.minerad.blocks.BlockUraniumInfuser;
import us.mcsw.minerad.blocks.BlockUraniumLump;
import us.mcsw.minerad.blocks.BlockUraniumOre;
import us.mcsw.minerad.blocks.BlockUraniumTraces;

public class ModBlocks {

	public static final BlockUraniumTraces uraniumTraces = new BlockUraniumTraces();
	public static final BlockUraniumLump uraniumLump = new BlockUraniumLump();
	public static final BlockUraniumOre uraniumOre = new BlockUraniumOre();

	public static final BlockPureUranium pureUranium = new BlockPureUranium();
	public static final BlockPureNeptunium pureNeptunium = new BlockPureNeptunium();
	public static final BlockPlutonium blockPlutonium = new BlockPlutonium();

	public static final BlockFusionReactor fusionReactor = new BlockFusionReactor();
	public static final BlockFissionReactor fissionReactor = new BlockFissionReactor();

	public static final BlockNuclearArsenal nuclearArsenal = new BlockNuclearArsenal();

	public static final BlockGroundZero groundZero = new BlockGroundZero();
	
	public static final BlockUraniumInfuser uraniumInfuser = new BlockUraniumInfuser();

	public static final BlockTest blockTest = new BlockTest();

	public static void init() {
		GameRegistry.registerBlock(uraniumTraces, uraniumTraces.getBasicName());
		GameRegistry.registerBlock(uraniumLump, uraniumLump.getBasicName());
		GameRegistry.registerBlock(uraniumOre, uraniumOre.getBasicName());

		GameRegistry.registerBlock(pureUranium, pureUranium.getBasicName());
		GameRegistry.registerBlock(pureNeptunium, pureNeptunium.getBasicName());
		GameRegistry.registerBlock(blockPlutonium, blockPlutonium.getBasicName());

		GameRegistry.registerBlock(fusionReactor, fusionReactor.getBasicName());
		GameRegistry.registerBlock(fissionReactor, fissionReactor.getBasicName());

		GameRegistry.registerBlock(nuclearArsenal, nuclearArsenal.getBasicName());
		
		GameRegistry.registerBlock(groundZero, groundZero.getBasicName());
		
		GameRegistry.registerBlock(uraniumInfuser, uraniumInfuser.getBasicName());

		GameRegistry.registerBlock(blockTest, blockTest.getBasicName());
	}

}
