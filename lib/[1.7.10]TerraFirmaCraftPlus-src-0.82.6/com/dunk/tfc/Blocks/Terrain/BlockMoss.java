package com.dunk.tfc.Blocks.Terrain;

import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.BlockTerra;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMoss extends BlockTerra
{
	
	IIcon[] icons;
	public BlockMoss()
	{
		super(Material.vine);
		this.setTickRandomly(true);
		this.canBlockGrass =true;
		this.setCreativeTab(TFCTabs.TFC_MISC);
		icons = new IIcon[15];
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
	
	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.mossRenderId;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss");
		int i = 0;
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0002");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0003");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0006");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0009");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0010");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0011");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0012");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0013");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0015");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0016");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0017");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0018");
		this.icons[i++] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Moss_0019");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		meta%= icons.length;
		return this.icons[meta];
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		
		this.setBlockBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	/**
	 * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
	 */
	@Override
	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
	{
		return true;
		/*
		switch (par5)
		{
		case 1:
			return this.canBePlacedOn(par1World.getBlock(par2, par3 + 1, par4));
		case 2:
			return this.canBePlacedOn(par1World.getBlock(par2, par3, par4 + 1));
		case 3:
			return this.canBePlacedOn(par1World.getBlock(par2, par3, par4 - 1));
		case 4:
			return this.canBePlacedOn(par1World.getBlock(par2 + 1, par3, par4));
		case 5:
			return this.canBePlacedOn(par1World.getBlock(par2 - 1, par3, par4));
		default:
			return false;
		}*/
	}

	/**
	 * returns true if a vine can be placed on that block (checks for render as normal block and if it is solid)
	 */
	private boolean canBePlacedOn(Block block)
	{
		return true;
	//	if (block == Blocks.air)
	//		return false;
	//	else
	//		return block.renderAsNormalBlock() && block.getMaterial().blocksMovement();
	}

	/**
	 * Returns if the vine can stay in the world. It also changes the metadata according to neighboring blocks.
	 */
	private boolean canVineStay(World world, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			if(TFC_Climate.getHeightAdjustedTemp(world, x, y, z) < 5)
			{
				return false;
			}
		}
		
		int l = world.getBlockMetadata(x, y, z);
		int i1 = l;

		if (l > 0)
		{
			for (int j1 = 0; j1 <= 3; ++j1)
			{
				int k1 = 1 << j1;
				if ((l & k1) != 0 && !this.canBePlacedOn(world.getBlock(x + Direction.offsetX[j1], y, z + Direction.offsetZ[j1])) && (world.getBlock(x, y + 1, z) != this || (world.getBlockMetadata(x, y + 1, z) & k1) == 0))
					i1 &= ~k1;
			}
		}

		if (i1 == 0 && !this.canBePlacedOn(world.getBlock(x, y + 1, z)))
		{
			return false;
		}
		else
		{
			if (i1 != l)
				world.setBlockMetadataWithNotify(x, y, z, i1, 2);

			return true;
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		if (!par1World.isRemote && !this.canVineStay(par1World, par2, par3, par4))
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (!world.isRemote && world.rand.nextInt(4) == 0)
		{
			byte b0 = 4;
			int l = 5;
			boolean flag = false;
			int i1;
			int j1;
			int k1;

			label138:
				for (i1 = x - b0; i1 <= x + b0; ++i1)
				{
					for (j1 = z - b0; j1 <= z + b0; ++j1)
					{
						for (k1 = y - 1; k1 <= y + 1; ++k1)
						{
							if (world.getBlock(i1, k1, j1) == this)
							{
								--l;
								if (l <= 0)
								{
									flag = true;
									break label138;
								}
							}
						}
					}
				}

			i1 = world.getBlockMetadata(x, y, z);
			j1 = world.rand.nextInt(6);
			k1 = Direction.facingToDirection[j1];
			int l1;
			int i2;
			Block block;

			if (j1 == 1 && y < 255 && world.isAirBlock(x, y + 1, z))
			{
				if (flag)
					return;

				l1 = world.rand.nextInt(16) & i1;
				if (l1 > 0)
				{
					for (i2 = 0; i2 <= 3; ++i2)
					{
						if (!this.canBePlacedOn(world.getBlock(x + Direction.offsetX[i2], y + 1, z + Direction.offsetZ[i2])))
							l1 &= ~(1 << i2);
					}

					if (l1 > 0)
						world.setBlock(x, y + 1, z, this, l1, 2);
				}
			}
			else
			{
				int j2;
				if (j1 >= 2 && j1 <= 5 && (i1 & 1 << k1) == 0)
				{
					if (flag)
						return;

					block = world.getBlock(x + Direction.offsetX[k1], y, z + Direction.offsetZ[k1]);
					if (!block.isAir(world, x + Direction.offsetX[k1], y, z + Direction.offsetZ[k1]))
					{
						if (block.getMaterial().isOpaque() && block.renderAsNormalBlock())
							world.setBlockMetadataWithNotify(x, y, z, i1 | 1 << k1, 2);
					}
					else
					{
						i2 = k1 + 1 & 3;
						j2 = k1 + 3 & 3;

						if ((i1 & 1 << i2) != 0 && this.canBePlacedOn(world.getBlock(x + Direction.offsetX[k1] + Direction.offsetX[i2], y, z + Direction.offsetZ[k1] + Direction.offsetZ[i2])))
							world.setBlock(x + Direction.offsetX[k1], y, z + Direction.offsetZ[k1], this, 1 << i2, 2);
						else if ((i1 & 1 << j2) != 0 && this.canBePlacedOn(world.getBlock(x + Direction.offsetX[k1] + Direction.offsetX[j2], y, z + Direction.offsetZ[k1] + Direction.offsetZ[j2])))
							world.setBlock(x + Direction.offsetX[k1], y, z + Direction.offsetZ[k1], this, 1 << j2, 2);
						else if ((i1 & 1 << i2) != 0 && world.isAirBlock(x + Direction.offsetX[k1] + Direction.offsetX[i2], y, z + Direction.offsetZ[k1] + Direction.offsetZ[i2]) && this.canBePlacedOn(world.getBlock(x + Direction.offsetX[i2], y, z + Direction.offsetZ[i2])))
							world.setBlock(x + Direction.offsetX[k1] + Direction.offsetX[i2], y, z + Direction.offsetZ[k1] + Direction.offsetZ[i2], this, 1 << (k1 + 2 & 3), 2);
						else if ((i1 & 1 << j2) != 0 && world.isAirBlock(x + Direction.offsetX[k1] + Direction.offsetX[j2], y, z + Direction.offsetZ[k1] + Direction.offsetZ[j2]) && this.canBePlacedOn(world.getBlock(x + Direction.offsetX[j2], y, z + Direction.offsetZ[j2])))
							world.setBlock(x + Direction.offsetX[k1] + Direction.offsetX[j2], y, z + Direction.offsetZ[k1] + Direction.offsetZ[j2], this, 1 << (k1 + 2 & 3), 2);
						else if (this.canBePlacedOn(world.getBlock(x + Direction.offsetX[k1], y + 1, z + Direction.offsetZ[k1])))
							world.setBlock(x + Direction.offsetX[k1], y, z + Direction.offsetZ[k1], this, 0, 2);
					}
				}
				else if (y > 1)
				{
					block = world.getBlock(x, y - 1, z);

					if (block.isAir(world, x, y - 1, z))
					{
						i2 = world.rand.nextInt(16) & i1;
						if (i2 > 0)
							world.setBlock(x, y - 1, z, this, i2, 2);
					}
					else if (block == this)
					{
						i2 = world.rand.nextInt(16) & i1;
						j2 = world.getBlockMetadata(x, y - 1, z);
						if (j2 != (j2 | i2))
							world.setBlockMetadataWithNotify(x, y - 1, z, j2 | i2, 2);
					}
				}
			}
		}
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		byte b0 = 0;

		switch (par5)
		{
		case 2:
			b0 = 1;
			break;
		case 3:
			b0 = 4;
			break;
		case 4:
			b0 = 8;
			break;
		case 5:
			b0 = 2;
			break;
		}

		return 0;//b0 != 0 ? b0 : par9;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return null;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	 * block and l is the block's subtype/damage.
	 */
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
	}

	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity)
	{
		return false;
	}
}
