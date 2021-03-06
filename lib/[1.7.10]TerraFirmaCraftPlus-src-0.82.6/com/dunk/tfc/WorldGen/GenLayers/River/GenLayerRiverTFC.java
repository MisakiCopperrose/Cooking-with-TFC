package com.dunk.tfc.WorldGen.GenLayers.River;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.WorldGen.TFCProvider;
import com.dunk.tfc.WorldGen.TFCWorldChunkManager;
import com.dunk.tfc.WorldGen.GenLayers.GenLayerTFC;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerRiverTFC extends GenLayerTFC
{
	public GenLayerRiverTFC(long par1, GenLayer par3GenLayer)
	{
		super(par1);
		super.parent = (GenLayerTFC) par3GenLayer;
	}

	/**
	 * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
	 * amounts, or biomeList[] indices based on the particular GenLayer subclass.
	 */
	@Override
	public int[] getInts(int xCoord, int zCoord, int xSize, int zSize)
	{
		//float rainfall = this.
		World world = null;//MinecraftServer.getServer().getEntityWorld();
		float rainfall = 0f;//TFC_Climate.getRainfall(world,xCoord<<2,Global.SEALEVEL,zCoord<<2);
		if(MinecraftServer.getServer() != null && MinecraftServer.getServer().worldServers.length > 0)
		{
			world = MinecraftServer.getServer().getEntityWorld();
			rainfall = TFC_Climate.getRainfall(world,xCoord<<2,Global.SEALEVEL,zCoord<<2);
		}
		rainfall += 800;
		int areaRadius = Math.max(Math.min((int)(rainfall / 800), 5), 2);
		int parentXCoord = xCoord - areaRadius;
		int parentZCoord = zCoord - areaRadius;
		int parentXSize = xSize + (2*areaRadius);
		int parentZSize = zSize + (2*areaRadius);
		int[] parentCache = this.parent.getInts(parentXCoord, parentZCoord, parentXSize, parentZSize);
		int[] outCache = new int[(xSize+0) * (zSize+0)];
		
		
		for (int z = 0; z < zSize + 0; ++z)
		{
			for (int x = 0; x < xSize + 0; ++x)
			{
				/*
				int xMinus = this.calcWidth(parentCache[x + 0 + (z + (1*areaRadius)) * parentXSize]);
				int xPlus = this.calcWidth(parentCache[x + (2*areaRadius) + (z + (1*areaRadius)) * parentXSize]);
				int zMinus = this.calcWidth(parentCache[x + (1*areaRadius) + (z + 0) * parentXSize]);
				int zPlus = this.calcWidth(parentCache[x + (1*areaRadius) + (z + (2*areaRadius)) * parentXSize]);
				int c = this.calcWidth(parentCache[x + (1*areaRadius) + (z + (1*areaRadius)) * parentXSize]);*/
				int[][]areas = new int[(areaRadius*2)+1][(areaRadius*2)+1];
				boolean same = true;
				boolean initialVal = false;
				int initialValue = -1;
				for(int rX = 0; rX < (areaRadius*2)+1 && same;rX++)
				{
					for(int rZ = 0; rZ < (areaRadius*2)+1 && same;rZ++)
					{
						if(Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= areaRadius)
						{
							if(initialVal == false)
							{
								initialValue = this.calcWidth(parentCache[x+rX + (z + rZ)*parentXSize]);
								initialVal = true;
								continue;
							}
							same = this.calcWidth(parentCache[x+rX + (z + rZ)*parentXSize]) == initialValue;
						}
					}
				}

				if (/*c == xMinus && c == zMinus && c == xPlus && c == zPlus*/ same)
				{
					outCache[x + z * xSize] = 0;
				}
				else
				{
					outCache[x + z * xSize] = TFCBiome.RIVER.biomeID;
				}
			}
		}

		return outCache;
	}

	private int calcWidth(int i)
	{
		return  i >= 2 ? 2 + (i & 1) : i; // Spits back 2 for even numbers >= 2 and 3 for odd numbers.
	}
}
