package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.Block;

public class ItemCustomWood extends ItemTerraBlock
{
	public ItemCustomWood(Block b)
	{
		super(b);
		metaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, metaNames, 0, 16);
	}
}
