package com.dunk.tfc.Food;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.TileEntities.TECrop;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Enums.EnumRegion;
import com.dunk.tfc.api.Util.Helper;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropIndex
{
	public int growthTime;
	public String cropName;
	public int cycleType;
	public int cropId;
	public int numGrowthStages;
	public float minGrowthTemp;
	public float minAliveTemp;
	public float nutrientUsageMult;
	public int[] nutrientExtraRestore;
	public boolean dormantInFrost;
	public int maxLifespan = -1;
	
	public int minimumNaturalRain;
	public int maximumNaturalRain;
	
	public int minimumNaturalBioTemp;
	public int maximumNaturalBioTemp;
	
	public EnumRegion[] regions;
	
	public boolean requiresLadder;

	public int chanceForOutput1 = 100;
	public Item output1;
	public float output1Avg;

	public int chanceForOutput2 = 100;
	public Item output2;
	public float output2Avg;
	
	//Whether you can harvest this block by right-clicking
	public boolean activateHarvestable;
	public int regrowMultiplier = 0;

	public boolean needsSunlight = true;
	public float waterUsageMult = 1;
	public Item seedItem;

	public CropIndex(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed, EnumRegion[] reg,int minR,int maxR,int minT,int maxT)
	{
		growthTime = growth;
		cycleType = type;
		cropName = name.toLowerCase();
		cropId = id;
		numGrowthStages = stages;
		minGrowthTemp = minGTemp;
		minAliveTemp = minATemp;
		nutrientExtraRestore = new int[]{0,0,0};
		nutrientUsageMult = 1.0f;
		dormantInFrost = false;
		seedItem = seed;
		regions = reg;
		this.minimumNaturalRain = minR;
		this.maximumNaturalRain = maxR;
		this.minimumNaturalBioTemp = minT;
		this.maximumNaturalBioTemp = maxT;
	}
	public CropIndex(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed, EnumRegion[] reg,int minR,int maxR,int minT,int maxT)
	{
		this(id,name,type,growth,stages,minGTemp,minATemp,seed,reg, minR, maxR, minT, maxT);
		nutrientUsageMult = nutrientUsageMultiplier;
	}
	public CropIndex(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed, EnumRegion[] reg,int minR,int maxR,int minT,int maxT, int[] nutriRestore)
	{
		this(id,name,type,growth,stages,minGTemp,minATemp,seed,reg, minR, maxR, minT, maxT);
		nutrientExtraRestore = nutriRestore.clone();
		nutrientUsageMult = nutrientUsageMultiplier;
	}
	
	public CropIndex setRequiresLadder(boolean t)
	{
		this.requiresLadder = t;
		return this;
	}

	public CropIndex setOutput1(Item o, float oAvg)
	{
		output1 = o;
		output1Avg = oAvg;
		return this;
	}
	public CropIndex setOutput2(Item o, float oAvg)
	{
		output2 = o;
		output2Avg = oAvg;
		return this;
	}
	public CropIndex setOutput1Chance(Item o, float oAvg, int chance)
	{
		output1 = o;
		output1Avg = oAvg;
		chanceForOutput1 = chance;
		return this;
	}
	
	public CropIndex setHarvestableWithRightClick(boolean b)
	{
		this.activateHarvestable = b;
		if(b)
		{
			this.regrowMultiplier = this.numGrowthStages/4;
		}
		return this;
	}
	
	public CropIndex setHarvestableWithRightClick(boolean b, int numDaysToRegrow)
	{
		this.activateHarvestable = b;
		if(b)
		{
			this.regrowMultiplier = numDaysToRegrow;
		}
		return this;
	}
	
	public CropIndex setOutput2Chance(Item o, float oAvg, int chance)
	{
		output2 = o;
		output2Avg = oAvg;
		chanceForOutput2 = chance;
		return this;
	}  
	public ItemStack getOutput1(TECrop crop)
	{
		if (output1 != null && crop.growth >= numGrowthStages)
		{
			ItemStack is = new ItemStack(output1);
			Random r = new Random();
			if(r.nextInt(100) < chanceForOutput1)
			{
				ItemFoodTFC.createTag(is, getWeight(output1Avg, r));
				addFlavorProfile(crop, is);
				return is;
			}
		}
		return null;
	}
	public ItemStack getOutput2(TECrop crop)
	{
		if (output2 != null && crop.growth >= numGrowthStages)
		{
			ItemStack is = new ItemStack(output2);
			Random r = new Random();
			if(r.nextInt(100) < chanceForOutput2)
			{
				ItemFoodTFC.createTag(is, getWeight(output2Avg, r));
				addFlavorProfile(crop, is);
				return is;
			}
		}
		return null;
	}

	private Random getGrowthRand(TECrop te)
	{
		Block farmBlock = te.getWorldObj().getBlock(te.xCoord, te.yCoord-1, te.zCoord);
		//Block underFarmBlock = te.getWorldObj().getBlock(te.xCoord, te.yCoord-2, te.zCoord);
		if(!TFC_Core.isSoil(farmBlock))
		{
			int soilType1 = (farmBlock == TFCBlocks.tilledSoil ? te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord-1, te.zCoord) : 
				te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord-1, te.zCoord)+16);
			int soilType2 = (farmBlock == TFCBlocks.dirt ? te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord-2, te.zCoord)*2 : 
				farmBlock == TFCBlocks.dirt2 ? (te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord-2, te.zCoord)+16)*2 : 0);

			int ph = TFC_Climate.getCacheManager(te.getWorldObj()).getPHLayerAt(te.xCoord, te.zCoord).data1*100;
			int drainage = 0;

			for(int y = 2; y < 8; y++)
			{
				if(TFC_Core.isGravel(te.getWorldObj().getBlock(te.xCoord, te.yCoord-y, te.zCoord)))
				{
					drainage++;
				}
			}
			drainage *= 1000;

			return new Random(soilType1+soilType2+ph+drainage);
		}
		return null;
	}
	
	public CropIndex setMaxLifespan(int m)
	{
		this.maxLifespan = m;
		return this;
	}

	private void addFlavorProfile(TECrop te, ItemStack outFood)
	{
		Random r = getGrowthRand(te);
		if(r != null)
		{
			Food.adjustFlavor(outFood, r);
		}
	}

	public static float getWeight(float average, Random r)
	{
		float weight = average + (average * (10 * r.nextFloat() - 5) / 100);
		return Helper.roundNumber(weight, 10);
	}

	public CropIndex setNeedsSunlight(boolean b)
	{
		needsSunlight = b;
		return this;
	}

	public CropIndex setWaterUsage(float m)
	{
		waterUsageMult = m;
		return this;
	}

	public CropIndex setGoesDormant(boolean b)
	{
		dormantInFrost = b;
		return this;
	}

	public ItemStack getSeed()
	{
		return new ItemStack(seedItem, 1);
	}

	public int getCycleType()
	{
		return cycleType;
	}
	public void onCropGrow(float stage){}
}
