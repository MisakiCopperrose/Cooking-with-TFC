package com.dunk.tfc.api.Crafting;

import java.util.Stack;

import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.TileEntities.TEBarrel;
import com.dunk.tfc.api.Food;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class BarrelAlcoholRecipe extends BarrelRecipe
{

	public BarrelAlcoholRecipe(ItemStack inputItem, FluidStack inputFluid,
			ItemStack outIS, FluidStack outputFluid) {
		super(inputItem, inputFluid, outIS, outputFluid);
		this.sealTime = 72;
	}

	@Override
	public Stack<ItemStack> getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		Stack<ItemStack> result = new Stack<ItemStack>();
		result.push(recipeOutIS);
		return result;
	}

	@Override
	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime,int fireHeatTicks)
	{
		float amt = inFS.amount/10000f;
		FluidStack out = recipeOutFluid.copy();
		if(out.tag == null)
			out.tag = new NBTTagCompound();
		float weight = Food.getWeight(inIS);
		out.tag.setFloat("potency", (weight/Food.getWeight(recipeIS))/amt);
		return recipeOutFluid;
	}

	@Override
	public Boolean matches(ItemStack itemstack, FluidStack inFluid, TEBarrel te)
	{
		if(recipeIS.hasTagCompound())
		{
			if(te.isHeated())
			{
				return false;
			}
			if(itemstack == null || (!itemstack.hasTagCompound() && itemstack.getItem() instanceof ItemFoodTFC))
			{
				return false;
			}
			if(this.requiresCooked && itemstack != null && !Food.isCooked(itemstack))
			{
				return false;
			}
			if(recipeIS.getItem() instanceof ItemFoodTFC)
			{
				if(!(itemstack.getItem() instanceof ItemFoodTFC))
				{
					return false;
				}
				float recipeWeight = Food.getWeight(recipeIS);
				float itemstackWeight = Food.getWeight(itemstack);
				float percent = itemstackWeight/(recipeWeight * ((float)inFluid.amount/(float)recipeFluid.amount));
				if (percent < 0.9f)
					return false;
			}
			else
			{
				if((itemstack.getItem() instanceof ItemFoodTFC))
				{
					return false;
				}
				float recipeWeight = recipeIS.stackSize;
				float itemstackWeight = itemstack.stackSize;
				float percent = itemstackWeight/(recipeWeight * ((float)inFluid.amount/(float)recipeFluid.amount));
				if (percent < 0.9f || percent > 1.1f)
					return false;
			}
		}
		else if(this.recipeIS != null && !this.recipeIS.hasTagCompound())
		{
			//assume we're making alcohol from a non-food
			boolean initialMatch = super.matches(itemstack, inFluid, te);
			
			if(inFluid.amount > 0 && initialMatch)
			{
				float ratio =  inFluid.amount / itemstack.stackSize;
				float recipeRatio = this.recipeFluid.amount;
				if(recipeRatio * 0.92f < ratio && recipeRatio * 1.08f > ratio)
				{
					return true;
				}
				return false;
			}
		}
		return OreDictionary.itemMatches(recipeIS, itemstack, false) && inFluid.isFluidEqual(recipeFluid);
	}
}
