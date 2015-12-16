package us.mcsw.minerad;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import us.mcsw.minerad.init.AchievementsInit;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.ref.PlayerProperties;
import us.mcsw.minerad.ref.RadEffectsHandler;
import us.mcsw.minerad.ref.RadProperties;

public class EventListener {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if ((event.entity instanceof EntityLiving || event.entity instanceof EntityPlayer)
				&& RadProperties.get(event.entity) == null) {
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
					if (o instanceof EntityVillager || o instanceof EntityPlayer) {
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
	public void onClone(Clone event) {
		NBTTagCompound temp = new NBTTagCompound();
		PlayerProperties.get(event.original).saveNBTData(temp);
		PlayerProperties.get(event.entityPlayer).loadNBTData(temp);
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
	
}
