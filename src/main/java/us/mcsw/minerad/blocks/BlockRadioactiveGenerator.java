package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.core.BlockMRMachine;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileRadioactiveGenerator;

public class BlockRadioactiveGenerator extends BlockMRMachine {
	
	public BlockRadioactiveGenerator() {
		super(MachineReference.RADIO_GENERATOR_ID, Material.iron, "radioactiveGenerator");
		
		setHardness(4.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileRadioactiveGenerator();
	}

}
