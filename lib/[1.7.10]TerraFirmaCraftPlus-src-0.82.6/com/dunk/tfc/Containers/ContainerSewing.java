package com.dunk.tfc.Containers;

import java.util.ArrayList;

import com.dunk.tfc.Containers.Slots.SlotForShowOnly;
import com.dunk.tfc.Containers.Slots.SlotOutputOnly;
import com.dunk.tfc.Containers.Slots.SlotSewing;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.GUI.GuiSewing;
import com.dunk.tfc.Items.ItemClothingPiece;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Crafting.ClothingManager;
import com.dunk.tfc.api.Crafting.SewingRecipe;
import com.dunk.tfc.api.Interfaces.ISewable;
import com.dunk.tfc.api.Interfaces.IString;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ContainerSewing extends ContainerTFC
{
	public InventoryCrafting containerInv = new InventoryCrafting(this, 4, 2);
	public InventoryCrafting outputInv = new InventoryCrafting(this, 1, 1);
	private GuiSewing clientGUI;
	private int yMod = 100;
	private final int ITEM_BREAK = 3;

	public ContainerSewing(InventoryPlayer playerinv, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		bagsSlotNum = player.inventory.currentItem;
		layoutContainer(playerinv, 0, 0);
		// This is because we're likely on the server or something
		clientGUI = null;
	}

	public void setGUI(GuiSewing g)
	{
		clientGUI = g;
	}

	public ContainerSewing(GuiSewing gui, InventoryPlayer playerinv, World world, int x, int y, int z)
	{
		this(playerinv, world, x, y, z);
		clientGUI = gui;
	}

	protected void layoutContainer(IInventory playerInventory, int xSize, int ySize)
	{
		this.addSlotToContainer(new SlotSewing(this, containerInv, 0, 53, yMod + 8));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 1, 71, yMod + 8));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 2, 89, yMod + 8));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 3, 107, yMod + 8));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 4, 53, yMod + 26));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 5, 71, yMod + 26));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 6, 89, yMod + 26));
		this.addSlotToContainer(new SlotSewing(this, containerInv, 7, 107, yMod + 26));

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

	// Called by a slot to tell the container that it should check if a recipe
	// exists
	public void checkForRecipe()
	{
		if (clientGUI == null)
		{
			// This is specialized. We know that string doesn't count as an item
			// in the recipe, it's used foItemStackurposes.
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			for (int i = 0; i < 8; i++)
			{
				ItemStack e = (ItemStack) this.inventoryItemStacks.get(i);
				if (e != null && (e.getItem() instanceof ItemClothingPiece || e.getItem() instanceof ISewable))
				{
					items.add(e);
				}
			}

			int index = ClothingManager.getInstance().findMatchingRecipeIndex(items.toArray(new Object[items.size()]));
			for (Object ic : crafters)
			{
				((ICrafting) ic).sendProgressBarUpdate(this, 0, index);
			}
		}
	}

	private int checkForRecipeSelf()
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (int i = 0; i < 8; i++)
		{
			ItemStack e = (ItemStack) this.inventoryItemStacks.get(i);
			if (e != null && (e.getItem() instanceof ItemClothingPiece || e.getItem() instanceof ISewable))
			{
				items.add(e);
			}
		}

		int index = ClothingManager.getInstance().findMatchingRecipeIndex(items.toArray(new Object[items.size()]));
		return index;
	}

	public int checkForString()
	{

		for (int i = 0; i < 8; i++)
		{
			ItemStack e = (ItemStack) this.inventoryItemStacks.get(i);
			if (e != null && e.getItem() instanceof IString)
			{
				return i;
			}
		}

		return -1;
	}

	public ItemStack getRecipe()
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (int i = 0; i < 8; i++)
		{
			ItemStack e = (ItemStack) this.inventoryItemStacks.get(i);
			if (e != null && (e.getItem() instanceof ItemClothingPiece || e.getItem() instanceof ISewable))
			{
				items.add(e);
			}
		}
		Object[] inputs = items.toArray(new Object[items.size()]);
		SewingRecipe recipe = ClothingManager.getInstance()
				.getRecipeByIndex(
						ClothingManager.getInstance().findMatchingRecipeIndex(inputs));
		return recipe.getSewingPattern().getOutput(recipe,inputs );
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		if (!player.worldObj.isRemote)
		{
			super.onContainerClosed(player);

			for (int i = 0; i < 8; ++i)
			{
				ItemStack itemstack = this.containerInv.getStackInSlotOnClosing(i);
				if (itemstack != null)
					player.dropPlayerItemWithRandomChoice(itemstack, false);
			}
			ItemStack itemstack = this.outputInv.getStackInSlotOnClosing(0);
			if (itemstack != null)
				player.dropPlayerItemWithRandomChoice(itemstack, false);
			this.containerInv.setInventorySlotContents(0, (ItemStack) null);
		}
	}

	@Override
	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		// We use this for our client GUI.
		if (index == 0)
		{
			// This means we're doing the recipe
			clientGUI.setSewingRecipe(value);
		}
		else if (index == 1)
		{
			// This means we're completing the recipe
		}
		else if (index == ITEM_BREAK)
		{
			clientGUI.mc.thePlayer.inventory.setItemStack(null);
		}
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer entityplayer, int slotNum)
	{
		return null;
	}

	@Override
	public ItemStack slotClick(int p_75144_1_, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_)
	{
		if (p_75144_1_ > -1 && p_75144_2_ > -1 && p_75144_3_ > -1)
		{
			return super.slotClick(p_75144_1_, p_75144_2_, p_75144_3_, p_75144_4_);
		}
		if(p_75144_1_ == -1)
		{
			//System.out.println("it was -1!!!");
		}
		// This means we successfully crafted our thing.
		if (p_75144_1_ == -5023)
		{
			// If the output is currently empty
			if (outputInv.getStackInSlot(0) == null)
			{
				// Set the ouput to the output of the recipe
				outputInv.setInventorySlotContents(0, getRecipe());
			}
			// If the output is not empty but the items are the same and the
			// size of the current output is less than its maximum
			else if (outputInv.getStackInSlot(0).getItem() == getRecipe().getItem()
					&& outputInv.getStackInSlot(0).stackSize < getRecipe().getMaxStackSize()
					&& (outputInv.getStackInSlot(0).stackTagCompound == getRecipe().stackTagCompound
							|| outputInv.getStackInSlot(0).stackTagCompound.equals(getRecipe().stackTagCompound)))
			{
				// Increment the size of the stack
				ItemStack e = outputInv.getStackInSlot(0);
				e.stackSize++;
				outputInv.setInventorySlotContents(0, e);
			}
			else
			{
				return null;
			}
			SewingRecipe r = ClothingManager.getInstance().getRecipeByIndex(checkForRecipeSelf());
			ItemStack[] reqs = r.getRequiredPieces();
			for (ItemStack it : reqs)
			{
				boolean alreadyChecked = false;
				for (int i = 0; i < 8 && !alreadyChecked; ++i)
				{
					if (this.containerInv.getStackInSlot(i) != null
							&& this.containerInv.getStackInSlot(i).getItem() == it.getItem()
							&& (this.containerInv.getStackInSlot(i).getItemDamage() == it.getItemDamage() || (r.getRepairRecipe() && it.getItemDamage() == OreDictionary.WILDCARD_VALUE))
							&& !(this.containerInv.getStackInSlot(i).getItem() instanceof IString))
					{
						this.containerInv.decrStackSize(i, 1);
						alreadyChecked = true;
					}
				}
			}
			for (int i = 0; i < 8; ++i)
			{
				// Unlock the input because we're reseting all the stitches.
				((SlotSewing) this.inventorySlots.get(i)).unlock();
			}
			if (clientGUI != null)
			{
				clientGUI.reset();
			}
			// This should ensure that the recipe is back after
			else if (clientGUI != null)
			{
				checkForRecipe();
			}
		}
		else if (p_75144_1_ == -5024)
		{
			// This means we're locking the input slots
			if (p_75144_2_ == -1)
			{
				for (int i = 0; i < 8; i++)
				{
					((SlotSewing) this.inventorySlots.get(i)).lock();
				}
			}
			// This means we're unlocking the input slots
			else if (p_75144_2_ == -2)
			{
				for (int i = 0; i < 8; i++)
				{
					((SlotSewing) this.inventorySlots.get(i)).unlock();
				}
			}
		}
		else if (p_75144_1_ == -5025)
		{
			// Damages the needle.
			// If the needle is out of health, we need to do a couple things
			if (p_75144_4_.inventory.getItemStack().getItem() == TFCItems.boneNeedleStrung
					|| p_75144_4_.inventory.getItemStack().getItem() == TFCItems.ironNeedleStrung)
			{
				boolean willBreak = (p_75144_4_.inventory.getItemStack().getItemDamage() + 1 == p_75144_4_.inventory
						.getItemStack().getMaxDamage());
				p_75144_4_.inventory.getItemStack().damageItem(1, p_75144_4_);
				// This means our item should break
				if (willBreak)
				{
					// If the needle is bone, it might break
					if (p_75144_4_.inventory.getItemStack().getItem() == TFCItems.boneNeedleStrung && clientGUI == null
							&& TFC_Time.getTotalTicks() % 8 == 0)
					{
						// We break the needle.
						p_75144_4_.inventory.setItemStack(null);
						for (Object ic : crafters)
						{
							((ICrafting) ic).sendProgressBarUpdate(this, ITEM_BREAK, 0);
						}
					}
					int yarnSlot = checkForString();
					if (yarnSlot > -1)
					{
						ItemStack itemstack = null;
						if(p_75144_4_.inventory.getItemStack() != null)
						itemstack = new ItemStack(p_75144_4_.inventory.getItemStack().getItem(), 1);
						// Set the string value
						if (itemstack != null && itemstack.hasTagCompound())
						{
							itemstack.stackTagCompound.setBoolean("stringed", true);
							itemstack.stackTagCompound.setString("string", "yarn");
						}
						else if(itemstack != null)
						{
							NBTTagCompound s = new NBTTagCompound();
							s.setBoolean("stringed", true);
							s.setString("string", "yarn");
							itemstack.stackTagCompound = s;
						}
						p_75144_4_.inventory.setItemStack(itemstack);
						this.containerInv.decrStackSize(yarnSlot, 1);
					}
					else if (p_75144_4_.inventory.getItemStack() != null && p_75144_4_.inventory.getItemStack().getItem() == TFCItems.boneNeedleStrung)
					{
						p_75144_4_.inventory.setItemStack(new ItemStack(TFCItems.boneNeedle, 1, 0));
					}
					else if (p_75144_4_.inventory.getItemStack() != null && p_75144_4_.inventory.getItemStack().getItem() == TFCItems.boneNeedleStrung)
					{
						p_75144_4_.inventory.setItemStack(new ItemStack(TFCItems.ironNeedle, 1, 0));
					}
				}
			}
		}
		else if (p_75144_1_ == -5026)
		{
			// Try to replenish the needle
			int yarnSlot = checkForString();
			if (yarnSlot > -1 && p_75144_4_.inventory.getItemStack().stackSize == 1)
			{
				Item p = null;
				if (p_75144_4_.inventory.getItemStack().getItem() == TFCItems.boneNeedle)
				{
					p = TFCItems.boneNeedleStrung;
				}
				else if (p_75144_4_.inventory.getItemStack().getItem() == TFCItems.ironNeedle)
				{
					p = TFCItems.ironNeedle;
				}
				ItemStack itemstack = new ItemStack(p, 1);
				// Set the string value
				if (itemstack.hasTagCompound())
				{
					itemstack.stackTagCompound.setBoolean("stringed", true);
					itemstack.stackTagCompound.setString("string", "yarn");
				}
				else
				{
					NBTTagCompound s = new NBTTagCompound();
					s.setBoolean("stringed", true);
					s.setString("string", "yarn");
					itemstack.stackTagCompound = s;
				}
				p_75144_4_.inventory.setItemStack(itemstack);
				this.containerInv.decrStackSize(yarnSlot, 1);
			}
		}
		return null;
	}
}
