package com.dunk.tfc.Blocks.Flora;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.BlockInvisible;
import com.dunk.tfc.Blocks.BlockTerra;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomIce;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves2;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.FloraIndex;
import com.dunk.tfc.Food.FloraManager;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.TileEntities.TEFruitLeaves;
import com.dunk.tfc.TileEntities.TEFruitTreeWood;
import com.dunk.tfc.WorldGen.Generators.WorldGenForests;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumTree;
import com.dunk.tfc.api.Util.Helper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class BlockBranch extends BlockTerra
{
	protected String[] woodNames;
	protected int searchDist = 10;
	protected static int damage;
	protected static int logs;
	protected boolean isStone;
	public IIcon[] sideIcons;
	public IIcon[] innerIcons;
	public IIcon[] rotatedSideIcons;

	public IIcon bananaBark;
	public IIcon bananaInner;

	public IIcon dateBark;
	public IIcon dateInner;

	protected boolean isTwo = false;

	private int sourceX = 0;
	private int sourceZ = 0;
	private int sourceY = 0;

	private boolean end = false;

	public BlockBranch()
	{
		super(Material.wood);
		this.setTickRandomly(true);
		this.woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		this.sideIcons = new IIcon[woodNames.length];
		this.innerIcons = new IIcon[woodNames.length];
		this.rotatedSideIcons = new IIcon[woodNames.length];
	}

	public BlockBranch setEnd(boolean e)
	{
		end = e;
		return this;
	}

	public boolean isEnd()
	{
		return end;
	}

	/**
	 * Given the meta and the blockNum, determine if this type of tree
	 * grows/loses its leaves
	 * 
	 * @param meta
	 *            the metadata of the block
	 * @param blockNum
	 *            the number of the block, ex branch = 0, branch2 = 1, etc.
	 * @return
	 */
	public static boolean canLoseLeaves(int meta, int blockNum)
	{
		if (blockNum == 1)
		{
			switch (meta)
			{
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				return false;
			}
		}
		else
		{
			switch (meta)
			{
			case 4:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 15:
				return false;
			}
		}
		return true;
	}

	private void dropLeaves(World world, int x, int y, int z, int height)
	{
		if (!world.isRemote)
		{
			Block b;
			Block bUp;
			int meta;

			int myMeta = world.getBlockMetadata(x, y, z);
			for (int i = 0; i > -(height + 7) && i + y > 0; i--)
			{
				b = world.getBlock(x, i + y, z);
				bUp = world.getBlock(x, i + y + 1, z);
				if (TFC_Core.isGrass(b))
				{
					meta = world.getBlockMetadata(x, y + i, z);
					world.setBlock(x, y + i, z, TFC_Core.getTypeForDirtFromGrass(b), meta, 2);
					if (bUp.isReplaceable(world, x, y + i + 1, z) && !bUp.getMaterial().isLiquid() && !(b instanceof BlockCustomIce))
					{
						world.setBlock(x, y + i + 1, z, TFCBlocks.leafLitter, (myMeta == 8 && !(this instanceof BlockBranch2)) ? 1 : 0, 2);
					}
					return;
				}
				else if (b.isSideSolid(world, x, y + i, z, ForgeDirection.UP) && !(bUp instanceof BlockCustomLeaves) && !(b instanceof BlockCustomLeaves)
				&& !(b instanceof BlockInvisible) && !(b instanceof BlockBranch || b == TFCBlocks.ice))
				{
					if (bUp.isReplaceable(world, x, y + i + 1, z))
					{
						world.setBlock(x, y + i + 1, z, TFCBlocks.leafLitter, (myMeta == 8 && !(this instanceof BlockBranch2)) ? 1 : 0, 2);
					}
					return;
				}
				if (b instanceof BlockInvisible)
				{
					return;
				}
			}
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		/*
		 * // We find out if we're in Christmas and a conifer if (this
		 * instanceof BlockBranch2) { return 0; } int meta =
		 * world.getBlockMetadata(x, y, z); if (renderSmallConiferBranch(meta,
		 * 0) && this.isEnd()) { // Check the date
		 * 
		 * boolean night = false; boolean morning = false; if
		 * (TFCOptions.enableDefaultCelestialAngle) { night = TFC_Time.getHour()
		 * > 18; morning = TFC_Time.getHour() < 6; } else { boolean dark =
		 * TFC_Core.getCelestialDaylight(world, x, y, z,
		 * TFC_Time.getTotalTicks(), 0.0125f) < 8; night = TFC_Time.getHour() >=
		 * 12 && dark; morning = TFC_Time.getHour() < 12 && dark; } boolean
		 * inGameChristmas = ((TFC_Time.getDayOfMonth() == 24 && night) ||
		 * (TFC_Time.getDayOfMonth() == 25 && morning)) && TFC_Time.getMonth()
		 * == TFC_Time.DECEMBER; inGameChristmas = false; if
		 * ((Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH) ==
		 * 11 && ((Calendar.getInstance(TimeZone.getDefault()).get(Calendar.
		 * DAY_OF_MONTH) == 24 && night) ||
		 * (Calendar.getInstance(TimeZone.getDefault()).get(Calendar.
		 * DAY_OF_MONTH) == 25 && morning))) || inGameChristmas) { World
		 * worldObj; if(MinecraftServer.getServer() != null &&
		 * MinecraftServer.getServer().worldServers.length > 0) { worldObj =
		 * MinecraftServer.getServer().getEntityWorld();
		 * worldObj.setLightValue(EnumSkyBlock.Block, x, y, z, 8); } return 8;
		 * 
		 * } else { World worldObj; if(MinecraftServer.getServer() != null &&
		 * MinecraftServer.getServer().worldServers.length > 0) { worldObj =
		 * MinecraftServer.getServer().getEntityWorld();
		 * worldObj.setLightValue(EnumSkyBlock.Block, x, y, z, 0); }
		 * 
		 * } }
		 */
		return 0;
	}

	/**
	 * Returns whether this branch renders as a small conifer, ie it renders
	 * small leaf blocks on its ends
	 * 
	 * @param meta
	 * @param blockNum
	 *            the number of the block. branch = 0, branch2 = 1, etc.
	 * @return
	 */
	public static boolean renderSmallConiferBranch(int meta, int blockNum)
	{

		if (blockNum == 0)
		{
			switch (meta)
			{
			case 8:
			case 10:
			case 12:
				return true;
			}
		}
		return false;
	}

	public static boolean canLoseLeaves(World world, int x, int y, int z)
	{
		Block b = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (b instanceof BlockBranch)
		{
			if (((BlockBranch) b).isEnd() && isBananaWood(world, x, y, z))
			{
				return false;
			}
			return canLoseLeaves(meta, b instanceof BlockBranch2 ? 1 : 0);

		}
		return true;
	}

	// If this leaf is adjacent to an existing leaf block of the same type or a
	// branch of the right type, ie right metadata, 1 or 2 and isEnd
	protected boolean validLeafLocation(World world, int x, int y, int z, int myMeta, boolean willow)
	{
		if (world.isRemote)
		{
			return false;
		}
		Block b = world.getBlock(x - 1, y, z);
		int blockMeta = world.getBlockMetadata(x - 1, y, z);
		boolean b2 = (this instanceof BlockBranch2);

		if (((b == TFCBlocks.leaves) || (b instanceof BlockBranch && (b instanceof BlockBranch2 == b2)) && ((BlockBranch) b).isEnd()) && blockMeta == myMeta)
		{
			return true;
		}
		b = world.getBlock(x + 1, y, z);
		blockMeta = world.getBlockMetadata(x + 1, y, z);
		if (((b == TFCBlocks.leaves) || (b instanceof BlockBranch && (b instanceof BlockBranch2 == b2)) && ((BlockBranch) b).isEnd()) && blockMeta == myMeta)
		{
			return true;
		}
		b = world.getBlock(x, y + 1, z);
		blockMeta = world.getBlockMetadata(x, y + 1, z);
		if ((b == TFCBlocks.leaves || (b instanceof BlockBranch && (b instanceof BlockBranch2 == b2)) && ((BlockBranch) b).isEnd()) && blockMeta == myMeta)
		{
			return true;
		}
		b = world.getBlock(x, y - 1, z);
		blockMeta = world.getBlockMetadata(x, y - 1, z);
		if (((b == TFCBlocks.leaves) || (b instanceof BlockBranch && (b instanceof BlockBranch2 == b2)) && ((BlockBranch) b).isEnd()) && blockMeta == myMeta)
		{
			return true;
		}
		b = world.getBlock(x, y, z - 1);
		blockMeta = world.getBlockMetadata(x, y, z - 1);
		if (((b == TFCBlocks.leaves) || (b instanceof BlockBranch && (b instanceof BlockBranch2 == b2)) && ((BlockBranch) b).isEnd()) && blockMeta == myMeta)
		{
			return true;
		}
		b = world.getBlock(x, y, z + 1);
		blockMeta = world.getBlockMetadata(x, y, z + 1);
		if (((b == TFCBlocks.leaves) || (b instanceof BlockBranch && (b instanceof BlockBranch2 == b2)) && ((BlockBranch) b).isEnd()) && blockMeta == myMeta)
		{
			return true;
		}
		return false;
	}

	private void handleLeafLossAndGrowth(World world, int x, int y, int z, Random rand, int dayDelta)
	{
		int randomChance = 1;
		if (isEnd() && rand.nextInt(randomChance) == 0 && canLoseLeaves(world, x, y, z) && !world.isRemote)
		{
			// Temporarily stop some trees from growing leaves back
			int meta = world.getBlockMetadata(x, y, z);
			int region = TFC_Climate.getRegionLayer(world, x, Global.SEALEVEL, z);
			boolean isUTAcacia = (this instanceof BlockBranch2 && meta == 0 && EnumTree.REGIONS[region] == EnumTree.AFRICA);
			boolean isBirchAspen = !(this instanceof BlockBranch2) && (meta == 1 || meta == 2);
			// Regrow leaves in the spring
			int season = TFC_Time.getSeasonAdjustedMonth(z);
			float lostDaysMultiplier = 0;
			float temp;
			int days = TFC_Time.getTotalDays();
			if (dayDelta > 0 && season >= TFC_Time.OCTOBER)
			{
				// This means we've skipped some time and it's currently time to
				// lose or gain leaves
				// we want to calculate how many losing days it's been since
				// last
				// load.
				int daySkip = TFC_Time.daysInMonth / 4;
				for (int i = -dayDelta; i <= 0; i += daySkip)
				{
					temp = TFC_Climate.getHeightAdjustedTempSpecificDay(world, days + i, x, y, z);
					if (temp <= 10 && TFC_Time.getSeasonFromDayOfYear(days + i, z) >= TFC_Time.OCTOBER)
					{
						lostDaysMultiplier++;
					}
				}
			}
			else if (dayDelta > 0 && season < TFC_Time.OCTOBER)
			{
				int daySkip = TFC_Time.daysInMonth / 4;
				for (int i = -dayDelta; i <= 0; i += daySkip)
				{
					temp = TFC_Climate.getHeightAdjustedTempSpecificDay(world, days + i, x, y, z);
					if (temp > 10 && TFC_Time.getSeasonFromDayOfYear(days + i, z) <= TFC_Time.AUGUST)
					{
						lostDaysMultiplier++;
					}
				}
			}
			boolean willow = !(this instanceof BlockBranch2) && meta == 14;
			temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);
			int numToLoseOrGain = (int) (2 + (lostDaysMultiplier * 8));
			int numTries = 0;
			int oldNumToLoseOrGain = numToLoseOrGain;
			while (numToLoseOrGain > 0 && numTries < 10)
			{
				if (season <= TFC_Time.AUGUST && temp > 10)
				{
					if (willow)
					{
						int num = Math.min(numTries / 2, 4);
						int ii = rand.nextInt(3 + num / 2) - (1 + num / 4);
						int jj = rand.nextInt(2 + num / 2) - (1 + num / 4);
						int kk = rand.nextInt(3 + num / 2) - (1 + num / 4);

						if (world.isAirBlock(x + ii, y + jj, z + kk) && !(world.getBlock(x + ii, y + jj, +kk) instanceof BlockCustomLeaves)
						&& validLeafLocation(world, x + ii, y + jj, z + kk, meta, true))
						{
							if (ii * ii + kk * kk <= 1 && !(ii == 0 && kk == 0 && jj < 1))
							{
								world.setBlock(x + ii, y + jj, z + kk, TFCBlocks.leaves, meta, 2);
								numToLoseOrGain--;
							}

						}
					}
					else
					{
						if (numToLoseOrGain > 0)
						{
							int num = Math.min(numTries / 2, 3);
							int i = rand.nextInt(3 + num * 2) - (1 + num);
							int j = rand.nextInt(((isUTAcacia || isBirchAspen) ? (1 * (num / 2)) : (2 * (num / 2))) + 1);
							int k = rand.nextInt(3 + num * 2) - (1 + num);
							if (isBirchAspen)
							{
								i = Math.min(Math.max(i, -2), 2);
								k = Math.min(Math.max(k, -2), 2);
							}
							if (world.isAirBlock(x + i, y + j, z + k) && validLeafLocation(world, x + i, y + j, z + k, meta, false))
							{
								world.setBlock(x + i, y + j, z + k, this instanceof BlockBranch2 ? TFCBlocks.leaves2 : TFCBlocks.leaves, meta, 2);
								if (this instanceof BlockBranch2 && meta == 10)
								{
									TEFruitTreeWood te = (TEFruitTreeWood) world.getTileEntity(x, y, z);
									if (te != null)
									{
										TEFruitLeaves te2 = (TEFruitLeaves) world.getTileEntity(x + i, y + j, z + k);
										if (te2 != null)
										{
											te2.fruitType = te.fruitType;
											world.markBlockForUpdate(x + i, y + j, z + k);
										}
									}
								}
								numToLoseOrGain--;
							}
						}
					}
				}
				else if (season >= TFC_Time.OCTOBER && shouldLoseLeaf(world, x, y, z, rand, this instanceof BlockBranch2, season, temp, meta))
				{
					if (!(this instanceof BlockBranch2) && meta == 14)
					{
						for (int ii = -2; ii < 3 && numToLoseOrGain > 0; ii++)
						{
							for (int jj = -3; jj <= 1 && numToLoseOrGain > 0; jj++)
							{
								for (int kk = -2; kk < 3 && numToLoseOrGain > 0; kk++)
								{
									if (world.getBlock(x + ii, y + jj, +kk) instanceof BlockCustomLeaves)
									{
										if (ii * ii + kk * kk <= 1 && !(ii == 0 && kk == 0 && jj < 1))
										{
											world.setBlockToAir(x + ii, y + jj, z + kk);
											numToLoseOrGain--;
										}
									}
								}
							}
						}
					}
					else
					{
						int i = rand.nextInt(9) - 4;
						int j = rand.nextInt(5);
						int k = rand.nextInt(9) - 4;

						int meta2 = world.getBlockMetadata(x + i, y + j, z + k);
						Block b = world.getBlock(x + i, y + j, z + k);
						if (b instanceof BlockCustomLeaves && BlockBranch.canLoseLeaves(meta2, b instanceof BlockCustomLeaves2 ? 1 : 0) && rand.nextBoolean())
						{
							if (rand.nextInt(4) != 0)
							{
								dropLeaves(world, x + i, y + j, z + k, 24);
							}
							world.setBlockToAir(x + i, y + j, z + k);
							numToLoseOrGain--;
						}
					}
				}
				if (oldNumToLoseOrGain == numToLoseOrGain)
				{
					numTries++;
				}
				oldNumToLoseOrGain = numToLoseOrGain;
			}
		}
	}

	private void lifeCycle(World world, int x, int y, int z)
	{
		if (!world.isRemote && world.getBlockMetadata(x, y, z) == 10)
		{
			TEFruitTreeWood te = (TEFruitTreeWood) world.getTileEntity(x, y, z);
			if (te != null)
			{
				Random rand = new Random();
				int fruitType = te.fruitType;

				FloraManager manager = FloraManager.getInstance();
				FloraIndex fi = manager.findMatchingIndex(BlockCustomLeaves2.getType(this, fruitType));

				float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);

				if (te.canFruit() && this.getDistanceToTrunk(world, x, y, z, 0) >= 3)
				{
					if (fi != null)
					{
						if (!fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)))
						{
							if (te.hasFruit)
							{
								te.hasFruit = false;
								((TEFruitTreeWood) te).broadcastPacketInRange();
								world.setBlockMetadataWithNotify(x, y, z, 10, 0x2);
							}

						}
						if (fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) && !te.hasFruit && TFC_Time.getMonthsSinceDay(te.dayHarvested) > 1)
						{
							te.hasFruit = true;
							te.dayFruited = TFC_Time.getTotalDays();
							((TEFruitTreeWood) te).broadcastPacketInRange();
							world.setBlockMetadataWithNotify(x, y, z, 10, 0x2);
						}
					}
				}
				else
				{
					te.hasFruit = false;
					((TEFruitTreeWood) te).broadcastPacketInRange();
					world.setBlockMetadataWithNotify(x, y, z, 10, 0x2);
				}
			}
		}
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 10 && this instanceof BlockBranch2 && isEnd())
		{
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (!world.isRemote && meta == 10 && this instanceof BlockBranch2 && isEnd() && TFCOptions.enableDebugMode)
		{
			TileEntity te = world.getTileEntity(x, y, z);
			System.out.println(((TEFruitTreeWood) te).fruitType);
			// return true;
		}
		if (!world.isRemote && meta == 10 && this instanceof BlockBranch2 && isEnd())
		{
			TileEntity te = world.getTileEntity(x, y, z);
			// Banana
			if (te != null && ((TEFruitTreeWood) te).canFruit() && ((TEFruitTreeWood) te).hasFruit)
			{
				FloraIndex fi = FloraManager.getInstance().findMatchingIndex(Global.FRUIT_META_NAMES[((TEFruitTreeWood) te).fruitType]);
				((TEFruitTreeWood) te).dayHarvested = TFC_Time.getTotalDays();
				((TEFruitTreeWood) te).hasFruit = false;
				world.setBlockMetadataWithNotify(x, y, z, meta, 3);
				((TEFruitTreeWood) te).broadcastPacketInRange();
				for (int i = 0; i < 16; i++)
				{
					dropBlockAsItem(world, x, y, z + 1, ItemFoodTFC.createTag(fi.getOutput(), Helper.roundNumber(6 + world.rand.nextFloat(), 10)));
				}
				return true;
			}
		}
		world.markBlockForUpdate(x, y, z);
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (!world.isRemote)
		{
			if (world.getChunkFromBlockCoords(x, z).lastSaveTime > 0 && world.isAirBlock(x + this.sourceX, y + this.sourceY, z + this.sourceZ))
			{
				// System.out.println("deleting branch. "+x+", " +y+", "+z +
				// Global.WOOD_ALL[world.getBlockMetadata(x, y, z)]);
				world.setBlockToAir(x, y, z);
			}

			if (isEnd())
			{
				handleLeafLossAndGrowth(world, x, y, z, rand, 0);
				if (world.getBlockMetadata(x, y, z) == 10 && this instanceof BlockBranch2)
				{
					lifeCycle(world, x, y, z);
					// world.markBlockForUpdate(x, y, z);
				}
				/*
				 * if(this == TFCBlocks.branchEnd2__y_ &&
				 * world.getBlockMetadata(x, y, z)==8 && rand.nextInt(1)==0 &&
				 * this.getDistanceToTrunk(world, x, y, z, 0)<14 &&
				 * (world.isAirBlock(x, y + 1, z)||world.getBlock(x, y+1,
				 * z)instanceof BlockCustomLeaves2)) { // world.setBlock(x, y+1,
				 * z, this,8,2); // world.setBlock(x, y, z,
				 * TFCBlocks.branch2__y_,8,2); }
				 */
			}
			// if(rand.nextInt(10)==0)world.scheduleBlockUpdate(x, y, z, this,
			// this.tickRate(world));
		}
		/*
		 * if (renderSmallConiferBranch(world.getBlockMetadata(x, y, z), this
		 * instanceof BlockBranch2 ? 1 : 0) && isEnd()) {
		 * world.updateLightByType(EnumSkyBlock.Block, x, y, z); }
		 */
	}

	@Override
	public int tickRate(World world)
	{
		return 50;
	}

	public static boolean shouldLoseLeaf(World world, int x, int y, int z, Random rand, boolean block2, int season, float temp, int meta)
	{
		if (season >= TFC_Time.OCTOBER && temp <= 10 && BlockBranch.canLoseLeaves(meta, block2 ? 1 : 0))
		{

			return true;

		}
		return false;
	}

	/**
	 * this method allows us to update branches to grow/lose leaves based on how
	 * long it's been since the chunk loaded.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param monthsSinceLastUpdate
	 */
	public void updateBranchTime(World world, int x, int y, int z, int daysSinceLastVisit)
	{
		if (canLoseLeaves(world, x, y, z))
		{
			// If we can lose or gain leaves
			int currentMonth = TFC_Time.currentMonth;
			int lastUpdateMonth = ((currentMonth + 12) - ((daysSinceLastVisit / TFC_Time.daysInMonth) % 12)) % 12;
			// This means we should be losing leaves
			handleLeafLossAndGrowth(world, x, y, z, new Random(), daysSinceLastVisit);
		}
	}

	public static boolean shouldDefinitelyLoseLeaf(World world, int x, int y, int z, boolean block2, int season, float temp, int meta)
	{
		if (season >= TFC_Time.OCTOBER && temp < 0 && BlockBranch.canLoseLeaves(meta, block2 ? 1 : 0))
		{
			return true;
		}
		return false;
	}

	public static boolean shouldDefinitelyLoseLeaf(World world, int x, int y, int z, boolean block2)
	{
		int season = TFC_Time.getSeasonAdjustedMonth(z);
		float temp = TFC_Climate.getHeightAdjustedBioTemp(world, TFC_Time.getTotalDays(), x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		return shouldDefinitelyLoseLeaf(world, x, y, z, block2, season, temp, meta);
	}

	public static boolean shouldLoseLeaf(World world, int x, int y, int z, Random rand, boolean block2)
	{
		int season = TFC_Time.getSeasonAdjustedMonth(z);
		float temp = TFC_Climate.getHeightAdjustedBioTemp(world, TFC_Time.getTotalDays(), x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		return shouldLoseLeaf(world, x, y, z, rand, block2, season, temp, meta);
	}

	public int getSourceY()
	{
		return sourceY;
	}

	public int getSourceX()
	{
		return sourceX;
	}

	public int getSourceZ()
	{
		return sourceZ;
	}

	public Block getSourceBlock(IBlockAccess world, int x, int y, int z, int depth)
	{

		if (depth > 1)
		{
			Block b = world.getBlock(x + sourceX, y + sourceY, z + sourceZ);

			if (b instanceof BlockBranch)
			{
				return ((BlockBranch) b).getSourceBlock(world, x + sourceX, y + sourceY, z + sourceZ, depth - 1);
			}
		}
		return world.getBlock(x + sourceX, y + sourceY, z + sourceZ);
	}

	public BlockBranch setSourceXYZ(int x, int y, int z)
	{
		sourceX = x;
		sourceY = y;
		sourceZ = z;
		return this;
	}

	public int getDistanceToTrunk(IBlockAccess world, int x, int y, int z, int total)
	{
		Block b = getSourceBlock(world, x, y, z, 1);
		if (total > 100)
		{
			// Obviously we have something circular;
			if (world instanceof World && !((World) world).isRemote)
			{
				((World) world).setBlockToAir(x, y, z);
			}
			return -1000;
		}
		if (b instanceof BlockBranch)
		{
			int totalX = ((BlockBranch) b).getSourceX() + getSourceX();
			int totalY = ((BlockBranch) b).getSourceY() + getSourceY();
			int totalZ = ((BlockBranch) b).getSourceZ() + getSourceZ();
			if (totalZ == 0 && totalY == 0 && totalX == 0)
			{
				return -1000;
			}
			if (b != this && !isEnd())
			{
				total += 6;
				return ((BlockBranch) b).getDistanceToTrunk(world, x + sourceX, y + sourceY, z + sourceZ, total);
			}
			total += 1;
			return ((BlockBranch) b).getDistanceToTrunk(world, x + sourceX, y + sourceY, z + sourceZ, total);
		}
		if ((b instanceof BlockLogNatural) || TFC_Core.isSoil(b) || TFC_Core.isSand(b))
		{
			return 1 + total;
		}
		if (world instanceof World && !((World) world).isRemote)
		{
			((World) world).setBlockToAir(x, y, z);
		}
		return -1000;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{

		// if (this instanceof BlockBranch2 && world.getBlockMetadata(x, y, z)
		// == 10 && isEnd())
		// this.setBlockBounds(0.3f, 0.3F, 0.3f, 0.7f, 0.7F, 0.7f);
		this.setBlockBounds(0f, 0F, 0f, 1f, 1F, 1f);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aaBB, List list, Entity entity)
	{
		// if (this.isEnd())
		// {
		this.setBlockBounds(0.3f, 0.3F, 0.3f, 0.7f, 0.7F, 0.7f);
		super.addCollisionBoxesToList(world, x, y, z, aaBB, list, entity);

		// This should allow us to add our collisions properly
		this.setBlockBounds(0.3f + (getSourceX() * 0.25f), 0.3F + (getSourceY() * 0.25f), 0.3f + (getSourceZ() * 0.25f), 0.7f + (getSourceX() * 0.25f),
		0.7F + (getSourceY() * 0.25f), 0.7f + (getSourceZ() * 0.25f));
		super.addCollisionBoxesToList(world, x, y, z, aaBB, list, entity);

		this.setBlockBounds(0.3f + (getSourceX() * 0.5f), 0.3F + (getSourceY() * 0.5f), 0.3f + (getSourceZ() * 0.5f), 0.7f + (getSourceX() * 0.5f), 0.7F + (getSourceY() * 0.5f),
		0.7f + (getSourceZ() * 0.5f));
		super.addCollisionBoxesToList(world, x, y, z, aaBB, list, entity);
		/*
		 * } / else { this.setBlockBounds(0.3f, 0.3F, 0.3f, 0.7f, 0.7F, 0.7f);
		 * super.addCollisionBoxesToList(world, x, y, z, aaBB, list, entity);
		 * 
		 * // This should allow us to add our collisions properly
		 * this.setBlockBounds(0.3f + (getSourceX() * 0.75f), 0.3F +
		 * (getSourceY() * 0.75f), 0.3f + (getSourceZ() * 0.75f), 0.7f +
		 * (getSourceX() * 0.75f), 0.7F + (getSourceY() * 0.75f), 0.7f +
		 * (getSourceZ() * 0.75f)); super.addCollisionBoxesToList(world, x, y,
		 * z, aaBB, list, entity); }
		 */
		// this.setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.branchRenderId;
	}

	protected boolean noLogsNearby(World world, int x, int y, int z)
	{
		return world.blockExists(x, y, z) && (world.getBlock(x, y, z) != this && world.getBlock(x, y, z) != TFCBlocks.logNatural);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for (int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		return this.blockHardness;
	}

	private boolean checkOut(World world, int x, int y, int z, int meta)
	{
		return world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == meta;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (isEnd())
		{
			return sideIcons[meta];
		}
		if (side == 0 || side == 1)
			return innerIcons[meta];
		return sideIcons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		for (int i = 0; i < woodNames.length; i++)
		{
			sideIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + woodNames[i] + " Log");
			innerIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + woodNames[i] + " Log Top");
			rotatedSideIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + woodNames[i] + " Log Side");
		}
		bananaBark = reg.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/Banana Wood");
		bananaInner = reg.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/Banana Inner");
		dateBark = reg.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/Date Wood");
		dateInner = reg.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/Date Inner");

	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		if (world.isRemote)
		{
			return;
		}
		// we need to make sure the player has the correct tool out
		boolean isAxe = false;
		boolean isHammer = false;
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if (!world.isRemote)
		{
			if (equip != null)
			{
				int[] equipIDs = OreDictionary.getOreIDs(equip);
				for (int id : equipIDs)
				{
					String name = OreDictionary.getOreName(id);
					if (name.startsWith("itemAxe"))
					{
						isAxe = true;
						if (name.startsWith("itemAxeStone"))
						{
							isStone = true;
							break;
						}
					}
					else if (name.startsWith("itemHammer"))
					{
						isHammer = true;
						break;
					}
				}

				if (isAxe)
				{
					damage = 0;
					// processTree(world, x, y, z, meta, equip);
					scanLogsForHarvest(world, entityplayer, x, y, z, meta, (byte) 0, (byte) 0, (byte) 0);

					/*
					 * if (entityplayer.getCurrentEquippedItem() != null) {
					 * //equip.damageItem(1, entityplayer); } else { //
					 * entityplayer.inventory.setInventorySlotContents(ind, //
					 * null); world.setBlock(x, y, z, this, meta, 0x2); }
					 */
					/*
					 * if (damage + equip.getItemDamage() >
					 * equip.getMaxDamage()) { //int ind =
					 * entityplayer.inventory.currentItem;
					 * entityplayer.inventory.setInventorySlotContents(ind,
					 * null); world.setBlock(x, y, z, this, meta, 0x2); } else
					 * equip.damageItem(damage, entityplayer);
					 */

					int smallStack = logs % 16;
					// dropBlockAsItem(world, x, y, z, new
					// ItemStack(TFCItems.logs, smallStack, damageDropped(meta)
					// * 2));
					logs -= smallStack;

					// In theory this section should never be triggered since
					// the full stacks are dropped higher up the tree, but
					// keeping it here just in case.
					while (logs > 0)
					{
						// dropBlockAsItem(world, x, y, z, new
						// ItemStack(TFCItems.logs, 16, damageDropped(meta)));
						logs -= 16;
					}

				}
				else if (isHammer)
				{
					// EntityItem item = new EntityItem(world, x + 0.5, y + 0.5,
					// z + 0.5,
					// new ItemStack(TFCItems.stick, 1 +
					// world.rand.nextInt(3)));
					// world.spawnEntityInWorld(item);
					world.setBlock(x, y, z, this, meta, 0x2);
				}
			}
			else
				world.setBlock(x, y, z, this, meta, 0x2);
		}
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int side, EntityPlayer entityplayer)
	{
		if (world.isRemote)
		{
			return;
		}
		int meta = world.getBlockMetadata(x, y, z);
		harvestBlock(world, entityplayer, x, y, z, meta);
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
		return false;
		/*if (world.isRemote)
		{
			return true;
		}
		boolean isAxe = false;
		boolean isHammer = false;
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if (!world.isRemote)
		{
			if (equip != null)
			{
				int[] equipIDs = OreDictionary.getOreIDs(equip);
				for (int id : equipIDs)
				{
					String name = OreDictionary.getOreName(id);
					if (name.startsWith("itemAxe"))
					{
						isAxe = true;
						if (name.startsWith("itemAxeStone"))
						{
							isStone = true;
							break;
						}
					}
					else if (name.startsWith("itemHammer"))
					{
						isHammer = true;
						break;
					}
				}
				return super.removedByPlayer(world, player, x, y, z, willHarvest);
			}
			return false;
		}
		return false;*/
    }

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex)
	{
		processTree(world, x, y, z, world.getBlockMetadata(x, y, z), null);
	}

	/*
	 * private void processTree(World world, int x, int y, int z, ItemStack is)
	 * { //TODO Rewrite the treecap algorithm using a list of coords instead of
	 * the ugly array. Shoudl also use a maxmium list size to prevent //any
	 * memory issues and should take shortcuts to find the top of the tree and
	 * search down }
	 */

	@Deprecated
	protected void processTree(World world, int x, int y, int z, int meta, ItemStack is)
	{
		boolean[][][] checkArray = new boolean[searchDist * 2 + 1][256][searchDist * 2 + 1];
		// scanLogs(world, x, y, z, meta, checkArray, (byte) 0, (byte) 0, (byte)
		// 0, is);
	}

	public boolean isLongDiagonal()
	{
		return this.sourceY != 0 && this.sourceX != 0 && this.sourceZ != 0;
	}

	public boolean isShortDiagonal()
	{
		return ((this.sourceY != 0 && this.sourceX != 0) || (this.sourceX != 0 && this.sourceZ != 0) || (this.sourceY != 0 && this.sourceZ != 0)) && !isLongDiagonal();
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return null;// TFCItems.logs;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{

		if (this instanceof BlockBranch2 && this.isEnd())
		{
			if (world.getBlockMetadata(x, y, z) == 10)
			{
				lifeCycle(world, x, y, z);
			}
		}
		if (world.getChunkFromBlockCoords(x, z).lastSaveTime > 0 && getDistanceToTrunk(world, x, y, z, 0) < 0)
		{
			world.setBlockToAir(x, y, z);
			return;
		}
		/*
		 * boolean check = false; for (int h = -2; h <= 2; h++) { for (int g =
		 * -2; g <= 2; g++) { for (int f = -2; f <= 2; f++) { if
		 * (world.getBlock(x + h, y + g, z + f) == this &&
		 * world.getBlockMetadata(x + h, y + g, z + f) == meta) check = true; }
		 * } } if (!check) { world.setBlockToAir(x, y, z); //
		 * dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.logs, 1, //
		 * damageDropped(meta))); }
		 */
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		if (isBananaWood(world, x, y, z))
		{
			if (side < 2)
			{
				return this.bananaInner;
			}
			return this.bananaBark;
		}
		else if (isDateWood(world, x, y, z))
		{

			if (side < 2)
			{
				return this.dateInner;
			}
			return this.dateBark;
		}
		else
		{
			int meta = world.getBlockMetadata(x, y, z);
			return this.getIcon(side, meta);
		}
	}

	/**
	 * This will return whether or not this branch is a banana. This is
	 * important because banana wood does not look like normal fruitwood
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static boolean isBananaWood(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		Block blo = world.getBlock(x, y, z);
		if (blo instanceof BlockBranch2 && meta == 10)
		{
			BlockBranch b = (BlockBranch) blo;
			if (b.getSourceX() == 0 && b.getSourceY() == -1 && b.getSourceZ() == 0)
				if (b.isEnd())
				{
					TileEntity te = world.getTileEntity(x, y, z);
					if (te != null)
					{
						return ((TEFruitTreeWood) te).fruitType == 1;
					}
				}
				else if (b == TFCBlocks.branch2__y_ && world.getBlock(x, y + 1, z) instanceof BlockBranch2)
				{
					return ((BlockBranch) (world.getBlock(x, y + 1, z))).isBananaWood(world, x, y + 1, z);
				}
		}
		return false;
	}

	/**
	 * This will return whether or not this branch is a banana. This is
	 * important because banana wood does not look like normal fruitwood
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static boolean isDateWood(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		Block blo = world.getBlock(x, y, z);
		if (blo instanceof BlockBranch2 && meta == 10)
		{
			BlockBranch b = (BlockBranch) blo;
			if (b.getSourceX() == 0 && b.getSourceY() == -1 && b.getSourceZ() == 0)
				if (b.isEnd())
				{
					TileEntity te = world.getTileEntity(x, y, z);
					if (te != null)
					{
						return ((TEFruitTreeWood) te).fruitType == 10;
					}
				}
				else if (b == TFCBlocks.branch2__y_ && world.getBlock(x, y + 1, z) instanceof BlockBranch2)
				{
					return ((BlockBranch) (world.getBlock(x, y + 1, z))).isDateWood(world, x, y + 1, z);
				}
		}
		return false;
	}

	public boolean scanLogsForHarvest(World world, EntityPlayer player, int i, int j, int k, int meta, byte x, byte y, byte z)
	{
		Random rand = new Random();
		if (!world.isRemote)
		{
			// What we do here is look in all directions for a block that points
			// at
			// us and then that's how we find everything
			for (int yDir = 1; yDir >= -1; yDir--)
			{
				for (int xDir = -1; xDir <= 1; xDir++)
				{
					for (int zDir = -1; zDir <= 1; zDir++)
					{
						// If we're not looking at our self and we're not
						// looking at
						// our source
						if (!(xDir == 0 && yDir == 0 && zDir == 0) && !(xDir == sourceX && yDir == sourceY && zDir == sourceZ))
						{
							Block b = TFC_Core.getSourcedBranchForBranch(this, xDir, yDir, zDir);
							Block match = world.getBlock(i + xDir, j + yDir, k + zDir);
							Block b2 = TFC_Core.getSourcedTerminalBranchForBranch(this, xDir, yDir, zDir);
							if (b == match || b2 == match)
							{
								// This means we've found a block which is
								// pointing
								// at us, meaning a block that thinks it is
								// growing
								// out of the current block, which would be its
								// "source"
								if (b == match)
								{
									if (!((BlockBranch) b).scanLogsForHarvest(world, player, i + xDir, j + yDir, k + zDir, meta, (byte) (x + (byte) xDir), (byte) (y + (byte) yDir),
									(byte) (z + (byte) zDir)))
									{
										return false;
									}
								}
								else
								{
									if (!((BlockBranch) b2).scanLogsForHarvest(world, player, i + xDir, j + yDir, k + zDir, meta, (byte) (x + (byte) xDir),
									(byte) (y + (byte) yDir), (byte) (z + (byte) zDir)))
									{
										return false;
									}
								}
							}
						}
					}
				}
			}

			BlockBranch b = null;
			if (world.getBlock(i, j, k) instanceof BlockBranch)
			{
				b = (BlockBranch) world.getBlock(i, j, k);
			}
			// damage++;
			if (player.getCurrentEquippedItem() != null && b != null)
			{
				// System.out.print("item damage was: " +
				// player.getCurrentEquippedItem().getItemDamage() +". ");

				//System.out.println("Harvesting log at " + x + ", " + y + ", " + z + " with item damage " + player.getCurrentEquippedItem().getItemDamage());

				// System.out.println("item damage was " +
				// player.getCurrentEquippedItem().getItemDamage() + " and will
				// be " + (player.getCurrentEquippedItem().getItemDamage() +
				// 1));
				Random rando = new Random();
				if (!((BlockBranch) b).isEnd() || rando.nextInt(4) == 0)
				{
					player.getCurrentEquippedItem().damageItem(1, player);
				}

				// System.out.println("Logs is " + logs);
				// System.out.println("damage is now: " +
				// player.getCurrentEquippedItem().getItemDamage());
				if (player.getCurrentEquippedItem().getItemDamage() >= player.getCurrentEquippedItem().getMaxDamage())
				{
					int ind = player.inventory.currentItem;
					player.inventory.setInventorySlotContents(ind, null);

				}
				if (player.getCurrentEquippedItem() != null && isStone && rand.nextInt(10) == 0)
				{
					world.setBlockToAir(i, j, k);
					notifyLeaves(world, i, j, k);
					return true;
				}
				// If the player's item isn't null after damaging it
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItemDamage() < player.getCurrentEquippedItem().getMaxDamage())
				{
					// This is how we damage the tool.
					if (b != null && meta == 1 && b.isEnd() && isTwo)
					{
						world.setBlock(i, j, k, Blocks.air, 0, 0x2);
						int r = (i % 17 + j % 13 + k % 11) % 8;
						Random random = new Random(r);
						if (random.nextBoolean())
						{
							dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.straw, 8 + r, 0));
						}
						else
						{
							dropBlockAsItem(world, i, j, k, new ItemStack(TFCBlocks.sapling2, 1, meta));
						}
					}
					else if (b != null && b.isEnd())
					{

						world.setBlock(i, j, k, Blocks.air, 0, 0x2);
						int r = (i + j << 4 + k << 8);
						Random random = new Random(r);
						int n = random.nextInt(4);
						if (n == 0)
						{
							if (random.nextInt(3) == 0)
							{
								world.playSoundEffect(i, j, k, TFC_Sounds.BRANCHSNAP, 0.5f + (random.nextFloat() * 0.7f), 0.6f + (random.nextFloat() * 0.3f));
							}
							else
							{
								world.playSoundEffect(i, j, k, TFC_Sounds.TWIGSNAP, 0.3f + (random.nextFloat() * 0.7f), 0.4f + (random.nextFloat() * 0.3f));
							}
						}

						int randNum = 100;
						if (!(this instanceof BlockBranch2) && (meta == 8 || meta == 10 || meta == 11))
						{
							randNum = 30;
						}
						randNum /= 3;

						if (meta == 8 && this instanceof BlockBranch2)
						{
							randNum = 2;
						}
						if (random.nextInt(randNum) == 0 && !(this instanceof BlockBranch2 && meta == 10))
						{
							dropBlockAsItem(world, i, j, k, new ItemStack(this instanceof BlockBranch2 ? TFCBlocks.sapling2 : TFCBlocks.sapling, 1, meta));
							notifyLeaves(world, i, j, k);
						}
						else
						{
							if (random.nextInt(10) > 0)
							{
								dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.stick,
								/* random.nextInt(random.nextInt(2) + 1) */1, 0));
							}
							else
							{
								dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.pole, 1, 0));
							}
							notifyLeaves(world, i, j, k);
						}
					}
					else
					{
						world.setBlockToAir(i, j, k);

						dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.logs, 1, damageDropped(meta) * 2));

						notifyLeaves(world, i, j, k);
					}
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
		/*
		 * if(y >= 0 && j + y < 256) { int offsetX = 0;int offsetY = 0;int
		 * offsetZ = 0; checkArray[x + searchDist][y][z + searchDist] = true;
		 * 
		 * for (offsetX = -3; offsetX <= 3; offsetX++) { for (offsetZ = -3;
		 * offsetZ <= 3; offsetZ++) { for (offsetY = 0; offsetY <= 2; offsetY++)
		 * { if(Math.abs(x + offsetX) <= searchDist && j + y + offsetY < 256 &&
		 * Math.abs(z + offsetZ) <= searchDist) { if(checkOut(world, i + x +
		 * offsetX, j + y + offsetY, k + z + offsetZ, meta) && !(offsetX == 0 &&
		 * offsetY == 0 && offsetZ == 0) && !checkArray[x + offsetX +
		 * searchDist][y + offsetY][z + offsetZ + searchDist]) scanLogs(world,i,
		 * j, k, meta, checkArray, (byte)(x + offsetX),(byte)(y +
		 * offsetY),(byte)(z + offsetZ), stack); } } } }
		 * 
		 * damage++; if(stack != null) { if(damage+stack.getItemDamage() <=
		 * stack.getMaxDamage()) { world.setBlock(i + x, j + y, k + z,
		 * Blocks.air, 0, 0x2); if (!isStone || world.rand.nextInt(10) != 0)
		 * logs++; if (logs >= 16) { dropBlockAsItem(world, i + x, j + y, k + z,
		 * new ItemStack(TFCItems.logs, 16, damageDropped(meta))); logs -= 16; }
		 * notifyLeaves(world, i + x, j + y, k + z); } } else {
		 * world.setBlockToAir(i, j, k); logs++; if (logs >= 16) {
		 * dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.logs, 16,
		 * damageDropped(meta))); logs -= 16; } notifyLeaves(world, i + x, j +
		 * y, k + z); } }
		 */
	}

	protected void notifyLeaves(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			/*
			 * for (int i = -1; i < 2; i++) { for (int j = -1; i < 2; j++) { for
			 * (int k = -1; i < 2; k++) { if (i != 0 || j != 0 || k != 0) {
			 * world.notifyBlockOfNeighborChange(x + i, y + j, z + k,
			 * Blocks.air); } } } }
			 */

			world.notifyBlockOfNeighborChange(x + 1, y, z, Blocks.air);
			world.notifyBlockOfNeighborChange(x - 1, y, z, Blocks.air);
			world.notifyBlockOfNeighborChange(x, y, z + 1, Blocks.air);
			world.notifyBlockOfNeighborChange(x, y, z - 1, Blocks.air);
			world.notifyBlockOfNeighborChange(x, y + 1, z, Blocks.air);
			world.notifyBlockOfNeighborChange(x, y - 1, z, Blocks.air);

		}
	}
}
