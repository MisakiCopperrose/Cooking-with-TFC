package com.dunk.tfc.Blocks;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.TileEntities.TEPartial;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTileRoof extends BlockTerra
{
	IIcon blockIconFlipped, blockIconSide;
	IIcon blockIconThatch;
	IIcon blockIconSlate;

	public BlockTileRoof()
	{
		super(Material.rock);
		this.opaque = true;
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}

	public BlockTileRoof(Material m)
	{
		super(m);
		if (m == Material.grass)
		{
			this.setStepSound(Block.soundTypeGrass);
		}
		this.opaque = true;
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}
	
	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
	{
		return false;
	}
	
	 public int getLightOpacity(IBlockAccess world, int x, int y, int z)
	    {
	        return 255;
	    }

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
		if (this == TFCBlocks.tileRoof || this == TFCBlocks.slateRoof)
		{
			list.add(new ItemStack(this, 1, 4));
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg < 4 ? 0 : 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 123)
		{
			return blockIconFlipped;
		}
		if(side == 345)
		{
			return blockIconSlate;
		}
		if (side == 124)
		{
			return blockIconThatch;
		}
		if (side == 0 || side == 1)
			return blockIcon;
		if (side == 2 || side == 3)
			return blockIcon;
		return blockIcon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int i1 = 0;// world.getBlockMetadata(x, y, z) & 4;
		int dmg = stack.getItemDamage();
		if (dmg < 4)
		{
			if (l == 0)
			{
				world.setBlockMetadataWithNotify(x, y, z, 0 | i1, 2);
			}

			if (l == 1)
			{
				world.setBlockMetadataWithNotify(x, y, z, 3 | i1, 2);
			}

			if (l == 2)
			{
				world.setBlockMetadataWithNotify(x, y, z, 2 | i1, 2);
			}

			if (l == 3)
			{
				world.setBlockMetadataWithNotify(x, y, z, 1 | i1, 2);
			}
		}
		else if (dmg == 4)
		{
			if (l == 0)
			{
				world.setBlockMetadataWithNotify(x, y, z, 4 | i1, 2);
			}

			if (l == 1)
			{
				world.setBlockMetadataWithNotify(x, y, z, 5 | i1, 2);
			}

			if (l == 2)
			{
				world.setBlockMetadataWithNotify(x, y, z, 4 | i1, 2);
			}

			if (l == 3)
			{
				world.setBlockMetadataWithNotify(x, y, z, 5 | i1, 2);
			}
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.tileRoofRenderId;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/Roof Tile Side");
		this.blockIconSide = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/Roof Tile Side");
		this.blockIconSlate = iconRegisterer.registerIcon(Reference.MOD_ID + ":" +"rocks/Slate Raw");
		// this.blockIconFlipped = iconRegisterer.registerIcon(Reference.MOD_ID
		// + ":" + "rocks/Roof Tile Flip");
		this.blockIconFlipped = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/Roof Tile Item");
		this.blockIconThatch = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "plants/Thatch");
	}
}
