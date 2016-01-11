package us.mcsw.minerad.effects;

import java.awt.Color;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EffectSphereParticles extends EntityFX {

	double cx, cy, cz;
	double lat, lon, vlat, vlon;
	double radius;

	public EffectSphereParticles(World w, double x, double y, double z, double radius, double lat, double lon,
			double vlat, double vlon, Color color, int maxAge) {
		super(w, x, y, z);
		this.radius = radius;
		this.lat = lat;
		this.lon = lon;

		this.vlat = vlat;
		this.vlon = vlon;

		this.cx = x;
		this.cy = y;
		this.cz = z;

		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;

		this.particleGravity = 0;

		this.setParticleTextureIndex(49);

		this.particleRed = color.getRed() / 255.0f;
		this.particleGreen = color.getGreen() / 255.0f;
		this.particleBlue = color.getBlue() / 255.0f;

		this.particleMaxAge = maxAge;

		setPosition(x, y, z, radius, lat, lon);
	}

	public void setPosition(double cx, double cy, double cz, double rad, double lat, double lon) {
		double xOff = Math.cos(lat) * Math.cos(lon), yOff = Math.sin(lat), zOff = Math.cos(lat) * Math.sin(lon);
		xOff *= rad;
		yOff *= rad;
		zOff *= rad;
		super.setPosition(xOff + cx, yOff + cy, zOff + cz);
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
			this.setDead();

		this.lat = this.lat += this.vlat;
		this.lon = this.lon += this.vlon;

		setPosition(cx, cy, cz, radius, lat, lon);
	}

}
