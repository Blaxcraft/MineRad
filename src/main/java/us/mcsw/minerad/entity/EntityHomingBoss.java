package us.mcsw.minerad.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ConfigMR;

public class EntityHomingBoss extends EntityThrowable {

	public EntityHomingBoss(World w) {
		super(w);
	}

	public EntityHomingBoss(World w, EntityLivingBase elb) {
		super(w, elb);
	}

	public EntityHomingBoss(World w, double x, double y, double z) {
		super(w, x, y, z);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public void onUpdate() {
		EntityPlayer targ = getTarget();
		if (targ != null) {
			double tx = targ.posX, ty = targ.posY, tz = targ.posZ;

			Vec3 vec = Vec3.createVectorHelper(tx - posX, ty - posY, tz - posZ).normalize();
			vec.xCoord /= 2;
			vec.yCoord /= 2;
			vec.zCoord /= 2;

			setVelocity(vec.xCoord, vec.yCoord, vec.zCoord);

			if (getTarget().getDistanceSqToEntity(this) < 2 * 2) {
				getTarget().attackEntityFrom(DamageSource.causeIndirectMagicDamage(getThrower(), this),
						getTarget().getMaxHealth() / 4);
				setDead();
			}
		}

		if (ticksExisted > 100)
			setDead();

		super.onUpdate();
	}

	@Override
	protected float getGravityVelocity() {
		return 0;
	}

	public EntityPlayer getTarget() {
		return worldObj.getClosestPlayerToEntity(this, 50);
	}

	@Override
	public void onEntityUpdate() {
		if (worldObj.isRemote) {
			for (int i = 0; i < 5; i++) {
				worldObj.spawnParticle("fireworksSpark", posX, posY, posZ, (Math.random() - 0.5) / 10,
						(Math.random() - 0.5) / 10, (Math.random() - 0.5) / 10);
			}
		}

		super.onEntityUpdate();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound data) {
		super.readEntityFromNBT(data);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound data) {
		super.writeEntityToNBT(data);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop.typeOfHit == MovingObjectType.BLOCK
				&& worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).isOpaqueCube()) {
			setDead();
		}
	}
}
