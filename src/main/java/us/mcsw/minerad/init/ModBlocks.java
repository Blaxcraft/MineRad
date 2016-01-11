package us.mcsw.minerad.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import us.mcsw.minerad.blocks.BlockAutoDropper;
import us.mcsw.minerad.blocks.BlockCraftingMachine;
import us.mcsw.minerad.blocks.BlockEnergyStorage;
import us.mcsw.minerad.blocks.BlockFilter;
import us.mcsw.minerad.blocks.BlockFissionReactor;
import us.mcsw.minerad.blocks.BlockFissionReactor.ItemBlockFissionReactor;
import us.mcsw.minerad.blocks.BlockFusionReactor;
import us.mcsw.minerad.blocks.BlockGhoulLight;
import us.mcsw.minerad.blocks.BlockFusionReactor.ItemBlockFusionReactor;
import us.mcsw.minerad.blocks.BlockGroundZero;
import us.mcsw.minerad.blocks.BlockLead;
import us.mcsw.minerad.blocks.BlockMagnet;
import us.mcsw.minerad.blocks.BlockMicrowave;
import us.mcsw.minerad.blocks.BlockNuclearArsenal;
import us.mcsw.minerad.blocks.BlockOreLead;
import us.mcsw.minerad.blocks.BlockPipe;
import us.mcsw.minerad.blocks.BlockPlutonium;
import us.mcsw.minerad.blocks.BlockPureNeptunium;
import us.mcsw.minerad.blocks.BlockPureUranium;
import us.mcsw.minerad.blocks.BlockRadioTowerAntenna;
import us.mcsw.minerad.blocks.BlockRadioTowerBase;
import us.mcsw.minerad.blocks.BlockRadioactiveGenerator;
import us.mcsw.minerad.blocks.BlockShieldGenerator;
import us.mcsw.minerad.blocks.BlockInfuser;
import us.mcsw.minerad.blocks.BlockUraniumLump;
import us.mcsw.minerad.blocks.BlockUraniumOre;
import us.mcsw.minerad.blocks.BlockUraniumTraces;
import us.mcsw.minerad.blocks.BlockWaterDistiller;
import us.mcsw.minerad.items.ItemEnergyStorage;
import us.mcsw.minerad.items.ItemPipe;

public class ModBlocks {

	public static final BlockUraniumTraces uraniumTraces = new BlockUraniumTraces();
	public static final BlockUraniumLump uraniumLump = new BlockUraniumLump();
	public static final BlockUraniumOre uraniumOre = new BlockUraniumOre();

	public static final BlockOreLead oreLead = new BlockOreLead();

	public static final BlockPureUranium pureUranium = new BlockPureUranium();
	public static final BlockPureNeptunium pureNeptunium = new BlockPureNeptunium();
	public static final BlockPlutonium blockPlutonium = new BlockPlutonium();
	public static final BlockLead blockLead = new BlockLead();

	public static final BlockFusionReactor fusionReactor = new BlockFusionReactor();
	public static final BlockFissionReactor fissionReactor = new BlockFissionReactor();

	public static final BlockNuclearArsenal nuclearArsenal = new BlockNuclearArsenal();

	public static final BlockGroundZero groundZero = new BlockGroundZero();

	public static final BlockMagnet magnet = new BlockMagnet();
	public static final BlockMicrowave microwave = new BlockMicrowave();
	public static final BlockFilter filter = new BlockFilter();
	public static final BlockAutoDropper autoDropper = new BlockAutoDropper();

	public static final BlockCraftingMachine basicMachineBlock = new BlockCraftingMachine("basicMachineBlock");
	public static final BlockCraftingMachine advancedMachineBlock = new BlockCraftingMachine("advancedMachineBlock");
	public static final BlockInfuser infuser = new BlockInfuser();
	public static final BlockRadioTowerBase radioTowerBase = new BlockRadioTowerBase();
	public static final BlockRadioTowerAntenna radioTowerAntenna = new BlockRadioTowerAntenna();
	public static final BlockRadioactiveGenerator radioactiveGenerator = new BlockRadioactiveGenerator();
	public static final BlockShieldGenerator shieldGenerator = new BlockShieldGenerator();
	public static final BlockWaterDistiller waterDistiller = new BlockWaterDistiller();

	public static final BlockPipe pipeBlock = new BlockPipe();
	public static final BlockEnergyStorage energyStorage = new BlockEnergyStorage();

	public static final BlockGhoulLight ghoulLight = new BlockGhoulLight();

	public static void init() {
		GameRegistry.registerBlock(uraniumTraces, uraniumTraces.getBasicName());
		GameRegistry.registerBlock(uraniumLump, uraniumLump.getBasicName());
		GameRegistry.registerBlock(uraniumOre, uraniumOre.getBasicName());

		GameRegistry.registerBlock(oreLead, oreLead.getBasicName());
		OreDictionary.registerOre("oreLead", oreLead);

		GameRegistry.registerBlock(pureUranium, pureUranium.getBasicName());
		GameRegistry.registerBlock(pureNeptunium, pureNeptunium.getBasicName());
		GameRegistry.registerBlock(blockPlutonium, blockPlutonium.getBasicName());
		GameRegistry.registerBlock(blockLead, blockLead.getBasicName());
		OreDictionary.registerOre("blockLead", blockLead);

		GameRegistry.registerBlock(fusionReactor, ItemBlockFusionReactor.class, fusionReactor.getBasicName());
		GameRegistry.registerBlock(fissionReactor, ItemBlockFissionReactor.class, fissionReactor.getBasicName());

		GameRegistry.registerBlock(nuclearArsenal, nuclearArsenal.getBasicName());

		GameRegistry.registerBlock(groundZero, groundZero.getBasicName());

		GameRegistry.registerBlock(magnet, magnet.getBasicName());
		GameRegistry.registerBlock(microwave, microwave.getBasicName());
		GameRegistry.registerBlock(filter, filter.getBasicName());
		GameRegistry.registerBlock(autoDropper, autoDropper.getBasicName());

		GameRegistry.registerBlock(basicMachineBlock, basicMachineBlock.getBasicName());
		GameRegistry.registerBlock(advancedMachineBlock, advancedMachineBlock.getBasicName());
		GameRegistry.registerBlock(infuser, infuser.getBasicName());
		GameRegistry.registerBlock(radioTowerBase, radioTowerBase.getBasicName());
		GameRegistry.registerBlock(radioTowerAntenna, radioTowerAntenna.getBasicName());
		GameRegistry.registerBlock(radioactiveGenerator, radioactiveGenerator.getBasicName());
		GameRegistry.registerBlock(shieldGenerator, shieldGenerator.getBasicName());
		GameRegistry.registerBlock(waterDistiller, waterDistiller.getBasicName());

		GameRegistry.registerBlock(pipeBlock, ItemPipe.class, pipeBlock.getBasicName());
		GameRegistry.registerBlock(energyStorage, ItemEnergyStorage.class, energyStorage.getBasicName());
		
		GameRegistry.registerBlock(ghoulLight, ghoulLight.getBasicName());
	}

}
