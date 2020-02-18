package com.dunk.tfc.Containers;

import com.dunk.tfc.Containers.Slots.SlotOutputOnly;
import com.dunk.tfc.Core.Player.PlayerInventory;
import com.dunk.tfc.TileEntities.TENestBox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerNestBox extends ContainerTFC
{
	/*private World world;
	private int posX;
	private int posY;
	private int posZ;*/

	public ContainerNestBox(InventoryPlayer playerinv, TENestBox te, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		/*this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;*/

		this.addSlotToContainer(new SlotOutputOnly(te, 0, 71, 25));
		this.addSlotToContainer(new SlotOutputOnly(te, 1, 89, 25));
		this.addSlotToContainer(new SlotOutputOnly(te, 2, 71, 43));
		this.addSlotToContainer(new SlotOutputOnly(te, 3, 89, 43));

		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			if (slotNum < 4)
			{
				if (!this.mergeItemStack(slotStack, 4, inventorySlots.size(), true))
					return null;
			}
			// Slots are output only, so there's no need to do input logic.
			/*else 
			{
				if (!this.mergeItemStack(slotStack, 0, 4, false))
					return null;
			}*/

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
