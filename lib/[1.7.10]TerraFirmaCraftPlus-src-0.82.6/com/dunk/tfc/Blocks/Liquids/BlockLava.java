package com.dunk.tfc.Blocks.Liquids;

import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.Interfaces.IHeatSource;

import net.minecraft.block.material.Material;

public class BlockLava extends BlockCustomLiquid implements IHeatSource
{
	public BlockLava()
	{
		super(TFCFluids.LAVA, Material.lava);
	}

	@Override
	public float getHeatSourceRadius()
	{
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public Class getTileEntityType()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
