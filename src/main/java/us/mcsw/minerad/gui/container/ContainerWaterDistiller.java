package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import us.mcsw.core.ContainerMRMachine;
import us.mcsw.minerad.gui.slot.SlotCoreEmpty;
import us.mcsw.minerad.gui.slot.SlotProduct;
import us.mcsw.minerad.tiles.TileWaterDistiller;

public class ContainerWaterDistiller extends ContainerMRMachine {

	TileWaterDistiller tile;

	public ContainerWaterDistiller(InventoryPlayer ip, TileWaterDistiller tile) {
		super(ip, tile);
		this.tile = tile;
	}

	int lastProgress = 0;

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (Object o : crafters) {
			ICrafting c = (ICrafting) o;

			if (lastProgress != tile.progress) {
				c.sendProgressBarUpdate(this, 1, tile.progress);
			}
		}

		lastProgress = tile.progress;
	}

	@Override
	public void updateProgressBar(int i, int v) {
		super.updateProgressBar(i, v);
		if (i == 1) {
			tile.progress = v;
		}
	}

	@Override
	public void addSlotsToContainer(IInventory inv) {
		addSlotToContainer(new SlotCoreEmpty(inv, 0, 51, 35));
		addSlotToContainer(new SlotProduct(inv, 1, 111, 35));
	}

}
