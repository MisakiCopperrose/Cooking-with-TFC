package com.dunk.tfc.Render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Blocks.BlockTileRoof;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;

public class RenderBlocksWithRotation extends RenderBlocks
{
	public static RenderBlocksWithRotation instance = new RenderBlocksWithRotation(null);

	public RenderBlocksWithRotation(RenderBlocks old)
	{
		if (old != null)
		{
			this.blockAccess = old.blockAccess;
		}
		this.renderDiagonal = -1;
	}

	public int renderDiagonal = -1;

	public final int DIAGONAL_DOWN_RIGHT = 12345;
	public final int DIAGONAL_UP_RIGHT = 12346;
	public final int DIAGONAL_UP_LEFT = 12347;
	public final int DIAGONAL_DOWN_LEFT = 12348;

	public final int CENTER_ROOF_NORTH = 128;
	public final int CENTER_ROOF_EAST = 256;
	public final int CENTER_ROOF_SOUTH = 512;
	public final int CENTER_ROOF_WEST = 1024;

	private int[][] mossId = new int[6][2];
	private boolean[][] mossDirection = new boolean[6][2];
	private int[][] mossRotation = new int[6][2];

	private double[][] renderMinXs = new double[6][2];
	private double[][] renderMaxXs = new double[6][2];
	private double[][] renderMinYs = new double[6][2];
	private double[][] renderMaxYs = new double[6][2];
	private double[][] renderMinZs = new double[6][2];
	private double[][] renderMaxZs = new double[6][2];
	
	public double offsetX, offsetY, offsetZ;

	public static double inv16 = 1d / 16d;
	public static double inv32 = 1d / 32d;

	public boolean staticTexture = false;

	public float rotation = 0;// (float) (-45d * (Math.PI / 180d));
	public float rot30 = (float) (30d * (Math.PI / 180d));
	public float rot45 = (float) ((Math.PI / 4));
	public float rot22_5 = (float) ((Math.PI / 8));

	public boolean useTextureBelow = false;

	public float rot1 = (float) (Math.PI / 180d);

	public float yRotation = 0;

	public void update(RenderBlocks old)
	{
		this.blockAccess = old.blockAccess;
	}

