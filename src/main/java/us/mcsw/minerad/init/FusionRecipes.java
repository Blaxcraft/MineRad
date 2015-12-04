package us.mcsw.minerad.init;

import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class FusionRecipes {

	public static HashMap<Item, Item> recipes = new HashMap<Item, Item>();

	public static void init() {
		addRecipe(ModItems.plutonium, ModItems.unknownElement);
	}

	public static void addRecipe(Item source, Item result) {
		recipes.put(source, result);
	}

	public static Item getResultFrom(Item source) {
		return recipes.get(source);
	}

	public static boolean hasRecipe(Item source) {
		return recipes.containsKey(source);
	}

}
