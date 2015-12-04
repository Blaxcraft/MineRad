package us.mcsw.minerad.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import us.mcsw.minerad.init.ModBlocks;

public class BiomeWasteland extends BiomeGenBase {

	public BiomeWasteland() {
		super(123);
		setBiomeName("Irradiated Wasteland");
		setColor(0xdd9944);
		spawnableCreatureList.clear();
		this.topBlock = Blocks.dirt;
		this.fillerBlock = Blocks.dirt;
		BiomeDecorator dec = this.theBiomeDecorator;
		dec.treesPerChunk = -999;
		dec.cactiPerChunk = -999;
		dec.deadBushPerChunk = 8;
		dec.grassPerChunk = -999;
		dec.flowersPerChunk = -999;
		dec.mushroomsPerChunk = 1;
		dec.reedsPerChunk = -999;
		dec.waterlilyPerChunk = -999;
	}
	
	@Override
	public int getSkyColorByTemp(float t) {
		return 0xddaa44;
	}
	
	@Override
	public int getBiomeFoliageColor(int x, int y, int z) {
		return 0xdd9944;
	}
	
	@Override
	public int getBiomeGrassColor(int x, int y, int z) {
		return 0xdd9944;
	}

}
