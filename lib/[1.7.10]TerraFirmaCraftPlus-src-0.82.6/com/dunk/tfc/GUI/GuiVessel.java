package com.dunk.tfc.GUI;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Containers.ContainerVessel;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiVessel extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_vessel.png");

	public GuiVessel(InventoryPlayer inventoryplayer, World world, int i, int j, int k, ContainerVessel.Types t)
	{
		super(new ContainerVessel(inventoryplayer, world, i, j, k, t), 176, 85);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	/**
	 * This function is what controls the hotbar shortcut check when you press a
	 * number key when hovering a stack.
	 */
	@Override
	protected boolean checkHotbarKeys(int par1)
	{
		if (this.mc.thePlayer.inventory.currentItem != par1 - 2)
		{
			super.checkHotbarKeys(par1);
			return true;
		}
		else
			return false;
	}
}
