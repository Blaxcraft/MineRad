package us.mcsw.core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ref.TextureReference;

public class ItemBlockMR extends ItemBlock {

	public Block block;

	public ItemBlockMR(Block block) {
		super(block);
		this.block = block;
	}
}
