package us.mcsw.minerad.ref;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import us.mcsw.minerad.MineRad;

public class MachineReference {

	private static final String textureLocation = TextureReference.RESOURCE_PREFIX + "textures/gui/";

	public static IIcon CORE_BACKGROUND = null;

	public static final int URANIUM_INFUSER_ID = 0;
	public static final ResourceLocation URANIUM_INFUSER_GUI = new ResourceLocation(
			textureLocation + "uraniumInfuser.png");

	public static final int RADIO_TOWER_ID = 1;
	public static final ResourceLocation RADIO_TOWER_GUI = new ResourceLocation(textureLocation + "radioTowerBase.png");

	public static final int FISSION_REACTOR_ID = 2;
	public static final ResourceLocation FISSION_REACTOR_GUI = new ResourceLocation(
			textureLocation + "fissionReactor.png");
	public static final ResourceLocation FISSION_REACTOR_GUI_NEI = new ResourceLocation(
			textureLocation + "nei/fissionReactorNEI.png");

	public static final int FUSION_REACTOR_ID = 3;
	public static final ResourceLocation FUSION_REACTOR_GUI = new ResourceLocation(
			textureLocation + "fusionReactor.png");

	public static final int RADIO_GENERATOR_ID = 4;
	public static final ResourceLocation RADIO_GENERATOR_GUI = new ResourceLocation(
			textureLocation + "radioGenerator.png");

	public static final ResourceLocation MICROWAVE_GUI_NEI = new ResourceLocation(
			textureLocation + "nei/microwaveNEI.png");

	public static final int DENSE_POUCH_ID = 100;

}
