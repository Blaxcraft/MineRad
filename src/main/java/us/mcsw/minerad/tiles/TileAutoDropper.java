package us.mcsw.minerad.tiles;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.TileMRInventory;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.MineRad;

public class TileAutoDropper extends TileMRInventory {

	public TileAutoDropper() {
		super(1);
	}

	@Override
	public void updateEntity() {
		if (getStackInSlot(0) != null) {
			setInventorySlotContents(0, dispenseItem(getStackInSlot(0), false));
		}
	}

	public ForgeDirection getSideFacing() {
		return ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
	}

	public ItemStack dispenseItem(ItemStack it, boolean sim) {
		ForgeDirection side = getSideFacing();
		TileEntity te = worldObj.getTileEntity(xCoord + side.offsetX, yCoord + side.offsetY, zCoord + side.offsetZ);
		if (te != null && te instanceof IInventory) {
			ItemStack rem = ItemUtil.addItemToNearbyInventories(this, it, false, sim, side);
			return rem;
		} else {
			if (!sim) {
				BlockSourceImpl impl = new BlockSourceImpl(worldObj, xCoord, yCoord, zCoord);
				BehaviorDefaultDispenseItem.doDispense(worldObj, it, 6,
						BlockDispenser.func_149937_b(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)),
						BlockDispenser.func_149939_a(impl));
			}
			return null;
		}
	}

	@Override
	public String getInventoryName() {
		return MineRad.MODID + ":autoDropper";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack it) {
		return true;
	}

}
