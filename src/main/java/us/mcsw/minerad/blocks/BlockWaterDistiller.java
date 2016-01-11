package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.core.BlockMRMachine;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileWaterDistiller;

public class BlockWaterDistiller extends BlockMRMachine {

	public BlockWaterDistiller() {
		super(MachineReference.WATER_DISTILLER_ID, Material.iron, "waterDistiller");
		
		setHardness(3.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileWaterDistiller();
	}

}
