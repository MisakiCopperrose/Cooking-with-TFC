package com.dunk.tfc.Blocks;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.Devices.BlockTeepee;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWoodenSpear extends BlockTerra
{

	@Override
	public int getRenderType()
	{
		return TFCBlocks.woodSpearRenderId;
	}
	
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return AxisAlignedBB.getBoundingBox(i+0.35, j, k+0.35, i + 0.65, j + 1.5, k + 0.65);
	}
	
	/*
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List boxes, Entity entity)
    {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0)
		{
			setBlockBounds(0.25f,0f,0.25f,0.75f,1f,0.75f);
			super.addCollisionBoxesToList(world,x,y,z,aabb,boxes,entity);
			return;
		}
		
		
	}*/

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "Wooden Spear");
	}
	
	private void destroyEffects(World world, int x, int y, int z, int meta)
	{
		if (!world.isRemote)
		{
			if (meta == 1 && world.getBlock(x + 1, y, z + 1) instanceof BlockTeepee)
			{
				world.getBlock(x + 1, y, z + 1).onBlockDestroyedByPlayer(world,x + 1, y, z + 1, world.getBlockMetadata(x + 1, y, z + 1));
				world.setBlockToAir(x + 1, y, z + 1);
			}
			else if (meta == 2 && world.getBlock(x + 1, y, z - 1) instanceof BlockTeepee)
			{
				world.getBlock(x + 1, y, z - 1).onBlockDestroyedByPlayer(world, x + 1, y, z - 1, world.getBlockMetadata(x + 1, y, z - 1));
				world.setBlockToAir(x + 1, y, z - 1);
			}
			else if (meta == 3 && world.getBlock(x - 1, y, z - 1) instanceof BlockTeepee)
			{
				world.getBlock(x - 1, y, z - 1).onBlockDestroyedByPlayer(world, x - 1, y, z - 1, world.getBlockMetadata(x - 1, y, z - 1));
				world.setBlockToAir(x - 1, y, z - 1);
			}
			else if (meta == 4 && world.getBlock(x - 1, y, z + 1) instanceof BlockTeepee)
			{
				((BlockTeepee)world.getBlock(x - 1, y, z + 1)).onBlockDestroyedByPlayer(world, x - 1, y, z +1, world.getBlockMetadata(x - 1, y, z + 1));
				world.setBlockToAir(x - 1, y, z + 1);
			}
			
		}
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
	{
		if (!world.isRemote)
		{	
			destroyEffects(world,x,y,z,meta);
			EntityItem ei = new EntityItem(world,x,y,z,new ItemStack(TFCItems.woodenSpear));
			ei.motionX = ei.motionY = ei.motionZ = 0;
			world.spawnEntityInWorld(ei);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
			float par8, float par9)
	{
		if(!world.isRemote && player.isSneaking())
		{
			int meta = world.getBlockMetadata(x, y, z);
			if(meta == 0)
			{
				destroyEffects(world,x,y,z,meta);
				world.setBlockToAir(x, y, z);
				EntityItem ei = new EntityItem(world,x,y,z,new ItemStack(TFCItems.woodenSpear));
				ei.motionX = ei.motionY = ei.motionZ = 0;
				world.spawnEntityInWorld(ei);
			}
		}
		return true;
	}
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.getBlock(x, y-1, z).isSideSolid(world, x, y-1, z, ForgeDirection.UP) && !world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if(meta == 0)
			{
				world.setBlockToAir(x, y, z);
				//drop a wooden spear
				world.spawnEntityInWorld(new EntityItem(world,x,y,z,new ItemStack(TFCItems.woodenSpear)));
			}
			else
			{
				onBlockDestroyedByPlayer(world,x,y,z,meta);
			}
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
