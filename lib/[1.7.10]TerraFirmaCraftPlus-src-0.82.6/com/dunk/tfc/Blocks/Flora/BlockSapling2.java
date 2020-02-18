package com.dunk.tfc.Blocks.Flora;

import com.dunk.tfc.api.Constant.Global;

import net.minecraft.util.IIcon;

public class BlockSapling2 extends BlockSapling
{
	public BlockSapling2()
	{
		super();
		this.woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
		this.icons = new IIcon[woodNames.length];
	}
}
