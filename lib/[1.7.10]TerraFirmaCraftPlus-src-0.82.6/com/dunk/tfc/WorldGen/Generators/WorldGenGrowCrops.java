package com.dunk.tfc.WorldGen.Generators;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.CropIndex;
import com.dunk.tfc.Food.CropManager;
import com.dunk.tfc.TileEntities.TECrop;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumRegion;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGenGrowCrops implements IWorldGenerator
{
	/** The ID of the plant block used in this plant generator. */
	private final int cropBlockId;
	private int region;

	public WorldGenGrowCrops(int par1)
	{
		this.cropBlockId = par1;
	}

	public void generate(World world, Random rand, int x, int z, int numToGen)
	{
		int i = x, j = 150, k = z;
		CropIndex crop;
		
		TECrop te;
		crop = CropManager.getInstance().getCropFromId(cropBlockId);
		region = TFC_Climate.getRegionLayer(world, x, Global.SEALEVEL, z);
		boolean validRegion = false;
		EnumRegion currentRegion = EnumRegion.values()[region];
		for(int i2 = 0; i2 < crop.regions.length && !validRegion; i2++)
		{
			validRegion |= crop.regions[i2]==currentRegion;
		}
		if(!validRegion)
		{
			return;
		}
		for(int c = 0; c < numToGen; c++)
		{
			i = x - 8 + rand.nextInt(16);
			k = z - 8 + rand.nextInt(16);
			j = world.getTopSolidOrLiquidBlock(i, k);
			crop = CropManager.getInstance().getCropFromId(cropBlockId);
			float bioTemp = TFC_Climate.getBioTemperatureHeight(world, i, j, k);
			float rainfall = TFC_Climate.getRainfall(world, i,j,k);
			if(crop != null)
			{
				float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(world, TFC_Time.getTotalDays(), i, j, k);
				int month = TFC_Time.getSeasonAdjustedMonth(k);

				if(temp > crop.minAliveTemp && month > 0 && month <= 6 && rainfall >= crop.minimumNaturalRain && rainfall <= crop.maximumNaturalRain && bioTemp >= crop.minimumNaturalBioTemp && bioTemp <= crop.maximumNaturalBioTemp)
				{
					Block b = world.getBlock(i, j, k);
					if (TFCBlocks.crops.canBlockStay(world, i, j, k) && (b.isAir(world, i, j, k) || b == TFCBlocks.tallGrass))
					{
						if(world.setBlock(i, j, k, TFCBlocks.crops, 0, 0x2))
						{
							te = (TECrop)world.getTileEntity(i, j, k);
							if(te != null)
							{
								//System.out.println(crop.cropName +" " + x + ", " + j + ", " + z);
								te.cropId = cropBlockId;
								float gt = Math.max(crop.growthTime / TFC_Time.daysInMonth, 0.01f);
								float mg = Math.min(month / gt, 1.0f) * (0.75f + (rand.nextFloat() * 0.25f));
								float growth = Math.min(crop.numGrowthStages * mg, crop.numGrowthStages);
								te.growth = growth;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void generate(Random par2Random, int x, int z, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
	}

}
