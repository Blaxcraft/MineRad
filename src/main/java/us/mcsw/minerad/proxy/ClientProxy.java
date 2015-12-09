package us.mcsw.minerad.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.minerad.entity.EntityArcThrowerProjectile;
import us.mcsw.minerad.entity.EntityStationaryMarker;
import us.mcsw.minerad.entity.EntityThrownMarker;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.render.TilePipeRenderer;
import us.mcsw.minerad.render.TileRadioTowerAntennaRenderer;
import us.mcsw.minerad.tiles.TilePipe;
import us.mcsw.minerad.tiles.TileRadioTowerAntenna;

public class ClientProxy extends CommonProxy {

	public void init() {
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownMarker.class,
				new RenderSnowball(ModItems.nukeMarker));
		RenderingRegistry.registerEntityRenderingHandler(EntityStationaryMarker.class,
				new RenderSnowball(ModItems.nukeMarker));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcThrowerProjectile.class, new RenderEntity() {
			@Override
			public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
					float p_76986_8_, float p_76986_9_) {
			}
		});

		ClientRegistry.bindTileEntitySpecialRenderer(TileRadioTowerAntenna.class, new TileRadioTowerAntennaRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePipe.class, new TilePipeRenderer());
	}

}
