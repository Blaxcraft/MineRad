package us.mcsw.minerad.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import us.mcsw.minerad.items.ItemEmptyCore;
import us.mcsw.minerad.items.ItemDiamondGrinder;
import us.mcsw.minerad.items.ItemFissionCore;
import us.mcsw.minerad.items.ItemFusionCore;
import us.mcsw.minerad.items.ItemGeigerCounter;
import us.mcsw.minerad.items.ItemNeptuniumNugget;
import us.mcsw.minerad.items.ItemNuclearArsenalUpgrade;
import us.mcsw.minerad.items.ItemNukeMarker;
import us.mcsw.minerad.items.ItemNukePackage;
import us.mcsw.minerad.items.ItemPlutonium;
import us.mcsw.minerad.items.ItemPlutoniumNugget;
import us.mcsw.minerad.items.ItemPurifiedNeptunium;
import us.mcsw.minerad.items.ItemPurifiedUranium;
import us.mcsw.minerad.items.ItemRadArmour;
import us.mcsw.minerad.items.ItemRadAway;
import us.mcsw.minerad.items.ItemRadX;
import us.mcsw.minerad.items.ItemUnknownElement;
import us.mcsw.minerad.items.ItemCoolantCore;
import us.mcsw.minerad.items.ItemCrafting;
import us.mcsw.minerad.items.ItemUraniumChunk;
import us.mcsw.minerad.items.ItemUraniumNugget;
import us.mcsw.minerad.items.ItemUraniumOre;

public class ModItems {

	public static final ItemGeigerCounter geigerCounter = new ItemGeigerCounter();

	public static final ItemUraniumChunk uraniumChunk = new ItemUraniumChunk();
	public static final ItemUraniumOre uraniumOreItem = new ItemUraniumOre();
	public static final ItemPurifiedUranium purifiedUranium = new ItemPurifiedUranium();

	public static final ItemFusionCore fusionCore = new ItemFusionCore();
	public static final ItemFissionCore fissionCore = new ItemFissionCore();
	public static final ItemCoolantCore coolantCore = new ItemCoolantCore();
	public static final ItemEmptyCore emptyCore = new ItemEmptyCore();

	public static final ItemCrafting radResistantPlating = new ItemCrafting("radResistantPlating");
	public static final ItemCrafting uraniumDust = new ItemCrafting("uraniumDust");
	public static final ItemCrafting acidSolution = new ItemCrafting("acidSolution");
	public static final ItemCrafting yellowCake = new ItemCrafting("yellowCake");
	public static final ItemDiamondGrinder diamondGrinder = new ItemDiamondGrinder();
	public static final ItemCrafting nuclearPod = new ItemCrafting("nuclearPod");
	public static final ItemCrafting radioReceiver = new ItemCrafting("radioReceiver");

	public static final ItemRadArmour radHelm = new ItemRadArmour(0);
	public static final ItemRadArmour radChest = new ItemRadArmour(1);
	public static final ItemRadArmour radLegs = new ItemRadArmour(2);
	public static final ItemRadArmour radBoots = new ItemRadArmour(3);

	public static final ItemPurifiedNeptunium purifiedNeptunium = new ItemPurifiedNeptunium();
	public static final ItemPlutonium plutonium = new ItemPlutonium();
	public static final ItemUnknownElement unknownElement = new ItemUnknownElement();

	public static final ItemUraniumNugget nuggetUranium = new ItemUraniumNugget();
	public static final ItemNeptuniumNugget nuggetNeptunium = new ItemNeptuniumNugget();
	public static final ItemPlutoniumNugget nuggetPlutonium = new ItemPlutoniumNugget();

	public static final ItemNukePackage nukePackage = new ItemNukePackage();
	public static final ItemNuclearArsenalUpgrade nuclearArsenalUpgrade = new ItemNuclearArsenalUpgrade();
	public static final ItemNukeMarker nukeMarker = new ItemNukeMarker();

	public static final ItemRadAway radAway = new ItemRadAway();
	public static final ItemRadX radX = new ItemRadX();

	public static void init() {
		GameRegistry.registerItem(geigerCounter, geigerCounter.getBasicName());
		GameRegistry.registerItem(uraniumChunk, uraniumChunk.getBasicName());
		GameRegistry.registerItem(uraniumOreItem, uraniumOreItem.getBasicName());
		GameRegistry.registerItem(purifiedUranium, purifiedUranium.getBasicName());

		GameRegistry.registerItem(fusionCore, fusionCore.getBasicName());
		GameRegistry.registerItem(fissionCore, fissionCore.getBasicName());
		GameRegistry.registerItem(coolantCore, coolantCore.getBasicName());
		GameRegistry.registerItem(emptyCore, emptyCore.getBasicName());

		GameRegistry.registerItem(radResistantPlating, radResistantPlating.getBasicName());
		GameRegistry.registerItem(uraniumDust, uraniumDust.getBasicName());
		GameRegistry.registerItem(acidSolution, acidSolution.getBasicName());
		GameRegistry.registerItem(yellowCake, yellowCake.getBasicName());
		GameRegistry.registerItem(diamondGrinder, diamondGrinder.getBasicName());
		GameRegistry.registerItem(nuclearPod, nuclearPod.getBasicName());
		GameRegistry.registerItem(radioReceiver, radioReceiver.getBasicName());

		GameRegistry.registerItem(radHelm, radHelm.getBasicName());
		GameRegistry.registerItem(radChest, radChest.getBasicName());
		GameRegistry.registerItem(radLegs, radLegs.getBasicName());
		GameRegistry.registerItem(radBoots, radBoots.getBasicName());

		GameRegistry.registerItem(purifiedNeptunium, purifiedNeptunium.getBasicName());
		GameRegistry.registerItem(plutonium, plutonium.getBasicName());
		GameRegistry.registerItem(unknownElement, unknownElement.getBasicName());

		GameRegistry.registerItem(nuggetUranium, nuggetUranium.getBasicName());
		GameRegistry.registerItem(nuggetNeptunium, nuggetNeptunium.getBasicName());
		GameRegistry.registerItem(nuggetPlutonium, nuggetPlutonium.getBasicName());

		GameRegistry.registerItem(nukePackage, nukePackage.getBasicName());
		GameRegistry.registerItem(nuclearArsenalUpgrade, nuclearArsenalUpgrade.getBasicName());
		GameRegistry.registerItem(nukeMarker, nukeMarker.getBasicName());

		GameRegistry.registerItem(radAway, radAway.getBasicName());
		GameRegistry.registerItem(radX, radX.getBasicName());
	}

}
