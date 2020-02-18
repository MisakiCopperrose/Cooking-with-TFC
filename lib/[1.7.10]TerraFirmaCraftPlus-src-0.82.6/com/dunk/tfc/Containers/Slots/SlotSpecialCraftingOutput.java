package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Containers.ContainerSpecialCrafting;
import com.dunk.tfc.GUI.GuiKnapping;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSpecialCraftingOutput extends Slot
{
	private final IInventory craftMatrix;
	private EntityPlayer thePlayer;
	private Container container;

	public SlotSpecialCraftingOutput(Container container, EntityPlayer entityplayer, IInventory iinventory, IInventory iinventory1, int i, int j, int k)
	{
		super(iinventory1, i, j, k);
		this.container = container;
		thePlayer = entityplayer;
		craftMatrix = iinventory;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return false;
	}


	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack)
	{
		itemstack.onCrafting(thePlayer.worldObj, thePlayer, slotNumber);
		TerraFirmaCraft.proxy.takenFromCrafting(thePlayer, itemstack, craftMatrix);

		for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
		{
			// Clear out everything in the crafting matrix.
			craftMatrix.setInventorySlotContents(i, null);
			if (player.worldObj.isRemote)
			{
				((GuiKnapping) Minecraft.getMinecraft().currentScreen).resetButton(i);
			}
		}

		// Reset decreasedStack flag so another item can be created if the clay forming is reset with NEI.
		((ContainerSpecialCrafting) container).setDecreasedStack(false);
	}
}
