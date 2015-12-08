package us.mcsw.minerad.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.minerad.init.ModBlocks;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TileRadioTowerAntenna;
import us.mcsw.minerad.util.LogUtil;

public class BlockRadioTowerAntenna extends BlockMR implements ITileEntityProvider {

	public BlockRadioTowerAntenna() {
		super(Material.iron, "radioTowerAntenna");

		setHardness(3.0f);
		setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 1.0f, 0.75f);
		isBlockContainer = true;
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer pl, int m, float cx, float cy,
			float cz) {
		ItemStack held = pl.inventory.getCurrentItem();
		if (held != null) {
			if (Block.getBlockFromItem(held.getItem()).equals(ModBlocks.radioTowerAntenna)) {
				for (int check = y; check < w.getHeight(); check++) {
					if (w.isAirBlock(x, check, z)) {
						w.setBlock(x, check, z, this);
						held.stackSize--;
						return true;
					} else if (!w.getBlock(x, check, z).equals(this)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		if (!w.getBlock(x, y - 1, z).isSideSolid(w, x, y, z, ForgeDirection.UP)) {
			EntityItem i = new EntityItem(w);
			i.setPosition(x + 0.5, y + 0.5, z + 0.5);
			i.setEntityItemStack(new ItemStack(ModBlocks.radioTowerAntenna));
			i.delayBeforeCanPickup = 20;
			w.spawnEntityInWorld(i);

			w.setBlockToAir(x, y, z);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z) {
		return w.getBlock(x, y - 1, z).isSideSolid(w, x, y, z, ForgeDirection.UP);
	}

	@Override
	public boolean isSideSolid(IBlockAccess w, int x, int y, int z, ForgeDirection side) {
		return side == ForgeDirection.UP || side == ForgeDirection.DOWN;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileRadioTowerAntenna();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "radioTowerAntennaIcon");
	}

}
