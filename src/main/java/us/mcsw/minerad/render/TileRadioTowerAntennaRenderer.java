package us.mcsw.minerad.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.minerad.ref.TextureReference;

public class TileRadioTowerAntennaRenderer extends TileEntitySpecialRenderer {

	private final ModelRadioTowerAntenna model;

	public TileRadioTowerAntennaRenderer() {
		model = new ModelRadioTowerAntenna();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5f, (float) z + 0.5f);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureReference.RADIO_TOWER_TEXTURE);
		GL11.glPushMatrix();
		GL11.glRotatef(180, 0.0f, 0.0f, 1.0f);
		model.render(null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
