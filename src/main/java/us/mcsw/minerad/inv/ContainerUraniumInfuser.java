package us.mcsw.minerad.inv;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import us.mcsw.minerad.tiles.TileMRMachine;
import us.mcsw.minerad.tiles.TileUraniumInfuser;

public class ContainerUraniumInfuser extends ContainerMRMachine {
	
	TileUraniumInfuser tile;

	public ContainerUraniumInfuser(InventoryPlayer ip, TileUraniumInfuser tile) {
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
	public void addSlotsToContainer(TileMRMachine tile) {
		addSlotToContainer(new SlotCore(tile, 0, 8, 54));
		addSlotToContainer(new Slot(tile, 1, 56, 35));
		addSlotToContainer(new SlotProduct(tile, 2, 116, 35));
	}

}
