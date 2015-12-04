package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;

public class BlockPureUranium extends BlockRadEmitter {

	public BlockPureUranium() {
		super(Material.iron, "pureUranium", 9, 0);
		
		setHardness(7.0f);
		setHarvestLevel("pickaxe", 3);
	}

}
