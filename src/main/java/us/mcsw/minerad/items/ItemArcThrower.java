package us.mcsw.minerad.items;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import us.mcsw.minerad.entity.EntityArcThrowerProjectile;
import us.mcsw.minerad.ref.CapacitorReference;
import us.mcsw.minerad.ref.TextureReference;

public class ItemArcThrower extends ItemMR implements IEnergyContainerItem {

	IIcon useable = null;

	public int maxTransfer = CapacitorReference.MAX_TRANSFER_GOLD;
	public int capacity = CapacitorReference.CAPACITY_GOLD;

	public ItemArcThrower() {
		super("arcThrower");

		setMaxStackSize(1);
		setNoRepair();
	}

	public static final int ENERGY_USAGE = CapacitorReference.CAPACITY_GOLD / 10, USE_TICKS = 40;

	public void use(ItemStack it, World w, EntityPlayer pl) {
		double vel = 1.35;

		EntityArcThrowerProjectile proj = new EntityArcThrowerProjectile(w, pl);
		proj.motionX *= vel;
		proj.motionY *= vel;
		proj.motionZ *= vel;
		w.spawnEntityInWorld(proj);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public int getDamage(ItemStack stack) {
		EnergyStorage storage = getFromItemstack(stack);
		return (storage.getMaxEnergyStored() - storage.getEnergyStored()) * getMaxDamage(stack)
				/ storage.getMaxEnergyStored();
	}

	@Override
	public int getMaxDamage() {
		return capacity / 500;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return getMaxDamage();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack it, World w, EntityPlayer pl) {
		pl.setItemInUse(it, getMaxItemUseDuration(it));
		return it;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack it, World w, EntityPlayer pl, int useLeft) {
		int ticks = getMaxItemUseDuration(it) - useLeft;
		if (ticks >= USE_TICKS) {
			if (getFromItemstack(it).getEnergyStored() >= ENERGY_USAGE) {
				setEnergyInItemStack(it, getFromItemstack(it).getEnergyStored() - ENERGY_USAGE);
				if (!w.isRemote) {
					use(it, w, pl);
				}
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack it) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack it) {
		return EnumAction.bow;
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		EnergyStorage storage = getFromItemstack(container);
		int ret = storage.receiveEnergy(maxReceive, simulate);
		if (!simulate) {
			setEnergyInItemStack(container, storage.getEnergyStored());
		}
		return ret;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		EnergyStorage storage = getFromItemstack(container);
		int ret = storage.extractEnergy(maxExtract, simulate);
		if (!simulate) {
			setEnergyInItemStack(container, storage.getEnergyStored());
		}
		return ret;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return getFromItemstack(container).getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return capacity;
	}

	public EnergyStorage getFromItemstack(ItemStack it) {
		if (!it.hasTagCompound()) {
			it.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound data = it.getTagCompound();
		if (!data.hasKey("energy")) {
			data.setInteger("energy", 0);
		}
		EnergyStorage ret = new EnergyStorage(capacity, maxTransfer);
		ret.setEnergyStored(data.getInteger("energy"));
		return ret;
	}

	public void setEnergyInItemStack(ItemStack it, int energy) {
		EnergyStorage current = getFromItemstack(it);
		current.setEnergyStored(energy);

		it.getTagCompound().setInteger("energy", current.getEnergyStored());
		it.setItemDamage(getDamage(it));
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		useable = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "arcThrowerUseable");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (usingItem != null) {
			if (getMaxItemUseDuration(usingItem) - useRemaining >= USE_TICKS) {
				return useable;
			}
		}
		return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
		list.add(getFromItemstack(it).getEnergyStored() + " / " + capacity + " RF");
		list.add("Zap Zap");
	}

	@Override
	public void getSubItems(Item i, CreativeTabs tab, List list) {
		ItemStack empty = new ItemStack(i);
		setEnergyInItemStack(empty, 0);
		ItemStack full = new ItemStack(i);
		setEnergyInItemStack(full, capacity);
		list.add(empty);
		list.add(full);
	}

}
