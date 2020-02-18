package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBigDrum extends ItemTerraBlock
{

	public ItemBigDrum(Block b)
	{
		super(b);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}
	
}
