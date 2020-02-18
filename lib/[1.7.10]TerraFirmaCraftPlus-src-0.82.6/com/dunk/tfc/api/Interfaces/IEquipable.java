package com.dunk.tfc.api.Interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IEquipable 
{
	EquipType getEquipType(ItemStack is);

	static enum EquipType
	{
		BACK,HEAD2,BODY2,LEGS2,FEET2,BODY,LEGS,FEET,HEAD, NULL;
	}
	
	static enum ClothingType
	{
		SHIRT,COAT,ROBE,PANTS,THINPANTS,THINSHIRT,THINCOAT,SANDALS,SKIRT,BOOTS,HEAVYCOAT,HEAVIERCOAT,CLOAK,FULLBOOTS,SOCKS,CLOTH_HAT,STRAW_HAT,NULL;
	}

	void onEquippedRender();

	boolean getTooHeavyToCarry(ItemStack is);
	
	ResourceLocation getClothingTexture(Entity entity, ItemStack itemstack, int num);
	
	ClothingType getClothingType();
}
