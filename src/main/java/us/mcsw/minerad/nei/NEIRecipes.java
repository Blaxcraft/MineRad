package us.mcsw.minerad.nei;

import codechicken.nei.api.API;
import cpw.mods.fml.common.Optional;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.init.ModBlocks;

public class NEIRecipes {
	
	@Optional.Method(modid = "NotEnoughItems")
	public static void init() {
		API.hideItem(new ItemStack(ModBlocks.groundZero));
		
		API.registerRecipeHandler(new RecipeHandlerFusion());
		API.registerUsageHandler(new RecipeHandlerFusion());
	}

}
