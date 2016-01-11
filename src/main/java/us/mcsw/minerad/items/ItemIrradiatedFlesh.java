package us.mcsw.minerad.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMRFood;
import us.mcsw.minerad.ref.RadProperties;

public class ItemIrradiatedFlesh extends ItemMRFood {

	public ItemIrradiatedFlesh() {
		super("irradiatedFlesh", 4, 0.2f);
		setAlwaysEdible();
	}

	static final int RADS_GIVEN = 20;

	@Override
	public void onFoodEaten(ItemStack it, World w, EntityPlayer pl) {
		RadProperties.get(pl).addRadiation(RADS_GIVEN, true);
	}
}
