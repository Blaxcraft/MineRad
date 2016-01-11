package us.mcsw.minerad.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntitySpawningProjectile extends EntityThrowable {

	boolean isExplosive = false;

	public EntitySpawningProjectile(World w) {
		super(w);
	}

	public EntitySpawningProjectile(World w, EntityLivingBase elb) {
		super(w, elb);

		EntityPlayer targ = getTarget();

		if (targ != null) {
			double tx = targ.posX, tz = targ.posZ;

			Vec3 vec = Vec3.createVectorHelper(tx - posX, 0, tz - posZ).normalize();
			double vel = rand.nextDouble() * 2;
			vec.xCoord *= vel + (rand.nextGaussian() / 15);
			vec.yCoord = 1.5;
			vec.zCoord *= vel + (rand.nextGaussian() / 15);

			setVelocity(vec.xCoord, vec.yCoord, vec.zCoord);
		} else {
			motionX = rand.nextGaussian() * 0.75;
			motionY = 1.5;
			motionZ = rand.nextGaussian() * 0.75;
		}
	}

	public EntitySpawningProjectile(World w, double x, double y, double z) {
		super(w, x, y, z);
	}

	@Override
	protected void entityInit() {

	}

	public EntityPlayer getTarget() {
		return worldObj.getClosestPlayerToEntity(this, 50);
	}

	@Override
	public void onUpdate() {

		if (ticksExisted > 200)
			setDead();

		super.onUpdate();
	}

	@Override
	protected float getGravityVelocity() {
		return 0.2f;
	}

	@Override
	public void onEntityUpdate() {
		if (worldObj.isRemote) {
			for (int i = 0; i < 5; i++) {
				worldObj.spawnParticle("explode", posX, posY, posZ, (Math.random() - 0.5) / 10,
						(Math.random() - 0.5) / 10, (Math.random() - 0.5) / 10);
			}
		}

		super.onEntityUpdate();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound data) {
		super.readEntityFromNBT(data);

		isExplosive = data.getBoolean("isExplosive");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound data) {
		super.writeEntityToNBT(data);

		data.setBoolean("isExplosive", isExplosive);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop.typeOfHit == MovingObjectType.BLOCK
				&& worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).isCollidable()) {
			if (!worldObj.isRemote) {
				if (isExplosive) {
					worldObj.createExplosion(this, posX, posY, posZ, 4.0f, true);
				} else {
					EntityFeralGhoul spawn = new EntityFeralGhoul(worldObj);
					spawn.setPosition(posX, posY, posZ);
					spawn.setGlowing(rand.nextBoolean());
					spawn.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
							.setBaseValue(spawn.isGlowing() ? 0.5 : 0.45);
					spawn.getEntityAttribute(SharedMonsterAttributes.attackDamage)
							.setBaseValue(spawn.isGlowing() ? 10 : 8);
					spawn.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.6);
					spawn.getEntityAttribute(SharedMonsterAttributes.maxHealth)
							.setBaseValue(spawn.isGlowing() ? 50 : 30);
					spawn.setCurrentItemOrArmor(4, new ItemStack(Items.diamond_helmet));
					spawn.setEquipmentDropChance(4, 0);
					spawn.setHealth(spawn.getMaxHealth());
					worldObj.spawnEntityInWorld(spawn);
				}
			}
			setDead();
		}
	}

}
