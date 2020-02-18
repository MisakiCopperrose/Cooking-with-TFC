package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockMM extends BlockStone
{
	public static boolean fallInstantly;

	public BlockMM(Material material)
	{
		super(material);
		this.dropBlock = TFCBlocks.stoneMMCobble;
		names = Global.STONE_MM;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_MM_START;
		gemChance = 3;
	}
}
