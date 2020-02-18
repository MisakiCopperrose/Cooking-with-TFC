package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.Reference;

import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockDryGrass extends BlockGrass
{
	public BlockDryGrass(int texOff)
	{
		super(texOff);
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		super.registerBlockIcons(registerer);
		//grassTopTexture = registerer.registerIcon(Reference.MOD_ID + ":" + "GrassSparseOverlay");
		grassTopTexture = registerer.registerIcon(Reference.MOD_ID + ":" + "GrassTopLightYellow2");
		iconGrassSideOverlay = registerer.registerIcon(Reference.MOD_ID + ":" + "GrassSideDry");
	}
	
}
