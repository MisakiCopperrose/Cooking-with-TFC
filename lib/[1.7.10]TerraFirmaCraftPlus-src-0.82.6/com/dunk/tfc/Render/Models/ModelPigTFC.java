package com.dunk.tfc.Render.Models;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Entities.Mobs.EntityPigTFC;
import com.dunk.tfc.api.Entities.IAnimal;
import com.dunk.tfc.api.Entities.IAnimal.GenderEnum;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ModelPigTFC extends ModelPig
{
	public ModelPigTFC()
	{
		this(0.0F);
	}

	private ModelRenderer base;
	private ModelRenderer base2;
	private ModelRenderer tusk1;
	private ModelRenderer tusk2;
	private ModelRenderer snout;

	private ModelRenderer jawTusk1a;
	private ModelRenderer jawTusk2a;

	private ModelRenderer jawTusk1b;
	private ModelRenderer jawTusk2b;

	private ModelRenderer leftShoulder;
	private ModelRenderer leftUpperArm;
	private ModelRenderer leftForeArm;
	private ModelRenderer leftWrist;
	private ModelRenderer leftToe;

	private ModelRenderer rightShoulder;
	private ModelRenderer rightUpperArm;
	private ModelRenderer rightForeArm;
	private ModelRenderer rightWrist;
	private ModelRenderer rightToe;

	private ModelRenderer leftThigh;
	private ModelRenderer leftCalf;
	private ModelRenderer leftFoot;
	private ModelRenderer leftBackToe;

	private ModelRenderer rightThigh;
	private ModelRenderer rightCalf;
	private ModelRenderer rightFoot;
	private ModelRenderer rightBackToe;

	private ModelRenderer belly;
	private ModelRenderer butt;
	private ModelRenderer tail;
	private ModelRenderer neck;
	private ModelRenderer jaw;

	private ModelRenderer leftEar;
	private ModelRenderer rightEar;

	private ModelRenderer mane, mane1, mane2;

	public ModelPigTFC(float par1)
	{
		super(par1);
		textureWidth = 128;
		textureHeight = 64;
		this.head = new ModelRendererAnimalHead(this, 0, 0);
		this.head.addBox(-3.5F, -4.0F, -8.0F, 6, 8, 8, par1);
		this.head.setRotationPoint(0.0F, (float) (10 - par1), -2.0F);

		tusk1 = new ModelRenderer(this, 2, 5);
		tusk1.addBox(-0.5F, 0F, -0.5F, 1, 2, 1, 0F);
		tusk1.setRotationPoint(-3f, 0.5f, -9f);
		tusk1.rotateAngleX = (float) Math.PI / 12;

		tusk2 = new ModelRenderer(this, 2, 5);
		tusk2.addBox(-0.5F, 0F, -0.5F, 1, 2, 1, 0F);
		tusk2.setRotationPoint(2f, 0.5f, -9f);
		tusk2.rotateAngleX = (float) Math.PI / 12;

		jawTusk1a = new ModelRenderer(this, 2, 5);
		jawTusk1a.addBox(-0.5F, 0F, -0.5F, 1, 2, 1, 0F);
		jawTusk1a.setRotationPoint(0f, 0f, 0f);

		jawTusk2a = new ModelRenderer(this, 2, 5);
		jawTusk2a.addBox(-0.5F, 0F, -0.5F, 1, 2, 1, 0F);
		jawTusk2a.setRotationPoint(0, 0f, 0f);

		jawTusk1b = new ModelRendererAnimalHead(this, 2, 5);
		jawTusk1b.addBox(-0.5F, 0F, -0.5F, 1, 2, 1, 0F);
		jawTusk1b.rotateAngleX = (float) Math.PI / 12;

		jawTusk2b = new ModelRendererAnimalHead(this, 2, 5);
		jawTusk2b.addBox(-0.5F, 0F, -0.5F, 1, 2, 1, 0F);
		jawTusk2b.rotateAngleX = (float) Math.PI / 12;

		snout = new ModelRendererAnimalHead(this, 0, 16);
		snout.addBox(-2.0F, 0.0F, -10.0F, 4, 3, 10, par1);
		snout.addChild(tusk1);
		snout.setTextureOffset(50, 48).addBox(-2.5f, -1.25f, -12f, 5, 4, 3, -0.75f);
		snout.addChild(tusk2);

		leftEar = new ModelRenderer(this, 0, 0);
		leftEar.addBox(0, 0, 0, 3, 4, 1);
		head.addChild(leftEar);

		rightEar = new ModelRenderer(this, 0, 0);

		rightEar.addBox(-3, 0, 0, 3, 4, 1);
		rightEar.mirror = true;
		head.addChild(rightEar);

		jaw = new ModelRendererAnimalHead(this, 18, 16);
		jaw.addBox(0, 0, -10, 3, 3, 7);
		head.addChild(jaw);

		leftThigh = new ModelRenderer(this, 46, 9);
		leftThigh.addBox(0F, 0F, 0F, 5, 8, 7);
		leftThigh.setRotationPoint(-3, 2, 8);

		leftCalf = new ModelRenderer(this, 20, 0);
		leftCalf.addBox(0f, 0f, 0f, 3, 4, 4);
		leftCalf.setRotationPoint(-0.01f, 8, 0);
		leftThigh.addChild(leftCalf);

		leftFoot = new ModelRenderer(this, 1, 20);
		leftFoot.addBox(0f, -1f, 0f, 2, 4, 2);
		leftFoot.setRotationPoint(0.01f, 4, 0);
		leftCalf.addChild(leftFoot);

		leftBackToe = new ModelRenderer(this, 1, 16);
		leftBackToe.addBox(0, 0, 0, 2, 2, 2);
		leftBackToe.setRotationPoint(0, 4, 0);
		leftFoot.addChild(leftBackToe);

		rightThigh = new ModelRenderer(this, 46, 9);
		rightThigh.addBox(0F, 0F, 0F, 5, 8, 7);
		rightThigh.setRotationPoint(3, 2, 8);
		rightThigh.mirror = true;

		rightCalf = new ModelRenderer(this, 20, 0);
		rightCalf.addBox(0f, 0f, 0f, 3, 4, 4);
		rightCalf.setRotationPoint(0.01f, 8, 0);
		rightThigh.addChild(rightCalf);
		rightCalf.mirror = true;

		rightFoot = new ModelRenderer(this, 1, 20);
		rightFoot.addBox(0f, -1f, 0f, 2, 4, 2);
		rightFoot.setRotationPoint(-0.01f, 4, 0);
		rightCalf.addChild(rightFoot);

		rightBackToe = new ModelRenderer(this, 1, 16);
		rightBackToe.addBox(0, 0, 0, 2, 2, 2);
		rightBackToe.setRotationPoint(0, 4, 0);
		rightFoot.addChild(rightBackToe);
		rightFoot.mirror = true;

		leftShoulder = new ModelRenderer(this, 46, 1);
		leftShoulder.addBox(0F, 0F, 0F, 3, 4, 3);
		leftShoulder.setRotationPoint(-3, 2, -3);

		leftUpperArm = new ModelRenderer(this, 57, 0);
		leftUpperArm.addBox(0F, -1F, 0F, 3, 5, 4);
		leftShoulder.addChild(leftUpperArm);

		leftForeArm = new ModelRenderer(this, 63, 9);
		leftForeArm.addBox(0F, 0F, 0F, 3, 4, 3);
		leftUpperArm.addChild(leftForeArm);

		leftWrist = new ModelRenderer(this, 3, 29);
		leftWrist.addBox(0f, 0f, 0f, 2, 3, 2);
		leftForeArm.addChild(leftWrist);

		leftToe = new ModelRenderer(this, 1, 16);
		leftToe.addBox(0, 0, 0, 2, 2, 2);
		leftWrist.addChild(leftToe);

		rightShoulder = new ModelRenderer(this, 46, 1);
		rightShoulder.addBox(0F, 0F, 0F, 3, 4, 3);
		rightShoulder.setRotationPoint(3, 2, -3);
		rightShoulder.mirror = true;

		rightUpperArm = new ModelRenderer(this, 57, 0);
		rightUpperArm.addBox(0F, -1F, 0F, 3, 5, 4);
		rightShoulder.addChild(rightUpperArm);
		rightUpperArm.mirror = true;

		rightForeArm = new ModelRenderer(this, 63, 9);
		rightForeArm.addBox(0F, 0F, 0F, 3, 4, 3);
		rightUpperArm.addChild(rightForeArm);
		rightForeArm.mirror = true;

		rightWrist = new ModelRenderer(this, 3, 29);
		rightWrist.addBox(0f, 0f, 0f, 2, 3, 2);
		rightForeArm.addChild(rightWrist);
		rightWrist.mirror = true;

		rightToe = new ModelRenderer(this, 1, 16);
		rightToe.addBox(0, 0, 0, 2, 2, 2);
		rightWrist.addChild(rightToe);
		rightToe.mirror = true;

		this.head.addChild(snout);

		belly = new ModelRenderer(this, 28, 16);
		belly.addBox(0, 0, 0, 4, 3, 10);
		belly.setRotationPoint(0, 8, 0);

		this.body = new ModelRendererAnimalHead(this, 14, 29);
		this.body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8);

		this.body.setTextureOffset(50, 29).addBox(-3.5f, -10, 0, 7, 16, 3);

		butt = new ModelRenderer(this, 0, 34);
		butt.addBox(0, 0, 0, 5, 8, 2);
		butt.setRotationPoint(-2.5f, 0, -10f);

		tail = new ModelRenderer(this, 19, 17);
		tail.addBox(0, 0, 0, 1, 4, 1);
		butt.addChild(tail);

		neck = new ModelRenderer(this, 28, 2);
		neck.addBox(-3, 0, -6, 6, 8, 6, -0.1f);
		neck.addBox(-3, 0, -0.5f, 6, 8, 6, 0f);

		mane = new ModelRenderer(this, 65, 19);
		mane.addBox(0, -3, 0, 1, 3, 5); // On the head
		mane1 = new ModelRenderer(this, 65, 19);
		mane1.addBox(0, -3, 0, 1, 3, 5); // On the neck
		mane2 = new ModelRenderer(this, 65, 19);
		mane2.addBox(0, -4, 0, 1, 3, 5); // On the back
		mane2.addBox(0, -4, -5, 1, 3, 5); // On the back

		jawTusk2a.addChild(jawTusk2b);
		jawTusk1a.addChild(jawTusk1b);
		jaw.addChild(jawTusk1a);
		jaw.addChild(jawTusk2a);

		head.addChild(mane);
		neck.addChild(mane1);
		body.addChild(mane2);

		base = new ModelRenderer(this, 0, 0);
		base2 = new ModelRenderer(this, 0, 0);
		base.addChild(base2);
		base2.offsetY = -1f;
		base2.addChild(body);
		base2.addChild(leftShoulder);
		base2.addChild(rightShoulder);
		neck.addChild(head);
		base2.addChild(leftThigh);
		base2.addChild(rightThigh);
		base2.addChild(belly);
		base2.addChild(butt);
		base2.addChild(neck);
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float percent = TFC_Core.getPercentGrown((IAnimal) entity);
		// percent = (TFC_Time.getTotalTicks()%1000)*0.003f;
		// percent = Math.min(1f, percent);
		float ageScale = 2.0F - percent;
		float ageHeadScale = (float) Math.pow(1 / ageScale, 0.66);
		// float offset = 1.4f - percent;
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
		if (entity instanceof IAnimal)
		{
			if (((IAnimal) entity).getGender() == GenderEnum.MALE)
			{
				if (percent > 0.7f)
				{
					jawTusk1a.isHidden = false;
					jawTusk2a.isHidden = false;
					jawTusk1b.isHidden = true;
					jawTusk2b.isHidden = true;
					if (percent > 0.8f)
					{
						tusk1.isHidden = false;
						tusk2.isHidden = false;

						if (percent > 0.9f)
						{
							jawTusk1b.isHidden = false;
							jawTusk2b.isHidden = false;
							mane.isHidden = mane1.isHidden = mane2.isHidden = ((EntityPigTFC) entity).isDomesticated();
						}
					}

				}
			}
			base2.offsetY = -1f;
			base2.setRotationPoint(0, 0, 0);
			// GL11.glTranslatef(0.0F, -2, 0f);
			/*
			 * GL11.glPushMatrix ();
			 * 
			 * GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
			 * GL11.glScalef(ageHeadScale, ageHeadScale, ageHeadScale);
			 * GL11.glTranslatef (0.0F,
			 * (ageScale-1)*-0.125f,0.1875f-(0.1875f*percent));
			 * head.render(par7); GL11.glPopMatrix();
			 */

			if (head instanceof ModelRendererAnimalHead)
			{
				((ModelRendererAnimalHead) head).setScalesPercent(ageHeadScale, ageScale, percent);
				((ModelRendererAnimalHead) snout).setScalesPercent(0.8f + (0.2f * percent), 1, 1);
				if (((EntityPigTFC) entity).isDomesticated())
				{
					snout.rotationPointY += 2.5f;
					snout.rotationPointZ += 1.5f;
					jaw.rotationPointY += 0.5f;
					jaw.rotationPointZ += 1f;
					jaw.rotationPointY += 1f;
					snout.rotateAngleX -= 0.4f;
					jaw.rotateAngleX -= 0.4f;

					leftEar.rotationPointY -= 1f;
					leftEar.rotationPointZ += 1f;
					rightEar.rotationPointY -= 1f;
					rightEar.rotationPointZ += 1f;
					leftEar.rotateAngleZ = 0.5f;
					rightEar.rotateAngleZ = -0.5f;
					leftEar.rotateAngleX = 2.9f;
					rightEar.rotateAngleX = 2.9f;
					rightEar.rotateAngleY = -0.3f;
					leftEar.rotateAngleY = 0.3f;

				}
				else
				{
					/*
					 * snout.rotationPointY += 1f * (1f - percent);
					 * jaw.rotationPointY += 1f * (1f - percent);
					 * snout.rotateAngleX -= 0.4f * (1f - percent);
					 * jaw.rotateAngleX -= 0.4f * (1f - percent);
					 */
					snout.rotationPointY += 2.5f * (1f - percent);
					snout.rotationPointZ += 1.5f * (1f - percent);
					jaw.rotationPointY += 0.5f * (1f - percent);
					jaw.rotationPointZ += 1f * (1f - percent);
					jaw.rotationPointY += 1f * (1f - percent);
					snout.rotateAngleX -= 0.4f * (1f - percent);
					jaw.rotateAngleX -= 0.4f * (1f - percent);
				}
				((ModelRendererAnimalHead) jaw).setScalesPercent(0.9f + (0.1f * percent), 1, 1);
			}
			float tickRate = (TFC_Time.getTotalTicks() % 1000) * 0.1f;
			if (!entity.isDead)
			{
				((ModelRendererAnimalHead) body).setScalesPercent(1 + MathHelper.sin(tickRate * (1f)) * 0.02f, 1, 1);
			}

			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
			GL11.glScalef(1 / ageScale, 1 / ageScale, 1 / ageScale);
			if (!((EntityPigTFC) entity).isDomesticated())
			{
				GL11.glTranslatef(0.0F, 0.45f, 0f);
				GL11.glScalef(0.7f, 0.7f, 0.7f);
			}
			// body.render(par7);
			base.render(par7);
			// leg1.render(par7);
			// leg2.render(par7);
			// leg3.render(par7);
			// leg4.render(par7);
			// leftShoulder.render(par7);
			// rightShoulder.render(par7);

			GL11.glPopMatrix();
			// GL11.glTranslatef(0.0F, 2, 0f);

		}
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity)
	{
		float foraging = ((EntityPigTFC) entity).forageTimer;
		float sleeping = Math.max(((EntityPigTFC) entity).getSleepTimer(), 0) * 1.25f;
		sleeping *= 2f;
		boolean gettingUp = sleeping > 0;
		// super.setRotationAngles(par1, par2, par3, par4, par5, par6);
		tusk1.isHidden = true;
		tusk2.isHidden = true;
		jawTusk1a.isHidden = true;
		jawTusk2a.isHidden = true;
		mane.isHidden = mane1.isHidden = mane2.isHidden = true;
		base.rotateAngleX = 0;
		// neck.rotateAngleX = 0.65f;
		neck.rotateAngleX = -0.3f;
		if (entity.isInWater())
		{
			par1 = (((float) TFC_Time.getTotalTicks() % 10000) * 0.75f) * (1f - par6);
			par2 = 1.25f;
			base.rotateAngleX = -0.05f;
			neck.rotateAngleX = -0.45f;
		}
		else
		{
			par2 = 0.5f;
			par1 *= 2f;
			par2 *= 1.5f;
		}

		float offset = (float) Math.PI;
		float backOffset = -(float) Math.PI * 0.1f;
		float sleepOffset = 0;
		float thighOffset = 0;

		if (gettingUp)
		{
			par1 %= (Math.PI * 2f / 0.6662F);
			if (par1 > (Math.PI * 1f / 0.6662F))
			{
				par1 += ((Math.PI * 2f / 0.6662F) - par1) * (Math.min(sleeping, 20) / 20f);
			}
			else
			{
				par1 *= (Math.max(20 - sleeping, 0) / 20f);
			}
			par2 *= (Math.max(20 - sleeping, 0) / 20f);
			par1 %= (Math.PI * 2f / 0.6662F);
			// if(par1 % (Math.PI * 2f / 0.6662F) ==0)
			if (par1 % (Math.PI * 2f / 0.6662F) > -0.01f && par1 % (Math.PI * 2f / 0.6662F) < 0.01f)
			{

				par1 = Math.max((float) (Math.PI * 2f / 0.6662F) - (float) Math.pow(sleeping * 0.1f, 1.5f),
						(float) (Math.PI * 0.4f / 0.6662F));
				/*
				 * par1 = (par1 * Math.max(20 - sleeping, 0) / 20f) +
				 * ((Math.min(sleeping, 20) / 20f) * ((6f) - (((float) Math
				 * .pow((float) Math.min(sleeping, 40), 2) / 40f) * 0.1f) * (1f
				 * - par6)));
				 */
				// par1 = sleeping - par1;
				par2 = 1f * (Math.min(sleeping, 10) / 10f);
				;
				offset *= Math.max(20 - sleeping, 0) / 20f;
				backOffset *= Math.max(20 - sleeping, 0) / 20f;
				// sleepOffset = 0; //+ (sleeping > 20 ? (float) Math.PI * 0.75f
				// * (sleeping - 20f) / 140f : 0f);
			}
			thighOffset = -Math.min(sleeping, 10) / 10f;
		}
		// par1 = (((float)TFC_Time.getTotalTicks()%1000)*0.75f) * (1f-par6);
		float deltaX = (float) (entity.prevPosX - entity.posX);
		float deltaZ = (float) (entity.prevPosZ - entity.posZ);
		float speed = (float) Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

		boolean running = speed >= 0.5f;

		// par2 = 0.75f;

		neck.setRotationPoint(0f, 7f, -6.5f);
		mane.setRotationPoint(0, 0, 0);
		mane1.setRotationPoint(-0.49f, 0, -5);
		leftShoulder.setRotationPoint(2.15f, 11, -6);
		leftUpperArm.setRotationPoint(0.1f, 4, 0);
		leftForeArm.setRotationPoint(-0.1f, 1, 1);
		leftWrist.setRotationPoint(0.4f, 4, 0);
		leftToe.setRotationPoint(0.1f, 3, -0.5F);

		rightShoulder.setRotationPoint(-5.15f, 11, -6);
		rightUpperArm.setRotationPoint(-0.1f, 4, 0);
		rightForeArm.setRotationPoint(0.1f, 1, 1);
		rightWrist.setRotationPoint(0.5f - 0.1f, 4, 0);
		rightToe.setRotationPoint(0.1f, 3, -0.5F);

		if (running)
		{
			offset *= 0.35f;
			par2 *= 1.5f;
			backOffset = (float) Math.PI;
			base.rotateAngleX = (float) (MathHelper.sin(par1 * 0.6662F + backOffset + offset) * 0.05F * par2);
		}
		else
		{

		}
		tusk1.setRotationPoint(-1.25f, 2f, -7.5f);
		tusk1.rotateAngleZ = (float) (Math.PI * 0.4f);
		tusk1.rotateAngleX = (float) Math.PI / 7;

		tusk2.setRotationPoint(1.25f, 2f, -7.5f);
		tusk2.rotateAngleZ = -(float) (Math.PI * 0.4f);
		tusk2.rotateAngleX = (float) Math.PI / 7;
		tusk2.rotateAngleY = (float) Math.PI * -0.1f;

		jawTusk1a.setRotationPoint(0.5f, 0.8f, -8.5f);
		jawTusk2a.setRotationPoint(2.5f, 0.8f, -8.5f);

		jawTusk1b.setRotationPoint(0, 1.7f, 0);
		jawTusk1b.rotateAngleX = (float) (Math.PI * 0.4f);
		jawTusk1b.rotateAngleY = (float) (Math.PI * 0.1f);
		jawTusk1b.rotateAngleZ = -(float) (Math.PI * 0.05f);

		jawTusk2b.setRotationPoint(0, 1.7f, 0);
		jawTusk2b.rotateAngleX = (float) (Math.PI * 0.4f);
		jawTusk2b.rotateAngleY = (float) -(Math.PI * 0.1f);
		jawTusk2b.rotateAngleZ = (float) (Math.PI * 0.05f);

		jawTusk2a.rotateAngleX = (float) (Math.PI * 0.4f);
		jawTusk2a.rotateAngleY = (float) (Math.PI * 0.4f) + 0.4f;
		jawTusk2a.rotateAngleZ = -(float) (Math.PI * 0.1f);

		jawTusk1a.rotateAngleX = (float) (Math.PI * 0.4f);
		jawTusk1a.rotateAngleY = -(float) (Math.PI * 0.4f) - 0.4f;
		jawTusk1a.rotateAngleZ = (float) (Math.PI * 0.1f);

		// jawTusk2a.rotateAngleZ =-(float)(Math.PI * 0.6f);
		mane.rotateAngleX = -((float) Math.PI * 0.4f);
		mane.setRotationPoint(-1, -4, -1);
		mane2.setRotationPoint(-0.5f, 0, 6);
		mane2.rotateAngleX = ((float) Math.PI * 0.55f);

		this.head.setRotationPoint(0.5F, (float) (1), -5F);
		jaw.setRotationPoint(-2f, 1f, -3f);
		jaw.rotateAngleX = /* (MathHelper.sin(par1 * 0.6662F)*0.25f) */ 0.0f - (float) (Math.PI * 0.2f) /*
																										 * +
																										 * Math
																										 * .
																										 * max
																										 * (
																										 * 0,
																										 * 0
																										 * .
																										 * 5f
																										 * *
																										 * MathHelper
																										 * .
																										 * sin
																										 * (
																										 * par1
																										 * *
																										 * 0
																										 * .
																										 * 06662f
																										 * )
																										 * )
																										 */;

		float percent = TFC_Core.getPercentGrown((IAnimal) entity);
		// percent = (TFC_Time.getTotalTicks()%1000)*0.003f;
		// percent = Math.min(1f, percent);
		leftEar.setRotationPoint(1, -2, -1);
		leftEar.rotateAngleX = (float) (Math.PI * 0.55f);
		leftEar.rotateAngleY = 0;
		leftEar.rotateAngleY = (float) (Math.PI * 0.25f) * (1f - percent);
		;
		leftEar.rotateAngleZ = (float) (Math.PI * 0.35f);

		rightEar.setRotationPoint(-2, -2, -1);
		rightEar.rotateAngleX = (float) (Math.PI * 0.55f);
		rightEar.rotateAngleY = 0;
		rightEar.rotateAngleY = -(float) (Math.PI * 0.25f) * (1f - percent);
		rightEar.rotateAngleZ = -(float) (Math.PI * 0.35f);

		tail.rotateAngleX = -(float) (Math.PI * 0.2f);
		tail.setRotationPoint(2, 7.5f, 1);
		leftShoulder.rotateAngleX = -0.15f;
		leftUpperArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + sleepOffset) * 0.5F * par2) + 0.65f;
		leftForeArm.rotateAngleX = -MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 > 0 ? -0.5f
				: -MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 - 0.5f;
		leftWrist.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 > 0 ? -0f
				: -MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 - 0f);
		leftToe.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + sleepOffset) * par2 < 0 ? -0.25f
				: -MathHelper.sin(par1 * 0.6662F + sleepOffset) * 0.25f * par2 - 0.25f);

		rightShoulder.rotateAngleX = -0.15f;
		rightUpperArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + offset) * 0.5F * par2) + 0.65f;
		rightForeArm.rotateAngleX = -MathHelper.sin(par1 * 0.6662F + offset) * par2 > 0 ? -0.5f
				: -MathHelper.sin(par1 * 0.6662F + offset) * par2 - 0.5f;
		rightWrist.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + offset) * par2 > 0 ? -0f
				: -MathHelper.sin(par1 * 0.6662F + offset) * par2 - 0f);
		rightToe.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + offset) * par2 < 0 ? -0.25f
				: -MathHelper.sin(par1 * 0.6662F + offset) * 0.25f * par2 - 0.25f);

		// float thighOffset = -0.75f *
		// ((float)((Math.min(40,sleeping)-40)*2f)/40f);

		leftThigh.setRotationPoint(0.05f, 8, 8);
		leftThigh.rotateAngleX = (MathHelper
				.cos((par1 * 0.6662F) + offset + backOffset + thighOffset) * 0.35F * par2) - 0.35f;
		leftCalf.setRotationPoint(2f - 0.01f, 8, 2);
		leftCalf.rotateAngleX = -MathHelper.sin((par1 * 0.6662F) + offset + backOffset + sleepOffset) * par2 > 0 ? 0.5f
				: MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * 0.75f * par2 + 0.5f;
		leftFoot.setRotationPoint(0, 4, 0.5f);
		leftFoot.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * par2 > 0
				? +0.25f : MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * 2f * par2 + 0.25f);
		leftBackToe.setRotationPoint(0, 3, -0.5f);
		leftBackToe.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * par2 < 0
				? -0.25f : -MathHelper.sin(par1 * 0.6662F + offset + backOffset + sleepOffset) * 0.25f * par2 - 0.25f);

		rightThigh.setRotationPoint(-5.05f, 8, 8);
		rightThigh.rotateAngleX = (MathHelper.cos((par1 * 0.6662F) + backOffset + thighOffset) * 0.35F * par2) - 0.35f;
		rightCalf.setRotationPoint(0f + 0.01f, 8, 2);
		rightCalf.rotateAngleX = -MathHelper.sin((par1 * 0.6662F) + backOffset) * par2 > 0 ? 0.5f
				: MathHelper.sin(par1 * 0.6662F + backOffset) * 0.75f * par2 + 0.5f;
		rightFoot.setRotationPoint(0, 4, 0.5f);
		rightFoot.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + backOffset) * par2 > 0 ? +0.25f
				: MathHelper.sin(par1 * 0.6662F + backOffset) * 2f * par2 + 0.25f);
		rightBackToe.setRotationPoint(0, 3, -0.5f);
		rightBackToe.rotateAngleX = -(-MathHelper.sin(par1 * 0.6662F + backOffset) * par2 < 0 ? -0.25f
				: -MathHelper.sin(par1 * 0.6662F + backOffset) * 0.25f * par2 - 0.25f);

		belly.setRotationPoint(-2, 14.5f, -2);

		butt.setRotationPoint(-2.5f, 7.5f, 7f);
		butt.rotateAngleX = (float) (Math.PI * 0.3f);

		// leftToe.rotateAngleX = -(leftWrist.rotateAngleX +
		// leftForeArm.rotateAngleX + leftUpperArm.rotateAngleX
		// +leftShoulder.rotateAngleX);
		// leftToe.rotateAngleX = 0.5f;
		this.head.rotateAngleX = (par5 / (180F / (float) Math.PI)) + (float) (Math.PI * 0.45f);
		this.head.rotateAngleY = ((par4 / (180F / (float) Math.PI)) * 0.5f);
		this.neck.rotateAngleY = this.head.rotateAngleY;

		this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
		this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;

		float baseDis = par1 * 2f;
		base.setRotationPoint(0, (!gettingUp ? (MathHelper.cos(baseDis * 0.6662F) * 0.5F * par2) : 0) + 16f, 0);
		// We simulate where the left wrist is, so we can calculate how to
		// adjust the body.

		// base.rotateAngleX = (float) + (MathHelper.cos(par1 * 0.6662F) * 0.05F
		// * par2);
		head.rotateAngleZ = 0;
		neck.rotateAngleZ = 0f;
		leftShoulder.rotateAngleZ = 0f;
		leftThigh.rotateAngleZ = 0f;
		base.rotateAngleZ = (float) +(MathHelper.cos(par1 * 0.6662F) * 0.025F * par2) - (sleeping > 40
				? (float) (Math.pow((Math.min(59, sleeping) - 40f) * 2f, 2)) / 1600f : 0);
		neck.rotateAngleZ += (sleeping > 40 ? (float) (Math.pow((Math.min(59, sleeping) - 40f) * 2f, 2)) / 1600f
				: 0) * 0.25f;
		head.rotateAngleZ += (sleeping > 40 ? (float) (Math.pow((Math.min(59, sleeping) - 40f) * 2f, 2)) / 1600f
				: 0) * 0.25f;
		head.rotateAngleY -= (sleeping > 40 ? (float) (Math.pow((Math.min(59, sleeping) - 40f) * 2f, 2)) / 1600f
				: 0) * 0.5f;
		head.rotateAngleX += (sleeping > 40 ? (float) (Math.pow((Math.min(59, sleeping) - 40f) * 2f, 2)) / 1600f
				: 0) * 0.25f;
		float forageRotation = 0;
		forageRotation += 1f * (foraging > 105 ? 1f - ((foraging - 105f) / 15f) : 0f);
		forageRotation += 1f * (foraging <= 105 && foraging > 0 ? (Math.min(foraging, 15) / 15f) : 0f);
		neck.rotateAngleX += forageRotation;
		head.rotateAngleX -= forageRotation * 0.5f;
		neck.rotateAngleY += 0.1f * (foraging >= 15 && foraging <= 105 ? MathHelper.sin((foraging - 15) * 0.1f) : 0f);

		// neck.rotateAngleZ = -0.5f;
		rightShoulder.rotateAngleZ = -0.35f * (sleeping > 40
				? (float) (Math.pow((Math.min(60, sleeping) - 40f) * 2f, 2)) / 1600f : 0);
		rightThigh.rotateAngleZ = -0.35f * (sleeping > 40
				? (float) (Math.pow((Math.min(60, sleeping) - 40f) * 2f, 2)) / 1600f : 0);
		// base.rotateAngleX = (float) + (MathHelper.cos(par1 * 0.6662F *2f) *
		// 0.5F * par2);
		this.body.setRotationPoint(0, 9.5f, -1);
		this.body.rotateAngleY = (float) Math.PI;
		this.body.rotateAngleX = -(0.1f + (float) (3f * Math.PI) / 2f);
		// base.rotationPointY = 12;
		this.snout.setRotationPoint(-0.5f, -3.8f, -1.5f);
		this.snout.rotateAngleX = -(float) (Math.PI * 0.1f);

		base.rotationPointY += Math.pow(Math.max(Math.min(sleeping - 10, 30), 0), 2) / 200f;
		base.rotateAngleX -= Math.min(sleeping, 40) / 1000f;
		/*
		 * if (sleeping > 25) { Vec3[] armVecs = new Vec3[6]; armVecs[0] =
		 * Vec3.createVectorHelper(base2.rotationPointX, base2.rotationPointY,
		 * base2.rotationPointZ); armVecs[1] =
		 * Vec3.createVectorHelper(rightShoulder.rotationPointX,
		 * rightShoulder.rotationPointY + base2.offsetY * 16f,
		 * rightShoulder.rotationPointZ); armVecs[2] =
		 * Vec3.createVectorHelper(rightUpperArm.rotationPointX,
		 * rightUpperArm.rotationPointY, rightUpperArm.rotationPointZ);
		 * armVecs[3] = Vec3.createVectorHelper(rightForeArm.rotationPointX,
		 * rightForeArm.rotationPointY + rightUpperArm.offsetY * 16f,
		 * rightForeArm.rotationPointZ); armVecs[4] =
		 * Vec3.createVectorHelper(rightWrist.rotationPointX,
		 * rightWrist.rotationPointY, rightWrist.rotationPointZ); armVecs[5] =
		 * Vec3.createVectorHelper(rightToe.rotationPointX,
		 * rightToe.rotationPointY - 2, rightToe.rotationPointZ);
		 * 
		 * Vec3[] rotationVecs = new Vec3[6]; rotationVecs[0] =
		 * Vec3.createVectorHelper(base.rotateAngleX, base.rotateAngleY,
		 * base.rotateAngleZ); rotationVecs[1] =
		 * Vec3.createVectorHelper(rightShoulder.rotateAngleX,
		 * rightShoulder.rotateAngleY, rightShoulder.rotateAngleZ);
		 * rotationVecs[2] = Vec3.createVectorHelper(rightUpperArm.rotateAngleX,
		 * rightUpperArm.rotateAngleY, rightUpperArm.rotateAngleZ);
		 * rotationVecs[3] = Vec3.createVectorHelper(rightForeArm.rotateAngleX,
		 * rightForeArm.rotateAngleY, rightForeArm.rotateAngleZ);
		 * rotationVecs[4] = Vec3.createVectorHelper(rightWrist.rotateAngleX,
		 * rightWrist.rotateAngleY, rightWrist.rotateAngleZ); rotationVecs[5] =
		 * Vec3.createVectorHelper(rightToe.rotateAngleX, rightToe.rotateAngleY,
		 * rightToe.rotateAngleZ);
		 * 
		 * for (int j = 0; j < 6; j++) { for (int i = j; i < 6; i++) {
		 * armVecs[i].rotateAroundZ((float) rotationVecs[j].zCoord);
		 * 
		 * armVecs[i].rotateAroundY((float) rotationVecs[j].yCoord);
		 * armVecs[i].rotateAroundX((float) rotationVecs[j].xCoord); } } Vec3
		 * finalVec = Vec3.createVectorHelper(0, 0, 0); for (int i = 0; i < 6;
		 * i++) { finalVec = finalVec.addVector(armVecs[i].xCoord,
		 * armVecs[i].yCoord, armVecs[i].zCoord); } //
		 * System.out.println((finalVec.lengthVector() - // 6.808019161224365));
		 * base.rotationPointY += Math.min(Math.max((finalVec.lengthVector() -
		 * 7.808019161224365), 0), 3); }
		 */
		// base.rotationPointY -= 16f;
		// this.head.rotateAngleX = 0;
		// We want to make sure the head moves with the body properly

	}
}
