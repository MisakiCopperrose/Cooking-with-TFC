package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomCedarTrees extends WorldGenTreeBase
{

	public WorldGenCustomCedarTrees(boolean flag, int id, boolean sap)
	{
		super(flag,id,sap);
	}

	@Override
	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		height = random.nextInt(6) + 3;
		if (yCoord < 1 || yCoord + height + 1 > world.getHeight())
			return false;

		if(!TFC_Core.isSoil(world.getBlock(xCoord, yCoord-1, zCoord)))
		{
			return false;
		}
		
		boolean flag = true;
		for (int y = yCoord; y <= yCoord + 1 + height; y++)
		{
			byte byte0 = 1;
			if (y == yCoord)
				byte0 = 0;
			if (y >= yCoord + 1 + height - 2)
				byte0 = 2;

			for (int x = xCoord - byte0; x <= xCoord + byte0 && flag; x++)
			{
				for (int z = zCoord - byte0; z <= zCoord + byte0 && flag; z++)
				{
					if (y >= 0 && y < world.getHeight())
					{
						Block j3 = world.getBlock(x, y, z);
						if (!j3.isAir(world, x, y, z) && !j3.canBeReplacedByLeaves(world, x, y, z))
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
		for (int l1 = 0; l1 < height; l1++)
		{
			setBlockAndNotifyAdequately(world, xCoord, yCoord + l1, zCoord, TFCBlocks.branch__y_, treeId);
		}
		int branchPattern = random.nextInt(8);
		for (int i = 1; i < height; i++)
		{
			int numBranchesHere = random.nextInt(3) + 5;
			int extraBranchLength = 1;
			//for (int j = 0; j < numBranchesHere; j++)
			//{
				//if (random.nextInt(4) != 0)
				//{
					generateRandomBranches(world, random, xCoord, yCoord + i, zCoord,
							new int[]{0,1,0}, numBranchesHere, extraBranchLength);
				//}
			//}
			branchPattern += numBranchesHere;
			branchPattern %= 8;
		}

		Block var3 = world.getBlock(xCoord, yCoord - 1, zCoord);
		if (!(TFC_Core.isSoil(var3)) || yCoord >= world.getHeight() - height - 1)
			return false;

		// Here we crate the tree trunk
		
		setBlockAndNotifyAdequately(world, xCoord, yCoord + height, zCoord, TFCBlocks.branchEnd__y_, treeId);
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
			int index = rand.nextInt(validDirections.length);
			while (!validDirections[index])
			{
				index = rand.nextInt(validDirections.length);
			}
			validDirections[index] = false;
			numValidDirections--;
			curDir = initialDirections[index];

			if (curDir[0] * curDir[2] != 0)
			{
				//currentRemainingDistance--;
			}
			if (world.getBlock(xCoord + curDir[0], yCoord, zCoord + curDir[1]).isReplaceable(world, xCoord + curDir[0],
					yCoord, zCoord + curDir[1]) || world.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]).getMaterial() == Material.leaves)
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
				if (world.getBlock(xCoord + curDir[0], yCoord - 1, zCoord + curDir[2]) == theBranch)
				{
					//i--;
				//	continue;
				}
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
					//System.out.println("placed branch in dir " + curDir[0] + ", "  +curDir[2]);
					
					placedBranch = true;
				}
			}
		}
		return placedBranch;
	}

	@Override
	protected void convertGrassToDirt(World world, int x, int y, int z, int height)
	{
		for (int i = 0; i > -(height + 7) && i + y > 0; i--)
		{
			Block b = world.getBlock(x, i + y, z);
			Block bUp = world.getBlock(x, i + y + 1, z);
			if (TFC_Core.isGrass(b))
			{
				int meta = world.getBlockMetadata(x, y + i, z);
				world.setBlock(x, y + i, z, TFC_Core.getTypeForDirtFromGrass(b));
				world.setBlockMetadataWithNotify(x, y + i, z, meta, 2);
				if (bUp.isReplaceable(world, x, y + i + 1, z) && bUp != TFCBlocks.snow)
				{
					world.setBlock(x, y + i + 1, z, TFCBlocks.leafLitter);
					world.setBlockMetadataWithNotify(x, y + i + 1, z, 1, 2);
				}
				return;
			}
		}
	}
}
