package com.dunk.tfc.api;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Metal
{
	public String name;
	//public Item meltedItem;
	public Item ingot;
	public boolean canUse = true;
	private HashMap<Item,Item> validMolds;
	//We are actually doing something crazy here. Instead of the Item,StackSize,Damage representing those values,
	//They represent Item,Counter,DamageOffset on the output and the base value on the input.
	private HashMap<ItemStack,ItemStack> partialMolds;

	public Metal(String name)
	{
		this.name = name;
		validMolds = new HashMap<Item,Item>();
		partialMolds = new HashMap<ItemStack,ItemStack>();
	}

	public Metal(String name, Item i)
	{
		this(name);
		//meltedItem = m;
		ingot = i;
	}

	public Metal(String name, Item i, boolean use)
	{
		this(name);
		//meltedItem = m;
		ingot = i;
		canUse = use;
	}
	
	public Metal addValidMold(Item theMold, Item resultantItem)
	{
		validMolds.put(theMold, resultantItem);
		return this;
	}
	
	public Metal addValidPartialMold(Item thePartialMold, int baseValue, Item resultantItem, int counter, int damageOffset)
	{
		partialMolds.put(new ItemStack(thePartialMold,1,baseValue), new ItemStack(thePartialMold,counter,damageOffset));
		return this;
	}
	
	public Item getResultFromMold(Item mold)
	{
		Item res = validMolds.get(mold);
		if(res != null)
		{
			return res;
		}
		for(ItemStack pm : partialMolds.keySet())
		{
			if(pm.getItem().equals(mold))
			{
				return partialMolds.get(pm).getItem();
			}
		}
		return null;
	}
	
	public int getBaseValueForResult(Item mold)
	{
		for(ItemStack pm : partialMolds.keySet())
		{
			if(pm.getItem().equals(mold))
			{
				return pm.getItemDamage();
			}
		}
		return 1;
	}
	
	//Check if the suggested mold would actually result in a valid item.
	public boolean isValidMold(ItemStack mold)
	{
		for(Item m : validMolds.keySet())
		{
			if(m.equals(mold.getItem()))
			{
				return true;
			}
		}
		for(ItemStack pm : partialMolds.keySet())
		{
			int counter = partialMolds.get(pm).stackSize;
			int damageOffset = partialMolds.get(pm).getItemDamage();
			if(pm.getItem().equals(mold.getItem()) && (((mold.getItemDamage()-damageOffset)%counter)+damageOffset == pm.getItemDamage() || mold.getItemDamage()==1))
			{
				return true;
			}
		}
		return false;
	}
	
	public float getMeltingPoint()
	{
		return HeatRegistry.getInstance().getMeltingPoint(new ItemStack(ingot));
	}
}
