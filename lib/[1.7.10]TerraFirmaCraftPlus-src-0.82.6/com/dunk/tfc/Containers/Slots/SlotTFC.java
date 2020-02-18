package com.dunk.tfc.Containers.Slots;

import java.util.ArrayList;

import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.Interfaces.IFood;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTFC extends Slot {

	public SlotTFC(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}

	@Override
	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
	 * stack.
	 */
	public ItemStack decrStackSize(int num)
	{
		//Temp2 is the itemstack of the num items removed from the slot
		ItemStack temp2 = this.inventory.decrStackSize(getSlotIndex(), num);
		//Temp is the itemstack left behind
		ItemStack temp = this.inventory.getStackInSlot(getSlotIndex());
		//Here, we need to check if it's a food
		//and then we need to take from the food set.
		if(temp2 != null && temp2.getItem() instanceof IFood)
		{
			if(temp != null)
			{
			ArrayList<float[]> foods = Food.getFoodsInStack(temp);
			if(foods != null){
				ArrayList<float[]> takenFoods = new ArrayList<float[]>();
				for(int i = 0; i < num; i++)
				{
					takenFoods.add(foods.remove(0));
				}
			//	System.out.println("The original weight was " + Food.getWeight(temp));
				Food.setFoodsInStack(temp, foods);
				Food.setFoodsInStack(temp2, takenFoods);
			//	System.out.println("The remaining weights are " + Food.getWeight(temp) + " in temp and " + Food.getWeight(temp2) + "in temp 2");
				this.inventory.setInventorySlotContents(getSlotIndex(), temp);
			}
			}
			else
			{
			//	System.out.println("uh oph");
			}
		}
		else
		{
		//	System.out.println("Something happened!");
		}
		return temp2;
	}

}
