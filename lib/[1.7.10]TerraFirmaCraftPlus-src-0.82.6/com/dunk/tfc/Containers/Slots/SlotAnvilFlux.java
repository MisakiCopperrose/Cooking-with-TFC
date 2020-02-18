package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.api.TFCItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAnvilFlux extends Slot
{
	public SlotAnvilFlux(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() == TFCItems.powder && itemstack.getItemDamage() == 0;
	}
}
