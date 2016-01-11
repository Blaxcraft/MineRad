package us.mcsw.minerad.effects;

import java.awt.Color;
import java.util.Random;

import net.minecraft.world.World;

public class EffectShield extends EffectSphereParticles {

	static Random rand = new Random();

	public EffectShield(World w, double x, double y, double z, double radius, double lat, double lon, Color color) {
		super(w, x, y, z, radius, lat, lon, Math.PI * (rand.nextDouble() - 0.5) * (0.005 / radius),
				Math.PI * 2 * (rand.nextBoolean() ? -1 : 1) * (0.05 / radius), color,
				(int) (40.0f + (rand.nextFloat() * 20) - 10));
	}

}
