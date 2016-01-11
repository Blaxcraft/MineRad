package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;
import us.mcsw.core.BlockMR;

public class BlockOreLead extends BlockMR {

	public BlockOreLead() {
		super(Material.rock, "oreLead");
		
		setHardness(5.0f);
		setHarvestLevel("pickaxe", 2);
	}

}
