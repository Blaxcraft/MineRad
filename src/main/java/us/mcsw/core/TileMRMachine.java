package us.mcsw.core;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileMRMachine extends TileMRInventory implements IEnergyReceiver, ISidedInventory {

	public EnergyStorage storage;

	public TileMRMachine(int capacity, int transfer, int size) {
		super(size);
		storage = new EnergyStorage(capacity, transfer);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
	public boolean canInsertItem(int i, ItemStack it, int side) {
		return isItemValidForSlot(i, it);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		storage.writeToNBT(data);
		writeSyncable(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, data);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		storage.readFromNBT(pkt.func_148857_g());
		readSyncable(pkt.func_148857_g());
	}

	public void writeSyncable(NBTTagCompound data) {
	}

	public void readSyncable(NBTTagCompound data) {
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		storage.readFromNBT(data);
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		storage.writeToNBT(data);
	}
}
