package us.mcsw.minerad.proxy;

import java.awt.Color;
import java.util.Random;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import us.mcsw.minerad.effects.EffectBossShield;
import us.mcsw.minerad.effects.EffectShield;
import us.mcsw.minerad.entity.EntityArcThrowerProjectile;
import us.mcsw.minerad.entity.EntityFeralGhoul;
import us.mcsw.minerad.entity.EntityFinalBoss;
import us.mcsw.minerad.entity.EntityHomingBoss;
import us.mcsw.minerad.entity.EntitySpawningProjectile;
import us.mcsw.minerad.entity.EntityFinalBoss.BossState;
import us.mcsw.minerad.entity.EntityStationaryMarker;
import us.mcsw.minerad.entity.EntityThrownMarker;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.render.RenderFinalBoss;
import us.mcsw.minerad.render.RenderGhoul;
import us.mcsw.minerad.render.RendererEnergyStorage;
import us.mcsw.minerad.render.RendererPipe;
import us.mcsw.minerad.render.RendererRadioAntenna;
import us.mcsw.minerad.tiles.TileEnergyStorage;
import us.mcsw.minerad.tiles.TilePipe;
import us.mcsw.minerad.tiles.TileRadioAntenna;

public class ClientProxy extends CommonProxy {

	public static RenderEntity renderInvisible = new RenderEntity() {
		@Override
		public void doRender(Entity w, double x, double y, double z, float f1, float f2) {
		};
	};

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);

		RenderingRegistry.registerEntityRenderingHandler(EntityThrownMarker.class,
				new RenderSnowball(ModItems.nukeMarker));
		RenderingRegistry.registerEntityRenderingHandler(EntityStationaryMarker.class,
				new RenderSnowball(ModItems.nukeMarker));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcThrowerProjectile.class, renderInvisible);
		RenderingRegistry.registerEntityRenderingHandler(EntityHomingBoss.class, renderInvisible);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpawningProjectile.class, renderInvisible);
		RenderingRegistry.registerEntityRenderingHandler(EntityFeralGhoul.class, new RenderGhoul());
		RenderingRegistry.registerEntityRenderingHandler(EntityFinalBoss.class, new RenderFinalBoss());

		ClientRegistry.bindTileEntitySpecialRenderer(TilePipe.class, new RendererPipe());
		ClientRegistry.bindTileEntitySpecialRenderer(TileRadioAntenna.class, new RendererRadioAntenna());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyStorage.class, new RendererEnergyStorage());

		RenderingRegistry.registerBlockHandler(new RendererEnergyStorage());
		RenderingRegistry.registerBlockHandler(new RendererRadioAntenna());
		RenderingRegistry.registerBlockHandler(new RendererPipe());
	}

	Random rand = new Random();

	@Override
	public void generateShieldParticles(World w, double cx, double cy, double cz, double radius, int amount,
			Color colour) {
		for (int i = 0; i < amount; i++) {
			double rLong = rand.nextDouble() * Math.PI * 2;
			double rLat = (rand.nextGaussian() / 3) * (Math.PI / 2);

			Color c;
			if (colour != null) {
				c = colour;
			} else {
				float hue = 0.7f, sat = 0.7f + (rand.nextFloat() * 0.2f), val = 0.5f;
				int ci = Color.HSBtoRGB(hue, sat, val);
				c = new Color(ci);
			}

			EffectShield fx = new EffectShield(w, cx, cy, cz, radius, rLat, rLong, c);

			if (getDistSqToPlayer(fx.posX, fx.posY, fx.posZ) < 20 * 20) {
				Minecraft.getMinecraft().effectRenderer.addEffect(fx);
			}
		}
	}

	@Override
	public void generateBossParticles(EntityFinalBoss boss) {
		Color c = new Color(0, 0, 255);
		if (boss.getState() == BossState.CHARGING) {
			int amt = boss.getCharge() / 10;
			double rad = ((double) boss.getCharge() / 50.0) + 1.0;

			for (int i = 0; i < amt; i++) {
				double rLong = rand.nextDouble() * Math.PI * 2;
				double rLat = (rand.nextGaussian() / 3) * (Math.PI / 2);

				EffectBossShield fx = new EffectBossShield(boss.worldObj, boss.posX,
						boss.posY + boss.getEyeHeight() / 2, boss.posZ, rad, rLat, rLong, c);
				if (getDistSqToPlayer(fx.posX, fx.posY, fx.posZ) < 20 * 20) {
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				}
			}
		}

		if (boss.getBarrierRadius() > -1) {
			generateShieldParticles(boss.worldObj, boss.posX, boss.posY + 1, boss.posZ, boss.getBarrierRadius(), 40,
					new Color(255, 0, 0));
		}

		if (boss.getShieldRadius() > -1) {
			generateShieldParticles(boss.worldObj, boss.posX, boss.posY + 1, boss.posZ, boss.getShieldRadius(), 4,
					new Color(0, 0, 255));
		}
	}

	public double getDistSqToPlayer(double x, double y, double z) {
		EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
		if (pl == null)
			return 0;
		double px = pl.posX, py = pl.posY, pz = pl.posZ;
		double distSq = (x - px) * (x - px) + (y - py) * (y - py) + (z - pz) * (z - pz);
		return distSq;
	}

}
