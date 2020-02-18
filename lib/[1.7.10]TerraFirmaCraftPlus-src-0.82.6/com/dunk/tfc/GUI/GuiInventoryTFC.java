package com.dunk.tfc.GUI;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.Core.Player.BodyTempStats;
import com.dunk.tfc.Core.Player.PlayerInventory;
import com.dunk.tfc.Food.ItemMeal;
import com.dunk.tfc.Food.TFCPotion;
import com.dunk.tfc.api.Food;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Interfaces.IFood;
import com.dunk.tfc.api.Tools.IKnife;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

public class GuiInventoryTFC extends InventoryEffectRenderer
{
	private float xSizeLow;
	private float ySizeLow;
	private boolean hasEffect;
	protected static final ResourceLocation UPPER_TEXTURE = new ResourceLocation(
			Reference.MOD_ID + ":textures/gui/inventory_expanded_beta.png");
	protected static final ResourceLocation UPPER_TEXTURE_2X2 = new ResourceLocation(
			Reference.MOD_ID + ":textures/gui/gui_inventory2x2_expanded_beta.png");
	protected static final ResourceLocation EFFECTS_TEXTURE = new ResourceLocation(
		Reference.MOD_ID + ":textures/gui/inv_effects.png");
	protected static final ResourceLocation ICONS_TEXTURE = new ResourceLocation(
	Reference.MOD_ID + ":textures/gui/icons.png");
	protected EntityPlayer player;
	protected Slot activeSlot;
	private int selectedTabIndex = 11;

	public GuiInventoryTFC(EntityPlayer player)
	{
		super(player.inventoryContainer);
		this.allowUserInput = true;
		player.addStat(AchievementList.openInventory, 1);
		//xSize = 176;
		xSize = 230;
		ySize = 85 + PlayerInventory.invYSize;
		this.player = player;
	}

	@Override
	/**
	 * Called when the mouse is clicked.
	 */
	protected void mouseClicked(int x, int y, int b)
	{
		// Stupid code so that we never accidentally click off the screen when
		// we're touching the back slot because dumb.
		if (isMouseOverSlot((Slot) this.inventorySlots.inventorySlots.get(50), x, y))
		{
			// Shift the x so that the click is just inside the inventory but
			// still on the slot.
			// the y should already be fine.
			//x = this.guiLeft + 1;
		}
		super.mouseClicked(x, y, b);
		// this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
	}

