package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.Items.ItemOre;
import com.dunk.tfc.Items.Tools.ItemFirestarter;
import com.dunk.tfc.api.HeatRegistry;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFirepitIn extends Slot
{
	public SlotFirepitIn(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack is)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		return is.getItem() instanceof ItemFirestarter ||
				is.getItem() == TFCItems.flintSteel ||
				!(manager.findMatchingIndex(is) == null ||
					is.getItem() instanceof ItemOre) || 
				(is.getItem() instanceof ItemClothing && is.stackTagCompound != null && is.stackTagCompound.getInteger("wetness") > 0);
	}

	@Override
	public void putStack(ItemStack is)
	{
		if (is != null)
			is.stackSize = 1;
		if (this.inventory != null)
			super.putStack(is);
	}
}
