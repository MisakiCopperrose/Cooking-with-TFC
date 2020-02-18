package com.dunk.tfc.Items.Pottery;

import java.util.List;

import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemPotteryPot extends ItemPotteryBase
{
	public ItemPotteryPot()
	{
		super();
		this.metaNames = new String[]{"Clay Pot", "Ceramic Pot"};
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.SMALL);
		this.setCreativeTab(null);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("LiquidType"))
		{
			arraylist.add(is.stackTagCompound.getString("LiquidType"));
		}
	}
}
