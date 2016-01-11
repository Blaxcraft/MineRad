package us.mcsw.minerad.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.ref.CapacitorTier;

public class MRRecipes {

	public static void init() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.geigerCounter), "IGI", "IUI", "SSS", 'I',
				"ingotIron", 'G', "blockGlass", 'U', ModItems.uraniumChunk, 'S', "stone"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.emptyCore, 2), " I ", "IUI", " I ", 'I',
				"ingotIron", 'U', ModItems.uraniumChunk));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fusionCore), "DTD", "TCT", "DTD", 'D',
				ModItems.deuteriumCore, 'T', ModItems.tritiumCore, 'C', ModItems.emptyCore));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fusionCore), "TDT", "DCD", "TDT", 'D',
				ModItems.deuteriumCore, 'T', ModItems.tritiumCore, 'C', ModItems.emptyCore));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fissionReactor, 2), "LUL", "UBU", "LUL", 'L',
				"ingotLead", 'B', ModBlocks.basicMachineBlock, 'U', ModItems.uraniumChunk));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.fusionReactor, 2), "LBL", "UPU", "LBL", 'L', "ingotLead",
						'U', ModItems.purifiedUranium, 'P', ModItems.plutonium, 'B', ModBlocks.advancedMachineBlock));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radResistantPlating), "IUI", "UIU", 'I',
				"ingotIron", 'U', ModItems.uraniumChunk));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radResistantPlating), "UIU", "IUI", 'I',
				"ingotIron", 'U', ModItems.uraniumChunk));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.dustUranium, 3),
				new ItemStack(ModItems.diamondGrinder, 1, OreDictionary.WILDCARD_VALUE), ModItems.uraniumOreItem));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.acidSolution), Items.bowl,
				Items.water_bucket, Items.gunpowder));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.yellowCake), "dustUranium", "dustUranium",
				"dustUranium", ModItems.acidSolution));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.diamondGrinder), " D ", "IBI", 'D',
				"gemDiamond", 'I', "ingotIron", 'B', "blockIron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(CapacitorTier.BASIC.getCapacitor(), "RRR", "RUR", "UIU", 'R',
				Items.redstone, 'U', ModItems.uraniumChunk, 'I', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(CapacitorTier.IRON.getCapacitor(), "RIR", "ICI", "RIR", 'R',
				Items.redstone, 'I', "ingotIron", 'C', CapacitorTier.BASIC.getCapacitor()));
		GameRegistry.addRecipe(new ShapedOreRecipe(CapacitorTier.GOLD.getCapacitor(), "RGR", "CRC", "GRG", 'R',
				Items.redstone, 'G', "ingotGold", 'C', CapacitorTier.IRON.getCapacitor()));
		GameRegistry.addRecipe(new ShapedOreRecipe(CapacitorTier.DIAMOND.getCapacitor(), "RDR", "CRC", "DRD", 'R',
				Items.redstone, 'D', "gemDiamond", 'C', CapacitorTier.GOLD.getCapacitor()));
		GameRegistry.addRecipe(new ShapedOreRecipe(CapacitorTier.QUARTZ.getCapacitor(), "RQR", "QRQ", "CQC", 'R',
				Items.redstone, 'Q', Blocks.quartz_block, 'C', CapacitorTier.DIAMOND.getCapacitor()));

		GameRegistry.addSmelting(ModItems.yellowCake, new ItemStack(ModItems.purifiedUranium), 0.5f);
		GameRegistry.addSmelting(ModBlocks.oreLead, new ItemStack(ModItems.ingotLead), 0.25f);
		GameRegistry.addSmelting(ModItems.dustLead, new ItemStack(ModItems.ingotLead), 0.25f);

		GameRegistry.addRecipe(new ItemStack(ModBlocks.pureUranium), "UUU", "UUU", "UUU", 'U',
				ModItems.purifiedUranium);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.pureNeptunium), "NNN", "NNN", "NNN", 'N',
				ModItems.purifiedNeptunium);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPlutonium), "PPP", "PPP", "PPP", 'P', ModItems.plutonium);
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockLead, "LLL", "LLL", "LLL", 'L', "ingotLead"));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.purifiedUranium, 9), ModBlocks.pureUranium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.purifiedNeptunium, 9), ModBlocks.pureNeptunium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.plutonium, 9), ModBlocks.blockPlutonium);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingotLead, 9), "blockLead"));

		GameRegistry.addRecipe(new ItemStack(ModItems.purifiedUranium), "UUU", "UUU", "UUU", 'U',
				ModItems.nuggetUranium);
		GameRegistry.addRecipe(new ItemStack(ModItems.purifiedNeptunium), "NNN", "NNN", "NNN", 'N',
				ModItems.nuggetNeptunium);
		GameRegistry.addRecipe(new ItemStack(ModItems.plutonium), "PPP", "PPP", "PPP", 'P', ModItems.nuggetPlutonium);
		GameRegistry.addRecipe(new ItemStack(ModItems.unknownElement), "UUU", "UUU", "UUU", 'U',
				ModItems.unknownNugget);
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.ingotLead), "LLL", "LLL", "LLL", 'L', "nuggetLead"));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuggetUranium, 9), ModItems.purifiedUranium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuggetNeptunium, 9), ModItems.purifiedNeptunium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuggetPlutonium, 9), ModItems.plutonium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.unknownNugget, 9), ModItems.unknownElement);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.nuggetLead, 9), "ingotLead"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radHelm), "PWP", "W W", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radChest), "W W", "PWP", "WWW", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radLegs), "PWP", "W W", "W W", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radBoots), "W W", "P P", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.hoverLegs, "DYD", "YLY", "DED", 'D', "gemDiamond", 'Y',
				"dyeYellow", 'E', ModItems.exitite, 'L', Items.diamond_leggings));

		if (ConfigMR.HARD_NUKE_RECIPE) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nukePackage), "INI", "TPT", "INI", 'I',
					"blockGold", 'N', ModItems.nuclearPod, 'T', Blocks.tnt, 'P', ModBlocks.blockPlutonium));
		} else {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nukePackage), "INI", "TPT", "INI", 'I',
					"blockIron", 'N', ModItems.nuclearPod, 'T', Blocks.tnt, 'P', ModItems.plutonium));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.nuclearArsenal), "PIP", "NRN", "PIP", 'P',
				ModItems.nuclearPod, 'I', "blockIron", 'N', ModItems.plutonium, 'R', ModItems.radioReceiver));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nuclearPod), "III", "NCN", "III", 'I',
				"ingotIron", 'N', ModItems.purifiedNeptunium, 'C', Blocks.chest));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radioReceiver), " B ", "NBN", "RIR", 'B',
				Blocks.iron_bars, 'N', ModItems.nuggetUranium, 'R', Items.redstone, 'I', "blockIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radioEmitter), " B ", "NBN", "RIR", 'B',
				Blocks.iron_bars, 'N', ModItems.nuggetPlutonium, 'R', Items.redstone, 'I', "blockIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nuclearArsenalUpgrade), "IPI", "PNP", "IPI",
				'I', "ingotIron", 'P', ModItems.nuclearPod, 'N', ModItems.purifiedNeptunium));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nukeMarker), " NN", " IN", "I  ", 'I',
				"ingotIron", 'N', ModItems.nuggetNeptunium));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.basicMachineBlock, "IRI", "RUR", "IRI", 'I', "ingotIron",
				'R', Items.redstone, 'U', ModItems.uraniumChunk));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.advancedMachineBlock, "LIL", "GBG", "LIL", 'B',
				ModBlocks.basicMachineBlock, 'U', ModItems.purifiedUranium, 'L', "ingotLead", 'I', "ingotIron", 'G',
				Blocks.glowstone));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.infuser, "CCC", "IBI", "IAI", 'C', ModItems.emptyCore, 'I',
				"ingotIron", 'B', ModBlocks.basicMachineBlock, 'A', CapacitorTier.IRON.getCapacitor()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.waterDistiller, "BBB", "EME", "UCU", 'B', Items.bucket,
				'E', ModItems.emptyCore, 'M', ModBlocks.basicMachineBlock, 'U', ModItems.purifiedUranium, 'C',
				CapacitorTier.GOLD.getCapacitor()));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(ModBlocks.radioTowerBase, "APA", "TBR", "PCP", 'A', ModBlocks.radioTowerAntenna,
						'P', ModItems.plutonium, 'T', ModItems.radioEmitter, 'B', ModBlocks.advancedMachineBlock, 'R',
						ModItems.radioReceiver, 'C', CapacitorTier.DIAMOND.getCapacitor()));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.radioTowerAntenna, 2), "BTB", "BIB", "BTB",
				'B', Blocks.iron_bars, 'T', ModItems.radioEmitter, 'I', "ingotIron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.magnetItem, " R ", "BPB", " R ", 'R', Items.redstone, 'B',
				ModBlocks.magnet, 'P', Items.ender_pearl));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.magnet, 2), "RIR", "CBC", "RGR", 'I', "ingotIron", 'C',
						ModItems.denseCore, 'B', ModBlocks.basicMachineBlock, 'G', "ingotGold", 'R', Items.redstone));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.denseCore, ModItems.unknownNugget, ModItems.emptyCore));

		ItemStack arcThrower = new ItemStack(ModItems.arcThrower);
		ModItems.arcThrower.setEnergyInItemStack(arcThrower, 0);
		GameRegistry.addRecipe(
				new ShapedOreRecipe(arcThrower, " IQ", "PQC", "  B", 'I', "ingotIron", 'Q', Blocks.quartz_block, 'P',
						ModItems.plutonium, 'C', CapacitorTier.DIAMOND.getCapacitor(), 'B', "blockIron"));

		ItemStack radSensor = new ItemStack(ModItems.xray);
		ModItems.xray.setEnergyInItemStack(radSensor, 0);
		GameRegistry.addRecipe(
				new ShapedOreRecipe(radSensor, "TIR", "IGI", "CIC", 'T', ModItems.radioEmitter, 'I', "ingotIron", 'R',
						ModItems.radioReceiver, 'G', ModItems.geigerCounter, 'C', CapacitorTier.QUARTZ.getCapacitor()));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.microwave, "IMI", "NBN", "RCR", 'I', "blockIron", 'M',
				ModItems.microwaveModule, 'N', ModItems.nuggetPlutonium, 'B', ModBlocks.advancedMachineBlock, 'C',
				CapacitorTier.GOLD.getCapacitor(), 'R', Items.redstone));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.microwaveModule, "IUI", "URU", "IUI", 'I', "ingotIron", 'U',
				ModItems.purifiedUranium, 'R', ModItems.radioEmitter));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.filter, 4), "IGI", "GHG", "IGI", 'I',
				"ingotIron", 'G', "blockGlass", 'H', Blocks.hopper));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.autoDropper, "CRC", "RDR", "CTC", 'C', Blocks.cobblestone,
				'R', Items.redstone, 'D', Blocks.dropper, 'T', Blocks.redstone_torch));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.energyPulsar, "LPL", "GEG", "LPL", 'L', "ingotLead", 'P',
				ModItems.plutonium, 'G', "blockGlass", 'E', ModItems.radioEmitter));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.shieldGenerator, "GCG", "PBP", "GCG", 'G',
				Blocks.glowstone, 'P', ModItems.energyPulsar, 'B', ModBlocks.advancedMachineBlock, 'C',
				CapacitorTier.DIAMOND.getCapacitor()));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.shieldUpgrade, 2), "ILI", "LPL", "ILI", 'I',
				"ingotIron", 'L', "ingotLead", 'P', ModItems.energyPulsar));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.radioactiveGenerator, "HHH", "UBU", "CKC", 'H',
				ModItems.uraniumChunk, 'U', ModItems.purifiedUranium, 'B', ModBlocks.basicMachineBlock, 'C',
				CapacitorTier.GOLD.getCapacitor(), 'K', Items.bucket));

		for (CapacitorTier ct : CapacitorTier.tiers) {
			ItemStack result = new ItemStack(ModBlocks.pipeBlock, 6);
			CapacitorTier.setInItemStack(result, ct);
			GameRegistry.addRecipe(new ShapedOreRecipe(result, "RRR", "CGC", "RRR", 'R', Items.redstone, 'G',
					new ItemStack(Blocks.glass, 1, OreDictionary.WILDCARD_VALUE), 'C', ct.getCapacitor()));

			ItemStack storage = new ItemStack(ModBlocks.energyStorage);
			CapacitorTier.setInItemStack(storage, ct);
			GameRegistry.addRecipe(new ShapedOreRecipe(storage, "CRC", "RBR", "CRC", 'C', ct.getCapacitor(), 'R',
					Blocks.redstone_block, 'B', ModBlocks.advancedMachineBlock));
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.densePouch, "WGW", "UCU", "WGW", 'U',
				ModItems.unknownNugget, 'G', Blocks.glass, 'W',
				new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), 'C', Blocks.chest));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.radAway), ModItems.acidSolution,
				Items.sugar, ModItems.radX));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.radX), Items.glass_bottle, Items.melon,
				Items.nether_wart));
	}

}
