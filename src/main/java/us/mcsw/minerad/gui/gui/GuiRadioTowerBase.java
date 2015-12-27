package us.mcsw.minerad.gui.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import us.mcsw.core.GuiMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.gui.container.ContainerRadioTowerBase;
import us.mcsw.minerad.net.MessageUpdateFrequency;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileRadioTowerBase;

public class GuiRadioTowerBase extends GuiMR {

	TileRadioTowerBase tile;

	public GuiRadioTowerBase(InventoryPlayer pl, TileRadioTowerBase tile) {
		super(new ContainerRadioTowerBase(pl, tile), MachineReference.RADIO_TOWER_GUI);
		this.tile = tile;
	}

	@Override
	public void initGui() {
		super.initGui();

		this.buttonList.add(new GuiButton(0, this.guiLeft + 25, this.guiTop + 40, 20, 20, "<"));
		this.buttonList.add(new GuiButton(1, this.guiLeft + this.xSize - 45, this.guiTop + 40, 20, 20, ">"));

		this.buttonList.add(new GuiButton(2, this.guiLeft + 4, this.guiTop + 40, 20, 20, "<<"));
		this.buttonList.add(new GuiButton(3, this.guiLeft + this.xSize - 24, this.guiTop + 40, 20, 20, ">>"));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			tile.freq--;
		}
		if (button.id == 1) {
			tile.freq++;
		}
		if (button.id == 2) {
			tile.freq -= 10;
		}
		if (button.id == 3) {
			tile.freq += 10;
		}
		if (tile.freq < 0) {
			tile.freq = 0;
		}
		if (tile.freq > 9999) {
			tile.freq = 9999;
		}
		updateScreen();
		MineRad.network.sendToServer(new MessageUpdateFrequency(tile.xCoord, tile.yCoord, tile.zCoord, tile.freq));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = I18n.format("container." + tile.getInventoryName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);

		int antennae = tile.getAntennaCount();
		fontRendererObj.drawString("Antennae: " + antennae, 5, 20, 4210752);

		int range = tile.getRange();
		fontRendererObj.drawString("Range: " + range, 5, 30, 4210752);

		int freq = tile.freq;
		String f = "Frequency: " + freq;
		fontRendererObj.drawString(f, (this.xSize / 2) - (fontRendererObj.getStringWidth(f) / 2), 45, 4210752);

		int power = tile.storage.getEnergyStored();
		int maxPower = tile.storage.getMaxEnergyStored();
		String energy = power + " / " + maxPower + " RF";
		this.fontRendererObj.drawString(energy, this.xSize / 2 - this.fontRendererObj.getStringWidth(power + ""), 65,
				4210752);
	}

}
