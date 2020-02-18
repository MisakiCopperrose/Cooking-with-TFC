package com.dunk.tfc.Core;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.BlockChimney;
import com.dunk.tfc.Blocks.BlockInvisible;
import com.dunk.tfc.Blocks.BlockTileRoof;
import com.dunk.tfc.Blocks.Devices.BlockTeepee;
import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockBranch2;
import com.dunk.tfc.Blocks.Flora.BlockLogNatural;
import com.dunk.tfc.Blocks.Flora.BlockLogNatural2;
import com.dunk.tfc.Blocks.Terrain.BlockStone;
import com.dunk.tfc.Chunkdata.ChunkData;
import com.dunk.tfc.Chunkdata.ChunkDataManager;
import com.dunk.tfc.Core.Player.BodyTempStats;
import com.dunk.tfc.Core.Player.FoodStatsTFC;
import com.dunk.tfc.Core.Player.InventoryPlayerTFC;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Core.Player.SkillStats;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.Items.ItemOre;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.dunk.tfc.TileEntities.TEFirepit;
import com.dunk.tfc.TileEntities.TEMetalSheet;
import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.TFC_ItemHeat;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Enums.EnumFuelMaterial;
import com.dunk.tfc.api.Interfaces.IEquipable;
import com.dunk.tfc.api.Interfaces.IEquipable.ClothingType;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.Interfaces.IHeatSource;
import com.dunk.tfc.api.Interfaces.IHeatSourceTE;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.util.ForgeDirection;

public class TFC_Core
{
	private static Map<Integer, ChunkDataManager> cdmMap = new HashMap<Integer, ChunkDataManager>();
	public static boolean preventEntityDataUpdate;
	public static Block[][][][] branchMap = new Block[3][3][3][4];

	public static int getClothingUpdateFrequency()
	{
		return 10;
	}

	public static ChunkDataManager getCDM(World world)
	{
		int key = world.isRemote ? 128 | world.provider.dimensionId : world.provider.dimensionId;
		return cdmMap.get(key);
	}

	public static ChunkDataManager addCDM(World world)
	{
		int key = world.isRemote ? 128 | world.provider.dimensionId : world.provider.dimensionId;
		if (!cdmMap.containsKey(key))
			return cdmMap.put(key, new ChunkDataManager(world));
		else
			return cdmMap.get(key);
	}

	public static boolean feetInAnyWater(EntityPlayer player, World world)
	{
		int x = (int) player.posX;
		int y = (int) player.posY - (world.isRemote ? 1 : 0);
		// For some stupid reason, THIS is how you accurately get the block
		// underneath the player.
		int z = (int) (player.posZ - (world.isRemote ? 0 : 1));
		Block b = TFC_Core.getCollidedLiquid(player, true);
		if (b != null)
		{
			return b.getMaterial() == Material.water && (player.ridingEntity != null ? headInAnyWater(player, world) : true);
		}
		return false;
	}

	public static boolean headInAnyWater(EntityPlayer player, World world)
	{
		int x = (int) player.posX;
		int y = (int) player.posY - (world.isRemote ? 1 : 0);
		// For some stupid reason, THIS is how you accurately get the block
		// underneath the player.
		int z = (int) player.posZ - (world.isRemote ? 0 : 1);
		Block b = TFC_Core.getCollidedLiquid(player, false);
		if (b != null)
		{
			return b.getMaterial() == Material.water;
		}
		return false;
	}

