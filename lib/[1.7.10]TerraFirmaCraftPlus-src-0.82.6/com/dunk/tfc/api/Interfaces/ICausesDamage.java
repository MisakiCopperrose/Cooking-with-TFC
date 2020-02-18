package com.dunk.tfc.api.Interfaces;

import com.dunk.tfc.api.Enums.EnumDamageType;

import net.minecraft.entity.EntityLivingBase;

public interface ICausesDamage 
{
	EnumDamageType getDamageType(EntityLivingBase entity);
}
