package us.mcsw.minerad.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileRadioTowerBase;

public class BlockRadioTowerBase extends BlockMRMachine implements ITileEntityProvider {

	public BlockRadioTowerBase() {
		super(MachineReference.RADIO_TOWER_ID, Material.iron, "radioTowerBase");
		
		setHardness(4.0f);
		isBlockContainer = true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileRadioTowerBase();
	}

}
