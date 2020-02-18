package com.dunk.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class ClothingManager
{
	private static final ClothingManager INSTANCE = new ClothingManager();
	public static final ClothingManager getInstance()
	{
		return INSTANCE;
	}

	private List<SewingRecipe> recipes;
	

	private ClothingManager()
	{
		recipes = new ArrayList<SewingRecipe>();
	}

	public void addRecipe(SewingRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public SewingRecipe getRecipeByIndex(int index)
	{
		return recipes.get(index);
	}

	public int findMatchingRecipeIndex(Object[] is)
	{
		ItemStack[] items = new ItemStack[is.length];
		for(int i = 0; i < is.length; i++)
		{
			items[i] = (ItemStack)is[i];
		}
		return findMatchingRecipeIndex(items);
	}
	
	public int findMatchingRecipeIndex(ItemStack[] is)
	{
		for(Object recipe : recipes)
		{
			SewingRecipe sw = (SewingRecipe) recipe;
			if(sw.matchesRecipe(is))
			{
				return recipes.indexOf(sw);
			}
		}
		return -1;
	}

	public List<SewingRecipe> getRecipes()
    {
        return recipes;
    }

}
