package com.dunk.tfc.Blocks;

import com.dunk.tfc.Reference;
import com.dunk.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockL extends BlockTerra
{
	public IIcon lights;

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		lights = reg.registerIcon(Reference.MOD_ID + ":" + "L");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(side == 0 || side ==1)
		{
			return TFCBlocks.invisibleBlock.getIcon(0, 0);
		}
		return lights;
	}
}
