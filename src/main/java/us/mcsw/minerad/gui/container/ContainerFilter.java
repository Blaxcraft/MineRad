package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ContainerMRTile;
import us.mcsw.core.TileMRInventory;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.gui.slot.SlotGhostItem;
import us.mcsw.minerad.tiles.TileFilter;

public class ContainerFilter extends ContainerMRTile {

	TileFilter tile;

	public ContainerFilter(InventoryPlayer ip, TileFilter tile) {
		super(ip, tile);
		this.tile = tile;

		addPlayerInventory(ip);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer pl, int s) {
		Slot slot = (Slot) inventorySlots.get(s);
		if (slot != null && slot.getHasStack()) {
			ItemStack cur = slot.getStack();

			if (s < 9) {
				slot.putStack(null);
			} else {
				for (int i = 0; i < 9; i++) {
					Slot fil = (Slot) inventorySlots.get(i);
					if (fil != null && !fil.getHasStack()) {
						fil.putStack(cur.copy());
						break;
					}
				}
			}
		}
		return null;
	}

	@Override
	public ItemStack slotClick(int slot, int par1, int par2, EntityPlayer pl) {
		if (slot < 9 && slot >= 0) {
			InventoryPlayer inv = pl.inventory;
			Slot s = (Slot) inventorySlots.get(slot);
			if (s != null) {
				s.putStack(inv.getItemStack());
			}
			return null;
		} else {
			return super.slotClick(slot, par1, par2, pl);
		}
	}

	@Override
	public void addSlotsToContainer(IInventory inv) {
		addSlotToContainer(new SlotGhostItem(inv, 12, 60, 16));
		addSlotToContainer(new SlotGhostItem(inv, 13, 80, 16));
		addSlotToContainer(new SlotGhostItem(inv, 14, 100, 16));
		addSlotToContainer(new SlotGhostItem(inv, 15, 60, 36));
		addSlotToContainer(new SlotGhostItem(inv, 16, 80, 36));
		addSlotToContainer(new SlotGhostItem(inv, 17, 100, 36));
		addSlotToContainer(new SlotGhostItem(inv, 18, 60, 56));
		addSlotToContainer(new SlotGhostItem(inv, 19, 80, 56));
		addSlotToContainer(new SlotGhostItem(inv, 20, 100, 56));
	}

}
