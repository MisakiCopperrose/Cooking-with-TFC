package com.dunk.tfc.Items.Tools;

import com.dunk.tfc.api.Enums.EnumDamageType;
import com.dunk.tfc.api.Enums.EnumItemReach;

import net.minecraft.item.ItemStack;

public class ItemCustomSword extends ItemWeapon
{
	public ItemCustomSword(ToolMaterial par2EnumToolMaterial, float damage, EnumDamageType dt)
	{
		super(par2EnumToolMaterial, damage);
		//this.weaponDamage = 150 + par2EnumToolMaterial.getDamageVsEntity();
		damage = par2EnumToolMaterial.getDamageVsEntity();
		this.damageType = dt;
	}

	public ItemCustomSword(ToolMaterial par2EnumToolMaterial, float damage)
	{
		super(par2EnumToolMaterial, damage);
		//this.weaponDamage = 150 + par2EnumToolMaterial.getDamageVsEntity();
		this.damageType = EnumDamageType.SLASHING;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.MEDIUM;
	}
}
