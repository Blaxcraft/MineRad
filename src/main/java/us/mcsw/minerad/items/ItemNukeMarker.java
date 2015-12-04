package us.mcsw.minerad.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.minerad.entity.EntityThrownMarker;

public class ItemNukeMarker extends ItemMR {

	public ItemNukeMarker() {
		super("nukeMarker");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack it, World w, EntityPlayer pl) {
		if (!w.isRemote) {
			w.spawnEntityInWorld(new EntityThrownMarker(w, pl));
			it.stackSize--;
		}
		return it;
	}

}
