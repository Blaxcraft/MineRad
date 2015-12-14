package us.mcsw.core;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public abstract class GuiMRMachine extends GuiMR {

	ContainerMRMachine con;

	public GuiMRMachine(ContainerMRMachine con, ResourceLocation texture) {
		super(con, texture);
		this.con = con;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = I18n.format("container." + con.tile.getInventoryName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);

		int power = con.tile.storage.getEnergyStored();
		int maxPower = con.tile.storage.getMaxEnergyStored();
		String energy = power + " / " + maxPower + " RF";
		this.fontRendererObj.drawString(energy, this.xSize / 2 - this.fontRendererObj.getStringWidth(power + ""), 70,
				4210752);
	}

}
