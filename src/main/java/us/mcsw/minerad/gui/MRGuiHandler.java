package us.mcsw.minerad.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.gui.container.ContainerDensePouch;
import us.mcsw.minerad.gui.container.ContainerFilter;
import us.mcsw.minerad.gui.container.ContainerFissionReactor;
import us.mcsw.minerad.gui.container.ContainerFusionReactor;
import us.mcsw.minerad.gui.container.ContainerRadioTowerBase;
import us.mcsw.minerad.gui.container.ContainerRadioactiveGenerator;
import us.mcsw.minerad.gui.container.ContainerShieldGenerator;
import us.mcsw.minerad.gui.container.ContainerInfuser;
import us.mcsw.minerad.gui.container.ContainerWaterDistiller;
import us.mcsw.minerad.gui.gui.GuiDensePouch;
import us.mcsw.minerad.gui.gui.GuiFilter;
import us.mcsw.minerad.gui.gui.GuiFissionReactor;
import us.mcsw.minerad.gui.gui.GuiFusionReactor;
import us.mcsw.minerad.gui.gui.GuiRadioTowerBase;
import us.mcsw.minerad.gui.gui.GuiRadioactiveGenerator;
import us.mcsw.minerad.gui.gui.GuiShieldGenerator;
import us.mcsw.minerad.gui.gui.GuiInfuser;
import us.mcsw.minerad.gui.gui.GuiWaterDistiller;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileFilter;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;
import us.mcsw.minerad.tiles.TileRadioTowerBase;
import us.mcsw.minerad.tiles.TileRadioactiveGenerator;
import us.mcsw.minerad.tiles.TileShieldGenerator;
import us.mcsw.minerad.tiles.TileInfuser;
import us.mcsw.minerad.tiles.TileWaterDistiller;

public class MRGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == MachineReference.DENSE_POUCH_ID) {
			return new ContainerDensePouch(player.inventory,
					ItemInventory.getFromItem(player.getCurrentEquippedItem(), ConfigMR.DENSE_POUCH_ROWS * 9));
		}

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileInfuser) {
			return new ContainerInfuser(player.inventory, (TileInfuser) te);
		}
		if (te instanceof TileRadioTowerBase) {
			return new ContainerRadioTowerBase(player.inventory, (TileRadioTowerBase) te);
		}
		if (te instanceof TileFissionReactor) {
			TileFissionReactor tfr = (TileFissionReactor) te;
			if (tfr.hasMaster()) {
				return new ContainerFissionReactor(player.inventory, (TileFissionReactor) tfr.getMaster());
			}
		}
		if (te instanceof TileFusionReactor) {
			TileFusionReactor tfr = (TileFusionReactor) te;
			if (tfr.hasMaster()) {
				return new ContainerFusionReactor(player.inventory, (TileFusionReactor) tfr.getMaster());
			}
		}
		if (te instanceof TileRadioactiveGenerator) {
			return new ContainerRadioactiveGenerator(player.inventory, (TileRadioactiveGenerator) te);
		}
		if (te instanceof TileShieldGenerator) {
			return new ContainerShieldGenerator(player.inventory, (TileShieldGenerator) te);
		}
		if (te instanceof TileWaterDistiller) {
			return new ContainerWaterDistiller(player.inventory, (TileWaterDistiller) te);
		}
		if (te instanceof TileFilter) {
			return new ContainerFilter(player.inventory, (TileFilter) te);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == MachineReference.DENSE_POUCH_ID) {
			return new GuiDensePouch(player.inventory,
					ItemInventory.getFromItem(player.getCurrentEquippedItem(), ConfigMR.DENSE_POUCH_ROWS * 9));
		}

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileInfuser) {
			return new GuiInfuser(player.inventory, (TileInfuser) te);
		}
		if (te instanceof TileRadioTowerBase) {
			return new GuiRadioTowerBase(player.inventory, (TileRadioTowerBase) te);
		}
		if (te instanceof TileFissionReactor) {
			TileFissionReactor tfr = (TileFissionReactor) te;
			if (tfr.hasMaster()) {
				return new GuiFissionReactor(player.inventory, (TileFissionReactor) tfr.getMaster());
			}
		}
		if (te instanceof TileFusionReactor) {
			TileFusionReactor tfr = (TileFusionReactor) te;
			if (tfr.hasMaster()) {
				return new GuiFusionReactor(player.inventory, (TileFusionReactor) tfr.getMaster());
			}
		}
		if (te instanceof TileRadioactiveGenerator) {
			return new GuiRadioactiveGenerator(player.inventory, (TileRadioactiveGenerator) te);
		}
		if (te instanceof TileShieldGenerator) {
			return new GuiShieldGenerator(player.inventory, (TileShieldGenerator) te);
		}
		if (te instanceof TileWaterDistiller) {
			return new GuiWaterDistiller(player.inventory, (TileWaterDistiller) te);
		}
		if (te instanceof TileFilter) {
			return new GuiFilter(player.inventory, (TileFilter) te);
		}
		return null;
	}

}
