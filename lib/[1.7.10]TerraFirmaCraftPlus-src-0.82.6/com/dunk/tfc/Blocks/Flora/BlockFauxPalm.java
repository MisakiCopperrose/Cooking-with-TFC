package com.dunk.tfc.Blocks.Flora;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.BlockTerra;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockFauxPalm extends BlockTerra
{

	IIcon blockIcon2;
	IIcon blockIcon3;
	IIcon blockIcon4;
	IIcon blockIcon7;
	IIcon blockIcon8;
	IIcon blockIcon9;
	IIcon blockIcon10;
	IIcon blockIcon11;
	IIcon blockIcon12;
	IIcon blockIcon13;
	IIcon blockIcon14;
	IIcon blockIcon15;
	IIcon blockIcon16;
	IIcon blockIcon17;
	IIcon blockIcon18;
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return meta==0?this.blockIcon:
			meta==1?this.blockIcon2:
			meta==2?this.blockIcon3:
				meta==3?this.blockIcon4:
					meta==4?this.blockIcon7:
						meta==5?this.blockIcon8:
							meta==6?this.blockIcon9:
								meta==7?this.blockIcon10:
									meta==8?this.blockIcon11:
										meta==9?this.blockIcon12:
											meta==10?this.blockIcon13:
												meta==11?this.blockIcon14:
													meta==12?this.blockIcon15:
														meta==13?this.blockIcon16:
															meta==14?this.blockIcon17:
																this.blockIcon18;
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = reg.registerIcon(Reference.MOD_ID + ":" + "plants/palmFrondLightA");
		this.blockIcon2 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/palmFrondLightB");
		this.blockIcon3 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/palmFrondDarkA");
		this.blockIcon4 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/palmFrondDarkB");
		this.blockIcon7 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/palmFrondLightEnd");
		this.blockIcon8 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/palmFrondDarkEnd");
		this.blockIcon9 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/palmFrondLightBase");
		this.blockIcon10 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/palmFrondDarkBase");
		this.blockIcon17 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/bananaLeafLightBase");
		this.blockIcon11 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/bananaLeafLightA");
		this.blockIcon12 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/bananaLeafLightB");
		this.blockIcon15 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/bananaLeafLightEnd");
		this.blockIcon18 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/bananaLeafDarkBase");
		this.blockIcon13 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/bananaLeafDarkA");
		this.blockIcon14 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/bananaLeafDarkB");
		this.blockIcon16 = reg.registerIcon(Reference.MOD_ID + ":" + "plants/bananaLeafDarkEnd");
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return true;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}
}
