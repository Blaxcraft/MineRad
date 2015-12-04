package us.mcsw.minerad.init;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import us.mcsw.minerad.MineRad;

public class AchievementsInit {

	static ArrayList<Achievement> achievements = new ArrayList<Achievement>();

	public static Achievement uraniumChunk = makeAchievement("uraniumChunk", 0, 0, ModItems.uraniumChunk, null, false),
			geigerCounter = makeAchievement("geigerCounter", 0, 2, ModItems.geigerCounter, uraniumChunk, false),
			radPlating = makeAchievement("radPlating", 0, 4, ModItems.radResistantPlating, geigerCounter, false),
			uraniumOre = makeAchievement("uraniumOre", 2, 0, ModItems.uraniumOreItem, uraniumChunk, false),
			fissionCore = makeAchievement("fissionCore", 2, 2, ModItems.fissionCore, uraniumOre, false),
			fissionReactor = makeAchievement("fissionReactor", 4, 2, ModBlocks.fissionReactor, fissionCore, false),
			purifiedUranium = makeAchievement("purifiedUranium", 4, 0, ModItems.purifiedUranium, uraniumOre, false),
			plutonium = makeAchievement("plutonium", 5, 1, ModItems.plutonium, fissionReactor, false),
			fusionReactor = makeAchievement("fusionReactor", 7, 2, ModBlocks.fusionReactor, plutonium, false),
			unknownElement = makeAchievement("unknownElement", 8, 2, ModItems.unknownElement, fusionReactor, true),
			nuclearArsenal = makeAchievement("nuclearArsenal", 6, 3, ModBlocks.nuclearArsenal, plutonium, false),
			nukeDrop = makeAchievement("nukeDrop", 6, 5, ModItems.nukePackage, nuclearArsenal, true);

	public static void init() {
		AchievementPage page = new AchievementPage(MineRad.MOD_NAME, achievements.toArray(new Achievement[0]));
		AchievementPage.registerAchievementPage(page);
	}

	static Achievement makeAchievement(String name, int x, int y, ItemStack icon, Achievement pre, boolean special) {
		Achievement ret = new Achievement(MineRad.MODID + ":" + name, MineRad.MODID + ":" + name, x, y, icon, pre);
		if (special)
			ret.setSpecial();
		ret.registerStat();
		achievements.add(ret);
		return ret;
	}

	static Achievement makeAchievement(String name, int x, int y, Item icon, Achievement pre, boolean special) {
		return makeAchievement(name, x, y, new ItemStack(icon), pre, special);
	}

	static Achievement makeAchievement(String name, int x, int y, Block icon, Achievement pre, boolean special) {
		return makeAchievement(name, x, y, new ItemStack(icon), pre, special);
	}

}
