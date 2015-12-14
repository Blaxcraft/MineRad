package us.mcsw.minerad.inv;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import us.mcsw.core.ContainerMR;
import us.mcsw.core.GuiMR;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileFissionReactor;

public class GuiFissionReactor extends GuiMR {
	
	TileFissionReactor tile;

	public GuiFissionReactor(InventoryPlayer pl, TileFissionReactor tile) {
		super(new ContainerFissionReactor(pl, tile), MachineReference.FISSION_REACTOR_GUI);
		this.tile = tile;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		
		String s = I18n.format("container." + tile.getInventoryName());
		this.fontRendererObj.drawString(s, this.xSize / 4 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		
		String heat = I18n.format("info.reactor.heat");
		String disp = heat + I18n.format("info.reactor.heat." + tile.getHeatLevel());
		this.fontRendererObj.drawString(disp, this.xSize / 4 - this.fontRendererObj.getStringWidth(disp) / 2, 65, 4210752);
	}

}
