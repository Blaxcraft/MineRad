package us.mcsw.minerad.init;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.init.UraniumInfuserRecipes.InfuserRecipe;

public class FusionRecipes {

	public static ArrayList<FusionRecipe> recipes = new ArrayList<FusionRecipe>();

	public static void init() {
		addRecipe(ModItems.plutonium, ModItems.plutonium, new ItemStack(ModItems.unknownElement));
		addRecipe(ModItems.purifiedUranium, ModItems.dustUranium, new ItemStack(ModItems.plutonium));
	}

	public static void addRecipe(Item source1, Item source2, ItemStack result) {
		FusionRecipe rec = new FusionRecipe(source1, source2, result);
		recipes.add(rec);
	}

	public static FusionRecipe getRecipeFor(ItemStack source1, ItemStack source2) {
		for (FusionRecipe r : recipes) {
			if (r.source1.equals(source1.getItem()) && r.source2.equals(source2.getItem())
					|| r.source2.equals(source1.getItem()) && r.source1.equals(source2.getItem())) {
				return r;
			}
		}
		return null;
	}

	public static boolean hasRecipe(ItemStack it) {
		for (FusionRecipe r : recipes) {
			if (r.source1.equals(it.getItem()) || r.source2.equals(it.getItem())) {
				return true;
			}
		}
		return false;
	}

	public static class FusionRecipe {
		Item source1, source2;
		ItemStack result;

		public FusionRecipe(Item source1, Item source2, ItemStack result) {
			this.source1 = source1;
			this.source2 = source2;
			this.result = result;
		}

		public Item getSource1() {
			return source1;
		}

		public Item getSource2() {
			return source2;
		}

		public ItemStack getResult() {
			return result;
		}
	}

}
