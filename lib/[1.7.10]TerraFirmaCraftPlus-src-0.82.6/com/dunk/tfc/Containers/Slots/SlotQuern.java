package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.api.TFCItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotQuern extends Slot
{
	public SlotQuern(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack is)
	{
		return is.getItem() == TFCItems.quern;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	public void putStack(ItemStack is)
	{
		if (is != null) is.stackSize = 1;
		super.putStack(is);
	}
}
