package com.dunk.tfc.Items;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.item.ItemStack;

public class ItemPumpkinShell extends ItemTerra
{
	public ItemPumpkinShell()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
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
