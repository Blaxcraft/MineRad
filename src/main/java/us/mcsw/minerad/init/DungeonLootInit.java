package us.mcsw.minerad.init;

import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class DungeonLootInit {

	public static void init() {
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH,
				new WeightedRandomChestContent(ModItems.radX, 0, 1, 3, 10));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH,
				new WeightedRandomChestContent(ModItems.radAway, 0, 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
				new WeightedRandomChestContent(ModItems.uraniumOreItem, 0, 1, 2, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
				new WeightedRandomChestContent(ModItems.emptyCore, 0, 1, 2, 4));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR,
				new WeightedRandomChestContent(ModItems.uraniumOreItem, 0, 1, 2, 3));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR,
				new WeightedRandomChestContent(ModItems.emptyCore, 0, 1, 2, 4));
	}

}
