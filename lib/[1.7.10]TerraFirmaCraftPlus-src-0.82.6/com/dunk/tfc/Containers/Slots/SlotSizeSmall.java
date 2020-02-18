package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Interfaces.ISize;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSizeSmall extends Slot
{
	private EnumSize size = EnumSize.SMALL;
	public SlotSizeSmall(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize)
			return true;
		else if (!(itemstack.getItem() instanceof ISize))
			return true;
		return false;
	}
}
