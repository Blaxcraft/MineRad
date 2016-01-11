package us.mcsw.minerad.gui.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import us.mcsw.core.GuiMR;
import us.mcsw.minerad.gui.container.ContainerFilter;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TileFilter;

public class GuiFilter extends GuiMR {

	TileFilter tile;

	public GuiFilter(InventoryPlayer pl, TileFilter tile) {
		super(new ContainerFilter(pl, tile), TextureReference.FILTER_GUI);
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);

		String s = I18n.format("container." + tile.getInventoryName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 5, 4210752);
	}

}
