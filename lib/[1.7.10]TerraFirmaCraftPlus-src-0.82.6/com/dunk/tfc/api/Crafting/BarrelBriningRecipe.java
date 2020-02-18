package com.dunk.tfc.api.Crafting;

import com.dunk.tfc.TileEntities.TEBarrel;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.Interfaces.IFood;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BarrelBriningRecipe extends BarrelRecipe
{

	public BarrelBriningRecipe(FluidStack inputFluid, FluidStack outputFluid)
	{
		super(null, inputFluid, null, outputFluid, 4);
	}

	@Override
	public Boolean matches(ItemStack item, FluidStack fluid, TEBarrel te)
	{
		if(item != null && item.getItem() instanceof IFood && !Food.isBrined(item))
		{
			float w = Food.getWeight(item);
			if (fluid.isFluidEqual(recipeFluid) && w <= 1f * (fluid.amount / this.recipeFluid.amount))
			{
				return true;
			}
		}
		return false;
	}
}
