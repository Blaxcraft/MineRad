package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.minerad.ref.RadProperties;

public class ItemRadAway extends ItemMR {

	public ItemRadAway() {
		super("radAway");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack it, World w, EntityPlayer pl) {
		if (pl.canEat(true)) {
			pl.setItemInUse(it, getMaxItemUseDuration(it));
		}
		return it;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 60;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack it) {
		return EnumAction.drink;
	}
	
	static final int RAD_LOSS = 200;
	
	@Override
	public ItemStack onEaten(ItemStack it, World w, EntityPlayer pl) {
		if (!w.isRemote) {
			it.stackSize--;
			RadProperties props = RadProperties.get(pl);
			props.addRadiation(-RAD_LOSS);
		}
		return it;
	}
	
	
	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Reduces rad dose by " + RAD_LOSS + " RADS");
	}
	
}
