package com.dunk.tfc.Items.Pottery;

import net.minecraft.item.ItemStack;

public class ItemPotteryMoldBase extends ItemPotteryBase
{
	protected int maxUnits;
	//This represents the values that are clay and ceramic
	protected int baseDamage;
	protected int counter;
	protected boolean hasSolidLiquidStates = false;

	public int getMaxUnits()
	{
		return maxUnits;
	}

	public ItemStack setFullUnits(ItemStack i)
	{
		if (i != null)
		{
			i = addUnits(i,getPossibleUnits(i));
		}
		return i;
	}
	
	public ItemPotteryMoldBase setHasSolidLiquidStates(boolean b)
	{
		this.hasSolidLiquidStates = b;
		return this;
	}
	
	//Assume that damage == 0 = clay
	public boolean isValidMold(ItemStack i)
	{
		return i.getItemDamage() != 0 && getPossibleUnits(i) > 0;
	}
	
	public int getPossibleUnits(ItemStack i)
	{
		return maxUnits - getUnits(i);
	}
	
	public int getUnits(ItemStack i)
	{
		int damage = i.getItemDamage();
		if(damage < baseDamage)
		{
			return 0;
		}
		int units = (maxUnits+(hasSolidLiquidStates?1:0)) - ((damage - baseDamage)/counter);
		return units;
	}
	
	public int getCounter()
	{
		return counter;
	}
	public int getDamageOffset()
	{
		return this.baseDamage;
	}
	
	public ItemStack setToMinimumUnits(ItemStack i)
	{
		i = addUnits(i,getUnits(i) * -1);
		return i;
	}
	
	public ItemStack addUnits(ItemStack i, int u)
	{
		int possibleUnits = Math.min(maxUnits - getUnits(i),u);
		i.setItemDamage(i.getItemDamage() - (possibleUnits * counter));
		return i;
	}

	public ItemPotteryMoldBase setBaseDamage(int i)
	{
		this.baseDamage = i;
		return this;
	}

	public ItemPotteryMoldBase setCounter(int i)
	{
		this.counter = i;
		return this;
	}

	public ItemPotteryMoldBase setMaxUnits(int i)
	{
		this.maxUnits = i;
		return this;
	}
}
