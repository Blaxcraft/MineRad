package us.mcsw.minerad.tiles;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.TileMRInventory;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.minerad.MineRad;

public class TileFilter extends TileMRInventory implements ISidedInventory {

	static int FILTER_SLOTS = 9;

	public TileFilter() {
		super(12 + 9);
	}

	@Override
	public void updateEntity() {
		for (int i = 0; i < 6; i++) {
			ForgeDirection pushTo = ForgeDirection.getOrientation(i);
			int slotExport = i + 6, slotImport = pushTo.getOpposite().ordinal();
			if (getStackInSlot(slotImport) != null) {
				setInventorySlotContents(slotExport, getStackInSlot(slotImport).copy());
				setInventorySlotContents(slotImport, null);
			}
			if (getStackInSlot(slotExport) != null)
				setInventorySlotContents(slotExport,
						ItemUtil.addItemToNearbyInventories(this, getStackInSlot(slotExport), false, false, pushTo));
		}
	}

	public boolean isAllowed(ItemStack check) {
		if (check == null)
			return false;
		boolean hasFilter = false;
		for (int i = 0; i < FILTER_SLOTS; i++) {
			ItemStack f = getStackInSlot(getSizeInventory() - FILTER_SLOTS + i);
			if (f != null) {
				if (f.getItem().equals(check.getItem())) {
					return true;
				}
				hasFilter = true;
			}
		}
		return !hasFilter;
	}

	@Override
	public String getInventoryName() {
		return MineRad.MODID + ":filter";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack it) {
		return slot < 6;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return new int[] { dir.ordinal(), dir.ordinal() + 6 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack it, int side) {
		return slot == side && ItemUtil.addItemToNearbyInventories(this, it, false, true,
				ForgeDirection.getOrientation(side).getOpposite()) == null && isAllowed(it);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack it, int side) {
		return slot == side + 6;
	}

}
