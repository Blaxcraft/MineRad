package us.mcsw.core;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiMR extends GuiContainer {

	ResourceLocation texture;
	Container con;

	public GuiMR(Container con, ResourceLocation texture) {
		super(con);
		this.texture = texture;
		this.con = con;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2, y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
