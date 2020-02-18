package com.dunk.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.BlockTerraContainer;
import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Flora.BlockLogNatural;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Sounds;
import com.dunk.tfc.TileEntities.TEBarrel;
import com.dunk.tfc.TileEntities.TEBasket;
import com.dunk.tfc.TileEntities.TEBeehive;
import com.dunk.tfc.TileEntities.TEFirepit;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBeehive extends BlockTerraContainer
{
	IIcon[] basketHiveIcons;
	IIcon[] wildHiveIcons;
	// The treehive uses the texture of the tree it is on, otherwise an
	// invisible texture
	IIcon invisible;
	IIcon honeycomb;

	public BlockBeehive(Material m)
	{
		super(m);
		this.setTickRandomly(true);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		basketHiveIcons = new IIcon[4];
		basketHiveIcons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Basket Hive Top");
		basketHiveIcons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Basket Hive Side");
		basketHiveIcons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Basket Hive Bottom");
		basketHiveIcons[3] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Basket Hive Front");

		wildHiveIcons = new IIcon[3];
		wildHiveIcons[0] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Wild Hive Top");
		wildHiveIcons[1] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Wild Hive Side");
		wildHiveIcons[2] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Wild Hive Front");

		invisible = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "Invisible");

		honeycomb = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Honeycomb2");
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if (this == TFCBlocks.wildBeehive)
		{
			return AxisAlignedBB.getBoundingBox(x+0.2, y+1, z+0.2,x+ 0.8,y+ 1.5,z+ 0.8);
		}
		if (this == TFCBlocks.wildTreehive)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (meta == 2)
			{
				// 2 is the north face of a block
				return AxisAlignedBB.getBoundingBox(x+0.2,y+ 0.1,z+ 0.8,x+ 0.8,y+ 0.9,z+ 1);
			}
			else if (meta == 3)
			{
				// south face
				return AxisAlignedBB.getBoundingBox(x+0.2,y+ 0.1, z+1 - 1,x+ 0.8, y+0.9,z+ 1 - 0.8);
			}
			else if (meta == 4)
			{
				// West face
				return AxisAlignedBB.getBoundingBox(x+0.8,y+ 0.1,z+ 0.2, x+1, y+0.9,z+ 0.8);
			}
			else if (meta == 5)
			{
				// east face
				return AxisAlignedBB.getBoundingBox(x+1 - 1,y+ 0.1,z+ 0.2, x+1 - 0.8, y+0.9,z+ 0.8);
			}
		}
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if (this == TFCBlocks.wildBeehive)
		{
			return AxisAlignedBB.getBoundingBox(x+0.2,y+ 1, z+0.2,x+ 0.8, y+1.5,z+ 0.8);
		}
		if (this == TFCBlocks.wildTreehive)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (meta == 2)
			{
				// 2 is the north face of a block
				return AxisAlignedBB.getBoundingBox(x+0.2, y+0.1,z+ 0.8,x+ 0.8,y+ 0.9,z+ 1);
			}
			else if (meta == 3)
			{
				// south face
				return AxisAlignedBB.getBoundingBox(x+0.2, y+0.1,z+ 1 - 1, 0.8, y+0.9,z+ 1 - 0.8);
			}
			else if (meta == 4)
			{
				// West face
				return AxisAlignedBB.getBoundingBox(x+0.8,y+ 0.1,z+ 0.2, 1,y+ 0.9, z+0.8);
			}
			else if (meta == 5)
			{
				// east face
				return AxisAlignedBB.getBoundingBox(x+1 - 1, y+0.1,z+ 0.2,x+ 1 - 0.8,y+ 0.9,z+ 0.8);
			}
		}
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == 1)// Top?
		{
			return basketHiveIcons[0];
		}
		else if (side == 0)
		{
			return basketHiveIcons[2];
		}
		else if (side == meta)
		{
			return basketHiveIcons[3];
		}
		return basketHiveIcons[1];
	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		// Rotation
		int meta = access.getBlockMetadata(x, y, z);

		if (this == TFCBlocks.beehive)
		{
			if (side == 1)// Top?
			{
				return basketHiveIcons[0];
			}
			else if (side == 0)
			{
				return basketHiveIcons[2];
			}
			else if (side == meta)
			{
				return basketHiveIcons[3];
			}
			return basketHiveIcons[1];
		}
		else if (this == TFCBlocks.wildBeehive)
		{
			return honeycomb;
			/*
			 * if(side == 0 || side == 1) { return wildHiveIcons[0]; } if(side
			 * == meta) { return wildHiveIcons[2]; } return wildHiveIcons[1];
			 */
		}
		// We're a treehive!
		// We're facing a certain direction
		int xOff, zOff;
		xOff = zOff = 0;
		if (meta == 2)
		{
			// 2 is the north face of a block
			zOff = 1;
		}
		else if (meta == 3)
		{
			// south face
			zOff = -1;
		}
		else if (meta == 4)
		{
			// West face
			xOff = 1;
		}
		else if (meta == 5)
		{
			// east face
			xOff = -1;
		}
		if (access.getBlock(x + xOff, y, z + zOff) instanceof BlockLogNatural)
		{
			if (side == 10)
			{
				return honeycomb;
			}
			int logMeta = access.getBlockMetadata(x + xOff, y, z + zOff);
			return access.getBlock(x + xOff, y, z + zOff).getIcon(2, logMeta);
		}
		return invisible;
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

	@Override
	public int getRenderType()
	{
		return TFCBlocks.beehiveRenderId;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (!world.isRemote)
		{
			// Play frog sound at night only in fresh water and above freezing
			// temperature.
			if (world.getTileEntity(x, y, z) instanceof TEBeehive && TFC_Climate.getHeightAdjustedTemp(world, x, y + 1, z) > 10 && !isSmoked(world, x, y, z))
			{
				boolean canBuzz = true;
				if (this == TFCBlocks.beehive)
				{
					TEBeehive hive = (TEBeehive) world.getTileEntity(x, y, z);
					if (!hive.hasFertileComb())
					{
						canBuzz = false;
					}
				}
				if (canBuzz && rand.nextInt(100) < 25 && world.getBlockLightValue(x, y, z) > 7)
				{
					float mod = rand.nextFloat();
					world.playSoundEffect(x, y, z, TFC_Sounds.BEES, 0.2f, mod * 0.2f + 0.9f);
				}
			}
		}
		super.updateTick(world, x, y, z, rand);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);

		// if(!world.isRemote)
		// {

		// }

		if (world.isRemote)
		{
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else
		{

			if (player.isSneaking())
			{
				return false;
			}
			if (!this.drunkChanceInteract(player))
			{
				return false;
			}
			if (world.getTileEntity(x, y, z) != null)
			{
				boolean smoked = isSmoked(world, x, y, z) || this == TFCBlocks.beehive;
				if (!smoked)
				{
					player.attackEntityFrom(new DamageSource("bees").setDamageBypassesArmor(), 100);
					world.playSoundEffect(x, y, z, TFC_Sounds.BEES, 0.2f, world.rand.nextFloat() * 0.2f + 0.9f);
					return false;
				}

				TEBeehive te = (TEBeehive) (world.getTileEntity(x, y, z));

				if (!handleInteraction(player, te))
				{
					player.openGui(TerraFirmaCraft.instance, 55, world, x, y, z);
					return true;
				}
				else
					return true;
			}
		}
		return false;
	}

	public boolean isSmoked(IBlockAccess world, int x, int y, int z)
	{
		boolean smoked = false;
		// We check for a firepit
		for (int i = 0; i > -10 && !smoked; i--)
		{
			Block b = world.getBlock(x, y + i, z);
			if (b instanceof BlockFirepit)
			{
				TEFirepit te = (TEFirepit) world.getTileEntity(x, y + i, z);
				if (te != null && te.fireTemp > 100)
				{
					smoked = true;
				}
			}
		}
		return smoked;
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null;
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		if (!world.isRemote)
		{
			TEBeehive te = (TEBeehive) world.getTileEntity(x, y, z);
			if (!isSmoked(world, x, y, z) && this != TFCBlocks.beehive && te.hasFertileComb())
			{
				//Cancel the block breaking
				world.setBlock(x, y, z, this,meta,3);
				entityplayer.attackEntityFrom(new DamageSource("bees").setDamageBypassesArmor(), 150);
				world.playSoundEffect(x, y, z, TFC_Sounds.BEES, 0.2f, world.rand.nextFloat() * 0.2f + 0.9f);
			}
		}
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta)
	{
		if (!world.isRemote)
		{
			TEBeehive te = (TEBeehive) world.getTileEntity(i, j, k);
			if (!isSmoked(world, i, j, k) && this != TFCBlocks.beehive && te.hasFertileComb())
			{
				return;
			}
			else
			{
				te.ejectContents();
				ItemStack drop;
				if (this == TFCBlocks.beehive)
				{
					drop = new ItemStack(this, 1);
				}
				else
				{
					drop = new ItemStack(TFCItems.emptyHoneycomb, world.rand.nextInt(2) + 2);
				}
				EntityItem ei = new EntityItem(world, i, j, k, drop);
				world.spawnEntityInWorld(ei);
			}
		}
	}

	protected boolean handleInteraction(EntityPlayer player, TEBeehive te)
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (!world.isSideSolid(x, y - 1, z, ForgeDirection.UP) && this == TFCBlocks.beehive)
		{
			// TEBeehive te = (TEBeehive) world.getTileEntity(x, y, z);
			// te.ejectContents();
			// EntityItem ei = new EntityItem(world, x, y, z, new
			// ItemStack(TFCItems.strawBasket,1));
			// world.spawnEntityInWorld(ei);
			world.setBlockToAir(x, y, z);
		}
		else
		{
			Block b = world.getBlock(x, y + 1, z);
			if (this == TFCBlocks.wildBeehive && !(b instanceof BlockBranch))
			{

				// TEBeehive te = (TEBeehive) world.getTileEntity(x, y, z);
				world.setBlockToAir(x, y, z);
			}
			else if (this == TFCBlocks.wildTreehive)
			{
				int meta = world.getBlockMetadata(x, y, z);
				int xOff = 0;
				int zOff = 0;
				if (meta == 2)
				{
					// 2 is the north face of a block
					zOff = 1;
				}
				else if (meta == 3)
				{
					// south face
					zOff = -1;
				}
				else if (meta == 4)
				{
					// West face
					xOff = 1;
				}
				else if (meta == 5)
				{
					// east face
					xOff = -1;
				}
				Block b2 = world.getBlock(x + xOff, y, z + zOff);
				if (!(b2 instanceof BlockLogNatural))
				{
					world.setBlockToAir(x, y, z);
				}
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		if (!world.isRemote)
		{
			// Set rotation
			float rot = Math.abs((entityliving.rotationYaw % 360) + 360) % 360;
			int meta = 0;
			if (rot > 315 || rot <= 45)
			{
				meta = 2;
			}
			else if (rot > 45 && rot <= 135)
			{
				meta = 5;
			}
			else if (rot > 135 && rot < 225)
			{
				meta = 3;
			}
			else
			{
				meta = 4;
			}
			world.setBlockMetadataWithNotify(i, j, k, meta, 3);
		}

	}

	/**
	 * This returns a complete list of items dropped from this block.
	 */
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int damageValue = getDamageValue(world, x, y, z);
		// ret.add(new ItemStack(TFCItems.strawBasket, 1, damageValue));

		return ret;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEBeehive();
	}
}
