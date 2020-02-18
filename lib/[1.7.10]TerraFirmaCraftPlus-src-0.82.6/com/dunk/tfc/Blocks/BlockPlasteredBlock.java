package com.dunk.tfc.Blocks;

import java.util.List;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.TileEntities.TEPlasteredBlock;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockPlasteredBlock extends BlockTerraContainer
{
	protected IIcon[] icons;

	public BlockPlasteredBlock(Material material)
	{
		super(Material.rock);
		icons = new IIcon[1];
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return  new TEPlasteredBlock();
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		return icons[0];
	}
	
	/*@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return replacedBlock.getItemDropped(metadata, rand, fortune);
	}*/
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}
	
	@Override
	public boolean removedByPlayer(World world,EntityPlayer player, int x, int y, int z, boolean canHarvest)
	{
		if(!canHarvest)
		{
			return false;
		}
		TEPlasteredBlock te = (TEPlasteredBlock) world.getTileEntity(x, y, z);
		world.setBlock(x, y, z, te.block, te.meta, 2);
		TFC_Core.addPlayerExhaustion(player, 0.001f);
		return false;//super.onBlockHarvested(world, x, y, z, m, player);
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "Plaster");
		//Blocks.planks.registerBlockIcons(registerer);
	}
}
