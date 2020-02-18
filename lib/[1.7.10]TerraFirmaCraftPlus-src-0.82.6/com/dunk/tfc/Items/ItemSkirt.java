package com.dunk.tfc.Items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemSkirt extends ItemClothing
{
	public ItemSkirt(ClothingType c)
	{
		this.textureFolder +="armor/clothing/";
		myClothingType = c;
	}
	
	public ItemSkirt(EquipType[] ex, ClothingType c)
	{
		super(ex);
		this.textureFolder +="armor/clothing/";
		myClothingType = c;
	}
	
	@Override
	public EquipType getEquipType(ItemStack is)
	{
		// TODO Auto-generated method stub
		return EquipType.LEGS;
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
			return armorType==2;
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
