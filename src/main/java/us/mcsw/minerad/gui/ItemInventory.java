package us.mcsw.minerad.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import us.mcsw.minerad.MineRad;

public class ItemInventory implements IInventory {

	public ItemStack[] items;

	public ItemInventory(int size) {
		this.items = new ItemStack[size];
	}

	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items[i];
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack it) {
		return true;
	}

	@Override
	public ItemStack decrStackSize(int i, int a) {
		ItemStack it = getStackInSlot(i);
		if (it != null) {
			if (it.stackSize <= a) {
				setInventorySlotContents(i, null);
			} else {
				it = it.splitStack(a);
				if (it.stackSize == 0) {
					setInventorySlotContents(i, null);
				}
			}
		}
		return it;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack it) {
		items[i] = it;
		if (it != null && it.stackSize > getInventoryStackLimit()) {
			it.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack it = getStackInSlot(i);
		if (it != null) {
			setInventorySlotContents(i, null);
		}
		return null;
	}

	@Override
	public String getInventoryName() {
		return "container." + MineRad.MODID + ":densePouch";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer pl) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public void markDirty() {
	}

	public void readFromNBT(NBTTagCompound data) {
		NBTTagList inv = data.getTagList("Inventory", 10);
		for (int i = 0; i < inv.tagCount(); i++) {
			NBTTagCompound item = inv.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if (slot >= 0 && slot < items.length) {
				items[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	public void writeToNBT(NBTTagCompound data) {
		NBTTagList inv = new NBTTagList();
		for (int i = 0; i < items.length; i++) {
			ItemStack it = items[i];
			if (it != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				it.writeToNBT(item);
				inv.appendTag(item);
			}
		}
		data.setTag("Inventory", inv);
	}

	public static ItemInventory createInItem(ItemStack it, int size) {
		if (!it.hasTagCompound()) {
			it.stackTagCompound = new NBTTagCompound();
		}
		ItemInventory inv = new ItemInventory(size);
		inv.writeToNBT(it.stackTagCompound);
		return inv;
	}

	public static ItemInventory getFromItem(ItemStack it, int size) {
		if (!it.hasTagCompound()) {
			createInItem(it, size);
		}
		ItemInventory ret = new ItemInventory(size);
		ret.readFromNBT(it.stackTagCompound);
		return ret;
	}

	public static void setInItem(ItemStack it, ItemInventory inv) {
		if (!it.hasTagCompound()) {
			return;
		}
		inv.writeToNBT(it.stackTagCompound);
	}

}
