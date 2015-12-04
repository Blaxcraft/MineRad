package us.mcsw.minerad.inv;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.util.LogUtil;

public abstract class GuiMRMachine extends GuiContainer {

	ResourceLocation texture;
	ContainerMRMachine con;

	public GuiMRMachine(ContainerMRMachine con, ResourceLocation texture) {
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

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = I18n.format("container." + con.tile.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);

		int power = con.tile.storage.getEnergyStored();
		int maxPower = con.tile.storage.getMaxEnergyStored();
		String energy = power + " / " + maxPower + " RF";
		this.fontRendererObj.drawString(energy, this.xSize / 2 - this.fontRendererObj.getStringWidth(power + ""), 70,
				4210752);
	}

}
