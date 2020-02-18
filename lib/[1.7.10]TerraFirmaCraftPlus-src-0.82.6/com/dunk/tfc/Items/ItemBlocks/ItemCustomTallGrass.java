package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.Core.ColorizerFoliageTFC;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemCustomTallGrass extends ItemTerraBlock
{
	public ItemCustomTallGrass(Block b)
	{
		super(b);
		metaNames = new String[] {"tallgrass", "fern", "shortgrass"};
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
		return TFCBlocks.tallGrass.getIcon(0, par1);
	}
}
