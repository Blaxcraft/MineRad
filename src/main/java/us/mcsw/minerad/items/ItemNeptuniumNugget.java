package us.mcsw.minerad.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemNeptuniumNugget extends ItemMR {

	public ItemNeptuniumNugget() {
		super("nuggetNeptunium");
	}
	
	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.rare;
	}
	
}
