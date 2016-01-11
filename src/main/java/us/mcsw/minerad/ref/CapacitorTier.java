package us.mcsw.minerad.ref;

import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;

import cofh.api.energy.EnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import us.mcsw.minerad.items.ItemCapacitor;

public class CapacitorTier {

	public static ArrayList<CapacitorTier> tiers = new ArrayList<CapacitorTier>();

	public static CapacitorTier BASIC = registerTier("basic", 0x883333, 10000, 125000, 150, 250),
			IRON = registerTier("iron", 0x909090, 25000, 300000, 500, 1000),
			GOLD = registerTier("gold", 0xbfbf60, 60000, 750000, 1250, 2500),
			DIAMOND = registerTier("diamond", 0x4dbfbf, 250000, 2000000, 2500, 5000),
			QUARTZ = registerTier("quartz", 0xffffff, 500000, 10000000, 7500, 10000);

	public static CapacitorTier registerTier(String name, int colour, int capMachine, int capStorage,
			int maxTransferMachine, int maxTransferPipe) {
		CapacitorTier ret = new CapacitorTier(name, colour, capMachine, capStorage, maxTransferMachine,
				maxTransferPipe);
		tiers.add(ret);
		ret.id = tiers.indexOf(ret);
		return ret;
	}

	String name;
	ItemCapacitor capacitor = null;
	final int colour, capMachineDefault, capStorageDefault, maxTransferMachineDefault, maxTransferPipeDefault;
	int capMachine, capStorage, maxTransferMachine, maxTransferPipe;

	int id;

	private CapacitorTier(String name, int colour, int capMachine, int capStorage, int maxTransferMachine,
			int maxTransferPipe) {
		this.name = name;
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

	public int getId() {
		return id;
	}

	public String getCapitalizedName() {
		return WordUtils.capitalizeFully(name);
	}

	public String getLowercaseName() {
		return name.toLowerCase();
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
	
	@Override
	public String toString() {
		return getLowercaseName();
	}

	public EnergyStorage createStorage(boolean machine) {
		EnergyStorage ret = new EnergyStorage(machine ? capMachine : capStorage,
				machine ? maxTransferMachine : maxTransferPipe);
		return ret;
	}

	public ItemCapacitor getCapacitor() {
		if (capacitor == null) {
			capacitor = new ItemCapacitor(this);
		}
		return capacitor;
	}

	public static CapacitorTier getFromId(int id) {
		return tiers.get(id);
	}

	public static final String NBT_KEY = "capacitorTier";

	public static CapacitorTier readFromNBT(NBTTagCompound data) {
		if (data == null || !data.hasKey(NBT_KEY)) {
			return BASIC;
		}
		return getFromId(data.getInteger(NBT_KEY));
	}

	public static void writeToNBT(NBTTagCompound data, CapacitorTier tier) {
		if (data == null || tier == null) {
			return;
		}
		data.setInteger(NBT_KEY, tier.getId());
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
