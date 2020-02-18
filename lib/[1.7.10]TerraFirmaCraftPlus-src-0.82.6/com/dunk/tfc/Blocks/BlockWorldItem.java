package com.dunk.tfc.Blocks;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.TileEntities.TEWorldItem;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWorldItem extends BlockTerraContainer
{
	public BlockWorldItem()
	{
		super(Material.circuits);
		this.setBlockBounds(0F, 0.00F, 0F, 1F, 0.05F, 1F);
		this.setTickRandomly(true);
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	/*
	 * @Override public void harvestBlock(World world, EntityPlayer
	 * entityplayer, int x, int y, int z, int l) { }
	 */

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta)
	{
		if (!world.isRemote)
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof IInventory)
			{
				IInventory inv = (IInventory) te;
				for (int i = 0; i < inv.getSizeInventory(); i++)
				{
					if (inv.getStackInSlot(i) != null)
					{
						EntityItem ei = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, inv.getStackInSlot(i));
						inv.setInventorySlotContents(i, null); // so it is not
																// created again
																// in
																// super.breakBlock()
						ei.motionX = 0;
						ei.motionY = 0;
						ei.motionZ = 0;
						world.spawnEntityInWorld(ei);
					}
				}
			}
		}
		super.onBlockPreDestroy(world, x, y, z, meta);
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public int tickRate(World world)
	{
		return 50;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random r)
	{
		if (!world.isRemote)
		{
			TEWorldItem te = (TEWorldItem) world.getTileEntity(x, y, z);

			float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);
			boolean inRain =  TFC_Core.isExposedToRain(world, x, y, z);
			if (temp <= 0 && inRain) // Raining
																					// and
																					// Below
																					// Freezing
			{
				if (r.nextInt(20) == 0)
				{
					int max = 8;
					if (te.snowLevel < max)
					{
						te.snowLevel++;
						world.setBlockMetadataWithNotify(x, y, z, 0, 2);
						te.broadcastPacketInRange();
					}
				}
			}
			else if (temp > 10) // to hot for snow (probably chunk loading
								// error)
			{
				te.snowLevel = 0;
				world.setBlockMetadataWithNotify(x, y, z, 0, 2);
				te.broadcastPacketInRange();
			}
			else if (temp > 0 && te.snowLevel > 0 && inRain) // Raining
			// and
			// above
			// freezing
			{
				if (r.nextInt(5) == 0)
				{
					te.snowLevel--;
					world.setBlockMetadataWithNotify(x, y, z, 0, 2);
					te.broadcastPacketInRange();
				}
			}
			else if (temp > 0) // Above freezing, not raining
			{
				if (r.nextInt(20) == 0)
				{
					if (te.snowLevel > 0)
					{
						te.snowLevel--;
						world.setBlockMetadataWithNotify(x, y, z, 0, 2);
						te.broadcastPacketInRange();
					}
				}
			}
		}
		else
		{
			world.getTileEntity(x, y, z).validate();
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX,
			float hitY, float hitZ)
	{
		if (!world.isRemote)
			return world.setBlockToAir(x, y, z);
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (world.isAirBlock(x, y - 1, z))
		{
			world.setBlockToAir(x, y, z);
			return;
		}
		if (!world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP))
		{
			world.setBlockToAir(x, y, z);
			return;
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public IIcon getIcon(int i, int m)
	{
		return TFCBlocks.snow.getIcon(i, m);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return null;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.snowRenderId;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.25, z + 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = TFC_Textures.invisibleTexture; // This gets registered
														// in BlockGrass
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TEWorldItem();
	}
}
