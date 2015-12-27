package us.mcsw.minerad.ref;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import us.mcsw.minerad.ConfigMR;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.items.ItemRadArmour;

public class PlayerProperties implements IExtendedEntityProperties {

	public static final String PROPERTIES_NAME = MineRad.MODID + "_PlayerProps";

	private final EntityPlayer p;
	
	public int nukes, maxNukes;
	public boolean hasArsenal;

	public PlayerProperties(EntityPlayer p) {
		this.p = p;
		this.nukes = 0;
		this.maxNukes = ConfigMR.INITIAL_ARSENAL_CAPACITY;
		this.hasArsenal = false;
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
		compound.setTag(PROPERTIES_NAME, data);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound data = (NBTTagCompound) compound.getTag(PROPERTIES_NAME);
		this.nukes = data.getInteger("nukes");
		this.maxNukes = data.getInteger("maxNukes");
		this.hasArsenal = data.getBoolean("hasArsenal");
	}

	@Override
	public void init(Entity entity, World world) {
	}

}
