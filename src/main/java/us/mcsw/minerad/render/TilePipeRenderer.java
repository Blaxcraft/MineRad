package us.mcsw.minerad.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TilePipe;

public class TilePipeRenderer extends TileEntitySpecialRenderer {

	public ModelPipe model;

	public TilePipeRenderer() {
		this.model = new ModelPipe();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f, (float) z + 0.5f);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureReference.PIPE_TEXTURE);
		GL11.glPushMatrix();
		GL11.glRotatef(180, 0.0f, 0.0f, 1.0f);
		if (te instanceof TilePipe) {
			TilePipe tp = (TilePipe) te;
			if (tp.getTier() != null) {
				int clr = tp.getTier().getColour();
				GL11.glColor3f((float) (clr >> 16 & 255) / 255.0f, (float) (clr >> 8 & 255) / 255.0f,
						(float) (clr & 255) / 255.0f);
			}
			model.render(null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f, tp);
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
