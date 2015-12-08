package us.mcsw.minerad.tiles;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileMRMachine extends TileEntity implements IEnergyReceiver, ISidedInventory {

	public EnergyStorage storage;
	protected ItemStack[] items;

	public TileMRMachine(int capacity, int transfer, int size) {
		storage = new EnergyStorage(capacity, transfer);
		items = new ItemStack[size];
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
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
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack it = getStackInSlot(i);
		if (it != null) {
			setInventorySlotContents(i, null);
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack it) {
		items[i] = it;
		if (it != null && it.stackSize > getInventoryStackLimit()) {
			it.stackSize = getInventoryStackLimit();
		}
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
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
				&& pl.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean canInsertItem(int i, ItemStack it, int side) {
		return isItemValidForSlot(i, it);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		storage.readFromNBT(data);

		NBTTagList inv = data.getTagList("Inventory", 10);
		for (int i = 0; i < inv.tagCount(); i++) {
			NBTTagCompound item = inv.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if (slot >= 0 && slot < items.length) {
				items[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		storage.writeToNBT(data);

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
}
