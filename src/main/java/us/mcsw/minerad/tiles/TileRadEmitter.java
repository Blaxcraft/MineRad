package us.mcsw.minerad.tiles;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.minerad.blocks.BlockRadEmitter;
import us.mcsw.minerad.util.RadUtil;

public class TileRadEmitter extends TileEntity {
	
	@Override
	public void updateEntity() {
		Block b = worldObj.getBlock(xCoord, yCoord, zCoord);
		if (b instanceof BlockRadEmitter) {
			BlockRadEmitter bre = (BlockRadEmitter) b;
			RadUtil.addEmitter(worldObj, xCoord, yCoord, zCoord, bre.power, bre.reach);
		}
	}

}
