package us.mcsw.minerad.inv;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import us.mcsw.core.ContainerMR;
import us.mcsw.core.TileMRInventory;
import us.mcsw.core.TileMRMachine;
import us.mcsw.minerad.tiles.TileRadioTowerBase;

public class ContainerRadioTowerBase extends ContainerMR {

	TileRadioTowerBase tile;

	public ContainerRadioTowerBase(InventoryPlayer ip, TileRadioTowerBase tile) {
		super(ip, tile);
		this.tile = tile;
	}

	@Override
	public void addSlotsToContainer(TileMRInventory tile) {
	}

}
