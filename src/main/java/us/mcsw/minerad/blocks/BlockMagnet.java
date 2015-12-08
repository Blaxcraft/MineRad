package us.mcsw.minerad.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.minerad.tiles.TileMagnet;

public class BlockMagnet extends BlockMR implements ITileEntityProvider {

	public BlockMagnet() {
		super(Material.iron, "magnet");
		
		setHardness(5.0f);
		isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileMagnet();
	}

}