	public static boolean shouldUpdateLocalWarmthFromHeatSource(EntityPlayer player, World world)
	{
		int x = (int) player.posX;
		int y = (int) player.posY;
		// For some stupid reason, THIS is how you accurately get the block
		// underneath the player.
		int z = (int) player.posZ - 1;
		boolean done = false;
		int radius = 7;
		for (int i = -radius; i < radius && !done; i++)
		{
			for (int j = -radius; j < radius && !done; j++)
			{
				for (int k = -radius; k < radius && !done; k++)
				{
					float distance = (float) i * i + j * j + k * k;
					Block b = world.getBlock((int) x + i, (int) y + j, (int) z + k);
					if (b instanceof IHeatSource && distance <= ((IHeatSource) b).getHeatSourceRadius() * ((IHeatSource) b).getHeatSourceRadius()
					&& ((IHeatSource) b).getTileEntityType() != null)
					{
						IHeatSourceTE te = (IHeatSourceTE) world.getTileEntity((int) x + i, (int) y + j, (int) z + k);
						if (te.getHeatSourceTemp() > 0)
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static float getCoolthTemperatureAdjustmentPercentage(EntityPlayer player, World world)
	{
		int x = (int) player.posX;
		int y = (int) player.posY;
		// For some stupid reason, THIS is how you accurately get the block
		// underneath the player.
		int z = (int) player.posZ - 1;
		float percent = 0;
		boolean roofed = false;
		int naturalRoof = -7;
		for (int i = 0; i < 12 && !roofed; i++)
		{
			if (world.getBlock(x, y + i, z) instanceof BlockStone && y >= Global.SEALEVEL - 5)
			{
				naturalRoof++;
			}
			else if (naturalRoof > -7)
			{
				naturalRoof--;
			}
			if (world.getBlock(x, y + i, z) == TFCBlocks.tileRoof)
			{
				percent = Math.min(percent + 0.5f, 1f);
				roofed = true;
			}
			else if (world.getBlock(x, y + i, z) == TFCBlocks.slateRoof)
			{
				percent = Math.min(percent + 0.25f, 1f);
				roofed = true;
			}
			if (naturalRoof == 0)
			{
				percent = Math.min(percent + 0.125f, 1f);
				roofed = true;
			}
		}
		return percent;

	}

	public static float getWarmthTemperatureAdjustmentPercentage(EntityPlayer player, World world)
	{
		int x = (int) player.posX;
		int y = (int) player.posY;
		// For some stupid reason, THIS is how you accurately get the block
		// underneath the player.
		int z = (int) player.posZ - 1;

		Block feetBlock = TFC_Core.getCollidedLiquid(player, true);
		Block headBlock = TFC_Core.getCollidedLiquid(player, false);
		boolean feetInHotWater = feetBlock == TFCBlocks.hotWater || feetBlock == TFCBlocks.hotWaterStationary;
		boolean headInHotWater = headBlock != null && ( headBlock.getMaterial() == Material.water && (headBlock == TFCBlocks.hotWater || headBlock == TFCBlocks.hotWaterStationary));
		int radius = 7;
		float percent = 0;
		if (feetInHotWater)
		{
			percent++;
		}
		if (headInHotWater)
		{
			percent++;
		}

		// 5 blocks from a firepit
		// 7 blocks from a forge or fire?
		for (int i = -radius; i < radius && percent < 2; i++)
		{
			for (int j = -(2 * radius) + 2; j < 2 && percent < 2; j++)
			{
				for (int k = -radius; k < radius && percent < 2; k++)
				{
					float distance = (float) Math.sqrt(i * i + j * j + k * k);
					Block b = world.getBlock((int) x + i, (int) y + j, (int) z + k);
					if (b instanceof IHeatSource && distance <= ((IHeatSource) b).getHeatSourceRadius() && ((IHeatSource) b).getTileEntityType() != null)
					{
						IHeatSourceTE te = (IHeatSourceTE) world.getTileEntity((int) x + i, (int) y + j, (int) z + k);
						if (te.getHeatSourceTemp() > 0)
						{
							float f = Math.min(1f, te.getHeatSourceTemp() / 100f);
							if (distance <= 2)
							{
								percent += f;
							}
							else
							{
								percent += f / distance;
							}
						}
					}
					else if (b instanceof IHeatSource && distance <= ((IHeatSource) b).getHeatSourceRadius())
					{
						float f = 1f;
						if (distance <= 2)
						{
							percent += f;
						}
						else
						{
							percent += f / distance;
						}
					}
				}
			}
		}
		boolean roofed = false;
		int naturalRoof = -7;
		float teepeeWarmth = TFC_Core.getTeepeeWarmth(player);
		if(teepeeWarmth > 0)
		{
			roofed = true;
			percent = Math.min(percent + teepeeWarmth, 1f);
		}
		for (int i = 0; i < 12 && !roofed; i++)
		{
			if (world.getBlock(x, y + i, z) instanceof BlockStone && y >= Global.SEALEVEL - 5)
			{
				naturalRoof++;
			}
			else if (naturalRoof > -7)
			{
				naturalRoof--;
			}
			if (world.getBlock(x, y + i, z) == TFCBlocks.tileRoof || world.getBlock(x, y + i, z) == TFCBlocks.thatchRoof)
			{
				percent = Math.min(percent + 0.25f, 1f);
				roofed = true;
			}
			else if (world.getBlock(x, y + i, z) == TFCBlocks.slateRoof)
			{
				percent = Math.min(percent + 0.5f, 1f);
				roofed = true;
			}
			if (naturalRoof == 0)
			{
				percent = Math.min(percent + 0.25f, 1f);
				roofed = true;
			}
		}
		return percent;
	}

	public static boolean hasRoof(World world, int x, int y, int z)
	{
		for (int i = 0; i < 12; i++)
		{
			if (world.getBlock(x, y + i, z) instanceof BlockTileRoof)
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isOre(Block b)
	{
		return b == TFCBlocks.ore || b == TFCBlocks.ore1b;
	}

	// positive is actually negative
	public static int getTemperatureChangeFromEnvironment(EntityPlayer player, World world, boolean upperBodyDamp, boolean upperBodySoaked, boolean lowerBodyDamporSoaked)
	{
		int adj = 0;
		int x = (int) player.posX;
		int y = (int) player.posY;
		// For some stupid reason, THIS is how you accurately get the block
		// underneath the player.
		int z = (int) player.posZ - 1;
		Block feet = TFC_Core.getCollidedLiquid(player, true);
		Block head = TFC_Core.getCollidedLiquid(player, true);
		boolean feetInWater = feet != null && feet.getMaterial() == Material.water && world.getBlock((int) x, (int) y, (int) z) != TFCBlocks.hotWater 
		&& feet != TFCBlocks.hotWaterStationary;
		boolean headInWater = head != null && (head.getMaterial() == Material.water && head != TFCBlocks.hotWater && head != TFCBlocks.hotWaterStationary);

		boolean feetInHotWater = feet == TFCBlocks.hotWater || feet == TFCBlocks.hotWaterStationary;
		boolean headInHotWater = head != null && head.getMaterial() == Material.water && head == TFCBlocks.hotWater || head == TFCBlocks.hotWaterStationary;
		if (feetInHotWater)
		{
			adj += 5;
		}
		if (headInHotWater)
		{
			adj += 5;
		}
		if (feetInWater || lowerBodyDamporSoaked)
		{
			adj -= 5;
		}
		if (headInWater || upperBodySoaked)
		{
			adj -= 10;
		}
		else if (upperBodyDamp)
		{
			adj -= 5;
		}

		return adj;
	}

	// This will calculate the hour based on the celestial angle
	// It will return a number between 0 and 15 if it is daytime and 0 otherwise
	public static float getCelestialDaylight(IBlockAccess world, int x, int y, int z, long worldTime, float partialTick)
	{
		float angle = calculateLocalCelestialAngle(world, x, y, z, worldTime, partialTick);
		// day time is angle > 0.75 and < 0.25
		if (angle >= 0.72 || angle <= 0.28)
		{
			if (angle >= 0.72)
			{
				return (float) Math.min(15, ((angle - 0.72) * 100));
			}
			else
			{
				return (float) Math.min(15, ((0.28 - angle) * 100));
			}
		}
		else
		{
			return 0;
		}
	}

	public static boolean canMonsterBurn(EntityLivingBase monster)
	{
		if (TFCOptions.enableDefaultCelestialAngle)
		{
			return monster.worldObj.isDaytime();
		}
		float angle = calculateLocalCelestialAngle(monster.worldObj, (int) monster.posX, (int) monster.posY, (int) monster.posZ, TFC_Time.getTotalTicks(), 0);
		// if the angle says it's daytime, we burn
		return angle > 0;
	}
	
	public static float getTeepeeWarmth(Entity entity)
	{
		int i = MathHelper.floor_double(entity.boundingBox.minX - 0.001D);
		int j = MathHelper.floor_double(entity.boundingBox.minY - 0.001D);
		int k = MathHelper.floor_double(entity.boundingBox.minZ - 0.001D);
		int l = MathHelper.floor_double(entity.boundingBox.maxX + 0.001D);
		int i1 = MathHelper.floor_double(entity.boundingBox.maxY + 0.001D);
		int j1 = MathHelper.floor_double(entity.boundingBox.maxZ + 0.001D);

		if (entity.worldObj.checkChunksExist(i, j, k, l, i1, j1))
		{
			for (int k1 = i; k1 <= l; ++k1)
			{
				for (int l1 = j; l1 <= i1; ++l1)
				{
					for (int i2 = k; i2 <= j1; ++i2)
					{
						Block block = entity.worldObj.getBlock(k1, l1, i2);

						try
						{
							if (block instanceof BlockTeepee)
							{
								return block == TFCBlocks.teepee ? 0.25f : block == TFCBlocks.wolfTeepee ? 0.375f : block == TFCBlocks.bearTeepee? 0.5f : 0.25f;
							}
						}
						catch (Throwable throwable)
						{
							CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
							CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
							CrashReportCategory.func_147153_a(crashreportcategory, k1, l1, i2, block, entity.worldObj.getBlockMetadata(k1, l1, i2));
							throw new ReportedException(crashreport);
						}
					}
				}
			}
		}
		return 0f;
	}

	// This code will find liquids that we are colliding with
	public static Block getCollidedLiquid(Entity entity, boolean feet)
	{
		int i = MathHelper.floor_double(entity.boundingBox.minX + 0.001D);
		int j = MathHelper.floor_double(entity.boundingBox.minY + 0.001D);
		int k = MathHelper.floor_double(entity.boundingBox.minZ + 0.001D);
		int l = MathHelper.floor_double(entity.boundingBox.maxX - 0.001D);
		int i1 = MathHelper.floor_double(entity.boundingBox.maxY - 0.001D);
		int j1 = MathHelper.floor_double(entity.boundingBox.maxZ - 0.001D);

		if (feet)
		{
			i1 = j + 1;
		}
		else
		{
			i1++;
			j = i1 - 1;
		}

		if(entity.isRiding())
		{
			i1++;
			j++;
		}
		
		if (entity.worldObj.checkChunksExist(i, j, k, l, i1, j1))
		{
			for (int k1 = i; k1 <= l; ++k1)
			{
				for (int l1 = j; l1 <= i1; ++l1)
				{
					for (int i2 = k; i2 <= j1; ++i2)
					{
						Block block = entity.worldObj.getBlock(k1, l1, i2);

						try
						{
							if (block instanceof BlockLiquid)
							{
								return block;
							}
						}
						catch (Throwable throwable)
						{
							CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
							CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
							CrashReportCategory.func_147153_a(crashreportcategory, k1, l1, i2, block, entity.worldObj.getBlockMetadata(k1, l1, i2));
							throw new ReportedException(crashreport);
						}
					}
				}
			}
		}
		return null;
	}

	// Assume we're using the new sun angle calcs
	public static float calculateLocalCelestialAngle(IBlockAccess world, int x, int y, int z, long worldTime, float partialTick)
	{
		double radiansPerTick = 0.000261799; // 15 degrees per hour
		// The hour angle of the sun is cos(w) = -tan(p)tan(d)
		// where w is the hour angle, p is the latitude (positive for north) and
		// d is the declination of the sun

		double zPos = z;
		zPos = Math.min(29999, Math.max(-29999, zPos));
		double declinationRad =
		-0.4091d * Math.cos((Math.PI * 2d / (double) TFC_Time.daysInYear) * ((TFC_Time.getDayOfYear() + TFC_Time.daysInMonth * 2) + ((double) TFC_Time.daysInYear * 10d / 365d)));
		double latitudeRad = (Math.PI / 2d) * (zPos / -30000d);
		double cosHour = -Math.tan(latitudeRad) * Math.tan(declinationRad);
		double solarHours = cosHour <= 1 && cosHour >= -1 ? Math.acos(cosHour) : cosHour * -1;
		int sunRiseTicksBeforeNoon = (int) (solarHours / radiansPerTick); // the
																			// number
																			// of
																			// ticks
																			// before
																			// solar
																			// noon
																			// that
																			// the
																			// sun
																			// will
																			// rise
																			// and
																			// the
																			// number
																			// of
																			// ticks
																			// after
																			// that
																			// it
																			// will
																			// set

		// f1 should represent the percentage of the day, where 0 = midnight
		// and 1 is 24 hours later
		float f1;
		int j = (int) ((worldTime + 6000) % 24000L);
		int sunrise = 12000 - sunRiseTicksBeforeNoon;
		int sunset = 12000 + sunRiseTicksBeforeNoon;
		if (j < sunrise)
		{
			f1 = (((float) j + partialTick) / sunrise) * 0.25f; // The angle
																// will be
																// the
																// fraction
																// towards 1
																// quarter
																// from
																// midnight
																// to
																// sunrise
		}
		else if (j > sunset)
		{
			f1 = 0.75f + (((float) j - sunset + partialTick) / (24000 - sunset)) * 0.25f; // The
																							// angle
																							// will
																							// be
																							// the
																							// fraction
																							// towards
																							// 1
																							// quarter
																							// from
																							// midnight
																							// to
																							// sunrise
		}
		else
		{
			// j is between sunrise and sunset
			// calculate how far between sunrise and sunset it is
			f1 = (float) (0.25f + (((float) j - sunrise + partialTick) / (sunRiseTicksBeforeNoon * 2d)) * 0.5f);
		}
		f1 = Math.min(1.0f, Math.max(0.0f, f1));
		f1 += 0.5f;
		f1 %= 1f;
		/*
		 * //Default code int j = (int)(p_76563_1_ % 24000L); float f1 =
		 * ((float)j + p_76563_3_) / 24000.0F - 0.25F;
		 * 
		 * if (f1 < 0.0F) { ++f1; }
		 * 
		 * if (f1 > 1.0F) { --f1; }
		 */
		return f1;
		/*
		 * f1 *= 2f; float f2 = f1; f1 = 1.0F - (float)((Math.cos((double)f1 *
		 * Math.PI) + 1.0D) / 2.0D); f1 = f2 + (f1 - f2) / 3.0F; return f1;
		 */

	}

	// Hot, Cold
	public static int[] getTemperatureResistanceFromClothes(EntityPlayer player, World world, ItemStack[] armor, ItemStack[] clothes)
	{
		int[] result = new int[] { 0, 0 };
		int numWool = 0;
		boolean feetInWater = feetInAnyWater(player, player.worldObj);
		boolean headInWater = headInAnyWater(player, player.worldObj);
		//boolean damp = TFC_Core.isClothingDamp(null, player);
		for (ItemStack i : armor)
		{
			if (i != null && i.getItem() instanceof ItemClothing)
			{
				int heat = ((ItemClothing) (i.getItem())).getHeatResistance(i);
				int cool = ((ItemClothing) (i.getItem())).getColdResistance(i);
				// We no longer have clothing soaked
				IEquipable.ClothingType c = ((IEquipable) (i.getItem())).getClothingType();
				int wetness = i.stackTagCompound != null && i.stackTagCompound.hasKey("wetness")?i.stackTagCompound.getInteger("wetness"):0; 
				boolean damp = wetness > 0 && wetness < 2000;
				boolean soaked = wetness >= 2000 ? ((c == ClothingType.BOOTS || c == ClothingType.SOCKS || c == ClothingType.FULLBOOTS || c == ClothingType.PANTS
				|| c == ClothingType.SKIRT || c == ClothingType.SANDALS || c == ClothingType.THINPANTS) ? true
				: (c == ClothingType.SHIRT || c == ClothingType.COAT || c == ClothingType.HEAVYCOAT || c == ClothingType.THINCOAT || c == ClothingType.CLOTH_HAT
				|| c == ClothingType.STRAW_HAT || c == ClothingType.THINSHIRT) ? true : false) : false;

				if (soaked)
				{
					heat = 0;
					cool = 0;
				}
				else if (damp)
				{
					if (heat > 0)
					{
						heat--;
					}
					else if (heat < 0)
					{
						heat++;
					}
					if (cool > 0)
					{
						cool--;
					}
					else if (cool < 0)
					{
						cool++;
					}
				}
				else if (((ItemClothing) i.getItem()).isWool())
				{
					numWool++;
				}
				result[0] += heat;
				result[1] += cool;
			}
		}
		if (clothes != null)
		{
			for (ItemStack i : clothes)
			{
				if (i != null && i.getItem() instanceof ItemClothing)
				{
					//damp = TFC_Core.isClothingDamp(i, player);
					int heat = ((ItemClothing) (i.getItem())).getHeatResistance(i);
					int cool = ((ItemClothing) (i.getItem())).getColdResistance(i);
					// We no longer have clothing soaked
					int wetness = i.stackTagCompound != null && i.stackTagCompound.hasKey("wetness")?i.stackTagCompound.getInteger("wetness"):0; 
					boolean damp = wetness > 0 && wetness < 2000;
					IEquipable.ClothingType c = ((IEquipable) (i.getItem())).getClothingType();
					boolean soaked = wetness>= 2000 ? ((c == ClothingType.BOOTS || c == ClothingType.SOCKS || c == ClothingType.FULLBOOTS || c == ClothingType.PANTS
					|| c == ClothingType.SKIRT || c == ClothingType.SANDALS || c == ClothingType.THINPANTS) ? true
					: (c == ClothingType.SHIRT || c == ClothingType.COAT || c == ClothingType.HEAVYCOAT || c == ClothingType.THINCOAT || c == ClothingType.CLOTH_HAT
					|| c == ClothingType.STRAW_HAT || c == ClothingType.THINSHIRT) ? true : false) : false;

					if (soaked)
					{
						heat = 0;
						cool = 0;
					}
					else if (damp)
					{
						if (heat > 0)
						{
							heat--;
						}
						else if (heat < 0)
						{
							heat++;
						}
						if (cool > 0)
						{
							cool--;
						}
						else if (cool < 0)
						{
							cool++;
						}
					}
					else if (((ItemClothing) i.getItem()).isWool())
					{
						numWool++;
					}
					result[0] += heat;
					result[1] += cool;
				}
			}
		}
		if (numWool > 1)
		{
			result[0]--;
		}
		return result;
	}

	public static ChunkDataManager removeCDM(World world)
	{
		int key = world.isRemote ? 128 | world.provider.dimensionId : world.provider.dimensionId;
		return cdmMap.remove(key);
	}

	@SideOnly(Side.CLIENT)
	public static int getMouseX()
	{
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int i = scaledresolution.getScaledWidth();
		int k = Mouse.getX() * i / Minecraft.getMinecraft().displayWidth;

		return k;
	}

	@SideOnly(Side.CLIENT)
	public static int getMouseY()
	{
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int j = scaledresolution.getScaledHeight();
		int l = j - Mouse.getY() * j / Minecraft.getMinecraft().displayHeight - 1;

		return l;
	}

	public static Boolean isBlockAboveSolid(IBlockAccess blockAccess, int i, int j, int k)
	{
		if (TerraFirmaCraft.proxy.getCurrentWorld().getBlock(i, j + 1, k).isOpaqueCube())
			return true;
		return false;
	}

	public static int getExtraEquipInventorySize()
	{
		// 5
		return 5;
	}

	public static InventoryPlayer getNewInventory(EntityPlayer player)
	{
		InventoryPlayer ip = player.inventory;
		NBTTagList nbt = new NBTTagList();
		nbt = player.inventory.writeToNBT(nbt);
		ip = new InventoryPlayerTFC(player);
		ip.readFromNBT(nbt);
		return ip;
	}

	public static ItemStack randomGem(Random random, int rockType)
	{
		ItemStack is = null;
		if (random.nextInt(500) == 0)
		{
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.gemAgate, 1, 0));
			items.add(new ItemStack(TFCItems.gemAmethyst, 1, 0));
			items.add(new ItemStack(TFCItems.gemBeryl, 1, 0));
			items.add(new ItemStack(TFCItems.gemEmerald, 1, 0));
			items.add(new ItemStack(TFCItems.gemGarnet, 1, 0));
			items.add(new ItemStack(TFCItems.gemJade, 1, 0));
			items.add(new ItemStack(TFCItems.gemJasper, 1, 0));
			items.add(new ItemStack(TFCItems.gemOpal, 1, 0));
			items.add(new ItemStack(TFCItems.gemRuby, 1, 0));
			items.add(new ItemStack(TFCItems.gemSapphire, 1, 0));
			items.add(new ItemStack(TFCItems.gemTourmaline, 1, 0));
			items.add(new ItemStack(TFCItems.gemTopaz, 1, 0));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if (random.nextInt(1000) == 0)
		{
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.gemAgate, 1, 1));
			items.add(new ItemStack(TFCItems.gemAmethyst, 1, 1));
			items.add(new ItemStack(TFCItems.gemBeryl, 1, 1));
			items.add(new ItemStack(TFCItems.gemEmerald, 1, 1));
			items.add(new ItemStack(TFCItems.gemGarnet, 1, 1));
			items.add(new ItemStack(TFCItems.gemJade, 1, 1));
			items.add(new ItemStack(TFCItems.gemJasper, 1, 1));
			items.add(new ItemStack(TFCItems.gemOpal, 1, 1));
			items.add(new ItemStack(TFCItems.gemRuby, 1, 1));
			items.add(new ItemStack(TFCItems.gemSapphire, 1, 1));
			items.add(new ItemStack(TFCItems.gemTourmaline, 1, 1));
			items.add(new ItemStack(TFCItems.gemTopaz, 1, 1));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if (random.nextInt(2000) == 0)
		{
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.gemAgate, 1, 2));
			items.add(new ItemStack(TFCItems.gemAmethyst, 1, 2));
			items.add(new ItemStack(TFCItems.gemBeryl, 1, 2));
			items.add(new ItemStack(TFCItems.gemEmerald, 1, 2));
			items.add(new ItemStack(TFCItems.gemGarnet, 1, 2));
			items.add(new ItemStack(TFCItems.gemJade, 1, 2));
			items.add(new ItemStack(TFCItems.gemJasper, 1, 2));
			items.add(new ItemStack(TFCItems.gemOpal, 1, 2));
			items.add(new ItemStack(TFCItems.gemRuby, 1, 2));
			items.add(new ItemStack(TFCItems.gemSapphire, 1, 2));
			items.add(new ItemStack(TFCItems.gemTourmaline, 1, 2));
			items.add(new ItemStack(TFCItems.gemTopaz, 1, 2));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if (random.nextInt(4000) == 0)
		{
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.gemAgate, 1, 3));
			items.add(new ItemStack(TFCItems.gemAmethyst, 1, 3));
			items.add(new ItemStack(TFCItems.gemBeryl, 1, 3));
			items.add(new ItemStack(TFCItems.gemEmerald, 1, 3));
			items.add(new ItemStack(TFCItems.gemGarnet, 1, 3));
			items.add(new ItemStack(TFCItems.gemJade, 1, 3));
			items.add(new ItemStack(TFCItems.gemJasper, 1, 3));
			items.add(new ItemStack(TFCItems.gemOpal, 1, 3));
			items.add(new ItemStack(TFCItems.gemRuby, 1, 3));
			items.add(new ItemStack(TFCItems.gemSapphire, 1, 3));
			items.add(new ItemStack(TFCItems.gemTourmaline, 1, 3));
			items.add(new ItemStack(TFCItems.gemTopaz, 1, 3));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if (random.nextInt(8000) == 0)
		{
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.gemAgate, 1, 4));
			items.add(new ItemStack(TFCItems.gemAmethyst, 1, 4));
			items.add(new ItemStack(TFCItems.gemBeryl, 1, 4));
			items.add(new ItemStack(TFCItems.gemEmerald, 1, 4));
			items.add(new ItemStack(TFCItems.gemGarnet, 1, 4));
			items.add(new ItemStack(TFCItems.gemJade, 1, 4));
			items.add(new ItemStack(TFCItems.gemJasper, 1, 4));
			items.add(new ItemStack(TFCItems.gemOpal, 1, 4));
			items.add(new ItemStack(TFCItems.gemRuby, 1, 4));
			items.add(new ItemStack(TFCItems.gemSapphire, 1, 4));
			items.add(new ItemStack(TFCItems.gemTourmaline, 1, 4));
			items.add(new ItemStack(TFCItems.gemTopaz, 1, 4));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		return is;
	}

	public static void surroundWithLeaves(World world, int i, int j, int k, int meta, Random r)
	{
		for (int y = 2; y >= -2; y--)
		{
			for (int x = 2; x >= -2; x--)
			{
				for (int z = 2; z >= -2; z--)
				{
					if (world.isAirBlock(i + x, j + y, k + z))
						world.setBlock(i + x, j + y, k + z, TFCBlocks.leaves, meta, 2);
				}
			}
		}
	}

	public static void setupWorld(World world)
	{
		long seed = world.getSeed();
		Random r = new Random(seed);
		world.provider.registerWorld(world);
		Recipes.registerAnvilRecipes(r, world);
		TFC_Time.updateTime(world);
		// TerraFirmaCraft.proxy.registerSkyProvider(world.provider);
	}

	public static void setupWorld(World w, long seed)
	{
		try
		{
			// ReflectionHelper.setPrivateValue(WorldInfo.class,
			// w.getWorldInfo(), "randomSeed", seed);
			ReflectionHelper.setPrivateValue(WorldInfo.class, w.getWorldInfo(), seed, 0);
			setupWorld(w);
		}
		catch (Exception ex)
		{
		}
	}

	public static boolean isRawStone(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		return block == TFCBlocks.stoneIgEx || block == TFCBlocks.stoneIgIn || block == TFCBlocks.stoneSed || block == TFCBlocks.stoneMM;
	}

	public static boolean isSmoothStone(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		return block == TFCBlocks.stoneIgExSmooth || block == TFCBlocks.stoneIgInSmooth || block == TFCBlocks.stoneSedSmooth || block == TFCBlocks.stoneMMSmooth;
	}

	public static boolean isSmoothStone(Block block)
	{
		return block == TFCBlocks.stoneIgExSmooth || block == TFCBlocks.stoneIgInSmooth || block == TFCBlocks.stoneSedSmooth || block == TFCBlocks.stoneMMSmooth;
	}

	public static boolean isBrickStone(Block block)
	{
		return block == TFCBlocks.stoneIgExBrick || block == TFCBlocks.stoneIgInBrick || block == TFCBlocks.stoneSedBrick || block == TFCBlocks.stoneMMBrick ||
		block == TFCBlocks.stoneIgExLargeBrick || block == TFCBlocks.stoneIgInLargeBrick || block == TFCBlocks.stoneSedLargeBrick || block == TFCBlocks.stoneMMLargeBrick;
	}

	public static boolean isRawStone(Block block)
	{
		return block == TFCBlocks.stoneIgEx || block == TFCBlocks.stoneIgIn || block == TFCBlocks.stoneSed || block == TFCBlocks.stoneMM;
	}

	public static boolean isOreStone(Block block)
	{
		return block == TFCBlocks.ore || block == TFCBlocks.ore1b || block == TFCBlocks.ore2 || block == TFCBlocks.ore3;
	}

	public static boolean isNaturalStone(Block block)
	{
		return isRawStone(block) || isOreStone(block);
	}

	public static boolean isCobbleStone(Block block)
	{
		return block == TFCBlocks.stoneIgExCobble || block == TFCBlocks.stoneIgInCobble || block == TFCBlocks.stoneSedCobble || block == TFCBlocks.stoneMMCobble;
	}

	public static boolean isStoneIgEx(Block block)
	{
		return block == TFCBlocks.stoneIgEx || block == TFCBlocks.stoneIgExCobble || block == TFCBlocks.stoneIgExSmooth || block == TFCBlocks.stoneIgExBrick|| block == TFCBlocks.stoneIgExLargeBrick
		|| block == TFCBlocks.wallRawIgEx || block == TFCBlocks.wallCobbleIgEx || block == TFCBlocks.wallBrickIgEx|| block == TFCBlocks.wallLargeBrickIgEx || block == TFCBlocks.wallSmoothIgEx;
	}

	public static boolean isStoneIgIn(Block block)
	{
		return block == TFCBlocks.stoneIgIn || block == TFCBlocks.stoneIgInCobble || block == TFCBlocks.stoneIgInSmooth || block == TFCBlocks.stoneIgInBrick|| block == TFCBlocks.stoneIgInLargeBrick
		|| block == TFCBlocks.wallRawIgIn || block == TFCBlocks.wallCobbleIgIn || block == TFCBlocks.wallBrickIgIn || block == TFCBlocks.wallLargeBrickIgIn || block == TFCBlocks.wallSmoothIgIn;
	}

	public static boolean isStoneSed(Block block)
	{
		return block == TFCBlocks.stoneSed || block == TFCBlocks.stoneSedCobble || block == TFCBlocks.stoneSedSmooth || block == TFCBlocks.stoneSedBrick|| block == TFCBlocks.stoneSedLargeBrick
		|| block == TFCBlocks.wallRawSed || block == TFCBlocks.wallCobbleSed || block == TFCBlocks.wallBrickSed|| block == TFCBlocks.wallLargeBrickSed || block == TFCBlocks.wallSmoothSed;
	}

	public static boolean isStoneMM(Block block)
	{
		return block == TFCBlocks.stoneMM || block == TFCBlocks.stoneMMCobble || block == TFCBlocks.stoneMMSmooth || block == TFCBlocks.stoneMMBrick|| block == TFCBlocks.stoneMMLargeBrick || block == TFCBlocks.wallRawMM
		|| block == TFCBlocks.wallCobbleMM || block == TFCBlocks.wallBrickMM|| block == TFCBlocks.wallLargeBrickMM || block == TFCBlocks.wallSmoothMM;
	}

	public static boolean isDirt(Block block)
	{
		return block == TFCBlocks.dirt || block == TFCBlocks.dirt2;
	}

	public static boolean isFarmland(Block block)
	{
		return block == TFCBlocks.tilledSoil || block == TFCBlocks.tilledSoil2;
	}

	public static boolean isGrass(Block block)
	{
		return block == TFCBlocks.grass || block == TFCBlocks.grass2 || block == TFCBlocks.clayGrass || block == TFCBlocks.clayGrass2 || block == TFCBlocks.peatGrass
		|| block == TFCBlocks.dryGrass || block == TFCBlocks.dryGrass2;
	}

	public static boolean isGrassNormal(Block block)
	{
		return block == TFCBlocks.grass || block == TFCBlocks.grass2;
	}

	public static boolean isLushGrass(Block block)
	{
		return block == TFCBlocks.grass || block == TFCBlocks.grass2 || block == TFCBlocks.clayGrass || block == TFCBlocks.clayGrass2 || block == TFCBlocks.peatGrass;
	}

	public static boolean isClayGrass(Block block)
	{
		return block == TFCBlocks.clayGrass || block == TFCBlocks.clayGrass2;
	}

	public static boolean isPeatGrass(Block block)
	{
		return block == TFCBlocks.peatGrass;
	}

	public static boolean isDryGrass(Block block)
	{
		return block == TFCBlocks.dryGrass || block == TFCBlocks.dryGrass2;
	}

	public static boolean isGrassType1(Block block)
	{
		return block == TFCBlocks.grass || block == TFCBlocks.clayGrass || block == TFCBlocks.dryGrass;
	}

	public static boolean isGrassType2(Block block)
	{
		return block == TFCBlocks.grass2 || block == TFCBlocks.clayGrass2 || block == TFCBlocks.dryGrass2;
	}

	public static boolean isClay(Block block)
	{
		return block == TFCBlocks.clay || block == TFCBlocks.clay2;
	}

	public static boolean isSand(Block block)
	{
		return block == TFCBlocks.sand || block == TFCBlocks.sand2;
	}

	public static boolean isPeat(Block block)
	{
		return block == TFCBlocks.peat;
	}

	public static boolean isHotWater(Block block)
	{
		return block == TFCBlocks.hotWater || block == TFCBlocks.hotWaterStationary;
	}

	public static boolean isWater(Block block)
	{
		return isSaltWater(block) || isFreshWater(block);
	}

	public static boolean isWaterFlowing(Block block)
	{
		return block == TFCBlocks.saltWater || block == TFCBlocks.freshWater;
	}

	public static boolean isSaltWater(Block block)
	{
		return block == TFCBlocks.saltWater || block == TFCBlocks.saltWaterStationary;
	}

	public static boolean isSaltWaterIncludeIce(Block block, int meta, Material mat)
	{
		return block == TFCBlocks.saltWater || block == TFCBlocks.saltWaterStationary || mat == Material.ice && meta == 0;
	}

	public static boolean isFreshWater(Block block)
	{
		return block == TFCBlocks.freshWater || block == TFCBlocks.freshWaterStationary;
	}

	public static boolean isFreshWaterIncludeIce(Block block, int meta)
	{
		return block == TFCBlocks.freshWater || block == TFCBlocks.freshWaterStationary || block == TFCBlocks.ice && meta != 0;
	}

	public static boolean isFreshWaterIncludeIce(Block block, int meta, Material mat)
	{
		return block == TFCBlocks.freshWater || block == TFCBlocks.freshWaterStationary || mat == Material.ice && meta != 0;
	}

	public static boolean isSoil(Block block)
	{
		return isGrass(block) || isDirt(block) || isClay(block) || isPeat(block);
	}

	public static boolean isSoilOrGravel(Block block)
	{
		return isGrass(block) || isDirt(block) || isClay(block) || isPeat(block) || isGravel(block);
	}

	public static boolean isGravel(Block block)
	{
		return block == TFCBlocks.gravel || block == TFCBlocks.gravel2;
	}

	public static boolean isGround(Block block)
	{
		return isSoilOrGravel(block) || isRawStone(block) || isSand(block);
	}

	public static boolean isGroundType1(Block block)
	{
		return isGrassType1(block) || block == TFCBlocks.dirt || block == TFCBlocks.gravel || block == TFCBlocks.sand;
	}

	public static boolean isSoilWAILA(Block block)
	{
		return isDirt(block) || isGravel(block) || isSand(block) || isGrassNormal(block) || isDryGrass(block);
	}

	public static int getSoilMetaFromStone(Block inBlock, int inMeta)
	{
		if (inBlock == TFCBlocks.stoneIgIn)
			return inMeta;
		else if (inBlock == TFCBlocks.stoneSed)
			return inMeta + 3;
		else if (inBlock == TFCBlocks.stoneIgEx)
			return inMeta + 11;
		else
		{
			if (inMeta == 0)
				return inMeta + 15;
			return inMeta - 1;
		}
	}

	public static int getSoilMeta(int inMeta)
	{
		return inMeta & 15;
	}
	
	public static Block getCobbleFromLayer(int stoneLayer)
	{
		if (stoneLayer < 3)
			return TFCBlocks.stoneIgInCobble;
		else if (stoneLayer < 11)
			return TFCBlocks.stoneSedCobble;
		else if ( stoneLayer < 15)
			return TFCBlocks.stoneIgExCobble;
		else
			return TFCBlocks.stoneMMCobble;
	}
	
	public static int getMetaFromLayer(int stoneLayer)
	{
		if (stoneLayer < 3)
			return stoneLayer;
		else if (stoneLayer < 11)
			return stoneLayer-3;
		else if ( stoneLayer < 15)
			return stoneLayer-11;
		return stoneLayer-15;
	}

	public static int getItemMetaFromStone(Block inBlock, int inMeta)
	{
		if (inBlock == TFCBlocks.stoneIgIn)
			return inMeta;
		else if (inBlock == TFCBlocks.stoneSed)
			return inMeta + 3;
		else if (inBlock == TFCBlocks.stoneIgEx)
			return inMeta + 11;
		else if (inBlock == TFCBlocks.stoneMM)
			return inMeta + 15;
		else
			return 0;
	}

	public static Block getTypeForGrassWithRain(int inMeta, float rain)
	{
		if (rain >= 200)
			return getTypeForGrass(inMeta);
		return getTypeForDryGrass(inMeta);
	}

	public static Block getTypeForGrassWithRainByBlock(Block block, float rain)
	{
		if (rain >= 200)
			return getTypeForGrassFromSoil(block);
		return getTypeForDryGrassFromSoil(block);
	}

	public static Block getTypeForGrass(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.grass;
		return TFCBlocks.grass2;
	}

	public static Block getTypeForGrassFromDirt(Block block)
	{
		if (block == TFCBlocks.dirt)
			return TFCBlocks.grass;
		return TFCBlocks.grass2;
	}

	public static Block getTypeForDryGrass(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.dryGrass;
		return TFCBlocks.dryGrass2;
	}

	public static Block getTypeForDryGrassFromSoil(Block block)
	{
		if (block == TFCBlocks.grass)
			return TFCBlocks.dryGrass;
		else if (block == TFCBlocks.dirt)
			return TFCBlocks.dryGrass;
		return TFCBlocks.dryGrass2;
	}

	public static Block getTypeForGrassFromSoil(Block block)
	{
		if (block == TFCBlocks.dryGrass)
			return TFCBlocks.grass;
		else if (block == TFCBlocks.dryGrass2)
			return TFCBlocks.grass2;
		else if (block == TFCBlocks.dirt)
			return TFCBlocks.grass;
		return TFCBlocks.grass2;
	}

	public static Block getTypeForClayGrass(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.clayGrass;
		return TFCBlocks.clayGrass2;
	}

	public static Block getTypeForClayGrass(Block block)
	{
		if (TFC_Core.isGroundType1(block))
			return TFCBlocks.clayGrass;
		return TFCBlocks.clayGrass2;
	}

	public static Block getTypeForDirt(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.dirt;
		return TFCBlocks.dirt2;
	}

	public static Block getTypeForDirtFromGrass(Block block)
	{
		if (TFC_Core.isClay(block))
			return block == TFCBlocks.clayGrass ? TFCBlocks.clay : block == TFCBlocks.clayGrass2 ? TFCBlocks.clay2 : block;
		if (TFC_Core.isDirt(block))
			return block;
		if (block == TFCBlocks.grass || block == TFCBlocks.dryGrass)
			return TFCBlocks.dirt;
		return TFCBlocks.dirt2;
	}

	public static Block getTypeForSoil(Block block)
	{
		if (TFC_Core.isGrass(block))
		{
			if (TFC_Core.isGrassType1(block))
				return TFCBlocks.dirt;
			else if (TFC_Core.isGrassType2(block))
				return TFCBlocks.dirt2;
			else if (TFC_Core.isPeatGrass(block))
				return TFCBlocks.peat;
		}

		return block;
	}

	public static Block getTypeForClay(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.clay;
		return TFCBlocks.clay2;
	}

	public static Block getTypeForClay(Block block)
	{
		if (TFC_Core.isGroundType1(block))
			return TFCBlocks.clay;
		return TFCBlocks.clay2;
	}

	public static Block getTypeForSand(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.sand;
		return TFCBlocks.sand2;
	}

	public static Block getTypeForGravel(int inMeta)
	{
		if (inMeta < 16)
			return TFCBlocks.gravel;
		return TFCBlocks.gravel2;
	}

	public static int getRockLayerFromHeight(World world, int x, int y, int z)
	{
		ChunkData cd = TFC_Core.getCDM(world).getData(x >> 4, z >> 4);
		if (cd != null)
		{
			int[] hm = cd.heightmap;
			int localX = x & 15;
			int localZ = z & 15;
			int localY = localX + localZ * 16;
			if (y <= TFCOptions.rockLayer3Height + hm[localY])
				return 2;
			else if (y <= TFCOptions.rockLayer2Height + hm[localY])
				return 1;
			else
				return 0;
		}
		return 0;
	}

	public static boolean convertGrassToDirt(World world, int i, int j, int k)
	{
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		if (TFC_Core.isGrass(block))
		{
			if (TFC_Core.isGrassType1(block))
			{
				world.setBlock(i, j, k, TFCBlocks.dirt, meta, 2);
				return true;
			}
			else if (TFC_Core.isGrassType2(block))
			{
				world.setBlock(i, j, k, TFCBlocks.dirt2, meta, 2);
				return true;
			}
		}
		return false;
	}

	public static EnumFuelMaterial getFuelMaterial(ItemStack is)
	{
		if (is.getItem() ==TFCItems.stick)
			return EnumFuelMaterial.STICK;
		if (is.getItem() == Item.getItemFromBlock(TFCBlocks.peat))
			return EnumFuelMaterial.PEAT;
		if (is.getItem() == TFCItems.coal && is.getItemDamage() == 0)
			return EnumFuelMaterial.COAL;
		else if (is.getItem() == TFCItems.coal && is.getItemDamage() == 1)
			return EnumFuelMaterial.CHARCOAL;
		int dam = is.getItemDamage() / 2;
		if (dam == 0)
			return EnumFuelMaterial.ASH;
		else if (dam == 1)
			return EnumFuelMaterial.ASPEN;
		else if (dam == 2)
			return EnumFuelMaterial.BIRCH;
		else if (dam == 3)
			return EnumFuelMaterial.CHESTNUT;
		else if (dam == 4)
			return EnumFuelMaterial.DOUGLASFIR;
		else if (dam == 5)
			return EnumFuelMaterial.HICKORY;
		else if (dam == 6)
			return EnumFuelMaterial.MAPLE;
		else if (dam == 7)
			return EnumFuelMaterial.OAK;
		else if (dam == 8)
			return EnumFuelMaterial.PINE;
		else if (dam == 9)
			return EnumFuelMaterial.REDWOOD;
		else if (dam == 10)
			return EnumFuelMaterial.SPRUCE;
		else if (dam == 11)
			return EnumFuelMaterial.SYCAMORE;
		else if (dam == 12)
			return EnumFuelMaterial.WHITECEDAR;
		else if (dam == 13)
			return EnumFuelMaterial.WHITEELM;
		else if (dam == 14)
			return EnumFuelMaterial.WILLOW;
		else if (dam == 15)
			return EnumFuelMaterial.KAPOK;
		else if (dam == 16)
			return EnumFuelMaterial.ACACIA;
		else if (dam == 17)
			return EnumFuelMaterial.PALM;
		else if (dam == 18)
			return EnumFuelMaterial.EBONY;
		else if (dam == 19)
			return EnumFuelMaterial.FEVER;
		else if (dam == 20)
			return EnumFuelMaterial.BAOBAB;
		else if (dam == 21)
			return EnumFuelMaterial.LIMBA;
		else if (dam == 22)
			return EnumFuelMaterial.MAHOGANY;
		else if (dam == 23)
			return EnumFuelMaterial.TEAK;
		else if (dam == 24)
			return EnumFuelMaterial.BAMBOO;
		else if (dam == 25)
			return EnumFuelMaterial.GINGKO;
		else if (dam == 26)
			return EnumFuelMaterial.FRUITWOOD;

		return EnumFuelMaterial.ASPEN;
	}

	public static boolean showShiftInformation()
	{
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}

	public static boolean showCtrlInformation()
	{
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
	}

	public static FoodStatsTFC getPlayerFoodStats(EntityPlayer player)
	{
		FoodStatsTFC foodstats = new FoodStatsTFC(player);
		foodstats.readNBT(player.getEntityData());
		return foodstats;
	}

	// returns the branch2 of this branch1
	public static Block getSecondBranch(Block originalBranch)
	{
		if (originalBranch instanceof BlockBranch)
		{
			if (((BlockBranch) originalBranch) instanceof BlockBranch2)
			{
				return originalBranch;
			}
			BlockBranch b = ((BlockBranch) originalBranch);
			if (b.isEnd())
			{
				return TFC_Core.branchMap[b.getSourceX() + 1][b.getSourceY() + 1][b.getSourceZ() + 1][3];
			}
			return TFC_Core.branchMap[b.getSourceX() + 1][b.getSourceY() + 1][b.getSourceZ() + 1][1];
		}
		return null;
	}

	// Given an initial branch, return the branch that would be in that
	// direction
	public static Block getSourcedBranchForBranch(Block initialBranch, int x, int y, int z)
	{
		// If the branch given wasn't even a branch, we just return null.
		if (!(initialBranch instanceof BlockBranch || initialBranch instanceof BlockLogNatural) || x < -1 || x > 1 || y < -1 || y > 1 || z < -1 || z > 1)
		{
			return null;
		}
		// If the branch given was a branch2, that means we need to return a
		// branch2
		if (initialBranch instanceof BlockBranch2 || initialBranch instanceof BlockLogNatural2)
		{
			return branchMap[2 - (x + 1)][2 - (y + 1)][2 - (z + 1)][1];
		}
		return branchMap[2 - (x + 1)][2 - (y + 1)][2 - (z + 1)][0];
	}

	// Given an initial branch, return the branch that would be in that
	// direction
	public static Block getSourcedTerminalBranchForBranch(Block initialBranch, int x, int y, int z)
	{
		// If the branch given wasn't even a branch, we just return null.
		if (!(initialBranch instanceof BlockBranch || initialBranch instanceof BlockLogNatural) || x < -1 || x > 1 || y < -1 || y > 1 || z < -1 || z > 1)
		{
			return null;
		}
		// If the branch given was a branch2, that means we need to return a
		// branch2
		if (initialBranch instanceof BlockBranch2 || initialBranch instanceof BlockLogNatural2)
		{
			return branchMap[2 - (x + 1)][2 - (y + 1)][2 - (z + 1)][3];
		}
		return branchMap[2 - (x + 1)][2 - (y + 1)][2 - (z + 1)][2];
	}

	public static void setPlayerFoodStats(EntityPlayer player, FoodStatsTFC foodstats)
	{
		foodstats.writeNBT(player.getEntityData());
	}

	public static BodyTempStats getBodyTempStats(EntityPlayer player)
	{
		BodyTempStats body = new BodyTempStats();
		body.readNBT(player.getEntityData());
		return body;
	}

	public static void setBodyTempStats(EntityPlayer player, BodyTempStats tempStats)
	{
		tempStats.writeNBT(player.getEntityData());
	}

	public static SkillStats getSkillStats(EntityPlayer player)
	{
		SkillStats skills = new SkillStats(player);
		skills.readNBT(player.getEntityData());
		return skills;
	}

	public static void setSkillStats(EntityPlayer player, SkillStats skills)
	{
		skills.writeNBT(player.getEntityData());
	}

	public static boolean isTopFaceSolid(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).isNormalCube())
			return true;
		else if (world.getBlock(x, y, z) == TFCBlocks.metalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if (te.topExists())
				return true;
		}
		return world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.UP);
	}

	public static boolean isBottomFaceSolid(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).isNormalCube())
			return true;
		else if (world.getBlock(x, y, z) == TFCBlocks.metalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if (te.bottomExists())
				return true;
		}
		return world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.DOWN);
	}

	public static boolean isNorthFaceSolid(World world, int x, int y, int z)
	{
		Block bid = world.getBlock(x, y, z);
		if (bid.isNormalCube())
			return true;
		else if (bid == TFCBlocks.metalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if (te.northExists())
				return true;
		}
		return world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.NORTH);
	}

	public static boolean isSouthFaceSolid(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).isNormalCube())
			return true;
		else if (world.getBlock(x, y, z) == TFCBlocks.metalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if (te.southExists())
				return true;
		}
		return world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.SOUTH);
	}

	public static boolean isEastFaceSolid(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).isNormalCube())
			return true;
		else if (world.getBlock(x, y, z) == TFCBlocks.metalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if (te.eastExists())
				return true;
		}
		return world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.EAST);
	}

	public static boolean isWestFaceSolid(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).isNormalCube())
			return true;
		else if (world.getBlock(x, y, z) == TFCBlocks.metalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if (te.westExists())
				return true;
		}
		return world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.WEST);
	}

	public static boolean isSurroundedSolid(World world, int x, int y, int z)
	{
		return TFC_Core.isNorthFaceSolid(world, x, y, z + 1) && TFC_Core.isSouthFaceSolid(world, x, y, z - 1) && TFC_Core.isEastFaceSolid(world, x - 1, y, z)
		&& TFC_Core.isWestFaceSolid(world, x + 1, y, z) && TFC_Core.isTopFaceSolid(world, x, y - 1, z);
	}

	public static boolean isSurroundedStone(World world, int x, int y, int z)
	{
		return world.getBlock(x, y, z + 1).getMaterial() == Material.rock && world.getBlock(x, y, z - 1).getMaterial() == Material.rock
		&& world.getBlock(x - 1, y, z).getMaterial() == Material.rock && world.getBlock(x + 1, y, z).getMaterial() == Material.rock
		&& world.getBlock(x, y - 1, z).getMaterial() == Material.rock;
	}

	public static boolean isOreIron(ItemStack is)
	{
		return is.getItem() instanceof ItemOre && ((ItemOre) is.getItem()).getMetalType(is) == Global.PIGIRON;
	}

	public static float getEntityMaxHealth(EntityLivingBase entity)
	{
		return (float) entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
	}

	public static float getPercentGrown(IAnimal animal)
	{
		float birth = animal.getBirthDay();
		float time = TFC_Time.getTotalDays();
		float percent = (time - birth) / animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
	}

	public static float getPercentGrownSheep(IAnimal animal)
	{
		float birth = animal.getBirthDay();
		float time = TFC_Time.getTotalDays();
		float percent = (time - birth) / animal.getNumberOfDaysToAdult();
		return Math.min(percent, 2f);
	}

	public static void bindTexture(ResourceLocation texture)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	}

	public static boolean isPlayerInDebugMode(EntityPlayer player)
	{
		return TFCOptions.enableDebugMode;
	}

	/**
	 * Adds exhaustion to the player. 0.001 is a standard amount.
	 */
	public static void addPlayerExhaustion(EntityPlayer player, float exhaustion)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		foodstats.addFoodExhaustion(exhaustion);
		// foodstats.addWaterExhaustion(exhaustion);
		TFC_Core.setPlayerFoodStats(player, foodstats);
	}

	public static float getEnvironmentalDecay(float temp)
	{
		if (temp > 0)
		{
			float tempFactor = 1f - (15f / (15f + temp));
			return tempFactor * 2;
		}
		else
			return 0;
	}

	/**
	 * This is the default item ticking method for use by all containers. Call
	 * this if you don't want to do custom environmental decay math.
	 */
	public static void handleItemTicking(IInventory iinv, World world, int x, int y, int z)
	{
		handleItemTicking(iinv, world, x, y, z, 1);
	}

	/**
	 * This is the default item ticking method for use by all containers. Call
	 * this if you don't want to do custom environmental decay math.
	 */
	public static void handleItemTicking(ItemStack[] iinv, World world, int x, int y, int z, boolean isCreative)
	{
		handleItemTicking(iinv, world, x, y, z, 1, isCreative);
	}

	/**
	 * This version of the method assumes that the environmental decay modifier
	 * has already been calculated.
	 */
	public static void handleItemTicking(IInventory iinv, World world, int x, int y, int z, float environmentalDecayFactor)
	{
		for (int i = 0; !world.isRemote && i < iinv.getSizeInventory(); i++)
		{
			ItemStack is = iinv.getStackInSlot(i);
			if (is != null && iinv.getStackInSlot(i).stackSize <= 0)
				iinv.setInventorySlotContents(i, null);

			if (is != null)
			{
				if (is.stackSize == 0)
				{
					iinv.setInventorySlotContents(i, null);
					continue;
				}
				if (is.getItem() instanceof ItemTerra && ((ItemTerra) is.getItem()).onUpdate(is, world, x, y, z))
					continue;
				else if (is.getItem() instanceof ItemTerraBlock && ((ItemTerraBlock) is.getItem()).onUpdate(is, world, x, y, z))
					continue;
				is = tickDecay(is, world, x, y, z, environmentalDecayFactor, 1f);
				if (is != null)
					TFC_ItemHeat.handleItemHeat(is);
				iinv.setInventorySlotContents(i, is);
			}

		}
	}

	// Takes a small float in the range of 0.5 to 1.5. The resulting float would
	// be of the form [0 0111111 [the byte] 0..0], such that the byte returned
	// is the only unknown value
	public static byte getByteFromSmallFloat(float f)
	{
		MathHelper.clamp_float(f, 0.5f, 1.5f);
		return (byte) ((Float.floatToIntBits(f) >> 16) & 0xff);
	}

	public static float getSmallFloatFromByte(byte b)
	{
		return ByteBuffer.wrap(new byte[] { (byte) 63, b, (byte) (0), (byte) 0 }).getFloat();
	}

	/**
	 * This version of the method assumes that the environmental decay modifier
	 * has already been calculated.
	 */
	public static void handleItemTicking(IInventory iinv, World world, int x, int y, int z, float environmentalDecayFactor, float baseDecayMod)
	{
		for (int i = 0; !world.isRemote && i < iinv.getSizeInventory(); i++)
		{
			ItemStack is = iinv.getStackInSlot(i);
			if (is != null && iinv.getStackInSlot(i).stackSize <= 0)
				iinv.setInventorySlotContents(i, null);

			if (is != null)
			{
				if (is.getItem() instanceof ItemTerra && ((ItemTerra) is.getItem()).onUpdate(is, world, x, y, z))
					continue;
				else if (is.getItem() instanceof ItemTerraBlock && ((ItemTerraBlock) is.getItem()).onUpdate(is, world, x, y, z))
					continue;
				is = tickDecay(is, world, x, y, z, environmentalDecayFactor, baseDecayMod);
				if (is != null)
					TFC_ItemHeat.handleItemHeat(is);
				iinv.setInventorySlotContents(i, is);
			}
		}
	}

	/**
	 * This version of the method assumes that the environmental decay modifier
	 * has already been calculated.
	 */
	public static void handleItemTicking(ItemStack[] iinv, World world, int x, int y, int z, float environmentalDecayFactor, boolean isCreative)
	{
		for (int i = 0; !world.isRemote && i < iinv.length; i++)
		{
			ItemStack is = iinv[i];
			if (is != null && iinv[i].stackSize <= 0)
				iinv[i] = null;

			if (is != null)
			{
				// if (is.getItem() instanceof ItemClothing
				// && TFC_Time.getTotalTicks() %
				// TFC_Core.getClothingUpdateFrequency() == 0 && !isCreative)
				// {
				// is = handleInventoryWetness(is,
				// TFC_Core.getClothingUpdateFrequency());
				// }
				if (is.getItem() instanceof ItemTerra && ((ItemTerra) is.getItem()).onUpdate(is, world, x, y, z))
					continue;
				else if (is.getItem() instanceof ItemTerraBlock && ((ItemTerraBlock) is.getItem()).onUpdate(is, world, x, y, z))
					continue;
				is = tickDecay(is, world, x, y, z, environmentalDecayFactor, 1);
				if (is != null)
					TFC_ItemHeat.handleItemHeat(is);
				iinv[i] = is;
			}

		}
	}

	public static void handleQuickDry(ItemStack i)
	{
		NBTTagCompound nbt = i.stackTagCompound;
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
			nbt.setLong("lastWorn", TFC_Time.getTotalDays());
			i.stackTagCompound = nbt;
			return;
		}
		 int wetness = nbt.getInteger("wetness");
		 if (wetness > 0)
		 {
		 wetness -= 10;
		 wetness = Math.max(0, wetness);
		 }
		 nbt.setInteger("wetness", wetness);
		 i.stackTagCompound = nbt;
	}

	public static ItemStack handleInventoryWetness(ItemStack i, int frequency)
	{
		// This is when we're in the inventory.
		NBTTagCompound nbt = i.stackTagCompound;
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
			nbt.setLong("lastWorn", TFC_Time.getTotalDays());
			return i;
		}
		// int wetness = nbt.getInteger("wetness");
		// if (wetness > 0 && TFC_Time.getTotalTicks()%3==0)
		// {
		// wetness -= frequency;
		// wetness = Math.max(0, wetness);
		// }
		// nbt.setInteger("wetness", wetness);
		return i;
	}

	public static boolean isClothingDamp(ItemStack i, EntityPlayer player)
	{
		if(i != null && i.stackTagCompound != null && i.stackTagCompound.hasKey("wetness") && i.stackTagCompound.getInteger("wetness") > 0 && i.stackTagCompound.getInteger("wetness") < 2000)
		{
			return true;
		}
		return false;
		// if (i == null)
		// {
		/*
		for (int count = 1; count < ((InventoryPlayerTFC) player.inventory).extraEquipInventory.length && i != null
		&& PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).myExtraItems != null && PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player) != null; count++)
		{
			if (i == PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).myExtraItems[count] && player.inventory.armorInventory[4 - count] != null)
			{
				return false;
			}
		}

		return (TFC_Core.isExposedToRain(player.worldObj, (int) player.posX, (int) player.posY - (player.worldObj.isRemote ? 1 : 0),
	
		(int) player.posZ - (player.worldObj.isRemote ? 0 : 1)) && TFC_Climate.getHeightAdjustedTemp(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ) > 0);
		*/// }
		// else if (i.stackTagCompound == null)
		// {
		// return false;
		// }
		// else if (!i.stackTagCompound.hasKey("wetness"))
		// {
		// return false;
		// }
		// else
		// {
		// int w = i.stackTagCompound.getInteger("wetness");
		// return w >= 500 && w < 1500;
		// }
	}

	public static int getClothingWetness(ItemStack i)
	{
		// if (i == null)
		// {
		if(i != null && i.stackTagCompound != null && i.stackTagCompound.hasKey("wetness"))
		{
			return  i.stackTagCompound.getInteger("wetness");
		}
		return 0;
		// }
		// else if (i.stackTagCompound == null)
		// {
		// return 0;
		// }
		// else if (!i.stackTagCompound.hasKey("wetness"))
		// {
		// return 0;
		// }
		// else
		// {
		// int w = i.stackTagCompound.getInteger("wetness");
		// return w;
		// }
	}

	public static boolean isClothingSoaked(ItemStack i, EntityPlayer player)
	{
		/*boolean feetInWater = feetInAnyWater(player, player.worldObj);
		boolean headInWater = headInAnyWater(player, player.worldObj);
		IEquipable.ClothingType c = ((IEquipable) (i.getItem())).getClothingType();
		boolean soaked = feetInWater | headInWater ? ((c == ClothingType.BOOTS || c == ClothingType.SOCKS || c == ClothingType.FULLBOOTS || c == ClothingType.PANTS
		|| c == ClothingType.SKIRT || c == ClothingType.SANDALS || c == ClothingType.THINPANTS) ? feetInWater
		: (c == ClothingType.SHIRT || c == ClothingType.COAT || c == ClothingType.HEAVYCOAT || c == ClothingType.THINCOAT || c == ClothingType.CLOTH_HAT
		|| c == ClothingType.STRAW_HAT || c == ClothingType.THINSHIRT) ? headInWater : false) : false;
		return soaked;*/
		if(i != null && i.stackTagCompound != null && i.stackTagCompound.hasKey("wetness") && i.stackTagCompound.getInteger("wetness") >= 2000)
		{
			return true;
		}
		return false;
		// }
		// else if (i.stackTagCompound == null)
		// {
		// return false;
		// }
		// else if (!i.stackTagCompound.hasKey("wetness"))
		// {
		// return false;
		// }
		// else
		// {
		// int w = i.stackTagCompound.getInteger("wetness");
		// return w > 1500;
		// }
	}

	/***
	 * 
	 * @param i
	 *            the itemstack of the clothing that is being worn
	 * @param player
	 *            the player wearing the clothing
	 * @param isInRain
	 *            whether or not it is raining and the player is standing out in
	 *            it, ie no roof over their head
	 * @param localBodyInWater
	 *            whether the relevant half of the body is in water, ie the legs
	 *            if this is a foot or leg item
	 * @param ambientTemp
	 *            the temperature of the area
	 * @param onFire
	 *            whether the player is burning
	 * @param isStraw
	 *            whether this item is made of straw
	 * @param bodyPartSheltered
	 *            whether or not this clothing would be protected from the rain
	 *            by another piece of clothing
	 */
	public static ItemStack handleClothingWear(ItemStack i, EntityPlayer player, boolean isInRain, boolean localBodyInWater, int ambientTemp, boolean onFire, boolean isStraw,
	boolean bodyPartSheltered, int wearFrequency)
	{
		// First we wear the clothing out. We load the clothing data

		NBTTagCompound nbt = i.stackTagCompound;
		boolean changed = false;
		boolean brandNew = false;
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
			brandNew = true;
			i.stackTagCompound = nbt;
			changed = true;
		}
		long lastWorn = nbt.getLong("lastWorn");
		// Our clothing is burning!
		if (lastWorn != TFC_Time.getTotalDays())
		{
			// Don't damage the item the first time it's worn.
			if (!brandNew && !((i.getItem()) instanceof ItemClothing && ((ItemClothing)i.getItem()).getNoWearInCold()))
			{
				i.damageItem(1, player);
			}
			nbt.setLong("lastWorn", TFC_Time.getTotalDays());
			changed = true;
		}
		int wetness = 0;// nbt.getInteger("wetness");
		int prevWetness = 0;
		if (nbt.hasKey("wetness"))
		{
			prevWetness = nbt.getInteger("wetness");
		}
		if(localBodyInWater)
		{
			nbt.setInteger("wetness", 4000);
		}
		else if(!bodyPartSheltered && isInRain && prevWetness < 500)
		{
			nbt.setInteger("wetness", 500);
		}

		if (onFire && wetness == 0)
		{
			if (i.getItemDamage() + wearFrequency < i.getMaxDamage())
			{
				i.setItemDamage(i.getItemDamage() + (1 * wearFrequency));
			}
			else
			{
				i = null;
				return null;
			}
			/*if (isStraw)
			{
				if (i.getItemDamage() + (5 * wearFrequency) < i.getMaxDamage())
				{
					i.setItemDamage(i.getItemDamage() + (5 * wearFrequency));
				}
				else
				{
					i = null;
					return null;
				}
			}*/
		}
		// We might be getting wetter
		/*
		 * if (localBodyInWater) { wetness = 4000; } else if (isInRain &&
		 * wetness + wearFrequency < 1000 && ambientTemp > 0 &&
		 * !bodyPartSheltered) { wetness+=wearFrequency; } else if (wetness > 0
		 * && ambientTemp > 0) { // If the item is wet and it's raining, it's
		 * not going to dry. if (!isInRain) wetness -= Math.max(1, (float)
		 * ambientTemp / 10f) * wearFrequency; if (onFire) { wetness -= 100 *
		 * wearFrequency; } wetness = Math.max(0, wetness); }
		 * if(nbt.getInteger("wetness") > 0 || wetness > 0) {
		 * nbt.setInteger("wetness", wetness); changed = true; }
		 */

		if (i.getItemDamage() == i.getMaxDamage())
		{
			i = null;
		}

		// if(changed)
		// {
		// PlayerManagerTFC.getInstance()
		// .getPlayerInfoFromName(player.getDisplayName()).clothingWetLock =
		// true;
		// }

		return i;
	}

	/**
	 * @param is
	 * @param baseDecayMod
	 * @param nbt
	 */
	public static ItemStack tickDecay(ItemStack is, World world, int x, int y, int z, float environmentalDecayFactor, float baseDecayMod)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if (nbt == null || !nbt.hasKey(Food.WEIGHT_TAG) || !nbt.hasKey(Food.DECAY_TAG))
			return is;

		int decayTimer = Food.getDecayTimer(is);
		// if the tick timer is up then we cause decay.
		if (decayTimer < TFC_Time.getTotalHours())
		{
			int timeDiff = (int) (TFC_Time.getTotalHours() - decayTimer);
			float protMult = 1;

			if (TFCOptions.useDecayProtection)
			{
				if (timeDiff > TFCOptions.decayProtectionDays * 24)
				{
					decayTimer = (int) TFC_Time.getTotalHours() - 24;
				}
				else if (timeDiff > 24)
				{
					protMult = 1 - (timeDiff / (TFCOptions.decayProtectionDays * 24));
				}
			}

			float decay = Food.getDecay(is);
			float thisDecayRate = 1.0f;
			// Get the base food decay rate
			if (is.getItem() instanceof IFood)
				thisDecayRate = ((IFood) is.getItem()).getDecayRate(is);
			// check if the food has a specially applied decay rate in its nbt
			// (meals, sandwiches, salads)
			else
				thisDecayRate = Food.getDecayRate(is);

			/*
			 * Here we calculate the decayRate based on the environment. We do
			 * this before everything else so that its only done once per
			 * inventory
			 */
			// int day =
			// TFC_Time.getDayOfYearFromDays(TFC_Time.getDayFromTotalHours(nbt.getInteger(Food.DECAY_TIMER_TAG)));
			// float temp =
			// TFC_Climate.getHeightAdjustedTempSpecificDay(world,day,nbt.getInteger(Food.DECAY_TIMER_TAG),
			// x, y, z);
			float temp = getCachedTemp(world, x, y, z, decayTimer);
			float environmentalDecay = getEnvironmentalDecay(temp) * environmentalDecayFactor;

			if (decay < 0)
			{
				float d = 1 * (thisDecayRate * baseDecayMod * environmentalDecay);
				if (decay + d < 0)
					decay += d;
				else
					decay = 0;
			}
			else if (decay == 0)
			{
				decay = (Food.getWeight(is) * (0.0025f + (world.rand.nextFloat() * 0.0025f))) * TFCOptions.decayMultiplier;
			}
			else
			{
				double fdr = TFCOptions.foodDecayRate - 1;
				fdr *= thisDecayRate * baseDecayMod * environmentalDecay * protMult * TFCOptions.decayMultiplier;
				decay *= 1 + fdr;
			}
			Food.setDecayTimer(is, decayTimer + 1);
			Food.setDecay(is, decay);
		}

		if (Food.getDecay(is) / Food.getWeight(is) > 0.9f)
		{
			if (is.getItem() instanceof IFood)
				is = ((IFood) is.getItem()).onDecayed(is, world, x, y, z);
			else
				is.stackSize = 0;
		}

		return is;
	}

	public static float getCachedTemp(World world, int x, int y, int z, int th)
	{
		float cacheTemp = TFC_Climate.getCacheManager(world).getTemp(x, z, th);
		if (cacheTemp != Float.MIN_VALUE)
		{
			return cacheTemp;
		}
		float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(world, TFC_Time.getDayFromTotalHours(th), TFC_Time.getHourOfDayFromTotalHours(th), x, y, z);
		addCachedTemp(world, x, z, th, temp);
		return temp;
	}

	public static void addCachedTemp(World world, int x, int z, int th, float temp)
	{
		TFC_Climate.getCacheManager(world).addTemp(x, z, th, temp);
	}

	public static void animalDropMeat(Entity e, Item i, float foodWeight)
	{
		Random r;
		ItemStack is = ItemFoodTFC.createTag(new ItemStack(i, 1), foodWeight);
		
		r = new Random(e.getUniqueID().getLeastSignificantBits() + e.getUniqueID().getMostSignificantBits());
		Food.adjustFlavor(is, r);
		//e.worldObj.spawnEntityInWorld(new EntityItem(e.worldObj, e.posX, e.posY, e.posZ, is));
		
		e.capturedDrops.add(new EntityItem(e.worldObj, e.posX, e.posY, e.posZ, is));
	}

	public static Vec3 getEntityPos(Entity e)
	{
		return Vec3.createVectorHelper(e.posX, e.posY, e.posZ);
	}

	public static void giveItemToPlayer(ItemStack is, EntityPlayer player)
	{
		if (player.worldObj.isRemote)
			return;
		EntityItem ei = player.entityDropItem(is, 1);
		ei.delayBeforeCanPickup = 0;
	}

	public static boolean isFence(Block b)
	{
		return b == TFCBlocks.fence || b == TFCBlocks.fence2;
	}

	public static boolean isVertSupport(Block b)
	{
		return b == TFCBlocks.woodSupportV || b == TFCBlocks.woodSupportV2;
	}

	public static boolean isHorizSupport(Block b)
	{
		return b == TFCBlocks.woodSupportH || b == TFCBlocks.woodSupportH2;
	}

	public static boolean isOceanicBiome(int id)
	{
		return id == TFCBiome.OCEAN.biomeID || id == TFCBiome.DEEP_OCEAN.biomeID;
	}

	public static boolean isMountainBiome(int id)
	{
		return id == TFCBiome.MOUNTAINS.biomeID || id == TFCBiome.MOUNTAINS_EDGE.biomeID || id == TFCBiome.MOUNTAIN_RANGE.biomeID || id == TFCBiome.MOUNTAIN_RANGE_EDGE.biomeID;
	}

	public static boolean isBeachBiome(int id)
	{
		return id == TFCBiome.BEACH.biomeID || id == TFCBiome.GRAVEL_BEACH.biomeID;
	}

	public static boolean isValidCharcoalPitCover(Block block)
	{
		if (Blocks.fire.getFlammability(block) > 0 && block != TFCBlocks.logPile)
			return false;

		return block == TFCBlocks.logPile || isCobbleStone(block) || isBrickStone(block) || isSmoothStone(block) || isGround(block) || block == Blocks.glass
		|| block == Blocks.stained_glass || block == TFCBlocks.metalTrapDoor || block == Blocks.iron_door || block.isOpaqueCube();
	}

	public static void writeInventoryToNBT(NBTTagCompound nbt, ItemStack[] storage)
	{
		writeInventoryToNBT(nbt, storage, "Items");
	}

	public static void writeInventoryToNBT(NBTTagCompound nbt, ItemStack[] storage, String name)
	{
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < storage.length; i++)
		{
			if (storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				storage[i].writeToNBT(nbttagcompound1);
				nbttagcompound1.setInteger("Stack", storage[i].stackSize);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag(name, nbttaglist);
	}

	public static void readInventoryFromNBT(NBTTagCompound nbt, ItemStack[] storage)
	{
		readInventoryFromNBT(nbt, storage, "Items");
	}

	public static void readInventoryFromNBT(NBTTagCompound nbt, ItemStack[] storage, String name)
	{
		NBTTagList nbttaglist = nbt.getTagList(name, 10);
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			int stack = nbttagcompound1.getInteger("Stack");
			if (byte0 >= 0 && byte0 < storage.length)
			{
				ItemStack is = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				if(is != null && nbttagcompound1.hasKey("Stack"))
				{
					is.stackSize = stack;
				}
				storage[byte0] = is;
			}
		}
	}

	public static ItemStack getItemInInventory(Item item, IInventory iinv)
	{
		for (int i = 0; i < iinv.getSizeInventory(); i++)
		{
			iinv.getStackInSlot(i);
			if (iinv.getStackInSlot(i) != null && iinv.getStackInSlot(i).getItem() == item)
			{
				return iinv.getStackInSlot(i);
			}
		}
		return null;
	}

	public static void destroyBlock(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z) != Blocks.air)
		{
			world.getBlock(x, y, z).dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	public static boolean areItemsEqual(ItemStack is1, ItemStack is2)
	{
		Item i1 = null;
		int d1 = 0;
		Item i2 = null;
		int d2 = 0;
		if (is1 != null)
		{
			i1 = is1.getItem();
			d1 = is1.getItemDamage();
		}
		if (is2 != null)
		{
			i2 = is2.getItem();
			d2 = is2.getItemDamage();
		}
		return i1 == i2 && d1 == d2;
	}

	public static boolean setBlockWithDrops(World world, int x, int y, int z, Block b, int meta)
	{
		Block block = world.getBlock(x, y, z);

		if (block.getMaterial() != Material.air)
		{
			int l = world.getBlockMetadata(x, y, z);
			world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (l << 12));
			block.dropBlockAsItem(world, x, y, z, l, 0);
		}
		return world.setBlock(x, y, z, b, meta, 3);
	}

	/**
	 * This is a wrapper method for the vanilla world method with no MCP mapping
	 */
	public static boolean setBlockToAirWithDrops(World world, int x, int y, int z)
	{
		return world.func_147480_a(x, y, z, true);
	}

	public static boolean isWaterBiome(BiomeGenBase b)
	{
		return TFC_Core.isBeachBiome(b.biomeID) || TFC_Core.isOceanicBiome(b.biomeID) || b == TFCBiome.LAKE || b == TFCBiome.RIVER;
	}

	public static String translate(String s)
	{
		return StatCollector.translateToLocal(s);
	}

	public static void sendInfoMessage(EntityPlayer player, IChatComponent text)
	{
		text.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(true);
		player.addChatComponentMessage(text);
	}

	public static long getSuperSeed(World w)
	{
		return w.getSeed() + w.getWorldInfo().getNBTTagCompound().getLong("superseed");
	}

	public static boolean isExposed(World world, int x, int y, int z)
	{
		int highestY = world.getPrecipitationHeight(x, z) - 1;
		boolean isExposed = true;
		if (highestY <= y)
		{
			return true;
		}
		if (world.canBlockSeeTheSky(x, y + 1, z)) // Either no blocks, or
													// transparent blocks above.
		{
			// Glass blocks, or blocks with a solid top or bottom block the
			// rain.
			if (world.getBlock(x, highestY, z) instanceof BlockGlass || world.getBlock(x, highestY, z) instanceof BlockStainedGlass
			|| world.isSideSolid(x, highestY, z, ForgeDirection.UP) || world.isSideSolid(x, highestY, z, ForgeDirection.DOWN)
			|| world.getBlock(x, highestY, z) instanceof BlockTileRoof || world.getBlock(x, highestY, z) instanceof BlockInvisible 
			|| world.getBlock(x, highestY, z) instanceof BlockChimney)
				isExposed = false;
		}
		else // Can't see the sky
			isExposed = false;

		return isExposed;
	}

	public static boolean isExposedToRain(World world, int x, int y, int z)
	{
		return world.isRaining() && isExposed(world, x, y, z);
	}

	// Tells us how much above or below our speed class we are
	// Changed so that it always returns nothing.
	public static String getAttackSpeedModifierString(int attackSpeed)
	{

		if (attackSpeed <= 0 || true)
		{
			return "";
		}
		else
		{
			attackSpeed %= 5;
			switch (attackSpeed)
			{
			case 0:
				return "";
			case 1:
				return "attackspeed.-1";
			case 2:
				return "attackspeed.-2";
			case 3:
				return "attackspeed.+2";
			case 4:
				return "attackspeed.+1";
			}
			return "";
		}
	}

	public static String getAttackSpeedString(int attackSpeed)
	{
		if (attackSpeed <= 0)
		{
			return "";
		}
		else
		{
			double attSpeed = (double) attackSpeed / 5d;
			attSpeed = Math.round(attSpeed);
			attackSpeed = (int) attSpeed * 5;
			switch (attackSpeed)
			{
			case 0:
				return "attackspeed.0";
			case 5:
				return "attackspeed.1";
			case 10:
				return "attackspeed.2";
			case 15:
				return "attackspeed.3";
			case 20:
				return "attackspeed.4";
			case 25:
				return "attackspeed.5";
			case 30:
				return "attackspeed.6";
			case 35:
				return "attackspeed.7";
			default:
				return "attackSpeed.8";
			}
		}
	}

	public static EnumChatFormatting getChatFormatAttackSpeed(int attackSpeed)
	{
		if (attackSpeed <= 0)
		{
			return EnumChatFormatting.WHITE;
		}
		else
		{
			attackSpeed %= 5;
			switch (attackSpeed)
			{
			case 0:
				return EnumChatFormatting.WHITE;
			case 1:
				return EnumChatFormatting.YELLOW;
			case 2:
				return EnumChatFormatting.RED;
			case 3:
				return EnumChatFormatting.GREEN;
			case 4:
				return EnumChatFormatting.DARK_GREEN;
			}
			return EnumChatFormatting.WHITE;
		}
		/*
		 * if(attackSpeed <= 0) { return EnumChatFormatting.OBFUSCATED; } else {
		 * attackSpeed -= attackSpeed%5; switch(attackSpeed) { case 0: return
		 * EnumChatFormatting.GREEN; case 5: return EnumChatFormatting.GREEN;
		 * case 10: return EnumChatFormatting.YELLOW; case 15: return
		 * EnumChatFormatting.YELLOW; case 20: return EnumChatFormatting.GRAY;
		 * case 25: return EnumChatFormatting.GRAY; case 30: return
		 * EnumChatFormatting.RED; case 35: return EnumChatFormatting.RED;
		 * default: return EnumChatFormatting.DARK_RED; } }
		 */
	}

	public static String getDrunkennessString(long drunkenness)
	{
		if(drunkenness < 250)
		{
			return "";
		}
		else if(drunkenness < 1000)
		{
			return "gui.buzzed";
		}
		else if(drunkenness < 8000)
		{
			return "gui.drunk";
		}
		else if(drunkenness < 12000)
		{
			return "gui.drunk2";
		}
		else
		{
			return "gui.wasted";
		}
	}
}
