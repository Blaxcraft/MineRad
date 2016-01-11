package us.mcsw.minerad.nei;

import java.util.Arrays;
import java.util.List;

import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemEmptyCore;
import us.mcsw.minerad.recipes.InfuserRecipes;
import us.mcsw.minerad.recipes.InfuserRecipes.InfuserRecipe;
import us.mcsw.minerad.ref.MachineReference;

public class RecipeHandlerInfuser extends RecipeHandlerMR {

	public RecipeHandlerInfuser() {
		super("infuser");
	}

	@Override
	public String getGuiTexture() {
		return MachineReference.INFUSER_GUI.toString();
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (InfuserRecipe rec : InfuserRecipes.recipes) {
			if (ItemUtil.areItemStacksEqual(result, rec.getProduct())) {
				arecipes.add(new RecipeInfuser(rec));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (InfuserRecipes.getRecipeFor(ingredient, false) != null) {
			arecipes.add(new RecipeInfuser(InfuserRecipes.getRecipeFor(ingredient, false)));
		}
		if (ingredient.getItem() instanceof ItemEmptyCore) {
			for (InfuserRecipe rec : InfuserRecipes.recipes) {
				if (ingredient.stackSize >= rec.getRequiredCores()) {
					arecipes.add(new RecipeInfuser(rec));
				}
			}
		}
	}

	public class RecipeInfuser extends CachedRecipe {

		InfuserRecipe rec;

		public RecipeInfuser(InfuserRecipe rec) {
			this.rec = rec;
		}

		@Override
		public PositionedStack getResult() {
			return makeStack(rec.getProduct(), 116, 35);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			PositionedStack[] ingredients = { makeStack(rec.getSource(), 56, 35),
					makeStack(new ItemStack(ModItems.emptyCore, rec.getRequiredCores()), 34, 35) };
			return Arrays.asList(ingredients);
		}

	}

}
