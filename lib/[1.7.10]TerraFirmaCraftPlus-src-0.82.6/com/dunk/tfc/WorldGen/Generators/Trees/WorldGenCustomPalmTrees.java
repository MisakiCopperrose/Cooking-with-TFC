package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomPalmTrees extends WorldGenerator
{

	private final int treeId;

	public WorldGenCustomPalmTrees(boolean flag, int id)
	{
		super(flag);
		treeId = id;
	}

	@Override
	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{

		int h = 0;//random.nextInt(4) + 2; // Height before lean
		int h2 = random.nextInt(3) + (4 - h); // Height during lean
		int h3 = random.nextInt(3) + (10 - h - h2); // Height after lean

		int xD, zD;
		int totalBlocks = 0;
		xD = 0;
		zD = 0;
		boolean finished = false;
		// Instead, we'll look for water
		for (int i = -5; i <= 5 && !finished; i++)
		{
			for (int j = -3; j <= 0 && !finished; j++)
			{
				for (int k = -5; k <= 5 && !finished; k++)
				{
					if((i+xCoord)/16 != xCoord/16 || (k+zCoord)/16 != zCoord/16)
					{
						continue;
					}
					if (world.blockExists(i + xCoord, j + yCoord, k + zCoord) && TFC_Core.isSaltWater(world.getBlock(i + xCoord, j + yCoord, k + zCoord)))
					{
						xD += i;
						zD += k;
						totalBlocks++;
						//finished = true;
					}
				}
			}
		}
		xD= Math.round((float)xD/(float)totalBlocks);
		zD=Math.round((float)zD/(float)totalBlocks);
		
		int leanX = 0;// random.nextInt(3)-1;
		int leanZ = 0;// random.nextInt(3)-1;
		if (xD != 0 && zD != 0)
		{
			if (xD != 0)
			{
				double d = Math.abs(zD)/Math.abs(xD);
				if(d >= 0.4142 && d <= 2.4142)
				{
					
					leanZ = zD / Math.abs(zD);
					
					leanX = xD / Math.abs(xD);
				}
				else if(d < 0.4142)
				{
					leanX = xD / Math.abs(xD);
					leanZ = 0;
				}
				else if(d > 2.4142)
				{
					leanX =0;
					leanZ = zD / Math.abs(zD);
				}
			}
			else
			{
				leanX = 0;
				leanZ = zD / Math.abs(zD);
			}
		}
		if (yCoord < 1 || yCoord + h + 1 > world.getHeight())
			return false;
		
		boolean flag = true;
		for (int i1 = yCoord; i1 <= yCoord + 1 + h; i1++)
		{
			byte byte0 = 1;
			if (i1 == yCoord)
				byte0 = 0;
			if (i1 >= yCoord + 1 + h - 2)
				byte0 = 2;

			for (int i2 = xCoord - byte0; i2 <= xCoord + byte0 && flag; i2++)
			{
				for (int l2 = zCoord - byte0; l2 <= zCoord + byte0 && flag; l2++)
				{
					if (i1 >= 0 && i1 < world.getHeight() && world.blockExists(i2, i1, l2))
					{
						Block j3 = world.getBlock(i2, i1, l2);
						if (!j3.isAir(world, i2, i1, l2) && !j3.canBeReplacedByLeaves(world, i2, i1, l2))
							flag = false;
					}
					else
					{
						flag = false;
					}
				}
			}
		}

		if (!flag)
			return false;

		boolean growOnSand = true;

		if ((!(TFC_Core.isSand(world.getBlock(xCoord, yCoord - 1, zCoord)))
				&& !(TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord))))
				|| yCoord >= world.getHeight() - h - 1)
			return false;
		if (TFC_Core.isSand(world.getBlock(xCoord, yCoord - 1, zCoord)))
		{
			growOnSand = true;
			if(leanX == leanZ && leanX == 0)
			{
				h= 3;
				h2+=random.nextInt(8);
			}
		}
		else if (TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord)))
		{
			growOnSand = false;
			leanX = 0;
			leanZ = 0;
			h= 3;
			h2+=random.nextInt(12);
		}

		Block initialBlock = TFCBlocks.branch2__y_;
		Block leaningBlock = TFC_Core.branchMap[2 - (leanX + 1)][0][2 - (leanZ + 1)][1];
		Block leanedBlock = TFC_Core.branchMap[2 - (leanX + 1)][1][2 - (leanZ + 1)][1];
		Block leanedBlockEnd = TFC_Core.branchMap[1][0][1][3];
		if (leanedBlockEnd == null)
		{
			leanedBlockEnd = TFCBlocks.branchEnd2__y_;
		}
		if (leanedBlock == null)
		{
			leanedBlock = initialBlock;
		}
		//Check that our path is clear
		for (int y = 0; y <= h && h>0; y++)
		{
			if(!world.getBlock(xCoord + y * leanX, yCoord + y + h, zCoord + y * leanZ).isReplaceable(world,  xCoord + y * leanX, yCoord + y + h, zCoord + y * leanZ))
			{
				return false;
			}
		}
		for (int y = h>0?1:0; y < h2; y++)
		{
			if(!world.getBlock( xCoord + y * leanX, yCoord + y + h, zCoord + y * leanZ).isReplaceable
					(world,   xCoord + y * leanX, yCoord + y + h, zCoord + y * leanZ))
			{
				return false;
			}
		}
		for (int y = 0; y < h3; y++)
		{
			if(!world.getBlock( xCoord + (h2 * leanX) + y * leanX, yCoord + h2 + h - 1,
					zCoord + (h2 * leanZ) + y * leanZ).isReplaceable
					(world,  xCoord + (h2 * leanX) + y * leanX, yCoord + h2 + h - 1,
							zCoord + (h2 * leanZ) + y * leanZ))
			{
				return false;
			}
		}
		if(!world.getBlock(xCoord + (h2 + h3) * leanX, yCoord + h2 + h, zCoord + (h2 + h3) * leanZ)
				.isReplaceable(world, xCoord + (h2 + h3) * leanX, yCoord + h2 + h, zCoord + (h2 + h3) * leanZ)
				||
				!world.getBlock( xCoord + (h2 + h3) * leanX, yCoord + h2 + h + 1, zCoord + (h2 + h3) * leanZ)
				.isReplaceable(world,  xCoord + (h2 + h3) * leanX, yCoord + h2 + h + 1, zCoord + (h2 + h3) * leanZ)
				)
		{
			return false;
		}
		for (int y = 0; y <= h && h>0; y++)
		{
			setBlockAndNotifyAdequately(world, xCoord, yCoord + y, zCoord, initialBlock, treeId);
		}
		for (int y = h>0?1:0; y < h2; y++)
		{
			setBlockAndNotifyAdequately(world, xCoord + y * leanX, yCoord + y + h, zCoord + y * leanZ,
					leaningBlock, treeId);
		}
		for (int y = 0; y < h3; y++)
		{
			setBlockAndNotifyAdequately(world, xCoord + (h2 * leanX) + y * leanX, yCoord + h2 + h - 1,
					zCoord + (h2 * leanZ) + y * leanZ, leanedBlock, treeId);
		}
		setBlockAndNotifyAdequately(world, xCoord + (h2 + h3) * leanX, yCoord + h2 + h, zCoord + (h2 + h3) * leanZ,
				leaningBlock, treeId);
		setBlockAndNotifyAdequately(world, xCoord + (h2 + h3) * leanX, yCoord + h2 + h + 1, zCoord + (h2 + h3) * leanZ,
				leanedBlockEnd, treeId);
		return true;
	}
}
