package us.mcsw.minerad.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.ItemStack;
import us.mcsw.core.util.LangUtil;
import us.mcsw.minerad.MineRad;

public abstract class RecipeHandlerMR extends TemplateRecipeHandler {

	String unloc;

	public static final int offsetX = -5, offsetY = -11;

	public RecipeHandlerMR(String unloc) {
		this.unloc = "container." + MineRad.MODID + ":" + unloc;
	}

	@Override
	public String getRecipeName() {
		return LangUtil.translate(unloc);
	}
	
	public PositionedStack makeStack(ItemStack it, int x, int y) {
		return new PositionedStack(it, x + offsetX, y + offsetY);
	}

}
