
package us.mcsw.minerad.gui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import us.mcsw.core.GuiMRMachine;
import us.mcsw.minerad.gui.container.ContainerWaterDistiller;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileWaterDistiller;

public class GuiWaterDistiller extends GuiMRMachine {
	
	TileWaterDistiller tile;

	public GuiWaterDistiller(InventoryPlayer pl, TileWaterDistiller tile) {
		super(new ContainerWaterDistiller(pl, tile), MachineReference.WATER_DISTILLER_GUI);
		this.tile = tile;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		if (tile.progress > 0) {
			int prog = tile.progress * 24 / tile.getMaxProgress();
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			this.drawTexturedModalRect(k + 73, l + 34, 176, 14, prog + 1, 16);
		}
	}

}
