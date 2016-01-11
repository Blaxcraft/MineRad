package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.core.ItemMRFood;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.ref.RadProperties;

public class ItemRadAway extends ItemMRFood {

	public ItemRadAway() {
		super("radAway", 0, 0);
		setAlwaysEdible();
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 60;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack it) {
		return EnumAction.drink;
	}

	@Override
	public void onFoodEaten(ItemStack it, World w, EntityPlayer pl) {
		RadProperties props = RadProperties.get(pl);
		props.addRadiation(-ConfigMR.RAD_AWAY_AMOUNT, true);
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Reduces rad dose by " + ConfigMR.RAD_AWAY_AMOUNT + " RADS");
	}

}
