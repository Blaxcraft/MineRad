package us.mcsw.minerad.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.util.ChatUtil;
import us.mcsw.minerad.ConfigMR;

public class EntityThrownMarker extends EntityThrowable {

	public EntityThrownMarker(World w) {
		super(w);
	}

	public EntityThrownMarker(World w, EntityLivingBase elb) {
		super(w, elb);
	}

	public EntityThrownMarker(World w, double x, double y, double z) {
		super(w, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (!worldObj.isRemote) {
			EntityStationaryMarker mark = new EntityStationaryMarker(worldObj);
			ForgeDirection hit = ForgeDirection.getOrientation(mop.sideHit);
			mop.blockX += hit.offsetX;
			mop.blockY += hit.offsetY;
			mop.blockZ += hit.offsetZ;
			mark.setPosition(mop.blockX + 0.5, mop.blockY + 0.1, mop.blockZ + 0.5);
			if (this.getThrower() instanceof EntityPlayer) {
				mark.thrower = (EntityPlayer) this.getThrower();
			}
			worldObj.spawnEntityInWorld(mark);
			notifyNearbyPlayers();
			this.setDead();
		}
	}

	public void notifyNearbyPlayers() {
		for (Object o : worldObj.playerEntities) {
			if (o instanceof EntityPlayer) {
				EntityPlayer pl = (EntityPlayer) o;
				if (pl.getDistanceSqToEntity(this) < ConfigMR.NUKE_PURGE_RADIUS * ConfigMR.NUKE_PURGE_RADIUS) {
					ChatUtil.sendTranslatedTo(pl, "message.nukeMarker.warning.nearby");
				}
			}
		}
	}

}
