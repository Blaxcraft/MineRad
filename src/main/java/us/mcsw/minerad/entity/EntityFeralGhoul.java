package us.mcsw.minerad.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.ref.PlayerProperties;
import us.mcsw.minerad.ref.RadProperties;
import us.mcsw.minerad.tiles.TileGhoulLight;

public class EntityFeralGhoul extends EntityZombie {

	public EntityFeralGhoul(World w) {
		super(w);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		setGlowing(rand.nextInt(ConfigMR.GLOWING_GHOUL_SPAWN_CHANCE) == 0);
		setHealth(getMaxHealth());
		return super.onSpawnWithEgg(data);
	}

	@Override
	public void onUpdate() {
		if (isGlowing()) {
			if (worldObj.isRemote) {
				worldObj.spawnParticle("flame", posX + rand.nextDouble() - 0.5, posY + rand.nextDouble() + 0.5,
						posZ + rand.nextDouble() - 0.5, 0, 0, 0);
			}
			int x = (int) Math.floor(posX), y = (int) Math.floor(posY), z = (int) Math.floor(posZ);
			if (worldObj.isAirBlock(x, y, z) && worldObj.getBlock(x, y, z) != ModBlocks.ghoulLight) {
				worldObj.setBlock(x, y, z, ModBlocks.ghoulLight, 8, 3);
				TileGhoulLight tile = (TileGhoulLight) worldObj.getTileEntity(x, y, z);
				if (tile != null)
					tile.setBound(this);
			}
		}
		super.onUpdate();
	}

	@Override
	public boolean attackEntityAsMob(Entity e) {
		boolean ret = super.attackEntityAsMob(e);
		if (ret && RadProperties.get(e) != null) {
			if (e instanceof EntityPlayer && PlayerProperties.get((EntityPlayer) e).isGhoul())
				return ret;
			RadProperties.get(e).addRadiation(isGlowing() ? 75 : 10, false);
		}
		return ret;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		setGhoulAttribs();
	}

	public void setGhoulAttribs() {
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(isGlowing() ? 40 : 20);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(isGlowing() ? 0.4 : 0.35);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(isGlowing() ? 6.0D : 4.0D);
	}

	public void setGlowing(boolean bool) {
		this.getDataWatcher().updateObject(12, Byte.valueOf((byte) (bool ? 1 : 0)));
		setGhoulAttribs();
	}

	public boolean isGlowing() {
		return this.getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	@Override
	public boolean isChild() {
		return false;
	}

	@Override
	public void setChild(boolean p_82227_1_) {
	}

	@Override
	public boolean interact(EntityPlayer pl) {
		if (pl.getCurrentEquippedItem() != null) {
			ItemStack it = pl.getCurrentEquippedItem();
			if (it.getItem().equals(ModItems.ghoulCure)) {
				it.stackSize--;

				if (!worldObj.isRemote) {
					EntityVillager vil = new EntityVillager(worldObj);
					vil.setPosition(posX, posY, posZ);
					worldObj.spawnEntityInWorld(vil);
					setDead();
				}
			}
		}
		return false;
	}

	@Override
	public boolean isVillager() {
		return false;
	}

	@Override
	public void setVillager(boolean p_82229_1_) {
	}

	@Override
	public boolean isConverting() {
		return false;
	}

	@Override
	protected Item getDropItem() {
		return isGlowing() ? ModItems.glowingFlesh : ModItems.irradiatedFlesh;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound data) {
		super.writeEntityToNBT(data);
		data.setBoolean("isGlowing", isGlowing());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound data) {
		super.readEntityFromNBT(data);
		setGlowing(data.getBoolean("isGlowing"));
	}

}
