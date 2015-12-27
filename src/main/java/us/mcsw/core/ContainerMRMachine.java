package us.mcsw.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public abstract class ContainerMRMachine extends ContainerMRTile {

	TileMRMachine tile;

	public ContainerMRMachine(InventoryPlayer ip, TileMRMachine tile) {
		super(ip, tile);
		this.tile = tile;

		addPlayerInventory(ip);
	}

	int lastStored = 0;

	@Override
	public void addCraftingToCrafters(ICrafting c) {
		super.addCraftingToCrafters(c);
		c.sendProgressBarUpdate(this, 0, tile.storage.getEnergyStored());
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (Object o : crafters) {
			ICrafting c = (ICrafting) o;

			if (lastStored != tile.storage.getEnergyStored()) {
				c.sendProgressBarUpdate(this, 0, tile.storage.getEnergyStored());
			}
		}
		lastStored = tile.storage.getEnergyStored();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int i, int v) {
		if (i == 0) {
			tile.storage.setEnergyStored(v);
		}
	}

}
