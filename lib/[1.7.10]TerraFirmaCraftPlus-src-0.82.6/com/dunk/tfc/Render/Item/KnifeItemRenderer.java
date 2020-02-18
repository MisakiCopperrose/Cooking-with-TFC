package com.dunk.tfc.Render.Item;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class KnifeItemRenderer implements IItemRenderer
{

	ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return /* false;/ */type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
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
		IIcon iicon = entity.getItemIcon(item, 0);

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
		float f4 = 0.0F;
		float f5 = 0.3F;
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
		{
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			// GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			
			GL11.glTranslatef(-f4, -f5, 0.0F);
			float f6 = 1.5F;
			GL11.glScalef(f6, f6, f6);
			GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
			if (entity instanceof EntityPlayer)
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer((EntityPlayer) entity);
				if (pi != null && pi.knifeMode == 1 && ((EntityPlayer)entity).getItemInUseCount()==0)
				{
					GL11.glTranslatef(0F, 0F, 0.0F);
					//GL11.glRotatef(-45, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(-22.5f, 0, -1, 1);
					//GL11.glTranslatef(0F, -1F, 0F);
				}
				else
				{
					GL11.glTranslatef(0F, 0.5F, -0.25F);
					GL11.glRotatef(45f, 0, 0, 1);
					GL11.glRotatef(0, 0, 1, 0);
					//GL11.glRotatef(20, 1, 0, 0);
				}
			}
			GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
		}
		else
		{
			if (entity instanceof EntityPlayer)
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer((EntityPlayer) entity);
				if (pi != null && pi.knifeMode == 1  && ((EntityPlayer)entity).getItemInUseCount()==0)
				{
					GL11.glScalef(2, 2, 2);
					GL11.glRotatef(150.0F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(-0.4f, 0.4f, -0.1f);
					GL11.glRotatef(170.0F, 0.0F, 0.0F, 1.0F);
				}
				else
				{
					GL11.glScalef(2, 2, 2);
					GL11.glRotatef(150.0F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(-0.75f, 0f, -0.1f);
					GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
				}
			}
			else
			{
				GL11.glScalef(2, 2, 2);
				GL11.glRotatef(150.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.75f, 0f, -0.1f);
				GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			}
		}
		ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);

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
