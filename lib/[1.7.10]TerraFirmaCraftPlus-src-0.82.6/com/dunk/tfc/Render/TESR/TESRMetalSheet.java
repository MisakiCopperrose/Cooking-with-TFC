package com.dunk.tfc.Render.TESR;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Render.Models.ModelMetalSheet;
import com.dunk.tfc.TileEntities.TEMetalSheet;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

public class TESRMetalSheet extends TESRBase
{
	/** The normal small chest model. */
	private final ModelMetalSheet sheetModel = new ModelMetalSheet();

	public void renderTileEntityIngotPileAt(TEMetalSheet tep, double d, double d1, double d2, float f)
	{
		Block block = tep.getBlockType();
		if (tep.getWorldObj() != null && block == TFCBlocks.metalSheet && tep.metalID == 10) //lead
		{
			int i = 6;
			TFC_Core.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/metal/" + "lead" + ".png")); //texture

			GL11.glPushMatrix(); //start
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size
			sheetModel.renderIngots(i, tep);
			GL11.glPopMatrix(); //end
		}
	}
	
	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess access = renderblocks.blockAccess;
		TEMetalSheet te = (TEMetalSheet)access.getTileEntity(i, j, k);

		double yMax = 1;
		double yMin = 0;
		double f0 = 0.0625;
		double f1 = 0.9375;

		if(te.bottomExists())
		{
			renderblocks.setRenderBounds(0, 0, 0, 1, f0, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
			yMin = 0.0625;
		}
		if(te.topExists())
		{
			renderblocks.setRenderBounds(0, f1, 0, 1, 1, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
			yMax = 0.9375;
		}
		if(te.northExists())
		{
			renderblocks.setRenderBounds(0, yMin, 0, 1, yMax, f0);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.southExists())
		{
			renderblocks.setRenderBounds(0, yMin, f1, 1, yMax, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.eastExists())
		{
			renderblocks.setRenderBounds(0, yMin, 0, f0, yMax, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.westExists())
		{
			renderblocks.setRenderBounds(f1, yMin, 0, 1, yMax, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
		}

		return true;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityIngotPileAt((TEMetalSheet) te, par2, par4, par6, par8);
	}
}
