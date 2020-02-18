package com.dunk.tfc.Items;

import com.dunk.tfc.api.Interfaces.IEquipable.EquipType;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemShirt extends ItemClothing
{
	
	
	public ItemShirt(ClothingType c)
	{
		this.textureFolder +="armor/clothing/";
		myClothingType=c;
	}
	
	public ItemShirt(EquipType[] ex,ClothingType c)
	{
		super(ex);
		this.textureFolder +="armor/clothing/";
		myClothingType=c;
	}
	
	public ItemShirt(EquipType over, EquipType[] ex,ClothingType c)
	{
		super(ex);
		this.overrideEquip = over;
		this.textureFolder +="armor/clothing/";
		myClothingType=c;
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
    {
		if(stack.getItem() == this)
		{
			return armorType==1 && overrideEquip == EquipType.BODY;
		}
		return false;
    }
	
	@Override
	public EquipType getEquipType(ItemStack is)
	{
		// TODO Auto-generated method stub
		return overrideEquip !=null?overrideEquip:EquipType.BODY2;
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
