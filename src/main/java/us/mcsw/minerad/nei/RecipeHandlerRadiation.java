package us.mcsw.minerad.nei;

import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.minerad.recipes.MicrowaveRecipes;
import us.mcsw.minerad.recipes.RadiationRecipes;
import us.mcsw.minerad.recipes.RadiationRecipes.RadiationRecipe;
import us.mcsw.minerad.ref.TextureReference;

public class RecipeHandlerRadiation extends RecipeHandlerMR {

	public RecipeHandlerRadiation() {
		super("radiation");
	}

	@Override
	public String getGuiTexture() {
		return TextureReference.RADIATION_GUI_NEI.toString();
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (RadiationRecipe rec : RadiationRecipes.recipes) {
			if (ItemUtil.areItemStacksEqual(result, rec.getProduct())) {
				arecipes.add(new RecipeRadiation(rec));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (MicrowaveRecipes.getRecipeFor(ingredient) != null) {
			arecipes.add(new RecipeRadiation(RadiationRecipes.getRecipeFor(ingredient)));
		}
	}

	@Override
	public void drawForeground(int recipe) {
		super.drawForeground(recipe);
		RecipeRadiation r = (RecipeRadiation) arecipes.get(recipe);
//		GuiDraw.drawStringC(r.rec.getRads() + " RADs", 80, 40, 4210752, false);
	}

	public class RecipeRadiation extends CachedRecipe {

		RadiationRecipe rec;

		public RecipeRadiation(RadiationRecipe rec) {
			this.rec = rec;
		}

		@Override
		public PositionedStack getResult() {
			return makeStack(rec.getProduct(), 102, 32);
		}

		@Override
		public PositionedStack getIngredient() {
			return makeStack(rec.getSource(), 56, 32);
		}

	}

}
