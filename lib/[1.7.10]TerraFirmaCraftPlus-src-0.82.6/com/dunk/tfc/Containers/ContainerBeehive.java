package com.dunk.tfc.Containers;

import java.util.ArrayList;
import java.util.List;

import com.dunk.tfc.Containers.Slots.SlotChest;
import com.dunk.tfc.Containers.Slots.SlotForShowOnly;
import com.dunk.tfc.Core.Player.PlayerInventory;
import com.dunk.tfc.TileEntities.TEBarrel;
import com.dunk.tfc.TileEntities.TEBasket;
import com.dunk.tfc.TileEntities.TEBeehive;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Enums.EnumSize;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class ContainerBeehive extends ContainerTFC
{
	TEBeehive hive;

	public ContainerBeehive(InventoryPlayer inventoryplayer, TEBeehive teBeehive, World world, int x, int y, int z)
	{
		this.hive = teBeehive;
		this.hive.numberInteractingPlayers++;
		buildLayout();

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);
	}


	protected void buildLayout()
	{

		for (int i = 0; i < 3; i++)
		{
			for (int k = 0; k < 2; k++)
			{

				addSlotToContainer(new SlotChest(hive, i + (k * 3), 62 + (i * 18), 25 + (k * 18)).setSize(EnumSize.MEDIUM).addItemException(ContainerChestTFC.getExceptions()));

			}
		}

	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot) inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();
			
			// From solid storage slots to inventory
			if (slotNum < 6)
			{
				if (!this.mergeItemStack(slotStack, 6, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				// To solid storage

					if (!this.mergeItemStack(slotStack, 0, 6, false))
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

	public static List<Item> getExceptions()
	{
		List<Item> exceptions = new ArrayList<Item>();
		/* exceptions.add(TFCItems.logs); */
		exceptions.add(TFCItems.thickLogs);
		exceptions.add(TFCItems.stackedLogs);
		exceptions.add(Item.getItemFromBlock(TFCBlocks.barrel));
		exceptions.add(Item.getItemFromBlock(TFCBlocks.vessel));
		return exceptions;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	// private int updatecounter = 0;
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting) this.crafters.get(var1);

		}
		// barrel.getWorldObj().markBlockForUpdate(barrel.xCoord, barrel.yCoord,
		// barrel.zCoord);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		this.hive.numberInteractingPlayers--;
	}

	@Override
	public void updateProgressBar(int id, int val)
	{
		/*if (id == 0)
		{
			if (barrel.fluid != null)
			{
				this.barrel.fluid = new FluidStack(val, this.barrel.fluid.amount);
			}
			else
			{
				this.barrel.fluid = new FluidStack(val, 1000);
			}
			barrel.processItems();
		}
		else if (id == 1)
		{
			if (barrel.fluid != null)
				this.barrel.fluid.amount = val;
		}*/
	}
}
