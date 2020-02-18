package com.dunk.tfc.Blocks.Terrain;

import com.dunk.tfc.Reference;
import com.dunk.tfc.api.TFCBlocks;

import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockIgInBrick extends BlockIgInSmooth
{
	public BlockIgInBrick()
	{
		super();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < names.length; i++)
		{
			if(this == TFCBlocks.stoneIgInLargeBrick)
			{
				icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/"+names[i]+" Brick");
			}
			else
			{
				icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/"+names[i]+" Small Brick");
			}
		}
	}
}
