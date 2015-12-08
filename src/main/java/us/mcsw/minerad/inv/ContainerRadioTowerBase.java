package us.mcsw.minerad.inv;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import us.mcsw.minerad.tiles.TileMRMachine;
import us.mcsw.minerad.tiles.TileRadioTowerBase;

public class ContainerRadioTowerBase extends ContainerMRMachine {

	TileRadioTowerBase tile;

	public ContainerRadioTowerBase(InventoryPlayer ip, TileRadioTowerBase tile) {
		super(ip, tile);
		this.tile = tile;
	}

	@Override
	public void addSlotsToContainer(TileMRMachine tile) {
	}

}
