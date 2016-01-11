package us.mcsw.minerad.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.init.ModBlocks;

public class LeadGenerator implements IWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (ConfigMR.ENABLE_LEAD && world.provider.dimensionId == 0) {
			int firstX, firstY, firstZ;

			for (int i = 0; i < 4; i++) {
				firstX = chunkX * 16 + rand.nextInt(16);
				firstZ = chunkZ * 16 + rand.nextInt(16);
				firstY = rand.nextInt(16) + 4;
				new WorldGenMinable(ModBlocks.oreLead, rand.nextInt(4) + 3).generate(world, rand, firstX, firstY,
						firstZ);
			}
		}
	}

}
