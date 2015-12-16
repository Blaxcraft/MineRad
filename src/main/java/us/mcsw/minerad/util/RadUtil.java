package us.mcsw.minerad.util;

import java.util.ArrayList;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.ModItems;

public class RadUtil {

	static ArrayList<RadEmitter> emitters = new ArrayList<RadEmitter>();

	public static DamageSource radiationDamage = new DamageSource(MineRad.MODID + ":radiation").setDamageBypassesArmor()
			.setDamageIsAbsolute();
	public static DamageSource nukeDamage = new DamageSource(MineRad.MODID + ":nuke").setDamageBypassesArmor()
			.setDamageIsAbsolute().setExplosion();

	public static void addEmitter(World w, int x, int y, int z, int power, int reach) {
		if (getPowerForEmitter(w, x, y, z) <= 0) {
			emitters.add(new RadEmitter(w.provider.dimensionId, x, y, z, power, reach));
		}
	}

	public static int getRadsAtLocation(World w, int x, int y, int z) {
		double maxPowerSq = 0;
		for (RadEmitter e : emitters.toArray(new RadEmitter[0])) {
			if (e.dimensionId != w.provider.dimensionId) {
				continue;
			}
			int dX = e.x - x, dY = e.y - y, dZ = e.z - z;
			int disSq = (dX * dX) + (dY * dY) + (dZ * dZ);
			int powSq = e.power * e.power;
			disSq /= e.reach + 1;
			if (disSq <= 4 * powSq) {
				double pow = (double) powSq / ((double) disSq + 1.0);
				pow -= 0.5;
				if (ConfigMR.ADDITIVE_RADIATION) {
					maxPowerSq += pow;
				} else if (pow > maxPowerSq) {
					maxPowerSq = pow;
				}
			}
		}
		boolean wasteland = w.getBiomeGenForCoords(x, z).equals(MineRad.wasteland) && w.canBlockSeeTheSky(x, y, z);
		if (wasteland) {
			if (ConfigMR.ADDITIVE_RADIATION) {
				maxPowerSq += ConfigMR.BACKGROUND_WASTELAND_RADIATION * ConfigMR.BACKGROUND_WASTELAND_RADIATION;
			} else if (maxPowerSq < ConfigMR.BACKGROUND_WASTELAND_RADIATION * ConfigMR.BACKGROUND_WASTELAND_RADIATION) {
				maxPowerSq = ConfigMR.BACKGROUND_WASTELAND_RADIATION * ConfigMR.BACKGROUND_WASTELAND_RADIATION;
			}
		}
		if (maxPowerSq <= 0) {
			return 0;
		}
		return (int) Math.ceil(Math.sqrt(maxPowerSq));
	}

	public static int getPowerForEmitter(World w, int x, int y, int z) {
		for (RadEmitter e : emitters.toArray(new RadEmitter[0])) {
			if (e.matchesLocation(w, x, y, z)) {
				return e.power;
			}
		}
		return 0;
	}

	public static int getReachForEmitter(World w, int x, int y, int z) {
		for (RadEmitter e : emitters.toArray(new RadEmitter[0])) {
			if (e.matchesLocation(w, x, y, z)) {
				return e.reach;
			}
		}
		return 0;
	}

	public static void setPowerAndReach(World w, int x, int y, int z, int power, int reach) {
		if (power <= 0) {
			for (RadEmitter e : emitters.toArray(new RadEmitter[0])) {
				if (e.matchesLocation(w, x, y, z)) {
					emitters.remove(e);
				}
			}
			return;
		}
		if (getPowerForEmitter(w, x, y, z) > 0) {
			for (RadEmitter e : emitters.toArray(new RadEmitter[0])) {
				if (e.matchesLocation(w, x, y, z)) {
					e.power = power;
				}
			}
		} else {
			addEmitter(w, x, y, z, power, reach);
		}
	}

	public static class RadEmitter {
		public int dimensionId;
		public int x, y, z, power, reach;

		public RadEmitter(int dimensionId, int x, int y, int z, int power, int reach) {
			this.dimensionId = dimensionId;
			this.x = x;
			this.y = y;
			this.z = z;
			this.power = power;
			this.reach = reach;
		}

		public boolean matchesLocation(World w, int x, int y, int z) {
			return matchesLocation(w.provider.dimensionId, x, y, z);
		}

		public boolean matchesLocation(int dimId, int x, int y, int z) {
			return this.dimensionId == dimId && this.x == x && this.y == y && this.z == z;
		}
	}

}
