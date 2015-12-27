package us.mcsw.minerad.tiles;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.TileMRMachine;
import us.mcsw.core.util.EnergyUtil;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemCoolantCore;
import us.mcsw.minerad.items.ItemFissionCore;
import us.mcsw.minerad.items.ItemFusionCore;
import us.mcsw.minerad.recipes.GeneratorFuels;
import us.mcsw.minerad.recipes.GeneratorFuels.GeneratorFuel;
import us.mcsw.minerad.ref.CapacitorTier;

public class TileRadioactiveGenerator extends TileMRMachine implements IEnergyProvider {

	public TileRadioactiveGenerator() {
		super(CapacitorTier.GOLD.getMachineCapacity(), CapacitorTier.GOLD.getMaxTransferPipe(), 2);
	}

	int coreDamageCount = 0, coolantDamageCount = 0, passiveCoolCount = 0;

	int generationAmount = 0, generationTicks = 0;

	int heat = 0;

	@Override
	public void updateEntity() {
		if (canRun()) {
			if (hasFuel() || generationTicks > 0) {
				if (--generationTicks <= 0) {
					if (hasFuel()) {
						GeneratorFuel fuel = GeneratorFuels.getFuelInfoFor(getStackInSlot(0).getItem());
						generationTicks = fuel.getGenTicks();
						generationAmount = fuel.getGenAmount();
						decrStackSize(0, 1);
					} else
						generationTicks = 0;
				}
			} else if (hasCore()) {
				if (++coreDamageCount > 9) {
					damageCore(1);
					coreDamageCount = 0;
				}
			}
			generateEnergy();
		} else {
			if (++passiveCoolCount > 3) {
				if (heat > 0)
					heat--;
			}
		}
		if (hasCoolant()) {
			if (canRun() && ++coolantDamageCount > (isFusion() ? 4 : 7)) {
				damageCoolant(1);
				coolantDamageCount = 0;
			}
			if (heat > 0) {
				heat -= 10;
				damageCoolant(1);
			}
		} else if (canRun()) {
			heat++;
		}
		if (!worldObj.isRemote) {
			if (heat > DANGER_HEAT_LEVEL) {
				if (Math.random() > 0.995) {
					this.worldObj.createExplosion(null, xCoord, yCoord + 1, zCoord, isFusion() ? 12.0f : 8.0f, true);
				}
			}
		}
	}

	public static final int DANGER_HEAT_LEVEL = 1000;

	public String getHeatLevel() {
		if (heat <= 20) {
			return "cool";
		} else if (heat <= 200) {
			return "warm";
		} else if (heat <= 500) {
			return "hot";
		} else if (heat <= DANGER_HEAT_LEVEL) {
			return "extreme";
		} else {
			return "danger";
		}
	}

	public boolean canRun() {
		return (hasCore() || hasFuel() || generationTicks > 0)
				&& storage.getEnergyStored() < storage.getMaxEnergyStored();
	}

	public boolean isFusion() {
		return hasCore() && getStackInSlot(0).getItem() instanceof ItemFusionCore;
	}

	public void generateEnergy() {
		if (!worldObj.isRemote) {
			storage.receiveEnergy(getEnergyPerTick(), false);
			for (ForgeDirection dir : new ForgeDirection[] { ForgeDirection.UP, ForgeDirection.DOWN,
					ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.SOUTH, ForgeDirection.EAST })
				EnergyUtil.pushEnergy(this, worldObj, xCoord, yCoord, zCoord, storage, dir);
			markDirty();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public int getEnergyPerTick() {
		return generationTicks > 0 ? generationAmount : isFusion() ? 120 : hasCore() ? 80 : 0;
	}

	public boolean hasFuel() {
		return getStackInSlot(0) != null && getStackInSlot(0).stackSize > 0 && !hasCore();
	}

	public boolean hasCore() {
		return getStackInSlot(0) != null && getStackInSlot(0).stackSize > 0
				&& (getStackInSlot(0).getItem() instanceof ItemFusionCore
						|| getStackInSlot(0).getItem() instanceof ItemFissionCore);
	}

	public boolean hasCoolant() {
		return getStackInSlot(1) != null && getStackInSlot(1).stackSize > 0;
	}

	public boolean isCoreDepleted() {
		if (!hasCore()) {
			return true;
		}
		return getStackInSlot(0).getItemDamage() > getStackInSlot(0).getMaxDamage();
	}

	public boolean isCoolantDepleted() {
		if (!hasCoolant()) {
			return true;
		}
		return getStackInSlot(1).getItemDamage() > getStackInSlot(1).getMaxDamage();
	}

	public void damageCore(int amount) {
		if (!isCoreDepleted()) {
			getStackInSlot(0).setItemDamage(getStackInSlot(0).getItemDamage() + amount);
		}
		if (isCoreDepleted()) {
			setInventorySlotContents(0, new ItemStack(ModItems.emptyCore));
		}
	}

	public void damageCoolant(int amount) {
		if (!isCoolantDepleted()) {
			getStackInSlot(1).setItemDamage(getStackInSlot(1).getItemDamage() + amount);
		}
		if (isCoolantDepleted()) {
			setInventorySlotContents(1, new ItemStack(ModItems.emptyCore));
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1 };
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack it, int side) {
		return true;
	}

	@Override
	public String getInventoryName() {
		return MineRad.MODID + ":radioactiveGenerator";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack it) {
		if (slot == 0) {
			return it != null && (it.getItem() instanceof ItemFissionCore || it.getItem() instanceof ItemFusionCore
					|| GeneratorFuels.isFuel(it.getItem()));
		}
		if (slot == 1) {
			return it != null && (it.getItem() instanceof ItemCoolantCore);
		}
		return false;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		data.setInteger("heat", heat);
		data.setInteger("generationTicks", generationTicks);
		data.setInteger("generationAmount", generationAmount);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		heat = data.getInteger("heat");
		generationTicks = data.getInteger("generationTicks");
		generationAmount = data.getInteger("generationAmount");
	}

}
