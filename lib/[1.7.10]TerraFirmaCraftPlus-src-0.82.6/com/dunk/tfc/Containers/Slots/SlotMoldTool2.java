package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.Items.ItemMeltedMetal;
import com.dunk.tfc.Items.Pottery.ItemPotteryMold;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFC_ItemHeat;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMoldTool2 extends Slot
{
	public SlotMoldTool2(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return (itemstack.getItem() instanceof ItemPotteryMold ||
				itemstack.getItem() == TFCItems.ceramicMold) && itemstack.getItemDamage() == 1 ||
				itemstack.getItem() instanceof ItemMeltedMetal && TFC_ItemHeat.getIsLiquid(itemstack);
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
