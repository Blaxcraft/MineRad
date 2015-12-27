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
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.recipes.FusionRecipes;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TileFusionReactor;

public class BlockFusionReactor extends BlockMultiblock {

	@SideOnly(Side.CLIENT)
	IIcon active = null;

	public BlockFusionReactor() {
		super(Material.iron, "fusionReactor");

		setHardness(5.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileFusionReactor();
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int meta, float cx, float cy,
			float cz) {
		TileEntity tile = w.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileFusionReactor) {
			TileFusionReactor cl = (TileFusionReactor) tile;
			if (cl.hasMaster() && cl.checkForMaster()) {
				int mX = cl.getMasterX(), mY = cl.getMasterY(), mZ = cl.getMasterZ();
				TileFusionReactor mas = (TileFusionReactor) w.getTileEntity(mX, mY, mZ);
				p.openGui(MineRad.ins, MachineReference.FUSION_REACTOR_ID, w, mX, mY, mZ);
				return true;
			}
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		active = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "fusionReactorComplete");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess ba, int x, int y, int z, int side) {
		TileEntity tile = ba.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileFusionReactor) {
			TileFusionReactor tf = (TileFusionReactor) tile;
			if (tf.hasMaster()) {
				return active;
			}
		}
		return super.getIcon(ba, x, y, z, side);
	}

	public static class ItemBlockFusionReactor extends ItemBlockMR {

		public ItemBlockFusionReactor(Block block) {
			super(block);
		}

		@Override
		public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
			super.addInformation(it, pl, list, n);

			list.add("Place in a 3x3x3 with no center");
		}

	}

}
