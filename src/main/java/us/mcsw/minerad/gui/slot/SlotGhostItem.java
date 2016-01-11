package us.mcsw.minerad.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotGhostItem extends Slot {

	public SlotGhostItem(IInventory inv, int i, int x, int y) {
		super(inv, i, x, y);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer pl, ItemStack it) {
		putStack(null);
		super.onPickupFromSlot(pl, it);
	}

	@Override
	public void putStack(ItemStack it) {
		if (it != null) {
			it = it.copy();
			if (it.stackSize > 1) {
				it.stackSize = 1;
			}
		}
		super.putStack(it);
	}

}
