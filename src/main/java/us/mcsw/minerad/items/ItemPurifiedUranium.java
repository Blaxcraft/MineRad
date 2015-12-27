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
	}

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.uncommon;
	}

}
