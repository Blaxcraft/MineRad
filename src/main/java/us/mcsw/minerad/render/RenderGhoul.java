package us.mcsw.minerad.render;

import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import us.mcsw.minerad.entity.EntityFeralGhoul;
import us.mcsw.minerad.ref.TextureReference;

public class RenderGhoul extends RenderZombie {

	@Override
	protected ResourceLocation getEntityTexture(EntityZombie zom) {
		EntityFeralGhoul gh = (EntityFeralGhoul) zom;
		return gh.isGlowing() ? TextureReference.GLOWING_GHOUL_TEXTURE : TextureReference.GHOUL_TEXTURE;
	}

}
