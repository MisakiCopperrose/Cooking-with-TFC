package com.dunk.tfc.GUI;

import com.dunk.tfc.Reference;
import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Containers.ContainerCarpentry;
import com.dunk.tfc.Containers.ContainerSewing;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Textures;
import com.dunk.tfc.TileEntities.TEAnvil;
import com.dunk.tfc.api.TFCOptions;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiCarpentry extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,
	Reference.ASSET_PATH_GUI + "gui_carpentry.png");
	private EntityPlayer player;
	private int x, y, z;
	
	public GuiCarpentry(InventoryPlayer inventoryplayer, World world, int i, int j, int k)
	{
		super(new ContainerCarpentry(inventoryplayer, world, i, j, k), 212, 149);
		((ContainerCarpentry) inventorySlots).setGUI(this);
		player = inventoryplayer.player;
	}
	
	public GuiCarpentry(Container container, int xsize, int ysize)
	{
		super(container, xsize, ysize);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void initGui()
	{
		super.initGui();

		buttonList.clear();
		/*buttonList.add(new GuiAnvilButton(7, guiLeft + 123, guiTop + 82, 16, 16, TFC_Textures.anvilShrink, 208, 17, 16, 16, this, TFC_Core.translate("gui.Anvil.Shrink")));
		buttonList.add(new GuiAnvilButton(6, guiLeft + 105, guiTop + 82, 16, 16, TFC_Textures.anvilUpset, 208, 17, 16, 16, this, TFC_Core.translate("gui.Anvil.Upset")));
		buttonList.add(new GuiAnvilButton(5, guiLeft + 123, guiTop + 64, 16, 16, TFC_Textures.anvilBend, 208, 17, 16, 16, this, TFC_Core.translate("gui.Anvil.Bend")));
		buttonList.add(new GuiAnvilButton(4, guiLeft + 105, guiTop + 64, 16, 16, TFC_Textures.anvilPunch, 208, 17, 16, 16, this, TFC_Core.translate("gui.Anvil.Punch")));
		buttonList.add(new GuiAnvilButton(3, guiLeft + 87, guiTop + 82, 16, 16, TFC_Textures.anvilDraw, 208, 33, 16, 16, this, TFC_Core.translate("gui.Anvil.Draw")));
		buttonList.add(new GuiAnvilButton(2, guiLeft + 69, guiTop + 82, 16, 16, TFC_Textures.anvilHitHeavy, 208, 33, 16, 16, this, TFC_Core.translate("gui.Anvil.HeavyHit")));
		buttonList.add(new GuiAnvilButton(1, guiLeft + 87, guiTop + 64, 16, 16, TFC_Textures.anvilHitMedium, 208, 33, 16, 16, this, TFC_Core.translate("gui.Anvil.MediumHit")));
		buttonList.add(new GuiAnvilButton(0, guiLeft + 69, guiTop + 64, 16, 16, TFC_Textures.anvilHitLight, 208, 33, 16, 16, this, TFC_Core.translate("gui.Anvil.LightHit")));
		buttonList.add(new GuiButton(8, guiLeft + 13, guiTop + 53, 36, 20, TFC_Core.translate("gui.Anvil.Weld")));
		buttonList.add(new GuiAnvilButton(9, guiLeft + 113, guiTop + 7, 19, 21, 208, 49, 19, 21, this, 2, TFCOptions.anvilRuleColor2[0], TFCOptions.anvilRuleColor2[1], TFCOptions.anvilRuleColor2[2]));
		buttonList.add(new GuiAnvilButton(10, guiLeft + 94, guiTop + 7, 19, 21, 208, 49, 19, 21, this, 1, TFCOptions.anvilRuleColor1[0], TFCOptions.anvilRuleColor1[1], TFCOptions.anvilRuleColor1[2]));
		buttonList.add(new GuiAnvilButton(11, guiLeft + 75, guiTop + 7, 19, 21, 208, 49, 19, 21, this, 0, TFCOptions.anvilRuleColor0[0], TFCOptions.anvilRuleColor0[1], TFCOptions.anvilRuleColor0[2]));*/
		buttonList.add(new GuiCarpentryPlanButton(12, guiLeft + 122, guiTop + 45, 18, 18, this, TFC_Core.translate("gui.Anvil.Plans")));
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 12 )
			player.openGui(TerraFirmaCraft.instance, 24, player.worldObj, x, y, z);
		this.inventorySlots.detectAndSendChanges();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
		/*if (currentTexture != null)
		{
			bindTexture(currentTexture);
			drawTextured98Rect((this.guiLeft + 6) + 33, (this.guiTop + 6), 0, 0, 98, 98);
		}
		bindTexture(failTexture);
		if (sr != null)
		{
			int[][][] patterns = sr.getSewingPattern().getPatterns();
			for (int a = 0; a < patterns.length; a++)
			{
				for (int b = 0; b < patterns[a].length - 1; b++)
				{
					drawGuideLines(patterns[a][b][0] + (this.guiLeft + 6), patterns[a][b][1] + (this.guiTop + 6), patterns[a][b + 1][0] + (this.guiLeft + 6),
							patterns[a][b + 1][1] + (this.guiTop + 6), a);
				}
			}
		}
		bindTexture(sewTexture);
		for (int x = 0; x < 164 / sewScale; x++)
		{
			for (int y = 0; y < 98 / sewScale; y++)
			{
				if (sewnPoints[x][y])
				{
					drawSewing((x * (int) sewScale) + (this.guiLeft + 6), (y * (int) sewScale) + (this.guiTop + 6));
				}
			}
		}
		if (invalids != null)
		{
			bindTexture(failTexture);
			for (int x = 0; x < 164; x++)
			{
				for (int y = 0; y < 98; y++)
				{
					if (invalids[x][y])
					{
						drawSewing((x) + (this.guiLeft + 6), (y) + (this.guiTop + 6));
					}
				}
			}
		}
		for (int j1 = 0; j1 < this.inventorySlots.inventorySlots.size(); ++j1)
		{
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(j1);
			if (this.isMouseOverSlot(slot, i, j))
				this.activeSlot = slot;
		}*/
	}

}
