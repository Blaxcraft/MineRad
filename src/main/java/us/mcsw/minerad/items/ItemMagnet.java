package us.mcsw.minerad.items;

import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath.Axis;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemMagnet extends ItemMR {

	public ItemMagnet() {
		super("magnetItem");

		setMaxStackSize(1);
	}

	@Override
	public boolean hasEffect(ItemStack it, int pass) {
		return isEnabled(it);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack it, World w, EntityPlayer pl) {
		if (pl.isSneaking()) {
			setEnabled(it, !isEnabled(it));
		} else {
			pl.setItemInUse(it, getMaxItemUseDuration(it));
		}
		return it;
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
	public void onUsingTick(ItemStack it, EntityPlayer pl, int c) {
		pullItems(pl, 15);
	}

	@Override
	public void onUpdate(ItemStack it, World w, Entity e, int m, boolean n) {
		if (isEnabled(it) && e instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer) e;
			if (!pl.isSneaking()) {
				pullItems(pl, 10);
			}
		}
	}

	private void pullItems(EntityPlayer pl, int range) {
		double x = pl.posX, y = pl.posY + 1, z = pl.posZ;

		AxisAlignedBB pull = AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range,
				z + range);
		for (Object o : pl.worldObj.getEntitiesWithinAABB(EntityItem.class, pull)) {
			EntityItem i = (EntityItem) o;
			if (i.delayBeforeCanPickup < 1 && checkSpace(pl.inventory, i.getEntityItem())) {
				Vec3 mot = Vec3.createVectorHelper(x - i.posX, y - i.posY, z - i.posZ);
				mot = mot.normalize();
				i.motionX += mot.xCoord * 0.1;
				i.motionY += mot.yCoord * 0.3;
				i.motionZ += mot.zCoord * 0.1;
			}
		}
	}

	private boolean checkSpace(InventoryPlayer inv, ItemStack add) {
		for (ItemStack it : inv.mainInventory) {
			if (it != null) {
				if (it.isItemEqual(add) && it.stackSize < it.getMaxStackSize()) {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
		if (isEnabled(it)) {
			list.add(EnumChatFormatting.GREEN + "On");
		} else {
			list.add(EnumChatFormatting.RED + "Off");
		}
		list.add("Hold right click to use");
		list.add("Shift-right click to toggle");
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
