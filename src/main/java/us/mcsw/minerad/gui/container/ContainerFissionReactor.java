package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import us.mcsw.core.ContainerMRTile;
import us.mcsw.core.TileMRInventory;
import us.mcsw.minerad.gui.slot.SlotCoolant;
import us.mcsw.minerad.gui.slot.SlotCoreFission;
import us.mcsw.minerad.gui.slot.SlotFission;
import us.mcsw.minerad.tiles.TileFissionReactor;

public class ContainerFissionReactor extends ContainerMRTile {
	
	TileFissionReactor master;

	public ContainerFissionReactor(InventoryPlayer ip, TileFissionReactor master) {
		super(ip, master);
		this.master = master;
		
		addPlayerInventory(ip);
	}

	@Override
	public void addSlotsToContainer(IInventory inv) {
		addSlotToContainer(new SlotFission(inv, 0, 59, 31));
		addSlotToContainer(new SlotFission(inv, 1, 85, 5));
		addSlotToContainer(new SlotFission(inv, 2, 85, 57));
		addSlotToContainer(new SlotFission(inv, 3, 111, 31));
		addSlotToContainer(new SlotCoreFission(inv, 4, 85, 31));
		addSlotToContainer(new SlotCoolant(inv, 5, 141, 57));
	}

}
