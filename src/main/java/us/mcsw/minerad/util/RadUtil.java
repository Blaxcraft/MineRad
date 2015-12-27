package us.mcsw.minerad.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.net.MessageUpdateEmitters;

public class RadUtil {

	public static DamageSource radiationDamage = new DamageSource(MineRad.MODID + ":radiation").setDamageBypassesArmor()
			.setDamageIsAbsolute();
	public static DamageSource nukeDamage = new DamageSource(MineRad.MODID + ":nuke").setDamageBypassesArmor()
			.setDamageIsAbsolute().setExplosion();

	public static void addEmitter(World w, int x, int y, int z, int power, int reach) {
		if (getPowerForEmitter(w, x, y, z) <= 0) {
			RadEmitter add = new RadEmitter(x, y, z, power, reach);
			forWorld(w).addSource(add);
			updateSavedDataForPlayers(w);
		}
	}

	public static int getRadsAtLocation(World w, int x, int y, int z) {
		double maxPowerSq = 0;
		for (RadEmitter e : getEmitters(w).toArray(new RadEmitter[0])) {
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
		for (RadEmitter e : getEmitters(w).toArray(new RadEmitter[0])) {
			if (e.matchesLocation(x, y, z)) {
				return e.power;
			}
		}
		return 0;
	}

	public static int getReachForEmitter(World w, int x, int y, int z) {
		for (RadEmitter e : getEmitters(w).toArray(new RadEmitter[0])) {
			if (e.matchesLocation(x, y, z)) {
				return e.reach;
			}
		}
		return 0;
	}

	public static void setPowerAndReach(World w, int x, int y, int z, int power, int reach) {
		if (power <= 0) {
			for (RadEmitter e : getEmitters(w).toArray(new RadEmitter[0])) {
				if (e.matchesLocation(x, y, z)) {
					forWorld(w).removeSource(x, y, z);
					updateSavedDataForPlayers(w);
				}
			}
			return;
		}
		if (getPowerForEmitter(w, x, y, z) > 0) {
			for (RadEmitter e : getEmitters(w).toArray(new RadEmitter[0])) {
				if (e.matchesLocation(x, y, z)) {
					setPowerAndReach(w, x, y, z, 0, 0);
					setPowerAndReach(w, x, y, z, power, reach);
				}
			}
		} else {
			addEmitter(w, x, y, z, power, reach);
		}
	}

	public static List<RadEmitter> getEmitters(World w) {

		return forWorld(w).getSources();
	}

	public static class RadEmitter {
		public int x, y, z, power, reach;

		public RadEmitter(int x, int y, int z, int power, int reach) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.power = power;
			this.reach = reach;
		}

		public RadEmitter(NBTTagCompound data) {
			this.x = data.getInteger("x");
			this.y = data.getInteger("y");
			this.z = data.getInteger("z");
			this.power = data.getInteger("power");
			this.reach = data.getInteger("reach");
		}

		public boolean matchesLocation(int x, int y, int z) {
			return this.x == x && this.y == y && this.z == z;
		}

		public void writeToNBT(NBTTagCompound data) {
			data.setInteger("x", x);
			data.setInteger("y", y);
			data.setInteger("z", z);
			data.setInteger("power", power);
			data.setInteger("reach", reach);
		}
	}

	static final String DATA_TAG = "RadSources";

	public static class RadSavedData extends WorldSavedData {

		public NBTTagList sources = new NBTTagList();

		public RadSavedData(String tag) {
			super(tag);
		}

		@Override
		public void readFromNBT(NBTTagCompound read) {
			this.sources = read.getTagList("Sources", 10);
		}

		@Override
		public void writeToNBT(NBTTagCompound write) {
			write.setTag("Sources", this.sources);
		}

		public ArrayList<RadEmitter> getSources() {
			ArrayList<RadEmitter> ret = new ArrayList<RadEmitter>();

			for (int i = 0; i < sources.tagCount(); i++) {
				NBTTagCompound e = sources.getCompoundTagAt(i);
				ret.add(new RadEmitter(e));
			}

			return ret;
		}

		public void addSource(RadEmitter source) {
			NBTTagCompound data = new NBTTagCompound();
			source.writeToNBT(data);
			sources.appendTag(data);
			markDirty();
		}

		public void removeSource(int x, int y, int z) {
			for (int i = 0; i < sources.tagCount(); i++) {
				NBTTagCompound e = sources.getCompoundTagAt(i);
				RadEmitter at = new RadEmitter(e);
				if (at.matchesLocation(x, y, z)) {
					sources.removeTag(i);
					break;
				}
			}
			markDirty();
		}

		public void setSourcesList(NBTTagList list) {
			this.sources = list;
			markDirty();
		}

	}

	public static RadSavedData forWorld(World w) {
		RadSavedData savedData = (RadSavedData) w.perWorldStorage.loadData(RadSavedData.class, DATA_TAG);

		if (savedData != null)
			return savedData;

		savedData = new RadSavedData(DATA_TAG);
		w.perWorldStorage.setData(DATA_TAG, savedData);
		return savedData;
	}

	public static void updateSavedDataForPlayers(World w) {
		if (!w.isRemote) {
			for (Object o : w.playerEntities) {
				EntityPlayerMP pl = (EntityPlayerMP) o;
				MineRad.network.sendTo(new MessageUpdateEmitters(forWorld(w).sources), pl);
			}
		}
	}

}
