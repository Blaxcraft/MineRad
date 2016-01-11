package us.mcsw.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import us.mcsw.core.util.ItemUtil;

public abstract class ContainerMR extends Container {

	public IInventory inv;
	public InventoryPlayer ip;

	public ContainerMR(InventoryPlayer ip, IInventory inv) {
		this.ip = ip;
		this.inv = inv;
		addSlotsToContainer(inv);
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

			if (s < inv.getSizeInventory()) {
				if (!mergeItemStack(cur, inv.getSizeInventory(), inv.getSizeInventory() + 36, true)) {
					return null;
				}
			} else if (!mergeItemStack(cur, 0, inv.getSizeInventory(), false)) {
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

	public abstract void addSlotsToContainer(IInventory inv);

	@Override
	public boolean canInteractWith(EntityPlayer pl) {
		return true;
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
		ItemStack current;

		if (it.isStackable()) {
			while (it.stackSize > 0 && (!backwards && k < max || backwards && k >= min)) {
				slot = (Slot) this.inventorySlots.get(k);
				current = slot.getStack();

				// changed to ItemUtil.areItemStacksEqual
				if (current != null && ItemUtil.areItemStacksEqual(it, current)) {
					int l = current.stackSize + it.stackSize;
					// added max stack size check for slot and inventory
					int maxStackSize = Math.min(it.getMaxStackSize(), Math.min(slot.getSlotStackLimit(),
							backwards ? ip.getInventoryStackLimit() : inv.getInventoryStackLimit()));

					if (l <= maxStackSize) {
						it.stackSize = 0;
						current.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					} else if (current.stackSize < maxStackSize) {
						it.stackSize -= maxStackSize - current.stackSize;
						current.stackSize = maxStackSize;
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
				current = slot.getStack();

				// added check to see if item can actually go into the slot
				int maxStack = Math.min(slot.getSlotStackLimit(), inv.getInventoryStackLimit());
				if (current == null && slot.isItemValid(it)) {
					if (it.stackSize <= maxStack) {
						slot.putStack(it.copy());
						slot.onSlotChanged();
						it.stackSize = 0;
						flag1 = true;
						break;
					} else {
						slot.putStack(it.splitStack(maxStack));
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

		return flag1;
	}

}
