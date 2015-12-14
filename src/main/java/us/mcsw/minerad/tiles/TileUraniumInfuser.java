package us.mcsw.minerad.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.TileMRMachine;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.init.UraniumInfuserRecipes;
import us.mcsw.minerad.init.UraniumInfuserRecipes.InfuserRecipe;
import us.mcsw.minerad.ref.CapacitorTier;

public class TileUraniumInfuser extends TileMRMachine {

	public int progress = 0;

	public TileUraniumInfuser() {
		super(CapacitorTier.IRON.getMachineCapacity(), CapacitorTier.IRON.getMaxTransferMachine(), 3);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		boolean changed = false;
		if (!worldObj.isRemote) {
			if (canRun()) {
				progress += storage.extractEnergy(50, false);
				if (progress >= getMaxProgress()) {
					if (getStackInSlot(2) != null) {
						ItemStack p = getStackInSlot(2);
						if (ItemUtil.areItemStacksEqual(getCurrentRecipe().getProduct(), p)) {
							p.stackSize += getCurrentRecipe().getProduct().stackSize;
						}
						setInventorySlotContents(2, p);
					} else {
						setInventorySlotContents(2, getCurrentRecipe().getProduct());
					}

					progress = 0;
					decrStackSize(0, getCurrentRecipe().getRequiredCores());
					decrStackSize(1, getCurrentRecipe().getSource().stackSize);
				}
				changed = true;

				// worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			} else {
				progress = 0;
			}
		}
		if (changed) {
			markDirty();
		}
	}

	public boolean canRun() {
		if (getCurrentRecipe() != null) {
			ItemStack it = getStackInSlot(1);
			InfuserRecipe r = getCurrentRecipe();
			if (r != null) {
				if (getCores() >= r.getRequiredCores() && storage.getEnergyStored() > 0 && getMaxProgress() > 0
						&& isSpaceForProduct()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isSpaceForProduct() {
		ItemStack it = getStackInSlot(2);
		if (it != null) {
			ItemStack p = getCurrentRecipe().getProduct();
			if (ItemUtil.areItemStacksEqual(it, p)) {
				return it.stackSize + p.stackSize <= it.getMaxStackSize();
			}
		}
		return true;
	}

	public InfuserRecipe getCurrentRecipe() {
		ItemStack it = getStackInSlot(1);
		if (it != null) {
			return UraniumInfuserRecipes.getRecipeFor(it, true);
		}
		return null;
	}

	public int getCores() {
		ItemStack it = getStackInSlot(0);
		if (it != null) {
			return it.stackSize;
		}
		return 0;
	}

	@Override
	public String getInventoryName() {
		return MineRad.MODID + ":uraniumInfuserTile";
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		if (dir == ForgeDirection.UP) {
			return new int[] { 0 };
		} else if (dir == ForgeDirection.DOWN) {
			return new int[] { 2 };
		} else if (side != getBlockMetadata()) {
			return new int[] { 1 };
		}
		return new int[0];
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack it) {
		if (i == 0) {
			return it.getItem().equals(ModItems.emptyCore);
		}
		if (i == 1) {
			return UraniumInfuserRecipes.getRecipeFor(it, false) != null;
		}
		if (i == 2) {
			return false;
		}
		return true;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack it, int side) {
		return super.canInsertItem(i, it, side) && i != 3;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack it, int side) {
		return true;
	}

	public int getMaxProgress() {
		if (getCurrentRecipe() != null) {
			return getCurrentRecipe().getPower();
		}
		return -1;
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
