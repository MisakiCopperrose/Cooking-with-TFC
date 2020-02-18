package com.dunk.tfc.Blocks;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMudBricks extends BlockTerra
{
	protected IIcon[] icons;
	Block sourceDirt;
	protected int textureOffset;

	public BlockMudBricks(int texOff)
	{
		super(Material.ground);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
		this.setTickRandomly(true);
		this.stepSound = this.soundTypeStone;
		this.textureOffset = texOff;
	}

	public BlockMudBricks setDirt(Block dirt)
	{
		this.sourceDirt = dirt;
		return this;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (TFC_Core.isExposedToRain(world, x, y, z))
		{
			world.setBlock(x, y, z, sourceDirt, world.getBlockMetadata(x, y, z), 2);
			world.scheduleBlockUpdate(x, y, z, sourceDirt, 5);
			world.scheduleBlockUpdate(x-1, y, z, sourceDirt, 20);
			world.scheduleBlockUpdate(x+1, y, z, sourceDirt, 20);
			world.scheduleBlockUpdate(x, y, z-1, sourceDirt, 20);
			world.scheduleBlockUpdate(x, y, z+1, sourceDirt, 20);
			world.scheduleBlockUpdate(x, y-1, z, sourceDirt, 20);
		}
		else
		{
			for (int i = -1; i < 2; i++)
			{
				for (int j = -1; j < 2; j++)
				{
					for (int k = -1; k < 2; k++)
					{
						if (!(y == 255 && j == 1) && !(y == 0 && j == -1) && (i*k == 0) && !(j !=0 && i != k))
						{
							Block b = world.getBlock(x + i, y + j, z + k);
							if (TFC_Core.isWater(b))
							{
								world.setBlock(x, y, z, sourceDirt, world.getBlockMetadata(x, y, z), 2);
								world.scheduleBlockUpdate(x, y, z, sourceDirt, 5);
							}
						}
					}
				}
			}
		}
	}

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
				count = 16;
			else
				count = Global.STONE_ALL.length - 16;

			for (int i = 0; i < count; i++)
				list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return Item.getItemFromBlock(sourceDirt);
	}

	@Override
	public IIcon getIcon(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		int meta = bAccess.getBlockMetadata(x, y, z);
		if (meta >= icons.length)
			return icons[icons.length - 1];
		return icons[meta];
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= icons.length)
			return icons[icons.length - 1];
		return icons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		int count = (textureOffset == 0 ? 16 : Global.STONE_ALL.length - 16);
		icons = new IIcon[count];
		for (int i = 0; i < count; i++)
			icons[i] = registerer
					.registerIcon(Reference.MOD_ID + ":" + "soil/Mud Brick " + Global.STONE_ALL[i + textureOffset]);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}
}
