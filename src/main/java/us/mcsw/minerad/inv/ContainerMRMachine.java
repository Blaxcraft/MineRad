package us.mcsw.minerad.inv;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import us.mcsw.core.TileMRMachine;

public abstract class ContainerMRMachine extends Container {

	TileMRMachine tile;

	public ContainerMRMachine(InventoryPlayer ip, TileMRMachine tile) {
		this.tile = tile;

		addSlotsToContainer(tile);
		addPlayerInventory(ip);
	}

	int lastStored = 0;

	@Override
	public void addCraftingToCrafters(ICrafting c) {
		super.addCraftingToCrafters(c);
		c.sendProgressBarUpdate(this, 0, tile.storage.getEnergyStored());
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (Object o : crafters) {
			ICrafting c = (ICrafting) o;

			if (lastStored != tile.storage.getEnergyStored()) {
				c.sendProgressBarUpdate(this, 0, tile.storage.getEnergyStored());
			}
		}
		lastStored = tile.storage.getEnergyStored();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int i, int v) {
		if (i == 0) {
			tile.storage.setEnergyStored(v);
		}
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

	public abstract void addSlotsToContainer(TileMRMachine tile);

	@Override
	public boolean canInteractWith(EntityPlayer pl) {
		return tile.isUseableByPlayer(pl);
	}

	// Identical except for one line, marked below
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

				if (itemstack1 != null && itemstack1.getItem() == it.getItem()
						&& (!it.getHasSubtypes() || it.getItemDamage() == itemstack1.getItemDamage())
						&& ItemStack.areItemStackTagsEqual(it, itemstack1)) {
					int l = itemstack1.stackSize + it.stackSize;

					if (l <= it.getMaxStackSize()) {
						it.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					} else if (itemstack1.stackSize < it.getMaxStackSize()) {
						it.stackSize -= it.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = it.getMaxStackSize();
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

				// added check to see if item can actually go into the slot
				if (itemstack1 == null && slot.isItemValid(it)) {
					slot.putStack(it.copy());
					slot.onSlotChanged();
					it.stackSize = 0;
					flag1 = true;
					break;
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
