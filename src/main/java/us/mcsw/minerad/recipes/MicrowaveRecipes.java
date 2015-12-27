package us.mcsw.minerad.recipes;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.recipes.UraniumInfuserRecipes.InfuserRecipe;

public class MicrowaveRecipes {

	public static ArrayList<MicrowaveRecipe> recipes = new ArrayList<MicrowaveRecipe>();

	public static void init() {
		addRecipe(Items.porkchop, new ItemStack(Items.cooked_porkchop), 60);
		addRecipe(Items.beef, new ItemStack(Items.cooked_beef), 60);
		addRecipe(Items.chicken, new ItemStack(Items.cooked_chicken), 40);
		for (int m = 0; m <= 1; m++) {
			addRecipe(new ItemStack(Items.fish, 1, m), new ItemStack(Items.cooked_fished, 1, m), 40);
		}
		addRecipe(new ItemStack(Items.wheat, 2), new ItemStack(Items.bread), 10);
		addRecipe(Items.poisonous_potato, new ItemStack(Items.potato), 100);
		addRecipe(Items.potato, new ItemStack(Items.baked_potato), 40);
		addRecipe(Items.rotten_flesh, new ItemStack(Items.leather), 100);
	}

	public static void addRecipe(Item source, ItemStack product, int ticks) {
		recipes.add(new MicrowaveRecipe(source, product, ticks));
	}

	public static void addRecipe(Block source, ItemStack product, int ticks) {
		recipes.add(new MicrowaveRecipe(source, product, ticks));
	}

	public static void addRecipe(ItemStack source, ItemStack product, int ticks) {
		recipes.add(new MicrowaveRecipe(source, product, ticks));
	}

	public static MicrowaveRecipe getRecipeFor(ItemStack source) {
		for (MicrowaveRecipe r : recipes) {
			if (r.source.isItemEqual(source) && source.stackSize >= r.source.stackSize) {
				return r;
			}
		}
		return null;
	}

	public static class MicrowaveRecipe {
		private ItemStack source, product;
		private int ticks;

		public MicrowaveRecipe(Item source, ItemStack product, int ticks) {
			this(new ItemStack(source, 1), product, ticks);
		}

		public MicrowaveRecipe(Block source, ItemStack product, int ticks) {
			this(new ItemStack(source, 1), product, ticks);
		}

		public MicrowaveRecipe(ItemStack source, ItemStack product, int ticks) {
			this.source = source;
			this.product = product;
			this.ticks = ticks;
		}

		public ItemStack getSource() {
			return source.copy();
		}

		public int getTicks() {
			return ticks;
		}

		public ItemStack getProduct() {
			return product.copy();
		}
	}

}
