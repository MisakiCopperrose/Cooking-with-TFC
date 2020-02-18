package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Render.Item.MudBrickItemRenderer;
import com.dunk.tfc.Render.Item.PoleItemRenderer;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemMud extends ItemTerra
{
	public ItemMud() 
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
	}
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		super.registerIcons(registerer);
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
