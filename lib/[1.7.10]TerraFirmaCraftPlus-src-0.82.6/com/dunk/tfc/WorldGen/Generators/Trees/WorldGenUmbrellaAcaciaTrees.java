package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenUmbrellaAcaciaTrees extends WorldGenTreeBase
{
	private int maxHeightBranch = 1;

	private int tempSourceY;


	public WorldGenUmbrellaAcaciaTrees(boolean flag, int id, boolean sap)
	{
		super(flag,id,sap);
	}

	protected boolean generateRandomBranches(World world, Random rand, int xCoord, int yCoord, int zCoord,
			int[] currentDirection, int numBranches, int remainingDistance)
	{
		if (remainingDistance < 1)
		{
			return true;
		}
		boolean[] validDirections = new boolean[] { false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false };

		int numValidDirections = 0;

		boolean allowGoUp = yCoord - maxHeightBranch < tempSourceY + height;

		if (currentDirection[0] != 1)
		{
			if (currentDirection[2] != 1)
			{
				validDirections[0] = true;
				numValidDirections++;
				validDirections[0 + 8] = allowGoUp;
				numValidDirections += allowGoUp ? 1 : 0;
			}
			validDirections[1] = true;
			numValidDirections++;
			validDirections[1 + 8] = allowGoUp;
			numValidDirections += allowGoUp ? 1 : 0;
			if (currentDirection[2] != -1)
			{
				validDirections[2] = true;
				numValidDirections++;
				validDirections[2 + 8] = allowGoUp;
				numValidDirections += allowGoUp ? 1 : 0;
			}
		}
		if (currentDirection[2] != 1)
		{
			if (currentDirection[0] != -1)
			{
				validDirections[3] = true;
				numValidDirections++;
				validDirections[3 + 8] = allowGoUp;
				numValidDirections += allowGoUp ? 1 : 0;
			}
			validDirections[4] = true;
			numValidDirections++;
			validDirections[4 + 8] = allowGoUp;
			numValidDirections += allowGoUp ? 1 : 0;
		}
		if (currentDirection[2] != -1)
		{
			if (currentDirection[0] != -1)
			{
				validDirections[5] = true;
				numValidDirections++;
				validDirections[5 + 8] = allowGoUp;
				numValidDirections += allowGoUp ? 1 : 0;
			}
			validDirections[6] = true;
			numValidDirections++;
			validDirections[6 + 8] = allowGoUp;
			numValidDirections += allowGoUp ? 1 : 0;
		}
		if (currentDirection[0] != -1)
		{
			validDirections[7] = true;
			numValidDirections++;
			validDirections[7 + 8] = allowGoUp;
			numValidDirections += allowGoUp ? 1 : 0;
		}
		numBranches = Math.min(numBranches, numValidDirections);
		boolean placedBranch = false;
		int[] curDir = null;
		Block theBranch = null;
		for (int i = 0; i < numBranches; i++)
		{
			if (numValidDirections == 0)
			{
				break;
			}
			curDir = null;

			boolean ignoreBranching = false;
			if (remainingDistance > 1)
			{
				// 50/50 chance whether we keep going straight or start
				// branching.
				ignoreBranching = rand.nextBoolean();
			}

			if (!ignoreBranching)
			{
				int index = rand.nextInt(directions.length);
				while (!validDirections[index])
				{
					index = rand.nextInt(directions.length);
				}
				validDirections[index] = false;
				numValidDirections--;
				curDir = directions[index];
			}
			else
			{
				curDir = currentDirection;
			}

			if (curDir[0] * curDir[2] != 0 || curDir[0] * curDir[1] != 0 || curDir[1] * curDir[2] != 0)
			{
				if (shouldSubtractDistance(rand))
				{
					remainingDistance--;
				}
			}
			if (world.blockExists(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2])
					&& world.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]).isReplaceable(world,
							xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]) || world.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]).getMaterial() == Material.leaves)
			{
				theBranch = null;
				if (curDir[1] == 1)
				{
					if (curDir[0] == -1)
					{
						if (curDir[2] == -1)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_XyZ : TFCBlocks.branchEnd_XyZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_Xy_ : TFCBlocks.branchEnd_Xy_;
						}
						else
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_Xyz : TFCBlocks.branchEnd_Xyz;
						}
					}
					else if (curDir[0] == 0)
					{
						if (curDir[2] == -1)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch__yZ : TFCBlocks.branchEnd__yZ;
						}
						else
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch__yz : TFCBlocks.branchEnd__yz;
						}
					}
					else
					{
						if (curDir[2] == -1)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_xyZ : TFCBlocks.branchEnd_xyZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_xy_ : TFCBlocks.branchEnd_xy_;
						}
						else
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_xyz : TFCBlocks.branchEnd_xyz;
						}
					}
				}
				else if (curDir[1] == 0)
				{
					if (curDir[0] == -1)
					{
						if (curDir[2] == -1)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_X_Z : TFCBlocks.branchEnd_X_Z;
						}
						else if (curDir[2] == 0)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_X__ : TFCBlocks.branchEnd_X__;
						}
						else
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_X_z : TFCBlocks.branchEnd_X_z;
						}
					}
					else if (curDir[0] == 0)
					{
						if (curDir[2] == -1)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch___Z : TFCBlocks.branchEnd___Z;
						}
						else
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch___z : TFCBlocks.branchEnd___z;
						}
					}
					else
					{
						if (curDir[2] == -1)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_x_Z : TFCBlocks.branchEnd_x_Z;
						}
						else if (curDir[2] == 0)
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_x__ : TFCBlocks.branchEnd_x__;
						}
						else
						{
							theBranch = remainingDistance > 1 ? TFCBlocks.branch_x_z : TFCBlocks.branchEnd_x_z;
						}
					}
				}
				if (block2)
				{
					theBranch = TFC_Core.getSecondBranch(theBranch);
				}
				// If the branch directly below is the same, it'll look ugly, so
				// skip it.
				if (world.getBlock(xCoord + curDir[0], yCoord - 1 + curDir[1], zCoord + curDir[2]) == theBranch
						&& !ignoreBranching)
				{
					i--;
					continue;
				}
				// We only want to place this branch here if this branch can
				// continue.
				if (generateRandomBranches(world, rand, xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2],
						curDir, 3, remainingDistance - 1))
				{
					world.setBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2], theBranch, treeId, 2);
					if (((BlockBranch) theBranch).isEnd())
					{
						placeLeaves(world, rand, xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]);
					}
					placedBranch = true;
				}
			}
		}
		return placedBranch;
	}

	public boolean placeLeaves(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int radius = 3;
		for (int i = -(radius + 1); i <= (radius + 1); i++)
		{
			for (int j = 0; j <= radius - 1; j++)
			{
				for (int k = -(radius + 1); k <= (radius + 1); k++)
				{
					if ((world.blockExists(xCoord + i, yCoord + j, zCoord + k)
							&& world.getBlock(xCoord + i, yCoord + j, zCoord + k).isReplaceable(world, xCoord + i, yCoord + j,
									zCoord + k))
							&& i * i + j * j + k * k < radius * radius)
					{
						world.setBlock(xCoord + i, yCoord + j, zCoord + k, leafBlock, treeId, 2);
						if (!fromSapling
								&& !conversionMatrix[16 + (xCoord + i) - tempSourceX][16 + (zCoord + k) - tempSourceZ])
						{
							convertGrassToDirt(world, xCoord + i, yCoord + j, zCoord + k, height);
							conversionMatrix[16 + (xCoord + i) - tempSourceX][16 + (zCoord + k) - tempSourceZ] = true;
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		if (random.nextInt(4) != 0)
		{
			return true;
		}

		height = random.nextInt(2) + 3;
		if (yCoord < 1 || yCoord + height + 1 > world.getHeight())
			return false;
		tempSourceX = xCoord;
		tempSourceZ = zCoord;
		tempSourceY = yCoord;
		conversionMatrix = new boolean[32][32];
		boolean flag = true;
		Block j3;
		for (int i1 = yCoord; i1 <= yCoord + 1 + height; i1++)
		{
			byte byte0 = 1;
			if (i1 == yCoord)
				byte0 = 0;
			if (i1 >= yCoord + 1 + height - 2)
				byte0 = 2;

			for (int i2 = xCoord - byte0; i2 <= xCoord + byte0 && flag; i2++)
			{
				for (int l2 = zCoord - byte0; l2 <= zCoord + byte0 && flag; l2++)
				{
					if (i1 >= 0 && i1 < world.getHeight())
					{
						j3 = world.getBlock(i2, i1, l2);
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

		if (!(TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord))) || yCoord >= world.getHeight() - height - 1)
			return false;

		for (int l1 = 0; l1 < height; l1++)
		{

			// setBlockAndNotifyAdequately(world, xCoord, yCoord + l1, zCoord,
			// TFCBlocks.logNatural, treeId);
			setBlockAndNotifyAdequately(world, xCoord, yCoord + l1, zCoord,
					block2 ? TFCBlocks.branch2__y_ : TFCBlocks.branch__y_, treeId);
		}
		int extraBranchLength = 5;
		// if(treeId == 0)
		// {
		generateRandomBranches(world, random, xCoord, yCoord + height - 1 - random.nextInt(2), zCoord,
				new int[] { -1, 1, -1 }, 1, random.nextInt(3) + extraBranchLength);
		generateRandomBranches(world, random, xCoord, yCoord + height - 1 - random.nextInt(2), zCoord,
				new int[] { 1, 1, -1 }, 1, random.nextInt(3) + extraBranchLength);
		generateRandomBranches(world, random, xCoord, yCoord + height - 1 - random.nextInt(2), zCoord,
				new int[] { 1, 1, 1 }, 1, random.nextInt(3) + extraBranchLength);
		generateRandomBranches(world, random, xCoord, yCoord + height - 1 - random.nextInt(2), zCoord,
				new int[] { -1, 1, 1 }, 1, random.nextInt(3) + extraBranchLength);
		// }

		return true;
	}
}
