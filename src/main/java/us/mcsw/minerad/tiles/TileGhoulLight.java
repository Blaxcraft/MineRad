package us.mcsw.minerad.tiles;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class TileGhoulLight extends TileEntity {

	Entity bound;

	@Override
	public void updateEntity() {
		if (bound != null) {
			ChunkCoordinates cc = new ChunkCoordinates(xCoord, yCoord, zCoord);
			if (bound.isDead || cc.getDistanceSquared((int) Math.floor(bound.posX), (int) Math.floor(bound.posY),
					(int) Math.floor(bound.posZ)) > 2)
				destroy();
		} else
			destroy();
	}

	public void destroy() {
		worldObj.setBlockToAir(xCoord, yCoord, zCoord);
	}

	public void setBound(Entity bound) {
		this.bound = bound;
	}

	public Entity getBound() {
		return bound;
	}

}
