package us.mcsw.minerad.items;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.core.util.ChatUtil;
import us.mcsw.minerad.ref.RadProperties;
import us.mcsw.minerad.util.RadUtil;

public class ItemGeigerCounter extends ItemMR {

	public ItemGeigerCounter() {
		super("geigerCounter");

		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack it, World w, EntityPlayer p) {
		if (!w.isRemote) {
			int x = (int) p.posX, y = (int) p.posY, z = (int) p.posZ;
			int rads = 0;
			double radResist = 1;
			if (RadProperties.get(p) != null) {
				rads = (int) RadProperties.get(p).getRadiation();
				radResist = RadProperties.get(p).getRadResistance();
			}
			int rps = RadUtil.getRadsAtLocation(w, x, y, z);
			ChatUtil.sendTranslatedTo(p, "message.geigerCounter.info", rps, (int) (rps / radResist), rads);
		}
		return it;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add(LanguageRegistry.instance().getStringLocalization("info.geigerCounter.desc"));
	}

}
