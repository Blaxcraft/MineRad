package us.mcsw.minerad.tiles;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ConfigMR;

public class TileMagnet extends TileEntity {

	int radius = ConfigMR.BLACK_HOLE_BLOCK_RADIUS;

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
				if (i == null || i.isDead || i.getEntityItem() == null) continue;
				ItemStack it = ItemUtil.addItemToNearbyInventories(this, i.getEntityItem(), true, true, pushDirs);
				if (it != null) {
					if (it.stackSize < i.getEntityItem().stackSize) {
						EntityItem nw = new EntityItem(worldObj);
						nw.setPosition(i.posX, i.posY, i.posZ);
						nw.setEntityItemStack(i.getEntityItem().splitStack(it.stackSize));
						nw.delayBeforeCanPickup = 40;
					}
					continue;
				}
				if (!worldObj.isRemote) {
					if (i.getDistanceSq(cx, cy, cz) < 3) {
						ItemStack left = ItemUtil.addItemToNearbyInventories(this, i.getEntityItem(), true, false,
								pushDirs);
						if (left != null) {
							i.setEntityItemStack(left);
						} else
							worldObj.removeEntity(i);
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
