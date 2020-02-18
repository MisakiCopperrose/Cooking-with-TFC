package com.dunk.tfc.Items;

import com.dunk.tfc.Reference;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemBasket extends ItemTerra
{

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if (side == 0)
				--y;
			else if (side == 1)
				++y;
			else if (side == 2)
				--z;
			else if (side == 3)
				++z;
			else if (side == 4)
				--x;
			else if (side == 5)
				++x;
			if(!world.isSideSolid(x, y-1, z, ForgeDirection.UP))
			{
				return false;
			}
			world.setBlock(x, y, z, TFCBlocks.basket);
			itemstack.stackSize--;
			return true;
		}
		return false;
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if(meta < icons.length)
			return icons[meta];
		return icons[0];
	}

	private IIcon[] icons = new IIcon[1];
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + this.getUnlocalizedName().substring(5));		
	}
}
