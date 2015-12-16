package us.mcsw.minerad.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.items.ItemArcThrower;
import us.mcsw.minerad.ref.CapacitorTier;

public class CraftingInit {

	public static void init() {
		ItemStack uranium = new ItemStack(ModItems.purifiedUranium, 1, OreDictionary.WILDCARD_VALUE);
		ItemStack neptunium = new ItemStack(ModItems.purifiedNeptunium, 1, OreDictionary.WILDCARD_VALUE);
		ItemStack plutonium = new ItemStack(ModItems.plutonium, 1, OreDictionary.WILDCARD_VALUE);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.geigerCounter), "IGI", "IUI", "SSS", 'I',
				"ingotIron", 'G', "blockGlass", 'U', ModItems.uraniumChunk, 'S', "stone"));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.coolantCore), ModItems.emptyCore, Blocks.snow,
				Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.emptyCore), " I ", "IUI", " I ", 'I',
				"ingotIron", 'U', ModItems.uraniumChunk));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fissionReactor, 2), "UIU", "UCU", "UIU", 'I',
				"blockIron", 'C', new ItemStack(ModItems.fissionCore, 1, 0), 'U', ModItems.uraniumChunk));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fusionReactor, 2), "IUI", "UPU", "IUI", 'I',
				"blockIron", 'U', ModItems.uraniumChunk, 'P', ModItems.plutonium));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radResistantPlating), "IUI", "UIU", 'I',
				"ingotIron", 'U', ModItems.uraniumChunk));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radResistantPlating), "UIU", "IUI", 'I',
				"ingotIron", 'U', ModItems.uraniumChunk));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.dustUranium, 3),
				new ItemStack(ModItems.diamondGrinder, 1, OreDictionary.WILDCARD_VALUE), ModItems.uraniumOreItem));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.acidSolution), Items.bowl,
				Items.water_bucket, Items.gunpowder));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.yellowCake), ModItems.dustUranium,
				ModItems.dustUranium, ModItems.dustUranium, ModItems.acidSolution));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.diamondGrinder), " D ", "IBI", 'D',
				"gemDiamond", 'I', "ingotIron", 'B', "blockIron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.capacitorBasic, "RRR", "RUR", "UIU", 'R', Items.redstone,
				'U', ModItems.uraniumChunk, 'I', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.capacitorIron, "RIR", "ICI", "RIR", 'R', Items.redstone,
				'I', "ingotIron", 'C', ModItems.capacitorBasic));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.capacitorGold, "RGR", "CRC", "GRG", 'R', Items.redstone,
				'G', "ingotGold", 'C', ModItems.capacitorIron));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.capacitorDiamond, "RDR", "CRC", "DRD", 'R', Items.redstone,
				'D', "gemDiamond", 'C', ModItems.capacitorGold));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.capacitorQuartz, "RQR", "QRQ", "CQC", 'R', Items.redstone,
				'Q', Blocks.quartz_block, 'C', ModItems.capacitorDiamond));

		GameRegistry.addSmelting(ModItems.yellowCake, new ItemStack(ModItems.purifiedUranium), 0.1f);

		GameRegistry.addRecipe(new ItemStack(ModBlocks.pureUranium), "UUU", "UUU", "UUU", 'U', uranium);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.pureNeptunium), "NNN", "NNN", "NNN", 'N', neptunium);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPlutonium), "PPP", "PPP", "PPP", 'P', plutonium);

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.purifiedUranium, 9), ModBlocks.pureUranium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.purifiedNeptunium, 9), ModBlocks.pureNeptunium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.plutonium, 9), ModBlocks.blockPlutonium);

		GameRegistry.addRecipe(new ItemStack(ModItems.purifiedUranium), "UUU", "UUU", "UUU", 'U',
				ModItems.nuggetUranium);
		GameRegistry.addRecipe(new ItemStack(ModItems.purifiedNeptunium), "NNN", "NNN", "NNN", 'N',
				ModItems.nuggetNeptunium);
		GameRegistry.addRecipe(new ItemStack(ModItems.plutonium), "PPP", "PPP", "PPP", 'P', ModItems.nuggetPlutonium);
		GameRegistry.addRecipe(new ItemStack(ModItems.unknownElement), "UUU", "UUU", "UUU", 'U',
				ModItems.unknownNugget);

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuggetUranium, 9), uranium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuggetNeptunium, 9), neptunium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuggetPlutonium, 9), plutonium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.unknownNugget, 9), ModItems.unknownElement);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radHelm), "PWP", "W W", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radChest), "W W", "PWP", "WWW", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radLegs), "PWP", "W W", "W W", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radBoots), "W W", "P P", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));

		if (ConfigMR.HARD_NUKE_RECIPE) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nukePackage), "INI", "TPT", "INI", 'I',
					"blockGold", 'N', ModItems.nuclearPod, 'T', Blocks.tnt, 'P', ModBlocks.blockPlutonium));
		} else {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nukePackage), "INI", "TPT", "INI", 'I',
					"blockIron", 'N', ModItems.nuclearPod, 'T', Blocks.tnt, 'P', plutonium));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.nuclearArsenal), "PIP", "NRN", "PIP", 'P',
				ModItems.nuclearPod, 'I', "blockIron", 'N', plutonium, 'R', ModItems.radioReceiver));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nuclearPod), "III", "NCN", "III", 'I',
				"ingotIron", 'N', neptunium, 'C', Blocks.chest));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radioReceiver), " B ", "NBN", "RIR", 'B',
				Blocks.iron_bars, 'N', ModItems.nuggetUranium, 'R', Items.redstone, 'I', "blockIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radioEmitter), " B ", "NBN", "RIR", 'B',
				Blocks.iron_bars, 'N', ModItems.nuggetPlutonium, 'R', Items.redstone, 'I', "blockIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nuclearArsenalUpgrade), "IPI", "PNP", "IPI",
				'I', "ingotIron", 'P', ModItems.nuclearPod, 'N', neptunium));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nukeMarker), " NN", " IN", "I  ", 'I',
				"ingotIron", 'N', ModItems.nuggetNeptunium));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.basicMachineBlock, "IRI", "RUR", "IRI", 'I', "ingotIron",
				'R', Items.redstone, 'U', ModItems.uraniumChunk));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.advancedMachineBlock, "IUI", "GBG", "IUI", 'B',
				ModBlocks.basicMachineBlock, 'U', uranium, 'I', "ingotIron", 'G', Blocks.glowstone));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.uraniumInfuser, "CCC", "IBI", "IAI", 'C',
				ModItems.emptyCore, 'I', "ingotIron", 'B', ModBlocks.basicMachineBlock, 'A', ModItems.capacitorIron));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.radioTowerBase, "APA", "TBR", "PCP", 'A',
				ModBlocks.radioTowerAntenna, 'P', plutonium, 'T', ModItems.radioEmitter, 'B',
				ModBlocks.advancedMachineBlock, 'R', ModItems.radioReceiver, 'C', ModItems.capacitorDiamond));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.radioTowerAntenna, 2), "BTB", "BIB", "BTB",
				'B', Blocks.iron_bars, 'T', ModItems.radioEmitter, 'I', "ingotIron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.magnetItem, " R ", "BPB", " R ", 'R', Items.redstone, 'B',
				ModBlocks.magnet, 'P', Items.ender_pearl));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.magnet, 2), "RIR", "CBC", "RGR", 'I', "blockIron", 'C',
						ModItems.denseCore, 'B', ModBlocks.basicMachineBlock, 'G', "blockGold", 'R', Items.redstone));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.denseCore, ModItems.unknownNugget, ModItems.emptyCore));

		ItemStack arcThrower = new ItemStack(ModItems.arcThrower);
		ModItems.arcThrower.setEnergyInItemStack(arcThrower, 0);
		GameRegistry.addRecipe(new ShapedOreRecipe(arcThrower, " IQ", "PQC", "  B", 'I', "ingotIron", 'Q',
				Blocks.quartz_block, 'P', plutonium, 'C', ModItems.capacitorGold, 'B', "blockIron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.microwave, "IMI", "NBN", "RCR", 'I', "blockIron", 'M',
				ModItems.microwaveModule, 'N', ModItems.nuggetPlutonium, 'B', ModBlocks.advancedMachineBlock, 'C',
				ModItems.capacitorGold, 'R', Items.redstone));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.microwaveModule, "IUI", "URU", "IUI", 'I', "ingotIron", 'U',
				uranium, 'R', ModItems.radioEmitter));

		for (CapacitorTier ct : CapacitorTier.values()) {
			ItemStack result = new ItemStack(ModBlocks.pipeBlock, 6);
			CapacitorTier.setInItemStack(result, ct);
			GameRegistry.addRecipe(new ShapedOreRecipe(result, "RRR", "CGC", "RRR", 'R', Items.redstone, 'G',
					new ItemStack(Blocks.glass, 1, OreDictionary.WILDCARD_VALUE), 'C', CapacitorTier.getCapacitor(ct)));
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.densePouch, "UGU", "WCW", "UGU", 'U',
				ModItems.unknownNugget, 'G', Blocks.glass, 'W',
				new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), 'C', Blocks.chest));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.radAway), ModItems.acidSolution,
				Items.sugar, ModItems.radX));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.radX), Items.glass_bottle, Items.melon,
				Items.nether_wart));
	}

}
