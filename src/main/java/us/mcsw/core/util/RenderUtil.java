package us.mcsw.core.util;

import java.awt.Color;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class RenderUtil {

	public static void renderBlockFaces(ChunkCoordinates pos, int colourInt, float transparency) {
		World world = Minecraft.getMinecraft().theWorld;
		Block block = world.getBlock(pos.posX, pos.posY, pos.posZ);

		GL11.glPushMatrix();
		GL11.glTranslated(pos.posX - RenderManager.renderPosX, pos.posY - RenderManager.renderPosY,
				pos.posZ - RenderManager.renderPosZ);
		Color colour = new Color(colourInt);
		GL11.glColor4ub((byte) colour.getRed(), (byte) colour.getGreen(), (byte) colour.getBlue(),
				(byte) (transparency * 255.0f));

		if (block != null) {
			AxisAlignedBB bounds = block.getSelectedBoundingBoxFromPool(world, pos.posX, pos.posY, pos.posZ);

			if (bounds != null) {
				GL11.glScalef(1, 1, 1);

				bounds.minX -= pos.posX;
				bounds.minY -= pos.posY;
				bounds.minZ -= pos.posZ;
				bounds.maxX -= pos.posX;
				bounds.maxY -= pos.posY;
				bounds.maxZ -= pos.posZ;

				Render.renderAABB(bounds);
			}
		}

		GL11.glPopMatrix();
	}

}
