package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ItemMR;
import us.mcsw.core.util.NumbersUtil;

public class ItemPurifiedNeptunium extends ItemMR {

	public ItemPurifiedNeptunium() {
		super("purifiedNeptunium");
	}

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.rare;
	}

}
