package us.mcsw.minerad.init;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UraniumInfuserRecipes {

	public static ArrayList<InfuserRecipe> recipes = new ArrayList<UraniumInfuserRecipes.InfuserRecipe>();

	public static void init() {
		addRecipe(new ItemStack(ModItems.uraniumChunk, 12), new ItemStack(ModItems.fusionCore), 1, 15000);
		addRecipe(ModItems.uraniumOreItem, new ItemStack(ModItems.fissionCore), 1, 7500);
	}

	public static void addRecipe(Item source, ItemStack product, int cores, int power) {
		recipes.add(new InfuserRecipe(source, product, cores, power));
	}

	public static void addRecipe(Block source, ItemStack product, int cores, int power) {
		recipes.add(new InfuserRecipe(source, product, cores, power));
	}

	public static void addRecipe(ItemStack source, ItemStack product, int cores, int power) {
		recipes.add(new InfuserRecipe(source, product, cores, power));
	}

	public static InfuserRecipe getRecipeFor(ItemStack source, boolean stack) {
		for (InfuserRecipe r : recipes) {
			if (r.source.isItemEqual(source) && (stack ? source.stackSize >= r.source.stackSize : true)) {
				return r;
			}
		}
		return null;
	}

	public static class InfuserRecipe {
		private ItemStack source, product;
		private int requiredCores, power;

		public InfuserRecipe(Item source, ItemStack product, int cores, int power) {
			this(new ItemStack(source, 1), product, cores, power);
		}

		public InfuserRecipe(Block source, ItemStack product, int cores, int power) {
			this(new ItemStack(source, 1), product, cores, power);
		}

		public InfuserRecipe(ItemStack source, ItemStack product, int cores, int power) {
			this.source = source;
			this.product = product;
			this.requiredCores = cores;
			this.power = power;
		}

		public ItemStack getSource() {
			return source.copy();
		}

		public int getPower() {
			return power;
		}

		public ItemStack getProduct() {
			return product.copy();
		}

		public int getRequiredCores() {
			return requiredCores;
		}
	}

}
