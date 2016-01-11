package us.mcsw.minerad.items;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import us.mcsw.core.ItemMRArmour;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ref.TextureReference;

public class ItemRadArmour extends ItemMRArmour {

	static final ArmorMaterial material = EnumHelper.addArmorMaterial("radSuit", 75, new int[] { 2, 4, 3, 2 }, 10);

	int setPiece;

	public ItemRadArmour(int setPiece) {
		super("radSuit", material, 0, setPiece);
		this.setPiece = setPiece;
	}

}
