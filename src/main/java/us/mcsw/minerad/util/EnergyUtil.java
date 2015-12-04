package us.mcsw.minerad.util;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyUtil {

	public static void pushEnergy(IEnergyProvider provider, World w, int x, int y, int z, EnergyStorage storage,
			ForgeDirection to) {
		if (storage.getEnergyStored() > 0) {
			TileEntity pushTo = w.getTileEntity(x + to.offsetX, y + to.offsetY, z + to.offsetZ);
			if (pushTo != null && pushTo instanceof IEnergyReceiver) {
				IEnergyReceiver rec = (IEnergyReceiver) pushTo;
				if (rec.canConnectEnergy(to)) {
					int extr = provider.extractEnergy(to, storage.getMaxExtract(), true);
					int reci = rec.receiveEnergy(to.getOpposite(), extr, true);
					if (extr > 0 && reci > 0) {
						rec.receiveEnergy(to.getOpposite(), provider.extractEnergy(to, reci, false), false);
					}
				}
			}
		}
	}

}
