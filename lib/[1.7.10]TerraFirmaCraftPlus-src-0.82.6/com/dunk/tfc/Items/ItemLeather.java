package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.Devices.BlockTeepee;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Items.Tools.ItemKnife;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemLeather extends ItemLooseRock
{
	boolean hasSizes = false;

	public ItemLeather()
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.metaNames = null;
	}

	public ItemLeather setHasSizes(boolean t)
	{
		this.hasSizes = t;
		this.metaNames = new String[] { "small", "medium", "large" };
		return this;
	}

	public boolean getHasSizes()
	{
		return hasSizes;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if ((this == TFCItems.bearFur || this == TFCItems.wolfFur || this == TFCItems.hide || this == TFCItems.bearFurScrap || this == TFCItems.wolfFurScrap)
			&& ItemRawHide.createTeepee(this, itemstack, entityplayer, world, x, y, z, side, hitX, hitY, hitZ))
			{
				return true;
			}
			else if(itemstack.getItem() == TFCItems.hide && itemstack.getItemDamage() >= 2){
				int d = (int) ((45 + (entityplayer.rotationYaw % 360 + 360f) % 360) / 90) % 4; //direction
				int x2 = x + (d == 1 ? -1 : d == 3 ? 1 : 0); // the x-coord of the second block
				int z2 = z + (d == 2 ? -1 : d == 0 ? 1 : 0);
				if(world.getBlock(x, y, z)==TFCBlocks.thatch && side == 1 && world.getBlock(x2,y,z2)==TFCBlocks.thatch
						&& world.isAirBlock(x, y+1, z) && world.isAirBlock(x2, y+1, z2)){
					world.func_147480_a/*destroyBlock*/(x, y, z, false);
					world.func_147480_a/*destroyBlock*/(x2, y, z2, false);
					world.setBlock(x, y, z, TFCBlocks.strawHideBed, d, 3);
					world.setBlock(x2, y, z2, TFCBlocks.strawHideBed, d+8, 3);
					itemstack.stackSize--;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		pi.specialCraftingType = new ItemStack(specialCraftingType, 1, (!this.hasSizes ? itemstack.getItemDamage() : 0));
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(par2World, entityplayer, true);
		boolean hasKnife = false;
		Block b = null;
		if (mop != null)
		{
			b = par2World.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		}
		if ((b != null && b instanceof BlockTeepee) || par2World.isRemote)
		{
			return itemstack;
		}
		for (int i = 0; i < entityplayer.inventory.mainInventory.length; i++)
		{
			if (entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemKnife)
				hasKnife = true;
		}

		if (hasKnife && b != TFCBlocks.thatch && b != TFCBlocks.strawHideBed)
		{
			if (specialCraftingTypeAlternate != null)
				pi.specialCraftingTypeAlternate = specialCraftingTypeAlternate;
			else
				pi.specialCraftingTypeAlternate = null;
			entityplayer.openGui(TerraFirmaCraft.instance, 28, entityplayer.worldObj, (int) entityplayer.posX, (int) entityplayer.posY, (int) entityplayer.posZ);
		}
		return itemstack;

	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (TFC_Core.showShiftInformation())
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.Leather.Inst0"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{

	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return this.itemIcon;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
		if (hasSizes)
		{
			list.add(new ItemStack(this, 1, 1));
			list.add(new ItemStack(this, 1, 2));
		}
	}
}
