package us.mcsw.core;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import us.mcsw.minerad.ref.CapacitorTier;

public class ItemMREnergy extends ItemMR implements IEnergyContainerItem {

	public int maxTransfer, capacity;

	public ItemMREnergy(String unloc, int maxTransfer, int capacity) {
		super(unloc);
		this.maxTransfer = maxTransfer;
		this.capacity = capacity;
	}

	public int getDamageFromEnergy(ItemStack stack) {
		EnergyStorage storage = getFromItemstack(stack);
		return (int) ((double) (storage.getMaxEnergyStored() - storage.getEnergyStored())
				/ (double) storage.getMaxEnergyStored() * (double) getMaxDamage(stack));
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		EnergyStorage storage = getFromItemstack(container);
		int ret = storage.receiveEnergy(maxReceive, simulate);
		if (!simulate) {
			setEnergyInItemStack(container, storage.getEnergyStored());
		}
		return ret;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		EnergyStorage storage = getFromItemstack(container);
		int ret = storage.extractEnergy(maxExtract, simulate);
		if (!simulate) {
			setEnergyInItemStack(container, storage.getEnergyStored());
		}
		return ret;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return getFromItemstack(container).getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return capacity;
	}

	public EnergyStorage getFromItemstack(ItemStack it) {
		if (!it.hasTagCompound()) {
			it.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound data = it.getTagCompound();
		if (!data.hasKey("energy")) {
			data.setInteger("energy", 0);
		}
		EnergyStorage ret = new EnergyStorage(capacity, maxTransfer);
		ret.setEnergyStored(data.getInteger("energy"));
		return ret;
	}

	public void setEnergyInItemStack(ItemStack it, int energy) {
		EnergyStorage current = getFromItemstack(it);
		current.setEnergyStored(energy);

		it.getTagCompound().setInteger("energy", current.getEnergyStored());
		it.setItemDamage(getDamage(it));
	}
	
	@Override
	public void getSubItems(Item i, CreativeTabs tab, List list) {
		ItemStack empty = new ItemStack(i);
		setEnergyInItemStack(empty, 0);
		ItemStack full = new ItemStack(i);
		setEnergyInItemStack(full, capacity);
		list.add(empty);
		list.add(full);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer pl, List list, boolean n) {
		list.add(getFromItemstack(stack).getEnergyStored() + " / " + capacity + " RF");
	}

}
