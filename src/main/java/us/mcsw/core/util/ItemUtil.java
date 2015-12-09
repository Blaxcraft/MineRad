package us.mcsw.core.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemUtil {

	public static boolean pushToNearbyInventories(TileEntity start, ItemStack toAdd, boolean simulate,
			ForgeDirection... pushDirs) {
		return pushToNearbyInventories(start, toAdd, true, simulate, pushDirs);
	}

	public static boolean pushToNearbyInventories(TileEntity start, ItemStack toAdd, boolean excludePairs,
			boolean simulate, ForgeDirection... pushDirs) {
		ItemStack it;
		if (simulate) {
			it = toAdd.copy();
		} else
			it = toAdd;
		for (ForgeDirection dir : pushDirs) {
			if (it.stackSize <= 0) {
				break;
			}
			TileEntity te = start.getWorldObj().getTileEntity(start.xCoord + dir.offsetX, start.yCoord + dir.offsetY,
					start.zCoord + dir.offsetZ);
			if (te != null && te instanceof IInventory
					&& (excludePairs ? !te.getClass().equals(start.getClass()) : true)) {
				if (te instanceof ISidedInventory) {
					ISidedInventory inv = (ISidedInventory) te;
					int sideTest = dir.getOpposite().ordinal();
					for (int slot : inv.getAccessibleSlotsFromSide(sideTest)) {
						if (it.stackSize <= 0) {
							break;
						}
						if (inv.canInsertItem(slot, it, sideTest) && inv.isItemValidForSlot(slot, it)) {
							addItem(inv, slot, it, simulate);
						}
					}
				} else {
					IInventory inv = (IInventory) te;
					for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
						if (it.stackSize <= 0) {
							break;
						}
						if (inv.isItemValidForSlot(slot, it)) {
							addItem(inv, slot, it, simulate);
						}
					}
				}
			}
		}
		return it.stackSize < toAdd.stackSize;
	}

	public static void addItem(IInventory inv, int slot, ItemStack it, boolean simulate) {
		ItemStack cur, toCheck = inv.getStackInSlot(slot);
		if (simulate && toCheck != null) {
			cur = toCheck.copy();
		} else
			cur = toCheck;

		int maxStack = Math.min(inv.getInventoryStackLimit(), it.getMaxStackSize());
		if (cur != null) {
			if (ItemStack.areItemStacksEqual(cur, it)) {
				if (cur.stackSize + it.stackSize > maxStack) {
					it.stackSize = (cur.stackSize + it.stackSize) - maxStack;
					cur.stackSize = maxStack;
				} else {
					cur.stackSize += it.stackSize;
					it.stackSize = 0;
				}
			}
		} else {
			if (it.stackSize > maxStack) {
				if (!simulate) {
					ItemStack set = it.splitStack(maxStack);
					inv.setInventorySlotContents(slot, set);
				} else {
					it.stackSize -= maxStack;
				}
			} else {
				if (!simulate) {
					inv.setInventorySlotContents(slot, it.copy());
				}
				it.stackSize = 0;
			}
		}
	}

}
