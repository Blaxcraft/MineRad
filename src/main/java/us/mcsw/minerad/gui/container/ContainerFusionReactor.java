package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import us.mcsw.core.ContainerMRTile;
import us.mcsw.core.TileMRInventory;
import us.mcsw.minerad.gui.slot.SlotCoolant;
import us.mcsw.minerad.gui.slot.SlotCoreFusion;
import us.mcsw.minerad.gui.slot.SlotFusion;
import us.mcsw.minerad.gui.slot.SlotProduct;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;

public class ContainerFusionReactor extends ContainerMRTile {

	TileFusionReactor master;

	public ContainerFusionReactor(InventoryPlayer ip, TileFusionReactor master) {
		super(ip, master);
		this.master = master;

		addPlayerInventory(ip);
	}

	@Override
	public void addSlotsToContainer(IInventory inv) {
		addSlotToContainer(new SlotFusion(inv, 0, 58, 30));
		addSlotToContainer(new SlotFusion(inv, 1, 112, 30));
		addSlotToContainer(new SlotProduct(inv, 2, 85, 30));
		addSlotToContainer(new SlotCoreFusion(inv, 3, 85, 57));
		addSlotToContainer(new SlotCoolant(inv, 4, 141, 57));
	}

}
