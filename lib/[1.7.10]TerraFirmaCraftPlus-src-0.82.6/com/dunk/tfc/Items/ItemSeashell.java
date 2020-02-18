package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Reference;
import com.dunk.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSeashell extends ItemTerra
{
	protected IIcon[] icons;

	public ItemSeashell()
	{
		icons = new IIcon[2];
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.metaNames = new String[] { "scallop", "conch" };
	}

	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if (TFCOptions.enableDebugMode && !world.isRemote)
		{
			Block b = world.getBlock((int) player.posX, (int) (player.posY + 3 - is.stackSize), (int) player.posZ-1);

			Material m = null;
			if (b != null)
				m = b.getMaterial();

			player.addChatMessage(new ChatComponentText((m!=null?m.isLiquid():"none") + ""));
		}

		return is;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for (int i = 0; i < metaNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "" + metaNames[i]);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for (int i = 0; i < metaNames.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
