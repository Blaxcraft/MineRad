package us.mcsw.minerad.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import us.mcsw.minerad.ConfigMR;

public class EntityArcThrowerProjectile extends EntityThrowable {

	public EntityArcThrowerProjectile(World w) {
		super(w);
	}

	public EntityArcThrowerProjectile(World w, EntityLivingBase elb) {
		super(w, elb);
	}

	public EntityArcThrowerProjectile(World w, double x, double y, double z) {
		super(w, x, y, z);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public void onUpdate() {
		this.motionY += this.getGravityVelocity();

		if (this.ticksExisted >= 20) {
			this.setDead();
		}

		super.onUpdate();
	}

	@Override
	public void onEntityUpdate() {
		if (worldObj.isRemote) {
			for (int i = 0; i < 5; i++) {
				worldObj.spawnParticle("fireworksSpark", posX, posY, posZ, (Math.random() - 0.5) / 10,
						(Math.random() - 0.5) / 10, (Math.random() - 0.5) / 10);
				worldObj.spawnParticle("explode", posX, posY, posZ, (Math.random() - 0.5) / 10,
						(Math.random() - 0.5) / 10, (Math.random() - 0.5) / 10);
				worldObj.spawnParticle("instantSpell", posX, posY, posZ, (Math.random() - 0.5) / 10,
						(Math.random() - 0.5) / 10, (Math.random() - 0.5) / 10);
			}
		}

		super.onEntityUpdate();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound data) {

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound data) {

	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop.entityHit != null) {
			Entity ent = mop.entityHit;
			if (ent instanceof EntityLivingBase) {
				EntityLivingBase elb = (EntityLivingBase) ent;
				if (EntityList.entityEggs.containsKey(EntityList.getEntityID(elb))) {
					if (Math.random() < getCaptureChance(elb) / ConfigMR.ARC_THROWER_CHANCE_MULTIPLIER) {
						EntityItem drop = new EntityItem(worldObj, elb.posX, elb.posY, elb.posZ);
						drop.setEntityItemStack(new ItemStack(Items.spawn_egg, 1, EntityList.getEntityID(elb)));
						elb.worldObj.spawnEntityInWorld(drop);

						elb.setDead();
					} else {
						elb.attackEntityFrom(DamageSource.generic, elb.getMaxHealth() / 4);
					}
					this.setDead();
				}
			}
		}
	}

	public double getCaptureChance(EntityLivingBase elb) {
		return (elb.getMaxHealth() - elb.getHealth()) / elb.getMaxHealth() - 0.4;
	}

}
