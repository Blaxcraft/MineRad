package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemNuclearArsenalUpgrade extends ItemMR {

	public ItemNuclearArsenalUpgrade() {
		super("nuclearArsenalUpgrade");
	}
	
	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Click arsenal to upgrade nuke storage");
	}

}