	@Override
	protected void handleMouseClick(Slot par1Slot, int par2, int par3, int par4)
	{
		if (par1Slot != null)
		{
			par2 = par1Slot.slotNumber;
		}
		// System.out.println(par2 + ", " + par3 + ", " + par4);
		this.mc.playerController.windowClick(this.inventorySlots.windowId, par2, par3, par4, this.mc.thePlayer);
		// super.handleMouseClick(par1Slot, par2, par3, par4);
		/*
		 * boolean flag = par4 == 1; par4 = par2 == -999 && par4 == 0 ? 4 :
		 * par4; ItemStack itemstack; InventoryPlayer inventoryplayer;
		 * 
		 * if (par1Slot == null && selectedTabIndex !=
		 * CreativeTabs.tabInventory.getTabIndex() && par4 != 5) {
		 * inventoryplayer = this.mc.thePlayer.inventory;
		 * 
		 * if (inventoryplayer.getItemStack() != null) { if (par3 == 0) {
		 * this.mc.thePlayer.entityDropItem(inventoryplayer.getItemStack(),
		 * 1.5F); //
		 * this.mc.playerController.windowClick(this.inventorySlots.windowId, //
		 * par2, par3, par4, this.mc.thePlayer);
		 * inventoryplayer.setItemStack((ItemStack) null); }
		 * 
		 * if (par3 == 1) { itemstack =
		 * inventoryplayer.getItemStack().splitStack(1);
		 * this.mc.thePlayer.entityDropItem(itemstack, 1.5F); //
		 * this.mc.playerController.windowClick(this.inventorySlots.windowId, //
		 * par2, par3, par4, this.mc.thePlayer);
		 * 
		 * if (inventoryplayer.getItemStack().stackSize == 0)
		 * inventoryplayer.setItemStack((ItemStack) null); } } } else { int l;
		 * ItemStack itemstack1;
		 * 
		 * if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) { if
		 * (par4 == 4 && par1Slot != null && par1Slot.getHasStack()) {
		 * itemstack1 = par1Slot.decrStackSize(par3 == 0 ? 1 :
		 * par1Slot.getStack().getMaxStackSize());
		 * //this.mc.thePlayer.entityDropItem(itemstack1, 1.5F); //
		 * this.mc.playerController.sendPacketDropItem(itemstack1); } else if
		 * (par4 == 4 && this.mc.thePlayer.inventory.getItemStack() != null) {
		 * if (par1Slot != null) { par2 = par1Slot.slotNumber; } if (par1Slot !=
		 * null) { par2 = par1Slot.slotNumber; } if (par2 >= 50 && par2 < 55) {
		 * this.inventorySlots.slotClick(par1Slot == null ? par2 :
		 * par1Slot.slotNumber, par3, par4, this.mc.thePlayer);
		 * this.mc.playerController.windowClick(this.inventorySlots.windowId,
		 * par2, par3, par4, this.mc.thePlayer); } else {
		 * this.mc.playerController.windowClick(this.inventorySlots.windowId,
		 * par2, par3, par4, this.mc.thePlayer); } //
		 * this.mc.thePlayer.entityDropItem(this.mc.thePlayer.inventory.
		 * getItemStack(), // 1.5F); // this.mc.playerController //
		 * .sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack()); //
		 * this.mc.thePlayer.inventory.setItemStack((ItemStack) // null); } else
		 * { if (par1Slot != null) { par2 = par1Slot.slotNumber; } if (par2 >=
		 * 50 && par2 < 55) { boolean b = player.inventory.getItemStack() ==
		 * null; this.inventorySlots.slotClick(par1Slot == null ? par2 :
		 * par1Slot.slotNumber, par3, par4, this.mc.thePlayer);
		 * this.mc.playerController.windowClick(this.inventorySlots.windowId,
		 * par2, par3, par4, this.mc.thePlayer); } else {
		 * this.mc.playerController.windowClick(this.inventorySlots.windowId,
		 * par2, par3, par4, this.mc.thePlayer); } //
		 * this.mc.thePlayer.inventoryContainer // .slotClick( // par1Slot ==
		 * null ? par2 // : par1Slot.slotNumber, // par3, par4,
		 * this.mc.thePlayer); //
		 * this.mc.thePlayer.inventoryContainer.detectAndSendChanges(); } } else
		 * { this.inventorySlots.slotClick(par1Slot == null ? par2 :
		 * par1Slot.slotNumber, par3, par4, this.mc.thePlayer);
		 * 
		 * if (Container.func_94532_c(par3) == 2) { for (l = 0; l < 9; ++l)
		 * this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(
		 * 45 + l).getStack(), 36 + l); } else if (par1Slot != null) {
		 * itemstack1 =
		 * this.inventorySlots.getSlot(par1Slot.slotNumber).getStack();
		 * this.mc.playerController.sendSlotPacket(itemstack1,
		 * par1Slot.slotNumber - this.inventorySlots.inventorySlots.size() + 9 +
		 * 36); } }
		 * 
		 * }
		 */
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (player.getEntityData().hasKey("craftingTable"))
			TFC_Core.bindTexture(UPPER_TEXTURE);
		else
			TFC_Core.bindTexture(UPPER_TEXTURE_2X2);
		int k = this.guiLeft;
		int l = this.guiTop;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 86);
		// Draw the player avatar
		drawPlayerModel(k + 51 + 54, l + 75, 30, k + 51 - this.xSizeLow, l + 75 - 50 - this.ySizeLow, this.mc.thePlayer);

