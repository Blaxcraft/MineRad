package us.mcsw.minerad.inv;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import us.mcsw.core.ContainerMR;
import us.mcsw.core.TileMRInventory;
import us.mcsw.minerad.tiles.TileFissionReactor;

public class ContainerFissionReactor extends ContainerMR {
	
	TileFissionReactor master;

	public ContainerFissionReactor(InventoryPlayer ip, TileFissionReactor master) {
		super(ip, master);
		this.master = master;
		
		addPlayerInventory(ip);
	}

	@Override
	public void addSlotsToContainer(TileMRInventory tile) {
		addSlotToContainer(new SlotFission(tile, 0, 58, 32));
		addSlotToContainer(new SlotFission(tile, 1, 85, 5));
		addSlotToContainer(new SlotFission(tile, 2, 85, 59));
		addSlotToContainer(new SlotFission(tile, 3, 112, 32));
		addSlotToContainer(new SlotCoreFission(tile, 4, 85, 32));
		addSlotToContainer(new SlotCoolant(tile, 5, 141, 59));
	}

}
