package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.core.util.NumbersUtil;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.init.ModItems;

public class ItemFusionCore extends ItemMR {

	public ItemFusionCore() {
		super("fusionCore");

		setMaxDamage(ConfigMR.FUSION_CORE_DURABILITY);
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
		list.add("Basic power source for fusion reactors");
		list.add("Capacity: " + NumbersUtil.roundDouble((((double) ConfigMR.FUSION_CORE_DURABILITY - it.getItemDamage())
				/ (ConfigMR.FUSION_CORE_DURABILITY / 100)), 1) + "%");
	}

}
