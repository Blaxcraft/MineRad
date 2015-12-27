package us.mcsw.minerad.blocks;

import java.util.ArrayList;

import cofh.api.energy.EnergyStorage;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.BlockMR;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.items.ItemEnergyStorage;
import us.mcsw.minerad.ref.CapacitorTier;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.render.RendererEnergyStorage;
import us.mcsw.minerad.tiles.TileEnergyStorage;
import us.mcsw.minerad.tiles.TilePipe;

public class BlockEnergyStorage extends BlockMR implements ITileEntityProvider {

	public BlockEnergyStorage() {
		super(Material.iron, "energyStorage");
		setBlockTextureName("pipeBlockParticles");

		setHardness(4.0f);

		isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileEnergyStorage();
	}

	@Override
	public int getRenderType() {
		return RendererEnergyStorage.getStaticRenderId();
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
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer pl, int side, float cx, float cy,
			float cz) {
		if (!pl.isSneaking()) {
			TileEnergyStorage storage = (TileEnergyStorage) w.getTileEntity(x, y, z);
			if (storage != null) {
				ForgeDirection s = ForgeDirection.getOrientation(side);
				storage.setOutputtingToSide(s, !storage.isOutputtingToSide(s));
			}
			return true;
		}
		return false;
	}

	@Override
	public void onBlockHarvested(World w, int x, int y, int z, int m, EntityPlayer pl) {
		if (!pl.capabilities.isCreativeMode) {
			TileEntity tile = w.getTileEntity(x, y, z);
			if (tile != null && tile instanceof TileEnergyStorage) {
				TileEnergyStorage ts = (TileEnergyStorage) tile;

				ItemStack it = new ItemStack(this, 1, ts.getTier().getId());
				CapacitorTier.setInItemStack(it, ts.getTier());
				ItemEnergyStorage i = (ItemEnergyStorage) it.getItem();
				i.setEnergyInItemStack(it, ts.getEnergyStored(null));

				EntityItem drop = new EntityItem(w);
				drop.setPosition(x + 0.5, y + 0.5, z + 0.5);
				drop.setEntityItemStack(it);
				w.spawnEntityInWorld(drop);
			}
		}
		super.onBlockHarvested(w, x, y, z, m, pl);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "pipeBlockParticles");
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		ItemStack stack = super.getPickBlock(target, world, x, y, z, player);
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEnergyStorage) {
			TileEnergyStorage es = (TileEnergyStorage) te;
			CapacitorTier.setInItemStack(stack, es.getTier());
			((ItemEnergyStorage) stack.getItem()).setEnergyInItemStack(stack, es.getEnergyStored(null));
		}
		return stack;
	}

}
