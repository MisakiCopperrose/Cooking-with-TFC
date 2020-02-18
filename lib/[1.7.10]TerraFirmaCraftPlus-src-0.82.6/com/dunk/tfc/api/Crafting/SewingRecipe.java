package com.dunk.tfc.api.Crafting;

import java.util.ArrayList;

import com.dunk.tfc.Items.ItemClothingPiece;
import com.dunk.tfc.Items.ItemClothingPiece.ClothingEnum;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SewingRecipe
{
	ItemStack[] requiredPieces;
	SewingPattern thePattern;
	boolean repairRecipe = false;
	private static final int WILD = OreDictionary.WILDCARD_VALUE;

	public SewingRecipe(SewingPattern pat, ItemStack[] in)
	{
		thePattern = pat;
		ArrayList<ItemStack> extras = new ArrayList<ItemStack>();
		for (ItemStack i : in)
		{
			while (i.stackSize > 1)
			{
				ItemStack i2 = i.copy();
				i2.stackSize = 1;
				i.stackSize--;
				extras.add(i2);
			}
		}
		if (!extras.isEmpty())
		{
			for (ItemStack i : in)
			{
				extras.add(i);
			}
			requiredPieces = (ItemStack[]) extras.toArray();
		}
		else
		{
			requiredPieces = in;
		}
	}
	
	public boolean getRepairRecipe()
	{
		return repairRecipe;
	}
	
	public SewingRecipe setRepairRecipe()
	{
		this.repairRecipe = true;
		return this;
	}
	
	public ItemStack[] getRequiredPieces()
	{
		return requiredPieces;
	}

	public SewingPattern getSewingPattern()
	{
		return thePattern;
	}

	public SewingRecipe(SewingPattern out, ItemStack inStack)
	{
		thePattern = out;
		requiredPieces = new ItemStack[] { inStack };
	}

	/**
	 * Checks if a set of items matches the recipe. Returns true if all of the
	 * items match 1 to 1 and if all the components of the potential have the
	 * same material
	 * 
	 * @param potentialMatch
	 * @return
	 */
	public boolean matchesRecipe(ItemStack[] potentialMatch)
	{
		// We check if this is valid. What we must do is check whether each item
		// matches an item in the recipe. If there is a match, we must not count
		// those items again
		// All items need to be made out of the same material as well.

		/*
		 * if (potentialMatch.length != requiredPieces.length) { return false; }
		 * 
		 * // Check all the components are the same material if
		 * (potentialMatch.length > 1) { ClothingEnum one =
		 * ((ItemClothingPiece)(potentialMatch[0].getItem())).
		 * getMaterialFromClothingPiece(potentialMatch[0]); if(one ==
		 * ClothingEnum.NONE) { return false; } for (int i = 1; i <
		 * potentialMatch.length; i++) { if
		 * (((ItemClothingPiece)(potentialMatch[0].getItem())).
		 * getMaterialFromClothingPiece(potentialMatch[i]) != one) { return
		 * false; } } }
		 */

		// Check that each item matches an item in the recipe.
		// We do this by creating this boolean array. It represents the items in
		// the potential match we've checked.
		// This ensures that when we're matching our recipe to the potential, we
		// don't match to the same potential item twice.
		// If we can't find a match, we return false
		// At the end, we check that all of our indices are set to true, meaning
		// we've checked everything.
		// We already made sure that the sizes of the arrays matched, so this
		// should be impossible if we've gotten this far
		// but I don't want to risk it.
		int[] checkedIndices = new int[potentialMatch.length];
		boolean[] reqs = new boolean[requiredPieces.length];
		// So what we do is instead of booleans, we use ints. initially all ints
		// are 0, which means we check them on that round.
		// If we've checked all of the slots but haven't matched yet, we check
		// any slots which we checked last time but that still have items.
		boolean noChange = false;
		int counter = 0;
		while (!noChange)
		{
			noChange = true;

			for (int i = 0; i < requiredPieces.length; i++)
			{
				if (!reqs[i])
				{
					boolean noMatch = true;
					for (int j = 0; j < potentialMatch.length && noMatch; j++)
					{
						if (potentialMatch[j].getItem() == requiredPieces[i].getItem() && checkedIndices[j] <= counter
								&& checkedIndices[j] < potentialMatch[j].stackSize
								//Either the damages are an exact match OR there's a wild card value and the damage isn't 0 and this is a repair recipe
								&& (potentialMatch[j].getItemDamage() == requiredPieces[i].getItemDamage() ||
								(requiredPieces[i].getItemDamage() == WILD && potentialMatch[j].getItemDamage() != 0 && repairRecipe)))
						{
							noMatch = false;
							checkedIndices[j]++;
							noChange = false;
							reqs[i] = true;
						}
					}
					// if(noMatch)
					// {
					// return false;
					// }
				}
			}
			counter++;
		}
		for (int b : checkedIndices)
		{
			if (b == 0)
			{
				return false;
			}
		}
		for (boolean r : reqs)
		{
			if (!r)
			{
				return r;
			}
		}
		return true;
	}
}
