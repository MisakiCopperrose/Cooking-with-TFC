package com.dunk.tfc.Render.Blocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Render.RenderBlocksWithRotation;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderBeehive implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer)
	{
		if (block == TFCBlocks.beehive)
		{
			GL11.glPushMatrix();
			renderer.setRenderBounds(0, 0, 0, 1, 0.75, 1);
			renderInvBlock(block, 3, renderer);
			
			renderer.setRenderBounds(0.1, 0.75, 0.1, 0.9, 0.9, 0.9);
			renderInvBlock(block, meta, renderer);
			
			renderer.setRenderBounds(0.2, 0.9, 0.2, 0.8, 1, 0.8);
			renderInvBlock(block, meta, renderer);
			
			GL11.glPopMatrix();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (block == TFCBlocks.beehive)
		{
			renderer.renderAllFaces = true;
			renderer.setRenderBounds(0.05, 0f, 0.05, 0.95, 0.7, 0.95);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.1f, 0.7f, 0.1f, 0.9f, 0.9f, 0.9f);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.2f, 0.9f, 0.2f, 0.8f, 1f, 0.8f);
			renderer.renderStandardBlock(block, x, y, z);
		}
		else if (block == TFCBlocks.wildBeehive)
		{
			renderer = new RenderBlocksWithRotation(renderer);
			((RenderBlocksWithRotation) renderer).staticTexture = true;
			renderer.renderAllFaces = true;

			renderer.setRenderBounds(0.25, 0.8f, 0.35, 0.35, 1.25, 0.7);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.375, 0.7f, 0.3, 0.475, 1.25, 0.7);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.5, 0.75f, 0.325, 0.6, 1.25, 0.75);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.625, 0.8f, 0.3, 0.725, 1.25, 0.7);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.25, 1.25f, 0.25, 0.75, 1.5, 0.75);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.35, 1.15f, 0.15, 0.65, 1.5, 0.35);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.35, 1.15f, 0.65, 0.65, 1.5, 0.85);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.15, 1.15f, 0.35, 0.35, 1.5, 0.65);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.65, 1.15f, 0.35, 0.85, 1.5, 0.65);
			renderer.renderStandardBlock(block, x, y, z);
		}
		else if (block == TFCBlocks.wildTreehive)
		{
			if (meta == 2)
			{
				renderer.setRenderBounds(0.3, 0.9f, 0.85, 0.45, 1, 1);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.45, 0.85f, 0.85, 0.7, 0.95, 1);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.3, 0f, 0.85, 0.6, 0.15, 1);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.5, 0.1f, 0.9, 0.7, 0.25, 1);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.2, 0.1, 0.85, 0.325, 0.9, 1);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.7, 0.175, 0.85, 0.85, 0.9, 1);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.3, 0.11, 0.95, 0.7, 0.95, 0.95);
				renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 10));
				renderer.renderStandardBlock(block, x, y, z);
				renderer.clearOverrideBlockTexture();
			}

			if (meta == 3)
			{
				renderer.setRenderBounds(0.3, 0.9, 1 - 1, 0.45, 1, 1 - 0.85);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.45, 0.85, 1 - 1, 0.7, 0.95, 1 - 0.85);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.3, 0, 1 - 1, 0.6, 0.15, 1 - 0.85);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.5, 0.1, 1 - 1, 0.7, 0.25, 1 - 0.9);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.2, 0.1, 1 - 1, 0.325, 0.9, 1 - 0.85);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.7, 0.175, 1 - 1, 0.85, 0.9, 1 - 0.85);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.3, 0.11, 1 - 0.95, 0.7, 0.95, 1 - 0.95);
				renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 10));
				renderer.renderStandardBlock(block, x, y, z);
				renderer.clearOverrideBlockTexture();
			}

			if (meta == 4)
			{
				renderer.setRenderBounds(0.85, 0.9, 0.3, 1, 1, 0.45);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.85, 0.85, 0.45, 1, 0.95, 0.7);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.85, 0, 0.3, 1, 0.15, 0.6);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.9, 0.1, 0.5, 1, 0.25, 0.7);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.85, 0.1, 0.2, 1, 0.9, 0.325);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.85, 0.175, 0.7, 1, 0.9, 0.85);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.95, 0.11, 0.3, 0.95, 0.95, 0.7);
				renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 10));
				renderer.renderStandardBlock(block, x, y, z);
				renderer.clearOverrideBlockTexture();
			}

			if (meta == 5)
			{
				renderer.setRenderBounds(1 - 1, 0.9, 0.3, 1 - 0.85, 1, 0.45);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(1 - 1, 0.85, 0.45, 1 - 0.85, 0.95, 0.7);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(1 - 1, 0, 0.3, 1 - 0.85, 0.15, 0.6);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(1 - 1, 0.1, 0.5, 1 - 0.9, 0.25, 0.7);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(1 - 1, 0.1, 0.2, 1 - 0.85, 0.9, 0.325);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(1 - 1, 0.175, 0.7, 1 - 0.85, 0.9, 0.85);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(1 - 0.95, 0.11, 0.3, 1 - 0.95, 0.95, 0.7);
				renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 10));
				renderer.renderStandardBlock(block, x, y, z);
				renderer.clearOverrideBlockTexture();
			}

		}
		return true;
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

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getRenderId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
