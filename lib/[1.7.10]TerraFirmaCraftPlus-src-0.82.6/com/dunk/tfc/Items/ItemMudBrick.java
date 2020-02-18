package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Render.Item.MudBrickItemRenderer;
import com.dunk.tfc.TileEntities.TEDryingBricks;
import com.dunk.tfc.TileEntities.TELogPile;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemMudBrick extends ItemTerra
{
	public ItemMudBrick()
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
	}

	private boolean createPile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z,
			int side, int l)
	{
		TEDryingBricks te = null;
		if (world.isAirBlock(x, y, z) && isValid(world, x, y, z))
		{
			world.setBlock(x, y, z, TFCBlocks.dryingBricks, 0, 3);
			te = (TEDryingBricks) world.getTileEntity(x, y, z);
		}
		else
		{
			return false;
		}

		if (te != null)
		{
			te.storage[0] = new ItemStack(this, 1, itemstack.getItemDamage());
		}

		return true;
	}

	public boolean isValid(World world, int i, int j, int k)
	{
		if (world.isSideSolid(i, j - 1, k, ForgeDirection.UP))
		{
			TileEntity te = world.getTileEntity(i, j - 1, k);

			if (te instanceof TEDryingBricks)
			{
				TEDryingBricks lp = (TEDryingBricks) te;

				if (lp.storage[0] == null)
				{
					return false;
				}
				if (lp.storage[1] == null)
				{
					return false;
				}
				if (lp.storage[2] == null)
				{
					return false;
				}
				if (lp.storage[3] == null)
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static Block getBlockFromBrick(ItemStack is)
	{
		if(is != null && is.getItem() == TFCItems.mudBrick)
		{
			if(is.getItemDamage()%32 > 16)
			{
				return TFCBlocks.dirt2;
			}
			return TFCBlocks.dirt;
		}
		return null;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && player.isSneaking())
		{
			if (side == 1 && itemstack != null && itemstack.getItemDamage() >= 32)
			{
				int offset = 0;
				if (world.getBlock(x, y, z) != TFCBlocks.dryingBricks && world.isAirBlock(x, y + 1, z))
				{
					// We only want the pottery to be placeable if the block is
					// solid on top.
					if (!world.isSideSolid(x, y, z, ForgeDirection.UP))
						return false;
					world.setBlock(x, y + 1, z, TFCBlocks.dryingBricks);
					offset = 1;
				}

				if (world.getBlock(x, y + offset, z) != TFCBlocks.dryingBricks || world.getTileEntity(x, y + offset,
						z) == null)
				{
					return false;
				}
				TEDryingBricks te = (TEDryingBricks) world.getTileEntity(x, y + offset, z);
				if (hitX < 0.5 && hitZ < 0.5)
				{
					if (te.storage[0] == null && itemstack != null && itemstack
							.getItem() == TFCItems.mudBrick && itemstack.getItemDamage() >= 32)
					{
						te.storage[0] = new ItemStack(this, 1, itemstack.getItemDamage());
						te.storage[0].stackTagCompound = itemstack.stackTagCompound;
						te.remainingTicks[0] = te.defaultRemainingTicks;
						itemstack.stackSize--;
						world.markBlockForUpdate(te.xCoord, te.yCoord + offset, te.zCoord);
					}
				}
				else if (hitX >= 0.5 && hitZ < 0.5)
				{
					if (te.storage[1] == null && itemstack != null && itemstack
							.getItem() == TFCItems.mudBrick && itemstack.getItemDamage() >= 32)
					{
						te.storage[1] = new ItemStack(this, 1, itemstack.getItemDamage());
						te.storage[1].stackTagCompound = itemstack.stackTagCompound;
						te.remainingTicks[1] = te.defaultRemainingTicks;
						itemstack.stackSize--;
						world.markBlockForUpdate(te.xCoord, te.yCoord + offset, te.zCoord);
					}
				}
				else if (hitX < 0.5 && hitZ >= 0.5)
				{
					if (te.storage[2] == null && itemstack != null && itemstack
							.getItem() == TFCItems.mudBrick && itemstack.getItemDamage() >= 32)
					{
						te.storage[2] = new ItemStack(this, 1, itemstack.getItemDamage());
						te.storage[2].stackTagCompound = itemstack.stackTagCompound;
						te.remainingTicks[2] = te.defaultRemainingTicks;
						itemstack.stackSize--;
						world.markBlockForUpdate(te.xCoord, te.yCoord + offset, te.zCoord);
					}
				}
				else
				{
					if (te.storage[3] == null && itemstack != null && itemstack
							.getItem() == TFCItems.mudBrick && itemstack.getItemDamage() >= 32)
					{
						te.storage[3] = new ItemStack(this, 1, itemstack.getItemDamage());
						te.storage[3].stackTagCompound = itemstack.stackTagCompound;
						te.remainingTicks[3] = te.defaultRemainingTicks;
						itemstack.stackSize--;
						world.markBlockForUpdate(te.xCoord, te.yCoord + offset, te.zCoord);
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for (int i = 0; i < Global.STONE_ALL.length; i++)
			list.add(new ItemStack(this, 1, i));
		for (int i = 0; i < Global.STONE_ALL.length; i++)
			list.add(new ItemStack(this, 1, i + 32));
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		MinecraftForgeClient.registerItemRenderer(this, new MudBrickItemRenderer());
		super.registerIcons(registerer);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if ((itemstack.getItemDamage() & 32) > 0)
			return "item.Wet Mud Brick";
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return false;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		if ((is.getItemDamage() & 31) < 21)
			arraylist.add(EnumChatFormatting.DARK_GRAY + Global.STONE_ALL[is.getItemDamage() & 31]);
		else
			arraylist.add(EnumChatFormatting.DARK_RED + "Unknown: " + is.getItemDamage());
	}
}
