package com.dunk.tfc.api.Interfaces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;

public interface IAttackSpeed
{

	public Item setAttackSpeed(int i);
	
	public int getAttackSpeed(EntityLivingBase entity);
}
