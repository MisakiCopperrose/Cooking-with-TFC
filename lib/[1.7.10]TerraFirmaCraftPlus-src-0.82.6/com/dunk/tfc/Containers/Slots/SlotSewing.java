package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.Containers.ContainerSewing;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.Items.ItemClothingPiece;
import com.dunk.tfc.Items.ItemYarn;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Interfaces.ISewable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSewing extends Slot
{

	private ContainerSewing theContainer;
	private boolean locked;
	
	public SlotSewing(ContainerSewing cs, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		theContainer = cs;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return locked? (itemstack.getItem() instanceof ItemYarn && getStack() == null):itemstack.getItem() instanceof ItemClothingPiece || itemstack.getItem() instanceof ItemYarn || itemstack.getItem() instanceof ISewable;
	}
	
	@Override
	public void onSlotChanged()
	{
		super.onSlotChanged();
		//We should be telling the container to check if the recipe matches
		theContainer.checkForRecipe();
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
	{
		return !locked;
	}

/*
	@Override
	@SideOnly(Side.CLIENT)
	public boolean func_111238_b()
	{
		return locked;
	}*/
	
	public void lock()
	{
		locked = true;
	}
	
	public void unlock()
	{
		locked = false;
	}
}
