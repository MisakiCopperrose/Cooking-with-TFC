package com.dunk.tfc.Containers.Slots;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.api.Interfaces.IEquipable;
import com.dunk.tfc.api.Interfaces.IEquipable.EquipType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class SlotExtraEquipable extends Slot
{
	public final IEquipable.EquipType armorType;

	public SlotExtraEquipable(IInventory inv, int index, int x, int y, IEquipable.EquipType armortype)
	{
		super(inv, index, x, y);
		armorType = armortype;
	}

	/**
	 * Returns the maximum stack size for a given slot (usually the same as
	 * getInventoryStackLimit(), but 1 in the case of armor slots)
	 */
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
	{
		// System.out.println(TFC_Time.getTotalTicks() +"");
		// return true;
		ItemStack item = this.inventory.getStackInSlot(this.getSlotIndex());
		ItemStack higherSlot = null;
		if (this.armorType != IEquipable.EquipType.BACK)
		{
			switch (armorType)
			{
			case LEGS2:
				higherSlot = par1EntityPlayer.getCurrentArmor(1);
				break;
			case BODY2:
				higherSlot = par1EntityPlayer.getCurrentArmor(2);
				break;
			case FEET2:
				higherSlot = par1EntityPlayer.getCurrentArmor(0);
				break;
			case HEAD2:
				higherSlot = par1EntityPlayer.getCurrentArmor(3);
				break;
			default:
				higherSlot = null;
				break;
			}
		}
		if(higherSlot != null)
		{
			return false;
		}
		return true/*!PlayerManagerTFC.getInstance().getPlayerInfoFromName(par1EntityPlayer.getDisplayName()).clothingWetLock
				|| (item.stackTagCompound == null)
				|| (item.stackTagCompound != null && !item.stackTagCompound.hasKey("wetness"))
				|| (item.stackTagCompound != null && item.stackTagCompound.getInteger("wetness") <= 0)*/;// TFC_Time.getTotalTicks()
																										// %
																										// TFC_Core.getClothingUpdateFrequency()
																										// !=
																										// 1;
	}

	/*
	 * @Override public ItemStack getStack(){ //TerraFirmaCraft.log.info(
	 * "getting itemstack " + super.getStack()); return super.getStack(); }
	 */

	/**
	 * Leave as-is for now. In the future, modify it to allow non-armor items to
	 * be in here. Set up a method to register certain item requirements for
	 * each slot.
	 */
	@Override
	public boolean isItemValid(ItemStack is)
	{
		if (is != null && is.getItem() instanceof IEquipable)
		{
			if (((IEquipable) is.getItem()).getEquipType(is) == armorType)
			{
				// Find exclusions
				// The armor slots are given indices by the following:
				// int index = playerInv.getSizeInventory() - 1 - x;
				if (is.getItem() instanceof ItemClothing)
				{
					EquipType[] exclusions = ((ItemClothing) is.getItem()).getMutuallyExclusiveSlots();
					boolean valid = true;
					int max = this.inventory.getSizeInventory() - 1;
					ItemStack higherSlot = null;
					if (this.armorType != IEquipable.EquipType.BACK)
					{
						switch (armorType)
						{
						case LEGS2:
							higherSlot = this.inventory.getStackInSlot(42);
							break;
						case BODY2:
							higherSlot = this.inventory.getStackInSlot(43);
							break;
						case FEET2:
							higherSlot = this.inventory.getStackInSlot(41);
							break;
						case HEAD2:
							higherSlot = this.inventory.getStackInSlot(44);
							break;
						default:
							higherSlot = null;
							break;
						}
					}
					if(higherSlot != null)
					{
						return false;
					}
					
					// Iterate through the list (if it exists) and check that
					// the corresponding slots are currently empty
					// Checks that our excluded slots don't have anything in
					// them.
					
					for (EquipType e : exclusions)
					{
						switch (e)
						{
						case BACK:
							valid &= this.inventory.getStackInSlot(36) == null;
							break;
						case LEGS:
							valid &= this.inventory.getStackInSlot(max - 1) == null;
							break;
						case LEGS2:
							valid &= this.inventory.getStackInSlot(39) == null;
							break;
						case BODY:
							valid &= this.inventory.getStackInSlot(max - 2) == null;
							break;
						case BODY2:
							valid &= this.inventory.getStackInSlot(38) == null;
							break;
						case HEAD2:
							valid &= this.inventory.getStackInSlot(37) == null;
							break;
						case HEAD:
							valid &= this.inventory.getStackInSlot(max - 3) == null;
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
					// Check that none of the filled slots hate this item.
					for (int i = 0; i < 4; i++)
					{
						ItemStack i1 = this.inventory.getStackInSlot(36 + i);
						ItemStack i2 = this.inventory.getStackInSlot(max - i);
						if (i1 != null && i1.getItem() instanceof ItemClothing)
						{
							EquipType[] excludes = ((ItemClothing) (i1.getItem())).getMutuallyExclusiveSlots();
							for (EquipType e : excludes)
							{
								valid &= e != armorType;
							}
						}
						if (i2 != null && i2.getItem() instanceof ItemClothing)
						{
							EquipType[] excludes = ((ItemClothing) (i2.getItem())).getMutuallyExclusiveSlots();
							for (EquipType e : excludes)
							{
								valid &= e != armorType;
							}
						}
					}
					return valid;
				}

				return true;
			}
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getBackgroundIconIndex()
	{
		return ItemClothing.getEquipIcon(this.armorType);
	}
}
