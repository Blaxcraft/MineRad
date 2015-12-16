package us.mcsw.minerad.items;

import java.util.List;

import com.typesafe.config.Config;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.gui.ItemInventory;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.ref.TextureReference;

public class ItemDensePouch extends ItemMR {

	IIcon background, foreground;

	public ItemDensePouch() {
		super("densePouch");

		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack it, World w, EntityPlayer pl) {
		ItemInventory inv = ItemInventory.getFromItem(it, ConfigMR.DENSE_POUCH_ROWS * 9);
		pl.openGui(MineRad.ins, MachineReference.DENSE_POUCH_ID, w, 0, 0, 0);
		return it;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		ItemStack add = new ItemStack(item);
		ItemInventory.createInItem(add, ConfigMR.DENSE_POUCH_ROWS * 9);
		list.add(add);
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		background = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "densePouchBackground");
		foreground = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "densePouchForeground");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (pass == 0)
			return background;
		return foreground;
	}

	@Override
	public boolean hasEffect(ItemStack it, int pass) {
		return pass == 0;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}

}
