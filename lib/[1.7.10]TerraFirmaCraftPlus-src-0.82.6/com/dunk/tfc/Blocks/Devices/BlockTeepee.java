package com.dunk.tfc.Blocks.Devices;

import java.util.Iterator;
import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.BlockTerra;
import com.dunk.tfc.Blocks.BlockTerraContainer;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.TileEntities.TEIngotPile;
import com.dunk.tfc.TileEntities.TEOre;
import com.dunk.tfc.WorldGen.TFCBiome;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTeepee extends BlockTerra
{

	IIcon icons[];
	public BlockTeepee()
	{
		super();
		icons = new IIcon[2];
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
			float par8, float par9)
	{
		/*if (TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			TerraFirmaCraft.LOG.info("Meta = " + (new StringBuilder()).append(getUnlocalizedName()).append(":")
					.append(metadata).toString());
			TEOre te = (TEOre) world.getTileEntity(x, y, z);
			if (te != null)
				TerraFirmaCraft.LOG.info("Ore  BaseID = " + te.baseBlockID + "| BaseMeta =" + te.baseBlockMeta);
		}*/
		if(world.isRemote)
		{
			super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
			return true;
		}
		else
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(x, z) != TFCBiome.HELL)
			{
				if (isBedOccupied(meta))
				{
					EntityPlayer entityplayer1 = null;
					Iterator iterator = world.playerEntities.iterator();

					while (iterator.hasNext())
					{
						EntityPlayer entityplayer2 = (EntityPlayer)iterator.next();

						if (entityplayer2.isPlayerSleeping())
						{
							ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;

							if (chunkcoordinates.posX == x && chunkcoordinates.posY == y && chunkcoordinates.posZ == z)
							{
								entityplayer1 = entityplayer2;
							}
						}
					}

					if (entityplayer1 != null)
					{
						TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("tile.bed.occupied"));
						return true;
					}
					setBedOccupied(world, x, y, z, false);
				}
				float temp = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);
				if(this == TFCBlocks.teepee && temp < -10)
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("tile.teepee.tooCold"));
					return false;
				}
				EnumStatus enumstatus = player.sleepInBedAt(x, y, z);
				if (enumstatus == EnumStatus.OK)
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("tile.customBed.sleep"));
					setBedOccupied(world, x, y, z, true);
					return true;
				}
				else
				{
					if (enumstatus == EnumStatus.NOT_POSSIBLE_NOW)
						TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("tile.bed.noSleep"));
					else if (enumstatus == EnumStatus.NOT_SAFE)
						TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("tile.bed.notSafe"));

					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isToolEffective(String type, int metadata)
    {
		return false;
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.getBlock(x, y-1, z).isSideSolid(world, x, y-1, z, ForgeDirection.UP))
		{
			this.onBlockDestroyed(world, x, y, z, world.getBlockMetadata(x, y, z));
		}
	}
	
	/**
	 * Return whether or not the bed is occupied.
	 */
	public static boolean isBedOccupied(int par0)
	{
		return (par0 & 8) != 0;
	}

	@Override
	public void setBedOccupied(IBlockAccess world, int x, int y, int z, EntityPlayer player, boolean occupied)
	{
		setBedOccupied((World)world, x, y, z, occupied);
	}
	/**
	 * Sets whether or not the bed is occupied.
	 */
	public static void setBedOccupied(World par0World, int x, int y, int z, boolean par4)
	{
		int meta  = par0World.getBlockMetadata(x, y, z);
		if(par4 && meta < 8)
		{
			meta += 8;
		}
		else if(!par4 && meta >= 8)
		{
			meta -= 8;
		}
		par0World.setBlockMetadataWithNotify(x, y, z, meta, 4);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		this.blockIcon = icons[0];
		if (side == 1)
		{
			return icons[1];
		}
		if (side == 0)
		{
			return icons[0];
		}
		return icons[1];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.icons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "teepeeWall");
		this.icons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "teepeeDoor");
		
		if(this == TFCBlocks.bearTeepee)
		{
			this.icons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "bearTeepeeWall");
			this.icons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "bearTeepeeDoor");
		}
		else if(this == TFCBlocks.wolfTeepee)
		{
			this.icons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wolfTeepeeWall");
			this.icons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wolfTeepeeDoor");
		}
	}

	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		
		int meta = world.getBlockMetadata(i, j, k);
		if(meta%4 == 0)
		{
			this.setBlockBounds(0, 0, -1, 1, 0.075f, 1);
			return AxisAlignedBB.getBoundingBox(i+0, j+0, k-1, i+1, j+0.075, k+1);
		}
		else if(meta%4 == 1)
		{
			this.setBlockBounds(0, 0, 0, 2, 0.075f, 1);
			return AxisAlignedBB.getBoundingBox(i+0, j+0, k+0, i+2, j+0.075, k+1);
		}
		else if(meta%4 == 2)
		{
			this.setBlockBounds(0, 0, 0, 1, 0.075f, 2);
			return AxisAlignedBB.getBoundingBox(i+0, j+0, k+0, i+1, j+0.075, k+2);
		}
		else if(meta%4 == 3)
		{
			this.setBlockBounds(-1, 0, 0, 1, 0.075f, 1);
			return AxisAlignedBB.getBoundingBox(i-1, j+0, k+0, i+1, j+0.075, k+1);
		}
		return AxisAlignedBB.getBoundingBox(i, j+0, k+0, i+1, j+0.075, k+1);
	}
