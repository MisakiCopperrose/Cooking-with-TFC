package com.dunk.tfc.Blocks.Flora;

import java.util.List;

import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockStackedLogHoriz2 extends BlockStackedLogHoriz
{

	public BlockStackedLogHoriz2(int off)
	{
		super(off);
		int size = Global.WOOD_ALL.length - 16 - off;
		if(size < 0) size = 0;
		if(size > 8)
		{
			size = 8;
		}
		woodNames = new String[16];
		if(off < Global.WOOD_ALL.length - 16)
		{
			System.arraycopy(Global.WOOD_ALL, 16 + off, woodNames, 0, size);
			System.arraycopy(Global.WOOD_ALL, 16 + off, woodNames, 8, size);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < (woodNames.length + 1) / 2; i++)
			list.add(new ItemStack(this, 1, i));
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		int dir = meta >> 3;
		meta = (meta & 7) /*+ offset*/; //NOPMD
		if (meta >= sideIcons.length)
			meta = 0;

		if(dir == 0)
		{
			if(side == 0 || side == 1)
				return sideIcons[meta];
			else if(side == 2 || side == 3)
				return innerIcons[meta];
			else
				return rotatedSideIcons[meta];
		}
		else
		{
			if(side == 0 || side == 1 || side == 2 || side == 3)
				return rotatedSideIcons[meta];
			else
				return innerIcons[meta];
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return ((dmg & 7) + offset + 16); //NOPMD
	}
}
