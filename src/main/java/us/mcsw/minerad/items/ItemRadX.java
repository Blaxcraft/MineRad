package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.core.ItemMRFood;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.potion.PotionRadX;
import us.mcsw.minerad.ref.RadProperties;

public class ItemRadX extends ItemMRFood {

	public ItemRadX() {
		super("radX", 0, 0);
		setAlwaysEdible();
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 20;
	}

	static final int SECONDS_DURATION = 120;

	@Override
	public void onFoodEaten(ItemStack it, World w, EntityPlayer pl) {
		pl.addPotionEffect(new PotionEffect(MineRad.potionRadX.id, SECONDS_DURATION * 20, 0));
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Reduces rad gain for " + SECONDS_DURATION + " seconds");
	}

}
