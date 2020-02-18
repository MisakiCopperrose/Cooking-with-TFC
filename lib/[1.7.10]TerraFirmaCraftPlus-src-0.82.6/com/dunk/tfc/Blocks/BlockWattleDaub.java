package com.dunk.tfc.Blocks;

import java.util.ArrayList;
import java.util.Random;

import com.dunk.tfc.Reference;
import com.dunk.tfc.api.TFCItems;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockWattleDaub extends BlockTerra
{
	public BlockWattleDaub(Material m)
	{
		super(m);
	}
	
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		Random rand = new Random();
		drops.add(new ItemStack(TFCItems.stick,rand.nextInt(3)+1));
		return drops;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "daub");
	}
}
