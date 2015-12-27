package us.mcsw.minerad.blocks;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.core.BlockMR;
import us.mcsw.minerad.tiles.TileRadEmitter;
import us.mcsw.minerad.util.RadUtil;

public class BlockRadEmitter extends BlockMR implements ITileEntityProvider {

	public int power, reach;

	public BlockRadEmitter(Material mat, String unloc, int power, int reach) {
		super(mat, unloc);
		this.power = power;
		this.reach = reach;

		isBlockContainer = true;
	}

	@Override
	public void onBlockPreDestroy(World w, int x, int y, int z, int m) {
		RadUtil.setPowerAndReach(w, x, y, z, 0, 0);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileRadEmitter();
	}

}
