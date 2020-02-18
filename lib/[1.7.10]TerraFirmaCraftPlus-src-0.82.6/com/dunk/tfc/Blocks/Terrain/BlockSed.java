package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockSed extends BlockStone
{
	public BlockSed(Material material)
	{
		super(material);
		this.dropBlock = TFCBlocks.stoneSedCobble;
		names = Global.STONE_SED;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_SED_START;
		gemChance = 1;
	}
}