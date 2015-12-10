package us.mcsw.minerad.blocks;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.BlockMR;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ref.CapacitorTier;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TilePipe;
import us.mcsw.minerad.tiles.TileRadioTowerAntenna;

public class BlockPipe extends BlockMR implements ITileEntityProvider {

	public BlockPipe() {
		super(Material.iron, "pipeBlock");
		setBlockTextureName("pipeBlockParticles");

		setHardness(2.0f);
		isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TilePipe();
	};

	@Override
	public boolean isSideSolid(IBlockAccess w, int x, int y, int z, ForgeDirection side) {
		return false;
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
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public void onBlockHarvested(World w, int x, int y, int z, int m, EntityPlayer pl) {
		if (!pl.capabilities.isCreativeMode) {
			TileEntity tile = w.getTileEntity(x, y, z);
			if (tile != null && tile instanceof TilePipe) {
				TilePipe tp = (TilePipe) tile;

				ItemStack it = new ItemStack(this);
				CapacitorTier.setInItemStack(it, tp.getTier());

				EntityItem drop = new EntityItem(w);
				drop.setPosition(x + 0.5, y + 0.5, z + 0.5);
				drop.setEntityItemStack(it);
				w.spawnEntityInWorld(drop);
			}
		}
		super.onBlockHarvested(w, x, y, z, m, pl);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess ba, int x, int y, int z) {
		float startX = 0.375f;
		float startY = 0.375f;
		float startZ = 0.375f;
		float endX = 0.625f;
		float endY = 0.625f;
		float endZ = 0.625f;
		TileEntity tile = (TileEntity) ba.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TilePipe) {
			TilePipe te = (TilePipe) tile;
			if (te.isConnected(ForgeDirection.UP)) {
				endY = 1f;
			}
			if (te.isConnected(ForgeDirection.DOWN)) {
				startY = 0;
			}
			if (te.isConnected(ForgeDirection.NORTH)) {
				startZ = 0;
			}
			if (te.isConnected(ForgeDirection.SOUTH)) {
				endZ = 1f;
			}
			if (te.isConnected(ForgeDirection.WEST)) {
				startX = 0;
			}
			if (te.isConnected(ForgeDirection.EAST)) {
				endX = 1f;
			}
		}
		setBlockBounds(startX, startY, startZ, endX, endY, endZ);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "pipeBlockParticles");
	}

}
