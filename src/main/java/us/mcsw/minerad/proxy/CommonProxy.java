package us.mcsw.minerad.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.MinecraftForge;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.EventListener;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ench.EnchantmentRadiationResist;
import us.mcsw.minerad.gui.MRGuiHandler;
import us.mcsw.minerad.init.AchievementsInit;
import us.mcsw.minerad.init.DungeonLootInit;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.init.ModEntities;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.init.ModPotions;
import us.mcsw.minerad.init.ModTileEntities;
import us.mcsw.minerad.nei.NEIRecipes;
import us.mcsw.minerad.net.MessageUpdateEmitters;
import us.mcsw.minerad.net.MessageUpdateFrequency;
import us.mcsw.minerad.recipes.FissionRecipes;
import us.mcsw.minerad.recipes.FusionRecipes;
import us.mcsw.minerad.recipes.MRRecipes;
import us.mcsw.minerad.recipes.MicrowaveRecipes;
import us.mcsw.minerad.recipes.UraniumInfuserRecipes;
import us.mcsw.minerad.world.BiomeWasteland;
import us.mcsw.minerad.world.UraniumGenerator;
import us.mcsw.minerad.world.WastelandHutGen;

public abstract class CommonProxy implements IProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		ConfigMR.init(event.getSuggestedConfigurationFile());

		ModBlocks.init();
		ModItems.init();

		MineRad.wasteland = new BiomeWasteland();
		BiomeDictionary.registerBiomeType(MineRad.wasteland);
		BiomeManager.addBiome(BiomeType.DESERT, new BiomeEntry(MineRad.wasteland, ConfigMR.WASTELAND_BIOME_WEIGHT));

		MineRad.enchRadResist = new EnchantmentRadiationResist();

		ModPotions.preInit();

		EventListener listener = new EventListener();
		MinecraftForge.EVENT_BUS.register(listener);
		FMLCommonHandler.instance().bus().register(listener);

		MineRad.network = NetworkRegistry.INSTANCE.newSimpleChannel(MineRad.MODID);
		MineRad.network.registerMessage(MessageUpdateFrequency.Handler.class, MessageUpdateFrequency.class, 0,
				Side.SERVER);
		MineRad.network.registerMessage(MessageUpdateEmitters.Handler.class, MessageUpdateEmitters.class, 1,
				Side.CLIENT);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		MRRecipes.init();
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

		NetworkRegistry.INSTANCE.registerGuiHandler(MineRad.ins, new MRGuiHandler());

		FMLInterModComms.sendMessage("Waila", "register", "us.mcsw.minerad.ref.WailaTileHandler.callbackRegister");

		if (Loader.isModLoaded("NotEnoughItems")) {
			NEIRecipes.init();
		}
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {

	}

}
