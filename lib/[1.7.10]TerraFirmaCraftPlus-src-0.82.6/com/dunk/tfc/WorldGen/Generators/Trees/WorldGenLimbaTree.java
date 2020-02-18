package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.WorldGen.Generators.WorldGenForests;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Enums.EnumForestPlant;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldGenLimbaTree extends WorldGenTreeBase
{
	float rainfall;
	float temperatureAvg;
	float evt;
	private int tempSourceY;
	public WorldGenLimbaTree(boolean flag, int id, boolean sap)
	{
		super(flag, id, sap);
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

		if (currentDirection[0] != 1)
		{
			if (currentDirection[2] != 1)
			{
				validDirections[0] = true;
				numValidDirections++;
				validDirections[0 + 8] = true;
				numValidDirections++;
			}
			validDirections[1] = true;
			numValidDirections++;
			validDirections[1 + 8] = true;
			numValidDirections++;
			if (currentDirection[2] != -1)
			{
				validDirections[2] = true;
				numValidDirections++;
				validDirections[2 + 8] = true;
				numValidDirections++;
			}
		}
		if (currentDirection[2] != 1)
		{
			if (currentDirection[0] != -1)
			{
				validDirections[3] = true;
				numValidDirections++;
				validDirections[3 + 8] = true;
				numValidDirections++;
			}
			validDirections[4] = true;
			numValidDirections++;
			validDirections[4 + 8] = true;
			numValidDirections++;
		}
		if (currentDirection[2] != -1)
		{
			if (currentDirection[0] != -1)
			{
				validDirections[5] = true;
				numValidDirections++;
				validDirections[5 + 8] = true;
				numValidDirections++;
			}
			validDirections[6] = true;
			numValidDirections++;
			validDirections[6 + 8] = true;
			numValidDirections++;
		}
		if (currentDirection[0] != -1)
		{
			validDirections[7] = true;
			numValidDirections++;
			validDirections[7 + 8] = true;
			numValidDirections++;
		}
		numBranches = Math.min(numBranches, numValidDirections);
		boolean placedBranch = false;
		int[] curDir = null;
		Block theBranch = null;
		for (int i = 0; i < numBranches; i++)
		{
			int currentRemainingDistance = remainingDistance;
			if (numValidDirections == 0)
			{
				break;
			}
			curDir = null;

			boolean ignoreBranching = false;
			if (currentRemainingDistance > 1)
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
					currentRemainingDistance--;
				}
			}
			if (world.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]).isReplaceable(world,
					xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]) || world.getBlock(xCoord + curDir[0],
							yCoord + curDir[1], zCoord + curDir[2]) instanceof BlockCustomLeaves)
			{
				theBranch = null;
				if (curDir[1] == 1)
				{
					if (curDir[0] == -1)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_XyZ : TFCBlocks.branchEnd_XyZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_Xy_ : TFCBlocks.branchEnd_Xy_;
						}
						else
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_Xyz : TFCBlocks.branchEnd_Xyz;
						}
					}
					else if (curDir[0] == 0)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch__yZ : TFCBlocks.branchEnd__yZ;
						}
						else
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch__yz : TFCBlocks.branchEnd__yz;
						}
					}
					else
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_xyZ : TFCBlocks.branchEnd_xyZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_xy_ : TFCBlocks.branchEnd_xy_;
						}
						else
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_xyz : TFCBlocks.branchEnd_xyz;
						}
					}
				}
				else if (curDir[1] == 0)
				{
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
					i--;
					continue;
				}
				// We only want to place this branch here if this branch can
				// continue.
				if (generateRandomBranches(world, rand, xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2],
						curDir, 3, currentRemainingDistance - 1))
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
		boolean allowVines = (WorldGenForests.shouldPlantGrow(EnumForestPlant.VINE, rainfall, temperatureAvg,
				evt, random));
		boolean lost = BlockBranch.shouldLoseLeaf(world, xCoord, yCoord, zCoord, random, block2);
		boolean defLost = BlockBranch.shouldDefinitelyLoseLeaf(world, xCoord, yCoord, zCoord, block2);
		for (int i = -(radius + 1); i <= (radius + 1); i++)
		{
			for (int j = 0; j <= (radius + 1); j++)
			{
				for (int k = -(radius + 1); k <= (radius + 1); k++)
				{
					if (world.getBlock(xCoord + i, yCoord + j, zCoord + k).isReplaceable(world, xCoord + i, yCoord + j,
							zCoord + k) && i * i + j * j + k * k < radius * radius && !defLost && (!lost || random
									.nextInt(3) == 0))
					{
						world.setBlock(xCoord + i, yCoord + j, zCoord + k, leafBlock, treeId, 2);
						if (!fromSapling && !conversionMatrix[16 + (xCoord + i) - tempSourceX][16 + (zCoord + k) - tempSourceZ])
						{
							convertGrassToDirt(world, xCoord + i, yCoord + j, zCoord + k, height);
							conversionMatrix[16 + (xCoord + i) - tempSourceX][16 + (zCoord + k) - tempSourceZ] = true;
						}
					}
					else if(j==0 && world.getBlock(xCoord + i, yCoord + j, zCoord + k).isAir(world, xCoord + i, yCoord + j,
							zCoord + k) && !defLost && allowVines)
					{
						int distFromTrunk_X = tempSourceX-(xCoord+i);
						int distFromTrunk_Z = tempSourceZ-(zCoord+k);
						//Make sure we're not too high and not too close to the trunk
						boolean outerLim = !this.fromSapling && yCoord < this.tempSourceY + this.height + 2 && distFromTrunk_X*distFromTrunk_X + distFromTrunk_Z*distFromTrunk_Z >= radius*radius;
						if (j != 0 && outerLim)
						{
							outerLim = false;
						}
						// If we're on the bottom and the most distant from the
						// trunk
						if (!(outerLim && ((i+1) * (i+1) + k * k <= radius * radius || (i-1) * (i-1) + k * k <= radius * radius ||i*i + (k+1) * (k+1) <=radius * radius || (i) * (i) + (k-1) * (k-1) <= radius * radius )))
						{
							outerLim = false;
						}
						// handle whether we should place vines instead.
						if (outerLim && random.nextInt(10)==0)
						{
						//	world.setBlock(xCoord + i, yCoord + j + 0, zCoord + k, Blocks.cobblestone, 0, 2);
							int meta = 0;
							boolean north_south = Math.abs(k) > Math.abs(i);
							if(north_south)
							{
								meta = k<0?1:4;
							}
							else
							{
								meta = i>=0?2:8;
							}
							world.setBlock(xCoord + i, yCoord + j, zCoord + k, TFCBlocks.vine, meta, 2);
							int dMax = -(random.nextInt(7) + 2);
							for (int depth = -1; depth > dMax && world
									.getBlock(xCoord + i, yCoord + j + depth, zCoord + k)
									.isAir(world, xCoord + i, yCoord + j + depth, zCoord + k); depth--)
							{
								
								if (world.isAirBlock(xCoord + i, yCoord + j + depth, zCoord + k))
								{
									world.setBlock(xCoord + i, yCoord + j + depth, zCoord + k, TFCBlocks.vine, meta, 2);
								}
								else
								{
									break;
								}
							}
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
		int distFromEdge = 2;
		// We don't want to generate too close to chunk boundaries, because that
		// gets out of hand
		this.rainfall = TFC_Climate.getRainfall(world, xCoord, yCoord, zCoord);
		this.temperatureAvg = TFC_Climate.getBioTemperatureHeight(world, xCoord, yCoord, zCoord);
		this.evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(xCoord, zCoord).floatdata1;
		if ((xCoord % 16 > 16 - distFromEdge || Math.abs(
				xCoord) % 16 < distFromEdge || zCoord % 16 > 16 - distFromEdge || Math.abs(zCoord) % 16 < distFromEdge)&&!fromSapling)
		{
			return false;
		}
		height = random.nextInt(12) + 9;
		this.tempSourceX = xCoord;
		this.tempSourceY = yCoord;
		this.tempSourceZ = zCoord;

		conversionMatrix = new boolean[32][32];

		if (yCoord < 1 || yCoord + height + 1 > world.getHeight())
		{
			return false;
		}

		boolean flag = true;
		for (int y = yCoord; y <= yCoord + 1 + height; y++)
		{
			byte byte0 = 1;
			if (y == yCoord)
			{
				byte0 = 0;
			}
			if (y >= yCoord + 1 + height - 2)
			{
				byte0 = 2;
			}
			Block j3;
			for (int x = xCoord - byte0; x <= xCoord + byte0 && flag; x++)
			{
				for (int z = zCoord - byte0; z <= zCoord + byte0 && flag; z++)
				{
					if (y >= 0 && y + height < world.getHeight())
					{
						j3 = world.getBlock(x, y, z);
						if (!j3.isAir(world, x, y, z) && !j3.canBeReplacedByLeaves(world, x, y, z) && !(j3 instanceof BlockCustomLeaves))
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

		if (block2)
		{
			for (int l1 = 0; l1 < height; l1++)
			{
				setBlockAndNotifyAdequately(world, xCoord, yCoord + l1, zCoord, TFCBlocks.branch2__y_, treeId);
				tryDoMoss(world, random, xCoord, yCoord + l1, zCoord);
			}
		}
		else
		{
			for (int l1 = 0; l1 < height; l1++)
			{
				setBlockAndNotifyAdequately(world, xCoord, yCoord + l1, zCoord, TFCBlocks.branch__y_, treeId);
				tryDoMoss(world, random, xCoord, yCoord + l1, zCoord);
			}
		}

		int extraBranchLength = 3;
		// if(treeId == 0)
		// {
		generateRandomBranches(world, random, xCoord, yCoord + height - 1 - random.nextInt(3), zCoord,
				new int[] { -1, 1, -1 }, 1, random.nextInt(3) + extraBranchLength);
		generateRandomBranches(world, random, xCoord, yCoord + height - 1 - random.nextInt(3), zCoord,
				new int[] { 1, 1, -1 }, 1, random.nextInt(3) + extraBranchLength);
		generateRandomBranches(world, random, xCoord, yCoord + height - 1 - random.nextInt(3), zCoord,
				new int[] { 1, 1, 1 }, 1, random.nextInt(3) + extraBranchLength);
		generateRandomBranches(world, random, xCoord, yCoord + height - 1 - random.nextInt(3), zCoord,
				new int[] { -1, 1, 1 }, 1, random.nextInt(3) + extraBranchLength);

		return true;
	}

}
