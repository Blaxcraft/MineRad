package us.mcsw.minerad;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.world.ExplosionEvent;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.core.util.NumbersUtil;
import us.mcsw.core.util.RenderUtil;
import us.mcsw.minerad.init.AchievementsInit;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemHoverLegs;
import us.mcsw.minerad.items.ItemXray;
import us.mcsw.minerad.net.MessageUpdateProperties;
import us.mcsw.minerad.ref.PlayerProperties;
import us.mcsw.minerad.ref.RadEffectsHandler;
import us.mcsw.minerad.ref.RadProperties;
import us.mcsw.minerad.tiles.TileGhoulLight;
import us.mcsw.minerad.tiles.TileShieldGenerator;
import us.mcsw.minerad.util.RadUtil;
import us.mcsw.minerad.util.RadUtil.RadEmitter;

public class EventListener {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if ((event.entity instanceof EntityLiving || event.entity instanceof EntityPlayer
				|| event.entity instanceof EntityItem) && RadProperties.get(event.entity) == null) {
			RadProperties.register(event.entity);
		}
		if (event.entity instanceof EntityPlayer) {
			PlayerProperties.register((EntityPlayer) event.entity);
		}
	}

	public int tick = 0;
	public static final double MAX_RAD_TICK = 2.0;

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		if (event.phase == Phase.END) {
			if (++tick >= MAX_RAD_TICK) {
				for (Object o : event.world.loadedEntityList.toArray()) {
					if (o instanceof EntityVillager || o instanceof EntityPlayer || o instanceof EntityItem) {
						Entity e = (Entity) o;
						if (RadProperties.get(e) != null) {
							RadProperties props = RadProperties.get(e);

							RadEffectsHandler.onRad(e, props, 20.0 / MAX_RAD_TICK);
						}
					}
				}
				tick = 0;
			}
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP && !event.world.isRemote) {
			EntityPlayerMP pl = (EntityPlayerMP) event.entity;
			PlayerProperties props = PlayerProperties.get(pl);
			if (props != null) {
				MineRad.network.sendTo(
						new MessageUpdateProperties(props, PlayerProperties.PROPERTIES_NAME, pl.getEntityId()), pl);
			}
		}
	}

	@SubscribeEvent
	public void onMobSpawn(LivingSpawnEvent.CheckSpawn event) {
		if (event.entityLiving instanceof EntityMob) {
			for (Object o : event.world.loadedTileEntityList.toArray()) {
				if (o instanceof TileShieldGenerator) {
					TileShieldGenerator tile = (TileShieldGenerator) o;
					if (tile.isRunning() && tile.isInRange(event.x, event.y, event.z)) {
						event.setResult(Result.DENY);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onClone(Clone event) {
		NBTTagCompound temp = new NBTTagCompound();
		PlayerProperties.get(event.original).saveNBTData(temp);
		PlayerProperties.get(event.entityPlayer).loadNBTData(temp);
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer pl = event.player;
		PlayerProperties props = PlayerProperties.get(pl);
		if (props != null) {
			if (props.isGhoul()) {
				int x = (int) Math.floor(pl.posX), y = (int) Math.floor(pl.posY - pl.getEyeHeight() - 1),
						z = (int) Math.floor(pl.posZ);
				if (pl.worldObj.isAirBlock(x, y, z) && pl.worldObj.getBlock(x, y, z) != ModBlocks.ghoulLight) {
					pl.worldObj.setBlock(x, y, z, ModBlocks.ghoulLight, props.isGlowingGhoul() ? 15 : 7, 3);
					TileGhoulLight tile = (TileGhoulLight) pl.worldObj.getTileEntity(x, y, z);
					if (tile != null)
						tile.setBound(pl);
				}
			}
			if (props.wearingHoverLegs) {
				if (pl.getCurrentArmor(1) == null || pl.getCurrentArmor(1) != null
						&& !(pl.getCurrentArmor(1).getItem() instanceof ItemHoverLegs)) {
					props.wearingHoverLegs = false;
					if (pl.capabilities.allowFlying && !pl.capabilities.isCreativeMode) {
						pl.capabilities.allowFlying = false;
						if (pl.capabilities.isFlying)
							pl.capabilities.isFlying = false;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event) {
		Item i = event.crafting.getItem();
		EntityPlayer pl = event.player;
		if (i != null) {
			if (i.equals(ModItems.geigerCounter)) {
				pl.addStat(AchievementsInit.geigerCounter, 1);
			} else if (i.equals(ModItems.radResistantPlating)) {
				pl.addStat(AchievementsInit.radPlating, 1);
			} else if (Block.getBlockFromItem(i).equals(ModBlocks.microwave)) {
				pl.addStat(AchievementsInit.microwave, 1);
			} else if (Block.getBlockFromItem(i).equals(ModBlocks.radioTowerBase)) {
				pl.addStat(AchievementsInit.radioTower, 1);
			} else if (Block.getBlockFromItem(i).equals(ModBlocks.magnet)) {
				pl.addStat(AchievementsInit.magnet, 1);
			}
		}
	}

	@SubscribeEvent
	public void onSmelt(ItemSmeltedEvent event) {
		Item i = event.smelting.getItem();
		EntityPlayer pl = event.player;
		if (i != null) {
			if (i.equals(ModItems.purifiedUranium)) {
				pl.addStat(AchievementsInit.purifiedUranium, 1);
			}
		}
	}

	@SubscribeEvent
	public void onItemPickup(ItemPickupEvent event) {
		Item i = event.pickedUp.getEntityItem().getItem();
		EntityPlayer pl = event.player;
		if (i != null) {
			if (i.equals(ModItems.uraniumChunk)) {
				pl.addStat(AchievementsInit.uraniumChunk, 1);
			} else if (i.equals(ModItems.uraniumOreItem)) {
				pl.addStat(AchievementsInit.uraniumOre, 1);
			} else if (i.equals(ModItems.plutonium)) {
				pl.addStat(AchievementsInit.plutonium, 1);
			} else if (i.equals(ModItems.unknownElement)) {
				pl.addStat(AchievementsInit.unknownElement, 1);
			}
		}
	}

	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event) {
		ItemStack it = event.itemStack;
		if (it.hasTagCompound()) {
			if (ItemUtil.getInteger("FissionProgress", it) > 0 && ItemUtil.getInteger("FissionMaximum", it) > 0) {
				double percentDone = (double) ItemUtil.getInteger("FissionProgress", it) * 100.0
						/ (double) ItemUtil.getInteger("FissionMaximum", it);
				event.toolTip.add("Fission Progress: " + (NumbersUtil.roundDouble(percentDone, 1)) + "%");
			}
			if (ItemUtil.getInteger("FusionProgress", it) > 0 && ItemUtil.getInteger("FusionMaximum", it) > 0) {
				double percentDone = (double) ItemUtil.getInteger("FusionProgress", it) * 100.0
						/ (double) ItemUtil.getInteger("FusionMaximum", it);
				event.toolTip.add("Fusion Progress: " + (NumbersUtil.roundDouble(percentDone, 1)) + "%");
			}
		}
	}

	@SubscribeEvent
	public void onExplode(ExplosionEvent.Detonate event) {
		Explosion expl = event.explosion;
		for (Object o : event.world.loadedTileEntityList) {
			if (o instanceof TileShieldGenerator) {
				TileShieldGenerator tile = (TileShieldGenerator) o;
				if (tile.isRunning()) {
					for (Object p : expl.affectedBlockPositions.toArray()) {
						ChunkPosition pos = (ChunkPosition) p;
						if (tile.isInRange(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ)) {
							expl.affectedBlockPositions.remove(p);
						}
					}
				}
			}
		}
	}
}
