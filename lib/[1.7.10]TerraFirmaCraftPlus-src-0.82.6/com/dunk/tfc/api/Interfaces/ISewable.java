package com.dunk.tfc.api.Interfaces;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public interface ISewable
{
	public ResourceLocation getFlatTexture();
	public boolean[][] getClothingAlpha();
	public Item setRepairCost(int i);
	public int getRepairCost();
}
