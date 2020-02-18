package com.dunk.tfc.Blocks.Vanilla;

import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.BlockTerra;
import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockLeafLitter;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Entities.Mobs.EntityWolfTFC;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCustomSnow extends BlockTerra
{
	public BlockCustomSnow()
	{
		super(Material.snow);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setTickRandomly(true);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		Block block = world.getBlock(i, j - 1, k);
		
		if (block == TFCBlocks.pottery)
			return false;
		if (block == TFCBlocks.leaves || block == TFCBlocks.leaves2 || block == TFCBlocks.thatch || block instanceof BlockBranch || block == TFCBlocks.ice)
			return true;
		return World.doesBlockHaveSolidTopSurface(world, i, j - 1, k);
	}

	private static boolean isSnow(IBlockAccess access, int x, int y, int z)
	{
		Material material = access.getBlock(x, y, z).getMaterial();
		return material == Material.snow || material == Material.craftedSnow;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
		switch(side){
		case NORTH: return isSnow(world,x,y+1,z-1);
		case SOUTH: return isSnow(world,x,y+1,z+1);
		case WEST: return isSnow(world,x-1,y+1,z);
		case EAST: return isSnow(world,x+1,y+1,z);
		}
		return false;
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		/*if(!world.getBlock(x, y-1, z).isSideSolid(world, x, y-1, z, ForgeDirection.UP))
		{
			return null;
		}
		else if(false)
		{
			return null;
		}*/
		int meta = world.getBlockMetadata(x, y, z)&7;
		float f = 0F + (meta/2 * 0.125F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + f, z + this.maxZ);
	}
	
	@Override
	public int getRenderType()
	{
		return TFCBlocks.snowRenderId;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
	{
		dropBlockAsItem(world, x, y, z, meta, 0);
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
	}

	@Override
	public Item getItemDropped(int i, Random r, int j)
	{
		return Items.snowball;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		// meta  speed
		//    0  0.98   -  one layer
		//    7  0.10   -  eight layers = like leaves
		float extraSlow = 1f;

		if(entity instanceof EntityPlayer)
		{
			ItemStack bootsI = ((EntityPlayer)entity).getCurrentArmor(0);
			if(bootsI != null && (bootsI.getItem() == TFCItems.wolfFurBoots || bootsI.getItem() == TFCItems.bearFurBoots))
			{
				return;
			}
		}
		int meta = world.getBlockMetadata(x, y, z) & 7;
		double speed = 0.9375 - (0.0625 * (meta - meta/2) * extraSlow);
		speed = Math.max(speed,0);
		if(!(entity instanceof EntityWolfTFC))
		{
			entity.motionX *= speed;
			entity.motionZ *= speed;
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if(!canPlaceBlockAt(world, x, y, z))
		{
			world.setBlock(x, y, z, Blocks.air, 0, 2);
		}
	}

	@Override
	public int quantityDropped(Random r)
	{
		return 1;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int x, int y, int z)
	{
		int meta = bAccess.getBlockMetadata(x, y, z) & 7;
		float top = (meta + 1) / 8.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, top, 1.0F);
	}

	@Override
	public int tickRate(World world)
	{
		return 50;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random r)
	{
		if (!canPlaceBlockAt(world, x, y, z))
		{
			world.setBlock(x, y, z, Blocks.air, 0, 2);
			return;
		}
		
		int meta = world.getBlockMetadata(x, y, z) & 7;
		
		if (world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) > 11)
		{
			if (r.nextInt(5) == 0)
			{
				if(meta > 0)
					world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
				else
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
		}
		
		float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);
		
		if (temp <= 0 && world.isRaining()&& world.canBlockSeeTheSky(x, y, z))  //Raining and Below Freezing
		{
			if (r.nextInt(20) == 0)
			{
				if(world.getBlock(x, y-1, z) instanceof BlockBranch)
				{
					for(int i = -1; i >-20 && y + i > 0;i--)
					{
						if(world.getBlock(x, y+i+1, z).isReplaceable(world, x, y+i+1, z) && world.isSideSolid(x, y+i, z, ForgeDirection.UP))
						{
							world.setBlock(x, y+1+i, z, this);
							return;
						}
					}
				}
				for(int i = -1; i < 2; i++)
				{
					for(int j = -1;j<2;j++)
					{
						if(world.getBlock(x+i, y, z+j) instanceof BlockLeafLitter || 
								(world.isAirBlock(x+i, y, z+j) && world.isSideSolid(x+i, y-1, z+j, ForgeDirection.UP)))
						{
							world.setBlock(x+i, y, z+j, this);
							return;
						}
					}
				}
				int max = (world.getBlock(x, y - 1, z).getMaterial() == Material.leaves) ? 3 : 7;
				if(meta < max && canAddSnow(world, x, y, z, meta))
				{
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
				}
			}
		}
		else if (temp > 10)  // to hot for snow (probably chunk loading error)
		{
			world.setBlock(x, y, z, Blocks.air, 0, 0x2);
		}
		else if (temp > 0 && world.isRaining()&& world.canBlockSeeTheSky(x, y, z))  //Raining and above freezing
		{
			if (r.nextInt(5) == 0)
			{
				if (meta > 0)
					world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
				else
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
		}
		else if (temp > 0)  //Above freezing, not raining
		{
			if (r.nextInt(20) == 0)
			{
				if(meta > 0)
					world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
				else
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);
			}
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		this.blockIcon = registerer.registerIcon(Reference.MOD_ID + ":snow");
	}

	private boolean canAddSnowCheckNeighbors(World world, int x, int y, int z, int meta)
	{
 		Block block = world.getBlock(x, y, z);
 		
 		if (block.getMaterial() == Material.snow)  // if neighbor is snow, allow up to one additional level
 			return meta <= (world.getBlockMetadata(x, y, z) & 7);
		else if (block == TFCBlocks.leaves || block == TFCBlocks.leaves2)  // 4 levels if adjacent to leaves (instead of just one level)
			return meta < 3;  
		else if (block.isNormalCube())  // if neighbor is a normal block (opaque, render as normal, not power),
			return meta < 6;            // up to 7 - leave the top layer empty so we just can see the block
 		else
			return false;
	}

	private boolean canAddSnow(World world, int x, int y, int z, int meta)
	{
		if (!canAddSnowCheckNeighbors(world, x + 1, y, z, meta))
			return false;
		if (!canAddSnowCheckNeighbors(world, x - 1, y, z, meta))
			return false;
		if (!canAddSnowCheckNeighbors(world, x, y, z + 1, meta))
			return false;
		return canAddSnowCheckNeighbors(world, x, y, z - 1, meta);
	}
}
