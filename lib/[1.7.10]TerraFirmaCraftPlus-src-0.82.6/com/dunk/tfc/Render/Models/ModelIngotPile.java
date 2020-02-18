package com.dunk.tfc.Render.Models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelIngotPile extends ModelBase
{
	public ModelRendererTFC[] renderer = new ModelRendererTFC[64];
	//public ModelRendererTFC renderer;
	
	//public ModelRendererTFC renderer2;
	
	public ModelIngotPile()
	{
		
		for (int n = 0; n < 64; n++){
			this.renderer[n] = new ModelRendererTFC(this,0,0);
			int m = (n+8)/8;
			float x = (n %4)*0.25f;
			float y = (m -1)*0.125f;
			float z = 0;

			if (n%8 >=4) z = .5F;
			
			renderer[n].cubeList.add(new ModelIngot(renderer[n],renderer[n].textureOffsetX, renderer[n].textureOffsetY));
			renderer[n].offsetY = y;
			if (m %2 == 1) {
				renderer[n].rotateAngleY = 1.56F;
				renderer[n].offsetX = x;
				renderer[n].offsetZ = z+.5F;
			} else {
				renderer[n].offsetX = z;
				renderer[n].offsetZ = x;
			}
		}
		/*
		renderer = new ModelRendererTFC(this,0,0);
		float x = 8;
		float y = 8;
		float z = 8;
		int baseHeight = 4;
		int baseWidth = 7;
		int baseDepth = 15;
		Object[] basicVesselData = new Object[]{
				new float[]{0.5F + x, y, z + 0.5f,8,0+0.01f,8,4},
				new float[]{0.5F + x, y, z + 0.5f,8,1,8,8},
				new float[]{0.5F + x, y, z + 0.5f,8,3,8,10},
				new float[]{0.5F + x, y, z + 0.5f,8,6,8,10},
				new float[]{0.5F + x, y, z + 0.5f,8,8,8,8},
				new float[]{0.5F + x, y, z + 0.5f,8,10,8,3},
				new float[]{0.5F + x, y, z + 0.5f,8,14,8,3},
				new float[]{0.5F + x, y, z + 0.5f,8,14,8,2f}
		};
		renderer.cubeList.add(
				new ModelPotteryBase(renderer,renderer.textureOffsetX, renderer.textureOffsetY, 5.5F + x, y, z + 5.5f, baseWidth, baseHeight, baseDepth, 0f,basicVesselData,false));
	
	*/
	
	}

	public void renderIngots(int i)
	{
		for (int n = 0; n < i; n++)
		{
			renderer[n].render(0.0625F / 2F);
		}
		//renderer.render(0.0625f/2f);
	}
}
