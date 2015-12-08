package us.mcsw.minerad.items;

import com.sun.prism.Texture;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.util.LogUtil;

public class ItemCapacitor extends ItemMR {

	IIcon background, foreground;
	public int colour;

	public ItemCapacitor(String id, int colour) {
		super("capacitor" + id);
		setTextureName(TextureReference.RESOURCE_PREFIX + "capacitorBackground");
		this.colour = colour;
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
			return colour;
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
