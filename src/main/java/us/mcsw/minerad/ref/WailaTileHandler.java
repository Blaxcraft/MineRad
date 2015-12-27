package us.mcsw.minerad.ref;

import java.util.List;

import cpw.mods.fml.common.Optional;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.blocks.BlockEnergyStorage;
import us.mcsw.minerad.blocks.BlockFissionReactor;
import us.mcsw.minerad.blocks.BlockFusionReactor;
import us.mcsw.minerad.blocks.BlockPipe;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.tiles.TileEnergyStorage;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;
import us.mcsw.minerad.tiles.TilePipe;

public class WailaTileHandler implements IWailaDataProvider {

	@Optional.Method(modid = "Waila")
	@Override
	public List<String> getWailaHead(ItemStack it, List<String> list, IWailaDataAccessor data,
			IWailaConfigHandler config) {
		return list;
	}

	@Optional.Method(modid = "Waila")
	@Override
	public List<String> getWailaBody(ItemStack it, List<String> list, IWailaDataAccessor data,
			IWailaConfigHandler config) {
		return list;
	}

	@Optional.Method(modid = "Waila")
	@Override
	public List<String> getWailaTail(ItemStack it, List<String> list, IWailaDataAccessor data,
			IWailaConfigHandler config) {
		return list;
	}

	@Optional.Method(modid = "Waila")
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor acc, IWailaConfigHandler config) {
		if (acc.getBlock().equals(ModBlocks.pipeBlock)) {
			if (acc.getTileEntity() != null && acc.getTileEntity() instanceof TilePipe) {
				TilePipe tp = (TilePipe) acc.getTileEntity();

				ItemStack ret = new ItemStack(acc.getBlock());
				if (tp.getTier() != null)
					CapacitorTier.setInItemStack(ret, tp.getTier());
				return ret;
			}
		}
		if (acc.getBlock().equals(ModBlocks.energyStorage)) {
			if (acc.getTileEntity() != null && acc.getTileEntity() instanceof TileEnergyStorage) {
				TileEnergyStorage ts = (TileEnergyStorage) acc.getTileEntity();

				ItemStack ret = new ItemStack(acc.getBlock());
				if (ts.getTier() != null)
					CapacitorTier.setInItemStack(ret, ts.getTier());
				return ret;
			}
		}
		return acc.getStack();
	}

	@Optional.Method(modid = "Waila")
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP pl, TileEntity te, NBTTagCompound nbt, World w, int x, int y,
			int z) {

		if (te != null)
			te.writeToNBT(nbt);

		return nbt;
	}

	@Optional.Method(modid = "Waila")
	public static void callbackRegister(IWailaRegistrar reg) {
		WailaTileHandler ins = new WailaTileHandler();

		reg.registerNBTProvider(ins, BlockPipe.class);
		reg.registerStackProvider(ins, BlockPipe.class);

		reg.registerNBTProvider(ins, BlockEnergyStorage.class);
		reg.registerStackProvider(ins, BlockEnergyStorage.class);
	}

}
