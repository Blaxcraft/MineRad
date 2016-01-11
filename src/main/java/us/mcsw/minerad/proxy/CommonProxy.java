package us.mcsw.minerad.proxy;

import java.awt.Color;
import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.MinecraftForge;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.EventListener;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ench.EnchantmentRadiationResist;
import us.mcsw.minerad.entity.EntityFinalBoss;
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
import us.mcsw.minerad.net.MessageUpdateProperties;
import us.mcsw.minerad.recipes.FissionRecipes;
import us.mcsw.minerad.recipes.FusionRecipes;
import us.mcsw.minerad.recipes.MRRecipes;
import us.mcsw.minerad.recipes.MicrowaveRecipes;
import us.mcsw.minerad.recipes.RadiationRecipes;
import us.mcsw.minerad.recipes.InfuserRecipes;
import us.mcsw.minerad.world.BiomeWasteland;
import us.mcsw.minerad.world.LeadGenerator;
import us.mcsw.minerad.world.UraniumGenerator;
import us.mcsw.minerad.world.WastelandHutGen;

public class CommonProxy implements IProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		ConfigMR.init(event.getSuggestedConfigurationFile());
		ConfigMR.initCapacitors(new File(event.getModConfigurationDirectory(), MineRad.MODID + "-capacitors.cfg"));

		ModBlocks.init();
		ModItems.init();

		MineRad.wasteland = new BiomeWasteland();
		BiomeManager.addBiome(BiomeType.DESERT, new BiomeEntry(MineRad.wasteland, ConfigMR.WASTELAND_BIOME_WEIGHT));
		BiomeDictionary.registerBiomeType(MineRad.wasteland, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.DRY,
				BiomeDictionary.Type.HOT, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.WASTELAND);

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
		MineRad.network.registerMessage(MessageUpdateProperties.Handler.class, MessageUpdateProperties.class, 2,
				Side.CLIENT);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		MRRecipes.init();
		ModTileEntities.init();
		ModEntities.init();
		FissionRecipes.init();
		FusionRecipes.init();
		InfuserRecipes.init();
		MicrowaveRecipes.init();
		RadiationRecipes.init();
		DungeonLootInit.init();
		AchievementsInit.init();
		GameRegistry.registerWorldGenerator(new UraniumGenerator(), 0);
		GameRegistry.registerWorldGenerator(new LeadGenerator(), 0);
		GameRegistry.registerWorldGenerator(new WastelandHutGen(), 1);

		NetworkRegistry.INSTANCE.registerGuiHandler(MineRad.ins, new MRGuiHandler());

		FMLInterModComms.sendMessage("Waila", "register", "us.mcsw.minerad.ref.WailaTileHandler.callbackRegister");

		if (Loader.isModLoaded("NotEnoughItems")) {
			NEIRecipes.init();
		}
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Override
	public void generateShieldParticles(World w, double cx, double cy, double cz, double radius, int amount,
			Color colour) {
	}

	@Override
	public void generateBossParticles(EntityFinalBoss boss) {
	}

}
