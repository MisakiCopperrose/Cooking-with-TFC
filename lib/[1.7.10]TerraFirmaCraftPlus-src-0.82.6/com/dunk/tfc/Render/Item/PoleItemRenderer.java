package com.dunk.tfc.Render.Item;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.dunk.tfc.Items.Tools.ItemCustomFishingRod;
import com.dunk.tfc.Items.Tools.ItemJavelin;
import com.dunk.tfc.Items.Tools.ItemStaff;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class PoleItemRenderer implements IItemRenderer
{

	ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type == ItemRenderType.EQUIPPED;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		EntityLivingBase entity = (EntityLivingBase) data[1];
		EntityPlayer player = null;
		if (entity instanceof EntityPlayer)
		{
			player = ((EntityPlayer) entity);
		}
		boolean badStick = false;
		if(player != null && player.getHeldItem().getItem() instanceof ItemCustomFishingRod && item.getItem() == TFCItems.stick)
		{
			item = player.getHeldItem();
		}
		else if(item.getItem() == TFCItems.stick)
		{
			badStick = true;
		}
		IIcon iicon = entity.getItemIcon(item, 0);
		IIcon iicon2 = entity.getItemIcon(new ItemStack(TFCItems.pole), 0);

		if (iicon == null)
		{
			GL11.glPopMatrix();
			return;
		}
		GL11.glPushMatrix();
		// GL11.glRotatef(45, 1,0, 0);
		Minecraft.getMinecraft().getTextureManager().bindTexture(
				Minecraft.getMinecraft().getTextureManager().getResourceLocation(item.getItemSpriteNumber()));
		TextureUtil.func_152777_a(false, false, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		
		float f = iicon.getMinU();
		float f1 = iicon.getMaxU();
		float f2 = iicon.getMinV();
		float f3 = iicon.getMaxV();
		
		float af = iicon2.getMinU();
		float af1 = iicon2.getMaxU();
		float af2 = iicon2.getMinV();
		float af3 = iicon2.getMaxV();
		
		float f4 = 0.0F;
		float f5 = 0.3F;

		if(badStick)
		{
			//GL11.glScalef(1.5f, 1.5f, 1.5f);
			//ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
			//return;
		}
		
		if (entity instanceof EntityPlayer && !badStick)
		{
			
			GL11.glTranslatef(0.0F, 0.3F, 0.0F);
			// GL11.glScalef(1/1.5F, 1/1.5F, 1/1.5F);
			GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
			
			GL11.glTranslatef(0.9375F, 0.0625F, 0.0F);
			if(item.getItem() instanceof ItemCustomFishingRod && ((item.hasTagCompound() && item.stackTagCompound.hasKey("fishing") && !item.stackTagCompound.getBoolean("fishing"))))
			{
				GL11.glRotatef(Math.max(-player.getItemInUseDuration(),-50), 0, 0, 1);
			}
			GL11.glScalef(4F, 4F, 4F);
			GL11.glTranslatef(-0.4F, -0.6F, 0.0F);

			if (item.getItem() instanceof ItemJavelin)
			{
				GL11.glTranslatef(-0.05f, 0.2f, 0);
				if (player.motionX == 0 && player.motionZ == 0 && !((EntityPlayer) entity).isUsingItem() && ((EntityPlayer) entity).swingProgress == 0)
				{
					GL11.glTranslatef(0.15F, 0.7F, 0.0F);
					GL11.glRotatef(-70.0F, 0.0F, 0.0F, 1.0F);
				}
				else if (!((EntityPlayer) entity).isUsingItem()  && ((EntityPlayer) entity).swingProgress == 0)
				{
					GL11.glTranslatef(0.1F, 0F, 0.0F);
				}
				else if ((((EntityPlayer) entity).swingProgress != 0 || ((EntityPlayer) entity).prevSwingProgress != 0))
				{
					float partial = (((System.currentTimeMillis() % 1000) * 0.06f) % 3) * 0.333f;
					float p = ((EntityPlayer) entity).getSwingProgress(partial);
					if (p > 0.625f)
					{
						p = 1.25f - p;
					}
					GL11.glRotatef(10.0F + (p) * 60f, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-10 + (p) * 60f, -1.0F, -1.0F, 0.0F);
					GL11.glTranslatef(-0.2f, 0.2f, 0);
				}
				GL11.glRotatef(80.0F, 0.0F, 0.0F, 1.0F);

				if (((EntityPlayer) entity).isUsingItem())
				{
					GL11.glRotatef(75.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(-0.5f, -1f, 0f);
				}
				else
				{
					GL11.glTranslatef(0F, -1F, 0.0F);
				}
				GL11.glScalef(0.75F, 1.5F, 0.75F);
			}
			else if (item.getItem() instanceof ItemStaff)
			{
				if (((EntityPlayer) entity).isBlocking())
				{
					GL11.glTranslatef(0.0f, 0.0f, 0.3F);
					GL11.glRotatef(80.0F, 1.0F, 1.0F, 0.0F);
				}
				else if ((((EntityPlayer) entity).swingProgress != 0 || ((EntityPlayer) entity).prevSwingProgress != 0) && item
						.getItem() instanceof ItemStaff)
				{
					float partial = (((System.currentTimeMillis() % 1000) * 0.06f) % 3) * 0.333f;
					float p = ((EntityPlayer) entity).getSwingProgress(partial);
					if (p > 0.625f)
					{
						p = 1.25f - p;
					}
					GL11.glRotatef(10.0F + (p) * 60f, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-10 + (p) * 60f, -1.0F, -1.0F, 0.0F);
					GL11.glTranslatef(-0.2f, 0.2f, 0);
				}
				else if (!((EntityPlayer) entity).isSprinting() && item.getItem() instanceof ItemStaff)
				{
					GL11.glTranslatef(-0.2F, 0.75F, 0.0F);
					GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);

				}
			}
			else if(item.getItem() instanceof ItemCustomFishingRod)
			{
				GL11.glTranslatef(-0.4f, 0.35f, 0F);
				
			}
		}
		else
		{
			if(entity instanceof EntitySkeletonTFC && Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.HARD)
			{
				GL11.glRotatef(-90, 0, 1, 0);
				GL11.glTranslatef(0.2F, 0.8F, -0.7F);
				GL11.glRotatef(-10, -1, 0, 1);
				GL11.glScalef(0.25f, 0.35f, 0.25f);

			}
			GL11.glTranslatef(0.5F, 1.7F, 0.8F);
			// GL11.glScalef(1/1.5F, 1/1.5F, 1/1.5F);
			GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-140.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.9375F, 0.0625F, 0.0F);
			GL11.glRotatef(180, 0, 0, 1);
			if(!badStick)
				{
				GL11.glScalef(4F, 4F, 4F);
				GL11.glTranslatef(-0.4F, -0.6F, 0.0F);
				}
			else
			{
				GL11.glScalef(2.5F,2.5F, 2.5F);
				GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
			}
			//GL11.glTranslatef(-0.4F, -0.6F, 0.0F);
		}

		ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
		if(entity instanceof EntitySkeletonTFC && Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.HARD)
		{
			GL11.glTranslatef(-0.875f, -0.875f, 0.005f);
			GL11.glScalef(1, 1, 0.99f);
			ItemRenderer.renderItemIn2D(tessellator, af1, af2, af, af3, iicon2.getIconWidth(), iicon2.getIconHeight(), 0.0625F);
		}
		if (item.hasEffect(0))
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			Minecraft.getMinecraft().getTextureManager().bindTexture(RES_ITEM_GLINT);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(768, 1, 1, 0);
			float f7 = 0.76F;
			GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float f8 = 0.125F;
			GL11.glScalef(f8, f8, f8);
			float f9 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
			GL11.glTranslatef(f9, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(f8, f8, f8);
			f9 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
			GL11.glTranslatef(-f9, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		Minecraft.getMinecraft().getTextureManager().bindTexture(
				Minecraft.getMinecraft().getTextureManager().getResourceLocation(item.getItemSpriteNumber()));
		TextureUtil.func_147945_b();
		GL11.glPopMatrix();
	}

}
