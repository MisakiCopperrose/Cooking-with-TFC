package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.Reference;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemFlora extends ItemTerraBlock
{
	public ItemFlora(Block par1)
	{
		super(par1);
		metaNames = new String[] { "Golden Rod", "Cat Tails" };
		icons = new IIcon[2];
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return icons[stack.getItemDamage()];
	}
	
	@Override
	public IIcon getIconFromDamage(int i)
	{
		return icons[i];
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for (int i = 0; i < metaNames.length; i++)
		{
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "plants/" + metaNames[i]);
		}
	}
}
