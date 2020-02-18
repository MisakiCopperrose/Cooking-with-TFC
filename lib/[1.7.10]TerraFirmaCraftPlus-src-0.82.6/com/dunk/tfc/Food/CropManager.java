package com.dunk.tfc.Food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Enums.EnumRegion;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class CropManager
{
	public List<CropIndex> crops;

	protected static final CropManager INSTANCE = new CropManager();
	
	protected CropIndex[] AMERICAS;
	protected CropIndex[] ASIA;
	protected CropIndex[] AFRICA;
	protected CropIndex[] EUROPE;
	
	public HashMap<EnumRegion,CropIndex[]> REGIONS;

	public static final CropManager getInstance()
	{
		return INSTANCE;
	}

	public CropManager()
	{
		crops = new ArrayList<CropIndex>();
	}

	public void addIndex(CropIndex index)
	{
		crops.add(index);
	}

	public int getTotalCrops()
	{
		return crops.size();
	}

	public CropIndex getCropFromName(String n)
	{
		for(CropIndex pi : crops)
			if (pi.cropName.equalsIgnoreCase(n))
				return pi;
		return null;
	} 

	public CropIndex getCropFromId(int n)
	{
		for(CropIndex pi : crops)
			if(pi.cropId == n)
				return pi;
		return null;
	} 

	static
	{
		INSTANCE.addIndex(new CropIndex(/*ID*/0, /*Name*/"wheat", /*type*/0, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsWheat, new EnumRegion[]{EnumRegion.AFRICA,EnumRegion.EUROPE,EnumRegion.ASIA},/*Minimum Rainfall*/110,/*Maximum Rainfall*/500,/*Minimum Natural BioTemp*/14,/*Maximum Natural Bio Temp*/36).setOutput1(TFCItems.wheatWhole, 14.0f*2));

		INSTANCE.addIndex(new CropIndex(/*ID*/1, /*Name*/"maize", /*type*/0, /*time*/36, /*stages*/5, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.8f, TFCItems.seedsMaize,new EnumRegion[]{EnumRegion.AMERICAS},/*Minimum Rainfall*/150,/*Maximum Rainfall*/1500,/*Minimum Natural BioTemp*/9,/*Maximum Natural Bio Temp*/34).setOutput1(TFCItems.maizeEar, 32.0f));//Converts to 4-6oz of corn when kernals removed

		INSTANCE.addIndex(new CropIndex(/*ID*/2, /*Name*/"tomato", /*type*/0, /*time*/23, /*stages*/7, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.seedsTomato,new EnumRegion[]{EnumRegion.AMERICAS},/*Minimum Rainfall*/400,/*Maximum Rainfall*/2000,/*Minimum Natural BioTemp*/12,/*Maximum Natural Bio Temp*/30).setWaterUsage(1.2f).setOutput1(TFCItems.tomato, 16));

		INSTANCE.addIndex(new CropIndex(/*ID*/3, /*Name*/"barley", /*type*/0, /*time*/33, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.85f, TFCItems.seedsBarley,new EnumRegion[]{EnumRegion.AFRICA,EnumRegion.EUROPE},/*Minimum Rainfall*/100,/*Maximum Rainfall*/550,/*Minimum Natural BioTemp*/14,/*Maximum Natural Bio Temp*/36).setOutput1(TFCItems.barleyWhole, 14.0f*2));

		INSTANCE.addIndex(new CropIndex(/*ID*/4, /*Name*/"rye", /*type*/0, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsRye,new EnumRegion[]{EnumRegion.ASIA,EnumRegion.AFRICA},/*Minimum Rainfall*/120,/*Maximum Rainfall*/490,/*Minimum Natural BioTemp*/16,/*Maximum Natural Bio Temp*/36).setOutput1(TFCItems.ryeWhole, 14.0f*2));

		INSTANCE.addIndex(new CropIndex(/*ID*/5, /*Name*/"oat", /*type*/0, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsOat,new EnumRegion[]{EnumRegion.EUROPE},/*Minimum Rainfall*/200,/*Maximum Rainfall*/900,/*Minimum Natural BioTemp*/11,/*Maximum Natural Bio Temp*/30).setWaterUsage(1.4f).setOutput1(TFCItems.oatWhole, 14.0f*2));

		INSTANCE.addIndex(new CropIndex(/*ID*/6, /*Name*/"rice", /*type*/1, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsRice,new EnumRegion[]{EnumRegion.ASIA,EnumRegion.AFRICA},/*Minimum Rainfall*/900,/*Maximum Rainfall*/5000,/*Minimum Natural BioTemp*/14,/*Maximum Natural Bio Temp*/36).setWaterUsage(1.1f).setOutput1(TFCItems.riceWhole, 14.0f*2));

		INSTANCE.addIndex(new CropIndex(/*ID*/7, /*Name*/"potato", /*type*/2, /*time*/32, /*stages*/6, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsPotato,new EnumRegion[]{EnumRegion.AMERICAS},/*Minimum Rainfall*/150,/*Maximum Rainfall*/2000,/*Minimum Natural BioTemp*/4,/*Maximum Natural Bio Temp*/25).setOutput1(TFCItems.potato, 55.0f));

		INSTANCE.addIndex(new CropIndex(/*ID*/8, /*Name*/"onion", /*type*/1, /*time*/16, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.seedsOnion,new EnumRegion[]{EnumRegion.ASIA,EnumRegion.EUROPE},/*Minimum Rainfall*/400,/*Maximum Rainfall*/2500,/*Minimum Natural BioTemp*/6,/*Maximum Natural Bio Temp*/28).setOutput1(TFCItems.onion, 36.0f).setGoesDormant(true));

		INSTANCE.addIndex(new CropIndex(/*ID*/9, /*Name*/"cabbage", /*type*/1, /*time*/29, /*stages*/5, /*minGTemp*/10, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsCabbage,new EnumRegion[]{EnumRegion.EUROPE},/*Minimum Rainfall*/250,/*Maximum Rainfall*/4000,/*Minimum Natural BioTemp*/8,/*Maximum Natural Bio Temp*/29).setWaterUsage(0.9f).setOutput1(TFCItems.cabbage, 32.0f).setGoesDormant(true));

		INSTANCE.addIndex(new CropIndex(/*ID*/10, /*Name*/"garlic", /*type*/2, /*time*/25, /*stages*/4, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.5f, TFCItems.seedsGarlic,new EnumRegion[]{EnumRegion.ASIA},/*Minimum Rainfall*/150,/*Maximum Rainfall*/3000,/*Minimum Natural BioTemp*/10,/*Maximum Natural Bio Temp*/31).setOutput1(TFCItems.garlic, 20.0f).setGoesDormant(true));

		INSTANCE.addIndex(new CropIndex(/*ID*/11, /*Name*/"carrot", /*type*/2, /*time*/23, /*stages*/4, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.75f, TFCItems.seedsCarrot,new EnumRegion[]{EnumRegion.ASIA,EnumRegion.EUROPE},/*Minimum Rainfall*/160,/*Maximum Rainfall*/3000,/*Minimum Natural BioTemp*/7,/*Maximum Natural Bio Temp*/36).setOutput1(TFCItems.carrot, 30.0f).setGoesDormant(true));

		INSTANCE.addIndex(new CropIndexPepper(/*ID*/12, /*Name*/"yellowbellpepper", /*type*/2, /*time*/18, /*stages*/6, /*minGTemp*/12, /*minATemp*/4, /*nutrientUsage*/1.2f, TFCItems.seedsYellowBellPepper,new EnumRegion[]{EnumRegion.AMERICAS},/*Minimum Rainfall*/600,/*Maximum Rainfall*/5000,/*Minimum Natural BioTemp*/13,/*Maximum Natural Bio Temp*/30).setOutput1(TFCItems.greenBellPepper, 6).setOutput2(TFCItems.yellowBellPepper, 6));
		INSTANCE.addIndex(new CropIndexPepper(/*ID*/13, /*Name*/"redbellpepper", /*type*/2, /*time*/18, /*stages*/6, /*minGTemp*/12, /*minATemp*/4, /*nutrientUsage*/1.2f, TFCItems.seedsRedBellPepper,new EnumRegion[]{EnumRegion.AMERICAS},/*Minimum Rainfall*/550,/*Maximum Rainfall*/4950,/*Minimum Natural BioTemp*/12,/*Maximum Natural Bio Temp*/29).setOutput1(TFCItems.greenBellPepper, 6).setOutput2(TFCItems.redBellPepper, 6));

		INSTANCE.addIndex(new CropIndex(/*ID*/14, /*Name*/"soybean", /*type*/1, /*time*/25, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.0f, TFCItems.seedsSoybean,new EnumRegion[]{EnumRegion.ASIA},/*Minimum Rainfall*/500,/*Maximum Rainfall*/6000,/*Minimum Natural BioTemp*/3,/*Maximum Natural Bio Temp*/30, new int[]{10,0,10}).setOutput1(TFCItems.soybean, 16));

		INSTANCE.addIndex(new CropIndex(/*ID*/15, /*Name*/"greenbean", /*type*/1, /*time*/24, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.0f, TFCItems.seedsGreenbean,new EnumRegion[]{EnumRegion.AMERICAS},/*Minimum Rainfall*/450,/*Maximum Rainfall*/4000,/*Minimum Natural BioTemp*/8,/*Maximum Natural Bio Temp*/30, new int[]{10,0,10}).setOutput1(TFCItems.greenbeans, 16));

		INSTANCE.addIndex(new CropIndex(/*ID*/16, /*Name*/"squash", /*type*/2, /*time*/33, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsSquash,new EnumRegion[]{EnumRegion.AMERICAS},/*Minimum Rainfall*/250,/*Maximum Rainfall*/6000,/*Minimum Natural BioTemp*/3,/*Maximum Natural Bio Temp*/30).setOutput1(TFCItems.squash, 16));

		INSTANCE.addIndex(new CropIndexJute(/*ID*/17, /*Name*/"jute", /*type*/1, /*time*/28, /*stages*/5, /*minGTemp*/10, /*minATemp*/5, /*nutrientUsage*/1.0f, TFCItems.seedsJute,new EnumRegion[]{EnumRegion.ASIA,EnumRegion.AFRICA,EnumRegion.EUROPE},/*Minimum Rainfall*/200,/*Maximum Rainfall*/6000,/*Minimum Natural BioTemp*/12,/*Maximum Natural Bio Temp*/36).setOutput1(TFCItems.jute, 2));

		INSTANCE.addIndex(new CropIndex(/*ID*/18, /*Name*/"sugarcane", /*type*/1, /*time*/96, /*stages*/7, /*minGTemp*/18, /*minATemp*/12, /*nutrientUsage*/0.25f, TFCItems.seedsSugarcane,new EnumRegion[]{EnumRegion.ASIA},/*Minimum Rainfall*/1000,/*Maximum Rainfall*/16000,/*Minimum Natural BioTemp*/21,/*Maximum Natural Bio Temp*/36).setOutput1(TFCItems.sugarcane, 8));

		INSTANCE.addIndex(new CropIndexJute(/*ID*/19, /*Name*/"flax", /*type*/1, /*time*/24, /*stages*/5, /*minGTemp*/10, /*minATemp*/5, /*nutrientUsage*/1.0f, TFCItems.seedsFlax,new EnumRegion[]{EnumRegion.AFRICA,EnumRegion.EUROPE,EnumRegion.ASIA},/*Minimum Rainfall*/150,/*Maximum Rainfall*/2500,/*Minimum Natural BioTemp*/10,/*Maximum Natural Bio Temp*/30).setOutput1(TFCItems.flax, 3));
		INSTANCE.addIndex(new CropIndexJute(/*ID*/20, /*Name*/"madder", /*type*/2, /*time*/35, /*stages*/4, /*minGTemp*/10, /*minATemp*/5, /*nutrientUsage*/0.4f, TFCItems.seedsMadder,new EnumRegion[]{EnumRegion.EUROPE},/*Minimum Rainfall*/500,/*Maximum Rainfall*/2500,/*Minimum Natural BioTemp*/3,/*Maximum Natural Bio Temp*/25).setOutput1(TFCItems.madderRoot, 2));
		INSTANCE.addIndex(new CropIndexJute(/*ID*/21, /*Name*/"weld", /*type*/3, /*time*/30, /*stages*/4, /*minGTemp*/10, /*minATemp*/5, /*nutrientUsage*/0.4f, TFCItems.seedsWeld,new EnumRegion[]{EnumRegion.AFRICA},/*Minimum Rainfall*/500,/*Maximum Rainfall*/2500,/*Minimum Natural BioTemp*/3,/*Maximum Natural Bio Temp*/30).setOutput1(TFCItems.weldRoot, 2));
		INSTANCE.addIndex(new CropIndexJute(/*ID*/22, /*Name*/"woad", /*type*/1, /*time*/34, /*stages*/5, /*minGTemp*/16, /*minATemp*/10, /*nutrientUsage*/0.2f, TFCItems.seedsWoad,new EnumRegion[]{EnumRegion.ASIA},/*Minimum Rainfall*/500,/*Maximum Rainfall*/6000,/*Minimum Natural BioTemp*/3,/*Maximum Natural Bio Temp*/30).setOutput1(TFCItems.woadLeaves, 3));
		
		INSTANCE.addIndex(new CropIndex(/*ID*/23, /*Name*/"pumpkin", /*type*/2, /*time*/40, /*stages*/4, /*minGTemp*/10, /*minATemp*/7, /*nutrientUsage*/0.2f, TFCItems.seedsPumpkin,new EnumRegion[]{EnumRegion.AMERICAS},/*Minimum Rainfall*/750,/*Maximum Rainfall*/6000,/*Minimum Natural BioTemp*/9,/*Maximum Natural Bio Temp*/25).setOutput1(Item.getItemFromBlock(TFCBlocks.pumpkin), 1));
		INSTANCE.addIndex(new CropIndex(/*ID*/24, /*Name*/"melon", /*type*/2, /*time*/40, /*stages*/4, /*minGTemp*/14, /*minATemp*/10, /*nutrientUsage*/0.3f, TFCItems.seedsMelon,new EnumRegion[]{EnumRegion.AFRICA},/*Minimum Rainfall*/750,/*Maximum Rainfall*/6000,/*Minimum Natural BioTemp*/16,/*Maximum Natural Bio Temp*/36).setOutput1(Item.getItemFromBlock(TFCBlocks.melon), 1));
		//Hopefully, this is roughly 2 years?
		INSTANCE.addIndex(new CropIndex(/*ID*/25, /*Name*/"grape", /*type*/3, /*time*/100, /*stages*/8, /*minGTemp*/15, /*minATemp*/-5, /*nutrientUsage*/0.05f, TFCItems.seedsGrapes,new EnumRegion[]{EnumRegion.AMERICAS,EnumRegion.EUROPE,EnumRegion.AFRICA},/*Minimum Rainfall*/250,/*Maximum Rainfall*/6000,/*Minimum Natural BioTemp*/12,/*Maximum Natural Bio Temp*/36).setOutput1(TFCItems.grapes, 40).setHarvestableWithRightClick(true,(int)(TFC_Time.daysInYear*0.9f)).setGoesDormant(true).setRequiresLadder(true).setMaxLifespan(10));
		INSTANCE.addIndex(new CropIndex(/*ID*/26, /*Name*/"blackeyedpeas", /*type*/1, /*time*/24, /*stages*/6, /*minGTemp*/10, /*minATemp*/3, /*nutrientUsage*/1.0f, TFCItems.seedsBlackEyedPeas,new EnumRegion[]{EnumRegion.AFRICA},/*Minimum Rainfall*/350,/*Maximum Rainfall*/4000,/*Minimum Natural BioTemp*/8,/*Maximum Natural Bio Temp*/35, new int[]{10,0,10}).setOutput1(TFCItems.blackEyedPeas, 16));
		INSTANCE.addIndex(new CropIndexJute(/*ID*/27, /*Name*/"agave", /*type*/1, /*time*/28, /*stages*/5, /*minGTemp*/15, /*minATemp*/10, /*nutrientUsage*/0.25f, TFCItems.seedsAgave,new EnumRegion[]{EnumRegion.AMERICAS},/*Minimum Rainfall*/50,/*Maximum Rainfall*/500,/*Minimum Natural BioTemp*/12,/*Maximum Natural Bio Temp*/50).setOutput1(TFCItems.agave, 4));
		INSTANCE.addIndex(new CropIndexJute(/*ID*/28, /*Name*/"cotton", /*type*/2, /*time*/26, /*stages*/5, /*minGTemp*/15, /*minATemp*/11, /*nutrientUsage*/0.5f, TFCItems.seedsCotton,new EnumRegion[]{EnumRegion.AMERICAS,EnumRegion.AFRICA,EnumRegion.ASIA},/*Minimum Rainfall*/1200,/*Maximum Rainfall*/5000,/*Minimum Natural BioTemp*/15,/*Maximum Natural Bio Temp*/35).setOutput1(TFCItems.cotton, 3));

		ArrayList<CropIndex> americas = new ArrayList<CropIndex>();
		ArrayList<CropIndex> asia = new ArrayList<CropIndex>();
		ArrayList<CropIndex> europe = new ArrayList<CropIndex>();
		ArrayList<CropIndex> africa = new ArrayList<CropIndex>();
		
		for(int i =0; i < INSTANCE.crops.size();i++)
		{
			for(EnumRegion r : INSTANCE.crops.get(i).regions)
			{
				switch(r)
				{
				case ASIA:
				{
					asia.add(INSTANCE.crops.get(i));
					break;
				}
				case AFRICA:
				{
					africa.add(INSTANCE.crops.get(i));
					break;
				}
				case EUROPE:
				{
					europe.add(INSTANCE.crops.get(i));
					break;
				}
				case AMERICAS:
				{
					americas.add(INSTANCE.crops.get(i));
					break;
				}
				}
			}
		}
		INSTANCE.AMERICAS = new CropIndex[americas.size()];
		INSTANCE.AMERICAS = americas.toArray(INSTANCE.AMERICAS);
		
		INSTANCE.EUROPE = new CropIndex[europe.size()];
		INSTANCE.EUROPE = europe.toArray(INSTANCE.EUROPE);
		
		INSTANCE.AFRICA = new CropIndex[africa.size()];
		INSTANCE.AFRICA = africa.toArray(INSTANCE.AFRICA);
		
		INSTANCE.ASIA = new CropIndex[asia.size()];
		INSTANCE.ASIA = asia.toArray(INSTANCE.ASIA);
		
		INSTANCE.REGIONS = new HashMap<EnumRegion,CropIndex[]>();
		
		INSTANCE.REGIONS.put(EnumRegion.AFRICA, INSTANCE.AFRICA);
		INSTANCE.REGIONS.put(EnumRegion.ASIA, INSTANCE.ASIA);
		INSTANCE.REGIONS.put(EnumRegion.AMERICAS, INSTANCE.AMERICAS);
		INSTANCE.REGIONS.put(EnumRegion.EUROPE, INSTANCE.EUROPE);
		
		// If adding another crop, use the following spreadsheet to make sure nutrients on average don't hit 0 before crop reaches maturity:
		// https://www.dropbox.com/s/sznzc08nly1i6tt/Crop%20GrowthNutriDrain%20Calculator.xlsx?dl=0
	}
}
