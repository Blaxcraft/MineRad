package us.mcsw.minerad.ref;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.util.LogUtil;
import us.mcsw.minerad.util.RadUtil;

public class RadEffectsHandler {

	public static void onRad(Entity e, RadProperties props, double perSecond) {
		int rds = RadUtil.getRadsAtLocation(e.worldObj, (int) e.posX, (int) e.posY, (int) e.posZ);
		if (e instanceof EntityPlayer) {
			if (props.getRadiation() > 200) {
				((EntityPlayer) e).addPotionEffect(new PotionEffect(MineRad.potionRadiationSickness.id, 19,
						props.getRadiation() > 400 ? 1 : 0, true));
			}
		}
		if (rds > 0) {
			props.addRadiation(rds / perSecond);
			if (!(e instanceof EntityPlayer) && props.getRadiation() > 200) {
				EntityLiving le = (EntityLiving) e;

				le.addPotionEffect(new PotionEffect(Potion.poison.getId(), 100, 1));
				if (!(le instanceof EntityMob)) {
					le.attackEntityFrom(RadUtil.radiationDamage, ((float) props.getRadiation() - 150.0f) / 200.0f);
				}

				if (le instanceof EntityVillager && le.getHealth() <= 0) {
					EntityZombie zom = new EntityZombie(le.worldObj);
					zom.setVillager(true);
					zom.setPosition(le.posX, le.posY, le.posZ);
					le.worldObj.spawnEntityInWorld(zom);
					props.setRadiation(-1);
				}
			}
		} else if (props.getRadiation() > 0) {
			props.addRadiation(-0.1 / perSecond);
		}
	}

}
