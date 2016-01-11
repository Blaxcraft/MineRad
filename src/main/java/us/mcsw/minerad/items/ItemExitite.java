package us.mcsw.minerad.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.EnumHelper;
import us.mcsw.core.ItemMR;

public class ItemExitite extends ItemMR {

	public ItemExitite() {
		super("exitite");
	}

	@Override
	public boolean hasEffect(ItemStack it, int pass) {
		return true;
	}

	static final EnumRarity specialRarity = EnumHelper.addRarity("exitite", EnumChatFormatting.DARK_AQUA, "Exitite");

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return specialRarity;
	}

}
