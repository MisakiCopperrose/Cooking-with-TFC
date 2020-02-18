package com.dunk.tfc.Render;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Entities.Mobs.EntityPigTFC;
import com.dunk.tfc.Entities.Mobs.EntitySheepTFC;
import com.dunk.tfc.api.Entities.IAnimal;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class RenderSheepTFC extends RenderSheep
{
	private static final ResourceLocation MOUFLON_TEXTURE = new ResourceLocation(Reference.MOD_ID,
			"textures/mob/mouflon.png");
	private static final ResourceLocation SHEEP_TEXTURE = new ResourceLocation(Reference.MOD_ID,
			"textures/mob/sheep.png");

	public RenderSheepTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par2ModelBase, par3);
		this.setRenderPassModel(par2ModelBase);
	}

	protected ResourceLocation getTexture(IAnimal entity)
	{
		float percent = TFC_Core.getPercentGrown(entity);
		//percent = (TFC_Time.getTotalTicks()%1000)*0.003f;
		//percent = Math.min(1f, percent);
		if(!((IAnimal)entity).isDomesticated()){
			return MOUFLON_TEXTURE;
		}
		else{
			return SHEEP_TEXTURE;
		}
	}
	
	protected int setWoolColorAndRender(EntitySheepTFC par1EntitySheep, int par2, float par3)
	{
		return -1;

	}

	protected ResourceLocation getTexture(EntitySheep entity)
	{
		return getTexture((IAnimal)entity);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		float scale = ((EntitySheepTFC) par1EntityLivingBase).getSizeMod() / 2 + 0.5f;
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getTexture((EntitySheep) par1Entity);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.shadowSize = 0.35f + (TFC_Core.getPercentGrown((IAnimal) par1Entity) * 0.35f);
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}
}
