package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemUraniumOre extends ItemMR {

	public ItemUraniumOre() {
		super("uraniumOreItem");
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.uncommon;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Very radioactive!");
	}

}
