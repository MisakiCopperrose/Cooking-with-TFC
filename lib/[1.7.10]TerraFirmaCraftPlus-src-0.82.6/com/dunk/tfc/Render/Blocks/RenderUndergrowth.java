package com.dunk.tfc.Render.Blocks;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Blocks.Flora.BlockLeafLitter;
import com.dunk.tfc.Blocks.Vanilla.BlockTFCFence;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Render.RenderBlocksWithRotation;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RenderUndergrowth implements ISimpleBlockRenderingHandler
{

	public static double inv16 = 1d / 16d;
	public static double inv32 = 1d / 32d;

	public float rotation = (float) (-45d * (Math.PI / 180d));
	public float rot30 = (float) (30d * (Math.PI / 180d));

	public float rot1 = (float) (Math.PI / 180d);

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks)
	{

		int meta = world.getBlockMetadata(x, y, z);
		Block left = world.getBlock(x - 1, y, z);
		if (left instanceof BlockLeafLitter)
		{
			RenderingRegistry.instance().renderWorldBlock(renderblocks, world, x, y, z, left, TFCBlocks.leafLitterRenderId);
		}
		if (block == TFCBlocks.undergrowth || block == TFCBlocks.undergrowthPalm || block == TFCBlocks.shrub)
		{
			renderblocks = new RenderBlocksWithRotation(renderblocks);
			GL11.glScaled(0.5, 0.5, 0.5);
			Tessellator.instance.setColorOpaque(200, 200, 200);
			if (block == TFCBlocks.shrub)
			{
				Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y + 1, z));
			}
			else
			{
				Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z));
			}
			renderblocks.setRenderBounds(0, 0.0D, 0, 1.5D, 2d, 1.5D);
			IIcon icon = block.getIcon(0, 0);
			double L = x % 23 + y % 25 + z % 27;
			Random r = new Random((long) L);
			r = new Random(r.nextInt(100000));
			double superScale = 1 + (r.nextInt(40) * 0.1);
			double yRotation = r.nextInt(360) * rot1;
			// renderblocks.drawCrossedSquares(icon, x, y, z, 2F);

			// Render as an alternate plant instead
			if (block == TFCBlocks.undergrowthPalm)
			{
				((RenderBlocksWithRotation) renderblocks).renderUndergrowthStem(block, x, y, z, yRotation, 0, superScale, 0);
			}
			else if (block == TFCBlocks.shrub)
			{
				superScale = Math.sqrt(superScale)  + 0.25;

				float[] modifiers = new float[6];
				for (int i = 0; i < modifiers.length; i++)
				{
					// modifiers[i] = r.nextBoolean()?0.125f *
					// r.nextFloat():-0.125f * r.nextFloat();
				}

				((RenderBlocksWithRotation) renderblocks).renderUndergrowthStem(block, x, y, z, yRotation, 0, superScale, 1);
				Vec3 leafBlockLoc = Vec3.createVectorHelper(0, 0.45 * superScale, 0);

				float temp = TFC_Climate.getHeightAdjustedTemp(Minecraft.getMinecraft().theWorld, x, y, z);
				if (temp > -5)
				{

					Vec3 leafBlockLoc2 = Vec3.createVectorHelper((modifiers[0] + 0.175) * superScale, 0.255 * superScale, (modifiers[1] + 0.17) * superScale);

					Vec3 leafBlockLoc3 = Vec3.createVectorHelper((modifiers[2] + 0.175) * superScale, 0.2525 * superScale, (modifiers[3] + 0.175) * superScale);

					Vec3 leafBlockLoc4 = Vec3.createVectorHelper((modifiers[4] + 0.175) * superScale, 0.2525 * superScale, (modifiers[5] + 0.175) * superScale);

					leafBlockLoc2.rotateAroundY((float) yRotation);
					leafBlockLoc3.rotateAroundY((float) yRotation);
					leafBlockLoc4.rotateAroundY((float) yRotation);

					float tilt1, tilt2, tilt3, tilt4;
					// tilt1 = r.nextFloat()*0.25f;
					tilt2 = r.nextFloat() * 0.25f;
					tilt3 = r.nextFloat() * 0.25f;
					tilt4 = r.nextFloat() * 0.25f;

					float rot1, rot2, rot3;
					rot1 = (r.nextFloat() * (r.nextBoolean() ? 0.25f : -0.25f)) + (float) yRotation;
					rot2 = (r.nextFloat() * (r.nextBoolean() ? 0.25f : -0.25f)) + (float) (2d / 3d * Math.PI) + (float) yRotation;
					rot3 = (r.nextFloat() * (r.nextBoolean() ? 0.25f : -0.25f)) + (float) (4d / 3d * Math.PI) + (float) yRotation;

					float leafScale1, leafScale2, leafScale3, leafScale4;
					leafScale1 = (float) (superScale / 2);
					leafScale2 = (float) (superScale / 3);
					leafScale3 = 0.75f;
					leafScale4 = (float) (superScale / 2.2);

					((RenderBlocksWithRotation) renderblocks).renderAllFaces = true;
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(leafBlockLoc.xCoord, leafBlockLoc.yCoord, leafBlockLoc.zCoord, leafBlockLoc.xCoord + leafScale1,
					leafBlockLoc.yCoord + leafScale1, leafBlockLoc.zCoord + leafScale1);
					// ((RenderBlocksWithRotation)
					// renderblocks).setOverrideBlockTexture(block.getIcon(0,
					// 1));
					// ((RenderBlocksWithRotation)
					// renderblocks).renderStandardBlock(block, x, y, z);
					((RenderBlocksWithRotation) renderblocks).clearOverrideBlockTexture();

					((RenderBlocksWithRotation) renderblocks).rotation = tilt2;
					((RenderBlocksWithRotation) renderblocks).yRotation = rot1;
					((RenderBlocksWithRotation) renderblocks).renderAllFaces = true;
					((RenderBlocksWithRotation) renderblocks).setOffset(leafBlockLoc2);
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(0.5 - leafScale2 / 2, 0.5 - leafScale2 / 2, 0.5 - leafScale2 / 2, 0.5 + leafScale2 / 2,
					0.5 + leafScale2 / 2, 0.5 + leafScale2 / 2);
					// ((RenderBlocksWithRotation)
					// renderblocks).setOverrideBlockTexture(block.getIcon(0,
					// 1));
					((RenderBlocksWithRotation) renderblocks).renderStandardBlock(block, x, y, z);
					((RenderBlocksWithRotation) renderblocks).clearOverrideBlockTexture();

					((RenderBlocksWithRotation) renderblocks).rotation = tilt3;
					((RenderBlocksWithRotation) renderblocks).yRotation = rot2;
					((RenderBlocksWithRotation) renderblocks).renderAllFaces = true;
					((RenderBlocksWithRotation) renderblocks).setOffset(leafBlockLoc3);
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(0.5 - leafScale3 / 2, 0.5 - leafScale3 / 2, 0.5 - leafScale3 / 2, 0.5 + leafScale3 / 2,
					0.5 + leafScale3 / 2, 0.5 + leafScale3 / 2);
					// ((RenderBlocksWithRotation)
					// renderblocks).setOverrideBlockTexture(block.getIcon(0,
					// 1));
					((RenderBlocksWithRotation) renderblocks).renderStandardBlock(block, x, y, z);
					((RenderBlocksWithRotation) renderblocks).clearOverrideBlockTexture();

					((RenderBlocksWithRotation) renderblocks).rotation = tilt3;
					((RenderBlocksWithRotation) renderblocks).yRotation = rot3;
					((RenderBlocksWithRotation) renderblocks).renderAllFaces = true;
					((RenderBlocksWithRotation) renderblocks).setOffset(leafBlockLoc4);
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(0.5 - leafScale4 / 2, 0.5 - leafScale4 / 2, 0.5 - leafScale4 / 2, 0.5 + leafScale4 / 2,
					0.5 + leafScale4 / 2, 0.5 + leafScale4 / 2);
					// ((RenderBlocksWithRotation)
					// renderblocks).setOverrideBlockTexture(block.getIcon(0,
					// 1));
					((RenderBlocksWithRotation) renderblocks).renderStandardBlock(block, x, y, z);
					((RenderBlocksWithRotation) renderblocks).clearOverrideBlockTexture();

					((RenderBlocksWithRotation) renderblocks).rotation = 0;
					((RenderBlocksWithRotation) renderblocks).yRotation = 0.01f;
					((RenderBlocksWithRotation) renderblocks).renderAllFaces = true;
					((RenderBlocksWithRotation) renderblocks).setOffset(leafBlockLoc);
					((RenderBlocksWithRotation) renderblocks).setRenderBounds(0.5 - leafScale2 / 2, 0.5 - leafScale2 / 2, 0.5 - leafScale2 / 2, 0.5 + leafScale2 / 2,
					0.5 + leafScale2 / 2, 0.5 + leafScale2 / 2);
					// ((RenderBlocksWithRotation)
					// renderblocks).setOverrideBlockTexture(block.getIcon(0,
					// 1));
					((RenderBlocksWithRotation) renderblocks).renderStandardBlock(block, x, y, z);
					((RenderBlocksWithRotation) renderblocks).clearOverrideBlockTexture();
					((RenderBlocksWithRotation) renderblocks).resetOffset();
				}
			}
			else
			{
				((RenderBlocksWithRotation) renderblocks).renderUndergrowthStem(block, x, y, z, yRotation, 0, superScale, 0);
				double baseScale = 0.25 * (superScale / 1.5);
				for (int n = 0; n < 8; n++)
				{
					int n3 = n >= 4 ? 1 : 0;
					double localOffsetZ = 0;

					double localYRotation =
					/*
					 * rot1*(TFC_Time.getTotalTicks()% 360);/
					 */rot1 * (r.nextInt(51) - 25);
					double localXRotation = rot1 * (r.nextInt(r.nextInt(61) + 1) - 60);
					double localZRotation = rot1 * (r.nextInt(51) - 25);
					double scale = (baseScale + (0.25 * n3)) + (r.nextInt(15) * 0.1);
					boolean doubleLeaves = r.nextInt(3) == 0;
					double offsetX = -0.5 * scale;
					double n2 = Math.max(0, (n / 2));
					if (n2 == 3)
					{
						n2 = 2.4;
					}
					if (doubleLeaves)
					{
						scale -= 0.25;
						offsetX = -0.5 * scale;
						double localScale = (baseScale + (0.25 * n3)) - 0.25 + (r.nextInt(15) * 0.1);
						double offsetX2 = -0.5 * localScale;
						localYRotation -= rot1 * 45;
						double localXRotation2 = rot1 * (r.nextInt(61) - 60);
						double localZRotation2 = rot1 * (r.nextInt(51) - 25);
						((RenderBlocksWithRotation) renderblocks).renderUndergrowthLeaves(x, y, z, 0, 0, 0, offsetX2, (superScale / 1.5) * (1.2 - (n3 * n2 * 0.3)),
						(superScale / 1.5) / Math.sqrt(2) * (0.1 + (n3 * 0.2)), localXRotation2, localYRotation + (Math.PI / 2), localZRotation2,
						yRotation + -(Math.PI / 4) + (Math.PI / 2) * n, 0, localScale);
					}

					((RenderBlocksWithRotation) renderblocks).renderUndergrowthLeaves(x, y, z, 0, 0, 0, offsetX, (superScale / 1.5) * (1.2 - (n3 * n2 * 0.3)),
					(superScale / 1.5) / Math.sqrt(2) * (0.1 + (n3 * 0.2)), localXRotation, localYRotation, localZRotation, yRotation + -(Math.PI / 4) + (Math.PI / 2) * n, 0,
					scale);
				}
			}
			Tessellator.instance.setColorOpaque(255, 255, 255);
			// Minecraft.getMinecraft().theWorld.markBlockForUpdate(x, y, z);
			GL11.glScaled(2, 2, 2);
			((RenderBlocksWithRotation) renderblocks).yRotation = 0;
			((RenderBlocksWithRotation) renderblocks).rotation = 0;
		}
		else if (block == TFCBlocks.fern)
		{
			renderblocks = new RenderBlocksWithRotation(renderblocks);
			Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z));
			((RenderBlocksWithRotation) renderblocks).enableAO = false;
			renderFern(world, x, y, z, block, modelId, renderblocks);
			((RenderBlocksWithRotation) renderblocks).enableAO = true;
			((RenderBlocksWithRotation) renderblocks).yRotation = 0;
			((RenderBlocksWithRotation) renderblocks).rotation = 0;
		}
		else
		{
			renderblocks = new RenderBlocksWithRotation(renderblocks);
			Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z));
			((RenderBlocksWithRotation) renderblocks).enableAO = false;
			((RenderBlocksWithRotation) renderblocks).renderLowUndergrowth(x, y, z);
			((RenderBlocksWithRotation) renderblocks).enableAO = true;
			((RenderBlocksWithRotation) renderblocks).yRotation = 0;
			((RenderBlocksWithRotation) renderblocks).rotation = 0;
		}
		return true;
	}

	protected void renderFern(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks)
	{
		double L = -x % 23 + y % 25 + z % 27;
		Random r = new Random((long) L);
		r = new Random(r.nextInt(100000));
		int s = (r.nextInt(6));
		int s2 = (r.nextInt(360));
		int s3 = (r.nextInt(31));
		for (int n = 0; n < 8; n++)
		{
			double angle = 0.25 * rot30 + ((n % 6) - 1) * (rot30 * 0.2d) + (rot1 * (s3)) * 0.3;
			((RenderBlocksWithRotation) renderblocks).renderPalmFrond(x, y - 0.4, z, (s2 * rot1) + (rot30 * (n / 4)) + (3 * rot30 * n), angle, 0.3 + (0.1 * s), 1, -0.5, 0, false);
		}
		((RenderBlocksWithRotation) renderblocks).rotation = 0;
		((RenderBlocksWithRotation) renderblocks).yRotation = 0;
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
