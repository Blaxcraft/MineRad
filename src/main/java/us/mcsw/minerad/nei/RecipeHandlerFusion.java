package us.mcsw.minerad.nei;

import java.util.Arrays;
import java.util.List;

import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemCoolantCore;
import us.mcsw.minerad.items.ItemFusionCore;
import us.mcsw.minerad.recipes.FusionRecipes;
import us.mcsw.minerad.recipes.FusionRecipes.FusionRecipe;
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
			if (ingredient.getItem().equals(fr.getSource1()) || ingredient.getItem().equals(fr.getSource2())
					|| ingredient.getItem() instanceof ItemCoolantCore
					|| ingredient.getItem() instanceof ItemFusionCore) {
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
			return makeStack(rec.getResult(), 85, 30);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			PositionedStack[] ingredients = { makeStack(new ItemStack(rec.getSource1()), 58, 30),
					makeStack(new ItemStack(rec.getSource2()), 112, 30) };
			return Arrays.asList(ingredients);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			PositionedStack[] others = { makeStack(new ItemStack(ModItems.fusionCore), 85, 57),
					makeStack(new ItemStack(ModItems.coolantCore), 141, 57) };
			return Arrays.asList(others);
		}

	}

}
