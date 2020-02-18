package com.dunk.tfc.Items.ItemBlocks;

import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.Block;

public class ItemHopper extends ItemTerraBlock
{

	public ItemHopper(Block b)
	{
		super(b);
		this.metaNames = new String[Global.STONE_ALL.length];
		for(int i = 0; i < Global.STONE_MM.length;i++)
		{
			metaNames[i] = Global.STONE_MM[i];
		}
		
		for(int i = 0; i < Global.STONE_SED.length;i++)
		{
			metaNames[i+Global.STONE_MM.length] = Global.STONE_SED[i];
		}
		
		for(int i = 0; i < Global.STONE_IGIN.length;i++)
		{
			metaNames[i+Global.STONE_MM.length + Global.STONE_SED.length] = Global.STONE_IGIN[i];
		}
		
		for(int i = 0; i < Global.STONE_IGEX.length;i++)
		{
			metaNames[i+Global.STONE_MM.length + Global.STONE_SED.length + Global.STONE_IGIN.length] = Global.STONE_IGEX[i];
		}
	}

}
