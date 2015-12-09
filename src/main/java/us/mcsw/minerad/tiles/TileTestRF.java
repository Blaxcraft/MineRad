package us.mcsw.minerad.tiles;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.TileEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.util.EnergyUtil;

public class TileTestRF extends TileEntity implements IEnergyHandler {

	EnergyStorage storage = new EnergyStorage(160000000, 8000);

	public TileTestRF() {

	}

	static final ForgeDirection[] push = { ForgeDirection.EAST, ForgeDirection.WEST };

	@Override
	public void updateEntity() {
		super.updateEntity();
		for (ForgeDirection fd : push) {
			EnergyUtil.pushEnergy(this, worldObj, xCoord, yCoord, zCoord, this.storage, fd);
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP && from != ForgeDirection.DOWN;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (from == ForgeDirection.EAST || from == ForgeDirection.WEST) {
			return 0;
		}
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (from == ForgeDirection.NORTH || from == ForgeDirection.SOUTH) {
			return 0;
		}
		return storage.extractEnergy(maxExtract, simulate);
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
