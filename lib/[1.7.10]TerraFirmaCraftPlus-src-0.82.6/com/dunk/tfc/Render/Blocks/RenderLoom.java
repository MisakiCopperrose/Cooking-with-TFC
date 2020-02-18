package com.dunk.tfc.Render.Blocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.TileEntities.TELoom;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class RenderLoom implements ISimpleBlockRenderingHandler
{
	private static final float MIN_X = 0F;
	private static final float MAX_X = 1F;
	private static final float MIN_Y = 0F;
	private static final float MAX_Y = 1F;
	private static final float MIN_Z = 0F;
	private static final float MAX_Z = 1F;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TELoom te = (TELoom) world.getTileEntity(x, y, z);
		boolean primitive = block == TFCBlocks.primitiveLoom;
		Block materialBlock;
		if (te.loomType < 16)
		{
			materialBlock = TFCBlocks.woodSupportH;
		}
		else
		{
			materialBlock = TFCBlocks.woodSupportH2;
		}
		renderer.renderAllFaces = true;
		GL11.glPushMatrix();
		float primitiveZOffset = 0.1f;
		float primitiveXOffset = 0.125f;
		float primitiveShortArm = 0.1f;
		float primitiveSkinnyPoles = 0.02f;
		if (primitive)
		{
			renderer.overrideBlockTexture = TFCBlocks.primitiveLoom.getIcon(0, 2000);

			this.setRotatedRenderBounds(renderer, te.rotation, -0.01f, -0.001f, 0.6875f, 1.01f, 1.03f, 0.8125f);
			renderer.renderStandardBlock(Blocks.carpet, x, y, z);

			renderer.overrideBlockTexture = TFCBlocks.primitiveLoom.getIcon(1, 2000);

			this.setRotatedRenderBounds(renderer, te.rotation, 0.01f, 0.1f - primitiveSkinnyPoles * 2, 0.75f, 0.99f, 0.101f - primitiveSkinnyPoles * 2,
			0.85f - primitiveSkinnyPoles * 2);
			renderer.renderStandardBlock(Blocks.carpet, x, y, z);

			this.setRotatedRenderBounds(renderer, te.rotation, 0.01f, -0.001f, 0.75f, 0.99f, 0f, 0.85f - primitiveSkinnyPoles * 2);
			renderer.renderStandardBlock(Blocks.carpet, x, y, z);

			this.setRotatedRenderBounds(renderer, te.rotation, 0.01f, 0.94f - primitiveSkinnyPoles * 2, 0.75f, 0.99f, 0.941f - primitiveSkinnyPoles * 2,
			0.85f - primitiveSkinnyPoles * 2);
			renderer.renderStandardBlock(Blocks.carpet, x, y, z);

			this.setRotatedRenderBounds(renderer, te.rotation, 0.01f, 0.879f - primitiveSkinnyPoles * 2, 0.75f, 0.99f, 0.88f - primitiveSkinnyPoles * 2,
			0.85f - primitiveSkinnyPoles * 2);
			renderer.renderStandardBlock(Blocks.carpet, x, y, z);

			renderer.overrideBlockTexture = TFCBlocks.primitiveLoom.getIcon(2, 2000);
			// renderer.clearOverrideBlockTexture();

			this.setRotatedRenderBounds(renderer, te.rotation, 0.099f + primitiveSkinnyPoles, 0.001f, 0.6875f, 0.1f + primitiveSkinnyPoles, 1.0025f,
			0.7875f - primitiveSkinnyPoles * 2);
			renderer.renderStandardBlock(Blocks.carpet, x, y, z);

			this.setRotatedRenderBounds(renderer, te.rotation, 0.2f - primitiveSkinnyPoles, 0.001f, 0.6875f, 0.201f - primitiveSkinnyPoles, 1.0025f,
			0.7875f - primitiveSkinnyPoles * 2);
			renderer.renderStandardBlock(Blocks.carpet, x, y, z);

			this.setRotatedRenderBounds(renderer, te.rotation, 0.799f + primitiveSkinnyPoles, 0.001f, 0.6875f, 0.8f + primitiveSkinnyPoles, 1.0025f,
			0.7875f - primitiveSkinnyPoles * 2);
			renderer.renderStandardBlock(Blocks.carpet, x, y, z);

			this.setRotatedRenderBounds(renderer, te.rotation, 0.9f - primitiveSkinnyPoles, 0.001f, 0.6875f, 0.901f - primitiveSkinnyPoles, 1.0025f,
			0.7875f - primitiveSkinnyPoles * 2);
			renderer.renderStandardBlock(Blocks.carpet, x, y, z);
		}

		if (!primitive)
		{
			primitiveZOffset *= 0;
			primitiveXOffset *= 0;
			primitiveShortArm *= 0;
			primitiveSkinnyPoles *= 0;
		}
		else
		{
			renderer.overrideBlockTexture = TFCBlocks.woodSupportH.getIcon(2, 5);
		}

		// Arms
		this.setRotatedRenderBounds(renderer, te.rotation, MIN_X + 0.1F + primitiveSkinnyPoles, MIN_Y, MIN_Z + 0.75F - primitiveZOffset + primitiveSkinnyPoles * 2,
		MAX_X - 0.8F - primitiveSkinnyPoles, MAX_Y, MAX_Z - 0.15F - primitiveZOffset);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		this.setRotatedRenderBounds(renderer, te.rotation, MIN_X + 0.8F + primitiveSkinnyPoles, MIN_Y, MIN_Z + 0.75F - primitiveZOffset + primitiveSkinnyPoles * 2,
		MAX_X - 0.1F - primitiveSkinnyPoles, MAX_Y, MAX_Z - 0.15F - primitiveZOffset);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		// Arm holding sections
		// L
		this.setRotatedRenderBounds(renderer, te.rotation, MIN_X + 0.1F + primitiveSkinnyPoles, MIN_Y + 0.25F, MIN_Z + 0.5F + primitiveShortArm * 0.65f,
		MAX_X - 0.8F - primitiveSkinnyPoles, MAX_Y - 0.7F, MAX_Z - 0.25F - primitiveShortArm + primitiveSkinnyPoles * 2);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		this.setRotatedRenderBounds(renderer, te.rotation, MIN_X + 0.1F + primitiveSkinnyPoles, MIN_Y + 0.05F, MIN_Z + 0.5F + primitiveShortArm * 0.65f,
		MAX_X - 0.8F - primitiveSkinnyPoles, MAX_Y - 0.9F, MAX_Z - 0.25F - primitiveShortArm + primitiveSkinnyPoles * 2);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		// R
		this.setRotatedRenderBounds(renderer, te.rotation, MIN_X + 0.8F + primitiveSkinnyPoles, MIN_Y + 0.25F, MIN_Z + 0.5F + primitiveShortArm * 0.65f,
		MAX_X - 0.1F - primitiveSkinnyPoles, MAX_Y - 0.7F, MAX_Z - 0.25F - primitiveShortArm + primitiveSkinnyPoles * 2);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		this.setRotatedRenderBounds(renderer, te.rotation, MIN_X + 0.8F + primitiveSkinnyPoles, MIN_Y + 0.05F, MIN_Z + 0.5F + primitiveShortArm * 0.65f,
		MAX_X - 0.1F - primitiveSkinnyPoles, MAX_Y - 0.9F, MAX_Z - 0.25F - primitiveShortArm + primitiveSkinnyPoles * 2);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		// cross
		this.setRotatedRenderBounds(renderer, te.rotation, MAX_X - 0.8F - primitiveXOffset, MIN_Y + 0.8F + primitiveSkinnyPoles * 2, MIN_Z + 0.75F, MIN_X + 0.8F + primitiveXOffset,
		MAX_Y - 0.1F, MAX_Z - 0.15F - primitiveSkinnyPoles * 2);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		this.setRotatedRenderBounds(renderer, te.rotation, MAX_X - 0.8F - primitiveXOffset, 0F, MIN_Z + 0.75F, MIN_X + 0.8F + primitiveXOffset,
		MIN_Y + 0.1F - primitiveSkinnyPoles * 2, MAX_Z - 0.15F - primitiveSkinnyPoles * 2);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		rotate(renderer, 0);
		renderer.renderAllFaces = false;
		renderer.clearOverrideBlockTexture();
		GL11.glPopMatrix();
		return true;
	}

	public void rotate(RenderBlocks renderer, int i)
	{
		renderer.uvRotateEast = i;
		renderer.uvRotateWest = i;
		renderer.uvRotateNorth = i;
		renderer.uvRotateSouth = i;
	}

	private void setRotatedRenderBounds(RenderBlocks renderer, byte rotation, float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		switch (rotation)
		{
		case 0:
			renderer.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
			break;
		case 1:
			renderer.setRenderBounds(MAX_Z - maxZ, minY, minX, MAX_Z - minZ, maxY, maxX);
			break;
		case 2:
			renderer.setRenderBounds(minX, minY, MAX_Z - maxZ, maxX, maxY, MAX_Z - minZ);
			break;
		case 3:
			renderer.setRenderBounds(minZ, minY, minX, maxZ, maxY, maxX);
			break;
		default:
			break;
		}
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		Block materialBlock;
		boolean primitive = block == TFCBlocks.primitiveLoom;
		if (primitive)
		{
			renderer.overrideBlockTexture = TFCBlocks.woodSupportH.getIcon(0, 5);
		}

		if (meta < 16)
		{
			materialBlock = TFCBlocks.woodSupportH;
		}
		else
		{
			materialBlock = TFCBlocks.woodSupportH2;
		}

		GL11.glPushMatrix();
		GL11.glRotatef(180, 0, 1, 0);
		// Arms
		renderer.setRenderBounds(MIN_X + 0.1F, MIN_Y, MIN_Z + 0.75F, MAX_X - 0.8F, MAX_Y, MAX_Z - 0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		renderer.setRenderBounds(MIN_X + 0.8F, MIN_Y, MIN_Z + 0.75F, MAX_X - 0.1F, MAX_Y, MAX_Z - 0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		// Arm holding sections
		// L
		renderer.setRenderBounds(MIN_X + 0.1F, MIN_Y + 0.35F, MIN_Z + 0.6F, MAX_X - 0.8F, MAX_Y - 0.6F, MAX_Z - 0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		renderer.setRenderBounds(MIN_X + 0.1F, MIN_Y + 0.15F, MIN_Z + 0.6F, MAX_X - 0.8F, MAX_Y - 0.8F, MAX_Z - 0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		// R
		renderer.setRenderBounds(MIN_X + 0.8F, MIN_Y + 0.35F, MIN_Z + 0.6F, MAX_X - 0.1F, MAX_Y - 0.6F, MAX_Z - 0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		renderer.setRenderBounds(MIN_X + 0.8F, MIN_Y + 0.15F, MIN_Z + 0.6F, MAX_X - 0.1F, MAX_Y - 0.8F, MAX_Z - 0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		// cross
		renderer.setRenderBounds(MAX_X - 0.8F, MIN_Y + 0.8F, MIN_Z + 0.75F, MIN_X + 0.8F, MAX_Y - 0.1F, MAX_Z - 0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		renderer.setRenderBounds(MAX_X - 0.8F, 0F, MIN_Z + 0.75F, MIN_X + 0.8F, MIN_Y + 0.1F, MAX_Z - 0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		rotate(renderer, 0);
		renderer.clearOverrideBlockTexture();
		GL11.glPopMatrix();
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

	public static void renderInvBlockHoop(Block block, int m, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(10, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(11, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(12, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(13, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(14, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(15, m));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
