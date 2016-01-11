package us.mcsw.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.core.util.LogUtil;

public abstract class ContainerMRTile extends ContainerMR {

	TileMRInventory tile;

	public ContainerMRTile(InventoryPlayer ip, TileMRInventory tile) {
		super(ip, tile);
		this.tile = tile;
	}

	@Override
	public boolean canInteractWith(EntityPlayer pl) {
		return tile.isUseableByPlayer(pl);
	}

}
