package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.Items.ItemMeltedMetal;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMetal extends Slot
{
	public SlotMetal(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ItemMeltedMetal || itemstack.getItem() == TFCItems.ceramicMold;
	}
}
