package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.Containers.ContainerPlayerTFC;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.api.Interfaces.IEquipable.EquipType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class SlotArmorTFC extends Slot 
{
	public final int armorType;
	public final EquipType equipType;
	private final ContainerPlayerTFC parent;
	public SlotArmorTFC(ContainerPlayerTFC cont, IInventory inv, int index, int x, int y, int armortype) 
	{
		super(inv, index, x, y);
		this.parent = cont;
		armorType = armortype;
		switch(armorType)
		{
		case 0:
			equipType=EquipType.HEAD;
			break;
		case 1:
			equipType=EquipType.BODY;
			break;
		case 2:
			equipType=EquipType.LEGS;
			break;
		case 3:
			equipType=EquipType.FEET;
			break;
		default:
			equipType = EquipType.NULL;
		}
	}


	/**
	 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
	 * of armor slots)
	 */
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
	 */
	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		Item item = (par1ItemStack == null ? null : par1ItemStack.getItem());
		boolean valid = item != null && item.isValidArmor(par1ItemStack, armorType, parent.getPlayer());
		if(valid)
		{
		//Check that none of the filled slots hate this item.
		int max = this.inventory.getSizeInventory()-1;
		
		if(par1ItemStack.getItem() instanceof ItemClothing)
		{
			EquipType[] exclusions = ((ItemClothing)par1ItemStack.getItem()).getMutuallyExclusiveSlots();
			//Iterate through the list (if it exists) and check that the corresponding slots are currently empty
			//Checks that our excluded slots don't have anything in them.
			
			for(EquipType e : exclusions)
			{										
				switch(e)
				{
				case BACK:
					valid &= this.inventory.getStackInSlot(36) == null;
					break;
				case LEGS:
					valid &= this.inventory.getStackInSlot(max-1) == null;
					break;
				case LEGS2:
					valid &= this.inventory.getStackInSlot(39) == null;
					break;
				case BODY:
					valid &= this.inventory.getStackInSlot(max-2) == null;
					break;
				case BODY2:
					valid &= this.inventory.getStackInSlot(38) == null;
					break;
				case HEAD2:
					valid &= this.inventory.getStackInSlot(37) == null;
					break;
				case HEAD:
					valid &= this.inventory.getStackInSlot(max-3) == null;
					break;
				case FEET2:
					valid &= this.inventory.getStackInSlot(40) == null;
					break;
				case FEET:
					valid &= this.inventory.getStackInSlot(max) == null;
					break;
				default:
					break;
				}
			}
		}
		
		for(int i = 0; i < 4; i++)
		{
			ItemStack i1 = this.inventory.getStackInSlot(36+i);
			ItemStack i2 = this.inventory.getStackInSlot(max-i);
			if(i1 != null && i1.getItem() instanceof ItemClothing)
			{
				EquipType[] excludes = ((ItemClothing)(i1.getItem())).getMutuallyExclusiveSlots();
				for(EquipType e : excludes)
				{
					valid &= e != equipType;
				}
			}
			if(i2 != null && i2.getItem() instanceof ItemClothing)
			{
				EquipType[] excludes = ((ItemClothing)(i2.getItem())).getMutuallyExclusiveSlots();
				for(EquipType e : excludes)
				{
					valid &= e != equipType;
				}
			}
		}
		}
		return valid;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getBackgroundIconIndex()
	{
		return ItemArmor.func_94602_b(this.armorType);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean func_111238_b()
	{
		return true;
	}

}
