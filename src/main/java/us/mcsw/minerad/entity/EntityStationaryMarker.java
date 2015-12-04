package us.mcsw.minerad.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import us.mcsw.minerad.init.AchievementsInit;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.ref.PlayerProperties;
import us.mcsw.minerad.util.ChatUtil;
import us.mcsw.minerad.util.RadUtil;

public class EntityStationaryMarker extends Entity {

	static final double SECONDS_UNTIL_DETONATION = 10;

	long explodeTime;
	public EntityPlayer thrower = null;

	public EntityStationaryMarker(World w) {
		super(w);
		this.explodeTime = w.getTotalWorldTime() + (int) (SECONDS_UNTIL_DETONATION * 20);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!worldObj.isRemote) {
			if (worldObj.getTotalWorldTime() >= explodeTime) {
				if (canCallInStrike()) {
					if (thrower != null && PlayerProperties.get(thrower) != null) {
						PlayerProperties props = PlayerProperties.get(thrower);
						if (props.nukes > 0) {
							props.nukes--;
							for (Object o : worldObj.loadedEntityList.toArray()) {
								if (o instanceof EntityLivingBase) {
									EntityLivingBase el = (EntityLivingBase) o;
									if (el.getDistanceSq(posX, posY, posZ) < 50 * 50) {
										el.attackEntityFrom(RadUtil.nukeDamage, el.getMaxHealth() * 10);
									}
								}
							}
							worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 25.0f, true);
							int rad = 20;
							for (int i = 0; i < 6; i++) {
								double x = this.posX + rand.nextInt(rad * 2) - rad;
								double z = this.posZ + rand.nextInt(rad * 2) - rad;
								worldObj.createExplosion(this, x, worldObj.getHeightValue((int) x, (int) z), z, 10.0f,
										true);
							}
							int rad2 = 1;
							for (int x = (int) this.posX - rad2; x <= (int) this.posX + rad2; x++) {
								for (int z = (int) this.posZ - rad2; z <= (int) this.posZ + rad2; z++) {
									if (worldObj.rand.nextBoolean()) {
										int y = worldObj.getHeightValue(x, z);
										worldObj.setBlock(x, y, z, ModBlocks.groundZero);
									}
								}
							}

							ChatUtil.sendTranslatedTo(thrower, "message.nukeMarker.success");
							thrower.addStat(AchievementsInit.nukeDrop, 1);
						} else {
							worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2f, false);
							ChatUtil.sendTranslatedTo(thrower, "message.nukeMarker.nonukes");
						}
					}
				} else {
					worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2f, false);
					ChatUtil.sendTranslatedTo(thrower, "message.nukeMarker.nosky");
				}
				this.setDead();
			}
		}
	}

	double tick = 0;
	int soundCount = 0;

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (worldObj.isRemote) {
			double cr = 0.1;
			tick += 0.2;
			worldObj.spawnParticle("smoke", posX, posY, posZ, cr * Math.sin(tick), 0.3, cr * Math.cos(tick));
			worldObj.spawnParticle("flame", posX, posY, posZ, cr * 0.1 * Math.cos(tick), 0.01,
					cr * 0.1 * Math.sin(tick));
		} else {
			if (--soundCount < 0) {
				worldObj.playSoundAtEntity(this, "note.pling", 5.0f, 0.5f);
				soundCount = 20;
			}
		}
	}

	public boolean canCallInStrike() {
		return worldObj.canBlockSeeTheSky((int) this.posX, (int) this.posY + 1, (int) this.posZ);
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		nbt.setLong("explodeTime", explodeTime);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		explodeTime = nbt.getLong("explodeTime");
	}

}
