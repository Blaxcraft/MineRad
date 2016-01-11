package us.mcsw.minerad.gui.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import us.mcsw.core.GuiMRMachine;
import us.mcsw.minerad.gui.container.ContainerRadioactiveGenerator;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileRadioactiveGenerator;

public class GuiRadioactiveGenerator extends GuiMRMachine {

	TileRadioactiveGenerator tile;

	public GuiRadioactiveGenerator(InventoryPlayer pl, TileRadioactiveGenerator tile) {
		super(new ContainerRadioactiveGenerator(pl, tile), MachineReference.RADIO_GENERATOR_GUI);
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);

		String disp = I18n.format("info.reactor.heat", I18n.format("info.reactor.heat." + tile.getHeatLevel()));
		this.fontRendererObj.drawString(disp, this.xSize / 6 - this.fontRendererObj.getStringWidth(disp) / 2, 60,
				4210752);

		String prod = I18n.format("info.generator.output", tile.getEnergyPerTick());
		this.fontRendererObj.drawString(prod, this.xSize * 11 / 16 - this.fontRendererObj.getStringWidth(prod) / 2, 60,
				4210752);
	}

}
