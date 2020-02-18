package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemStraw extends ItemLooseRock
{
	public ItemStraw()
	{
		super();
		// this.setCreativeTab(TFCTabs.TFC_POTTERY);
		this.hasSubtypes = true;
		this.metaNames = null;
		this.icons = new IIcon[1];
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{

		// TerraFirmaCraft.log.info(itemstack.stackSize+",
		// "+itemstack.getItem().getClass() +": "+Items.clay_ball.getClass());
		Block b = world.getBlock(x, y, z);
		if (b == TFCBlocks.pottery)
		{
			return false;
		}
		if (itemstack.stackSize >= 5)
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
			pi.specialCraftingType = new ItemStack(specialCraftingType, 1, 0);
			pi.specialCraftingTypeAlternate = null;
			entityplayer.openGui(TerraFirmaCraft.instance, 28, entityplayer.worldObj, (int) entityplayer.posX, (int) entityplayer.posY, (int) entityplayer.posZ);
		}
		return true;

	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		return is;
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (TFC_Core.showShiftInformation())
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.Straw.Inst0"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		// nothing
		list.add(new ItemStack(this, 1));
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[0];
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/Straw");
	}
}
