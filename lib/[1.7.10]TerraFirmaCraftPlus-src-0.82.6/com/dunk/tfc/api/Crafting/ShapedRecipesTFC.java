package com.dunk.tfc.api.Crafting;

import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.api.HeatRegistry;
import com.dunk.tfc.api.TFC_ItemHeat;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumRegion;
import com.dunk.tfc.api.Interfaces.IRegionalRecipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ShapedRecipesTFC implements IRecipe,IRegionalRecipe
{
	private int recipeWidth;
	private int recipeHeight;
	private ItemStack recipeItems[];
	private ItemStack recipeOutput;
	private final EnumRegion[] validRegions;

	public ShapedRecipesTFC(int i, int j, ItemStack aitemstack[], ItemStack itemstack)
	{
		recipeWidth = i;
		recipeHeight = j;
		recipeItems = aitemstack.clone();
		recipeOutput = itemstack;
		this.validRegions = null;
	}
	
	public ShapedRecipesTFC(int i, int j, EnumRegion[] regions, ItemStack aitemstack[], ItemStack itemstack)
	{
		recipeWidth = i;
		recipeHeight = j;
		recipeItems = aitemstack.clone();
		recipeOutput = itemstack;
		this.validRegions = regions;
	}

	private boolean compare(InventoryCrafting inventorycrafting, int i, int j, boolean flag)
	{
		for (int k = 0; k < 5; k++)
		{
			for (int l = 0; l < 5; l++)
			{
				int i1 = k - i;
				int j1 = l - j;
				ItemStack recipeIS = null;
				if (i1 >= 0 && j1 >= 0 && i1 < recipeWidth && j1 < recipeHeight)
				{
					if (flag)
					{
						recipeIS = recipeItems[recipeWidth - i1 - 1 + j1 * recipeWidth];
					}
					else
					{
						recipeIS = recipeItems[i1 + j1 * recipeWidth];
					}
				}
				ItemStack inputIS = inventorycrafting.getStackInRowAndColumn(k, l);
				if (inputIS == null && recipeIS == null)
				{
					continue;
				}
				else if (inputIS == null || recipeIS == null) // No need for XOR
																// since the X
																// is handled
																// above
				{
					return false;
				}
				else
				{
					if (recipeIS.getItem() != inputIS.getItem())
					{
						return false;
					}
					if (recipeIS.getItemDamage() != 32767 && recipeIS.getItemDamage() != inputIS.getItemDamage())
					{
						return false;
					}
				}
				if (!tempMatch(recipeIS, inputIS))
				{
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
	{
		NBTTagCompound nbt = null;
		if (recipeOutput.stackTagCompound != null)
		{
			nbt = (NBTTagCompound) recipeOutput.stackTagCompound.copy();
		}
		ItemStack is = new ItemStack(recipeOutput.getItem(), recipeOutput.stackSize, recipeOutput.getItemDamage());
		is.stackTagCompound = nbt;
		return is;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return recipeOutput;
	}

	@Override
	public int getRecipeSize()
	{
		return recipeWidth * recipeHeight;
	}

	public int getRecipeWidth()
	{
		return recipeWidth;
	}

	public int getRecipeHeight()
	{
		return recipeHeight;
	}

	public ItemStack[] getRecipeItems()
	{
		return recipeItems.clone();
	}

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world)
	{
		for (int i = 0; i <= 5 - recipeWidth; i++)
		{
			for (int j = 0; j <= 5 - recipeHeight; j++)
			{
				if (compare(inventorycrafting, i, j, true))
				{
					return true;
				}
				if (compare(inventorycrafting, i, j, false))
				{
					return true;
				}
			}
		}

		return false;
	}
	
	@Override
	public boolean matches(InventoryCrafting inventorycrafting, int x, int y, int z, World world)
	{
		int regionIndex = TFC_Climate.getRegionLayer(world, x, Global.SEALEVEL, z);
		if(validRegions == null || validRegions.length == 0)
		{
			boolean matches = false;
			matches = matches(inventorycrafting,world);
			if(matches)
			{
				//System.out.println("valid regions: " + validRegions);
			}
			return matches;
		}
		else
		{
			EnumRegion region = EnumRegion.values()[regionIndex];
			for(int i = 0; i < validRegions.length;i++)
			{
				if(region == validRegions[i])
				{
					return matches(inventorycrafting,world); 
				}
			}
		}
		return false;
	}

	private static boolean tempMatch(ItemStack recipeIS, ItemStack inputIS)
	{
		NBTTagCompound rnbt = recipeIS.getTagCompound();
		NBTTagCompound inbt = inputIS.getTagCompound();

		if (rnbt != null && rnbt.hasKey("noTemp"))
		{
			// Recipe expects a cold item and either the input has not tag at
			// all or at the least is missing a temperature tag
			return inbt == null || !TFC_ItemHeat.hasTemp(inputIS);
		}

		if (rnbt != null && TFC_ItemHeat.hasTemp(recipeIS))
		{
			if (inbt != null && TFC_ItemHeat.hasTemp(inputIS))
			{
				return HeatRegistry.getInstance().getIsLiquid(inputIS);// Recipe
																		// expects
																		// a hot
																		// item
																		// and
																		// the
																		// input
																		// is
																		// liquid
			}
			else
			{
				return false;// Recipe expects a cold item and the input is not
								// cold
			}
		}

		return true;
	}
}
