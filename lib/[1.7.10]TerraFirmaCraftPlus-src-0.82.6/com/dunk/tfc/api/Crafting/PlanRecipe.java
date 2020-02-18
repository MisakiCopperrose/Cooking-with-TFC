package com.dunk.tfc.api.Crafting;

import com.dunk.tfc.api.Enums.RuleEnum;

import net.minecraft.util.IIcon;

public class PlanRecipe 
{
	public RuleEnum[] rules;
	public IIcon icon;

	public PlanRecipe(RuleEnum[] r, IIcon i)
	{
		rules = r.clone();
		icon = i;
	}

	public PlanRecipe(RuleEnum[] r)
	{
		rules = r.clone();
	}
}
