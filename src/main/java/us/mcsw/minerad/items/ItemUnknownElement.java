package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import us.mcsw.core.ItemMR;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.ref.RadProperties;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.util.RadUtil;

public class ItemUnknownElement extends ItemMR {

	IIcon background, foreground;

	public ItemUnknownElement() {
		super("unknownElement");
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
		super.addInformation(it, pl, list, n);
		list.add("It's a mystery!");
		list.add("Highly unstable");
		list.add(EnumChatFormatting.RED + "+ " + (ConfigMR.UNKNOWN_ELEMENT_RADIATION * it.stackSize) + " RAD/s");
	}

	@Override
	public boolean hasEffect(ItemStack it, int pass) {
		return pass == 0;
	}

	static final EnumRarity specialRarity = EnumHelper.addRarity("special", EnumChatFormatting.DARK_GRAY, "Special");

	@Override
	public EnumRarity getRarity(ItemStack it) {
		return specialRarity;
	}

	@Override
	public void onUpdate(ItemStack it, World w, Entity e, int m, boolean n) {
		if (!w.isRemote) {
			if (e instanceof EntityPlayer && RadProperties.get(e) != null) {
				EntityPlayer pl = (EntityPlayer) e;
				RadProperties props = RadProperties.get(pl);
				props.addRadiation((double) ConfigMR.UNKNOWN_ELEMENT_RADIATION * it.stackSize / 20.0, false);
			}
		}
		super.onUpdate(it, w, e, m, n);
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		background = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "unknownElementBackground");
		foreground = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "unknownElementForeground");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (pass == 0) {
			return background;
		} else
			return foreground;
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
