package us.mcsw.minerad.nei;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import codechicken.nei.PositionedStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemCoolantCore;
import us.mcsw.minerad.items.ItemFissionCore;
import us.mcsw.minerad.recipes.FissionRecipes;
import us.mcsw.minerad.recipes.FissionRecipes.FissionRecipe;
import us.mcsw.minerad.ref.MachineReference;

public class RecipeHandlerFission extends RecipeHandlerMR {

	public RecipeHandlerFission() {
		super("fissionReactor");
	}

	@Override
	public String getGuiTexture() {
		return MachineReference.FISSION_REACTOR_GUI_NEI.toString();
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (FissionRecipe fr : FissionRecipes.recipes) {
			if (fr.getResult().getItem().equals(result.getItem())) {
				this.arecipes.add(new RecipeFission(fr.getSource()));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (FissionRecipe fr : FissionRecipes.recipes) {
			if (ingredient.getItem().equals(fr.getSource()) || ingredient.getItem() instanceof ItemCoolantCore
					|| ingredient.getItem() instanceof ItemFissionCore) {
				this.arecipes.add(new RecipeFission(fr.getSource()));
			}
		}
	}

	public class RecipeFission extends CachedRecipe {

		Item rec;

		public RecipeFission(Item rec) {
			this.rec = rec;
		}

		@Override
		public PositionedStack getResult() {
			return makeStack(FissionRecipes.getRecipeFor(new ItemStack(rec)).getResult(), 111, 31);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			PositionedStack[] ingredients = { makeStack(new ItemStack(rec), 59, 31) };
			return Arrays.asList(ingredients);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			PositionedStack[] others = { makeStack(new ItemStack(ModItems.fissionCore), 85, 31),
					makeStack(new ItemStack(ModItems.coolantCore), 141, 57) };
			return Arrays.asList(others);
		}

	}

}
