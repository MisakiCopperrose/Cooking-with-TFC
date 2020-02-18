package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockIgExCobble extends BlockCobble
{
	public BlockIgExCobble(Material material)
	{
		super(material);
		names = Global.STONE_IGEX;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_IGEX_START;
	}

	@Override
	public int tickRate(World world)
	{
		return 3;
	}
}
