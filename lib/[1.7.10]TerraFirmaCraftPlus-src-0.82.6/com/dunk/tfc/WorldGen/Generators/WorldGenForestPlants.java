package com.dunk.tfc.WorldGen.Generators;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockUndergrowth;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenForestPlants extends WorldGenerator
{
	private Block plant;

	public WorldGenForestPlants(Block b)
	{
		this.plant = b;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		Block b = world.blockExists(x, y, z) ? world.getBlock(x, y, z) : null;
		if (b == null)
		{
			return false;
		}
		if (plant == TFCBlocks.moss)
		{
			float rainfall = TFC_Climate.getRainfall(world, x, y, z);
			// We make 3 attempts. We pick a random spot in a 125 block area. If
			// that spot is legal, we generate moss. Otherwise we don't.
			// Moss generates slightly differently
			Block b_x, b_X, b_y, b_z, b_Z;
			for (int c = 0; c <= 25; c++)
			{
				int i = 0, j = 0, k = 0;
				if (rand.nextInt(4) == 0)
				{
					i = rand.nextInt(5) - 2;
					j = rand.nextInt(5) - 1;
					k = rand.nextInt(5) - 2;
				}

				// We check >1 and < 255 because we may do -1 or +1 after.
				if (j + y > 1 && j + y < 255
						&& world.getBlock(i + x, j + y, k + z).isReplaceable(world, i + x, j + y, k + z) && world.getBlock(i + x, j + y, k + z).getMaterial() != Material.water)
				{
					b_z = world.getBlock(x + i, y + j, k + z - 1);
					b_Z = world.getBlock(x + i, y + j, k + z + 1);
					if (rainfall < 4000)
					{
						if (z < 0 && (b_Z.isOpaqueCube() || b_Z instanceof BlockBranch))
						{
							world.setBlock(x + i, y + j, z + k, plant, 0, 2);
							return true;
						}
						else if (z >= 0 && (b_z.isOpaqueCube() || b_z instanceof BlockBranch))
						{
							world.setBlock(x + i, y + j, z + k, plant, 0, 2);
							return true;
						}
					}
					else
					{
						b_x = world.getBlock(x + i - 1, y + j, k + z);
						b_X = world.getBlock(x + i + 1, y + j, k + z);
						b_y = world.getBlock(x + i, y + j - 1, k + z);
						if ((b_Z.isOpaqueCube() || b_Z instanceof BlockBranch)
								|| (b_z.isOpaqueCube() || b_z instanceof BlockBranch)
								|| (b_x.isOpaqueCube() || b_x instanceof BlockBranch)
								|| (b_X.isOpaqueCube() || b_X instanceof BlockBranch)
								|| (b_y.isOpaqueCube() || b_y instanceof BlockBranch))
						{
							world.setBlock(x + i, y + j, z + k, plant, 0, 2);
							return true;
						}
					}
				}

			}
		}
		else if (b.isReplaceable(world, x, y, z) && !(b instanceof BlockUndergrowth) && TFC_Core.isSoil(world.getBlock(x, y - 1, z))
				&& !b.getMaterial().isLiquid())
		{
			world.setBlock(x, y, z, plant, 0, 2);
			return true;
		}
		return false;
	}

}
