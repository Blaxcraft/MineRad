package us.mcsw.core.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemUtil {

	public static ItemStack addItemToNearbyInventories(TileEntity source, ItemStack add, boolean excludePairs,
			boolean sim, ForgeDirection... sides) {
		add = add.copy();
		for (ForgeDirection dir : sides) {
			if (add == null)
				break;
			TileEntity te = source.getWorldObj().getTileEntity(source.xCoord + dir.offsetX, source.yCoord + dir.offsetY,
					source.zCoord + dir.offsetZ);
			if (te != null && te instanceof IInventory
					&& (excludePairs ? !te.getClass().equals(source.getClass()) : true)) {
				IInventory inv = (IInventory) te;
				add = addItemToInventory(inv, dir.getOpposite(), add, sim);
				if (!sim) {
					te.markDirty();
					te.getWorldObj().markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
				}
			}
		}
		return add;
	}

	public static ItemStack addItemToInventory(IInventory inv, ForgeDirection side, ItemStack add, boolean sim) {
		add = add.copy();
		int[] slots = {};
		if (inv instanceof ISidedInventory) {
			ISidedInventory si = (ISidedInventory) inv;
			slots = si.getAccessibleSlotsFromSide(side.ordinal());
		} else {
			slots = new int[inv.getSizeInventory()];
			for (int i = 0; i < slots.length; i++) {
				slots[i] = i;
			}
		}
		for (int slot : slots) {
			if (add == null || add.stackSize <= 0)
				break;
			if (!inv.isItemValidForSlot(slot, add))
				continue;
			if (inv instanceof ISidedInventory && !((ISidedInventory) inv).canInsertItem(slot, add, side.ordinal()))
				continue;
			ItemStack in = inv.getStackInSlot(slot);
			if (sim && in != null)
				in = in.copy();
			if (in != null) {
				if (ItemUtil.areItemStacksEqual(in, add)) {
					int maxStack = Math.min(in.getMaxStackSize(), inv.getInventoryStackLimit());
					if (in.stackSize >= maxStack)
						continue;

					in.stackSize += add.stackSize;
					if (in.stackSize > maxStack) {
						add.stackSize = in.stackSize - maxStack;
						in.stackSize = maxStack;
					} else
						add = null;
				}
			} else {
				if (!sim)
					inv.setInventorySlotContents(slot, add.copy());
				add = null;
			}
		}
		if (add != null && add.stackSize <= 0) {
			add = null;
		}
		return add;
	}

	// because the one in ItemStack checks stack size -.-
	public static boolean areItemStacksEqual(ItemStack s1, ItemStack s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 == null || s2 == null) {
			return false;
		}

		return s1.getItem().equals(s2.getItem()) && s1.getItemDamage() == s2.getItemDamage()
				&& (s1.stackTagCompound == null && s2.stackTagCompound == null
 || s1.stackTagCompound != null
						&& s2.stackTagCompound != null && s1.stackTagCompound.equals(s2.stackTagCompound));
	}

	public static void setInteger(String key, ItemStack it, int val) {
		if (!it.hasTagCompound()) {
			it.stackTagCompound = new NBTTagCompound();
		}
		it.stackTagCompound.setInteger(key, val);
	}

	public static int getInteger(String key, ItemStack it) {
		if (!it.hasTagCompound()) {
			setInteger(key, it, 0);
			return getInteger(key, it);
		}
		return it.stackTagCompound.getInteger(key);
	}

	public static void dropItemsFromInventory(TileEntity te) {
		if (!(te instanceof IInventory)) {
			return;
		}
		IInventory inv = (IInventory) te;

		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack item = inv.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				float rx = te.getWorldObj().rand.nextFloat() * 0.8F + 0.1F;
				float ry = te.getWorldObj().rand.nextFloat() * 0.8F + 0.1F;
				float rz = te.getWorldObj().rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(te.getWorldObj(), te.xCoord + rx, te.yCoord + ry, te.zCoord + rz,
						item.copy());

				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = te.getWorldObj().rand.nextGaussian() * factor;
				entityItem.motionY = te.getWorldObj().rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = te.getWorldObj().rand.nextGaussian() * factor;
				te.getWorldObj().spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}

}
