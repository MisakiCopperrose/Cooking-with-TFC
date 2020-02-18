package com.dunk.tfc.Render;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Entities.EntityStand;
import com.dunk.tfc.Items.ItemQuiver;
import com.dunk.tfc.Render.Models.ModelStand;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Interfaces.IEquipable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

@SideOnly(Side.CLIENT)
public class RenderEntityStand extends RenderBiped
{
	// private ModelBiped modelBipedMain;
	private ModelBiped modelArmorChestplate;
	private ModelBiped modelArmor;
	private static RenderPlayer playerRender = new RenderPlayer();
	// private static final ResourceLocation Texture = new
	// ResourceLocation("textures/entity/zombie/zombie.png");
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/stand.png");

	private ModelRenderer plume;
	private ModelRenderer plume2;
	private ModelRenderer hornR1;
	private ModelRenderer hornL1;
	private ModelRenderer hornR2;
	private ModelRenderer hornL2;

	public static final RenderQuiver RENDER_QUIVER = new RenderQuiver();
	public static final RenderLargeItem RENDER_LARGE = new RenderLargeItem();
	public static final RenderClothing RENDER_CLOTHING = new RenderClothing();

	private RenderLargeItem standBlockRenderer = new RenderLargeItem();

	public RenderEntityStand()
	{
		super(new ModelStand(), 0.5F);
		this.modelBipedMain = (ModelStand) this.mainModel;
		this.modelArmorChestplate = new ModelStand(1.0F);
		this.modelArmor = new ModelStand(0.5F);
		// Bronze
		plume = new ModelRenderer(modelArmorChestplate, 40, 0);
		plume2 = new ModelRenderer(modelArmorChestplate, 40, 0);
		plume.addBox(-1, -6, -10, 2, 6, 10, 0.5f);
		plume2.addBox(-1, -6, -10, 2, 6, 10);
		plume.setRotationPoint(0, -8, 2);
		plume2.setRotationPoint(0, -2, 4);
		plume2.rotateAngleX = (float) (Math.PI / -3f);
		// Iron
		hornR1 = new ModelRenderer(modelArmorChestplate, 40, 0);
		hornR1.addBox(-6, -1.5f, -1.5f, 3, 3, 6);
		hornL1 = new ModelRenderer(modelArmorChestplate, 40, 0);
		hornL1.addBox(6, -1.5f, -1.5f, 3, 3, 6);
		hornR1.setRotationPoint(-6, -6, 5);
		hornL1.setRotationPoint(6, -6, 8);
		hornR1.rotateAngleY = (float) (Math.PI / -2);
		hornR1.rotateAngleX = (float) Math.PI * (-1f / 12f);
		hornL1.rotateAngleY = (float) (Math.PI / 2);
		hornL1.rotateAngleX = (float) Math.PI * (-1f / 12f);
		hornR2 = new ModelRenderer(modelArmorChestplate, 40, 9);
		hornR2.addBox(0, 0, -5f, 2, 2, 5);
		hornR2.setRotationPoint(-6, 0f, 2f);
		hornR2.rotateAngleX = (float) Math.PI * (6f / 12f);
		hornR2.rotateAngleZ = (float) Math.PI * (1f / 6f);
		hornL2 = new ModelRenderer(modelArmorChestplate, 40, 9);
		hornL2.addBox(0, 0, -5f, 2, 2, 5);
		hornL2.setRotationPoint(7, 0f, 2f);
		hornL2.rotateAngleX = (float) Math.PI * (6f / 12f);
		hornL2.rotateAngleZ = (float) Math.PI * (-1f / 6f);

		modelArmorChestplate.bipedHead.addChild(plume);
		modelArmorChestplate.bipedHead.addChild(plume2);
		modelArmorChestplate.bipedHead.addChild(hornR1);
		modelArmorChestplate.bipedHead.addChild(hornL1);
		hornR1.addChild(hornR2);
		hornL1.addChild(hornL2);
		hornR1.showModel = false;
		hornL1.showModel = false;
		plume.showModel = false;
		plume2.showModel = false;
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int pass, float delta)
	{
		return this.setArmorModelTFC((EntityStand) entity, pass, delta);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return TEXTURE;
	}

	@Override
	public void doRender(Entity e, double par2, double par3, double par4, float par5, float par6)
	{
		float rotation = e instanceof EntityStand ? ((EntityStand) e).getRotation() : 0;
		GL11.glPushMatrix();
		if (e.isEntityAlive())
			super.doRender(e, par2, par3, par4, rotation, 0);
		GL11.glPopMatrix();
	}

