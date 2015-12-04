package us.mcsw.minerad.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.util.LogUtil;
import us.mcsw.minerad.util.RadUtil;

public class PotionRadiationSickness extends Potion {

	public PotionRadiationSickness() {
		super(79, true, 10044730);
		setPotionName("potion." + MineRad.MODID + ":radiationSickness");
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public int getStatusIconIndex() {
		return 0;
	}

	Potion[][] badEffects = { { Potion.confusion, Potion.digSlowdown, Potion.hunger, Potion.weakness },
			{ Potion.blindness, Potion.moveSlowdown } };

	@Override
	public void performEffect(EntityLivingBase elb, int amp) {
		if (!elb.worldObj.isRemote) {
			if (elb instanceof EntityPlayer) {
				if (elb.getRNG().nextInt(2000 / (amp * 2 + 1)) == 0) {
					EntityPlayer pl = (EntityPlayer) elb;
					pl.addChatMessage(
							new ChatComponentTranslation("message.radSickness." + amp + "." + pl.getRNG().nextInt(3))
									.setChatStyle(
											new ChatStyle().setItalic(true).setColor(EnumChatFormatting.DARK_GREEN)));
					pl.attackEntityFrom(RadUtil.radiationDamage, pl.getRNG().nextInt(3 + amp * 3) + 2 + amp * 2);
				}
			}
		}
	}

	@Override
	public boolean isReady(int par1, int par2) {
		return true;
	}

}
