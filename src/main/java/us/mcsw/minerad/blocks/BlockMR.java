package us.mcsw.minerad.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ref.TextureReference;

public class BlockMR extends Block {

	public BlockMR(Material mat, String unloc) {
		super(mat);
		setBlockName(unloc);
		setBlockTextureName(TextureReference.RESOURCE_PREFIX + unloc);
		setCreativeTab(MineRad.creativeTab);
	}

	@Override
	public String getUnlocalizedName() {
		return String.format("block.%s%s", TextureReference.RESOURCE_PREFIX,
				getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	String getUnwrappedUnlocalizedName(String unlc) {
		return unlc.substring(unlc.indexOf(".") + 1);
	}

	public String getBasicName() {
		return getUnwrappedUnlocalizedName(super.getUnlocalizedName());
	}

}
