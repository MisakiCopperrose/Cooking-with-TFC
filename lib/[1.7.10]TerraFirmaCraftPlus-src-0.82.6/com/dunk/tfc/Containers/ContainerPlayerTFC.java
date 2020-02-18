package com.dunk.tfc.Containers;

import java.util.ArrayList;

import com.dunk.tfc.Containers.Slots.SlotArmorTFC;
import com.dunk.tfc.Core.Player.PlayerInventory;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Handlers.CraftingHandler;
import com.dunk.tfc.Handlers.FoodCraftingHandler;
import com.dunk.tfc.Items.ItemTFCArmor;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Interfaces.IEquipable;
import com.dunk.tfc.api.Interfaces.IEquipable.EquipType;
import com.dunk.tfc.api.Interfaces.IFood;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerPlayerTFC extends ContainerPlayer
{
	private final EntityPlayer thePlayer;

	public ContainerPlayerTFC(InventoryPlayer playerInv, boolean par2, EntityPlayer player)
	{
		super(playerInv, par2, player);
		this.craftMatrix = new InventoryCrafting(this, 3, 3);
		this.inventorySlots.clear();
		this.inventoryItemStacks.clear();
		this.thePlayer = player;
		this.addSlotToContainer(new SlotCrafting(player, craftMatrix, craftResult, 0, 54 + 152, 36));
		int x;
		int y;
		int expandedX = 54;

		for (x = 0; x < 2; ++x)
		{
			for (y = 0; y < 2; ++y)
				this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18, 18 + x * 18));
		}

		for (x = 0; x < playerInv.armorInventory.length; ++x)
		{
			int index = playerInv.getSizeInventory() - 1 - x;
			this.addSlotToContainer(new SlotArmorTFC(this, playerInv, index, 18 + 8, 8 + x * 18, x));
		}
		PlayerInventory.buildInventoryLayout(this, playerInv, (expandedX / 2) + 8, 90, false, true);

		// Manually built the remaining crafting slots because of an order
		// issue. These have to be created after the default slots
		if (player.getEntityData().hasKey("craftingTable") || !player.worldObj.isRemote)
		{
			x = 2;
			y = 0;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18, 18 + x * 18));
			x = 2;
			y = 1;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18, 18 + x * 18));
			x = 0;
			y = 2;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18, 18 + x * 18));
			x = 1;
			y = 2;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18, 18 + x * 18));
			x = 2;
			y = 2;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18, 18 + x * 18));
		}
		else
		{
			// Have to create some dummy slots
			x = 2;
			y = 0;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18 - 50000, 18 + x * 18));
			x = 2;
			y = 1;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18 - 50000, 18 + x * 18));
			x = 0;
			y = 2;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18 - 50000, 18 + x * 18));
			x = 1;
			y = 2;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18 - 50000, 18 + x * 18));
			x = 2;
			y = 2;
			this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, expandedX + 82 + y * 18 - 50000, 18 + x * 18));
		}
		PlayerInventory.addExtraEquipables(this, playerInv, 8, 90, false);
		this.onCraftMatrixChanged(this.craftMatrix);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory iinventory)
	{
		super.onCraftMatrixChanged(iinventory);

		Slot craftOut = (Slot) this.inventorySlots.get(0);
		if (craftOut != null && craftOut.getHasStack())
		{
			ItemStack craftResult = craftOut.getStack();
			if (craftResult != null)
			{
				if (craftResult.getItem() instanceof ItemFoodTFC)
					FoodCraftingHandler.updateOutput(thePlayer, craftResult, craftMatrix);
				else
					CraftingHandler.transferNBT(false, thePlayer, craftResult, craftMatrix);
			}
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		if (!player.worldObj.isRemote)
		{
			super.onContainerClosed(player);

			for (int i = 0; i < 9; ++i)
			{
				ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);
				if (itemstack != null)
					player.dropPlayerItemWithRandomChoice(itemstack, false);
			}

			this.craftResult.setInventorySlotContents(0, (ItemStack) null);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	{
		/*if(this.inventorySlots != null)
			System.out.println("transferring "+ (((Slot) this.inventorySlots.get(slotNum)).getHasStack()? "x" + ((Slot) this.inventorySlots.get(slotNum)).getStack().stackSize : "null" )+ " on " + (player.worldObj.isRemote?"Client":"Server"));
		*/
		ItemStack origStack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotNum);
		Slot equipmentSlot = (Slot) this.inventorySlots.get(50);
		

		if (slot != null && slot.getHasStack())
		{
			//System.out.println("has stack on " + (player.worldObj.isRemote?"Client":"Server"));
			if(slot.getStack().stackSize == 0 && slotNum != 50)
			{
				ItemStack is = slot.getStack();
				is.stackSize = 1;
				slot.putStack(is);
			}
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();
			
			// Crafting Grid Output
			if (slotNum == 0)
			{
				FoodCraftingHandler.preCraft(player, slotStack, craftMatrix);
				CraftingHandler.preCraft(player, slotStack, craftMatrix);

				if (!this.mergeItemStack(slotStack, 9, 45, true, true))
					return null;

				slot.onSlotChange(slotStack, origStack);
			}
			// From crafting grid input to inventory
			else if (slotNum >= 1 && slotNum < 5 || player.getEntityData().hasKey("craftingTable") && slotNum >= 45 && slotNum < 50)
			{
				if (!this.mergeItemStack(slotStack, 9, 45, true, false))
					return null;
			}
			// From armor or equipment slot to inventoryz
			else if ((slotNum >= 5 && slotNum < 9))
			{
				if (!this.mergeItemStack(slotStack, 9, 45, true, false))
					return null;
			}
			// From inventory to armor slots
			else if (origStack.getItem() instanceof ItemArmor)
			{
				int armorSlotNum = 5 + ((ItemArmor) origStack.getItem()).armorType;
				if (origStack.getItem() instanceof ItemTFCArmor)
				{
					armorSlotNum = 5 + ((ItemTFCArmor) origStack.getItem()).getUnadjustedArmorType();

					if (!((Slot) this.inventorySlots.get(armorSlotNum)).getHasStack())
					{
						if (!this.mergeItemStack(slotStack, armorSlotNum, armorSlotNum + 1, false, false))
							return null;
					}
				}
				else if (!((Slot) this.inventorySlots.get(armorSlotNum)).getHasStack())
				{
					if (!this.mergeItemStack(slotStack, armorSlotNum, armorSlotNum + 1, false, false))
						return null;
				}
			}
			// From inventory to back slot
			else if (!equipmentSlot.getHasStack() && origStack.getItem() instanceof IEquipable && ((IEquipable) origStack.getItem()).getEquipType(origStack) == EquipType.BACK && ((IEquipable) origStack.getItem() == TFCItems.quiver || ((IEquipable) origStack.getItem()).getTooHeavyToCarry(origStack)))
			{
					ItemStack backStack = slotStack.copy();
					backStack.stackSize = 1;
					equipmentSlot.putStack(backStack);
					slotStack.stackSize--;
				
			}
			else if (origStack.getItem() instanceof IEquipable && (((IEquipable) origStack.getItem()).getEquipType(origStack) != EquipType.BACK))
			{
			//	System.out.println("equipable on " + (player.worldObj.isRemote?"Client":"Server"));
				IEquipable equipment = (IEquipable) origStack.getItem();
				if (equipment.getEquipType(origStack) == EquipType.HEAD && !((Slot) this.inventorySlots.get(5+0)).getHasStack())
				{
					if (!this.mergeItemStack(slotStack, 5+0, 5+0 + 1, false, false))
						return null;
				}
				else if (equipment.getEquipType(origStack) == EquipType.BODY && !((Slot) this.inventorySlots.get(5+1)).getHasStack())
				{
					if (!this.mergeItemStack(slotStack, 5+1, 5+1 + 1, false, false))
						return null;
				}
				else if (equipment.getEquipType(origStack) == EquipType.LEGS && !((Slot) this.inventorySlots.get(5+2)).getHasStack())
				{
					if (!this.mergeItemStack(slotStack, 5+2, 5+2 + 1, false, false))
						return null;
				}
				else if (equipment.getEquipType(origStack) == EquipType.FEET && !((Slot) this.inventorySlots.get(5+3)).getHasStack())
				{
					if (!this.mergeItemStack(slotStack, 5+3, 5+3 + 1, false, false))
						return null;
				}
				else if (equipment.getEquipType(origStack) == EquipType.HEAD2 && !((Slot) this.inventorySlots.get(5+0)).getHasStack() && !((Slot) this.inventorySlots.get(51)).getHasStack())
				{
					if (!this.mergeItemStack(slotStack, 51, 51 + 1, false, false))
						return null;
				}
				else if (equipment.getEquipType(origStack) == EquipType.BODY2 && !((Slot) this.inventorySlots.get(5+1)).getHasStack()&& !((Slot) this.inventorySlots.get(52)).getHasStack())
				{
					if (!this.mergeItemStack(slotStack, 52, 52 + 1, false, false))
						return null;
				}
				else if (equipment.getEquipType(origStack) == EquipType.LEGS2 && !((Slot) this.inventorySlots.get(5+2)).getHasStack()&& !((Slot) this.inventorySlots.get(53)).getHasStack())
				{
					if (!this.mergeItemStack(slotStack, 53, 53 + 1, false, false))
						return null;
				}
				else if (equipment.getEquipType(origStack) == EquipType.FEET2 && !((Slot) this.inventorySlots.get(5+3)).getHasStack()&& !((Slot) this.inventorySlots.get(54)).getHasStack())
				{
				//	System.out.println("putting on feet on " + (player.worldObj.isRemote?"Client":"Server"));
					if (!this.mergeItemStack(slotStack, 54, 54 + 1, false, false))
						return null;
				}
				
			}
			// Food from inventory/hotbar to crafting grid
			/*
			 * else if (slotNum >= 9 && slotNum < 45 && origStack.getItem()
			 * instanceof IFood && !(origStack.getItem() instanceof ItemMeal) &&
			 * !isCraftingGridFull()) { if (!this.mergeItemStack(slotStack, 1,
			 * 5, false, false) && slotStack.stackSize == 0) return null; else
			 * if (slotStack.stackSize > 0 &&
			 * player.getEntityData().hasKey("craftingTable") &&
			 * !this.mergeItemStack(slotStack, 45, 50, false)) return null; else
			 * if (slotStack.stackSize > 0 && slotNum >= 9 && slotNum < 36) { if
			 * (!this.mergeItemStack(slotStack, 36, 45, false, false)) return
			 * null; } else if (slotStack.stackSize > 0 && slotNum >= 36 &&
			 * slotNum < 45) { if (!this.mergeItemStack(slotStack, 9, 36, false,
			 * false)) return null; } }
			 */
			// From inventory to hotbar
			else if (slotNum >= 9 && slotNum < 36)
			{
				if (!this.mergeItemStack(slotStack, 36, 45, false, false))
					return null;
			}
			// From hotbar to inventory
			else if (slotNum >= 36 && slotNum < 45)
			{
				if (!this.mergeItemStack(slotStack, 9, 36, false, false))
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
		else
		{
			//System.out.println("couldn't find item on " + (player.worldObj.isRemote?"Client":"Server") + slot.getHasStack());
		}

		return origStack;
	}

	@Override
	public ItemStack slotClick(int sourceSlotID, int destSlotID, int clickType, EntityPlayer p)
	{
		if (sourceSlotID >= 0 && sourceSlotID < this.inventorySlots.size())
		{
			Slot sourceSlot = (Slot) this.inventorySlots.get(sourceSlotID);
			ItemStack slotStack = sourceSlot.getStack();

			// Hotbar press to remove from crafting output
			if (clickType == 2 && sourceSlotID == 0 && slotStack != null)
			{
				CraftingHandler.preCraft(p, slotStack, craftMatrix);
			}
			// S and D hotkeys for trimming/combining food
			else if (clickType == 7 && sourceSlotID >= 9 && sourceSlotID < 45)
			{
				if (sourceSlot.canTakeStack(p))
				{
					Slot destSlot = (Slot) this.inventorySlots.get(destSlotID);
					destSlot.putStack(slotStack);
					sourceSlot.putStack(null);
					return null;
				}
			}
			// Couldn't figure out what was causing the food dupe with a full
			// inventory, so we're just going to block shift clicking for that
			// case.
			else if (clickType == 1 && sourceSlotID == 0 && isInventoryFull() && slotStack != null && slotStack.getItem() instanceof IFood)
				return null;
		}
		// if(sourceSlotID == -999)
		// {
		// return super.slotClick(sourceSlotID, destSlotID, 0, p);
		// }
		return super.slotClick(sourceSlotID, destSlotID, clickType, p);
	}

	protected boolean isCraftingGridFull()
	{
		for (int i = 0; i < this.craftMatrix.getSizeInventory(); i++)
		{
			if (this.craftMatrix.getStackInSlot(i) == null)
				return false;
		}
		return true;
	}

	protected boolean isInventoryFull()
	{
		// Slots 9 through 44 are the standard inventory and hotbar.
		for (int i = 9; i < 45; i++)
		{
			if (((Slot) inventorySlots.get(i)).getStack() == null)
				return false;
		}
		return true;
	}

	public EntityPlayer getPlayer()
	{
		return this.thePlayer;
	}

	private boolean canMergeAnyway(ItemStack is, ItemStack is2)
	{
		Item i1 = is.getItem();
		Item i2 = is2.getItem();
		if ((i1 instanceof IFood && i2 instanceof IFood) && i1 == i2 && i1 != TFCItems.honeybowl && i2 != TFCItems.honeybowl)
		{
			return true;
		}
		return false;
	}

	protected boolean mergeItemStack(ItemStack is, int slotStart, int slotFinish, boolean reverseOrder, boolean craftingOutput)
	{
		boolean merged = false;
		int slotIndex = slotStart;
		if(is.stackSize == 0)
		{
			is = null;
			return false;
		}
		if (reverseOrder)
			slotIndex = slotFinish - 1;

		Slot slot;
		ItemStack slotstack;

		if (is.isStackable())
		{
			while (is.stackSize > 0 && (!reverseOrder && slotIndex < slotFinish || reverseOrder && slotIndex >= slotStart))
			{
				slot = (Slot) this.inventorySlots.get(slotIndex);
				slotstack = slot.getStack();

				if (slotstack != null && slotstack.getItem() == is.getItem()
				// && !is.getHasSubtypes()
				&& is.getItemDamage() == slotstack.getItemDamage() && canMergeAnyway(is, slotstack) && slotstack.stackSize < slot.getSlotStackLimit())
				{
					int mergedStackSize = is.stackSize + getSmaller(slotstack.stackSize, slot.getSlotStackLimit());

					// We merge the sizes of the stacks of food
					// We know these are foods.

					float newWeight = Food.getWeight(is) + Food.getWeight(slotstack);
					// System.out.println(newWeight + " the weight");
					if (newWeight <= 160F)
					{
						// First we check if we can add the two stacks together
						// and the resulting stack is smaller than the maximum
						// size for the slot or the stack
						if (mergedStackSize <= is.getMaxStackSize() && mergedStackSize <= slot.getSlotStackLimit())
						{
							ArrayList<float[]> foods2 = Food.getFoodsInStackVerifySize(slotstack);
							ArrayList<float[]> foods = Food.getFoodsInStackVerifySize(is);
							if (is.stackSize > 1 && foods2 != null)
							{

								foods2.addAll(foods);
							}
							else if (foods2 != null)
							{
								foods2.add(new float[] { foods.get(0)[0], foods.get(0)[1] });
							}
							else
							{
								foods2 = new ArrayList<float[]>();
								foods2.add(new float[] { Food.getWeight(slotstack), Food.getDecay(slotstack) });
								foods2.add(new float[] { foods.get(0)[0], foods.get(0)[1] });
							}
							is.stackSize = 0;
							slotstack.stackSize = mergedStackSize;
							Food.setFoodsInStack(slotstack, foods2);
							slot.onSlotChanged();
							// System.out.println("the new weight " +
							// Food.getWeight(slotstack));
							merged = true;
						}
						// Do not attempt merge stacks resulting in greater than
						// the max size for slot/stack if shift-clicking the
						// crafting grid output
						else if (!craftingOutput && slotstack.stackSize < is.getMaxStackSize() && slotstack.stackSize < slot.getSlotStackLimit())
						{
							// Slot stack size is greater than or equal to the
							// item's max stack size. Most containers are this
							// case.
							if (slot.getSlotStackLimit() >= is.getMaxStackSize())
							{
								is.stackSize -= is.getMaxStackSize() - slotstack.stackSize;
								slotstack.stackSize = is.getMaxStackSize();
								slot.onSlotChanged();
								merged = true;
							}
							// Slot stack size is smaller than the item's normal
							// max stack size. Example: Log Piles
							else if (slot.getSlotStackLimit() < is.getMaxStackSize())
							{
								is.stackSize -= slot.getSlotStackLimit() - slotstack.stackSize;
								slotstack.stackSize = slot.getSlotStackLimit();
								slot.onSlotChanged();
								merged = true;
							}
						}
					}
				}
				else if (slotstack != null && slotstack.getItem() == is.getItem()
				// && !is.getHasSubtypes()
				&& is.getItemDamage() == slotstack.getItemDamage() && ItemStack.areItemStackTagsEqual(is, slotstack) && slotstack.stackSize < slot.getSlotStackLimit())
				{
					int mergedStackSize = is.stackSize + getSmaller(slotstack.stackSize, slot.getSlotStackLimit());

					// First we check if we can add the two stacks together and
					// the resulting stack is smaller than the maximum size for
					// the slot or the stack
					if (mergedStackSize <= is.getMaxStackSize() && mergedStackSize <= slot.getSlotStackLimit())
					{
						is.stackSize = 0;
						slotstack.stackSize = mergedStackSize;
						slot.onSlotChanged();
						merged = true;
					}
					// Do not attempt merge stacks resulting in greater than the
					// max size for slot/stack if shift-clicking the crafting
					// grid output
					else if (!craftingOutput && slotstack.stackSize < is.getMaxStackSize() && slotstack.stackSize < slot.getSlotStackLimit())
					{
						// Slot stack size is greater than or equal to the
						// item's max stack size. Most containers are this case.
						if (slot.getSlotStackLimit() >= is.getMaxStackSize())
						{
							is.stackSize -= is.getMaxStackSize() - slotstack.stackSize;
							slotstack.stackSize = is.getMaxStackSize();
							slot.onSlotChanged();
							merged = true;
						}
						// Slot stack size is smaller than the item's normal max
						// stack size. Example: Log Piles
						else if (slot.getSlotStackLimit() < is.getMaxStackSize())
						{
							is.stackSize -= slot.getSlotStackLimit() - slotstack.stackSize;
							slotstack.stackSize = slot.getSlotStackLimit();
							slot.onSlotChanged();
							merged = true;
						}

					}
				}
				if (reverseOrder)
					--slotIndex;
				else
					++slotIndex;
			}
		}

		if (is.stackSize > 0)
		{
			if (reverseOrder)
				slotIndex = slotFinish - 1;
			else
				slotIndex = slotStart;

			while (!reverseOrder && slotIndex < slotFinish || reverseOrder && slotIndex >= slotStart)
			{
				slot = (Slot) this.inventorySlots.get(slotIndex);
				slotstack = slot.getStack();
				if (slotstack == null && slot.isItemValid(is) && slot.getSlotStackLimit() < is.stackSize)
				{
					ItemStack copy = is.copy();
					copy.stackSize = slot.getSlotStackLimit();
					is.stackSize -= slot.getSlotStackLimit();
					slot.putStack(copy);
					slot.onSlotChanged();
					merged = true;
					// this.bagsSlotNum = slotIndex;
					break;
				}
				else if (slotstack == null && slot.isItemValid(is))
				{
					slot.putStack(is.copy());
					slot.onSlotChanged();
					is.stackSize = 0;
					merged = true;
					break;
				}

				if (reverseOrder)
					--slotIndex;
				else
					++slotIndex;
			}
		}
		

		return merged;
	}

	protected int getSmaller(int i, int j)
	{
		if (i < j)
			return i;
		else
			return j;
	}
}
