package com.dunk.tfc.Blocks;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Effects.FastFlameFX;
import com.dunk.tfc.Effects.GasFX;
import com.dunk.tfc.TileEntities.TEChimney;
import com.dunk.tfc.TileEntities.TELogPile;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockChimney extends BlockTerraContainer
{
	int textureOffset;
	IIcon[] icons;

	public BlockChimney()
	{
		super(Material.rock);
		this.textureOffset = -1;
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
		this.opaque = false;
		this.setBlockBounds(0f, 0f, 0f, 1, 1f, 1f);
		icons = new IIcon[1];
	}
	
	public BlockChimney(int i)
	{
		super(Material.rock);
		this.textureOffset = i;
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
		this.opaque = false;
		this.setBlockBounds(0f, 0f, 0f, 1, 1f, 1f);
		icons = new IIcon[Math.min(Global.STONE_ALL.length - textureOffset, 16)];
	}
	
	@Override
	public int damageDropped(int n)
	{
		return n;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// Change to false if this block should not be added to the creative tab
		Boolean addToCreative = true;

		if (addToCreative)
		{
			int count;
			
			if (textureOffset == 0)
			{
				count = 16;
			}
			else if(textureOffset == -1)
			{
				count = 1;
			}
			else
			{
				count = Global.STONE_ALL.length - 16;
			}
				

			for (int i = 0; i < count; i++)
				list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		if(textureOffset == -1)
		{
			icons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/Brick");
			return;
		}
		for (int i = 0; i < Math.min(Global.STONE_ALL.length - textureOffset, 16); i++)
		{
			if(this == TFCBlocks.chimneyLargeBricks || this == TFCBlocks.chimneyLargeBricks2)
			{
				icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/" + Global.STONE_ALL[i + textureOffset] + " Brick");
			}
			else
			{
				icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/" + Global.STONE_ALL[i + textureOffset] + " Small Brick");
			}
			
		}
	}

	@Override
	public IIcon getIcon(int s, int m)
	{
		return icons[m % Math.min(Global.STONE_ALL.length - textureOffset, icons.length)];
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		if (side == ForgeDirection.UP || side == ForgeDirection.DOWN)
		{
			return false;

		}
		return true;
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
	{
		return false;

	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isNormalCube()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		if (world.getTileEntity(x, y, z) instanceof TEChimney)
		{
			if (((TEChimney) world.getTileEntity(x, y, z)).smoking > 0 && world.isAirBlock(x, y + 1, z))
			{
				double centerX = x + 0.5F;
				double centerY = y + 2F;
				double centerZ = z + 0.5F;
				// double d3 = 0.2199999988079071D;
				// double d4 = 0.27000001072883606D;
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 2,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.1D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 2,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.15D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 1,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.1D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 1,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.15D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 2,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.1D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 2,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.15D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 1,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.1D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 1,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.15D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 2,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.1D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 2,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.15D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 1,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.1D, 0.0D);
				world.spawnParticle("smoke", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 1,
						centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.15D, 0.0D);
			}
			if (world.isAirBlock(x, y + 1, z))
			{
				int onFire = ((TEChimney) world.getTileEntity(x, y, z)).onFire;
				if (onFire > 0)
				{
					double centerX = x + 0.5F;
					double centerY = y + 2F;
					double centerZ = z + 0.5F;
					// double d3 = 0.2199999988079071D;
					// double d4 = 0.27000001072883606D;
					int n = 10;
					for (int i = 0; i < n; i++)
					{
						/*Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
								,centerY - 4,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.25d + (rand.nextDouble()*0.25d) ,0d,7,i));
						Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
								,centerY - 4,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.25d + (rand.nextDouble()*0.25d),0d,7,i+1));
						Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
								,centerY - 4,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.25d + (rand.nextDouble()*0.25d),0d,7,i));
						Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
								,centerY - 4,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.25d + (rand.nextDouble()*0.25d),0d,7,i+1));*/
						
						
						Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
								,centerY - 1,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.05D ,0d,(int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4,i));
						Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
								,centerY - 1,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.1D,0d,(int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4,i));
						Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
								,centerY - 2,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.05D,0d,(int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4,i));
						Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
								,centerY - 2,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.1D,0d,(int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4,i));
						
					}
			/*		Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
							,centerY - 1,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.05D ,0d,(int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4,0));
					Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
							,centerY - 1,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.1D,0d,(int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4,0));
					Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
							,centerY - 2,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.05D,0d,(int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4,0));
					Minecraft.getMinecraft().effectRenderer.addEffect(new FastFlameFX(world, centerX + ((rand.nextDouble() - 0.5) * 0.5)
							,centerY - 2,centerZ + ((rand.nextDouble() - 0.5) * 0.5),0d,0.1D,0d,(int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4,0));*/
					
					/*world.spawnParticle("flame", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 1,
							centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.1D, 0.0D);
					world.spawnParticle("flame", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 1,
							centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.05D, 0.0D);
					world.spawnParticle("flame", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 2,
							centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.1D, 0.0D);
					world.spawnParticle("flame", centerX + ((rand.nextDouble() - 0.5) * 0.5), centerY - 2,
							centerZ + ((rand.nextDouble() - 0.5) * 0.5), 0.0D, 0.05D, 0.0D);*/
				}
			}
		}
	}

	public static boolean canChimneySeeSky(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z) instanceof BlockChimney)
		{
			if (y < 255 && world.getBlock(x, y + 1, z) instanceof BlockChimney)
			{
				return canChimneySeeSky(world, x, y + 1, z);
			}
			else if (y == 255)
			{
				return true;
			}
			else
			{
				return TFC_Core.isExposed(world, x, y, z);
			}
		}
		return false;
	}

	public int getRenderType()
	{
		return TFCBlocks.chimneyRenderId;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TEChimney();
	}
}
