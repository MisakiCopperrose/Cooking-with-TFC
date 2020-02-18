package com.dunk.tfc.Render.Item;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class MudBrickItemRenderer implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		if(ItemRenderType.EQUIPPED == type)
		{
			GL11.glTranslatef(0.5f, 0.25f, 0.5f);
		}
		// TODO Auto-generated method stub
		RenderBlocks renderer = (type==ItemRenderType.INVENTORY?(RenderBlocks)data[0]:RenderBlocks.getInstance());
		renderer.setRenderBounds(0.2, 0.2, 0, 0.8, 0.7, 1);
		Block toRender = TFCBlocks.dryingBricks;
		
		boolean wet = item.getItemDamage() >= 32;
		
		TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
		renderInvBlock(toRender,(item.getItemDamage())%32, renderer, wet);
		TFC_Core.bindTexture(TextureMap.locationItemsTexture);
		GL11.glPopMatrix();
	}
	
	public static void renderInvBlock(Block block, int meta, RenderBlocks renderer, boolean wet)
	{
		GL11.glPushMatrix();
		Tessellator var14 = Tessellator.instance;
		if(wet)
		{
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			
		}
		else
		{
			GL11.glColor3f(0.8f, 0.8f, 0.8f);
		}
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glPopMatrix();
	}

}
