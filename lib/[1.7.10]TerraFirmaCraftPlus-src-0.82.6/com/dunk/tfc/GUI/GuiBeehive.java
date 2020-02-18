package com.dunk.tfc.GUI;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Containers.ContainerBasket;
import com.dunk.tfc.Containers.ContainerBeehive;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.Player.PlayerInventory;
import com.dunk.tfc.TileEntities.TEBarrel;
import com.dunk.tfc.TileEntities.TEBasket;
import com.dunk.tfc.TileEntities.TEBeehive;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiBeehive extends GuiContainerTFC
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_bag.png");
	private TEBeehive beehiveTE;
	private EntityPlayer player;

	public GuiBeehive(InventoryPlayer inventoryplayer, TEBeehive te, World world, int x, int y, int z)
	{
		super(new ContainerBeehive(inventoryplayer, te, world, x, y, z), 176, 85);
		beehiveTE = te;
		player = inventoryplayer.player;
		guiLeft = (width - 208) / 2;
		guiTop = (height - 198) / 2;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
	/*	if (basketTE.getInvCount() > 0)
		{
			if (guiTab == 0)
				((GuiButton) buttonList.get(4)).visible = false;//Turn off Liquid
			else if (guiTab == 1)
				((GuiButton) buttonList.get(1)).visible = false;//Turn off Liquid
		}
		else
		{
			if (guiTab == 0)
				((GuiButton) buttonList.get(4)).visible = true;//Turn on Liquid
			else if (guiTab == 1)
				((GuiButton) buttonList.get(1)).visible = true;//Turn on Liquid
		}
		if (basketTE.getFluidLevel() > 0)
		{
			if (guiTab == 0)
				((GuiButton) buttonList.get(3)).visible = false;//Turn off Solid
			else if (guiTab == 1)
				((GuiButton) buttonList.get(0)).visible = false;//Turn off Solid
		}
		else
		{
			if (guiTab == 0)
				((GuiButton) buttonList.get(3)).visible = true;//Turn on Solid
			else if (guiTab == 1)
				((GuiButton) buttonList.get(0)).visible = true;//Turn on Solid
		}

		if (basketTE.getSealed() && guiTab == 0)
		{
			((GuiButton) buttonList.get(0)).displayString = TFC_Core.translate("gui.Barrel.Unseal");
			((GuiButton) buttonList.get(1)).enabled = false;
			((GuiButton) buttonList.get(2)).enabled = false;
		}
		else if (!basketTE.getSealed() && guiTab == 0)
		{
			((GuiButton) buttonList.get(0)).displayString = TFC_Core.translate("gui.Barrel.Seal");
			((GuiButton) buttonList.get(1)).enabled = true;
			((GuiButton) buttonList.get(2)).enabled = true;
		}*/
	}

	@Override
	public void initGui()
	{
		super.initGui();
		//createButtons();
	}

	public void createButtons()
	{
	/*	buttonList.clear();
		if (guiTab == 0)
		{
			if (!basketTE.getSealed())
				buttonList.add(new GuiButton(0, guiLeft + 38, guiTop + 50, 50, 20, TFC_Core.translate("gui.Barrel.Seal")));
			else
				buttonList.add(new GuiButton(0, guiLeft + 38, guiTop + 50, 50, 20, TFC_Core.translate("gui.Barrel.Unseal")));
			buttonList.add(new GuiButton(1, guiLeft + 88, guiTop + 50, 50, 20, TFC_Core.translate("gui.Barrel.Empty")));
			if (basketTE.mode == TEBarrel.MODE_IN)
				buttonList.add(new GuiBarrelTabButton(2, guiLeft + 39, guiTop + 29, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOn"), 0, 204, 16, 16));
			else if (basketTE.mode == TEBarrel.MODE_OUT)
				buttonList.add(new GuiBarrelTabButton(2, guiLeft + 39, guiTop + 29, 16, 16, this, TFC_Core.translate("gui.Barrel.ToggleOff"), 0, 188, 16, 16));
			buttonList.add(new GuiBarrelTabButton(3, guiLeft + 36, guiTop - 12, 31, 15, this, TFC_Textures.guiSolidStorage, TFC_Core.translate("gui.Barrel.Solid")));
			buttonList.add(new GuiBarrelTabButton(4, guiLeft + 5, guiTop - 12, 31, 15, this, TFC_Textures.guiLiquidStorage, TFC_Core.translate("gui.Barrel.Liquid")));

		}
		else if (guiTab == 1)
		{
			buttonList.add(new GuiBarrelTabButton(0, guiLeft + 36, guiTop - 12, 31, 15, this, TFC_Textures.guiSolidStorage, TFC_Core.translate("gui.Barrel.Solid")));
			buttonList.add(new GuiBarrelTabButton(1, guiLeft + 5, guiTop - 12, 31, 15, this, TFC_Textures.guiLiquidStorage, TFC_Core.translate("gui.Barrel.Liquid")));

			if (!basketTE.getSealed())
				buttonList.add(new GuiButton(2, guiLeft + 6, guiTop + 33, 44, 20, TFC_Core.translate("gui.Barrel.Seal")));
			else
				buttonList.add(new GuiButton(2, guiLeft + 6, guiTop + 33, 44, 20, TFC_Core.translate("gui.Barrel.Unseal")));
		}*/
	}

	@Override
	public void drawTooltip(int mx, int my, String text)
	{
		List<String> list = new ArrayList<String>();
		list.add(text);
		this.drawHoveringText(list, mx, my + 15, this.fontRendererObj);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
	}

	/*public class GuiButtonMode extends GuiButton
	{
		public GuiButtonMode(int par1, int par2, int par3, String par4Str)
		{
			super(par1, par2, par3, 200, 20, par4Str);
		}

		public GuiButtonMode(int par1, int par2, int par3, int par4, int par5, String par6Str)
		{
			super(par1, par2, par3, par4, par5, par6Str);
		}

		@Override
		public void drawButton(Minecraft par1Minecraft, int xPos, int yPos)
		{
			if (this.visible)
			{
				FontRenderer fontrenderer = par1Minecraft.fontRenderer;
				par1Minecraft.getTextureManager().bindTexture(buttonTextures);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.field_146123_n = xPos >= this.xPosition && yPos >= this.yPosition && xPos < this.xPosition + this.width && yPos < this.yPosition + this.height;
				int k = this.getHoverState(this.field_146123_n);
				this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
				this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
				this.mouseDragged(par1Minecraft, xPos, yPos);
				int l = 14737632;

				if (!this.enabled)
				{
					l = -6250336;
				}
				else if (this.field_146123_n)
				{
					l = 16777120;
				}

				this.drawCenteredString(fontrenderer, basketTE.mode == 0 ? TFC_Core.translate("gui.Barrel.ToggleOn") : TFC_Core.translate("gui.Barrel.ToggleOff"), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
			}
		}
	}*/

	/*public class GuiBarrelTabButton extends GuiButton
	{
		private GuiLargeVessel screen;
		private IIcon buttonicon;

		private int xPos;
		private int yPos = 172;
		private int xSize = 31;
		private int ySize = 15;

		public GuiBarrelTabButton(int index, int xPos, int yPos, int width, int height, GuiLargeVessel gui, IIcon icon, String s)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			buttonicon = icon;
		}

		public GuiBarrelTabButton(int index, int xPos, int yPos, int width, int height, GuiLargeVessel gui, String s, int xp, int yp, int xs, int ys)
		{
			super(index, xPos, yPos, width, height, s);
			screen = gui;
			this.xPos = xp;
			this.yPos = yp;
			xSize = xs;
			ySize = ys;
		}

		@Override
		public void drawButton(Minecraft mc, int x, int y)
		{
			if (this.visible)
			{
				TFC_Core.bindTexture(GuiLargeVessel.TEXTURE);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.zLevel = 301f;
				this.drawTexturedModalRect(this.xPosition, this.yPosition, xPos, yPos, xSize, ySize);
				this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				if (buttonicon != null)
					this.drawTexturedModelRectFromIcon(this.xPosition + 12, this.yPosition + 4, buttonicon, 8, 8);

				this.zLevel = 0;
				this.mouseDragged(mc, x, y);

				if (field_146123_n)
				{
					screen.drawTooltip(x, y, this.displayString);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		}
	}*/
