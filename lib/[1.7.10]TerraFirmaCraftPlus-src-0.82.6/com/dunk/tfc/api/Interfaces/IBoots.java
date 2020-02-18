package com.dunk.tfc.api.Interfaces;

import net.minecraft.block.material.Material;

public interface IBoots
{
	public IBoots setDefaultWalkable(float defaultSpeed);
	public float getSpeedBonus(Material walkingOn);
	public IBoots addWalkableSurface(Material surface, float speedBonus);
	public boolean areTrueBoots();
	public IBoots setTrueBoots(boolean b);
}
