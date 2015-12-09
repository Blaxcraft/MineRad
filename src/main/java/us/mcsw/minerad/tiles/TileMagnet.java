package us.mcsw.minerad.tiles;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.util.ItemUtil;

public class TileMagnet extends TileEntity {

	int radius = 12;

	public boolean isRunning() {
		return !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity() {
		if (isRunning()) {
			double cx = xCoord + 0.5, cy = yCoord + 0.5, cz = zCoord + 0.5;

			AxisAlignedBB range = AxisAlignedBB.getBoundingBox(cx - radius, cy - radius, cz - radius, cx + radius,
					cy + radius, cz + radius);
			for (Object o : worldObj.getEntitiesWithinAABB(EntityItem.class, range)) {
				EntityItem i = (EntityItem) o;
				if (!ItemUtil.pushToNearbyInventories(this, i.getEntityItem(), true, pushDirs)) {
					continue;
				}
				if (!worldObj.isRemote) {
					if (i.getDistanceSq(cx, cy, cz) < 3) {
						ItemUtil.pushToNearbyInventories(this, i.getEntityItem(), false, pushDirs);
						continue;
					}
				}
				Vec3 mot = Vec3.createVectorHelper(cx - i.posX, cy - i.posY, cz - i.posZ);
				mot = mot.normalize();
				i.motionX += mot.xCoord * 0.1;
				i.motionY += mot.yCoord * 0.3;
				i.motionZ += mot.zCoord * 0.1;
			}
		}
	}

	private static final ForgeDirection[] pushDirs = { ForgeDirection.UP, ForgeDirection.DOWN, ForgeDirection.NORTH,
			ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST };

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		data.setInteger("radius", radius);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		radius = data.getInteger("radius");
	}

}
