package us.mcsw.minerad.nei;

import java.util.List;

import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.init.UraniumInfuserRecipes;
import us.mcsw.minerad.init.UraniumInfuserRecipes.InfuserRecipe;
import us.mcsw.minerad.items.ItemEmptyCore;
import us.mcsw.minerad.ref.MachineReference;

public class RecipeHandlerInfuser extends RecipeHandlerMR {

	public RecipeHandlerInfuser() {
		super("uraniumInfuserTile");
	}

	@Override
	public String getGuiTexture() {
		return MachineReference.URANIUM_INFUSER_GUI.toString();
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (InfuserRecipe rec : UraniumInfuserRecipes.recipes) {
			if (ItemUtil.areItemStacksEqual(result, rec.getProduct())) {
				arecipes.add(new RecipeInfuser(rec));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (UraniumInfuserRecipes.getRecipeFor(ingredient, false) != null) {
			arecipes.add(new RecipeInfuser(UraniumInfuserRecipes.getRecipeFor(ingredient, false)));
		}
		if (ingredient.getItem() instanceof ItemEmptyCore) {
			for (InfuserRecipe rec : UraniumInfuserRecipes.recipes) {
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
