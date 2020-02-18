package com.dunk.tfc.Render.Models;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.dunk.tfc.Entities.EntityStand;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class ModelHat extends ModelClothingBase
{

	private ModelRenderer base;
	private ModelRenderer hat;
	private ModelRenderer strawBrim;
	private ModelRenderer hatBulge;
	private ModelRenderer animalHead;
	private ModelRenderer animalSnout;
	private ModelRenderer animalEars;
	private ModelRenderer animalFur;
	private ModelRenderer animalFur2;
	private TexturedQuad[] quadList;

	private float scaleFactor;

	public ModelHat()
	{
		this(0.0f);
	}

	public ModelHat(float f)
	{
		super();
		this.scaleFactor = f;
		// Quiver

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0, 0, 0, 0, 0, 0);
		base.setRotationPoint(0, 4f, 0);

		hat = new ModelRenderer(this, 0, 0);
		hat.addBox(-4f, -9f, -6f, 8, 4, 10, f);

		base.addChild(hat);
		strawBrim = new ModelRenderer(this, 20, 0);
		strawBrim.addBox(-7, -6, -9, 14, 1, 16, f);
		strawBrim.setRotationPoint(0, 0f, 0);
		hatBulge = new ModelRenderer(this, 0, 14);
		hatBulge.addBox(-4.5f, -7f, -6.5f, 9, 3, 11, f - 0.3f);
		animalHead = new ModelRenderer(this, 36, 0);
		animalHead.addBox(-4f, -10f, -5f, 8, 4, 6);
		base.addChild(animalHead);
		hat.addChild(strawBrim);

		animalFur = new ModelRenderer(this, 5, 19);
		animalFur.addBox(-4.5f, -9f, -5.5f, 9, 6, 6);

		animalFur2 = new ModelRenderer(this, 5, 19);
		animalFur2.addBox(-4.5f, -5.6f, -6f, 9, 6, 6, -0.1f);

		base.addChild(animalFur);

		animalFur.addChild(animalFur2);

		animalSnout = new ModelRenderer(this, 36, 11);
		animalSnout.addBox(-2f, -8f, -8f, 4, 3, 4);
		animalEars = new ModelRenderer(this, 36, 18);
		animalEars.addBox(-3.5f, -11.5f, 0f, 2, 3, 1);
		animalEars.addBox(1.5f, -11.5f, 0f, 2, 3, 1);
		animalHead.addChild(animalSnout);
		animalHead.addChild(animalEars);
		// hat.addChild(strawBrim);
		base.addChild(hatBulge);
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

	public void render(EntityLivingBase theEntity, ItemStack item, RenderPlayer renderer, float partialRenderTick,
			boolean hasHelmet)
	{
		// The ARMS WERE BACKWARDS DAMMNIT
		if (theEntity instanceof EntityPlayer || theEntity instanceof EntityStand|| theEntity instanceof EntityMob)
		{
			this.hat.rotateAngleZ = 0.0F;
			this.hat.rotateAngleY = 0.0F;
			this.hat.rotateAngleX = 0.0F;
			base.showModel = true;
			boolean altStrawHat = false;
			if (item != null && item.getItem() == TFCItems.strawHat)
			{
				hat.showModel = true;
				strawBrim.showModel = true;
				hatBulge.showModel = false;
				animalHead.showModel = false;
				animalSnout.showModel = false;
				animalEars.showModel = false;
				animalFur.showModel = false;
			}
			else if (item != null && item.getItem() == TFCItems.strawHat2)
			{
				hat.showModel = false;
				strawBrim.showModel = true;
				hatBulge.showModel = false;
				animalHead.showModel = false;
				animalSnout.showModel = false;
				animalEars.showModel = false;
				animalFur.showModel = false;
				altStrawHat = true;
			}
			else if (item != null && (item.getItem() == TFCItems.bearFurHat || item.getItem() == TFCItems.wolfFurHat))
			{

				if (item.getItem() == TFCItems.bearFurHat)
				{
					animalSnout.offsetZ = -1f / 32f;
				}
				else
				{
					animalSnout.offsetZ = 0f;
				}
				hat.showModel = false;
				strawBrim.showModel = false;
				hatBulge.showModel = false;
				animalHead.showModel = true;
				animalSnout.showModel = true;
				animalEars.showModel = true;
				animalFur.showModel = true;
			}
			else if (hasHelmet)
			{
				strawBrim.showModel = false;
				hatBulge.showModel = false;
				hat.showModel = false;
				animalHead.showModel = false;
				animalSnout.showModel = false;
				animalEars.showModel = false;
				animalFur.showModel = false;
			}
			else
			{
				strawBrim.showModel = false;
				hatBulge.showModel = true;
				hat.showModel = true;
				animalHead.showModel = false;
				animalSnout.showModel = false;
				animalEars.showModel = false;
				animalFur.showModel = false;
			}

			float wetnessMult = 1f;
			if (item.stackTagCompound != null)
			{
				wetnessMult = (12000f - (float) item.stackTagCompound.getInteger("wetness")) / 12000f;
			}

			int j = ((ItemClothing) (item.getItem())).getColor(item);
			if (j != -1)
			{
				float f1 = (float) (j >> 16 & 255) / 255.0F;
				float f2 = (float) (j >> 8 & 255) / 255.0F;
				float f3 = (float) (j & 255) / 255.0F;
				GL11.glColor3f(f1 * wetnessMult, f2 * wetnessMult, f3 * wetnessMult);

			}
			else
			{
				GL11.glColor3f(1.0F * wetnessMult, 1.0F * wetnessMult, 1.0F * wetnessMult);
			}

			// armR.rotateAngleY =
			// (float)Math.toRadians(theEntity.rotationYaw);
			// armL.rotateAngleY =
			// (float)Math.toRadians(theEntity.rotationYaw);
			/*
			 * float f2 =
			 * this.interpolateRotation(theEntity.prevRenderYawOffset,
			 * theEntity.renderYawOffset, partialRenderTick); float f3 =
			 * this.interpolateRotation(theEntity.prevRotationYawHead,
			 * theEntity.rotationYawHead, partialRenderTick); float f4;
			 * 
			 * if (theEntity.isRiding() && theEntity.ridingEntity instanceof
			 * EntityLivingBase) { EntityLivingBase entitylivingbase1 =
			 * (EntityLivingBase) theEntity.ridingEntity; f2 =
			 * this.interpolateRotation(entitylivingbase1.prevRenderYawOffset,
			 * entitylivingbase1.renderYawOffset, partialRenderTick); f4 =
			 * MathHelper.wrapAngleTo180_float(f3 - f2);
			 * 
			 * if (f4 < -85.0F) { f4 = -85.0F; }
			 * 
			 * if (f4 >= 85.0F) { f4 = 85.0F; }
			 * 
			 * f2 = f3 - f4;
			 * 
			 * if (f4 * f4 > 2500.0F) { f2 += f4 * 0.2F; } }
			 * 
			 * f4 = this.handleRotationFloat(theEntity, partialRenderTick);
			 * 
			 * float rotateAngle; if (!theEntity.isRiding()) { rotateAngle =
			 * theEntity.prevRenderYawOffset + (theEntity.renderYawOffset -
			 * theEntity.prevRenderYawOffset) * partialRenderTick; } else {
			 * //rotateAngle = theEntity.ridingEntity.rotationYaw // +
			 * (theEntity.ridingEntity.rotationYaw -
			 * theEntity.ridingEntity.prevRotationYaw) // * partialRenderTick;
			 * rotateAngle = theEntity.prevRotationYawHead +
			 * (theEntity.rotationYawHead - theEntity.prevRotationYawHead) *
			 * partialRenderTick; } float f6 = theEntity.prevLimbSwingAmount +
			 * (theEntity.limbSwingAmount - theEntity.prevLimbSwingAmount) *
			 * partialRenderTick; float f7 = theEntity.limbSwing -
			 * theEntity.limbSwingAmount * (1.0F - partialRenderTick);
			 * 
			 * if (theEntity.isChild()) { f7 *= 3.0F; }
			 * 
			 * if (f6 > 1.0F) { f6 = 1.0F; }
			 * 
			 * this.armL.rotateAngleX = MathHelper.cos(f7 * 0.6662F + (float)
			 * Math.PI) * 2.0F * f6 * 0.5F; this.armR.rotateAngleX =
			 * MathHelper.cos(f7 * 0.6662F) * 2.0F * f6 * 0.5F;
			 * this.armL.rotateAngleZ = 0.0F; this.armR.rotateAngleZ = 0.0F;
			 * 
			 * if (theEntity.isRiding()) { this.armL.rotateAngleX +=
			 * ((float)Math.PI / 5F); this.armR.rotateAngleX += ((float)Math.PI
			 * / 5F); //this.bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F
			 * / 5F); // this.bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F
			 * / 5F); // this.bipedRightLeg.rotateAngleY = ((float)Math.PI /
			 * 10F); // this.bipedLeftLeg.rotateAngleY = -((float)Math.PI /
			 * 10F); } //System.out.println("theEntity.getHeldItem " +
			 * theEntity.getHeldItem()); // if
			 * (renderer.modelBipedMain.heldItemLeft != 0) // { //
			 * this.armR.rotateAngleX = this.armR.rotateAngleX * 0.5F -
			 * ((float)Math.PI / 10F) *
			 * (float)renderer.modelBipedMain.heldItemLeft; // }
			 * 
			 * if (theEntity.getHeldItem() != null) { this.armR.rotateAngleX =
			 * this.armR.rotateAngleX * 0.5F + ((float)Math.PI / 10F) * 1; }
			 * 
			 * if (renderer.modelBipedMain.onGround > -9990.0F) { f6 =
			 * renderer.modelBipedMain.onGround; this.body.rotateAngleY =
			 * MathHelper.sin(MathHelper.sqrt_float(f6) * (float) Math.PI *
			 * 2.0F) * 0.2F; this.armL.rotationPointZ =
			 * MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			 * this.armL.rotationPointX =
			 * -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
			 * this.armR.rotationPointZ =
			 * -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			 * this.armR.rotationPointX = MathHelper.cos(this.body.rotateAngleY)
			 * * 5.0F; this.armL.rotateAngleY += this.body.rotateAngleY;
			 * this.armR.rotateAngleY += this.body.rotateAngleY;
			 * this.armR.rotateAngleX += this.body.rotateAngleY; f6 = 1.0F -
			 * renderer.modelBipedMain.onGround; f6 *= f6; f6 *= f6; f6 = 1.0F -
			 * f6; f7 = MathHelper.sin(f6 * (float) Math.PI); float f8 =
			 * MathHelper.sin(renderer.modelBipedMain.onGround * (float)
			 * Math.PI) -(renderer.modelBipedMain.bipedHead.rotateAngleX - 0.7F)
			 * * 0.75F; this.armL.rotateAngleX = (float) ((double)
			 * this.armL.rotateAngleX - ((double) f7 * 1.2D + (double) f8));
			 * this.armL.rotateAngleY += this.armL.rotateAngleY * 2.0F;
			 * this.armL.rotateAngleZ =
			 * MathHelper.sin(renderer.modelBipedMain.onGround * (float)
			 * Math.PI) * -0.4F; }
			 * 
			 * // No idea why we multiply by 0.9, but it seems to stop legs from
			 * // clipping through! // armL.rotateAngleX = MathHelper.cos(f7 *
			 * 0.6662F) * 1.4F * f6; // armR.rotateAngleX = MathHelper.cos(f7 *
			 * 0.6662F + (float) // Math.PI) * 1.4F * f6;
			 * 
			 * this.armL.rotateAngleZ += MathHelper.cos(f4 * 0.09F) * 0.05F +
			 * 0.05F; this.armR.rotateAngleZ -= MathHelper.cos(f4 * 0.09F) *
			 * 0.05F + 0.05F; this.armL.rotateAngleX += MathHelper.sin(f4 *
			 * 0.067F) * 0.05F; this.armR.rotateAngleX -= MathHelper.sin(f4 *
			 * 0.067F) * 0.05F;
			 * 
			 * armR.rotationPointZ =
			 * renderer.modelBipedMain.bipedLeftArm.rotationPointZ;
			 * armR.rotationPointY =
			 * renderer.modelBipedMain.bipedLeftArm.rotationPointY;
			 * armR.rotationPointX =
			 * renderer.modelBipedMain.bipedLeftArm.rotationPointX;
			 * 
			 * armL.rotationPointZ =
			 * renderer.modelBipedMain.bipedRightArm.rotationPointZ;
			 * armL.rotationPointY =
			 * renderer.modelBipedMain.bipedRightArm.rotationPointY;
			 * armL.rotationPointX =
			 * renderer.modelBipedMain.bipedRightArm.rotationPointX;
			 * 
			 * this.body.rotationPointX =
			 * renderer.modelBipedMain.bipedBody.rotationPointX;
			 * this.body.rotationPointY =
			 * renderer.modelBipedMain.bipedBody.rotationPointY;
			 * this.body.rotationPointZ =
			 * renderer.modelBipedMain.bipedBody.rotationPointZ;
			 * 
			 * this.body.rotateAngleX =
			 * renderer.modelBipedMain.bipedBody.rotateAngleX;
			 * this.body.rotateAngleY =
			 * renderer.modelBipedMain.bipedBody.rotateAngleY;
			 * 
			 * this.body.rotateAngleZ =
			 * renderer.modelBipedMain.bipedBody.rotateAngleZ;
			 * 
			 * if (theEntity.getHeldItem() != null && theEntity instanceof
			 * EntityPlayer && ((EntityPlayer) theEntity).getItemInUseCount() >
			 * 0 && theEntity.getHeldItem().getItemUseAction() ==
			 * EnumAction.bow) { f6 = 0.0F; f7 = 0.0F; this.armR.rotateAngleZ =
			 * 0.0F; this.armL.rotateAngleZ = 0.0F;
			 * 
			 * this.armR.rotateAngleY = (float)Math.PI +(0.1F - f6 * 0.6F) +
			 * renderer.modelBipedMain.bipedHead.rotateAngleY;
			 * this.armL.rotateAngleY = (float)Math.PI -(0.1F - f6 * 0.6F) +
			 * renderer.modelBipedMain.bipedHead.rotateAngleY + 0.4F;
			 * 
			 * this.armL.rotateAngleX = -((float)Math.PI / 2F) +
			 * renderer.modelBipedMain.bipedHead.rotateAngleX;
			 * this.armR.rotateAngleX = -((float)Math.PI / 2F) +
			 * renderer.modelBipedMain.bipedHead.rotateAngleX;
			 * 
			 * this.armL.rotateAngleX += f6 * 1.2F - f7 * 0.4F;
			 * this.armR.rotateAngleX += f6 * 1.2F - f7 * 0.4F;
			 * 
			 * this.armR.rotateAngleZ += MathHelper.cos(f4 * 0.09F) * 0.05F +
			 * 0.05F; this.armL.rotateAngleZ -= MathHelper.cos(f4 * 0.09F) *
			 * 0.05F + 0.05F;
			 * 
			 * this.armL.rotateAngleX += MathHelper.sin(f4 * 0.067F) * 0.05F;
			 * this.armR.rotateAngleX -= MathHelper.sin(f4 * 0.067F) * 0.05F; }
			 * 
			 * GL11.glPushMatrix(); if (!theEntity.isSneaking()) {
			 * GL11.glTranslatef(0f / 16f, 1f / 16f, -1.5f / 16f); }
			 * 
			 * GL11.glRotatef(rotateAngle, 0, 1, 0);
			 * 
			 * if (theEntity.isSneaking()) { GL11.glTranslatef(0 / 16F, 1f /
			 * 16F, 1.5f / 16F); armL.rotateAngleX += this.body.rotateAngleX *
			 * 0.8f; armR.rotateAngleX += this.body.rotateAngleX * 0.8f; }
			 * this.body.render(0.0625F); if (theEntity.isSneaking()) {
			 * GL11.glTranslatef(0 / 16F, 2.25f / 16F, -1.5f / 16F); } else {
			 * GL11.glTranslatef(0 / 16F, 2.5f / 16F, -0.25f / 16F); }
			 * this.armL.render(0.0625F); this.armR.render(0.0625F);
			 * 
			 * GL11.glPopMatrix(); // body.render(0.0625f);
			 */

			float rotateAngle;

			base.rotateAngleX = renderer.modelBipedMain.bipedHead.rotateAngleX - 0.2f;
			animalHead.rotateAngleX = 0.2f;
			animalFur.rotateAngleX = -0.4f;
			animalFur2.rotateAngleX = -0.9f;
			base.rotateAngleY = renderer.modelBipedMain.bipedHead.rotateAngleY;
			base.rotateAngleZ = renderer.modelBipedMain.bipedHead.rotateAngleZ;

			// strawBrim.rotateAngleX = base.rotateAngleX;
			// strawBrim.rotateAngleY = base.rotateAngleY;
			// strawBrim.rotateAngleZ = base.rotateAngleZ;

			// theEntity.rotationYawHead

			if (!renderer.modelBipedMain.aimedBow)
			{
				// float f7 = theEntity.limbSwing - theEntity.limbSwingAmount *
				// (1.0F - partialRenderTick);
				// System.out.println("arm swing: " + Math.cos(f7 * 0.6662F +
				// Math.PI) + ", armR: " + armR.rotateAngleX + ", division: " +
				// (armR.rotateAngleX != 0? Math.cos(f7 * 0.6662F + Math.PI) /
				// armR.rotateAngleX : 0));
			}
			rotateAngle = 0;
			if (theEntity.ridingEntity != null && theEntity.ridingEntity instanceof EntityLiving)
			{
				rotateAngle = ((EntityLiving) theEntity.ridingEntity).prevRenderYawOffset
						+ (((EntityLiving) theEntity.ridingEntity).renderYawOffset
								- ((EntityLiving) theEntity.ridingEntity).prevRenderYawOffset) * partialRenderTick;
			}
			else if(!(theEntity instanceof EntityMob))
			{
				rotateAngle = (theEntity.prevRenderYawOffset
						+ (theEntity.renderYawOffset - theEntity.prevRenderYawOffset) * partialRenderTick);
			}
			else
			{

			}
			GL11.glPushMatrix();
			if (!theEntity.isSneaking())
			{
				GL11.glTranslatef(0f / 16f, -1f / 16f, 0f / 16f);
			}

			if (!theEntity.isPlayerSleeping())
			{
				GL11.glRotatef(rotateAngle, 0, 1, 0);
			}

			if (theEntity.isSneaking())
			{
				GL11.glTranslatef(0 / 16F, 1f / 16F, 0f / 16F);
				// armL.rotateAngleX += this.body.rotateAngleX * 0.8f;
				// armR.rotateAngleX += this.body.rotateAngleX * 0.8f;
			}
			if (theEntity.isPlayerSleeping())
			{
				GL11.glRotatef(-90, 1, 0, 0);
				GL11.glTranslatef(0, 1.95f - 2.2f, 1.75f);
				GL11.glRotatef(180, 0, 0, 1);
				float eff = 2;
				GL11.glTranslatef(0, -0.2f - (0.05f * (eff - 4)), 0.18f);
			}

			if (altStrawHat)
			{
				this.quadList = new TexturedQuad[14];
				Vec3 TLDir = Vec3.createVectorHelper(-7, -5, -7.5);
				Vec3 TRDir = Vec3.createVectorHelper(7, -5, -7.5);
				Vec3 BLDir = Vec3.createVectorHelper(-7, -5, 7);
				Vec3 BRDir = Vec3.createVectorHelper(7, -5, 7);
				Vec3 PDir = Vec3.createVectorHelper(0, -11, -0.25);
				Vec3 headPosition = Vec3.createVectorHelper(0, 4, 0);
				if(theEntity.isSneaking())
				{
					headPosition.yCoord-=1.5;
				}

				Vec3 chinLeftF = Vec3.createVectorHelper(-4, 0.35, -0);
				Vec3 chinRightF = Vec3.createVectorHelper(4, 0.35, -0);

				Vec3 strapLeftF = Vec3.createVectorHelper(-6, -5.5, -0);
				Vec3 strapRightF = Vec3.createVectorHelper(6, -5.5, -0);

				Vec3 chinLeftB = Vec3.createVectorHelper(-4, 0.35, -1);
				Vec3 chinRightB = Vec3.createVectorHelper(4, 0.35, -1);

				Vec3 strapLeftB = Vec3.createVectorHelper(-6, -5.5, -1);
				Vec3 strapRightB = Vec3.createVectorHelper(6, -5.5, -1);

				ModelBiped baseModel = renderer.modelBipedMain;

				TLDir.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				TLDir.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				TLDir.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				TRDir.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				TRDir.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				TRDir.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				BLDir.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				BLDir.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				BLDir.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				BRDir.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				BRDir.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				BRDir.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				PDir.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				PDir.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				PDir.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				chinLeftF.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				chinLeftF.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				chinLeftF.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				chinRightF.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				chinRightF.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				chinRightF.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				chinLeftB.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				chinLeftB.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				chinLeftB.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				chinRightB.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				chinRightB.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				chinRightB.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				strapLeftF.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				strapLeftF.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				strapLeftF.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				strapRightF.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				strapRightF.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				strapRightF.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				strapLeftB.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				strapLeftB.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				strapLeftB.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				strapRightB.rotateAroundX(-baseModel.bipedHead.rotateAngleX + 0.1f);
				strapRightB.rotateAroundY(baseModel.bipedHead.rotateAngleY + baseModel.bipedBody.rotateAngleY);
				strapRightB.rotateAroundZ(baseModel.bipedHead.rotateAngleZ);

				int textureWidth = 64;
				int textureHeight = 32;
				PositionTextureVertex leftFront = new PositionTextureVertex(
						(float) TLDir.xCoord + (float) headPosition.xCoord,
						(float) TLDir.yCoord + (float) headPosition.yCoord,
						(float) TLDir.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex rightFront = new PositionTextureVertex(
						(float) TRDir.xCoord + (float) headPosition.xCoord,
						(float) TRDir.yCoord + (float) headPosition.yCoord,
						(float) TRDir.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex rightBack = new PositionTextureVertex(
						(float) BRDir.xCoord + (float) headPosition.xCoord,
						(float) BRDir.yCoord + (float) headPosition.yCoord,
						(float) BRDir.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex leftBack = new PositionTextureVertex(
						(float) BLDir.xCoord + (float) headPosition.xCoord,
						(float) BLDir.yCoord + (float) headPosition.yCoord,
						(float) BLDir.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex top = new PositionTextureVertex((float) PDir.xCoord + (float) headPosition.xCoord,
						(float) PDir.yCoord + (float) headPosition.yCoord,
						(float) PDir.zCoord + (float) headPosition.zCoord, 0, 0);

				PositionTextureVertex chinLF = new PositionTextureVertex(
						(float) chinLeftF.xCoord + (float) headPosition.xCoord,
						(float) chinLeftF.yCoord + (float) headPosition.yCoord,
						(float) chinLeftF.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex chinLB = new PositionTextureVertex(
						(float) chinLeftB.xCoord + (float) headPosition.xCoord,
						(float) chinLeftB.yCoord + (float) headPosition.yCoord,
						(float) chinLeftB.zCoord + (float) headPosition.zCoord, 0, 0);

				PositionTextureVertex strapLF = new PositionTextureVertex(
						(float) strapLeftF.xCoord + (float) headPosition.xCoord,
						(float) strapLeftF.yCoord + (float) headPosition.yCoord,
						(float) strapLeftF.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex strapLB = new PositionTextureVertex(
						(float) strapLeftB.xCoord + (float) headPosition.xCoord,
						(float) strapLeftB.yCoord + (float) headPosition.yCoord,
						(float) strapLeftB.zCoord + (float) headPosition.zCoord, 0, 0);

				PositionTextureVertex chinRF = new PositionTextureVertex(
						(float) chinRightF.xCoord + (float) headPosition.xCoord,
						(float) chinRightF.yCoord + (float) headPosition.yCoord,
						(float) chinRightF.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex chinRB = new PositionTextureVertex(
						(float) chinRightB.xCoord + (float) headPosition.xCoord,
						(float) chinRightB.yCoord + (float) headPosition.yCoord,
						(float) chinRightB.zCoord + (float) headPosition.zCoord, 0, 0);

				PositionTextureVertex strapRF = new PositionTextureVertex(
						(float) strapRightF.xCoord + (float) headPosition.xCoord,
						(float) strapRightF.yCoord + (float) headPosition.yCoord,
						(float) strapRightF.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex strapRB = new PositionTextureVertex(
						(float) strapRightB.xCoord + (float) headPosition.xCoord,
						(float) strapRightB.yCoord + (float) headPosition.yCoord,
						(float) strapRightB.zCoord + (float) headPosition.zCoord, 0, 0);

				PositionTextureVertex chinLF2 = new PositionTextureVertex(
						(float) chinLeftF.xCoord + (float) headPosition.xCoord + 0.01f,
						(float) chinLeftF.yCoord + (float) headPosition.yCoord,
						(float) chinLeftF.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex chinLB2 = new PositionTextureVertex(
						(float) chinLeftB.xCoord + (float) headPosition.xCoord + 0.01f,
						(float) chinLeftB.yCoord + (float) headPosition.yCoord,
						(float) chinLeftB.zCoord + (float) headPosition.zCoord, 0, 0);

				PositionTextureVertex strapLF2 = new PositionTextureVertex(
						(float) strapLeftF.xCoord + (float) headPosition.xCoord + 0.01f,
						(float) strapLeftF.yCoord + (float) headPosition.yCoord,
						(float) strapLeftF.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex strapLB2 = new PositionTextureVertex(
						(float) strapLeftB.xCoord + (float) headPosition.xCoord + 0.01f,
						(float) strapLeftB.yCoord + (float) headPosition.yCoord,
						(float) strapLeftB.zCoord + (float) headPosition.zCoord, 0, 0);

				PositionTextureVertex chinRF2 = new PositionTextureVertex(
						(float) chinRightF.xCoord + (float) headPosition.xCoord - 0.01f,
						(float) chinRightF.yCoord + (float) headPosition.yCoord,
						(float) chinRightF.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex chinRB2 = new PositionTextureVertex(
						(float) chinRightB.xCoord + (float) headPosition.xCoord - 0.01f,
						(float) chinRightB.yCoord + (float) headPosition.yCoord,
						(float) chinRightB.zCoord + (float) headPosition.zCoord, 0, 0);

				PositionTextureVertex strapRF2 = new PositionTextureVertex(
						(float) strapRightF.xCoord + (float) headPosition.xCoord - 0.01f,
						(float) strapRightF.yCoord + (float) headPosition.yCoord,
						(float) strapRightF.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex strapRB2 = new PositionTextureVertex(
						(float) strapRightB.xCoord + (float) headPosition.xCoord - 0.01f,
						(float) strapRightB.yCoord + (float) headPosition.yCoord,
						(float) strapRightB.zCoord + (float) headPosition.zCoord, 0, 0);

				PositionTextureVertex leftFront2 = new PositionTextureVertex(
						(float) TLDir.xCoord + (float) headPosition.xCoord,
						(float) TLDir.yCoord + (float) headPosition.yCoord + 0.01f,
						(float) TLDir.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex rightFront2 = new PositionTextureVertex(
						(float) TRDir.xCoord + (float) headPosition.xCoord,
						(float) TRDir.yCoord + (float) headPosition.yCoord + 0.01f,
						(float) TRDir.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex rightBack2 = new PositionTextureVertex(
						(float) BRDir.xCoord + (float) headPosition.xCoord,
						(float) BRDir.yCoord + (float) headPosition.yCoord + 0.01f,
						(float) BRDir.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex leftBack2 = new PositionTextureVertex(
						(float) BLDir.xCoord + (float) headPosition.xCoord,
						(float) BLDir.yCoord + (float) headPosition.yCoord + 0.01f,
						(float) BLDir.zCoord + (float) headPosition.zCoord, 0, 0);
				PositionTextureVertex top2 = new PositionTextureVertex(
						(float) PDir.xCoord + (float) headPosition.xCoord,
						(float) PDir.yCoord + (float) headPosition.yCoord + 0.01f,
						(float) PDir.zCoord + (float) headPosition.zCoord, 0, 0);

				this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] { rightBack, leftBack, top, top }, 10,
						0, 19, 10, textureWidth, textureHeight); // petit
				this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] { top2, top2, leftBack2, rightBack2 },
						10, 0, 19, 10, textureWidth, textureHeight); // petit
				this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] { leftBack, leftFront, top, top }, 10,
						0, 19, 10, textureWidth, textureHeight); // petit
				this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] { leftFront2, leftBack2, top2, top2 },
						10, 0, 19, 10, textureWidth, textureHeight); // petit
				this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] { leftFront, rightFront, top, top }, 10,
						0, 19, 10, textureWidth, textureHeight); // petit
				this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] { top2, top2, rightFront2, leftFront2 },
						10, 0, 19, 10, textureWidth, textureHeight); // petit
				this.quadList[6] = new TexturedQuad(new PositionTextureVertex[] { top, rightFront, rightBack, top }, 10,
						0, 19, 10, textureWidth, textureHeight); // petit
				this.quadList[7] = new TexturedQuad(new PositionTextureVertex[] { top2, rightBack2, rightFront2, top2 },
						10, 0, 19, 10, textureWidth, textureHeight); // petit

				this.quadList[8] = new TexturedQuad(new PositionTextureVertex[] { strapLB, strapLF, chinLF, chinLB },
						38, 31, 64, 32, textureWidth, textureHeight); // petit
				this.quadList[9] = new TexturedQuad(
						new PositionTextureVertex[] { chinLB2, chinLF2, strapLF2, strapLB2 }, 38, 31, 64, 32,
						textureWidth, textureHeight); // petit

				this.quadList[10] = new TexturedQuad(new PositionTextureVertex[] { strapRB, strapRF, chinRF, chinRB },
						38, 31, 64, 32, textureWidth, textureHeight); // petit
				this.quadList[11] = new TexturedQuad(
						new PositionTextureVertex[] { chinRB2, chinRF2, strapRF2, strapRB2 }, 38, 31, 64, 32,
						textureWidth, textureHeight); // petit

				this.quadList[12] = new TexturedQuad(new PositionTextureVertex[] { chinLB, chinLF, chinRF, chinRB }, 38,
						31, 64, 32, textureWidth, textureHeight); // petit
				this.quadList[13] = new TexturedQuad(new PositionTextureVertex[] { chinRB, chinRF, chinLF, chinLB }, 38,
						31, 64, 32, textureWidth, textureHeight); // petit

				for (int i = 0; i < quadList.length; i++)
				{
					if (quadList[i] != null)
						this.quadList[i].draw(Tessellator.instance, 0.0625F);
				}
			}

			// GL11.glDisable(GL11.GL_CULL_FACE);
			// GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			if (!altStrawHat)
			{
				this.base.render(0.0625F);
			}
			// GL11.glScalef(1, 0.5f, 1);
			// this.strawBrim.render(0.0625F);
			// GL11.glScalef(1, 2f, 1);
			// GL11.glEnable(GL11.GL_CULL_FACE);
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

			this.hat.render(0.0625F);
		}
	}

}
