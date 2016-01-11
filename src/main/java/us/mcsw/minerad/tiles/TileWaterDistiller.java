package us.mcsw.minerad.tiles;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.TileMRMachine;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemEmptyCore;
import us.mcsw.minerad.ref.CapacitorTier;

public class TileWaterDistiller extends TileMRMachine {

	public int progress = 0;

	public TileWaterDistiller() {
		super(CapacitorTier.GOLD.getMachineCapacity(), CapacitorTier.GOLD.getMaxTransferMachine(), 2);
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote && canRun()) {
			progress += storage.extractEnergy(getUsagePerTick(), false);
			if (progress > getMaxProgress()) {
				getStackInSlot(0).stackSize--;
				if (getStackInSlot(0).stackSize <= 0)
					setInventorySlotContents(0, null);
				if (getStackInSlot(1) != null) {
					getStackInSlot(1).stackSize++;
				} else {
					setInventorySlotContents(1, new ItemStack(ModItems.deuteriumCore));
				}
				progress = 0;
			}
			markDirty();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public int getUsagePerTick() {
		return 40;
	}

	public int getMaxProgress() {
		return 30000;
	}

	public boolean canRun() {
		return getStackInSlot(0) != null && getStackInSlot(0).getItem() instanceof ItemEmptyCore
				&& getStackInSlot(0).stackSize > 0
				&& (getStackInSlot(1) == null || getStackInSlot(1).stackSize < getStackInSlot(1).getMaxStackSize())
				&& storage.getEnergyStored() > 40 && getNearbyWaterSources() >= 2;
	}

	public int getNearbyWaterSources() {
		int count = 0;
		for (ForgeDirection dir : new ForgeDirection[] { ForgeDirection.UP, ForgeDirection.DOWN, ForgeDirection.NORTH,
				ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST }) {
			if (Blocks.water
					.equals(worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ))) {
				count++;
			}
		}
		return count;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (side == ForgeDirection.DOWN.ordinal() || side == ForgeDirection.UP.ordinal()) {
			return new int[] { 1 };
		} else
			return new int[] { 0 };
	}

	@Override
	public boolean canExtractItem(int side, ItemStack it, int slot) {
		return true;
	}

	@Override
	public String getInventoryName() {
		return MineRad.MODID + ":waterDistiller";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack it) {
		return slot == 0 && it.getItem() instanceof ItemEmptyCore;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		progress = data.getInteger("progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		data.setInteger("progress", progress);
	}

}
