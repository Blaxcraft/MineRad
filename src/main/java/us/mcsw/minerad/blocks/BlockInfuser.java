package us.mcsw.minerad.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import us.mcsw.core.BlockMRMachine;
import us.mcsw.minerad.ref.MachineReference;
import us.mcsw.minerad.ref.TextureReference;
import us.mcsw.minerad.tiles.TileInfuser;

public class BlockInfuser extends BlockMRMachine implements ITileEntityProvider {

	public BlockInfuser() {
		super(MachineReference.INFUSER_ID, Material.iron, "infuser");
		setHardness(0.5f);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		return new TileInfuser();
	}

}
