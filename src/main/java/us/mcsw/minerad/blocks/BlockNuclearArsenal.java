package us.mcsw.minerad.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import us.mcsw.core.BlockMR;
import us.mcsw.core.util.ChatUtil;
import us.mcsw.minerad.init.AchievementsInit;
import us.mcsw.minerad.init.ModItems;
import us.mcsw.minerad.ref.PlayerProperties;

public class BlockNuclearArsenal extends BlockMR {

	public BlockNuclearArsenal() {
		super(Material.iron, "nuclearArsenal");

		setHardness(5.0f);
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int m, float cx, float cy, float cz) {
		PlayerProperties props = PlayerProperties.get(p);
		if (!w.isRemote && props != null) {
			p.addStat(AchievementsInit.nuclearArsenal, 1);
			if (p.getHeldItem() != null) {
				if (p.getHeldItem().getItem().equals(ModItems.nuclearArsenalUpgrade)) {
					p.getHeldItem().stackSize--;
					props.maxNukes++;
					ChatUtil.sendTranslatedTo(p, "message.arsenal.upgrade", props.maxNukes);
					return true;
				}
				if (p.getHeldItem().getItem().equals(ModItems.nukePackage)) {
					if (props.hasArsenal) {
						if (props.nukes < props.maxNukes) {
							props.nukes++;
							ChatUtil.sendTranslatedTo(p, "message.nukePackage.info", props.nukes);
							p.getHeldItem().stackSize--;
						} else {
							ChatUtil.sendTranslatedTo(p, "message.nukePackage.full", props.nukes, props.maxNukes);
						}
					} else {
						ChatUtil.sendTranslatedTo(p, "message.nukePackage.fail");
					}
					return true;
				}
			}
			if (props.hasArsenal) {
				ChatUtil.sendTranslatedTo(p, "message.arsenal.info", props.nukes, props.maxNukes);
			} else {
				props.hasArsenal = true;
				ChatUtil.sendTranslatedTo(p, "message.arsenal.added");
			}
		}
		return true;
	}

}
