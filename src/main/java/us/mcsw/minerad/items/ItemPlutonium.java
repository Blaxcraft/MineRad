package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ItemMR;
import us.mcsw.core.util.NumbersUtil;

public class ItemPlutonium extends ItemMR {

	public ItemPlutonium() {
		super("plutonium");

		setMaxDamage(1000);
		setNoRepair();
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("The most deadly!");
		if (it.getItemDamage() > 0) {
			list.add("Progress: " + NumbersUtil.roundDouble((((double) it.getItemDamage()) / (getMaxDamage() / 100.0)), 1)
					+ "%");
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.epic;
	}

	@Override
	public boolean showDurabilityBar(ItemStack it) {
		return false;
	}

}
