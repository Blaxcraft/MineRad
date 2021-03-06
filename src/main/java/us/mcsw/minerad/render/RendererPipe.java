package us.mcsw.minerad.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.minerad.ref.CapacitorTier;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TilePipe;

public class RendererPipe extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

	public ModelPipe model;
	static int renderId = -1;

	public RendererPipe() {
		this.model = new ModelPipe();
	}

	public static int getStaticRenderId() {
		if (renderId == -1)
			return renderId = RenderingRegistry.getNextAvailableRenderId();
		return renderId;
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

			model.render(null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f, tp);
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		TilePipe tp = new TilePipe() {
			@Override
			public boolean isConnected(ForgeDirection side) {
				return true;
			}
			
			@Override
			public boolean isConnectedToProvider(ForgeDirection side) {
				return false;
			}
			
			@Override
			public boolean isConnectedToReceiver(ForgeDirection side) {
				return false;
			}
		};
		tp.setTier(CapacitorTier.getFromId(metadata));
		TileEntityRendererDispatcher.instance.renderTileEntityAt(tp, 0, 0, 0, 0);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return getStaticRenderId();
	}

}
