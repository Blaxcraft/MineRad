package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.util.NumbersUtil;

public class ItemPurifiedNeptunium extends ItemMR {

	public ItemPurifiedNeptunium() {
		super("purifiedNeptunium");

		setMaxDamage(750);
		setNoRepair();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.rare;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		if (it.getItemDamage() > 0) {
			list.add("Progress: " + NumbersUtil.roundDouble(it.getItemDamage() * 100 / getMaxDamage(), 1) + "%");
		}
	}

}
