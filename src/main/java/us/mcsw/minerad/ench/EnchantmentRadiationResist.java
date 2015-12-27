package us.mcsw.minerad.ench;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;

public class EnchantmentRadiationResist extends Enchantment {

	public EnchantmentRadiationResist() {
		super(ConfigMR.RAD_RESIST_ID, 5, EnumEnchantmentType.armor);
	}
	
	@Override
	public int getMinLevel() {
		return 1;
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
	@Override
	public String getName() {
		return "enchantment." + MineRad.MODID + ":radResist";
	}

}
