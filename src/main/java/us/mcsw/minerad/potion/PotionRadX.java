package us.mcsw.minerad.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ref.RadProperties;

public class PotionRadX extends Potion {

	public PotionRadX() {
		super(ConfigMR.RADX_ID, false, 10044730);
		setPotionName("potion." + MineRad.MODID + ":radx");
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public int getStatusIconIndex() {
		return 0;
	}

}
