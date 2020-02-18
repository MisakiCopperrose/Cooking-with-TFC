package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.Core.ColorizerFoliageTFC;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemVine extends ItemTerraBlock
{
	public ItemVine(Block b)
	{
		super(b);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int par2)
	{
		return ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return TFCBlocks.vine.getIcon(0, 0);
	}
}
