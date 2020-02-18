package com.dunk.tfc.Render.Blocks;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Render.RenderBlocksWithRotation;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCOptions;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderTeepee implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer)
	{
		if(block == TFCBlocks.invisibleBlock)
		{
			renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
			renderer.renderStandardBlock(TFCOptions.enableDebugMode?TFCBlocks.planks:block, x, y, z);
			return false;
		}
		renderer = new RenderBlocksWithRotation(renderer);
		int meta = world.getBlockMetadata(x, y, z)%4;
		IIcon wall = block.getIcon(1, 0);
		IIcon door = block.getIcon(0, 0);
		((RenderBlocksWithRotation)renderer).rotation = 0;
		Tessellator.instance.setColorOpaque(200, 200, 200);
		((RenderBlocksWithRotation)renderer).renderTeepee(wall,door, x, y, z,meta);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
