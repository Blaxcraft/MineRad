package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.core.ItemMRCapacity;
import us.mcsw.core.util.NumbersUtil;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.init.ModItems;

public class ItemFissionCore extends ItemMRCapacity {

	public ItemFissionCore() {
		super("fissionCore", ConfigMR.FISSION_CORE_DURABILITY);
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		list.add("Basic power source for fission reactors");
		super.addInformation(it, p, list, n);
	}

}
