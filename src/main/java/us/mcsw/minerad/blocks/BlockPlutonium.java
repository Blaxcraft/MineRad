package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;

public class BlockPlutonium extends BlockRadEmitter {

	public BlockPlutonium() {
		super(Material.iron, "blockPlutonium", 24, 1);

		setHardness(12.0f);
		setHarvestLevel("pickaxe", 3);
	}

}
