package us.mcsw.minerad.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.util.LogUtil;
import us.mcsw.minerad.util.RadUtil;

public class BlockUraniumLump extends BlockRadEmitter {

	public BlockUraniumLump() {
		super(Material.rock, "uraniumLump", 2, 0);

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
