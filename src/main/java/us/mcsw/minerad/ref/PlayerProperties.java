package us.mcsw.minerad.ref;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import us.mcsw.core.util.ChatUtil;
import us.mcsw.core.util.LangUtil;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.items.ItemRadArmour;
import us.mcsw.minerad.net.MessageUpdateProperties;

public class PlayerProperties implements IExtendedEntityProperties {

	public static final String PROPERTIES_NAME = MineRad.MODID + "_PlayerProps";

	private final EntityPlayer p;

	public int nukes, maxNukes, ghoulTicks;
	public boolean hasArsenal;
	boolean isGhoul, isGlowing;

	public boolean wearingHoverLegs = false;

	public PlayerProperties(EntityPlayer p) {
		this.p = p;
		this.nukes = 0;
		this.maxNukes = ConfigMR.INITIAL_ARSENAL_CAPACITY;
		this.ghoulTicks = 0;
		this.hasArsenal = false;
		this.isGhoul = false;
		this.isGlowing = false;
		this.wearingHoverLegs = false;
	}

	public static final void register(EntityPlayer p) {
		p.registerExtendedProperties(PROPERTIES_NAME, new PlayerProperties(p));
	}

	public static final PlayerProperties get(EntityPlayer p) {
		return (PlayerProperties) p.getExtendedProperties(PROPERTIES_NAME);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("nukes", nukes);
		data.setInteger("maxNukes", maxNukes);
		data.setBoolean("hasArsenal", hasArsenal);
		data.setBoolean("isGhoul", isGhoul);
		data.setBoolean("isGlowing", isGlowing);
		data.setInteger("ghoulTicks", ghoulTicks);
		data.setBoolean("wearingHoverBoots", wearingHoverLegs);
		compound.setTag(PROPERTIES_NAME, data);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound data = (NBTTagCompound) compound.getTag(PROPERTIES_NAME);
		this.nukes = data.getInteger("nukes");
		this.maxNukes = data.getInteger("maxNukes");
		this.hasArsenal = data.getBoolean("hasArsenal");
		this.isGhoul = data.getBoolean("isGhoul");
		this.isGlowing = data.getBoolean("isGlowing");
		this.ghoulTicks = data.getInteger("ghoulTicks");
		this.wearingHoverLegs = data.getBoolean("wearingHoverBoots");
	}

	public boolean isGhoul() {
		return isGhoul;
	}

	public boolean isGlowingGhoul() {
		return isGhoul && isGlowing;
	}

	public void onBecomeGhoul() {
		RadProperties props = RadProperties.get(p);
		props.setRadiation(0);
		isGhoul = true;
		isGlowing = false;
		ghoulTicks = 0;

		p.addChatComponentMessage(new ChatComponentTranslation("info.ghoul.transform")
				.setChatStyle(new ChatStyle().setItalic(true).setColor(EnumChatFormatting.GREEN)));

		sendUpdate();
	}

	public void onBecomeGlowing() {
		isGhoul = true;
		isGlowing = true;
		ghoulTicks = 0;

		p.addChatComponentMessage(new ChatComponentTranslation("info.ghoul.glowing.transform")
				.setChatStyle(new ChatStyle().setItalic(true).setColor(EnumChatFormatting.GREEN)));

		sendUpdate();
	}

	public void onBecomeHuman() {
		isGhoul = false;
		isGlowing = false;
		ghoulTicks = 0;

		p.addChatComponentMessage(new ChatComponentTranslation("info.ghoul.transform.human")
				.setChatStyle(new ChatStyle().setItalic(true).setColor(EnumChatFormatting.GOLD)));

		sendUpdate();
	}

	private void sendUpdate() {
		if (p instanceof EntityPlayerMP)
			MineRad.network.sendTo(new MessageUpdateProperties(this, PROPERTIES_NAME, p.getEntityId()),
					(EntityPlayerMP) p);
	}

	@Override
	public void init(Entity entity, World world) {
	}

}
