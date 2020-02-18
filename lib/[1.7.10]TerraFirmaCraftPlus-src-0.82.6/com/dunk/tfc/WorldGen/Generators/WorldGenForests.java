package com.dunk.tfc.WorldGen.Generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenAcaciaKoaTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomPalmTrees;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomShortTrees;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumForestPlant;
import com.dunk.tfc.api.Enums.EnumTree;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenForests implements IWorldGenerator
{
	/** The number of blocks to generate. */
	// private int numberOfBlocks;

	private WorldGenerator gen0;

	private float evt;
	private float rainfall;
	private float temperature = 20f;
	private int region;

	// See if we can speed things up by not doing forest gen forever
	public static ArrayList<Long> generatingChunks = new ArrayList<Long>();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		int origChunkX, origChunkZ;
		origChunkX = chunkX;
		origChunkZ = chunkZ;

		generatingChunks.add((long) (origChunkX + origChunkZ << 16));

		chunkX *= 16;
		chunkZ *= 16;

		if (world.getBiomeGenForCoords(chunkX, chunkZ) instanceof TFCBiome) // Fixes
																			// ClassCastException
		{
			TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(chunkX, chunkZ);
			if (biome == TFCBiome.OCEAN || biome == TFCBiome.DEEP_OCEAN)
				return;

			rainfall = TFC_Climate.getRainfall(world, chunkX, 0, chunkZ);
			if (TFC_Climate.getCacheManager(world) != null && TFC_Climate.getCacheManager(world).getEVTLayerAt(chunkX + 8, chunkZ + 8) != null)
			{
				// Seems to be a weird crash here, so we'll just grab a fake
				// value if it goes wrong.
				evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(chunkX + 8, chunkZ + 8).floatdata1;
			}
			else
			{
				evt = 0.5f;
			}
			// treeType0 = TFC_Climate.getTreeLayer(world, chunkX,
			// Global.SEALEVEL, chunkZ, 0);
			// treeType1 = TFC_Climate.getTreeLayer(world, chunkX,
			// Global.SEALEVEL, chunkZ, 1);
			// treeType2 = TFC_Climate.getTreeLayer(world, chunkX,
			// Global.SEALEVEL, chunkZ, 2);

			region = TFC_Climate.getRegionLayer(world, chunkX, Global.SEALEVEL, chunkZ);

			// gen0 = TFCBiome.getTreeGen(treeType0, random.nextBoolean());
			// gen1 = TFCBiome.getTreeGen(treeType1, random.nextBoolean());
			// gen2 = TFCBiome.getTreeGen(treeType2, random.nextBoolean());
			// gen0 = new WorldGenTrees(false, 4, 1, 1, false);
			// gen1 = new WorldGenTrees(false, 4, 1, 1, false);
			// gen2 = new WorldGenTrees(false, 4, 1, 1, false);

			// if (!generateJungle(random, chunkX, chunkZ, world))
			generateForest(random, chunkX, chunkZ, world);
		}
		generatingChunks.remove((long) (origChunkX + origChunkZ << 16));
	}

	private void generateForest(Random random, int chunkX, int chunkZ, World world)
	{
		int xCoord = chunkX;
		int yCoord = Global.SEALEVEL + 1;
		int zCoord = chunkZ;

		if (getNearWater(world, xCoord, yCoord, zCoord))
		{
			rainfall = Math.max(rainfall * 2, 400);
			// evt /= 2;
		}
		if (world.getBiomeGenForCoords(chunkX, chunkZ) == TFCBiome.SWAMPLAND)
		{
			rainfall *= 1.25f;
		}
		// int numTrees = 1;
		int numPlants = 25;
		int numTrees = (int) (30f * (rainfall / 2000f) * (Math.min(Math.max(0.1f, evt), 1f)));
		numTrees = (int) (numTrees * (Math.min(2000f, rainfall) / 2000f));
		int numActualTrees = 0;

		for (int var2 = 0; var2 < numTrees; ++var2)
		{
			xCoord = chunkX + random.nextInt(16);
			zCoord = chunkZ + random.nextInt(16);
			yCoord = world.getHeightValue(xCoord, zCoord);

			// numTrees = (int) (numTreesBase + ((rainfall / 1000) * 2));
			// if (numTrees > 30)
			// numTrees = 30;

			if (world.getBiomeGenForCoords(chunkX, chunkZ) == TFCBiome.BEACH && numTrees > 5)
			{
				numTrees = 10;
			}

			temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, world.getHeightValue(xCoord, zCoord), zCoord);

			try
			{
				boolean success = false;
				int counter = 0;
				// This should help. Previously, we went through all trees in
				// order, but this gives an advantage to trees lower on the list
				int startingTree = random.nextInt(EnumTree.REGIONS[region].length);
				while (counter < EnumTree.REGIONS[region].length && !success)
				{
					EnumTree tree = EnumTree.REGIONS[region][startingTree % EnumTree.REGIONS[region].length];
					startingTree++;
					counter++;

					if (success)
					{
						break;
					}
					int i = 0;
					boolean foundTree = false;
					for (; i < EnumTree.values().length; i++)
					{
						if (tree == EnumTree.values()[i])
						{
							foundTree = true;
							break;
						}
					}
					if (!foundTree)
					{
						continue;
					}
					gen0 = TFCBiome.getTreeGen(random.nextBoolean(), numTrees < 2, tree);
					// gen0 = TFCBiome.getTreeGen(false, false,
					// EnumTree.FEVERTREE);
					if (gen0 != null && shouldTreeGrow(EnumTree.values()[i], rainfall, temperature, evt, random)
					&& world.getBlock(xCoord, yCoord, zCoord).isReplaceable(world, xCoord, yCoord, zCoord))
					{

						// Generate Bamboo in clumps
						if (tree == EnumTree.BAMBOO)
						{

							for (int ii = -2; ii <= 2; ii++)
							{
								for (int j = -2; j <= 2; j++)
								{
									if (random.nextInt(4) == 0 && world.blockExists(xCoord + ii, yCoord, zCoord + j))
									{
										gen0.generate(world, random, xCoord + ii, yCoord, zCoord + j);
									}
								}
							}
						}
						if (gen0 != null)
						{
							success = gen0.generate(world, random, xCoord, yCoord, zCoord);
							numActualTrees++;
						}
						// numTrees--;

					}
				}

			}
			catch (IndexOutOfBoundsException e)
			{
			}

		}
		int numShrubs = (int) (((2000 - Math.min(rainfall, 2000)) / 40f));
		for (int var2 = 0; var2 < numShrubs; ++var2)
		{
			xCoord = chunkX + random.nextInt(16);
			zCoord = chunkZ + random.nextInt(16);
			yCoord = world.getHeightValue(xCoord, zCoord);
			temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, world.getHeightValue(xCoord, zCoord), zCoord);
			EnumForestPlant plant = EnumForestPlant.SHRUB;
			try
			{
				int i = 0;
				boolean foundPlant = false;
				for (; i < EnumForestPlant.values().length; i++)
				{
					if (plant == EnumForestPlant.values()[i])
					{
						foundPlant = true;
						break;
					}

				}
				if (!foundPlant)
				{
					continue;
				}
				gen0 = TFCBiome.getPlantGen(i, random.nextBoolean());
				if (plant != EnumForestPlant.SHRUB && numActualTrees <= 15)
				{
					continue;
				}
				if (gen0 != null && shouldPlantGrow(EnumForestPlant.values()[i], rainfall, temperature, evt, random)
				&& world.getBlock(xCoord, yCoord, zCoord).isReplaceable(world, xCoord, yCoord, zCoord))
				{
					for (int ii = -3; ii <= 3; ii++)
					{
						for (int j = -3; j <= 3; j++)
						{
							if (world.canBlockSeeTheSky(xCoord + ii, yCoord, zCoord + j) && gen0 != null && random.nextInt(16) == 0 && (xCoord + ii) / 16 == xCoord / 16 && (zCoord + j) / 16 == zCoord / 16 && ii * ii + j * j < 9)
							{
								gen0.generate(world, random, xCoord + ii, yCoord, zCoord + j);
							}
						}
					}

				}
			}
			catch (IndexOutOfBoundsException e)
			{
			}
		}
		numPlants = (int) Math.min(((numActualTrees / 5) * Math.min((rainfall / 2000), 1.5f)), 20);
		for (int var2 = 0; var2 < numPlants && numActualTrees > 15; ++var2)
		{
			xCoord = chunkX + random.nextInt(16);
			zCoord = chunkZ + random.nextInt(16);
			yCoord = world.getHeightValue(xCoord, zCoord);

			if (world.getBiomeGenForCoords(chunkX, chunkZ) == TFCBiome.BEACH && numPlants > 10)
			{
				numPlants = 0;
			}

			temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, world.getHeightValue(xCoord, zCoord), zCoord);

			try
			{

				int randomNumber = random.nextInt(EnumForestPlant.REGIONS[region].length);
				boolean success = false;
				int regionSize = EnumForestPlant.REGIONS[region].length;
				for (int count = 0; count < EnumForestPlant.REGIONS[region].length && !success; count++)
				{
					// We count through the plants in order, starting at a
					// random plant
					EnumForestPlant plant = EnumForestPlant.REGIONS[region][(randomNumber + count) % regionSize];
					if (success)
					{
						break;
					}
					int i = 0;
					boolean foundPlant = false;
					for (; i < EnumForestPlant.values().length; i++)
					{
						if (plant == EnumForestPlant.values()[i])
						{
							foundPlant = true;
							break;
						}
					}
					if (!foundPlant)
					{
						continue;
					}
					gen0 = TFCBiome.getPlantGen(i, random.nextBoolean());
					if (plant != EnumForestPlant.SHRUB && numActualTrees <= 15)
					{
						continue;
					}
					if (gen0 != null && shouldPlantGrow(EnumForestPlant.values()[i], rainfall, temperature, evt, random)
					&& world.getBlock(xCoord, yCoord, zCoord).isReplaceable(world, xCoord, yCoord, zCoord))
					{

						if (plant != EnumForestPlant.UNDERGROWTH_SHORT)
						{

							for (int ii = -3; ii <= 3; ii++)
							{
								for (int j = -3; j <= 3; j++)
								{
									if (gen0 != null && random.nextInt(40) == 0 && (xCoord + ii) / 16 == xCoord / 16 && (zCoord + j) / 16 == zCoord / 16 && ii * ii + j * j < 9)
									{
										gen0.generate(world, random, xCoord + ii, yCoord, zCoord + j);
									}
								}
							}
						}
						else if (plant == EnumForestPlant.UNDERGROWTH_SHORT)
						{
							int radius = 3;
							for (int ii = -radius; ii <= radius; ii++)
							{
								for (int j = -radius; j <= radius; j++)
								{
									if (gen0 != null && random.nextInt(12) != 0 && (xCoord + ii) / 16 == xCoord / 16 && (zCoord + j) / 16 == zCoord / 16
									&& ii * ii + j * j < radius * radius)
									{
										gen0.generate(world, random, xCoord + ii, yCoord, zCoord + j);
									}
								}
							}
						}

						if (gen0 != null)
						{
							success = gen0.generate(world, random, xCoord, yCoord, zCoord);
						}

						// numPlants--;
					}
				}
			}
			catch (IndexOutOfBoundsException e)
			{
			}
		}
	}

	public static boolean shouldTreeGrow(EnumTree tree, float rainfall, float temperatureAvg, float evt, Random rand)
	{
		// We want to calculate whether the tree should grow
		// We do this by seeing whether the conditions are ideal
		if (evt <= tree.maxEVT && rainfall >= tree.minRain && rainfall <= tree.maxRain && temperatureAvg >= tree.minTemp && temperatureAvg <= tree.maxTemp)
		{
			// This means our values are at least possible for this type of tree
			// We take the "ideal" zone, defined as the average of max and min
			float tempIdeal = (tree.maxTemp + tree.minTemp) / 2f;
			float evtIdeal = (tree.minEVT + tree.maxEVT) / 2f;

			float rainIdeal = (tree.minRain + tree.maxRain) / 2f;
			if (tree.maxRain == 16000)
			{
				rainIdeal = 3000;
			}
			float rainValue = Math.abs(rainfall - rainIdeal) / (rainIdeal - tree.minRain);
			if (tree.maxRain == 16000 && rainfall >= 3000)
			{
				rainValue = 1f;
			}
			float evtValue = Math.abs(evt - evtIdeal) / (evtIdeal - tree.minEVT);
			float tempValue = Math.abs(temperatureAvg - tempIdeal) / (tempIdeal - tree.minTemp);
			float totalValue = (rainValue + evtValue + tempValue) / 3f;
			if (totalValue > 0)
			{
				return rand.nextFloat() < totalValue * tree.rarity;
			}
			else
			{
				return rand.nextInt(100) == 0;
			}
		}
		return false;
	}

	public static boolean shouldPlantGrow(EnumForestPlant plant, float rainfall, float temperatureAvg, float evt, Random rand)
	{
		// We want to calculate whether the tree should grow
		// We do this by seeing whether the conditions are ideal
		if (evt <= plant.maxEVT && rainfall >= plant.minRain && rainfall <= plant.maxRain && temperatureAvg >= plant.minTemp && temperatureAvg <= plant.maxTemp)
		{
			// This means our values are at least possible for this type of tree
			// We take the "ideal" zone, defined as the average of max and min
			float tempIdeal = (plant.maxTemp + plant.minTemp) / 2f;
			float evtIdeal = (plant.minEVT + plant.maxEVT) / 2f;

			float rainIdeal = (plant.minRain + plant.maxRain) / 2f;
			if (plant.maxRain == 16000)
			{
				rainIdeal = 3000;
			}
			float rainValue = Math.abs(rainfall - rainIdeal) / (rainIdeal - plant.minRain);
			if (plant.maxRain == 16000 && rainfall >= 3000)
			{
				rainValue = 1f;
			}
			float evtValue = Math.abs(evt - evtIdeal) / (evtIdeal - plant.minEVT);
			float tempValue = Math.abs(temperatureAvg - tempIdeal) / (tempIdeal - plant.minTemp);
			float totalValue = (rainValue + evtValue + tempValue) / 3f;
			if (totalValue > 0)
			{
				return rand.nextFloat() < totalValue;
			}
			else
			{
				return rand.nextInt(100) == 0;
			}
		}
		return false;
	}

	public boolean getNearWater(World world, int x, int y, int z)
	{
		for (int x1 = -4; x1 < 5; ++x1)
		{
			for (int z1 = -4; z1 < 5; ++z1)
			{
				for (int y1 = -2; y1 < 1; ++y1)
				{
					if (world.blockExists(x + x1, y + y1, z + z1) && TFC_Core.isFreshWater(world.getBlock(x + x1, y + y1, z + z1)))
						return true;
				}
			}
		}
		return false;
	}
}
