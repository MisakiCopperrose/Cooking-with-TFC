package com.dunk.tfc.Containers;

import com.dunk.tfc.Containers.Slots.SlotForShowOnly;
import com.dunk.tfc.Containers.Slots.SlotOutputOnly;
import com.dunk.tfc.Containers.Slots.SlotSewing;
import com.dunk.tfc.GUI.GuiCarpentry;
import com.dunk.tfc.GUI.GuiSewing;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;

public class ContainerCarpentry extends ContainerTFC
{
	public InventoryCrafting containerInv = new InventoryCrafting(this, 4, 2);
	public InventoryCrafting outputInv = new InventoryCrafting(this, 1, 1);
	private GuiCarpentry clientGUI;
	private int yMod = 100;
	private final int ITEM_BREAK = 3;
	
	public ContainerCarpentry(InventoryPlayer playerinv, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		bagsSlotNum = player.inventory.currentItem;
		layoutContainer(playerinv, 0, 0);
		// This is because we're likely on the server or something
		clientGUI = null;
	}

	public void setGUI(GuiCarpentry g)
	{
		clientGUI = g;
	}
	
	protected void layoutContainer(IInventory playerInventory, int xSize, int ySize)
	{
		/*this.addSlotToContainer(new SlotSewing(this, containerInv, 0, 53, yMod + 8));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 1, 71, yMod + 8));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 2, 89, yMod + 8));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 3, 107, yMod + 8));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 4, 53, yMod + 26));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 5, 71, yMod + 26));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 6, 89, yMod + 26));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 7, 107, yMod + 26));*/

		int row;
		int col;

		for (row = 0; row < 9; ++row)
		{
			if (row == bagsSlotNum + 10000)
				this.addSlotToContainer(new SlotForShowOnly(playerInventory, row, 8 + row * 18, yMod + 112));
			else
				this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, yMod + 112));
		}

		for (row = 0; row < 3; ++row)
		{
			for (col = 0; col < 9; ++col)
				this.addSlotToContainer(
						new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, yMod + 54 + row * 18));
		}
		this.addSlotToContainer(new SlotOutputOnly(outputInv, 0, 143, yMod + 17));
	}
}
