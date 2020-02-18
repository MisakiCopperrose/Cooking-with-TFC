package com.dunk.tfc.Items.Tools;

import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.item.ItemStack;

public class ItemSpindle extends ItemTerra
{
	public ItemSpindle()
	{
		super();
		this.setMaxDamage(40);
		this.setFolder("tools/");
		setNoRepair();

		this.setSize(EnumSize.VERYSMALL);
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		return HashMultimap.create();
	}
	
	@Override
	public EnumItemReach getReach(ItemStack is){
		return EnumItemReach.SHORT;
	}

	@Override
	public int getItemStackLimit()
	{
		return 1;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}
}