package com.dunk.tfc.api.Crafting;

import java.util.Stack;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class BarrelDyeRecipe extends BarrelRecipe
{
	private int color;
	private boolean white;
	public BarrelDyeRecipe(ItemStack inputItem, FluidStack inputFluid, FluidStack outputFluid, int color)
	{
		super(inputItem, inputFluid, null, outputFluid);
		this.color = color;
		this.sealTime = 4;
	}

	public BarrelDyeRecipe setWhite()
	{
		this.white = true;
		return this;
	}
	
	@Override
	public Stack<ItemStack> getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		Stack<ItemStack> stackList = new Stack();
		ItemStack outStack = null;

		if (recipeOutIS == null)
		{
			stackList.clear();
			outStack = inIS.copy();
			int outputCount = outStack.stackSize * this.getnumberOfRuns(inIS, inFS);
			int maxStackSize = outStack.getMaxStackSize();
			Item item = outStack.getItem();
			int damage = outStack.getItemDamage();

			if(outStack.stackTagCompound == null)
			{
				outStack.stackTagCompound = new NBTTagCompound();
			}
			int j = outStack.stackTagCompound.hasKey("color")?outStack.stackTagCompound.getInteger("color"):16777215;
			
			float f1 = (float) (j >> 16 & 255) / 255.0F;
			float f2 = (float) (j >> 8 & 255) / 255.0F;
			float f3 = (float) (j & 255) / 255.0F;
			
			if(white)
			{
				f1 = (float) Math.min(f1+0.2f,1f);
				f2 = (float) Math.min(f2+0.2f,1f);
				f3 = (float) Math.min(f3+0.2f,1f);
			}
			else
			{
				j = this.color;
				f1 *= (float) (j >> 16 & 255) / 255.0F;
				f2 *= (float) (j >> 8 & 255) / 255.0F;
				f3 *= (float) (j & 255) / 255.0F;
			}
			int outColor = ((int)(f1*255f) << 16) + ((int)(f2*255f) << 8) + (int)(f3*255f);
			outStack.stackTagCompound.setInteger("color", outColor);
			//outStack.stackTagCompound.setInteger("wetness",4000);	//Since we've been soaking in dye, the clothes are soaking wet.
			int remainder = outputCount % maxStackSize; // The amount remaining after making full-sized stacks.
			if (remainder > 0)
			{
				ItemStack extra = new ItemStack(item, remainder, damage);
				extra.stackTagCompound = (NBTTagCompound) outStack.stackTagCompound.copy();
				stackList.push(extra); // Push this on first, so it doesn't end up in the input slot.
				outputCount -= remainder;
			}

			while (outputCount >= maxStackSize) // Add as many full-sized stacks as possible to stackList.
			{
				stackList.push(new ItemStack(item, maxStackSize, damage));
				outputCount -= maxStackSize;
			}
			return stackList;

		}
	/*	if (!removesLiquid && inIS != null && inFS != null)
		{
			stackList.clear();
			outStack = inIS.copy();
			outStack.stackSize -= inFS.amount / this.recipeOutFluid.amount;
			stackList.push(outStack);
		}
		if (outStack == null)
		{
			stackList.clear();
			stackList.push(outStack);
		}*/
		return stackList;
	}
	
}
