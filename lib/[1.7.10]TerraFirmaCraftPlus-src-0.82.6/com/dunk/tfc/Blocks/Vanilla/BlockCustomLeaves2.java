package com.dunk.tfc.Blocks.Vanilla;

import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.FloraIndex;
import com.dunk.tfc.Food.FloraManager;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.TileEntities.TEFruitLeaves;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Util.Helper;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCustomLeaves2 extends BlockCustomLeaves implements ITileEntityProvider
{
	public static String[] fruitNames = Global.FRUIT_META_NAMES;
	public static IIcon[] iconsFruit = new IIcon[16];
	public static IIcon[] iconsFlowers = new IIcon[16];
	public static IIcon[] iconsFancyFlowers = new IIcon[16];

	public BlockCustomLeaves2()
	{
		super();
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
		icons = new IIcon[woodNames.length];
		iconsOpaque = new IIcon[woodNames.length];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		super.registerBlockIcons(iconRegisterer);
		for (int i = 0; i < Global.FRUIT_META_NAMES.length; i++)
		{
			iconsFruit[i] = iconRegisterer
					.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + fruitNames[i] + " Fruit");
			iconsFlowers[i] = iconRegisterer
					.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + fruitNames[i] + " Flowers");
			iconsFancyFlowers[i] = iconRegisterer
					.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + fruitNames[i] + " Flowers");			
		}
		iconsFancyFlowers[6] = iconRegisterer
				.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/Cherry Flowers Fancy");
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		super.onNeighborBlockChange(world, x, y, z, b);
		if (world.getBlockMetadata(x, y, z) == 10)
		{
			lifeCycle(world, x, y, z);
		}
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j)
	{
		return j != 10 ? Item.getItemFromBlock(TFCBlocks.sapling2) : null;
	}

	@Override
	protected void dropSapling(World world, int x, int y, int z, int meta)
	{
		if (meta != 0 && meta != 10)
			dropBlockAsItem(world, x, y, z, new ItemStack(this.getItemDropped(0, null, 0), 1, meta));
	}

	/* Left-Click Harvest Fruit */
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (!world.isRemote && meta == 10)
		{
			TEFruitLeaves te = (TEFruitLeaves) world.getTileEntity(x, y, z);
			int fruitIndex = te.fruitType;
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(getType(this, fruitIndex));

			if (fi != null && (fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) || fi
					.inHarvest((TFC_Time.getSeasonAdjustedMonth(z) + 11) % 12)))
			{

				if (te != null && te.hasFruit)
				{
					te.hasFruit = false;
					te.dayHarvested = TFC_Time.getTotalDays();
					world.setBlockMetadataWithNotify(x, y, z, meta, 3);
					dropBlockAsItem(world, x, y, z, ItemFoodTFC.createTag(fi.getOutput(),
							Helper.roundNumber(4 + (world.rand.nextFloat() * 12), 10)));
				}
			}
		}
	}

	/* Right-Click Harvest Fruit */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX,
			float hitY, float hitZ)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (!world.isRemote && meta == 10)
		{
			FloraManager manager = FloraManager.getInstance();
			TEFruitLeaves te = (TEFruitLeaves) world.getTileEntity(x, y, z);
			FloraIndex fi = manager.findMatchingIndex(getType(this, te.fruitType));

			if (fi != null && (fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) || fi
					.inHarvest((TFC_Time.getSeasonAdjustedMonth(z) + 11) % 12)))
			{

				if (te != null && te.hasFruit)
				{
					te.hasFruit = false;
					te.dayHarvested = TFC_Time.getTotalDays();
					world.setBlockMetadataWithNotify(x, y, z, meta, 3);
					world.markBlockForUpdate(x, y, z);
					dropBlockAsItem(world, x, y, z, ItemFoodTFC.createTag(fi.getOutput(),
							Helper.roundNumber(4 + (world.rand.nextFloat() * 12), 10)));
					return true;
				}
			}
		}
		return false;
	}

	public static String getType(Block block, int fruitType)
	{
		if (fruitType >= 0 && fruitType < Global.FRUIT_META_NAMES.length)
		{
			return Global.FRUIT_META_NAMES[fruitType];
		}
		return "";
	}

	private void lifeCycle(World world, int x, int y, int z)
	{
		if (!world.isRemote && world.getBlockMetadata(x, y, z) == 10)
		{
			TEFruitLeaves te = (TEFruitLeaves) world.getTileEntity(x, y, z);
			Random rand = new Random();
			int fruitType = te.fruitType;

			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = manager.findMatchingIndex(getType(this, fruitType));

			float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);

			if (te != null)
			{
				if (fi != null)
				{
					if (!fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)))
					{
						if (te.hasFruit)
						{
							te.hasFruit = false;
							world.setBlockMetadataWithNotify(x, y, z, 10, 0x2);
						}

					}
					if (fi.inHarvest(TFC_Time.getSeasonAdjustedMonth(z)) && !te.hasFruit && TFC_Time
							.getMonthsSinceDay(te.dayHarvested) > 1)
					{
						te.hasFruit = true;
						te.dayFruited = TFC_Time.getTotalDays();
						world.setBlockMetadataWithNotify(x, y, z, 10, 0x2);
					}
				}

				if (rand.nextInt(100) > 50)
					world.markBlockForUpdate(x, y, z);
			}
		}
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.leavesNewFruitRenderId;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		if (meta == 10)
		{
			return new TEFruitLeaves();
		}
		return null;
	}
}
