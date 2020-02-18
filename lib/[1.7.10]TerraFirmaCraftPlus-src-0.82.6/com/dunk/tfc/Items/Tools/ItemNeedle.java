package com.dunk.tfc.Items.Tools;

import java.util.List;
import java.util.Set;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.TileEntities.TELogPile;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemNeedle extends ItemTerraTool
{
	public boolean stackable = false;
	private static final Set<Block> BLOCKS = Sets.newHashSet(new Block[] { Blocks.carpet });

	public ItemNeedle(float par2, ToolMaterial par3, Set<Block> par4)
	{
		super(par2, par3, BLOCKS);
		// TODO Auto-generated constructor stub
		// setHasSubtypes(true);
	}

	@Override
	public IIcon getIcon(ItemStack is, int pass)
	{
		// if(is.hasTagCompound() && !stackable)
		// {
		// return icons[1];
		// }
		return icons[0];

	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.TINY;
	}

	public ItemNeedle setStackable(boolean b)
	{
		stackable = b;
		return this;
	}

	@Override
	public boolean canStack()
	{
		return stackable;
	}

	private IIcon[] icons = new IIcon[1];

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "tools/" + getUnlocalizedName().substring(5));
		// icons[1] = registerer.registerIcon(Reference.MOD_ID + ":" +
		// "tools/"+getUnlocalizedName().substring(5)+" Strung");
	}

	private boolean createPile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z,
			int side, int l, int distance)
	{
		TELogPile te = null;
		if (world.isAirBlock(x, y, z))
		{
			int meta = (itemstack.stackSize-1) % Global.WOOD_ALL.length;
			boolean two = false;
			int Y = entityplayer.eyeHeight + entityplayer.posY + 1 < y ? 1
					: entityplayer.eyeHeight + entityplayer.posY - 1 > y ? -1 : 0;
					if(meta >= 16)
					{
						two = true;
						meta %=16;
					}
			if (this == TFCItems.ironNeedle)
			{
				if (side == 0)
				{
					return false;
				}
				if (side == 1)
				{

					world.setBlock(x, y, z, (two?TFCBlocks.branchEnd2__y_:TFCBlocks.branchEnd__y_) , l, 3);
				}
				if (side == 2)
				{
					world.setBlock(x, y, z, Y == 1 ? (two?TFCBlocks.branchEnd2__YZ:TFCBlocks.branchEnd__YZ) 
							: Y == -1 ? (two?TFCBlocks.branchEnd2__yZ:TFCBlocks.branchEnd__yZ) : (two?TFCBlocks.branchEnd2___Z:TFCBlocks.branchEnd___Z), l, 3);
				}
				if (side == 3)
				{
					world.setBlock(x, y, z, Y == 1 ? (two?TFCBlocks.branchEnd2__Yz:TFCBlocks.branchEnd__Yz) 
							: Y == -1 ? (two?TFCBlocks.branchEnd2__yz:TFCBlocks.branchEnd__yz) : (two?TFCBlocks.branchEnd2___z:TFCBlocks.branchEnd___z), l, 3);
				}
				if (side == 4)
				{
					world.setBlock(x, y, z, Y == 1 ? (two?TFCBlocks.branchEnd2_XY_:TFCBlocks.branchEnd_XY_) 
							: Y == -1 ? (two?TFCBlocks.branchEnd2_Xy_:TFCBlocks.branchEnd_Xy_) : (two?TFCBlocks.branchEnd2_X__:TFCBlocks.branchEnd_X__) , l, 3);
				}
				if (side == 5)
				{
					world.setBlock(x, y, z, Y == 1 ? (two?TFCBlocks.branchEnd2_xY_:TFCBlocks.branchEnd_xY_) 
							: Y == -1 ? (two?TFCBlocks.branchEnd2_xy_:TFCBlocks.branchEnd_xy_) : (two?TFCBlocks.branchEnd2_x__:TFCBlocks.branchEnd_x__), l, 3);
				}
				world.setBlockMetadataWithNotify(x, y, z, meta, 3);
			}
			else
			{
				if (side == 0)
				{
					return false;
				}
				if (side == 1)
				{

					world.setBlock(x, y, z, (two?TFCBlocks.branch2__y_:TFCBlocks.branch__y_), l, 3);
				}
				if (side == 2)
				{
					world.setBlock(x, y, z,
							Y == 1 ? (two?TFCBlocks.branch2__YZ:TFCBlocks.branch__YZ) : Y == -1 ? (two?TFCBlocks.branch2__yZ:TFCBlocks.branch__yZ) : (two?TFCBlocks.branch2___Z:TFCBlocks.branch___Z), l,
							3);
				}
				if (side == 3)
				{
					world.setBlock(x, y, z,
							Y == 1 ? (two?TFCBlocks.branch2__Yz:TFCBlocks.branch__Yz) : Y == -1 ? (two?TFCBlocks.branch2__yz:TFCBlocks.branch__yz) : (two?TFCBlocks.branch2___z:TFCBlocks.branch___z), l,
							3);
				}
				if (side == 4)
				{
					world.setBlock(x, y, z,
							Y == 1 ? (two?TFCBlocks.branch2_XY_:TFCBlocks.branch_XY_) : Y == -1 ? (two?TFCBlocks.branch2_Xy_:TFCBlocks.branch_Xy_) : (two?TFCBlocks.branch2_X__:TFCBlocks.branch_X__), l,
							3);
				}
				if (side == 5)
				{
					world.setBlock(x, y, z,
							Y == 1 ? (two?TFCBlocks.branch2_xY_:TFCBlocks.branch_xY_) : Y == -1 ? (two?TFCBlocks.branch2_xy_:TFCBlocks.branch_xy_) : (two?TFCBlocks.branch2_x__:TFCBlocks.branch_x__), l,
							3);
				}
				world.setBlockMetadataWithNotify(x, y, z, meta, 3);
			}
			// te = (TELogPile)world.getTileEntity(x, y, z);
		}
		else
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{
		//return false;
		
		if (!world.isRemote && TFCOptions.enableDebugMode)
		{
			if (!entityplayer.isSneaking() )
			{
				Block b2 = world.getBlock(x, y, z);
				int dist = 0;
				if (b2 instanceof BlockBranch)
				{
					dist = ((BlockBranch) b2).getDistanceToTrunk(world, x, y, z,0);
				}
				int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
				// side 0 means bottom
				if (side == 0)
					--y;
				// 1 means top
				else if (side == 1)
					++y;
				// 2 means front
				else if (side == 2)
					--z;
				// 3 is back
				else if (side == 3)
					++z;
				// 4 is left
				else if (side == 4)
					--x;
				// 5 is right
				else if (side == 5)
					++x;
				if (world.canPlaceEntityOnSide(TFCBlocks.logPile, x, y, z, false, side, entityplayer, itemstack))
					if (createPile(itemstack, entityplayer, world, x, y, z, side, dir, dist))
					{
						itemstack.stackSize = itemstack.stackSize - 1;
						// playSound(world, x, y, z);
					}
				return true;
			}
		}
		return false;
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (is.stackTagCompound != null && is.stackTagCompound.hasKey("string"))
		{
			//arraylist.add(TFC_Core.translate("gui.Needle." + is.stackTagCompound.getString("string")));
		}

	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
