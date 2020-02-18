package com.dunk.tfc.Core;

import java.util.HashMap;
import java.util.Map;

import com.dunk.tfc.Chunkdata.ChunkData;
import com.dunk.tfc.WorldGen.DataLayer;
import com.dunk.tfc.WorldGen.WorldCacheManager;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Util.Helper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TFC_Climate
{
	public static Map<World, WorldCacheManager> worldPair = new HashMap<World, WorldCacheManager>();
	private static final float[] Y_FACTOR_CACHE = new float[441];
	private static final float[] Z_FACTOR_CACHE = new float[30001];
	// private static final float[][] MONTH_TEMP_CACHE = new float[12][30001];
	// The average daily low in the winter
	private static final float[] WINTER_LOW_TEMP_CACHE = new float[30001];
	private static final float[] WINTER_LOW_TEMP_PRIME_CACHE = new float[30001];
	// The average difference between the average low and average high
	private static final float[] BASE_TEMP_DELTA_CACHE = new float[30001];
	// The average daily low in the summer
	private static final float[] SUMMER_LOW_TEMP_CACHE = new float[30001];
	private static final float[] SUMMER_LOW_TEMP_PRIME_CACHE = new float[30001];
	// How much above and below the maximums the temperature can be
	private static final float[] SUMMER_HIGH_VARIATION_CACHE = new float[30001];
	private static final float[] SUMMER_HIGH_VARIATION_PRIME_CACHE = new float[30001];
	private static final float[] SUMMER_LOW_VARIATION_CACHE = new float[30001];
	private static final float[] WINTER_HIGH_VARIATION_CACHE = new float[30001];
	private static final float[] WINTER_HIGH_VARIATION_PRIME_CACHE = new float[30001];
	private static final float[] WINTER_LOW_VARIATION_CACHE = new float[30001];
	
	private static final float[] MAXIMUM_TEMPERATURE = new float[30001];

	// private static int[][] insolationMap;

	/**
	 * All Temperature related code
	 */

	public static void initCache()
	{
		// internationally accepted average lapse time is 6.49 K / 1000 m, for
		// the first 11 km of the atmosphere. I suggest graphing our temperature
		// across the 110 m against 2750 m, so that gives us a change of 1.6225
		// / 10 blocks, which isn't /terrible/
		// Now going to attemp exonential growth. calculations but change in
		// temperature at 17.8475 for our system, so that should be the drop at
		// 255.
		// therefore, change should be temp - f(x), where f(x) is an exp
		// function roughly equal to f(x) = (x^2)/ 677.966.
		// This seems to work nicely. I like this. Since creative allows players
		// to travel above 255, I'll see if I can't code in the rest of it.
		// The upper troposhere has no lapse rate, so we'll just use that.
		// The equation looks rather complicated, but you can see it here:
		// http://www.wolframalpha.com/input/?i=%28%28%28x%5E2+%2F+677.966%29+*+%280.5%29*%28%28%28110+-+x%29+%2B+%7C110+-+x%7C%29%2F%28110+-
		// +x%29%29%29+%2B+%28%280.5%29*%28%28%28x+-+110%29+%2B+%7Cx+-+110%7C%29%2F%28x+-+110%29%29+*+x+*+0.16225%29%29+0+to+440

		for (int y = 0; y < Y_FACTOR_CACHE.length; y += 1)
		{
			// temp = temp - (ySq / 677.966f) * (((110.01f - y) +
			// Math.abs(110.01f - y)) / (2 * (110.01f - y)));
			// temp -= (0.16225 * y * (((y - 110.01f) + Math.abs(y - 110.01f)) /
			// (2 * (y - 110.01f))));

			// float ySq = y * y;
			// float diff = 110.01f - y;
			// float factor = (ySq / 677.966f) * ((diff + Math.abs(diff)) / (2 *
			// diff))
			// + 0.16225f * y * ((diff - Math.abs(diff)) / (2 * diff));

			// more optimization: using an if should be more efficient (and
			// simpler)
			
			//Whoever redid this royally f**ked it up so now I have to fix it
			//Assume dry adiabatic lapse rate of 9.8 C per km
			//Scale an altitude of 3 km against this value = our maximum decrease should be 29.4 C
			double deltaSq = Math.pow(256 - Global.SEALEVEL, 2);
			float factor;
			if (y < Global.SEALEVEL)
			{
				// diff > 0
				factor = 0;
			}
			else
			{				
				factor = (float) (29.4D * Math.pow(y - Global.SEALEVEL, 2) / deltaSq);
			}
			Y_FACTOR_CACHE[y] = factor;
		}
		double dayStep = 1d / (double)TFC_Time.daysInYear;
		for (int zCoord = 0; zCoord < getMaxZPos() + 1; ++zCoord)
		{
			float factor = 0;
			float z = zCoord;

			factor = Math.abs(z) / getMaxZPos();
			factor = Math.max(-1f, Math.min(1f,factor));

			Z_FACTOR_CACHE[zCoord] = factor;
			double L = factor * 90;

			SUMMER_LOW_TEMP_CACHE[zCoord] = (float) ((0.000025 * L * L * L) - (0.01 * (L-10) *(L-10)) + (L/6d) + 20);/*(float) ((0.0000207237 * L * L * L) - (0.00699399 * L * L) + (0.244519 * L)
					+ 19.1519);*/
			
			//No longer accurate
			SUMMER_LOW_TEMP_PRIME_CACHE[zCoord] = (float) ((3d * 0.0000207237 * L * L) - (2d * 0.00699399 * L ) + (0.244519));
			
			WINTER_LOW_TEMP_CACHE[zCoord] = /*(float) ((0.000290385 * L * L * L) - (0.0415465 * L * L) + (0.51148 * L) + (18.7403));*/
					(float) ((0.000290385 * L * L * L) - (0.0375465 * L * L) + (0.51148 * L) + (18.7403)); //This is the old value
			WINTER_LOW_TEMP_PRIME_CACHE[zCoord] = (float) ((3d * 0.000290385 * L * L) - (2d * 0.0415465 * L) + (0.51148) );
			
			BASE_TEMP_DELTA_CACHE[zCoord] = (11f - factor * 3f)/5f;
			
			

			SUMMER_HIGH_VARIATION_CACHE[zCoord] = (float) ((0.000119065 * L * L * L) - (0.0196944 * L * L)
					+ (0.79228 * L) + 5.01897);
			//No longer accurate
			SUMMER_HIGH_VARIATION_PRIME_CACHE[zCoord] = (float) ((3d * 0.000140065 * L * L) - (2d * 0.0196944 * L)
					+ (0.79228));
			WINTER_HIGH_VARIATION_CACHE[zCoord] = (float) ((-0.000100645 * L * L * L) + (0.0138831 * L * L)
					- (0.188317 * L) + 5.09465);
			WINTER_HIGH_VARIATION_PRIME_CACHE[zCoord] = (float) ((3d * -0.000100645 * L * L) + (2d  *0.0138831 * L)
					- (0.188317));

			SUMMER_LOW_VARIATION_CACHE[zCoord] = (float) ((0.000126883 * L * L * L) - (0.0121216 * L * L)
					+ (0.134343 * L) - 4.95081);
			WINTER_LOW_VARIATION_CACHE[zCoord] = (float) ((0.000266199 * L * L * L) - (0.0270309 * L * L)
					+ (0.35208 * L) - 4.82876);
			L = Math.abs(3 * z / 10000d);
			L = Math.min(L, 80);
			
			//The maximum temperature would be the day and time at which the sum of the base_temp_delta, temp_high and temp_variation is maximized.
			//Since we're doing an optimization problem, we should define the function:
			//let t(a) be the maximum temperature on a given day. Let s(a) be the summer_low_cache and w(a) be the winter_low_cache. let v(a) be the
			//summer_variation and u(a) be the winter_variation. Let a be the day of the year from 0 to 1
			//let a = 0 represent winter.
			//let b represent the base temp delta, which is constant at a given Z
			//t(a) = a * (w(a) + u(a)) + (1-a) * (s(a) + v(a)) + b
			//t(a) = a * w(a) + a*u(a) + s(a) + v(a) -a*s(a) -a*v(a) + b
			//d/da [ a * w(a) + a*u(a) + s(a) + v(a) -a*s(a) -a*v(a) + b] = a * w'(a) + w(a) + a * u'(a) + u(a) + s'(a) + v'(a) -a*s'(a) - s(a) - a*v'(a) -v(a)
			double warmestTemp = -1000;
			for(double day = 0; day <=1;day+=dayStep)
			{
				double temperature = day * (WINTER_HIGH_VARIATION_CACHE[zCoord] + WINTER_LOW_TEMP_CACHE[zCoord])
						+ (1d-day) * ( SUMMER_LOW_TEMP_CACHE[zCoord] + SUMMER_HIGH_VARIATION_CACHE[zCoord]) + BASE_TEMP_DELTA_CACHE[zCoord];
				if(Math.abs(zCoord)>27000)
				{
				//	temperature = Math.min(temperature, (float)(Math.min(Math.abs(zCoord),30000)-27000)/1000f);
				}
				if(temperature > warmestTemp)
				{
					warmestTemp = temperature;
				}
			}
			MAXIMUM_TEMPERATURE[zCoord] = (float)warmestTemp;
			/*
			 * for(int month = 0; month < 12; ++month) { final float MAXTEMP =
			 * 35F;
			 * 
			 * double angle = factor * (Math.PI / 2); double latitudeFactor =
			 * Math.cos(angle);
			 * 
			 * switch(month) { case 10: { MONTH_TEMP_CACHE[month][zCoord] =
			 * (float)(MAXTEMP-13.5*latitudeFactor - (latitudeFactor*55));
			 * break; } case 9: case 11: { MONTH_TEMP_CACHE[month][zCoord] =
			 * (float)(MAXTEMP -12.5*latitudeFactor- (latitudeFactor*53));
			 * break; } case 0: case 8: { MONTH_TEMP_CACHE[month][zCoord] =
			 * (float)(MAXTEMP -10*latitudeFactor- (latitudeFactor*46)); break;
			 * } case 1: case 7: { MONTH_TEMP_CACHE[month][zCoord] =
			 * (float)(MAXTEMP -7.5*latitudeFactor- (latitudeFactor*40)); break;
			 * } case 2: case 6: { MONTH_TEMP_CACHE[month][zCoord] =
			 * (float)(MAXTEMP - 5*latitudeFactor- (latitudeFactor*33)); break;
			 * } case 3: case 5: { MONTH_TEMP_CACHE[month][zCoord] =
			 * (float)(MAXTEMP -2.5*latitudeFactor- (latitudeFactor*27)); break;
			 * } case 4: { MONTH_TEMP_CACHE[month][zCoord] = (float)(MAXTEMP
			 * -1.5*latitudeFactor- (latitudeFactor*27)); break; } } }
			 */
		}
	}

	protected static float getZFactor(int zCoord)
	{
		if (zCoord < 0)
			zCoord = -zCoord;

		if (zCoord > getMaxZPos())
			zCoord = getMaxZPos();

		return Z_FACTOR_CACHE[zCoord];
	}

	protected static float getTemp(World world, int x, int z)
	{
		return getTemp0(world, TFC_Time.currentDay, TFC_Time.getHour(), x, z, false);
	}

	protected static float getTemp(World world, int day, int hour, int x, int z)
	{
		return getTemp0(world, day, hour, x, z, false);
	}

	protected static float getBioTemp(World world, int day, int x, int z)
	{
		return getTemp0(world, day, 0, x, z, true);
	}

	// How much the temperature variation is affected by local rainfall
	private static float getRainVariationAdjustment(World world, int x, int z)
	{
		float rain = getRainfall(world, x, Global.SEALEVEL, z);
		if (rain < 500)
		{
			return 2f - (rain / 500f);
		}
		else
		{
			rain = Math.min(rain, 8000);
			return 1f - (rain / 16000f);
		}
	}
	/**
	 * This function returns the maximum temperature for a given location
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static float getMaxTemperature(World world, int x, int y, int z)
	{
		int adjustedZ = Math.min(Math.abs(z),30000);
		return adjustHeightToTemp(y, MAXIMUM_TEMPERATURE[adjustedZ]);
	}

	private static float getTemp0(World world, int day, int hour, int x, int z, boolean bio)
	{
		if (TFC_Climate.getCacheManager(world) != null)
		{
			int aZ = Math.min(30000, Math.abs(z));
			float temperatureDeltaCoefficient = getRainVariationAdjustment(world, x, z);
			
			
			float tempVariation = bio ? 0f : WeatherManager.INSTANCE.getDailyTemp(day);
			//tempVariation *= 0;

			float summerCache = Math.abs(z) > 27000 && SUMMER_LOW_TEMP_CACHE[aZ] > 0 ? SUMMER_LOW_TEMP_CACHE[aZ] * ( Math.max(28000 - Math.abs(z),-1000f)/1000f):SUMMER_LOW_TEMP_CACHE[aZ] ;
			float temp =  TFC_Time.getPercentSummer(day, z) * summerCache
					+ TFC_Time.getPercentWinter(day, z) * WINTER_LOW_TEMP_CACHE[aZ];
			
			
			float dailyFluxuation = 0.5f;
			//Get the non-bio temperature
			if (!bio)
			{
				//Find out how much above or below average we are.
				float interpolatedHigh = TFC_Time.getPercentSummer(day, z) * SUMMER_HIGH_VARIATION_CACHE[aZ]
						+ TFC_Time.getPercentWinter(day, z) * WINTER_HIGH_VARIATION_CACHE[aZ];
				float interpolatedLow = TFC_Time.getPercentSummer(day, z) * SUMMER_LOW_VARIATION_CACHE[aZ]
						+ TFC_Time.getPercentWinter(day, z) * WINTER_LOW_VARIATION_CACHE[aZ];
								
				//if(TFC_Time.getTotalTicks()%20 == 0)
				//	System.out.println("giving interpolatedLow " + interpolatedLow +", at " +x +", " + z);
				
				if (tempVariation > 0)
				{
					tempVariation *= interpolatedHigh;
				}
				else
				{
					tempVariation *= interpolatedLow * -1f;
				}
				
				
				
				//Based on time of day. Assume hour 0 = 6 am. This is the coldest.
				hour-=6;
				if(hour < 0)
				{
					hour += 24;
				}
				dailyFluxuation = hour < 9? (float)hour/9f:(24f-(float)hour)/15f;	
			}
			temp += BASE_TEMP_DELTA_CACHE[aZ] * dailyFluxuation * temperatureDeltaCoefficient + tempVariation;
			//Rainy areas have higher lows than normal. Dry areas have lower lows or something. The normal range is 0.5 to 2, so this becomes -0.5 to 1.		
			if(Math.abs(z)>27000)
			{
			//	temp = Math.min(temp, (float)(Math.min(Math.abs(z),30000)-27000)/1000f);
			}
			temp += 3 * -(temperatureDeltaCoefficient-1);			
			
			return temp;
		}
		return -10;
	}

	/*
	 * protected static float getMonthTemp(int season, int z) { if(z < 0) z =
	 * -z; if (z > getMaxZPos()) z = getMaxZPos(); return
	 * MONTH_TEMP_CACHE[season][z]; }
	 */

	protected static float getTempSpecificDay(World world, int day, int x, int z)
	{
		return getTemp(world, day, 12, x, z);
	}

	public static float getHeightAdjustedTemp(World world, int x, int y, int z)
	{
		float temp = getTemp(world, x, z);
		temp += getTemp(world, x + 1, z);
		temp += getTemp(world, x - 1, z);
		temp += getTemp(world, x, z + 1);
		temp += getTemp(world, x, z - 1);
		temp /= 5;
		temp = adjustHeightToTemp(y, temp);
		
			return temp;
	}

	public static float adjustHeightToTempGeneration(int y, float temp)
	{
		
			int i = y - Global.SEALEVEL;
			if (i >= Y_FACTOR_CACHE.length)
			{
				i = Y_FACTOR_CACHE.length - 1;
			}
			i = Math.max(i, 0);
			temp -= Y_FACTOR_CACHE[i];
	
		return temp;
	}
	
	public static float adjustHeightToTemp(int y, float temp)
	{
		// internationally accepted average lapse time is 6.49 K / 1000 m, for
		// the first 11 km of the atmosphere. I suggest graphing our temperature
		// across the 110 m against 2750 m, so that gives us a change of 1.6225
		// / 10 blocks, which isn't /terrible/
		// Now going to attemp exonential growth. calculations but change in
		// temperature at 17.8475 for our system, so that should be the drop at
		// 255.
		// therefore, change should be temp - f(x), where f(x) is an exp
		// function roughly equal to f(x) = (x^2)/ 677.966.
		// This seems to work nicely. I like this. Since creative allows players
		// to travel above 255, I'll see if I can't code in the rest of it.
		// The upper troposhere has no lapse rate, so we'll just use that.
		// The equation looks rather complicated, but you can see it here:
		// http://www.wolframalpha.com/input/?i=%28%28%28x%5E2+%2F+677.966%29+*+%280.5%29*%28%28%28110+-+x%29+%2B+%7C110+-+x%7C%29%2F%28110+-
		// +x%29%29%29+%2B+%28%280.5%29*%28%28%28x+-+110%29+%2B+%7Cx+-+110%7C%29%2F%28x+-+110%29%29+*+x+*+0.16225%29%29+0+to+440
		if (y > Global.SEALEVEL)
		{
			int i = y;
			if (i >= Y_FACTOR_CACHE.length)
			{
				i = Y_FACTOR_CACHE.length - 1;
			}
			temp -= Y_FACTOR_CACHE[i];
		}
		else if (y < Global.SEALEVEL - 5)
		{
			// Basic temperature of underground areas.
			temp = 15;
		}
		return temp;
	}

	public static float getHeightAdjustedTempSpecificDay(World world, int day, int x, int y, int z)
	{
		float temp = getTempSpecificDay(world, day, x, z);
		temp = adjustHeightToTemp(y, temp);
		return temp;
	}

	public static float getHeightAdjustedTempSpecificDay(World world, int day, int hour, int x, int y, int z)
	{
		float temp = getTemp(world, day, hour, x, z);
		temp = adjustHeightToTemp(y, temp);
		return temp;
	}

	public static float getHeightAdjustedBioTemp(World world, int day, int x, int y, int z)
	{
		float temp = getBioTemp(world, day, x, z);
		temp = adjustHeightToTemp(y, temp);
		return temp;
	}

	public static float getMaxTemperature()
	{
		return 52;
	}

	public static float getBioTemperatureHeight(World world, int x, int y, int z)
	{
		float temp = 0;
		for (int i = 0; i < 48; i++)
		{
			float t = getHeightAdjustedBioTemp(world, i * TFC_Time.daysInMonth/4, x, y, z);
			// if(t < 0)
			// t = 0;
			temp += t;
		}
		return temp / 48;
	}

	public static float getBioTemperature(World world, int x, int z)
	{
		float temp = 0;
		for (int i = 0; i < 24; i++)
		{
			float t = getBioTemp(world, i * TFC_Time.daysInMonth / 2, x, z);
			// if(t < 0)
			// t = 0;
			temp += t;
		}
		return temp / 24;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic grass color based on the biome temperature and
	 * rainfall
	 */
	public static int getGrassColor(World world, int x, int y, int z)
	{
		float temp = (getTemp(world, x, z) + getMaxTemperature()) / (getMaxTemperature() * 2);

		float rain = getRainfall(world, x, y, z) / 8000;

		double var1 = Helper.clampFloat(temp, 0.0F, 1.0F);
		double var3 = Helper.clampFloat(rain, 0.0F, 1.0F);

		return ColorizerGrassTFC.getGrassColor(var1, var3);
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic foliage color based on the biome temperature and
	 * rainfall
	 */
	public static int getFoliageColor(World world, int x, int y, int z)
	{
		float temperature = getHeightAdjustedTempSpecificDay(world, TFC_Time.getDayOfYear(), x, y, z);
		float rainfall = getRainfall(world, x, y, z);
		if (temperature > 5 && rainfall > 100)
		{
			float temp = (temperature + 35) / (getMaxTemperature() + 35);
			float rain = rainfall / 8000;

			double var1 = Helper.clampFloat(temp, 0.0F, 1.0F);
			double var3 = Helper.clampFloat(rain, 0.0F, 1.0F);
			return ColorizerFoliageTFC.getFoliageColor(var1, var3);
		}
		else
		{
			return ColorizerFoliageTFC.getFoliageDead();
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Provides the basic foliage color based on the biome temperature and
	 * rainfall
	 */
	public static int getFoliageColorEvergreen(World world, int x, int y, int z)
	{
		// int month = TFC_Time.getSeasonAdjustedMonth(z);
		float rainfall = getRainfall(world, x, y, z);
		if (rainfall > 100)
		{
			float temp = (getTemp(world, x, z) + 35) / (getMaxTemperature() + 35);
			float rain = rainfall / 8000;

			double var1 = Helper.clampFloat(temp, 0.0F, 1.0F);
			double var3 = Helper.clampFloat(rain, 0.0F, 1.0F);
			return ColorizerFoliageTFC.getFoliageColor(var1, var3);
		}
		else
		{
			return ColorizerFoliageTFC.getFoliageDead();
		}
	}

	/**
	 * All Rainfall related code
	 */
	// We now adjust latitude for the horse latitudes so that it's dry or hot in
	// different places?
	public static float getRainfall(World world, int x, int y, int z)
	{
		// This isn't actually latitude, we combine *90 and /30 to get *3 and
		// combine *3 with /30000 to get /10000
		float latitude = Math.abs((float) z / 10000f);
		float latitudeAdjustment = (1.1f + (MathHelper.cos((float) Math.PI * latitude))) * (1 - latitude / 4);
		if (world.isRemote && TFC_Core.getCDM(world) != null)
		{
			ChunkData cd = TFC_Core.getCDM(world).getData(x >> 4, z >> 4);
			if (cd != null)
				return cd.getRainfall(x & 15, z & 15) * latitudeAdjustment;
		}

		if (getCacheManager(world) != null)
		{
			DataLayer dl = getCacheManager(world).getRainfallLayerAt(x, z);
			return dl != null ? latitudeAdjustment * dl.floatdata1 : latitudeAdjustment * DataLayer.RAIN_500.floatdata1;
		}

		return latitudeAdjustment * DataLayer.RAIN_500.floatdata1;
	}

	public static int getTreeLayer(World world, int x, int y, int z, int index)
	{
		return getCacheManager(world).getTreeLayerAt(x, z, index).data1;
	}
	
	public static int getRegionLayer(World world, int x, int y, int z)
	{
		return getCacheManager(world).getRegionLayerAt(x, z).data1;
	}

	public static DataLayer getRockLayer(World world, int x, int y, int z, int index)
	{
		return getCacheManager(world).getRockLayerAt(x, z, index);
	}

	public static int getMaxZPos()
	{
		return 30000;
	}

	public static boolean isSwamp(World world, int x, int y, int z)
	{
		float rain = getRainfall(world, x, y, z);
		float evt = getCacheManager(world).getEVTLayerAt(x, z).floatdata1;
		return rain >= 1000 && evt <= 0.25 && world.getBiomeGenForCoords(x, z).heightVariation < 0.15;
	}

	public static int getStability(World world, int x, int z)
	{
		if (getCacheManager(world) != null)
			return getCacheManager(world).getStabilityLayerAt(x, z).data1;
		else
			return 0;
	}

	public static WorldCacheManager getCacheManager(World world)
	{
		return worldPair.get(world);
	}

	public static void removeCacheManager(World world)
	{
		if (worldPair.containsKey(world))
			worldPair.remove(world);
	}
}
