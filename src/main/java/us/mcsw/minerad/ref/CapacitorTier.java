package us.mcsw.minerad.ref;

import org.apache.commons.lang3.text.WordUtils;

import cofh.api.energy.EnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemCapacitor;

public enum CapacitorTier {

	BASIC(0x883333, 10000, 25000, 100), IRON(0xababab, 25000, 100000, 350), GOLD(0xbfbf60, 50000, 250000,
			650), DIAMOND(0x4dbfbf, 100000, 1000000, 1250), QUARTZ(0xdfdfdf, 250000, 2500000, 2500);

	int colour, capMachine, capStorage, maxTransfer;

	private CapacitorTier(int colour, int capMachine, int capStorage, int maxTransfer) {
		this.colour = colour;
		this.capMachine = capMachine;
		this.capStorage = capStorage;
		this.maxTransfer = maxTransfer;
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

	public int getMaxTransfer() {
		return maxTransfer;
	}

	public EnergyStorage createStorage(boolean machine) {
		EnergyStorage ret = new EnergyStorage(machine ? capMachine : capStorage, maxTransfer);
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
