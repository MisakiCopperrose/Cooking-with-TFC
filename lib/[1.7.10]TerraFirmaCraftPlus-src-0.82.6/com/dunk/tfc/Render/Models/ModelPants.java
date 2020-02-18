package com.dunk.tfc.Render.Models;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Entities.EntityStand;
import com.dunk.tfc.Items.ItemClothing;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ModelPants extends ModelBase
{
	private ModelRenderer legL;
	private ModelRenderer legR;

	private ModelRenderer body;

	private float scaleFactor;

	public ModelPants()
	{
		this(0.0f);
	}

	public ModelPants(float f)
	{
		super();
		this.scaleFactor = f;
		// Quiver

		body = new ModelRenderer(this, 16, 0);
		body.addBox(-4F, 10.0F, -2F, 8, 2, 4, scaleFactor);
		body.setRotationPoint(0, 0, 0f);
		legL = new ModelRenderer(this, 16, 16);
		legL.mirror = true;
		legR = new ModelRenderer(this, 16, 16);
		// Should be locx,locy,locz,sizex,sizey,sizez
		legL.addBox(-2, 0f, -2f, 4, 8, 4, scaleFactor);
		legR.addBox(-2F, 0f, -2F, 4, 8, 4, scaleFactor);
		legL.setRotationPoint(1.9f, 12f, 0f);
		legR.setRotationPoint(-1.9f, 12f, 0f);
		// body.addChild(legL);
		// body.addChild(legR);
		/// quiver.rotateAngleZ = (float)(Math.PI/4) + (float)(Math.PI);
		// quiver.setTextureSize(64, 32);
		legR.setTextureSize(64, 32);
		legL.setTextureSize(64, 32);

		/*
		 * for(int i = 0; i < arrows.length; i++) { arrows[i] = new
		 * ModelRenderer(this,59,0); arrows[i].addBox(-1,-8,0,2,14,0);
		 * arrows[i].setRotationPoint(0,0,0f); arrows[i].setTextureSize(64,32);
		 * arrows[i].rotateAngleZ = (float)(Math.PI) +
		 * (float)(Math.PI/36)*(i%3)*(i%2==0?1f:-1f); arrows[i].rotateAngleX =
		 * (float)(Math.PI/36)*(i%3)*(i%2==(i%3)?1f:-1f);
		 * quiver.addChild(arrows[i]); }
		 */

	}

	/**
	 * Sets the rotation on a model where the provided params are in radians
	 * 
	 * @param model
	 *            The model
	 * @param x
	 *            The x angle
	 * @param y
	 *            The y angle
	 * @param z
	 *            The z angle
	 */
	protected void setRotationRadians(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	/**
	 * Sets the rotation on a model where the provided params are in degrees
	 * 
	 * @param model
	 *            The model
	 * @param x
	 *            The x angle
	 * @param y
	 *            The y angle
	 * @param z
	 *            The z angle
	 */
	protected void setRotationDegrees(ModelRenderer model, float x, float y, float z)
	{
		this.setRotationRadians(model, (float) Math.toRadians(x), (float) Math.toRadians(y), (float) Math.toRadians(z));
	}

	public void render(EntityLivingBase theEntity, ItemStack item, RenderPlayer renderer, float partialRenderTick)
	{
		if (theEntity instanceof EntityPlayer || theEntity instanceof EntityStand|| theEntity instanceof EntityMob)
		{
			// legL.rotateAngleY =
			// (float)Math.toRadians(theEntity.rotationYaw);
			// legR.rotateAngleY =
			// (float)Math.toRadians(theEntity.rotationYaw);
			
			float wetnessMult = 1f;
			if(item.stackTagCompound != null)
			{
				wetnessMult = (12000f-(float)item.stackTagCompound.getInteger("wetness"))/12000f;
			}
			
			int j = ((ItemClothing)(item.getItem())).getColor(item);
			if (j != -1)
			{
				float f1 = (float) (j >> 16 & 255) / 255.0F;
				float f2 = (float) (j >> 8 & 255) / 255.0F;
				float f3 = (float) (j & 255) / 255.0F;
				GL11.glColor3f(f1* wetnessMult, f2* wetnessMult, f3* wetnessMult);

			}
			else
			{
				GL11.glColor3f(1.0F* wetnessMult, 1.0F* wetnessMult, 1.0F* wetnessMult);
			}
			
			float rotateAngle = 0;
			 if(theEntity.ridingEntity != null && theEntity.ridingEntity instanceof EntityLiving)
	            {
	            	rotateAngle = ((EntityLiving)theEntity.ridingEntity).prevRenderYawOffset
	    					+ (((EntityLiving)theEntity.ridingEntity).renderYawOffset - ((EntityLiving)theEntity.ridingEntity).prevRenderYawOffset) * partialRenderTick;
	            }
	            else if(!(theEntity instanceof EntityMob))
	            {
	            rotateAngle = theEntity.prevRenderYawOffset
						+ (theEntity.renderYawOffset - theEntity.prevRenderYawOffset) * partialRenderTick;
	            }

			/*
			 * legR.rotateAngleX = MathHelper.cos(theEntity.limbSwing * 0.6662F)
			 * * 1.4F * (theEntity.prevLimbSwingAmount +
			 * ((theEntity.limbSwingAmount -
			 * theEntity.prevLimbSwingAmount)*partialRenderTick)) ;
			 * legL.rotateAngleX = MathHelper.cos(theEntity.limbSwing * 0.6662F
			 * + (float)Math.PI) * 1.4F * (theEntity.prevLimbSwingAmount +
			 * ((theEntity.limbSwingAmount -
			 * theEntity.prevLimbSwingAmount)*partialRenderTick));
			 */


			legL.rotationPointZ = renderer.modelBipedMain.bipedLeftLeg.rotationPointZ;
			legL.rotationPointY = renderer.modelBipedMain.bipedLeftLeg.rotationPointY+0.5f;
			legL.rotationPointX = renderer.modelBipedMain.bipedLeftLeg.rotationPointX;

			legR.rotationPointZ = renderer.modelBipedMain.bipedRightLeg.rotationPointZ;
			legR.rotationPointY = renderer.modelBipedMain.bipedRightLeg.rotationPointY+0.5f;
			legR.rotationPointX = renderer.modelBipedMain.bipedRightLeg.rotationPointX;
			
		//	if(((EntityPlayer) theEntity).getCurrentArmor(1)== null && theEntity.ridingEntity == null)
		//	{
		//		legL.rotateAngleX = renderer.modelBipedMain.bipedLeftLeg.rotateAngleX * 0.85f;
		//		legR.rotateAngleX = renderer.modelBipedMain.bipedRightLeg.rotateAngleX* 0.85f;
		//	}
		//	else if(theEntity.ridingEntity == null)
		//	{
		//		legL.rotateAngleX = renderer.modelBipedMain.bipedLeftLeg.rotateAngleX * 0.9f;
		//		legR.rotateAngleX = renderer.modelBipedMain.bipedRightLeg.rotateAngleX* 0.9f;
		//	}
		//	else
		//	{
				legL.rotateAngleX = renderer.modelBipedMain.bipedLeftLeg.rotateAngleX;
				legR.rotateAngleX = renderer.modelBipedMain.bipedRightLeg.rotateAngleX;
			//}
			legL.rotateAngleZ = renderer.modelBipedMain.bipedLeftLeg.rotateAngleZ;
			legL.rotateAngleY = renderer.modelBipedMain.bipedLeftLeg.rotateAngleY;
			//legL.rotateAngleX = renderer.modelBipedMain.bipedLeftLeg.rotateAngleX * 0.85f;

			legR.rotateAngleZ = renderer.modelBipedMain.bipedRightLeg.rotateAngleZ;
			legR.rotateAngleY = renderer.modelBipedMain.bipedRightLeg.rotateAngleY;
			//legR.rotateAngleX = renderer.modelBipedMain.bipedRightLeg.rotateAngleX* 0.85f;

			this.body.rotationPointX = renderer.modelBipedMain.bipedBody.rotationPointX;
			this.body.rotationPointY = renderer.modelBipedMain.bipedBody.rotationPointY;
			this.body.rotationPointZ = renderer.modelBipedMain.bipedBody.rotationPointZ;

			this.body.rotateAngleX = -renderer.modelBipedMain.bipedBody.rotateAngleX;
			this.body.rotateAngleY = renderer.modelBipedMain.bipedBody.rotateAngleY;
			this.body.rotateAngleZ = renderer.modelBipedMain.bipedBody.rotateAngleZ;

			GL11.glPushMatrix();
			if (!theEntity.isSneaking())
			{
				GL11.glTranslatef(0f / 16f, 2f / 16f, 0f / 16f);
			}

			if(!theEntity.isPlayerSleeping())
			{
				GL11.glRotatef(rotateAngle, 0, 1, 0);
			}
			
			if (theEntity.isSneaking())
			{
				GL11.glRotatef(180, 0, 1, 0);
				GL11.glTranslatef(0 / 16F, 2.8f / 16F, 0.6f / 16F);
			}
			if(theEntity.isPlayerSleeping())
			{
				GL11.glRotatef(-90,1,0,0);
				GL11.glTranslatef(0, -2.2f, 1.7f);
			}
			this.body.render(0.0625F);
			if (theEntity.isSneaking())
			{
				
				GL11.glTranslatef(0 / 16F, 0f / 16F, -0f / 16F);
			}
			if(theEntity.ridingEntity != null)
			{
				GL11.glTranslatef(0 / 16F, 0.5f / 16F, 0f / 16F);
			}
			if (theEntity.isSneaking())
			{
				GL11.glTranslatef(0 / 16F, -0.5f / 16F, -8.5f / 16F);
			}
			else
			{
				GL11.glTranslatef(0 / 16F, 0 / 16F, 0f / 16F);				
			}
			//if(!theEntity.isSneaking() && renderer.modelBipedMain.bipedRightLeg.rotateAngleX < 0)
			//{
			//	GL11.glTranslatef(0 / 16F, 0 / 16F, 0.15f / 16F);
			//}
			
			this.legR.render(0.0625F);
		//	if(!theEntity.isSneaking() && renderer.modelBipedMain.bipedRightLeg.rotateAngleX < 0)
		//	{
		//		GL11.glTranslatef(0 / 16F, 0 / 16F, -0.15f / 16F);
		//	}
		//	if(!theEntity.isSneaking() && renderer.modelBipedMain.bipedLeftLeg.rotateAngleX < 0)
		//	{
		//		GL11.glTranslatef(0 / 16F, 0 / 16F, 0.15f / 16F);
		//	}
			this.legL.render(0.0625F);

			GL11.glPopMatrix();
			// body.render(0.0625f);
		}

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{

		if (entity instanceof EntityPlayer)
		{

			this.legR.render(0.0625F);
			this.legL.render(0.0625F);
		}
	}

}
