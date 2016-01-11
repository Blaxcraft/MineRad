package us.mcsw.minerad.gui.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import us.mcsw.core.GuiMRMachine;
import us.mcsw.minerad.gui.container.ContainerShieldGenerator;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileShieldGenerator;

public class GuiShieldGenerator extends GuiMRMachine {

	TileShieldGenerator tile;

	public GuiShieldGenerator(InventoryPlayer pl, TileShieldGenerator tile) {
		super(new ContainerShieldGenerator(pl, tile), MachineReference.SHIELD_GENERATOR_GUI);
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);

		String rad = I18n.format("info.shield.radius", tile.getRadius());
		this.fontRendererObj.drawString(rad, this.xSize / 4 - this.fontRendererObj.getStringWidth(rad) / 2, 60,
				4210752);
		
		String prod = I18n.format("info.machine.usage", tile.isRunning() ? tile.getUsagePerTick() : 0);
		this.fontRendererObj.drawString(prod, this.xSize / 4 - this.fontRendererObj.getStringWidth(prod) / 2, 50,
				4210752);
	}

}
