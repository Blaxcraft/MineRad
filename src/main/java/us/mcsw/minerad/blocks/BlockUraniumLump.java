package us.mcsw.minerad.blocks;

import java.util.ArrayList;

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
		ret.add(new ItemStack(ModItems.uraniumChunk, 18 + fortune * 2));
		return ret;
	}

}
