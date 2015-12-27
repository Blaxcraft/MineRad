package us.mcsw.minerad.items;

import net.minecraft.item.ItemStack;
import us.mcsw.core.ItemMR;

public class ItemDiamondGrinder extends ItemMR {

	public ItemDiamondGrinder() {
		super("diamondGrinder");

		maxStackSize = 1;
		setMaxDamage(128);
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack it) {
		return false;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		itemStack.setItemDamage(itemStack.getItemDamage() + 1);
		return itemStack;
	}

}
