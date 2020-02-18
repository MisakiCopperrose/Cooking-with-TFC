package com.dunk.tfc.Core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class TFC_Textures
{
	/**
	 * Registered in ItemTerraTool
	 */
	public static IIcon brokenItem;
	public static IIcon wip;
	/**
	 * These are defined in BlockGrass
	 */
	public static IIcon invisibleTexture;
	/**
	 * These are defined in BlockAnvil
	 */
	public static IIcon anvilHit;
	public static IIcon anvilHitHeavy;
	public static IIcon anvilHitMedium;
	public static IIcon anvilHitLight;
	public static IIcon anvilDraw;
	public static IIcon anvilPunch;
	public static IIcon anvilBend;
	public static IIcon anvilUpset;
	public static IIcon anvilShrink;

	/**
	 * These are defined in BlockTuyere
	 */
	public static IIcon sheetBismuth;
	public static IIcon sheetBismuthBronze;
	public static IIcon sheetBlackBronze;
	public static IIcon sheetBlackSteel;
	public static IIcon sheetBlueSteel;
	public static IIcon sheetBrass;
	public static IIcon sheetBronze;
	public static IIcon sheetCopper;
	public static IIcon sheetGold;
	public static IIcon sheetLead;
	public static IIcon sheetNickel;
	public static IIcon sheetPigIron;
	public static IIcon sheetPlatinum;
	public static IIcon sheetRedSteel;
	public static IIcon sheetRoseGold;
	public static IIcon sheetSilver;
	public static IIcon sheetSteel;
	public static IIcon sheetSterlingSilver;
	public static IIcon sheetTin;
	public static IIcon sheetWroughtIron;
	public static IIcon sheetZinc;

	/**
	 * These are defined in BlockHotWaterStill
	 */
	public static IIcon gasFXIcon;
	public static IIcon guiInventory;
	public static IIcon guiSkills;
	public static IIcon guiCalendar;
	public static IIcon guiHealth;

	/**
	 * These are defined in BlockBarrel
	 */
	public static IIcon guiSolidStorage;
	public static IIcon guiLiquidStorage;
	public static IIcon guiButtonIn;
	public static IIcon guiButtonOut;

	public static boolean[][] loadClothingPattern(ResourceLocation res)
	{
		boolean[][] alpha = new boolean[98][98];
		InputStream is;
		BufferedImage image;
		try
		{
			is = Minecraft.getMinecraft().getResourceManager().getResource((res)).getInputStream();
			image = ImageIO.read(is);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		int minX = image.getRaster().getMinX();
		int minY = image.getRaster().getMinY();
		int wid = image.getRaster().getWidth();
		int hei = image.getRaster().getHeight();
		int[] texture = new int[image.getWidth() * image.getHeight()*4];
		texture = image.getRaster().getPixels(minX, minY,
				wid,hei, texture);

		for (int i = 0; i < (texture.length/4); i++)
		{
			alpha[i%98][i/98] = texture[3+(i*4)] ==255;
		}
		try
		{
			is.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alpha;
	}
}
