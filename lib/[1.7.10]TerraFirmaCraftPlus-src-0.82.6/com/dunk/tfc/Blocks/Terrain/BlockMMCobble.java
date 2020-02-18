package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockMMCobble extends BlockCobble
{
	public BlockMMCobble(Material material) 
	{
		super(material);
		names = Global.STONE_MM;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_MM_START;
	}
}
