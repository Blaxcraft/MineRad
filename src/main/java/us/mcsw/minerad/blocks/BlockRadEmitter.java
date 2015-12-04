package us.mcsw.minerad.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import us.mcsw.minerad.util.LogUtil;
import us.mcsw.minerad.util.RadUtil;

public class BlockRadEmitter extends BlockMR {

	public int power, reach;

	public BlockRadEmitter(Material mat, String unloc, int power, int reach) {
		super(mat, unloc);
		setTickRandomly(true);
		this.power = power;
		this.reach = reach;
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		super.onBlockAdded(w, x, y, z);
		if (!w.isRemote) {
			RadUtil.addEmitter(w, x, y, z, power, reach);
		}
	}

	@Override
	public void onBlockPreDestroy(World w, int x, int y, int z, int m) {
		super.onBlockPreDestroy(w, x, y, z, m);
		if (!w.isRemote) {
			RadUtil.setPowerAndReach(w, x, y, z, 0, 0);
		}
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random rand) {
		super.updateTick(w, x, y, z, rand);
		if (!w.isRemote) {
			RadUtil.addEmitter(w, x, y, z, power, reach);
		}
	}

}
