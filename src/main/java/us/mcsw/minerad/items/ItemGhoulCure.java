package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMRFood;
import us.mcsw.minerad.ref.PlayerProperties;

public class ItemGhoulCure extends ItemMRFood {

	public ItemGhoulCure() {
		super("ghoulCure", 0, 0);
		setAlwaysEdible();
	}

	@Override
	public void onFoodEaten(ItemStack it, World w, EntityPlayer pl) {
		PlayerProperties props;
		if ((props = PlayerProperties.get(pl)) != null) {
			if (props.isGhoul() && !w.isRemote) {
				props.onBecomeHuman();
			}
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack it) {
		return EnumAction.drink;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
		super.addInformation(it, pl, list, n);
		list.add("Turns a ghoul into a human");
	}

}
