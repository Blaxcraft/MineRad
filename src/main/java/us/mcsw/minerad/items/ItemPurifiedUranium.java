package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ItemMR;
import us.mcsw.core.util.NumbersUtil;

public class ItemPurifiedUranium extends ItemMR {

	public ItemPurifiedUranium() {
		super("purifiedUranium");

		setMaxDamage(100);
		setNoRepair();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Even more dangerous!");
		if (it.getItemDamage() > 0) {
			list.add("Progress: " + NumbersUtil.roundDouble((((double) it.getItemDamage()) / (getMaxDamage() / 100.0)), 1)
					+ "%");
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.uncommon;
	}

}
