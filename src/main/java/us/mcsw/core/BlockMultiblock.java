package us.mcsw.core;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.minerad.tiles.TileFusionReactor;

public abstract class BlockMultiblock extends BlockMR implements ITileEntityProvider {

	public BlockMultiblock(Material mat, String unloc) {
		super(mat, unloc);

		isBlockContainer = true;
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
		TileEntity tile = w.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileMultiblock) {
			TileMultiblock multiBlock = (TileMultiblock) tile;
			if (multiBlock.hasMaster()) {
				if (multiBlock.isMaster()) {
					if (!multiBlock.checkMultiblockForm())
						multiBlock.resetStructure();
				} else {
					if (!multiBlock.checkForMaster()) {
						TileMultiblock master = multiBlock.getMaster();
						master.resetStructure();
					} else {
						if (!multiBlock.getMaster().checkMultiblockForm()) {
							multiBlock.getMaster().resetStructure();
						}
					}
				}
			}
		}
		super.onNeighborBlockChange(w, x, y, z, block);
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int m) {
		TileEntity te = w.getTileEntity(x, y, z);
		if (!(te instanceof TileMultiblock)) {
			return;
		}
		TileMultiblock check = (TileMultiblock) te;

		if (check.hasMaster()) {
			TileMultiblock inv = check.getMaster();
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack item = inv.getStackInSlot(i);

				if (item != null && item.stackSize > 0) {
					float rx = w.rand.nextFloat() * 0.8F + 0.1F;
					float ry = w.rand.nextFloat() * 0.8F + 0.1F;
					float rz = w.rand.nextFloat() * 0.8F + 0.1F;

					EntityItem entityItem = new EntityItem(w, x + rx, y + ry, z + rz, item.copy());

					if (item.hasTagCompound()) {
						entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
					}

					float factor = 0.05F;
					entityItem.motionX = w.rand.nextGaussian() * factor;
					entityItem.motionY = w.rand.nextGaussian() * factor + 0.2F;
					entityItem.motionZ = w.rand.nextGaussian() * factor;
					w.spawnEntityInWorld(entityItem);
					item.stackSize = 0;
				}
				
				inv.setInventorySlotContents(i, null);
			}
		}

		super.breakBlock(w, x, y, z, b, m);
	}

	@Override
	public abstract TileEntity createNewTileEntity(World w, int m);

}
