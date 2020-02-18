package com.dunk.tfc.Items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemCoat extends ItemClothing
{
	public ItemCoat(ClothingType c)
	{
		this.textureFolder +="armor/clothing/";
		myClothingType=c;
	}
	
	@Override
	public EquipType getEquipType(ItemStack is)
	{
		// TODO Auto-generated method stub
		return EquipType.BODY;
	}

	@Override
	public void onEquippedRender()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
    {
		if(stack.getItem() == this)
		{
			return armorType==1;
		}
		return false;
    }
	

	@Override
	public boolean getTooHeavyToCarry(ItemStack is)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
