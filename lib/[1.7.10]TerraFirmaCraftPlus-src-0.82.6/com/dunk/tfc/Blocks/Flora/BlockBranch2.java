package com.dunk.tfc.Blocks.Flora;

import com.dunk.tfc.TileEntities.TEFruitTreeWood;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumTree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBranch2 extends BlockBranch
{

	public BlockBranch2()
	{
		super();
		this.woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
		this.sideIcons = new IIcon[woodNames.length];
		this.innerIcons = new IIcon[woodNames.length];
		this.rotatedSideIcons = new IIcon[woodNames.length];
		this.isTwo = true;
	}
	
	@Override
	public int damageDropped(int dmg)
	{
		return dmg += 16;
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		super.harvestBlock(world, entityplayer, x, y, z, meta);		
	}
	
	@Override
	public boolean hasTileEntity(int metadata)
    {
        return Global.WOOD_ALL[(metadata+16)%Global.WOOD_ALL.length]=="Fruitwood" && isEnd();
    }

	@Override
    public TileEntity createTileEntity(World world, int metadata)
    {
		TileEntity te = Global.WOOD_ALL[metadata+16]=="Fruitwood" && isEnd()?new TEFruitTreeWood():null;
		if(te != null)
		{
			((TEFruitTreeWood)te).initBirth();
		}
        return te;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= woodNames.length)
			meta = 0;
		if (side == 0 || side == 1)
			return innerIcons[meta];
		return sideIcons[meta];
	}
	
	@Override
	protected boolean noLogsNearby(World world, int x, int y, int z)
	{
		return world.blockExists(x, y, z) && (world.getBlock(x, y, z) != this && world.getBlock(x,y,z) != TFCBlocks.logNatural2);
	}
}
