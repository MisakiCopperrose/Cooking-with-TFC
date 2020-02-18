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

public class ModelShirt extends ModelBase
{
	private ModelRenderer armR;
	private ModelRenderer armL;

	private ModelRenderer body;

	private float scaleFactor;

	public ModelShirt()
	{
		this(0.0f);
	}

	public ModelShirt(float f)
	{
		super();
		this.scaleFactor = f;
		// Quiver

		body = new ModelRenderer(this, 16, 0);
		body.addBox(-4F, 2F, -2F, 8, 10, 4, scaleFactor);
		body.setRotationPoint(0, 0, 0f);
		armR = new ModelRenderer(this, 16, 16);
		armR.mirror = true;
		armL = new ModelRenderer(this, 16, 16);
		// Should be locx,locy,locz,sizex,sizey,sizez
		armR.addBox(-3f, -2f, -2f, 4, 8, 4, scaleFactor);
		armL.addBox(-1F, -2f, -2F, 4, 8, 4, scaleFactor);
		armR.setRotationPoint(1.9f, 14f, 0f);
		armL.setRotationPoint(-1.9f, 14f, 0f);
		// body.addChild(armR);
		// body.addChild(armL);
		/// quiver.rotateAngleZ = (float)(Math.PI/4) + (float)(Math.PI);
		// quiver.setTextureSize(64, 32);
		armL.setTextureSize(64, 32);
		armR.setTextureSize(64, 32);

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

	/**
	 * Returns a rotation angle that is inbetween two other rotation angles.
	 * par1 and par2 are the angles between which to interpolate, par3 is
	 * probably a float between 0.0 and 1.0 that tells us where "between" the
	 * two angles we are. Example: par1 = 30, par2 = 50, par3 = 0.5, then return
	 * = 40
	 */
	private float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_)
	{
		float f3;

		for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F)
		{
			;
		}

		while (f3 >= 180.0F)
		{
			f3 -= 360.0F;
		}

