package us.mcsw.minerad.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import us.mcsw.minerad.init.FusionRecipes;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TileFusionReactor;
import us.mcsw.minerad.tiles.TileMultiblock;

public class BlockFusionReactor extends BlockMultiblock {

	@SideOnly(Side.CLIENT)
	IIcon active = null, activeCore = null, activeDepleted = null, activeOre = null, activeOreCompleted = null,
			activeCoolant = null;

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
				if (mas.hasCore) {
					if (mas.coreX == x && mas.coreY == y && mas.coreZ == z) {
						ItemStack core = !mas.isCoreDepleted() ? new ItemStack(ModItems.fusionCore, 1, mas.coreDamage)
								: new ItemStack(ModItems.emptyCore);
						if (!w.isRemote) {
							EntityItem drop = new EntityItem(w);
							drop.setPosition(x, y, z);
							drop.setEntityItemStack(core);
							w.spawnEntityInWorld(drop);
						}

						mas.hasCore = false;
						mas.coreDamage = -1;
						mas.coreX = mas.coreY = mas.coreZ = 0;
						w.markBlockForUpdate(mX, mY, mZ);
						w.markBlockForUpdate(x, y, z);
					}
				} else {
					ItemStack held = p.getHeldItem();
					if (held != null && held.getItem().equals(ModItems.fusionCore)) {
						mas.hasCore = true;
						mas.coreDamage = held.getItemDamage();
						mas.coreX = x;
						mas.coreY = y;
						mas.coreZ = z;
						w.markBlockForUpdate(mX, mY, mZ);
						w.markBlockForUpdate(x, y, z);
						held.stackSize--;
					}
				}

				if (mas.hasOre) {
					if (mas.oreX == x && mas.oreY == y && mas.oreZ == z) {
						ItemStack ore = !mas.isOreCompleted() ? new ItemStack(mas.source, 1, mas.oreProgress)
								: new ItemStack(FusionRecipes.getResultFrom(mas.source));
						if (!w.isRemote) {
							EntityItem drop = new EntityItem(w);
							drop.setPosition(x, y, z);
							drop.setEntityItemStack(ore);
							w.spawnEntityInWorld(drop);
						}

						mas.hasOre = false;
						mas.oreProgress = -1;
						mas.oreX = mas.oreY = mas.oreZ = 0;
						mas.source = null;
						w.markBlockForUpdate(mX, mY, mZ);
						w.markBlockForUpdate(x, y, z);
					}
				} else {
					ItemStack held = p.getHeldItem();
					if (held != null && FusionRecipes.hasRecipe(held.getItem())) {
						mas.hasOre = true;
						mas.oreProgress = held.getItemDamage();
						mas.oreX = x;
						mas.oreY = y;
						mas.oreZ = z;
						mas.source = held.getItem();
						mas.maxNeeded = held.getItem().getMaxDamage();
						w.markBlockForUpdate(mX, mY, mZ);
						w.markBlockForUpdate(x, y, z);
						held.stackSize--;
					}
				}

				if (mas.hasCoolant) {
					if (mas.coolantX == x && mas.coolantY == y && mas.coolantZ == z) {
						ItemStack core = !mas.isCoolantDepleted()
								? new ItemStack(ModItems.coolantCore, 1, mas.coolantDamage)
								: new ItemStack(ModItems.emptyCore);
						if (!w.isRemote) {
							EntityItem drop = new EntityItem(w);
							drop.setPosition(x, y, z);
							drop.setEntityItemStack(core);
							w.spawnEntityInWorld(drop);
						}

						mas.hasCoolant = false;
						mas.coolantDamage = -1;
						mas.coolantX = mas.coolantY = mas.coolantZ = 0;
						w.markBlockForUpdate(mX, mY, mZ);
						w.markBlockForUpdate(x, y, z);
					}
				} else {
					ItemStack held = p.getHeldItem();
					if (held != null && held.getItem().equals(ModItems.coolantCore)) {
						mas.hasCoolant = true;
						mas.coolantDamage = held.getItemDamage();
						mas.coolantX = x;
						mas.coolantY = y;
						mas.coolantZ = z;
						w.markBlockForUpdate(mX, mY, mZ);
						w.markBlockForUpdate(x, y, z);
						held.stackSize--;
					}
				}
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
		activeCore = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "fusionReactorCompleteCore");
		activeDepleted = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "fusionReactorCompleteDepleted");
		activeOre = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "fusionReactorOre");
		activeOreCompleted = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "fusionReactorOreComplete");
		activeCoolant = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "fusionReactorCoolant");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess ba, int x, int y, int z, int side) {
		TileEntity tile = ba.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileFusionReactor) {
			TileFusionReactor tf = (TileFusionReactor) tile;
			if (tf.hasMaster()) {
				TileMultiblock mas = tf.getMaster();
				if (mas != null && mas instanceof TileFusionReactor) {
					TileFusionReactor tfm = (TileFusionReactor) mas;
					if (tfm.hasCore && tfm.coreX == x && tfm.coreY == y && tfm.coreZ == z) {
						if (!tfm.isCoreDepleted()) {
							return activeCore;
						} else {
							return activeDepleted;
						}
					}

					if (tfm.hasOre && tfm.oreX == x && tfm.oreY == y && tfm.oreZ == z) {
						if (!tfm.isOreCompleted()) {
							return activeOre;
						} else {
							return activeOreCompleted;
						}
					}

					if (tfm.hasCoolant && tfm.coolantX == x && tfm.coolantY == y && tfm.coolantZ == z) {
						if (!tfm.isCoolantDepleted()) {
							return activeCoolant;
						} else {
							return activeDepleted;
						}
					}
				}
				return active;
			}
		}
		return super.getIcon(ba, x, y, z, side);
	}

}
