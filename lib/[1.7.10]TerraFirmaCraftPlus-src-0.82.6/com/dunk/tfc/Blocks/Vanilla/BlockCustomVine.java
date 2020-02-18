package com.dunk.tfc.Blocks.Vanilla;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.ColorizerFoliageTFC;
import com.dunk.tfc.Core.TFCTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockVine;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;

public class BlockCustomVine extends BlockVine implements IShearable
{
	public BlockCustomVine()
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
	}

	@Override
	public int getBlockColor()
	{
		return ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@Override
	public int getRenderColor(int par1)
	{
		return par1 == 0 ? 16777215 : ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		return TerraFirmaCraft.proxy.foliageColorMultiplier(bAccess, x, y, z);
	}
}
