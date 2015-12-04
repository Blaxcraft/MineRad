package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;

public class BlockPureNeptunium extends BlockRadEmitter {

	public BlockPureNeptunium() {
		super(Material.iron, "pureNeptunium", 15, 1);
		
		setHardness(10.0f);
		setHarvestLevel("pickaxe", 3);
	}

}
