package us.mcsw.minerad.tiles;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.TileMRMachine;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.ref.CapacitorTier;

public class TileRadioTowerBase extends TileMRMachine {

	public TileRadioTowerBase() {
		super(CapacitorTier.DIAMOND.getMachineCapacity(), CapacitorTier.DIAMOND.getMaxTransferMachine(), 2);
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			if (items[0] != null) {
				if (items[0].stackSize <= 0) {
					items[0] = null;
					updateEntity();
					return;
				}
				int sourceId = 0;
				int energyToUse = 0;
				ArrayList<TileRadioTowerBase> near = getConnectedTowersInRange();
				if (near.size() > 0) {
					Collections.shuffle(near);
					for (TileRadioTowerBase tile : near) {
						if (tile.canReceiveItem(items[sourceId])) {
							int destId = 1;
							if (tile.items[destId] == null || tile.items[destId].stackSize <= 0) {
								tile.items[destId] = items[sourceId].copy();
								energyToUse += getEnergyCost(items[sourceId]);
								items[sourceId].stackSize = 0;
							} else if (ItemUtil.areItemStacksEqual(items[sourceId], tile.items[destId])) {
								int maxStack = Math.min(tile.getInventoryStackLimit(),
										tile.items[destId].getMaxStackSize());
								if (tile.items[destId].stackSize < maxStack) {
									tile.items[destId].stackSize += items[sourceId].stackSize;
									energyToUse += getEnergyCost(items[sourceId]);
									if (tile.items[destId].stackSize > maxStack) {
										items[sourceId].stackSize = tile.items[destId].stackSize - maxStack;
										energyToUse -= getEnergyCost(items[sourceId]);
										tile.items[destId].stackSize = maxStack;
									} else {
										items[sourceId].stackSize = 0;
									}
								}
							}
						}
					}
				}
				if (storage.extractEnergy(energyToUse, false) > 0) {
					markDirty();
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			if (items[1] != null) {
				items[1] = ItemUtil.addItemToNearbyInventories(this, items[1], true, false, pushDirs);
			}
		}
	}

	private static final ForgeDirection[] pushDirs = { ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH,
			ForgeDirection.WEST, ForgeDirection.DOWN };

	public boolean canReceiveItem(ItemStack it) {
		if (ItemUtil.addItemToNearbyInventories(this, it, true, true, pushDirs) != null) {
			return false;
		}

		ItemStack ch = items[1];
		if (ch == null || ch.stackSize <= 0) {
			return true;
		}
		if (ItemUtil.areItemStacksEqual(ch, it)) {
			int maxStack = Math.min(getInventoryStackLimit(), ch.getMaxStackSize());
			if (ch.stackSize < maxStack) {
				return true;
			}
		}
		return false;
	}

	public boolean canTransmitItem(ItemStack it) {
		if (storage.getEnergyStored() < getEnergyCost(it)) {
			return false;
		}
		for (TileRadioTowerBase tile : getConnectedTowersInRange()) {
			if (tile.canReceiveItem(it)) {
				return true;
			}
		}
		return false;
	}

	public int freq = 0;

	public int getAntennaCount() {
		int count = 0;
		for (int y = yCoord + 1; y < worldObj.getHeight(); y++) {
			if (worldObj.getBlock(xCoord, y, zCoord).equals(ModBlocks.radioTowerAntenna)) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	public int getEnergyCost(ItemStack it) {
		return getEnergyCost((double) it.stackSize / (double) it.getMaxStackSize());
	}

	public int getEnergyCost(double stacks) {
		return (int) (stacks * 640.0d);
	}

	public int getRange() {
		return (getAntennaCount() + 1) * (getAntennaCount() + 2);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack it, int side) {
		if (slot == 0) {
			return getConnectedTowersInRange().size() <= 0;
		}
		if (slot == 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack it, int side) {
		if (i == 0) {
			return canTransmitItem(it);
		}
		return super.canInsertItem(i, it, side);
	}

	@Override
	public String getInventoryName() {
		return MineRad.MODID + ":radioTowerBase";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack it) {
		return slot == 0;
	}

	public ArrayList<TileRadioTowerBase> getConnectedTowersInRange() {
		ArrayList<TileRadioTowerBase> ret = new ArrayList<TileRadioTowerBase>();
		for (Object o : worldObj.loadedTileEntityList) {
			if (o instanceof TileRadioTowerBase) {
				TileRadioTowerBase tile = (TileRadioTowerBase) o;
				if (!tile.equals(this)) {
					if (tile.freq == this.freq && tile.getDistanceFrom(xCoord + 0.5, yCoord + 0.5,
							zCoord + 0.5) <= getRange() * getRange()) {
						ret.add(tile);
					}
				}
			}
		}
		return ret;
	}

	@Override
	public void writeSyncable(NBTTagCompound data) {
		data.setInteger("Frequency", freq);
	}

	@Override
	public void readSyncable(NBTTagCompound data) {
		freq = data.getInteger("Frequency");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		data.setInteger("Frequency", freq);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		freq = data.getInteger("Frequency");
	}

}
