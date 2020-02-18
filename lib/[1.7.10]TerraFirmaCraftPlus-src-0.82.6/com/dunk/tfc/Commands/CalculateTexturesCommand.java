package com.dunk.tfc.Commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class CalculateTexturesCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "animaltexture";
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		//This code is now outdated. I have a different version which creates the image itself and colorcodes it.
		//executeCalculations();
		//sender.addChatMessage(new ChatComponentText(""+TFC_Time.getPercentSummer(TFC_Time.currentDay, sender.getPlayerCoordinates().posZ)));
		EntityPlayer ep = sender.getEntityWorld().getPlayerEntityByName(sender.getCommandSenderName());
		ItemStack is = new ItemStack(TFCBlocks.primitiveLoom);
		EntityItem ei = new EntityItem(ep.worldObj,ep.posX,ep.posY,ep.posZ,is);
		ep.worldObj.spawnEntityInWorld(ei);
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return true;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		// TODO Auto-generated method stub
		return "";
	}
	
	public static void executeCalculations()
	{
		//We have a list of all of the shapes we need.
		//A shape has a length, width, and height.
		//In reality, we only need the total width and height of the shape, plus the value which will be the square cut out's side length
		//We should accept the original length-width-height to avoid confusion though.
		//We sort the shapes from smallest to largest
		//We go through the list in order and for each shape, we try to place it as near to the top left corner of the window as possible without overlapping
		//We try to minimize the total area of the shape, but we care about minimizing height more than width by two times.
		ArrayList<Shape> finalShapes = new ArrayList<Shape>();
		ArrayList<Shape> initialShapes = new ArrayList<Shape>();
		
		initialShapes.add(new Shape("head",2,2,5));
		initialShapes.add(new Shape("hornCentre",4,3,3));
		initialShapes.add(new Shape("butt",8,7,7));
		initialShapes.add(new Shape("tail",3,4,1));
		initialShapes.add(new Shape("upperMouth",5,4,8));
		initialShapes.add(new Shape("jaw",4,2,10));
		initialShapes.add(new Shape("snout",5,5,5));
		initialShapes.add(new Shape("muzzle",5,5,5));
		initialShapes.add(new Shape("rightEar",2,3,1));
		initialShapes.add(new Shape("body",10,8,10));
		initialShapes.add(new Shape("wildWool",10,6,5));
		initialShapes.add(new Shape("chest",10,12,10));
		initialShapes.add(new Shape("neck",3,6,10));
		initialShapes.add(new Shape("leftThigh",5,8,7));
		initialShapes.add(new Shape("leftCalf",3,7,3));
		initialShapes.add(new Shape("leftFoot",2,8,2));
		initialShapes.add(new Shape("leftBackToe",2,2,2));
		initialShapes.add(new Shape("leftShoulder",3,5,4));
		initialShapes.add(new Shape("leftUpperArm",3,5,4));
		initialShapes.add(new Shape("leftForeArm",2,6,2));
		initialShapes.add(new Shape("leftWrist",2,6,2));
		initialShapes.add(new Shape("leftToe",2,2,2));
		initialShapes.add(new Shape("horn",3,3,3));
		
		Collections.sort(initialShapes, new ShapeSorter());
		boolean error = false;
		//For each shape in our initialShapes, we go through it
		for (Shape shape : initialShapes)
		{
			if(error)
			{
				continue;
			}
			int maxWidth = 512;
			int maxHeight = 256;
			int bestX = -1;
			int bestY = -1;
			int bestWidth = 999;
			int bestHeight = 999;
			for(int x = 0; x < 512 && x + shape.getMaxWidth() <= maxWidth; x++)
			{
				//Since we're not optimizing anything, if our bestY is less than y, there's no way we can get any better by checking higher y values.
				for(int y = 0; y < 256 && y + shape.getMaxHeight() <= maxHeight && (bestX == -1 || (bestY > y)); y++)
				{
					boolean valid = true;
					shape.x = x;
					shape.y = y;
					//If we this spot is already worse, we shouldn't even bother.
					if((((shape.getMaxWidth() + x)/2) + shape.getMaxHeight() + y) <= (bestWidth/2 + bestHeight))
					{
						for(Shape shape2 : finalShapes)
						{
							if(!valid)
							{
								continue;
							}
						
							if(shapesOverlap(shape,shape2))
							{
								//System.out.println("shapes overlapped: (" + shape.x + ", " + shape.y +") and (" + shape2.x + ", " + shape2.y + ")");
								valid = false;
							}
						}
						if(valid)
						{
							bestX = x;
							bestY = y;
							bestWidth = shape.getMaxWidth() + x;
							bestHeight = shape.getMaxHeight() + y;
						}
					}
					else
					{
						//System.out.println("unable to place shape: " + shape.name + " at " + (((shape.getMaxWidth() + x)/2) + shape.getMaxHeight() + y) + " > " + (bestWidth/2 + bestHeight));
					}
				}
			}
			//Here, we should have found out where our shape should go.
			if(bestX != -1 && bestY != -1)
			{
				System.out.println("Placing " + shape.name + " at " + bestX + ", " + bestY);
				shape.x = bestX;
				shape.y = bestY;
				finalShapes.add(shape);
			}
			else
			{
				//Something went horribly wrong!
				System.out.println("Error! Valid position for shape: " + shape.name + " couldn't be found!");
				error = true;
			}
		}
		if(!error)
		{
			//We've successfully created a layout for our shapes!
		}
	}
	
	public static boolean shapesOverlap(Shape one, Shape two)
	{
		//We check if shapes overlap by seeing if the either is inside the other
		//We take the four points that define the 2 rectangles of the shape. Each rectangle is defined by a pair
		//If either rectangle straddles the two points of either rectangle of the second shape, they overlap
		int[] oneTUL = one.getTopUpperLeft();
		int[] oneTBR = one.getTopLowerRight();
		int[] twoTUL = two.getTopUpperLeft();
		int[] twoTBR = two.getTopLowerRight();
		
		int[] oneBUL = one.getBottomUpperLeft();
		int[] oneBBR = one.getBottomLowerRight();
		int[] twoBUL = two.getBottomUpperLeft();
		int[] twoBBR = two.getBottomLowerRight();
		
		//The top left corner of oneT is left of the bottomRight corner of twoT and the bottom right corner of oneT is right of the topLeft corner of twoT
		//The top left corner of oneT is above of the bottomRight corner of twoT and the bottom right corner of oneT is below of the topLeft corner of twoT
		if( oneTUL[0] <= twoTBR[0] && oneTBR[0] >= twoTUL[0] && oneTUL[1] <= twoTBR[1] && oneTBR[1] >= twoTUL[1] )
		{
			//The top shapes overlapped, so we're already done
			return true;
		}
		
		//The top left corner of oneT is left of the bottomRight corner of twoB and the bottom right corner of oneT is right of the topLeft corner of twoB
		//The top left corner of oneT is above of the bottomRight corner of twoB and the bottom right corner of oneT is below of the topLeft corner of twoB
		if( oneTUL[0] <= twoBBR[0] && oneTBR[0] >= twoBUL[0] && oneTUL[1] <= twoBBR[1] && oneTBR[1] >= twoBUL[1] )
		{
			//The top shape overlapped with the bottom, so we're already done
			return true;
		}
		
		//The top left corner of oneB is left of the bottomRight corner of twoT and the bottom right corner of oneB is right of the topLeft corner of twoT
		//The top left corner of oneB is above of the bottomRight corner of twoT and the bottom right corner of oneB is below of the topLeft corner of twoT
		if( oneBUL[0] <= twoTBR[0] && oneBBR[0] >= twoTUL[0] && oneBUL[1] <= twoTBR[1] && oneBBR[1] >= twoTUL[1] )
		{
			//The top shapes overlapped, so we're already done
			return true;
		}
		
		//The top left corner of oneB is left of the bottomRight corner of twoB and the bottom right corner of oneB is right of the topLeft corner of twoB
		//The top left corner of oneB is above of the bottomRight corner of twoB and the bottom right corner of oneB is below of the topLeft corner of twoB
		if( oneBUL[0] <= twoBBR[0] && oneBBR[0] >= twoBUL[0] && oneBUL[1] <= twoBBR[1] && oneBBR[1] >= twoBUL[1] )
		{
			//The top shapes overlapped, so we're already done
			return true;
		}
		return false;
	}
	
	static class ShapeSorter implements Comparator<Shape>
	{
		public int compare(Shape a, Shape b)
		{
			int areaA = a.getArea();
			int areaB = b.getArea();
			return areaA - areaB;
		}
	}
	
	static class Shape
	{
		public int x = 0;
		public int y = 0;
		int length;
		int width;
		int height;	
		public String name;
		
		public Shape(String n, int w, int h, int l)
		{
			this.name = n;
			this.length = l;
			this.width = w;
			this.height = h;
		}
		
		public int getArea()
		{
			return ((length + height) * (2 * (length + width))) - (2 * length * length);
		}
		
		public int getMaxWidth()
		{
			return 2 * (length + width);
		}
		
		public int getMaxHeight()
		{
			return length + height;
		}
		
		public int[] getTopUpperLeft()
		{
			return new int[]{x + length, y};
		}	
		
		public int[] getTopLowerRight()
		{
			return new int[]{x + getMaxWidth() - length,y + length};
		}
			
		public int[] getBottomUpperLeft()
		{
			return new int[]{x,y + length};
		}
		
		public int[] getBottomLowerRight()
		{
			return new int[]{x + getMaxWidth(),y + getMaxHeight()};
		}
	}

}
