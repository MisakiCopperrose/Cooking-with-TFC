package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WorldGenTreeBase extends WorldGenerator
{
	protected final int treeId;
	protected boolean block2;
	protected int height;
	protected boolean fromSapling;

	protected int tempSourceX, tempSourceZ;

	protected boolean[][] conversionMatrix = new boolean[32][32];

	protected Block leafBlock;

	protected final int[][] initialDirections = new int[][] { new int[] { -1, 0, -1 }, new int[] { 0, 0, -1 },
		new int[] { 1, 0, -1 }, new int[] { 1, 0, 0 }, new int[] { 1, 0, 1 }, new int[] { 0, 0, 1 },
		new int[] { -1, 0, 1 }, new int[] { -1, 0, 0 } };
	
	protected final int[][] directions = new int[][] { new int[] { -1, 0, -1 }, new int[] { -1, 0, 0 }, new int[] { -1, 0, 1 },
			new int[] { 1, 0, -1 }, new int[] { 0, 0, -1 }, new int[] { 1, 0, 1 }, new int[] { 0, 0, 1 },
			new int[] { 1, 0, 0 }, new int[] { -1, 1, -1 }, new int[] { -1, 1, 0 }, new int[] { -1, 1, 1 },
			new int[] { 1, 1, -1 }, new int[] { 0, 1, -1 }, new int[] { 1, 1, 1 }, new int[] { 0, 1, 1 },
			new int[] { 1, 1, 0 } };

	public WorldGenTreeBase(boolean bb, int id, boolean sap)
	{
		super(bb);
		if (id > 15)
		{
			id %= 16;
			block2 = true;
		}
		this.fromSapling = sap;
		treeId = id;
		leafBlock = block2 ? TFCBlocks.leaves2 : TFCBlocks.leaves;
	}

	@Override
	public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		return false;
	}

	abstract protected boolean generateRandomBranches(World world, Random rand, int xCoord, int yCoord, int zCoord,
			int[] currentDirection, int numBranches, int remainingDistance);
	
	protected boolean shouldSubtractDistance(Random rand)
	{
		//This is math. Basically, the trees look funky on daigonals because the diagonal length is greater than
		//the straight length. It is longer by sqrt(2) = 1.414
		//So in order to account for this, we must subtract from our overall length
		//How often we subtract should be such that the chance of subtracing = 1/(sqrt(2))
		//in the situation rand.nextInt(x) > y, we have y + a = x
		//where a is the number of values > y. 
		//Thus, the chance of succeeding is a/x = 0.707...
		//given x - y = a, we divide everything by x, giving 1 - (y/x) = 0.707
		//Move the integers to one side and we get the ratio (y/x) = 0.29289
		//The inverse of 0.29298 is 3.414
		if (rand.nextInt(3414) > 1000)
		{
			return true;
		}
		return false;
	}
	
	protected boolean tryDoMoss(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int zMoss = 0;

		if (zCoord < 0)
			zMoss = -1;
		else
			zMoss = 1;
		float rain =  TFC_Climate.getRainfall(world, xCoord, yCoord, zCoord + zMoss);	
		
		boolean mossy = rain > 1000 && !fromSapling;
		
		boolean addedMoss = false;
		
		if(mossy && random.nextInt(120)<(rain/100) && world.isAirBlock(xCoord, yCoord, zCoord+zMoss))
		{
			setBlockAndNotifyAdequately(world, xCoord, yCoord, zCoord+zMoss, TFCBlocks.moss, 0);
			addedMoss = true;
		}
		if(mossy & random.nextInt(120)<(rain/150) && world.isAirBlock(xCoord+1, yCoord, zCoord))
		{
			setBlockAndNotifyAdequately(world, xCoord+1, yCoord, zCoord, TFCBlocks.moss, 0);
			addedMoss = true;
		}
		if(mossy & random.nextInt(120)<(rain/150) && world.isAirBlock(xCoord-1, yCoord, zCoord))
		{
			setBlockAndNotifyAdequately(world, xCoord-1, yCoord, zCoord, TFCBlocks.moss, 0);
			addedMoss = true;
		}
		if(mossy && random.nextInt(120)<(rain/500) && world.isAirBlock(xCoord, yCoord, zCoord-zMoss))
		{
			setBlockAndNotifyAdequately(world, xCoord, yCoord, zCoord-zMoss, TFCBlocks.moss, 0);
			addedMoss = true;
		}
		return addedMoss;
	}
	
	public boolean placeLeaves(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int radius = height > 4?3:2;
		boolean lost = BlockBranch.shouldLoseLeaf(world, xCoord , yCoord, zCoord , random,block2);
		boolean defLost = BlockBranch.shouldDefinitelyLoseLeaf(world, xCoord, yCoord, zCoord, block2);
		for (int i = -(radius+1); i <= (radius+1); i++)
		{
			for (int j = 0; j <= (radius+1); j++)
			{
				for (int k = -(radius+1); k <= (radius+1); k++)
				{
					if (i * i + j * j + k * k <= radius * radius && world.blockExists(xCoord + i, yCoord + j, zCoord + k) && world.getBlock(xCoord + i, yCoord + j, zCoord + k).isReplaceable(world, xCoord + i, yCoord + j,
							zCoord + k) &&  !defLost && (!lost || random.nextInt(4)==0))
					{
						world.setBlock(xCoord + i, yCoord + j, zCoord + k, leafBlock, treeId, 2);
						if (!fromSapling && !conversionMatrix[16+(xCoord + i)-tempSourceX][16+(zCoord+k)-tempSourceZ])
						{
							convertGrassToDirt(world, xCoord + i, yCoord + j, zCoord + k, height);
							conversionMatrix[16+(xCoord + i)-tempSourceX][16+(zCoord+k)-tempSourceZ] = true;
						}
					}
				}
			}
		}
		return true;
	}

	protected void convertGrassToDirt(World world, int x, int y, int z, int height)
	{
		Block b;
		Block bUp;
		int meta;
		for (int i = 0; i > -(height + 7) && i + y > 0; i--)
		{
			b = world.getBlock(x, i + y, z);
			bUp = world.getBlock(x, i + y + 1, z);
			if (TFC_Core.isGrass(b))
			{
				meta = world.getBlockMetadata(x, y + i, z);
				world.setBlock(x, y + i, z, TFC_Core.getTypeForDirtFromGrass(b), meta, 2);
				if (bUp.canBeReplacedByLeaves(world, x, y + i + 1, z) && bUp != TFCBlocks.snow)
				{
					world.setBlock(x, y + i + 1, z, TFCBlocks.leafLitter, 0, 2);
				}
				return;
			}
		}
	}

}
