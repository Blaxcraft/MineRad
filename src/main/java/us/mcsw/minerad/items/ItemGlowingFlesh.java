package us.mcsw.minerad.items;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMRFood;
import us.mcsw.core.util.ChatUtil;
import us.mcsw.core.util.LangUtil;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.ref.PlayerProperties;
import us.mcsw.minerad.ref.RadProperties;

public class ItemGlowingFlesh extends ItemMRFood {

	public ItemGlowingFlesh() {
		super("glowingFlesh", 6, 0.7f);
		setAlwaysEdible();
	}

	static final int RADS_GIVEN = 75;

	@Override
	public void onFoodEaten(ItemStack it, World w, EntityPlayer pl) {
		RadProperties.get(pl).addRadiation(RADS_GIVEN, true);
		PlayerProperties props = PlayerProperties.get(pl);
		if (props.isGhoul() && !props.isGlowingGhoul()) {
			if (!w.isRemote) {
				if (++props.ghoulTicks >= ConfigMR.GLOWING_FLESH_NEEDED) {
					props.onBecomeGlowing();
				} else {
					ChatUtil.sendChatTo(pl, ChatFormatting.DARK_GREEN + LangUtil
							.translate("info.ghoul.glowing.progress", props.ghoulTicks, ConfigMR.GLOWING_FLESH_NEEDED));
				}
			}
		}
	}

}