		PlayerInventory.drawInventory(this, width, height, ySize - PlayerInventory.invYSize);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void drawTexturedModelRectFromIcon(int i, int j, IIcon icon, int w, int h)
	{
		
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(i + 0, j + h, this.zLevel, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(i + w, j + h, this.zLevel, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(i + w, j + 0, this.zLevel, icon.getMaxU(), icon.getMinV());
		tessellator.addVertexWithUV(i + 0, j + 0, this.zLevel, icon.getMinU(), icon.getMinV());
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawPlayerModel(int par0, int par1, int par2, float par3, float par4,
			EntityLivingBase par5EntityLivingBase)
	{
		
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(par0, par1, 25.0F);
		GL11.glScalef(-par2, par2, par2);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = par5EntityLivingBase.renderYawOffset;
		float f3 = par5EntityLivingBase.rotationYaw;
		float f4 = par5EntityLivingBase.rotationPitch;
		float f5 = par5EntityLivingBase.prevRotationYawHead;
		float f6 = par5EntityLivingBase.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan(par4 / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		par5EntityLivingBase.renderYawOffset = (float) Math.atan(par3 / 40.0F) * 20.0F;
		par5EntityLivingBase.rotationYaw = (float) Math.atan(par3 / 40.0F) * 40.0F;
		par5EntityLivingBase.rotationPitch = -((float) Math.atan(par4 / 40.0F)) * 20.0F;
		par5EntityLivingBase.rotationYawHead = par5EntityLivingBase.rotationYaw;
		par5EntityLivingBase.prevRotationYawHead = par5EntityLivingBase.rotationYaw;
		GL11.glTranslatef(0.0F, par5EntityLivingBase.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(par5EntityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		par5EntityLivingBase.renderYawOffset = f2;
		par5EntityLivingBase.rotationYaw = f3;
		par5EntityLivingBase.rotationPitch = f4;
		par5EntityLivingBase.prevRotationYawHead = f5;
		par5EntityLivingBase.rotationYawHead = f6;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		// this.fontRendererObj.drawString(I18n.format("container.crafting", new
		// Object[0]), 86, 7, 4210752);
	}

	@Override
	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		if (this.mc.playerController.isInCreativeMode())
			this.mc.displayGuiScreen(new GuiContainerCreativeTFC(player));
	}

	@Override
	public void initGui()
	{
		
		super.buttonList.clear();

		if (this.mc.playerController.isInCreativeMode())
			this.mc.displayGuiScreen(new GuiContainerCreativeTFC(this.mc.thePlayer));
		else
			super.initGui();

		BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);
		boolean hasHeatProt = bodyTemp.temporaryHeatProtection > 0;
		boolean hasColdProt = bodyTemp.temporaryColdProtection > 0;
		int slashWound = TFC_Core.getPlayerFoodStats(player).slashWoundTimer;
		int pierceWound = TFC_Core.getPlayerFoodStats(player).pierceWoundTimer;
		int crushWound = TFC_Core.getPlayerFoodStats(player).crushWoundTimer;
		long drunkenness = TFC_Core.getPlayerFoodStats(player).soberTime - TFC_Time.getTotalTicks();
		if (!this.mc.thePlayer.getActivePotionEffects().isEmpty() || hasHeatProt || hasColdProt || slashWound > 0 || pierceWound > 0|| crushWound > 0 || drunkenness > 250)
		{
			// this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
			this.guiLeft = (this.width - this.xSize) / 2;
			this.hasEffect = true;
		}

		buttonList.clear();
		buttonList.add(new GuiInventoryButton(0, guiLeft + xSize, guiTop + 3, 25, 20, 0, 86, 25, 20,
				TFC_Core.translate("gui.Inventory.Inventory"), TFC_Textures.guiInventory));
		buttonList.add(new GuiInventoryButton(1, guiLeft + xSize, guiTop + 22, 25, 20, 0, 86, 25, 20,
				TFC_Core.translate("gui.Inventory.Skills"), TFC_Textures.guiSkills));
		buttonList.add(new GuiInventoryButton(2, guiLeft + xSize, guiTop + 41, 25, 20, 0, 86, 25, 20,
				TFC_Core.translate("gui.Calendar.Calendar"), TFC_Textures.guiCalendar));
		buttonList.add(new GuiInventoryButton(3, guiLeft + xSize, guiTop + 60, 25, 20, 0, 86, 25, 20,
				TFC_Core.translate("gui.Inventory.Health"), TFC_Textures.guiHealth));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 1)
			Minecraft.getMinecraft().displayGuiScreen(new GuiSkills(player));
		else if (guibutton.id == 2)
			Minecraft.getMinecraft().displayGuiScreen(new GuiCalendar(player));
		else if (guibutton.id == 3)
			Minecraft.getMinecraft().displayGuiScreen(new GuiHealth(player));
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.drawScreen(par1, par2, par3);
		this.xSizeLow = par1;
		this.ySizeLow = par2;
		if (hasEffect)
			displayDebuffEffects();

		for (int j1 = 0; j1 < this.inventorySlots.inventorySlots.size(); ++j1)
		{
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(j1);
			if (this.isMouseOverSlot(slot, par1, par2) && slot.func_111238_b())
				this.activeSlot = slot;
		}
	}

	protected boolean isMouseOverSlot(Slot par1Slot, int par2, int par3)
	{
		return this.func_146978_c/* isPointInRegion */(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16,
				par2, par3);
	}

	/**
	 * Displays debuff/potion effects that are currently being applied to the
	 * player
	 */
	private void displayDebuffEffects()
	{
		int var1 = this.guiLeft - 124;
		int var2 = this.guiTop;
		Collection var4 = this.mc.thePlayer.getActivePotionEffects();
		BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);
		boolean hasHeatProt = bodyTemp.temporaryHeatProtection > 0;
		boolean hasColdProt = bodyTemp.temporaryColdProtection > 0;
		int slashWound = TFC_Core.getPlayerFoodStats(player).slashWoundTimer;
		int pierceWound = TFC_Core.getPlayerFoodStats(player).pierceWoundTimer;
		int crushWound = TFC_Core.getPlayerFoodStats(player).crushWoundTimer;
		int slashWoundSev = TFC_Core.getPlayerFoodStats(player).slashWoundSeverity;
		int pierceWoundSev = TFC_Core.getPlayerFoodStats(player).pierceWoundSeverity;
		int crushWoundSev = TFC_Core.getPlayerFoodStats(player).crushWoundSeverity;
		int crushWoundTreat = TFC_Core.getPlayerFoodStats(player).crushWoundTreatment;
		long drunkenness = TFC_Core.getPlayerFoodStats(player).soberTime - TFC_Time.getTotalTicks();
		if (!var4.isEmpty() || hasHeatProt || hasColdProt || slashWound > 0 || pierceWound > 0|| crushWound > 0 || drunkenness > 250)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			int var6 = 33;
			

			int extras = hasHeatProt ? 1 : 0;
			extras += hasColdProt ? 1 : 0;
			extras += slashWound > 0?1:0;
			extras += pierceWound > 0?1:0;
			extras += crushWound > 0?1:0;
			extras += drunkenness > 250?1:0;

			if ((var4.size() + extras) > 5)
			{
				var6 = 132 / ((var4.size() + extras) - 1);
			}
			if (!var4.isEmpty())
			{
				for (Iterator var7 = this.mc.thePlayer.getActivePotionEffects().iterator(); var7
						.hasNext(); var2 += var6)
				{
					PotionEffect var8 = (PotionEffect) var7.next();
					Potion var9 = Potion.potionTypes[var8.getPotionID()] instanceof TFCPotion
							? ((TFCPotion) Potion.potionTypes[var8.getPotionID()])
							: Potion.potionTypes[var8.getPotionID()];
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					TFC_Core.bindTexture(EFFECTS_TEXTURE);
					this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

					if (var9.hasStatusIcon())
					{
						int var10 = var9.getStatusIconIndex();
						this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var10 % 8 * 18, 198 + var10 / 8 * 18, 18,
								18);
					}

					String var12 = TFC_Core.translate(var9.getName());

					if (var8.getAmplifier() == 1)
						var12 = var12 + " II";
					else if (var8.getAmplifier() == 2)
						var12 = var12 + " III";
					else if (var8.getAmplifier() == 3)
						var12 = var12 + " IV";

					this.fontRendererObj.drawStringWithShadow(var12, var1 + 10 + 18, var2 + 6, 16777215);
					String var11 = Potion.getDurationString(var8);
					this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
				}
			}
			if (extras > 0)
			{
				if (hasColdProt)
				{
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					TFC_Core.bindTexture(EFFECTS_TEXTURE);
					this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
					
					this.drawTexturedModalRect(var1 + 6, var2 + 7, 144, 198, 18,
							18);
					
					String effectName = TFC_Core.translate("gui.coldProtection");
					this.fontRendererObj.drawStringWithShadow(effectName, var1 + 10 + 18, var2 + 6, 16777215);
					String var11 = StringUtils.ticksToElapsedTime((int) bodyTemp.tempColdTimeRemaining);
					this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
					var2 += var6;
				}
				if (hasHeatProt)
				{
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					TFC_Core.bindTexture(EFFECTS_TEXTURE);
					this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
					this.drawTexturedModalRect(var1 + 6, var2 + 7, 144, 198+18, 18,
							18);
					String effectName = TFC_Core.translate("gui.heatProtection");
					this.fontRendererObj.drawStringWithShadow(effectName, var1 + 10 + 18, var2 + 6, 16777215);
					String var11 = StringUtils.ticksToElapsedTime((int) bodyTemp.tempHeatTimeRemaining);
					this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
					var2 += var6;
				}
				if (slashWound > 0)
				{
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					TFC_Core.bindTexture(EFFECTS_TEXTURE);
					this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
					TFC_Core.bindTexture(ICONS_TEXTURE);
					this.drawTexturedModalRect(var1 + 7, var2 + 8, 92, 80, 16,
							16);
					String effectName = TFC_Core.translate("gui.slashWound"+slashWoundSev);
					this.fontRendererObj.drawStringWithShadow(effectName, var1 + 10 + 18, var2 + 6, 16777215);
					String var11 = StringUtils.ticksToElapsedTime(slashWound);
					this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
					var2 += var6;
				}
				if (pierceWound > 0)
				{
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					TFC_Core.bindTexture(EFFECTS_TEXTURE);
					this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
					TFC_Core.bindTexture(ICONS_TEXTURE);
					this.drawTexturedModalRect(var1 + 7, var2 + 8, 108, 80, 16,
							16);
					String effectName = TFC_Core.translate("gui.pierceWound"+pierceWoundSev);
					this.fontRendererObj.drawStringWithShadow(effectName, var1 + 10 + 18, var2 + 6, 16777215);
					String var11 = StringUtils.ticksToElapsedTime(pierceWound);
					this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
					var2 += var6;
				}
				if (crushWound > 0)
				{
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					TFC_Core.bindTexture(EFFECTS_TEXTURE);
					this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
					TFC_Core.bindTexture(ICONS_TEXTURE);
					this.drawTexturedModalRect(var1 + 7, var2 + 8, 124, 80, 16,
							16);
					String effectName = TFC_Core.translate("gui.crushWound"+crushWoundSev+":"+crushWoundTreat);
					this.fontRendererObj.drawStringWithShadow(effectName, var1 + 10 + 18, var2 + 6, 16777215);
					String var11 = StringUtils.ticksToElapsedTime(crushWound);
					this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
					var2 += var6;
				}
				if (drunkenness > 250)
				{
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					TFC_Core.bindTexture(EFFECTS_TEXTURE);
					this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
					this.drawTexturedModalRect(var1 + 6, var2 + 7, 144, 198+18+18, 18,
					18);
					String effectName = TFC_Core.translate(TFC_Core.getDrunkennessString(drunkenness));
					this.fontRendererObj.drawStringWithShadow(effectName, var1 + 10 + 18, var2 + 6, 16777215);
					String var11 = StringUtils.ticksToElapsedTime((int)drunkenness-250);
					this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
					var2 += var6;
				}
			}
		}
	}

