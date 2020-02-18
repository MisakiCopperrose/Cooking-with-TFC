package com.dunk.tfc.Blocks.Devices;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Blocks.BlockTerraContainer;
import com.dunk.tfc.Items.ItemMudBrick;
import com.dunk.tfc.TileEntities.TEDryingBricks;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDryingBricks extends BlockTerraContainer
{
	protected IIcon[] icons;
	public BlockDryingBricks()
	{
		super(Material.ground);
		this.setBlockBounds(0, 0, 0, 1, 0.05f, 1);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}


	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= icons.length)
			return icons[icons.length - 1];
		return icons[meta];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		int count = Global.STONE_ALL.length;
		icons = new IIcon[count];
		for (int i = 0; i < count; i++)
			icons[i] = registerer
					.registerIcon(Reference.MOD_ID + ":" + "soil/Mud " + Global.STONE_ALL[i]);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && player.isSneaking())
		{
			TEDryingBricks te = (TEDryingBricks) world.getTileEntity(x, y, z);
			ItemStack is = player.getHeldItem();
				if(hitX < 0.5 && hitZ <0.5)
				{
					if(te.storage[0] != null && (is == null || (is != null && !(is.getItem() instanceof ItemMudBrick))))
					{
						te.ejectItem(0);
						
						world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
					}
				}
				else if(hitX >=0.5 && hitZ < 0.5)
				{
					if(te.storage[1] != null && (is == null || (is != null && !(is.getItem() instanceof ItemMudBrick))))
					{
						te.ejectItem(1);
						world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
					}
				}
				else if(hitX < 0.5 && hitZ >= 0.5)
				{
					if(te.storage[2] != null && (is == null || (is != null && !(is.getItem() instanceof ItemMudBrick))))
					{
						te.ejectItem(2);
						world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
					}
				}
				else
				{
					if(te.storage[3] != null && (is == null || (is != null && !(is.getItem() instanceof ItemMudBrick))))
					{
						te.ejectItem(3);
						world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
					}
				}
			return true;
		}
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.dryingBricksRenderId;
	}
	
	public void eject(World world, int x, int y, int z)
	{
		if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TEDryingBricks)
		{
			TEDryingBricks te = (TEDryingBricks) world.getTileEntity(x, y, z);
			te.ejectContents();
			world.removeTileEntity(x, y, z);
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		eject(world, x, y, z);
		return world.setBlockToAir(x, y, z);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j)
	{
		return null;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			if (!world.isSideSolid(x, y - 1, z, UP))
			{
				((TEDryingBricks)world.getTileEntity(x, y, z)).ejectContents();
				world.setBlockToAir(x, y, z);
				return;
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEDryingBricks();
	}
}
