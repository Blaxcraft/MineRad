package us.mcsw.minerad.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ItemMR;

public class ItemPlutoniumNugget extends ItemMR {

	public ItemPlutoniumNugget() {
		super("nuggetPlutonium");
	}
	
	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.epic;
	}
	
}
