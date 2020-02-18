package com.dunk.tfc.GUI;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Containers.ContainerLogPile;
import com.dunk.tfc.TileEntities.TELogPile;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiLogPile extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_logpile.png");

	public GuiLogPile(InventoryPlayer inventoryplayer, TELogPile te, World world, int x, int y, int z)
	{
		super(new ContainerLogPile(inventoryplayer, te, world, x, y, z), 176, 85);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}
}
