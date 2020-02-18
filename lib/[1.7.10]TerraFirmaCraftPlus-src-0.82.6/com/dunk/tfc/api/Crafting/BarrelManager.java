package com.dunk.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.List;

import com.dunk.tfc.TileEntities.TEBarrel;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BarrelManager
{
	private static final BarrelManager INSTANCE = new BarrelManager();
	public static final BarrelManager getInstance()
	{
		return INSTANCE;
	}

	private List<BarrelRecipe> recipes;
	private List<BarrelPreservativeRecipe> preservativeRecipes;

	private BarrelManager()
	{
		recipes = new ArrayList<BarrelRecipe>();
		preservativeRecipes = new ArrayList<BarrelPreservativeRecipe>();
	}

	public void addRecipe(BarrelRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public void addPreservative(BarrelPreservativeRecipe recipe) {
		preservativeRecipes.add(recipe);
	}

	public BarrelRecipe findMatchingRecipe(ItemStack item, FluidStack fluid, boolean sealed, int techLevel, boolean heated, TEBarrel te)
	{
		for(Object recipe : recipes)
		{
			BarrelRecipe br = (BarrelRecipe) recipe;
			if(br instanceof BarrelFireRecipe)
			{
				if(!heated)
				{
					continue;
				}
				if(((BarrelFireRecipe) br).isDistillationRecipe() && br.matches(item, fluid,te))
				{
					if(br.sealedRecipe == sealed && techLevel == 0)
						return br;
				}
				if(/*item != null && */fluid != null &&/*(br.inItemStack != null && item != null) && (br.inFluid != null && fluid != null) &&*/ br.matches(item, fluid,te))
					if(br.sealedRecipe == sealed && techLevel == 0)
						return br;
			}
			else
			{
			if(/*item != null && */fluid != null &&/*(br.inItemStack != null && item != null) && (br.inFluid != null && fluid != null) &&*/ br.matches(item, fluid,te))
				if(br.sealedRecipe == sealed && br.minTechLevel <= techLevel)
					return br;
			}
		}
		return null;
	}
	
	public BarrelPreservativeRecipe findMatchingPreservativeRepice(TEBarrel barrel, ItemStack item, FluidStack fluid, boolean sealed)
	{
		for(BarrelPreservativeRecipe recipe : preservativeRecipes){
			if(recipe.checkForPreservation(barrel, fluid, item, sealed))
				return recipe;
		}
		return null;
	}

	public List<BarrelRecipe> getRecipes()
	{
		return recipes;
	}
	
	public List<BarrelPreservativeRecipe> getPreservatives()
	{
		return preservativeRecipes;
	}


}
