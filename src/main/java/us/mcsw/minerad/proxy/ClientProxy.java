package us.mcsw.minerad.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import us.mcsw.minerad.entity.EntityArcThrowerProjectile;
import us.mcsw.minerad.entity.EntityStationaryMarker;
import us.mcsw.minerad.entity.EntityThrownMarker;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.render.RendererEnergyStorage;
import us.mcsw.minerad.render.RendererPipe;
import us.mcsw.minerad.render.RendererRadioAntenna;
import us.mcsw.minerad.tiles.TileEnergyStorage;
import us.mcsw.minerad.tiles.TilePipe;
import us.mcsw.minerad.tiles.TileRadioAntenna;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);

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

		ClientRegistry.bindTileEntitySpecialRenderer(TilePipe.class, new RendererPipe());
		ClientRegistry.bindTileEntitySpecialRenderer(TileRadioAntenna.class, new RendererRadioAntenna());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyStorage.class, new RendererEnergyStorage());

		RenderingRegistry.registerBlockHandler(new RendererEnergyStorage());
		RenderingRegistry.registerBlockHandler(new RendererRadioAntenna());
		RenderingRegistry.registerBlockHandler(new RendererPipe());
	}

}
