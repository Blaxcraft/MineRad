package us.mcsw.minerad.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import us.mcsw.core.BlockMR;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.minerad.tiles.TileAutoDropper;

public class BlockAutoDropper extends BlockMR implements ITileEntityProvider {

	IIcon side = null, top = null;

	public BlockAutoDropper() {
		super(Material.rock, "autoDropper");

		setHardness(4.0f);
	}

	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase elb, ItemStack it) {
		int l = BlockPistonBase.determineOrientation(w, x, y, z, elb);
		w.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		side = reg.registerIcon("furnace_side");
		top = reg.registerIcon("furnace_top");
	}

	public IIcon getIconFromMetadata(int side, int meta) {
		return side == meta ? this.blockIcon
				: (meta != 1 && meta != 0 ? (side != 1 && side != 0 ? this.side : this.top) : this.top);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return getIconFromMetadata(side, 3);
	}

	@Override
	public IIcon getIcon(IBlockAccess ba, int x, int y, int z, int s) {
		return getIconFromMetadata(s, ba.getBlockMetadata(x, y, z));
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int m) {
		TileEntity te = w.getTileEntity(x, y, z);
		ItemUtil.dropItemsFromInventory(te);
		super.breakBlock(w, x, y, z, b, m);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileAutoDropper();
	}

}
