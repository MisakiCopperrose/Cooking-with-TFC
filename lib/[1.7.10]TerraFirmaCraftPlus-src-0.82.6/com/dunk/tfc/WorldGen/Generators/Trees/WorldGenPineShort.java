package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.ArrayList;
import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenPineShort extends WorldGenTreeBase
{
	boolean newT = true;

	final int[][] pineDirections = new int[][] { new int[] { -1, 0, -1 }, new int[] { -1, 0, 0 },
			new int[] { -1, 0, 1 }, new int[] { 1, 0, -1 }, new int[] { 0, 0, -1 }, new int[] { 1, 0, 1 },
			new int[] { 0, 0, 1 }, new int[] { 1, 0, 0 } };

	public WorldGenPineShort(boolean par1, int id, boolean sap)
	{
		super(par1, id, sap);
	}

	public boolean generate2(World world, Random rand, int xCoord, int yCoord, int zCoord)
	{
		int extraBranchLength = 2;
		height = rand.nextInt(7) + 6;
		if (!TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord))
				|| TFC_Core.isWater(world.getBlock(xCoord, yCoord, zCoord)))
		{
			return false;
		}
		for (int y = 0; y <= height; y++)
		{
			boolean isValid = world.getBlock(xCoord, yCoord + 1, zCoord).isReplaceable(world, xCoord, yCoord + 1,
					zCoord);
			if (!isValid)
			{
				return false;
			}
		}
		int lim = 2 + rand.nextInt(height / 4);
	//	System.out.println("Starting Tree");
		for (int y = 0; y < height; y++)
		{
			world.setBlock(xCoord, yCoord + y, zCoord, TFCBlocks.branch__y_);
			world.setBlockMetadataWithNotify(xCoord, yCoord + y, zCoord, treeId, 2);
			this.tryDoMoss(world, rand, xCoord, yCoord + y, zCoord);
			if (y > lim)
			{
				if (y < height / 3)
				{
					int numBranches = 4 - rand.nextInt(rand.nextInt(3) + 1);
					generateRandomBranches(world, rand, xCoord, yCoord + y, zCoord, new int[] { 0, 1, 0 }, numBranches,
							2 + extraBranchLength);

				}

				else if (y < (2 * height) / 3)
				{
					// Distance could be two.
					int numBranches = 6 - rand.nextInt(rand.nextInt(5) + 1);
					generateRandomBranches(world, rand, xCoord, yCoord + y, zCoord, new int[] { 0, 1, 0 }, numBranches,
							rand.nextInt(2 + extraBranchLength) + 1);
					fillInLeaves(world, rand, xCoord, yCoord + y, zCoord);
				}
				else if (y >= 2 * height / 3)
				{
					int numBranches = 4 - rand.nextInt(rand.nextInt(3) + 1);
					generateRandomBranches(world, rand, xCoord, yCoord + y, zCoord, new int[] { 0, 1, 0 }, numBranches,
							1);
					// fillInLeaves(world,rand,xCoord,yCoord+y,zCoord);
				}
			}
		}
		// fillInLeaves(world,rand,xCoord,yCoord+height-1,zCoord);
		world.setBlock(xCoord, yCoord + height, zCoord, TFCBlocks.branchEnd__y_);
		world.setBlockMetadataWithNotify(xCoord, yCoord + height, zCoord, treeId, 2);
	//	System.out.println("Finishing Tree");
		return true;
	}

	protected boolean fillInLeaves(World world, Random rand, int xCoord, int yCoord, int zCoord)
	{
		for (int i = -1; i < 2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				if (world.getBlock(xCoord + i, yCoord, zCoord + j).canBeReplacedByLeaves(world, xCoord + i, yCoord,
						zCoord + j) && i * j == 0)
				{
					world.setBlock(xCoord + i, yCoord, zCoord + j, TFCBlocks.leaves);
					world.setBlockMetadataWithNotify(xCoord + i, yCoord, zCoord + j, treeId, 2);
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
			if (numValidDirections == 0)
			{
				break;
			}
			int currentRemainingDistance = remainingDistance;
			int[] curDir = null;
			int index = rand.nextInt(pineDirections.length);
			while (!validDirections[index])
			{
				index = rand.nextInt(pineDirections.length);
			}
			validDirections[index] = false;
			numValidDirections--;
			curDir = pineDirections[index];

			if (curDir[0] * curDir[2] != 0)
			{
				if (shouldSubtractDistance(rand) && currentRemainingDistance > 1)
				{
					currentRemainingDistance--;
				}
			}
			if (world.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]).isReplaceable(world,
					xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]) || world.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]).getMaterial() == Material.leaves)
			{
				Block theBranch = null;
				if (curDir[0] == -1)
				{
					if (curDir[2] == -1)
					{
						theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_X_Z : TFCBlocks.branchEnd_X_Z;
					}
					else if (curDir[2] == 0)
					{
						theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_X__ : TFCBlocks.branchEnd_X__;
					}
					else
					{
						theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_X_z : TFCBlocks.branchEnd_X_z;
					}
				}
				else if (curDir[0] == 0)
				{
					if (curDir[2] == -1)
					{
						theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch___Z : TFCBlocks.branchEnd___Z;
					}
					else
					{
						theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch___z : TFCBlocks.branchEnd___z;
					}
				}
				else
				{
					if (curDir[2] == -1)
					{
						theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_x_Z : TFCBlocks.branchEnd_x_Z;
					}
					else if (curDir[2] == 0)
					{
						theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_x__ : TFCBlocks.branchEnd_x__;
					}
					else
					{
						theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_x_z : TFCBlocks.branchEnd_x_z;
					}
				}
				// If the branch directly below is the same, it'll look ugly, so
				// skip it.
				if (world.getBlock(xCoord + curDir[0], yCoord + curDir[1] - 1, zCoord + curDir[2]) == theBranch)
				{
				//	i--;
				//	continue;
				}
				// We only want to place this branch here if this branch can
				// continue.
				if (generateRandomBranches(world, rand, xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2],
						new int[] { curDir[0], curDir[1], curDir[2] }, 3, currentRemainingDistance - 1))
				{
					world.setBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2], theBranch);
					if (((BlockBranch) theBranch).isEnd() && !fromSapling)
					{
						convertGrassToDirt(world, xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2], height);
					}
			//		System.out.println( (xCoord + curDir[0])+", "+ (yCoord + curDir[1])+", "+(zCoord + curDir[2]));
					world.setBlockMetadataWithNotify(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2], treeId,
							2);
					placedBranch = true;
				}
			}
		}
		return placedBranch;
	}

	@Override
	public boolean generate(World world, Random rand, int xCoord, int yCoord, int zCoord)
	{
		newT = true;
		if (newT)
		{
			return generate2(world, rand, xCoord, yCoord, zCoord);
		}
		else
		{
			int treeHeight = rand.nextInt(4) + 6;
			int var7 = 1 + rand.nextInt(2);
			int var8 = treeHeight - var7;
			int var9 = 2 + rand.nextInt(2);
			boolean isValid = true;

			if (yCoord >= 1 && yCoord + treeHeight + 1 <= 256)
			{
				int var15;
				int var21;
				Block block;

				for (int y = yCoord; y <= yCoord + 1 + treeHeight && isValid; ++y)
				{
					if (y - yCoord < var7)
						var21 = 0;
					else
						var21 = var9;

					for (int x = xCoord - var21; x <= xCoord + var21 && isValid; ++x)
					{
						for (int z = zCoord - var21; z <= zCoord + var21 && isValid; ++z)
						{
							if (y >= 0 && y < 256)
							{
								block = world.getBlock(x, y, z);
								if (!block.isAir(world, x, y, z) && !block.isLeaves(world, x, y, z)
										&& !block.isReplaceable(world, x, y, z))
									isValid = false;
							}
							else
							{
								isValid = false;
							}
						}
					}
				}

				if (!isValid)
				{
					return false;
				}
				else
				{
					block = world.getBlock(xCoord, yCoord - 1, zCoord);

					if (TFC_Core.isSoil(block) && yCoord < world.getActualHeight() - treeHeight - 1)
					{
						Block soil = TFC_Core.getTypeForSoil(block);
						int soilMeta = world.getBlockMetadata(xCoord, yCoord - 1, zCoord);

						this.setBlockAndNotifyAdequately(world, xCoord, yCoord - 1, zCoord, soil, soilMeta);
						var21 = rand.nextInt(2);
						int i = 1;
						byte var22 = 0;
						int x;
						int y;

						for (var15 = 0; var15 <= var8; ++var15)
						{
							y = yCoord + treeHeight - var15;
							for (x = xCoord - var21; x <= xCoord + var21; ++x)
							{
								int var18 = x - xCoord;
								for (int z = zCoord - var21; z <= zCoord + var21; ++z)
								{
									int var20 = z - zCoord;
									block = world.getBlock(x, y, z);
									if ((Math.abs(var18) != var21 || Math.abs(var20) != var21 || var21 <= 0)
											&& (block == null || block.canBeReplacedByLeaves(world, x, y, z)))
									{
										// this.setBlockAndNotifyAdequately(world,
										// x, y, z,
										// block2?TFCBlocks.leaves2:TFCBlocks.leaves,
										// treeId);
									}
								}
							}

							if (var21 >= i)
							{
								var21 = var22;
								var22 = 1;
								++i;
								if (i > var9)
									i = var9;
							}
							else
							{
								++var21;
							}
						}

						for (y = 0; y < treeHeight - 1; ++y)
						{
							this.setBlockAndNotifyAdequately(world, xCoord, yCoord + y, zCoord,
									block2 ? TFCBlocks.branch2__y_ : TFCBlocks.branch__y_, treeId);
							//this.tryDoMoss(world, rand, xCoord, yCoord + y, zCoord);
						}
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			else
			{
				return false;
			}
		}

	}
}
