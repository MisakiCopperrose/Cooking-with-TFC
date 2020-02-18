package com.dunk.tfc.Render.Blocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Render.Models.ModelPalmTree;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderPalm  implements ISimpleBlockRenderingHandler 
{
	static float pixel3 = 3f/16f;
	static float pixel5 = 5f/16f;
	static float pixel12 = 12f/16f;
	static float pixel14 = 14f/16f;

	private static final ModelPalmTree palmModel = new ModelPalmTree();

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderer)
	{
		//TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, "textures/blocks/plants/frond_bw.png")); //texture
		int l = TerraFirmaCraft.proxy.foliageColorMultiplier(world, i, j, k);
		float f0 = (float)(l >> 16 & 255) / 255.0F;
		float f1 = (float)(l >> 8 & 255) / 255.0F;
		float f2 = (float)(l & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f0 * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f0 * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f0 * 30.0F + f2 * 70.0F) / 100.0F;
			f0 = f3;
			f1 = f4;
			f2 = f5;
		}
		GL11.glPushMatrix(); //start
		//Tessellator.instance.setColorOpaque_F(f0, f1, f2);
		//GL11.glColor3f(f0, f1, f2);
		GL11.glTranslatef((float)i + 0.5F, (float)j + 0F, (float)k + 0.5F); //size
		//ingotModel.renderIngots(i);
		//palmModel.renderFronds();
		setUpFrond(i,j,k);
		GL11.glPopMatrix(); //end
		//IBlockAccess blockAccess = renderer.blockAccess;
		/*
		renderer.renderAllFaces = true;
		renderer.setRenderBounds(0.15F, 0.1F, 0.15F, 0.85F, 0.1F, 0.85F);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);

		renderer.setRenderBounds(0.1F, 0F, 0.15F, 0.15F, 0.4F, 0.85F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);

		renderer.setRenderBounds(0.85F, 0F, 0.15F, 0.9F, 0.4F, 0.85F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);

		renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.9F, 0.4F, 0.15F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);

		renderer.setRenderBounds(0.1F, 0F, 0.85F, 0.9F, 0.4F, 0.9F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);
		renderer.renderAllFaces = false;
		 */
		return true;
	}

	public void setUpFrond(int i, int j, int k)
	{
		double originX = 0;
		double originY = 0.1;
		double originZ = 0;
		double[][] vertexPositions;
		vertexPositions = new double[16][5];
		double maxX = originX + 16;
		double maxZ = originZ + 32;
		double maxX2 = originX - 16;

		//First part of frond
		double[] vert0 = new double[]{originX, originY+8, originZ, 0.0, 0.0};
		double[] vert1 = new double[]{maxX-2, originY+4, originZ, 16.0, 0.0};
		double[] vert2 = new double[]{maxX, originY+6, maxZ, 16.0, 32.0};
		double[] vert3 = new double[]{originX, originY+12, maxZ, 0.0, 32.0};

		vertexPositions[0] = vert0;
		vertexPositions[1] = vert1;
		vertexPositions[2] = vert2;
		vertexPositions[3] = vert3;

		//Second part of frond
		vert0 = new double[]{originX, originY+8, originZ, 0.0, 0.0};
		vert1 = new double[]{maxX2+2, originY+4, originZ, 16.0, 0.0};
		vert2 = new double[]{maxX2, originY+6, maxZ, 16.0, 32.0};
		vert3 = new double[]{originX, originY+12, maxZ, 0.0, 32.0};

		vertexPositions[4] = vert0;
		vertexPositions[5] = vert1;
		vertexPositions[6] = vert2;
		vertexPositions[7] = vert3;

		//Third part of frond
		vert0 = new double[]{originX, originY+12, maxZ, 0.0, 0.0};
		vert1 = new double[]{maxX, originY+6, maxZ, 16.0, 0.0};
		vert2 = new double[]{maxX-6, originY+2, maxZ*2, 16.0, 32.0};
		vert3 = new double[]{originX, originY+6, maxZ*2, 0.0, 32.0};

		vertexPositions[8] = vert0;
		vertexPositions[9] = vert1;
		vertexPositions[10] = vert2;
		vertexPositions[11] = vert3;

		//Fourth part of frond
		vert0 = new double[]{originX, originY+12, maxZ, 0.0, 0.0};
		vert1 = new double[]{maxX2, originY+6, maxZ, 16.0, 0.0};
		vert2 = new double[]{maxX2+6, originY+2, maxZ*2, 16.0, 32.0};
		vert3 = new double[]{originX, originY+6, maxZ*2, 0.0, 32.0};

		vertexPositions[12] = vert0;
		vertexPositions[13] = vert1;
		vertexPositions[14] = vert2;
		vertexPositions[15] = vert3;


		//int x2 = textureOffsetX + 0;
		//int x3 = textureOffsetX + 32;

		//int y1 = textureOffsetY + 0;
		//int y2 = textureOffsetY + 16;

		//this.quadList[0] = new TexturedQuad(new double[][] {vertexPositions[0], vertexPositions[3], vertexPositions[2], vertexPositions[1]}, 
		//		x2, y1, x3, y2, 32, 16); // long

		//this.quadList[1] = new TexturedQuad(new double[][] {vertexPositions[7], vertexPositions[4], vertexPositions[5], vertexPositions[6]}, 
		//		x2, y1, x3, y2, 32, 16); // long
		//this.quadList[2] = new TexturedQuad(new double[][] {vertexPositions[8], vertexPositions[11], vertexPositions[10], vertexPositions[9]}, 
		//		x2, y1, x3, y2, 32, 16); // long
		//this.quadList[3] = new TexturedQuad(new double[][] {vertexPositions[15], vertexPositions[12], vertexPositions[13], vertexPositions[14]}, 
		//		x2, y1, x3, y2, 32, 16); // long
		Tessellator tes = Tessellator.instance;
		addFace(new double[][]{vertexPositions[0], vertexPositions[3], vertexPositions[2], vertexPositions[1]},tes,i,j,k);
		addFace(new double[][]{vertexPositions[7], vertexPositions[4], vertexPositions[5], vertexPositions[6]},tes,i,j,k);
		addFace(new double[][]{vertexPositions[8], vertexPositions[11], vertexPositions[10], vertexPositions[9]},tes,i,j,k);
		addFace(new double[][]{vertexPositions[15], vertexPositions[12], vertexPositions[13], vertexPositions[14]},tes,i,j,k);
	}

	public void addFace(double[][] vertices, Tessellator t, int x, int y, int z){
		for(int i = 0; i < vertices.length; i++){
			t.addVertexWithUV(x+vertices[i][0]/16d,y+vertices[i][1]/16d, z+vertices[i][2]/16d, vertices[i][3], vertices[i][4]);
		}
	}

	public void rotate(RenderBlocks renderer, int i)
	{
		/*
		renderer.uvRotateEast = i;
		renderer.uvRotateWest = i;
		renderer.uvRotateNorth = i;
		renderer.uvRotateSouth = i;
		 */
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0.15F, 0.2F, 0.15F, 0.85F, 0.1F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);

		renderer.setRenderBounds(0.1F, 0F, 0.15F, 0.15F, 0.4F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(0.85F, 0F, 0.15F, 0.9F, 0.4F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.9F, 0.4F, 0.15F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(0.1F, 0F, 0.85F, 0.9F, 0.4F, 0.9F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}

	public static void renderInvBlock(Block block, int m, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, m));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
