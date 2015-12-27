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
import us.mcsw.core.ItemMR;
import us.mcsw.core.ItemMREnergy;
import us.mcsw.minerad.entity.EntityArcThrowerProjectile;
import us.mcsw.minerad.ref.CapacitorTier;
import us.mcsw.minerad.ref.TextureReference;

public class ItemArcThrower extends ItemMREnergy {

	IIcon useable = null;

	public ItemArcThrower() {
		super("arcThrower", CapacitorTier.DIAMOND.getMaxTransferMachine(),
				CapacitorTier.DIAMOND.getMachineCapacity() * 2);

		setMaxStackSize(1);
		setNoRepair();
	}

	public static final int ENERGY_USAGE = CapacitorTier.DIAMOND.getMachineCapacity() / 5, USE_TICKS = 40;

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
		list.add("Zap Zap");
		super.addInformation(it, pl, list, n);
	}

}
