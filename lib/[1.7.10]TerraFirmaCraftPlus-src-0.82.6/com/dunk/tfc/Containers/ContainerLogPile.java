package com.dunk.tfc.Containers;

import com.dunk.tfc.Containers.Slots.SlotLogPile;
import com.dunk.tfc.Core.Player.PlayerInventory;
import com.dunk.tfc.TileEntities.TELogPile;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerLogPile extends ContainerTFC
{
	private World world;
	// private int posX;
	// private int posY;
	// private int posZ;
	private TELogPile logpile;
	private EntityPlayer player;

	public ContainerLogPile(InventoryPlayer playerinv, TELogPile pile, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.logpile = pile;
		this.world = world;
		// this.posX = x;
		// this.posY = y;
		// this.posZ = z;
		pile.openInventory();
		layoutContainer(playerinv, pile, 0, 0);
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
	}

	@Override
	public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
	{
		ItemStack is = super.slotClick(par1, par2, par3, par4EntityPlayer);
		try
		{
			Slot slot = (Slot) this.inventorySlots.get(par1);
			if (slot.getHasStack() && par1 < 4)
			{
				ItemStack is2 = slot.getStack();
				if (is2.getItem() == TFCItems.thickLogs && is2.stackSize > 1)
				{
					int excess = is2.stackSize - 1;
					ItemStack dropItem = is2.copy();
					dropItem.stackSize = excess;
					is2.stackSize = 1;
					slot.putStack(is2);
					slot.onSlotChanged();
					saveContents(is2);
					if (!par4EntityPlayer.worldObj.isRemote)
					{
						EntityItem droppedLogs = new EntityItem(par4EntityPlayer.worldObj, par4EntityPlayer.posX, par4EntityPlayer.posY, par4EntityPlayer.posZ, dropItem);
						par4EntityPlayer.worldObj.spawnEntityInWorld(droppedLogs);
					}
				}
			}
		}
		catch (Exception e)
		{

		}
		return is;
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);

		if (!world.isRemote)
			logpile.closeInventory();
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift
	 * clicking.
	 */
	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// From pile to inventory
			if (slotNum < 4)
			{
				if (!this.mergeItemStack(slotStack, 4, inventorySlots.size(), true))
					return null;
			}
			else
			{
				if (!this.mergeItemStack(slotStack, 0, 4, false))
					return null;
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

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize)
	{
		this.addSlotToContainer(new SlotLogPile(getPlayer(), chestInventory, 0, 71, 25));
		this.addSlotToContainer(new SlotLogPile(getPlayer(), chestInventory, 1, 89, 25));
		this.addSlotToContainer(new SlotLogPile(getPlayer(), chestInventory, 2, 71, 43));
		this.addSlotToContainer(new SlotLogPile(getPlayer(), chestInventory, 3, 89, 43));
	}

	public EntityPlayer getPlayer()
	{
		return player;
	}
}
