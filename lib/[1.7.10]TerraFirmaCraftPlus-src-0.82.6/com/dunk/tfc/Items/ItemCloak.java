package com.dunk.tfc.Items;

import com.dunk.tfc.api.Interfaces.IEquipable.ClothingType;
import com.dunk.tfc.api.Interfaces.IEquipable.EquipType;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemCloak extends ItemClothing
{
	public ItemCloak(ClothingType c)
	{
		this.textureFolder +="armor/clothing/";
		myClothingType=c;
	}
	
	@Override
	public EquipType getEquipType(ItemStack is)
	{
		// TODO Auto-generated method stub
		return EquipType.BACK;
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
			return armorType==4;
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
