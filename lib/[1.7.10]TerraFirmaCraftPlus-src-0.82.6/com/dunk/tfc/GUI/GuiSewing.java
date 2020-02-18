package com.dunk.tfc.GUI;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Containers.ContainerSewing;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.Items.Tools.ItemKnife;
import com.dunk.tfc.Items.Tools.ItemNeedle;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Crafting.ClothingManager;
import com.dunk.tfc.api.Crafting.SewingRecipe;
import com.dunk.tfc.api.Interfaces.ISewable;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiSewing extends GuiContainerTFC
{

	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,
			Reference.ASSET_PATH_GUI + "gui_sewing.png");
	public static ResourceLocation sewTexture = new ResourceLocation(Reference.MOD_ID,
			Reference.ASSET_PATH_GUI + "gui_sewnthread.png");
	public static ResourceLocation guideTexture = new ResourceLocation(Reference.MOD_ID,
			Reference.ASSET_PATH_GUI + "gui_sewingguide.png");
	public static ResourceLocation failTexture = new ResourceLocation(Reference.MOD_ID,
			Reference.ASSET_PATH_GUI + "gui_sewingfail.png");

	public static ResourceLocation currentTexture;

	public int serverNeedleHealth;

	private boolean[][] sewnPoints;
	// This keeps track of any stitches that are in an illegal area
	private boolean[][] invalids;
	// This keeps track of areas without enough stitches
	private int[][] invalidZones;
	private long lastClick = 0;
	private int lastX, lastY;
	private boolean hasStitchesSinceLastCheck;

	private SewingRecipe sr;
	boolean[][] validAlphas;

	// InventorySlots is our client-side container

	// We use this to decide whether we should be checking if the current
	// configuration is valid
	// We only check if it's valid when the player isn't holding an item
	// And only if stitchesChanged is true. When we check, we set
	// stitchesChanged to false so that
	// we don't look again until after the player changes something.
	private boolean stitchesChanged = false;

	private double sewScale = 2;

	public GuiSewing(InventoryPlayer inventoryplayer, World world, int i, int j, int k)
	{
		super(new ContainerSewing(inventoryplayer, world, i, j, k), 176, 149);
		((ContainerSewing) inventorySlots).setGUI(this);
		sewnPoints = new boolean[(int) Math.round(164 / sewScale)][(int) Math.round(98 / sewScale)];
		lastX = lastY = -1;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
		if (currentTexture != null)
		{
			bindTexture(currentTexture);
			drawTextured98Rect((this.guiLeft + 6) + 33, (this.guiTop + 6), 0, 0, 98, 98);
		}
		bindTexture(failTexture);
		if (sr != null)
		{
			int[][][] patterns = sr.getSewingPattern().getPatterns();
			for (int a = 0; a < patterns.length; a++)
			{
				for (int b = 0; b < patterns[a].length - 1; b++)
				{
					drawGuideLines(patterns[a][b][0] + (this.guiLeft + 6), patterns[a][b][1] + (this.guiTop + 6), patterns[a][b + 1][0] + (this.guiLeft + 6),
							patterns[a][b + 1][1] + (this.guiTop + 6), a);
				}
			}
		}
		bindTexture(sewTexture);
		for (int x = 0; x < 164 / sewScale; x++)
		{
			for (int y = 0; y < 98 / sewScale; y++)
			{
				if (sewnPoints[x][y])
				{
					drawSewing((x * (int) sewScale) + (this.guiLeft + 6), (y * (int) sewScale) + (this.guiTop + 6));
				}
			}
		}
		if (invalids != null)
		{
			bindTexture(failTexture);
			for (int x = 0; x < 164; x++)
			{
				for (int y = 0; y < 98; y++)
				{
					if (invalids[x][y])
					{
						drawSewing((x) + (this.guiLeft + 6), (y) + (this.guiTop + 6));
					}
				}
			}
		}
		for (int j1 = 0; j1 < this.inventorySlots.inventorySlots.size(); ++j1)
		{
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(j1);
			if (this.isMouseOverSlot(slot, i, j))
				this.activeSlot = slot;
		}
	}

	public void setSewingRecipe(int index)
	{
		if (index < 0)
		{
			sr = null;
			currentTexture = null;
			validAlphas = null;
		}
		else
		{
			sr = ClothingManager.getInstance().getRecipeByIndex(index);
			currentTexture = ((ISewable) (sr.getSewingPattern().getResultingItem())).getFlatTexture();
			validAlphas = ((ISewable) (sr.getSewingPattern().getResultingItem())).getClothingAlpha();
		}
	}

	/**
	 * call this when you want to reset the gui because you've done everything
	 * already.
	 */
	public void reset()
	{
		sewnPoints = new boolean[(int) Math.round(164 / sewScale)][(int) Math.round(98 / sewScale)];
		lastX = lastY = -1;
		invalids = null;
		hasStitchesSinceLastCheck = false;
		invalidZones = null;
		setSewingRecipe(-1);
	}

	public void updateScreen()
	{
		ItemStack is = this.mc.thePlayer.inventory.getItemStack();
		if (is != null && (is.getItem() == TFCItems.boneNeedle || is.getItem() == TFCItems.ironNeedle))
		{
			// Ask the server if the needle could be replenished
			this.mc.playerController.windowClick(this.inventorySlots.windowId, -5026, -1, 0, this.mc.thePlayer);
		}
		if (is == null && stitchesChanged)
		{
			stitchesChanged = false;
			if (hasStithces())
			{
				// This means we need to lock the input
				this.mc.playerController.windowClick(this.inventorySlots.windowId, -5024, -1, -1, this.mc.thePlayer);
			}
			else
			{
				// This means we need to unlock the input
				this.mc.playerController.windowClick(this.inventorySlots.windowId, -5024, -2, -1, this.mc.thePlayer);
			}
			Object[] results = validateStitches();
			invalids = (boolean[][]) results[0];
			invalidZones = (int[][]) results[1];
			// invalids = validateStitches();
			if (invalids != null && checkStitches() && invalidZones.length == 0)
			{
				// System.out.println("Should be correct!");
				// This is how we tell the server what we need it to know.
				this.mc.playerController.windowClick(this.inventorySlots.windowId, -5023, -1, -1, this.mc.thePlayer);
			}
		}
	}

	/**
	 * Draws a textured rectangle at the stored z-value. Args: x, y, u, v,
	 * width, height
	 */
	public void drawTextured98Rect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_,
			int p_73729_6_)
	{
		float f = 0.010204082F;
		float f1 = 0.010204082F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) (p_73729_1_ + 0), (double) (p_73729_2_ + p_73729_6_), (double) this.zLevel,
				(double) ((float) (p_73729_3_ + 0) * f), (double) ((float) (p_73729_4_ + p_73729_6_) * f1));
		tessellator.addVertexWithUV((double) (p_73729_1_ + p_73729_5_), (double) (p_73729_2_ + p_73729_6_),
				(double) this.zLevel, (double) ((float) (p_73729_3_ + p_73729_5_) * f),
				(double) ((float) (p_73729_4_ + p_73729_6_) * f1));
		tessellator.addVertexWithUV((double) (p_73729_1_ + p_73729_5_), (double) (p_73729_2_ + 0), (double) this.zLevel,
				(double) ((float) (p_73729_3_ + p_73729_5_) * f), (double) ((float) (p_73729_4_ + 0) * f1));
		tessellator.addVertexWithUV((double) (p_73729_1_ + 0), (double) (p_73729_2_ + 0), (double) this.zLevel,
				(double) ((float) (p_73729_3_ + 0) * f), (double) ((float) (p_73729_4_ + 0) * f1));
		tessellator.draw();
	}

	private boolean hasStithces()
	{
		for (int i = 0; i < sewnPoints.length; i++)
		{
			for (int j = 0; j < sewnPoints[0].length; j++)
			{
				if (sewnPoints[i][j])
				{
					hasStitchesSinceLastCheck = true;
					return true;
				}
			}
		}
		hasStitchesSinceLastCheck = false;
		return false;
	}

	private boolean checkStitches()
	{
		for (boolean[] b : invalids)
		{
			for (boolean bb : b)
			{
				if (bb)
				{
					return false;
				}
			}
		}
		return true;
	}

	private Object[] validateStitches()
	{
		if (sr != null)
		{
			return sr.getSewingPattern().AllPointsMatch(sewnPoints, (int) sewScale);
		}
		return null;
	}

	private void drawSewing(int i, int j)
	{
		drawTexturedModalRect(i, j, 0, 0, 1, 1);
	}

	protected void drawGuideLines(int x0, int y0, int x2, int y2, int patternNum)
	{
		int x = x0 < x2 ? 1 : -1;
		int y = y0 < y2 ? 1 : -1;
		if ((x2 - x0) * x > (y2 - y0) * y)
		{
			for (int i = 0; i < (x2 - x0) * x; i++)
			{
				int xP = x0 + i * x;
				int yP = y0 + (int) (1 * (y2 - y0) * ((double) i / (x * (x2 - x0))));
				boolean failed = false;
				// If we have a list of invalid zones, the list has items in it
				// and we have stitches, highlight the bad ones in red. If we
				// don't have any stitches, we'll
				// obviously not have any zones.
				if (invalidZones != null && invalidZones.length > 0)
				{
					for (int k = 0; k < invalidZones.length; k++)
					{
						if (invalidZones[k][3] == patternNum)
						{
							int xD = (invalidZones[k][0] + (this.guiLeft + 6)) - xP;
							int yD = (invalidZones[k][1] + (this.guiTop + 6)) - yP;
							if (xD * xD + yD * yD <= (invalidZones[k][2]) * (invalidZones[k][2]))
							{
								bindTexture(failTexture);
								failed = true;
							}
						}
					}
				}
				if (!failed)
				{
					bindTexture(hasStitchesSinceLastCheck ? guideTexture : failTexture);
				}

				drawTexturedModalRect(xP, yP, 0, 0, 1, 1);
				// System.out.println("Drawing at (" + (x0 + i * x) + ","
				// + (y0 + (int) (y * (y2 - y0) * ((double) i / (x * (x2 -
				// x0))))) + "), which is from (" + x0
				// + "," + y0 + ") to (" + x2 + "," + y2 + ")");
			}
		}
		else
		{
			for (int i = 0; i < (y2 - y0) * y; i++)
			{
				int xP = x0 + (int) (1 * (x2 - x0) * ((double) i / (y * (y2 - y0))));
				int yP = y0 + i * y;
				boolean failed = false;
				if (invalidZones != null && invalidZones.length > 0)
				{
					for (int k = 0; k < invalidZones.length; k++)
					{
						if (invalidZones[k][3] == patternNum)
						{
							int xD = (invalidZones[k][0] + (this.guiLeft + 6)) - xP;
							int yD = (invalidZones[k][1] + (this.guiTop + 6)) - yP;
							if (xD * xD + yD * yD <= (invalidZones[k][2]) * (invalidZones[k][2]))
							{
								bindTexture(failTexture);
								failed = true;
							}
						}
					}
				}
				if (!failed)
				{
					bindTexture(hasStitchesSinceLastCheck ? guideTexture : failTexture);
				}
				drawTexturedModalRect(xP, yP, 0, 0, 1, 1);
			}
		}
	}

	/**
	 * This function is what controls the hotbar shortcut check when you press a
	 * number key when hovering a stack.
	 */
	@Override
	protected boolean checkHotbarKeys(int par1)
	{
		if (this.mc.thePlayer.inventory.currentItem != par1 - 2)
		{
			super.checkHotbarKeys(par1);
			return true;
		}
		else
			return false;
	}

	// This is where we can determine if we have a valid sewing or not.
	private void sewPoint(int mouseX, int mouseY, boolean sew)
	{
		// Here we check that we have a valid place to sew. That means we're
		// sewing on top of the texture.
		int xAlpha = (int) Math.round((double) (mouseX - ((this.guiLeft + 6) + 33)) / 1);
		int yAlpha = (int) Math.round((double) (mouseY - (this.guiTop + 6)) / 1);
		if (validAlphas == null
				|| (xAlpha <= 0 || yAlpha <= 0 || xAlpha >= 98 || yAlpha >= 98 || !validAlphas[xAlpha][yAlpha]))
		{
			return;
		}
		if (sew && !sewnPoints[(int) Math.round((double) (mouseX - (this.guiLeft + 6)) / sewScale)][(int) Math
				.round((double) (mouseY - (this.guiTop + 6)) / sewScale)])
		{
			// Tell the server to damage our item
			this.mc.playerController.windowClick(this.inventorySlots.windowId, -5025, -1, 0, this.mc.thePlayer);
			// this.mc.thePlayer.inventory.getItemStack().damageItem(1,
			// this.mc.thePlayer);
		}
		if (!sew)
		{
			for (int i = -1; i <= 1; i++)
			{
				for (int j = -1; j <= 1; j++)
				{
					sewnPoints[Math.max(0,
							Math.min(((int) Math.round((double) (mouseX - (this.guiLeft + 6)) / sewScale)) + i,
									sewnPoints.length - 1))][Math.max(0,
											Math.min((int) Math.round(((double) (mouseY - (this.guiTop + 6)) / sewScale)) + j,
													sewnPoints[0].length - 1))] = sew;
				}
			}
		}
		else
		{
			sewnPoints[(int) Math.round((double) (mouseX - (this.guiLeft + 6)) / sewScale)][(int) Math
					.round((double) (mouseY - (this.guiTop + 6)) / sewScale)] = sew;
		}
		stitchesChanged = true;
		invalids = null;
	}

	protected void sewLine(int oldX, int oldY, int newX, int newY, boolean sew)
	{

		double deltaX, deltaY;
		double length;
		deltaX = newX - oldX;
		deltaY = newY - oldY;
		length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		double xUnit, yUnit;
		xUnit = deltaX / length;
		yUnit = deltaY / length;

		try
		{
			// What we do is see which is smaller, deltaX or deltaY
			double absDeltaX = Math.abs(deltaX);
			double absDeltaY = Math.abs(deltaY);
			if (absDeltaX < absDeltaY)
			{
				int x = 1;
				if (0 > deltaX)
				{
					x = -1;
				}
				int y = 1;
				if (0 > deltaY)
				{
					y = -1;
				}
				for (double i = 0; i <= absDeltaX; i++)
				{

					for (double j = i * (int) (absDeltaY / Math.max(1, absDeltaX)); j < (i + 1) * absDeltaY
							/ Math.max(1, absDeltaX); j++)
					{

						sewPoint((oldX + (int) Math.round(x * i)), (oldY + (int) Math.round(y * j)), sew);
					}
				}
			}
			else
			{
				int x = 1;
				if (0 > deltaX)
				{
					x = -1;
				}
				int y = 1;
				if (0 > deltaY)
				{
					y = -1;
				}
				for (double j = 0; j <= absDeltaY; j++)
				{

					for (double i = j * (absDeltaX / Math.max(1, absDeltaY)); i < (j + 1) * absDeltaX
							/ Math.max(1, absDeltaY); i++)
					{
						sewPoint((oldX + (int) Math.round(x * i)), (oldY + (int) Math.round(y * j)), sew);
					}
				}
			}
			/*
			 * sewnPointDirections[(int)Math.round((double)(oldX +
			 * Math.round(xUnit * i) - (this.guiLeft + 6)) / sewScale)][(int)Math.round((oldY +
			 * Math.round(yUnit * i) - 15) / sewScale)] = direction;
			 * sewPoint((oldX + (int) Math.round(xUnit * i)), (oldY + (int)
			 * Math.round(yUnit * i)), sew);
			 */

		}
		catch (Exception e)
		{
			//System.out.println(e);
			//System.out.println("unitX: " + xUnit + ", unitY: " + yUnit + ", length: " + length + ", oldX: " + oldX
				//	+ ", oldY: " + oldY + ", newX: " + newX + ", newY: " + newY);
		}
	}

	// So what we need to do is basically record when the mouse is pressed and
	// dragged. If it's a left click, that's sewing. If it's a right click,
	// that's removing thread.
	// We may highlight where the sewing needs to be?

	// Hold a needle to sew, a knife to remove stitches
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		ItemStack is = this.mc.thePlayer.inventory.getItemStack();
		if (is != null && sr != null)
		{
			if ((is.getItem() instanceof ItemNeedle && !((ItemNeedle) is.getItem()).stackable)
					|| (is.getItem() instanceof ItemKnife && clickedMouseButton == 1))
			{
				// System.out.println(mouseX + ", " + mouseY + ": " +
				// clickedMouseButton);
				boolean sew = is.getItem() instanceof ItemNeedle;
				int movePoint = sew ? -9 : -6;
				// We're in the right region
				// if (mouseX - movePoint > (this.guiLeft + 6) && mouseY - movePoint> 15 &&
				// mouseY - movePoint < 115 && mouseX - movePoint < 322)
				// {
				if ((mouseY + movePoint - (this.guiTop + 6)) / sewScale < sewnPoints[0].length
						&& (mouseY + movePoint - (this.guiTop + 6)) / sewScale > 0
						&& (mouseX + movePoint - (this.guiLeft + 6)) / sewScale < sewnPoints.length
						&& (mouseX + movePoint - (this.guiLeft + 6)) / sewScale > 0)
				{

					// If we're removing a sewn point, we want to do it at
					// the tip of the knife

					// This means we haven't released the mouse yet.
					if (timeSinceLastClick >= lastClick && lastX > -1 && lastY > -1 && Math.abs(mouseX - lastX) < 8
							&& Math.abs(mouseY - lastY) < 8)
					{
						// We need to sew a line from there to here.
					//	System.out.println("Sewing a line. timeSinceLastClick: " + timeSinceLastClick + ", lastClick: "
					//			+ lastClick + ", lastX: " + lastX + ", lastY: " + lastY);
						sewLine(lastX + movePoint, lastY + movePoint, mouseX + movePoint, mouseY + movePoint, sew);
					}
					else
					{
						// System.out.println("not sewing a line
						// timeSinceLastClick: " + timeSinceLastClick
						// + ", lastClick: " + lastClick + ", lastX: " + lastX +
						// ", lastY: " + lastY);
						sewPoint(mouseX + movePoint, mouseY + movePoint, sew);
					}

				}

				// }
				// else
				// {
				// System.out.println("The coords were: " + ((mouseX + movePoint
				// - (this.guiLeft + 6)) / sewScale) +", " + sewnPoints.length +"; " + ((mouseY
				// + movePoint - 15) / sewScale)+", " + sewnPoints[0].length);
				// }

				lastClick = timeSinceLastClick;
				lastX = mouseX;
				lastY = mouseY;
			}
		}

	}
}
