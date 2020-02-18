package com.dunk.tfc.WorldGen.Generators;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.WorldGen.DataLayer;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGenLargeRock implements IWorldGenerator
{
	private int xWidth;
	private int xWidth2;
	private int zWidth;
	private int zWidth2;
	private int height;
	private int height2;
	private float rainfall;

	public WorldGenLargeRock()
	{
	}

	public boolean generate(World world, Random rand, int i, int j, int k)
	{
		int yOffset = 0;
		boolean isFlatEnough = false;

		for (; yOffset > -2 && !isFlatEnough; yOffset--)
		{
			if (world.getBlock(i, j + yOffset, k).isNormalCube())
			{
				if (world.getBlock(i + 1, j + yOffset, k).isNormalCube()
						&& world.getBlock(i - 1, j + yOffset, k).isNormalCube()
						&& world.getBlock(i - 1, j + yOffset, k).isNormalCube()
						&& world.getBlock(i, j + yOffset, k + 1).isNormalCube())
					isFlatEnough = true;
			}
		}
		rainfall = TFC_Climate.getRainfall(world, i, j, k);
		rainfall *= getNearWater(world, i, j, k) ? 2 : 1;
		//rainfall = 0;
		if (j <= 155)
		{
			int i2, j2, k2;
			i2 = i + (rand.nextInt(2) + 1) * (rand.nextBoolean() ? 1 : -1);
			j2 = j + (rand.nextInt(2) + 1) * (rand.nextBoolean() ? 1 : -1);
			k2 = k + (rand.nextInt(2) + 1) * (rand.nextBoolean() ? 1 : -1);
			genFromPoint(world, rand, i, j, k, yOffset);
			genFromPoint(world, rand, i2, j2, k2, yOffset);
		}
		return true;
	}

	public void genFromPoint(World world, Random rand, int i, int j, int k, int yOffset)
	{
		DataLayer rockLayer2 = TFC_Climate.getCacheManager(world).getRockLayerAt(i, k, 1);
		Vec3 center = Vec3.createVectorHelper(i, j + yOffset, k);
		/*
		 * xWidth = 3; xWidth2 = 3; zWidth = 3; zWidth2 = 3;
		 */
		int xW = rand.nextInt(2)+1;
		int yW = rand.nextInt(2)+1;
		xWidth = rand.nextInt(2) + xW;
		xWidth2 = rand.nextInt(2) + xW;
		zWidth = rand.nextInt(2) + yW;
		zWidth2 = rand.nextInt(2) + yW;
		
		height = rand.nextInt(3) + 1;
		height2 = rand.nextInt(3) + 1;
		
		// Initialize to a high value so that if it isn't calculated for some
		// reason, it won't give a false positive
		double adj_x = 1000, adj_y = 1000, adj_X = 1000, adj_z = 1000, adj_Z = 1000;
		for (int xCoord = i - xWidth-1; xCoord <= i + xWidth2 + 1; ++xCoord)
		{
			for (int zCoord = k - zWidth -1; zCoord <= k + zWidth2 +1; ++zCoord)
			{
				for (int yCoord = j + yOffset - height2 ; yCoord <= j + yOffset + height + 1; ++yCoord)
				{
					Vec3 point = Vec3.createVectorHelper(xCoord, yCoord, zCoord);
					double distance = center.squareDistanceTo(point);
					boolean canPlaceX = true;
					boolean canPlaceZ = true;
					boolean canMossX = false;
					boolean canMossZ = false;
					if ((xCoord < i && distance >= (xWidth * xWidth + (yCoord < j +yOffset? height2 * height2:height*height) + (zCoord < k? zWidth * zWidth:zWidth2*zWidth2))) || (xCoord > i && distance >= xWidth2 * xWidth2 + (yCoord < j +yOffset? height2 * height2:height*height) +  (zCoord < k? zWidth * zWidth:zWidth2*zWidth2)) || yCoord > j + yOffset + height)
					{
						canPlaceX = false;

						if (rand.nextInt(25) < (rainfall / 100))
						{
							point = Vec3.createVectorHelper(xCoord - 1, yCoord, zCoord);
							adj_x = center.squareDistanceTo(point);
							point = Vec3.createVectorHelper(xCoord + 1, yCoord, zCoord);
							adj_X = center.squareDistanceTo(point);
							point = Vec3.createVectorHelper(xCoord, yCoord - 1, zCoord);
							adj_y = center.squareDistanceTo(point);
							point = Vec3.createVectorHelper(xCoord, yCoord, zCoord - 1);
							adj_z = center.squareDistanceTo(point);
							point = Vec3.createVectorHelper(xCoord, yCoord, zCoord + 1);
							adj_Z = center.squareDistanceTo(point);

							if (((xCoord < i && (adj_x < xWidth * xWidth || adj_X < xWidth * xWidth
									|| adj_y < xWidth * xWidth || adj_z < xWidth * xWidth || adj_Z < xWidth * xWidth))
									|| (xCoord > i && (adj_x < xWidth2 * xWidth2 || adj_X < xWidth2 * xWidth2
											|| adj_y < xWidth2 * xWidth2 || adj_z < xWidth2 * xWidth2
											|| adj_Z < xWidth2 * xWidth2))))
							{
								canMossX = true;
							}
						}
					}
					if ((zCoord < k && distance >= zWidth * zWidth + (yCoord < j +yOffset? height2 * height2:height*height) + (xCoord < i? xWidth * xWidth:xWidth2*xWidth2)) || (zCoord > k && distance >= zWidth2 * zWidth2 + (yCoord < j +yOffset? height2 * height2:height*height) + (xCoord < i? xWidth * xWidth:xWidth2*xWidth2)))
					{
						canPlaceZ = false;
						// No point in doing these calculations if we can't put
						// moss here anyway
						if (canMossX)
						{
							// No need to do rainfall calculations since they
							// were required for canMossX
							if (((zCoord < k && (adj_x < zWidth * zWidth || adj_X < zWidth * zWidth
									|| adj_y < zWidth * zWidth || adj_z < zWidth * zWidth || adj_Z < zWidth * zWidth))
									|| (zCoord > k && (adj_x < zWidth2 * zWidth2 || adj_X < zWidth2 * zWidth2
											|| adj_y < zWidth2 * zWidth2 || adj_z < zWidth2 * zWidth2
											|| adj_Z < zWidth2 * zWidth2))))
							{
								canMossZ = true;
							}
						}
					}
					if (canPlaceX && canPlaceZ)
					{
						world.setBlock(xCoord, yCoord, zCoord, rockLayer2.block, rockLayer2.data2, 0x2);
					}
					else if ((canMossX && canMossZ) && world.isAirBlock(xCoord, yCoord, zCoord) && rand.nextBoolean())
					{
						world.setBlock(xCoord, yCoord, zCoord, TFCBlocks.moss, 0, 0x2);
					}
				}
			}
		}
	}

	public boolean getNearWater(World world, int x, int y, int z)
	{
		for (int x1 = -4; x1 < 5; ++x1)
		{
			for (int z1 = -4; z1 < 5; ++z1)
			{
				for (int y1 = -2; y1 < 1; ++y1)
				{
					if (world.blockExists(x + x1, y + y1, z + z1)
							&& TFC_Core.isWater(world.getBlock(x + x1, y + y1, z + z1)))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider)
	{
		chunkX *= 16;
		chunkZ *= 16;
		int xCoord = 0;
		int zCoord = 0;

		for (int count = 0; count < 1; ++count)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			int yCoord = world.getHeightValue(xCoord, zCoord) - 1;
			xWidth = 2 + random.nextInt(6);
			xWidth2 = 2 + random.nextInt(6);
			zWidth = 2 + random.nextInt(6);
			zWidth2 = 2 + random.nextInt(6);
			if (random.nextInt(20) == 0 && TFC_Core.isSoil(world.getBlock(xCoord, yCoord, zCoord)))
				generate(world, random, xCoord, world.getTopSolidOrLiquidBlock(xCoord, zCoord) - 1, zCoord);
		}
	}
}
