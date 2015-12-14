package us.mcsw.minerad.nei;

import codechicken.nei.recipe.TemplateRecipeHandler;
import us.mcsw.core.util.LangUtil;
import us.mcsw.minerad.MineRad;

public abstract class RecipeHandlerMR extends TemplateRecipeHandler {
	
	String unloc;
	
	public RecipeHandlerMR(String unloc) {
		this.unloc = "container." + MineRad.MODID + ":" + unloc;
	}

	@Override
	public String getRecipeName() {
		return LangUtil.translate(unloc);
	}

}
