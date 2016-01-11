package us.mcsw.minerad.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.minerad.MineRad;

public class ItemPlutonium extends ItemMR {

	public ItemPlutonium() {
		super("plutonium");
	}

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.epic;
	}

}
