package us.mcsw.minerad.ref;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.recipes.RadiationRecipes;
import us.mcsw.minerad.recipes.RadiationRecipes.RadiationRecipe;
import us.mcsw.minerad.util.RadUtil;

public class RadEffectsHandler {

	public static void onRad(Entity e, RadProperties props, double perSecond) {
		int rds = RadUtil.getRadsAtLocation(e.worldObj, (int) Math.floor(e.posX), (int) Math.floor(e.posY),
				(int) Math.floor(e.posZ));
		if (e instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer) e;
			if (props.getRadiation() > ConfigMR.RAD_SICKNESS_THRESHOLD_1) {
				pl.addPotionEffect(new PotionEffect(MineRad.potionRadiationSickness.id, 19,
						props.getRadiation() > ConfigMR.RAD_SICKNESS_THRESHOLD_3 ? 2
								: props.getRadiation() > ConfigMR.RAD_SICKNESS_THRESHOLD_2 ? 1 : 0,
						true));
			}
			PlayerProperties pprops = PlayerProperties.get(pl);
			if (pprops != null && !pprops.isGhoul()) {
				if (props.getRadiation() >= ConfigMR.GHOUL_THRESHOLD) {
					if (pprops.ghoulTicks++ > ConfigMR.GHOUL_TIME_NEEDED) {
						pprops.onBecomeGhoul();
					}
				} else if (pprops.ghoulTicks > 0)
					pprops.ghoulTicks = 0;
			}
		}
		if (rds > 0) {
			props.addRadiation(rds / perSecond, false);
			if (e instanceof EntityVillager && props.getRadiation() > ConfigMR.VILLAGER_CONVERSION_THRESHOLD) {
				EntityVillager le = (EntityVillager) e;

				le.addPotionEffect(new PotionEffect(Potion.poison.getId(), 100, 1));
				le.attackEntityFrom(RadUtil.radiationDamage, ((float) props.getRadiation() - 150.0f) / 200.0f);

				if (le.getHealth() <= 0) {
					EntityZombie zom = new EntityZombie(le.worldObj);
					zom.setVillager(true);
					zom.setPosition(le.posX, le.posY, le.posZ);
					le.worldObj.spawnEntityInWorld(zom);
					props.setRadiation(-1);
				}
			}
			if (e instanceof EntityItem) {
				EntityItem ei = (EntityItem) e;
				RadiationRecipe recipe;
				if ((recipe = RadiationRecipes.getRecipeFor(ei.getEntityItem())) != null) {
					if (props.getRadiation() > recipe.getRads()) {
						ItemStack result = recipe.getProduct();
						result.stackSize *= ei.getEntityItem().stackSize;
						ei.setEntityItemStack(result);
						props.setRadiation(0);
					}
				}
			}
		} else if (props.getRadiation() > 0) {
			props.addRadiation(-0.25 * ConfigMR.RAD_LOSS_MULTIPLIER / perSecond, false);
		}
	}

}
