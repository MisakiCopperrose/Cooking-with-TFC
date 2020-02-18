package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockIgExSmooth extends BlockSmooth
{
	public BlockIgExSmooth()
	{
		super(Material.rock);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
		names = Global.STONE_IGEX;
		icons = new IIcon[names.length];
	}
}