/*	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List boxes, Entity entity)
    {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta%4 == 0)
		{
			this.setBlockBounds(0f, 0, -1f, 1f, 0.075f, 1f);
		}
		else if(meta%4 == 1)
		{
			this.setBlockBounds(0f, 0, 0f, 2f, 0.075f, 1f);
		}
		else if(meta%4 == 2)
		{
			this.setBlockBounds(0f, 0, 0f, 1f, 0.075f, 2f);
		}
		else if(meta%4 == 3)
		{
			this.setBlockBounds(-1f, 0, 0f, 1f, 0.075f, 1f);
		}
	}*/

	private void onBlockDestroyed(World world, int x, int y, int z, int meta)
	{
		if (!world.isRemote)
		{
			// Based on our meta, we want to drop some hide
			if ((meta%8) / 4 == 1)
			{
				EntityItem ie = new EntityItem(world, x, y, z, new ItemStack(getDroppedItem(), 2, 2));
				world.spawnEntityInWorld(ie);
			}
			else
			{
				EntityItem ie = new EntityItem(world, x, y, z, new ItemStack(getDroppedItem(), 4, 1));
				world.spawnEntityInWorld(ie);
			}
			// update the adjacent spears
			if (world.getBlock(x - 1, y, z - 1) == TFCBlocks.woodSpear && world.getBlockMetadata(x - 1, y, z - 1) == 1)
			{
				world.setBlockMetadataWithNotify(x - 1, y, z - 1, 0, 2);
			}
			if (world.getBlock(x - 1, y, z + 1) == TFCBlocks.woodSpear && world.getBlockMetadata(x - 1, y, z + 1) == 2)
			{
				world.setBlockMetadataWithNotify(x - 1, y, z + 1, 0, 2);
			}
			if (world.getBlock(x + 1, y, z + 1) == TFCBlocks.woodSpear && world.getBlockMetadata(x + 1, y, z + 1) == 3)
			{
				world.setBlockMetadataWithNotify(x + 1, y, z + 1, 0, 2);
			}
			if (world.getBlock(x + 1, y, z - 1) == TFCBlocks.woodSpear && world.getBlockMetadata(x + 1, y, z - 1) == 4)
			{
				world.setBlockMetadataWithNotify(x + 1, y, z - 1, 0, 2);
			}
			if(world.getBlock(x+1, y, z) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x+1, y, z);
			}
			if(world.getBlock(x-1, y, z) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x-1, y, z);
			}
			if(world.getBlock(x, y, z+1) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x, y, z+1);
			}
			if(world.getBlock(x, y, z-1) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x, y, z-1);
			}
			if(world.getBlock(x+1, y+1, z) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x+1, y+1, z);
			}
			if(world.getBlock(x-1, y+1, z) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x-1, y+1, z);
			}
			if(world.getBlock(x, y+1, z+1) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x, y+1, z+1);
			}
			if(world.getBlock(x, y+1, z-1) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x, y+1, z-1);
			}
			if(world.getBlock(x+1, y+1, z-1) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x+1, y+1, z-1);
			}
			if(world.getBlock(x-1, y+1, z+1) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x-1, y+1, z+1);
			}
			if(world.getBlock(x+1, y+1, z+1) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x+1, y+1, z+1);
			}
			if(world.getBlock(x-1, y+1, z-1) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x-1, y+1, z-1);
			}
			if(world.getBlock(x, y+3, z) == TFCBlocks.invisibleBlock)
			{
				world.setBlockToAir(x, y+3, z);
			}
		}
	}
	
	public Item getDroppedItem()
	{
		if(this == TFCBlocks.wolfTeepee)
		{
			return TFCItems.wolfFurScrap;
		}
		else if(this == TFCBlocks.bearTeepee)
		{
			return TFCItems.bearFurScrap;
		}
		return TFCItems.hide;
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
	{
		onBlockDestroyed(world, x, y, z, meta);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.teepeeRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion p_149723_5_)
	{
		int meta = world.getBlockMetadata(x, y, z);
		onBlockDestroyed(world, x, y, z, meta);
	}

	@Override
	public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player)
	{
		World w = (World) world;
		if (!w.isRemote && player != null)
		{
			// ((EntityPlayer)player).sleepTimer = 50;
			// System.out.println(((EntityPlayer)player).isPlayerSleeping());
		}
		return true;
	}
}
