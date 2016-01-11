package us.mcsw.minerad.render;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import us.mcsw.minerad.entity.EntityFinalBoss;
import us.mcsw.minerad.ref.TextureReference;

public class RenderFinalBoss extends RenderBiped {

	public RenderFinalBoss() {
		super(new ModelFinalBoss(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity e) {
		return TextureReference.BOSS_TEXTURE;
	}

	public void showBossBar(Entity e) {
		if (e instanceof EntityFinalBoss) {
			EntityFinalBoss ef = (EntityFinalBoss) e;
			BossStatus.setBossStatus(ef, true);
		}
	}

	@Override
	public void doRender(EntityLivingBase elb, double x, double y, double z, float f1, float f2) {
		showBossBar(elb);
		super.doRender(elb, x, y, z, f1, f2);
	}

	@Override
	public void doRender(Entity e, double x, double y, double z, float f1, float f2) {
		showBossBar(e);
		super.doRender(e, x, y, z, f1, f2);
	}
	
	@Override
	public void doRender(EntityLiving e, double x, double y, double z, float f1, float f2) {
		showBossBar(e);
		super.doRender(e, x, y, z, f1, f2);
	}

}
