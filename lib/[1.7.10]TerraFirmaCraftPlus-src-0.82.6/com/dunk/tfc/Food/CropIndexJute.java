package com.dunk.tfc.Food;

import com.dunk.tfc.TileEntities.TECrop;
import com.dunk.tfc.api.Enums.EnumRegion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropIndexJute extends CropIndex
{

	public CropIndexJute(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed, EnumRegion[] reg,int minR, int maxR,int minT,int maxT)
	{
		super(id,name,type,growth,stages,minGTemp,minATemp, seed,reg, minR, maxR, minT, maxT);
	}
	public CropIndexJute(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed, EnumRegion[] reg,int minR, int maxR,int minT,int maxT)
	{
		super(id,name,type,growth,stages,minGTemp,minATemp, seed,reg, minR, maxR, minT, maxT);
		nutrientUsageMult = nutrientUsageMultiplier;
	}
	public CropIndexJute(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed, EnumRegion[] reg,int minR, int maxR,int minT,int maxT, int[] nutriRestore)
	{
		super(id,name,type,growth,stages,minGTemp,minATemp, seed,reg, minR, maxR, minT, maxT);
		nutrientExtraRestore = nutriRestore.clone();
		nutrientUsageMult = nutrientUsageMultiplier;
	}

	@Override
	public ItemStack getOutput1(TECrop crop)
	{
		if (output1 != null && crop.growth >= this.numGrowthStages)
		{
			return new ItemStack(output1, (int) this.output1Avg);
		}
		return null;
	}
	@Override
	public ItemStack getOutput2(TECrop crop)
	{
		return null;
	}
}
