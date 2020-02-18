package com.dunk.tfc.Render.Blocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Blocks.Flora.BlockFruitLeaves;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Food.FloraManager;
import com.dunk.tfc.Food.ItemFoodTFC;
import com.dunk.tfc.Items.ItemFruitTreeSapling;
import com.dunk.tfc.Render.RenderBlocksWithRotation;
import com.dunk.tfc.TileEntities.TEBarrel;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderVessel implements ISimpleBlockRenderingHandler
{
	private static final float MIN = 0.2F;
	private static final float MAX = 0.8F;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TEBarrel te = (TEBarrel) world.getTileEntity(x, y, z);
		renderer.renderAllFaces = true;
		float yOffset = 0;
		int distillationMode = -1;
		if(world.getBlock(x, y-1, z) == TFCBlocks.firepit)
		{
			yOffset = -0.85f;
			distillationMode = te.getDistillationMode();
		}
		if((te.rotation & -128) == 0)
		{
			if(te.getSealed())
			{
				renderer.setRenderBounds(MIN-0.025F, yOffset + 0.55f, MIN-0.025F, MAX+0.025F,  yOffset + 0.65F, MAX+0.025F);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.4375f, yOffset +  0.65f, 0.4375f, 0.5625F, yOffset +  0.7F, 0.5625F);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(MIN+0.05F,  yOffset + 0, MIN+0.05F, MAX-0.05F, yOffset +  0.05F, MAX-0.05F);
				renderer.renderStandardBlock(block, x, y, z);
			}
			else
			{
				boolean fruitSapling = true;
				for(int i = 0; i <9 && fruitSapling;i++)
				{
					if(te.storage[i] != null)
					{
						if(i!=4)
						{
							if(!TFC_Core.isDirt(Block.getBlockFromItem(te.storage[i].getItem())))
							{
								fruitSapling = false;
							}
						}
						else if (!(te.storage[i].getItem() instanceof ItemFruitTreeSapling))
						{
							fruitSapling = false;
						}
					}
					else
					{
						fruitSapling = false;
					}
				}
				if(fruitSapling)
				{
					renderer = new RenderBlocksWithRotation(renderer);
					((RenderBlocksWithRotation)renderer).rotation = 0;
					IIcon fSap = BlockFruitLeaves.getSaplingIcon(te.storage[4].getItemDamage());
					//IIcon fSap = te.storage[4].getItem().getIconFromDamage(te.storage[4].getItemDamage());
					((RenderBlocksWithRotation)renderer).drawCrossedSquares(fSap, x, y+0.3, z, 1);
					((RenderBlocksWithRotation) renderer).yRotation = 0;
					((RenderBlocksWithRotation) renderer).rotation = 0;
				}
				
				renderer.setRenderBounds(MIN+0.05F,  yOffset + 0, MIN+0.05F, MAX-0.05F, yOffset +  0.05F, MAX-0.05F);
				renderer.renderStandardBlock(block, x, y, z);

				if(te.fluid != null && renderer.overrideBlockTexture == null)
				{
					int color = te.fluid.getFluid().getColor(te.fluid);
					float f = (color >> 16 & 255) / 255.0F;
					float f1 = (color >> 8 & 255) / 255.0F;
					float f2 = (color & 255) / 255.0F;
					float h = 0.5f*((float)te.fluid.amount/(float)te.getMaxLiquid());
					renderer.setRenderBounds(MIN+0.05F, yOffset+ 0.05, MIN+0.05F, MAX-0.05F, yOffset+0.05f+h, MAX-0.05F);
					IIcon still = te.fluid.getFluid().getStillIcon();
					renderer.setOverrideBlockTexture(still);
					renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, f, f1, f2);
					renderer.clearOverrideBlockTexture();
				}
			}
			renderer.setRenderBounds(MIN,  yOffset + 0F, MIN+0.05F, MIN+0.05F,  yOffset + 0.6F, MAX-0.05F);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(MAX-0.05F, yOffset +  0F, MIN+0.05F, MAX, yOffset +  0.6F, MAX-0.05F);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(MIN,  yOffset + 0F, MIN, MAX,  yOffset + 0.6F, MIN+0.05F);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(MIN,  yOffset + 0F, MAX-0.05F, MAX, yOffset +  0.6F, MAX);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(MIN+0.05F, yOffset +  0.05, MIN+0.05F, MAX-0.05F, yOffset +  0.1f, MAX-0.05F);
			renderer.renderStandardBlock(block, x, y, z);
		}
		else
		{
			if((te.rotation & 3) == 0)
			{
				renderer.setRenderBounds(MIN,  yOffset + MIN, MIN+0.05F, 0.95F,  yOffset + MIN+0.05F, MAX-0.05F);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if((te.rotation & 3) == 1)
			{
				renderer.setRenderBounds(MIN+0.05F,  yOffset + MIN, MIN,MAX-0.05F, yOffset +  MIN+0.05F, 0.95F);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		if(distillationMode != -1)
		{
			renderer = new RenderBlocksWithRotation(renderer);			
			((RenderBlocksWithRotation)renderer).rotation = 1.25f;
			((RenderBlocksWithRotation)renderer).yRotation = (2-(distillationMode+1) )* (float) (Math.PI/2d);
			int x1,z1;
			switch(distillationMode)
			{
			case 0:
				x1 = 1; z1=0; break;
			case 1:
				x1 = 0; z1=1; break;
			case 2:
				x1 = -1; z1=0; break;
			case 3:
				x1 = 0; z1=-1; break;
				default:x1=0;z1=0;
			}
			if(world.getBlock(x +x1, y - 2, z+z1) != TFCBlocks.firepit)
			{
				renderer.setRenderBounds(0.45F, -0.6F, -0.2F, 0.55F, 0F, -0.1F);
			}
			else
			{
				((RenderBlocksWithRotation)renderer).rotation = 0.5f;
				renderer.setRenderBounds(0.45F, -1.5F, 0.3F, 0.55F, -0.3F, 0.4F);
			
			}
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
		renderer.setRenderBounds(MIN-0.025F, 0.55f, MIN-0.025F, MAX+0.025F, 0.65F, MAX+0.025F);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(0.4375f, 0.65f, 0.4375f, 0.5625F, 0.7F, 0.5625F);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(MIN, 0F, MIN, MAX, 0.6F, MAX);
		renderInvBlock(block, meta, renderer);

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
