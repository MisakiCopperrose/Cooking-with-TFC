package com.dunk.tfc.Render;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Entities.EntityBarrel;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBarrelEntity extends Render {

	@Override
	public void doRender(Entity e, double x,double y, double z, float par8,float par9) 
	{
		EntityBarrel entity = (EntityBarrel)e;
		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y, z+0.5);
		Block block = TFCBlocks.barrel;
		TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
		RenderBlocks.getInstance().renderBlockAsItem(block, entity.getBarrelType(), 1F);

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
