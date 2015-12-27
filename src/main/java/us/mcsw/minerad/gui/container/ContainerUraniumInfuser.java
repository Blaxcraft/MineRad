package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ContainerMRMachine;
import us.mcsw.core.TileMRInventory;
import us.mcsw.core.TileMRMachine;
import us.mcsw.minerad.gui.slot.SlotCoreEmpty;
import us.mcsw.minerad.gui.slot.SlotProduct;
import us.mcsw.minerad.recipes.UraniumInfuserRecipes;
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
	public void addSlotsToContainer(IInventory inv) {
		addSlotToContainer(new SlotCoreEmpty(inv, 0, 34, 35));
		addSlotToContainer(new Slot(inv, 1, 56, 35) {
			public boolean isItemValid(ItemStack it) {
				return UraniumInfuserRecipes.getRecipeFor(it, false) != null;
			}
		});
		addSlotToContainer(new SlotProduct(inv, 2, 116, 35));
	}

}
