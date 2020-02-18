package com.dunk.tfc.GUI;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Containers.ContainerQuern;
import com.dunk.tfc.TileEntities.TEQuern;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiQuern extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_quern.png");

	public GuiQuern(InventoryPlayer inventoryplayer, TEQuern te, World world, int x, int y, int z)
	{
		super(new ContainerQuern(inventoryplayer, te, world, x, y, z), 176, 85);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}
}
