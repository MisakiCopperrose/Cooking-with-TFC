package com.dunk.tfc.Blocks.Vanilla;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockBranch2;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Food.FloraIndex;
import com.dunk.tfc.Food.FloraManager;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.TileEntities.TEFruitLeaves;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Util.Helper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class BlockCustomLeaves extends BlockLeaves implements IShearable
{
	protected int adjacentTreeBlocks[][][];
	protected String[] woodNames;
	protected IIcon[] icons;
	protected IIcon[] iconsOpaque;
	protected IIcon[] willowBottom;

	public BlockCustomLeaves()
	{
		super();
		this.canBlockGrass = true;
		this.woodNames = new String[16];
		this.willowBottom = new IIcon[2];
		System.arraycopy(Global.WOOD_ALL, 0, this.woodNames, 0, 16);
		this.icons = new IIcon[16];
		this.iconsOpaque = new IIcon[16];
		this.setTickRandomly(true);

	}

	@Override
	public int tickRate(World world)
	{
		return 200;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return !BlockBranch.shouldDefinitelyLoseLeaf(world, x, y, z, this instanceof BlockCustomLeaves2) && super.canBlockStay(world, x, y, z);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		if (side == ForgeDirection.DOWN)
		{
			return true;
		}
		return false;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// Leaves are not added to the creative tab
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		return TerraFirmaCraft.proxy.foliageColorMultiplier(bAccess, x, y, z);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		entity.motionX *= 0.1D;
		entity.motionZ *= 0.1D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		Block block = world.getBlock(x, y, z);
		/*
		 * if(!Minecraft.isFancyGraphicsEnabled() && block == this) return
		 * false;
		 */
		if (side == 0 && this.minY > 0.0D)
			return true;
		else if (side == 1 && this.maxY < 1.0D)
			return true;
		else if (side == 2 && this.minZ > 0.0D)
			return true;
		else if (side == 3 && this.maxZ < 1.0D)
			return true;
		else if (side == 4 && this.minX > 0.0D)
			return true;
		else if (side == 5 && this.maxX < 1.0D)
			return true;
		else
			return !block.isOpaqueCube();
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (rand.nextInt(10) == 0 && !world.isRemote)
		{
			onNeighborBlockChange(world, x, y, z, null);
		}
		if (!world.isRemote)
		{
			float rainfall = TFC_Climate.getRainfall(world, x, y, z);
			//Make it possible for jungle sounds with rainfall level 800 but a lot less likely
			if (TFC_Climate.getBioTemperatureHeight(world, x, y, z) > 18 && rainfall > 800 && world.rand.nextInt(200) < rainfall - 800)
			{
				if (world.isRaining()?rand.nextInt(1000) < 5 : rand.nextInt(100) < 5)
				{
					if(rand.nextInt(100) > 0)
					{
						world.playSoundEffect(x, y, z, TFC_Sounds.JUNGLE, rand.nextFloat()*0.6f + 1.2f, rand.nextFloat()*0.2f + 0.9f);
					}
					else
					{
						world.playSoundEffect(x, y, z, TFC_Sounds.JUNGLECHIRPS, rand.nextFloat()*0.6f + 1.2f, rand.nextFloat()*0.2f + 0.9f);
					}
				}
				
			}
			else if (TFC_Climate.getHeightAdjustedTemp(world, x, y, z) > 16 && world.isDaytime() && TFC_Time.isSummer(z) && rainfall > 200)
			{
				if (rand.nextInt(100) < 5)
				{
					world.playSoundEffect(x, y, z, TFC_Sounds.CICADAS, rand.nextFloat()*0.6f + 1.2f, rand.nextFloat()*0.2f + 0.9f);
				}
			}
		}
	}

	@Override
	public void beginLeavesDecay(World world, int x, int y, int z)
	{
		// We don't do vanilla leaves decay
	}

	@Override
	public void onNeighborBlockChange(World world, int xOrig, int yOrig, int zOrig, Block b)
	{

		if (!world.isRemote)
		{
			int var6 = world.getBlockMetadata(xOrig, yOrig, zOrig);

			byte searchRadius = 4;
			int maxDist = searchRadius + 1;
			byte searchDistance = 11;
			int center = searchDistance / 2;
			adjacentTreeBlocks = null;
			if (this.adjacentTreeBlocks == null)
				this.adjacentTreeBlocks = new int[searchDistance][searchDistance][searchDistance];

			if (world.checkChunksExist(xOrig - maxDist, yOrig - maxDist, zOrig - maxDist, xOrig + maxDist, yOrig + maxDist, zOrig + maxDist))
			{
				for (int xd = -searchRadius; xd <= searchRadius; ++xd)
				{
					int searchY = searchRadius - Math.abs(xd);
					for (int yd = -searchY; yd <= searchY; ++yd)
					{
						int searchZ = searchY - Math.abs(yd);
						for (int zd = -searchZ; zd <= searchZ; ++zd)
						{
							Block block = world.getBlock(xOrig + xd, yOrig + yd, zOrig + zd);

							if (((block == TFCBlocks.logNatural && !(this instanceof BlockCustomLeaves2)) || (block == TFCBlocks.logNatural2 && this instanceof BlockCustomLeaves2)
							|| (block instanceof BlockBranch && ((block instanceof BlockBranch2) == (this instanceof BlockCustomLeaves2))))
							&& var6 == world.getBlockMetadata(xOrig + xd, yOrig + yd, zOrig + zd))
								this.adjacentTreeBlocks[xd + center][yd + center][zd + center] = 0;
							else if (block == this && var6 == world.getBlockMetadata(xOrig + xd, yOrig + yd, zOrig + zd))
								this.adjacentTreeBlocks[xd + center][yd + center][zd + center] = -2;
							else
								this.adjacentTreeBlocks[xd + center][yd + center][zd + center] = -1;
						}
					}
				}

				for (int pass = 1; pass <= 4; ++pass)
				{
					for (int xd = -searchRadius; xd <= searchRadius; ++xd)
					{
						int searchY = searchRadius - Math.abs(xd);
						for (int yd = -searchY; yd <= searchY; ++yd)
						{
							int searchZ = searchY - Math.abs(yd);
							for (int zd = -searchZ; zd <= searchZ; ++zd)
							{
								if (this.adjacentTreeBlocks[xd + center][yd + center][zd + center] == pass - 1)
								{
									if (this.adjacentTreeBlocks[xd + center - 1][yd + center][zd + center] == -2)
										this.adjacentTreeBlocks[xd + center - 1][yd + center][zd + center] = pass;

									if (this.adjacentTreeBlocks[xd + center + 1][yd + center][zd + center] == -2)
										this.adjacentTreeBlocks[xd + center + 1][yd + center][zd + center] = pass;

									if (this.adjacentTreeBlocks[xd + center][yd + center - 1][zd + center] == -2)
										this.adjacentTreeBlocks[xd + center][yd + center - 1][zd + center] = pass;

									if (this.adjacentTreeBlocks[xd + center][yd + center + 1][zd + center] == -2)
										this.adjacentTreeBlocks[xd + center][yd + center + 1][zd + center] = pass;

									if (this.adjacentTreeBlocks[xd + center][yd + center][zd + center - 1] == -2)
										this.adjacentTreeBlocks[xd + center][yd + center][zd + center - 1] = pass;

									if (this.adjacentTreeBlocks[xd + center][yd + center][zd + center + 1] == -2)
										this.adjacentTreeBlocks[xd + center][yd + center][zd + center + 1] = pass;
								}
							}
						}
					}
				}
			}

			int res = this.adjacentTreeBlocks[center][center][center];

			if (res < 0)
			{
				if (world.getChunkFromBlockCoords(xOrig, zOrig) != null)
					this.destroyLeaves(world, xOrig, yOrig, zOrig);
			}
		}
	}

	private void destroyLeaves(World world, int x, int y, int z)
	{
		world.setBlockToAir(x, y, z);
	}

	private void removeLeaves(World world, int x, int y, int z)
	{
		dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		if (world.rand.nextInt(100) < 30)
			dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.stick, 1));
		world.setBlockToAir(x, y, z);
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return rand.nextInt(20) != 0 ? 0 : 1;
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j)
	{
		return Item.getItemFromBlock(TFCBlocks.sapling);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float f, int i1)
	{
		// Do Nothing
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int meta)
	{
		if (!world.isRemote)
		{
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			int[] equipIDs = OreDictionary.getOreIDs(itemstack);
			for (int id : equipIDs)
			{
				String name = OreDictionary.getOreName(id);
				if (name.startsWith("itemScythe"))
				{
					for (int x = -1; x < 2; x++)
					{
						for (int z = -1; z < 2; z++)
						{
							for (int y = -1; y < 2; y++)
							{
								if (world.getBlock(i + x, j + y, k + z).getMaterial() == Material.leaves
								&& entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem) != null)
								{
									entityplayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
									entityplayer.addExhaustion(0.045F);
									if (world.rand.nextInt(100) < 11)
										dropBlockAsItem(world, i + x, j + y, k + z, new ItemStack(TFCItems.stick, 1));
									else if (world.rand.nextInt(100) < 4 && TFCOptions.enableSaplingDrops)
										dropSapling(world, i + x, j + y, k + z, meta);
									removeLeaves(world, i + x, j + y, k + z);
									super.harvestBlock(world, entityplayer, i + x, j + y, k + z, meta);

									itemstack.damageItem(1, entityplayer);
									if (itemstack.stackSize == 0)
										entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
								}
							}
						}
					}
					return;
				}
			}

			// Only executes if scythe wasn't found
			entityplayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
			entityplayer.addExhaustion(0.025F);
			if (world.rand.nextInt(100) < 28)
				dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.stick, 1));
			else if (world.rand.nextInt(100) < 6 && TFCOptions.enableSaplingDrops)
				dropSapling(world, i, j, k, meta);

			super.harvestBlock(world, entityplayer, i, j, k, meta);

		}
	}

	protected void dropSapling(World world, int x, int y, int z, int meta)
	{
		if (meta != 9 && meta != 15)
			dropBlockAsItem(world, x, y, z, new ItemStack(this.getItemDropped(0, null, 0), 1, meta));
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (!(this instanceof BlockCustomLeaves2))
		{
			if (meta == 14 && side == 0)
			{
				if (TerraFirmaCraft.proxy.getGraphicsLevel())
					return this.willowBottom[1];
				else
					return this.willowBottom[0];
			}
		}
		if (meta > woodNames.length - 1)
			meta = 0;
		if (TerraFirmaCraft.proxy.getGraphicsLevel())
			return this.icons[meta];
		else
			return this.iconsOpaque[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for (int i = 0; i < this.woodNames.length; i++)
		{
			this.icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + this.woodNames[i] + " Leaves Fancy");
			this.iconsOpaque[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + this.woodNames[i] + " Leaves");
		}
		this.willowBottom[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + Global.WOOD_ALL[14] + " Leaves Bottom");
		this.willowBottom[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + Global.WOOD_ALL[14] + " Leaves Bottom Fancy");
	}

	@Override
	public String[] func_150125_e()
	{
		return this.woodNames.clone();
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
}
