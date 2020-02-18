package com.dunk.tfc.Items;

import java.util.List;

import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.api.Interfaces.IEquipable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemSocks extends ItemClothing implements IEquipable
{

	protected float walkableBonus = 0f;

	public ItemSocks(ClothingType c)
	{
		this.textureFolder += "armor/clothing/";
		myClothingType = c;
	}

	@Override
	public EquipType getEquipType(ItemStack is)
	{
		// TODO Auto-generated method stub
		return EquipType.FEET2;
	}

	@Override
	public void onEquippedRender()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		super.addExtraInformation(is, player, arraylist);
		// Extra information for boots or something.
		if (TFC_Core.showShiftInformation() && walkableBonus != 0)
		{
			// Show specific material informations

			arraylist.add(TFC_Core.translate("gui.walkable_bonus_socks") + ":  "
					+ (walkableBonus * 100) + "%");
		}
	}

	public ItemSocks setWalkableBonus(float f)
	{
		this.walkableBonus = f;
		return this;
	}

	public float getWalkableBonus()
	{
		return this.walkableBonus;
	}

	@Override
	public boolean getTooHeavyToCarry(ItemStack is)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
