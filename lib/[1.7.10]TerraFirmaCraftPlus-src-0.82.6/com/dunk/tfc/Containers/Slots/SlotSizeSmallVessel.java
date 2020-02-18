package com.dunk.tfc.Containers.Slots;

import java.util.ArrayList;
import java.util.List;

import com.dunk.tfc.Containers.ContainerVessel;
import com.dunk.tfc.Items.ItemMeltedMetal;
import com.dunk.tfc.Items.Pottery.ItemPotteryBase;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Interfaces.IBag;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.Interfaces.IItemFoodBlock;
import com.dunk.tfc.api.Interfaces.ISize;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotSizeSmallVessel extends Slot
{
	private EnumSize size = EnumSize.SMALL;
	private List<Item> exceptions;
	private ContainerVessel.Types type;

	public SlotSizeSmallVessel(IInventory iinventory, int i, int j, int k, ContainerVessel.Types myType)
	{
		super(iinventory, i, j, k);
		exceptions = new ArrayList<Item>();
		type = myType;
	}
	
	public SlotSizeSmallVessel(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		exceptions = new ArrayList<Item>();
		type = null;
	}
	
	@Override
	public int getSlotStackLimit()
    {
		return super.getSlotStackLimit();
    }

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof IBag ||
				itemstack.getItem() instanceof ItemMeltedMetal ||
				itemstack.getItem() instanceof ItemPotteryBase)
		{
			return false;
		}
		
		Item i = itemstack.getItem();
		if ((i instanceof ISize && ((ISize) i).getSize(itemstack).stackSize >= size.stackSize && !(i instanceof IFood && (type == null || ContainerVessel.validForType((IFood)i, type)))) &&
				(i instanceof IFood || i instanceof IItemFoodBlock) &&
				!(itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(Food.WEIGHT_TAG) && itemstack.getTagCompound().hasKey(Food.DECAY_TAG)))
				return false;
		
		if(i instanceof IFood && type != null)
			return ContainerVessel.validForType((IFood)i, type);
		
		boolean except = exceptions.contains(itemstack.getItem());
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize && !except)
			return true;
		else if (!(itemstack.getItem() instanceof ISize) && !except)
			return true;
		return false;
	}

	public SlotSizeSmallVessel addItemException(List<Item> ex)
	{
		exceptions = ex;
		return this;
	}
}
