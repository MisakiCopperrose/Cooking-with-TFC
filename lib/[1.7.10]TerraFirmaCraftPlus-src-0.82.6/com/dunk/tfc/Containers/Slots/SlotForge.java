package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.ItemOre;
import com.dunk.tfc.Items.Pottery.ItemPotterySheetMold;
import com.dunk.tfc.api.HeatRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotForge extends Slot
{
	public SlotForge(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		return !(manager.findMatchingIndex(itemstack) == null || itemstack.getItem() instanceof ItemOre
				|| itemstack.getItem() instanceof ItemFoodTFC)
				|| (itemstack.getItem() instanceof ItemPotterySheetMold && itemstack.getItemDamage() > 1);
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
