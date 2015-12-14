package us.mcsw.minerad.ref;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import us.mcsw.minerad.MineRad;

public class MachineReference {

	private static final String textureLocation = "/textures/gui/";

	public static IIcon CORE_BACKGROUND = null;

	public static final int URANIUM_INFUSER_ID = 0;
	public static final ResourceLocation URANIUM_INFUSER_GUI = new ResourceLocation(MineRad.MODID,
			textureLocation + "uraniumInfuser.png");

	public static final int RADIO_TOWER_ID = 1;
	public static final ResourceLocation RADIO_TOWER_GUI = new ResourceLocation(MineRad.MODID,
			textureLocation + "radioTowerBase.png");

	public static final int FISSION_REACTOR_ID = 2;
	public static final ResourceLocation FISSION_REACTOR_GUI = new ResourceLocation(MineRad.MODID,
			textureLocation + "fissionReactor.png");

	public static final int FUSION_REACTOR_ID = 3;
	public static final ResourceLocation FUSION_REACTOR_GUI = new ResourceLocation(MineRad.MODID,
			textureLocation + "fusionReactor.png");

}
