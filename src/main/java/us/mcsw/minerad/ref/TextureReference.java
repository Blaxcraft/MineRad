package us.mcsw.minerad.ref;

import net.minecraft.util.ResourceLocation;
import us.mcsw.minerad.MineRad;

public class TextureReference {

	public static final String RESOURCE_ID = MineRad.MODID.toLowerCase();
	public static final String RESOURCE_PREFIX = RESOURCE_ID + ":";

	public static final ResourceLocation GHOUL_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/entity/feralGhoul.png");
	public static final ResourceLocation GLOWING_GHOUL_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/entity/feralGlowing.png");
	public static final ResourceLocation BOSS_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/entity/finalBoss.png");

	public static final ResourceLocation RADIO_TOWER_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/blocks/radioTowerAntenna.png");
	public static final ResourceLocation PIPE_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/blocks/pipeBlock.png");
	public static final ResourceLocation ENERGY_STORAGE_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/blocks/energyStorage.png");

	public static final ResourceLocation RADIATION_GUI_NEI = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/gui/nei/radiationNEI.png");

	public static final ResourceLocation FILTER_GUI = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/gui/filter.png");

	public static final ResourceLocation HOVER_LEGS_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/armor/hoverLegs.png");

}
