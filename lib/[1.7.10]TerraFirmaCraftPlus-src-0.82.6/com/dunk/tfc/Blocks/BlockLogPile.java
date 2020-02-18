package com.dunk.tfc.Blocks;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.TileEntities.TEChimney;
import com.dunk.tfc.TileEntities.TEFirepit;
import com.dunk.tfc.TileEntities.TELogPile;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLogPile extends BlockTerraContainer
{
	private IIcon[] icons = new IIcon[3];

	public BlockLogPile()
	{
		super(Material.wood);
		this.setTickRandomly(true);
	}

	public static int getDirectionFromMetadata(int i)
	{
		return i & 3;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return checkIsFlammable(world, x, y, z, face) ? Blocks.fire.getFlammability(this) : -1;
	}

	@Override
	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
	{
		if (world.getTileEntity(x, y, z) instanceof TELogPile && side == UP)
		{
			if (((TELogPile) world.getTileEntity(x, y, z)).isOnFire)
				return true;
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7,
			float par8, float par9)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			if ((TELogPile) world.getTileEntity(i, j, k) != null)
			{
				// TELogPile te;
				// te = (TELogPile)world.getTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				if (is != null && is.getItem() == TFCItems.logs)
				{
					return false;
				}
				else
				{
					entityplayer.openGui(TerraFirmaCraft.instance, 0, world, i, j, k);
				}
				return true;
			}
			else
			{
				return false;
			}

		}

	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		if (j == 0 || j == 2)// +z
		{
			if (i == 0)
			{
				return icons[1];
			}
			else if (i == 1)
			{
				return icons[1];
			}
			else if (i == 2)
			{
				return icons[2];
			}
			else if (i == 3)
			{
				return icons[2];
			}
			else if (i == 4)
			{
				return icons[0];
			}
			else if (i == 5)
			{
				return icons[0];
			}
		}
		else if (j == 1 || j == 3)// -x
		{
			if (i == 0)
			{
				return icons[0];
			}
			else if (i == 1)
			{
				return icons[0];
			}
			else if (i == 2)
			{
				return icons[0];
			}
			else if (i == 3)
			{
				return icons[0];
			}
			else if (i == 4)
			{
				return icons[2];
			}
			else if (i == 5)
			{
				return icons[2];
			}
		}

		return icons[0];

	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		icons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Log Pile Side 0");
		icons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Log Pile Side 1");
		icons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Log Pile End");
	}

	public void eject(World world, int x, int y, int z)
	{
		if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TELogPile)
		{
			TELogPile te = (TELogPile) world.getTileEntity(x, y, z);
			te.ejectContents();
			world.removeTileEntity(x, y, z);
		}
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par3)
	{
		return Item.getItemById(0);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		eject(world, i, j, k);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex)
	{
		eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i)
	{
		eject(world, x, y, z);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		eject(world, x, y, z);
		return world.setBlockToAir(x, y, z); // super.removedByPlayer is
												// deprecated, and causes a
												// loop.
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TELogPile();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TELogPile)
		{
			((TELogPile) world.getTileEntity(x, y, z)).lightNeighbors();
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
	
	private boolean checkIsFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return Blocks.fire.canCatchFire(world, x, y, z,face);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		// Check to see if we have the conditions for the new kiln
		boolean passedCheck = false;
		if (world.getBlock(x, y + 1, z) != Blocks.air && world.getBlock(x, y + 1, z) != Blocks.fire)
		{
			return true;
		}
		if ((world.getBlock(x - 1, y, z) == Blocks.air || world.getBlock(x - 1, y, z) == Blocks.fire) 
				&& world.getBlock(x+1,y,z)!= TFCBlocks.logPile && !checkIsFlammable(world, x + 1, y, z, ForgeDirection.WEST)
				&& world.getBlock(x,y,z-1)!= TFCBlocks.logPile && !checkIsFlammable(world, x, y, z-1, ForgeDirection.SOUTH) 
				&& world.getBlock(x,y,z+1)!= TFCBlocks.logPile && !checkIsFlammable(world, x, y, z+1, ForgeDirection.NORTH))
		{
			passedCheck = true;
		}
		else if ((world.getBlock(x + 1, y, z) == Blocks.air || world.getBlock(x + 1, y, z) == Blocks.fire) 
				&& world.getBlock(x-1,y,z)!= TFCBlocks.logPile && !checkIsFlammable(world, x - 1, y, z, ForgeDirection.EAST) 
				&& world.getBlock(x,y,z-1)!= TFCBlocks.logPile && !checkIsFlammable(world, x, y, z-1,ForgeDirection.SOUTH) 
				&& world.getBlock(x,y,z+1)!= TFCBlocks.logPile && !checkIsFlammable(world, x, y, z+1, ForgeDirection.NORTH))
		{
			passedCheck = true;
		}
		else if ((world.getBlock(x, y, z - 1) == Blocks.air || world.getBlock(x, y, z - 1) == Blocks.fire) 
				&& world.getBlock(x+1,y,z)!= TFCBlocks.logPile && !checkIsFlammable(world, x + 1, y, z, ForgeDirection.WEST) 
				&& world.getBlock(x-1,y,z)!= TFCBlocks.logPile && !checkIsFlammable(world, x - 1, y, z,ForgeDirection.EAST) 
				&& world.getBlock(x,y,z+1)!= TFCBlocks.logPile && !checkIsFlammable(world, x, y, z+1, ForgeDirection.NORTH))
		{
			passedCheck = true;
		}
		else if ((world.getBlock(x, y, z + 1) == Blocks.air || world.getBlock(x, y, z + 1) == Blocks.fire) 
				&& world.getBlock(x,y,z-1)!= TFCBlocks.logPile && !checkIsFlammable(world, x, y, z-1, ForgeDirection.SOUTH) 
				&& world.getBlock(x+1,y,z)!= TFCBlocks.logPile && !checkIsFlammable(world, x + 1, y, z,ForgeDirection.WEST) 
				&& world.getBlock(x-1,y,z)!= TFCBlocks.logPile && !checkIsFlammable(world, x - 1, y, z, ForgeDirection.EAST))
		{
			passedCheck = true;
		}
		if (passedCheck)
		{
			// We check if we havea valid kiln shape
			for (int y2 = 1; y2 <= 4 && passedCheck; y2++)
			{
				for (int i = -2; i <= 2 && passedCheck; i++)
				{
					for (int j = -2; j <= 2 && passedCheck; j++)
					{
						if (y2 == 2||y2==3)
						{
							Block b = world.getBlock(x + i, y + y2, z + j);
							if(i*j == 4 || i * j == -4)
							{
								continue;
							}
							if (Math.abs(i) == 2 || Math.abs(j) == 2)
							{
								if (checkIsFlammable(world, x + i, y + y2, z + j, (i==-2?ForgeDirection.EAST:(i==2?ForgeDirection.WEST:(j==-2?ForgeDirection.SOUTH:ForgeDirection.NORTH)))) ||
										(!b.isSideSolid(world, x + i, y + y2, z + j, (i==-2?ForgeDirection.EAST:(i==2?ForgeDirection.WEST:(j==-2?ForgeDirection.SOUTH:ForgeDirection.NORTH))))))
								{
									passedCheck = false;
								}
							}
							else if (b != TFCBlocks.pottery && b != Blocks.air && b != Blocks.fire)
							{
								passedCheck = false;
							}
						}
						else
						{
							Block b = world.getBlock(x + i, y + y2, z + j);
							if (Math.abs(i) == 2 || Math.abs(j) == 2)
							{
							}
							else if (i != 0 || j != 0)
							{
								if (checkIsFlammable(world, x + i, y + y2, z + j, y2==0?ForgeDirection.UP:ForgeDirection.DOWN) || !b.isNormalCube(world,x + i, y + y2, z + j))
								{
									passedCheck = false;
								}
							}
							else if( y2 == 4)
							{
								if(!((b instanceof BlockChimney) && ((TEChimney)world.getTileEntity(x + i, y + y2, z + j)).canChimneySeeSky()))
								{
									passedCheck = false;
								}
							}
							else
							{
								if (b != Blocks.air && b != Blocks.fire)
								{
									passedCheck = false;
								}
							}
						}
					}
				}
			}
		}
		if (passedCheck)
		{
			/*for (int y2 = 2; y2 < 12; y2++)
			{
				if (world.getBlock(x, y + y2, z) != Blocks.air)
				{
					return true;
				}
			}*/
			return false;
		}
		return true;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (world.getTileEntity(x, y, z) instanceof TELogPile)
		{
			TELogPile te = (TELogPile) world.getTileEntity(x, y, z);

			Block b1, b2, b3, b4;
			b1 = world.getBlock(x - 1, y, z);
			b2 = world.getBlock(x + 1, y, z);
			b3 = world.getBlock(x, y, z - 1);
			b4 = world.getBlock(x, y, z + 1);
			if (te.getNumberOfLogs() == 16 && world.getBlock(x - 1, y, z) == Blocks.fire || world.getBlock(x + 1, y,
					z) == Blocks.fire || world.getBlock(x, y, z - 1) == Blocks.fire || world.getBlock(x, y,
							z + 1) == Blocks.fire)
			{
				if (!te.isOnFire)
				{
					te.activateCharcoal();
				}
			}
			if (te.getNumberOfLogs() == 1)
			{
				Block b = world.getBlock(x + 1, y, z);
				if ((world.getBlock(x - 1, y, z) == TFCBlocks.firepit && ((TEFirepit) world.getTileEntity(x - 1, y,
						z)).fireTemp > 100) || world.getBlock(x - 1, y, z) == Blocks.fire)
				{
					te.clearContents();
					world.setBlock(x, y, z, Blocks.fire);
					if (world.getBlock(x + 1, y, z) == this)
					{
						updateTick(world, x + 1, y, z, rand);
					}
					return;
				}
				else if ((world.getBlock(x + 1, y, z) == TFCBlocks.firepit && ((TEFirepit) world.getTileEntity(x + 1, y,
						z)).fireTemp > 100) || world.getBlock(x + 1, y, z) == Blocks.fire)
				{
					te.clearContents();
					world.setBlock(x, y, z, Blocks.fire);
					if (world.getBlock(x - 1, y, z) == this)
					{
						updateTick(world, x - 1, y, z, rand);
					}
					return;
				}
				else if ((world.getBlock(x, y, z - 1) == TFCBlocks.firepit && ((TEFirepit) world.getTileEntity(x, y,
						z - 1)).fireTemp > 100) || world.getBlock(x, y, z - 1) == Blocks.fire)
				{
					te.clearContents();
					world.setBlock(x, y, z, Blocks.fire);
					if (world.getBlock(x, y, z + 1) == this)
					{
						updateTick(world, x, y, z + 1, rand);
					}
					return;
				}
				else if ((world.getBlock(x, y, z + 1) == TFCBlocks.firepit && ((TEFirepit) world.getTileEntity(x, y,
						z + 1)).fireTemp > 100) || world.getBlock(x, y, z + 1) == Blocks.fire)
				{
					te.clearContents();
					world.setBlock(x, y, z, Blocks.fire);
					if (world.getBlock(x, y, z - 1) == this)
					{
						updateTick(world, x, y, z - 1, rand);
					}
					return;
				}
			}
			if (te.isOnFire && te.fireTimer + TFCOptions.charcoalPitBurnTime < TFC_Time.getTotalHours())
			{
				te.createCharcoal(x, y, z, true);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		if (world.getTileEntity(x, y, z) instanceof TELogPile && ((TELogPile) world.getTileEntity(x, y, z)).isOnFire)
		{
			double centerX = x + 0.5F;
			double centerY = y + 2F;
			double centerZ = z + 0.5F;
			// double d3 = 0.2199999988079071D;
			// double d4 = 0.27000001072883606D;
			world.spawnParticle("smoke", centerX + (rand.nextDouble() - 0.5), centerY,
					centerZ + (rand.nextDouble() - 0.5), 0.0D, 0.1D, 0.0D);
			world.spawnParticle("smoke", centerX + (rand.nextDouble() - 0.5), centerY,
					centerZ + (rand.nextDouble() - 0.5), 0.0D, 0.15D, 0.0D);
			world.spawnParticle("smoke", centerX + (rand.nextDouble() - 0.5), centerY - 1,
					centerZ + (rand.nextDouble() - 0.5), 0.0D, 0.1D, 0.0D);
			world.spawnParticle("smoke", centerX + (rand.nextDouble() - 0.5), centerY - 1,
					centerZ + (rand.nextDouble() - 0.5), 0.0D, 0.15D, 0.0D);
		}
	}
}
