package com.dunk.tfc.Render.Models;

import com.dunk.tfc.TileEntities.TEMetalSheet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelMetalSheet extends ModelBase
{
	public ModelRendererTFC[] renderer = new ModelRendererTFC[6];

	float quartRotation = (float) (Math.PI / 2d);

	public ModelMetalSheet()
	{
		/*
		 * for (int n = 0; n < 6; n++){ this.renderer[n] = new
		 * ModelRendererTFC(this,0,0); int m = (n+8)/8; float x = 0;//(n
		 * %4)*0.25f; float y = 0;//(m -1)*0.125f; float z = 0;
		 * 
		 * //if (n%8 >=4) z = .5F;
		 * 
		 * renderer[n].cubeList.add(new
		 * ModelSheet(renderer[n],renderer[n].textureOffsetX,
		 * renderer[n].textureOffsetY)); renderer[n].offsetY = y; if (m %2 == 1)
		 * { renderer[n].rotateAngleY = 0;//1.56F; renderer[n].offsetX = x;
		 * renderer[n].offsetZ = z;//+.5F; } else { renderer[n].offsetX = z;
		 * renderer[n].offsetZ = x; } }
		 */
		for (int n = 0; n < 6; n++)
		{
			this.renderer[n] = new ModelRendererTFC(this, 0, 0);
		}

		// bottom
		int n = 0;
		renderer[n].cubeList.add(new ModelSheet(renderer[n], renderer[n].textureOffsetX, renderer[n].textureOffsetY));

		// top
		n = 1;
		renderer[n].cubeList.add(new ModelSheet(renderer[n], renderer[n].textureOffsetX, renderer[n].textureOffsetY));
		renderer[n].offsetY = 1f - 0.0625f;

		// left
		n = 2;
		renderer[n].cubeList.add(new ModelSheet(renderer[n], renderer[n].textureOffsetX, renderer[n].textureOffsetY));
		renderer[n].rotateAngleZ = quartRotation;
		renderer[n].offsetX = 0.0625f;

		// right
		n = 3;
		renderer[n].cubeList.add(new ModelSheet(renderer[n], renderer[n].textureOffsetX, renderer[n].textureOffsetY));
		renderer[n].rotateAngleZ = -quartRotation;
		renderer[n].offsetY = 1;
		renderer[n].offsetX = 1f - 0.0625f;

		// back
		n = 4;
		renderer[n].cubeList.add(new ModelSheet(renderer[n], renderer[n].textureOffsetX, renderer[n].textureOffsetY));
		renderer[n].rotateAngleX = quartRotation;
		renderer[n].offsetY = 1;

		// front
		n = 5;
		renderer[n].cubeList.add(new ModelSheet(renderer[n], renderer[n].textureOffsetX, renderer[n].textureOffsetY));
		renderer[n].rotateAngleX = -quartRotation;
		renderer[n].offsetZ = 1f;
	}

	public void renderIngots(int i, TEMetalSheet te)
	{
		if (te.metalID == 10)// lead
		{
			if (te.bottomExists())
			{
				renderer[0].render(0.0625F / 1F);
			}
			if (te.topExists())
			{
				renderer[1].render(0.0625F / 1F);
			}
			if (te.eastExists())
			{
				renderer[2].render(0.0625F / 1F);
			}
			if (te.westExists())
			{
				renderer[3].render(0.0625F / 1F);
			}
			if (te.northExists())
			{
				renderer[4].render(0.0625F / 1F);
			}
			if (te.southExists())
			{
				renderer[5].render(0.0625F / 1F);
			}
		}
	}
}
