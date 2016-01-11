package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ItemMR;

public class ItemShieldUpgrade extends ItemMR {

	public ItemShieldUpgrade() {
		super("shieldUpgrade");
	}
	
	@Override
	public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
		super.addInformation(it, pl, list, n);
		list.add("Increases Shield Generator range by 1 block");
	}

}
