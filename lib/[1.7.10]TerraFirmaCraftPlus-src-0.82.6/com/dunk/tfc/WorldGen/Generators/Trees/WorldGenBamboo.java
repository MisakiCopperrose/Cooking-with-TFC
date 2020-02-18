package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBamboo extends WorldGenerator
{
	private final int treeId;

	public WorldGenBamboo(boolean flag, int id)
	{
		super(flag);
		treeId = id;
	}

	@Override
	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{

		int h = random.nextInt(15) + 4; // Height before lean
		if (TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord))
				&& world.getBlock(xCoord, yCoord, zCoord).isReplaceable(world, xCoord, yCoord, zCoord) && world.getBlock(xCoord, yCoord, zCoord).getMaterial() != Material.water)
		{
			for (int i = 0; i < h; i++)
			{
				if(!world.blockExists(xCoord, yCoord + i, zCoord))
				{
					return false;
				}
			}
			for (int i = 0; i < h; i++)
			{
				setBlockAndNotifyAdequately(world, xCoord, yCoord + i, zCoord, TFCBlocks.branch2__y_, treeId);
			}
			setBlockAndNotifyAdequately(world, xCoord, yCoord + h, zCoord, TFCBlocks.branchEnd2__y_, treeId);
			for (int i = -2; i < (h > 10 ? 2 : 1); i++)
			{
				if (world.getBlock(xCoord - 1, yCoord+h+i, zCoord).canBeReplacedByLeaves(world, xCoord - 1, yCoord+h+i, zCoord))
				{
					setBlockAndNotifyAdequately(world, xCoord-1, yCoord +h+i, zCoord, TFCBlocks.leaves2, 8);
				}
				if (world.getBlock(xCoord + 1, yCoord+h+i, zCoord).canBeReplacedByLeaves(world,xCoord + 1, yCoord+h+i, zCoord))
				{
					setBlockAndNotifyAdequately(world, xCoord+1, yCoord +h+i, zCoord, TFCBlocks.leaves2, 8);
				}
				if (world.getBlock(xCoord, yCoord+h+i, zCoord-1).canBeReplacedByLeaves(world, xCoord, yCoord+h+i, zCoord-1))
				{
					setBlockAndNotifyAdequately(world, xCoord, yCoord +h+i, zCoord-1, TFCBlocks.leaves2, 8);
				}
				if (world.getBlock(xCoord, yCoord+h+i, zCoord+1).canBeReplacedByLeaves(world, xCoord, yCoord+h+i, zCoord+1))
				{
					setBlockAndNotifyAdequately(world, xCoord, yCoord +h+i, zCoord+1, TFCBlocks.leaves2, 8);
				}
				if (world.getBlock(xCoord, yCoord+h+i, zCoord).canBeReplacedByLeaves(world, xCoord, yCoord+h+i, zCoord))
				{
					setBlockAndNotifyAdequately(world, xCoord, yCoord +h+i, zCoord, TFCBlocks.leaves2, 8);
				}
				
			}
			if (world.getBlock(xCoord, yCoord+h+(h > 10 ? 2 : 1), zCoord).canBeReplacedByLeaves(world, xCoord, yCoord+h+(h > 10 ? 2 : 1), zCoord))
			{
				setBlockAndNotifyAdequately(world, xCoord, yCoord +h+(h > 10 ? 2 : 1), zCoord, TFCBlocks.leaves2, 8);
			}
			return true;
		}
		return false;
	}

}
