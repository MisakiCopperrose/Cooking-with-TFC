package com.dunk.tfc.Blocks.Flora;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.Flora.BlockLogVert;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockStackedLogVert extends BlockLogVert
{
	IIcon sideIcons[],innerIcons[],rotatedSideIcons[];
	public BlockStackedLogVert()
	{
		super();
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		sideIcons = new IIcon[16];
		innerIcons = new IIcon[16];
		rotatedSideIcons = new IIcon[16];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 0 || side == 1)
			return innerIcons[meta];
		return sideIcons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		for (int i = 0; i < woodNames.length; i++)
		{
			sideIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/stacked/Stacked " + woodNames[i] + " Log");
			innerIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/stacked/Stacked " + woodNames[i] + " Log Top");
			rotatedSideIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/stacked/Stacked " + woodNames[i] + " Log Side");
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public int damageDropped(int dmg)
	{
		return (dmg); //NOPMD
	}

	@Override
	public Item getItemDropped(int i, Random r, int j)
	{
		return TFCItems.stackedLogs;
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 1;
	}
}
