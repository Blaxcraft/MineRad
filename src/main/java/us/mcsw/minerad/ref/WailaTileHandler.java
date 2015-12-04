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
import us.mcsw.minerad.blocks.BlockFissionReactor;
import us.mcsw.minerad.blocks.BlockFusionReactor;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;

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
		if (config.getConfig("option.minerad.showFusionDamage")) {
			if (data.getBlock().equals(ModBlocks.fusionReactor)) {
				TileEntity tile = data.getTileEntity();
				if (tile != null && tile instanceof TileFusionReactor) {
					TileFusionReactor tf = (TileFusionReactor) tile;
					if (tf.hasMaster() && tf.checkForMaster()) {
						TileFusionReactor tfm = (TileFusionReactor) tf.getMaster();
						
						list.add("Heat: " + tfm.getHeatLevel());
						
						if (tfm.hasCore) {
							if (tfm.isCoreDepleted()) {
								list.add("Fusion Core Depleted");
							} else
								list.add("Fusion Core Power: " + (ModItems.fusionCore.getMaxDamage() - tfm.coreDamage));
						} else {
							list.add("No core present");
						}

						if (tfm.hasOre) {
							if (tfm.isOreCompleted()) {
								list.add("Process complete");
							} else
								list.add("Progress: " + (tfm.oreProgress * 100 / tfm.maxNeeded) + "%");
						} else {
							list.add("No item present");
						}

						if (tfm.hasCoolant) {
							if (tfm.isCoolantDepleted()) {
								list.add("Coolant depleted");
							} else
								list.add("Coolant durability: "
										+ (ModItems.coolantCore.getMaxDamage() - tfm.coolantDamage));
						} else {
							list.add("No coolant present");
						}
					}
				}
			}
		}
		if (config.getConfig("option.minerad.showFissionDamage")) {
			if (data.getBlock().equals(ModBlocks.fissionReactor)) {
				TileEntity tile = data.getTileEntity();
				if (tile != null && tile instanceof TileFissionReactor) {
					TileFissionReactor tf = (TileFissionReactor) tile;
					if (tf.hasMaster() && tf.checkForMaster()) {
						TileFissionReactor tfm = (TileFissionReactor) tf.getMaster();
						
						list.add("Heat: " + tfm.getHeatLevel());
						
						if (tfm.hasCore) {
							if (tfm.isCoreDepleted()) {
								list.add("Fission Core Depleted");
							} else
								list.add("Fission Core Power: "
										+ (ModItems.fissionCore.getMaxDamage() - tfm.coreDamage));
						} else {
							list.add("No core present");
						}

						if (tfm.hasOre) {
							if (tfm.isOreCompleted()) {
								list.add("Process complete");
							} else
								list.add("Progress: " + (tfm.oreProgress * 100 / tfm.maxNeeded) + "%");
						} else {
							list.add("No item present");
						}

						if (tfm.hasCoolant) {
							if (tfm.isCoolantDepleted()) {
								list.add("Coolant depleted");
							} else
								list.add("Coolant durability: "
										+ (ModItems.coolantCore.getMaxDamage() - tfm.coolantDamage));
						} else {
							list.add("No coolant present");
						}
					}
				}
			}
		}
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
		reg.registerBodyProvider(ins, BlockFusionReactor.class);
		reg.registerBodyProvider(ins, BlockFissionReactor.class);

		reg.registerNBTProvider(ins, BlockFusionReactor.class);
		reg.registerNBTProvider(ins, BlockFissionReactor.class);

		reg.addConfig(MineRad.MODID, "option.minerad.showFusionDamage");
		reg.addConfig(MineRad.MODID, "option.minerad.showFissionDamage");
	}

}
