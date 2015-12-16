package us.mcsw.minerad.ref;

import org.apache.commons.lang3.text.WordUtils;

import cofh.api.energy.EnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemCapacitor;

public enum CapacitorTier {

	BASIC(0x883333, 10000, 100000, 150, 250), IRON(0xababab, 25000, 250000, 500, 1000), GOLD(0xbfbf60, 50000, 750000,
			1250,
			2500), DIAMOND(0x4dbfbf, 100000, 2500000, 2500, 5000), QUARTZ(0xdfdfdf, 250000, 10000000, 7500, 10000);

	final int colour, capMachineDefault, capStorageDefault, maxTransferMachineDefault, maxTransferPipeDefault;
	int capMachine, capStorage, maxTransferMachine, maxTransferPipe;

	private CapacitorTier(int colour, int capMachine, int capStorage, int maxTransferMachine, int maxTransferPipe) {
		this.colour = colour;
		capMachineDefault = this.capMachine = capMachine;
		capStorageDefault = this.capStorage = capStorage;
		maxTransferMachineDefault = this.maxTransferMachine = maxTransferMachine;
		maxTransferPipeDefault = this.maxTransferPipe = maxTransferPipe;
	}

	public void loadFromConfig(Configuration conf) {
		String cat = getCapitalizedName() + " Machine Tier";
		conf.setCategoryComment(cat, "Values for the " + getCapitalizedName() + " tier of machines");
		capMachine = conf.getInt(getLowercaseName() + "-machine-capacity", cat, capMachineDefault, 0, Integer.MAX_VALUE,
				"Energy Capacity for " + getCapitalizedName() + " machines");
		capStorage = conf.getInt(getLowercaseName() + "-storage-capacity", cat, capStorageDefault, 0, Integer.MAX_VALUE,
				"Energy Capacity for " + getCapitalizedName() + " storage blocks");
		maxTransferMachine = conf.getInt(getLowercaseName() + "-machine-transfer", cat, maxTransferMachineDefault, 0,
				Integer.MAX_VALUE, "Max transfer (RF/t) for " + getCapitalizedName() + " machines");
		maxTransferPipe = conf.getInt(getLowercaseName() + "-pipe-transfer", cat, maxTransferPipeDefault, 0,
				Integer.MAX_VALUE, "Max transfer (RF/t) for " + getCapitalizedName() + " pipes");
	}

	public String getCapitalizedName() {
		return WordUtils.capitalizeFully(name());
	}

	public String getLowercaseName() {
		return name().toLowerCase();
	}

	public int getColour() {
		return colour;
	}

	public int getMachineCapacity() {
		return capMachine;
	}

	public int getStorageCapacity() {
		return capStorage;
	}

	public int getMaxTransferMachine() {
		return maxTransferMachine;
	}

	public int getMaxTransferPipe() {
		return maxTransferPipe;
	}

	public EnergyStorage createStorage(boolean machine) {
		EnergyStorage ret = new EnergyStorage(machine ? capMachine : capStorage,
				machine ? maxTransferMachine : maxTransferPipe);
		return ret;
	}

	public static ItemCapacitor getCapacitor(CapacitorTier tier) {
		switch (tier) {
		case BASIC:
			return ModItems.capacitorBasic;
		case IRON:
			return ModItems.capacitorIron;
		case GOLD:
			return ModItems.capacitorGold;
		case DIAMOND:
			return ModItems.capacitorDiamond;
		case QUARTZ:
			return ModItems.capacitorQuartz;
		default:
			return null;
		}
	}

	public static final String NBT_KEY = "capacitorTier";

	public static CapacitorTier readFromNBT(NBTTagCompound data) {
		if (data == null || !data.hasKey(NBT_KEY)) {
			return BASIC;
		}
		return values()[data.getInteger(NBT_KEY)];
	}

	public static void writeToNBT(NBTTagCompound data, CapacitorTier tier) {
		if (data == null || tier == null) {
			return;
		}
		data.setInteger(NBT_KEY, tier.ordinal());
	}

	public static CapacitorTier getFromItemStack(ItemStack it) {
		if (!it.hasTagCompound()) {
			it.stackTagCompound = new NBTTagCompound();
		}
		return readFromNBT(it.stackTagCompound);
	}

	public static void setInItemStack(ItemStack it, CapacitorTier tier) {
		if (!it.hasTagCompound()) {
			it.stackTagCompound = new NBTTagCompound();
		}
		writeToNBT(it.stackTagCompound, tier);
	}

}
