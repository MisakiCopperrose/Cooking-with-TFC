package com.dunk.tfc.Handlers.Client;

import java.nio.FloatBuffer;
import java.util.UUID;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.Devices.BlockTeepee;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.InventoryPlayerTFC;
import com.dunk.tfc.Core.Player.PlayerInfo;
import com.dunk.tfc.Core.Player.PlayerManagerTFC;
import com.dunk.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.dunk.tfc.Items.ItemClothing;
import com.dunk.tfc.Items.ItemHat;
import com.dunk.tfc.Items.ItemQuiver;
import com.dunk.tfc.Render.RenderClothing;
import com.dunk.tfc.Render.RenderLargeItem;
import com.dunk.tfc.Render.RenderQuiver;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCOptions;
import com.dunk.tfc.api.Interfaces.IEquipable;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class PlayerRenderHandler
{

	public static ItemStack toRender;

	private boolean fixedArmor = false;

	public static final RenderQuiver RENDER_QUIVER = new RenderQuiver();
	public static final RenderLargeItem RENDER_LARGE = new RenderLargeItem();
	public static final RenderClothing RENDER_CLOTHING = new RenderClothing();

	private static final ResourceLocation WOOL_SOCKS = new ResourceLocation(Reference.MOD_ID, "textures/models/armor/wool_socks.png");

	private static final ResourceLocation BIBRONZE = new ResourceLocation(Reference.MOD_ID, "textures/models/armor/bismuthbronze_2.png");

	public PlayerRenderHandler()
	{
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	// @SideOnly(Side.CLIENT)
	public void onPlayerRenderTick(RenderPlayerEvent.Pre e)
	{
		// Make the armor pants larger
		// e.renderer.modelArmor = new ModelBiped(0.75F);
		// If the player is wearing something on it's feet
		if (!fixedArmor)
		{
			e.renderer.modelArmor = new ModelBiped(0.75f);
			fixedArmor = true;
		}
		if (e.entityPlayer.getCurrentArmor(0) != null && !e.entityPlayer.isSneaking())
		{
			GL11.glTranslatef(0, 0.5f / 16f, 0f);
		}
		if (e.entityPlayer.isPlayerSleeping())
		{
			Block blo = e.entityPlayer.worldObj.getBlock((int) e.entityPlayer.posX, (int) e.entityPlayer.posY, (int) e.entityPlayer.posZ - 1);
			if (blo instanceof BlockTeepee)
			{
				GL11.glTranslatef(0, -9f / 16f, 0f);
			}
		}
		else
		{

		}

		/*
		 * e.renderer.modelArmorChestplate.bipedHead = new
		 * ModelRenderer(e.renderer.modelArmorChestplate, 0, 0);
		 * e.renderer.modelArmorChestplate.bipedHead.addBox(-4.0F, -8.0F, -4.0F,
		 * 8, 8, 8, 1.5f);
		 * e.renderer.modelArmorChestplate.bipedHead.setRotationPoint(0.0F, 0.0F
		 * , 0.0F);
		 */

		/*
		 * UUID uuid = e.entityPlayer.getPersistentID(); EntityPlayer el =
		 * e.entityPlayer; PlayerInfo f =
		 * PlayerManagerTFC.getInstance().getPlayerInfoFromName(el.
		 * getDisplayName()); if (f != null && f.chiselMode != 0) { // This
		 * seems to work!!!! System.out.println(f.chiselMode); }
		 * 
		 * // if(((EntityPlayer)el).inventory instanceof InventoryPlayerTFC){ //
		 * System.out.println("The inventory was found"); ItemStack[] equipables
		 * = el == Minecraft.getMinecraft().thePlayer ? ((InventoryPlayerTFC)
		 * ((EntityPlayer) el).inventory).extraEquipInventory :
		 * 
		 * f != null ? f.myExtraItems : null
		 * 
		 * ;// ((InventoryPlayerTFC)((EntityPlayer)el).inventory).
		 * extraEquipInventory; for (ItemStack i : equipables) { if (i != null
		 * && i.getItem() instanceof ItemQuiver) {
		 * RENDER_QUIVER.render(e.entityPlayer, i); } else if (i != null &&
		 * i.getItem() instanceof ItemBlock) {
		 * RENDER_LARGE.render(e.entityPlayer, i, e.partialRenderTick); } else
		 * if (i != null && i.getItem() instanceof IEquipable) { //
		 * Minecraft.getMinecraft().renderEngine.bindTexture(BIBRONZE);
		 * RENDER_CLOTHING.render(e.entityPlayer,
		 * i,e.partialRenderTick,e.renderer); } } for(ItemStack i :
		 * e.entityPlayer.inventory.armorInventory) { if(i != null) {
		 * RENDER_CLOTHING.render(e.entityPlayer,
		 * i,e.partialRenderTick,e.renderer); } } // }
		 */
	}

	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);

	/**
	 * Update and return colorBuffer with the RGBA values passed as arguments
	 */
	private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_)
	{
		/**
		 * Update and return colorBuffer with the RGBA values passed as
		 * arguments
		 */
		return setColorBuffer((float) p_74517_0_, (float) p_74517_2_, (float) p_74517_4_, (float) p_74517_6_);
	}

	/**
	 * Update and return colorBuffer with the RGBA values passed as arguments
	 */
	private static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_)
	{
		colorBuffer.clear();
		colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
		colorBuffer.flip();
		/** Float buffer used to set OpenGL material colors */
		return colorBuffer;
	}

	@SubscribeEvent()
	public void onPlayerRenderTickPost(RenderPlayerEvent.Post e)
	{
		UUID uuid = e.entityPlayer.getPersistentID();
		EntityPlayer el = e.entityPlayer;

		PlayerInfo f = PlayerManagerTFC.getInstance().getPlayerInfoFromName(el.getDisplayName());
		// PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
		if (f != null && f.chiselMode != 0)
		{
			// This seems to work!!!!
			// System.out.println(f.chiselMode);
		}
		if (e.entityPlayer.getCurrentArmor(0) != null && !e.entityPlayer.isSneaking())
		{
			GL11.glTranslatef(0, 0.5f / 16f, 0f);
		}
		// if(((EntityPlayer)el).inventory instanceof InventoryPlayerTFC){
		// System.out.println("The inventory was found");
		ItemStack[] equipables = el == Minecraft.getMinecraft().thePlayer ? f.myExtraItems// ((InventoryPlayerTFC)
																							// ((EntityPlayer)
																							// el).inventory).extraEquipInventory
		: f != null ? f.myExtraItems : null;// ((InventoryPlayerTFC)((EntityPlayer)el).inventory).extraEquipInventory;*/
		if (RenderManager.instance.playerViewY == 180)
		{

			// GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			// RenderHelper.enableStandardItemLighting();
			float f0 = 0.4F;
			float f1 = 0.6F;
			float f2 = 0.0F;
			// GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT,
			// setColorBuffer(0.4f, 0.4f, 0.4f, 1.0F));
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			// GL11.glDisable(GL11.GL_DEPTH_TEST);
		}
		if (equipables != null)
		{
			for (ItemStack i : equipables)
			{
				if (i != null && i.getItem() instanceof ItemQuiver)
				{
					if (e.entityPlayer.isPlayerSleeping())
					{
						continue;
					}
					GL11.glPushMatrix();
					EntityPlayer entity = e.entityPlayer;
					float rotateAngle;
					if (entity.ridingEntity != null && entity.ridingEntity instanceof EntityLiving)
					{
						rotateAngle = ((EntityLiving) entity.ridingEntity).prevRenderYawOffset
						+ (((EntityLiving) entity.ridingEntity).renderYawOffset - ((EntityLiving) entity.ridingEntity).prevRenderYawOffset) * e.partialRenderTick;
					}
					else
					{
						rotateAngle = entity.prevRenderYawOffset + (entity.renderYawOffset - entity.prevRenderYawOffset) * e.partialRenderTick;
					}

					// System.out.println(rotateAngle);

					GL11.glRotatef(-rotateAngle, 0, 1, 0);
					GL11.glRotatef(180, 0, 0, 1);
					GL11.glRotatef(180, 0, 1, 0);
					GL11.glTranslatef(0, 2f / 16f, -2.5f / 16f);

					RENDER_QUIVER.render(e.entityPlayer, i, e.partialRenderTick);
					GL11.glPopMatrix();
				}
				else if (i != null && i.getItem() instanceof ItemBlock)
				{
					if (e.entityPlayer.isPlayerSleeping())
					{
						continue;
					}
					GL11.glPushMatrix();
					if (el != Minecraft.getMinecraft().thePlayer)
					{
						GL11.glTranslatef(0, -0.8f, 0);
					}
					RENDER_LARGE.render(e.entityPlayer, i, e.partialRenderTick);
					GL11.glPopMatrix();
				}
				else if (i != null && i.getItem() instanceof IEquipable)
				{
					// Minecraft.getMinecraft().renderEngine.bindTexture(BIBRONZE);
					GL11.glPushMatrix();
					RENDER_CLOTHING.render(e.entityPlayer, i, e.partialRenderTick, e.renderer, e.entityPlayer.inventory.armorInventory);
					GL11.glPopMatrix();
				}
			}
		}
		if (TFCOptions.enableDebugInventoryRendering)
		{
			if (e.entityPlayer.getHeldItem() != null && e.entityPlayer.getHeldItem().getItem() instanceof ItemBlock)
			{
				// This is used to get images of item renders for the wiki
				GL11.glPushMatrix();

				GL11.glColor3f(1f, 1f, 1f);
				GL11.glTranslatef(4, 0f, 0);
				// GL11.glRotatef(90, 0, 1, 0);
				GL11.glScalef(1.8f, 1.8f, 1.8f);
				if (el != Minecraft.getMinecraft().thePlayer)
				{
					GL11.glTranslatef(0, -0.8f, 0);

				}
				RENDER_LARGE.render(e.entityPlayer, e.entityPlayer.getHeldItem(), e.partialRenderTick);
				GL11.glPopMatrix();
			}
		}
		for (ItemStack i : e.entityPlayer.inventory.armorInventory)
		{
			if (i != null)
			{
				RENDER_CLOTHING.render(e.entityPlayer, i, e.partialRenderTick, e.renderer, e.entityPlayer.inventory.armorInventory);
			}
		}

		// }
		// Reset to null after rendering the current tail
		toRender = null;
		if (e.entityPlayer.getCurrentArmor(0) != null && !e.entityPlayer.isSneaking())
		{
			GL11.glTranslatef(0, -1f / 16f, 0f);
		}
		if (e.entityPlayer.isPlayerSleeping())
		{
			Block blo = e.entityPlayer.worldObj.getBlock((int) e.entityPlayer.posX, (int) e.entityPlayer.posY, (int) e.entityPlayer.posZ - 1);
			if (blo instanceof BlockTeepee)
			{
				GL11.glTranslatef(0, 9f / 16f, 0f);
			}
		}
		if (RenderManager.instance.playerViewY == 180)
		{
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		}
	}

	public static void renderForSomeEntity(EntityLivingBase e, float partialRender, RenderBiped rb)
	{
		// Do some shit
		// Coat
		for (int i = 1; i <= 4; i++)
		{
			ItemStack clothing = e.getEquipmentInSlot(i);
			if (clothing != null && clothing.getItem() instanceof ItemClothing)
			{
				// Minecraft.getMinecraft().renderEngine.bindTexture(BIBRONZE);
				GL11.glPushMatrix();
				if(clothing.getItem() instanceof ItemHat)
				{
					//GL11.glRotatef(e.renderYawOffset-e.rotationYaw, 0, 1, 0);
					//System.out.println(e.rotationYaw +", " +e.rotationYawHead  +", " + e.renderYawOffset );
				}
				RENDER_CLOTHING.render(e, clothing, partialRender, rb, new ItemStack[4]);
				GL11.glPopMatrix();
			}
		}
	}

	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e)
	{

	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTick(TickEvent.PlayerTickEvent e)
	{
		/*
		 * if (e.side.isClient()) {
		 * 
		 * }
		 */
	}
}
