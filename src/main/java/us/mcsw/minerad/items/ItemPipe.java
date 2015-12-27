package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import us.mcsw.core.ItemBlockMR;
import us.mcsw.core.util.LangUtil;
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
	public int getDamage(ItemStack stack) {
		return CapacitorTier.getFromItemStack(stack).getId();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public String getItemStackDisplayName(ItemStack it) {
		return super.getItemStackDisplayName(it) + " ["
				+ LangUtil.translate("description.pipe." + CapacitorTier.getFromItemStack(it).getLowercaseName()) + "]";
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
		if (CapacitorTier.getFromItemStack(it) != null) {
			list.add("Transfers " + CapacitorTier.getFromItemStack(it).getMaxTransferPipe() + " RF/t");
		}
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (CapacitorTier tier : CapacitorTier.tiers) {
			ItemStack stack = new ItemStack(item);
			CapacitorTier.setInItemStack(stack, tier);
			list.add(stack);
		}
	}

}
