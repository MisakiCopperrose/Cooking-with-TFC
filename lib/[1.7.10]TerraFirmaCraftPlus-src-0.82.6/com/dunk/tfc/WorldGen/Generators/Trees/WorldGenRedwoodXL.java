/**
 * Copyright (c) Scott Killen and MisterFiber, 2012
 */
package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenRedwoodXL extends WorldGenTreeBase
{
	private int height;

	public WorldGenRedwoodXL(boolean doNotify, int id, boolean sap)
	{
		super(doNotify, id, sap);
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int distFromEdge = 5;
		// We don't want to generate too close to chunk boundaries, because that
		// gets out of hand
		if ((x % 16 > 16 - distFromEdge || Math.abs(
				x) % 16 < distFromEdge || z % 16 > 16 - distFromEdge || Math.abs(z) % 16 < distFromEdge)&&!fromSapling)
		{
			return false;
		}
		height = rand.nextInt(20) + 22;

		if (y < 1 || y + height + 1 > 256)
			return false;

		if (!TFC_Core.isSoil(world.getBlock(x, y - 1, z)) || !TFC_Core.isSoil(world.getBlock(x - 1, y - 1, z))
				|| !TFC_Core.isSoil(world.getBlock(x, y - 1, z - 1))
				|| !TFC_Core.isSoil(world.getBlock(x - 1, y - 1, z - 1)) || y >= 180
				|| !world.getBlock(x - 1, y, z).isReplaceable(world, x - 1, y, z)
				|| !world.getBlock(x - 1, y, z - 1).isReplaceable(world, x - 1, y, z - 1)
				|| !world.getBlock(x, y, z - 1).isReplaceable(world, x, y, z - 1)
				|| !world.getBlock(x, y, z).isReplaceable(world, x, y, z))
		{
			return false;
		}

		int c = 0;
		for (int j = y; j <= y + 1 + height; j++)
		{
			c++;
			for(int ii = x-c>(height/3)?4:2; ii < x+ (c>(height/3)?4:1); ii++)
			{
				for(int kk = z-c>(height/3)?4:2; kk < z+(c>(height/3)?4:1); kk++)
				{
					if(!world.getBlock(ii, j, kk).isReplaceable(world,ii, j, kk))
					{
						return false;
					}
				}
			}
		}

		Block block = world.getBlock(x, y - 1, z);
		Block soil = null;
		int soilMeta = 0;

		if (TFC_Core.isGrass(block))
		{
			soil = TFC_Core.getTypeForSoil(block);
			soilMeta = world.getBlockMetadata(x, y - 1, z);
			world.setBlock(x, y - 1, z, soil, soilMeta, 2);
		}

		block = world.getBlock(x - 1, y - 1, z);
		if (TFC_Core.isGrass(block))
		{
			soil = TFC_Core.getTypeForSoil(block);
			soilMeta = world.getBlockMetadata(x - 1, y - 1, z);
			world.setBlock(x - 1, y - 1, z, soil, soilMeta, 2);
		}

		block = world.getBlock(x, y - 1, z - 1);
		if (TFC_Core.isGrass(block))
		{
			soil = TFC_Core.getTypeForSoil(block);
			soilMeta = world.getBlockMetadata(x, y - 1, z - 1);
			world.setBlock(x, y - 1, z - 1, soil, soilMeta, 2);
		}

		block = world.getBlock(x - 1, y - 1, z - 1);
		if (TFC_Core.isGrass(block))
		{
			soil = TFC_Core.getTypeForSoil(block);
			soilMeta = world.getBlockMetadata(x - 1, y - 1, z - 1);
			world.setBlock(x - 1, y - 1, z - 1, soil, soilMeta, 2);
		}

		int l1 = rand.nextInt(2);
		int j2 = 1;
		boolean flag1 = false;

		float xMid, zMid;
		xMid = x - 0.5f;
		zMid = z - 0.5f;

		int branchPattern = rand.nextInt(8);
		for (int i = 0; i < height; i++)
		{
			if (i > height / 3)
			{
				int numBranchesHere = rand.nextInt(3) + 4;
				int extraBranchLength = i < ((7f * height) / 10f) ? 3 : 1;

				for (int j = 0; j < numBranchesHere; j++)
				{
					// We have to pick which block we're going to generate on
					// We know that if we're on a corner, we want to be on the
					// corner
					// But if we're going out straight, we have two choices
					// Therefore, we set our mid point and either round up or
					// down
					int[] curDir = initialDirections[(branchPattern + j) % 8];
					// Break-down: if curDir is -1 or 1, localX will be
					// guaranteed to be on the correct block
					// If curDir is 0, it wouldn't matter. In that case, the
					// boolean will randomly pick one or the other.
					int localX = (int) Math.round(xMid + (curDir[0] * 0.5f) + (rand.nextBoolean() ? -0.1f : 0.1f));
					int localZ = (int) Math.round(zMid + (curDir[2] * 0.5f) + (rand.nextBoolean() ? -0.1f : 0.1f));
					generateRandomBranches(world, rand, localX, y + i, localZ, curDir, 1,
							rand.nextInt(2) + extraBranchLength);

				}

				branchPattern += numBranchesHere;
				branchPattern %= 8;
			}

			setBlockAndNotifyAdequately(world, x, y + i, z, block2 ? TFCBlocks.logNatural2 : TFCBlocks.logNatural,
					treeId);
			setBlockAndNotifyAdequately(world, x - 1, y + i, z, block2 ? TFCBlocks.logNatural2 : TFCBlocks.logNatural,
					treeId);
			setBlockAndNotifyAdequately(world, x, y + i, z - 1, block2 ? TFCBlocks.logNatural2 : TFCBlocks.logNatural,
					treeId);
			setBlockAndNotifyAdequately(world, x - 1, y + i, z - 1,
					block2 ? TFCBlocks.logNatural2 : TFCBlocks.logNatural, treeId);
		}
		fillInLeaves(world, rand, x, y + height, z, true);
		fillInLeaves(world, rand, x - 1, y + height, z, true);
		fillInLeaves(world, rand, x, y + height, z - 1, true);
		fillInLeaves(world, rand, x - 1, y + height, z - 1, true);
		return true;
	}

	protected boolean fillInLeaves(World world, Random rand, int xCoord, int yCoord, int zCoord, boolean high)
	{
		for (int i = -1; i < 2; i++)
		{
			for (int i2 = 0; i2 < 2; i2++)
			{
				for (int j = -1; j < 2; j++)
				{
					if (i2 > 0 && ((i != 0 || j != 0) || !high))
					{
						continue;
					}
					if (world.getBlock(xCoord + i, yCoord + i2, zCoord + j).canBeReplacedByLeaves(world, xCoord + i,
							yCoord + i2, zCoord + j) && i * j == 0)
					{
						world.setBlock(xCoord + i, yCoord + i2, zCoord + j, TFCBlocks.leaves, treeId, 2);
					}
				}
			}
		}
		return true;
	}

	protected boolean generateRandomBranches(World world, Random rand, int xCoord, int yCoord, int zCoord,
			int[] currentDirection, int numBranches, int remainingDistance)
	{
		if (remainingDistance < 1)
		{
			return true;
		}
		boolean[] validDirections = new boolean[] { false, false, false, false, false, false, false, false };
		int numValidDirections = 0;
		if (currentDirection[0] != 1)
		{
			if (currentDirection[2] != 1)
			{
				validDirections[0] = true;
				numValidDirections++;
			}
			validDirections[1] = true;
			numValidDirections++;
			if (currentDirection[2] != -1)
			{
				validDirections[2] = true;
				numValidDirections++;
			}
		}
		if (currentDirection[2] != 1)
		{
			if (currentDirection[0] != -1)
			{
				validDirections[3] = true;
				numValidDirections++;
			}
			validDirections[4] = true;
			numValidDirections++;
		}
		if (currentDirection[2] != -1)
		{
			if (currentDirection[0] != -1)
			{
				validDirections[5] = true;
				numValidDirections++;
			}
			validDirections[6] = true;
			numValidDirections++;
		}
		if (currentDirection[0] != -1)
		{
			validDirections[7] = true;
			numValidDirections++;
		}
		numBranches = Math.min(numBranches, numValidDirections);
		boolean placedBranch = false;
		for (int i = 0; i < numBranches; i++)
		{
			int currentRemainingDistance = remainingDistance;
			if (numValidDirections == 0)
			{
				break;
			}
			int[] curDir = null;
			if (i > 0)
			{
				int index = rand.nextInt(initialDirections.length);
				while (!validDirections[index])
				{
					index = rand.nextInt(initialDirections.length);
				}
				validDirections[index] = false;
				numValidDirections--;
				curDir = initialDirections[index];
				if (currentRemainingDistance > 1)
				{
					currentRemainingDistance = 1;
				}
			}
			else
			{
				curDir = currentDirection;
			}
			if (curDir[0] * curDir[2] != 0)
			{
				if (shouldSubtractDistance(rand))
				{
					currentRemainingDistance--;
				}
			}
			if (world.getBlock(xCoord + curDir[0], yCoord, zCoord + curDir[2]).isReplaceable(world, xCoord + curDir[0],
					yCoord, zCoord + curDir[2]) || world.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]).getMaterial() == Material.leaves)
			{
				Block theBranch = null;
				if (curDir[0] == -1)
				{
					if (curDir[2] == -1)
					{
						theBranch = currentRemainingDistance > 2 ? TFCBlocks.branch_X_Z : TFCBlocks.branchEnd_X_Z;
					}
					else if (curDir[2] == 0)
					{
						theBranch = currentRemainingDistance > 2 ? TFCBlocks.branch_X__ : TFCBlocks.branchEnd_X__;
					}
					else
					{
						theBranch = currentRemainingDistance > 2 ? TFCBlocks.branch_X_z : TFCBlocks.branchEnd_X_z;
					}
				}
				else if (curDir[0] == 0)
				{
					if (curDir[2] == -1)
					{
						theBranch = currentRemainingDistance > 2 ? TFCBlocks.branch___Z : TFCBlocks.branchEnd___Z;
					}
					else
					{
						theBranch = currentRemainingDistance > 2 ? TFCBlocks.branch___z : TFCBlocks.branchEnd___z;
					}
				}
				else
				{
					if (curDir[2] == -1)
					{
						theBranch = currentRemainingDistance > 2 ? TFCBlocks.branch_x_Z : TFCBlocks.branchEnd_x_Z;
					}
					else if (curDir[2] == 0)
					{
						theBranch = currentRemainingDistance > 2 ? TFCBlocks.branch_x__ : TFCBlocks.branchEnd_x__;
					}
					else
					{
						theBranch = currentRemainingDistance > 2 ? TFCBlocks.branch_x_z : TFCBlocks.branchEnd_x_z;
					}
				}
				// If the branch directly below is the same, it'll look ugly, so
				// skip it.
				// if (world.getBlock(xCoord + curDir[0], yCoord - 1, zCoord +
				// curDir[2]) == theBranch)
				// {
				// i--;
				// continue;
				// }
				// We only want to place this branch here if this branch can
				// continue.
				if (generateRandomBranches(world, rand, xCoord + curDir[0], yCoord, zCoord + curDir[2],
						new int[] { curDir[0], 0, curDir[2] }, 3, currentRemainingDistance - 1))
				{
					world.setBlock(xCoord + curDir[0], yCoord, zCoord + curDir[2], theBranch);
					if (((BlockBranch) theBranch).isEnd() && !fromSapling)
					{
						convertGrassToDirt(world, xCoord + curDir[0], yCoord, zCoord + curDir[2], height);
					}
					world.setBlockMetadataWithNotify(xCoord + curDir[0], yCoord, zCoord + curDir[2], treeId, 2);
					if (((BlockBranch) theBranch).isEnd())
					{
						fillInLeaves(world, rand, xCoord + curDir[0], yCoord, zCoord + curDir[2],
								currentRemainingDistance > 1);
					}
					placedBranch = true;
				}
			}
		}
		return placedBranch;
	}

}
