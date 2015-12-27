package us.mcsw.minerad;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import us.mcsw.minerad.ench.EnchantmentRadiationResist;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.potion.PotionRadX;
import us.mcsw.minerad.potion.PotionRadiationSickness;
import us.mcsw.minerad.proxy.IProxy;
import us.mcsw.minerad.world.BiomeWasteland;

@Mod(modid = MineRad.MODID, version = MineRad.VERSION, name = MineRad.MOD_NAME)
public class MineRad {

	public static final String MODID = "minerad";
	public static final String MOD_NAME = "MineRad";
	public static final String VERSION = "1.0.0";
	public static final String CLIENT_PROXY_PATH = "us.mcsw.minerad.proxy.ClientProxy",
			SERVER_PROXY_PATH = "us.mcsw.minerad.proxy.CommonProxy";

	@SidedProxy(modId = MODID, clientSide = CLIENT_PROXY_PATH, serverSide = SERVER_PROXY_PATH)
	public static IProxy proxy;

	@Instance
	public static MineRad ins;

	public static SimpleNetworkWrapper network;

	public static BiomeWasteland wasteland = null;

	public static PotionRadX potionRadX;
	public static PotionRadiationSickness potionRadiationSickness;

	public static EnchantmentRadiationResist enchRadResist;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	public static CreativeTabs creativeTab = new CreativeTabs("minerad") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ModItems.geigerCounter;
		}
	};
}
