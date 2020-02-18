package com.dunk.tfc.Render.Item;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.dunk.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.dunk.tfc.Items.Tools.ItemCustomFishingRod;
import com.dunk.tfc.Items.Tools.ItemJavelin;
import com.dunk.tfc.Items.Tools.ItemStaff;
import com.dunk.tfc.api.TFCItems;

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
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class CompositeBowItemRenderer implements IItemRenderer
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
		
		float f20 = 2.5F;

		GL11.glRotatef(-40, 0, 1, 0);
		GL11.glRotatef(-80, 0,0, 1);
		GL11.glTranslatef(-1.8f,2.5f,1);
		GL11.glRotatef(10, 1,1,1);
        GL11.glScalef(f20, -f20, f20);
        
		EntityLivingBase entity = (EntityLivingBase) data[1];
		EntityPlayer player = null;
		if (entity instanceof EntityPlayer)
		{
			player = ((EntityPlayer) entity);
		}
		boolean badStick = false;
		IIcon iicon = entity.getItemIcon(item, 0);
		IIcon iicon2 = iicon;

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
