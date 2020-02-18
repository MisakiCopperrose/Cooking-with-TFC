package com.dunk.tfc.Items;

import net.minecraft.item.ItemStack;

public class ItemPants extends ItemClothing
{
	public ItemPants(ClothingType c)
	{
		this.textureFolder +="armor/clothing/";
		myClothingType=c;
	}
	
	@Override
	public EquipType getEquipType(ItemStack is)
	{
		// TODO Auto-generated method stub
		return EquipType.LEGS2;
	}

	@Override
	public void onEquippedRender()
	{
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public boolean getTooHeavyToCarry(ItemStack is)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
