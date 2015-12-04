package us.mcsw.minerad.blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockGroundZero extends BlockRadEmitter {

	public BlockGroundZero() {
		super(Material.ground, "groundZero", 15, 2);

		setHardness(2.0f);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}

}
