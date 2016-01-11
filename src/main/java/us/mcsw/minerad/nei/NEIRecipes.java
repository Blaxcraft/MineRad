package us.mcsw.minerad.nei;

import codechicken.nei.api.API;
import cpw.mods.fml.common.Optional;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.init.ModBlocks;

public class NEIRecipes {

	@Optional.Method(modid = "NotEnoughItems")
	public static void init() {
		API.hideItem(new ItemStack(ModBlocks.groundZero));
		API.hideItem(new ItemStack(ModBlocks.ghoulLight));

		API.registerRecipeHandler(new RecipeHandlerFusion());
		API.registerUsageHandler(new RecipeHandlerFusion());

		API.registerRecipeHandler(new RecipeHandlerFission());
		API.registerUsageHandler(new RecipeHandlerFission());

		API.registerRecipeHandler(new RecipeHandlerInfuser());
		API.registerUsageHandler(new RecipeHandlerInfuser());

		API.registerRecipeHandler(new RecipeHandlerMicrowave());
		API.registerUsageHandler(new RecipeHandlerMicrowave());

		API.registerRecipeHandler(new RecipeHandlerRadiation());
		API.registerUsageHandler(new RecipeHandlerRadiation());
	}

}
