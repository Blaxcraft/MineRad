package us.mcsw.minerad.effects;

import java.awt.Color;
import java.util.Random;

import net.minecraft.world.World;

public class EffectBossShield extends EffectSphereParticles {

	static Random rand = new Random();

	public EffectBossShield(World w, double x, double y, double z, double radius, double lat, double lon, Color color) {
		super(w, x, y, z, radius, lat, lon, rand.nextGaussian() / radius, rand.nextGaussian() / radius, color,
				rand.nextInt(10) + 20);
	}

}
