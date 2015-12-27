package us.mcsw.minerad;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import scala.collection.generic.BitOperations.Int;
import us.mcsw.minerad.ref.CapacitorTier;

public class ConfigMR {

	public static Configuration config;

	public static void init(File file) {
		if (config == null) {
			config = new Configuration(file);
			config.load();
			loadConfig();
		}
	}

	public static void loadConfig() {
		String catRad = "Radiation";
		ADDITIVE_RADIATION = config.getBoolean("additive-radiation", catRad, false,
				"!!IMPORTANT!! Setting this to TRUE will make radioactive sources ADD instead of using the highest one");
		RAD_LOSS_MULTIPLIER = config.getFloat("rad-loss-multiplier", catRad, 1, 0, 10,
				"Multiplier for passive rad loss");
		RAD_ARMOUR_PROTECTION_MULTIPLIER = config.getFloat("rad-armour-protection-modifier", catRad, 1, 0, 50,
				"Multiplier for protection while wearing the Radiation Suit");
		RAD_AWAY_AMOUNT = config.getInt("rad-away-amount", catRad, 200, 1, Integer.MAX_VALUE,
				"RADs lost when consuming RadAway");
		UNKNOWN_ELEMENT_RADIATION = config.getInt("unknown-element-radiation", catRad, 25, 1, 1000,
				"Radiation gained while holding '???'");
		RAD_SICKNESS_THRESHOLD_1 = config.getInt("radiation-sickness-threshold-1", catRad, 200, 0, Integer.MAX_VALUE,
				"RADs required for Radiation Sickness I");
		RAD_SICKNESS_THRESHOLD_2 = config.getInt("radiation-sickness-threshold-2", catRad, 400, 0, Integer.MAX_VALUE,
				"RADs required for Radiation Sickness II");
		RAD_SICKNESS_THRESHOLD_3 = config.getInt("radiation-sickness-threshold-3", catRad, 500, 0, Integer.MAX_VALUE,
				"RADs required for Radiation Sickness III");
		VILLAGER_CONVERSION_THRESHOLD = config.getInt("villager-conversion-threshold", catRad, 250, 0,
				Integer.MAX_VALUE, "Minimum RADs for villagers to turn into zombies");
		BACKGROUND_WASTELAND_RADIATION = config.getInt("background-wasteland-radiation", catRad, 2, 0, 150,
				"Background radiation for the Wasteland biome");

		String catCores = "Cores";
		FISSION_CORE_DURABILITY = config.getInt("fission-durability", catCores, 500, 100, Short.MAX_VALUE,
				"Capacity of the Fission Core");
		FUSION_CORE_DURABILITY = config.getInt("fusion-durability", catCores, 800, 100, Short.MAX_VALUE,
				"Capacity of the Fusion Core");
		COOLANT_CORE_DURABILITY = config.getInt("coolant-durability", catCores, 1250, 100, Short.MAX_VALUE,
				"Capacity of the Coolant Core");

		String catNukes = "Nukes";
		HARD_NUKE_RECIPE = config.getBoolean("hard-nuke-recipe", catNukes, false,
				"Makes the nuke require more materials to craft");
		NUKE_PURGE_RADIUS = config.getInt("nuke-purge-radius", catNukes, 50, 1, 250,
				"Radius in which the nuke will kill anything inside");
		NUKE_EXPLOSION_POWER_MULT = config.getFloat("nuke-power-mult", catNukes, 1, 0.01f, 10f,
				"Multiplier for the nuke's explosion");
		INITIAL_ARSENAL_CAPACITY = config.getInt("arsenal-capacity", catNukes, 3, 0, Integer.MAX_VALUE,
				"Initial capacity for storing nukes in the arsenal");

		String catMisc = "Misc";
		ARC_THROWER_CHANCE_MULTIPLIER = config.getFloat("arc-thrower-chance-multiplier", catMisc, 1, 0.01f, 10,
				"Multiplier for the Arc Thrower's capture chance");
		DENSE_POUCH_ROWS = config.getInt("dense-pouch-rows", catMisc, 5, 1, 6, "Rows for the Dense Pouch's inventory");
		BLACK_HOLE_RADIUS_ACTIVE = config.getInt("black-hole-radius-active", catMisc, 15, 1, 50,
				"Range to pick up items with the Portable Black Hole when active");
		BLACK_HOLE_RADIUS_PASSIVE = config.getInt("black-hole-radius-passive", catMisc, 10, 1, 40,
				"Range to pick up items with the Portable Black Hole when on in inventory");
		BLACK_HOLE_BLOCK_RADIUS = config.getInt("black-hole-block-radius", catMisc, 12, 1, 50,
				"Range for the Black Hole block (will not update current placed blocks unless broken and replaced)");
		WASTELAND_BIOME_WEIGHT = config.getInt("wasteland-biome-weight", catMisc, 50, 0, 1000,
				"Generation weight for the wasteland biome");

		String catCompat = "Compatability";
		config.setCategoryComment(catCompat,
				"Compatability with other mods, only change if you know what you are doing");
		WASTELAND_ID = config.getInt("wasteland-id", catCompat, 123, 0, 256, "Biome ID for Wasteland");
		RAD_SICKNESS_ID = config.getInt("rad-sickness-id", catCompat, 79, 0, Integer.MAX_VALUE,
				"Potion ID for Rad Sickness");
		RADX_ID = config.getInt("radx-id", catCompat, 78, 0, Integer.MAX_VALUE, "Potion ID for RadX");
		RAD_RESIST_ID = config.getInt("rad-resist-id", catCompat, 89, 60, Integer.MAX_VALUE,
				"Enchantment ID for Radiation Resist");

		for (CapacitorTier tier : CapacitorTier.tiers) {
			tier.loadFromConfig(config);
		}

		if (config.hasChanged()) {
			config.save();
		}
	}

	// Radiation
	public static boolean ADDITIVE_RADIATION;
	public static int RAD_SICKNESS_THRESHOLD_1, RAD_SICKNESS_THRESHOLD_2, RAD_SICKNESS_THRESHOLD_3,
			VILLAGER_CONVERSION_THRESHOLD, BACKGROUND_WASTELAND_RADIATION, RAD_AWAY_AMOUNT, UNKNOWN_ELEMENT_RADIATION;
	public static float RAD_LOSS_MULTIPLIER, RAD_ARMOUR_PROTECTION_MULTIPLIER;

	// Cores
	public static int FISSION_CORE_DURABILITY, FUSION_CORE_DURABILITY, COOLANT_CORE_DURABILITY;

	// Nukes
	public static boolean HARD_NUKE_RECIPE;
	public static int NUKE_PURGE_RADIUS, INITIAL_ARSENAL_CAPACITY;
	public static float NUKE_EXPLOSION_POWER_MULT;

	// Misc
	public static float ARC_THROWER_CHANCE_MULTIPLIER;
	public static int DENSE_POUCH_ROWS, BLACK_HOLE_RADIUS_PASSIVE, BLACK_HOLE_RADIUS_ACTIVE, BLACK_HOLE_BLOCK_RADIUS,
			WASTELAND_BIOME_WEIGHT;

	// Compatability
	public static int WASTELAND_ID, RAD_SICKNESS_ID, RADX_ID, RAD_RESIST_ID;
}
