package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import us.mcsw.core.ContainerMR;
import us.mcsw.core.TileMRInventory;
import us.mcsw.minerad.gui.slot.SlotCoolant;
import us.mcsw.minerad.gui.slot.SlotCoreFusion;
import us.mcsw.minerad.gui.slot.SlotFusion;
import us.mcsw.minerad.gui.slot.SlotProduct;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;

public class ContainerFusionReactor extends ContainerMR {

	TileFusionReactor master;

	public ContainerFusionReactor(InventoryPlayer ip, TileFusionReactor master) {
		super(ip, master);
		this.master = master;

		addPlayerInventory(ip);
	}

	@Override
	public void addSlotsToContainer(TileMRInventory tile) {
		addSlotToContainer(new SlotFusion(tile, 0, 58, 30));
		addSlotToContainer(new SlotFusion(tile, 1, 112, 30));
		addSlotToContainer(new SlotProduct(tile, 2, 85, 30));
		addSlotToContainer(new SlotCoreFusion(tile, 3, 85, 57));
		addSlotToContainer(new SlotCoolant(tile, 4, 141, 57));
	}

}
