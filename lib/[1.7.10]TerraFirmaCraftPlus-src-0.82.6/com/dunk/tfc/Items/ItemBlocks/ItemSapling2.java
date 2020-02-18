package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

public class ItemSapling2 extends ItemSapling
{
	public ItemSapling2(Block b)
	{
		super(b);
		this.metaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, metaNames, 0, Global.WOOD_ALL.length - 16);
		this.icons = new IIcon[metaNames.length];
	}
}
