package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.core.util.NumbersUtil;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.init.ModItems;

public class ItemCoolantCore extends ItemMR {

	public ItemCoolantCore() {
		super("coolantCore");

		setMaxDamage(ConfigMR.COOLANT_CORE_DURABILITY);
		setMaxStackSize(1);
		setNoRepair();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Used to cool down reactors");
		list.add(
				"Capacity: " + NumbersUtil.roundDouble((((double) ConfigMR.COOLANT_CORE_DURABILITY - it.getItemDamage())
						/ (ConfigMR.COOLANT_CORE_DURABILITY / 100)), 1) + "%");
	}

}
