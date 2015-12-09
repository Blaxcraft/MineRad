package us.mcsw.core;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.minerad.tiles.TileFusionReactor;

public abstract class BlockMultiblock extends BlockMR implements ITileEntityProvider {

	public BlockMultiblock(Material mat, String unloc) {
		super(mat, unloc);

		isBlockContainer = true;
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
		TileEntity tile = w.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileMultiblock) {
			TileMultiblock multiBlock = (TileMultiblock) tile;
			if (multiBlock.hasMaster()) {
				if (multiBlock.isMaster()) {
					if (!multiBlock.checkMultiblockForm())
						multiBlock.resetStructure();
				} else {
					if (!multiBlock.checkForMaster()) {
						TileMultiblock master = multiBlock.getMaster();
						master.resetStructure();
					} else {
						if (!multiBlock.getMaster().checkMultiblockForm()) {
							multiBlock.getMaster().resetStructure();
						}
					}
				}
			}
		}
		super.onNeighborBlockChange(w, x, y, z, block);
	}

	@Override
	public abstract TileEntity createNewTileEntity(World w, int m);

}
