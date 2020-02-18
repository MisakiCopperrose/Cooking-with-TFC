package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockSedSmooth extends BlockSmooth
{
	public BlockSedSmooth()
	{
		super(Material.rock);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
		names = Global.STONE_SED;
		icons = new IIcon[names.length];
	}
}