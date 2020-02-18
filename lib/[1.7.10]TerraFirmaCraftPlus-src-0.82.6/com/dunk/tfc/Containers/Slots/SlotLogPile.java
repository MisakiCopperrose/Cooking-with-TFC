package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.api.TFCItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLogPile extends Slot
{
	public SlotLogPile(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() == TFCItems.logs || itemstack.getItem() == TFCItems.thickLogs;
	}

	@Override
	public int getSlotStackLimit()
	{
		if(this.inventory.getStackInSlot(this.getSlotIndex()) != null && this.inventory.getStackInSlot(this.getSlotIndex()).getItem() == TFCItems.thickLogs)
		{
			return 1;
		}
		return 4;
	}
}
