package us.mcsw.minerad.nei;

import java.util.Iterator;

import codechicken.nei.PositionedStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.minerad.blocks.BlockMicrowave;
import us.mcsw.minerad.init.MicrowaveRecipes;
import us.mcsw.minerad.init.MicrowaveRecipes.MicrowaveRecipe;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.init.ModBlocks;

public class RecipeHandlerMicrowave extends RecipeHandlerMR {

	public RecipeHandlerMicrowave() {
		super("microwave");
	}

	@Override
	public String getGuiTexture() {
		return MachineReference.MICROWAVE_GUI_NEI.toString();
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (MicrowaveRecipe rec : MicrowaveRecipes.recipes) {
			if (ItemUtil.areItemStacksEqual(result, rec.getProduct())) {
				arecipes.add(new RecipeMicrowave(rec));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (MicrowaveRecipes.getRecipeFor(ingredient) != null) {
			arecipes.add(new RecipeMicrowave(MicrowaveRecipes.getRecipeFor(ingredient)));
		}
		if (Block.getBlockFromItem(ingredient.getItem()) instanceof BlockMicrowave) {
			for (MicrowaveRecipe rec : MicrowaveRecipes.recipes) {
				arecipes.add(new RecipeMicrowave(rec));
			}
		}
	}

	public class RecipeMicrowave extends CachedRecipe {

		MicrowaveRecipe rec;

		public RecipeMicrowave(MicrowaveRecipe rec) {
			this.rec = rec;
		}

		@Override
		public PositionedStack getResult() {
			return makeStack(rec.getProduct(), 84, 32);
		}

		@Override
		public PositionedStack getIngredient() {
			return makeStack(rec.getSource(), 38, 32);
		}

		@Override
		public PositionedStack getOtherStack() {
			return makeStack(new ItemStack(ModBlocks.microwave), 141, 32);
		}

	}

}
