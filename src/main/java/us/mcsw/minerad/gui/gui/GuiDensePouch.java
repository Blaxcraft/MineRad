package us.mcsw.minerad.gui.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import us.mcsw.core.GuiMR;
import us.mcsw.minerad.gui.ItemInventory;
import us.mcsw.minerad.gui.container.ContainerDensePouch;

public class GuiDensePouch extends GuiMR {

	public static final ResourceLocation chestBasic = new ResourceLocation("textures/gui/container/generic_54.png");

	InventoryPlayer ip;
	ItemInventory inv;
	private int inventoryRows;

	public GuiDensePouch(InventoryPlayer ip, ItemInventory inv) {
		super(new ContainerDensePouch(ip, inv), chestBasic);
		this.ip = ip;
		this.inv = inv;

		short short1 = 222;
		int i = short1 - 108;
		this.inventoryRows = inv.getSizeInventory() / 9;
		this.ySize = i + this.inventoryRows * 18;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(chestBasic);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
		this.drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(I18n.format(inv.getInventoryName()), 8, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format(ip.getInventoryName()), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();

		ItemInventory inv = (ItemInventory) this.inv;
		ItemInventory.setInItem(ip.player.getCurrentEquippedItem(), inv);
	}

}
