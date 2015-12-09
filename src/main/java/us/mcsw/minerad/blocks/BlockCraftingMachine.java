package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;
import us.mcsw.core.BlockMR;

public class BlockCraftingMachine extends BlockMR {

	public BlockCraftingMachine(String unloc) {
		super(Material.iron, unloc);
		setHardness(0.4f);
	}

}
