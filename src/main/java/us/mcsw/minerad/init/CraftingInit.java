package us.mcsw.minerad.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ref.CapacitorTier;

public class CraftingInit {

	public static void init() {
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

		GameRegistry.addRecipe(new ItemStack(ModBlocks.pureUranium), "UUU", "UUU", "UUU", 'U',
				ModItems.purifiedUranium);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.pureNeptunium), "NNN", "NNN", "NNN", 'N',
				ModItems.purifiedNeptunium);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockPlutonium), "PPP", "PPP", "PPP", 'P', ModItems.plutonium);

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.purifiedUranium, 9), ModBlocks.pureUranium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.purifiedNeptunium, 9), ModBlocks.pureNeptunium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.plutonium, 9), ModBlocks.blockPlutonium);

		GameRegistry.addRecipe(new ItemStack(ModItems.purifiedUranium), "UUU", "UUU", "UUU", 'U',
				ModItems.nuggetUranium);
		GameRegistry.addRecipe(new ItemStack(ModItems.purifiedNeptunium), "NNN", "NNN", "NNN", 'N',
				ModItems.nuggetNeptunium);
		GameRegistry.addRecipe(new ItemStack(ModItems.plutonium), "PPP", "PPP", "PPP", 'P', ModItems.nuggetPlutonium);

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuggetUranium, 9), ModItems.purifiedUranium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuggetNeptunium, 9), ModItems.purifiedNeptunium);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuggetPlutonium, 9), ModItems.plutonium);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radHelm), "PWP", "W W", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radChest), "W W", "PWP", "WWW", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radLegs), "PWP", "W W", "W W", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.radBoots), "W W", "P P", 'P',
				ModItems.radResistantPlating, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE)));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.nukePackage), "INI", "TPT", "INI", 'I',
				"blockIron", 'N', ModItems.nuclearPod, 'T', Blocks.tnt, 'P', ModItems.plutonium));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.nuclearArsenal), "PIP", "NRN", "PIP", 'P',
				ModItems.nuclearPod, 'I', "blockIron", 'N', ModItems.nuggetPlutonium, 'R', ModItems.radioReceiver));
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
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.advancedMachineBlock, "IPI", "GBG", "IPI", 'B',
				ModBlocks.basicMachineBlock, 'P', ModItems.plutonium, 'I', "ingotIron", 'G', Blocks.glowstone));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.uraniumInfuser, "CCC", "IBI", "IAI", 'C',
				ModItems.emptyCore, 'I', "ingotIron", 'B', ModBlocks.basicMachineBlock, 'A', ModItems.capacitorIron));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.radioTowerBase, "APA", "TBR", "PCP", 'A',
				ModBlocks.radioTowerAntenna, 'P', ModItems.plutonium, 'T', ModItems.radioEmitter, 'B',
				ModBlocks.advancedMachineBlock, 'R', ModItems.radioReceiver, 'C', ModItems.capacitorDiamond));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.radioTowerAntenna, 2), "BTB", "BIB", "BTB",
				'B', Blocks.iron_bars, 'T', ModItems.radioEmitter, 'I', "blockIron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.magnetItem, " R ", "DBD", " R ", 'R', Items.redstone, 'B',
				ModBlocks.magnet, 'D', "gemDiamond"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.magnet, 2), " I ", "CBC", " G ", 'I',
				"blockIron", 'C', ModItems.denseCore, 'B', ModBlocks.basicMachineBlock, 'G', "blockGold"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.denseCore,
				new ItemStack(ModItems.unknownElement, 1, OreDictionary.WILDCARD_VALUE), ModItems.emptyCore));

		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.arcThrower, 1, ModItems.arcThrower.getMaxDamage()), " IQ",
						"PQC", "  B", 'I', "ingotIron", 'Q', Blocks.quartz_block, 'P', ModItems.plutonium, 'C',
						ModItems.capacitorGold, 'B', "blockIron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.microwave, "IMI", "NBN", "NCN", 'I', "blockIron", 'M',
				ModItems.microwaveModule, 'N', ModItems.nuggetPlutonium, 'B', ModBlocks.advancedMachineBlock, 'C',
				ModItems.capacitorGold));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.microwaveModule, "IUI", "URU", "IUI", 'I', "ingotIron", 'U',
				ModItems.purifiedUranium, 'R', ModItems.radioEmitter));

		for (CapacitorTier ct : CapacitorTier.values()) {
			ItemStack result = new ItemStack(ModBlocks.pipeBlock, 6);
			CapacitorTier.setInItemStack(result, ct);
			GameRegistry.addRecipe(new ShapedOreRecipe(result, "RRR", "CGC", "RRR", 'R', Items.redstone, 'G',
					new ItemStack(Blocks.glass, 1, OreDictionary.WILDCARD_VALUE), 'C', CapacitorTier.getCapacitor(ct)));
		}

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.radAway), ModItems.acidSolution,
				Items.sugar, ModItems.radX));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.radX), Items.glass_bottle, Items.melon,
				Items.nether_wart));
	}

}
