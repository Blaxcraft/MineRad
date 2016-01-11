package us.mcsw.minerad.recipes;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.recipes.InfuserRecipes.InfuserRecipe;

public class RadiationRecipes {

	public static ArrayList<RadiationRecipe> recipes = new ArrayList<RadiationRecipe>();

	public static void init() {
		addRecipe(Items.rotten_flesh, new ItemStack(ModItems.irradiatedFlesh), 100);
		addRecipe(ModItems.radAway, new ItemStack(ModItems.ghoulCure), 250);
	}

	public static void addRecipe(Item source, ItemStack product, int rads) {
		recipes.add(new RadiationRecipe(source, product, rads));
	}

	public static void addRecipe(Block source, ItemStack product, int rads) {
		recipes.add(new RadiationRecipe(source, product, rads));
	}

	public static void addRecipe(ItemStack source, ItemStack product, int rads) {
		recipes.add(new RadiationRecipe(source, product, rads));
	}

	public static RadiationRecipe getRecipeFor(ItemStack source) {
		for (RadiationRecipe r : recipes) {
			if (r.source.isItemEqual(source) && source.stackSize >= r.source.stackSize) {
				return r;
			}
		}
		return null;
	}

	public static class RadiationRecipe {
		private ItemStack source, product;
		private int rads;

		public RadiationRecipe(Item source, ItemStack product, int rads) {
			this(new ItemStack(source, 1), product, rads);
		}

		public RadiationRecipe(Block source, ItemStack product, int rads) {
			this(new ItemStack(source, 1), product, rads);
		}

		public RadiationRecipe(ItemStack source, ItemStack product, int rads) {
			this.source = source;
			this.product = product;
			this.rads = rads;
		}

		public ItemStack getSource() {
			return source.copy();
		}

		public int getRads() {
			return rads;
		}

		public ItemStack getProduct() {
			return product.copy();
		}
	}

}