/*
	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guiTab == 0)
		{
			if (guibutton.id == 0)
			{
				if (!basketTE.getSealed())
					basketTE.actionSeal(0, player);
				else
					basketTE.actionUnSeal(0, player);
			}
			else if (guibutton.id == 1)
				basketTE.actionEmpty();
			else if (guibutton.id == 2)
			{
				basketTE.actionMode();
				createButtons();
			}
			else if (guibutton.id == 3 && basketTE.getFluidLevel() == 0 && basketTE.getInvCount() == 0)
				basketTE.actionSwitchTab(1, player);
		}
		else if (guiTab == 1)
		{
			if (guibutton.id == 1 && basketTE.getInvCount() == 0)
				basketTE.actionSwitchTab(0, player);
			else if (guibutton.id == 2)
			{
				if (!basketTE.getSealed())
					basketTE.actionSeal(1, player);
				else
					basketTE.actionUnSeal(1, player);
				createButtons();
			}
		}
	}*/

	/*
	 * Edited Copy/Paste of drawGui() code since the background texture changes depending on the tab selected.
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		/*TFC_Core.bindTexture(TEXTURE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - xSize) / 2;
		int h = (height - ySize) / 2;
		drawTexturedModalRect(w, h, 0, 86, xSize, this.getShiftedYSize());*/
		this.drawGui(TEXTURE);
		//PlayerInventory.drawInventory(this, width, height, this.getShiftedYSize());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	public void drawScreen(int x, int y, float par3)
	{
		super.drawScreen(x, y, par3);

	}

	private void drawSlotOverlay(Slot slot)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		int xPos = slot.xDisplayPosition + guiLeft - 1;
		int yPos = slot.yDisplayPosition + guiTop - 1;
		GL11.glColorMask(true, true, true, false);
		this.drawGradientRect(xPos, yPos, xPos + 18, yPos + 18, 0x75FFFFFF, 0x75FFFFFF);
		GL11.glColorMask(true, true, true, true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}
