package us.mcsw.minerad.entity;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.ModItems;

public class EntityFinalBoss extends EntityMob implements IBossDisplayData {

	public EntityFinalBoss(World w) {
		super(w);

		setHealth(getMaxHealth());
		this.isImmuneToFire = true;

		getNavigator().setCanSwim(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false, false));

		experienceValue = 100;
	}

	static int CHARGE = 15, STATE = 14, CURATTACK = 16;;

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(STATE, 0);
		dataWatcher.addObject(CHARGE, 0);
		dataWatcher.addObject(CURATTACK, 0);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float dmg) {
		if (getState() == BossState.CHARGING || getShieldRadius() > -1 || source.isExplosion())
			return false;
		return super.attackEntityFrom(source, dmg);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(10);
		setState(getState());
	}

	public BossAttack getCurrentAttack() {
		return BossAttack.values()[dataWatcher.getWatchableObjectInt(CURATTACK)];
	}

	public void setCurrentAttack(BossAttack attk) {
		dataWatcher.updateObject(CURATTACK, attk.ordinal());
	}

	public int getCharge() {
		return dataWatcher.getWatchableObjectInt(CHARGE);
	}

	public void setCharge(int set) {
		dataWatcher.updateObject(CHARGE, set);
	}

	public BossState getState() {
		return BossState.values()[dataWatcher.getWatchableObjectInt(STATE)];
	}

	public void setState(BossState state) {
		dataWatcher.updateObject(STATE, state.ordinal());

		if (state == BossState.CHARGING) {
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.00001);
		} else {
			if (getState() == BossState.ATTACKING) {
				setCharge(0);
				setCurrentAttack(BossAttack.getRandom(this, rand));
			}
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.075);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound data) {
		super.writeEntityToNBT(data);

		data.setInteger("state", getState().ordinal());

		data.setInteger("charge", getCharge());

		data.setInteger("curattack", getCurrentAttack() == null ? -1 : getCurrentAttack().ordinal());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound data) {
		super.readEntityFromNBT(data);

		setState(BossState.values()[data.getInteger("state")]);

		setCharge(data.getInteger("charge"));

		setCurrentAttack(data.getInteger("curattack") == -1 ? null : BossAttack.values()[data.getInteger("curattack")]);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (getState() == BossState.CHARGING) {
			setCharge(getCharge() + 1);
			if (getCharge() > 200) {
				setState(BossState.SHIELDING);
				worldObj.createExplosion(this, posX, posY, posZ, 5.0f, true);
			}
		} else {
			if (getState() == BossState.SHIELDING) {
				if (!worldObj.isRemote && rand.nextInt((int) (getHealth() * 100.0 / getMaxHealth()) + 30) == 0) {
					setState(BossState.ATTACKING);
				}
			}
			if (getState() == BossState.ATTACKING && getCurrentAttack() != null) {
				setCharge(getCharge() + 1);
				if (getCharge() > getCurrentAttack().tickDuration) {
					setState(BossState.SHIELDING);
				} else
					getCurrentAttack().doAttack(getCharge(), this, worldObj, worldObj.rand);
			}
		}

		MineRad.proxy.generateBossParticles(this);
		if (getBarrierRadius() > -1)
			doBarrier();

		if (getShieldRadius() > -1)
			doShield();

		if (getState() != BossState.CHARGING) {
			int height = 0;
			for (int y = (int) posY; y > 0; y--) {
				height++;
				if (!worldObj.isAirBlock((int) Math.floor(posX), y, (int) Math.floor(posZ))) {
					break;
				}
			}

			double targHeight = 8;
			this.motionY = Math.signum(targHeight - height) * 0.1;
		}
	}

	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
		EntityItem item = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(ModItems.exitite)) {
			public boolean isEntityInvulnerable() {
				return true;
			};
		};
		item.delayBeforeCanPickup = 10;
		if (captureDrops) {
			capturedDrops.add(item);
		} else
			worldObj.spawnEntityInWorld(item);
		super.dropFewItems(recentlyHit, looting);
	}

	@Override
	public void onDeath(DamageSource source) {
		for (int i = 0; i < 8; i++) {
			worldObj.createExplosion(this, posX + rand.nextGaussian() * 5, posY + rand.nextGaussian() * 5,
					posZ + rand.nextGaussian() * 5, 6.0f, true);
		}
		super.onDeath(source);
	}

	@Override
	protected void fall(float h) {
	}

	@Override
	protected void updateFallState(double dist, boolean onGround) {
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public int getTotalArmorValue() {
		return (int) (getMaxHealth() / (getHealth() + 30)) + 3;
	}

	@Override
	public void setInWeb() {
	}

	public int getBarrierRadius() {
		return getState() != BossState.CHARGING ? 25 : -1;
	}

	public int getShieldRadius() {
		return getState() == BossState.SHIELDING ? 4 : -1;
	}

	public void doBarrier() {
		int r = getBarrierRadius();
		AxisAlignedBB range = AxisAlignedBB.getBoundingBox(posX - r, posY - r, posZ - r, posX + r, posY + r, posZ + r);
		for (Object o : worldObj.getEntitiesWithinAABB(Entity.class, range)) {
			Entity e = (Entity) o;
			double distSq = e.getDistanceSq(posX, posY, posZ);
			if (e instanceof EntityPlayer) {
				EntityPlayer pl = (EntityPlayer) e;
				if (distSq <= (r + 1) * (r + 1)) {
					if (distSq >= (r - 2) * (r - 2)) {
						Vec3 vec = Vec3.createVectorHelper(posX - e.posX, posY - e.posY, posZ - e.posZ).normalize();
						double mult = 0.5;
						e.setVelocity(vec.xCoord * mult, vec.yCoord * mult, vec.zCoord * mult);
					}
					if (pl.capabilities.isFlying && !pl.capabilities.isCreativeMode) {
						pl.capabilities.isFlying = false;
					}
				}
			}
		}
	}

	public void doShield() {
		int r = getShieldRadius();
		AxisAlignedBB range = AxisAlignedBB.getBoundingBox(posX - r, posY - r, posZ - r, posX + r, posY + r, posZ + r);
		for (Object o : worldObj.getEntitiesWithinAABB(Entity.class, range)) {
			Entity e = (Entity) o;
			double distSq = e.getDistanceSq(posX, posY, posZ);
			if (o instanceof EntityPlayer || o instanceof IProjectile || o instanceof EntityThrowable
					|| o instanceof EntityMob) {
				if (distSq <= (r + 1) * (r + 1) && distSq >= (r - 2) * (r - 2)) {
					Vec3 vec = Vec3.createVectorHelper(e.posX - posX, e.posY - posY, e.posZ - posZ).normalize();
					double mult = 0.5;
					e.setVelocity(vec.xCoord * mult, vec.yCoord * mult, vec.zCoord * mult);
				}
			}
		}
	}

	public static enum BossState {
		CHARGING, ATTACKING, SHIELDING;
	}

	public static enum BossAttack {
		HOMING_BULLETS(30, 125, 0, 0.75) {
			@Override
			public void doAttack(int ticks, EntityFinalBoss boss, World w, Random rand) {
				if (ticks % 5 == 0 && !w.isRemote) {
					EntityHomingBoss proj = new EntityHomingBoss(w, boss);
					proj.setPosition(proj.posX + (rand.nextDouble() * 10) - 5, proj.posY + (rand.nextDouble() * 4) - 2,
							proj.posZ + (rand.nextDouble() * 10) - 5);
					w.spawnEntityInWorld(proj);
				}
			}
		},
		SPAWNING(40, 150, 0.25, 0.75) {
			@Override
			public void doAttack(int ticks, EntityFinalBoss boss, World w, Random rand) {
				if (ticks % 10 == 0 && !w.isRemote) {
					for (int i = 0; i < w.rand.nextInt(3) + 1; i++) {
						EntitySpawningProjectile proj = new EntitySpawningProjectile(w, boss);
						w.spawnEntityInWorld(proj);
					}
				}
			}
		},
		LAZER(30, 150, 0.1, 1) {
			@Override
			public void doAttack(int ticks, EntityFinalBoss boss, World w, Random rand) {
				if (ticks % 5 == 0) {
					Vec3 start = Vec3.createVectorHelper(boss.posX, boss.posY + 0.5, boss.posZ);
					AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(start.xCoord - 50, start.yCoord - 50,
							start.zCoord - 50, start.xCoord + 50, start.yCoord + 50, start.zCoord + 50);
					for (Object o : w.getEntitiesWithinAABB(EntityPlayer.class, bounds)) {
						EntityPlayer pl = (EntityPlayer) o;
						// For some reason, the server's position is normal, but
						// the client's position is 1.62 higher
						Vec3 end = Vec3.createVectorHelper(pl.posX, pl.posY + (w.isRemote ? 0 : 1.62), pl.posZ);
						Vec3 vec = start.subtract(end).normalize();
						vec.xCoord /= 2;
						vec.yCoord /= 2;
						vec.zCoord /= 2;

						double lastDistSq = -1;
						Vec3 pos = Vec3.createVectorHelper(start.xCoord, start.yCoord, start.zCoord);
						boolean hit = true;
						while (true) {
							pos = pos.addVector(vec.xCoord, vec.yCoord, vec.zCoord);
							if (w.getBlock((int) Math.floor(pos.xCoord), (int) Math.floor(pos.yCoord),
									(int) Math.floor(pos.zCoord)).isOpaqueCube()) {
								hit = false;
								break;
							}
							if (lastDistSq >= 0 && pos.squareDistanceTo(end) > lastDistSq) {
								break;
							}
							w.spawnParticle("witchMagic", pos.xCoord, pos.yCoord, pos.zCoord, 0, 0, 0);
							lastDistSq = pos.squareDistanceTo(end);
						}
						if (hit) {
							pl.attackEntityFrom(DamageSource.causeMobDamage(boss).setDamageBypassesArmor(),
									pl.getMaxHealth() / 4);
						}
					}
				}
			}
		},
		PURGE(40, 200, 0, 0.25) {
			@Override
			public void doAttack(int ticks, EntityFinalBoss boss, World w, Random rand) {
				if (ticks % 8 == 0 && !w.isRemote) {
					for (int i = 0; i < w.rand.nextInt(5) + 2; i++) {
						EntitySpawningProjectile proj = new EntitySpawningProjectile(w, boss);
						proj.isExplosive = true;
						w.spawnEntityInWorld(proj);
					}
				}
			}
		};

		public int tickDuration, weight;
		public double minPercentHealth, maxPercentHealth;

		private BossAttack(int tickDuration, int weight, double minPercentHealth, double maxPercentHealth) {
			this.tickDuration = tickDuration;
			this.weight = weight;
			this.minPercentHealth = minPercentHealth;
			this.maxPercentHealth = maxPercentHealth;
		}

		public void doAttack(int ticks, EntityFinalBoss boss, World w, Random rand) {
		}

		public boolean canBeUsed(EntityFinalBoss boss) {
			double percHealth = boss.getHealth() / boss.getMaxHealth();
			return percHealth <= maxPercentHealth && percHealth >= minPercentHealth;
		}

		public static BossAttack getRandom(EntityFinalBoss boss, Random rand) {
			int totalWeight = 0;
			for (BossAttack ba : values()) {
				if (ba.canBeUsed(boss))
					totalWeight += ba.weight;
			}
			int w = rand.nextInt(totalWeight);
			for (BossAttack ba : values()) {
				if (ba.canBeUsed(boss))
					if ((w -= ba.weight) < 0) {
						return ba;
					}
			}
			return null;
		}
	}

}
