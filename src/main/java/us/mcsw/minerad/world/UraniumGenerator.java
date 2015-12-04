package us.mcsw.minerad.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.util.LogUtil;

public class UraniumGenerator implements IWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.dimensionId == 0) {
			int firstX, firstY, firstZ;

			for (int i = 0; i < 5; i++) {
				firstX = chunkX * 16 + rand.nextInt(16);
				firstZ = chunkZ * 16 + rand.nextInt(16);
				firstY = rand.nextInt(40) + 24;
				new WorldGenMinable(ModBlocks.uraniumTraces, rand.nextInt(8) + 5).generate(world, rand, firstX, firstY,
						firstZ);
			}

			for (int i = 0; i < 3; i++) {
				firstX = chunkX * 16 + rand.nextInt(16);
				firstZ = chunkZ * 16 + rand.nextInt(16);
				firstY = rand.nextInt(24);
				new WorldGenMinable(ModBlocks.uraniumLump, rand.nextInt(3) + 3).generate(world, rand, firstX, firstY,
						firstZ);
			}

			for (int i = 0; i < 2; i++) {
				firstX = chunkX * 16 + rand.nextInt(16);
				firstZ = chunkZ * 16 + rand.nextInt(16);
				firstY = rand.nextInt(5) + 4;
				new WorldGenMinable(ModBlocks.uraniumOre, rand.nextInt(2) + 1).generate(world, rand, firstX, firstY,
						firstZ);
			}
		}
	}

}
