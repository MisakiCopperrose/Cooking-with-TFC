package com.dunk.tfc.Render.Blocks;

import java.util.Random;

import com.dunk.tfc.Render.RenderBlocksWithRotation;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderLeafLitter implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		// TODO Auto-generated method stub

	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderblocks)
	{
		renderblocks.setRenderBounds(0, 0.0D, 0, 1.0D, 0.0125D, 1D);
		float red = 1F;
		float green = 1F;
		float blue = 1F;

		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0 || true)
		{
			renderblocks = new RenderBlocksWithRotation(renderblocks);
			int l = block.colorMultiplier(world, x, y, z);
			float f = (float) (l >> 16 & 255) / 255.0F;
			float f1 = (float) (l >> 8 & 255) / 255.0F;
			float f2 = (float) (l & 255) / 255.0F;

			if (EntityRenderer.anaglyphEnable)
			{
				float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
				float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
				float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
				f = f3;
				f1 = f4;
				f2 = f5;
			}
			Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z));
			IIcon layer5 = TFCBlocks.leafLitter.getIcon(0, 5);
			Tessellator.instance.setColorOpaque_F(f, f1, f2);
			((RenderBlocksWithRotation) renderblocks).renderQuad(layer5, x, y + 0.005, z, 1);
			if (Minecraft.isFancyGraphicsEnabled())
			{
				Random rand = new Random(x + (y << 16) + (z << 8));
				// rand = new Random(rand.nextInt(10000));

				IIcon layer1 = TFCBlocks.leafLitter.getIcon(0, 0);

				IIcon layer2 = TFCBlocks.leafLitter.getIcon(0, 2);

				IIcon layer3 = TFCBlocks.leafLitter.getIcon(0, 3);

				IIcon layer4 = TFCBlocks.leafLitter.getIcon(0, 4);

				
				float tempR, tempG, tempB;
				tempR = rand.nextFloat() * 0.125f;
				tempG = rand.nextFloat() * 0.125f;
				tempB = rand.nextFloat() * 0.025f;

				if (rand.nextBoolean())
				{
					Tessellator.instance.setColorOpaque_F(f * (0.75f + tempR), f1 * (0.75f + tempG),
							f2 * (0.75f + tempB));
					((RenderBlocksWithRotation) renderblocks).renderQuad(layer4, x, y + 0.025, z, 1);
				}
				if (rand.nextBoolean())
				{
					Tessellator.instance.setColorOpaque_F(f, f1, f2);
					((RenderBlocksWithRotation) renderblocks).renderQuad(layer3, x, y + 0.04, z, 1);
				}

				Tessellator.instance.setColorOpaque_F(f * (0.875f + tempR), f1 * (0.875f + tempG),
						f2 * (0.875f + tempB));
				((RenderBlocksWithRotation) renderblocks).renderQuad(layer2, x, y + 0.06, z, 1);
				if (rand.nextBoolean())
				{
					Tessellator.instance.setColorOpaque_F(f * (0.8f + tempR), f1 * (0.8f + tempG), f2 * (0.8f + tempB));
					((RenderBlocksWithRotation) renderblocks).renderQuad(layer1, x, y + 0.08, z, 1);
				}
			}
			((RenderBlocksWithRotation) renderblocks).yRotation = 0;
			((RenderBlocksWithRotation) renderblocks).rotation = 0;
		}
		// else
		// {
		// renderblocks.renderStandardBlock(block, x, y, z);
		// }
		return false;

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
