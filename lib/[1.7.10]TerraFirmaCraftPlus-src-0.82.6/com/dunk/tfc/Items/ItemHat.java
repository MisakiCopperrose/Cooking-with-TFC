package com.dunk.tfc.Items;

import com.dunk.tfc.api.Interfaces.IEquipable.EquipType;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemHat extends ItemClothing
{

	
	public ItemHat(ClothingType c)
	{
		this.textureFolder +="armor/clothing/";
		myClothingType=c;
	}
	
	public ItemHat(EquipType[] ex,ClothingType c)
	{
		super(ex);
		this.textureFolder +="armor/clothing/";
		myClothingType=c;
	}
	
	public ItemHat(EquipType over, EquipType[] ex,ClothingType c)
	{
		super(ex);
		this.overrideEquip = over;
		this.textureFolder +="armor/clothing/";
		myClothingType=c;
	}
	
	@Override
	public EquipType getEquipType(ItemStack is)
	{
		// TODO Auto-generated method stub
		return overrideEquip !=null?overrideEquip:EquipType.HEAD2;
	}


	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
    {
		if(stack.getItem() == this)
		{
			return armorType==0 && overrideEquip == EquipType.HEAD;
		}
		return false;
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
