package us.mcsw.minerad;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import us.mcsw.minerad.gui.MRGuiHandler;
import us.mcsw.minerad.init.AchievementsInit;
import us.mcsw.minerad.init.CraftingInit;
import us.mcsw.minerad.init.DungeonLootInit;
import us.mcsw.minerad.init.FissionRecipes;
import us.mcsw.minerad.init.FusionRecipes;
import us.mcsw.minerad.init.MicrowaveRecipes;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.init.ModEntities;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.init.ModPotions;
import us.mcsw.minerad.init.ModTileEntities;
import us.mcsw.minerad.init.UraniumInfuserRecipes;
import us.mcsw.minerad.nei.NEIRecipes;
import us.mcsw.minerad.net.MessageUpdateFrequency;
import us.mcsw.minerad.potion.PotionRadX;
import us.mcsw.minerad.potion.PotionRadiationSickness;
import us.mcsw.minerad.proxy.IProxy;
import us.mcsw.minerad.world.BiomeWasteland;
import us.mcsw.minerad.world.UraniumGenerator;
import us.mcsw.minerad.world.WastelandHutGen;

@Mod(modid = MineRad.MODID, version = MineRad.VERSION, name = MineRad.MOD_NAME)
public class MineRad {

	public static final String MODID = "minerad";
	public static final String MOD_NAME = "MineRad";
	public static final String VERSION = "1.0.0";
	public static final String CLIENT_PROXY_PATH = "us.mcsw.minerad.proxy.ClientProxy",
			SERVER_PROXY_PATH = "us.mcsw.minerad.proxy.ServerProxy";

	@SidedProxy(modId = MODID, clientSide = CLIENT_PROXY_PATH, serverSide = SERVER_PROXY_PATH)
	public static IProxy proxy;

	@Instance
	public static MineRad ins;

	public static SimpleNetworkWrapper network;

	public static final BiomeWasteland wasteland = new BiomeWasteland();

	public static PotionRadX potionRadX;
	public static PotionRadiationSickness potionRadiationSickness;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// load network handling, mod config, items + blocks
		ConfigMR.init(event.getSuggestedConfigurationFile());

		ModBlocks.init();
		ModItems.init();

		BiomeDictionary.registerBiomeType(wasteland);
		BiomeManager.addBiome(BiomeType.DESERT, new BiomeEntry(wasteland, 100));

		ModPotions.preInit();

		EventListener listener = new EventListener();
		MinecraftForge.EVENT_BUS.register(listener);
		FMLCommonHandler.instance().bus().register(listener);

		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		network.registerMessage(MessageUpdateFrequency.Handler.class, MessageUpdateFrequency.class, 0, Side.SERVER);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// register guis, tileentities, crafting, etc

		CraftingInit.init();
		ModTileEntities.init();
		ModEntities.init();
		FissionRecipes.init();
		FusionRecipes.init();
		UraniumInfuserRecipes.init();
		MicrowaveRecipes.init();
		DungeonLootInit.init();
		AchievementsInit.init();
		GameRegistry.registerWorldGenerator(new UraniumGenerator(), 0);
		GameRegistry.registerWorldGenerator(new WastelandHutGen(), 0);

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new MRGuiHandler());

		proxy.init();

		FMLInterModComms.sendMessage("Waila", "register", "us.mcsw.minerad.ref.WailaTileHandler.callbackRegister");
		NEIRecipes.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// anything to run after other mods have done initialization
	}

	public static CreativeTabs creativeTab = new CreativeTabs("minerad") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ModItems.geigerCounter;
		}
	};
}
