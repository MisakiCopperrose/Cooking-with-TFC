package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemLittleDrum extends ItemTerraBlock
{

	public ItemLittleDrum(Block b)
	{
		super(b);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}
	
	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.MEDIUM;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}

}
