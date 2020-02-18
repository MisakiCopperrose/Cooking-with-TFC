package com.dunk.tfc.Blocks.Flora;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.BlockTerra;
import com.dunk.tfc.Core.ColorizerGrassTFC;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.TileEntities.TESapling;
import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;
import com.sun.prism.paint.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLeafLitter extends BlockTerra
{
	protected IIcon[] icons;

	public BlockLeafLitter()
	{
		super(Material.plants);
		// this.opaque = true;
		this.opaque = false;
		this.canBlockGrass = true;
		this.setBlockBounds(0, 0.0F, 0, 1, 0.125f, 1);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		this.icons = new IIcon[6];
	}

	@Override
	public Item getItemDropped(int i, Random r, int j)
	{
		return null;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		if (side == ForgeDirection.DOWN)
		{
			return true;
		}
		return false;
	}

	@Override
	public int getBlockColor()
	{
		return 15452320;// (255<<16) + (128<<8);
	}

	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		// return super.colorMultiplier(bAccess, x, y, z);
		return 15452320;
	}

	@Override
	public int getRenderColor(int par1)
	{
		return this.getBlockColor();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for (int i = 0; i < 2; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public int damageDropped(int i)
	{
		return i;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		return icons[j%icons.length];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		this.icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/Deciduous Leaf Litter Layer1");
		this.icons[1] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/Coniferous Leaf Litter");
		this.icons[2] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/Deciduous Leaf Litter Layer2");
		this.icons[3] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/Deciduous Leaf Litter Layer3");
		this.icons[4] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/Deciduous Leaf Litter Layer4");
		this.icons[5] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/Deciduous Leaf Litter");
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block b)
	{
		if (!world.isRemote)
		{
			Block block = world.getBlock(i, j, k);
			if (!this.canBlockStay(world, i, j, k))
			{
				int meta = world.getBlockMetadata(i, j, k);
				// this.dropBlockAsItem(world, i, j, k, new ItemStack(this, 1,
				// meta));
				world.setBlockToAir(i, j, k);
			}
		}
	}

	// Set the sapling growth timer the moment it is planted, instead of the
	// first random tick it gets after being planted.
	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
	}

	/**
	 * Can this block stay at this position. Similar to canPlaceBlockAt except
	 * gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return world.getBlock(x, y - 1, z).isBlockSolid(world, x, y - 1, z, 1);
	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return world.getBlock(x, y, z).getMaterial().isReplaceable();
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void harvestBlock(World world,EntityPlayer player, int x, int y, int z, int meta)
	{
		if(!world.isRemote)
		{
			Random rand = new Random();
			if(rand.nextInt(6)==0)
			{
				this.dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.stick,1));
			}
			else if(rand.nextInt(20)==0)
			{
				if(rand.nextInt(4)!=0)
				{
					this.dropBlockAsItem(world, x, y, z, new ItemStack(Item.getItemFromBlock(TFCBlocks.fungi),1,0));
				}
				else
				{
					this.dropBlockAsItem(world, x, y, z,new ItemStack(Item.getItemFromBlock(TFCBlocks.fungi),1,1));
				}
			}
			else if(rand.nextInt(25)==0)
			{
				this.dropBlockAsItem(world, x, y, z, new ItemStack(Items.feather,1));
			}
		}
		return;
	}
	
	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;//AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.0625, z + 1);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.leafLitterRenderId;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	protected void checkChange(World world, int x, int y, int z)
	{
		if (!this.canBlockStay(world, x, y, z))
		{
			// this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y,
			// z), 0);
			world.setBlockToAir(x, y, z);
		}
	}
}
