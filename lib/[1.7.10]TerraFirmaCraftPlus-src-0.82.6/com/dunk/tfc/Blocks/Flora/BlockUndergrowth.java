package com.dunk.tfc.Blocks.Flora;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.BlockTerra;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.TileEntities.TESapling;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class BlockUndergrowth extends BlockTerra
{
	protected IIcon[] icons;

	public BlockUndergrowth()
	{
		super(Material.wood);
		this.canBlockGrass = true;
		this.setBlockBounds(0f, 0.0F, 0f, 1f, 2f, 1f);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		this.icons = new IIcon[4];
		this.setTickRandomly(true);
	}

	public BlockUndergrowth(Material m)
	{
		super(m);
		this.canBlockGrass = m == Material.wood;
		this.setBlockBounds(0f, 0.0F, 0f, 1f, 2f, 1f);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		this.icons = new IIcon[3];
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		if(this != TFCBlocks.shrub)
		{
			return TerraFirmaCraft.proxy.foliageColorMultiplier(bAccess, x, y, z);
		}
		int r,g,b;
		double L = x % 23 + y % 25 + z % 27;
		Random rand = new Random((long) L);
		rand = new Random(rand.nextInt(100000));
		
		float rain = Math.min(TFC_Climate.getRainfall(Minecraft.getMinecraft().theWorld, x, y, z),750);
		float temp = TFC_Climate.getHeightAdjustedTemp(Minecraft.getMinecraft().theWorld, x, y, z);
		
		r = 225  - (int)((rain/750) * 60) + (rand.nextInt(60) - 30);
		g = 225 + (rand.nextInt(60) - 30); 
		b = 150 + (rand.nextInt(60) - 30);
		if(temp < 0)
		{
			g *= 0.65;
			b *= 0.5;
		}
		
		return (r<<16) + (g<<8) + b;
		//return 0;
	}

	@Override
	public int damageDropped(int i)
	{
		return i;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		return icons[j];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		if (this == TFCBlocks.undergrowth)
		{
			this.icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/Undergrowth Stem");
			this.icons[1] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/Undergrowth Leaves");
			this.icons[2] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/Undergrowth Leaves Autumn");
			this.icons[3] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/tropical_undergrowth_1");
		}
		else if (this == TFCBlocks.undergrowthPalm)
		{
			this.icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/tropical_undergrowth_1");
		}
		else if (this == TFCBlocks.shrub)
		{
			this.icons[1] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/shrub_stem");
			this.icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/shrub_leaves");
		}
		else
		{
			this.icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/Low Undergrowth Top");
			this.icons[1] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/Low Undergrowth Side");
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block b)
	{
		Block block = world.getBlock(i, j, k);
		if (!this.canBlockStay(world, i, j, k))
		{
			int meta = world.getBlockMetadata(i, j, k);
			// this.dropBlockAsItem(world, i, j, k, new ItemStack(this, 1,
			// meta));
			world.setBlockToAir(i, j, k);
		}
	}

	// Set the sapling growth timer the moment it is planted, instead of the
	// first random tick it gets after being planted.
	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{

	}

	/**
	 * Can this block stay at this position. Similar to canPlaceBlockAt except
	 * gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return world.getBlock(x, y - 1, z).isBlockSolid(world, x, y - 1, z, 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		if (this == TFCBlocks.undergrowth || this == TFCBlocks.undergrowthPalm)
		{
			double L = x % 23 + y % 25 + z % 27;
			Random r = new Random((long) L);
			r = new Random(r.nextInt(100000));
			float superScale = 1 + (r.nextInt(40) * 0.1f);
			this.setBlockBounds(0.5f - (0.125f * superScale), 0.0F, 0.5f - (0.125f * superScale), 0.5f + (0.125f * superScale), superScale * 2, 0.5f + (0.125f * superScale));
		}
		else if (this == TFCBlocks.fern)
		{
			this.setBlockBounds(0f, 0.0F, 0f, 1f, 0.5f, 1f);
		}
		else
		{
			this.setBlockBounds(0.2f, 0F, 0.2f, 0.8f, 0.8f, 0.8f);
		}
	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return world.getBlock(x, y, z).getMaterial().isReplaceable();
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess bAccess, int x, int y, int z)
	{
		return false;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if (this == TFCBlocks.undergrowth || this == TFCBlocks.undergrowthPalm)
		{
			// double L = x % 23 + y % 25 + z % 27;
			// Random r = new Random((long) L);
			// r = new Random(r.nextInt(100000));
			// double superScale = 1 + (r.nextInt(40) * 0.1);
			return AxisAlignedBB.getBoundingBox(x + 0.4f, y + 0.0F, z + 0.4f, x + 0.6f, y + 2, z + 0.6f);
			// return AxisAlignedBB.getBoundingBox(x + 0.5f - (0.025f *
			// superScale), y + 0.0F,
			// z + 0.5f - (0.025f * superScale), x + 0.5f + (0.025f *
			// superScale), y + superScale * 3,
			// z + 0.5f + (0.025f * superScale));
			// this.setBlockBounds(0.5f-(0.125f*superScale), 0.0F,
			// 0.5f-(0.125f*superScale), 0.5f+(0.125f*superScale), superScale,
			// 0.5f+(0.125f*superScale));
		}
		else if(this == TFCBlocks.shrub)
		{
			return AxisAlignedBB.getBoundingBox(x + 0.2F, y + 0.0F, z + 0.2F, x + 0.8f, y + 1.5, z + 0.8F);
		}
		else
		{
			return null;// AxisAlignedBB.getBoundingBox(x+0.f, y+0.0F,z+ 0f,x+
						// 1f,y+ 0.5f,z+ 1f);
		}
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.undergrowthRenderId;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		// we need to make sure the player has the correct tool out
		boolean isAxe = false;
		boolean isHammer = false;
		double L = x % 23 + y % 25 + x % 27;
		Random r = new Random((long) L);
		double superScale = 1 + (r.nextInt(40) * 0.1);
		if (meta == 1)
		{
			dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.straw, 1 + r.nextInt(3), 0));
		}
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if (!world.isRemote)
		{
			if (this == TFCBlocks.undergrowth || this == TFCBlocks.undergrowthPalm|| this == TFCBlocks.shrub)
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

						}
						/*
						 * else if (name.startsWith("itemHammer")) { isHammer =
						 * true; break; }
						 */
					}

					if (isAxe)
					{
						if (r.nextBoolean())
						{
							world.playSoundEffect(x, y, z, TFC_Sounds.BRANCHSNAP, 0.5f + (r.nextFloat() * 0.7f), 0.2f + (r.nextFloat() * 0.6f));
						}
						else
						{
							world.playSoundEffect(x, y, z, TFC_Sounds.TWIGSNAP, 0.5f + (r.nextFloat() * 0.7f), 0.2f + (r.nextFloat() * 0.6f));
						}
						if (r.nextInt(4) == 0)
						{
							dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.pole, 1));
						}
						else
						{
							dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.stick, r.nextInt(3), 0));
						}

					}
				}
				else
					dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.stick, r.nextInt(2) + 1, 0));
			}
			else if (this == TFCBlocks.fern)
			{
				dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.straw, r.nextInt(2) + 2, 0));
			}
		}
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (!world.isRemote)
		{
			float rainfall = TFC_Climate.getRainfall(world, x, y, z);
			// Make it possible for jungle sounds with rainfall level 800 but a
			// lot less likely
			if (TFC_Climate.getBioTemperatureHeight(world, x, y, z) > 18 && rainfall > 800 && world.rand.nextInt(200) < rainfall - 800)
			{
				if (rand.nextInt(100) < 5)
				{
					world.playSoundEffect(x, y, z, TFC_Sounds.JUNGLE, 1f, 1f);
				}
			}
		}
	}

	protected void checkChange(World world, int x, int y, int z)
	{
		if (!this.canBlockStay(world, x, y, z))
		{
			// this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y,
			// z), 0);
			world.setBlockToAir(x, y, z);
		}
	}
}
