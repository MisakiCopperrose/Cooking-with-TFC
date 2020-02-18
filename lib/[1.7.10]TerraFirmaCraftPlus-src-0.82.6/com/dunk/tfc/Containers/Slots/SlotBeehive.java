package com.dunk.tfc.Containers.Slots;

import java.util.ArrayList;
import java.util.List;

import com.dunk.tfc.Items.ItemHoneycomb;
import com.dunk.tfc.Items.ItemLeatherBag;
import com.dunk.tfc.Items.Tools.ItemTerraTool;
import com.dunk.tfc.Items.Tools.ItemWeapon;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Interfaces.ISize;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class SlotBeehive extends SlotTFC
{

	public SlotBeehive(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack != null && itemstack.getItem() instanceof ItemHoneycomb)
		{
			return true;
		}
		return false;
	}
}
