package us.mcsw.minerad.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderSnowball;
import us.mcsw.minerad.entity.EntityStationaryMarker;
import us.mcsw.minerad.entity.EntityThrownMarker;
import us.mcsw.minerad.init.ModItems;

public class ClientProxy extends CommonProxy {

	public void init() {
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownMarker.class,
				new RenderSnowball(ModItems.nukeMarker));
		RenderingRegistry.registerEntityRenderingHandler(EntityStationaryMarker.class,
				new RenderSnowball(ModItems.nukeMarker));
	}

}