	private long spamTimer;

	@Override
	protected boolean checkHotbarKeys(int keycode)
	{
		if (this.activeSlot != null && this.activeSlot.slotNumber == 0 && this.activeSlot.getHasStack()
				&& this.activeSlot.getStack().getItem() instanceof IFood)
			return false;
		// Here is the code for quick stacking food
		if (keycode == 31 && activeSlot != null && activeSlot.canTakeStack(player) && activeSlot.getHasStack()
				&& activeSlot.getStack() != null && activeSlot.getStack().getItem() instanceof IFood
				&& TFC_Time.getTotalTicks() > spamTimer + 5)
		{
			spamTimer = TFC_Time.getTotalTicks();
			Item iType = activeSlot.getStack().getItem();
			ItemStack activeIS = activeSlot.getStack();
			for (int i = 9; i < 45 && getEmptyCraftSlot() != -1; i++)
			{
				ItemStack is = this.inventorySlots.getSlot(i).getStack();
				if (is != null && is.getItem() == iType && Food.areEqual(activeIS, is)
						&& Food.getWeight(is) < Global.FOOD_MAX_WEIGHT)
					this.handleMouseClick(this.inventorySlots.getSlot(i), i, getEmptyCraftSlot(), 7);
			}

			if (this.inventorySlots.getSlot(0).getStack() != null)
			{
				this.handleMouseClick(this.inventorySlots.getSlot(0), 0, 0, 1);
			}
			return true;
		}
		else if (keycode == 32 && TFC_Time.getTotalTicks() > spamTimer + 5)
		{
			spamTimer = TFC_Time.getTotalTicks();
			int knifeSlot = -1;
			if (!getCraftingHasKnife())
			{
				for (int i = 9; i < 45 && getEmptyCraftSlot() != -1; i++)
				{
					ItemStack is = this.inventorySlots.getSlot(i).getStack();
					if (is != null && is.getItem() instanceof IKnife)
					{
						knifeSlot = i;
						break;
					}
				}
			}
			for (int i = 9; i < 45 && getEmptyCraftSlot() != -1 && knifeSlot != -1
					&& inventorySlots.getSlot(knifeSlot).getStack() != null; i++)
			{
				ItemStack is = this.inventorySlots.getSlot(i).getStack();
				int knifeDamage = inventorySlots.getSlot(knifeSlot).getStack().getItemDamage();
				if (knifeDamage >= inventorySlots.getSlot(knifeSlot).getStack().getMaxDamage())
					break;
				if (is != null && !(is.getItem() instanceof ItemMeal) && is.getItem() instanceof IFood
						&& Food.getDecay(is) > 0 && Food.getDecayTimer(is) >= TFC_Time.getTotalHours())
				{
					this.handleMouseClick(this.inventorySlots.getSlot(i), i, getEmptyCraftSlot(), 7);
					this.handleMouseClick(this.inventorySlots.getSlot(0), 0, 0, 1);
				}
			}
			return true;
		}
		else
			return super.checkHotbarKeys(keycode);
	}

