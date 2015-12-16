package us.mcsw.minerad.blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.BlockMR;
import us.mcsw.minerad.init.ModItems;

public class BlockUraniumTraces extends BlockMR {

	public BlockUraniumTraces() {
		super(Material.rock, "uraniumTraces");

		setHardness(3.0f);
		setHarvestLevel("pickaxe", 1);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(ModItems.uraniumChunk, world.rand.nextInt(2 * (fortune + 1)) + 2));
		return ret;
	}

}
