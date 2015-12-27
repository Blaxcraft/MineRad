package us.mcsw.minerad.ref;

import net.minecraft.util.ResourceLocation;
import us.mcsw.minerad.MineRad;

public class TextureReference {

	public static final String RESOURCE_ID = MineRad.MODID.toLowerCase();
	public static final String RESOURCE_PREFIX = RESOURCE_ID + ":";

	public static final ResourceLocation RADIO_TOWER_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/blocks/radioTowerAntenna.png");
	public static final ResourceLocation PIPE_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/blocks/pipeBlock.png");
	public static final ResourceLocation ENERGY_STORAGE_TEXTURE = new ResourceLocation(
			RESOURCE_PREFIX + "/textures/blocks/energyStorage.png");

}
