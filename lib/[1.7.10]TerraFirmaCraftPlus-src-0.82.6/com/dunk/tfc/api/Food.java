package com.dunk.tfc.api;

import java.util.ArrayList;
import java.util.Random;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.api.Enums.EnumFoodGroup;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.Util.Helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class Food
{
	public static final String DECAY_TAG = "foodDecay";
	public static final String DECAY_TIMER_TAG = "decayTimer";
	public static final String DECAY_RATE_TAG = "decayRate";
	public static final String WEIGHT_TAG = "foodWeight";
	public static final String PROCESSING_TAG = "Processing Tag";
	public static final String BRINED_TAG = "Brined";
	public static final String PICKLED_TAG = "Pickled";
	public static final String SALTED_TAG = "Salted";
	public static final String COOKED_TAG = "Cooked";
	public static final String COOKED_PROFILE_TAG = "CookedProfile";
	public static final String FUEL_PROFILE_TAG = "FuelProfile";
	public static final String DRIED_TAG = "Dried";
	public static final String SMOKE_COUNTER_TAG = "SmokeCounter";
	public static final String SWEET_MOD_TAG = "tasteSweetMod";
	public static final String SOUR_MOD_TAG = "tasteSourMod";
	public static final String SALTY_MOD_TAG = "tasteSaltyMod";
	public static final String BITTER_MOD_TAG = "tasteBitterMod";
	public static final String UMAMI_MOD_TAG = "tasteUmamiMod";
	public static final String MEAL_SKILL_TAG = "mealSkill";
	public static final String INFUSION_TAG = "Infusion";
	public static final String FOOD_GROUP_TAG = "FG";
	public static final String FOOD_LIST_TAG = "List";
	public static final String FOOD_LIST_SIZE_TAG = "ListSize";
	public static final String FOOD_LIST_DECAY_TAG = "ListDecay";
	protected static final String WATER_LOSS_TAG = "WaterLoss";
	public static final int DRYHOURS = 4;
	public static final int SMOKEHOURS = 12;

	private static NBTTagCompound getProcTag(ItemStack is)
	{
		if (is.hasTagCompound() && is.getTagCompound().hasKey(PROCESSING_TAG))
		{
			return (NBTTagCompound) is.getTagCompound().getTag(PROCESSING_TAG);
		}
		else
			return new NBTTagCompound();
	}

	private static void setProcTag(ItemStack is, NBTTagCompound nbt)
	{
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setTag(PROCESSING_TAG, nbt);
	}

	private static NBTTagCompound getNBT(ItemStack is)
	{
		if (is.hasTagCompound())
		{
			return is.getTagCompound();
		}
		else
		{
			if(is != null && is.getItem() == TFCItems.honeybowl)
			{
				return new NBTTagCompound();
			}
			TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " "
					+ TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
			return new NBTTagCompound();
		}
	}

	public static boolean areEqual(ItemStack is1, ItemStack is2)
	{
		return isBrined(is1) == isBrined(is2) && isPickled(is1) == isPickled(is2) && isCooked(is1) == isCooked(is2)
				&& isDried(is1) == isDried(is2) && isSalted(is1) == isSalted(is2)
				&& (isInfused(is1) && isInfused(is2) && getInfusion(is1).equals(getInfusion(is2))
						|| !isInfused(is1) && !isInfused(is2))
				&& (isSmoked(is1) && isSmoked(is2) && isSameSmoked(is1, is2) || !isSmoked(is1) && !isSmoked(is2));
	}

	public static boolean isBrined(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.hasKey(BRINED_TAG) && nbt.getBoolean(BRINED_TAG);
	}

	public static void setBrined(ItemStack is, boolean value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setBoolean(BRINED_TAG, value);
		setProcTag(is, nbt);
	}
	
	public static boolean hasNutritionWhenRaw(ItemStack is)
	{
		if(is != null && is.getItem() instanceof ItemFoodTFC)
		{
			return ((ItemFoodTFC)is.getItem()).nutritionAsIfCooked;
		}
		return false;
	}

	public static boolean isPickled(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.hasKey(PICKLED_TAG) && nbt.getBoolean(PICKLED_TAG);
	}

	public static void setPickled(ItemStack is, boolean value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setBoolean(PICKLED_TAG, value);
		setProcTag(is, nbt);
	}

	public static boolean isSalted(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.hasKey(SALTED_TAG) && nbt.getBoolean(SALTED_TAG);
	}

	public static void setSalted(ItemStack is, boolean value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setBoolean(SALTED_TAG, value);
		setProcTag(is, nbt);
	}

	public static boolean isCooked(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.hasKey(COOKED_TAG) && nbt.getFloat(COOKED_TAG) > 600;
	}

	public static float getCooked(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		if (nbt.hasKey(COOKED_TAG))
			return nbt.getFloat(COOKED_TAG);
		else
			return 0.0F;
	}

	// We cook small foods faster
	public static void setCookedByWeight(ItemStack is, float value)
	{
		value -= getCooked(is);
		value *= (12.65f / MathHelper.sqrt_float(getWeight(is)));
		value += getCooked(is);
		setCooked(is, value);

	}

	public static void setCooked(ItemStack is, float value)
	{
		// The value passed in is the increase due to fire temperature. We want
		// to make smaller food cook faster.
		// value -= getCooked(is);
		// value *= 160f / getWeight(is);
		// value += getCooked(is);
		
		//If we have a high water content, we want to reduce our weight
		
		NBTTagCompound nbt = getProcTag(is);
		byte currentCooked = 0;
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(WATER_LOSS_TAG))
		{
			currentCooked = is.stackTagCompound.getByte(WATER_LOSS_TAG);
		}
		int cookDif = ((int)value/(int)100)-5 - currentCooked;
		if(is.hasTagCompound() && cookDif > 0)
		{
			for(int i = 0; i < cookDif;i++)
			{
				is.stackTagCompound = reduceWater(is,is.stackTagCompound);
			}
		}
		nbt.setFloat(COOKED_TAG, value);
		setProcTag(is, nbt);
	}
	
	//When we cook a food, we might reduce the weight due to water loss
	public static NBTTagCompound reduceWater(ItemStack is, NBTTagCompound nbt)
	{
		float waterPercentage = 0;
		if(is != null && is.getItem() instanceof ItemFoodTFC)
		{
			ItemFoodTFC theFood = (ItemFoodTFC)is.getItem();
			waterPercentage = theFood.waterPercentage;
			nbt.setFloat(WEIGHT_TAG, nbt.getFloat(WEIGHT_TAG) * (float)(1f - 0.1f * Math.pow(waterPercentage, 4)));
			if(nbt.hasKey(WATER_LOSS_TAG))
			{
				nbt.setByte(WATER_LOSS_TAG,(byte) (is.stackTagCompound.getByte(WATER_LOSS_TAG)+1));
			}
			else
			{
				nbt.setByte(WATER_LOSS_TAG, (byte)1);
			}
		}
		return nbt;
	}

	public static int[] getCookedProfile(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		if (nbt.hasKey(COOKED_PROFILE_TAG))
			return nbt.getIntArray(COOKED_PROFILE_TAG);
		else
			return new int[] { 0, 0, 0, 0, 0 };
	}

	public static void setCookedProfile(ItemStack is, int[] value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setIntArray(COOKED_PROFILE_TAG, value);
		setProcTag(is, nbt);
	}

	public static int[] getFuelProfile(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		if (nbt.hasKey(FUEL_PROFILE_TAG))
			return nbt.getIntArray(FUEL_PROFILE_TAG);
		else
			return new int[] { 0, 0, 0, 0, 0 };
	}

	public static void setFuelProfile(ItemStack is, int[] value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setIntArray(FUEL_PROFILE_TAG, value);
		setProcTag(is, nbt);
	}

	public static boolean isSmoked(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.hasKey(FUEL_PROFILE_TAG) && !isSameSmoked(getFuelProfile(is), new int[] { 0, 0, 0, 0, 0 });
	}

	public static boolean isSameSmoked(ItemStack is1, ItemStack is2)
	{
		int[] f1 = getFuelProfile(is1);
		int[] f2 = getFuelProfile(is2);
		return f1[0] == f2[0] && f1[1] == f2[1] && f1[2] == f2[2] && f1[3] == f2[3] && f1[4] == f2[4];
	}

	public static boolean isSameSmoked(int[] f1, int[] f2)
	{
		return f1[0] == f2[0] && f1[1] == f2[1] && f1[2] == f2[2] && f1[3] == f2[3] && f1[4] == f2[4];
	}

	public static void setDecay(ItemStack is, float value)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setFloat(DECAY_TAG, Helper.roundNumber(value, 10000));
		if (value > getWeight(is))
			is.stackSize = 0;
	}

	public static float getDecay(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(DECAY_TAG))
			return nbt.getFloat(DECAY_TAG);
		else
			return 0.0F;
	}

	public static float getWater(ItemStack is)
	{
		if (is.getItem() instanceof ItemFoodTFC && ((ItemFoodTFC) (is.getItem())).waterRestoreAmount > 0)
		{
			return ((ItemFoodTFC) (is.getItem())).waterRestoreAmount;
		}
		return 0f;
	}
	
	public static byte getWaterLoss(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(WATER_LOSS_TAG))
			return nbt.getByte(WATER_LOSS_TAG);
		else
			return (byte)0;
	}

	public static void setDecayTimer(ItemStack is, int value)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setInteger(DECAY_TIMER_TAG, value);
	}

	public static int getDecayTimer(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(DECAY_TIMER_TAG))
			return nbt.getInteger(DECAY_TIMER_TAG);
		else
			return (int) TFC_Time.getTotalHours();
	}

	public static void setWeight(ItemStack is, float value)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setFloat(WEIGHT_TAG, Helper.roundNumber(value, 100));
		if (getDecay(is) > value || value <= 0)
			is.stackSize = 0;
	}
	
	public static void setWaterLoss(ItemStack is, byte waterLoss)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setByte(WATER_LOSS_TAG, waterLoss);
	}

	public static float getWeight(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(WEIGHT_TAG))
			return nbt.getFloat(WEIGHT_TAG);
		else
			return 0.0F;
	}

	public static boolean isDried(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		return nbt.hasKey(DRIED_TAG) && nbt.getShort(DRIED_TAG) >= DRYHOURS;
	}

	public static short getDried(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		if (nbt.hasKey(DRIED_TAG))
			return nbt.getShort(DRIED_TAG);
		else
			return 0;
	}

	public static void setDried(ItemStack is, int value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setShort(DRIED_TAG, (short) value);
		setProcTag(is, nbt);
	}

	public static short getSmokeCounter(ItemStack is)
	{
		NBTTagCompound nbt = getProcTag(is);
		if (nbt.hasKey(SMOKE_COUNTER_TAG))
			return nbt.getShort(SMOKE_COUNTER_TAG);
		else
			return 0;
	}

	public static void setSmokeCounter(ItemStack is, int value)
	{
		NBTTagCompound nbt = getProcTag(is);
		nbt.setShort(SMOKE_COUNTER_TAG, (short) value);
		setProcTag(is, nbt);
	}

	public static int getCookedColorMultiplier(ItemStack is)
	{
		float cookedLevel = Food.getCooked(is);
		int r = 255 - (int) (160 * (Math.max(cookedLevel - 600, 0) / 600f));
		int b = 255 - (int) (160 * (Math.max(cookedLevel - 600, 0) / 600f));
		int g = 255 - (int) (160 * (Math.max(cookedLevel - 600, 0) / 600f));
		return (r << 16) + (b << 8) + g;
	}

	public static void setSweetMod(ItemStack is, int val)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setInteger(SWEET_MOD_TAG, val);
	}

	public static int getSweetMod(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(SWEET_MOD_TAG))
			return nbt.getInteger(SWEET_MOD_TAG);
		else
			return 0;
	}

	public static void setSourMod(ItemStack is, int val)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setInteger(SOUR_MOD_TAG, val);
	}

	public static int getSourMod(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(SOUR_MOD_TAG))
			return nbt.getInteger(SOUR_MOD_TAG);
		else
			return 0;
	}

	public static void setSaltyMod(ItemStack is, int val)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setInteger(SALTY_MOD_TAG, val);
	}

	public static int getSaltyMod(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(SALTY_MOD_TAG))
			return nbt.getInteger(SALTY_MOD_TAG);
		else
			return 0;
	}

	public static void setBitterMod(ItemStack is, int val)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setInteger(BITTER_MOD_TAG, val);
	}

	public static int getBitterMod(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(BITTER_MOD_TAG))
			return nbt.getInteger(BITTER_MOD_TAG);
		else
			return 0;
	}

	public static void setSavoryMod(ItemStack is, int val)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setInteger(UMAMI_MOD_TAG, val);
	}

	public static int getSavoryMod(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(UMAMI_MOD_TAG))
			return nbt.getInteger(UMAMI_MOD_TAG);
		else
			return 0;
	}

	public static void adjustFlavor(ItemStack is, Random r)
	{
		Food.setSweetMod(is, r.nextInt(17) - 8);
		Food.setSourMod(is, r.nextInt(17) - 8);
		Food.setSaltyMod(is, r.nextInt(17) - 8);
		Food.setBitterMod(is, r.nextInt(17) - 8);
		Food.setSavoryMod(is, r.nextInt(17) - 8);
	}

	public static void setMealSkill(ItemStack is, int val)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setInteger(MEAL_SKILL_TAG, val);
	}

	public static int getMealSkill(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(MEAL_SKILL_TAG))
			return nbt.getInteger(MEAL_SKILL_TAG);
		else
			return 0;
	}

	public static boolean hasMealSkill(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		return nbt.hasKey(MEAL_SKILL_TAG);
	}

	public static int[] getFoodTasteProfile(ItemStack is)
	{
		int[] profile = new int[5];
		if (is != null && is.getItem() instanceof IFood)
		{
			profile[0] = ((IFood) is.getItem()).getTasteSweet(is);
			profile[1] = ((IFood) is.getItem()).getTasteSour(is);
			profile[2] = ((IFood) is.getItem()).getTasteSalty(is);
			profile[3] = ((IFood) is.getItem()).getTasteBitter(is);
			profile[4] = ((IFood) is.getItem()).getTasteSavory(is);
		}
		return profile;
	}

	public static boolean isInfused(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		return nbt.hasKey(INFUSION_TAG);
	}

	public static String getInfusion(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(INFUSION_TAG))
			return nbt.getString(INFUSION_TAG);
		else
			return null;
	}

	public static void setInfusion(ItemStack is, String val)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setString(INFUSION_TAG, val);
	}

	public static void setFoodGroups(ItemStack is, int[] val)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setIntArray(FOOD_GROUP_TAG, val);
	}

	public static int[] getFoodGroups(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(FOOD_GROUP_TAG))
		{
			return nbt.getIntArray(FOOD_GROUP_TAG);
		}
		else if(is != null && is.getItem() instanceof ItemFoodTFC)
		{
			return new int[] {((ItemFoodTFC)is.getItem()).foodID,-1,-1,-1,-1};
		}
		else
		{
			return new int[] { -1, -1, -1, -1 };
		}
	}

	public static void setDecayRate(ItemStack is, float val)
	{
		NBTTagCompound nbt = getNBT(is);
		nbt.setFloat(DECAY_RATE_TAG, val);
	}

	public static float getDecayRate(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(DECAY_RATE_TAG))
			return nbt.getFloat(DECAY_RATE_TAG);
		else
			return 1.0F;
	}

	public static ArrayList<float[]> getFoodsInStack(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(FOOD_LIST_SIZE_TAG + 0))
		{
			ArrayList<float[]> foodList = new ArrayList<float[]>();
			int count = 0;
			while (nbt.hasKey(FOOD_LIST_SIZE_TAG + count))
			{
				foodList.add(new float[] { nbt.getFloat(FOOD_LIST_SIZE_TAG + count),
						nbt.getFloat(FOOD_LIST_DECAY_TAG + count) });
				count++;
			}
			return foodList;
		}
		return null;
	}

	public static ArrayList<float[]> getFoodsInStackVerifySize(ItemStack is)
	{
		NBTTagCompound nbt = getNBT(is);
		if (nbt.hasKey(FOOD_LIST_SIZE_TAG + 0))
		{
			ArrayList<float[]> foodList = new ArrayList<float[]>();
			int count = 0;
			while (nbt.hasKey(FOOD_LIST_SIZE_TAG + count))
			{
				foodList.add(new float[] { nbt.getFloat(FOOD_LIST_SIZE_TAG + count),
						nbt.getFloat(FOOD_LIST_DECAY_TAG + count) });
				if (count >= is.stackSize)
				{
					foodList.remove(0);
				}
				count++;
			}
			return foodList;
		}
		return null;
	}

	// The foods are a byte array of
	public static void setFoodsInStack(ItemStack is, ArrayList<float[]> foodList)
	{
		NBTTagCompound nbt = getNBT(is);
		int count = 0;
		float size = 0;
		float decay = 0;
		while (nbt.hasKey(FOOD_LIST_SIZE_TAG + count))
		{
			nbt.removeTag(FOOD_LIST_SIZE_TAG + count);
			nbt.removeTag(FOOD_LIST_DECAY_TAG + count);
			count++;
		}
		count = 0;
		if (foodList != null)
		{
			for (float[] food : foodList)
			{
				nbt.setFloat(FOOD_LIST_SIZE_TAG + count, food[0]);
				size += food[0];
				decay += food[1];
				nbt.setFloat(FOOD_LIST_DECAY_TAG + count, food[1]);
				count++;
			}
		}
		Food.setWeight(is, size);
		//Food.setDecay(is, decay / count);
		Food.setDecay(is, decay / 1);
	}
}
