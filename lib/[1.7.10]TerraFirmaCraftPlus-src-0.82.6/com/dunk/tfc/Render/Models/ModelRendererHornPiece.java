package com.dunk.tfc.Render.Models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelRendererHornPiece extends ModelRenderer
{
	ModelRenderer[] hornSources;
	int originalPiece;
	int pieceOffset;

	public ModelRendererHornPiece(ModelBase p_i1173_1_)
	{
		super(p_i1173_1_);
		// TODO Auto-generated constructor stub
	}

	public ModelRendererHornPiece(ModelBase base, int a, int b)
	{
		super(base, a, b);
	}

	public void setOriginal(int i)
	{
		this.originalPiece = i;
	}

	public void setOffset(int o)
	{
		this.pieceOffset = o;
	}

	@Override
	public void render(float partialTick)
	{
		if (isHidden)
		{
			return;
		}
		// this.cubeList.clear();
		// this.cubeList.add(hornSources[(originalPiece + pieceOffset +
		// 10)%hornSources.length].cubeList.get(0));
		if (hornSources != null && hornSources.length >= 0)
		{
			int renderIndex = (originalPiece + pieceOffset) % hornSources.length;
			GL11.glPushMatrix();
			GL11.glTranslatef(this.rotationPointX * partialTick, this.rotationPointY * partialTick,
					this.rotationPointZ * partialTick);

			if (this.rotateAngleZ != 0.0F)
			{
				GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
			}

			if (this.rotateAngleY != 0.0F)
			{
				GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
			}

			if (this.rotateAngleX != 0.0F)
			{
				GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
			}
			hornSources[renderIndex].render(partialTick);
			GL11.glPopMatrix();
		}
		super.render(partialTick);
	}
}
