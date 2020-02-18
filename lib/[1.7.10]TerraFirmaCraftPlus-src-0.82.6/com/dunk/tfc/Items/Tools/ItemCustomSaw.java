package com.dunk.tfc.Items.Tools;

import java.util.List;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Crafting.AnvilManager;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.ISize;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCustomSaw extends ItemCustomAxe implements ISize
{
	public ItemCustomSaw(ToolMaterial e)
	{
		super(e, 0);
		this.setMaxDamage((int)(e.getMaxUses()*0.85));
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial()*1.35F;
	}

	@Override
	public float func_150893_a/*getStrVsBlock*/(ItemStack par1ItemStack, Block par2Block)
	{
		return par2Block != null && par2Block.getMaterial() == Material.wood ? this.efficiencyOnProperMaterial*1.35F : super.func_150893_a(par1ItemStack, par2Block);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{

		//Later, this will only work on valid surfaces
		if (itemstack != null)
		{
	/*		entityplayer.openGui(TerraFirmaCraft.instance, 54, entityplayer.worldObj, (int) entityplayer.posX,
					(int) entityplayer.posY, (int) entityplayer.posZ);*/
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(TFC_Core.translate("gui."+this.getReach(is).getName()));
		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
	}
	
	@Override
	public double getWeaponDamage(ItemStack is)
	{
		return super.getWeaponDamage(is)*0.25;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.MEDIUM;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}

	@Override
	public EnumItemReach getReach(ItemStack is){
		return EnumItemReach.SHORT;
	}
}