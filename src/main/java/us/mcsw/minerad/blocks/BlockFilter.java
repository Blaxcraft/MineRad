package us.mcsw.minerad.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.core.BlockMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileFilter;

public class BlockFilter extends BlockMR implements ITileEntityProvider {

	public BlockFilter() {
		super(Material.iron, "filter");
		setHardness(2.0f);
		isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileFilter();
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer pl, int m, float cx, float cy,
			float cz) {
		TileEntity te = w.getTileEntity(x, y, z);
		if (te == null || pl.isSneaking())
			return false;

		pl.openGui(MineRad.ins, MachineReference.FILTER_ID, w, x, y, z);
		return true;
	}

}