		return p_77034_1_ + p_77034_3_ * f3;
	}

	public void render(EntityLivingBase theEntity, ItemStack item, RenderPlayer renderer, float partialRenderTick)
	{
		//The ARMS WERE BACKWARDS DAMMNIT
		if (theEntity instanceof EntityPlayer || theEntity instanceof EntityStand|| theEntity instanceof EntityMob)
		{
			this.armR.rotateAngleZ = 0.0F;
            this.armL.rotateAngleZ = 0.0F;
            this.armR.rotateAngleY = 0.0F;
            this.armL.rotateAngleY = 0.0F;
            this.armR.rotateAngleX = 0.0F;
            this.armL.rotateAngleX = 0.0F;
			
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
			
           // GL11.glColor3f(1.0F, 1.0F, 1.0F);
			
			// armR.rotateAngleY =
			// (float)Math.toRadians(theEntity.rotationYaw);
			// armL.rotateAngleY =
			// (float)Math.toRadians(theEntity.rotationYaw);
/*
			float f2 = this.interpolateRotation(theEntity.prevRenderYawOffset, theEntity.renderYawOffset,
					partialRenderTick);
			float f3 = this.interpolateRotation(theEntity.prevRotationYawHead, theEntity.rotationYawHead,
					partialRenderTick);
			float f4;

			if (theEntity.isRiding() && theEntity.ridingEntity instanceof EntityLivingBase)
			{
				EntityLivingBase entitylivingbase1 = (EntityLivingBase) theEntity.ridingEntity;
				f2 = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset,
						partialRenderTick);
				f4 = MathHelper.wrapAngleTo180_float(f3 - f2);

				if (f4 < -85.0F)
				{
					f4 = -85.0F;
				}

				if (f4 >= 85.0F)
				{
					f4 = 85.0F;
				}

				f2 = f3 - f4;

				if (f4 * f4 > 2500.0F)
				{
					f2 += f4 * 0.2F;
				}
			}

			f4 = this.handleRotationFloat(theEntity, partialRenderTick);

			float rotateAngle;
			if (!theEntity.isRiding())
			{
				rotateAngle = theEntity.prevRenderYawOffset
						+ (theEntity.renderYawOffset - theEntity.prevRenderYawOffset) * partialRenderTick;
			}
			else
			{
				//rotateAngle = theEntity.ridingEntity.rotationYaw
				//		+ (theEntity.ridingEntity.rotationYaw - theEntity.ridingEntity.prevRotationYaw)
				//				* partialRenderTick;
				rotateAngle = theEntity.prevRotationYawHead
						+ (theEntity.rotationYawHead - theEntity.prevRotationYawHead) * partialRenderTick;
			}
			float f6 = theEntity.prevLimbSwingAmount
					+ (theEntity.limbSwingAmount - theEntity.prevLimbSwingAmount) * partialRenderTick;
			float f7 = theEntity.limbSwing - theEntity.limbSwingAmount * (1.0F - partialRenderTick);

			if (theEntity.isChild())
			{
				f7 *= 3.0F;
			}

			if (f6 > 1.0F)
			{
				f6 = 1.0F;
			}

			this.armL.rotateAngleX = MathHelper.cos(f7 * 0.6662F + (float) Math.PI) * 2.0F * f6 * 0.5F;
			this.armR.rotateAngleX = MathHelper.cos(f7 * 0.6662F) * 2.0F * f6 * 0.5F;
			this.armL.rotateAngleZ = 0.0F;
			this.armR.rotateAngleZ = 0.0F;
			
			if (theEntity.isRiding())
	        {
	            this.armL.rotateAngleX += ((float)Math.PI / 5F);
	            this.armR.rotateAngleX += ((float)Math.PI / 5F);
	           //this.bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
	          //  this.bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
	         //   this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
	         //   this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
	        }
			//System.out.println("theEntity.getHeldItem " + theEntity.getHeldItem());
		//	if (renderer.modelBipedMain.heldItemLeft != 0)
	   //     {
	   //         this.armR.rotateAngleX = this.armR.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)renderer.modelBipedMain.heldItemLeft;
	 //       }

	        if (theEntity.getHeldItem() != null)
	        {
	            this.armR.rotateAngleX = this.armR.rotateAngleX * 0.5F + ((float)Math.PI / 10F) * 1;
	        }

			if (renderer.modelBipedMain.onGround > -9990.0F)
			{
				f6 = renderer.modelBipedMain.onGround;
				this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float) Math.PI * 2.0F) * 0.2F;
				this.armL.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
				this.armL.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
				this.armR.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
				this.armR.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
				this.armL.rotateAngleY += this.body.rotateAngleY;
				this.armR.rotateAngleY += this.body.rotateAngleY;
				this.armR.rotateAngleX += this.body.rotateAngleY;
				f6 = 1.0F - renderer.modelBipedMain.onGround;
				f6 *= f6;
				f6 *= f6;
				f6 = 1.0F - f6;
				f7 = MathHelper.sin(f6 * (float) Math.PI);
				float f8 = MathHelper.sin(renderer.modelBipedMain.onGround * (float) Math.PI)
						* -(renderer.modelBipedMain.bipedHead.rotateAngleX - 0.7F) * 0.75F;
				this.armL.rotateAngleX = (float) ((double) this.armL.rotateAngleX - ((double) f7 * 1.2D + (double) f8));
				this.armL.rotateAngleY += this.armL.rotateAngleY * 2.0F;
				this.armL.rotateAngleZ = MathHelper.sin(renderer.modelBipedMain.onGround * (float) Math.PI) * -0.4F;
			}

			// No idea why we multiply by 0.9, but it seems to stop legs from
			// clipping through!
			// armL.rotateAngleX = MathHelper.cos(f7 * 0.6662F) * 1.4F * f6;
			// armR.rotateAngleX = MathHelper.cos(f7 * 0.6662F + (float)
			// Math.PI) * 1.4F * f6;

			this.armL.rotateAngleZ += MathHelper.cos(f4 * 0.09F) * 0.05F + 0.05F;
			this.armR.rotateAngleZ -= MathHelper.cos(f4 * 0.09F) * 0.05F + 0.05F;
			this.armL.rotateAngleX += MathHelper.sin(f4 * 0.067F) * 0.05F;
			this.armR.rotateAngleX -= MathHelper.sin(f4 * 0.067F) * 0.05F;

			armR.rotationPointZ = renderer.modelBipedMain.bipedLeftArm.rotationPointZ;
			armR.rotationPointY = renderer.modelBipedMain.bipedLeftArm.rotationPointY;
			armR.rotationPointX = renderer.modelBipedMain.bipedLeftArm.rotationPointX;

			armL.rotationPointZ = renderer.modelBipedMain.bipedRightArm.rotationPointZ;
			armL.rotationPointY = renderer.modelBipedMain.bipedRightArm.rotationPointY;
			armL.rotationPointX = renderer.modelBipedMain.bipedRightArm.rotationPointX;

			this.body.rotationPointX = renderer.modelBipedMain.bipedBody.rotationPointX;
			this.body.rotationPointY = renderer.modelBipedMain.bipedBody.rotationPointY;
			this.body.rotationPointZ = renderer.modelBipedMain.bipedBody.rotationPointZ;

			this.body.rotateAngleX = renderer.modelBipedMain.bipedBody.rotateAngleX;
			this.body.rotateAngleY = renderer.modelBipedMain.bipedBody.rotateAngleY;

			this.body.rotateAngleZ = renderer.modelBipedMain.bipedBody.rotateAngleZ;
			
			if (theEntity.getHeldItem() != null && theEntity instanceof EntityPlayer && ((EntityPlayer) theEntity).getItemInUseCount() > 0 && theEntity.getHeldItem().getItemUseAction() == EnumAction.bow)
	        {
	            f6 = 0.0F;
	            f7 = 0.0F;
	            this.armR.rotateAngleZ = 0.0F;
	            this.armL.rotateAngleZ = 0.0F;
	            
	            this.armR.rotateAngleY = (float)Math.PI +(0.1F - f6 * 0.6F) + renderer.modelBipedMain.bipedHead.rotateAngleY;
	            this.armL.rotateAngleY = (float)Math.PI  -(0.1F - f6 * 0.6F) + renderer.modelBipedMain.bipedHead.rotateAngleY + 0.4F;
	            
	            this.armL.rotateAngleX = -((float)Math.PI / 2F) + renderer.modelBipedMain.bipedHead.rotateAngleX;
	            this.armR.rotateAngleX = -((float)Math.PI / 2F) + renderer.modelBipedMain.bipedHead.rotateAngleX;
	            
	            this.armL.rotateAngleX += f6 * 1.2F - f7 * 0.4F;
	            this.armR.rotateAngleX += f6 * 1.2F - f7 * 0.4F;
	            
	            this.armR.rotateAngleZ += MathHelper.cos(f4 * 0.09F) * 0.05F + 0.05F;
	            this.armL.rotateAngleZ -= MathHelper.cos(f4 * 0.09F) * 0.05F + 0.05F;
	            
	            this.armL.rotateAngleX += MathHelper.sin(f4 * 0.067F) * 0.05F;
	            this.armR.rotateAngleX -= MathHelper.sin(f4 * 0.067F) * 0.05F;
	        }

			GL11.glPushMatrix();
			if (!theEntity.isSneaking())
			{
				GL11.glTranslatef(0f / 16f, 1f / 16f, -1.5f / 16f);
			}

			GL11.glRotatef(rotateAngle, 0, 1, 0);

			if (theEntity.isSneaking())
			{
				GL11.glTranslatef(0 / 16F, 1f / 16F, 1.5f / 16F);
				armL.rotateAngleX += this.body.rotateAngleX * 0.8f;
				armR.rotateAngleX += this.body.rotateAngleX * 0.8f;
			}
			this.body.render(0.0625F);
			if (theEntity.isSneaking())
			{
				GL11.glTranslatef(0 / 16F, 2.25f / 16F, -1.5f / 16F);
			}
			else
			{
				GL11.glTranslatef(0 / 16F, 2.5f / 16F, -0.25f / 16F);
			}
			this.armL.render(0.0625F);
			this.armR.render(0.0625F);

			GL11.glPopMatrix();
			// body.render(0.0625f);*/
            
            float rotateAngle = 0;
            
            body.rotateAngleX = renderer.modelBipedMain.bipedBody.rotateAngleX;
            body.rotateAngleY = renderer.modelBipedMain.bipedBody.rotateAngleY;
            body.rotateAngleZ = renderer.modelBipedMain.bipedBody.rotateAngleZ;
            
            armL.rotateAngleX = renderer.modelBipedMain.bipedLeftArm.rotateAngleX;
            armL.rotateAngleY = renderer.modelBipedMain.bipedLeftArm.rotateAngleY;
            armL.rotateAngleZ = renderer.modelBipedMain.bipedLeftArm.rotateAngleZ;
            
            armL.rotationPointX = renderer.modelBipedMain.bipedBody.rotationPointX;
            armL.rotationPointY = renderer.modelBipedMain.bipedBody.rotationPointY;
            armL.rotationPointZ = renderer.modelBipedMain.bipedBody.rotationPointZ;
            
            armL.rotationPointX += renderer.modelBipedMain.bipedLeftArm.rotationPointX;
            armL.rotationPointY += renderer.modelBipedMain.bipedLeftArm.rotationPointY;
            armL.rotationPointZ += renderer.modelBipedMain.bipedLeftArm.rotationPointZ;
            
            armR.rotateAngleX = renderer.modelBipedMain.bipedRightArm.rotateAngleX;
            armR.rotateAngleY = renderer.modelBipedMain.bipedRightArm.rotateAngleY;
            armR.rotateAngleZ = renderer.modelBipedMain.bipedRightArm.rotateAngleZ;
            
            armR.rotationPointX = renderer.modelBipedMain.bipedBody.rotationPointX;
            armR.rotationPointY = renderer.modelBipedMain.bipedBody.rotationPointY;
            armR.rotationPointZ = renderer.modelBipedMain.bipedBody.rotationPointZ;
            
            armR.rotationPointX += renderer.modelBipedMain.bipedRightArm.rotationPointX;
            armR.rotationPointY += renderer.modelBipedMain.bipedRightArm.rotationPointY;
            armR.rotationPointZ += renderer.modelBipedMain.bipedRightArm.rotationPointZ;
            
            if(!renderer.modelBipedMain.aimedBow)
            {
            	//float f7 = theEntity.limbSwing - theEntity.limbSwingAmount * (1.0F - partialRenderTick);
            	//System.out.println("arm swing: " + Math.cos(f7 * 0.6662F + Math.PI) + ", armR: " + armR.rotateAngleX + ", division: " +  (armR.rotateAngleX != 0? Math.cos(f7 * 0.6662F + Math.PI) / armR.rotateAngleX : 0));
            }
            
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
            
            GL11.glPushMatrix();
			if (!theEntity.isSneaking())
			{
				GL11.glTranslatef(0f / 16f, 0.8f / 16f, 0f / 16f);
			}

			if(!theEntity.isPlayerSleeping())
			{
				GL11.glRotatef(rotateAngle, 0, 1, 0);
			}

			if (theEntity.isSneaking())
			{
				GL11.glTranslatef(0 / 16F, 1f / 16F, -1f / 16F);
				//armL.rotateAngleX += this.body.rotateAngleX * 0.8f;
				//armR.rotateAngleX += this.body.rotateAngleX * 0.8f;
			}
			if(theEntity.isPlayerSleeping())
			{
				GL11.glRotatef(-90,1,0,0);
				GL11.glTranslatef(0, 1.95f-2.2f,1.75f);
				GL11.glRotatef(180, 0, 0, 1);
			}
			this.body.render(0.0625F);
			if (theEntity.isSneaking())
			{
				GL11.glTranslatef(0 / 16F, 2.25f / 16F, 1f / 16F);
			}
			else
			{
				GL11.glTranslatef(0 / 16F, 2.4f / 16F, /*-0.25*/0f / 16F);
			}
			GL11.glTranslatef(-0.5f/16f, -0.1f/16f, 0);
			this.armL.render(0.0625F);
			GL11.glTranslatef(1f/16f, 0, 0);
			this.armR.render(0.0625F);

			GL11.glPopMatrix();
		}

	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_)
	{
		return (float) p_77044_1_.ticksExisted + p_77044_2_;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{

		if (entity instanceof EntityPlayer)
		{

			this.armL.render(0.0625F);
			this.armR.render(0.0625F);
		}
	}

}
