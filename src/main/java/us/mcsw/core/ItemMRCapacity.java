package us.mcsw.core;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import us.mcsw.core.util.NumbersUtil;
import us.mcsw.minerad.ConfigMR;

public class ItemMRCapacity extends ItemMR {

	public ItemMRCapacity(String unloc, int capacity) {
		super(unloc);

		setMaxDamage(capacity);
		setNoRepair();
		setMaxStackSize(1);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Capacity: " + NumbersUtil.roundDouble(getCapacity(it), 1) + "%");
	}

	public double getCapacity(ItemStack stack) {
		double dam = getDamage(stack), maxDam = getMaxDamage(stack);
		double percentDecimal = (maxDam - dam) / maxDam;
		return percentDecimal * 100;
	}

}
