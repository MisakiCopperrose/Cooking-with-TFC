package com.dunk.tfc.WorldGen.GenLayers.Mountains;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.WorldGen.GenLayers.GenLayerTFC;

import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerMountainsInitTFC extends GenLayerTFC
{
	public GenLayerMountainsInitTFC(long par1, GenLayer par3GenLayer)
	{
		super(par1);
		this.parent = (GenLayerTFC) par3GenLayer;
	}

	/**
	 * Creates the random width of the river at the location
	 */
	@Override
	public int[] getInts(int xCoord, int zCoord, int xSize, int zSize)
	{
		int areaRadius = 14;
		int peakRadius = 2;
		int rangeRadius = 9;
		int parentXCoord = xCoord - areaRadius;
		int parentZCoord = zCoord - areaRadius;
		int parentXSize = xSize + (2*areaRadius);
		int parentZSize = zSize + (2*areaRadius);
		
		int[] parentCache = this.parent.getInts(parentXCoord, parentZCoord, parentXSize, parentZSize);
		int[] outCache = new int[xSize * zSize];
		
		for (int z = 0; z < zSize; ++z)
		{
			for (int x = 0; x < xSize; ++x)
			{
				
				this.initChunkSeed(x + xCoord, z + zCoord);
				
				boolean legal1 = true;
				boolean legal2 = true;
				boolean legal3 = true;
				
				for(int rX = 0; rX < (areaRadius*2)+1 && (legal1||legal2||legal3) ;rX++)
				{
					for(int rZ = 0; rZ < (areaRadius*2)+1 && (legal1||legal2||legal3);rZ++)
					{
						int id = parentCache[x+rX + (z + rZ)*parentXSize];
						if(Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= areaRadius && legal1)
						{
							
								legal1 = !TFC_Core.isOceanicBiome(id) && !TFC_Core.isBeachBiome(id) && id != TFCBiome.LAKE.biomeID && id != TFCBiome.RIVER.biomeID;
							
						}
						if(Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= peakRadius && legal2)
						{
							
								legal2 = !TFC_Core.isOceanicBiome(id) && !TFC_Core.isBeachBiome(id) && id != TFCBiome.LAKE.biomeID && id != TFCBiome.RIVER.biomeID;
							
						}
						if(Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= rangeRadius && legal3)
						{
							
								legal3 = !TFC_Core.isOceanicBiome(id) && !TFC_Core.isBeachBiome(id) && id != TFCBiome.LAKE.biomeID && id != TFCBiome.RIVER.biomeID;
							
						}
					/*	
						if(Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= areaRadius)
						{
							int id = parentCache[x+rX + (z + rZ)*parentXSize];
							legal = !TFC_Core.isOceanicBiome(id) && !TFC_Core.isBeachBiome(id) && id != TFCBiome.LAKE.biomeID && id != TFCBiome.RIVER.biomeID;
						}*/
					}
				}
				int index = x + z * xSize;
				int id = parentCache[index];
				//outCache[index] = legal?parentCache[areaRadius + areaRadius * parentXSize]:0;
				/*int index = x + z * xSize;
				//int xn = index-1;
				//int xp = index+1;
				//int zn = index-zSize;
				//int zp = index+zSize;
				int id = parentCache[index];*/
				if(legal2)
				{
					outCache[index] = 3;
				}
				else if(legal3)
				{
					outCache[index] = 2;
				}
				else if(legal1)
				{
					outCache[index] = 1;
				}
				else
				{
					outCache[index] = 0;
				}
				
			}
		}
		return outCache;
	}
}
