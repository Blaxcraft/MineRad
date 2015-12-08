package us.mcsw.minerad.tiles;

import java.util.HashMap;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.minerad.init.MicrowaveRecipes;
import us.mcsw.minerad.init.MicrowaveRecipes.MicrowaveRecipe;
import us.mcsw.minerad.ref.CapacitorReference;
import us.mcsw.minerad.util.LogUtil;

public class TileMicrowave extends TileEntity implements IEnergyReceiver {

	EnergyStorage storage = new EnergyStorage(CapacitorReference.CAPACITY_GOLD, CapacitorReference.MAX_TRANSFER_GOLD);

	private static final int USAGE_PER_TICK = 100, RADIUS = 5;

	double vertTick = 0, horizTick = 0;
	int particleTick = 0;

	@Override
	public void updateEntity() {
		if (isRunning()) {
			storage.extractEnergy(USAGE_PER_TICK, false);
			cookNearbyItems();
			if (worldObj.isRemote && ++particleTick >= 3) {
				if ((vertTick += Math.PI / 10) > (Math.PI * 2)) {
					vertTick = 0;
				}
				if ((horizTick += Math.PI / 20) > (Math.PI / 4)) {
					horizTick = 0;
				}
				double r = 0.15;
				for (double a = horizTick; a < Math.PI * 2 + horizTick; a += Math.PI / 10) {
					double vx = r * Math.sin(a);
					double vy = 0.04 * Math.sin(vertTick);
					double vz = r * Math.cos(a);
					worldObj.spawnParticle("flame", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, vx, vy, vz);
				}
				particleTick = 0;
			}
			this.markDirty();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public boolean isRunning() {
		return storage.extractEnergy(USAGE_PER_TICK, true) >= USAGE_PER_TICK
				&& worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	public HashMap<Integer, Integer> itemProgress = new HashMap<Integer, Integer>();

	private void cookNearbyItems() {
		AxisAlignedBB range = AxisAlignedBB.getBoundingBox(xCoord - RADIUS, yCoord - RADIUS, zCoord - RADIUS,
				xCoord + RADIUS, yCoord + RADIUS, zCoord + RADIUS);
		for (Object o : worldObj.getEntitiesWithinAABB(EntityItem.class, range)) {
			EntityItem i = (EntityItem) o;
			if (MicrowaveRecipes.getRecipeFor(i.getEntityItem()) != null) {
				if (!worldObj.isRemote) {
					incrItemProgress(i);
				} else
					worldObj.spawnParticle("smoke", i.posX, i.posY, i.posZ, (Math.random() - 0.5) / 10, 0.01,
							(Math.random() - 0.5) / 10);
			}
		}
	}

	private void incrItemProgress(EntityItem i) {
		int prog = 0;
		if (itemProgress.containsKey(i.getEntityId())) {
			prog = itemProgress.get(i.getEntityId());
		}
		itemProgress.remove(i.getEntityId());
		if (i.age > 1000) {
			i.age = 0;
		}
		prog++;
		MicrowaveRecipe recipe = MicrowaveRecipes.getRecipeFor(i.getEntityItem());
		if (prog > recipe.getTicks()) {
			EntityItem r = new EntityItem(i.worldObj);
			r.setPosition(i.posX, i.posY, i.posZ);
			r.setEntityItemStack(recipe.getProduct());
			worldObj.spawnEntityInWorld(r);

			i.getEntityItem().stackSize -= recipe.getSource().stackSize;
			return;
		}
		itemProgress.put(i.getEntityId(), prog);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound sync = new NBTTagCompound();
		storage.writeToNBT(sync);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, sync);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		storage.readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		storage.writeToNBT(data);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		storage.readFromNBT(data);
	}

}
