package com.dunk.tfc.Render;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.dunk.tfc.Handlers.Client.PlayerRenderHandler;
import com.dunk.tfc.Items.ItemQuiver;
import com.dunk.tfc.Items.Tools.ItemJavelin;
import com.dunk.tfc.Render.Models.ModelSkeletonTFC;
import com.dunk.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSkeletonTFC extends RenderBiped
{
	private static final ResourceLocation SKELETON_TEXTURE = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation WITHER_TEXTURE = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
	public static final RenderQuiver QUIVER_RENDER = new RenderQuiver();
	public static ItemStack quiver = new ItemStack(TFCItems.quiver, 1, 1);
	public static ItemStack ammo = ((ItemQuiver) TFCItems.quiver).addItem(quiver, new ItemStack(TFCItems.arrow, 16, 0));

	public RenderSkeletonTFC()
	{
		super(new ModelSkeletonTFC(), 0.5F);
	}

	protected void scaleRender(EntitySkeletonTFC par1EntitySkeleton, float par2)
	{
		if (par1EntitySkeleton.getSkeletonType() == 1)
			GL11.glScalef(1.2F, 1.2F, 1.2F);
	}

	@Override
	protected void func_82422_c()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}
	
	@Override
	 public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
		PlayerRenderHandler.renderForSomeEntity(p_76986_1_, p_76986_9_, this);
    }

	protected ResourceLocation getTextureLocation(EntitySkeletonTFC par1EntitySkeleton)
	{
		return par1EntitySkeleton.getSkeletonType() == 1 ? WITHER_TEXTURE : SKELETON_TEXTURE;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
	{
		return this.getTextureLocation((EntitySkeletonTFC) par1EntityLiving);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase entity, float par2)
	{
		ItemStack itemstack = entity.getHeldItem();

		if (itemstack != null && (itemstack.getItem() == TFCItems.bow || itemstack.getItem() instanceof ItemJavelin))
		{
			QUIVER_RENDER.render(entity, quiver);
		}
		this.scaleRender((EntitySkeletonTFC) entity, par2);
		//PlayerRenderHandler.renderForSomeEntity(entity, par2, this);

		// System.out.println(eq);
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getTextureLocation((EntitySkeletonTFC) par1Entity);
	}
}
