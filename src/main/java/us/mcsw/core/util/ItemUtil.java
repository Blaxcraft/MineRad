package us.mcsw.core.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemUtil {

	public static ItemStack addItemToNearbyInventories(TileEntity source, ItemStack add, boolean excludePairs,
			boolean sim, ForgeDirection... sides) {
		add = add.copy();
		for (ForgeDirection dir : sides) {
			if (add == null)
				break;
			TileEntity te = source.getWorldObj().getTileEntity(source.xCoord + dir.offsetX, source.yCoord + dir.offsetY,
					source.zCoord + dir.offsetZ);
			if (te != null && te instanceof IInventory
					&& (excludePairs ? !te.getClass().equals(source.getClass()) : true)) {
				IInventory inv = (IInventory) te;
				add = addItemToInventory(inv, dir.getOpposite(), add, sim);
			}
		}
		return add;
	}

	public static ItemStack addItemToInventory(IInventory inv, ForgeDirection side, ItemStack add, boolean sim) {
		add = add.copy();
		int[] slots = {};
		if (inv instanceof ISidedInventory) {
			ISidedInventory si = (ISidedInventory) inv;
			slots = si.getAccessibleSlotsFromSide(side.ordinal());
		} else {
			slots = new int[inv.getSizeInventory()];
			for (int i = 0; i < slots.length; i++) {
				slots[i] = i;
			}
		}
		for (int slot : slots) {
			if (add == null || add.stackSize <= 0)
				break;
			if (!inv.isItemValidForSlot(slot, add))
				continue;
			if (inv instanceof ISidedInventory && !((ISidedInventory) inv).canInsertItem(slot, add, side.ordinal()))
				continue;
			ItemStack in = inv.getStackInSlot(slot);
			if (sim && in != null)
				in = in.copy();
			if (in != null) {
				if (ItemUtil.areItemStacksEqual(in, add)) {
					int maxStack = Math.min(in.getMaxStackSize(), inv.getInventoryStackLimit());
					if (in.stackSize >= maxStack)
						continue;

					in.stackSize += add.stackSize;
					if (in.stackSize > maxStack) {
						add.stackSize = in.stackSize - maxStack;
						in.stackSize = maxStack;
					} else
						add = null;
				}
			} else {
				if (!sim)
					inv.setInventorySlotContents(slot, add.copy());
				add = null;
			}
		}
		if (add != null && add.stackSize <= 0) {
			add = null;
		}
		return add;
	}

	// because the one in ItemStack checks stack size -.-
	public static boolean areItemStacksEqual(ItemStack s1, ItemStack s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 == null || s2 == null) {
			return false;
		}

		return s1.getItem().equals(s2.getItem()) && s1.getItemDamage() == s2.getItemDamage()
				&& (s1.stackTagCompound == null && s2.stackTagCompound == null
						|| s1.stackTagCompound.equals(s2.stackTagCompound));
	}

}
