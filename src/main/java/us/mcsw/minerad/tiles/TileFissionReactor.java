package us.mcsw.minerad.tiles;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.core.TileMultiblock;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.FissionRecipes;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemCoolantCore;
import us.mcsw.minerad.items.ItemFissionCore;
import us.mcsw.minerad.util.RadUtil;

public class TileFissionReactor extends TileMultiblock {

	public int coreDamageCount = 0, oreProgressCount = 0, coolantDamageCount = 0, passiveCoolCount = 0;

	public int heat = 0;

	public TileFissionReactor() {
		super(6);
	}

	@Override
	public void onUpdate() {
		if (hasCore() && !isCoreDepleted() && hasSource()) {
			RadUtil.setPowerAndReach(worldObj, xCoord, yCoord + 1, zCoord, 15, 0);
			if (++coreDamageCount > 5) {
				damageCore(1);
				coreDamageCount = 0;
			}
			if (++oreProgressCount > 2) {
				incrProgress(1);
				oreProgressCount = 0;
			}
		} else {
			RadUtil.setPowerAndReach(worldObj, xCoord, yCoord + 1, zCoord, 0, 0);
			if (++passiveCoolCount > 5) {
				if (heat > 0) {
					heat--;
				}
			}
		}
		if (hasCoolant() && !isCoolantDepleted()) {
			if (hasCore() && !isCoreDepleted() && hasSource()) {
				if (++coolantDamageCount > 4) {
					damageCoolant(1);
					coolantDamageCount = 0;
				}
			}
			if (heat > 0) {
				heat -= 5;
				damageCoolant(1);
			}
		} else if (hasCore() && !isCoreDepleted()) {
			heat += 1;
		}
		if (!worldObj.isRemote) {
			if (heat > DANGER_HEAT_LEVEL) {
				if (Math.random() > 0.995) {
					this.worldObj.createExplosion(null, xCoord, yCoord + 1, zCoord, 12.0f, true);
				}
			}
		}
	}

	@Override
	public String getInventoryName() {
		return MineRad.MODID + ":fissionReactor";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	public static final int DANGER_HEAT_LEVEL = 1000;

	public String getHeatLevel() {
		if (heat <= 20) {
			return "cool";
		} else if (heat <= 200) {
			return "warm";
		} else if (heat <= 500) {
			return "hot";
		} else if (heat <= DANGER_HEAT_LEVEL) {
			return "extreme";
		} else {
			return "danger";
		}
	}

	int updateCount = 0;

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (updateCount-- < 0) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			updateCount = 20;
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot <= 3) {
			return FissionRecipes.hasRecipe(stack.getItem());
		} else if (slot == 4) {
			return stack.getItem() instanceof ItemFissionCore;
		} else if (slot == 5) {
			return stack.getItem() instanceof ItemCoolantCore;
		}
		return false;
	}

	@Override
	public boolean checkMultiblockForm() {
		int i = 0;
		for (int x = xCoord - 1; x <= xCoord + 1; x++)
			for (int y = yCoord; y <= yCoord + 2; y++)
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {
					TileEntity tile = worldObj.getTileEntity(x, y, z);
					if (tile != null && tile instanceof TileFissionReactor) {
						TileFissionReactor tf = (TileFissionReactor) tile;
						if (this.isMaster()) {
							if (tf.hasMaster())
								i++;
						} else if (!tf.hasMaster())
							i++;
					}
				}

		return i >= 26 && worldObj.isAirBlock(xCoord, yCoord + 1, zCoord);
	}

	@Override
	public void setupStructure() {
		for (int x = xCoord - 1; x <= xCoord + 1; x++)
			for (int y = yCoord; y <= yCoord + 2; y++)
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {
					TileEntity tile = worldObj.getTileEntity(x, y, z);
					boolean master = (x == xCoord && y == yCoord && z == zCoord);
					if (tile != null && tile instanceof TileFissionReactor) {
						TileFissionReactor tf = (TileFissionReactor) tile;
						tf.setMasterCoords(xCoord, yCoord, zCoord);
						tf.setHasMaster(true);
						tf.setIsMaster(master);
					}
				}
	}

	@Override
	public void resetStructure() {
		for (int x = xCoord - 1; x <= xCoord + 1; x++)
			for (int y = yCoord; y <= yCoord + 2; y++)
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {
					TileEntity tile = worldObj.getTileEntity(x, y, z);
					if (tile != null && tile instanceof TileMultiblock) {
						((TileMultiblock) tile).reset();
					}
				}
	}

	public boolean hasCore() {
		return getStackInSlot(4) != null && getStackInSlot(4).stackSize > 0;
	}

	public boolean hasSource() {
		for (int i = 0; i <= 3; i++) {
			if (hasSource(i)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasSource(int slot) {
		return getStackInSlot(slot) != null && getStackInSlot(slot).stackSize > 0
				&& FissionRecipes.hasRecipe(getStackInSlot(slot).getItem());
	}

	public boolean hasCoolant() {
		return getStackInSlot(5) != null && getStackInSlot(5).stackSize > 0;
	}

	public boolean isCoreDepleted() {
		if (!hasCore()) {
			return true;
		}
		return getStackInSlot(4).getItemDamage() > getStackInSlot(4).getMaxDamage();
	}

	public boolean isSourceCompleted(int slot) {
		if (!hasSource(slot)) {
			return false;
		}
		return getStackInSlot(slot).getItemDamage() >= getStackInSlot(slot).getMaxDamage();
	}

	public boolean isCoolantDepleted() {
		if (!hasCoolant()) {
			return true;
		}
		return getStackInSlot(5).getItemDamage() > getStackInSlot(5).getMaxDamage();
	}

	public void damageCore(int amount) {
		if (!isCoreDepleted()) {
			getStackInSlot(4).setItemDamage(getStackInSlot(4).getItemDamage() + amount);
			if (isCoreDepleted()) {
				setInventorySlotContents(4, new ItemStack(ModItems.emptyCore));
			}
		}
	}

	public void incrProgress(int amount) {
		for (int i = 0; i <= 3; i++) {
			if (hasSource(i) && !isSourceCompleted(i)) {
				getStackInSlot(i).setItemDamage(getStackInSlot(i).getItemDamage() + amount);
			}
			if (isSourceCompleted(i)) {
				ItemStack result = new ItemStack(FissionRecipes.getResultFrom(getStackInSlot(i).getItem()));
				setInventorySlotContents(i, result.copy());
			}
		}
	}

	public void damageCoolant(int amount) {
		if (!isCoolantDepleted()) {
			getStackInSlot(5).setItemDamage(getStackInSlot(5).getItemDamage() + amount);
			if (isCoolantDepleted()) {
				setInventorySlotContents(5, new ItemStack(ModItems.emptyCore));
			}
		}
	}

	@Override
	public void masterWriteToNBT(NBTTagCompound tag) {
	}

	@Override
	public void masterWriteSyncable(NBTTagCompound data) {
		data.setInteger("heat", heat);
	}

	@Override
	public void masterReadFromNBT(NBTTagCompound tag) {
	}

	@Override
	public void masterReadSyncable(NBTTagCompound data) {
		heat = data.getInteger("heat");
	}
}
