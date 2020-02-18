package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDouglasFir extends WorldGenTreeBase
{
	private boolean tall;

	int extraTreeHeight = 6;

	boolean newT = true;

	public WorldGenDouglasFir(boolean par1, int m, boolean t, boolean sap)
	{
		super(par1, m, sap);
	}

	@Override
	public boolean generate(World world, Random rand, int xCoord, int yCoord, int zCoord)
	{
		int distFromEdge = 5;
		// We don't want to generate too close to chunk boundaries, because that
		// gets out of hand
		if ((xCoord % 16 > 16 - distFromEdge || Math.abs(
				xCoord) % 16 < distFromEdge || zCoord % 16 > 16 - distFromEdge || Math.abs(zCoord) % 16 < distFromEdge)&&!fromSapling)
		{
			return false;
		}
		height = rand.nextInt(10) + 8;
		
		this.tempSourceX = xCoord;
		this.tempSourceZ = zCoord;
		
		if (rand.nextInt(20) == 0)
			tall = true;
		if (tall)
			height += rand.nextInt(10);

		if (yCoord < 1 || yCoord + height + 1 + extraTreeHeight > 256)
			return false;

		if (TFC_Core.isWater(world.getBlock(xCoord, yCoord, zCoord)))
		{
			return false;
		}

		boolean flag = true;
		int c = 0;
		for (int j = yCoord; j <= yCoord + 1 + height + extraTreeHeight; j++)
		{
			c++;
			for(int ii = xCoord-c>(height/3)?3:2; ii < xCoord+ (c>(height/3)?4:3); ii++)
			{
				for(int kk = zCoord-c>(height/3)?3:2; kk < zCoord+(c>(height/3)?4:3); kk++)
				{
					if(!world.getBlock(ii, j, kk).isReplaceable(world,ii, j, kk))
					{
						return false;
					}
				}
			}
		}

		if (!flag)
			return false;

		if (!TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord)) || yCoord >= 256 - height - 1)
			return false;
		for (int l1 = 0; l1 < height; l1++)
		{
			setBlockAndNotifyAdequately(world, xCoord, yCoord + l1, zCoord, TFCBlocks.logNatural, treeId);
		}

		// We want to make branches in a radial pattern going up
		int startHeight = height / 3;
		int branchPattern = rand.nextInt(8);
		for (int i = startHeight; i < height; i++)
		{
			int numBranchesHere = rand.nextInt(3) + 2;
			int extraBranchLength = i < ((9f * height) / 10f) ? 2 : 1;
			for (int j = 0; j < numBranchesHere; j++)
			{
				if (rand.nextInt(4) != 0)
				{
					generateRandomBranches(world, rand, xCoord, yCoord + i, zCoord,
							initialDirections[(branchPattern + j) % 8], 1, rand.nextInt(2) + extraBranchLength);
				}
			}
			branchPattern += numBranchesHere;
			branchPattern %= 8;
		}
		for (int i = height; i < height + extraTreeHeight; i++)
		{
			int numBranchesHere = rand.nextInt(3) + 2;
			int extraBranchLength = 1;
			for (int j = 0; j < numBranchesHere; j++)
			{
				if (rand.nextInt(4) != 0)
				{
					generateRandomBranches(world, rand, xCoord, yCoord + i, zCoord,
							initialDirections[(branchPattern + j) % 8], 1, rand.nextInt(2) + extraBranchLength);
				}
			}
			setBlockAndNotifyAdequately(world, xCoord, yCoord + i, zCoord, TFCBlocks.branch__y_, treeId);
			branchPattern += numBranchesHere;
			branchPattern %= 8;
		}
		setBlockAndNotifyAdequately(world, xCoord, yCoord + height + extraTreeHeight, zCoord, TFCBlocks.branchEnd__y_,
				treeId);
		fillInLeaves(world, rand, xCoord, yCoord + height + extraTreeHeight, zCoord, true);
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
					if (world.getBlock(xCoord + i, yCoord + i2, zCoord + j).isReplaceable(world, xCoord + i,
							yCoord + i2, zCoord + j) && i * j == 0)
					{
						world.setBlock(xCoord + i, yCoord + i2, zCoord + j, TFCBlocks.leaves, treeId, 2);
					}
				}
			}
		}
		return true;
	}

	@Override
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
						theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_X_Z : TFCBlocks.branchEnd_X_Z;
					}
					else if (curDir[2] == 0)
					{
						theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_X__ : TFCBlocks.branchEnd_X__;
					}
					else
					{
						theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_X_z : TFCBlocks.branchEnd_X_z;
					}
				}
				else if (curDir[0] == 0)
				{
					if (curDir[2] == -1)
					{
						theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch___Z : TFCBlocks.branchEnd___Z;
					}
					else
					{
						theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch___z : TFCBlocks.branchEnd___z;
					}
				}
				else
				{
					if (curDir[2] == -1)
					{
						theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_x_Z : TFCBlocks.branchEnd_x_Z;
					}
					else if (curDir[2] == 0)
					{
						theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_x__ : TFCBlocks.branchEnd_x__;
					}
					else
					{
						theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_x_z : TFCBlocks.branchEnd_x_z;
					}
				}
				// If the branch directly below is the same, it'll look ugly, so
				// skip it.
				if (world.getBlock(xCoord + curDir[0], yCoord - 1, zCoord + curDir[2]) == theBranch )
				{
				//	i--;
				//	continue;
				}
				// We only want to place this branch here if this branch can
				// continue.
				if (generateRandomBranches(world, rand, xCoord + curDir[0], yCoord  + curDir[1], zCoord + curDir[2],
						curDir, 3, currentRemainingDistance - 1))
				{
					//world.setBlock(xCoord + curDir[0], yCoord, zCoord + curDir[2], theBranch,treeId,2);
					setBlockAndNotifyAdequately(world,xCoord + curDir[0], yCoord, zCoord + curDir[2], theBranch,treeId);
					if (((BlockBranch) theBranch).isEnd() && !fromSapling)
					{
						convertGrassToDirt(world, xCoord + curDir[0], yCoord, zCoord + curDir[2], height);
					}
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
