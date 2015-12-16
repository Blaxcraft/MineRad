package us.mcsw.minerad.blocks;

import java.util.ArrayList;

import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.BlockMR;
import us.mcsw.minerad.init.ModItems;

public class BlockUraniumLump extends BlockMR {

	public BlockUraniumLump() {
		super(Material.rock, "uraniumLump");

		setHardness(7.0f);
		setHarvestLevel("pickaxe", 2);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		if (world.rand.nextInt(15 / (fortune + 1)) == 0) {
			ret.add(new ItemStack(ModItems.uraniumOreItem));
			ret.add(new ItemStack(ModItems.uraniumChunk, world.rand.nextInt(2 * (fortune + 1)) + 2));
		} else {
			ret.add(new ItemStack(ModItems.uraniumChunk, world.rand.nextInt(6 * (fortune + 1)) + 8));
		}
		return ret;
	}

}
