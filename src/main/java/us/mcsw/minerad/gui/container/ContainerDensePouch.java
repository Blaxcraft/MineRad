package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ContainerMR;
import us.mcsw.minerad.gui.ItemInventory;

public class ContainerDensePouch extends ContainerMR {

	ItemInventory inv;

	public ContainerDensePouch(InventoryPlayer ip, ItemInventory inv) {
		super(ip, inv);
		this.inv = inv;
	}

	@Override
	public void addSlotsToContainer(IInventory inv) {
		int numRows = inv.getSizeInventory() / 9;
		int i = (numRows - 4) * 18;

		for (int j = 0; j < numRows; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(inv, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}

		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(ip, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
			}
		}

		for (int j = 0; j < 9; ++j) {
			if (ip.currentItem == j) {
				this.addSlotToContainer(new Slot(ip, j, 8 + j * 18, 161 + i) {
					@Override
					public boolean canTakeStack(EntityPlayer pl) {
						return false;
					}
				});
			} else
				this.addSlotToContainer(new Slot(ip, j, 8 + j * 18, 161 + i));
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		ItemInventory inv = (ItemInventory) this.inv;
		ItemInventory.setInItem(player.getCurrentEquippedItem(), inv);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
