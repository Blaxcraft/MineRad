package us.mcsw.minerad.recipes;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.recipes.FusionRecipes.FusionRecipe;

public class FissionRecipes {

	public static ArrayList<FissionRecipe> recipes = new ArrayList<FissionRecipe>();

	public static void init() {
		addRecipe(ModItems.purifiedUranium, new ItemStack(ModItems.purifiedNeptunium), 600);
		addRecipe(ModItems.purifiedNeptunium, new ItemStack(ModItems.plutonium), 5400);
	}

	public static void addRecipe(Item source, ItemStack result, int ticks) {
		FissionRecipe rec = new FissionRecipe(source, result, ticks);
		recipes.add(rec);
	}

	public static FissionRecipe getRecipeFor(ItemStack source) {
		if (source == null)
			return null;
		for (FissionRecipe r : recipes) {
			if (r.source.equals(source.getItem())) {
				return r;
			}
		}
		return null;
	}

	public static boolean hasRecipe(ItemStack it) {
		if (it == null)
			return false;
		for (FissionRecipe r : recipes) {
			if (r.source.equals(it.getItem())) {
				return true;
			}
		}
		return false;
	}

	public static class FissionRecipe {
		Item source;
		ItemStack result;
		int ticks;

		public FissionRecipe(Item source, ItemStack result, int ticks) {
			this.source = source;
			this.result = result;
			this.ticks = ticks;
		}

		public Item getSource() {
			return source;
		}

		public ItemStack getResult() {
			return result;
		}

		public int getTicks() {
			return ticks;
		}
	}

}
