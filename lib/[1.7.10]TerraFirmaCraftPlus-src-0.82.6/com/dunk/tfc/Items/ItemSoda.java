package com.dunk.tfc.Items;

import com.dunk.tfc.api.Food;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSoda extends ItemTerra 
{

	
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			if(is.hasTagCompound())
			{
				float weight = Food.getWeight(is);
				int size = (int) (weight/2.5f);
				//is.stackSize = size;
				is.stackTagCompound = null;
				return true;
			}
		}
		return super.onUpdate(is, world, x, y, z);
	}
	
}
