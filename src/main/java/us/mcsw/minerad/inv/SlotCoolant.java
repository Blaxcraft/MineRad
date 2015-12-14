package us.mcsw.minerad.inv;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemCoolantCore;
import us.mcsw.minerad.items.ItemFissionCore;
import us.mcsw.minerad.ref.MachineReference;

public class SlotCoolant extends Slot {

	public SlotCoolant(IInventory inv, int i, int x, int y) {
		super(inv, i, x, y);
		setBackgroundIcon(MachineReference.CORE_BACKGROUND);
	}

	@Override
	public boolean isItemValid(ItemStack it) {
		return it.getItem() instanceof ItemCoolantCore;
	}

}
