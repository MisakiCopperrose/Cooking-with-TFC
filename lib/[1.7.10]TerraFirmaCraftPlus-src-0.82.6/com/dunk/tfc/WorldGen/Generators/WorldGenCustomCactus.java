package com.dunk.tfc.WorldGen.Generators;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumRegion;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomCactus extends WorldGenerator
{
	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		for (int var6 = 0; var6 < random.nextInt(4)+1; ++var6)
		{
			int xCoord = i + random.nextInt(8) - random.nextInt(8);
			int yCoord = j + random.nextInt(4) - random.nextInt(4);
			int zCoord = k + random.nextInt(8) - random.nextInt(8);
			
			int region = TFC_Climate.getRegionLayer(world, i, Global.SEALEVEL, k);
			if(EnumRegion.values()[region] != EnumRegion.AMERICAS)
			{
				return false;
			}
			if (world.isAirBlock(xCoord, yCoord, zCoord) && !TFC_Core.isBeachBiome(world.getBiomeGenForCoords(xCoord, zCoord).biomeID))
			{
				int var10 = 1 + random.nextInt(random.nextInt(3) + 1);
				for (int var11 = 0; var11 < var10; ++var11)
				{
					if (TFC_Core.isSand(world.getBlock(xCoord, yCoord - 1, zCoord)) || TFCBlocks.cactus.canBlockStay(world, xCoord, yCoord + var11, zCoord))
						world.setBlock(xCoord, yCoord + var11, zCoord, TFCBlocks.cactus, 0, 0x2);
				}
			}
		}
		return true;
	}
}
