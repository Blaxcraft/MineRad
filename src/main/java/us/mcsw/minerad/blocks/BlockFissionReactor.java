package us.mcsw.minerad.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import us.mcsw.core.BlockMultiblock;
import us.mcsw.core.ItemBlockMR;
import us.mcsw.core.TileMultiblock;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.init.FissionRecipes;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TileFissionReactor;

public class BlockFissionReactor extends BlockMultiblock {

	@SideOnly(Side.CLIENT)
	IIcon active = null;

	public BlockFissionReactor() {
		super(Material.iron, "fissionReactor");

		setHardness(5.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileFissionReactor();
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int meta, float cx, float cy,
			float cz) {
		TileEntity tile = w.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileFissionReactor) {
			TileFissionReactor cl = (TileFissionReactor) tile;
			if (cl.hasMaster() && cl.checkForMaster()) {
				int mX = cl.getMasterX(), mY = cl.getMasterY(), mZ = cl.getMasterZ();
				TileFissionReactor mas = (TileFissionReactor) w.getTileEntity(mX, mY, mZ);
				p.openGui(MineRad.ins, MachineReference.FISSION_REACTOR_ID, w, mX, mY, mZ);
				return true;
			}
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		active = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "fissionReactorComplete");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess ba, int x, int y, int z, int side) {
		TileEntity tile = ba.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileFissionReactor) {
			TileFissionReactor tf = (TileFissionReactor) tile;
			if (tf.hasMaster()) {
				return active;
			}
		}
		return super.getIcon(ba, x, y, z, side);
	}

	public static class ItemBlockFissionReactor extends ItemBlockMR {

		public ItemBlockFissionReactor(Block block) {
			super(block);
		}

		@Override
		public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
			super.addInformation(it, pl, list, n);

			list.add("Place in a 3x3x3 with no center");
		}

	}

}
