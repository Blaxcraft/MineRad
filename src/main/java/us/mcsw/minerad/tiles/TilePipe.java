package us.mcsw.minerad.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ref.CapacitorTier;

public class TilePipe extends TileEntity implements IEnergyConnection {

	private CapacitorTier tier;

	public void setTier(CapacitorTier tier) {
		this.tier = tier;
		this.markDirty();
	}

	public CapacitorTier getTier() {
		return tier;
	}

	public ForgeDirection[] pushDirs = { ForgeDirection.UP, ForgeDirection.DOWN, ForgeDirection.NORTH,
			ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST };

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			for (ForgeDirection dir : pushDirs) {
				TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY,
						zCoord + dir.offsetZ);
				if (te != null && te instanceof IEnergyReceiver) {
					IEnergyReceiver receive = (IEnergyReceiver) te;
					if (receive.receiveEnergy(dir.getOpposite(), getTier().getMaxTransferPipe(), true) > 0) {
						int baseEnergyTransfer = 0;
						for (Entry<IEnergyProvider, ForgeDirection> e : getConnectedProviders().entrySet()) {
							baseEnergyTransfer += e.getKey().extractEnergy(e.getValue(), getTier().getMaxTransferPipe(),
									true);
						}
						int topEnergyTransfer = baseEnergyTransfer;
						baseEnergyTransfer -= receive.receiveEnergy(dir.getOpposite(),
								Math.min(baseEnergyTransfer, getTier().getMaxTransferPipe()), false);
						int energyToTransfer = topEnergyTransfer - baseEnergyTransfer;
						for (Entry<IEnergyProvider, ForgeDirection> e : getConnectedProviders().entrySet()) {
							if (energyToTransfer > 0) {
								energyToTransfer -= e.getKey().extractEnergy(e.getValue(),
										Math.min(getTier().getMaxTransferPipe(), energyToTransfer), false);
							}
						}
					}
				}
			}
		}
	}

	public boolean isConnected(ForgeDirection side) {
		int cx = xCoord + side.offsetX;
		int cy = yCoord + side.offsetY;
		int cz = zCoord + side.offsetZ;
		TileEntity te = worldObj.getTileEntity(cx, cy, cz);
		if (te != null) {
			if (te instanceof TilePipe) {
				TilePipe tp = (TilePipe) te;
				return tp.willConnect(side.getOpposite(), this);
			} else if (te instanceof IEnergyConnection) {
				IEnergyConnection tc = (IEnergyConnection) te;
				return tc.canConnectEnergy(side.getOpposite());
			}
		}
		return false;
	}

	public boolean willConnect(ForgeDirection side, TilePipe connectTo) {
		if (connectTo.tier != tier) {
			return false;
		}
		return true;
	}

	// public HashMap<IEnergyReceiver, ForgeDirection> getConnectedReceivers() {
	// HashMap<IEnergyReceiver, ForgeDirection> ret = new
	// HashMap<IEnergyReceiver, ForgeDirection>();
	// for (TilePipe tp : getConnectedPipes()) {
	// for (ForgeDirection dir : tp.pushDirs) {
	// if (isConnected(dir)) {
	// TileEntity te = worldObj.getTileEntity(tp.xCoord + dir.offsetX, tp.yCoord
	// + dir.offsetY,
	// tp.zCoord + dir.offsetZ);
	// if (te != null && te instanceof IEnergyReceiver) {
	// IEnergyReceiver add = (IEnergyReceiver) te;
	// if (add.canConnectEnergy(dir.getOpposite()) && !ret.containsKey(add)) {
	// ret.put(add, dir.getOpposite());
	// }
	// }
	// }
	// }
	// }
	// return ret;
	// }

	public HashMap<IEnergyProvider, ForgeDirection> getConnectedProviders() {
		HashMap<IEnergyProvider, ForgeDirection> ret = new HashMap<IEnergyProvider, ForgeDirection>();
		for (TilePipe tp : getConnectedPipes()) {
			for (ForgeDirection dir : tp.pushDirs) {
				if (tp.isConnected(dir)) {
					TileEntity te = worldObj.getTileEntity(tp.xCoord + dir.offsetX, tp.yCoord + dir.offsetY,
							tp.zCoord + dir.offsetZ);
					if (te != null) {
						if (te instanceof IEnergyProvider) {
							IEnergyProvider add = (IEnergyProvider) te;
							if (!ret.containsKey(add)) {
								ret.put(add, dir.getOpposite());
							}
						}
					}
				}
			}
		}
		return ret;
	}

	public ArrayList<TilePipe> getConnectedPipes() {
		return getConnectedPipes(new ArrayList<TilePipe>());
	}

	public ArrayList<TilePipe> getConnectedPipes(ArrayList<TilePipe> recur) {
		recur.add(this);
		for (ForgeDirection dir : pushDirs) {
			if (isConnected(dir)) {
				TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY,
						zCoord + dir.offsetZ);
				if (te instanceof TilePipe) {
					TilePipe tp = (TilePipe) te;
					if (!recur.contains(tp)) {
						ArrayList<TilePipe> pipes = tp.getConnectedPipes((ArrayList<TilePipe>) recur.clone());
						for (TilePipe p : pipes) {
							if (!recur.contains(p)) {
								recur.add(p);
							}
						}
					}
				}
			}
		}
		return recur;
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		CapacitorTier.writeToNBT(data, tier);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		tier = CapacitorTier.readFromNBT(data);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound sync = new NBTTagCompound();
		CapacitorTier.writeToNBT(sync, getTier());
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, sync);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		tier = CapacitorTier.readFromNBT(pkt.func_148857_g());
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

}
