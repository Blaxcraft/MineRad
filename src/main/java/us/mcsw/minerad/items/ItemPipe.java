package us.mcsw.minerad.items;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import us.mcsw.core.ItemBlockMR;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ref.CapacitorTier;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TilePipe;

public class ItemPipe extends ItemBlockMR {

	IIcon fore = null, back = null;

	public ItemPipe(Block block) {
		super(block);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ, int metadata) {
		if (super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TilePipe) {
				TilePipe tp = (TilePipe) te;
				tp.setTier(CapacitorTier.getFromItemStack(stack));
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		fore = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "pipeItemFore");
		back = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "pipeItemBack");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (pass == 0) {
			return back;
		}
		return fore;
	}

	@Override
	public int getColorFromItemStack(ItemStack it, int pass) {
		if (pass == 1)
			return CapacitorTier.getFromItemStack(it).getColour();
		return super.getColorFromItemStack(it, pass);
	}

	@Override
	public String getItemStackDisplayName(ItemStack it) {
		return super.getItemStackDisplayName(it) + " [" + LanguageRegistry.instance().getStringLocalization(
				"description.pipe." + CapacitorTier.getFromItemStack(it).getLowercaseName()) + "]";
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (CapacitorTier tier : CapacitorTier.values()) {
			ItemStack stack = new ItemStack(item);
			CapacitorTier.setInItemStack(stack, tier);
			list.add(stack);
		}
	}

}
