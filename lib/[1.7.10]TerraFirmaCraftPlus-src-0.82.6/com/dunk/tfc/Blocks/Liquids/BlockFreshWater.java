package com.dunk.tfc.Blocks.Liquids;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockFreshWater extends BlockCustomLiquid
{
	public BlockFreshWater(Fluid fluid)
	{
		super(fluid, Material.water);
	}
	
	public void onBlockAdded(World world, int x, int y, int z) 
	{
		super.onBlockAdded(world, x, y, z);
	}
}
