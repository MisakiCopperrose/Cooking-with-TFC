package com.dunk.tfc.Render.Blocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.TileEntities.TEChimney;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderChimney implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		// TODO Auto-generated method stub
		renderer.setRenderBounds(0, 0, 0, 0.3, 1, 1);
		renderInvBlock(block, metadata,renderer);
		renderer.setRenderBounds(0.7, 0, 0, 1, 1, 1);
		renderInvBlock(block, metadata,renderer);
		renderer.setRenderBounds(0.3, 0, 0, 0.7, 1, 0.3);
		renderInvBlock(block, metadata,renderer);
		renderer.setRenderBounds(0.3, 0, 0.7, 0.7, 1,1);
		renderInvBlock(block, metadata,renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer)
	{
		// TODO Auto-generated method stub
		renderer.renderAllFaces = true;
		renderer.setRenderBounds(0, 0, 0, 0.3, 1, 1);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.7, 0, 0, 1, 1, 1);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.3, 0, 0, 0.7, 1, 0.3);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.3, 0, 0.7, 0.7, 1,1);
		renderer.renderStandardBlock(block, x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TEChimney)
		{
			if(((TEChimney)te).onFire > 0 && world.isAirBlock(x, y+1, z))
			{
				renderer.setRenderBounds(0.2, 0.2, 0.2, 0.8, 1.2, 0.8);
				//???
			}
		}
		renderer.renderAllFaces = false;
		return true;
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
