package com.dunk.tfc.Render.Models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelRendererAnimalHead extends ModelRenderer
{
	protected float headScale;
	protected float ageScale;
	protected float percentGrown;
	
	public ModelRendererAnimalHead(ModelBase p_i1174_1_, int p_i1174_2_, int p_i1174_3_)
	{
		super(p_i1174_1_, p_i1174_2_, p_i1174_3_);
		// TODO Auto-generated constructor stub
		headScale =1f;
		ageScale = 1f;
		percentGrown = 1f;
	}
	
	public void  setScalesPercent(float hScale, float aScale,  float percent)
	{
		this.headScale = hScale;
		this.ageScale = aScale;
		this.percentGrown = percent;
	}

	@Override
	public void render(float partialTick)
	{
		if(isHidden)
		{
			return;
		}
		GL11.glPushMatrix ();
		GL11.glTranslatef(0.0F, -(0.75f - (0.75f * percentGrown)), 0f);
		GL11.glScalef(ageScale, ageScale, ageScale);
		GL11.glTranslatef(0.0F, 0.3f - (0.3f * percentGrown), 0f);
		GL11.glScalef(headScale, headScale, headScale);
		GL11.glTranslatef (0.0F, (ageScale-1)*-0.125f,0.1875f-(0.1875f*percentGrown));
		super.render(partialTick);
		GL11.glPopMatrix();
	}
}
