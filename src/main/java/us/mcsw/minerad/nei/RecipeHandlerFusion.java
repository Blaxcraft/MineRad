package us.mcsw.minerad.nei;

import java.util.ArrayList;
import java.util.List;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.IUsageHandler;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.FusionRecipes;
import us.mcsw.minerad.init.FusionRecipes.FusionRecipe;
import us.mcsw.minerad.ref.MachineReference;

public class RecipeHandlerFusion extends RecipeHandlerMR {

	public RecipeHandlerFusion() {
		super("fusionReactor");
	}

	@Override
	public String getGuiTexture() {
		return MachineReference.FUSION_REACTOR_GUI.toString();
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (FusionRecipe fr : FusionRecipes.recipes) {
			if (fr.getResult().isItemEqual(result)) {
				this.arecipes.add(new RecipeFusion(fr));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (FusionRecipe fr : FusionRecipes.recipes) {
			if (ingredient.getItem().equals(fr.getSource1()) || ingredient.getItem().equals(fr.getSource2())) {
				this.arecipes.add(new RecipeFusion(fr));
			}
		}
	}

	public class RecipeFusion extends CachedRecipe {

		FusionRecipe rec;

		public RecipeFusion(FusionRecipe rec) {
			this.rec = rec;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(rec.getResult(), 85, 30);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return new ArrayList<PositionedStack>(
					Arrays.asList(new PositionedStack[] { new PositionedStack(new ItemStack(rec.getSource1()), 58, 30),
							new PositionedStack(new ItemStack(rec.getSource2()), 112, 30) }));
		}

	}

}