	@Override
	protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		// super.rotateCorpse(par1EntityLivingBase, par2, par3, par4);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entity, float par2)
	{
		GL11.glScalef(1f, 0.95f, 1f);
		// GL11.glRotatef(entity.rotationYaw, 0, 1, 0);
		GL11.glRotatef(entity.rotationYaw + 180, 0, 1, 0);
		int l = 0;
		if (entity instanceof EntityStand)
		{
			l = ((EntityStand) entity).woodType;
		}
		standBlockRenderer.render(entity, new ItemStack(TFCBlocks.armorStand, 1, l), par2);
		doClothingRendering((EntityStand)entity,par2);
	}

	private void doClothingRendering(EntityStand es, float partialRenderTick)
	{
		ItemStack[] equipables = new ItemStack[5];
		ItemStack[] armor = new ItemStack[4];
		playerRender.modelBipedMain = this.modelBipedMain;
		playerRender.modelArmor = this.modelArmor;
		playerRender.modelArmorChestplate = this.modelArmorChestplate;
		for (int i = 0; i < 5; i++)
		{
			equipables[i] = es.getEquipableInSlot(i);
		}
		for (int i = 0; i < 4; i++)
		{
			armor[i] = es.getArmorInSlot(i);
		}
		if (equipables != null)
		{
			for (ItemStack i : equipables)
			{
				if (i != null && i.getItem() instanceof ItemQuiver)
				{
					GL11.glPushMatrix();

					float rotateAngle;

					rotateAngle = es.prevRenderYawOffset + (es.renderYawOffset - es.prevRenderYawOffset) * partialRenderTick;

					// System.out.println(rotateAngle);

					GL11.glRotatef(-rotateAngle, 0, 1, 0);
					GL11.glRotatef(180, 0, 0, 1);
					GL11.glRotatef(180, 0, 1, 0);
					GL11.glTranslatef(0, 2f / 16f, -2.5f / 16f);

					RENDER_QUIVER.render(es, i, partialRenderTick);
					GL11.glPopMatrix();
				}
				else if (i != null && i.getItem() instanceof ItemBlock)
				{
					GL11.glPushMatrix();
					/*
					 * if (el != Minecraft.getMinecraft().thePlayer) {
					 * GL11.glTranslatef(0, -0.8f, 0); }
					 */
					RENDER_LARGE.render(es, i, partialRenderTick);
					GL11.glPopMatrix();
				}
				else if (i != null && i.getItem() instanceof IEquipable)
				{
					// Minecraft.getMinecraft().renderEngine.bindTexture(BIBRONZE);
					GL11.glPushMatrix();

					RENDER_CLOTHING.render(es, i, partialRenderTick, playerRender, armor);
					GL11.glPopMatrix();
				}
			}
		}
		for (ItemStack i : armor)
		{
			if (i != null)
			{
				RENDER_CLOTHING.render(es, i, partialRenderTick, playerRender, armor);
			}
		}
	}

	protected int setArmorModelTFC(EntityStand stand, int par2, float par3)
	{
		ItemStack itemstack = stand.getArmorInSlot(3 - par2);

		if (itemstack != null)
		{
			Item item = itemstack.getItem();

			if (item instanceof ItemArmor)
			{
				ItemArmor itemarmor = (ItemArmor) item;
				this.bindTexture(RenderBiped.getArmorResource(stand, itemstack, par2, null));
				ModelBiped modelbiped = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
				modelbiped.bipedHead.showModel = par2 == 0;
				modelbiped.bipedHeadwear.showModel = par2 == 0;
				modelbiped.bipedBody.showModel = par2 == 1 || par2 == 2;
				modelbiped.bipedRightArm.showModel = par2 == 1;
				modelbiped.bipedLeftArm.showModel = par2 == 1;
				modelbiped.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
				modelbiped.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
				modelbiped = ForgeHooksClient.getArmorModel(stand, itemstack, par2, modelbiped);
				this.setRenderPassModel(modelbiped);
				modelbiped.onGround = this.mainModel.onGround;
				modelbiped.isRiding = this.mainModel.isRiding;
				modelbiped.isChild = this.mainModel.isChild;
				float f1 = 1.0F;

				// Move outside if to allow for more then just CLOTH
				int j = itemarmor.getColor(itemstack);
				if (j != -1)
				{
					float f2 = (j >> 16 & 255) / 255.0F;
					float f3 = (j >> 8 & 255) / 255.0F;
					float f4 = (j & 255) / 255.0F;
					GL11.glColor3f(f1 * f2, f1 * f3, f1 * f4);

					if (itemstack.isItemEnchanted())
					{
						return 31;
					}

					return 16;
				}

				GL11.glColor3f(f1, f1, f1);

				if (itemstack.isItemEnchanted())
				{
					return 15;
				}

				return 1;
			}
		}

		return -1;
	}

}
