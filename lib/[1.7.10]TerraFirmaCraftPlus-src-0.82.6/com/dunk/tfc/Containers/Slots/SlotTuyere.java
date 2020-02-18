package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.Items.ItemTuyere;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTuyere extends Slot
{
	public SlotTuyere(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ItemTuyere;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
