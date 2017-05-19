package us.mcsw.minerad;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import us.mcsw.core.util.RenderUtil;
import us.mcsw.minerad.items.ItemXray;
import us.mcsw.minerad.util.RadUtil;
import us.mcsw.minerad.util.RadUtil.RadEmitter;

public class EventListenerClient {

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
	
	float satLast = 0.5f;
	float satDelta = 1f;

	public int nextColour() {
		satLast += 0.01f * satDelta;
		if (satLast > 0.75 || satLast < 0.25)
			satDelta *= -1;
		return Color.HSBtoRGB(0.333f, satLast, 0.5f);
	}
	
}
