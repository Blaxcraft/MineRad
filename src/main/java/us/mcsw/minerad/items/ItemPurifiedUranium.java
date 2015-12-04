package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

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
			list.add("Progress: " + it.getItemDamage() * 100 / getMaxDamage() + "%");
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.uncommon;
	}

}
