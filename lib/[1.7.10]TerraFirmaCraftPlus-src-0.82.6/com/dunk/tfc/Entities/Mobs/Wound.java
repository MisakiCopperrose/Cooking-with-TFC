package com.dunk.tfc.Entities.Mobs;

import net.minecraft.entity.Entity;

public class Wound
{
	public Entity sourceOfWound;
	public int timeRemaining;
	
	public Wound(int t, Entity e)
	{
		timeRemaining = t;
		sourceOfWound = e;
	}
	
	public Wound()
	{
		
	}
}
