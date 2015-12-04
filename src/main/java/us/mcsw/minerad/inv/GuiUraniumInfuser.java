package us.mcsw.minerad.inv;

import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileUraniumInfuser;

public class GuiUraniumInfuser extends GuiMRMachine {

	private TileUraniumInfuser tile;

	public GuiUraniumInfuser(InventoryPlayer pl, TileUraniumInfuser tile) {
		super(new ContainerUraniumInfuser(pl, tile), MachineReference.URANIUM_INFUSER_GUI);
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		if (tile.progress > 0) {
			int prog = tile.progress * 24 / tile.getMaxProgress();
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			this.drawTexturedModalRect(k + 79, l + 34, 176, 14, prog + 1, 16);
		}
	}

}
