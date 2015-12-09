package us.mcsw.minerad.blocks;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.BlockMR;
import us.mcsw.minerad.tiles.TileTestRF;

public class BlockTest extends BlockMR implements ITileEntityProvider {

	public BlockTest() {
		super(Material.iron, "blockTest");
		setBlockTextureName(null);
		
		isBlockContainer = true;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (ForgeDirection.getOrientation(side) == ForgeDirection.UP
				|| ForgeDirection.getOrientation(side) == ForgeDirection.DOWN) {
			return Blocks.obsidian.getIcon(side, meta);
		}
		if (ForgeDirection.getOrientation(side) == ForgeDirection.SOUTH
				|| ForgeDirection.getOrientation(side) == ForgeDirection.NORTH) {
			return Blocks.stone.getIcon(side, meta);
		}
		if (ForgeDirection.getOrientation(side) == ForgeDirection.WEST
				|| ForgeDirection.getOrientation(side) == ForgeDirection.EAST) {
			return Blocks.dirt.getIcon(side, meta);
		}
		return super.getIcon(side, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileTestRF();
	}

}
