package com.dunk.tfc.Handlers.Client;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.Entities.IAnimal;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent.Specials.Post;

public class FamiliarityHighlightHandler {

	//Assumed client only
	@SubscribeEvent
	public void renderLivingEvent(Post evt){
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		if(RenderManager.instance.livingPlayer instanceof EntityPlayer){
			EntityLivingBase entity = evt.entity;
			EntityPlayer player = (EntityPlayer)RenderManager.instance.livingPlayer;
			double x,y,z;
			x = evt.x;
			y = evt.y;
			z = evt.z;
			if(entity instanceof IAnimal && entity == Minecraft.getMinecraft().pointedEntity)
			{	
				IAnimal animal = (IAnimal) entity;
				boolean familiarized = animal.getFamiliarizedToday();
				float f = 1.6F * 3;
				float f1 = 0.016666668F * f;
				double d3 = entity.getDistanceSqToEntity(RenderManager.instance.livingPlayer);
				float f2 = 8;
				
				if (d3 < f2 * f2)
				{

					if (player.isSneaking())
					{
						GL11.glPushMatrix();
						GL11.glTranslatef((float)x + 0.0F, (float)y + (entity.height )+ 1.25F, (float)z);
						GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
						GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
						GL11.glScalef(-f1, -f1, f1);
						GL11.glDisable(GL11.GL_LIGHTING);
						GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						TFC_Core.bindTexture(RenderOverlayHandler.tfcicons);
						float maxFam = 100f;
						float percentFam = Math.min(animal.getFamiliarity() / maxFam, 1.0f);
						GL11.glScalef(0.33F, 0.33F, 0.33F);
						int xOffset = familiarized?36:0;
						if(percentFam >= 0.3){
							this.drawTexturedModalRect(-8, 0, 112+xOffset, 40, 16, 16);
						}
						else
						{
							this.drawTexturedModalRect(-8, 0, 92+xOffset, 40, 16, 16);
						}
						int numCuts, numBlood;
						numCuts = animal.getNumberCuts();
						numBlood = animal.getNumberBleeding();
						
						GL11.glTranslatef(0, 0, -0.001F);
						if (percentFam == 1)
						{
							this.drawTexturedModalRect(-6, 14 - (int) (12 * percentFam), 114, 74 - (int) (12 * percentFam), 12, (int) (12 * percentFam));
						}
						else
						{
							this.drawTexturedModalRect(-6, 14 - (int) (12 * percentFam), 94, 74 - (int) (12 * percentFam), 12, (int) (12 * percentFam));
						}
						if(numCuts > 0)
						{
							GL11.glTranslatef(16, 0, 0F);
							this.drawTexturedModalRect(-8, 0, 92,80, 16, 16);
							//Minecraft.getMinecraft().fontRenderer.drawString("" +numCuts, 0, 0, Color.WHITE.getRGB());
							GL11.glTranslatef(-16, 0, 0F);
						}
						if(numBlood > 0)
						{
							GL11.glTranslatef(-16, 0, 0F);
							this.drawTexturedModalRect(-8, 0, 108,80, 16, 16);
							Minecraft.getMinecraft().fontRenderer.drawString("" +numBlood, 0, 0, Color.WHITE.getRGB());
							GL11.glTranslatef(16, 0, 0F);
						}
						if(numCuts > 0)
						{
							GL11.glTranslatef(16, 0, 0F);
							//this.drawTexturedModalRect(-8, 0, 92,80, 16, 16);
							Minecraft.getMinecraft().fontRenderer.drawString("" +numCuts, 0, 0, Color.WHITE.getRGB());
							GL11.glTranslatef(-16, 0, 0F);
						}
						GL11.glDepthMask(true);
						GL11.glEnable(GL11.GL_LIGHTING);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glPopMatrix();
					}
				}
			}
		}
	}

	public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(par1 + 0, par2 + par6, 0.0, (par3 + 0) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + par6, 0.0, (par3 + par5) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + 0, 0.0, (par3 + par5) * f, (par4 + 0) * f1);
		tessellator.addVertexWithUV(par1 + 0, par2 + 0, 0.0, (par3 + 0) * f, (par4 + 0) * f1);
		tessellator.draw();
	}
}
