package com.dunk.tfc.Blocks;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCOptions;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockInvisible extends BlockTerra
{
	public BlockInvisible(Material m)
	{
		super(m);
		this.opaque = false;
		this.setBlockBounds(0f, 0f, 0f, 0f, 0f, 0f);
	}

	public Material getMaterial()
	{
		return super.getMaterial();
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		// if(side == ForgeDirection.UP)
		// {
		return true;
		// }
		// return false;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "Invisible");

	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List boxes, Entity entity)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0)
		{
			this.setBlockBounds(0f, -0.5f, 0f, 1f, 1f, 1);
			super.addCollisionBoxesToList(world, x, y, z, aabb, boxes, entity);
		}
		if (world.isRemote && TFCOptions.enableDebugMode)
		{

		}
		else
		{
			this.setBlockBounds(0f, 0f, 0f, 0f, 0f, 0f);
		}
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.teepeeRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

}
