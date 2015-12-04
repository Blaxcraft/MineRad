package us.mcsw.minerad.tiles;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.minerad.init.FissionRecipes;
import us.mcsw.minerad.init.FusionRecipes;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.util.RadUtil;

public class TileFusionReactor extends TileMultiblock {

	int coreDamageCount = 0, oreProgressCount = 0, coolantDamageCount = 0, passiveCoolCount = 0;

	public Item source = null;
	public int maxNeeded = 0;

	public int heat = 0;

	@Override
	public void onUpdate() {
		if (hasCore && !isCoreDepleted()) {
			RadUtil.setPowerAndReach(worldObj, xCoord, yCoord + 1, zCoord, 5, 1);
			if (++coreDamageCount > 5) {
				damageCore(1);
				coreDamageCount = 0;
			}
			if (hasOre && !isOreCompleted()) {
				if (++oreProgressCount > 2) {
					incrProgress(1);
					oreProgressCount = 0;
				}
			}
		} else {
			RadUtil.setPowerAndReach(worldObj, xCoord, yCoord + 1, zCoord, 0, 0);
			if (++passiveCoolCount > 5) {
				if (heat > 0) {
					heat--;
				}
			}
		}
		if (hasCoolant && !isCoolantDepleted()) {
			if (hasCore && !isCoreDepleted()) {
				if (++coolantDamageCount > 7) {
					damageCoolant(1);
					coolantDamageCount = 0;
				}
			}
			if (heat > 0) {
				heat--;
				damageCoolant(1);
			}
		} else if (hasCore && !isCoreDepleted()) {
			heat += 1;
		}
		if (!worldObj.isRemote) {
			if (heat > DANGER_HEAT_LEVEL) {
				if (Math.random() > 0.995) {
					hasCore = false;
					this.worldObj.createExplosion(null, coreX, coreY, coreZ, 15.0f, true);
				}
			}
		}
	}

	public static final int DANGER_HEAT_LEVEL = 1000;

	public String getHeatLevel() {
		if (heat <= 20) {
			return "Cool";
		} else if (heat <= 200) {
			return "Warm";
		} else if (heat <= 500) {
			return "Hot";
		} else if (heat <= DANGER_HEAT_LEVEL) {
			return "Extreme";
		} else {
			return "DANGER";
		}
	}

	private void onCoreDepleted() {
		worldObj.markBlockForUpdate(coreX, coreY, coreZ);
	}

	private void onOreCompleted() {
		worldObj.markBlockForUpdate(oreX, oreY, oreZ);
	}

	private void onCoolantDepleted() {
		worldObj.markBlockForUpdate(coolantX, coolantY, coolantZ);
	}

	int updateCount = 0;

	public boolean hasCore = false;
	public int coreX = 0, coreY = 0, coreZ = 0;
	public int coreDamage = -1;

	public boolean hasOre = false;
	public int oreX = 0, oreY = 0, oreZ = 0;
	public int oreProgress = -1;

