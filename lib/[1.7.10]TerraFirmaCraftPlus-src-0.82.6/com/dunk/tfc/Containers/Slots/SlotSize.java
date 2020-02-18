package com.dunk.tfc.Containers.Slots;

import java.util.ArrayList;
import java.util.List;

import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Interfaces.ISize;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotSize extends SlotTFC
{
	protected EnumSize size = EnumSize.MEDIUM;
	private List<Item> excpetions = new ArrayList<Item>();
	private List<Item> inclusions = new ArrayList<Item>();

	public SlotSize(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		boolean except = excpetions.contains(itemstack.getItem());
		boolean include = inclusions.contains(itemstack.getItem()) || inclusions.isEmpty();
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize && !except && include)
			return true;
		else if (!(itemstack.getItem() instanceof ISize) && !except && include)
			return true;
		return false;
	}

	public SlotSize setSize(EnumSize s)
	{
		size = s;
		return this;
	}

	public SlotSize addItemException(Item... ex)
	{
		for(int i = 0; i < ex.length; i++)
			excpetions.add(ex[i]);
		return this;
	}

	public SlotSize addItemInclusion(Item... ex)
	{
		for(int i = 0; i < ex.length; i++)
			inclusions.add(ex[i]);
		return this;
	}
}
