package com.dunk.tfc.WorldGen;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Chunkdata.ChunkData;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Entities.Mobs.EntityFishTFC;
import com.dunk.tfc.Entities.Mobs.EntityHorseTFC;
import com.dunk.tfc.api.Enums.EnumAnimalSpawn;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public final class SpawnerAnimalsTFC
{
	/**
	 * Returns whether or not the specified creature type can spawn at the specified location.
	 */
	public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType par0EnumCreatureType, World par1World, int par2, int par3, int par4)
	{
		if (par0EnumCreatureType.getCreatureMaterial() == Material.water)
		{
			return par1World.getBlock(par2, par3, par4).getMaterial().isLiquid() &&
					/*par1World.getBlock(par2, par3 - 1, par4).getMaterial().isLiquid() &&*/
					!par1World.getBlock(par2, par3 + 1, par4).isNormalCube();
		}
		else if (!World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4))
		{
			return false;
		}
		else
		{
			Block b = par1World.getBlock(par2, par3 - 1, par4);
			boolean spawnBlock = b != null && b.canCreatureSpawn(par0EnumCreatureType, par1World, par2, par3 - 1, par4);
			return spawnBlock && b != Blocks.bedrock &&
					!par1World.getBlock(par2, par3, par4).isNormalCube() &&
					!par1World.getBlock(par2, par3, par4).getMaterial().isLiquid() &&
					!par1World.getBlock(par2, par3 + 1, par4).isNormalCube();
		}
	}

	/**
	 * Called during chunk generation to spawn initial creatures.
	 */
	@SuppressWarnings("unchecked")
	public static void performWorldGenSpawning(World world, TFCBiome biome, int par2, int par3, int par4, int par5, Random par6Random)
	{
		//List list = TFCChunkProviderGenerate.getCreatureSpawnsByChunk(world, biome, par2, par3);//par1BiomeGenBase.getSpawnableList(EnumCreatureType.creature);
		List list = TFCChunkProviderGenerate.getLegalSpawnsByChunk(world, biome, par2, par3);//par1BiomeGenBase.getSpawnableList(EnumCreatureType.creature);
		if (!list.isEmpty())
		{
			/*if(canCreatureTypeSpawnAtLocation(EnumCreatureType.waterCreature, world, par2, world.getTopSolidOrLiquidBlock(par2, par3), par3))
				{
				System.out.println("water spawnable");
					}
			else
			{
				int y =  world.getTopSolidOrLiquidBlock(par2, par3);
				System.out.println(world.getBlock(par2,y, par3).getUnlocalizedName() +": " + world.getBlock(par2,y-1, par3).getUnlocalizedName());
			}*/
			EnumAnimalSpawn spawnlistentry = (EnumAnimalSpawn) list.get(world.rand.nextInt(list.size()));
			while (par6Random.nextFloat() < (spawnlistentry == EnumAnimalSpawn.BASS?0.4f:biome.getSpawningChance()*0.2f))
			{
				
				IEntityLivingData entitylivingdata = null;
				int i1 = spawnlistentry.minGroupCount + par6Random.nextInt(1 + spawnlistentry.maxGroupCount - spawnlistentry.minGroupCount);
				int j1 = par2 + par6Random.nextInt(par4);
				int k1 = par3 + par6Random.nextInt(par5);
				int l1 = j1;
				int i2 = k1;
				
				for (int j2 = 0; j2 < i1; ++j2)
				{
					boolean flag = false;
					for (int k2 = 0; !flag && k2 < 4; ++k2)
					{
						int l2 = world.getTopSolidOrLiquidBlock(j1, k1);
						if(world.getBlock(j1, l2+1, k1).getMaterial().isLiquid())
						{
							l2++;
						}
						if (canCreatureTypeSpawnAtLocation(EnumAnimalSpawn.BASS == spawnlistentry?EnumCreatureType.waterCreature:EnumCreatureType.creature, world, j1, l2, k1))
						{
							EntityLiving entityliving;
							try
							{
								if(spawnlistentry == EnumAnimalSpawn.ASS)
								{
									entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class,int.class}).newInstance(new Object[] {world,1});
								}
								else if(spawnlistentry == EnumAnimalSpawn.WILD_HORSE || spawnlistentry == EnumAnimalSpawn.ZEBRA)
								{
									entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class,int.class}).newInstance(new Object[] {world,0});
								}
								else
								{
									if(spawnlistentry.entityClass == EntityHorseTFC.class)
									{
										System.out.println("Uncaught horse!!");
									}
									entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
								}
								if(spawnlistentry == EnumAnimalSpawn.BASS && biome == TFCBiome.RIVER)
								{
									//System.out.println(j1 +", " + l2 + ", " + k1 + ": " + spawnlistentry.name + ", " + biome.biomeName);
								}
							}
							catch (Exception exception)
							{
								TerraFirmaCraft.LOG.catching(exception);
								continue;
							}
							if(entityliving instanceof EntityFishTFC && entityliving.posY > 0){
								if(entityliving.getRNG().nextInt((int)ChunkData.FISH_POP_MAX) > TFC_Core.getCDM(world).getFishPop(j1 >> 4, k1 >> 4)){
									return;
								}
							}

							float f = j1 + 0.5F;
							float f1 = l2;
							float f2 = k1 + 0.5F;
							entityliving.setLocationAndAngles(f, f1, f2, par6Random.nextFloat() * 360.0F, 0.0F);
							world.spawnEntityInWorld(entityliving);
							entitylivingdata = entityliving.onSpawnWithEgg(entitylivingdata);
							if(spawnlistentry == EnumAnimalSpawn.ASS)
							{
								((EntityHorseTFC)entityliving).setHorseType(1);;
							}
							else if(spawnlistentry == EnumAnimalSpawn.WILD_HORSE || spawnlistentry == EnumAnimalSpawn.ZEBRA)
							{
								((EntityHorseTFC)entityliving).setHorseType(0);;
							}
							flag = true;
						}

						j1 += par6Random.nextInt(5) - par6Random.nextInt(5);
						for (k1 += par6Random.nextInt(5) - par6Random.nextInt(5);
								j1 < par2 || j1 >= par2 + par4 || k1 < par3 || k1 >= par3 + par4;
								k1 = i2 + par6Random.nextInt(5) - par6Random.nextInt(5))
						{
							j1 = l1 + par6Random.nextInt(5) - par6Random.nextInt(5);
						}
					}
				}
				spawnlistentry = (EnumAnimalSpawn) list.get(world.rand.nextInt(list.size()));
			}
		}
	}
}
