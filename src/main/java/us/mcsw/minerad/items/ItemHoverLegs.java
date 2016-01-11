package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import us.mcsw.core.ItemMRArmour;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ref.PlayerProperties;
import us.mcsw.minerad.ref.TextureReference;

public class ItemHoverLegs extends ItemMRArmour {

	static final ArmorMaterial material = EnumHelper.addArmorMaterial("hoverLegs", 150, new int[] { 0, 0, 6, 0 }, 20);

	public ItemHoverLegs() {
		super("hoverLegs", material, 0, 2);
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
		super.addInformation(it, pl, list, n);
		list.add("Who needs jetpacks when you");
		list.add(" have legs like THESE?");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return slot == 2 ? TextureReference.HOVER_LEGS_TEXTURE.toString() : null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (!player.capabilities.allowFlying) {
			player.capabilities.allowFlying = true;
			PlayerProperties.get(player).wearingHoverLegs = true;
		}
		super.onArmorTick(world, player, itemStack);
	}

}
