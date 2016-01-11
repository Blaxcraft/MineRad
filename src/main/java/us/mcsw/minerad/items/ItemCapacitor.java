package us.mcsw.minerad.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import us.mcsw.core.ItemMR;
import us.mcsw.minerad.ref.CapacitorTier;
import us.mcsw.minerad.ref.TextureReference;

public class ItemCapacitor extends ItemMR {

	IIcon background, foreground;
	CapacitorTier type;

	public ItemCapacitor(CapacitorTier type) {
		super("capacitor" + type.getCapitalizedName());
		this.type = type;
		setTextureName(TextureReference.RESOURCE_PREFIX + "capacitorBackground");
	}

	@Override
	public boolean hasEffect(ItemStack it, int pass) {
		return pass == 0;
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		background = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "capacitorBackground");
		foreground = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "capacitorForeground");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (pass == 1) {
			return background;
		}
		return foreground;
	}

	@Override
	public int getColorFromItemStack(ItemStack it, int pass) {
		if (pass == 0) {
			return type.getColour();
		}
		return super.getColorFromItemStack(it, pass);
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
