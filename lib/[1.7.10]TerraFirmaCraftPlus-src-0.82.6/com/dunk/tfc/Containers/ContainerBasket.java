package com.dunk.tfc.Containers;

import com.dunk.tfc.Containers.Slots.SlotChest;
import com.dunk.tfc.Containers.Slots.SlotForShowOnly;
import com.dunk.tfc.TileEntities.TEBasket;
import com.dunk.tfc.TileEntities.TEVessel;
import com.dunk.tfc.api.Enums.EnumSize;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerBasket extends ContainerBarrel
{

	public ContainerBasket(InventoryPlayer inventoryplayer, TEBasket tileentitybarrel, World world, int x, int y, int z, int tab)
	{
		super(inventoryplayer, tileentitybarrel, world, x, y, z, tab);
	}

	@Override
	protected void buildLayout()
	{
		if(guiTab == 1)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int k = 0; k < 3; k++)
				{
					if(!barrel.getSealed())
						addSlotToContainer(new SlotChest(barrel, k+(i*3), 71+(i*18), 17+(k*18)).setSize(EnumSize.MEDIUM).addItemException(ContainerChestTFC.getExceptions()));
					else
						addSlotToContainer(new SlotForShowOnly(barrel, k+(i*3), 71+(i*18), 17+(k*18)));
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)inventorySlots.get(slotNum);

		if (!barrel.getSealed() && slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// From liquid input slot to inventory
			if (slotNum < 1 && guiTab == 0)
			{
				if(!this.mergeItemStack(slotStack, 1, this.inventorySlots.size(), true))
					return null;
			}
			// From solid storage slots to inventory
			else if(slotNum < 9 && guiTab == 1)
			{
				if(!this.mergeItemStack(slotStack, 9, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				// To solid storage
				if (guiTab == 1)
				{
					if (!this.mergeItemStack(slotStack, 0, 9, false))
						return null;
				}
				// To liquid input slot
				else if (guiTab == 0)
				{
					if (!this.mergeItemStack(slotStack, 0, 1, false))
						return null;
				}
			}

			if (slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}

		return origStack;
	}
}
