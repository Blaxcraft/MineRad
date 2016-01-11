package us.mcsw.core;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ref.TextureReference;

public class ItemMRFood extends ItemFood implements IRegisteredItemMR {

	public ItemMRFood(String unloc, int food, float satur) {
		super(food, satur, false);
		ItemMR.constructItemMR(this, unloc);
	}

	@Override
	public String getUnlocalizedName() {
		return String.format("item.%s%s", TextureReference.RESOURCE_PREFIX,
				getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack stack) {
		return getUnlocalizedName();
	};

	String getUnwrappedUnlocalizedName(String unlc) {
		return unlc.substring(unlc.indexOf(".") + 1);
	}

	public String getBasicName() {
		return getUnwrappedUnlocalizedName(super.getUnlocalizedName());
	}

}
