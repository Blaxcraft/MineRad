package us.mcsw.minerad.nei;

import java.util.List;
import java.util.Map.Entry;

import codechicken.nei.PositionedStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;
import us.mcsw.minerad.init.FissionRecipes;
import us.mcsw.minerad.init.FusionRecipes;
import us.mcsw.minerad.init.FusionRecipes.FusionRecipe;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemCoolantCore;
import us.mcsw.minerad.items.ItemFissionCore;
import us.mcsw.minerad.items.ItemFusionCore;
import us.mcsw.minerad.nei.RecipeHandlerFusion.RecipeFusion;
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
		for (Entry<Item, Item> fr : FissionRecipes.recipes.entrySet()) {
			if (fr.getValue().equals(result.getItem())) {
				this.arecipes.add(new RecipeFission(fr.getKey()));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (Entry<Item, Item> fr : FissionRecipes.recipes.entrySet()) {
			if (ingredient.getItem().equals(fr.getKey()) || ingredient.getItem() instanceof ItemCoolantCore
					|| ingredient.getItem() instanceof ItemFissionCore) {
				this.arecipes.add(new RecipeFission(fr.getKey()));
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
			return makeStack(new ItemStack(FissionRecipes.getResultFrom(rec)), 111, 31);
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
