package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemCustomWoodH3 extends ItemTerraBlock
{
	public ItemCustomWoodH3(Block b)
	{
		super(b);
		int size = 8;
		metaNames = new String[size * 2];
		System.arraycopy(Global.WOOD_ALL, 16, metaNames, 0, 8);
		System.arraycopy(Global.WOOD_ALL, 16, metaNames, 8, 8);
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		try
		{
			int meta = is.getItemDamage();
			if(meta > 15) meta -= 16;
			if(metaNames != null && meta < metaNames.length)
				return getUnlocalizedName().concat("." + metaNames[meta]);
		}
		catch(Exception ex)
		{
			TerraFirmaCraft.LOG.error(ex.getLocalizedMessage());
		}
		return "Unknown";
	}
}
