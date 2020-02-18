package com.dunk.tfc.WorldGen.GenLayers.Biome;

import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.WorldGen.GenLayers.GenLayerTFC;

import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDeepOcean extends GenLayerTFC
{
	public GenLayerDeepOcean(long seed, GenLayerTFC parent)
	{
		super(seed);
		this.parent = parent;
	}

	/**
	 * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
	 * amounts, or biomeList[] indices based on the particular GenLayer subclass.
	 */
	@Override
	public int[] getInts(int parX, int parZ, int parXSize, int parZSize)
	{
		int xSize = parXSize + 2;
		int zSize = parZSize + 2;
		int thisID;
		int[] parentIDs = this.parent.getInts(parX - 1, parZ - 1, xSize, zSize);
		validateIntArray(parentIDs, xSize, zSize);
		int[] outCache = IntCache.getIntCache(parXSize * parZSize);

		for (int z = 0; z < parZSize; ++z)
		{
			for (int x = 0; x < parXSize; ++x)
			{
				int northID = parentIDs[x + 1 + z * xSize];
				int rightID = parentIDs[x + 2 + (z + 1) * xSize];
				int leftID = parentIDs[x + (z + 1) * xSize];
				int southID = parentIDs[x + 1 + (z + 2) * xSize];
				thisID = parentIDs[x + 1 + (z + 1) * xSize];
				int oceanCount = 0;
				int outIndex = x + z * parXSize;

				if (northID == 0)
				{
					++oceanCount;
				}

				if (rightID == 0)
				{
					++oceanCount;
				}

				if (leftID == 0)
				{
					++oceanCount;
				}

				if (southID == 0)
				{
					++oceanCount;
				}

				if (thisID == 0 && oceanCount > 3)
				{
					outCache[outIndex] = TFCBiome.DEEP_OCEAN.biomeID;
				}
				else
				{
					outCache[outIndex] = thisID;
				}
			}
		}

		return outCache;
	}
}
