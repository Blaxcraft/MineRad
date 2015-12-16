package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import us.mcsw.core.ContainerMRTile;
import us.mcsw.core.TileMRInventory;
import us.mcsw.core.TileMRMachine;
import us.mcsw.minerad.tiles.TileRadioTowerBase;

public class ContainerRadioTowerBase extends ContainerMRTile {

	TileRadioTowerBase tile;

	public ContainerRadioTowerBase(InventoryPlayer ip, TileRadioTowerBase tile) {
		super(ip, tile);
		this.tile = tile;
	}

	@Override
	public void addSlotsToContainer(IInventory inv) {
	}

}