	public void renderDiagonalQuad(IIcon texture, double x, double y, double z, double offsetX, double offsetY,
			double offsetZ, double scale, int rotation, float yRot, boolean flipTexture)
	{
		Tessellator tessellator = Tessellator.instance;
		double[] U, V;
		U = new double[4];
		V = new double[4];

		U[0] = texture.getMinU();
		V[0] = texture.getMinV();
		U[1] = texture.getMaxU();
		V[1] = texture.getMinV();
		U[2] = texture.getMaxU();
		V[2] = texture.getMaxV();
		U[3] = texture.getMinU();
		V[3] = texture.getMaxV();

		double originX = -(scale / 2d);
		double originY = 0 + offsetY;
		double originZ = -(scale / 2d);
		Vec3[] vertexPositions = new Vec3[4];

		// vertexPositions = new PositionTextureVertex[16];
		// quadList = new TexturedQuad[4];
		double maxX = (originX + scale);
		double maxZ = (originZ + scale);
		double maxY = (originY + scale * Math.sqrt(2));

		int n = rotation + 2;
		double[] vert0, vert1, vert2, vert3;

		if (!flipTexture)
		{
			vert0 = new double[] { originX, originY, originZ, U[(0 + n) % 4], V[(0 + n) % 4] };
			vert1 = new double[] { maxX, originY, maxZ, U[(1 + n) % 4], V[(1 + n) % 4] };
			vert2 = new double[] { maxX, maxY, maxZ, U[(2 + n) % 4], V[(2 + n) % 4] };
			vert3 = new double[] { originX, maxY, originZ, U[(3 + n) % 4], V[(3 + n) % 4] };
		}
		else
		{
			vert0 = new double[] { originX, originY, originZ, U[(1 + n) % 4], V[(1 + n) % 4] };
			vert1 = new double[] { maxX, originY, maxZ, U[(0 + n) % 4], V[(0 + n) % 4] };
			vert2 = new double[] { maxX, maxY, maxZ, U[(3 + n) % 4], V[(3 + n) % 4] };
			vert3 = new double[] { originX, maxY, originZ, U[(2 + n) % 4], V[(2 + n) % 4] };
		}

		vertexPositions[0] = Vec3.createVectorHelper(vert0[0], vert0[1], vert0[2]);
		vertexPositions[1] = Vec3.createVectorHelper(vert1[0], vert1[1], vert1[2]);
		vertexPositions[2] = Vec3.createVectorHelper(vert2[0], vert2[1], vert2[2]);
		vertexPositions[3] = Vec3.createVectorHelper(vert3[0], vert3[1], vert3[2]);

		for (int i = 0; i < 4; i++)
		{

			vertexPositions[i].rotateAroundY(yRot);
			vertexPositions[i].xCoord += offsetX;
			vertexPositions[i].zCoord += offsetZ;
			vertexPositions[i].rotateAroundX(this.rotation);
			vertexPositions[i].rotateAroundY(this.yRotation);
			vertexPositions[i].xCoord += 0.5;
			vertexPositions[i].zCoord += 0.5;
		}

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			tessellator.setBrightness(brightnessTopLeft);
		}
		tessellator.addVertexWithUV(vertexPositions[0].xCoord + x, vertexPositions[0].yCoord + y,
				vertexPositions[0].zCoord + z, vert0[3], vert0[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			tessellator.setBrightness(brightnessTopRight);
		}
		tessellator.addVertexWithUV(vertexPositions[1].xCoord + x, vertexPositions[1].yCoord + y,
				vertexPositions[1].zCoord + z, vert1[3], vert1[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			tessellator.setBrightness(brightnessBottomRight);
		}
		tessellator.addVertexWithUV(vertexPositions[2].xCoord + x, vertexPositions[2].yCoord + y,
				vertexPositions[2].zCoord + z, vert2[3], vert2[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			tessellator.setBrightness(brightnessBottomLeft);
		}
		tessellator.addVertexWithUV(vertexPositions[3].xCoord + x, vertexPositions[3].yCoord + y,
				vertexPositions[3].zCoord + z, vert3[3], vert3[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			tessellator.setBrightness(brightnessTopLeft);
		}
		tessellator.addVertexWithUV(vertexPositions[3].xCoord + x, vertexPositions[3].yCoord + y,
				vertexPositions[3].zCoord + z, vert3[3], vert3[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			tessellator.setBrightness(brightnessTopRight);
		}
		tessellator.addVertexWithUV(vertexPositions[2].xCoord + x, vertexPositions[2].yCoord + y,
				vertexPositions[2].zCoord + z, vert2[3], vert2[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			tessellator.setBrightness(brightnessBottomRight);
		}
		tessellator.addVertexWithUV(vertexPositions[1].xCoord + x, vertexPositions[1].yCoord + y,
				vertexPositions[1].zCoord + z, vert1[3], vert1[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			tessellator.setBrightness(brightnessBottomLeft);
		}
		tessellator.addVertexWithUV(vertexPositions[0].xCoord + x, vertexPositions[0].yCoord + y,
				vertexPositions[0].zCoord + z, vert0[3], vert0[4]);

		// doQuad(tessellator, x, y, z, vertexPositions[3], vertexPositions[2],
		// vertexPositions[1], vertexPositions[0],
		// vert3, vert2, vert1, vert0, 1.0, true, 0);
	}

	public void renderTeepee(IIcon teepeeTexture, IIcon teepeeDoorTexture, double x, double y, double z, int meta)
	{
		Tessellator tessellator = Tessellator.instance;
double AminU, AminV, AmaxU, AmaxV;
		
		AminU = teepeeTexture.getMinU();
		AminV = teepeeTexture.getMinV();
		AmaxU = teepeeTexture.getMaxU();
		AmaxV = teepeeTexture.getMaxV();
		
		double doorminU, doorminV, doormaxU, doormaxV;
		
		doorminU = teepeeDoorTexture.getMinU();
		doorminV = teepeeDoorTexture.getMinV();
		doormaxU = teepeeDoorTexture.getMaxU();
		doormaxV = teepeeDoorTexture.getMaxV();


		Vec3[] vertexPositions = new Vec3[4];
		Vec3[] spears = new Vec3[4];
		Vec3[] spearBases = new Vec3[4];
		for (int i = 0; i < 4; i++)
		{
			spears[i] = Vec3.createVectorHelper(0, 9, 0);
			spearBases[i] = Vec3.createVectorHelper(0, 0, 0);
		}
		spearBases[0].xCoord = -1.575;
		spearBases[0].zCoord = -1.575;
		spearBases[1].xCoord = -1.575;
		spearBases[1].zCoord = 1.575;
		spearBases[2].xCoord = 1.575;
		spearBases[2].zCoord = 1.575;
		spearBases[3].xCoord = 1.575;
		spearBases[3].zCoord = -1.575;
		for (int i = 0; i < 4; i++)
		{
			
			spears[i].rotateAroundX(rot1 * 25f);	
			spears[i].rotateAroundY(rot45 * ((i*2f)+5));
		}

		for (int i = 0; i < 4; i++)
		{
			Vec3 spearL, spearR, spearBL, spearBR;
			spearL = spears[i];
			spearBL = spearBases[i];
			spearR = spears[(i+1)%4];
			spearBR = spearBases[(i+1)%4];
			
			double[] vert0 ;
			double[] vert1;
			double[] vert2 ;
			double[] vert3;
			
			if(i == (7-meta)%4)
			{
			vert0 = new double[] { spearBL.xCoord  ,0  , spearBL.zCoord, doorminU, doormaxV };
			vert1 = new double[] { spearBR.xCoord , 0 , spearBR.zCoord, doormaxU, doormaxV };
			vert2 = new double[] { spearBR.xCoord + spearR.xCoord, spearR.yCoord, spearBR.zCoord + spearR.zCoord, teepeeDoorTexture.getInterpolatedU(9f), doorminV };
			vert3 = new double[] { spearBL.xCoord + spearL.xCoord , spearL.yCoord, spearBL.zCoord + spearL.zCoord, teepeeDoorTexture.getInterpolatedU(7f), doorminV };
			}
			else
			{
				vert0 = new double[] { spearBL.xCoord  ,0  , spearBL.zCoord, AminU, AmaxV };
				vert1 = new double[] { spearBR.xCoord , 0 , spearBR.zCoord, AmaxU, AmaxV };
				vert2 = new double[] { spearBR.xCoord + spearR.xCoord, spearR.yCoord, spearBR.zCoord + spearR.zCoord, /*AmaxU*/teepeeTexture.getInterpolatedU(9f), AminV };
				vert3 = new double[] { spearBL.xCoord + spearL.xCoord , spearL.yCoord, spearBL.zCoord + spearL.zCoord, /*AminU*/teepeeTexture.getInterpolatedU(7f), AminV };
			}

			vertexPositions[0] = Vec3.createVectorHelper(vert0[0], vert0[1], vert0[2]);
			vertexPositions[1] = Vec3.createVectorHelper(vert1[0], vert1[1], vert1[2]);
			vertexPositions[2] = Vec3.createVectorHelper(vert2[0], vert2[1], vert2[2]);
			vertexPositions[3] = Vec3.createVectorHelper(vert3[0], vert3[1], vert3[2]);

			Tessellator.instance.setColorOpaque(200, 200, 200);
			doQuad(tessellator, x, y , z, vertexPositions[1], vertexPositions[2], vertexPositions[3], vertexPositions[0],
					vert1, vert2, vert3, vert0, 1, true, 0.5);
			Tessellator.instance.setColorOpaque(50, 50, 50);
			
			doQuad(tessellator, x, y, z, vertexPositions[0], vertexPositions[3], vertexPositions[2], vertexPositions[1],
					vert0, vert3, vert2, vert1, 1, true, 0.5);
		}
	}

	public void renderQuad(IIcon leaves, double x, double y, double z, double scale)
	{
		Tessellator tessellator = Tessellator.instance;
		double AminU, AminV, AmaxU, AmaxV;

		AminU = leaves.getMinU();
		AminV = leaves.getMinV();
		AmaxU = leaves.getMaxU();
		AmaxV = leaves.getMaxV();

		double originX = 0d * inv16;
		double originY = 0 * inv16;
		double originZ = 0d * inv16 ;
		Vec3[] vertexPositions = new Vec3[4];

		// vertexPositions = new PositionTextureVertex[16];
		// quadList = new TexturedQuad[4];
		double maxX = (originX + 1);
		double maxZ = (originZ + 2); //why does this need to be 2????

		double[] vert0 = new double[] { originX, originY, originZ, AminU, AminV };
		double[] vert1 = new double[] { maxX, originY, originZ, AmaxU, AminV };
		double[] vert2 = new double[] { maxX, originY, maxZ, AmaxU, AmaxV };
		double[] vert3 = new double[] { originX, originY, maxZ, AminU, AmaxV };

		vertexPositions[0] = Vec3.createVectorHelper(vert0[0], vert0[1], vert0[2]);
		vertexPositions[1] = Vec3.createVectorHelper(vert1[0], vert1[1], vert1[2]);
		vertexPositions[2] = Vec3.createVectorHelper(vert2[0], vert2[1], vert2[2]);
		vertexPositions[3] = Vec3.createVectorHelper(vert3[0], vert3[1], vert3[2]);

		for (int i = 0; i < 4; i++)
		{
			vertexPositions[i].xCoord *= scale;
			vertexPositions[i].yCoord *= scale;
			vertexPositions[i].zCoord *= scale;
		}

		doQuad(tessellator, x, y, z, vertexPositions[0], vertexPositions[3], vertexPositions[2], vertexPositions[1],
				vert0, vert3, vert2, vert1, scale, true, 0);
		doQuad(tessellator, x, y, z, vertexPositions[1], vertexPositions[2], vertexPositions[3], vertexPositions[0],
				vert1, vert2, vert3, vert0, scale, true, 0);
	}

	public void renderUndergrowthStem(Block b, double x, double y, double z, double yRotation, double xRotation, double scale,
			int iconIndex)
	{
		Tessellator tessellator = Tessellator.instance;
		IIcon leaves = b.getIcon(0, iconIndex);
		double AminU, AminV, AmaxU, AmaxV;

		AminU = leaves.getMinU();
		AminV = leaves.getMinV();
		AmaxU = leaves.getMaxU();
		AmaxV = leaves.getMaxV();
		// scale = 1 + (TFC_Time.getTotalTicks()%400)*0.01;
		double originX = -1;
		double originY = 0;// -(4 - (4*scale))* inv16;
		double originZ = -1;
		Vec3[] vertexPositions = new Vec3[8];

		// vertexPositions = new PositionTextureVertex[16];
		// quadList = new TexturedQuad[4];
		double halfWidth = 16d * inv16;
		double maxX = (originX + 2);
		double maxZ = (originZ + 2);
		double maxY = (originY + 2);
		double maxX2 = (originX - halfWidth) * inv16;

		double[] vert0 = new double[] { originX, originY, originZ, AminU, AmaxV };
		double[] vert1 = new double[] { maxX, originY, maxZ, AmaxU, AmaxV };
		double[] vert2 = new double[] { maxX, maxY, maxZ, AmaxU, AminV };
		double[] vert3 = new double[] { originX, maxY, originZ, AminU, AminV };

		double[] vert4 = new double[] { maxX, originY, originZ, AminU, AmaxV };
		double[] vert5 = new double[] { originX, originY, maxZ, AmaxU, AmaxV };
		double[] vert6 = new double[] { originX, maxY, maxZ, AmaxU, AminV };
		double[] vert7 = new double[] { maxX, maxY, originZ, AminU, AminV };

		vertexPositions[0] = Vec3.createVectorHelper(vert0[0], vert0[1], vert0[2]);
		vertexPositions[1] = Vec3.createVectorHelper(vert1[0], vert1[1], vert1[2]);
		vertexPositions[2] = Vec3.createVectorHelper(vert2[0], vert2[1], vert2[2]);
		vertexPositions[3] = Vec3.createVectorHelper(vert3[0], vert3[1], vert3[2]);

		vertexPositions[4] = Vec3.createVectorHelper(vert4[0], vert4[1], vert4[2]);
		vertexPositions[5] = Vec3.createVectorHelper(vert5[0], vert5[1], vert5[2]);
		vertexPositions[6] = Vec3.createVectorHelper(vert6[0], vert6[1], vert6[2]);
		vertexPositions[7] = Vec3.createVectorHelper(vert7[0], vert7[1], vert7[2]);

		for (int i = 0; i < 8; i++)
		{
			vertexPositions[i].rotateAroundX((float) xRotation);
		}
		for (int i = 0; i < 8; i++)
		{
			vertexPositions[i].rotateAroundY((float) yRotation);
		}
		for (int i = 0; i < 8; i++)
		{
			vertexPositions[i].xCoord *= scale / (2d * Math.sqrt(2));
			vertexPositions[i].yCoord += 0.5;
			vertexPositions[i].yCoord *= scale;
			vertexPositions[i].yCoord -= 0.5;
			vertexPositions[i].zCoord *= scale / (2d * Math.sqrt(2));
		}

		doQuad(tessellator, x, y, z, vertexPositions[0], vertexPositions[3], vertexPositions[2], vertexPositions[1],
				vert0, vert3, vert2, vert1, scale, true, 0.5);
		doQuad(tessellator, x, y, z, vertexPositions[1], vertexPositions[2], vertexPositions[3], vertexPositions[0],
				vert1, vert2, vert3, vert0, scale, true, 0.5);

		doQuad(tessellator, x, y, z, vertexPositions[4], vertexPositions[7], vertexPositions[6], vertexPositions[5],
				vert4, vert7, vert6, vert5, scale, true, 0.5);
		doQuad(tessellator, x, y, z, vertexPositions[5], vertexPositions[6], vertexPositions[7], vertexPositions[4],
				vert5, vert6, vert7, vert4, scale, true, 0.5);
	}
	
	public void resetOffset()
	{
		this.offsetX = this.offsetY = this.offsetZ = 0;
	}
	
	public void setOffset(Vec3 v)
	{
		offsetX = v.xCoord;
		offsetY = v.yCoord;
		offsetZ = v.zCoord;
	}

	public void renderLowUndergrowth(double x, double y, double z)
	{
		Random rand = new Random((int) x + ((int) y << 16) + ((int) z << 8));
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorRGBA(200, 200, 200, 255);
		IIcon low = TFCBlocks.lowUndergrowth.getIcon(0, 1);
		IIcon lowTop = TFCBlocks.lowUndergrowth.getIcon(0, 0);

		float heightDelta = ((rand.nextFloat() - 0.5f) * 0.1f);
		drawCrossedSquares(low, x, y - 0.1f + heightDelta, z, 1.0F);
		double height = 0.25 + heightDelta;

		double AminU, AminV, AmaxU, AmaxV;

		Vec3[] vertexPositions = new Vec3[8];

		AminU = lowTop.getMinU();
		AminV = lowTop.getMinV();
		AmaxU = lowTop.getMaxU();
		AmaxV = lowTop.getMaxV();

		double[] vert0 = new double[] { 0, height, 0, AminU, AmaxV };
		double[] vert1 = new double[] { 0, height, 1, AmaxU, AmaxV };
		double[] vert2 = new double[] { 2, height, 1, AmaxU, AminV };
		double[] vert3 = new double[] { 2, height, 0, AminU, AminV };

		double[] vert4 = new double[] { 0, height - 0.02, 0, AminU, AmaxV };
		double[] vert5 = new double[] { 0, height - 0.02, 1, AmaxU, AmaxV };
		double[] vert6 = new double[] { 2, height - 0.02, 1, AmaxU, AminV };
		double[] vert7 = new double[] { 2, height - 0.02, 0, AminU, AminV };

		vertexPositions[0] = Vec3.createVectorHelper(vert0[0], vert0[1], vert0[2]);
		vertexPositions[1] = Vec3.createVectorHelper(vert1[0], vert1[1], vert1[2]);
		vertexPositions[2] = Vec3.createVectorHelper(vert2[0], vert2[1], vert2[2]);
		vertexPositions[3] = Vec3.createVectorHelper(vert3[0], vert3[1], vert3[2]);

		vertexPositions[4] = Vec3.createVectorHelper(vert4[0], vert4[1], vert4[2]);
		vertexPositions[5] = Vec3.createVectorHelper(vert5[0], vert5[1], vert5[2]);
		vertexPositions[6] = Vec3.createVectorHelper(vert6[0], vert6[1], vert6[2]);
		vertexPositions[7] = Vec3.createVectorHelper(vert7[0], vert7[1], vert7[2]);

		doQuad(tessellator, x - 0.5, y, z - 0.5, vertexPositions[0], vertexPositions[3], vertexPositions[2],
				vertexPositions[1], vert0, vert3, vert2, vert1, 1, true, 0.5);
		doQuad(tessellator, x - 0.5, y, z - 0.5, vertexPositions[1], vertexPositions[2], vertexPositions[3],
				vertexPositions[0], vert1, vert2, vert3, vert0, 1, true, 0.5);

		doQuad(tessellator, x - 0.5, y, z - 0.5, vertexPositions[4], vertexPositions[7], vertexPositions[6],
				vertexPositions[5], vert7, vert6, vert5, vert4, 1, true, 0.5);
		doQuad(tessellator, x - 0.5, y, z - 0.5, vertexPositions[5], vertexPositions[6], vertexPositions[7],
				vertexPositions[4], vert6, vert7, vert4, vert5, 1, true, 0.5);
	}

	public void renderUndergrowthLeaves(double x, double y, double z, double offsetX, double offsetY, double offsetZ,
			double localOffsetX, double localOffsetY, double localOffsetZ, double localRotationX, double localRotationY,
			double localRotationZ, double yRotation, double xRotation, double scale)
	{

		int season = TFC_Time.getSeasonAdjustedMonth((int) z);
		int bioTemp = (int) TFC_Climate.getHeightAdjustedBioTemp(Minecraft.getMinecraft().theWorld,
				(int) TFC_Time.getTotalDays(), (int) x, (int) y, (int) z);

		Tessellator tessellator = Tessellator.instance;
		IIcon leaves = (season >= TFC_Time.OCTOBER && bioTemp < 16) ? TFCBlocks.undergrowth.getIcon(0, 2)
				: TFCBlocks.undergrowth.getIcon(0, 1);
		if (season >= TFC_Time.DECEMBER && bioTemp < 16)
		{
			return;
		}
		double AminU, AminV, AmaxU, AmaxV;

		AminU = leaves.getMinU();
		AminV = leaves.getMinV();
		AmaxU = leaves.getMaxU();
		AmaxV = leaves.getMaxV();

		double originX = 0d * inv16;
		double originY = 0 * inv16;
		double originZ = 0d * inv16;
		Vec3[] vertexPositions = new Vec3[4];

		// vertexPositions = new PositionTextureVertex[16];
		// quadList = new TexturedQuad[4];
		double halfWidth = 16d * inv16;
		double maxX = (originX + halfWidth);
		double maxZ = (originZ + halfWidth * 2);

		double[] vert0 = new double[] { originX, originY + 8d * inv16, originZ, AminU, AminV };
		double[] vert1 = new double[] { maxX, originY + 8d * inv16, originZ, AmaxU, AminV };
		double[] vert2 = new double[] { maxX, originY + 8d * inv16, maxZ, AmaxU, AmaxV };
		double[] vert3 = new double[] { originX, originY + 8d * inv16, maxZ, AminU, AmaxV };

		vertexPositions[0] = Vec3.createVectorHelper(vert0[0], vert0[1], vert0[2]);
		vertexPositions[1] = Vec3.createVectorHelper(vert1[0], vert1[1], vert1[2]);
		vertexPositions[2] = Vec3.createVectorHelper(vert2[0], vert2[1], vert2[2]);
		vertexPositions[3] = Vec3.createVectorHelper(vert3[0], vert3[1], vert3[2]);

		for (int i = 0; i < 4; i++)
		{
			vertexPositions[i].xCoord -= 0.5;
			vertexPositions[i].zCoord -= 0.0;
			vertexPositions[i].yCoord -= 0.5;
			vertexPositions[i].rotateAroundX((float) localRotationX);
			vertexPositions[i].rotateAroundY((float) localRotationY);
			vertexPositions[i].rotateAroundZ((float) localRotationZ);
			vertexPositions[i].xCoord += 0.5;
			vertexPositions[i].zCoord += 0.0;
			vertexPositions[i].yCoord += 0.5;
		}
		for (int i = 0; i < 4; i++)
		{
			vertexPositions[i].xCoord *= scale;
			vertexPositions[i].yCoord *= scale;
			vertexPositions[i].zCoord *= scale;
		}
		for (int i = 0; i < 4; i++)
		{
			vertexPositions[i].xCoord += localOffsetX;
			vertexPositions[i].yCoord += localOffsetY;
			vertexPositions[i].zCoord += localOffsetZ;
		}

		for (int i = 0; i < 4; i++)
		{
			vertexPositions[i].rotateAroundX((float) xRotation);
		}
		for (int i = 0; i < 4; i++)
		{
			vertexPositions[i].rotateAroundY((float) yRotation);
		}

		for (int i = 0; i < 4; i++)
		{
			vertexPositions[i].xCoord += offsetX;
			vertexPositions[i].yCoord += offsetY;
			vertexPositions[i].zCoord += offsetZ;
		}

		doQuad(tessellator, x, y, z, vertexPositions[0], vertexPositions[3], vertexPositions[2], vertexPositions[1],
				vert0, vert3, vert2, vert1, scale, true, 0.5);
		doQuad(tessellator, x, y, z, vertexPositions[1], vertexPositions[2], vertexPositions[3], vertexPositions[0],
				vert1, vert2, vert3, vert0, scale, true, 0.5);
	}

	public void renderPalmFrond(double x, double y, double z, double yRotation, double xRotation, double scale,
			double scaleWidth, double droopPercentage, double distalDroopPercentage, boolean banana)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorRGBA(255, 255, 255, 255);
		int off = banana ? 8 : 0;
		int textureOffsetX = 0, textureOffsetY = 0;
		IIcon frondA = TFCBlocks.fauxPalm.getIcon(0, off + 0);
		IIcon frondB = TFCBlocks.fauxPalm.getIcon(0, off + 1);
		IIcon frondAD = TFCBlocks.fauxPalm.getIcon(0, off + 2);
		IIcon frondBD = TFCBlocks.fauxPalm.getIcon(0, off + 3);
		IIcon frondBTip = TFCBlocks.fauxPalm.getIcon(0, off + 4);
		IIcon frondBDTip = TFCBlocks.fauxPalm.getIcon(0, off + 5);
		IIcon frondBase = TFCBlocks.fauxPalm.getIcon(0, off + 6);
		IIcon frondBaseD = TFCBlocks.fauxPalm.getIcon(0, off + 7);
		double AminU, AminV, AmaxU, AmaxV, BminU, BminV, BmaxU, BmaxV, ADminU, ADminV, ADmaxU, ADmaxV, BDminU, BDminV,
				BDmaxU, BDmaxV;
		double bminU, bminV, bmaxU, bmaxV, bDminU, bDminV, bDmaxU, bDmaxV;
		double BTipminU, BTipminV, BTipmaxU, BTipmaxV;
		double BDTipminU, BDTipminV, BDTipmaxU, BDTipmaxV;
		AminU = frondA.getMinU();
		AminV = frondA.getMinV();
		AmaxU = frondA.getMaxU();
		AmaxV = frondA.getMaxV();

		BminU = frondB.getMinU();
		BminV = frondB.getMinV();
		BmaxU = frondB.getMaxU();
		BmaxV = frondB.getMaxV();

		ADminU = frondAD.getMinU();
		ADminV = frondAD.getMinV();
		ADmaxU = frondAD.getMaxU();
		ADmaxV = frondAD.getMaxV();

		BDminU = frondBD.getMinU();
		BDminV = frondBD.getMinV();
		BDmaxU = frondBD.getMaxU();
		BDmaxV = frondBD.getMaxV();

		BTipminU = frondBTip.getMinU();
		BTipminV = frondBTip.getMinV();
		BTipmaxU = frondBTip.getMaxU();
		BTipmaxV = frondBTip.getMaxV();

		BDTipminU = frondBDTip.getMinU();
		BDTipminV = frondBDTip.getMinV();
		BDTipmaxU = frondBDTip.getMaxU();
		BDTipmaxV = frondBDTip.getMaxV();

		bminU = frondBase.getMinU();
		bminV = frondBase.getMinV();
		bmaxU = frondBase.getMaxU();
		bmaxV = frondBase.getMaxV();

		bDminU = frondBaseD.getMinU();
		bDminV = frondBaseD.getMinV();
		bDmaxU = frondBaseD.getMaxU();
		bDmaxV = frondBaseD.getMaxV();

		double originX = 0d;
		double originY = 0.1d + 0.1d * inv16;
		double originZ = 4d * inv16;
		Vec3[] vertexPositions = new Vec3[16];

		// vertexPositions = new PositionTextureVertex[16];
		// quadList = new TexturedQuad[4];

		// double scaleWidth = 0.5;
		double halfWidth = 12d * scaleWidth;
		double maxX = (originX + halfWidth) * inv16;
		double maxX_ = (originX + halfWidth - 4 * scaleWidth) * inv16;
		double maxZ = ((originZ * 16 + 32) / (!banana ? 1.5d : 1)) * inv16;
		double maxX2 = (originX - halfWidth) * inv16;
		double maxX2_ = (originX - halfWidth + 4 * scaleWidth) * inv16;
		droopPercentage *= scaleWidth;

		// YDrop is how much the frond wilts because of the angle. The closer to
		// 0 degrees, the more it wilts. It starts on the second half.
		double yDrop = Math.cos(xRotation) * 0.5d * inv16 * distalDroopPercentage;

		// First part of frond

		/*
		 * double[] vert0 = new double[] { originX, originY + 8d * inv16,
		 * originZ, AminU, AminV }; double[] vert1 = new double[] { maxX - 2d *
		 * inv16, originY + 4d * inv16, originZ, AmaxU, AminV }; double[] vert2
		 * = new double[] { maxX, originY + 6d * inv16, maxZ, AmaxU, AmaxV };
		 * double[] vert3 = new double[] { originX, originY + 12d * inv16, maxZ,
		 * AminU, AmaxV };
		 */

		double[] vert0 = new double[] { originX, originY + 8d * inv16, originZ, bminU, bminV };
		double[] vert1 = new double[] { maxX - 2d * inv16, originY + (4d - (4d * droopPercentage)) * inv16, originZ,
				bmaxU, bminV };
		double[] vert2 = new double[] { maxX, originY + (6d - (4d * droopPercentage)) * inv16, maxZ, bmaxU, bmaxV };
		double[] vert3 = new double[] { originX, originY + 12d * inv16, maxZ, bminU, bmaxV };

		/*
		 * double[] vert0D = new double[] { originX, originY + 8d * inv16,
		 * originZ, ADminU, ADminV }; double[] vert1D = new double[] { maxX - 2d
		 * * inv16, originY + 4d * inv16, originZ, ADmaxU, ADminV }; double[]
		 * vert2D = new double[] { maxX, originY + 6d * inv16, maxZ, ADmaxU,
		 * ADmaxV }; double[] vert3D = new double[] { originX, originY + 12d *
		 * inv16, maxZ, ADminU, ADmaxV };
		 */

		double[] vert0D = new double[] { originX, originY + 8d * inv16, originZ, ADminU, ADminV };
		double[] vert1D = new double[] { maxX - 2d * inv16, originY + (4d - (4d * droopPercentage)) * inv16, originZ,
				ADmaxU, ADminV };
		double[] vert2D = new double[] { maxX, originY + 0d * inv16, maxZ, ADmaxU, ADmaxV };
		double[] vert3D = new double[] { originX, originY + 12d * inv16, maxZ, ADminU, ADmaxV };

		double[] vert0bD = new double[] { originX, originY + 8d * inv16, originZ, bDminU, bDminV };
		double[] vert1bD = new double[] { maxX - 2d * inv16, originY + (4d - (4d * droopPercentage)) * inv16, originZ,
				bDmaxU, bDminV };
		double[] vert2bD = new double[] { maxX, originY + 0d * inv16, maxZ, bDmaxU, bDmaxV };
		double[] vert3bD = new double[] { originX, originY + 12d * inv16, maxZ, bDminU, bDmaxV };

		vertexPositions[0] = Vec3.createVectorHelper(vert0[0], vert0[1], vert0[2]);
		vertexPositions[1] = Vec3.createVectorHelper(vert1[0], vert1[1], vert1[2]);
		vertexPositions[2] = Vec3.createVectorHelper(vert2[0], vert2[1], vert2[2]);
		vertexPositions[3] = Vec3.createVectorHelper(vert3[0], vert3[1], vert3[2]);

		/*
		 * double[] vert02 = new double[] { originX, ((originY + 12d * inv16) +
		 * (originY + 8d * inv16)) * 0.5d, maxZ * 0.5d, BminU, BminV }; double[]
		 * vert12 = new double[] { maxX - 2d * inv16, ((originY + 6d * inv16) +
		 * (originY + 4d * inv16)) * 0.5d, maxZ * 0.5d, BmaxU, BminV }; double[]
		 * vert22 = new double[] { maxX, originY + 6d * inv16, maxZ, BmaxU,
		 * BmaxV }; double[] vert32 = new double[] { originX, originY + 12d *
		 * inv16, maxZ, BminU, BmaxV };
		 * 
		 * double[] vert02D = new double[] { originX, ((originY + 12d * inv16) +
		 * (originY + 8d * inv16)) * 0.5d, maxZ * 0.5d, BDminU, BDminV };
		 * double[] vert12D = new double[] { maxX - 2d * inv16, ((originY + 6d *
		 * inv16) + (originY + 4d * inv16)) * 0.5d, maxZ * 0.5d, BDmaxU, BDminV
		 * }; double[] vert22D = new double[] { maxX, originY + 6d * inv16,
		 * maxZ, BDmaxU, BDmaxV }; double[] vert32D = new double[] { originX,
		 * originY + 12d * inv16, maxZ, BDminU, BDmaxV };
		 */

		double[] vert02 = new double[] { originX, ((originY + 12d * inv16) + (originY + 8d * inv16)) * 0.5d,
				maxZ * 0.5d, BminU, BminV };
		double[] vert12 = new double[] { maxX - 2d * inv16,
				((originY + +(6d - (4d * droopPercentage)) * inv16) + (originY + (4d - (4d * droopPercentage)) * inv16)) * 0.5d,
				maxZ * 0.5d, BmaxU, BminV };
		double[] vert22 = new double[] { maxX, originY + (6d - (4d * droopPercentage)) * inv16, maxZ, BmaxU, BmaxV };
		double[] vert32 = new double[] { originX, originY + 12d * inv16, maxZ, BminU, BmaxV };

		double[] vert02D = new double[] { originX, ((originY + 12d * inv16) + (originY + 8d * inv16)) * 0.5d,
				maxZ * 0.5d, BDminU, BDminV };
		double[] vert12D = new double[] { maxX - 2d * inv16,
				((originY + (6d - (4d * droopPercentage)) * inv16) + (originY + (4d - (4d * droopPercentage)) * inv16)) * 0.5d,
				maxZ * 0.5d, BDmaxU, BDminV };
		double[] vert22D = new double[] { maxX, originY + (6d - (4d * droopPercentage)) * inv16, maxZ, BDmaxU, BDmaxV };
		double[] vert32D = new double[] { originX, originY + 12d * inv16, maxZ, BDminU, BDmaxV };

		double[] vert02TD = new double[] { originX, ((originY + 12d * inv16) + (originY + 8d * inv16)) * 0.5d,
				maxZ * 0.5d, BDTipminU, BDTipminV };
		double[] vert12TD = new double[] { maxX - 2d * inv16,
				((originY + (6d - (4d * droopPercentage)) * inv16) + (originY + (4d - (4d * droopPercentage)) * inv16)) * 0.5d,
				maxZ * 0.5d, BDTipmaxU, BDTipminV };
		double[] vert22TD = new double[] { maxX, originY + (6d - (4d * droopPercentage)) * inv16, maxZ, BDTipmaxU,
				BDTipmaxV };
		double[] vert32TD = new double[] { originX, originY + 12d * inv16, maxZ, BDTipminU, BDTipmaxV };

		// Second part of frond
		/*
		 * double[] vert0b = new double[] { originX, originY + 8d * inv16,
		 * originZ, AminU, AminV }; double[] vert1b = new double[] { maxX2 + 2d
		 * * inv16, originY + 4d * inv16, originZ, AmaxU, AminV }; double[]
		 * vert2b = new double[] { maxX2, originY + 6d * inv16, maxZ, AmaxU,
		 * AmaxV }; double[] vert3b = new double[] { originX, originY + 12d *
		 * inv16, maxZ, AminU, AmaxV };
		 * 
		 * double[] vert02b = new double[] { originX, originY + 8d * inv16,
		 * originZ, BminU, BminV }; double[] vert12b = new double[] { maxX2 + 2d
		 * * inv16, originY + 4d * inv16, originZ, BmaxU, BminV }; double[]
		 * vert22b = new double[] { maxX2, originY + 6d * inv16, maxZ, BmaxU,
		 * BmaxV }; double[] vert32b = new double[] { originX, originY + 12d *
		 * inv16, maxZ, BminU, BmaxV };
		 */

		// Second part of frond
		double[] vert0b = new double[] { originX, originY + 8d * inv16, originZ, bminU, bminV };
		double[] vert1b = new double[] { maxX2 + 2d * inv16, originY + (4d - (4d * droopPercentage)) * inv16, originZ,
				bmaxU, bminV };
		double[] vert2b = new double[] { maxX2, originY + (6d - (4d * droopPercentage)) * inv16, maxZ, bmaxU, bmaxV };
		double[] vert3b = new double[] { originX, originY + 12d * inv16, maxZ, bminU, bmaxV };

		double[] vert02b = new double[] { originX, originY + 8d * inv16, originZ, BminU, BminV };
		double[] vert12b = new double[] { maxX2 + 2d * inv16, originY + (4d - (4d * droopPercentage)) * inv16, originZ,
				BmaxU, BminV };
		double[] vert22b = new double[] { maxX2, originY + (6d - (4d * droopPercentage)) * inv16, maxZ, BmaxU, BmaxV };
		double[] vert32b = new double[] { originX, originY + 12d * inv16, maxZ, BminU, BmaxV };

		vertexPositions[4] = Vec3.createVectorHelper(vert0b[0], vert0b[1], vert0b[2]);
		vertexPositions[5] = Vec3.createVectorHelper(vert1b[0], vert1b[1], vert1b[2]);
		vertexPositions[6] = Vec3.createVectorHelper(vert2b[0], vert2b[1], vert2b[2]);
		vertexPositions[7] = Vec3.createVectorHelper(vert3b[0], vert3b[1], vert3b[2]);

		// Third part of frond
		/*
		 * double[] vert0c = new double[] { originX, originY + 12d * inv16,
		 * maxZ, AminU, AminV }; double[] vert1c = new double[] { maxX, originY
		 * + 6d * inv16, maxZ, AmaxU, AminV }; double[] vert2c = new double[] {
		 * maxX - 6 * inv16, originY + 2d * inv16, maxZ * 2, AmaxU, AmaxV };
		 * double[] vert3c = new double[] { originX, originY + 6d * inv16, maxZ
		 * * 2, AminU, AmaxV };
		 * 
		 * double[] vert02c = new double[] { originX, originY + 12d * inv16,
		 * maxZ, BminU, BminV }; double[] vert12c = new double[] { maxX, originY
		 * + 6d * inv16, maxZ, BmaxU, BminV }; double[] vert22c = new double[] {
		 * maxX - 6 * inv16, originY + 2d * inv16, maxZ * 2, BmaxU, BmaxV };
		 * double[] vert32c = new double[] { originX, originY + 6d * inv16, maxZ
		 * * 2, BminU, BmaxV };
		 */

		double[] vert0c = new double[] { originX, originY + 12d * inv16, maxZ, AminU, AminV };
		double[] vert1c = new double[] { maxX, originY + (6d - (4d * droopPercentage)) * inv16, maxZ, AmaxU, AminV };
		double[] vert2c = new double[] { maxX_, (-yDrop * 32) + originY + (2d - (4d * droopPercentage)) * inv16,
				maxZ * 2, AmaxU, AmaxV };
		double[] vert3c = new double[] { originX, (-yDrop * 32) + originY + 6d * inv16, maxZ * 2, AminU, AmaxV };

		double[] vert02c = new double[] { originX, originY + 12d * inv16, maxZ, BTipminU, BTipminV };
		double[] vert12c = new double[] { maxX, originY + (4d - (4d * droopPercentage)) * inv16, maxZ, BTipmaxU,
				BTipminV };
		double[] vert22c = new double[] { maxX_, (-yDrop * 32) + originY + (2d - (4d * droopPercentage)) * inv16,
				maxZ * 2, BTipmaxU, BTipmaxV };
		double[] vert32c = new double[] { originX, (-yDrop * 32) + originY + 6d * inv16, maxZ * 2, BTipminU, BTipmaxV };

		vertexPositions[8] = Vec3.createVectorHelper(vert0c[0], vert0c[1], vert0c[2]);
		vertexPositions[9] = Vec3.createVectorHelper(vert1c[0], vert1c[1], vert1c[2]);
		vertexPositions[10] = Vec3.createVectorHelper(vert2c[0], vert2c[1], vert2c[2]);
		vertexPositions[11] = Vec3.createVectorHelper(vert3c[0], vert3c[1], vert3c[2]);

		// Fourth part of frond
		/*
		 * double[] vert0d = new double[] { originX, originY + 12d * inv16,
		 * maxZ, AminU, AminV }; double[] vert1d = new double[] { maxX2, originY
		 * + 6d * inv16, maxZ, AmaxU, AminV }; double[] vert2d = new double[] {
		 * maxX2 + 6d * inv16, originY + 2d * inv16, maxZ * 2, AmaxU, AmaxV };
		 * double[] vert3d = new double[] { originX, originY + 6d * inv16, maxZ
		 * * 2, AminU, AmaxV };
		 * 
		 * double[] vert02d = new double[] { originX, originY + 12d * inv16,
		 * maxZ, BminU, BminV }; double[] vert12d = new double[] { maxX2,
		 * originY + 6d * inv16, maxZ, BmaxU, BminV }; double[] vert22d = new
		 * double[] { maxX2 + 6d * inv16, originY + 2d * inv16, maxZ * 2, BmaxU,
		 * BmaxV }; double[] vert32d = new double[] { originX, originY + 6d *
		 * inv16, maxZ * 2, BminU, BmaxV };
		 */

		double[] vert0d = new double[] { originX, originY + 12d * inv16, maxZ, AminU, AminV };
		double[] vert1d = new double[] { maxX2, originY + (6d - (4d * droopPercentage)) * inv16, maxZ, AmaxU, AminV };
		double[] vert2d = new double[] { maxX2_, (-yDrop * 32) + originY + (2d - (4d * droopPercentage)) * inv16,
				maxZ * 2, AmaxU, AmaxV };
		double[] vert3d = new double[] { originX, (-yDrop * 32) + originY + 6d * inv16, maxZ * 2, AminU, AmaxV };

		double[] vert02d = new double[] { originX, originY + 12d * inv16, maxZ, BTipminU, BTipminV };
		double[] vert12d = new double[] { maxX2, originY + (6d - (4d * droopPercentage)) * inv16, maxZ, BTipmaxU,
				BTipminV };
		double[] vert22d = new double[] { maxX2_, (-yDrop * 32) + originY + (2d - (4d * droopPercentage)) * inv16,
				maxZ * 2, BTipmaxU, BTipmaxV };
		double[] vert32d = new double[] { originX, (-yDrop * 32) + originY + 6d * inv16, maxZ * 2, BTipminU, BTipmaxV };

		vertexPositions[12] = Vec3.createVectorHelper(vert0d[0], vert0d[1], vert0d[2]);
		vertexPositions[13] = Vec3.createVectorHelper(vert1d[0], vert1d[1], vert1d[2]);
		vertexPositions[14] = Vec3.createVectorHelper(vert2d[0], vert2d[1], vert2d[2]);
		vertexPositions[15] = Vec3.createVectorHelper(vert3d[0], vert3d[1], vert3d[2]);

		/*
		 * double[] vert0e = new double[] { originX, originY + 12d * inv16,
		 * maxZ, AminU, AminV }; double[] vert1e = new double[] { maxX2, originY
		 * + 2d * inv16, maxZ, AmaxU, AminV }; double[] vert2e = new double[] {
		 * maxX2 + 6d * inv16,(-yDrop * 32) + originY - 2d * inv16, maxZ * 2,
		 * AmaxU, AmaxV }; double[] vert3e = new double[] { originX,(-yDrop *
		 * 32) + originY + 6d * inv16, maxZ * 2, AminU, AmaxV };
		 * 
		 * double[] vert02e = new double[] { originX, originY + 12d * inv16,
		 * maxZ, BminU, BminV }; double[] vert12e = new double[] { maxX2,
		 * originY + 2d * inv16, maxZ, BmaxU, BminV }; double[] vert22e = new
		 * double[] { maxX2 + 6d * inv16,(-yDrop * 32) + originY - 2d * inv16,
		 * maxZ * 2, BmaxU, BmaxV }; double[] vert32e = new double[] { originX,
		 * (-yDrop * 32) +originY + 6d * inv16, maxZ * 2, BminU, BmaxV };
		 */

		for (int i = 0; i < 16; i++)
		{
			vertexPositions[i].rotateAroundX((float) xRotation + this.rotation);
		}
		for (int i = 0; i < 16; i++)
		{
			vertexPositions[i].rotateAroundY((float) yRotation + this.yRotation);
		}
		for (int i = 0; i < 16; i++)
		{
			vertexPositions[i].xCoord *= scale;
			vertexPositions[i].yCoord *= scale;
			vertexPositions[i].zCoord *= scale;
		}

		int x2 = textureOffsetX + 0;
		int x3 = textureOffsetX + 32;

		int y1 = textureOffsetY + 0;
		int y2 = textureOffsetY + 16;
		boolean culled = GL11.glIsEnabled(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_CULL_FACE);

		// 0,3,2,1
		doQuads(tessellator, x, y, z, vertexPositions[0], vertexPositions[3], vertexPositions[2], vertexPositions[1],
				vert0, vert3, vert2, vert1, vert02, vert32, vert22, vert12, scale, true);
		doQuads(tessellator, x, y, z, vertexPositions[1], vertexPositions[2], vertexPositions[3], vertexPositions[0],
				vert1bD, vert2bD, vert3bD, vert0bD, vert12D, vert22D, vert32D, vert02D, scale, true);

		// 7,4,5,6
		doQuads(tessellator, x, y, z, vertexPositions[7], vertexPositions[4], vertexPositions[5], vertexPositions[6],
				vert3b, vert0b, vert1b, vert2b, vert32b, vert02b, vert12b, vert22b, scale, false);
		doQuads(tessellator, x, y, z, vertexPositions[6], vertexPositions[5], vertexPositions[4], vertexPositions[7],
				vert2bD, vert1bD, vert0bD, vert3bD, vert22D, vert12D, vert02D, vert32D, scale, false);

		// 8,11,10,9
		doQuads(tessellator, x, y, z, vertexPositions[8], vertexPositions[11], vertexPositions[10], vertexPositions[9],
				vert0c, vert3c, vert2c, vert1c, vert02c, vert32c, vert22c, vert12c, scale, true);
		doQuads(tessellator, x, y, z, vertexPositions[9], vertexPositions[10], vertexPositions[11], vertexPositions[8],
				vert1D, vert2D, vert3D, vert0D, vert12TD, vert22TD, vert32TD, vert02TD, scale, true);

		// 15,12,13,14
		doQuads(tessellator, x, y, z, vertexPositions[15], vertexPositions[12], vertexPositions[13],
				vertexPositions[14], vert3d, vert0d, vert1d, vert2d, vert32d, vert02d, vert12d, vert22d, scale, false);
		doQuads(tessellator, x, y, z, vertexPositions[14], vertexPositions[13], vertexPositions[12],
				vertexPositions[15], vert2D, vert1D, vert0D, vert3D, vert22TD, vert12TD, vert02TD, vert32TD, scale,
				false);
		/*
		 * quadList[0] = new TexturedQuad(new PositionTextureVertex[]
		 * {vertexPositions[0], vertexPositions[3], vertexPositions[2],
		 * vertexPositions[1]}, x2, y1, x3, y2, 32, 16); // long quadList[1] =
		 * new TexturedQuad(new PositionTextureVertex[] {vertexPositions[7],
		 * vertexPositions[4], vertexPositions[5], vertexPositions[6]}, x2, y1,
		 * x3, y2, 32, 16); // long quadList[2] = new TexturedQuad(new
		 * PositionTextureVertex[] {vertexPositions[8], vertexPositions[11],
		 * vertexPositions[10], vertexPositions[9]}, x2, y1, x3, y2, 32, 16); //
		 * long quadList[3] = new TexturedQuad(new PositionTextureVertex[]
		 * {vertexPositions[15], vertexPositions[12], vertexPositions[13],
		 * vertexPositions[14]}, x2, y1, x3, y2, 32, 16); // long
		 */

		if (culled)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	public void renderDiagonalForRoof(IIcon texture, int x, int y, int z, int rotation)
	{

	}

	@Override
	public boolean renderStandardBlockWithAmbientOcclusionPartial(Block block, int x, int y, int z, float p_147808_5_,
			float p_147808_6_, float p_147808_7_)
	{
		boolean b = true;
		if (!b)
		{
			return super.renderStandardBlockWithAmbientOcclusionPartial(block, x, y, z, p_147808_5_, p_147808_6_,
					p_147808_7_);
		}
		int roofAdjust = 0;
		if (block instanceof BlockTileRoof && y < 254)
		{
			roofAdjust = 1;
		}
		this.enableAO = true;
		boolean flag = false;
		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;
		float f6 = 0.0F;
		boolean flag1 = true;
		int l = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(983055);

		if (this.getBlockIcon(block).getIconName().equals("grass_top"))
		{
			flag1 = false;
		}
		else if (this.hasOverrideBlockTexture())
		{
			flag1 = false;
		}

		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		int i1;
		float f7;

		Vec3[][][] vecs = new Vec3[3][3][3];
		for (int i = -1; i < 2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				for (int k = -1; k < 2; k++)
				{
					if (i != 0 || k != 0 || j != 0)
					{
						vecs[i + 1][j + 1][k + 1] = Vec3.createVectorHelper(i, j + roofAdjust, k);
						vecs[i + 1][j + 1][k + 1].rotateAroundX(rotation);
						vecs[i + 1][j + 1][k + 1].rotateAroundY(yRotation);
						vecs[i + 1][j + 1][k + 1].xCoord = Math.round(vecs[i + 1][j + 1][k + 1].xCoord);
						vecs[i + 1][j + 1][k + 1].yCoord = Math.round(vecs[i + 1][j + 1][k + 1].yCoord);
						vecs[i + 1][j + 1][k + 1].zCoord = Math.round(vecs[i + 1][j + 1][k + 1].zCoord);
					}
				}
			}
		}

		if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, (int) (x + vecs[1][0][1].xCoord),
				(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord), 0))
		{
			if (this.renderMinY <= 0.0D)
			{
				--y;
			}

			this.aoBrightnessXYNN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[0][1][1].xCoord),
					(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord));
			this.aoBrightnessYZNN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][0].xCoord),
					(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord));
			this.aoBrightnessYZNP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][2].xCoord),
					(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord));
			this.aoBrightnessXYPN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[2][1][1].xCoord),
					(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord));
			this.aoLightValueScratchXYNN = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord),
					(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchYZNN = this.blockAccess.getBlock((int) (x + vecs[1][1][0].xCoord),
					(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchYZNP = this.blockAccess.getBlock((int) (x + vecs[1][1][2].xCoord),
					(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXYPN = this.blockAccess.getBlock((int) (x + vecs[2][1][1].xCoord),
					(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord)).getAmbientOcclusionLightValue();
			flag2 = this.blockAccess.getBlock((int) (x + vecs[2][0][1].xCoord), (int) (y + vecs[2][0][1].yCoord),
					(int) (z + vecs[2][0][1].zCoord)).getCanBlockGrass();
			flag3 = this.blockAccess.getBlock((int) (x + vecs[0][0][1].xCoord), (int) (y + vecs[0][0][1].yCoord),
					(int) (z + vecs[0][0][1].zCoord)).getCanBlockGrass();
			flag4 = this.blockAccess.getBlock((int) (x + vecs[1][0][2].xCoord), (int) (y + vecs[1][0][2].yCoord),
					(int) (z + vecs[1][0][2].zCoord)).getCanBlockGrass();
			flag5 = this.blockAccess.getBlock((int) (x + vecs[1][0][0].xCoord), (int) (y + vecs[1][0][0].yCoord),
					(int) (z + vecs[1][0][0].zCoord)).getCanBlockGrass();

			if (!flag5 && !flag3)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock((int) (x + vecs[0][1][0].xCoord),
						(int) (y + vecs[0][1][0].yCoord), (int) (z + vecs[0][1][0].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[0][1][0].xCoord), (int) (y + vecs[0][1][0].yCoord),
						(int) (z + vecs[0][1][0].zCoord));
			}

			if (!flag4 && !flag3)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock((int) (x + vecs[0][1][2].xCoord),
						(int) (y + vecs[0][1][2].yCoord), (int) (z + vecs[0][1][2].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[0][1][2].xCoord), (int) (y + vecs[0][1][2].yCoord),
						(int) (z + vecs[0][1][2].zCoord));
			}

			if (!flag5 && !flag2)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock((int) (x + vecs[2][1][0].xCoord),
						(int) (y + vecs[2][1][0].yCoord), (int) (z + vecs[2][1][0].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[2][1][0].xCoord), (int) (y + vecs[2][1][0].yCoord),
						(int) (z + vecs[2][1][0].zCoord));
			}

			if (!flag4 && !flag2)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock((int) (x + vecs[2][1][2].xCoord),
						(int) (y + vecs[2][1][2].yCoord), (int) (z + vecs[2][1][2].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[2][1][2].xCoord), (int) (y + vecs[2][1][2].yCoord),
						(int) (z + vecs[2][1][2].zCoord));
			}

			if (this.renderMinY <= 0.0D)
			{
				++y;
			}

			i1 = l;

			if (this.renderMinY <= 0.0D || !this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord),
					(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord)).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][0][1].xCoord),
						(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord));
			}

			f7 = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord), (int) (y + vecs[1][0][1].yCoord),
					(int) (z + vecs[1][0][1].zCoord)).getAmbientOcclusionLightValue();
			f3 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + f7) / 4.0F;
			f6 = (this.aoLightValueScratchYZNP + f7 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
			f5 = (f7 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
			f4 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + f7 + this.aoLightValueScratchYZNN) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN,
					this.aoBrightnessYZNP, i1);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP,
					this.aoBrightnessXYPN, i1);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN,
					this.aoBrightnessXYZPNN, i1);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN,
					this.aoBrightnessYZNN, i1);

			if (flag1)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.5F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F;
			}

			this.colorRedTopLeft *= f3;
			this.colorGreenTopLeft *= f3;
			this.colorBlueTopLeft *= f3;
			this.colorRedBottomLeft *= f4;
			this.colorGreenBottomLeft *= f4;
			this.colorBlueBottomLeft *= f4;
			this.colorRedBottomRight *= f5;
			this.colorGreenBottomRight *= f5;
			this.colorBlueBottomRight *= f5;
			this.colorRedTopRight *= f6;
			this.colorGreenTopRight *= f6;
			this.colorBlueTopRight *= f6;
			this.renderFaceYNeg(block, (double) x, (double) y, (double) z,
					this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
			flag = true;
		}

		if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, (int) (x + vecs[1][2][1].xCoord),
				(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord), 1))
		{
			if (this.renderMaxY >= 1.0D)
			{
				++y;
			}

			this.aoBrightnessXYNP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[0][1][1].xCoord),
					(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord));
			this.aoBrightnessXYPP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[2][1][1].xCoord),
					(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord));
			this.aoBrightnessYZPN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][0].xCoord),
					(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord));
			this.aoBrightnessYZPP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][2].xCoord),
					(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord));
			this.aoLightValueScratchXYNP = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord),
					(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXYPP = this.blockAccess.getBlock((int) (x + vecs[2][1][1].xCoord),
					(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchYZPN = this.blockAccess.getBlock((int) (x + vecs[1][1][0].xCoord),
					(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchYZPP = this.blockAccess.getBlock((int) (x + vecs[1][1][2].xCoord),
					(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord)).getAmbientOcclusionLightValue();
			flag2 = this.blockAccess.getBlock((int) (x + vecs[2][2][1].xCoord), (int) (y + vecs[2][2][1].yCoord),
					(int) (z + vecs[2][2][1].zCoord)).getCanBlockGrass();
			flag3 = this.blockAccess.getBlock((int) (x + vecs[0][2][1].xCoord), (int) (y + vecs[0][2][1].yCoord),
					(int) (z + vecs[0][2][1].zCoord)).getCanBlockGrass();
			flag4 = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord), (int) (y + vecs[1][2][1].yCoord),
					(int) (z + vecs[1][2][1].zCoord) + 1).getCanBlockGrass();
			flag5 = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord), (int) (y + vecs[1][2][1].yCoord),
					(int) (z + vecs[1][2][1].zCoord) - 1).getCanBlockGrass();

			if (!flag5 && !flag3)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord),
						(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord) - 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[0][1][1].xCoord), (int) (y + vecs[0][1][1].yCoord),
						(int) (z + vecs[0][1][1].zCoord) - 1);
			}

			if (!flag5 && !flag2)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock((int) (x + vecs[2][1][0].xCoord),
						(int) (y + vecs[2][1][0].yCoord), (int) (z + vecs[2][1][0].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[2][1][0].xCoord), (int) (y + vecs[2][1][0].yCoord),
						(int) (z + vecs[2][1][0].zCoord));
			}

			if (!flag4 && !flag3)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord),
						(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord) + 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[0][1][1].xCoord), (int) (y + vecs[0][1][1].yCoord),
						(int) (z + vecs[0][1][1].zCoord) + 1);
			}

			if (!flag4 && !flag2)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock((int) (x + vecs[2][1][2].xCoord),
						(int) (y + vecs[2][1][2].yCoord), (int) (z + vecs[2][1][2].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[2][1][2].xCoord), (int) (y + vecs[2][1][2].yCoord),
						(int) (z + vecs[2][1][2].zCoord));
			}

			if (this.renderMaxY >= 1.0D)
			{
				--y;
			}

			i1 = l;

			if (this.renderMaxY >= 1.0D || !this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord),
					(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord)).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][2][1].xCoord),
						(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord));
			}

			f7 = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord), (int) (y + vecs[1][2][1].yCoord),
					(int) (z + vecs[1][2][1].zCoord)).getAmbientOcclusionLightValue();
			f6 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + f7) / 4.0F;
			f3 = (this.aoLightValueScratchYZPP + f7 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
			f4 = (f7 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
			f5 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + f7 + this.aoLightValueScratchYZPN) / 4.0F;
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP,
					this.aoBrightnessYZPP, i1);
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP,
					this.aoBrightnessXYPP, i1);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP,
					this.aoBrightnessXYZPPN, i1);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN,
					this.aoBrightnessYZPN, i1);
			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_;
			this.colorRedTopLeft *= f3;
			this.colorGreenTopLeft *= f3;
			this.colorBlueTopLeft *= f3;
			this.colorRedBottomLeft *= f4;
			this.colorGreenBottomLeft *= f4;
			this.colorBlueBottomLeft *= f4;
			this.colorRedBottomRight *= f5;
			this.colorGreenBottomRight *= f5;
			this.colorBlueBottomRight *= f5;
			this.colorRedTopRight *= f6;
			this.colorGreenTopRight *= f6;
			this.colorBlueTopRight *= f6;
			this.renderFaceYPos(block, (double) x, (double) y, (double) z,
					this.getBlockIcon(block, this.blockAccess, x, y, z, 1));
			flag = true;
		}

		float f8;
		float f9;
		float f10;
		float f11;
		int j1;
		int k1;
		int l1;
		int i2;
		IIcon iicon;

		if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, (int) (x + vecs[1][1][0].xCoord),
				(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord), 2))
		{
			if (this.renderMinZ <= 0.0D)
			{
				--z;
			}

			this.aoLightValueScratchXZNN = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord),
					(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchYZNN = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord),
					(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchYZPN = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord),
					(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXZPN = this.blockAccess.getBlock((int) (x + vecs[2][1][1].xCoord),
					(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoBrightnessXZNN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[0][1][1].xCoord),
					(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord));
			this.aoBrightnessYZNN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][0][1].xCoord),
					(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord));
			this.aoBrightnessYZPN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][2][1].xCoord),
					(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord));
			this.aoBrightnessXZPN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[2][1][1].xCoord),
					(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord));
			flag2 = this.blockAccess.getBlock((int) (x + vecs[2][1][0].xCoord), (int) (y + vecs[2][1][0].yCoord),
					(int) (z + vecs[2][1][0].zCoord)).getCanBlockGrass();
			flag3 = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord), (int) (y + vecs[0][1][1].yCoord),
					(int) (z + vecs[0][1][1].zCoord) - 1).getCanBlockGrass();
			flag4 = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord), (int) (y + vecs[1][2][1].yCoord),
					(int) (z + vecs[1][2][1].zCoord) - 1).getCanBlockGrass();
			flag5 = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord), (int) (y + vecs[1][0][1].yCoord),
					(int) (z + vecs[1][0][1].zCoord) - 1).getCanBlockGrass();

			if (!flag3 && !flag5)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock((int) (x + vecs[0][0][1].xCoord),
						(int) (y + vecs[0][0][1].yCoord), (int) (z + vecs[0][0][1].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[0][0][1].xCoord), (int) (y + vecs[0][0][1].yCoord),
						(int) (z + vecs[0][0][1].zCoord));
			}

			if (!flag3 && !flag4)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock((int) (x + vecs[0][2][1].xCoord),
						(int) (y + vecs[0][2][1].yCoord), (int) (z + vecs[0][2][1].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[0][2][1].xCoord), (int) (y + vecs[0][2][1].yCoord),
						(int) (z + vecs[0][2][1].zCoord));
			}

			if (!flag2 && !flag5)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock((int) (x + vecs[2][0][1].xCoord),
						(int) (y + vecs[2][0][1].yCoord), (int) (z + vecs[2][0][1].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[2][0][1].xCoord), (int) (y + vecs[2][0][1].yCoord),
						(int) (z + vecs[2][0][1].zCoord));
			}

			if (!flag2 && !flag4)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock((int) (x + vecs[2][2][1].xCoord),
						(int) (y + vecs[2][2][1].yCoord), (int) (z + vecs[2][2][1].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[2][2][1].xCoord), (int) (y + vecs[2][2][1].yCoord),
						(int) (z + vecs[2][2][1].zCoord));
			}

			if (this.renderMinZ <= 0.0D)
			{
				++z;
			}

			i1 = l;

			if (this.renderMinZ <= 0.0D || !this.blockAccess.getBlock((int) (x + vecs[1][1][0].xCoord),
					(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord)).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][0].xCoord),
						(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord));
			}

			f7 = this.blockAccess.getBlock((int) (x + vecs[1][1][0].xCoord), (int) (y + vecs[1][1][0].yCoord),
					(int) (z + vecs[1][1][0].zCoord)).getAmbientOcclusionLightValue();
			f8 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + f7 + this.aoLightValueScratchYZPN) / 4.0F;
			f9 = (f7 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
			f10 = (this.aoLightValueScratchYZNN + f7 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
			f11 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + f7) / 4.0F;
			f3 = (float) ((double) f8 * this.renderMaxY * (1.0D - this.renderMinX) + (double) f9 * this.renderMaxY * this.renderMinX + (double) f10 * (1.0D - this.renderMaxY) * this.renderMinX + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
			f4 = (float) ((double) f8 * this.renderMaxY * (1.0D - this.renderMaxX) + (double) f9 * this.renderMaxY * this.renderMaxX + (double) f10 * (1.0D - this.renderMaxY) * this.renderMaxX + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
			f5 = (float) ((double) f8 * this.renderMinY * (1.0D - this.renderMaxX) + (double) f9 * this.renderMinY * this.renderMaxX + (double) f10 * (1.0D - this.renderMinY) * this.renderMaxX + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
			f6 = (float) ((double) f8 * this.renderMinY * (1.0D - this.renderMinX) + (double) f9 * this.renderMinY * this.renderMinX + (double) f10 * (1.0D - this.renderMinY) * this.renderMinX + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
			j1 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, i1);
			k1 = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, i1);
			l1 = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, i1);
			i2 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, i1);
			this.brightnessTopLeft = this.mixAoBrightness(j1, k1, l1, i2, this.renderMaxY * (1.0D - this.renderMinX),
					this.renderMaxY * this.renderMinX, (1.0D - this.renderMaxY) * this.renderMinX,
					(1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
			this.brightnessBottomLeft = this.mixAoBrightness(j1, k1, l1, i2, this.renderMaxY * (1.0D - this.renderMaxX),
					this.renderMaxY * this.renderMaxX, (1.0D - this.renderMaxY) * this.renderMaxX,
					(1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
			this.brightnessBottomRight = this.mixAoBrightness(j1, k1, l1, i2,
					this.renderMinY * (1.0D - this.renderMaxX), this.renderMinY * this.renderMaxX,
					(1.0D - this.renderMinY) * this.renderMaxX, (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
			this.brightnessTopRight = this.mixAoBrightness(j1, k1, l1, i2, this.renderMinY * (1.0D - this.renderMinX),
					this.renderMinY * this.renderMinX, (1.0D - this.renderMinY) * this.renderMinX,
					(1.0D - this.renderMinY) * (1.0D - this.renderMinX));

			if (flag1)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= f3;
			this.colorGreenTopLeft *= f3;
			this.colorBlueTopLeft *= f3;
			this.colorRedBottomLeft *= f4;
			this.colorGreenBottomLeft *= f4;
			this.colorBlueBottomLeft *= f4;
			this.colorRedBottomRight *= f5;
			this.colorGreenBottomRight *= f5;
			this.colorBlueBottomRight *= f5;
			this.colorRedTopRight *= f6;
			this.colorGreenTopRight *= f6;
			this.colorBlueTopRight *= f6;
			iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 2);
			this.renderFaceZNeg(block, (double) x, (double) y, (double) z, iicon);

			if (fancyGrass && iicon.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= p_147808_5_;
				this.colorRedBottomLeft *= p_147808_5_;
				this.colorRedBottomRight *= p_147808_5_;
				this.colorRedTopRight *= p_147808_5_;
				this.colorGreenTopLeft *= p_147808_6_;
				this.colorGreenBottomLeft *= p_147808_6_;
				this.colorGreenBottomRight *= p_147808_6_;
				this.colorGreenTopRight *= p_147808_6_;
				this.colorBlueTopLeft *= p_147808_7_;
				this.colorBlueBottomLeft *= p_147808_7_;
				this.colorBlueBottomRight *= p_147808_7_;
				this.colorBlueTopRight *= p_147808_7_;
				this.renderFaceZNeg(block, (double) x, (double) y, (double) z, BlockGrass.getIconSideOverlay());
			}

			flag = true;
		}

		if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, (int) (x + vecs[1][1][2].xCoord),
				(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord), 3))
		{
			if (this.renderMaxZ >= 1.0D)
			{
				++z;
			}

			this.aoLightValueScratchXZNP = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord),
					(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXZPP = this.blockAccess.getBlock((int) (x + vecs[2][1][1].xCoord),
					(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchYZNP = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord),
					(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchYZPP = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord),
					(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoBrightnessXZNP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[0][1][1].xCoord),
					(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord));
			this.aoBrightnessXZPP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[2][1][1].xCoord),
					(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord));
			this.aoBrightnessYZNP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][0][1].xCoord),
					(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord));
			this.aoBrightnessYZPP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][2][1].xCoord),
					(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord));
			flag2 = this.blockAccess.getBlock((int) (x + vecs[2][1][2].xCoord), (int) (y + vecs[2][1][2].yCoord),
					(int) (z + vecs[2][1][2].zCoord)).getCanBlockGrass();
			flag3 = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord), (int) (y + vecs[0][1][1].yCoord),
					(int) (z + vecs[0][1][1].zCoord) + 1).getCanBlockGrass();
			flag4 = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord), (int) (y + vecs[1][2][1].yCoord),
					(int) (z + vecs[1][2][1].zCoord) + 1).getCanBlockGrass();
			flag5 = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord), (int) (y + vecs[1][0][1].yCoord),
					(int) (z + vecs[1][0][1].zCoord) + 1).getCanBlockGrass();

			if (!flag3 && !flag5)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock((int) (x + vecs[0][0][1].xCoord),
						(int) (y + vecs[0][0][1].yCoord), (int) (z + vecs[0][0][1].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[0][0][1].xCoord), (int) (y + vecs[0][0][1].yCoord),
						(int) (z + vecs[0][0][1].zCoord));
			}

			if (!flag3 && !flag4)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock((int) (x + vecs[0][2][1].xCoord),
						(int) (y + vecs[0][2][1].yCoord), (int) (z + vecs[0][2][1].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[0][2][1].xCoord), (int) (y + vecs[0][2][1].yCoord),
						(int) (z + vecs[0][2][1].zCoord));
			}

			if (!flag2 && !flag5)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock((int) (x + vecs[2][0][1].xCoord),
						(int) (y + vecs[2][0][1].yCoord), (int) (z + vecs[2][0][1].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[2][0][1].xCoord), (int) (y + vecs[2][0][1].yCoord),
						(int) (z + vecs[2][0][1].zCoord));
			}

			if (!flag2 && !flag4)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock((int) (x + vecs[2][2][1].xCoord),
						(int) (y + vecs[2][2][1].yCoord), (int) (z + vecs[2][2][1].zCoord))
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[2][2][1].xCoord), (int) (y + vecs[2][2][1].yCoord),
						(int) (z + vecs[2][2][1].zCoord));
			}

			if (this.renderMaxZ >= 1.0D)
			{
				--z;
			}

			i1 = l;

			if (this.renderMaxZ >= 1.0D || !this.blockAccess.getBlock((int) (x + vecs[1][1][2].xCoord),
					(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord)).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][2].xCoord),
						(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord));
			}

			f7 = this.blockAccess.getBlock((int) (x + vecs[1][1][2].xCoord), (int) (y + vecs[1][1][2].yCoord),
					(int) (z + vecs[1][1][2].zCoord)).getAmbientOcclusionLightValue();
			f8 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + f7 + this.aoLightValueScratchYZPP) / 4.0F;
			f9 = (f7 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			f10 = (this.aoLightValueScratchYZNP + f7 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
			f11 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + f7) / 4.0F;
			f3 = (float) ((double) f8 * this.renderMaxY * (1.0D - this.renderMinX) + (double) f9 * this.renderMaxY * this.renderMinX + (double) f10 * (1.0D - this.renderMaxY) * this.renderMinX + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
			f4 = (float) ((double) f8 * this.renderMinY * (1.0D - this.renderMinX) + (double) f9 * this.renderMinY * this.renderMinX + (double) f10 * (1.0D - this.renderMinY) * this.renderMinX + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
			f5 = (float) ((double) f8 * this.renderMinY * (1.0D - this.renderMaxX) + (double) f9 * this.renderMinY * this.renderMaxX + (double) f10 * (1.0D - this.renderMinY) * this.renderMaxX + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
			f6 = (float) ((double) f8 * this.renderMaxY * (1.0D - this.renderMaxX) + (double) f9 * this.renderMaxY * this.renderMaxX + (double) f10 * (1.0D - this.renderMaxY) * this.renderMaxX + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
			j1 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, i1);
			k1 = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, i1);
			l1 = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, i1);
			i2 = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, i1);
			this.brightnessTopLeft = this.mixAoBrightness(j1, i2, l1, k1, this.renderMaxY * (1.0D - this.renderMinX),
					(1.0D - this.renderMaxY) * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * this.renderMinX,
					this.renderMaxY * this.renderMinX);
			this.brightnessBottomLeft = this.mixAoBrightness(j1, i2, l1, k1, this.renderMinY * (1.0D - this.renderMinX),
					(1.0D - this.renderMinY) * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * this.renderMinX,
					this.renderMinY * this.renderMinX);
			this.brightnessBottomRight = this.mixAoBrightness(j1, i2, l1, k1,
					this.renderMinY * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * (1.0D - this.renderMaxX),
					(1.0D - this.renderMinY) * this.renderMaxX, this.renderMinY * this.renderMaxX);
			this.brightnessTopRight = this.mixAoBrightness(j1, i2, l1, k1, this.renderMaxY * (1.0D - this.renderMaxX),
					(1.0D - this.renderMaxY) * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * this.renderMaxX,
					this.renderMaxY * this.renderMaxX);

			if (flag1)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= f3;
			this.colorGreenTopLeft *= f3;
			this.colorBlueTopLeft *= f3;
			this.colorRedBottomLeft *= f4;
			this.colorGreenBottomLeft *= f4;
			this.colorBlueBottomLeft *= f4;
			this.colorRedBottomRight *= f5;
			this.colorGreenBottomRight *= f5;
			this.colorBlueBottomRight *= f5;
			this.colorRedTopRight *= f6;
			this.colorGreenTopRight *= f6;
			this.colorBlueTopRight *= f6;
			iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 3);
			this.renderFaceZPos(block, (double) x, (double) y, (double) z, iicon);

			if (fancyGrass && iicon.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= p_147808_5_;
				this.colorRedBottomLeft *= p_147808_5_;
				this.colorRedBottomRight *= p_147808_5_;
				this.colorRedTopRight *= p_147808_5_;
				this.colorGreenTopLeft *= p_147808_6_;
				this.colorGreenBottomLeft *= p_147808_6_;
				this.colorGreenBottomRight *= p_147808_6_;
				this.colorGreenTopRight *= p_147808_6_;
				this.colorBlueTopLeft *= p_147808_7_;
				this.colorBlueBottomLeft *= p_147808_7_;
				this.colorBlueBottomRight *= p_147808_7_;
				this.colorBlueTopRight *= p_147808_7_;
				this.renderFaceZPos(block, (double) x, (double) y, (double) z, BlockGrass.getIconSideOverlay());
			}

			flag = true;
		}

		if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, (int) (x + vecs[0][1][1].xCoord),
				(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord), 4))
		{
			if (this.renderMinX <= 0.0D)
			{
				--x;
			}

			this.aoLightValueScratchXYNN = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord),
					(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXZNN = this.blockAccess.getBlock((int) (x + vecs[1][1][0].xCoord),
					(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXZNP = this.blockAccess.getBlock((int) (x + vecs[1][1][2].xCoord),
					(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXYNP = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord),
					(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoBrightnessXYNN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][0][1].xCoord),
					(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord));
			this.aoBrightnessXZNN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][0].xCoord),
					(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord));
			this.aoBrightnessXZNP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][2].xCoord),
					(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord));
			this.aoBrightnessXYNP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][2][1].xCoord),
					(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord));
			flag2 = this.blockAccess.getBlock((int) (x + vecs[0][2][1].xCoord), (int) (y + vecs[0][2][1].yCoord),
					(int) (z + vecs[0][2][1].zCoord)).getCanBlockGrass();
			flag3 = this.blockAccess.getBlock((int) (x + vecs[0][0][1].xCoord), (int) (y + vecs[0][0][1].yCoord),
					(int) (z + vecs[0][0][1].zCoord)).getCanBlockGrass();
			flag4 = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord), (int) (y + vecs[0][1][1].yCoord),
					(int) (z + vecs[0][1][1].zCoord) - 1).getCanBlockGrass();
			flag5 = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord), (int) (y + vecs[0][1][1].yCoord),
					(int) (z + vecs[0][1][1].zCoord) + 1).getCanBlockGrass();

			if (!flag4 && !flag3)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord),
						(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord) - 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[1][0][1].xCoord), (int) (y + vecs[1][0][1].yCoord),
						(int) (z + vecs[1][0][1].zCoord) - 1);
			}

			if (!flag5 && !flag3)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord),
						(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord) + 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[1][0][1].xCoord), (int) (y + vecs[1][0][1].yCoord),
						(int) (z + vecs[1][0][1].zCoord) + 1);
			}

			if (!flag4 && !flag2)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord),
						(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord) - 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[1][2][1].xCoord), (int) (y + vecs[1][2][1].yCoord),
						(int) (z + vecs[1][2][1].zCoord) - 1);
			}

			if (!flag5 && !flag2)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord),
						(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord) + 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[1][2][1].xCoord), (int) (y + vecs[1][2][1].yCoord),
						(int) (z + vecs[1][2][1].zCoord) + 1);
			}

			if (this.renderMinX <= 0.0D)
			{
				++x;
			}

			i1 = l;

			if (this.renderMinX <= 0.0D || !this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord),
					(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord)).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[0][1][1].xCoord),
						(int) (y + vecs[0][1][1].yCoord), (int) (z + vecs[0][1][1].zCoord));
			}

			f7 = this.blockAccess.getBlock((int) (x + vecs[0][1][1].xCoord), (int) (y + vecs[0][1][1].yCoord),
					(int) (z + vecs[0][1][1].zCoord)).getAmbientOcclusionLightValue();
			f8 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + f7 + this.aoLightValueScratchXZNP) / 4.0F;
			f9 = (f7 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
			f10 = (this.aoLightValueScratchXZNN + f7 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
			f11 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + f7) / 4.0F;
			f3 = (float) ((double) f9 * this.renderMaxY * this.renderMaxZ + (double) f10 * this.renderMaxY * (1.0D - this.renderMaxZ) + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + (double) f8 * (1.0D - this.renderMaxY) * this.renderMaxZ);
			f4 = (float) ((double) f9 * this.renderMaxY * this.renderMinZ + (double) f10 * this.renderMaxY * (1.0D - this.renderMinZ) + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + (double) f8 * (1.0D - this.renderMaxY) * this.renderMinZ);
			f5 = (float) ((double) f9 * this.renderMinY * this.renderMinZ + (double) f10 * this.renderMinY * (1.0D - this.renderMinZ) + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + (double) f8 * (1.0D - this.renderMinY) * this.renderMinZ);
			f6 = (float) ((double) f9 * this.renderMinY * this.renderMaxZ + (double) f10 * this.renderMinY * (1.0D - this.renderMaxZ) + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + (double) f8 * (1.0D - this.renderMinY) * this.renderMaxZ);
			j1 = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, i1);
			k1 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, i1);
			l1 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, i1);
			i2 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, i1);
			this.brightnessTopLeft = this.mixAoBrightness(k1, l1, i2, j1, this.renderMaxY * this.renderMaxZ,
					this.renderMaxY * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ),
					(1.0D - this.renderMaxY) * this.renderMaxZ);
			this.brightnessBottomLeft = this.mixAoBrightness(k1, l1, i2, j1, this.renderMaxY * this.renderMinZ,
					this.renderMaxY * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ),
					(1.0D - this.renderMaxY) * this.renderMinZ);
			this.brightnessBottomRight = this.mixAoBrightness(k1, l1, i2, j1, this.renderMinY * this.renderMinZ,
					this.renderMinY * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * (1.0D - this.renderMinZ),
					(1.0D - this.renderMinY) * this.renderMinZ);
			this.brightnessTopRight = this.mixAoBrightness(k1, l1, i2, j1, this.renderMinY * this.renderMaxZ,
					this.renderMinY * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ),
					(1.0D - this.renderMinY) * this.renderMaxZ);

			if (flag1)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= f3;
			this.colorGreenTopLeft *= f3;
			this.colorBlueTopLeft *= f3;
			this.colorRedBottomLeft *= f4;
			this.colorGreenBottomLeft *= f4;
			this.colorBlueBottomLeft *= f4;
			this.colorRedBottomRight *= f5;
			this.colorGreenBottomRight *= f5;
			this.colorBlueBottomRight *= f5;
			this.colorRedTopRight *= f6;
			this.colorGreenTopRight *= f6;
			this.colorBlueTopRight *= f6;
			iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 4);
			this.renderFaceXNeg(block, (double) x, (double) y, (double) z, iicon);

			if (fancyGrass && iicon.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= p_147808_5_;
				this.colorRedBottomLeft *= p_147808_5_;
				this.colorRedBottomRight *= p_147808_5_;
				this.colorRedTopRight *= p_147808_5_;
				this.colorGreenTopLeft *= p_147808_6_;
				this.colorGreenBottomLeft *= p_147808_6_;
				this.colorGreenBottomRight *= p_147808_6_;
				this.colorGreenTopRight *= p_147808_6_;
				this.colorBlueTopLeft *= p_147808_7_;
				this.colorBlueBottomLeft *= p_147808_7_;
				this.colorBlueBottomRight *= p_147808_7_;
				this.colorBlueTopRight *= p_147808_7_;
				this.renderFaceXNeg(block, (double) x, (double) y, (double) z, BlockGrass.getIconSideOverlay());
			}

			flag = true;
		}

		if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, (int) (x + vecs[2][1][1].xCoord),
				(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord), 5))
		{
			if (this.renderMaxX >= 1.0D)
			{
				++x;
			}

			this.aoLightValueScratchXYPN = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord),
					(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXZPN = this.blockAccess.getBlock((int) (x + vecs[1][1][0].xCoord),
					(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXZPP = this.blockAccess.getBlock((int) (x + vecs[1][1][2].xCoord),
					(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord)).getAmbientOcclusionLightValue();
			this.aoLightValueScratchXYPP = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord),
					(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord)).getAmbientOcclusionLightValue();
			this.aoBrightnessXYPN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][0][1].xCoord),
					(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord));
			this.aoBrightnessXZPN = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][0].xCoord),
					(int) (y + vecs[1][1][0].yCoord), (int) (z + vecs[1][1][0].zCoord));
			this.aoBrightnessXZPP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][1][2].xCoord),
					(int) (y + vecs[1][1][2].yCoord), (int) (z + vecs[1][1][2].zCoord));
			this.aoBrightnessXYPP = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[1][2][1].xCoord),
					(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord));
			flag2 = this.blockAccess.getBlock((int) (x + vecs[2][2][1].xCoord), (int) (y + vecs[2][2][1].yCoord),
					(int) (z + vecs[2][2][1].zCoord)).getCanBlockGrass();
			flag3 = this.blockAccess.getBlock((int) (x + vecs[2][0][1].xCoord), (int) (y + vecs[2][0][1].yCoord),
					(int) (z + vecs[2][0][1].zCoord)).getCanBlockGrass();
			flag4 = this.blockAccess.getBlock((int) (x + vecs[2][1][2].xCoord), (int) (y + vecs[2][1][2].yCoord),
					(int) (z + vecs[2][1][2].zCoord)).getCanBlockGrass();
			flag5 = this.blockAccess.getBlock((int) (x + vecs[2][1][0].xCoord), (int) (y + vecs[2][1][0].yCoord),
					(int) (z + vecs[2][1][0].zCoord)).getCanBlockGrass();

			if (!flag3 && !flag5)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord),
						(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord) - 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[1][0][1].xCoord), (int) (y + vecs[1][0][1].yCoord),
						(int) (z + vecs[1][0][1].zCoord) - 1);
			}

			if (!flag3 && !flag4)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock((int) (x + vecs[1][0][1].xCoord),
						(int) (y + vecs[1][0][1].yCoord), (int) (z + vecs[1][0][1].zCoord) + 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[1][0][1].xCoord), (int) (y + vecs[1][0][1].yCoord),
						(int) (z + vecs[1][0][1].zCoord) + 1);
			}

			if (!flag2 && !flag5)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord),
						(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord) - 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[1][2][1].xCoord), (int) (y + vecs[1][2][1].yCoord),
						(int) (z + vecs[1][2][1].zCoord) - 1);
			}

			if (!flag2 && !flag4)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock((int) (x + vecs[1][2][1].xCoord),
						(int) (y + vecs[1][2][1].yCoord), (int) (z + vecs[1][2][1].zCoord) + 1)
						.getAmbientOcclusionLightValue();
				this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess,
						(int) (x + vecs[1][2][1].xCoord), (int) (y + vecs[1][2][1].yCoord),
						(int) (z + vecs[1][2][1].zCoord) + 1);
			}

			if (this.renderMaxX >= 1.0D)
			{
				--x;
			}

			i1 = l;

			if (this.renderMaxX >= 1.0D || !this.blockAccess.getBlock((int) (x + vecs[2][1][1].xCoord),
					(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord)).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(this.blockAccess, (int) (x + vecs[2][1][1].xCoord),
						(int) (y + vecs[2][1][1].yCoord), (int) (z + vecs[2][1][1].zCoord));
			}

			f7 = this.blockAccess.getBlock((int) (x + vecs[2][1][1].xCoord), (int) (y + vecs[2][1][1].yCoord),
					(int) (z + vecs[2][1][1].zCoord)).getAmbientOcclusionLightValue();
			f8 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + f7 + this.aoLightValueScratchXZPP) / 4.0F;
			f9 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + f7) / 4.0F;
			f10 = (this.aoLightValueScratchXZPN + f7 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
			f11 = (f7 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			f3 = (float) ((double) f8 * (1.0D - this.renderMinY) * this.renderMaxZ + (double) f9 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + (double) f10 * this.renderMinY * (1.0D - this.renderMaxZ) + (double) f11 * this.renderMinY * this.renderMaxZ);
			f4 = (float) ((double) f8 * (1.0D - this.renderMinY) * this.renderMinZ + (double) f9 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + (double) f10 * this.renderMinY * (1.0D - this.renderMinZ) + (double) f11 * this.renderMinY * this.renderMinZ);
			f5 = (float) ((double) f8 * (1.0D - this.renderMaxY) * this.renderMinZ + (double) f9 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + (double) f10 * this.renderMaxY * (1.0D - this.renderMinZ) + (double) f11 * this.renderMaxY * this.renderMinZ);
			f6 = (float) ((double) f8 * (1.0D - this.renderMaxY) * this.renderMaxZ + (double) f9 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + (double) f10 * this.renderMaxY * (1.0D - this.renderMaxZ) + (double) f11 * this.renderMaxY * this.renderMaxZ);
			j1 = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, i1);
			k1 = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, i1);
			l1 = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, i1);
			i2 = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, i1);
			this.brightnessTopLeft = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - this.renderMinY) * this.renderMaxZ,
					(1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), this.renderMinY * (1.0D - this.renderMaxZ),
					this.renderMinY * this.renderMaxZ);
			this.brightnessBottomLeft = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - this.renderMinY) * this.renderMinZ,
					(1.0D - this.renderMinY) * (1.0D - this.renderMinZ), this.renderMinY * (1.0D - this.renderMinZ),
					this.renderMinY * this.renderMinZ);
			this.brightnessBottomRight = this.mixAoBrightness(j1, i2, l1, k1,
					(1.0D - this.renderMaxY) * this.renderMinZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ),
					this.renderMaxY * (1.0D - this.renderMinZ), this.renderMaxY * this.renderMinZ);
			this.brightnessTopRight = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - this.renderMaxY) * this.renderMaxZ,
					(1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), this.renderMaxY * (1.0D - this.renderMaxZ),
					this.renderMaxY * this.renderMaxZ);

			if (flag1)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= f3;
			this.colorGreenTopLeft *= f3;
			this.colorBlueTopLeft *= f3;
			this.colorRedBottomLeft *= f4;
			this.colorGreenBottomLeft *= f4;
			this.colorBlueBottomLeft *= f4;
			this.colorRedBottomRight *= f5;
			this.colorGreenBottomRight *= f5;
			this.colorBlueBottomRight *= f5;
			this.colorRedTopRight *= f6;
			this.colorGreenTopRight *= f6;
			this.colorBlueTopRight *= f6;
			iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 5);
			this.renderFaceXPos(block, (double) x, (double) y, (double) z, iicon);

			if (fancyGrass && iicon.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= p_147808_5_;
				this.colorRedBottomLeft *= p_147808_5_;
				this.colorRedBottomRight *= p_147808_5_;
				this.colorRedTopRight *= p_147808_5_;
				this.colorGreenTopLeft *= p_147808_6_;
				this.colorGreenBottomLeft *= p_147808_6_;
				this.colorGreenBottomRight *= p_147808_6_;
				this.colorGreenTopRight *= p_147808_6_;
				this.colorBlueTopLeft *= p_147808_7_;
				this.colorBlueBottomLeft *= p_147808_7_;
				this.colorBlueBottomRight *= p_147808_7_;
				this.colorBlueTopRight *= p_147808_7_;
				this.renderFaceXPos(block, (double) x, (double) y, (double) z, BlockGrass.getIconSideOverlay());
			}

			flag = true;
		}

		this.enableAO = false;
		return flag;
	}

	@Override
	public boolean renderStandardBlockWithAmbientOcclusion(Block p_147751_1_, int p_147751_2_, int p_147751_3_,
			int p_147751_4_, float p_147751_5_, float p_147751_6_, float p_147751_7_)
	{
		return super.renderStandardBlockWithAmbientOcclusion(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_,
				p_147751_5_, p_147751_6_, p_147751_7_);
	}

	private void doQuad(Tessellator tessellator, double x, double y, double z, Vec3 a, Vec3 b, Vec3 c, Vec3 d,
			double[] aUV, double[] bUV, double[] cUV, double[] dUV, double scale, boolean inverse, double extra)
	{
		// If inverse, we just do things reversed
		// If inverse, a and d are big, otherwise b and c are big
		// So for example 3012, 0321
		// If our scale is 1, we'd be at the right height. If it's 0.5, we're
		// half too short. At 0.25, it's 0.75 too short. At 2? it would be twice
		// too big. So our thing should be
		// That if we're at 0.5, we go up by 0.5. At 2, we go down by 1.
		double yTranslate = (1d - scale) / 2d;

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			tessellator.setBrightness(brightnessTopLeft);
		}
		tessellator.addVertexWithUV((inverse ? a.xCoord : (a.xCoord + b.xCoord) * 0.5) + extra + x,
				(inverse ? a.yCoord : (a.yCoord + b.yCoord) * 0.5f) + yTranslate + y,
				(inverse ? a.zCoord : (a.zCoord + b.zCoord) * 0.5f) + extra + z, aUV[3], aUV[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			tessellator.setBrightness(brightnessTopRight);
		}
		tessellator.addVertexWithUV((inverse ? (b.xCoord + a.xCoord) * 0.5 : b.xCoord) + extra + x,
				(inverse ? (b.yCoord + a.yCoord) * 0.5f : b.yCoord) + yTranslate + y,
				(inverse ? (b.zCoord + a.zCoord) * 0.5f : b.zCoord) + extra + z, bUV[3], bUV[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			tessellator.setBrightness(brightnessBottomRight);
		}
		tessellator.addVertexWithUV((inverse ? (c.xCoord + d.xCoord) * 0.5 : c.xCoord) + extra + x,
				(inverse ? (c.yCoord + d.yCoord) * 0.5f : c.yCoord) + yTranslate + y,
				(inverse ? (c.zCoord + d.zCoord) * 0.5f : c.zCoord) + extra + z, cUV[3], cUV[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			tessellator.setBrightness(brightnessBottomLeft);
		}
		tessellator.addVertexWithUV((inverse ? d.xCoord : (d.xCoord + c.xCoord) * 0.5) + extra + x,
				(inverse ? d.yCoord : (d.yCoord + c.yCoord) * 0.5f) + yTranslate + y,
				(inverse ? d.zCoord : (d.zCoord + c.zCoord) * 0.5f) + extra + z, dUV[3], dUV[4]);
	}

	// Now, we'll handle the crappy stuff in here
	/**
	 * 
	 * @param tessellator
	 * @param x
	 * @param y
	 * @param z
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param aUV
	 *            the first 3 are the same as a, etc.
	 * @param bUV
	 * @param cUV
	 * @param dUV
	 */
	private void doQuads(Tessellator tessellator, double x, double y, double z, Vec3 a, Vec3 b, Vec3 c, Vec3 d,
			double[] aUV, double[] bUV, double[] cUV, double[] dUV, double[] aUV2, double[] bUV2, double[] cUV2,
			double[] dUV2, double scale, boolean inverse)
	{
		// If inverse, we just do things reversed
		// If inverse, a and d are big, otherwise b and c are big
		// So for example 3012, 0321
		// If our scale is 1, we'd be at the right height. If it's 0.5, we're
		// half too short. At 0.25, it's 0.75 too short. At 2? it would be twice
		// too big. So our thing should be
		// That if we're at 0.5, we go up by 0.5. At 2, we go down by 1.
		double yTranslate = (1d - scale) / 2d;

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			tessellator.setBrightness(brightnessTopLeft);
		}
		tessellator.addVertexWithUV((inverse ? a.xCoord : (a.xCoord + b.xCoord) * 0.5) + 0.5 + x,
				(inverse ? a.yCoord : (a.yCoord + b.yCoord) * 0.5) + yTranslate + y,
				(inverse ? a.zCoord : (a.zCoord + b.zCoord) * 0.5) + 0.5 + z, aUV[3], aUV[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			tessellator.setBrightness(brightnessTopRight);
		}
		tessellator.addVertexWithUV((inverse ? (b.xCoord + a.xCoord) * 0.5 : b.xCoord) + 0.5 + x,
				(inverse ? (b.yCoord + a.yCoord) * 0.5 : b.yCoord) + yTranslate + y,
				(inverse ? (b.zCoord + a.zCoord) * 0.5 : b.zCoord) + 0.5 + z, bUV[3], bUV[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			tessellator.setBrightness(brightnessBottomRight);
		}
		tessellator.addVertexWithUV((inverse ? (c.xCoord + d.xCoord) * 0.5 : c.xCoord) + 0.5 + x,
				(inverse ? (c.yCoord + d.yCoord) * 0.5 : c.yCoord) + yTranslate + y,
				(inverse ? (c.zCoord + d.zCoord) * 0.5 : c.zCoord) + 0.5 + z, cUV[3], cUV[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			tessellator.setBrightness(brightnessBottomLeft);
		}
		tessellator.addVertexWithUV((inverse ? d.xCoord : (d.xCoord + c.xCoord) * 0.5) + 0.5 + x,
				(inverse ? d.yCoord : (d.yCoord + c.yCoord) * 0.5) + yTranslate + y,
				(inverse ? d.zCoord : (d.zCoord + c.zCoord) * 0.5) + 0.5 + z, dUV[3], dUV[4]);

		// The second half
		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			tessellator.setBrightness(brightnessTopLeft);
		}
		tessellator.addVertexWithUV((!inverse ? a.xCoord : (a.xCoord + b.xCoord) * 0.5) + 0.5 + x,
				(!inverse ? a.yCoord : (a.yCoord + b.yCoord) * 0.5) + yTranslate + y,
				(!inverse ? a.zCoord : (a.zCoord + b.zCoord) * 0.5) + 0.5 + z, aUV2[3], aUV2[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			tessellator.setBrightness(brightnessTopRight);
		}
		tessellator.addVertexWithUV((!inverse ? (b.xCoord + a.xCoord) * 0.5 : b.xCoord) + 0.5 + x,
				(!inverse ? (b.yCoord + a.yCoord) * 0.5 : b.yCoord) + yTranslate + y,
				(!inverse ? (b.zCoord + a.zCoord) * 0.5 : b.zCoord) + 0.5 + z, bUV2[3], bUV2[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			tessellator.setBrightness(brightnessBottomRight);
		}
		tessellator.addVertexWithUV((!inverse ? (c.xCoord + d.xCoord) * 0.5 : c.xCoord) + 0.5 + x,
				(!inverse ? (c.yCoord + d.yCoord) * 0.5 : c.yCoord) + yTranslate + y,
				(!inverse ? (c.zCoord + d.zCoord) * 0.5 : c.zCoord) + 0.5 + z, cUV2[3], cUV2[4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			tessellator.setBrightness(brightnessBottomLeft);
		}
		tessellator.addVertexWithUV((!inverse ? d.xCoord : (d.xCoord + c.xCoord) * 0.5) + 0.5 + x,
				(!inverse ? d.yCoord : (d.yCoord + c.yCoord) * 0.5) + yTranslate + y,
				(!inverse ? d.zCoord : (d.zCoord + c.zCoord) * 0.5) + 0.5 + z, dUV2[3], dUV2[4]);
	}

	// Helper method which lets us set up our stuff.
	public void renderMossFace(int mId, int textureRotation, double x, double y, double z, int direction, int n)
	{
		this.mossDirection[direction][n] = true;
		this.mossRotation[direction][n] = textureRotation;
		this.mossId[direction][n] = mId;
		// this.renderStandardBlock(TFCBlocks.moss, (int)x, (int)y, (int)z);
	}

	/**
	 * Renders the moss. Use the block bounds to set up where you want the moss
	 * to be rendered
	 * 
	 * @param mId
	 *            the texture index for the moss
	 */
	private void renderMoss(int mId, int textureRotation, double x, double y, double z, int direction, int n)
	{
		if (mId == -1)
		{
			return;
		}
		IIcon mossTexture = TFCBlocks.moss.getIcon(0, mId);
		Tessellator tessellator = Tessellator.instance;

		double originX = 0d;
		double originY = 0;
		double originZ = 0d;

		boolean flip = textureRotation >= 4;
		textureRotation %= 4;

		double maxX; // = this.renderMaxX - this.renderMinX;
		double maxY;// = this.renderMaxY - this.renderMinY;
		double maxZ;// = this.renderMaxZ - this.renderMinZ;
		originX = this.renderMinXs[direction][n] - 0.5;
		originY = this.renderMinYs[direction][n] - 0.5;
		originZ = this.renderMinZs[direction][n] - 0.5;
		maxX = this.renderMaxXs[direction][n] - 0.5;
		maxY = this.renderMaxYs[direction][n] - 0.5;
		maxZ = this.renderMaxZs[direction][n] - 0.5;

		double[] AU = new double[4], AV = new double[4];

		if (!flip)
		{
			AU[0] = mossTexture.getMinU();
			AU[1] = mossTexture.getMaxU();
			AU[2] = mossTexture.getMaxU();
			AU[3] = mossTexture.getMinU();

			AV[0] = mossTexture.getMinV();
			AV[1] = mossTexture.getMinV();
			AV[2] = mossTexture.getMaxV();
			AV[3] = mossTexture.getMaxV();
		}
		else
		{
			AU[3] = mossTexture.getMinU();
			AU[2] = mossTexture.getMaxU();
			AU[1] = mossTexture.getMaxU();
			AU[0] = mossTexture.getMinU();

			AV[3] = mossTexture.getMinV();
			AV[2] = mossTexture.getMinV();
			AV[1] = mossTexture.getMaxV();
			AV[0] = mossTexture.getMaxV();
		}

		Vec3 v0 = null, v1 = null, v2 = null, v3 = null;

		switch (direction)
		{
		case 0: // --y
		{
			v0 = Vec3.createVectorHelper(originX, originY, originZ);
			v1 = Vec3.createVectorHelper(maxX, originY, originZ);
			v2 = Vec3.createVectorHelper(maxX, originY, maxZ);
			v3 = Vec3.createVectorHelper(originX, originY, maxZ);
			break;
		}
		case 1: // ++y
		{
			v3 = Vec3.createVectorHelper(originX, maxY, originZ);
			v2 = Vec3.createVectorHelper(maxX, maxY, originZ);
			v1 = Vec3.createVectorHelper(maxX, maxY, maxZ);
			v0 = Vec3.createVectorHelper(originX, maxY, maxZ);
			break;
		}
		case 2:// --z
		{
			v0 = Vec3.createVectorHelper(originX, originY, maxZ);
			v1 = Vec3.createVectorHelper(maxX, originY, maxZ);
			v2 = Vec3.createVectorHelper(maxX, maxY, maxZ);
			v3 = Vec3.createVectorHelper(originX, maxY, maxZ);
			break;
		}
		case 3:// ++z
		{
			v3 = Vec3.createVectorHelper(originX, originY, originZ);
			v2 = Vec3.createVectorHelper(maxX, originY, originZ);
			v1 = Vec3.createVectorHelper(maxX, maxY, originZ);
			v0 = Vec3.createVectorHelper(originX, maxY, originZ);
			break;
		}
		case 4:// --x
		{
			v0 = Vec3.createVectorHelper(maxX, originY, originZ);
			v1 = Vec3.createVectorHelper(maxX, maxY, originZ);
			v2 = Vec3.createVectorHelper(maxX, maxY, maxZ);
			v3 = Vec3.createVectorHelper(maxX, originY, maxZ);
			break;
		}
		case 5:// ++x
		{
			v3 = Vec3.createVectorHelper(originX, originY, originZ);
			v2 = Vec3.createVectorHelper(originX, maxY, originZ);
			v1 = Vec3.createVectorHelper(originX, maxY, maxZ);
			v0 = Vec3.createVectorHelper(originX, originY, maxZ);
			break;
		}
		default:
		{
			return;
		}
		}

		if (rotation != 0)
		{
			v0.rotateAroundX(rotation);
			v1.rotateAroundX(rotation);
			v2.rotateAroundX(rotation);
			v3.rotateAroundX(rotation);
		}

		if (yRotation != 0)
		{
			v0.rotateAroundY(yRotation);
			v1.rotateAroundY(yRotation);
			v2.rotateAroundY(yRotation);
			v3.rotateAroundY(yRotation);
		}

		float[] R = new float[4], G = new float[4], B = new float[4];
		int[] br = new int[4];

		R[0] = colorRedTopLeft;
		R[1] = colorRedTopRight;
		R[2] = colorRedBottomRight;
		R[3] = colorRedBottomLeft;

		G[0] = colorGreenTopLeft;
		G[1] = colorGreenTopRight;
		G[2] = colorGreenBottomRight;
		G[3] = colorGreenBottomLeft;

		B[0] = colorBlueTopLeft;
		B[1] = colorBlueTopRight;
		B[2] = colorBlueBottomRight;
		B[3] = colorBlueBottomLeft;

		br[0] = brightnessTopLeft;
		br[1] = brightnessTopRight;
		br[2] = brightnessBottomRight;
		br[3] = brightnessBottomLeft;

		int sideOffset = 0;
		switch (direction)
		{
		case 1:
		{
			sideOffset += 0;
			break;
		}
		case 3:
		{
			sideOffset += 3;
			break;
		}
		case 4:
		{
			sideOffset += 0;
			break;
		}
		case 2:
		{
			sideOffset += 2;
			break;
		}
		case 5:
		{
			sideOffset += 2;
			break;
		}
		}
		// sideOffset = 0;
		if (enableAO)
		{
			tessellator.setColorOpaque_F(R[(0 + sideOffset) % 4], G[(0 + sideOffset) % 4], B[(0 + sideOffset) % 4]);
			tessellator.setBrightness(br[(0 + sideOffset) % 4]);
		}
		tessellator.addVertexWithUV(v0.xCoord + 0.5 + x, v0.yCoord + 0.5 + y, v0.zCoord + 0.5 + z,
				AU[(0 + textureRotation) % 4], AV[(0 + textureRotation) % 4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(R[(1 + sideOffset) % 4], G[(1 + sideOffset) % 4], B[(1 + sideOffset) % 4]);
			tessellator.setBrightness(br[(1 + sideOffset) % 4]);
		}
		tessellator.addVertexWithUV(v1.xCoord + 0.5 + x, v1.yCoord + 0.5 + y, v1.zCoord + 0.5 + z,
				AU[(1 + textureRotation) % 4], AV[(1 + textureRotation) % 4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(R[(2 + sideOffset) % 4], G[(2 + sideOffset) % 4], B[(2 + sideOffset) % 4]);
			tessellator.setBrightness(br[(2 + sideOffset) % 4]);
		}
		tessellator.addVertexWithUV(v2.xCoord + 0.5 + x, v2.yCoord + 0.5 + y, v2.zCoord + 0.5 + z,
				AU[(2 + textureRotation) % 4], AV[(2 + textureRotation) % 4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(R[(3 + sideOffset) % 4], G[(3 + sideOffset) % 4], B[(3 + sideOffset) % 4]);
			tessellator.setBrightness(br[(3 + sideOffset) % 4]);
		}
		tessellator.addVertexWithUV(v3.xCoord + 0.5 + x, v3.yCoord + 0.5 + y, v3.zCoord + 0.5 + z,
				AU[(3 + textureRotation) % 4], AV[(3 + textureRotation) % 4]);

		// textureRotation+=1;
		// Reverse
		if (enableAO)
		{
			tessellator.setColorOpaque_F(R[(0 + sideOffset) % 4], G[(0 + sideOffset) % 4], B[(0 + sideOffset) % 4]);
			tessellator.setBrightness(br[(0 + sideOffset) % 4]);
		}
		tessellator.addVertexWithUV(v3.xCoord + 0.5 + x, v3.yCoord + 0.5 + y, v3.zCoord + 0.5 + z,
				AU[(3 + textureRotation) % 4], AV[(3 + textureRotation) % 4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(R[(1 + sideOffset) % 4], G[(1 + sideOffset) % 4], B[(1 + sideOffset) % 4]);
			tessellator.setBrightness(br[(1 + sideOffset) % 4]);
		}
		tessellator.addVertexWithUV(v2.xCoord + 0.5 + x, v2.yCoord + 0.5 + y, v2.zCoord + 0.5 + z,
				AU[(2 + textureRotation) % 4], AV[(2 + textureRotation) % 4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(R[(2 + sideOffset) % 4], G[(2 + sideOffset) % 4], B[(2 + sideOffset) % 4]);
			tessellator.setBrightness(br[(2 + sideOffset) % 4]);
		}
		tessellator.addVertexWithUV(v1.xCoord + 0.5 + x, v1.yCoord + 0.5 + y, v1.zCoord + 0.5 + z,
				AU[(1 + textureRotation) % 4], AV[(1 + textureRotation) % 4]);

		if (enableAO)
		{
			tessellator.setColorOpaque_F(R[(3 + sideOffset) % 4], G[(3 + sideOffset) % 4], B[(3 + sideOffset) % 4]);
			tessellator.setBrightness(br[(3 + sideOffset) % 4]);
		}
		tessellator.addVertexWithUV(v0.xCoord + 0.5 + x, v0.yCoord + 0.5 + y, v0.zCoord + 0.5 + z,
				AU[(0 + textureRotation) % 4], AV[(0 + textureRotation) % 4]);
	}

	// public void renderFaceYNeg(Block block, double x, double y, double z,
	// IIcon icon)
	// {
	//
	// }

	public void setRenderBounds(double x, double y, double z, double X, double Y, double Z, int side, int n)
	{
		if (side > 5 || side < 0)
		{
			return;
		}
		this.renderMaxXs[side][n] = X;
		this.renderMinXs[side][n] = x;
		this.renderMaxYs[side][n] = Y;
		this.renderMinYs[side][n] = y;
		this.renderMaxZs[side][n] = Z;
		this.renderMinZs[side][n] = z;
	}

	// We use this class to allow us to rotate blocks before we render them.
	// we'll set up a way to store the rotation and then we'll get rid of the
	// rotation after
	// A rotation is around the center of the block
	// So when we have a rotation, all we're doing is

	private Vec3 translateVec(Vec3 v)
	{
		v.xCoord += this.offsetX;
		v.yCoord += this.offsetY;
		v.zCoord += this.offsetZ;
		return v;
	}
	
	/**
	 * Renders the given texture to the bottom face of the block. Args: block,
	 * x, y, z, texture
	 */
	public void renderFaceYNeg(Block block, double x, double y, double z, IIcon icon)
	{
		Tessellator tessellator = Tessellator.instance;

		if (block == TFCBlocks.moss)
		{
			for (int n = 0; n < 2; n++)
			{
				if (this.mossId[0][n] != -1 && this.mossDirection[1][n] && this.mossRotation[0][n] != -1)
				{
					this.renderMoss(this.mossId[0][n], this.mossRotation[0][n], x, y, z, 0, n);
					this.mossDirection[0][n] = false;
					this.mossId[0][n] = this.mossRotation[0][n] = -1;
				}
			}
			return;
		}

		if (block == TFCBlocks.fauxPalm)
		{
			int i, j, j2;
			double L = x % 23 + y % 29 + z % 31;
			for (int n = 0; n < 16; n++)
			{

				renderPalmFrond(x, y, z, L * rot1 + (rot30) + (rot30 * (n / 4) * 0.8) + (3 * rot30 * n),
						((n % 6) - 1) * (rot30 * 0.5d), 1, 1, 1, 1, false);
			}
			return;
		}

		if (this.hasOverrideBlockTexture())
		{
			icon = this.overrideBlockTexture;
		}
		else if (useTextureBelow)
		{
			icon = block.getIcon(0, this.blockAccess.getBlockMetadata((int) x, (int) y - 1, (int) z));
		}

		double d3 = (double) icon.getInterpolatedU(this.renderMinX * 16.0D);
		double d4 = (double) icon.getInterpolatedU(this.renderMaxX * 16.0D);
		double d5 = (double) icon.getInterpolatedV(this.renderMinZ * 16.0D);
		double d6 = (double) icon.getInterpolatedV(this.renderMaxZ * 16.0D);

		if (staticTexture)
		{
			if (this.renderMaxX - this.renderMinX >= 1.0D)
			{
				d3 = (double) icon.getMinU();
				d4 = (double) icon.getMaxU();
			}
			else
			{
				// d3 = (double) icon.getMinU();
				// d4 = (double) icon.getInterpolatedU((this.renderMaxX -
				// this.renderMinX) * 16.0D);
				d3 = (double) icon.getInterpolatedU((0.25D) * 16.0D);
				d4 = (double) icon.getInterpolatedU(0.5D * 16.0D);
			}
			if (this.renderMaxZ - this.renderMinZ >= 1.0D)
			{
				d5 = (double) icon.getMinV();
				d6 = (double) icon.getMaxV();
			}
			else
			{
				d5 = (double) icon.getMinV();
				d6 = (double) icon.getInterpolatedV((this.renderMaxZ - this.renderMinZ) * 16.0D);
			}
		}
		if (!staticTexture)
		{
			if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
			{
				if (this.renderMaxX - this.renderMinX >= 1.0D)
				{
					d3 = (double) icon.getMinU();
					d4 = (double) icon.getMaxU();
				}
				else if (this.renderMaxX > 1.0D)
				{
					d3 = (double) icon.getInterpolatedU((1.0D - (this.renderMaxX - this.renderMinX)) * 16.0D);
					d4 = (double) icon.getMaxU();
				}
				else
				{
					d3 = (double) icon.getMinU();
					d4 = (double) icon.getInterpolatedU((this.renderMaxX - this.renderMinX) * 16.0D);
				}
			}

			if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
			{
				if (this.renderMaxZ - this.renderMinZ >= 1.0D)
				{
					d5 = (double) icon.getMinV();
					d6 = (double) icon.getMaxV();
				}
				else if (this.renderMaxZ > 1.0D)
				{
					d5 = (double) icon.getInterpolatedV((1.0D - (this.renderMaxZ - this.renderMinZ)) * 16.0D);
					d6 = (double) icon.getMaxV();
				}
				else
				{
					d5 = (double) icon.getMinV();
					d6 = (double) icon.getInterpolatedV((this.renderMaxZ - this.renderMinZ) * 16.0D);
				}
			}
		}

		double d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;

		if (this.uvRotateBottom == 2)
		{
			d3 = (double) icon.getInterpolatedU(this.renderMinZ * 16.0D);
			d5 = (double) icon.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
			d4 = (double) icon.getInterpolatedU(this.renderMaxZ * 16.0D);
			d6 = (double) icon.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if (this.uvRotateBottom == 1)
		{
			d3 = (double) icon.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			d5 = (double) icon.getInterpolatedV(this.renderMinX * 16.0D);
			d4 = (double) icon.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			d6 = (double) icon.getInterpolatedV(this.renderMaxX * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if (this.uvRotateBottom == 3)
		{
			d3 = (double) icon.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			d4 = (double) icon.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			d5 = (double) icon.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			d6 = (double) icon.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}

		Vec3 xyz = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMinY - 0.5, this.renderMinZ - 0.5);
		Vec3 Xyz = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMinY - 0.5, this.renderMinZ - 0.5);
		Vec3 xyZ = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMinY - 0.5, this.renderMaxZ - 0.5);
		Vec3 XyZ = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMinY - 0.5, this.renderMaxZ - 0.5);

		xyz = translateVec(xyz);
		Xyz = translateVec(Xyz);
		xyZ = translateVec(xyZ);
		XyZ = translateVec(XyZ);
		
		xyz.rotateAroundX(rotation);
		xyZ.rotateAroundX(rotation);
		Xyz.rotateAroundX(rotation);
		XyZ.rotateAroundX(rotation);

		xyz.rotateAroundY(yRotation);
		xyZ.rotateAroundY(yRotation);
		Xyz.rotateAroundY(yRotation);
		XyZ.rotateAroundY(yRotation);

		// double d11 = x + this.renderMinX;
		// double d12 = x + this.renderMaxX;
		// double d13 = y + this.renderMinY;
		// double d14 = z + this.renderMinZ;
		// double d15 = z + this.renderMaxZ;

		/*
		 * double d11 = x + 0.5 + tempVector.xCoord; double d12 = x + 0.5 +
		 * tempVector2.xCoord; double d13 = y + 0.5 + tempVector.yCoord; double
		 * d14 = z + 0.5 + tempVector.zCoord; double d15 = z + 0.5 +
		 * tempVector2.zCoord;
		 * 
		 * if (this.renderFromInside) { d11 = x + 0.5 + tempVector2.xCoord; d12
		 * = x + 0.5 + tempVector.xCoord; }
		 */

		/*
		 * if (this.enableAO) {
		 * tessellator.setColorOpaque_F(this.colorRedTopLeft,
		 * this.colorGreenTopLeft, this.colorBlueTopLeft);
		 * tessellator.setBrightness(this.brightnessTopLeft);
		 * tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
		 * tessellator.setColorOpaque_F(this.colorRedBottomLeft,
		 * this.colorGreenBottomLeft, this.colorBlueBottomLeft);
		 * tessellator.setBrightness(this.brightnessBottomLeft);
		 * tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
		 * tessellator.setColorOpaque_F(this.colorRedBottomRight,
		 * this.colorGreenBottomRight, this.colorBlueBottomRight);
		 * tessellator.setBrightness(this.brightnessBottomRight);
		 * tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
		 * tessellator.setColorOpaque_F(this.colorRedTopRight,
		 * this.colorGreenTopRight, this.colorBlueTopRight);
		 * tessellator.setBrightness(this.brightnessTopRight);
		 * tessellator.addVertexWithUV(d12, d13, d15, d4, d6); } else {
		 * tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
		 * tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
		 * tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
		 * tessellator.addVertexWithUV(d12, d13, d15, d4, d6); }
		 */
		boolean culled = GL11.glIsEnabled(GL11.GL_CULL_FACE);
		if (this.yRotation != 0 || this.rotation != 0)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
		enableAO = true;
		if (this.enableAO && Minecraft.getMinecraft().gameSettings.ambientOcclusion != 0)
		{
			if (renderDiagonal == DIAGONAL_DOWN_LEFT)
			{
				/*
				 * tessellator.setColorOpaque_F(this.colorRedTopLeft,
				 * this.colorGreenTopLeft, this.colorBlueTopLeft);
				 * tessellator.setBrightness(this.brightnessTopLeft);
				 * tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord :
				 * XyZ.xCoord) + 0.5 + x, xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5
				 * + z, d8, d10); // xyZ
				 */

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5); // xyz

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d7, d9); // Xyz

				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5); // xyz
			}
			else if (renderDiagonal == DIAGONAL_UP_LEFT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ

				/*
				 * tessellator.setColorOpaque_F(this.colorRedBottomLeft,
				 * this.colorGreenBottomLeft, this.colorBlueBottomLeft);
				 * tessellator.setBrightness(this.brightnessBottomLeft);
				 * tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord :
				 * Xyz.xCoord) + 0.5 + x, xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5
				 * + z, d3, d5); // xyz
				 */

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d7, d9); // Xyz

				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
			}
			else if (renderDiagonal == DIAGONAL_DOWN_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5); // xyz

				/*
				 * tessellator.setColorOpaque_F(this.colorRedBottomRight,
				 * this.colorGreenBottomRight, this.colorBlueBottomRight);
				 * tessellator.setBrightness(this.brightnessBottomRight);
				 * tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord :
				 * xyz.xCoord) + 0.5 + x, Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5
				 * + z, d7, d9); // Xyz
				 */

				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
			}
			else if (renderDiagonal == DIAGONAL_UP_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5); // xyz

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d7, d9); // Xyz

				/*
				 * tessellator.setColorOpaque_F(this.colorRedTopRight,
				 * this.colorGreenTopRight, this.colorBlueTopRight);
				 * tessellator.setBrightness(this.brightnessTopRight);
				 * tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord :
				 * xyZ.xCoord) + 0.5 + x, XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5
				 * + z, d4, d6); // XyZ
				 */
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
			}
			else
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5); // xyz
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d7, d9); // Xyz
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ
			}
		}
		else
		{
			if (renderDiagonal == DIAGONAL_DOWN_LEFT)
			{
				/*
				 * tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord :
				 * XyZ.xCoord) + 0.5 + x, xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5
				 * + z, d8, d10);
				 */

				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5);

				tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d7, d9);

				tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6);

				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5);
			}
			else if (renderDiagonal == DIAGONAL_UP_LEFT)
			{
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10);

				/*
				 * tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord :
				 * Xyz.xCoord) + 0.5 + x, xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5
				 * + z, d3, d5);
				 */

				tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d7, d9);

				tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6);

				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10);
			}
			else if (renderDiagonal == DIAGONAL_DOWN_RIGHT)
			{
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10);

				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5);

				/*
				 * tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord :
				 * xyz.xCoord) + 0.5 + x, Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5
				 * + z, d7, d9);
				 */

				tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6);

				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10);
			}
			else if (renderDiagonal == DIAGONAL_UP_RIGHT)
			{
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10);

				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5);

				tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d7, d9);

				/*
				 * tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord :
				 * xyZ.xCoord) + 0.5 + x, XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5
				 * + z, d4, d6);
				 */

				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10);
			}
			else
			{
				tessellator.addVertexWithUV((!renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10);
				tessellator.addVertexWithUV((!renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d3, d5);
				tessellator.addVertexWithUV((!renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d7, d9);
				tessellator.addVertexWithUV((!renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6);
			}
		}
		if ((this.yRotation != 0 || this.rotation != 0) && culled)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	/**
	 * Renders the given texture to the top face of the block. Args: block, x,
	 * y, z, texture
	 */
	public void renderFaceYPos(Block block, double x, double y, double z, IIcon p_147806_8_)
	{
		if (renderDiagonal != -1 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
		{
			return;
		}
		Tessellator tessellator = Tessellator.instance;

		if (block == TFCBlocks.moss)
		{
			for (int n = 0; n < 2; n++)
			{
				if (this.mossId[1][n] != -1 && this.mossDirection[1][n] && this.mossRotation[1][n] != -1)
				{
					this.renderMoss(this.mossId[1][n], this.mossRotation[1][n], x, y, z, 1, n);
					this.mossDirection[1][n] = false;
					this.mossId[1][n] = this.mossRotation[1][n] = -1;
				}
			}
			return;
		}

		if (block == TFCBlocks.fauxPalm)
		{
			return;
		}

		if (this.hasOverrideBlockTexture())
		{
			p_147806_8_ = this.overrideBlockTexture;
		}
		else if (useTextureBelow)
		{
			p_147806_8_ = block.getIcon(1, this.blockAccess.getBlockMetadata((int) x, (int) y - 1, (int) z));
		}

		double d3 = (double) p_147806_8_.getInterpolatedU(this.renderMinX * 16.0D);
		double d4 = (double) p_147806_8_.getInterpolatedU(this.renderMaxX * 16.0D);
		double d5 = (double) p_147806_8_.getInterpolatedV(this.renderMinZ * 16.0D);
		double d6 = (double) p_147806_8_.getInterpolatedV(this.renderMaxZ * 16.0D);

		if (staticTexture)
		{
			if (this.renderMaxX - this.renderMinX >= 1.0D)
			{
				d3 = (double) p_147806_8_.getMinU();
				d4 = (double) p_147806_8_.getMaxU();
			}
			else
			{
				d3 = (double) p_147806_8_.getMinU();
				d4 = (double) p_147806_8_.getInterpolatedU((this.renderMaxX - this.renderMinX) * 16.0D);
			}
			if (this.renderMaxZ - this.renderMinZ >= 1.0D)
			{
				d5 = (double) p_147806_8_.getMinV();
				d6 = (double) p_147806_8_.getMaxV();
			}
			else
			{
				d5 = (double) p_147806_8_.getMinV();
				d6 = (double) p_147806_8_.getInterpolatedV((this.renderMaxZ - this.renderMinZ) * 16.0D);
			}
		}
		if (!staticTexture)
		{
			if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
			{
				if (this.renderMaxX - this.renderMinX >= 1.0D)
				{
					d3 = (double) p_147806_8_.getMinU();
					d4 = (double) p_147806_8_.getMaxU();
				}
				else if (this.renderMaxX > 1.0D)
				{
					d3 = (double) p_147806_8_.getInterpolatedU((1.0D - (this.renderMaxX - this.renderMinX)) * 16.0D);
					d4 = (double) p_147806_8_.getMaxU();
				}
				else
				{
					d3 = (double) p_147806_8_.getMinU();
					d4 = (double) p_147806_8_.getInterpolatedU((this.renderMaxX - this.renderMinX) * 16.0D);
				}
			}

			if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
			{
				if (this.renderMaxZ - this.renderMinZ >= 1.0D)
				{
					d5 = (double) p_147806_8_.getMinV();
					d6 = (double) p_147806_8_.getMaxV();
				}
				else if (this.renderMaxZ > 1.0D)
				{
					d5 = (double) p_147806_8_.getInterpolatedV((1.0D - (this.renderMaxZ - this.renderMinZ)) * 16.0D);
					d6 = (double) p_147806_8_.getMaxV();
				}
				else
				{
					d5 = (double) p_147806_8_.getMinV();
					d6 = (double) p_147806_8_.getInterpolatedV((this.renderMaxZ - this.renderMinZ) * 16.0D);
				}
			}
		}
		/*
		 * if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D) { d5 = (double)
		 * p_147806_8_.getMinV(); d6 = (double) p_147806_8_.getMaxV(); }
		 */

		double d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;

		if (this.uvRotateTop == 1)
		{
			d3 = (double) p_147806_8_.getInterpolatedU(this.renderMinZ * 16.0D);
			d5 = (double) p_147806_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
			d4 = (double) p_147806_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
			d6 = (double) p_147806_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if (this.uvRotateTop == 2)
		{
			d3 = (double) p_147806_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			d5 = (double) p_147806_8_.getInterpolatedV(this.renderMinX * 16.0D);
			d4 = (double) p_147806_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			d6 = (double) p_147806_8_.getInterpolatedV(this.renderMaxX * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if (this.uvRotateTop == 3)
		{
			d3 = (double) p_147806_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			d4 = (double) p_147806_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			d5 = (double) p_147806_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			d6 = (double) p_147806_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}

		Vec3 xYz = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMaxY - 0.5, this.renderMinZ - 0.5);
		Vec3 XYz = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMaxY - 0.5, this.renderMinZ - 0.5);
		Vec3 xYZ = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMaxY - 0.5, this.renderMaxZ - 0.5);
		Vec3 XYZ = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMaxY - 0.5, this.renderMaxZ - 0.5);

		xYz = translateVec(xYz);
		XYz = translateVec(XYz);
		xYZ = translateVec(xYZ);
		XYZ = translateVec(XYZ);
		
		xYz.rotateAroundX(rotation);
		xYZ.rotateAroundX(rotation);
		XYz.rotateAroundX(rotation);
		XYZ.rotateAroundX(rotation);

		xYz.rotateAroundY(yRotation);
		xYZ.rotateAroundY(yRotation);
		XYz.rotateAroundY(yRotation);
		XYZ.rotateAroundY(yRotation);

		/*
		 * double d11 = p_147806_2_ + this.renderMinX; double d12 = p_147806_2_
		 * + this.renderMaxX; double d13 = p_147806_4_ + this.renderMaxY; double
		 * d14 = p_147806_6_ + this.renderMinZ; double d15 = p_147806_6_ +
		 * this.renderMaxZ;
		 * 
		 * if (this.renderFromInside) { d11 = p_147806_2_ + this.renderMaxX; d12
		 * = p_147806_2_ + this.renderMinX; }
		 */
		boolean culled = GL11.glIsEnabled(GL11.GL_CULL_FACE);
		if (this.yRotation != 0 || this.rotation != 0)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
		if (this.enableAO && Minecraft.getMinecraft().gameSettings.ambientOcclusion != 0)
		{
			if (renderDiagonal == DIAGONAL_UP_RIGHT)
			{
				/*
				 * tessellator.setColorOpaque_F(this.colorRedTopLeft,
				 * this.colorGreenTopLeft, this.colorBlueTopLeft);
				 * tessellator.setBrightness(this.brightnessTopLeft);
				 * tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord :
				 * XYZ.xCoord) + 0.5 + x, XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5
				 * + z, d4, d6); // XYZ
				 */

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz

				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz
			}
			else if (renderDiagonal == DIAGONAL_DOWN_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
				/*
				 * tessellator.setColorOpaque_F(this.colorRedBottomLeft,
				 * this.colorGreenBottomLeft, this.colorBlueBottomLeft);
				 * tessellator.setBrightness(this.brightnessBottomLeft);
				 * tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord :
				 * XYz.xCoord) + 0.5 + x, XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5
				 * + z, d7, d9);// XYz
				 */
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
			}
			else if (renderDiagonal == DIAGONAL_UP_LEFT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz
				/*
				 * tessellator.setColorOpaque_F(this.colorRedBottomRight,
				 * this.colorGreenBottomRight, this.colorBlueBottomRight);
				 * tessellator.setBrightness(this.brightnessBottomRight);
				 * tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord :
				 * xYz.xCoord) + 0.5 + x, xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5
				 * + z, d3, d5);// xYz
				 */
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
			}
			else if (renderDiagonal == DIAGONAL_DOWN_LEFT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz

				/*
				 * tessellator.setColorOpaque_F(this.colorRedTopRight,
				 * this.colorGreenTopRight, this.colorBlueTopRight);
				 * tessellator.setBrightness(this.brightnessTopRight);
				 * tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord :
				 * xYZ.xCoord) + 0.5 + x, xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5
				 * + z, d8, d10);// xYZ
				 */
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
			}
			else
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ
			}
		}
		else
		{
			
			if (renderDiagonal == DIAGONAL_UP_RIGHT)
			{


				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz


				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz


				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ

				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz
			}
			else if (renderDiagonal == DIAGONAL_DOWN_RIGHT)
			{

				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ

				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz

				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ

				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
			}
			else if (renderDiagonal == DIAGONAL_UP_LEFT)
			{

				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ

				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz

				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ

				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
			}
			else if (renderDiagonal == DIAGONAL_DOWN_LEFT)
			{

				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ


				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz


				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz


				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
			}
			else
			{

				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ

				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz

				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz

				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ
			}
			/*
			
			if (renderDiagonal == DIAGONAL_UP_RIGHT)
			{


				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz
			}
			else if (renderDiagonal == DIAGONAL_DOWN_LEFT)
			{
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz

			}
			else
			{
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d4, d6); // XYZ
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d7, d9);// XYz
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d3, d5);// xYz
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d8, d10);// xYZ
			}*/
		}
		if ((this.yRotation != 0 || this.rotation != 0) && culled)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	/**
	 * Renders the given texture to the north (z-negative) face of the block.
	 * Args: block, x, y, z, texture
	 */
	public void renderFaceZNeg(Block block, double x, double y, double z, IIcon p_147761_8_)
	{
		Tessellator tessellator = Tessellator.instance;

		if (block == TFCBlocks.moss)
		{
			for (int n = 0; n < 2; n++)
			{
				if (this.mossId[3][n] != -1 && this.mossDirection[3][n] && this.mossRotation[3][n] != -1)
				{
					this.renderMoss(this.mossId[3][n], this.mossRotation[3][n], x, y, z, 3, n);
					this.mossDirection[3][n] = false;
					this.mossId[3][n] = this.mossRotation[3][n] = -1;
				}
			}
			return;
		}

		if (block == TFCBlocks.fauxPalm)
		{
			// renderPalmFrond(x, y, z, Math.PI - rot30, 0, 0.5);
			// renderPalmFrond(x, y, z, Math.PI, 0, 0.5);
			// renderPalmFrond(x, y, z, Math.PI + rot30, 0, 0.5);
			return;
		}

		if (this.hasOverrideBlockTexture())
		{
			p_147761_8_ = this.overrideBlockTexture;
		}
		else if (useTextureBelow)
		{
			p_147761_8_ = block.getIcon(2, this.blockAccess.getBlockMetadata((int) x, (int) y - 1, (int) z));
		}

		double d3 = (double) p_147761_8_.getInterpolatedU(this.renderMinX * 16.0D);
		double d4 = (double) p_147761_8_.getInterpolatedU(this.renderMaxX * 16.0D);
		if (staticTexture)
		{
			d3 = (double) p_147761_8_.getMinU();
			if (this.renderMaxX - this.renderMinX <= 1.0D)
			{
				d4 = (double) p_147761_8_.getInterpolatedU((this.renderMaxX - this.renderMinX) * 16.0D);
			}
			else
			{
				d4 = (double) p_147761_8_.getMaxU();
			}
		}
		if (this.field_152631_f)
		{
			d4 = (double) p_147761_8_.getInterpolatedU((1.0D - this.renderMinX) * 16.0D);
			d3 = (double) p_147761_8_.getInterpolatedU((1.0D - this.renderMaxX) * 16.0D);
		}

		double d5 = (double) p_147761_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double d6 = (double) p_147761_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double d7;

		if (this.flipTexture)
		{
			d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		if (staticTexture)
		{
			if (this.renderMaxX - this.renderMinX >= 1.0D)
			{
				d3 = (double) p_147761_8_.getMinU();
				d4 = (double) p_147761_8_.getMaxU();
			}
			else
			{
				// d3 = (double) p_147761_8_.getMinU();
				// d4 = (double) p_147761_8_.getInterpolatedU((this.renderMaxX -
				// this.renderMinX) * 16.0D);
				d3 = (double) p_147761_8_.getInterpolatedU((1.0D - (this.renderMaxX - this.renderMinX)) * 16.0D);
				d4 = (double) p_147761_8_.getMaxU();
			}
			if (this.renderMaxY - this.renderMinY >= 1.0D)
			{
				d5 = (double) p_147761_8_.getMinV();
				d6 = (double) p_147761_8_.getMaxV();
			}
			else
			{
				d5 = (double) p_147761_8_.getMinV();
				d6 = (double) p_147761_8_.getInterpolatedV((this.renderMaxY - this.renderMinY) * 16.0D);
			}
		}
		else
		{
			if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
			{
				if (this.renderMaxX - this.renderMinX >= 1.0D)
				{
					d3 = (double) p_147761_8_.getMinU();
					d4 = (double) p_147761_8_.getMaxU();
				}
				else if (this.renderMaxX > 1.0D)
				{
					d3 = (double) p_147761_8_.getInterpolatedU((1.0D - (this.renderMaxX - this.renderMinX)) * 16.0D);
					d4 = (double) p_147761_8_.getMaxU();
				}
				else
				{
					d3 = (double) p_147761_8_.getMinU();
					d4 = (double) p_147761_8_.getInterpolatedU((this.renderMaxX - this.renderMinX) * 16.0D);
				}
			}

			if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
			{
				d5 = (double) p_147761_8_.getMinV();
				d6 = (double) p_147761_8_.getMaxV();
			}
		}
		d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;

		if (this.uvRotateEast == 2)
		{
			d3 = (double) p_147761_8_.getInterpolatedU(this.renderMinY * 16.0D);
			d4 = (double) p_147761_8_.getInterpolatedU(this.renderMaxY * 16.0D);
			d5 = (double) p_147761_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
			d6 = (double) p_147761_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if (this.uvRotateEast == 1)
		{
			d3 = (double) p_147761_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			d4 = (double) p_147761_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			d5 = (double) p_147761_8_.getInterpolatedV(this.renderMaxX * 16.0D);
			d6 = (double) p_147761_8_.getInterpolatedV(this.renderMinX * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if (this.uvRotateEast == 3)
		{
			d3 = (double) p_147761_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			d4 = (double) p_147761_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			d5 = (double) p_147761_8_.getInterpolatedV(this.renderMaxY * 16.0D);
			d6 = (double) p_147761_8_.getInterpolatedV(this.renderMinY * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}

		Vec3 xYz = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMaxY - 0.5, this.renderMinZ - 0.5);
		Vec3 XYz = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMaxY - 0.5, this.renderMinZ - 0.5);
		Vec3 Xyz = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMinY - 0.5, this.renderMinZ - 0.5);
		Vec3 xyz = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMinY - 0.5, this.renderMinZ - 0.5);

		xYz = translateVec(xYz);
		XYz = translateVec(XYz);
		Xyz = translateVec(Xyz);
		xyz = translateVec(xyz);
		
		xYz.rotateAroundX(rotation);
		xyz.rotateAroundX(rotation);
		XYz.rotateAroundX(rotation);
		Xyz.rotateAroundX(rotation);

		xYz.rotateAroundY(yRotation);
		xyz.rotateAroundY(yRotation);
		XYz.rotateAroundY(yRotation);
		Xyz.rotateAroundY(yRotation);
		/*
		 * double d11 = x + this.renderMinX; double d12 = x + this.renderMaxX;
		 * double d13 = y + this.renderMinY; double d14 = y + this.renderMaxY;
		 * double d15 = z + this.renderMinZ;
		 * 
		 * if (this.renderFromInside) { d11 = x + this.renderMaxX; d12 = x +
		 * this.renderMinX; }
		 */
		boolean culled = GL11.glIsEnabled(GL11.GL_CULL_FACE);
		if (this.yRotation != 0 || this.rotation != 0)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
		if (this.enableAO && Minecraft.getMinecraft().gameSettings.ambientOcclusion != 0)
		{
			if (renderDiagonal > 0 && (renderDiagonal & CENTER_ROOF_NORTH) == CENTER_ROOF_NORTH && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : xyz.zCoord) + 0.5 + z, d4, d10); // xyz

				tessellator.setColorOpaque_F(
						(this.colorRedTopLeft + this.colorRedBottomLeft + this.colorRedBottomRight + this.colorRedTopRight) / 4f,
						(this.colorGreenTopLeft + this.colorGreenBottomLeft + this.colorGreenBottomRight + this.colorGreenTopRight) / 4f,
						(this.colorBlueTopLeft + this.colorBlueBottomLeft + this.colorBlueBottomRight + this.colorBlueTopRight) / 4f);
				tessellator.setBrightness(
						(this.brightnessTopLeft + this.brightnessBottomLeft + this.brightnessBottomRight + this.brightnessTopRight) / 4);
				tessellator.addVertexWithUV((xYz.xCoord + XYz.xCoord) / 2d + 0.5 + x,
						(XYz.yCoord + Xyz.yCoord) / 2d + 0.5 + y, XYz.zCoord + 0.5 + z, (d3 + d7) / 2d,
						(d9 + d10) / 2d); // XYz

				tessellator.setColorOpaque_F((this.colorRedBottomRight + this.colorRedBottomLeft) / 2f,
						(this.colorGreenBottomRight + this.colorGreenBottomLeft) / 2f,
						(this.colorBlueBottomRight + this.colorBlueBottomLeft) / 2f);
				tessellator.setBrightness((this.brightnessBottomRight + this.brightnessBottomLeft) / 2);
				tessellator.addVertexWithUV((xyz.xCoord + Xyz.xCoord) / 2d + 0.5 + x, Xyz.yCoord + 0.5 + y,
						Xyz.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((xyz.xCoord + Xyz.xCoord) / 2d + 0.5 + x, Xyz.yCoord + 0.5 + y,
						Xyz.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((xyz.xCoord + Xyz.xCoord) / 2d + 0.5 + x, Xyz.yCoord + 0.5 + y,
						Xyz.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((xYz.xCoord + XYz.xCoord) / 2d + 0.5 + x,
						(XYz.yCoord + Xyz.yCoord) / 2d + 0.5 + y, XYz.zCoord + 0.5 + z, (d3 + d7) / 2d,
						(d9 + d10) / 2d); // XYz

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : Xyz.zCoord) + 0.5 + z, d3, d6); // Xyz

				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((xyz.xCoord + Xyz.xCoord) / 2d + 0.5 + x, Xyz.yCoord + 0.5 + y,
						Xyz.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

			}
			else if (renderDiagonal >= 64 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{

			}
			else if (renderDiagonal == DIAGONAL_DOWN_LEFT || renderDiagonal == DIAGONAL_UP_RIGHT || renderDiagonal == -1 || renderDiagonal == 39 || ((renderDiagonal & 3) == 2 && renderDiagonal != 18 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT))
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d7, d9); // xYz
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d3, d5); // XYz
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d8, d10); // Xyz
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d4, d6); // xyz
			}
			else if (((renderDiagonal & 3) == 1 && renderDiagonal != 13 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT) || renderDiagonal == 18)
			{
				/*
				 * tessellator.setColorOpaque_F(this.colorRedTopLeft,
				 * this.colorGreenTopLeft, this.colorBlueTopLeft);
				 * tessellator.setBrightness(this.brightnessTopLeft);
				 * tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord :
				 * xYz.xCoord) + 0.5 + x, xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5
				 * + z, d7, d9); // xYz
				 */
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d3, d5); // XYz
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d8, d10); // Xyz
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d4, d6); // xyz
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d3, d5); // XYz
			}
			else if ((renderDiagonal & 3) == 3 && renderDiagonal != 15 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d7, d9); // xYz
				/*
				 * tessellator.setColorOpaque_F(this.colorRedBottomLeft,
				 * this.colorGreenBottomLeft, this.colorBlueBottomLeft);
				 * tessellator.setBrightness(this.brightnessBottomLeft);
				 * tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord :
				 * XYz.xCoord) + 0.5 + x, XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5
				 * + z, d3, d5); // XYz
				 */
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d8, d10); // Xyz
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d4, d6); // xyz
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d7, d9); // xYz
			}
		}
		else
		{
			if (renderDiagonal > 0 && (renderDiagonal & CENTER_ROOF_NORTH) == CENTER_ROOF_NORTH && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{

				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : xyz.zCoord) + 0.5 + z, d4, d10); // xyz

				tessellator.addVertexWithUV((xYz.xCoord + XYz.xCoord) / 2d + 0.5 + x,
						(XYz.yCoord + Xyz.yCoord) / 2d + 0.5 + y, XYz.zCoord + 0.5 + z, (d3 + d7) / 2d,
						(d9 + d10) / 2d); // XYz

				tessellator.addVertexWithUV((xyz.xCoord + Xyz.xCoord) / 2d + 0.5 + x, Xyz.yCoord + 0.5 + y,
						Xyz.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				tessellator.addVertexWithUV((xyz.xCoord + Xyz.xCoord) / 2d + 0.5 + x, Xyz.yCoord + 0.5 + y,
						Xyz.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				tessellator.addVertexWithUV((xyz.xCoord + Xyz.xCoord) / 2d + 0.5 + x, Xyz.yCoord + 0.5 + y,
						Xyz.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				tessellator.addVertexWithUV((xYz.xCoord + XYz.xCoord) / 2d + 0.5 + x,
						(XYz.yCoord + Xyz.yCoord) / 2d + 0.5 + y, XYz.zCoord + 0.5 + z, (d3 + d7) / 2d,
						(d9 + d10) / 2d); // XYz

				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : Xyz.zCoord) + 0.5 + z, d3, d6); // Xyz

				tessellator.addVertexWithUV((xyz.xCoord + Xyz.xCoord) / 2d + 0.5 + x, Xyz.yCoord + 0.5 + y,
						Xyz.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

			}
			else if (renderDiagonal >= 64 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{

			}
			else if (renderDiagonal == DIAGONAL_DOWN_LEFT || renderDiagonal == DIAGONAL_UP_RIGHT || renderDiagonal == -1 || renderDiagonal == 39 || ((renderDiagonal & 3) == 2 && renderDiagonal != 18 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT))
			{
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d7, d9); // xYz
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d3, d5); // XYz
				tessellator.addVertexWithUV((renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d8, d10); // Xyz
				tessellator.addVertexWithUV((renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d4, d6); // xyz
			}
			else if (((renderDiagonal & 3) == 1 && renderDiagonal != 13) && renderDiagonal != 15 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				/*
				 * tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord :
				 * xYz.xCoord) + 0.5 + x, xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5
				 * + z, d7, d9); // xYz
				 */
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d3, d5); // XYz
				tessellator.addVertexWithUV((renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d8, d10); // Xyz
				tessellator.addVertexWithUV((renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d4, d6); // xyz
				tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord : XYz.xCoord) + 0.5 + x,
						XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5 + z, d3, d5); // XYz
			}
			else if ((renderDiagonal & 3) == 3 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d7, d9); // xYz
				/*
				 * tessellator.addVertexWithUV((renderFromInside ? xYz.xCoord :
				 * XYz.xCoord) + 0.5 + x, XYz.yCoord + 0.5 + y, XYz.zCoord + 0.5
				 * + z, d3, d5); // XYz
				 */
				tessellator.addVertexWithUV((renderFromInside ? xyz.xCoord : Xyz.xCoord) + 0.5 + x,
						Xyz.yCoord + 0.5 + y, Xyz.zCoord + 0.5 + z, d8, d10); // Xyz
				tessellator.addVertexWithUV((renderFromInside ? Xyz.xCoord : xyz.xCoord) + 0.5 + x,
						xyz.yCoord + 0.5 + y, xyz.zCoord + 0.5 + z, d4, d6); // xyz
				tessellator.addVertexWithUV((renderFromInside ? XYz.xCoord : xYz.xCoord) + 0.5 + x,
						xYz.yCoord + 0.5 + y, xYz.zCoord + 0.5 + z, d7, d9); // xYz
			}
		}
		if ((this.yRotation != 0 || this.rotation != 0) && culled)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	/**
	 * Renders the given texture to the south (z-positive) face of the block.
	 * Args: block, x, y, z, texture
	 */
	public void renderFaceZPos(Block block, double x, double y, double z, IIcon p_147734_8_)
	{
		Tessellator tessellator = Tessellator.instance;

		if (block == TFCBlocks.moss)
		{
			int dir = 2;
			for (int n = 0; n < 2; n++)
			{
				if (this.mossId[dir][n] != -1 && this.mossDirection[dir][n] && this.mossRotation[dir][n] != -1)
				{
					this.renderMoss(this.mossId[dir][n], this.mossRotation[dir][n], x, y, z, dir, n);
					this.mossDirection[dir][n] = false;
					this.mossId[dir][n] = this.mossRotation[dir][n] = -1;
				}
			}
			return;
		}

		if (block == TFCBlocks.fauxPalm)
		{
			// renderPalmFrond(x, y, z, -rot30, 0, 0.5);
			// renderPalmFrond(x, y, z, 0, 0, 0.5);
			// renderPalmFrond(x, y, z, rot30, 0, 0.5);
			return;
		}

		if (this.hasOverrideBlockTexture())
		{
			p_147734_8_ = this.overrideBlockTexture;
		}
		else if (useTextureBelow)
		{
			p_147734_8_ = block.getIcon(3, this.blockAccess.getBlockMetadata((int) x, (int) y - 1, (int) z));
		}

		double d3 = (double) p_147734_8_.getInterpolatedU(this.renderMinX * 16.0D);
		double d4 = (double) p_147734_8_.getInterpolatedU(this.renderMaxX * 16.0D);
		if (staticTexture)
		{
			d3 = (double) p_147734_8_.getMinU();
			if (this.renderMaxX - this.renderMinX <= 1.0D)
			{
				d4 = (double) p_147734_8_.getInterpolatedU((this.renderMaxX - this.renderMinX) * 16.0D);
			}
			else
			{
				d4 = (double) p_147734_8_.getMaxU();
			}
		}

		double d5 = (double) p_147734_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double d6 = (double) p_147734_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double d7;

		if (this.flipTexture)
		{
			d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		if (staticTexture)
		{
			if (this.renderMaxX - this.renderMinX >= 1.0D)
			{
				d3 = (double) p_147734_8_.getMinU();
				d4 = (double) p_147734_8_.getMaxU();
			}
			else
			{
				// d3 = (double) p_147734_8_.getMinU();
				// d4 = (double) p_147734_8_.getInterpolatedU((this.renderMaxX -
				// this.renderMinX) * 16.0D);
				d3 = (double) p_147734_8_.getInterpolatedU((1.0D - (this.renderMaxX - this.renderMinX)) * 16.0D);
				d4 = (double) p_147734_8_.getMaxU();
			}
			if (this.renderMaxY - this.renderMinY >= 1.0D)
			{
				d5 = (double) p_147734_8_.getMinV();
				d6 = (double) p_147734_8_.getMaxV();
			}
			else
			{
				d5 = (double) p_147734_8_.getMinV();
				d6 = (double) p_147734_8_.getInterpolatedV((this.renderMaxY - this.renderMinY) * 16.0D);
			}
		}
		else
		{
			if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
			{

				if (this.renderMaxX - this.renderMinX >= 1.0D)
				{
					d3 = (double) p_147734_8_.getMinU();
					d4 = (double) p_147734_8_.getMaxU();
				}
				else if (this.renderMaxX > 1.0D)
				{
					d3 = (double) p_147734_8_.getInterpolatedU((1.0D - (this.renderMaxX - this.renderMinX)) * 16.0D);
					d4 = (double) p_147734_8_.getMaxU();
				}
				else
				{
					d3 = (double) p_147734_8_.getMinU();
					d4 = (double) p_147734_8_.getInterpolatedU((this.renderMaxX - this.renderMinX) * 16.0D);
				}
			}

			if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
			{
				d5 = (double) p_147734_8_.getMinV();
				d6 = (double) p_147734_8_.getMaxV();
			}
		}
		d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;

		if (this.uvRotateWest == 1)
		{
			d3 = (double) p_147734_8_.getInterpolatedU(this.renderMinY * 16.0D);
			d6 = (double) p_147734_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
			d4 = (double) p_147734_8_.getInterpolatedU(this.renderMaxY * 16.0D);
			d5 = (double) p_147734_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if (this.uvRotateWest == 2)
		{
			d3 = (double) p_147734_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			d5 = (double) p_147734_8_.getInterpolatedV(this.renderMinX * 16.0D);
			d4 = (double) p_147734_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			d6 = (double) p_147734_8_.getInterpolatedV(this.renderMaxX * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if (this.uvRotateWest == 3)
		{
			d3 = (double) p_147734_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			d4 = (double) p_147734_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			d5 = (double) p_147734_8_.getInterpolatedV(this.renderMaxY * 16.0D);
			d6 = (double) p_147734_8_.getInterpolatedV(this.renderMinY * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}

		Vec3 xYZ = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMaxY - 0.5, this.renderMaxZ - 0.5);
		Vec3 XYZ = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMaxY - 0.5, this.renderMaxZ - 0.5);
		Vec3 XyZ = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMinY - 0.5, this.renderMaxZ - 0.5);
		Vec3 xyZ = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMinY - 0.5, this.renderMaxZ - 0.5);

		xYZ = translateVec(xYZ);
		XYZ = translateVec(XYZ);
		XyZ = translateVec(XyZ);
		xyZ = translateVec(xyZ);
		
		xYZ.rotateAroundX(rotation);
		xyZ.rotateAroundX(rotation);
		XYZ.rotateAroundX(rotation);
		XyZ.rotateAroundX(rotation);

		xYZ.rotateAroundY(yRotation);
		xyZ.rotateAroundY(yRotation);
		XYZ.rotateAroundY(yRotation);
		XyZ.rotateAroundY(yRotation);
		/*
		 * double d11 = x + this.renderMinX; double d12 = x + this.renderMaxX;
		 * double d13 = y + this.renderMinY; double d14 = y + this.renderMaxY;
		 * double d15 = z + this.renderMaxZ;
		 * 
		 * if (this.renderFromInside) { d11 = x + this.renderMaxX; d12 = x +
		 * this.renderMinX; }
		 */
		boolean culled = GL11.glIsEnabled(GL11.GL_CULL_FACE);
		if (this.yRotation != 0 || this.rotation != 0)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
		if (this.enableAO && Minecraft.getMinecraft().gameSettings.ambientOcclusion != 0)
		{
			if (renderDiagonal > 0 && (renderDiagonal & CENTER_ROOF_SOUTH) == CENTER_ROOF_SOUTH && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xyZ.xCoord + 0.5 + x, xyZ.yCoord + 0.5 + y,
						(renderFromInside ? XyZ.zCoord : xyZ.zCoord) + 0.5 + z, d3, d10); // xyZ

				tessellator.setColorOpaque_F((this.colorRedBottomRight + this.colorRedBottomLeft) / 2f,
						(this.colorGreenBottomRight + this.colorGreenBottomLeft) / 2f,
						(this.colorBlueBottomRight + this.colorBlueBottomLeft) / 2f);
				tessellator.setBrightness((this.brightnessBottomRight + this.brightnessBottomLeft) / 2);
				tessellator.addVertexWithUV((xyZ.xCoord + XyZ.xCoord) / 2d + 0.5 + x, XyZ.yCoord + 0.5 + y,
						XyZ.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // XyZ

				tessellator.setColorOpaque_F(
						(this.colorRedTopLeft + this.colorRedBottomLeft + this.colorRedBottomRight + this.colorRedTopRight) / 4f,
						(this.colorGreenTopLeft + this.colorGreenBottomLeft + this.colorGreenBottomRight + this.colorGreenTopRight) / 4f,
						(this.colorBlueTopLeft + this.colorBlueBottomLeft + this.colorBlueBottomRight + this.colorBlueTopRight) / 4f);
				tessellator.setBrightness(
						(this.brightnessTopLeft + this.brightnessBottomLeft + this.brightnessBottomRight + this.brightnessTopRight) / 4);
				tessellator.addVertexWithUV((xYZ.xCoord + XYZ.xCoord) / 2d + 0.5 + x,
						(XYZ.yCoord + XyZ.yCoord) / 2d + 0.5 + y, XYZ.zCoord + 0.5 + z, (d3 + d7) / 2d,
						(d9 + d10) / 2d); // XYZ

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((xyZ.xCoord + XyZ.xCoord) / 2d + 0.5 + x, XyZ.yCoord + 0.5 + y,
						XyZ.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // XyZ

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((xyZ.xCoord + XyZ.xCoord) / 2d + 0.5 + x, XyZ.yCoord + 0.5 + y,
						XyZ.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // XyZ

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? xyZ.zCoord : XyZ.zCoord) + 0.5 + z, d4, d6); // XyZ

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((xYZ.xCoord + XYZ.xCoord) / 2d + 0.5 + x,
						(XYZ.yCoord + XyZ.yCoord) / 2d + 0.5 + y, XYZ.zCoord + 0.5 + z, (d3 + d7) / 2d,
						(d9 + d10) / 2d); // XYZ

				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((xyZ.xCoord + XyZ.xCoord) / 2d + 0.5 + x, XyZ.yCoord + 0.5 + y,
						XyZ.zCoord + 0.5 + z, (d3 + d7) / 2d, d6); // XyZ

			}
			else if (renderDiagonal >= 64 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{

			}
			else if (renderDiagonal == DIAGONAL_UP_LEFT || renderDiagonal == DIAGONAL_DOWN_RIGHT || renderDiagonal == -1 || renderDiagonal == 45 || renderDiagonal == 47 || ((renderDiagonal & 3) == 0 && renderDiagonal != 8 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT))
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d3, d5); // xYZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d7, d9); // XYZ
			}
			else if (((renderDiagonal & 3) == 1 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT) || renderDiagonal == 50)
			{
				/*
				 * tessellator.setColorOpaque_F(this.colorRedTopLeft,
				 * this.colorGreenTopLeft, this.colorBlueTopLeft);
				 * tessellator.setBrightness(this.brightnessTopLeft);
				 * tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord :
				 * xYZ.xCoord) + 0.5 + x, xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5
				 * + z, d3, d5); // xYZ
				 */
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d7, d9); // XYZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
			}
			else if (((renderDiagonal & 3) == 3 || renderDiagonal == 8) && renderDiagonal != 7 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d3, d5); // xYZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV((renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV((renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ
				/*
				 * tessellator.setColorOpaque_F(this.colorRedTopRight,
				 * this.colorGreenTopRight, this.colorBlueTopRight);
				 * tessellator.setBrightness(this.brightnessTopRight);
				 * tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord :
				 * XYZ.xCoord) + 0.5 + x, XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5
				 * + z, d7, d9); // XYZ
				 */
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d3, d5); // xYZ
			}
		}
		else
		{
			if (renderDiagonal > 99 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				System.out.println(renderDiagonal);
			}
			else if (renderDiagonal == DIAGONAL_UP_LEFT || renderDiagonal == DIAGONAL_DOWN_RIGHT || renderDiagonal == -1 || renderDiagonal == 45 || renderDiagonal == 47 || ((renderDiagonal & 3) == 0 && renderDiagonal != 8 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT))
			{
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d3, d5); // xYZ
				tessellator.addVertexWithUV((renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
				tessellator.addVertexWithUV((renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d7, d9); // XYZ
			}
			else if (((renderDiagonal & 3) == 1 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT) || renderDiagonal == 50)
			{
				/*
				 * tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord :
				 * xYZ.xCoord) + 0.5 + x, xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5
				 * + z, d3, d5); // xYZ
				 */
				tessellator.addVertexWithUV((renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
				tessellator.addVertexWithUV((renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ
				tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord : XYZ.xCoord) + 0.5 + x,
						XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5 + z, d7, d9); // XYZ
				tessellator.addVertexWithUV((renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
			}
			else if (((renderDiagonal & 3) == 3 || renderDiagonal == 8) && renderDiagonal != 7 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d3, d5); // xYZ
				tessellator.addVertexWithUV((renderFromInside ? XyZ.xCoord : xyZ.xCoord) + 0.5 + x,
						xyZ.yCoord + 0.5 + y, xyZ.zCoord + 0.5 + z, d8, d10); // xyZ
				tessellator.addVertexWithUV((renderFromInside ? xyZ.xCoord : XyZ.xCoord) + 0.5 + x,
						XyZ.yCoord + 0.5 + y, XyZ.zCoord + 0.5 + z, d4, d6); // XyZ
				/*
				 * tessellator.addVertexWithUV((renderFromInside ? xYZ.xCoord :
				 * XYZ.xCoord) + 0.5 + x, XYZ.yCoord + 0.5 + y, XYZ.zCoord + 0.5
				 * + z, d7, d9); // XYZ
				 */
				tessellator.addVertexWithUV((renderFromInside ? XYZ.xCoord : xYZ.xCoord) + 0.5 + x,
						xYZ.yCoord + 0.5 + y, xYZ.zCoord + 0.5 + z, d3, d5); // xYZ
			}
		}
		if ((this.yRotation != 0 || this.rotation != 0) && culled)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	/**
	 * Renders the given texture to the west (x-negative) face of the block.
	 * Args: block, x, y, z, texture
	 */
	public void renderFaceXNeg(Block block, double x, double y, double z, IIcon p_147798_8_)
	{
		Tessellator tessellator = Tessellator.instance;

		if (block == TFCBlocks.moss)
		{
			int dir = 5;
			for (int n = 0; n < 2; n++)
			{
				if (this.mossId[dir][n] != -1 && this.mossDirection[dir][n] && this.mossRotation[dir][n] != -1)
				{
					this.renderMoss(this.mossId[dir][n], this.mossRotation[dir][n], x, y, z, dir, n);
					this.mossDirection[dir][n] = false;
					this.mossId[dir][n] = this.mossRotation[dir][n] = -1;
				}
			}
			return;
		}

		if (block == TFCBlocks.fauxPalm)
		{
			// renderPalmFrond(x, y, z, 0.5 * Math.PI - rot30, 0, 0.5);
			// renderPalmFrond(x, y, z, 0.5 * Math.PI, 0, 0.5);
			// renderPalmFrond(x, y, z, 0.5 * Math.PI + rot30, 0, 0.5);
			return;
		}

		if (this.hasOverrideBlockTexture())
		{
			p_147798_8_ = this.overrideBlockTexture;
		}
		else if (useTextureBelow)
		{
			p_147798_8_ = block.getIcon(4, this.blockAccess.getBlockMetadata((int) x, (int) y - 1, (int) z));
		}

		double d3 = (double) p_147798_8_.getInterpolatedU(this.renderMinZ * 16.0D);
		double d4 = (double) p_147798_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
		double d5 = (double) p_147798_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double d6 = (double) p_147798_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double d7;

		if (this.flipTexture)
		{
			d7 = d3;
			d3 = d4;
			d4 = d7;
		}

		if (staticTexture)
		{
			if (this.renderMaxZ - this.renderMinZ >= 1.0D)
			{
				d3 = (double) p_147798_8_.getMinU();
				d4 = (double) p_147798_8_.getMaxU();
			}
			else
			{
				d3 = (double) p_147798_8_.getMinU();
				d4 = (double) p_147798_8_.getInterpolatedU((this.renderMaxZ - this.renderMinZ) * 16.0D);
			}
			if (this.renderMaxY - this.renderMinY >= 1.0D)
			{
				d5 = (double) p_147798_8_.getMinV();
				d6 = (double) p_147798_8_.getMaxV();
			}
			else
			{
				d5 = (double) p_147798_8_.getMinV();
				d6 = (double) p_147798_8_.getInterpolatedV((this.renderMaxY - this.renderMinY) * 16.0D);
			}
		}
		else
		{
			if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
			{
				if (this.renderMaxZ - this.renderMinZ >= 1.0D)
				{
					d3 = (double) p_147798_8_.getMinU();
					d4 = (double) p_147798_8_.getMaxU();
				}
				else if (this.renderMaxZ > 1.0D)
				{
					d3 = (double) p_147798_8_.getInterpolatedU((1.0D - (this.renderMaxZ - this.renderMinZ)) * 16.0D);
					d4 = (double) p_147798_8_.getMaxU();
				}
				else
				{
					d3 = (double) p_147798_8_.getMinU();
					d4 = (double) p_147798_8_.getInterpolatedU((this.renderMaxZ - this.renderMinZ) * 16.0D);
				}
			}

			if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
			{
				d5 = (double) p_147798_8_.getMinV();
				d6 = (double) p_147798_8_.getMaxV();
			}
		}
		d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;

		if (this.uvRotateNorth == 1)
		{
			d3 = (double) p_147798_8_.getInterpolatedU(this.renderMinY * 16.0D);
			d5 = (double) p_147798_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
			d4 = (double) p_147798_8_.getInterpolatedU(this.renderMaxY * 16.0D);
			d6 = (double) p_147798_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if (this.uvRotateNorth == 2)
		{
			d3 = (double) p_147798_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			d5 = (double) p_147798_8_.getInterpolatedV(this.renderMinZ * 16.0D);
			d4 = (double) p_147798_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			d6 = (double) p_147798_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if (this.uvRotateNorth == 3)
		{
			d3 = (double) p_147798_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			d4 = (double) p_147798_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			d5 = (double) p_147798_8_.getInterpolatedV(this.renderMaxY * 16.0D);
			d6 = (double) p_147798_8_.getInterpolatedV(this.renderMinY * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}

		Vec3 xYZ = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMaxY - 0.5, this.renderMaxZ - 0.5);
		Vec3 xYz = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMaxY - 0.5, this.renderMinZ - 0.5);
		Vec3 xyz = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMinY - 0.5, this.renderMinZ - 0.5);
		Vec3 xyZ = Vec3.createVectorHelper(this.renderMinX - 0.5, this.renderMinY - 0.5, this.renderMaxZ - 0.5);

		xYZ = translateVec(xYZ);
		xYz = translateVec(xYz);
		xyz = translateVec(xyz);
		xyZ = translateVec(xyZ);
		
		xYZ.rotateAroundX(rotation);
		xYz.rotateAroundX(rotation);
		xyz.rotateAroundX(rotation);
		xyZ.rotateAroundX(rotation);

		xYZ.rotateAroundY(yRotation);
		xYz.rotateAroundY(yRotation);
		xyz.rotateAroundY(yRotation);
		xyZ.rotateAroundY(yRotation);
		/*
		 * double d11 = x + this.renderMinX; double d12 = y + this.renderMinY;
		 * double d13 = y + this.renderMaxY; double d14 = z + this.renderMinZ;
		 * double d15 = z + this.renderMaxZ;
		 * 
		 * if (this.renderFromInside) { d14 = z + this.renderMaxZ; d15 = z +
		 * this.renderMinZ; }
		 */

		boolean culled = GL11.glIsEnabled(GL11.GL_CULL_FACE);
		if (this.yRotation != 0 || this.rotation != 0)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
		if (this.enableAO && Minecraft.getMinecraft().gameSettings.ambientOcclusion != 0)
		{
			if (renderDiagonal > 0 && ((renderDiagonal & CENTER_ROOF_WEST) == CENTER_ROOF_WEST) && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xyZ.xCoord + 0.5 + x, xyZ.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : xyZ.zCoord) + 0.5 + z, d4, d10); // xyZ

				tessellator.setColorOpaque_F((this.colorRedTopLeft + this.colorRedBottomLeft) / 2f,
						(this.colorGreenTopLeft + this.colorGreenBottomLeft) / 2f,
						(this.colorBlueTopLeft + this.colorBlueBottomLeft) / 2f);
				tessellator.setBrightness((this.brightnessTopLeft + this.brightnessBottomLeft) / 2);
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(xyZ.zCoord + xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // xyz

				tessellator.setColorOpaque_F(
						(this.colorRedTopLeft + this.colorRedBottomLeft + this.colorRedBottomRight + this.colorRedTopRight) / 4f,
						(this.colorGreenTopLeft + this.colorGreenBottomLeft + this.colorGreenBottomRight + this.colorGreenTopRight) / 4f,
						(this.colorBlueTopLeft + this.colorBlueBottomLeft + this.colorBlueBottomRight + this.colorBlueTopRight) / 4f);
				tessellator.setBrightness(
						(this.brightnessTopLeft + this.brightnessBottomLeft + this.brightnessBottomRight + this.brightnessTopRight) / 4);
				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, (xYz.yCoord + xyz.yCoord) / 2d + 0.5 + y,
						(xYZ.zCoord + xYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, (d9 + d10) / 2d); // xYz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(xyZ.zCoord + xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // xyz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(xyZ.zCoord + xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // xyz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, (xYz.yCoord + xyz.yCoord) / 2d + 0.5 + y,
						(xYZ.zCoord + xYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, (d9 + d10) / 2d); // xYz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyZ.zCoord : xyz.zCoord) + 0.5 + z, d3, d6); // xyz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(xyZ.zCoord + xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // xyz

			}
			else if (renderDiagonal >= 64 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{

			}
			else if (renderDiagonal == DIAGONAL_UP_RIGHT || renderDiagonal == DIAGONAL_DOWN_RIGHT || renderDiagonal == -1 || renderDiagonal == 40 || ((renderDiagonal & 3) == 3 && renderDiagonal != 7 && renderDiagonal != 15 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT))
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xYZ.xCoord + 0.5 + x, xYZ.yCoord + 0.5 + y,
						(renderFromInside ? xYz.zCoord : xYZ.zCoord) + 0.5 + z, d7, d9); // xYZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, xYz.yCoord + 0.5 + y,
						(renderFromInside ? xYZ.zCoord : xYz.zCoord) + 0.5 + z, d3, d5); // xYz
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyZ.zCoord : xyz.zCoord) + 0.5 + z, d8, d10); // xyz
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV(xyZ.xCoord + 0.5 + x, xyZ.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : xyZ.zCoord) + 0.5 + z, d4, d6); // xyZ
			}
			else if (((renderDiagonal & 3) == 0 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT) || renderDiagonal == 15 || renderDiagonal == 45)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xYZ.xCoord + 0.5 + x, xYZ.yCoord + 0.5 + y,
						(renderFromInside ? xYz.zCoord : xYZ.zCoord) + 0.5 + z, d7, d9); // xYZ

				/*
				 * tessellator.setColorOpaque_F(this.colorRedBottomLeft,
				 * this.colorGreenBottomLeft, this.colorBlueBottomLeft);
				 * tessellator.setBrightness(this.brightnessBottomLeft);
				 * tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, xYz.yCoord
				 * + 0.5 + y, (renderFromInside ? xYZ.zCoord : xYz.zCoord) + 0.5
				 * + z, d3, d5); // xYz
				 */

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyZ.zCoord : xyz.zCoord) + 0.5 + z, d8, d10); // xyz

				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV(xyZ.xCoord + 0.5 + x, xyZ.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : xyZ.zCoord) + 0.5 + z, d4, d6); // xyZ

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(xYZ.xCoord + 0.5 + x, xYZ.yCoord + 0.5 + y,
						(renderFromInside ? xYz.zCoord : xYZ.zCoord) + 0.5 + z, d7, d9); // xYZ
			}
			else if (((renderDiagonal & 3) == 2 && renderDiagonal != 18 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT) || renderDiagonal == 7)
			{
				/*
				 * tessellator.setColorOpaque_F(this.colorRedTopLeft,
				 * this.colorGreenTopLeft, this.colorBlueTopLeft);
				 * tessellator.setBrightness(this.brightnessTopLeft);
				 * tessellator.addVertexWithUV(xYZ.xCoord + 0.5 + x, xYZ.yCoord
				 * + 0.5 + y, (renderFromInside ? xYz.zCoord : xYZ.zCoord) + 0.5
				 * + z, d7, d9); // xYZ
				 */

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, xYz.yCoord + 0.5 + y,
						(renderFromInside ? xYZ.zCoord : xYz.zCoord) + 0.5 + z, d3, d5); // xYz

				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyZ.zCoord : xyz.zCoord) + 0.5 + z, d8, d10); // xyz

				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV(xyZ.xCoord + 0.5 + x, xyZ.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : xyZ.zCoord) + 0.5 + z, d4, d6); // xyZ

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, xYz.yCoord + 0.5 + y,
						(renderFromInside ? xYZ.zCoord : xYz.zCoord) + 0.5 + z, d3, d5); // xYz
			}
		}
		else
		{
			if (renderDiagonal > 0 && ((renderDiagonal & CENTER_ROOF_WEST) == CENTER_ROOF_WEST) && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{

				tessellator.addVertexWithUV(xyZ.xCoord + 0.5 + x, xyZ.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : xyZ.zCoord) + 0.5 + z, d4, d10); // xyZ

				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(xyZ.zCoord + xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // xyz

				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, (xYz.yCoord + xyz.yCoord) / 2d + 0.5 + y,
						(xYZ.zCoord + xYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, (d9 + d10) / 2d); // xYz

				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(xyZ.zCoord + xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // xyz

				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(xyZ.zCoord + xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // xyz

				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, (xYz.yCoord + xyz.yCoord) / 2d + 0.5 + y,
						(xYZ.zCoord + xYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, (d9 + d10) / 2d); // xYz

				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyZ.zCoord : xyz.zCoord) + 0.5 + z, d3, d6); // xyz

				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(xyZ.zCoord + xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // xyz

			}
			else if (renderDiagonal >= 64 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{

			}
			else if (renderDiagonal == DIAGONAL_UP_RIGHT || renderDiagonal == DIAGONAL_DOWN_RIGHT || renderDiagonal == -1 || renderDiagonal == 40 || ((renderDiagonal & 3) == 3 && renderDiagonal != 7 && renderDiagonal != 15 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT))
			{
				tessellator.addVertexWithUV(xYZ.xCoord + 0.5 + x, xYZ.yCoord + 0.5 + y,
						(renderFromInside ? xYz.zCoord : xYZ.zCoord) + 0.5 + z, d7, d9); // xYZ
				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, xYz.yCoord + 0.5 + y,
						(renderFromInside ? xYZ.zCoord : xYz.zCoord) + 0.5 + z, d3, d5); // xYz
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyZ.zCoord : xyz.zCoord) + 0.5 + z, d8, d10); // xyz
				tessellator.addVertexWithUV(xyZ.xCoord + 0.5 + x, xyZ.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : xyZ.zCoord) + 0.5 + z, d4, d6); // xyZ
			}
			else if (((renderDiagonal & 3) == 0 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT) || renderDiagonal == 15 || renderDiagonal == 45)
			{
				tessellator.addVertexWithUV(xYZ.xCoord + 0.5 + x, xYZ.yCoord + 0.5 + y,
						(renderFromInside ? xYz.zCoord : xYZ.zCoord) + 0.5 + z, d7, d9); // xYZ
				/*
				 * tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, xYz.yCoord
				 * + 0.5 + y, (renderFromInside ? xYZ.zCoord : xYz.zCoord) + 0.5
				 * + z, d3, d5); // xYz
				 */
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyZ.zCoord : xyz.zCoord) + 0.5 + z, d8, d10); // xyz
				tessellator.addVertexWithUV(xyZ.xCoord + 0.5 + x, xyZ.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : xyZ.zCoord) + 0.5 + z, d4, d6); // xyZ
				tessellator.addVertexWithUV(xYZ.xCoord + 0.5 + x, xYZ.yCoord + 0.5 + y,
						(renderFromInside ? xYz.zCoord : xYZ.zCoord) + 0.5 + z, d7, d9); // xYZ
			}
			else if (((renderDiagonal & 3) == 2 && renderDiagonal != 18 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT) || renderDiagonal == 7)
			{
				/*
				 * tessellator.addVertexWithUV(xYZ.xCoord + 0.5 + x, xYZ.yCoord
				 * + 0.5 + y, (renderFromInside ? xYz.zCoord : xYZ.zCoord) + 0.5
				 * + z, d7, d9); // xYZ
				 */
				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, xYz.yCoord + 0.5 + y,
						(renderFromInside ? xYZ.zCoord : xYz.zCoord) + 0.5 + z, d3, d5); // xYz
				tessellator.addVertexWithUV(xyz.xCoord + 0.5 + x, xyz.yCoord + 0.5 + y,
						(renderFromInside ? xyZ.zCoord : xyz.zCoord) + 0.5 + z, d8, d10); // xyz
				tessellator.addVertexWithUV(xyZ.xCoord + 0.5 + x, xyZ.yCoord + 0.5 + y,
						(renderFromInside ? xyz.zCoord : xyZ.zCoord) + 0.5 + z, d4, d6); // xyZ
				tessellator.addVertexWithUV(xYz.xCoord + 0.5 + x, xYz.yCoord + 0.5 + y,
						(renderFromInside ? xYZ.zCoord : xYz.zCoord) + 0.5 + z, d3, d5); // xYz
			}
		}
		if ((this.yRotation != 0 || this.rotation != 0) && culled)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	public void clearMossRenderBounds()
	{
		for (int side = 0; side < 6; side++)
		{
			for (int n = 0; n < 2; n++)
			{
				this.renderMaxXs[side][n] = 0;
				this.renderMinXs[side][n] = 0;
				this.renderMaxYs[side][n] = 0;
				this.renderMinYs[side][n] = 0;
				this.renderMaxZs[side][n] = 0;
				this.renderMinZs[side][n] = 0;
			}
		}
	}

	/**
	 * Renders the given texture to the east (x-positive) face of the block.
	 * Args: block, x, y, z, texture
	 */
	public void renderFaceXPos(Block block, double x, double y, double z, IIcon p_147764_8_)
	{
		Tessellator tessellator = Tessellator.instance;

		if (block == TFCBlocks.moss)
		{
			int dir = 4;
			for (int n = 0; n < 2; n++)
			{
				if (this.mossId[dir][n] != -1 && this.mossDirection[dir][n] && this.mossRotation[dir][n] != -1)
				{
					this.renderMoss(this.mossId[dir][n], this.mossRotation[dir][n], x, y, z, dir, n);
					this.mossDirection[dir][n] = false;
					this.mossId[dir][n] = this.mossRotation[dir][n] = -1;
				}
			}
			return;
		}

		if (block == TFCBlocks.fauxPalm)
		{
			// renderPalmFrond(x, y, z, 1.5 * Math.PI - rot30,
			// (-8+(z%11)*z%2==0?1:-1)*rot1, 0.5*(((z%10)/20d)+0.5));
			// renderPalmFrond(x, y, z, 1.5 * Math.PI,
			// (10+(x%7)*x%2==0?1:-1)*rot1, 0.5*(((x%10)/20d)+0.5));
			// renderPalmFrond(x, y, z, 1.5 * Math.PI + rot30,
			// (5+(y%13)*y%2==0?1:-1)*rot1, 0.5*(((y%10)/20d)+0.5));
			return;
		}

		if (this.hasOverrideBlockTexture())
		{
			p_147764_8_ = this.overrideBlockTexture;
		}
		else if (useTextureBelow)
		{
			p_147764_8_ = block.getIcon(5, this.blockAccess.getBlockMetadata((int) x, (int) y - 1, (int) z));
		}
		double d3 = (double) p_147764_8_.getInterpolatedU(this.renderMinZ * 16.0D);
		double d4 = (double) p_147764_8_.getInterpolatedU(this.renderMaxZ * 16.0D);

		if (this.field_152631_f)
		{
			d4 = (double) p_147764_8_.getInterpolatedU((1.0D - this.renderMinZ) * 16.0D);
			d3 = (double) p_147764_8_.getInterpolatedU((1.0D - this.renderMaxZ) * 16.0D);
		}

		double d5 = (double) p_147764_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double d6 = (double) p_147764_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double d7;

		if (this.flipTexture)
		{
			d7 = d3;
			d3 = d4;
			d4 = d7;
		}
		if (staticTexture)
		{
			if (this.renderMaxZ - this.renderMinZ >= 1.0D)
			{
				d3 = (double) p_147764_8_.getMinU();
				d4 = (double) p_147764_8_.getMaxU();
			}
			else
			{
				d3 = (double) p_147764_8_.getMinU();
				d4 = (double) p_147764_8_.getInterpolatedU((this.renderMaxZ - this.renderMinZ) * 16.0D);
			}
			if (this.renderMaxY - this.renderMinY >= 1.0D)
			{
				d5 = (double) p_147764_8_.getMinV();
				d6 = (double) p_147764_8_.getMaxV();
			}
			else
			{
				d5 = (double) p_147764_8_.getMinV();
				d6 = (double) p_147764_8_.getInterpolatedV((this.renderMaxY - this.renderMinY) * 16.0D);
			}
		}
		else
		{
			if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
			{
				if (this.renderMaxZ - this.renderMinZ >= 1.0D)
				{
					d3 = (double) p_147764_8_.getMinU();
					d4 = (double) p_147764_8_.getMaxU();
				}
				else if (this.renderMaxZ > 1.0D)
				{
					d3 = (double) p_147764_8_.getInterpolatedU((1.0D - (this.renderMaxZ - this.renderMinZ)) * 16.0D);
					d4 = (double) p_147764_8_.getMaxU();
				}
				else
				{
					d3 = (double) p_147764_8_.getMinU();
					d4 = (double) p_147764_8_.getInterpolatedU((this.renderMaxZ - this.renderMinZ) * 16.0D);
				}
			}

			if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
			{
				d5 = (double) p_147764_8_.getMinV();
				d6 = (double) p_147764_8_.getMaxV();
			}
		}
		if (renderDiagonal > 99)
		{
			// d4 = (double) p_147764_8_.getInterpolatedU(8d);
			// d5 = (double) p_147764_8_.getMinV();
			// d6 = (double) p_147764_8_.getInterpolatedV(8.0D);
		}

		d7 = d4;
		double d8 = d3;
		double d9 = d5;
		double d10 = d6;

		if (this.uvRotateSouth == 2)
		{
			d3 = (double) p_147764_8_.getInterpolatedU(this.renderMinY * 16.0D);
			d5 = (double) p_147764_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			d4 = (double) p_147764_8_.getInterpolatedU(this.renderMaxY * 16.0D);
			d6 = (double) p_147764_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
			d9 = d5;
			d10 = d6;
			d7 = d3;
			d8 = d4;
			d5 = d6;
			d6 = d9;
		}
		else if (this.uvRotateSouth == 1)
		{
			d3 = (double) p_147764_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			d5 = (double) p_147764_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
			d4 = (double) p_147764_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			d6 = (double) p_147764_8_.getInterpolatedV(this.renderMinZ * 16.0D);
			d7 = d4;
			d8 = d3;
			d3 = d4;
			d4 = d8;
			d9 = d6;
			d10 = d5;
		}
		else if (this.uvRotateSouth == 3)
		{
			d3 = (double) p_147764_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			d4 = (double) p_147764_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			d5 = (double) p_147764_8_.getInterpolatedV(this.renderMaxY * 16.0D);
			d6 = (double) p_147764_8_.getInterpolatedV(this.renderMinY * 16.0D);
			d7 = d4;
			d8 = d3;
			d9 = d5;
			d10 = d6;
		}

		Vec3 XYZ = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMaxY - 0.5, this.renderMaxZ - 0.5);
		Vec3 XYz = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMaxY - 0.5, this.renderMinZ - 0.5);
		Vec3 Xyz = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMinY - 0.5, this.renderMinZ - 0.5);
		Vec3 XyZ = Vec3.createVectorHelper(this.renderMaxX - 0.5, this.renderMinY - 0.5, this.renderMaxZ - 0.5);

		XYZ = translateVec(XYZ);
		XYz = translateVec(XYz);
		Xyz = translateVec(Xyz);
		XyZ = translateVec(XyZ);
		
		XYZ.rotateAroundX(rotation);
		XYz.rotateAroundX(rotation);
		Xyz.rotateAroundX(rotation);
		XyZ.rotateAroundX(rotation);

		XYZ.rotateAroundY(yRotation);
		XYz.rotateAroundY(yRotation);
		Xyz.rotateAroundY(yRotation);
		XyZ.rotateAroundY(yRotation);

		/*
		 * double d11 = x + this.renderMaxX; double d12 = y + this.renderMinY;
		 * double d13 = y + this.renderMaxY; double d14 = z + this.renderMinZ;
		 * double d15 = z + this.renderMaxZ;
		 * 
		 * if (this.renderFromInside) { d14 = z + this.renderMaxZ; d15 = z +
		 * this.renderMinZ; }
		 */

		boolean culled = GL11.glIsEnabled(GL11.GL_CULL_FACE);
		if (this.yRotation != 0 || this.rotation != 0)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
		if (this.enableAO && Minecraft.getMinecraft().gameSettings.ambientOcclusion != 0)
		{
			if (renderDiagonal > 0 && (renderDiagonal & CENTER_ROOF_EAST) == CENTER_ROOF_EAST && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d3, d10); // XyZ

				tessellator.setColorOpaque_F((this.colorRedTopLeft + this.colorRedBottomLeft) / 2f,
						(this.colorGreenTopLeft + this.colorGreenBottomLeft) / 2f,
						(this.colorBlueTopLeft + this.colorBlueBottomLeft) / 2f);
				tessellator.setBrightness((this.brightnessTopLeft + this.brightnessBottomLeft) / 2);
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(XyZ.zCoord + Xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				tessellator.setColorOpaque_F(
						(this.colorRedTopLeft + this.colorRedBottomLeft + this.colorRedBottomRight + this.colorRedTopRight) / 4f,
						(this.colorGreenTopLeft + this.colorGreenBottomLeft + this.colorGreenBottomRight + this.colorGreenTopRight) / 4f,
						(this.colorBlueTopLeft + this.colorBlueBottomLeft + this.colorBlueBottomRight + this.colorBlueTopRight) / 4f);
				tessellator.setBrightness(
						(this.brightnessTopLeft + this.brightnessBottomLeft + this.brightnessBottomRight + this.brightnessTopRight) / 4);
				tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, (XYz.yCoord + Xyz.yCoord) / 2d + 0.5 + y,
						(XYZ.zCoord + XYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, (d9 + d10) / 2d); // XYz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(XyZ.zCoord + Xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(XyZ.zCoord + Xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(renderFromInside ? XyZ.zCoord : Xyz.zCoord) + 0.5 + z, d7, d6); // Xyz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, (XYz.yCoord + Xyz.yCoord) / 2d + 0.5 + y,
						(XYZ.zCoord + XYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, (d9 + d10) / 2d); // XYz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(XyZ.zCoord + Xyz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

			}
			else if ((renderDiagonal == 8 || renderDiagonal >= 64) && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{

			}
			else if (renderDiagonal == DIAGONAL_UP_LEFT || renderDiagonal == DIAGONAL_DOWN_LEFT || renderDiagonal == -1 || renderDiagonal == 50 || ((renderDiagonal & 3) == 1 && renderDiagonal != 13 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT))
			{
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ

				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(renderFromInside ? XyZ.zCoord : Xyz.zCoord) + 0.5 + z, d4, d6); // Xyz

				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, XYz.yCoord + 0.5 + y,
						(renderFromInside ? XYZ.zCoord : XYz.zCoord) + 0.5 + z, d7, d9); // XYz

				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(XYZ.xCoord + 0.5 + x, XYZ.yCoord + 0.5 + y,
						(renderFromInside ? XYz.zCoord : XYZ.zCoord) + 0.5 + z, d3, d5); // XYZ
			}
			else if (((renderDiagonal & 3) == 0 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT) || renderDiagonal == 13 || renderDiagonal == 47)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(renderFromInside ? XyZ.zCoord : Xyz.zCoord) + 0.5 + z, d4, d6); // Xyz
				/*
				 * tessellator.setColorOpaque_F(this.colorRedBottomRight,
				 * this.colorGreenBottomRight, this.colorBlueBottomRight);
				 * tessellator.setBrightness(this.brightnessBottomRight);
				 * tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, XYz.yCoord
				 * + 0.5 + y, (renderFromInside ? XYZ.zCoord : XYz.zCoord) + 0.5
				 * + z, d7, d9); // XYz
				 */
				tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				tessellator.setBrightness(this.brightnessTopRight);
				tessellator.addVertexWithUV(XYZ.xCoord + 0.5 + x, XYZ.yCoord + 0.5 + y,
						(renderFromInside ? XYz.zCoord : XYZ.zCoord) + 0.5 + z, d3, d5); // XYZ
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ
			}
			else if ((renderDiagonal & 3) == 2 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ
				tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft,
						this.colorBlueBottomLeft);
				tessellator.setBrightness(this.brightnessBottomLeft);
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(renderFromInside ? XyZ.zCoord : Xyz.zCoord) + 0.5 + z, d4, d6); // Xyz
				tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight,
						this.colorBlueBottomRight);
				tessellator.setBrightness(this.brightnessBottomRight);
				tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, XYz.yCoord + 0.5 + y,
						(renderFromInside ? XYZ.zCoord : XYz.zCoord) + 0.5 + z, d7, d9); // XYz
				/*
				 * tessellator.setColorOpaque_F(this.colorRedTopRight,
				 * this.colorGreenTopRight, this.colorBlueTopRight);
				 * tessellator.setBrightness(this.brightnessTopRight);
				 * tessellator.addVertexWithUV(XYZ.xCoord + 0.5 + x, XYZ.yCoord
				 * + 0.5 + y, (renderFromInside ? XYz.zCoord : XYZ.zCoord) + 0.5
				 * + z, d3, d5); // XYZ
				 */
				tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				tessellator.setBrightness(this.brightnessTopLeft);
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ
			}
		}
		else
		{
			if (renderDiagonal > 0 && (renderDiagonal & CENTER_ROOF_EAST) == CENTER_ROOF_EAST && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				if (renderDiagonal > 0 && ((renderDiagonal >> 6) & 1) == 1)
				{
					tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
							(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d3, d10); // XyZ
					tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
							(XYZ.zCoord + XYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz
					tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, (XYz.yCoord + Xyz.yCoord) / 2d + 0.5 + y,
							(XYZ.zCoord + XYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, (d9 + d10) / 2d); // XYz
					tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
							(XYZ.zCoord + XYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

					tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
							(XYZ.zCoord + XYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz
					tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
							(renderFromInside ? XyZ.zCoord : Xyz.zCoord) + 0.5 + z, d7, d6); // Xyz
					tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, (XYz.yCoord + Xyz.yCoord) / 2d + 0.5 + y,
							(XYZ.zCoord + XYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, (d9 + d10) / 2d); // XYz
					tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
							(XYZ.zCoord + XYz.zCoord) / 2d + 0.5 + z, (d3 + d7) / 2d, d6); // Xyz

				}
			}
			else if ((renderDiagonal == 8 || renderDiagonal >= 64) && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{

			}
			else if (renderDiagonal == DIAGONAL_UP_LEFT || renderDiagonal == DIAGONAL_DOWN_LEFT || renderDiagonal == -1 || renderDiagonal == 50 || ((renderDiagonal & 3) == 1 && renderDiagonal != 13 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT))
			{
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(renderFromInside ? XyZ.zCoord : Xyz.zCoord) + 0.5 + z, d4, d6); // Xyz
				tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, XYz.yCoord + 0.5 + y,
						(renderFromInside ? XYZ.zCoord : XYz.zCoord) + 0.5 + z, d7, d9); // XYz
				tessellator.addVertexWithUV(XYZ.xCoord + 0.5 + x, XYZ.yCoord + 0.5 + y,
						(renderFromInside ? XYz.zCoord : XYZ.zCoord) + 0.5 + z, d3, d5); // XYZ
			}
			else if (((renderDiagonal & 3) == 0 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT) || renderDiagonal == 13 || renderDiagonal == 47)
			{
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(renderFromInside ? XyZ.zCoord : Xyz.zCoord) + 0.5 + z, d4, d6); // Xyz
				/*
				 * tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, XYz.yCoord
				 * + 0.5 + y, (renderFromInside ? XYZ.zCoord : XYz.zCoord) + 0.5
				 * + z, d7, d9); // XYz
				 */
				tessellator.addVertexWithUV(XYZ.xCoord + 0.5 + x, XYZ.yCoord + 0.5 + y,
						(renderFromInside ? XYz.zCoord : XYZ.zCoord) + 0.5 + z, d3, d5); // XYZ
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ
			}
			else if ((renderDiagonal & 3) == 2 && renderDiagonal != DIAGONAL_UP_LEFT && renderDiagonal != DIAGONAL_DOWN_LEFT && renderDiagonal != DIAGONAL_UP_RIGHT && renderDiagonal != DIAGONAL_DOWN_RIGHT)
			{
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ
				tessellator.addVertexWithUV(Xyz.xCoord + 0.5 + x, Xyz.yCoord + 0.5 + y,
						(renderFromInside ? XyZ.zCoord : Xyz.zCoord) + 0.5 + z, d4, d6); // Xyz
				tessellator.addVertexWithUV(XYz.xCoord + 0.5 + x, XYz.yCoord + 0.5 + y,
						(renderFromInside ? XYZ.zCoord : XYz.zCoord) + 0.5 + z, d7, d9); // XYz
				/*
				 * tessellator.addVertexWithUV(XYZ.xCoord + 0.5 + x, XYZ.yCoord
				 * + 0.5 + y, (renderFromInside ? XYz.zCoord : XYZ.zCoord) + 0.5
				 * + z, d3, d5); // XYZ
				 */
				tessellator.addVertexWithUV(XyZ.xCoord + 0.5 + x, XyZ.yCoord + 0.5 + y,
						(renderFromInside ? Xyz.zCoord : XyZ.zCoord) + 0.5 + z, d8, d10); // XyZ
			}
		}
		if ((this.yRotation != 0 || this.rotation != 0) && culled)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}
}
