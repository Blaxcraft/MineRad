package us.mcsw.minerad.items;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import us.mcsw.core.util.LangUtil;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.ref.CapacitorTier;
import us.mcsw.minerad.tiles.TileEnergyStorage;
import us.mcsw.minerad.tiles.TilePipe;

public class ItemEnergyStorage extends ItemBlock implements IEnergyContainerItem {

	public ItemEnergyStorage(Block block) {
		super(block);

		setMaxStackSize(1);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return super.getIcon(stack, pass);
	}

	@Override
	public String getItemStackDisplayName(ItemStack it) {
		return super.getItemStackDisplayName(it) + " ["
				+ LangUtil.translate("description.pipe." + CapacitorTier.getFromItemStack(it).getLowercaseName()) + "]";
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer pl, List list, boolean n) {
		list.add(getFromItemstack(it).getEnergyStored() + " / " + getFromItemstack(it).getMaxEnergyStored() + " RF");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (CapacitorTier tier : CapacitorTier.tiers) {
			ItemStack stack = new ItemStack(item);
			CapacitorTier.setInItemStack(stack, tier);
			list.add(stack);

			ItemStack full = new ItemStack(item);
			CapacitorTier.setInItemStack(full, tier);
			setEnergyInItemStack(full, tier.getStorageCapacity());
			list.add(full);
		}
	}

	public int getDamageFromEnergy(ItemStack stack) {
		EnergyStorage storage = getFromItemstack(stack);
		return (int) ((double) (storage.getMaxEnergyStored() - storage.getEnergyStored())
				/ (double) storage.getMaxEnergyStored() * (double) getMaxDamage(stack));
	}

	@Override
	public int getDamage(ItemStack stack) {
		return CapacitorTier.getFromItemStack(stack).getId();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ, int metadata) {
		if (super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileEnergyStorage) {
				TileEnergyStorage ts = (TileEnergyStorage) te;
				ts.setTier(CapacitorTier.getFromItemStack(stack));
				ts.storage.setEnergyStored(getFromItemstack(stack).getEnergyStored());
			}
			return true;
		}
		return false;
	}

	public EnergyStorage getFromItemstack(ItemStack it) {
		if (!it.hasTagCompound()) {
			it.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound data = it.getTagCompound();
		if (!data.hasKey("energy")) {
			data.setInteger("energy", 0);
		}
		CapacitorTier tier = CapacitorTier.getFromItemStack(it);
		EnergyStorage ret = new EnergyStorage(tier.getStorageCapacity(), tier.getMaxTransferPipe());
		ret.setEnergyStored(data.getInteger("energy"));
		return ret;
	}

	public void setEnergyInItemStack(ItemStack it, int energy) {
		EnergyStorage current = getFromItemstack(it);
		current.setEnergyStored(energy);

		it.getTagCompound().setInteger("energy", current.getEnergyStored());
		it.setItemDamage(getDamage(it));
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		EnergyStorage storage = getFromItemstack(container);
		int ret = storage.receiveEnergy(maxReceive, simulate);
		if (!simulate) {
			setEnergyInItemStack(container, storage.getEnergyStored());
		}
		return ret;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		EnergyStorage storage = getFromItemstack(container);
		int ret = storage.extractEnergy(maxExtract, simulate);
		if (!simulate) {
			setEnergyInItemStack(container, storage.getEnergyStored());
		}
		return ret;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return getFromItemstack(container).getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return getFromItemstack(container).getMaxEnergyStored();
	}

}
