package com.dunk.tfc.Blocks.Vanilla;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFCTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockMelon;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockCustomMelon extends BlockMelon
{
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon faceIcon;
	
	public BlockCustomMelon()
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}
	
	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? this.topIcon : side == 0 ? this.topIcon : // Top or Bottom Side
        	meta == 2 && side == 2 ? this.faceIcon : meta == 3 && side == 5 ? this.faceIcon : // Face Side
        		meta == 0 && side == 3 ? this.faceIcon : meta == 1 && side == 4 ? this.faceIcon : // Face Side
        			this.blockIcon; // Blank Side
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.topIcon = iconRegister.registerIcon(this.getTextureName() + "_top");
		this.blockIcon = iconRegister.registerIcon(this.getTextureName() + "_side");
		this.faceIcon =  this.blockIcon; // Only have a face when lit.
	}
}
