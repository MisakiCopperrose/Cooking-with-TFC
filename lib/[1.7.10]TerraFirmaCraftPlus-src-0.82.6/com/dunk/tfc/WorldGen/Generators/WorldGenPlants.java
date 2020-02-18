package com.dunk.tfc.WorldGen.Generators;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockLogNatural;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Food.FloraManager;
import com.dunk.tfc.TileEntities.TEBeehive;
import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenCustomFruitTree;
import com.dunk.tfc.WorldGen.Generators.Trees.WorldGenFruitTree;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumForestPlant;
import com.dunk.tfc.api.Enums.EnumFruitTree;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenPlants implements IWorldGenerator
{
	private static WorldGenFungi plantFungiGen = new WorldGenFungi();
	/*
	 * private static WorldGenCustomFruitTree appleTree = new
	 * WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves, 0); private
	 * static WorldGenCustomFruitTree bananaTree = new
	 * WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves, 1); private
	 * static WorldGenCustomFruitTree orangeTree = new
	 * WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves, 2); private
	 * static WorldGenCustomFruitTree grappleTree = new
	 * WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves, 3); private
	 * static WorldGenCustomFruitTree lemonTree = new
	 * WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves, 4); private
	 * static WorldGenCustomFruitTree oliveTree = new
	 * WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves, 5); private
	 * static WorldGenCustomFruitTree cherryTree = new
	 * WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves, 6); private
	 * static WorldGenCustomFruitTree peachTree = new
	 * WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves, 7);
	 * 
	 * private static WorldGenCustomFruitTree plumTree = new
	 * WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves2, 8);
	 */
	// private static WorldGenCustomFruitTree cacaoTree = new
	// WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves2, 9);

	private static WorldGenBerryBush wintergreenGen = new WorldGenBerryBush(false, 0, 12, 1, 5);
	private static WorldGenBerryBush blueberryGen = new WorldGenBerryBush(false, 1, 6, 1, 4);
	private static WorldGenBerryBush raspberryGen = new WorldGenBerryBush(false, 2, 5, 2, 4);
	private static WorldGenBerryBush strawberryGen = new WorldGenBerryBush(false, 3, 8, 1, 4);
	private static WorldGenBerryBush blackberryGen = new WorldGenBerryBush(false, 4, 5, 2, 4);
	private static WorldGenBerryBush bunchberryGen = new WorldGenBerryBush(false, 5, 8, 1, 4);
	private static WorldGenBerryBush cranberryGen = new WorldGenBerryBush(false, 6, 15, 1, 6);
	private static WorldGenBerryBush snowberryGen = new WorldGenBerryBush(false, 7, 6, 1, 4);
	private static WorldGenBerryBush elderberryGen = new WorldGenBerryBush(false, 8, 5, 2, 4);
	private static WorldGenBerryBush gooseberryGen = new WorldGenBerryBush(false, 9, 8, 1, 4);
	private static WorldGenBerryBush cloudberryGen = new WorldGenBerryBush(false, 10, 12, 1, 6, TFCBlocks.peatGrass);

	private int region;

	public WorldGenPlants()
	{
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		chunkX *= 16;
		chunkZ *= 16;

		int xCoord;
		int yCoord;
		int zCoord;

		int grassPerChunk = 100;
		int flowerChunkRarity = 30;
		int mushroomsPerChunk = 0;
		int beeHivesPerChunk = 0;

		region = TFC_Climate.getRegionLayer(world, chunkX, Global.SEALEVEL, chunkZ);

		float evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(chunkX, chunkZ).floatdata1;
		float rain = TFC_Climate.getRainfall(world, chunkX, 144, chunkZ);
		float bioTemperature;

		/*
		 * if(rain >= 62.5f) { }
		 */
		grassPerChunk += 50;
		if (rain >= 125)
		{
			 grassPerChunk+=50;
			mushroomsPerChunk += 1;
		}
		if (rain >= 250)
		{
			flowerChunkRarity -= 2;
			mushroomsPerChunk += 1;
			beeHivesPerChunk++;
		}
		if (rain >= 500)
		{
			grassPerChunk -= 24;
			flowerChunkRarity -= 3;
			mushroomsPerChunk += 1;
			beeHivesPerChunk += 3;
		}
		if (rain >= 1000)
		{
			flowerChunkRarity -= 5;
			mushroomsPerChunk += 1;
			beeHivesPerChunk++;
		}
		if (rain >= 2000)
		{
			flowerChunkRarity -= 5;
			mushroomsPerChunk += 1;
			beeHivesPerChunk++;
		}
		bioTemperature = TFC_Climate.getBioTemperatureHeight(world, chunkX, Global.SEALEVEL, chunkZ);
		if (bioTemperature < 10)
		{
			grassPerChunk /= 1.5f;
			
		}
		if (bioTemperature < 5)
		{
			grassPerChunk /= 2;
			beeHivesPerChunk = 0;
		}
		/*
		 * if (bioTemperature < -15) { grassPerChunk = 0; }
		 */
		WorldGenFlowers.generate(world, random, chunkX, chunkZ, flowerChunkRarity);

		genBushes(random, chunkX, chunkZ, world);
		for (int i = 0; i < grassPerChunk; ++i)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			bioTemperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, yCoord, zCoord);
			if (world.isAirBlock(xCoord, yCoord, zCoord) && TFCBlocks.tallGrass.canBlockStay(world, xCoord, yCoord, zCoord)
			&& !TFC_Core.isDryGrass(world.getBlock(xCoord, yCoord - 1, zCoord)))
			{
				world.setBlock(xCoord, yCoord, zCoord, TFCBlocks.tallGrass, (world.rand.nextInt(20) == 0 ? 1 : 0), 0x2); // tallgrass
																															// with
																															// 1/20
																															// chance
																															// to
																															// spawn
																															// a
																															// fern
			}

			if (bioTemperature >= 0)
			{
				if (world.isAirBlock(xCoord, yCoord, zCoord) && TFCBlocks.tallGrass.canBlockStay(world, xCoord, yCoord, zCoord)
				&& TFC_Core.isDryGrass(world.getBlock(xCoord, yCoord - 1, zCoord)))
				{
					world.setBlock(xCoord, yCoord, zCoord, TFCBlocks.tallGrass, (world.rand.nextInt(60) == 0 ? 1 : 2), 0x2); // shortgrass
																																// with
																																// 1/60
																																// chance
																																// to
																																// spawn
																																// a
																																// fern
				}
			}
		}

		// Only one hive per chunk. More hives per chunk just increases the
		// likelihood of one generating
		boolean madeHive = random.nextInt(20)>0;
		for (int i = 0; i < beeHivesPerChunk && !madeHive; i++)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			// We go upward until we find a suitable location.

			for (int yOffset = 0; yOffset > -12 && !madeHive && yCoord + yOffset > Global.SEALEVEL; yOffset--)
			{
				Block cur = world.getBlock(xCoord, yCoord + yOffset, zCoord);
				//System.out.println("cur ("+yOffset+"): " + cur.getLocalizedName());
				if ((cur.isReplaceable(world, xCoord, yCoord + yOffset, zCoord) || cur instanceof BlockCustomLeaves) && !(cur instanceof BlockLiquid))
				{
					// Test if this is suitable for a beehive
					Block above = world.getBlock(xCoord, yCoord + yOffset + 1, zCoord);
					if (above instanceof BlockBranch && !(
					((BlockBranch)above).getSourceY() == -1 
					&&((BlockBranch)above).getSourceX() == 0 
					&& ((BlockBranch)above).getSourceZ() == 0))
					{
						
						world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildBeehive);
						TEBeehive te = (TEBeehive) world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
						te.setInventorySlotContents(0, new ItemStack(TFCItems.fertileHoneycomb));
						madeHive = true;
					}
					else
					{
						Block adjacent = world.getBlock(xCoord -1, yCoord + yOffset, zCoord);
						if(adjacent instanceof BlockLogNatural && !madeHive && world.rand.nextInt(4)==0)
						{
							world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildTreehive,5,3);
							TEBeehive te = (TEBeehive) world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
							te.setInventorySlotContents(0, new ItemStack(TFCItems.fertileHoneycomb));
							madeHive = true;
						}
						adjacent = world.getBlock(xCoord +1, yCoord + yOffset, zCoord);
						if(adjacent instanceof BlockLogNatural && !madeHive && world.rand.nextInt(4)==0)
						{
							world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildTreehive,4,3);
							TEBeehive te = (TEBeehive) world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
							te.setInventorySlotContents(0, new ItemStack(TFCItems.fertileHoneycomb));
							madeHive = true;
						}
						adjacent = world.getBlock(xCoord, yCoord + yOffset, zCoord+1);
						if(adjacent instanceof BlockLogNatural && !madeHive && world.rand.nextInt(4)==0)
						{
							world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildTreehive,2,3);
							TEBeehive te = (TEBeehive) world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
							te.setInventorySlotContents(0, new ItemStack(TFCItems.fertileHoneycomb));
							madeHive = true;
						}
						adjacent = world.getBlock(xCoord, yCoord + yOffset, zCoord-1);
						if(adjacent instanceof BlockLogNatural && !madeHive && world.rand.nextInt(4)==0)
						{
							world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildTreehive,3,3);
							TEBeehive te = (TEBeehive) world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
							te.setInventorySlotContents(0, new ItemStack(TFCItems.fertileHoneycomb));
							madeHive = true;
						}
					}
				}
				else if(!(cur instanceof BlockLiquid))
				{
					//System.out.println("couldn't replace: " + cur.getLocalizedName());
				}
			}
		}

		for (int i = 0; i < mushroomsPerChunk; ++i)
		{
			if (random.nextInt(4) == 0)
			{
				xCoord = chunkX + random.nextInt(16) + 8;
				zCoord = chunkZ + random.nextInt(16) + 8;
				yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
				plantFungiGen.genWithMeta(world, random, xCoord, yCoord, zCoord, 0); // vanilla
																						// brown
																						// mushroom
			}

			if (random.nextInt(8) == 0)
			{
				xCoord = chunkX + random.nextInt(16) + 8;
				zCoord = chunkZ + random.nextInt(16) + 8;
				yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
				plantFungiGen.genWithMeta(world, random, xCoord, yCoord, zCoord, 1); // vanilla
																						// red
																						// mushroom
			}
		}
		xCoord = chunkX + random.nextInt(16);
		zCoord = chunkZ + random.nextInt(16);
		yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
		bioTemperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, yCoord, zCoord);
		if (getNearWater(world, xCoord, yCoord, zCoord))
		{
			rain = Math.max(rain * 2, 200);
			// evt /= 2;
		}
		WorldGenerator gen0 = null;
		if (random.nextInt(70) < 15)
		{
			try
			{
				boolean success = false;
				int numTrees = EnumFruitTree.REGIONS[region].length;
				int treeNum = random.nextInt(numTrees);
				for (int t = 0; t < numTrees; t++)
				{
					EnumFruitTree tree = EnumFruitTree.REGIONS[region][(t + treeNum) % numTrees];
					if (success)
					{
						break;
					}
					int i = 0;
					boolean foundPlant = false;
					for (; i < EnumFruitTree.values().length; i++)
					{
						if (tree == EnumFruitTree.values()[i])
						{
							foundPlant = true;
							break;
						}
					}
					if (!foundPlant)
					{
						continue;
					}
					switch (tree)
					{
					case APPLE:
					{
						gen0 = TFCBiome.appleTree;
						break;
					}
					case GRAPPLE:
					{
						gen0 = TFCBiome.grappleTree;
						break;
					}
					case LEMON:
					{
						gen0 = TFCBiome.lemonTree;
						break;
					}
					case CHERRY:
					{
						gen0 = TFCBiome.cherryTree;
						break;
					}
					case PLUM:
					{
						gen0 = TFCBiome.plumTree;
						break;
					}
					case OLIVE:
					{
						gen0 = TFCBiome.oliveTree;
						break;
					}
					case ORANGE:
					{
						gen0 = TFCBiome.orangeTree;
						break;
					}
					case PEACH:
					{
						gen0 = TFCBiome.peachTree;
						break;
					}
					case BANANA:
					{
						gen0 = TFCBiome.bananaTree;
						break;
					}
					case PAPAYA:
					{
						gen0 = TFCBiome.papayaTree;
						break;
					}
					case DATE:
					{
						gen0 = TFCBiome.datePalm;
						break;
					}
					}
					// Bananas are five times more common than other fruit
					// trees, so we increase the likelihood of all fruittrees by
					// 5 and then decrease it again
					if (tree != EnumFruitTree.BANANA && tree != EnumFruitTree.DATE)
					{
						if (random.nextInt(5) != 0)
						{
							continue;
						}

					}
					if (gen0 != null && shouldPlantGrow(EnumFruitTree.values()[i], rain, bioTemperature, evt, random)
					&& world.getBlock(xCoord, yCoord, zCoord).isReplaceable(world, xCoord, yCoord, zCoord))
					{
						if (gen0 != null)
						{
							success = gen0.generate(world, random, xCoord, yCoord, zCoord);
						}
					}
				}
			}
			catch (IndexOutOfBoundsException e)
			{
			}
		}

	}

	public static boolean shouldPlantGrow(EnumFruitTree plant, float rainfall, float temperatureAvg, float evt, Random rand)
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

	private void genBushes(Random random, int chunkX, int chunkZ, World world)
	{
		int xCoord;
		int yCoord;
		int zCoord;
		if (random.nextInt(12) == 0)
		{
			int region = TFC_Climate.getRegionLayer(world, chunkX, Global.SEALEVEL, chunkZ);
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			int[] validBerries = FloraManager.getInstance().berryREGIONS[region];
			switch (validBerries[random.nextInt(validBerries.length)])
			{
			default:
				wintergreenGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 1:
				blueberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 2:
				raspberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 3:
				strawberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 4:
				blackberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 5:
				bunchberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 6:
				cranberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 7:
				snowberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 8:
				elderberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 9:
				gooseberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 10:
				cloudberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			}
		}
	}
}
