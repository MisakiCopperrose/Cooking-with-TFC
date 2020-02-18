package com.dunk.tfc.api.Crafting;

import java.util.ArrayList;

import com.dunk.tfc.api.Interfaces.ISewable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SewingPattern
{
	private ItemStack itemToCreate;
	/**
	 * This is how patterns function: the structure is a 3D int array. The most basic level is a 2D vector representing a pixel coordinate.
	 * An array of these vectors represents a single path, where the first vector is the starting position and the remaining ones are the
	 * following points which define the path of the pattern
	 * the patterns structure contains multiples of these when sewn lines are disconnected
	 */
	private int[][][] patterns;
	/**
	 * This structure represents where it is valid to sew. We'll try a radius of 4 and a distance between points of 5.
	 */
	private ArrayList<stitchZone> mySewingPoints;
	private static int radius = 4;
	private static int distance = 5;
	private static int minStitches = 2;
	
	
	public SewingPattern (ItemStack item, int[][][] newPattern)
	{
		itemToCreate = item;
		patterns = newPattern;
		mySewingPoints = new ArrayList<stitchZone>();
		makeSewingPattern();
	}
	
	private void makeSewingPattern()
	{
		//Start on the first pattern
		boolean[][] stencil = ((ISewable)(itemToCreate.getItem())).getClothingAlpha();
		for(int i = 0; i < patterns.length; i++)
		{
			//This accounts for instances where points are close together. For example if two points were 6 units apart, we'd have a problem
			//The problem would be that first point would be 2.5 units from the start, but would be 3.5 from the end.
			//Since 3.5 < 5, this would mean the next point on the new line would be 6 away instead of only 5. This
			//allows us to adjust how far from the beginning we go
			double previousStartDist = -2.5;
			for(int j = 0; j < patterns[i].length - 1; j++)
			{
				int[] start = patterns[i][j];
				int[] end = patterns[i][j+1];
				double xDist = (double)(end[0] - start[0]);
				double yDist = (double)(end[1] - start[1]);
				double distance = Math.sqrt(xDist * xDist + yDist * yDist);
				double startDist = Math.min((5 + previousStartDist), distance/2d);
				int numPoints = 0;
				for(double k = startDist; k < distance; k+=5)
				{
					double xOffset = xDist * (k/distance);
					double yOffset = yDist * (k/distance);
					//if(stencil[start[0] + (int) Math.round(xOffset)-33][start[1] + (int)Math.round(yOffset)])
					//{
					
					int req;
					if(k == startDist || k+5 >= distance)
					{
						req = minStitches/2;
					}
					else
					{
						req = minStitches;
					}
					stitchZone newPoint = new stitchZone(start[0] + (int) Math.round(xOffset),start[1] + (int)Math.round(yOffset),req, i);
					previousStartDist = k - distance;
					mySewingPoints.add(newPoint);
					numPoints++;
				//	}
				//	else
				//	{
				//		System.out.println("we tried to make a point out of bounds.");
				//	}
				}
				if(numPoints == 0)
				{
				//	System.out.println("We didn't have any points!");
				}
			}
		}
	}
	
	//Shifts the given pattern over by 33 units in x because that's how it works.
	public SewingPattern (ItemStack item, int[][][] newPattern,boolean b)
	{
		if(b)
		{
			for(int i = 0; i < newPattern.length;i++)
			{
				for(int j = 0; j < newPattern[i].length;j++)
				{
					newPattern[i][j][0]+=33;
				}
			}
		}

		itemToCreate = item;
		patterns = newPattern;
		mySewingPoints = new ArrayList<stitchZone>();
		makeSewingPattern();
	}
	
	public Item getResultingItem()
	{
		return itemToCreate.getItem();
	}
	
	public int[][][] getPatterns()
	{
		return patterns;
	}
	
	/**
	 * We take an array of booleans representing whether there is a stitch at a location and ensure that all stitches match a point sufficiently and that each stitch has a home
	 * @param sewnPoints
	 * @return
	 */
	public Object[] AllPointsMatch(boolean[][] sewnPoints)
	{
		//First we copy our array. we do this so we can find orphaned stitches.
		boolean[][] validator = new boolean[sewnPoints.length][sewnPoints[0].length];
		for(int i = 0; i < sewnPoints.length; i++)
		{
			for(int j = 0; j < sewnPoints[0].length;j++)
			{
				validator[i][j] = sewnPoints[i][j];
			}
		}
		boolean willFail = false;
		ArrayList<int[]> badZones = new ArrayList<int[]>();
		int[][]badZonesArray;
		//we go through all the sewing points
		for(stitchZone p : mySewingPoints)
		{
			int x = p.x;
			int y = p.y;
			int remainingRequiredStitches = p.requiredStitches;
			for(int i = -radius; i < radius ; i++)
			{
				for(int j = -radius; j < radius ; j++)
				{
					if(i * i + j * j > radius * radius)
					{
						//This means we're too far away
						continue;
					}
					if(sewnPoints[Math.max(Math.min(x+i, sewnPoints.length-1), 0)][Math.max(Math.min(y+j, sewnPoints[0].length-1), 0)])
					{
						validator[Math.max(Math.min(x+i, sewnPoints.length-1), 0)][Math.max(Math.min(y+j, sewnPoints[0].length-1), 0)] = false;
						remainingRequiredStitches--;
					}
				}
			}
			if(remainingRequiredStitches > 0)
			{
				//System.out.println("Not enough stitches! Required: " + p.requiredStitches +", remaining: " + remainingRequiredStitches +"; at "+p.x+", "+p.y);
				/*for(int i = 0; i < 98; i++)
				{
					for(int j = 0; j < sewnPoints.length; j++)
					{
						System.out.print(""+(sewnPoints[j][i]?"t":"f"));						
					}
					System.out.println();
				}*/
				willFail = true;
				badZones.add(new int[] {p.x, p.y,radius, p.patternNum});
				//return false;
			}
		}
		badZonesArray = new int[badZones.size()][];
		badZones.toArray(badZonesArray);
		for(int i = 0; i < validator.length; i++)
		{
			for(int j = 0; j < validator[0].length; j++)
			{
				if(validator[i][j])
				{
					//System.out.println("Points were outside of bounds!");
					return new Object[] {validator,badZonesArray};
				}
			}
		}
		return new Object[] {validator,badZonesArray};//true && !willFail;
	}
	
	public ItemStack getOutput(SewingRecipe recipe, Object[] inputs)
	{
		ItemStack[] items = new ItemStack[inputs.length];
		for(int i = 0; i < inputs.length; i++)
		{
			items[i] = (ItemStack)inputs[i];
		}
		return getOutput(recipe,items);
	}
	
	public ItemStack getOutput(SewingRecipe recipe, ItemStack[] inputs)
	{
		if(recipe.repairRecipe)
		{
			//Find the input item that matches the output item
			for(int i = 0; i < inputs.length; i++)
			{
				//We found our item
				if(inputs[i] != null && inputs[i].getItem() == itemToCreate.getItem())
				{
					ItemStack output = inputs[i].copy();
					int maxDamage = output.getItem().getMaxDamage();
					int repairCost = 1;
					if(output.getItem() instanceof ISewable)
					{
						repairCost = ((ISewable)output.getItem()).getRepairCost();
					}
					if(output.getItemDamage() < maxDamage/repairCost)
					{
						output.setItemDamage(0);
						return output;
					}
					//otherwise, we can just subtract the damage
					output.setItemDamage(output.getItemDamage() - (maxDamage/repairCost));
					return output;
				}
			}
		}
		return itemToCreate.copy();
	}
	
	/**
	 * Given a scaled array of sewn points and a scale factor, we resize the boolean array so that each coordinate is a pixel.
	 * @param sewnPoints
	 * @return
	 */
	public Object[] AllPointsMatch(boolean[][] sewnPoints, int scaleFactor)
	{
		boolean[][] scaledPoints = new boolean[sewnPoints.length*scaleFactor][sewnPoints[0].length *scaleFactor];
		for(int i = 0; i < sewnPoints.length;i++)
		{
			for(int j = 0; j < sewnPoints[0].length;j++)
			{
				scaledPoints[i*scaleFactor][j*scaleFactor] = sewnPoints[i][j];
			}
		}		
		return AllPointsMatch(scaledPoints);
	}
	
	private class stitchZone
	{
		public int x;
		public int y;
		public int requiredStitches;
		public int patternNum;
		
		public stitchZone(int x, int y, int req, int p)
		{
			this.x = x;
			this.y = y;
			this.requiredStitches = req;
			this.patternNum = p;
		}
	}
}
