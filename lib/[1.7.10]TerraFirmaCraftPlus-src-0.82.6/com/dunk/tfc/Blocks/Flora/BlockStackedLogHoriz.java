package com.dunk.tfc.Blocks.Flora;

import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockStackedLogHoriz extends BlockLogHoriz
{
	IIcon sideIcons[],innerIcons[],rotatedSideIcons[];
	
	public BlockStackedLogHoriz(int off)
	{
		super(off);
		sideIcons = new IIcon[16];
		innerIcons = new IIcon[16];
		rotatedSideIcons = new IIcon[16];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		for (int i = 0; i < woodNames.length; i++)
		{
			sideIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/stacked/Stacked " + woodNames[i] + " Log");
			innerIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/stacked/Stacked " + woodNames[i] + " Log Top");
			rotatedSideIcons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "wood/trees/stacked/Stacked " + woodNames[i] + " Log Side");
		}
	}
	
	@Override
	public int damageDropped(int dmg)
	{
		return ((dmg & 7) + offset); //NOPMD
	}

	@Override
	public Item getItemDropped(int i, Random r, int j)
	{
		return TFCItems.stackedLogs;
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		int dir = meta >> 3;
		meta = (meta & 7) + offset; //NOPMD

		if(dir == 0)
		{
			if(side == 0 || side == 1)
				return sideIcons[meta];
			else if(side == 2 || side == 3)
				return innerIcons[meta];
			else
				return rotatedSideIcons[meta];
		}
		else
		{
			if(side == 0 || side == 1 || side == 2 || side == 3)
				return rotatedSideIcons[meta];
			else
				return innerIcons[meta];
		}
	}
}
