package us.mcsw.minerad.gui.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import us.mcsw.core.GuiMR;
import us.mcsw.minerad.gui.container.ContainerFusionReactor;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileFusionReactor;

public class GuiFusionReactor extends GuiMR {

	TileFusionReactor tile;

	public GuiFusionReactor(InventoryPlayer pl, TileFusionReactor tile) {
		super(new ContainerFusionReactor(pl, tile), MachineReference.FUSION_REACTOR_GUI);
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);

		String s = I18n.format("container." + tile.getInventoryName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);

		String disp = I18n.format("info.reactor.heat", I18n.format("info.reactor.heat." + tile.getHeatLevel()));
		this.fontRendererObj.drawString(disp, this.xSize / 4 - this.fontRendererObj.getStringWidth(disp) / 2, 65,
				4210752);
	}

}
