package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSedCobble extends BlockCobble
{
	public BlockSedCobble(Material material) {
		super(material);
		names = Global.STONE_SED;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_SED_START;
	}

	@Override
	public int tickRate(World world)
	{
		return 3;
	}
}
