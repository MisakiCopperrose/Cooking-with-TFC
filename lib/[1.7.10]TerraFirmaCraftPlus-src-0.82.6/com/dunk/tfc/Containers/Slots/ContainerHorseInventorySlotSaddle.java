package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.Containers.ContainerHorseInventoryTFC;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventorySlotSaddle extends Slot
{
	//private final ContainerHorseInventoryTFC field_111239_a;

    public ContainerHorseInventorySlotSaddle(ContainerHorseInventoryTFC par1ContainerHorseInventory, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
		//this.field_111239_a = par1ContainerHorseInventory;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
	public boolean isItemValid(ItemStack par1ItemStack)
    {
    	//We replace saddle.
        return super.isItemValid(par1ItemStack) && par1ItemStack.getItem() == TFCItems.saddleTFC && !this.getHasStack();
    }
}
