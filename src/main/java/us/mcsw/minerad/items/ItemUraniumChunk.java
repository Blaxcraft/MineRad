package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ItemMR;

public class ItemUraniumChunk extends ItemMR {

	public ItemUraniumChunk() {
		super("uraniumChunk");
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Slightly radioactive");
	}

}
