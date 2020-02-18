package com.dunk.tfc.WorldGen.Generators;

import java.util.Random;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.WorldGen.DataLayer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomSand extends WorldGenerator
{
	/** Stores Block for WorldGenSand */
	private Block sandBlock;

	/** The maximum radius used when generating a patch of blocks. */
	private int radius;

	public WorldGenCustomSand(int par1, Block par2)
	{
		this.sandBlock = par2;
		this.radius = par1;
	}

	@Override
	public boolean generate(World world, Random par2Random, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).getMaterial() != Material.water)
		{
			return false;
		}
		else
		{
			DataLayer dl = TFC_Climate.getRockLayer(world, x, y, z, 0);
			//sandBlock = TFC_Core.getTypeForSand(dl.data1);
			float bioTemp = TFC_Climate.getBioTemperature(world, x, z);
			int sandMeta = dl.data2;
			if(bioTemp > 25)
			{
				sandBlock = TFC_Core.getTypeForSand(0);
				sandMeta = 10;
			}
			else if(bioTemp <=25 && bioTemp > 1.5)
			{
				sandBlock = TFC_Core.getTypeForSand(0);
				sandMeta = 15;
			}
			
			int var6 = par2Random.nextInt(this.radius - 2) + 2;
			byte var7 = 2;

			for (int var8 = x - var6; var8 <= x + var6; ++var8)
			{
				for (int var9 = z - var6; var9 <= z + var6; ++var9)
				{
					int var10 = var8 - x;
					int var11 = var9 - z;
					if (var10 * var10 + var11 * var11 <= var6 * var6)
					{
						for (int var12 = y - var7; var12 <= y + var7; ++var12)
						{
							Block var13 = world.getBlock(var8, var12, var9);
							boolean notCorrectSoil = !TFC_Core.isSoil(var13) && !TFC_Core.isSand(var13) ;
							if (!notCorrectSoil)
								world.setBlock(var8, var12, var9, sandBlock, /*dl.data2*/sandMeta, 0x2);
						}
					}
				}
			}
			return true;
		}
	}
}
