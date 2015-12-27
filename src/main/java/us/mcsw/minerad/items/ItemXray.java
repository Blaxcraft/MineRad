package us.mcsw.minerad.items;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.core.ItemMREnergy;
import us.mcsw.minerad.ref.CapacitorTier;

public class ItemXray extends ItemMREnergy {

	public ItemXray() {
		super("xray", CapacitorTier.QUARTZ.getMaxTransferMachine(), CapacitorTier.QUARTZ.getMachineCapacity() * 2);

		setMaxStackSize(1);
		setNoRepair();
	}

	public static final int USAGE_PER_TICK = 50;

	@Override
	public ItemStack onItemRightClick(ItemStack it, World world, EntityPlayer pl) {
		if (pl.isSneaking()) {
			setEnabled(it, !isEnabled(it));
		}
		return it;
	}

	@Override
	public void onUpdate(ItemStack it, World w, Entity e, int m, boolean n) {
		if (isEnabled(it)) {
			setEnergyInItemStack(it, getFromItemstack(it).getEnergyStored() - USAGE_PER_TICK);
			if (getFromItemstack(it).getEnergyStored() <= 0) {
				setEnabled(it, false);
			}
		}
	}

	@Override
	public boolean hasEffect(ItemStack it, int pass) {
		return isEnabled(it);
	};

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public int getDamage(ItemStack stack) {
		return super.getDamageFromEnergy(stack);
	}

	@Override
	public int getMaxDamage() {
		return 1000;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return getMaxDamage();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer pl, List list, boolean n) {
		if (isEnabled(stack)) {
			list.add(EnumChatFormatting.GREEN + "On");
		} else {
			list.add(EnumChatFormatting.RED + "Off");
		}
		list.add("Sneak + right-click to toggle");
		super.addInformation(stack, pl, list, n);
	}

	public boolean isEnabled(ItemStack it) {
		NBTTagCompound data = it.getTagCompound();
		if (data != null && data.hasKey("enabled")) {
			return it.stackTagCompound.getBoolean("enabled");
		} else {
			setEnabled(it, false);
			return false;
		}
	}

	public void setEnabled(ItemStack it, boolean set) {
		NBTTagCompound data = it.getTagCompound();
		if (data == null) {
			data = new NBTTagCompound();
			it.setTagCompound(data);
		}
		data.setBoolean("enabled", set);
	}
	
}
