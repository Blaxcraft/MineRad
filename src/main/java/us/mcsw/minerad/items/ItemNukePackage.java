package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.core.util.ChatUtil;
import us.mcsw.minerad.ref.PlayerProperties;

public class ItemNukePackage extends ItemMR {

	public ItemNukePackage() {
		super("nukePackage");
	}
	
	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.uncommon;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		PlayerProperties props = PlayerProperties.get(p);
		list.add("Right-click arsenal to add nukes");
	}

}
