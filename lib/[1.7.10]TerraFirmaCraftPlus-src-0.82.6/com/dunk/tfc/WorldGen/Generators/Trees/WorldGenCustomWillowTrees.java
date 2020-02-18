package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.WorldGen.Generators.WorldGenForests;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomWillowTrees extends WorldGenTreeBase
{

	protected final int[][] initialWillowDirections = new int[][] { new int[] { -1, 1, -1 }, new int[] { 0, 1, -1 },
			new int[] { 1, 1, -1 }, new int[] { 1, 1, 0 }, new int[] { 1, 1, 1 }, new int[] { 0, 1, 1 },
			new int[] { -1, 1, 1 }, new int[] { -1, 1, 0 } };

	protected final int[][] willowMiddleDirections = new int[][] { new int[] { -1, 0, -1 }, new int[] { 0, 0, -1 },
			new int[] { 1, 0, -1 }, new int[] { 1, 0, 0 }, new int[] { 1, 0, 1 }, new int[] { 0, 0, 1 },
			new int[] { -1, 0, 1 }, new int[] { -1, 0, 0 } };

	protected final int[][] willowFinalDirections = new int[][] { new int[] { -1, -1, -1 }, new int[] { 0, -1, -1 },
			new int[] { 1, -1, -1 }, new int[] { 1, -1, 0 }, new int[] { 1, -1, 1 }, new int[] { 0, -1, 1 },
			new int[] { -1, -1, 1 }, new int[] { -1, -1, 0 } };

	public WorldGenCustomWillowTrees(boolean flag, int id, boolean sap)
	{
		super(flag, id, sap);
	}

	@Override
	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int distFromEdge = 6;
		// We don't want to generate too close to chunk boundaries, because that
		// gets out of hand
		if ((Math.abs(xCoord) % 16 > 16 - distFromEdge || Math
				.abs(xCoord) % 16 < distFromEdge || Math.abs(zCoord) % 16 > 16 - distFromEdge || Math
						.abs(zCoord) % 16 < distFromEdge) && !fromSapling)
		{
			return false;
		}
		int height = random.nextInt(2) + 2;

		this.tempSourceX = xCoord;
		this.tempSourceZ = zCoord;

		if (!TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord)))
		{
			return false;
		}
		if (!world.getBlock(xCoord, yCoord, zCoord).isReplaceable(world, xCoord, yCoord, zCoord))
		{
			return false;
		}
		for (int j = 1; j < height + 3; j++)
		{
			for (int i = -4; i < 5; i++)
			{

				for (int k = -4; k < 5; k++)
				{
					if (!world.getBlock(xCoord + i, yCoord + j, zCoord + k).isReplaceable(world, xCoord + i, yCoord + j,
							zCoord + k))
					{
						return false;
					}
				}
			}
		}

		int extraBranchLength = random.nextInt(2) + 4;
		int c = 0;
		int vertNum = random.nextInt(4);

		generateRandomBranches(world, random, xCoord, yCoord + height - 1, zCoord, new int[] { 0, 1, 0 }, 1,
				random.nextInt(3) + extraBranchLength + 2);

		if (vertNum != 0)
		{
			while (!generateRandomBranches(world, random, xCoord, yCoord + height - 1, zCoord, new int[] { -1, 1, -1 },
					1, random.nextInt(3) + extraBranchLength) && c < 10)
			{
				c++;
			}
			// System.out.println(c);
		}
		if (vertNum != 1)
		{
			generateRandomBranches(world, random, xCoord, yCoord + height - 1, zCoord, new int[] { 1, 1, -1 }, 1,
					random.nextInt(3) + extraBranchLength);
		}
		if (vertNum != 2)
		{
			generateRandomBranches(world, random, xCoord, yCoord + height - 1, zCoord, new int[] { 1, 1, 1 }, 1,
					random.nextInt(3) + extraBranchLength);
		}
		if (vertNum != 3)
		{
			generateRandomBranches(world, random, xCoord, yCoord + height - 1, zCoord, new int[] { -1, 1, 1 }, 1,
					random.nextInt(3) + extraBranchLength);
		}

		for (int i = 0; i < height; i++)
		{
			world.setBlock(xCoord, yCoord + i, zCoord, block2 ? TFCBlocks.logNatural2 : TFCBlocks.logNatural, treeId,
					2);
			tryDoMoss(world, random, xCoord, yCoord + i, zCoord);
		}

		// System.out.println("Placed willow at " + xCoord + ", " + yCoord + ",
		// " + zCoord);
		return true;
	}

	// Willow uses alternative leaf generation
	@Override
	public boolean placeLeaves(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int radius = 1;
		boolean lost = false;// BlockBranch.shouldLoseLeaf(world, xCoord,
								// yCoord, zCoord, random, block2);
		boolean defLost = false;// BlockBranch.shouldDefinitelyLoseLeaf(world,
								// xCoord, yCoord, zCoord, block2);
		for (int i = -(radius + 1); i <= (radius + 1); i++)
		{
			for (int k = -(radius + 1); k <= (radius + 1); k++)
			{
				for (int j = 1; j >= -5; j--)
				{
					Block b = world.getBlock(xCoord + i, yCoord + j, zCoord + k);
					int d = i * i + (j > 0 ? (j * j) : 0) + k * k;
					if (b.isReplaceable(world, xCoord + i, yCoord + j,
							zCoord + k) && !defLost && (!lost || random.nextInt(
									4) == 0) && (this.tempSourceX / 16 == (xCoord + i) / 16 && this.tempSourceZ / 16 == (zCoord + k) / 16))
					{
						world.setBlock(xCoord + i, yCoord + j, zCoord + k, leafBlock, treeId, 2);
						if (!fromSapling && !conversionMatrix[16 + (xCoord + i) - tempSourceX][16 + (zCoord + k) - tempSourceZ])
						{
							// convertGrassToDirt(world, xCoord + i, yCoord + j,
							// zCoord + k, height);
							// conversionMatrix[16 + (xCoord + i) -
							// tempSourceX][16 + (zCoord + k) - tempSourceZ] =
							// true;
						}
					}
					else if (i != 0 && j != 0 && k != 0)
					{
						break;
					}
				}
			}
		}
		return true;
	}

	@Override
	protected boolean generateRandomBranches(World world, Random rand, int xCoord, int yCoord, int zCoord,
			int[] previousDirection, int numBranches, int remainingDistance)
	{
		if (remainingDistance < 1)
		{
			return true;
		}
		boolean[] validDirections = new boolean[] { false, false, false, false, false, false, false, false };

		int numValidDirections = 0;

		if (previousDirection[0] != 1)
		{
			if (previousDirection[2] != 1)
			{
				validDirections[0] = true;
				numValidDirections++;
			}
			validDirections[1] = true;
			numValidDirections++;
			if (previousDirection[2] != -1)
			{
				validDirections[2] = true;
				numValidDirections++;
			}
		}
		if (previousDirection[2] != 1)
		{
			if (previousDirection[0] != -1)
			{
				validDirections[3] = true;
				numValidDirections++;
			}
			validDirections[4] = true;
			numValidDirections++;
		}
		if (previousDirection[2] != -1)
		{
			if (previousDirection[0] != -1)
			{
				validDirections[5] = true;
				numValidDirections++;
			}
			validDirections[6] = true;
			numValidDirections++;
		}
		if (previousDirection[0] != -1)
		{
			validDirections[7] = true;
			numValidDirections++;
		}

		numBranches = Math.min(numBranches, numValidDirections);
		boolean placedBranch = false;
		Block theBranch = null;
		for (int i = 0; i < numBranches; i++)
		{
			int currentRemainingDistance = remainingDistance;
			if (numValidDirections == 0)
			{
				break;
			}
			int[] curDir = null;

			boolean ignoreBranching = false;
			if (currentRemainingDistance > 3 && i == 0)
			{
				// 50/50 chance whether we keep going straight or start
				// branching.
				ignoreBranching = true;
			}
			else
			{
				// return true;
			}

			if (!ignoreBranching)
			{
				int index = rand.nextInt(initialWillowDirections.length);
				while (!validDirections[index])
				{
					index = rand.nextInt(initialWillowDirections.length);
				}
				validDirections[index] = false;
				numValidDirections--;
				if (currentRemainingDistance == 1)
				{
					curDir = willowFinalDirections[index];
				}
				else if (currentRemainingDistance <= 3)
				{
					curDir = willowMiddleDirections[index];
				}
				else
				{
					curDir = initialWillowDirections[index];
				}
			}
			else
			{
				curDir = previousDirection;
			}

			if (curDir[0] * curDir[2] != 0 || curDir[0] * curDir[1] != 0 || curDir[1] * curDir[2] != 0)
			{
				if (shouldSubtractDistance(rand))
				{
					// currentRemainingDistance--;
				}
			}
			if (world.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2])
					.isReplaceable(world, xCoord + curDir[0], yCoord + curDir[1],
							zCoord + curDir[2]) || world
									.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2])
									.getMaterial() == Material.leaves)
			{
				theBranch = null;
				if (curDir[0] == 0 && curDir[1] == 1 && curDir[2] == 0)
				{
					theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch__y_ : TFCBlocks.branchEnd__y_;
				}
				else if (curDir[1] == 1)
				{
					if (curDir[0] == -1)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_XyZ : TFCBlocks.branchEnd_XyZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_Xy_ : TFCBlocks.branchEnd_Xy_;
						}
						else
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_Xyz : TFCBlocks.branchEnd_Xyz;
						}
					}
					else if (curDir[0] == 0)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch__yZ : TFCBlocks.branchEnd__yZ;
						}
						else
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch__yz : TFCBlocks.branchEnd__yz;
						}
					}
					else
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_xyZ : TFCBlocks.branchEnd_xyZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_xy_ : TFCBlocks.branchEnd_xy_;
						}
						else
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_xyz : TFCBlocks.branchEnd_xyz;
						}
					}
				}
				else if (curDir[1] == 0)
				{
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
				}
				else if (curDir[1] == -1)
				{
					if (curDir[0] == -1)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_XYZ : TFCBlocks.branchEnd_XYZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_XY_ : TFCBlocks.branchEnd_XY_;
						}
						else
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_XYz : TFCBlocks.branchEnd_XYz;
						}
					}
					else if (curDir[0] == 0)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch__YZ : TFCBlocks.branchEnd__YZ;
						}
						else
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch__Yz : TFCBlocks.branchEnd__Yz;
						}
					}
					else
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_xYZ : TFCBlocks.branchEnd_xYZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_xY_ : TFCBlocks.branchEnd_xY_;
						}
						else
						{
							theBranch = currentRemainingDistance > 3 ? TFCBlocks.branch_xYz : TFCBlocks.branchEnd_xYz;
						}
					}
				}
				if (block2)
				{
					theBranch = TFC_Core.getSecondBranch(theBranch);
				}
				// If the branch directly below is the same, it'll look ugly, so
				// skip it.
				if (world.getBlock(xCoord + curDir[0], yCoord - 1 + curDir[1],
						zCoord + curDir[2]) == theBranch && !ignoreBranching)
				{
					// i--;
					// continue;
				}
				// We only want to place this branch here if this branch can
				// continue.
				if (generateRandomBranches(world, rand, xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2],
						curDir, remainingDistance > 4 ? 1 : 3, currentRemainingDistance - 1))
				{
					int xD = ((BlockBranch) theBranch).getSourceX();
					int yD = ((BlockBranch) theBranch).getSourceY();
					int zD = ((BlockBranch) theBranch).getSourceZ();
					/*
					 * String s = "Placing branch! branch_"; s += (xD == -1 ?
					 * "x" : xD == 0 ? "_" : "X"); s += (yD == -1 ? "y" : yD ==
					 * 0 ? "_" : "Y"); s += (zD == -1 ? "z" : zD == 0 ? "_" :
					 * "Z"); if (xD == 0 && yD == 0 && zD == 0) {
					 * System.out.println("WAIT"); } System.out.println(s + ": "
					 * + (xCoord + curDir[0]) + ", " + (yCoord + curDir[1]) +
					 * ", " + (zCoord + curDir[2]));
					 */

					if (((BlockBranch) theBranch).isEnd())
					{
						// placeLeaves(world, rand, xCoord + curDir[0], yCoord +
						// curDir[1], zCoord + curDir[2]);
						for (int ii = -2; ii < 3; ii++)
						{
							for (int jj = 1; jj > -3; jj--)
							{
								for (int kk = -2; kk < 3; kk++)
								{
									if (world.getBlock(xCoord + curDir[0] + ii, yCoord + curDir[1] + jj,
											zCoord + curDir[2] + kk).isReplaceable(world, xCoord + curDir[0] + ii,
													yCoord + curDir[1] + jj, zCoord + curDir[2] + kk))
									{
										if (ii * ii + kk * kk <= 1 && !(ii == 0 && kk == 0 && jj < 1))
										{
											world.setBlock(xCoord + curDir[0] + ii, yCoord + curDir[1] + jj,
													zCoord + curDir[2] + kk, leafBlock, treeId, 2);
										}
									}
								}
							}
						}

					}
					world.setBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2], theBranch, treeId, 2);
					placedBranch = true;
				}
			}
		}
		return placedBranch;
	}
}