package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.ref.TextureReference;

public class ItemEmptyCore extends ItemMR {

	public ItemEmptyCore() {
		super("emptyCore");
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		MachineReference.CORE_BACKGROUND = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "coreBackground");
	}

}
