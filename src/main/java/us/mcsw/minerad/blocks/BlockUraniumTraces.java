package us.mcsw.minerad.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.util.RadUtil;

public class BlockUraniumTraces extends BlockRadEmitter {

	public BlockUraniumTraces() {
		super(Material.rock, "uraniumTraces", 1, 0);

		setHardness(3.0f);
		setHarvestLevel("pickaxe", 1);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(ModItems.uraniumChunk, 2 + fortune));
		return ret;
	}

}
