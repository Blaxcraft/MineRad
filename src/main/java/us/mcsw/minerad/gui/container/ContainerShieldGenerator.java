package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ContainerMRMachine;
import us.mcsw.minerad.items.ItemShieldUpgrade;
import us.mcsw.minerad.tiles.TileRadioactiveGenerator;
import us.mcsw.minerad.tiles.TileShieldGenerator;

public class ContainerShieldGenerator extends ContainerMRMachine {

	TileShieldGenerator tile;

	public ContainerShieldGenerator(InventoryPlayer ip, TileShieldGenerator tile) {
		super(ip, tile);
		this.tile = tile;
	}

	@Override
	public void addSlotsToContainer(IInventory inv) {
		addSlotToContainer(new Slot(inv, 0, 134, 34) {
			@Override
			public boolean isItemValid(ItemStack it) {
				return it != null && it.getItem() instanceof ItemShieldUpgrade;
			}
		});
	}

}
