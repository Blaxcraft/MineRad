package us.mcsw.minerad.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import us.mcsw.core.BlockMR;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TileMicrowave;

public class BlockMicrowave extends BlockMR implements ITileEntityProvider {

	IIcon active = null;

	public BlockMicrowave() {
		super(Material.iron, "microwave");

		setHardness(4.0f);

		isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileMicrowave();
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		active = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "microwaveOn");
	}

	@Override
	public IIcon getIcon(IBlockAccess ba, int x, int y, int z, int m) {
		TileEntity te = ba.getTileEntity(x, y, z);
		if (te != null && te instanceof TileMicrowave) {
			TileMicrowave tm = (TileMicrowave) te;
			if (tm.isRunning()) {
				return active;
			}
		}
		return super.getIcon(ba, x, y, z, m);
	}

}
