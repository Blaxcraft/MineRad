package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.core.BlockMRMachine;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileShieldGenerator;

public class BlockShieldGenerator extends BlockMRMachine {

	public BlockShieldGenerator() {
		super(MachineReference.SHIELD_GENERATOR_ID, Material.iron, "shieldGenerator");
		setHardness(4.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileShieldGenerator();
	}

}
