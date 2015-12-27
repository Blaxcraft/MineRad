package us.mcsw.minerad;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.core.util.NumbersUtil;
import us.mcsw.core.util.RenderUtil;
import us.mcsw.minerad.init.AchievementsInit;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.items.ItemXray;
import us.mcsw.minerad.ref.PlayerProperties;
import us.mcsw.minerad.ref.RadEffectsHandler;
import us.mcsw.minerad.ref.RadProperties;
import us.mcsw.minerad.util.RadUtil;
import us.mcsw.minerad.util.RadUtil.RadEmitter;

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
	public void onWorldRenderEnd(RenderWorldLastEvent event) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);

		EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
		World w = Minecraft.getMinecraft().theWorld;
		boolean canSeeEmitters = false;
		
		for (ItemStack it : pl.inventory.mainInventory) {
			if (it != null && it.getItem() instanceof ItemXray) {
				ItemXray item = (ItemXray) it.getItem();
				if (item.isEnabled(it)) {
					canSeeEmitters = true;
				}
			}
		}
		
		if (canSeeEmitters) {
			int colour = nextColour();
			for (RadEmitter rad : RadUtil.getEmitters(w)) {
				ChunkCoordinates coords = new ChunkCoordinates(rad.x, rad.y, rad.z);
				if (coords.getDistanceSquared((int) pl.posX, (int) pl.posY, (int) pl.posZ) < 100 * 100) {
					RenderUtil.renderBlockFaces(coords, colour, 0.50f);
				}
			}
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	float hueLast = 0f;

	public int nextColour() {
		hueLast += 0.01f;
		if (hueLast > 1)
			hueLast = 0;
		return Color.HSBtoRGB(hueLast, 0.5f, 0.5f);
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

}
