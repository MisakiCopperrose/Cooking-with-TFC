package com.dunk.tfc.Render.Models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelPalmTree extends ModelBase
{
	public ModelRendererTFC[] renderer = new ModelRendererTFC[24];

	public ModelPalmTree()
	{
		int i;
		int j;
		int j2;
		for (int n = 0; n < renderer.length; n++){
			this.renderer[n] = new ModelRendererTFC(this,0,0);
			i = n%8;
			j = n/8;
			j2 = n%3;
			renderer[n].cubeList.add(new ModelPalmFrond(renderer[n],renderer[n].textureOffsetX, renderer[n].textureOffsetY));
			renderer[n].rotateAngleX = (float) (((float)j)*(Math.PI/6d)) + (float)(j2 * (Math.PI/32d) * (n%2 == 0? -1f :1f)) - (float)(Math.PI/8d);
			renderer[n].rotateAngleY = (float) (((float)i)*(Math.PI/4d)) + (float) (((float)j)*(Math.PI/16d)) ;
		}
	}

	public void renderFronds()
	{

		for (int n = 0; n < renderer.length; n++)
		{
			renderer[n].render(0.0625F / 2F);
		}
	}
}
