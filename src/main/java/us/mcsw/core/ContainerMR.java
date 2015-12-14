package us.mcsw.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.core.util.LogUtil;

public abstract class ContainerMR extends Container {

	TileMRInventory tile;

	public ContainerMR(InventoryPlayer ip, TileMRInventory tile) {
		this.tile = tile;

		addSlotsToContainer(tile);
	}

	// player's inventory
	public void addPlayerInventory(InventoryPlayer pl) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(pl, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(pl, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer pl, int s) {
		ItemStack it = null;
		Slot slot = (Slot) inventorySlots.get(s);
		if (slot != null && slot.getHasStack()) {
			ItemStack cur = slot.getStack();
			it = cur.copy();

			if (s < tile.getSizeInventory()) {
				if (!mergeItemStack(cur, tile.getSizeInventory(), tile.getSizeInventory() + 36, true)) {
					return null;
				}
			} else if (!mergeItemStack(cur, 0, tile.getSizeInventory(), false)) {
				return null;
			}

			if (cur.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			if (cur.stackSize == it.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(pl, cur);
		}
		return it;
	}

	public abstract void addSlotsToContainer(TileMRInventory tile);

	@Override
	public boolean canInteractWith(EntityPlayer pl) {
		return tile.isUseableByPlayer(pl);
	}

	// Identical to superclass except for commented lines
	@Override
	protected boolean mergeItemStack(ItemStack it, int min, int max, boolean backwards) {
		boolean flag1 = false;
		int k = min;

		if (backwards) {
			k = max - 1;
		}

		Slot slot;
		ItemStack itemstack1;

		if (it.isStackable()) {
			while (it.stackSize > 0 && (!backwards && k < max || backwards && k >= min)) {
				slot = (Slot) this.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				// changed to ItemStack.areItemStacksEqual
				if (ItemStack.areItemStacksEqual(itemstack1, it)) {
					int l = itemstack1.stackSize + it.stackSize;
					// added max stack size check for slot and inventory
					int maxStackSize = Math.min(it.getMaxStackSize(),
							Math.min(slot.getSlotStackLimit(), backwards ? 64 : tile.getInventoryStackLimit()));

					if (l <= maxStackSize) {
						it.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					} else if (itemstack1.stackSize < maxStackSize) {
						it.stackSize -= maxStackSize - itemstack1.stackSize;
						itemstack1.stackSize = maxStackSize;
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if (backwards) {
					--k;
				} else {
					++k;
				}
			}
		}

		if (it.stackSize > 0) {
			if (backwards) {
				k = max - 1;
			} else {
				k = min;
			}

			while (!backwards && k < max || backwards && k >= min) {
				slot = (Slot) this.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				// added check to see if item can actually go into the slot +
				// max stack check
				int maxStackSize = Math.min(it.getMaxStackSize(),
						Math.min(slot.getSlotStackLimit(), backwards ? 64 : tile.getInventoryStackLimit()));
				if (itemstack1 == null && slot.isItemValid(it)) {
					if (it.stackSize <= maxStackSize) {
						slot.putStack(it.copy());
						slot.onSlotChanged();
						it.stackSize = 0;
						flag1 = true;
						break;
					} else {
						ItemStack put = it.copy();
						put.stackSize = maxStackSize;
						slot.putStack(put);
						it.stackSize -= maxStackSize;
						flag1 = true;
					}
				}

				if (backwards) {
					--k;
				} else {
					++k;
				}
			}
		}

		return flag1;
	}

}
