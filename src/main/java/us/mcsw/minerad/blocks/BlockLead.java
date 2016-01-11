package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;
import us.mcsw.core.BlockMR;

public class BlockLead extends BlockMR {

	public BlockLead() {
		super(Material.iron, "blockLead");
		setHardness(6.0f);
		setHarvestLevel("pickaxe", 2);
	}

}
