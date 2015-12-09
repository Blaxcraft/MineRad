package us.mcsw.core;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ref.TextureReference;

public class ItemMR extends Item {
	
	public ItemMR(String unloc) {
		super();
		setUnlocalizedName(unloc);
		setTextureName(TextureReference.RESOURCE_PREFIX + unloc);
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
