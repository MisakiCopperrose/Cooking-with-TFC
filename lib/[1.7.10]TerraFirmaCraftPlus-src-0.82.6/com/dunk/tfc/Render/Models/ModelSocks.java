package com.dunk.tfc.Render.Models;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Entities.EntityStand;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ModelSocks extends ModelBase
{
	private ModelRenderer sockL;
	private ModelRenderer sockR;
	private ModelRenderer toeL;
	private ModelRenderer toeR;

	private ModelRenderer soleR;
	private ModelRenderer soleL;

	private ModelRenderer body;

	private boolean onlyToes;

	private float scaleFactor;

	public ModelSocks()
	{
		this(0.0f);
	}

	public ModelSocks(float f)
	{
		this(f, 6, 29, false, false);
		onlyToes = false;
	}

	public ModelSocks(float f, int i, int j, boolean onlyToes, boolean sandals)
	{
		super();
		this.scaleFactor = f;
		// Quiver
		this.onlyToes = onlyToes;

		body = new ModelRenderer(this, 0, 0);
		body.addBox(-4F, 0.0F, -2F, 8, 12, 4);
		body.setRotationPoint(0, 0, 0f);

		sockL = new ModelRenderer(this, i == 6 ? 16 : i, i == 6 ? 23 : j - 7);
		toeL = new ModelRenderer(this, i, j);
		toeR = new ModelRenderer(this, i, j);
		soleL = new ModelRenderer(this, 6, 0);
		soleR = new ModelRenderer(this, 6, 0);
		sockL.mirror = true;
		sockR = new ModelRenderer(this, i == 6 ? 16 : i, i == 6 ? 23 : j - 7);
		// Should be locx,locy,locz,sizex,sizey,sizez
		sockL.addBox(-2, 7f, -2f, 4, 5, 4, scaleFactor);

		toeL.addBox(-2, 11f, -3, 4, 1, 1, scaleFactor);

		sockR.addBox(-2F, 7f, -2F, 4, 5, 4, scaleFactor);

		toeR.addBox(-2, 11f, -3, 4, 1, 1, scaleFactor);

		soleR.addBox(-2, 12f, -2, 4, 1, 5);
		soleL.addBox(-2, 12f, -2, 4, 1, 5);

		sockL.setRotationPoint(1.9f, 12f, 0f);
		sockR.setRotationPoint(-1.9f, 12f, 0f);
		body.addChild(sockL);
		body.addChild(sockR);
		sockL.addChild(toeL);
		sockR.addChild(toeR);
		sockL.addChild(soleL);
		sockR.addChild(soleR);
		/// quiver.rotateAngleZ = (float)(Math.PI/4) + (float)(Math.PI);
		// quiver.setTextureSize(64, 32);
		sockR.setTextureSize(64, 32);
		sockL.setTextureSize(64, 32);
		soleR.isHidden = !sandals;
		soleL.isHidden = !sandals;
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
			// sockL.rotateAngleY =
			// (float)Math.toRadians(theEntity.rotationYaw);
			// sockR.rotateAngleY =
			// (float)Math.toRadians(theEntity.rotationYaw);

			float wetnessMult = 1f;
			if (item.stackTagCompound != null)
			{
				wetnessMult = (12000f - (float) item.stackTagCompound.getInteger("wetness")) / 12000f;
			}
			if (item.getItem() instanceof ItemClothing)
			{
				int j = ((ItemClothing) (item.getItem())).getColor(item);
				if (j != -1)
				{

					float f1 = (float) (j >> 16 & 255) / 255.0F;
					float f2 = (float) (j >> 8 & 255) / 255.0F;
					float f3 = (float) (j & 255) / 255.0F;

					if (item.getItem() == TFCItems.leatherSandals)
					{
						int j2 = 13416087; // our new leather multiplier. the
											// old was 10511680
						f1 *= (float) (j2 >> 16 & 255) / 255.0F;
						f2 *= (float) (j2 >> 8 & 255) / 255.0F;
						f3 *= (float) (j2 & 255) / 255.0F;
						// GL11.glColor3f(f1* wetnessMult, f2* wetnessMult, f3*
						// wetnessMult);
					}

					GL11.glColor3f(f1 * wetnessMult, f2 * wetnessMult, f3 * wetnessMult);

				}
				else
				{

					float f1 = (float) 255f / 255.0F;
					float f2 = (float) 255f / 255.0F;
					float f3 = (float) 255f / 255.0F;

					if (item.getItem() == TFCItems.leatherSandals)
					{
						int j2 = 13416087; // our new leather multiplier. the
											// old was 10511680
						f1 *= (float) (j2 >> 16 & 255) / 255.0F;
						f2 *= (float) (j2 >> 8 & 255) / 255.0F;
						f3 *= (float) (j2 & 255) / 255.0F;
						// GL11.glColor3f(f1* wetnessMult, f2* wetnessMult, f3*
						// wetnessMult);
					}

					GL11.glColor3f(f1 * wetnessMult, f2 * wetnessMult, f3 * wetnessMult);
				}
			}

			float rotateAngle = 0;
			if (theEntity.ridingEntity != null && theEntity.ridingEntity instanceof EntityLiving)
			{
				rotateAngle = ((EntityLiving) theEntity.ridingEntity).prevRenderYawOffset
						+ (((EntityLiving) theEntity.ridingEntity).renderYawOffset
								- ((EntityLiving) theEntity.ridingEntity).prevRenderYawOffset) * partialRenderTick;
			}
			else if(!(theEntity instanceof EntityMob))
			{
				rotateAngle = theEntity.prevRenderYawOffset
						+ (theEntity.renderYawOffset - theEntity.prevRenderYawOffset) * partialRenderTick;
			}

			if (item.getItem() instanceof ItemArmor)
			{
				ItemArmor itemarmor = (ItemArmor) item.getItem();
				int j0 = itemarmor.getColor(item);
				if (j0 != -1)
				{
					float f1 = (float) (j0 >> 16 & 255) / 255.0F;
					float f2 = (float) (j0 >> 8 & 255) / 255.0F;
					float f3 = (float) (j0 & 255) / 255.0F;
					GL11.glColor3f(f1 * wetnessMult, f2 * wetnessMult, f3 * wetnessMult);

				}
				else
				{
					GL11.glColor3f(1.0F * wetnessMult, 1.0F * wetnessMult, 1.0F * wetnessMult);
				}
			}
			/*
			 * sockR.rotateAngleX = MathHelper.cos(theEntity.limbSwing *
			 * 0.6662F) * 1.4F * (theEntity.prevLimbSwingAmount +
			 * ((theEntity.limbSwingAmount -
			 * theEntity.prevLimbSwingAmount)*partialRenderTick)) ;
			 * sockL.rotateAngleX = MathHelper.cos(theEntity.limbSwing * 0.6662F
			 * + (float)Math.PI) * 1.4F * (theEntity.prevLimbSwingAmount +
			 * ((theEntity.limbSwingAmount -
			 * theEntity.prevLimbSwingAmount)*partialRenderTick));
			 */

			sockL.rotationPointZ = renderer.modelBipedMain.bipedLeftLeg.rotationPointZ;
			sockL.rotationPointY = renderer.modelBipedMain.bipedLeftLeg.rotationPointY;
			sockL.rotationPointX = renderer.modelBipedMain.bipedLeftLeg.rotationPointX;

			sockR.rotationPointZ = renderer.modelBipedMain.bipedRightLeg.rotationPointZ;
			sockR.rotationPointY = renderer.modelBipedMain.bipedRightLeg.rotationPointY;
			sockR.rotationPointX = renderer.modelBipedMain.bipedRightLeg.rotationPointX;

			sockL.rotateAngleZ = renderer.modelBipedMain.bipedLeftLeg.rotateAngleZ;
			sockL.rotateAngleY = renderer.modelBipedMain.bipedLeftLeg.rotateAngleY;
			sockL.rotateAngleX = renderer.modelBipedMain.bipedLeftLeg.rotateAngleX;

			sockR.rotateAngleZ = renderer.modelBipedMain.bipedRightLeg.rotateAngleZ;
			sockR.rotateAngleY = renderer.modelBipedMain.bipedRightLeg.rotateAngleY;
			sockR.rotateAngleX = renderer.modelBipedMain.bipedRightLeg.rotateAngleX;

			GL11.glPushMatrix();

			GL11.glTranslatef(0f / 16f, 2f / 16f, 0f / 16f);

			if (theEntity.ridingEntity != null)
			{
				GL11.glTranslatef(0 / 16F, 0.5f / 16F, 0f / 16F);
				// GL11.glRotatef(180, 0, 1, 0);
			}
			if(!theEntity.isPlayerSleeping())
			{
				GL11.glRotatef(rotateAngle, 0, 1, 0);
			}
			if(!theEntity.isSneaking())
			{
				GL11.glTranslatef(0, 0, 0.25f/16f);
			}

			GL11.glTranslatef(0 / 16F, 0 / 16F, -0.35f / 16F);

			// sockR.showModel = !onlyToes;
			// sockL.showModel = !onlyToes;
			if(theEntity.isPlayerSleeping())
			{
				GL11.glRotatef(-90,1,0,0);
				GL11.glTranslatef(0, 1.95f-2.2f,1.75f);
				GL11.glRotatef(180, 0, 0, 1);
			}
			 this.sockR.render(0.0625F);
			this.sockL.render(0.0625F);

			GL11.glPopMatrix();
			// body.render(0.0625f);
		}

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{

		if (entity instanceof EntityPlayer)
		{

			this.sockR.render(0.0625F);
			this.sockL.render(0.0625F);

		}
	}

}