	public boolean hasCoolant = false;
	public int coolantX = 0, coolantY = 0, coolantZ = 0;
	public int coolantDamage = -1;

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (updateCount-- < 0) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			updateCount = 20;
		}
	}

	@Override
	public boolean checkMultiblockForm() {
		int i = 0;
		for (int x = xCoord - 1; x <= xCoord + 1; x++)
			for (int y = yCoord; y <= yCoord + 2; y++)
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {
					TileEntity tile = worldObj.getTileEntity(x, y, z);
					if (tile != null && tile instanceof TileFusionReactor) {
						TileFusionReactor tf = (TileFusionReactor) tile;
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
					if (tile != null && tile instanceof TileFusionReactor) {
						TileFusionReactor tf = (TileFusionReactor) tile;
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

		if (hasCore) {
			ItemStack core = !isCoreDepleted() ? new ItemStack(ModItems.fusionCore, 1, coreDamage)
					: new ItemStack(ModItems.emptyCore);
			if (!worldObj.isRemote) {
				EntityItem drop = new EntityItem(worldObj);
				drop.setPosition(coreX, coreY, coreZ);
				drop.setEntityItemStack(core);
				worldObj.spawnEntityInWorld(drop);
			}
		}

		if (hasOre) {
			ItemStack ore = !isOreCompleted() ? new ItemStack(source, 1, coreDamage)
					: new ItemStack(FusionRecipes.getResultFrom(source));
			if (!worldObj.isRemote) {
				EntityItem drop = new EntityItem(worldObj);
				drop.setPosition(oreX, oreY, oreZ);
				drop.setEntityItemStack(ore);
				worldObj.spawnEntityInWorld(drop);
			}
		}

		if (hasCoolant) {
			ItemStack cool = !isCoolantDepleted() ? new ItemStack(ModItems.coolantCore, 1, coolantDamage)
					: new ItemStack(ModItems.emptyCore);
			if (!worldObj.isRemote) {
				EntityItem drop = new EntityItem(worldObj);
				drop.setPosition(oreX, oreY, oreZ);
				drop.setEntityItemStack(cool);
				worldObj.spawnEntityInWorld(drop);
			}
		}

		RadUtil.setPowerAndReach(worldObj, xCoord, yCoord + 1, zCoord, 0, 0);
		coreX = coreY = coreZ = 0;
		coreDamage = -1;
		hasCore = false;

		oreX = oreY = oreZ = 0;
		oreProgress = -1;
		hasOre = false;

		coolantX = coolantY = coolantZ = 0;
		coolantDamage = -1;
		hasCoolant = false;

		source = null;
	}

	public boolean isCoreDepleted() {
		return coreDamage > ModItems.fusionCore.getMaxDamage();
	}

	public boolean isOreCompleted() {
		return oreProgress >= maxNeeded;
	}

	public boolean isCoolantDepleted() {
		return coolantDamage > ModItems.coolantCore.getMaxDamage();
	}

	public void damageCore(int amount) {
		if (!isCoreDepleted()) {
			coreDamage += amount;
			if (isCoreDepleted()) {
				onCoreDepleted();
			}
		}
	}

	public void incrProgress(int amount) {
		if (!isOreCompleted()) {
			oreProgress += amount;
			if (isOreCompleted()) {
				onOreCompleted();
			}
		}
	}

	public void damageCoolant(int amount) {
		if (!isCoolantDepleted()) {
			coolantDamage += amount;
			if (isCoolantDepleted()) {
				onCoolantDepleted();
			}
		}
	}

	@Override
	public void masterWriteToNBT(NBTTagCompound tag) {
	}

	@Override
	public void masterWriteSyncable(NBTTagCompound data) {
		data.setBoolean("hasCore", hasCore);
		if (hasCore) {
			data.setInteger("coreDamage", coreDamage);
			data.setInteger("coreX", coreX);
			data.setInteger("coreY", coreY);
			data.setInteger("coreZ", coreZ);
		}
		data.setBoolean("hasOre", hasOre);
		if (hasOre) {
			data.setInteger("oreProgress", oreProgress);
			data.setInteger("oreX", oreX);
			data.setInteger("oreY", oreY);
			data.setInteger("oreZ", oreZ);
		}
		data.setBoolean("hasCoolant", hasCoolant);
		if (hasCoolant) {
			data.setInteger("coolantDamage", coolantDamage);
			data.setInteger("coolantX", coolantX);
			data.setInteger("coolantY", coolantY);
			data.setInteger("coolantZ", coolantZ);
		}
		data.setInteger("maxNeeded", maxNeeded);
		data.setInteger("heat", heat);
		if (source != null)
			data.setTag("source", new ItemStack(source).stackTagCompound);
	}

	@Override
	public void masterReadFromNBT(NBTTagCompound tag) {
	}

	@Override
	public void masterReadSyncable(NBTTagCompound data) {
		hasCore = data.getBoolean("hasCore");
		if (hasCore) {
			coreDamage = data.getInteger("coreDamage");
			coreX = data.getInteger("coreX");
			coreY = data.getInteger("coreY");
			coreZ = data.getInteger("coreZ");
		} else {
			coreDamage = -1;
			coreX = 0;
			coreY = 0;
			coreZ = 0;
		}
		hasOre = data.getBoolean("hasOre");
		if (hasOre) {
			oreProgress = data.getInteger("oreProgress");
			oreX = data.getInteger("oreX");
			oreY = data.getInteger("oreY");
			oreZ = data.getInteger("oreZ");
		} else {
			oreProgress = -1;
			oreX = 0;
			oreY = 0;
			oreZ = 0;
		}
		hasCoolant = data.getBoolean("hasCoolant");
		if (hasCoolant) {
			coolantDamage = data.getInteger("coolantDamage");
			coolantX = data.getInteger("coolantX");
			coolantY = data.getInteger("coolantY");
			coolantZ = data.getInteger("coolantZ");
		} else {
			coolantDamage = -1;
			coolantX = 0;
			coolantY = 0;
			coolantZ = 0;
		}
		heat = data.getInteger("heat");
		maxNeeded = data.getInteger("maxNeeded");
		if (data.getCompoundTag("source") != null) {
			NBTTagCompound item = data.getCompoundTag("source");
			if (ItemStack.loadItemStackFromNBT(item) != null) {
				source = ItemStack.loadItemStackFromNBT(item).getItem();
			}
		} else {
			source = null;
		}
	}
}
