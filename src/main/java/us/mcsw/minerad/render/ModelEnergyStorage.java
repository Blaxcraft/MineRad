// Date: 12/23/2015 6:55:43 PM
// Template version 1.1
// Java generated by Techne
package us.mcsw.minerad.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.minerad.ref.CapacitorTier;
import us.mcsw.minerad.tiles.TileEnergyStorage;

public class ModelEnergyStorage extends ModelBase {
	// fields
	ModelRenderer ShapeCenter;
	ModelRenderer ShapeTop;
	ModelRenderer ShapeBottom;
	ModelRenderer Shape4;
	ModelRenderer Shape2;
	ModelRenderer Shape1;
	ModelRenderer Shape3;

	public ModelEnergyStorage() {
		textureWidth = 64;
		textureHeight = 64;

		ShapeCenter = new ModelRenderer(this, 0, 0);
		ShapeCenter.addBox(-7F, 0F, -7F, 14, 14, 14);
		ShapeCenter.setRotationPoint(0F, 9F, 0F);
		ShapeCenter.setTextureSize(64, 64);
		ShapeCenter.mirror = true;
		setRotation(ShapeCenter, 0F, 0F, 0F);
		ShapeTop = new ModelRenderer(this, 0, 32);
		ShapeTop.addBox(-4F, 0F, -4F, 8, 1, 8);
		ShapeTop.setRotationPoint(0F, 8F, 0F);
		ShapeTop.setTextureSize(64, 64);
		ShapeTop.mirror = true;
		setRotation(ShapeTop, 0F, 0F, 0F);
		ShapeBottom = new ModelRenderer(this, 0, 32);
		ShapeBottom.addBox(-4F, 0F, -4F, 8, 1, 8);
		ShapeBottom.setRotationPoint(0F, 23F, 0F);
		ShapeBottom.setTextureSize(64, 64);
		ShapeBottom.mirror = true;
		setRotation(ShapeBottom, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 32);
		Shape4.addBox(-4F, 0F, -4F, 8, 1, 8);
		Shape4.setRotationPoint(0F, 16F, 8F);
		Shape4.setTextureSize(64, 64);
		Shape4.mirror = true;
		setRotation(Shape4, -1.570796F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 32);
		Shape2.addBox(-4F, 0F, -4F, 8, 1, 8);
		Shape2.setRotationPoint(0F, 16F, -7F);
		Shape2.setTextureSize(64, 64);
		Shape2.mirror = true;
		setRotation(Shape2, -1.570796F, 0F, 0F);
		Shape1 = new ModelRenderer(this, 0, 32);
		Shape1.addBox(-4F, 0F, -4F, 8, 1, 8);
		Shape1.setRotationPoint(8F, 16F, 0F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 1.570796F);
		Shape3 = new ModelRenderer(this, 0, 32);
		Shape3.addBox(-4F, 0F, -4F, 8, 1, 8);
		Shape3.setRotationPoint(-7F, 16F, 0F);
		Shape3.setTextureSize(64, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 1.570796F);
	}
	
	int colourSideInput = 0x6677bb, colourSideOutput=0x885533;

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5,
			TileEnergyStorage tile) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		CapacitorTier tier = tile.getTier();
		if (tier != null) {
			Color colour = new Color(tier.getColour());
			GL11.glColor3ub((byte) colour.getRed(), (byte) colour.getGreen(), (byte) colour.getBlue());
		}
		ShapeCenter.render(f5);
		
		setColourBasedOnState(ForgeDirection.UP, tile);
		ShapeTop.render(f5);
		setColourBasedOnState(ForgeDirection.DOWN, tile);
		ShapeBottom.render(f5);
		setColourBasedOnState(ForgeDirection.NORTH, tile);
		Shape2.render(f5);
		setColourBasedOnState(ForgeDirection.SOUTH, tile);
		Shape4.render(f5);
		setColourBasedOnState(ForgeDirection.EAST, tile);
		Shape3.render(f5);
		setColourBasedOnState(ForgeDirection.WEST, tile);
		Shape1.render(f5);
		GL11.glColor3f(1, 1, 1);
	}
	
	private void setColourBasedOnState(ForgeDirection side, TileEnergyStorage tile) {
		Color in = new Color(colourSideInput);
		Color out = new Color(colourSideOutput);
		if (tile.isOutputtingToSide(side)) {
			GL11.glColor3ub((byte) out.getRed(), (byte) out.getGreen(), (byte) out.getBlue());
		} else {
			GL11.glColor3ub((byte) in.getRed(), (byte) in.getGreen(), (byte) in.getBlue());
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

}