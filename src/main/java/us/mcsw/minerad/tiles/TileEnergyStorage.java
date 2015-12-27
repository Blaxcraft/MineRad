package us.mcsw.minerad.tiles;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.util.EnergyUtil;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ref.CapacitorTier;

public class TileEnergyStorage extends TileEntity implements IEnergyHandler {

	public EnergyStorage storage;
	CapacitorTier tier;

	byte[] sideModes = { 0, 0, 0, 0, 0, 0 };

	public TileEnergyStorage() {
		setTier(CapacitorTier.BASIC);
	}

	@Override
	public void updateEntity() {
		for (int i = 0; i < sideModes.length; i++) {
			ForgeDirection side = ForgeDirection.getOrientation(i);
			if (isOutputtingToSide(side)) {
				EnergyUtil.pushEnergy(this, worldObj, xCoord, yCoord, zCoord, storage, side);
			}
		}
	}

	public void setTier(CapacitorTier tier) {
		this.tier = tier;
		int energy = 0;
		if (storage != null)
			energy = storage.getEnergyStored();
		storage = new EnergyStorage(tier.getStorageCapacity(), tier.getMaxTransferPipe());
		storage.setEnergyStored(energy);

		markDirty();
		if (worldObj != null) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public boolean isOutputtingToSide(ForgeDirection side) {
		return sideModes[side.ordinal()] == 1;
	}

	public void setOutputtingToSide(ForgeDirection side, boolean set) {
		sideModes[side.ordinal()] = (byte) (set ? 1 : 0);
		markDirty();
	}

	public CapacitorTier getTier() {
		return tier;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (!isOutputtingToSide(from)) {
			markDirty();
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return storage.receiveEnergy(maxReceive, simulate);
		}
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (isOutputtingToSide(from)) {
			markDirty();
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return storage.extractEnergy(maxExtract, simulate);
		}
		return 0;
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
	public Packet getDescriptionPacket() {
		NBTTagCompound sync = new NBTTagCompound();
		CapacitorTier.writeToNBT(sync, getTier());
		storage.writeToNBT(sync);
		sync.setByteArray("sideModes", sideModes);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, sync);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound sync = pkt.func_148857_g();
		setTier(CapacitorTier.readFromNBT(sync));
		storage.readFromNBT(sync);
		byte[] modes = sync.getByteArray("sideModes");
		for (int i = 0; i < sideModes.length; i++)
			sideModes[i] = modes[i];
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		CapacitorTier.writeToNBT(data, tier);
		storage.writeToNBT(data);

		data.setByteArray("sideModes", sideModes);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		setTier(CapacitorTier.readFromNBT(data));
		storage.readFromNBT(data);

		byte[] modes = data.getByteArray("sideModes");
		for (int i = 0; i < sideModes.length; i++)
			sideModes[i] = modes[i];
	}

}
