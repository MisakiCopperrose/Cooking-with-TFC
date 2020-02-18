package com.dunk.tfc.Render;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Entities.Mobs.EntityPigTFC;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Entities.IAnimal.GenderEnum;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderPigTFC extends RenderPig
{

	private static final ResourceLocation BOAR_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/pig.png");
	private static final ResourceLocation PIG_DOMESTIC_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/pig_domestic.png");
	private static final ResourceLocation BOAR_PIGLET_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/piglet.png");
	public RenderPigTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase,par2ModelBase, par3);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.shadowSize = 0.35f + (TFC_Core.getPercentGrown((IAnimal)par1Entity)*0.35f);
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}
	
	protected ResourceLocation getTexture(IAnimal entity)
	{
		float percent = TFC_Core.getPercentGrown(entity);
		//percent = (TFC_Time.getTotalTicks()%1000)*0.003f;
		//percent = Math.min(1f, percent);
		if(percent < 0.65f && !((EntityPigTFC)entity).isDomesticated() ){
			return BOAR_PIGLET_TEXTURE;
		}
		else if(!((EntityPigTFC)entity).isDomesticated()){
			return BOAR_TEXTURE;
		}
		else{
			return PIG_DOMESTIC_TEXTURE;
		}
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		float scale = ((EntityPigTFC) par1EntityLivingBase).getSizeMod() / 2 + 0.5f;
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getTexture((IAnimal)entity);
	}
}
