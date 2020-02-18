package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.api.Crafting.QuernManager;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotQuernGrain extends Slot
{
	public SlotQuernGrain(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return QuernManager.getInstance().isValidItem(itemstack);
	}
}
