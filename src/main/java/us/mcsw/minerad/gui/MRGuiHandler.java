package us.mcsw.minerad.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.gui.container.ContainerDensePouch;
import us.mcsw.minerad.gui.container.ContainerFissionReactor;
import us.mcsw.minerad.gui.container.ContainerFusionReactor;
import us.mcsw.minerad.gui.container.ContainerRadioTowerBase;
import us.mcsw.minerad.gui.container.ContainerUraniumInfuser;
import us.mcsw.minerad.gui.gui.GuiDensePouch;
import us.mcsw.minerad.gui.gui.GuiFissionReactor;
import us.mcsw.minerad.gui.gui.GuiFusionReactor;
import us.mcsw.minerad.gui.gui.GuiRadioTowerBase;
import us.mcsw.minerad.gui.gui.GuiUraniumInfuser;
import us.mcsw.minerad.items.ItemDensePouch;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;
import us.mcsw.minerad.tiles.TileRadioTowerBase;
import us.mcsw.minerad.tiles.TileUraniumInfuser;

public class MRGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == MachineReference.DENSE_POUCH_ID) {
			return new ContainerDensePouch(player.inventory,
					ItemInventory.getFromItem(player.getCurrentEquippedItem(), ConfigMR.DENSE_POUCH_ROWS * 9));
		}

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileUraniumInfuser) {
			return new ContainerUraniumInfuser(player.inventory, (TileUraniumInfuser) te);
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
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == MachineReference.DENSE_POUCH_ID) {
			return new GuiDensePouch(player.inventory,
					ItemInventory.getFromItem(player.getCurrentEquippedItem(), ConfigMR.DENSE_POUCH_ROWS * 9));
		}

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileUraniumInfuser) {
			return new GuiUraniumInfuser(player.inventory, (TileUraniumInfuser) te);
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
		return null;
	}

}
