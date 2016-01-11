package us.mcsw.minerad.tiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import us.mcsw.core.TileMRMachine;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.items.ItemShieldUpgrade;
import us.mcsw.minerad.ref.CapacitorTier;

public class TileShieldGenerator extends TileMRMachine {

	private static final double USAGE_PER_TICK_PER_RADIUS = 1.5;

	int radius_sync = -1;

	public TileShieldGenerator() {
		super(CapacitorTier.DIAMOND.getMachineCapacity(), CapacitorTier.DIAMOND.getMaxTransferMachine(), 1);
	}

	int particleTick = 0;

	@Override
	public void updateEntity() {
		if (isRunning()) {
			if (worldObj.isRemote) {
				double cx = xCoord + 0.5, cy = yCoord + 0.5, cz = zCoord + 0.5;
				if (particleTick++ > 2) {
					particleTick = 0;
					MineRad.proxy.generateShieldParticles(worldObj, cx, cy, cz, getRadius(),
							getRadius() * getRadius() / 3, null);
				}
			}
			int r = getRadius();
			AxisAlignedBB range = AxisAlignedBB.getBoundingBox(xCoord - r, yCoord - r, zCoord - r, xCoord + r,
					yCoord + r, zCoord + r);
			for (Object o : worldObj.getEntitiesWithinAABB(Entity.class, range)) {
				Entity e = (Entity) o;
				double distSq = e.getDistanceSq(xCoord, yCoord, zCoord);
				if (o instanceof IProjectile || o instanceof EntityThrowable || o instanceof EntityMob) {
					if (distSq <= (getRadius() + 1) * (getRadius() + 1)
							&& distSq >= (getRadius() - 2) * (getRadius() - 2)) {

						Vec3 vec = Vec3.createVectorHelper(e.posX - xCoord, e.posY - yCoord, e.posZ - zCoord)
								.normalize();
						double mult = 0.5;
						e.setVelocity(vec.xCoord * mult, vec.yCoord * mult, vec.zCoord * mult);
					}
				}
			}
			storage.extractEnergy(getUsagePerTick(), false);
		}
	}

	public boolean isRunning() {
		return !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)
				&& storage.getEnergyStored() >= getUsagePerTick();
	}

	public int getUsagePerTick() {
		return (int) (USAGE_PER_TICK_PER_RADIUS * getRadius());
	}

	public int getRadius() {
		if (worldObj.isRemote && radius_sync > -1)
			return radius_sync;
		int radBoost = 0;
		for (int i = 0; i < items.length; i++) {
			if (isItemValidForSlot(i, getStackInSlot(i))) {
				radBoost += getStackInSlot(i).stackSize;
			}
		}
		return radius_sync = (16 + radBoost);
	}

	public boolean isInRange(double x, double y, double z) {
		int rad = getRadius();
		return Vec3.createVectorHelper(x, y, z).squareDistanceTo(xCoord, yCoord, zCoord) < rad * rad;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canExtractItem(int side, ItemStack it, int slot) {
		return true;
	}

	@Override
	public String getInventoryName() {
		return MineRad.MODID + ":shieldGenerator";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack it) {
		return it != null && it.getItem() instanceof ItemShieldUpgrade;
	}

	@Override
	public void writeSyncable(NBTTagCompound data) {
		data.setInteger("radius_sync", radius_sync);
	}

	@Override
	public void readSyncable(NBTTagCompound data) {
		radius_sync = data.getInteger("radius_sync");
	}

}
