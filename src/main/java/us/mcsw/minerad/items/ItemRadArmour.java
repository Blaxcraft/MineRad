package us.mcsw.minerad.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.ref.TextureReference;

public class ItemRadArmour extends ItemArmor {

	static final ArmorMaterial material = EnumHelper.addArmorMaterial("radSuit", 75, new int[] { 2, 4, 3, 2 }, 0);

	int setPiece;

	public ItemRadArmour(int setPiece) {
		super(material, 0, setPiece);
		this.setPiece = setPiece;
		setUnlocalizedName("radSuit" + setPiece);
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
