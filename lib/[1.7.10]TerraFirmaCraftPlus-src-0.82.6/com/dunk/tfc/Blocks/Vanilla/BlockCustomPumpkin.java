package com.dunk.tfc.Blocks.Vanilla;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.BlockCrop;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Food.CropIndex;
import com.dunk.tfc.Food.CropManager;
import com.dunk.tfc.TileEntities.TECrop;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCustomPumpkin extends BlockPumpkin
{
	private boolean isLit;
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon faceIcon;
	
	//@SideOnly(Side.CLIENT)
	private IIcon[] growthIconTop = new IIcon[3];
	//@SideOnly(Side.CLIENT)
	private IIcon[] growthIconSide = new IIcon[3];

	public BlockCustomPumpkin(boolean lit)
	{
		super(lit);
		this.isLit = lit;
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
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		Block potentialCrop = access.getBlock(x, y, z);
		if(potentialCrop instanceof BlockCrop)
		{
			TECrop te = (TECrop) access.getTileEntity(x, y, z);
			CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
			float growth = te.growth/crop.numGrowthStages;
			int g = 0;
			if(growth >=0.75f && growth < 1)
			{
				g = 1;
			}
			else if(growth >=1)
			{
				g=2;
			}
			return side == 1 ? this.growthIconTop[g] : side == 0 ? this.growthIconTop[g] : // Top or Bottom Side
       		 side == 3 ? this.growthIconSide[g] :  side == 4 ? this.growthIconSide[g] : // Face Side
       			this.growthIconSide[g]; // Blank Side
		}
		return getIcon(side, access.getBlockMetadata(x, y, z));
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		// Intentionally blank to override the creation of snow and iron golems.
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.topIcon = iconRegister.registerIcon(this.getTextureName() + "_top");
		this.blockIcon = iconRegister.registerIcon(this.getTextureName() + "_side");
		this.faceIcon = this.isLit ? iconRegister.registerIcon(this.getTextureName() + "_face_on") : this.blockIcon; // Only have a face when lit.
		for(int i = 0 ; i < 3; i++)
		{
			this.growthIconSide[i] = iconRegister.registerIcon(Reference.MOD_ID + ":" + "plants/crops/pumpkin_side (" + i + ")");
			this.growthIconTop[i] = iconRegister.registerIcon(Reference.MOD_ID + ":" + "plants/crops/pumpkin_top (" + i + ")");
		}
	}

}
