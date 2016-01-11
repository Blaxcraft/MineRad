package us.mcsw.minerad.ref;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.items.ItemRadArmour;
import us.mcsw.minerad.potion.PotionRadX;

public class RadProperties implements IExtendedEntityProperties {

	public static final String PROPERTIES_NAME = MineRad.MODID + "_RadProps";

	private final Entity e;
	private double rads;
	private double radResist;

	public RadProperties(Entity e) {
		this.e = e;
		this.rads = 0;
		this.radResist = 0;
	}

	public static final void register(Entity e) {
		e.registerExtendedProperties(PROPERTIES_NAME, new RadProperties(e));
	}

	public static final RadProperties get(Entity e) {
		return (RadProperties) e.getExtendedProperties(PROPERTIES_NAME);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound data = new NBTTagCompound();
		data.setDouble("RADS", rads);
		data.setDouble("radResist", radResist);
		compound.setTag(PROPERTIES_NAME, data);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound data = (NBTTagCompound) compound.getTag(PROPERTIES_NAME);
		this.rads = data.getDouble("RADS");
		this.radResist = data.getDouble("radResist");
	}

	public double getRadResistance() {
		double ret = radResist;
		if (e instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e;
			for (ItemStack it : p.inventory.armorInventory) {
				if (it != null) {
					if (it.getItem() instanceof ItemRadArmour) {
						ItemRadArmour ira = (ItemRadArmour) it.getItem();
						ret += ira.damageReduceAmount * ConfigMR.RAD_ARMOUR_PROTECTION_MULTIPLIER;
					}

					int lvl = EnchantmentHelper.getEnchantmentLevel(MineRad.enchRadResist.effectId, it);
					if (lvl > 0) {
						ret += lvl * 3 - 1;
					}
				}
			}
			if (p.capabilities.isCreativeMode) {
				return Double.MAX_VALUE;
			}
			PlayerProperties pprops = PlayerProperties.get(p);
			if (pprops != null && pprops.isGhoul()) {
				return Double.MAX_VALUE;
			}
		}
		if (e instanceof EntityLivingBase) {
			EntityLivingBase elb = (EntityLivingBase) e;
			for (Object o : elb.getActivePotionEffects()) {
				if (o instanceof PotionEffect) {
					PotionEffect pe = (PotionEffect) o;
					if (pe.getPotionID() == MineRad.potionRadX.id) {
						ret += 4 + (pe.getAmplifier() * 2);
					}
				}
			}
		}
		return ret + 1;
	}

	public double getRadiation() {
		return rads;
	}

	public void addRadiation(double amt, boolean ignoreResist) {
		double add;
		if (e instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e;
			PlayerProperties pprops = PlayerProperties.get(p);
			if (pprops != null) {
				if (pprops.isGhoul()) {
					p.heal((float) (amt / (pprops.isGlowingGhoul() ? 10.0 : 25.0)));
					return;
				}
			}
		}
		if (!ignoreResist) {
			if (amt < 0) {
				add = amt * getRadResistance() / 20;
			} else
				add = (double) amt / getRadResistance();
		} else {
			add = amt;
		}
		rads += add;
		if (rads < 0) {
			rads = 0;
		}
	}

	public void setRadiation(double amt) {
		rads = amt;
	}

	@Override
	public void init(Entity entity, World world) {
	}

}
