package com.dunk.tfc.Render.Blocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Render.RenderBlocksWithRotation;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderWoodSpear implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer)
	{
		int meta = world.getBlockMetadata(x, y, z);

		renderer = new RenderBlocksWithRotation(renderer);
		if(meta > 0)
		{
			renderer.setRenderBounds(0.45, -1.5, 0.45, 0.55, 5, 0.55);
			((RenderBlocksWithRotation)renderer).rotation = ((RenderBlocksWithRotation)renderer).rot1 * 25f;
		}
		else
		{
			renderer.setRenderBounds(0.45, -1.5, 0.45, 0.55, 3, 0.55);
		}
		GL11.glDisable(GL11.GL_CULL_FACE);
		renderer.renderAllFaces = true;
		((RenderBlocksWithRotation)renderer).yRotation += ((RenderBlocksWithRotation)renderer).rot45 * ((meta*2)+3);
		((RenderBlocksWithRotation)renderer).staticTexture = true;
		
		
		renderer.setOverrideBlockTexture(TFCBlocks.woodSupportH.getIcon(0, 0));
		renderer.renderStandardBlock(block, x, y+1, z);
		renderer.clearOverrideBlockTexture();
		renderer.renderAllFaces = false;
		GL11.glEnable(GL11.GL_CULL_FACE);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
