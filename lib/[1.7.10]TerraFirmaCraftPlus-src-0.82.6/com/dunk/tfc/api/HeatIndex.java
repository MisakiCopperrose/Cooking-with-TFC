package com.dunk.tfc.api;

import java.util.Random;

import com.dunk.tfc.Items.Pottery.ItemPotteryMoldBase;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class HeatIndex
{
	public float specificHeat;
	public float meltTemp;
	public boolean keepNBT;

	private ItemStack output;
	private int outputMin;
	private int outputMax;
	
	//This keeps track of whether we allow multiples of the initial damage.
	private int counter = -1;
	private int damageOffset = 0;
	
	private float weightRatio = 0;

	private ItemStack morph;
	public ItemStack input;

	public HeatIndex(ItemStack in, double sh, double melt, ItemStack out)
	{
		input = in;
		specificHeat = (float)sh;
		meltTemp = (float)melt;
		output = out;
	}

	public HeatIndex(ItemStack in, HeatRaw raw)
	{
		input = in;
		specificHeat = raw.specificHeat;
		meltTemp = raw.meltTemp;
	}

	public HeatIndex(ItemStack in, HeatRaw raw, ItemStack out)
	{
		this(in, raw);
		output = out;
	}
	
	public HeatIndex(ItemStack in, HeatRaw raw, ItemStack out, int counter, int dmgOffset)
	{
		this(in, raw);
		this.counter = counter;
		this.damageOffset = dmgOffset;
		output = out;
	}

	public HeatIndex setKeepNBT(boolean k)
	{
		keepNBT = k;
		return this;
	}

	public boolean hasOutput(){
		return output != null;
	}

	public Item getOutputItem()
	{
		if(output!= null)
			return output.getItem();
		else return null;
	}

	public int getOutputDamage()
	{
		if(output!= null)
			return output.getItemDamage();
		else return 0;
	}

	public HeatIndex setMinMax(int min, int max)
	{
		outputMin = min;
		outputMax = max;
		return this;
	}

	public HeatIndex setMinMax(int amt)
	{
		outputMin = amt;
		outputMax = amt;
		return this;
	}

	public HeatIndex setMorph(ItemStack is)
	{
		morph = is;
		return this;
	}

	public ItemStack getMorph()
	{
		return morph;
	}

	public ItemStack getOutput(Random r)
	{
		if(getOutputItem() == null)
			return null;
		int rand = 0;
		if(outputMax - outputMin > 0) 
		{
			rand = outputMin + r.nextInt(outputMax - outputMin);
			return new ItemStack(getOutputItem(),output.stackSize, 100-rand);
		}
		else if(output.getItemDamage() > 0)
		{
			return new ItemStack(getOutputItem(),output.stackSize, output.getItemDamage());
		}
		else 
		{
			return new ItemStack(getOutputItem(),output.stackSize, outputMin);
		}
	}

	public ItemStack getOutput(ItemStack in, Random r)
	{
		ItemStack is = getOutput(r);
		if(is != null && this.keepNBT)
		{
			
			if(is.hasTagCompound())
			{
				NBTTagCompound nbt = is.getTagCompound();
				for(Object o : in.getTagCompound().func_150296_c())
				{
					NBTBase n = (NBTBase)o;
					if(nbt.hasKey(n.toString()))
						nbt.removeTag(n.toString());
					nbt.func_150296_c().add(o);
				}
			}
			else
			{
				is.setTagCompound(in.stackTagCompound);
				if(TFC_ItemHeat.hasTemp(is))
					TFC_ItemHeat.setTemp(is, TFC_ItemHeat.getTemp(is)*0.9f);
			}
			is.stackSize = getStackSizeFromWeight(is);
		}
		if(counter > 0)
		{
			is.setItemDamage(in.getItemDamage());
		}
		return is;
	}
	
	public int getStackSizeFromWeight(ItemStack i)
	{
		float w = Food.getWeight(i);
		int size = (int)(w * weightRatio);
		if(size > 0 || (w * weightRatio > 0))
		{
			if(i.hasTagCompound())
			{
				i.stackTagCompound = null;
			}
			return size;
		}
		return 1;
	}
	
	public HeatIndex setWeightRatio(float ratio)
	{
		this.weightRatio = ratio;
		return this;
	}

	public boolean matches(ItemStack is)
	{
		if(is != null)
		{
			boolean b = is.getItem().getHasSubtypes();
			if(is.getItem() != input.getItem())
				return false;
			else if(is.getItem() instanceof ItemPotteryMoldBase && ((ItemPotteryMoldBase)(is.getItem())).getCounter() > 0 && is.getItemDamage() >=damageOffset)
			{				
				//Rather than checking whether the damages are an exact match, we check whether it's a multiple.
				if(b && (is.getItemDamage()-((ItemPotteryMoldBase)(is.getItem())).getDamageOffset()) % ((ItemPotteryMoldBase)(is.getItem())).getCounter() != (input.getItemDamage()-((ItemPotteryMoldBase)(is.getItem())).getDamageOffset())%((ItemPotteryMoldBase)(is.getItem())).getCounter())
				{
					return false;
				}
			}
			else if (b &&input.getItemDamage() != 32767 &&
						is.getItemDamage() != input.getItemDamage())
				return false;
		}
		else return false;
		return true;
	}
}
