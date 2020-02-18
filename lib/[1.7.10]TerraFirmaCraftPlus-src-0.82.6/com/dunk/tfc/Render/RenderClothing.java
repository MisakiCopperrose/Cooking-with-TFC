package com.dunk.tfc.Render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Entities.EntityStand;
import com.dunk.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.Items.ItemCoat;
import com.dunk.tfc.Items.ItemHat;
import com.dunk.tfc.Items.ItemPants;
import com.dunk.tfc.Items.ItemShirt;
import com.dunk.tfc.Items.ItemSkirt;
import com.dunk.tfc.Items.ItemSocks;
import com.dunk.tfc.Items.ItemTFCArmor;
import com.dunk.tfc.Render.Models.ModelCoat;
import com.dunk.tfc.Render.Models.ModelHat;
import com.dunk.tfc.Render.Models.ModelPants;
import com.dunk.tfc.Render.Models.ModelShirt;
import com.dunk.tfc.Render.Models.ModelSkirt;
import com.dunk.tfc.Render.Models.ModelSocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Interfaces.IEquipable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderClothing
{
	private ModelSocks socks = new ModelSocks(0.2f);
	private ModelSocks boots = new ModelSocks(0.6f, 0, 29, true, false);
	private ModelSocks fullboots = new ModelSocks(0.5f, 0, 29, false, false);
	private ModelSocks sandals = new ModelSocks(0.4f, 6, 29, false, true);
	private ModelPants pants = new ModelPants(0.25f);
	private ModelPants thinpants = new ModelPants(0.25f);
	private ModelShirt shirt = new ModelShirt(0.3f);
	private ModelShirt thinshirt = new ModelShirt(0.19f);
	private ModelCoat coat = new ModelCoat(0.6f, false, false, true, 0);
	private ModelCoat thincoat = new ModelCoat(0.55f, false, false, true, 0);
	private ModelCoat heavycoat = new ModelCoat(0.7f, false, false, true, 0);
	private ModelCoat heaviercoat = new ModelCoat(0.8f, false, false, true, 0f);
	private ModelCoat cloak = new ModelCoat(1f, true, false, true, 0f);
	private ModelCoat robe = new ModelCoat(0.6f, true, false, false, 0.05f);
	private ModelSkirt skirt = new ModelSkirt(0.1f);
	private ModelHat hat = new ModelHat(-0.1f);

	private RenderPlayer bipedPlayer = new RenderPlayer();

	public RenderClothing()
	{

	}

	public void render(EntityLivingBase entity, ItemStack item, RenderPlayer renderer, float partialRenderTick, ItemStack[] armor)
	{
		this.doRender(entity, item, renderer, partialRenderTick, armor);
	}

	public void switchRender(EntityLivingBase entity, ItemStack item, RenderPlayer renderer, float partialRenderTick, ItemStack[] armor)
	{
		switch (((IEquipable) (item.getItem())).getClothingType())
		{
		case SOCKS:
			this.socks.render(entity, item, renderer, partialRenderTick);
			break;
		case PANTS:
			this.pants.render(entity, item, renderer, partialRenderTick);
			break;
		case BOOTS:
			this.boots.render(entity, item, renderer, partialRenderTick);
			break;
		case FULLBOOTS:
			this.fullboots.render(entity, item, renderer, partialRenderTick);
			break;
		case SHIRT:
			this.shirt.render(entity, item, renderer, partialRenderTick);
			break;
		case SANDALS:
			this.sandals.render(entity, item, renderer, partialRenderTick);
			break;
		case COAT:
			this.coat.render(entity, item, renderer, partialRenderTick);
			break;
		case THINCOAT:
			this.thincoat.render(entity, item, renderer, partialRenderTick);
			break;
		case THINSHIRT:
			this.thinshirt.render(entity, item, renderer, partialRenderTick);
			break;
		case THINPANTS:
			this.thinpants.render(entity, item, renderer, partialRenderTick);
			break;
		case ROBE:
			this.robe.render(entity, item, renderer, partialRenderTick);
			break;
		case HEAVYCOAT:
		{
			this.heavycoat.render(entity, item, renderer, partialRenderTick);
			break;
		}
		case CLOAK:
			this.cloak.render(entity, item, renderer, partialRenderTick);
			break;
		case HEAVIERCOAT:
			this.heaviercoat.render(entity, item, renderer, partialRenderTick);
			break;
		case SKIRT:
			this.skirt.render(entity, item, renderer, partialRenderTick);
			break;
		case CLOTH_HAT:
		case STRAW_HAT:
			this.hat.render(entity, item, renderer, partialRenderTick, armor[3] != null);
			break;
		default:
			break;
		}

	}

	public void doRender(EntityLivingBase entity, ItemStack item, RenderPlayer renderer, float partialRenderTick, ItemStack[] armor)
	{
		float entityTranslateY = entity instanceof EntityPlayer ? 0F : -1.5F;
		GL11.glPushMatrix();
		IEquipable ie = (IEquipable) (item.getItem());
		int x = 0;
		if (item.getItem() instanceof ItemTFCArmor)
		{
			x = ((ItemTFCArmor) item.getItem()).getUnadjustedArmorType();
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(ie.getClothingTexture(entity, item, x));

		if (item != null)
		{
			if (item.getItem() instanceof IEquipable)
			{
				GL11.glPushMatrix();

				EntityPlayer cp = Minecraft.getMinecraft().thePlayer;
				if (!cp.equals(entity) && entity instanceof EntityPlayer)
				{
					GL11.glTranslated(-interpolatePositions(cp.prevPosX, cp.posX, partialRenderTick), -interpolatePositions(cp.prevPosY, cp.posY, partialRenderTick),
					-interpolatePositions(cp.prevPosZ, cp.posZ, partialRenderTick));
					// makeAdjustments(entity, partialRenderTick);
				}

				if (!entity.isSneaking())
				{
					// GL11.glTranslatef(0F, entityTranslateY + 0.0F/* 0.65F */,
					// 0.1F);
				}
				else
				{
					GL11.glTranslatef(0F, entityTranslateY + 0.1F/* 0.55F */, 0.1F);
					GL11.glRotatef(20F, 1F, 0F, 0F);
				}
				((IEquipable) (item.getItem())).onEquippedRender();
				GL11.glPopMatrix();
			}
			switchRender(entity, item, renderer, partialRenderTick, armor);
		}
		GL11.glPopMatrix();
	}

	public void makeAdjustments(EntityLivingBase entity, float partialRenderTick)
	{
		try
		{
			float f2 = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialRenderTick);
			float f3 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialRenderTick);
			float f4;

			if (entity.isRiding() && entity.ridingEntity instanceof EntityLivingBase)
			{
				EntityLivingBase entitylivingbase1 = (EntityLivingBase) entity.ridingEntity;
				f2 = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, partialRenderTick);
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

			float f13 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialRenderTick;
			// this.renderLivingAt(entity, p_76986_2_, p_76986_4_, p_76986_6_);
			// GL11.glTranslated(entity.posX, entity.posY, entity.posZ);
			GL11.glTranslated(interpolatePositions(entity.prevPosX, entity.posX, partialRenderTick), interpolatePositions(entity.prevPosY, entity.posY, partialRenderTick),
			interpolatePositions(entity.prevPosZ, entity.posZ, partialRenderTick));
			GL11.glTranslatef(0, 2f / 16f, 0f);
			f4 = this.handleRotationFloat(entity, partialRenderTick);
			// this.rotateCorpse(entity, f4, f2, partialRenderTick);
			float f5 = 0.0625F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			// this.preRenderCallback(entity, partialRenderTick);
			GL11.glTranslatef(0.0F, -24.0F * f5 - 0.0078125F, 0.0F);
			float f6 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialRenderTick;
			float f7 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialRenderTick);

			if (entity.isChild())
			{
				f7 *= 3.0F;
			}

			if (f6 > 1.0F)
			{
				f6 = 1.0F;
			}

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			// this.mainModel.setLivingAnimations(entity, f7, f6,
			// partialRenderTick);
			// this.renderModel(entity, f7, f6, f4, f3 - f2, f13, f5);
			int j;
			float f8;
			float f9;
			float f10;

			for (int i = 0; i < 4; ++i)
			{
				j = 0;

				if (j > 0)
				{
					// this.renderPassModel.setLivingAnimations(entity, f7, f6,
					// partialRenderTick);
					// this.renderPassModel.render(entity, f7, f6, f4, f3 - f2,
					// f13, f5);

					if ((j & 240) == 16)
					{
						// this.func_82408_c(entity, i, partialRenderTick);
						// this.renderPassModel.render(entity, f7, f6, f4, f3 -
						// f2, f13, f5);
					}

					if ((j & 15) == 15)
					{
						f8 = (float) entity.ticksExisted + partialRenderTick;
						// this.bindTexture(RES_ITEM_GLINT);
						GL11.glEnable(GL11.GL_BLEND);
						f9 = 0.5F;
						GL11.glColor4f(f9, f9, f9, 1.0F);
						GL11.glDepthFunc(GL11.GL_EQUAL);
						GL11.glDepthMask(false);

						for (int k = 0; k < 2; ++k)
						{
							GL11.glDisable(GL11.GL_LIGHTING);
							f10 = 0.76F;
							GL11.glColor4f(0.5F * f10, 0.25F * f10, 0.8F * f10, 1.0F);
							GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
							GL11.glMatrixMode(GL11.GL_TEXTURE);
							GL11.glLoadIdentity();
							float f11 = f8 * (0.001F + (float) k * 0.003F) * 20.0F;
							float f12 = 0.33333334F;
							GL11.glScalef(f12, f12, f12);
							GL11.glRotatef(30.0F - (float) k * 60.0F, 0.0F, 0.0F, 1.0F);
							GL11.glTranslatef(0.0F, f11, 0.0F);
							GL11.glMatrixMode(GL11.GL_MODELVIEW);
							// this.renderPassModel.render(entity, f7, f6, f4,
							// f3 - f2, f13, f5);
						}

						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glMatrixMode(GL11.GL_TEXTURE);
						GL11.glDepthMask(true);
						GL11.glLoadIdentity();
						GL11.glMatrixMode(GL11.GL_MODELVIEW);
						GL11.glEnable(GL11.GL_LIGHTING);
						GL11.glDisable(GL11.GL_BLEND);
						GL11.glDepthFunc(GL11.GL_LEQUAL);
					}

					GL11.glDisable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_ALPHA_TEST);
				}
			}

			GL11.glDepthMask(true);
			// this.renderEquippedItems(entity, partialRenderTick);
			/*
			 * float f14 = entity.getBrightness(partialRenderTick); j =
			 * this.getColorMultiplier(entity, f14, partialRenderTick);
			 * OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			 * GL11.glDisable(GL11.GL_TEXTURE_2D);
			 * OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
			 * 
			 * if ((j >> 24 & 255) > 0 || entity.hurtTime > 0 ||
			 * entity.deathTime > 0) { GL11.glDisable(GL11.GL_TEXTURE_2D);
			 * GL11.glDisable(GL11.GL_ALPHA_TEST); GL11.glEnable(GL11.GL_BLEND);
			 * GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			 * GL11.glDepthFunc(GL11.GL_EQUAL);
			 * 
			 * if (entity.hurtTime > 0 || entity.deathTime > 0) {
			 * GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
			 * this.mainModel.render(entity, f7, f6, f4, f3 - f2, f13, f5);
			 * 
			 * for (int l = 0; l < 4; ++l) { if (this.inheritRenderPass(entity,
			 * l, partialRenderTick) >= 0) { GL11.glColor4f(f14, 0.0F, 0.0F,
			 * 0.4F); this.renderPassModel.render(entity, f7, f6, f4, f3 - f2,
			 * f13, f5); } } }
			 * 
			 * if ((j >> 24 & 255) > 0) { f8 = (float)(j >> 16 & 255) / 255.0F;
			 * f9 = (float)(j >> 8 & 255) / 255.0F; float f15 = (float)(j & 255)
			 * / 255.0F; f10 = (float)(j >> 24 & 255) / 255.0F;
			 * GL11.glColor4f(f8, f9, f15, f10); this.mainModel.render(entity,
			 * f7, f6, f4, f3 - f2, f13, f5);
			 * 
			 * for (int i1 = 0; i1 < 4; ++i1) { if
			 * (this.inheritRenderPass(entity, i1, partialRenderTick) >= 0) {
			 * GL11.glColor4f(f8, f9, f15, f10);
			 * this.renderPassModel.render(entity, f7, f6, f4, f3 - f2, f13,
			 * f5); } } }
			 * 
			 * GL11.glDepthFunc(GL11.GL_LEQUAL); GL11.glDisable(GL11.GL_BLEND);
			 * GL11.glEnable(GL11.GL_ALPHA_TEST);
			 * GL11.glEnable(GL11.GL_TEXTURE_2D); }
			 */
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
		catch (Exception exception)
		{
			// logger.error("Couldn\'t render entity", exception);
		}
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

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_)
	{
		return (float) p_77044_1_.ticksExisted + p_77044_2_;
	}

	private double interpolatePositions(double prev, double current, float partialRenderTick)
	{
		return prev + ((current - prev) * partialRenderTick);
	}

	private float interpolatePositions(float prev, float current, float partialRenderTick)
	{
		return prev + ((current - prev) * partialRenderTick);
	}

	public void doRender(EntityLivingBase entity, ItemStack item, float partialRenderTick, RenderPlayer renderer, ItemStack[] armor)
	{

		float entityTranslateY = entity instanceof EntityPlayer ? 0F : -1.5F;
		GL11.glPushMatrix();

		EntityPlayer cp = Minecraft.getMinecraft().thePlayer;
		if (!(entity instanceof EntityStand/* || entity instanceof EntityMob */))
		{

			GL11.glTranslated(-interpolatePositions(cp.prevPosX, cp.posX, partialRenderTick), -interpolatePositions(cp.prevPosY, cp.posY, partialRenderTick),
			-interpolatePositions(cp.prevPosZ, cp.posZ, partialRenderTick));
			makeAdjustments(entity, partialRenderTick);
		}
		else if (!(entity instanceof EntityMob))
		{
			GL11.glRotatef(entity.rotationYaw, 0, 1, 0);
			GL11.glTranslated(0, -1.8, 0);
		}
		else
		{

			GL11.glRotatef(0, 0, 1, 0);
			GL11.glTranslated(0, -1.7, 0);
		}
		if (entity instanceof EntityMob)
		{

			if (entity instanceof EntitySkeletonTFC && ((EntitySkeletonTFC) entity).getSkeletonType() == 1)
			{
				GL11.glScalef(1.2f, 1.2F, 1.2F);
				GL11.glTranslatef(0, -0.3f, 0);
			}
			if (item != null && item.getItem() instanceof ItemHat)
			{
				GL11.glScalef(1.1f, 1.1f, 1.1f);
				GL11.glTranslatef(0, -0.03f, 0);
			}
			GL11.glRotatef(180 + entity.renderYawOffset, 0, 1, 0);
		}
		if (!(entity instanceof EntityStand || entity instanceof EntityMob))
		{
			GL11.glRotatef(180, 0, 0, 1);
		}
		// }
		// else
		// {
		// makeAdjustments(entity, partialRenderTick);
		// }

		if (cp.equals(entity) && entity instanceof EntityPlayer)
		{
			GL11.glTranslatef(0, -1.65f, 0);
		}
		if (!(entity instanceof EntityStand || entity instanceof EntityMob))
		{
			GL11.glRotatef(180f, 1, 0, 0);
		}
		IEquipable ie = (IEquipable) (item.getItem());
		int x = 0;
		if (item.getItem() instanceof ItemTFCArmor)
		{
			x = ((ItemTFCArmor) item.getItem()).getUnadjustedArmorType();
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(ie.getClothingTexture(entity, item, x));

		if (!entity.isSneaking())
		{
			// GL11.glTranslatef(0F, entityTranslateY + 0.0F/* 0.65F */, 0.1F);
		}
		else
		{
			// GL11.glTranslatef(0F, entityTranslateY + 0.1F/* 0.55F */, 0.1F);
			// GL11.glRotatef(20F, 1F, 0F, 0F);
		}
		if (item != null)
		{
			if (item.getItem() instanceof IEquipable)
			{
				((IEquipable) (item.getItem())).onEquippedRender();
			}
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			switchRender(entity, item, renderer, partialRenderTick, armor);
		}
		GL11.glPopMatrix();
	}

	public void render(EntityLivingBase entity, ItemStack item, float partialRenderTick, RenderPlayer renderer, ItemStack[] armor)
	{
		this.doRender(entity, item, partialRenderTick, renderer, armor);
	}

	public void render(EntityLivingBase entity, ItemStack item, float partialRenderTick, RenderBiped renderer, ItemStack[] armor)
	{
		this.bipedPlayer.modelBipedMain = renderer.modelBipedMain;
		this.doRender(entity, item, partialRenderTick, bipedPlayer, armor);
	}

}
