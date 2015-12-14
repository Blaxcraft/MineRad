package us.mcsw.minerad.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;

public class WastelandHutGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		int xS = chunkX * 16 + random.nextInt(16);
		int zS = chunkZ * 16 + random.nextInt(16);
		if (world.getBiomeGenForCoords(xS, zS) instanceof BiomeWasteland) {
			if (random.nextInt(50) == 0) {
				int yS = world.getHeightValue(xS, zS) - 1;
				if (world.getBlock(xS, yS, zS).getMaterial().isSolid()) {
					generateAtLocation(world, random, xS, yS, zS);
				}
			}
		}
	}

	public boolean generateAtLocation(World w, Random rand, int xS, int yS, int zS) {
		int xD = rand.nextInt(3) + 2, zD = rand.nextInt(3) + 2;
		int xStart = xS - xD, xEnd = xS + xD;
		int yStart = yS, yEnd = yS + rand.nextInt(3) + 2;
		int zStart = zS - zD, zEnd = zS + zD;

		for (int x = xStart; x <= xEnd; x++) {
			for (int z = zStart; z <= zEnd; z++) {
				for (int y = yStart; y <= yEnd; y++) {
					if (!w.isAirBlock(x, y, z)) {
						w.setBlockToAir(x, y, z);
					}
				}

				if (rand.nextInt(4) != 0) {
					w.setBlock(x, yS, z, Blocks.cobblestone);
				} else {
					w.setBlock(x, yS, z, Blocks.dirt);
				}

				if (x == xStart || x == xEnd || z == zStart || z == zEnd) {
					for (int y = yStart + 1; y <= yEnd; y++) {
						if (rand.nextInt(3) != 0) {
							break;
						}
						w.setBlock(x, y, z, Blocks.planks);
					}
				}
			}
		}

		boolean chestAxis = rand.nextBoolean(), chestSide = rand.nextBoolean();
		int cX = chestAxis ? chestSide ? xStart + 1 : xEnd - 1 : (rand.nextInt(xEnd - xStart) + xStart + 1);
		int cZ = chestAxis ? (rand.nextInt(xEnd - xStart) + zStart + 1) : chestSide ? zStart + 1 : zEnd - 1;
		placeChest(rand, w, cX, yS + 1, cZ, 0);
		return false;
	}

	public void placeChest(Random rand, World w, int x, int y, int z, int m) {
		w.setBlock(x, y, z, Blocks.chest, m, 2);
		TileEntityChest te = (TileEntityChest) w.getTileEntity(x, y, z);
		if (te != null) {
			WeightedRandomChestContent.generateChestContents(rand,
					ChestGenHooks.getItems(ChestGenHooks.DUNGEON_CHEST, rand), te,
					ChestGenHooks.getCount(ChestGenHooks.DUNGEON_CHEST, rand));
		}
	}

}
