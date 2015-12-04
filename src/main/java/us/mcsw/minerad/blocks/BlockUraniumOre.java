package us.mcsw.minerad.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.util.LogUtil;
import us.mcsw.minerad.util.RadUtil;

public class BlockUraniumOre extends BlockRadEmitter {

	public BlockUraniumOre() {
		super(Material.rock, "uraniumOre", 3, 0);

		setHardness(15.0f);
		setHarvestLevel("pickaxe", 3);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(ModItems.uraniumChunk, 12 + fortune * 2));
		ret.add(new ItemStack(ModItems.uraniumOreItem, 2));
		return ret;
	}

}