	private int getEmptyCraftSlot()
	{
		if (this.inventorySlots.getSlot(4).getStack() == null)
			return 4;
		if (this.inventorySlots.getSlot(1).getStack() == null)
			return 1;
		if (this.inventorySlots.getSlot(2).getStack() == null)
			return 2;
		if (this.inventorySlots.getSlot(3).getStack() == null)
			return 3;
		if (player.getEntityData().hasKey("craftingTable"))
		{
			if (this.inventorySlots.getSlot(45).getStack() == null)
				return 45;
			if (this.inventorySlots.getSlot(46).getStack() == null)
				return 46;
			if (this.inventorySlots.getSlot(47).getStack() == null)
				return 47;
			if (this.inventorySlots.getSlot(48).getStack() == null)
				return 48;
			if (this.inventorySlots.getSlot(49).getStack() == null)
				return 49;
		}

		return -1;
	}

	private boolean getCraftingHasKnife()
	{
		if (this.inventorySlots.getSlot(4).getStack() != null
				&& this.inventorySlots.getSlot(4).getStack().getItem() instanceof IKnife)
			return true;
		if (this.inventorySlots.getSlot(1).getStack() != null
				&& this.inventorySlots.getSlot(1).getStack().getItem() instanceof IKnife)
			return true;
		if (this.inventorySlots.getSlot(2).getStack() != null
				&& this.inventorySlots.getSlot(2).getStack().getItem() instanceof IKnife)
			return true;
		if (this.inventorySlots.getSlot(3).getStack() != null
				&& this.inventorySlots.getSlot(3).getStack().getItem() instanceof IKnife)
			return true;
		if (player.getEntityData().hasKey("craftingTable"))
		{
			if (this.inventorySlots.getSlot(45).getStack() != null
					&& this.inventorySlots.getSlot(45).getStack().getItem() instanceof IKnife)
				return true;
			if (this.inventorySlots.getSlot(46).getStack() != null
					&& this.inventorySlots.getSlot(46).getStack().getItem() instanceof IKnife)
				return true;
			if (this.inventorySlots.getSlot(47).getStack() != null
					&& this.inventorySlots.getSlot(47).getStack().getItem() instanceof IKnife)
				return true;
			if (this.inventorySlots.getSlot(48).getStack() != null
					&& this.inventorySlots.getSlot(48).getStack().getItem() instanceof IKnife)
				return true;
			if (this.inventorySlots.getSlot(49).getStack() != null
					&& this.inventorySlots.getSlot(49).getStack().getItem() instanceof IKnife)
				return true;
		}

		return false;
	}
}
