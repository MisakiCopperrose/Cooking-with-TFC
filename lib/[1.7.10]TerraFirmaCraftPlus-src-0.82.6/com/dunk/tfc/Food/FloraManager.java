package com.dunk.tfc.Food;

import java.util.ArrayList;
import java.util.List;

import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FloraManager
{
	private static final FloraManager INSTANCE = new FloraManager();
	private int[] AMERICAS, ASIA, AFRICA, EUROPE;
	public int[][] berryREGIONS;
	private List<FloraIndex> floraList;

	public static final FloraManager getInstance()
	{
		return INSTANCE;
	}

	public FloraManager()
	{
		floraList = new ArrayList<FloraIndex>();
	}

	public void addIndex(FloraIndex index)
	{
		floraList.add(index);
	}

	public FloraIndex findMatchingIndex(Item fruit)
	{
		for (int k = 0; k < floraList.size(); k++)
		{
			FloraIndex tempIndex = floraList.get(k);
			if (tempIndex.output.getItem() == fruit)
			{
				return tempIndex;
			}
		}

		return null;
	}

	public FloraIndex findMatchingIndex(String input)
	{
		for (int k = 0; k < floraList.size(); k++)
		{
			FloraIndex tempIndex = floraList.get(k);
			if (tempIndex.type.equalsIgnoreCase(input))
			{
				return tempIndex;
			}
		}

		return null;
	}

	static
	{
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[0], TFC_Time.APRIL, TFC_Time.MAY, TFC_Time.OCTOBER,
				TFC_Time.NOVEMBER, new ItemStack(TFCItems.fruitTreeSapling, 1, 0),
				new ItemStack(TFCItems.redApple, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[1], TFC_Time.APRIL, TFC_Time.MAY, TFC_Time.SEPTEMBER,
				TFC_Time.SEPTEMBER, new ItemStack(TFCItems.fruitTreeSapling, 1, 1), new ItemStack(TFCItems.banana, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[2], TFC_Time.FEBRUARY, TFC_Time.APRIL,
				TFC_Time.NOVEMBER, TFC_Time.NOVEMBER, new ItemStack(TFCItems.fruitTreeSapling, 1, 2),
				new ItemStack(TFCItems.orange, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[3], TFC_Time.MAY, TFC_Time.JUNE, TFC_Time.OCTOBER,
				TFC_Time.NOVEMBER, new ItemStack(TFCItems.fruitTreeSapling, 1, 3),
				new ItemStack(TFCItems.greenApple, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[4], TFC_Time.MAY, TFC_Time.JUNE, TFC_Time.AUGUST,
				TFC_Time.AUGUST, new ItemStack(TFCItems.fruitTreeSapling, 1, 4), new ItemStack(TFCItems.lemon, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[5], TFC_Time.JUNE, TFC_Time.JUNE, TFC_Time.OCTOBER,
				TFC_Time.OCTOBER, new ItemStack(TFCItems.fruitTreeSapling, 1, 5), new ItemStack(TFCItems.olive, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[6], TFC_Time.APRIL, TFC_Time.APRIL, TFC_Time.JUNE,
				TFC_Time.JUNE, new ItemStack(TFCItems.fruitTreeSapling, 1, 6), new ItemStack(TFCItems.cherry, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[7], TFC_Time.APRIL, TFC_Time.MAY, TFC_Time.SEPTEMBER,
				TFC_Time.SEPTEMBER, new ItemStack(TFCItems.fruitTreeSapling, 1, 7), new ItemStack(TFCItems.peach, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[8], TFC_Time.MAY, TFC_Time.JUNE, TFC_Time.JULY,
				TFC_Time.AUGUST, new ItemStack(TFCItems.fruitTreeSapling, 1, 8), new ItemStack(TFCItems.plum, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[9], TFC_Time.MAY, TFC_Time.JUNE, TFC_Time.SEPTEMBER,
				TFC_Time.SEPTEMBER, new ItemStack(TFCItems.fruitTreeSapling, 1, 9), new ItemStack(TFCItems.papaya, 1)));
		INSTANCE.addIndex(new FloraIndex(Global.FRUIT_META_NAMES[10], TFC_Time.APRIL, TFC_Time.JUNE, TFC_Time.AUGUST,
				TFC_Time.SEPTEMBER, new ItemStack(TFCItems.fruitTreeSapling, 1, 10), new ItemStack(TFCItems.date, 1)));

		// Berry Bushes
		INSTANCE.addIndex(new FloraIndex("Wintergreen", TFC_Time.SEPTEMBER, TFC_Time.OCTOBER,
				new ItemStack(TFCItems.wintergreenBerry, 1)).setHangTime(5).setTemp(-18, 28).setBioTemp(0, 20)
						.setRain(500, 4000).setEVT(0f, 1));
		INSTANCE.addIndex(
				new FloraIndex("Blueberry", TFC_Time.JULY, TFC_Time.SEPTEMBER, new ItemStack(TFCItems.blueberry, 1))
						.setHangTime(2).setTemp(0, 32).setBioTemp(5, 25).setRain(125, 1000));
		INSTANCE.addIndex(
				new FloraIndex("Raspberry", TFC_Time.JULY, TFC_Time.AUGUST, new ItemStack(TFCItems.raspberry, 1))
						.setHangTime(2).setTemp(0, 30).setBioTemp(5, 25).setRain(250, 2000));
		INSTANCE.addIndex(
				new FloraIndex("Strawberry", TFC_Time.MAY, TFC_Time.JUNE, new ItemStack(TFCItems.strawberry, 1))
						.setHangTime(2).setTemp(0, 27).setBioTemp(5, 25).setRain(500, 2000));
		INSTANCE.addIndex(
				new FloraIndex("Blackberry", TFC_Time.JUNE, TFC_Time.SEPTEMBER, new ItemStack(TFCItems.blackberry, 1))
						.setHangTime(2).setTemp(0, 30).setBioTemp(5, 25).setRain(125, 4000).setEVT(0.25f, 4));
		INSTANCE.addIndex(
				new FloraIndex("Bunchberry", TFC_Time.JULY, TFC_Time.SEPTEMBER, new ItemStack(TFCItems.bunchberry, 1))
						.setHangTime(2).setTemp(0, 18).setBioTemp(0, 20).setRain(125, 2000));
		INSTANCE.addIndex(
				new FloraIndex("Cranberry", TFC_Time.SEPTEMBER, TFC_Time.NOVEMBER, new ItemStack(TFCItems.cranberry, 1))
						.setHangTime(3).setTemp(2, 18).setBioTemp(0, 30).setRain(1000, 8000));
		INSTANCE.addIndex(
				new FloraIndex("Snowberry", TFC_Time.AUGUST, TFC_Time.SEPTEMBER, new ItemStack(TFCItems.snowberry, 1))
						.setHangTime(3).setTemp(0, 18).setBioTemp(0, 20).setRain(250, 4000).setEVT(0.125f, 4));
		INSTANCE.addIndex(
				new FloraIndex("Elderberry", TFC_Time.AUGUST, TFC_Time.SEPTEMBER, new ItemStack(TFCItems.elderberry, 1))
						.setHangTime(2).setTemp(0, 28).setBioTemp(5, 25).setRain(250, 2000));
		INSTANCE.addIndex(
				new FloraIndex("Gooseberry", TFC_Time.MAY, TFC_Time.JULY, new ItemStack(TFCItems.gooseberry, 1))
						.setHangTime(2).setTemp(0, 28).setBioTemp(5, 25).setRain(250, 2000));
		INSTANCE.addIndex(
				new FloraIndex("Cloudberry", TFC_Time.JULY, TFC_Time.AUGUST, new ItemStack(TFCItems.cloudberry, 1))
						.setHangTime(2).setTemp(0, 18).setBioTemp(0, 25).setRain(1000, 8000).setEVT(0.125f, 4));

		INSTANCE.AMERICAS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		INSTANCE.ASIA = new int[] { 2, 4, 5, 7, 8, 10 };
		INSTANCE.AFRICA = new int[] { 2, 4, 9 };
		INSTANCE.EUROPE = new int[] { 2, 4, 5, 6, 8, 9, 10 };
		INSTANCE.berryREGIONS = new int[][]{INSTANCE.AMERICAS,INSTANCE.EUROPE,INSTANCE.AFRICA,INSTANCE.ASIA};
	}

}
