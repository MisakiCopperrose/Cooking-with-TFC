package com.dunk.tfc.Handlers;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockLogNatural;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.dunk.tfc.Chunkdata.ChunkData;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.CropIndex;
import com.dunk.tfc.Food.CropManager;
import com.dunk.tfc.TileEntities.TEBeehive;
import com.dunk.tfc.WorldGen.WorldCacheManager;
import com.dunk.tfc.WorldGen.Generators.WorldGenGrowCrops;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Crafting.AnvilManager;
import com.dunk.tfc.api.Enums.EnumRegion;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ChunkEventHandler
{
	@SubscribeEvent
	public void onLoad(ChunkEvent.Load event)
	{
		if (!event.world.isRemote && TFC_Core.getCDM(event.world) != null && event.getChunk() != null)
		{
			ChunkData cd = TFC_Core.getCDM(event.world).getData(event.getChunk().xPosition, event.getChunk().zPosition);
			if (cd == null)
				return;
			BiomeGenBase biome = event.world.getBiomeGenForCoords(event.getChunk().xPosition,
					event.getChunk().zPosition);
			int month = TFC_Time.getSeasonAdjustedMonth(event.getChunk().zPosition << 4);

			// Make branches grow or lose leaves?
			Block b;
			/*int daysSinceLastVisit = (int) ((TFC_Time.getTotalTicks() - cd.lastVisited) / 24000);
			int maxY = 255;
			for (int i = 0; i < 16 && daysSinceLastVisit > 0; i++)
			{
				for (int k = 0; k < 16; k++)
				{

					for (int j = Global.SEALEVEL; j <= maxY; j++)
					{
						int x = event.getChunk().xPosition << 4 + i;
						int z = event.getChunk().zPosition << 4 + k;
						if (event.world.blockExists(x, j, z))
						{
						try{
							b = event.world.getBlock(x, j, z);
							if (b instanceof BlockBranch && ((BlockBranch) b).isEnd())
							{
								// Need to update the branch with the time
								// delta.
								// System.out.println("updating branch at " + x
								// +", " + j + ", " + z);
								//((BlockBranch) b).updateBranchTime(event.world, x, j, z, daysSinceLastVisit);
							}
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
						}
					}
				}
			}*/

			if (TFC_Time.getYear() > cd.lastSpringGen && month > 1 && month < 6)
			{
				int chunkX = event.getChunk().xPosition;
				int chunkZ = event.getChunk().zPosition;
				
				float bioTemperature = TFC_Climate.getBioTemperature(event.world, (chunkX << 4), (chunkZ << 4));
				int beeHivesPerChunk = 3;
				if (bioTemperature < 5)
				{
					
				}
				if (TFC_Core.isWaterBiome(biome))
				{
					cd.fishPop *= Math.pow(1.2, cd.lastSpringGen - TFC_Time.getYear());
					cd.fishPop = Math.min(cd.fishPop, ChunkData.FISH_POP_MAX);
				}
				cd.lastSpringGen = TFC_Time.getYear();

				Random rand = new Random(event.world.getSeed() + ((chunkX >> 3) - (chunkZ >> 3)) * (chunkZ >> 3));
				int region = TFC_Climate.getRegionLayer(event.world, chunkX, Global.SEALEVEL, chunkZ);
				CropIndex[] regionCrops = CropManager.getInstance().REGIONS.get(EnumRegion.values()[region]);
				int regionCropId = rand.nextInt(regionCrops.length);
				CropIndex crop = CropManager.getInstance().getCropFromId(regionCropId);
				if (event.world.rand.nextInt(15) == 0 && crop != null)
				{
					int num = 1 + event.world.rand.nextInt(5);
					WorldGenGrowCrops cropGen = new WorldGenGrowCrops(regionCropId);
					int x = (chunkX << 4) + event.world.rand.nextInt(16) + 8;
					int z = (chunkZ << 4) + event.world.rand.nextInt(16) + 8;
					cropGen.generate(event.world, event.world.rand, x, z, num);
				}
				
				boolean madeHive = rand.nextInt(20)>0;
				for (int i = 0; i < beeHivesPerChunk && !madeHive; i++)
				{
					int xCoord = (chunkX << 4) + rand.nextInt(16) + 8;
					int zCoord = (chunkZ << 4) + rand.nextInt(16) + 8;
					int yCoord = event.world.getTopSolidOrLiquidBlock(xCoord, zCoord);
					// We go upward until we find a suitable location.

					for (int yOffset = 0; yOffset > -12 && !madeHive && yCoord + yOffset > Global.SEALEVEL; yOffset--)
					{
						Block cur = event.world.getBlock(xCoord, yCoord + yOffset, zCoord);
						//System.out.println("cur ("+yOffset+"): " + cur.getLocalizedName());
						if ((cur.isReplaceable(event.world, xCoord, yCoord + yOffset, zCoord) || cur instanceof BlockCustomLeaves) && !(cur instanceof BlockLiquid))
						{
							// Test if this is suitable for a beehive
							Block above = event.world.getBlock(xCoord, yCoord + yOffset + 1, zCoord);
							if (above instanceof BlockBranch && !(
							((BlockBranch)above).getSourceY() == -1 
							&&((BlockBranch)above).getSourceX() == 0 
							&& ((BlockBranch)above).getSourceZ() == 0))
							{
								
								event.world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildBeehive);
								TEBeehive te = (TEBeehive) event.world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
								te.setInventorySlotContents(0, new ItemStack(TFCItems.fertileHoneycomb));
								madeHive = true;
							}
							else
							{
								Block adjacent = event.world.getBlock(xCoord -1, yCoord + yOffset, zCoord);
								if(adjacent instanceof BlockLogNatural && !madeHive && event.world.rand.nextInt(4)==0)
								{
									event.world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildTreehive,5,3);
									TEBeehive te = (TEBeehive) event.world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
									te.setInventorySlotContents(0, new ItemStack(TFCItems.fertileHoneycomb));
									madeHive = true;
								}
								adjacent = event.world.getBlock(xCoord +1, yCoord + yOffset, zCoord);
								if(adjacent instanceof BlockLogNatural && !madeHive && event.world.rand.nextInt(4)==0)
								{
									event.world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildTreehive,4,3);
									TEBeehive te = (TEBeehive) event.world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
									te.setInventorySlotContents(0, new ItemStack(TFCItems.fertileHoneycomb));
									madeHive = true;
								}
								adjacent = event.world.getBlock(xCoord, yCoord + yOffset, zCoord+1);
								if(adjacent instanceof BlockLogNatural && !madeHive && event.world.rand.nextInt(4)==0)
								{
									event.world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildTreehive,2,3);
									TEBeehive te = (TEBeehive) event.world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
									te.setInventorySlotContents(0, new ItemStack(TFCItems.fertileHoneycomb));
									madeHive = true;
								}
								adjacent = event.world.getBlock(xCoord, yCoord + yOffset, zCoord-1);
								if(adjacent instanceof BlockLogNatural && !madeHive && event.world.rand.nextInt(4)==0)
								{
									event.world.setBlock(xCoord, yCoord + yOffset, zCoord, TFCBlocks.wildTreehive,3,3);
									TEBeehive te = (TEBeehive) event.world.getTileEntity(xCoord, yCoord + yOffset, zCoord);
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
				
			}
			else if (TFC_Time.getYear() > cd.lastSpringGen && month >= 6)
			{
				// Replenish fish
				if (TFC_Core.isWaterBiome(biome))
				{
					cd.fishPop *= Math.pow(1.2, cd.lastSpringGen - TFC_Time.getYear());
					cd.fishPop = Math.min(cd.fishPop, ChunkData.FISH_POP_MAX);
				}
				cd.lastSpringGen = TFC_Time.getYear();
			}
			else if (TFC_Time.getYear() > cd.lastSpringGen + 1)
			{
				// Replenish fish
				if (TFC_Core.isWaterBiome(biome))
				{
					cd.fishPop *= Math.pow(1.2, cd.lastSpringGen - TFC_Time.getYear());
					cd.fishPop = Math.min(cd.fishPop, ChunkData.FISH_POP_MAX);
				}
				cd.lastSpringGen = TFC_Time.getYear();
			}
		}
		else if (TFC_Core.getCDM(event.world) != null && TFC_Climate.getCacheManager(event.world) != null)
		{
			Chunk chunk = event.getChunk();
			ChunkData data = new ChunkData(chunk).createNew(event.world, chunk.xPosition, chunk.zPosition);
			data.rainfallMap = TFC_Climate.getCacheManager(event.world).loadRainfallLayerGeneratorData(data.rainfallMap,
					event.getChunk().xPosition * 16, event.getChunk().zPosition * 16, 16, 16);
			TFC_Core.getCDM(event.world).addData(chunk, data);
		}
	}

	@SubscribeEvent
	public void onUnload(ChunkEvent.Unload event)
	{
		if (TFC_Core.getCDM(event.world) != null
				&& TFC_Core.getCDM(event.world).getData(event.getChunk().xPosition, event.getChunk().zPosition) != null)
			TFC_Core.getCDM(event.world).getData(event.getChunk().xPosition,
					event.getChunk().zPosition).isUnloaded = true;
	}

	@SubscribeEvent
	public void onUnloadWorld(WorldEvent.Unload event)
	{
		TFC_Climate.removeCacheManager(event.world);
		TFC_Core.removeCDM(event.world);
		if (event.world.provider.dimensionId == 0)
			AnvilManager.getInstance().clearRecipes();
	}

	@SubscribeEvent
	public void onLoadWorld(WorldEvent.Load event)
	{
		if (event.world.provider.dimensionId == 0 && event.world.getTotalWorldTime() < 100)
			createSpawn(event.world);
		if (!event.world.isRemote && event.world.provider.dimensionId == 0
				&& AnvilManager.getInstance().getRecipeList().size() == 0)
		{
			TFC_Core.setupWorld(event.world);
		}
		TFC_Climate.worldPair.put(event.world, new WorldCacheManager(event.world));
		TFC_Core.addCDM(event.world);
	}

	@SubscribeEvent
	public void onDataLoad(ChunkDataEvent.Load event)
	{
		if (!event.world.isRemote)
		{
			NBTTagCompound eventTag = event.getData();

			Chunk chunk = event.getChunk();
			if (eventTag.hasKey("ChunkData"))
			{
				NBTTagCompound spawnProtectionTag = eventTag.getCompoundTag("ChunkData");
				ChunkData data = new ChunkData(chunk, spawnProtectionTag);
				if (TFC_Core.getCDM(event.world) != null)
					TFC_Core.getCDM(event.world).addData(chunk, data);
			}
			else
			{
				/*
				 * if(TFC_Core.getCDM(event.world).hasData(event.getChunk()))
				 * return;
				 */
				NBTTagCompound levelTag = eventTag.getCompoundTag("Level");
				ChunkData data = new ChunkData(chunk).createNew(event.world, levelTag.getInteger("xPos"),
						levelTag.getInteger("zPos"));
				if (TFC_Core.getCDM(event.world) != null)
					TFC_Core.getCDM(event.world).addData(chunk, data);
			}
		}
	}

	@SubscribeEvent
	public void onDataSave(ChunkDataEvent.Save event)
	{
		if (!event.world.isRemote && TFC_Core.getCDM(event.world) != null)
		{
			NBTTagCompound levelTag = event.getData().getCompoundTag("Level");
			int x = levelTag.getInteger("xPos");
			int z = levelTag.getInteger("zPos");
			ChunkData data = TFC_Core.getCDM(event.world).getData(x, z);

			if (data != null)
			{
				NBTTagCompound spawnProtectionTag = data.getTag();
				// Why was this line here in the first place?
				// spawnProtectionTag = new NBTTagCompound();
				event.getData().setTag("ChunkData", spawnProtectionTag);
				if (data.isUnloaded)
					TFC_Core.getCDM(event.world).removeData(x, z);
			}
		}
	}

	private ChunkCoordinates createSpawn(World world)
	{
		List biomeList = world.getWorldChunkManager().getBiomesToSpawnIn();
		long seed = world.getWorldInfo().getSeed();
		Random rand = new Random(seed);

		ChunkPosition chunkCoord = null;
		int xOffset = 0;
		int xCoord = 0;
		// int yCoord = Global.SEALEVEL+1;
		int zCoord = 10000;
		boolean coldOrHot = rand.nextBoolean();
		int startingZ = coldOrHot ? 2000 + rand.nextInt(4000) : 12000 + rand.nextInt(5000);

		while (chunkCoord == null)
		{
			chunkCoord = world.getWorldChunkManager().findBiomePosition(xOffset, -startingZ, 64, biomeList, rand);
			if (chunkCoord != null)
			{
				xCoord = chunkCoord.chunkPosX;
				zCoord = chunkCoord.chunkPosZ;
			}
			else
			{
				xOffset += 64;
				// TerraFirmaCraft.log.warn("Unable to find spawn biome");
			}
		}

		int var9 = 0;
		while (!world.provider.canCoordinateBeSpawn(xCoord, zCoord))
		{
			xCoord += rand.nextInt(16) - rand.nextInt(16);
			zCoord += rand.nextInt(16) - rand.nextInt(16);
			++var9;
			if (var9 == 1000)
				break;
		}

		WorldInfo info = world.getWorldInfo();
		info.setSpawnPosition(xCoord, world.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
		if (!info.getNBTTagCompound().hasKey("superseed"))
			info.getNBTTagCompound().setLong("superseed", System.currentTimeMillis());
		return new ChunkCoordinates(xCoord, world.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
	}
}
