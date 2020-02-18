package com.dunk.tfc.Core;

import java.util.Random;

import net.minecraft.world.World;

public class WeatherManager
{
	protected static final WeatherManager INSTANCE = new WeatherManager();
	private Random rand = new Random();
	private Random clientRand = new Random();
	public static final WeatherManager getInstance()
	{
		return INSTANCE;
	}

	public long seed;

	public WeatherManager()
	{
	}

	/*private Random getRandom(World world)
	{
		if(world.isRemote)
			return clientRand;
		return rand;
	}*/

	public float getDailyTemp()
	{
		return getDailyTemp(TFC_Time.getTotalDays());
	}

	public float getDailyTemp(int day)
	{
		rand.setSeed(seed + day);
		int x = rand.nextInt(100);
		return (rand.nextInt(Math.max((2*x),1))-x) / 100f;
	}

	public float getWeeklyTemp(int week)
	{
		rand.setSeed(seed + week);
		int x = rand.nextInt(100);
		return (rand.nextInt(Math.max((2*x),1))-x) / 100f;
	}

	public static int getDayOfWeek(long day)
	{
		long days = day / 6;
		return (int) (day - (days * 6));
	}

	public static boolean canSnow(World world, int x, int y, int z)
	{
		return TFC_Climate.getHeightAdjustedTemp(world, x, y, z) <= 0;
	}

	public float getLocalFog(World world, int x, int y, int z)
	{
		if(world.isRemote)
		{
			int hour = TFC_Time.getHour();
			if(hour >= 4 && hour < 9)
			{
				clientRand.setSeed(TFC_Time.getTotalDays());
				float rain = TFC_Climate.getRainfall(world, x, y, z);
				float strength = clientRand.nextFloat();
				if(rain >= 500 && clientRand.nextInt(3) == 0)
				{
					float mult = 1f;
					if(9-hour < 2)
						mult = 0.5f;
					return strength*mult;//Makes the fog weaker as time goes on.
				}
			}
		}
		return 0;
	}

	public float getSnowStrength()
	{
		int hour = TFC_Time.getHour();
		clientRand.setSeed(TFC_Time.getTotalDays()+hour);
		return clientRand.nextFloat();
	}
}
