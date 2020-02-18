package com.dunk.tfc.Items;

import com.dunk.tfc.api.Enums.EnumAmmo;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.IQuiverAmmo;

import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemArrow extends ItemTerra implements IQuiverAmmo
{
	public ItemArrow()
	{
		super();
		this.setSize(EnumSize.LARGE);
		this.setWeight(EnumWeight.LIGHT);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon("minecraft:arrow");
	}

	@Override
	public EnumAmmo getAmmoType() 
	{
		return EnumAmmo.ARROW;
	}
}
