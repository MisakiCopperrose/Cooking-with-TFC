package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockIgInCobble extends BlockCobble
{
	public BlockIgInCobble(Material material)
	{
		super(material);
		names = Global.STONE_IGIN;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_IGIN_START;
	}
}
