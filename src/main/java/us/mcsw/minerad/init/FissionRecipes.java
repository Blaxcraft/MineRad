package us.mcsw.minerad.init;

import java.util.HashMap;

import net.minecraft.item.Item;

public class FissionRecipes {

	public static HashMap<Item, Item> recipes = new HashMap<Item, Item>();

	public static void init() {
		addRecipe(ModItems.purifiedUranium, ModItems.purifiedNeptunium);
		addRecipe(ModItems.purifiedNeptunium, ModItems.plutonium);
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
