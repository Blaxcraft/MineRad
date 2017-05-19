package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMRCapacity;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.entity.EntityFinalBoss;

public class ItemCoolantCore extends ItemMRCapacity {

	public ItemCoolantCore() {
		super("coolantCore", ConfigMR.COOLANT_CORE_DURABILITY);
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		list.add("Used to cool down reactors");
		list.add("[WARNING] Do not right click this item unless you want to die");
		list.add("It will spawn a very very very difficult boss (temp)");
		super.addInformation(it, p, list, n);
	}

	// TODO Temp, remove later
	@Override
	public ItemStack onItemRightClick(ItemStack it, World w, EntityPlayer pl) {
		if (!w.isRemote) {
			EntityFinalBoss n = new EntityFinalBoss(w);
			n.setPosition(pl.posX, pl.posY, pl.posZ);
			w.spawnEntityInWorld(n);
		}
		return it;
	}

}
