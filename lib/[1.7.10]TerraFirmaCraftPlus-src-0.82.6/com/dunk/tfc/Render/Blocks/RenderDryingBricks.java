package com.dunk.tfc.Render.Blocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Items.ItemMudBrick;
import com.dunk.tfc.Render.RenderBlocksWithRotation;
import com.dunk.tfc.TileEntities.TEDryingBricks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public class RenderDryingBricks implements ISimpleBlockRenderingHandler
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
		if (world.getTileEntity(x, y, z) == null)
		{
			return false;
		}
		TEDryingBricks te = (TEDryingBricks) world.getTileEntity(x, y, z);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		renderer.renderAllFaces = true;
		renderer = new RenderBlocksWithRotation(renderer);
		((RenderBlocksWithRotation) renderer).yRotation = 0;
		((RenderBlocksWithRotation) renderer).rotation = 0;
		for (int i = 0; i < te.storage.length / 2; i++)
		{
			for (int j = 0; j < te.storage.length / 2; j++)
			{
				int n = i * 2 + j;
				if (te.storage[n] != null)
				{
					ItemStack is = te.storage[n];
					if (is != null)
					{
						float r,g,b;
						
						if (is.getItemDamage() >= 32)
						{
							r = g = b = 0.5f;
						}
						else
						{
							r = g = b =0.8f;
						}
						((RenderBlocksWithRotation) renderer).staticTexture = true;
						renderer.setRenderBounds(0.1 + 0.5 * j, 0, 0.05  + 0.5 * i, 0.4 + 0.5 * j, 0.25, 0.45  + 0.5 * i);
						renderer.setOverrideBlockTexture(block.getIcon(0, is.getItemDamage()%32));
						renderer.renderStandardBlockWithColorMultiplier(ItemMudBrick.getBlockFromBrick(is), x, y, z,r,g,b);
					}
				}
			}
		}
		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glEnable(GL11.GL_CULL_FACE);
		renderer.clearOverrideBlockTexture();
		GL11.glPopMatrix();
		renderer.renderAllFaces = false;
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
