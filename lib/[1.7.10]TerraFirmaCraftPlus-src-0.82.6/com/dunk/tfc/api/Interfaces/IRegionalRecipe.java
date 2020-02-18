package com.dunk.tfc.api.Interfaces;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.world.World;

public interface IRegionalRecipe
{
	
	public boolean matches(InventoryCrafting inventorycrafting, int x, int y, int z, World world);
}
