package us.mcsw.core;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ref.TextureReference;

public class ItemMRArmour extends ItemArmor implements IRegisteredItemMR {

	public ItemMRArmour(String unloc, ArmorMaterial mat, int id, int setPiece) {
		super(mat, id, setPiece);
		setUnlocalizedName(unloc + setPiece);
		setTextureName(TextureReference.RESOURCE_PREFIX + getBasicName());
		setCreativeTab(MineRad.creativeTab);
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
