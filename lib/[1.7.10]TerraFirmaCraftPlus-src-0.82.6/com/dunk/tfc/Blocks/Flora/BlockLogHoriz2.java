package com.dunk.tfc.Blocks.Flora;

import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class BlockLogHoriz2 extends BlockLogHoriz
{
	public BlockLogHoriz2(int off)
	{
		super(off);
		int size = Global.WOOD_ALL.length - 16 - off;
		if(size < 0) size = 0;
		if(size > 8) size = 8;
		woodNames = new String[16];
		if(off < Global.WOOD_ALL.length - 16)
		{
			System.arraycopy(Global.WOOD_ALL, 16 + off, woodNames, 0, size);
			System.arraycopy(Global.WOOD_ALL, 16 + off, woodNames, 8, size);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		int dir = meta >> 3;
		meta = (meta & 7) + offset; //NOPMD
		if (meta >= ((BlockLogNatural2) TFCBlocks.logNatural2).sideIcons.length)
			meta = 0;

		if(dir == 0)
		{
			if(side == 0 || side == 1)
				return ((BlockLogNatural2)TFCBlocks.logNatural2).sideIcons[meta];
			else if(side == 2 || side == 3)
				return ((BlockLogNatural2)TFCBlocks.logNatural2).innerIcons[meta];
			else
				return ((BlockLogNatural2)TFCBlocks.logNatural2).rotatedSideIcons[meta];
		}
		else
		{
			if(side == 0 || side == 1 || side == 2 || side == 3)
				return ((BlockLogNatural2)TFCBlocks.logNatural2).rotatedSideIcons[meta];
			else
				return ((BlockLogNatural2)TFCBlocks.logNatural2).innerIcons[meta];
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return (dmg & 7) + offset + 16; //NOPMD
	}
}
