package com.dunk.tfc.Render.Blocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.TileEntities.TEBarrel;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderDrum implements ISimpleBlockRenderingHandler
{
	private static final float MIN = 0.2F;
	private static final float MAX = 0.8F;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer)
	{
		renderer.renderAllFaces = true;

		if (block == TFCBlocks.bigDrum)
		{
			renderer.setRenderBounds(MIN - 0.1f, 0.F, MIN - 0.1f, MAX + 0.1f, 0.9F, MAX + 0.1f);
			renderer.renderStandardBlock(block, x, y, z);
		}
		else
		{
			renderer.setRenderBounds(MIN, 0.F, MIN, MAX, 0.65F, MAX);
			renderer.renderStandardBlock(block, x, y, z);
		}
		renderer.renderAllFaces = false;

		return true;
	}

	public void rotate(RenderBlocks renderer, int i)
	{
		renderer.uvRotateEast = i;
		renderer.uvRotateWest = i;
		renderer.uvRotateNorth = i;
		renderer.uvRotateSouth = i;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		
		if (block == TFCBlocks.bigDrum)
		{
			renderer.setRenderBounds(MIN - 0.1f, 0.F, MIN - 0.1f, MAX + 0.1f, 0.9F, MAX + 0.1f);
			renderInvBlock(block, meta, renderer);
		}
		else
		{
			renderer.setRenderBounds(MIN, 0.F, MIN, MAX, 0.65F, MAX);
			renderInvBlock(block, meta, renderer);
		}
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
