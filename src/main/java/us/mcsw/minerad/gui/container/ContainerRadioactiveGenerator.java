package us.mcsw.minerad.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ContainerMRMachine;
import us.mcsw.minerad.gui.slot.SlotCoolant;
import us.mcsw.minerad.gui.slot.SlotCoreEmpty;
import us.mcsw.minerad.items.ItemFissionCore;
import us.mcsw.minerad.items.ItemFusionCore;
import us.mcsw.minerad.recipes.GeneratorFuels;
import us.mcsw.minerad.recipes.GeneratorFuels.GeneratorFuel;
import us.mcsw.minerad.tiles.TileRadioactiveGenerator;

public class ContainerRadioactiveGenerator extends ContainerMRMachine {

	public ContainerRadioactiveGenerator(InventoryPlayer ip, TileRadioactiveGenerator tile) {
		super(ip, tile);
	}

	@Override
	public void addSlotsToContainer(IInventory inv) {
		addSlotToContainer(new SlotCoreEmpty(inv, 0, 68, 30) {
			@Override
			public boolean isItemValid(ItemStack it) {
				return it != null && (it.getItem() instanceof ItemFissionCore || it.getItem() instanceof ItemFusionCore
						|| GeneratorFuels.isFuel(it.getItem()));
			}
		});
		addSlotToContainer(new SlotCoolant(inv, 1, 95, 30));
	}

}
